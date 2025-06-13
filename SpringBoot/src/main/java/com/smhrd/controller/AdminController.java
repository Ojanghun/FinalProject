package com.smhrd.controller;

import com.smhrd.entity.Admin;
import com.smhrd.repository.AdminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/adminLogin.do")
    public String showAdminLoginPage() {
        return "admin_login"; // 관리자 로그인 폼
    }

    @PostMapping("/adminLoginCheck")
    public String checkAdminLogin(@RequestParam String id, @RequestParam String pw,
                                   HttpSession session, Model model) {
        Admin admin = adminRepository.findByAdminIdAndAdminPw(id, pw);
        if (admin != null) {
            session.setAttribute("admin", admin);
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("loginError", "관리자 인증 실패");
            return "admin_login";
        }
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin_dashboard"; // 관리자 대시보드 화면
    }
}
