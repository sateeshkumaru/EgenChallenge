package com.egen.exception;

public class UserNotFoundException extends Exception {
	/**
	 * This class will throw user not found exception when user is not available in the DB
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String string) {
		super(string);
	}
}
