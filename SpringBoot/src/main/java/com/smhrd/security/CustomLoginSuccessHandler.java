// com.smhrd.security.CustomLoginSuccessHandler.java
package com.smhrd.security;

import com.smhrd.entity.Member;
import com.smhrd.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String userId = authentication.getName(); // 로그인한 아이디
        Member member = memberRepository.findById(userId).orElse(null);
        if (member != null) {
            HttpSession session = request.getSession();
            session.setAttribute("info", member); // ✔ 세션에 사용자 정보 저장
        }
        response.sendRedirect("/"); // 로그인 후 이동할 페이지
    }
}
