package com.mmt.adminui.controller;

import org.springframework.web.context.request.async.DeferredResult;

import com.mmt.adminui.pojo.NotificationResponse;
import com.mmt.adminui.pojo.SingleObNotificationResponse;

import rx.Subscriber;
import rx.exceptions.CompositeException;

public class NotificationResponseAggregator extends Subscriber<SingleObNotificationResponse> {

	NotificationResponse finalResponse = new NotificationResponse();
	DeferredResult<NotificationResponse> defResult;
	private Boolean errorSet = false;

	public NotificationResponseAggregator(DeferredResult<NotificationResponse> defResult) {
		this.defResult = defResult;
	}

	@Override
	public void onCompleted() {
		defResult.setResult(finalResponse);
	}

	@Override
	public void onError(Throwable e) {
		/*our observable uses merge with delay hence the exception will be of form composite exception if multiple observerable fail*/
		if (e instanceof CompositeException) {
			CompositeException ex = (CompositeException) e;
			finalResponse.setError("Some or All Notifications failed");
			for (Throwable individualex : ex.getExceptions()) {
				if (individualex instanceof SingleObNotificationResponse) {
					SingleObNotificationResponse errorResp = (SingleObNotificationResponse) individualex;
					finalResponse.getObErrorMap().put(errorResp.getObserver(), errorResp.getError());
				}
			}
		} else {
			/*this is the case when only one failure occurs*/
			if (e instanceof SingleObNotificationResponse) {
				SingleObNotificationResponse errorResp = (SingleObNotificationResponse) e;
				finalResponse.setError("Notification to "+errorResp.getObserver()+" failed");
				finalResponse.getObErrorMap().put(errorResp.getObserver(), errorResp.getError());
			}else{
				finalResponse.setError("Unknown Error occured");
				defResult.setErrorResult(finalResponse);
			}
		}
		defResult.setResult(finalResponse);
	}

	@Override
	public void onNext(SingleObNotificationResponse t) {
		if (t.getObserver() != null && t.getError() != null) {
			if (!errorSet) {
				errorSet = true;
				finalResponse.setError("Error In Notifying Some Observers");
			}
			finalResponse.getObErrorMap().put(t.getObserver(), t.getError());
		}

	}

}
