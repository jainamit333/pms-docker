package com.mmt.exceptions;

public class PastSchedulingException extends RuntimeException {

	private static final long serialVersionUID = 5684914980438964141L;

	public PastSchedulingException() {
		super();
	}

	public PastSchedulingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PastSchedulingException(String message, Throwable cause) {
		super(message, cause);
	}

	public PastSchedulingException(String message) {
		super(message);
	}

	public PastSchedulingException(Throwable cause) {
		super(cause);
	}

}
