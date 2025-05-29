package com.smhrd.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class KakaoController {

    @Value("${kakao.client-id}")
    private String KAKAO_CLIENT_ID; // 대문자로 변경 (Value 필드명은 자유)

    @Value("${kakao.redirect-uri}")
    private String KAKAO_REDIRECT_URI;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/oauth/kakao")
    public String kakaoCallback(@RequestParam("code") String code, HttpSession session) {
        try {
            // 1. 액세스 토큰 요청
            String tokenRequestUrl = "https://kauth.kakao.com/oauth/token";
            HttpHeaders tokenHeaders = new HttpHeaders();
            tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> tokenParams = new LinkedMultiValueMap<>();
            tokenParams.add("grant_type", "authorization_code");
            tokenParams.add("client_id", KAKAO_CLIENT_ID);
            tokenParams.add("redirect_uri", KAKAO_REDIRECT_URI);
            tokenParams.add("code", code);

            HttpEntity<MultiValueMap<String, String>> tokenRequestEntity = new HttpEntity<>(tokenParams, tokenHeaders);
            ResponseEntity<String> tokenResponseEntity = restTemplate.exchange(tokenRequestUrl, HttpMethod.POST, tokenRequestEntity, String.class);

            JsonNode tokenJson = objectMapper.readTree(tokenResponseEntity.getBody());
            String accessToken = tokenJson.get("access_token").asText();

            // 2. 사용자 정보 요청
            String userInfoRequestUrl = "https://kapi.kakao.com/v2/user/me";
            HttpHeaders userInfoHeaders = new HttpHeaders();
            userInfoHeaders.setBearerAuth(accessToken); // Authorization: Bearer {ACCESS_TOKEN}

            HttpEntity<Void> userInfoRequestEntity = new HttpEntity<>(userInfoHeaders);
            ResponseEntity<String> userInfoResponseEntity = restTemplate.exchange(userInfoRequestUrl, HttpMethod.POST, userInfoRequestEntity, String.class);

            JsonNode userInfoJson = objectMapper.readTree(userInfoResponseEntity.getBody());
            String nickname = userInfoJson.path("properties").path("nickname").asText();
            // Long kakaoId = userInfoJson.path("id").asLong(); // 카카오 고유 ID, 필요시 사용

            session.setAttribute("kakaoNickname", nickname);
            // session.setAttribute("kakaoRegistered", true); // 카카오 통해 왔음을 표시하는 플래그 (선택적)

        } catch (Exception e) {
            e.printStackTrace();
            // 사용자에게 에러를 알리는 것이 좋음 (예: RedirectAttributes 사용)
            return "redirect:/login?error=kakao_link_failed";
        }
        return "redirect:/join"; // 회원가입 페이지로 리다이렉트
    }
}