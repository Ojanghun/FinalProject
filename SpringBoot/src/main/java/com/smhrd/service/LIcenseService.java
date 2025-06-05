package com.smhrd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smhrd.entity.Atd_Log;
import com.smhrd.repository.LicenseRepository;

@Service
public class LIcenseService {

	@Autowired
	LicenseRepository repository;
	
	public void atd_check(String id) {
        Atd_Log log = new Atd_Log();
        log.setId(id);
        repository.save(log);
	}
}
