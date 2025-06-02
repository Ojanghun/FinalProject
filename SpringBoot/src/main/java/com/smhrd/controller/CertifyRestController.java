package com.smhrd.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tika.Tika;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smhrd.entity.MemberId;

import jakarta.servlet.http.HttpSession;

@RestController
public class CertifyRestController {

	// 비동기 파일 업로드
	// 넘어오는 데이터는 form 형식이기 때문에 vo or requestParam으로 받으면 된다.
	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file, HttpSession session) {
		// 기본 생성자로 생성시 현재 시간과 날짜 정보를 가진 Date 객체가 생성됩니다.
		Date date = new Date();
		
		// 원하는 형태의 포맷으로 날짜, 시간을 표현하기 위해서는 SimpleDateFormat 클래스를 이용합니다.
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		MemberId memberId = (MemberId) session.getAttribute("userId");

		try {
			Tika tika = new Tika(); // 파일 유효성 체크하는 라이브러리
			String contentType = tika.detect(file.getInputStream()); // MIME 타입을 확인

//			System.out.println(contentType);

			// 동영상 파일일 때만 업로드
			if (contentType.startsWith("video/")) {
				// 저장 경로
				String uploadDir = "C:\\Users\\smhrd3\\" + memberId.getId() + "\\";

				// 예외 방지와 안정성 확보
				File dir = new File(uploadDir);
				if (!dir.exists()) { // 해당 경로에 파일이 존재하지 않는다면,
					dir.mkdir(); // 해당 경로에 폴더 생성
				}

				// 파일 저장
				// File 객체는 실제 파일이 아닌 "경로 정보"를 담은 객체
				String filePath = uploadDir + dateFormat.format(date) + "_" + file.getOriginalFilename();

				// 업로드된 파일 데이터를 실제 디스크 상의 파일로 저장
				file.transferTo(new File(filePath));

				return "success";
			} else {
				// 동영상 파일이 아닐 시 typeMismatch 문자열 반환
				return "typeMismatch";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

}
