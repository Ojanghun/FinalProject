package com.smhrd.controller;

import java.io.File;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

	// 비동기 파일 업로드
	// 넘어오는 데이터는 form 형식이기 때문에 vo or requestParam으로 받으면 된다.
	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file) {

		try {
			// 저장 경로
			String uploadDir = "C:\\Users\\smhrd3\\";
			
			// 예외 방지와 안정성 확보
			File dir = new File(uploadDir);
			if(!dir.exists()) { // 해당 경로에 파일이 존재하지 않는다면,
				dir.mkdir(); // 해당 경로에 폴더 생성
			}
			
			// 파일 저장
			// File 객체는 실제 파일이 아닌 "경로 정보"를 담은 객체
			String filePath = uploadDir + file.getOriginalFilename();
			
			// 업로드된 파일 데이터를 실제 디스크 상의 파일로 저장
			file.transferTo(new File(filePath));
			
			return "success";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
			
		}
	}
	
}
