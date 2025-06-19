package com.smhrd.config;

import com.smhrd.security.CustomLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomLoginSuccessHandler loginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // ✅ CSRF 예외 처리
            .csrf(csrf -> csrf.ignoringRequestMatchers(
                "/loadExam/**",
                "/shuffle/**",
                "/correctAnswer/**",
                "/solution/**",
                "/pay/submit",
                "/loadExam1/**",
                "/atd_check/**",
                "/loadTopic/**",
                "/submitPbsData/**",
                "/adminLoginCheck",
                "/admin/plan-usage",             // ✅ 관리자 플랜 이용 통계
                "/admin/wrong-rate-page",        // ✅ 오답률 페이지
                "/admin/refund-rate-page",       // ✅ 환급률 페이지
                "/admin/pay-info",                // ✅ 결제 상세
                "/refund-session", // ✅ 환급 요청 세션 저장 경로 예외 추가
                "/chapResult/**"
            ))

            // ✅ 요청 경로별 권한 부여
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(
                    "/adminLogin.do",
                    "/adminLoginCheck",
                    "/refund-session",  // ✅ 여기에 추가!
                    "/admin/**"   // ✅ 관리자 대시보드 접근 허용
<<<<<<< Updated upstream

=======
                    //이거왜됨??
>>>>>>> Stashed changes
                ).permitAll()

                .requestMatchers(
                    "/",
                    "/main",
                    "/login",
                    "/join",
                    "/oauth/kakao",
                    "/webjars/**",
                    "/idCheck",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/searchLicenses",
                    "/main/plan-usage" // 🔹 여기에 추가!!
                ).permitAll()

                .anyRequest().authenticated()
            )

            // ✅ 사용자 로그인 설정
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("id")
                .passwordParameter("pw")
                .successHandler(loginSuccessHandler)
                .failureUrl("/login?error=true")
                .permitAll()
            )

            // ✅ 로그아웃 설정
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/main?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
