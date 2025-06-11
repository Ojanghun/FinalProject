package com.smhrd.repository;

import com.smhrd.entity.User_Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserScoreRepository extends JpaRepository<User_Score, Integer> {
   
}