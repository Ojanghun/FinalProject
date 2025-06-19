package com.smhrd.repository;

import com.smhrd.entity.User_Score;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserScoreRepository extends JpaRepository<User_Score, Integer> {
	   @Query(value="""
		   		SELECT *
					FROM user_score
					WHERE user_id= :userId
					ORDER BY sc_at DESC
		   		""", nativeQuery = true)
	   List<User_Score> chapResult(@Param("userId") String userId);
}