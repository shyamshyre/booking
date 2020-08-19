package com.shyam.booking.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Bookingsystem.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
	
	private String fileuploadpath;

	/**
	 * @return the fileuploadpath
	 */
	public String getFileuploadpath() {
		return fileuploadpath;
	}

	/**
	 * @param fileuploadpath the fileuploadpath to set
	 */
	public void setFileuploadpath(String fileuploadpath) {
		this.fileuploadpath = fileuploadpath;
	}
	
	
	
}
