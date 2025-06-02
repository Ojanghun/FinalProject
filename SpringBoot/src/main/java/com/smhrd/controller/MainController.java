package com.smhrd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

	@RequestMapping("/")
	public String main() {
		return "main";
	}
	
	@GetMapping("/promote")
	public String promote() {
		return "promote";
	}
	
	@GetMapping("/main")
    public String mainPage(@RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        if (logout != null) {
            model.addAttribute("logoutMessage", "성공적으로 로그아웃되었습니다.");
        }
        return "main";  // main.html 뷰로 이동
    }

}

