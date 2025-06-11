package com.smhrd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.Pbs_Log;

@Repository
public interface PbsLogRepository extends JpaRepository<Pbs_Log, Integer> {
	
	public List<Pbs_Log> findByUserIdAndPbsCheck(String id, int pbsCheck);
}
