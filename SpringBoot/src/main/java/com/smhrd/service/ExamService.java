package com.smhrd.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.smhrd.entity.Exam;
import com.smhrd.entity.Pbs_Log;
import com.smhrd.entity.Topic_Info;
import com.smhrd.entity.User_Score;
import com.smhrd.repository.ExamRepository;
import com.smhrd.repository.PbsLogRepository;
import com.smhrd.repository.TopicInfoRpository;
import com.smhrd.repository.UserScoreRepository;

@Service
public class ExamService {
	
	@Autowired
	private ExamRepository repository;
	
	@Autowired
	private TopicInfoRpository TIrepository;
	
	@Autowired
	private PbsLogRepository Pbsrepository;
	
	@Autowired
	private UserScoreRepository userScoreRepository;
	
	// 년도 별 문제 불러오기
	public List<Exam> loadExam(int category, int liIdx) { // 카테고리 값을 받아왔음, 2가지 값 받아오는게 가능할지도?
		System.out.println(category);
		if(category < 100) {
			if(category == 0) {
				return repository.findRandom100(liIdx);
			}else {
				return repository.findTop100ByExIdOrderByPbNum(category);
			}}else { // 유형별 문제는 0~56 
				return repository.findByPbTopicOrderByPbTopicAsc(category);
		}
	}
	
	// Topic info 가져오기
	public List<Topic_Info> loadTopic(int category){
		return TIrepository.findByTopicIdxOrderByTopicNumAsc(category);
	}
	
	// 년도 별 선지 랜덤화
	public List<List<String>> shuffle(int category) {
		
		List<Exam> exList = null;
		
		if(category < 100) { // 년도별 문제는 1~8 -> 그대로 1~8
			exList = repository.findTop100ByExIdOrderByPbNum(category);
		}else { // 유형별 문제는 0~56 -> 100~156
			exList = repository.findByPbTopicOrderByPbTopicAsc(category);
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


	public void updateTopic() {	
		TIrepository.updateTopicNumPlus100IfLessThan100();
		// TODO Auto-generated method stub
	}



	public void submitPbsData(Pbs_Log log) {
		Pbsrepository.save(log);	
	}

	@Transactional
	public void saveUserScoreFromPbsLog(String userId, LocalDateTime pbsAt, String exCat) {
		List<Pbs_Log> logs = Pbsrepository.findByUserIdAndPbsAt(userId, pbsAt);
	    if (logs.isEmpty()) return;

	    int[] scores = new int[6];
	    int liIdx = 0;

	    for (Pbs_Log log : logs) {
	        if (log.getPbsCheck() == 1) {
	            int chapter = (log.getPbNum() - 1) / 20;
	            if (chapter >= 0 && chapter < 6) {
	                scores[chapter]++;
	            }
	        }

	        // liIdx 가져오기 (1회만)
	        if (liIdx == 0) {
	            liIdx = repository.findById(log.getPbIdx())
	                                  .map(e -> e.getLiIdx())
	                                  .orElse(0);
	        }
	    }

	    User_Score score = new User_Score();
	    score.setUserId(userId);
	    score.setLiIdx(liIdx);
	    score.setExCat(exCat);
	    score.setScChap1(scores[0]);
	    score.setScChap2(scores[1]);
	    score.setScChap3(scores[2]);
	    score.setScChap4(scores[3]);
	    score.setScChap5(scores[4]);
	    score.setScChap6(scores[5]);
	    score.setScAt(pbsAt);

	    userScoreRepository.save(score);
		
	}
}
