package com.smhrd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smhrd.entity.Member;
import com.smhrd.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

	@Autowired
	private MemberService memberService;
	
    @Value("${kakao.client-id}")
    private String kakaoClientId;

    @Value("${kakao.redirect-uri}")
    private String kakaoRedirectUri;

    // 첫 화면으로 사용될 로그인 페이지
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            @RequestParam(value = "join_success", required = false) String joinSuccess,
                            Model model) {

        // 카카오 인증 요청 URL 생성하여 login.html로 전달
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize?client_id="
                            + kakaoClientId
                            + "&redirect_uri=" + kakaoRedirectUri
                            + "&response_type=code";
        model.addAttribute("kakaoAuthUrlForRegister", kakaoAuthUrl); // 회원가입용 URL임을 명시

        if (error != null) {
            model.addAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "성공적으로 로그아웃되었습니다.");
            return "main";
        }
        if (joinSuccess != null) { // MemberController의 joinProcess에서 전달한 FlashAttribute
             model.addAttribute("joinSuccessMessage", "회원가입이 완료되었습니다. 로그인해주세요.");
        }
        return "login"; // templates/login.html
    }

    // (이하 /join GET, POST 메소드 등은 이전과 동일하게 유지)
    @GetMapping("/join")
    public String joinPage(HttpSession session, Model model) {
        if (!model.containsAttribute("member")) {
            model.addAttribute("member", new Member());
        }

        String kakaoNickname = (String) session.getAttribute("kakaoNickname");
        if (kakaoNickname != null) {
            Member memberFromModel = (Member) model.getAttribute("member");
            if (memberFromModel != null) {
                memberFromModel.setName(kakaoNickname);
            }
            model.addAttribute("isKakaoNameFixed", true);
        }
        return "join";
    }

    @PostMapping("/join")
    public String joinProcess(@ModelAttribute Member member, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, HttpSession session) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.member", bindingResult);
            redirectAttributes.addFlashAttribute("member", member);
            return "redirect:/join";
        }
        try {
            memberService.join(member);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("joinError", e.getMessage());
            redirectAttributes.addFlashAttribute("member", member);
            return "redirect:/join";
        }
        session.removeAttribute("kakaoNickname");
        redirectAttributes.addFlashAttribute("joinSuccessMessage", "회원가입이 성공적으로 완료되었습니다. 로그인해주세요.");
        return "redirect:/login?join_success=true"; // RedirectAttributes 사용 시 ?join_success=true 불필요할 수 있음
                                                  // FlashAttribute는 리다이렉트 후 모델에 자동 추가됨
    }
	
}
