/**
 * 
 */
package com.shyam.booking.service;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;


/**
 * @author shyam
 *
 */
public interface FileStorageService {

	public String storeFile(MultipartFile file);
	public Resource loadFileAsResource(String fileName);
}
