/**
 * 
 */
package com.shyam.booking.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.shyam.booking.config.ApplicationProperties;
import com.shyam.booking.service.FileStorageService;
import com.shyam.booking.web.rest.errors.FileDoesNotExistException;
import com.shyam.booking.web.rest.errors.FileStorageException;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author shyam
 *
 */
@Service
@Transactional
public class FileStorageServiceImpl implements FileStorageService {
	
	
	
	private final Path fileStorageLocation;

	    @Autowired
	    public FileStorageServiceImpl(final ApplicationProperties applicationProperties) {
	        this.fileStorageLocation = Paths.get(applicationProperties.getFileuploadpath())
	                .toAbsolutePath().normalize();

	        try {
	            Files.createDirectories(this.fileStorageLocation);
	        } catch (final Exception ex) {
	            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
	        }
	    }

	@Override
	public String storeFile(final MultipartFile file) {
		 final String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	        try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }

	            // Copy file to the target location (Replacing existing file with the same name)
	            final Path targetLocation = this.fileStorageLocation.resolve(fileName);
	            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

	            return fileName;
	        } catch (final IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }
	}

	@Override
	public Resource loadFileAsResource(final String fileName) {
		 try {
	            final Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
	            final Resource resource = new UrlResource(filePath.toUri());
	            if(resource.exists()) {
	                return resource;
	            } else {
	                throw new FileDoesNotExistException("File not found " + fileName);
	            }
	        } catch (final MalformedURLException ex) {
	            throw new FileDoesNotExistException("File not found " + fileName, ex);
	        }
	    }
	}


