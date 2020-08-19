/**
 * 
 */
package com.shyam.booking.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @author shyam
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileDoesNotExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1895761719612201812L;

	/**
	 * 
	 */
	public FileDoesNotExistException(String message) {
		 super(message);
		// TODO Auto-generated constructor stub
	}
	
	public FileDoesNotExistException(String message, Throwable cause) {
		 super(message,cause);
		// TODO Auto-generated constructor stub
	}

}
