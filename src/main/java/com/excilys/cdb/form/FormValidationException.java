package com.excilys.cdb.form;

public class FormValidationException extends Exception {

	private static final long serialVersionUID = 1100000003L;

	public FormValidationException() {
	}

	public FormValidationException(String message) {
		super(message);
	}

	public FormValidationException(Throwable cause) {
		super(cause);
	}

	public FormValidationException(String message, Throwable cause) {
		super(message, cause);
	}

}
