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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
                new AntPathRequestMatcher("/loadExam/**"),
                new AntPathRequestMatcher("/shuffle/**"),
                new AntPathRequestMatcher("/correctAnswer/**"),
                new AntPathRequestMatcher("/solution/**"),
                new AntPathRequestMatcher("/pay/submit"),
                new AntPathRequestMatcher("/loadExam1/**"),
                new AntPathRequestMatcher("/atd_check/**"),
                new AntPathRequestMatcher("/loadTopic/**"),
                new AntPathRequestMatcher("/submitPbsData/**"),
                new AntPathRequestMatcher("/adminLoginCheck")  // ✅ 관리자 로그인 예외 처리
            ))

            // ✅ 요청 경로별 권한 부여
            .authorizeHttpRequests(authz -> authz
                // ✅ 관리자 경로 전부 예외 처리 (인증 안 받아도 됨)
                .requestMatchers(
                    new AntPathRequestMatcher("/adminLogin.do"),
                    new AntPathRequestMatcher("/adminLoginCheck"),
                    new AntPathRequestMatcher("/admin/**")
                ).permitAll()

                // ✅ 공개 경로
                .requestMatchers(
                    new AntPathRequestMatcher("/"),
                    new AntPathRequestMatcher("/main"),
                    new AntPathRequestMatcher("/login"),
                    new AntPathRequestMatcher("/join"),
                    new AntPathRequestMatcher("/css/**"),
                    new AntPathRequestMatcher("/js/**"),
                    new AntPathRequestMatcher("/images/**")
                ).permitAll()

                // ✅ 그 외는 인증 필요
                .anyRequest().authenticated()
            )

            // ✅ 사용자 로그인 설정
            .formLogin(form -> form
                .loginPage("/login") // 일반 사용자 로그인 폼
                .loginProcessingUrl("/login") // 일반 사용자 로그인 처리
                .usernameParameter("id")
                .passwordParameter("pw")
                .successHandler(loginSuccessHandler)
                .failureUrl("/login?error=true")
                .permitAll()
            )

            // ✅ 로그아웃 설정
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
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
