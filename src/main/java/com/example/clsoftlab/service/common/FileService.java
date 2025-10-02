package com.example.clsoftlab.service.common;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

	 @Value("${file.upload.dir}")
	 private String uploadDir;
	 
	 public String saveFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
	        if (multipartFile.isEmpty()) {
	            return null;
	        }
	        File uploadPath = new File(uploadDir);
	        if (!uploadPath.exists()) {
	            // mkdirs()는 C:/dev/uploads/ 와 같이 중간 경로의 폴더들도 모두 생성해줍니다.
	            uploadPath.mkdirs(); 
	        }

	        String originalFilename = multipartFile.getOriginalFilename();
	        String storedFilename = UUID.randomUUID().toString() + "_" + originalFilename;
	        
	        // 3. 지정된 경로에 파일 저장
	        File storeFile = new File(uploadDir, storedFilename);

	        multipartFile.transferTo(storeFile);
	        // 4. 저장된 파일명(이것이 attachId가 됨) 반환
	        System.out.println("저장 경로: " + storeFile.getAbsolutePath());
	        return storedFilename;
	    }
}
