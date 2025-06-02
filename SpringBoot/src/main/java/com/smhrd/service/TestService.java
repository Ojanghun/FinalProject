package com.smhrd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smhrd.repository.TestRepository;

@Service
public class TestService {
	@Autowired
	TestRepository repository;
	
	
}
