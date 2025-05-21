package com.smhrd.controller; // 실제 패키지 경로로 수정

import com.smhrd.entity.Member;
import com.smhrd.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        Member loggedInUser = (Member) session.getAttribute("loggedInUser"); // Spring Security 사용 시 Principal 객체 활용
        if (loggedInUser != null) {
            model.addAttribute("username", loggedInUser.getName());
        }
        return "index"; // 메인 페이지 (index.html)
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "성공적으로 로그아웃되었습니다.");
        }
        return "login"; // templates/login.html
    }

    @GetMapping("/join")
    public String joinPage(HttpSession session, Model model) {
        String kakaoNickname = (String) session.getAttribute("kakaoNickname");
        if (kakaoNickname != null) {
            model.addAttribute("formNickname", kakaoNickname);
            // 세션에서 사용 후 제거 (선택 사항, F5 연타 시 문제 방지)
            // session.removeAttribute("kakaoNickname");
        }
        if (!model.containsAttribute("member")) { // RedirectAttributes로 전달된 Member가 없을 경우 새 객체 전달
             model.addAttribute("member", new Member());
        }
        return "join"; // templates/join.html
    }

    @PostMapping("/join")
    public String joinProcess(Member member, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // 여기에 추가적인 유효성 검사 (예: @Valid 어노테이션 사용)
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.member", bindingResult);
            redirectAttributes.addFlashAttribute("member", member);
            return "redirect:/join";
        }
        try {
            memberService.join(member);
        } catch (IllegalArgumentException e) {
            // ID 중복 등 예외 처리
            redirectAttributes.addFlashAttribute("joinError", e.getMessage());
            redirectAttributes.addFlashAttribute("member", member); // 입력값 유지를 위해 다시 전달
            return "redirect:/join";
        }
        return "redirect:/login?join_success=true"; // 회원가입 성공 시 로그인 페이지로
    }
}