package com.smhrd.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smhrd.entity.Li_Info;
import com.smhrd.entity.Member;
import com.smhrd.entity.Pbs_Log;
import com.smhrd.entity.Topic_Info;
import com.smhrd.entity.User_Score;
import com.smhrd.service.LicenseService;
import com.smhrd.entity.Ex_Info;
import com.smhrd.repository.ExInfoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {
	
	@Autowired
	LicenseService licenseservice;
	
	@Autowired
	private ExInfoRepository exinfoRepository;


	@GetMapping("/license")
	public String License(@RequestParam("liIdx") int liIdx, HttpSession session, Model model) {
		model.addAttribute("liIdx", liIdx);
		// session에서 info로 저장된 Member 데이터를 꺼내서 member에 저장
		Member member = (Member) session.getAttribute("info");
		String id = null;
		if (member != null) {
			// member 값이 있을 때 모델에 member이라는 이름으로 저장
			model.addAttribute("member", member);
			
			// 로그인한 id값 저장해두기
			id = member.getId();
		}
		
		// 자격증 정보
		List<Li_Info> licenseList = licenseservice.liInfo(liIdx);
		model.addAttribute("licenseList", licenseList);
		System.out.println("li정보 : "+licenseList);
		
		// 위에서 로그인시 받아온 id 값 기준으로 /출석된 날짜들 서비스에서 불러오기
		List<LocalDate> date = licenseservice.getAllAttendanceDates(id);
		
		if (date != null) {
			// 모델에 저장
			model.addAttribute("date", date);	
		}
		
		
		// 토픽 정보
		List<Topic_Info> topicList = licenseservice.topicInfo(liIdx);
		model.addAttribute("topicList", topicList);
		
		LocalDateTime now = LocalDateTime.now();
        List<Ex_Info> exams = exinfoRepository.findByExStdAfterOrderByExStdAsc(now);

        model.addAttribute("exams", exams);
        model.addAttribute("dateFormatter", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        
        List<Pbs_Log> pbsLog = licenseservice.PbsLog(id);
        List<Integer> pbIdxList = new ArrayList<>();
        
        for(int i=0;i<pbsLog.size(); i++) {
        	pbIdxList.add(pbsLog.get(i).getPbIdx());
        }
        model.addAttribute("pbIdxList", pbIdxList);
        
		// 기출 문제 - 회차 정보 불러오기
		List<Object[]> rounds = exinfoRepository.findAllYearsAndRounds(liIdx);
		model.addAttribute("rounds", rounds);
		
		// 오답노트 top5
		List<Object[]> top5List = licenseservice.wrongRate(id);
		model.addAttribute("top5List", top5List);
		
		
		// 과목별 점수
		User_Score subjectScore = licenseservice.subjectScore(id);
		model.addAttribute("subjectScore", subjectScore);
		System.out.println(subjectScore);
		
		return "license";
	}

    // 생성자를 통한 의존성 주입
    public ProductController(ExInfoRepository exinfoRepository) {
        this.exinfoRepository = exinfoRepository;
    }
	

	@GetMapping("/topic")
	public String subject() {
		return "topic";
	}
	 

	
}
