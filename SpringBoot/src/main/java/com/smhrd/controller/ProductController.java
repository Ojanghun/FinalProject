package com.smhrd.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smhrd.entity.Ex_Info;
import com.smhrd.entity.Member;
import com.smhrd.repository.ExInfoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {
	
	private final ExInfoRepository exinfoRepository;

    // 생성자를 통한 의존성 주입
    public ProductController(ExInfoRepository exinfoRepository) {
        this.exinfoRepository = exinfoRepository;
    }
	
    @GetMapping("/license")
    public String license(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("info");
        if (member != null) {
            model.addAttribute("member", member);
        }

        LocalDateTime now = LocalDateTime.now();
        List<Ex_Info> exams = exinfoRepository.findByExStdAfterOrderByExStdAsc(now);

        model.addAttribute("exams", exams);
        model.addAttribute("dateFormatter", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        return "license";
    }
    
	@GetMapping("/topic")
	public String subject() {
		return "topic";
	}
	
    

	
}
