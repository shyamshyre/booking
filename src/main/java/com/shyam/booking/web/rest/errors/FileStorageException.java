/**
 * 
 */
package com.shyam.booking.web.rest.errors;

/**
 * @author shyam
 *
 */
public class FileStorageException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8815572591296018489L;

	public FileStorageException(String message) {
        super(message);
    }
	
	 public FileStorageException(String message, Throwable cause) {
	        super(message, cause);
	    }

}
