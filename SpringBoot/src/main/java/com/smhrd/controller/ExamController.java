package com.smhrd.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.entity.Exam;
import com.smhrd.entity.Pbs_Log;
import com.smhrd.entity.Topic_Info;
import com.smhrd.service.ExamService;

@RestController
public class ExamController {

	@Autowired
	private ExamService service;
	
	// 문제 불러오기
	@PostMapping("/loadExam")
	public List<Exam> loadExam(@RequestParam("category") int category, @RequestParam("liIdx") int liIdx) {
		
		// 서비스에 문제 데이터 요청
		List<Exam> exList =service.loadExam(category,liIdx); // 여기가 문제
		System.out.println("문제 데이터: "+exList);
		return exList;
	}
	
	// 선지 랜덤화
	@PostMapping("/shuffle")
	public List<List<String>> shuffle(@RequestParam("category") int category, @RequestParam("liIdx") int liIdx) {
		List<List<String>> choice = service.shuffle(category,liIdx);
			
//		Ex.setPbQues("문제1. 다음 중 먹고싶은 것은?");
//		Ex.setPbChoi1("유정씨가 내려주시는 커피");
//		Ex.setPbChoi2("장훈씨가 사주시는 치킨");
//		Ex.setPbChoi3("성현씨가 쏘는 피자");
//		Ex.setPbChoi4("조은씨가 만들어주시는 돈까스");

		System.out.println("선지 데이터: "+choice);
	    return choice;
	}
	
	// Topic info 가져오기
	@PostMapping("/loadTopic/{category}")
	public List<Topic_Info> loadTopic(@PathVariable("category") int category){
		List<Topic_Info> topicInfo = service.loadTopic(category);
		System.out.println("토픽: "+topicInfo);
		System.out.println(category);
		return topicInfo;
	}
	
	// 해결책
	@PutMapping("/solution/{pbId}")
	public String solution(@PathVariable("pbId") int pbId){
		String PbSolu = service.solution(pbId);
		//System.out.println(PbSolu);
		return PbSolu;
	}
	
	// 정답 텍스트
	@PutMapping("/correctAnswer/{pbId}")
	public String correctAnswer(@PathVariable("pbId") int pbId) {
		String pbans = service.correctAnswer(pbId);
		System.out.println(pbans);
		return pbans;
	}
	

	
	
	@PostMapping("/submitPbsData")
	public void submitPbsData(@RequestBody List<Pbs_Log> dataList, @RequestParam String exCat) {
	    dataList.forEach(log -> {
	        Pbs_Log entity = new Pbs_Log();
	        entity.setUserId(log.getUserId());
	        entity.setPbIdx(log.getPbIdx());
	        entity.setPbsCheck(log.getPbsCheck());
	        entity.setPbsAt(log.getPbsAt());
	        entity.setPbNum(log.getPbNum());

	        service.submitPbsData(entity);
	    });
	    
	    String userId = dataList.get(0).getUserId();
	    LocalDateTime pbsAt = dataList.get(0).getPbsAt();
	    System.out.println("받아왔나??" + exCat);
	    // 점수 계산 및 저장
	    service.saveUserScoreFromPbsLog(userId, pbsAt, exCat);
	}
	
}
