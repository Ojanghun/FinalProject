package com.smhrd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smhrd.entity.MemberId;
import com.smhrd.service.CertifyService;

import jakarta.servlet.http.HttpSession;

@RestController
public class CertifyRestController {

	@Autowired
	private CertifyService certifyService;

	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file, HttpSession session,
			@RequestParam("payIdx") int payIdx, @RequestParam("planIdx") int planIdx,
			@RequestParam("holder") String holder, @RequestParam("bank") String bank,
			@RequestParam("account") String account) {

		MemberId memberId = (MemberId) session.getAttribute("userId");

		return certifyService.handleUpload(file, memberId, payIdx, planIdx, holder, bank, account);
	}
}
