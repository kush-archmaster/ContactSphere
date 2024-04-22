package com.contact.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUploadUtil {

	@Value("${project.image.dir}")
	private String path;
	
	public String saveImage(MultipartFile image) throws IOException {
		if(image.isEmpty()) {
			return "default.jpg";
		}
		
		String name = image.getOriginalFilename();
		/* create random id to avoid duplicate file name */
		String randomId = UUID.randomUUID().toString();
		String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));
		
		/* create folder if not created */
		File f = new ClassPathResource(path).getFile();
		Files.copy(image.getInputStream(), Paths.get(f.getAbsolutePath() + File.separator + fileName1), StandardCopyOption.REPLACE_EXISTING);
		return fileName1;
	}
}
