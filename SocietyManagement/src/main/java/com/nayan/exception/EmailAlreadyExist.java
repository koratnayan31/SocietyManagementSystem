package com.nayan.exception;

public class EmailAlreadyExist extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public EmailAlreadyExist() {
		super();
	}
	
	public EmailAlreadyExist(String message) {
		super(message);
	}

}
