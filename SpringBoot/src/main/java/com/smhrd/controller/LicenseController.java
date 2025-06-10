package com.smhrd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smhrd.entity.Li_Info;
import com.smhrd.entity.Member;
import com.smhrd.service.ExamService;
import com.smhrd.service.LicenseService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LicenseController {
	
	@Autowired
	LicenseService service;
	
	@Autowired
	ExamService Exservice;
	
	@PostMapping("/goExam.do")
	public String goExam(@RequestParam("category") int category,
			@RequestParam("liIdx") String liIdx,
		    @RequestParam("liName") String liName,
		    @RequestParam("liTime") String liTime,
		    @RequestParam("liPbc") String liPbc,
		    @RequestParam("liChap1") String liChap1,
		    @RequestParam("liChap2") String liChap2,
		    @RequestParam("liChap3") String liChap3,
		    @RequestParam("liChap4") String liChap4,
		    @RequestParam("liChap5") String liChap5,
		    Model model) {
		model.addAttribute("category", category);
	    model.addAttribute("liIdx", liIdx);
	    model.addAttribute("liName", liName);
	    model.addAttribute("liTime", liTime);
	    model.addAttribute("liPbc", liPbc);
	    model.addAttribute("liChap1", liChap1);
	    model.addAttribute("liChap2", liChap2);
	    model.addAttribute("liChap3", liChap3);
	    model.addAttribute("liChap4", liChap4);
	    model.addAttribute("liChap5", liChap5);
		System.out.println("카테고리 값: "+category);
		return "past";
	}
	
	@PostMapping("/goTest.do")
	public String goTest(
	    @RequestParam("category") String category,
	    @RequestParam("liIdx") int liIdx,
	    @RequestParam("liName") String liName,
	    @RequestParam("liTime") String liTime,
	    @RequestParam("liPbc") String liPbc,
	    HttpSession session,
	    Model model) {
		
		Member member = (Member) session.getAttribute("info");
		if (member != null) {
			// member 값이 있을 때 모델에 member이라는 이름으로 저장
			model.addAttribute("member", member);
		}
		
	    // 시간 가공 (분 → 시/분/초 포맷)
	    int totalMinutes = Integer.parseInt(liTime); // 예: "150"
	    int totalSeconds = totalMinutes * 60;

	    String formattedTime = String.format("%d시 %d분 %02d초", totalMinutes / 60, totalMinutes % 60, 0);

	    model.addAttribute("category", category);
	    model.addAttribute("liIdx", liIdx);
	    model.addAttribute("liName", liName);
	    model.addAttribute("liTime", formattedTime);     // 보기용
	    model.addAttribute("liTimeSec", totalSeconds);   // 타이머용
	    model.addAttribute("liPbc", liPbc);


	    return "test";
	}

	
	@RequestMapping("/goTopic.do")
	public String goTopic(@RequestParam("category") int category, Model model) {
		model.addAttribute("category", category);
		System.out.println("카테고리 값: "+category);
		return "topic";
	}
	
	// 출석체크
	@PostMapping("/atd_check")
	@ResponseBody
	public boolean atd_check(@RequestParam("id") String id) {
	    return service.atd_check(id);
	}
	
	// 발작버튼
	@PostMapping("/updateTopicNums.do")
	public String updateTopicNums() {
	    Exservice.updateTopic();
	    System.out.println("완료");
	    return "redirect:/license"; // 원하는 페이지로 이동
	}
	
	
}
