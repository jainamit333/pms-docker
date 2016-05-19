package com.mmt.adminui.pojo;

public class SingleObNotificationResponse extends Throwable {
	private static final long serialVersionUID = -2675299355640543740L;
	private String observer;
	private String error;

	public String getObserver() {
		return observer;
	}

	public void setObserver(String observer) {
		this.observer = observer;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public SingleObNotificationResponse() {
		super();
	}

	public SingleObNotificationResponse(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SingleObNotificationResponse(String message, Throwable cause) {
		super(message, cause);
	}

	public SingleObNotificationResponse(String message) {
		super(message);
	}

	public SingleObNotificationResponse(Throwable cause) {
		super(cause);
	}

}
