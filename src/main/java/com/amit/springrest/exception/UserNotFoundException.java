package com.amit.springrest.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String exMessage) {
		super(exMessage);
	}

}
