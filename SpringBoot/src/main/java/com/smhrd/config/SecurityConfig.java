package com.smhrd.config; // 실제 패키지 경로로 수정

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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    // 인증 없이 접근 허용할 경로들 명시
                    .requestMatchers(
                        new AntPathRequestMatcher("/login"),      // 로그인 페이지
                        new AntPathRequestMatcher("/join"),       // 회원가입 페이지
                        new AntPathRequestMatcher("/idCheck"),    // ID 중복확인 API
                        new AntPathRequestMatcher("/oauth/kakao"),// 카카오 로그인 콜백
                        new AntPathRequestMatcher("/css/**"),     // 정적 리소스
                        new AntPathRequestMatcher("/js/**"),
                        new AntPathRequestMatcher("/images/**"),
                        new AntPathRequestMatcher("/webjars/**")
                    ).permitAll()
                    // 그 외 모든 요청은 인증 필요 (루트 '/' 포함)
                    .anyRequest().authenticated()
            )
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/login")             // 사용자가 만든 커스텀 로그인 페이지 경로
                    .loginProcessingUrl("/login")    // 로그인 폼 데이터(action)를 처리할 URL (POST)
                    .usernameParameter("id")
                    .passwordParameter("pw")
                    .defaultSuccessUrl("/", true)    // 로그인 성공 시 리다이렉트될 기본 URL (예: 메인 페이지)
                    .failureUrl("/login?error=true") // 로그인 실패 시
                    .permitAll()                     // loginPage 자체는 접근 허용
            )
            .logout(logout ->
                logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
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