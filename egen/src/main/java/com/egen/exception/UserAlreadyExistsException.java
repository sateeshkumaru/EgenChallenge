package com.egen.exception;

public class UserAlreadyExistsException extends Exception {
	/**
	 * This class will throw user already exists exception.
	 */
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(String string) {
		super(string);
	}
}
