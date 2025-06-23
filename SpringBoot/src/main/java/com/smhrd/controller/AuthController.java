package com.smhrd.controller;

import com.smhrd.entity.Member;
import com.smhrd.service.MemberService;
import com.smhrd.service.SmsService;

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

    @Autowired
    private SmsService smsService;
    
    @Value("${kakao.client-id}")
    private String kakaoClientId;

    @Value("${kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {

        // 🔹 scope 추가하여 프로필 사진 강제 요청
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize?client_id="
                + kakaoClientId
                + "&redirect_uri=" + kakaoRedirectUri
                + "&response_type=code"
                + "&scope=profile_nickname,profile_image";

        System.out.println("kakaoAuthUrl 정보: "+kakaoAuthUrl);
        model.addAttribute("kakaoAuthUrlForRegister", kakaoAuthUrl);

        if (error != null) {
            model.addAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        if (logout != null) {
            model.addAttribute("logoutMessage", "성공적으로 로그아웃되었습니다.");
            return "main";
        }

        return "login";
    }

    
    @GetMapping("/join")
    public String joinPage(HttpSession session, Model model) {
        if (!model.containsAttribute("member")) {
            model.addAttribute("member", new Member());
        }

        String kakaoNickname = (String) session.getAttribute("kakaoNickname");
        String kakaoProfileImage = (String) session.getAttribute("kakaoProfileImage");

        System.out.println(">> 세션 nickname: " + kakaoNickname);
        System.out.println(">> 세션 profileImage: " + kakaoProfileImage);

        Member memberFromModel = (Member) model.getAttribute("member");
        if (memberFromModel != null) {
            if (kakaoNickname != null) {
                memberFromModel.setName(kakaoNickname);
                model.addAttribute("isKakaoNameFixed", true);
            }
            if (kakaoProfileImage != null && !kakaoProfileImage.isBlank()) {
                memberFromModel.setUserpro(kakaoProfileImage);
            }
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

        // 🔥 세션에서 프로필 이미지가 있다면 강제로 덮어쓰기
        String kakaoProfileImage = (String) session.getAttribute("kakaoProfileImage");
        if (kakaoProfileImage != null && !kakaoProfileImage.isBlank()) {
            member.setUserpro(kakaoProfileImage);
        }

        try {
            memberService.join(member);
            String cleanedPhone = member.getPhone().replaceAll("-", "");
            smsService.sendJoinSuccessMessage(cleanedPhone); // 회원가입 성공 시 문자 발송
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("joinError", e.getMessage());
            redirectAttributes.addFlashAttribute("member", member);
            return "redirect:/join";
        }

        session.removeAttribute("kakaoNickname");
        session.removeAttribute("kakaoProfileImage");

        redirectAttributes.addFlashAttribute("joinSuccessMessage", "회원가입이 성공적으로 완료되었습니다. 로그인해주세요.");
        return "redirect:/login";
    }
}
