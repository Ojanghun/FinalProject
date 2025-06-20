package com.smhrd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.Exam;
import com.smhrd.entity.Topic_Info;

import jakarta.transaction.Transactional;

@Repository
public interface TopicInfoRpository extends JpaRepository<Topic_Info, Integer> {
	
	List<Topic_Info> findByTopicIdxOrderByTopicNumAsc(int category);
	
	@Modifying
	@Transactional
	@Query("UPDATE Topic_Info t SET t.topicNum = t.topicNum + 100 WHERE t.topicNum < 100")
	void updateTopicNumPlus100IfLessThan100();

	List<Topic_Info> findByLiIdx(int i);

	
}
