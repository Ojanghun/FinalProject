package com.smhrd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smhrd.repository.LicenseRepository;

@Service
public class LIcenseService {

	@Autowired
	LicenseRepository repository;
	
	
}
