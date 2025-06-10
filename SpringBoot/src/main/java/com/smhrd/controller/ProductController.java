package com.smhrd.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smhrd.entity.Atd_Log;
import com.smhrd.entity.Li_Info;
import com.smhrd.entity.Member;
import com.smhrd.entity.Topic_Info;
import com.smhrd.repository.LiInfoRepository;
import com.smhrd.service.LicenseService;
import com.smhrd.entity.Ex_Info;
import com.smhrd.entity.Member;
import com.smhrd.repository.ExInfoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {
	
	@Autowired
	LicenseService licenseservice;

	@GetMapping("/license")
	public String license(HttpSession session, Model model) {
		// session에서 info로 저장된 Member 데이터를 꺼내서 member에 저장
		Member member = (Member) session.getAttribute("info");
		String id = null;
		if (member != null) {
			// member 값이 있을 때 모델에 member이라는 이름으로 저장
			model.addAttribute("member", member);
			
			// 출석을 위해 id값 저장해두기
			id = member.getId();
		}
		List<Li_Info> licenseList = licenseservice.liInfo();
		model.addAttribute("licenseList", licenseList);
		System.out.println("li정보 : "+licenseList);
		
		// 위에서 로그인시 받아온 id 값 기준으로 /출석된 날짜들 서비스에서 불러오기
		List<LocalDate> date = licenseservice.getAllAttendanceDates(id);
		
		 LocalDateTime now = LocalDateTime.now();
	     List<Ex_Info> exams = exinfoRepository.findByExStdAfterOrderByExStdAsc(now);

	     model.addAttribute("exams", exams);
	     model.addAttribute("dateFormatter", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

		
		if (date != null) {
			// 모델에 저장
			model.addAttribute("date", date);	
		}
		
		List<Topic_Info> topicList = licenseservice.topicInfo();
		model.addAttribute("topicList", topicList);
		
		return "license";
	}
	
	private final ExInfoRepository exinfoRepository;

    // 생성자를 통한 의존성 주입
    public ProductController(ExInfoRepository exinfoRepository) {
        this.exinfoRepository = exinfoRepository;
    }
	
    
	@GetMapping("/topic")
	public String subject() {
		return "topic";
	}
	
    

	
}
