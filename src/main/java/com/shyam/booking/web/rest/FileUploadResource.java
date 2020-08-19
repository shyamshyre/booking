/**
 * 
 */
package com.shyam.booking.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.shyam.booking.service.FileStorageService;
import com.shyam.booking.service.dto.FileUploadResponse;

import io.github.jhipster.web.util.HeaderUtil;

/**
 * @author shyam
 *
 */

@RestController
@RequestMapping("/api")
public class FileUploadResource {
	
	
	private final Logger log = LoggerFactory.getLogger(EmployeeInfoResource.class);

    @Autowired
    private FileStorageService fileStorageService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    /**
     * {@code POST  /uploadFile} : Uploads the file.
     *
     * @param employeeInfo the employeeInfo to update.
     * @return the {FileResponse} with status {@code 200 (OK)} and with body the updated employeeInfo,
     * or with status {@code 400 (Bad Request)} if the employeeInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    
    @PostMapping("/uploadFile")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
    	String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

//        return new FileUploadResponse(fileName, fileDownloadUri,
//                file.getContentType(), file.getSize());

        
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, "FileUpload", "SUCCESS"))
                .body(new FileUploadResponse(fileName, fileDownloadUri,
                        file.getContentType(), file.getSize()));
	}

    
    
    @PostMapping("/uploadMultipleFiles")
    public List<ResponseEntity<FileUploadResponse>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files)  {
    	
    	
        return Arrays.asList(files)
                .stream()
                .map(file -> {
					try {
						return uploadFile(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				 return null;
				})
                .collect(Collectors.toList());
    }
    
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
        	log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
