package com.smhrd.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smhrd.entity.Atd_Log;
import com.smhrd.entity.Li_Info;
import com.smhrd.entity.Pbs_Log;
import com.smhrd.entity.Topic_Info;
import com.smhrd.repository.LiInfoRepository;
import com.smhrd.repository.LicenseRepository;
import com.smhrd.repository.PbsLogRepository;
import com.smhrd.repository.TopicInfoRpository;

@Service
public class LicenseService {

	@Autowired
	LicenseRepository repository;
	
    @Autowired
    LiInfoRepository liInforepository;
    
    @Autowired
    TopicInfoRpository TopicInforepository;
    
    @Autowired
    PbsLogRepository PbsLogRepository;

	// 출석 가능 여부를 True와 False로 받아주기
	public boolean atd_check(String id) {
		 
		// 받아온 ID의 년,월,일로 날짜 비교해서 있으면 +1, 없으면 0 반환
	    int count = repository.checkUserAttendanceToday(id);	    
	    // count가 0 즉, 출석을 안했을 경우
	    if (count == 0) {
	    	// 객체 생성
	    	Atd_Log atd = new Atd_Log();
	    	// 객체의 UserId값을 받아온 id값으로 지정
	        atd.setUserId(id);
	        // atd에 insert
	        repository.save(atd);
	        return true; // 가능하면 true 반환
	    } else {
	        return false; // 불가능하면 false 반환
	    }
	}

	// 저장된 날짜들 List에 받아오기
	public List<LocalDate> getAllAttendanceDates(String id){
		// 출석 데이터를 atd라는 이름의 List에 담아주기
		List<Atd_Log> atd = repository.findByUserId(id);
		// 날짜 데이터만 받기위한 date라는 이름의 배열 생성 / (LocalDate는 날짜만, LocalDateTime는 시간까지 다룸)
		List<LocalDate> date = new ArrayList<>();
		
		for (int i=0; i<atd.size(); i++) {
			// toLocalDate로 시간 짤라내고 날짜만 가져오기.
			date.add(atd.get(i).getAtdAt().toLocalDate());
		}
		return date;
	}
	
	// 정보처리기사에 대한 정보 가져오기
	public List<Li_Info> liInfo(){
		return liInforepository.findByLiName("정보처리기사");
	}
	
	public List<Topic_Info> topicInfo(){
		return TopicInforepository.findByLiIdx(1);
	}
	
	public List<Pbs_Log> PbsLog(String id){
		List<Pbs_Log> pbsLog = PbsLogRepository.findByUserIdAndPbsCheck(id, 0);
		return pbsLog;
	}
	
	public void topicCount() {
		
		
	}

	
	
	
}
