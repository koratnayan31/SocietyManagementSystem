package com.nayan.exception;

public class FileTypeNotAllowedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FileTypeNotAllowedException() {
		super();
	}
	
	public FileTypeNotAllowedException(String message) {
		super(message);
	}

}
