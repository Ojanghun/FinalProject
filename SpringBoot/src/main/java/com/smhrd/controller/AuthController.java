package com.smhrd.controller;

import com.smhrd.entity.Member;
import com.smhrd.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private MemberService memberService;

    @Value("${kakao.client-id}")
    private String kakaoClientId;

    @Value("${kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {

        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize?client_id="
                            + kakaoClientId
                            + "&redirect_uri=" + kakaoRedirectUri
                            + "&response_type=code";
        model.addAttribute("kakaoAuthUrlForRegister", kakaoAuthUrl);

        if (error != null) {
            model.addAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "성공적으로 로그아웃되었습니다.");
        }

        // FlashAttribute: joinSuccessMessage는 자동 전달되므로 별도 처리 없음
        return "login";
    }

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
    public String joinProcess(@ModelAttribute Member member,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {

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

        // ✅ 회원가입 성공 메시지를 FlashAttribute로 전달
        redirectAttributes.addFlashAttribute("joinSuccessMessage", "회원가입이 성공적으로 완료되었습니다. 로그인해주세요.");
        return "redirect:/login";
    }
}
