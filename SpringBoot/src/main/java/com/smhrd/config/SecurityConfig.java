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
        .csrf(csrf -> csrf
        	    .ignoringRequestMatchers(
        	        new AntPathRequestMatcher("/loadExam/**"),
        	        new AntPathRequestMatcher("/shuffle/**"),
        	        new AntPathRequestMatcher("/correctAnswer/**"),
        	        new AntPathRequestMatcher("/solution/**"),
        	        new AntPathRequestMatcher("/pay/submit"),
        	        new AntPathRequestMatcher("/loadExam1/**"),
        	        new AntPathRequestMatcher("/atd_check/**"),
        	        new AntPathRequestMatcher("/loadTopic/**"),
        	        new AntPathRequestMatcher("/submitPbsData"),
        	        new AntPathRequestMatcher("/submitPbsData/**"),
        	        new AntPathRequestMatcher("/adminLoginCheck") // ✅ 관리자 로그인 POST 예외 처리
        	    )

            )
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(
                    new AntPathRequestMatcher("/"),
                    new AntPathRequestMatcher("/main"),
                    new AntPathRequestMatcher("/login"),
                    new AntPathRequestMatcher("/join"),
                    new AntPathRequestMatcher("/adminLogin.do"), // ✅ 관리자 로그인 페이지 허용
                    new AntPathRequestMatcher("/idCheck"),
                    new AntPathRequestMatcher("/oauth/kakao"),
                    new AntPathRequestMatcher("/css/**"),
                    new AntPathRequestMatcher("/js/**"),
                    new AntPathRequestMatcher("/images/**"),
                    new AntPathRequestMatcher("/webjars/**"),
                    new AntPathRequestMatcher("/plan"),
                    new AntPathRequestMatcher("/pay/**"), // ✅ 결제 관련 페이지 접근 허용
                    new AntPathRequestMatcher("/searchLicenses")
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("id")
                .passwordParameter("pw")
                .successHandler(loginSuccessHandler)
                .failureUrl("/login?error=true")
                .permitAll()
            )
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
