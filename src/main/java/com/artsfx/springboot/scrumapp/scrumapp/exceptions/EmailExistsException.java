package com.artsfx.springboot.scrumapp.scrumapp.exceptions;

public class EmailExistsException extends RuntimeException {

	public EmailExistsException() {
		// TODO Auto-generated constructor stub
	}

	public EmailExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public EmailExistsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public EmailExistsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public EmailExistsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
