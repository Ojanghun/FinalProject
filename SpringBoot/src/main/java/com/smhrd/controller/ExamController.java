package com.smhrd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.entity.Exam;
import com.smhrd.service.ExamService;

@RestController
public class ExamController {

	@Autowired
	private ExamService service;
	
	// 문제 불러오기
	@PostMapping("/loadExam/{category}")
	public List<Exam> loadExam(@PathVariable("category") int category) {
		
		// 서비스에 문제 데이터 요청
		List<Exam> exList =service.loadExam(category);
		System.out.println(exList);
		return exList;
	}
	
	// 선지 랜덤화
	@PostMapping("/shuffle/{category}")
	public List<List<String>> shuffle(@PathVariable("category") int category) {
		List<List<String>> choice = service.shuffle(category);
		
//		Ex.setPbQues("문제1. 다음 중 먹고싶은 것은?");
//		Ex.setPbChoi1("유정씨가 내려주시는 커피");
//		Ex.setPbChoi2("장훈씨가 사주시는 치킨");
//		Ex.setPbChoi3("성현씨가 쏘는 피자");
//		Ex.setPbChoi4("조은씨가 만들어주시는 돈까스");

		System.out.println(choice);
	    return choice;
	}
	
	// 해결책
	@PutMapping("/solution/{index}")
	public String solution(@PathVariable("index") int pbId){
		String PbSolu = service.solution(pbId);
		//System.out.println(PbSolu);
		return PbSolu;
	}
	
	// 정답 텍스트
	@PutMapping("/correctAnswer/{index}")
	public String correctAnswer(@PathVariable("index") int pbId) {
		String pbans = service.correctAnswer(pbId);
		System.out.println(pbans);
		return pbans;
	}
	
	@PostMapping("/loadExam1")
	public List<Exam> loadExam1(@RequestParam int page) {
	    int pageSize = 5; // 고정값이든 클라이언트에서 받아오든
	    return service.loadExam1(page, pageSize);
	}
	
	@PostMapping("/shuffle1")
	public List<List<String>> shuffle1(@RequestParam int page) {
		int pageNum = page; // 요청 받은 page 파라미터 사용
		int pageSize = 5;
		List<List<String>> choice = service.shuffle1(pageNum, pageSize);
	    return choice;
	}

	
	
}
