package com.smhrd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.projection.LiIdxAndLiName;
import com.smhrd.service.SearchService;


@RestController
public class SearchRestController {

	@Autowired
	SearchService service;
	
	@GetMapping("/searchLicenses")
	public List<LiIdxAndLiName> search() {
		return service.getLicensesName();
	}
	
}
