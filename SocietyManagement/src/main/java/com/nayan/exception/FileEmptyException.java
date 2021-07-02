package com.nayan.exception;

public class FileEmptyException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileEmptyException() {
		super();
	}
	
	public FileEmptyException(String message) {
		super(message);
	}

}
