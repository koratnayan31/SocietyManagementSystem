package com.nayan.helper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nayan.exception.FileEmptyException;
import com.nayan.exception.FileTypeNotAllowedException;
import com.nayan.exception.FileSizeLimitCrossedException;

@Component
public class FileUploader {

	private String getFormat(String name) {
		String[] split=name.split("\\.");
		return "."+split[split.length-1];
	}
	
	public String uploadFile(MultipartFile file, String folder,String fileName) throws FileEmptyException,FileSizeLimitCrossedException,FileTypeNotAllowedException{
		fileName=fileName+getFormat(file.getOriginalFilename());
		System.out.println(fileName);
		if(file.isEmpty()) {
			throw new FileEmptyException("Please choose File");
		}
		
		if(file.getSize()>10240000)
			throw new FileSizeLimitCrossedException("Maximum file size allowed is 10 MB");
		
		if(folder.equals("profileImage")) {
			if(!file.getContentType().startsWith("image/")) {
				throw new FileTypeNotAllowedException("Only Image file is allowed");
			}
		}
		
		if(folder.equals("ownerProof")) {
			if(!file.getContentType().equals("application/pdf")) {
				throw new FileTypeNotAllowedException("Only PDF file are allowed");
			}
		}
		
		if(folder.equals("adharcard")) {
			if(!(file.getContentType().equals("application/pdf")||file.getContentType().startsWith("image/"))) {
				throw new FileTypeNotAllowedException("Only PDF and Image files are allowed");
			}
		}
		
		try {
			String Path = new ClassPathResource("static/" + folder).getFile().getAbsolutePath();
			System.out.println(Path);
			Files.copy(file.getInputStream(), Paths.get(Path + File.separator +fileName),
					StandardCopyOption.REPLACE_EXISTING);
			String storagePath = ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + folder + "/"+fileName).toUriString();
			System.out.println(storagePath);
			return storagePath;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
