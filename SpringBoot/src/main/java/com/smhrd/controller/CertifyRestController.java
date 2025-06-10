package com.smhrd.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smhrd.entity.MemberId;
import com.smhrd.entity.Pay_Info;
import com.smhrd.repository.PayInfoRepository;
import com.smhrd.service.PayInfoService;

import jakarta.servlet.http.HttpSession;

@RestController
public class CertifyRestController {

	@Autowired
	PayInfoService payInfoService;
	
	// 비동기 파일 업로드
	// 넘어오는 데이터는 form 형식이기 때문에 vo or requestParam으로 받으면 된다.
	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file, @RequestParam("planIdx") int planIdx,
			HttpSession session) {

		LocalDate date = LocalDate.now(); // 현재 날짜 정보가 저장된 LocalDate

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd"); // yyyyMMdd 형식 포매터

		MemberId memberId = (MemberId) session.getAttribute("userId"); // 유저 ID

		// rf_act가 1인 자격증만 업로드 가능하게 해야 됨
		// liIdx 이걸 어떻게 받아오지?
		Pay_Info refundablePlan = payInfoService.getRefundablePlan(memberId.getId(), planIdx);
		
	    if (refundablePlan == null) {
	        return "noRefundablePlan";  // 또는 "invalidPlan", "notFound" 등
	    }
		
		LocalDate planEd = refundablePlan.getPlanEd().toLocalDate();
		
		if (date.isAfter(planEd) && date.isBefore(planEd.plusDays(7))) {
			try {
				Tika tika = new Tika(); // 파일 유효성 체크하는 라이브러리
				String contentType = tika.detect(file.getInputStream()); // MIME 타입을 확인

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
					String filePath = uploadDir + date.format(formatter) + "_" + file.getOriginalFilename();

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
		} else {
			return "uploadPeriodExpired";
		}

	}

}
