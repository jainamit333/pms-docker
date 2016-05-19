package com.mmt.adminui.controller.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.mmt.adminui.pojo.CopyOrMoveRequest;
import com.mmt.adminui.pojo.CreateNodeRequest;
import com.mmt.adminui.pojo.NodeOperationResponse;
import com.mmt.adminui.pojo.OperationResponse;
import com.mmt.adminui.pojo.RenameNodeRequest;

@Aspect
@Component
public class ExceptionControllerAdvice {
	@Pointcut("execution(com.mmt.adminui.pojo.NodeOperationResponse com.mmt.adminui.controller.AdminPanelNodesController.*(..)))")
	public void adminPanelControllerPointCut() {

	}

	@Pointcut("execution(com.mmt.adminui.pojo.OperationResponse com.mmt.adminui.controller.AdminPanelMiscController.*(..)))")
	public void adminPanelMiscControllerPointCut() {

	}

	@Around("adminPanelControllerPointCut()")
	public NodeOperationResponse handleDataIntegrityVoilation(ProceedingJoinPoint jp) {
		NodeOperationResponse op = new NodeOperationResponse();
		Object[] args = jp.getArgs();
		try {
			op = (NodeOperationResponse) jp.proceed(args);
		} catch (DataIntegrityViolationException e) {
			if (args[0] != null) {
				if (args[0] instanceof CreateNodeRequest) {
					if (e.getRootCause().getMessage().contains("NON_REPEAT_DIR")) {
						op.setError("Name of File Or FileSystemObject Duplicate");
					} else {
						op.setError("unknown error occured");
					}
				} else if (args[0] instanceof RenameNodeRequest || args[0] instanceof CopyOrMoveRequest) {
					if (e.getRootCause().getMessage().contains("NON_REPEAT_DIR")) {
						op.setError("File with Same Name exists in parent directory");
					} else {
						op.setError("unknown error occured");
					}
				} else {
					op.setError("unknown error occured");
				}
			}
		} catch(RuntimeException ex){
			op.setError(ex.getMessage());
		}catch (Throwable e) {
			op.setError("Unknown Error Occured");
		}
		return op;
	}

	@Around("adminPanelMiscControllerPointCut()")
	public OperationResponse handleMiscControllerExceptions(ProceedingJoinPoint jp) {
		OperationResponse resp=new OperationResponse();
		try{
			resp=(OperationResponse) jp.proceed(jp.getArgs());
		}catch(RuntimeException e){
			resp.setError(e.getMessage());
		} catch (Throwable e) {
			resp.setError("Unkown error occured");
		}
      return resp;
	}
}
