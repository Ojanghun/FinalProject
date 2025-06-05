package com.smhrd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.Atd_Log;


@Repository
public interface LicenseRepository extends JpaRepository<Atd_Log, Integer> {

	
}
