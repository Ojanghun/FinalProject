package com.smhrd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import com.smhrd.entity.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer>{

	// 년도 별 문제 불러오기
	List<Exam> findTop100ByExIdOrderByPbNum(int exId);
	
	List<Exam> findAllByOrderByPbTopicAsc();

	List<Exam> findAllByOrderByPbNum(Pageable pageable);
	
}
