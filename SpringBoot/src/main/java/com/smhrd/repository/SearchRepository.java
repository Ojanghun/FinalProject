package com.smhrd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.Li_Info;
import com.smhrd.projection.LiNameOnly;

@Repository
public interface SearchRepository extends JpaRepository<Li_Info, Integer>{

	List<LiNameOnly> findAllBy();
	
}