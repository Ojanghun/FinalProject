package com.smhrd.controller;

import com.smhrd.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberRestController {

    private final MemberService memberService;

    @Autowired
    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/idCheck")
    public ResponseEntity<Map<String, Boolean>> idCheck(@RequestParam("id") String id) {
        boolean isExists = memberService.isIdExists(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isAvailable", !isExists); // 사용 가능하면 true (존재하지 않으면), 중복이면 false
        return ResponseEntity.ok(response);
    }
}