package com.smhrd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/adminLogin.do")
    public String adminLoginPage() {
        return "admin_login"; // templates/admin_login.html로 이동
    }
}