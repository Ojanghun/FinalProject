package com.smhrd.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.Pbs_Log;

@Repository
public interface PbsLogRepository extends JpaRepository<Pbs_Log, Integer> {
	
	public List<Pbs_Log> findByUserIdAndPbsCheck(String id, int pbsCheck);
	
	@Query("SELECT p FROM Pbs_Log p WHERE p.userId = :userId AND p.pbsAt = :pbsAt")
	List<Pbs_Log> findByUserIdAndPbsAt(@Param("userId") String userId, @Param("pbsAt") LocalDateTime pbsAt);

}
