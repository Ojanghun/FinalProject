package com.smhrd.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smhrd.entity.Exam;
import com.smhrd.repository.ExamRepository;

@Service
public class ExamService {
	
	@Autowired
	private ExamRepository repository;
	
	// 년도 별 문제 불러오기
	public List<Exam> loadExam(int category) { // 카테고리 값을 받아왔음, 2가지 값 받아오는게 가능할지도?
		
		if(category < 100) {
			return repository.findTop100ByExIdOrderByPbNum(category);
		}else { // 유형별 문제는 0~56 
			return repository.findAllByOrderByPbTopicAsc();
		}
	}
	
	
	// 년도 별 선지 랜덤화
	public List<List<String>> shuffle(int category) {
		
		List<Exam> exList = null;
		
		if(category < 100) { // 년도별 문제는 1~8 -> 그대로 1~8
			exList = repository.findTop100ByExIdOrderByPbNum(category);
		}else { // 유형별 문제는 0~56 -> 100~156
			exList = repository.findAllByOrderByPbTopicAsc();
		}
			 
		// 섞인 선지 데이터 모든 세트를 담아줄 리스트
		List<List<String>> choice = new ArrayList<>();

	    for (int i = 0; i < exList.size(); i++) {
	    	// 선지 데이터 1 세트 담아줄 리스트
	        List<String> options = new ArrayList<>();
	        options.add(exList.get(i).getPbChoi1());
	        options.add(exList.get(i).getPbChoi2());
	        options.add(exList.get(i).getPbChoi3());
	        options.add(exList.get(i).getPbChoi4());

	        Collections.shuffle(options);  // 문제별로 선택지를 섞음

	        choice.add(options); // 섞은 걸 다시 리스트에 추가
	        
	    }
	    return choice;
	}
	
	
	
	
	// 해결책
	public String solution(int pbId) {
	    Optional<Exam> optionalExam = repository.findById(pbId+1);
	    
	    if (optionalExam.isPresent()) {
	        return optionalExam.get().getPbSolu();
	    } else {
	        throw new IllegalArgumentException("문제 ID가 유효하지 않습니다: " + (pbId+1));
	    }
	}
	
	public String correctAnswer(int pbId) {
		Exam exam = repository.findById(pbId+1).get();
		// 정답 번호
		int pbans = exam.getPbAns();
		// 정답
		
		switch (pbans) {
			case 1:
				return exam.getPbChoi1();
			case 2:
				return exam.getPbChoi2();
			case 3:
				return exam.getPbChoi3();
			case 4:
				return exam.getPbChoi4();
			default:
				return null;
		}
//		return exam.getPbAns();
	}
	

	public List<Exam> loadExam1(int pageNum, int pageSize) {
	    Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("pbNum").ascending());
	    return repository.findAllByOrderByPbNum(pageable);
	}

	public List<List<String>> shuffle1(int pageNum, int pageSize) {
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("pbNum").ascending());
		List<Exam> exList = repository.findAllByOrderByPbNum(pageable);
		List<List<String>> choice = new ArrayList<>();
		for (Exam exam : exList) {
			List<String> options = new ArrayList<>();
			options.add(exam.getPbChoi1());
			options.add(exam.getPbChoi2());
			options.add(exam.getPbChoi3());
			options.add(exam.getPbChoi4());
			Collections.shuffle(options);
			choice.add(options);
		}
		return choice;
	}

	
}
