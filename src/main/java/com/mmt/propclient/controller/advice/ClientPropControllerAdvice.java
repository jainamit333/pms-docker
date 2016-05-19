package com.mmt.propclient.controller.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.mmt.propclient.pojo.ClientPropsOPResponse;

@Aspect
@Component
public class ClientPropControllerAdvice {
	
	@Around("execution(String com.mmt.propclient.controller.ClientPropsController.getPropertyFile(String)))")
	public String handleDataIntegrityVoilation(ProceedingJoinPoint jp){
	    String resp;
		Object[] args=jp.getArgs();
		try{
			resp=(String)jp.proceed(args);
		}catch(RuntimeException e){
			resp=e.getMessage();
		}catch(Throwable e){
			resp="Unknown Error Occured";
		}
		return resp;
	}
	@Around("execution(com.mmt.propclient.pojo.ClientPropsOPResponse com.mmt.propclient.controller.ClientPropsController.*(..)))")
	public ClientPropsOPResponse handleExceptionUncknown(ProceedingJoinPoint jp){
		ClientPropsOPResponse resp=new ClientPropsOPResponse();
		Object[] args=jp.getArgs();
		try{
			resp=(ClientPropsOPResponse)jp.proceed(args);
		}
		catch(Throwable e){
			resp.setError(e.getMessage());
		}
		return resp;
	}
}
