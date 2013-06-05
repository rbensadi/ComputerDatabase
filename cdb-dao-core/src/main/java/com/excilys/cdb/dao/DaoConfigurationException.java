package com.excilys.cdb.dao;

public class DaoConfigurationException extends RuntimeException {
	
	private static final long serialVersionUID = 1100000001L;

	public DaoConfigurationException(String message) {
		super(message);
	}

	public DaoConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoConfigurationException(Throwable cause) {
		super(cause);
	}
}