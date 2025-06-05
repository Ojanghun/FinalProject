package com.smhrd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smhrd.projection.LiNameOnly;
import com.smhrd.repository.SearchRepository;

@Service
public class SearchService {

	@Autowired
	SearchRepository searchRepository;
	
	public List<LiNameOnly> getLicensesName(){
		return searchRepository.findAllBy();
	}
	
}
