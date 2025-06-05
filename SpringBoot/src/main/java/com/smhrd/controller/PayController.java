package com.smhrd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PayController {
    @GetMapping("/pay")
    public String pay(@RequestParam("plan") String plan, Model model) {
        model.addAttribute("plan", plan); // 플랜 정보를 템플릿에 전달
        return "pay"; // templates/pay.html 렌더링
    }
}