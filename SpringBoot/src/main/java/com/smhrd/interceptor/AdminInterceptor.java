package com.smhrd.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        HttpSession session = request.getSession(false);

        // ✅ 특정 경로는 무조건 통과
        if (uri.equals("/adminLogin.do") || uri.equals("/adminLoginCheck") || uri.equals("/refund-session")) {
            return true;
        }

        // ✅ 세션에 admin 속성이 있으면 통과
        if (session != null && session.getAttribute("admin") != null) {
            return true;
        }

        // ✅ AJAX 요청이면 401 Unauthorized 응답
        if (isAjax) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // ✅ 그 외에는 로그인 페이지로 리다이렉트
        response.sendRedirect("/adminLogin.do");
        return false;
    }
}
