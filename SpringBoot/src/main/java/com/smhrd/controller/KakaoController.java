package com.smhrd.controller; // 실제 패키지 경로로 수정

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
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    // RestTemplate을 Bean으로 등록하고 주입받는 것을 권장
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/oauth/kakao")
    public String kakaoLogin(@RequestParam("code") String code, HttpSession session) {
        try {
            // 1. 액세스 토큰 요청
            String tokenRequestUrl = "https://kauth.kakao.com/oauth/token";
            HttpHeaders tokenHeaders = new HttpHeaders();
            tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> tokenParams = new LinkedMultiValueMap<>();
            tokenParams.add("grant_type", "authorization_code");
            tokenParams.add("client_id", clientId);
            tokenParams.add("redirect_uri", redirectUri);
            tokenParams.add("code", code);

            HttpEntity<MultiValueMap<String, String>> tokenRequestEntity = new HttpEntity<>(tokenParams, tokenHeaders);
            ResponseEntity<String> tokenResponseEntity = restTemplate.exchange(tokenRequestUrl, HttpMethod.POST, tokenRequestEntity, String.class);

            JsonNode tokenJson = objectMapper.readTree(tokenResponseEntity.getBody());
            String accessToken = tokenJson.get("access_token").asText();

            // 2. 사용자 정보 요청
            String userInfoRequestUrl = "https://kapi.kakao.com/v2/user/me";
            HttpHeaders userInfoHeaders = new HttpHeaders();
            userInfoHeaders.setBearerAuth(accessToken);
            userInfoHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED); // 본문이 없으므로 필수는 아님

            HttpEntity<Void> userInfoRequestEntity = new HttpEntity<>(userInfoHeaders); // 본문 없이 헤더만 전달
            ResponseEntity<String> userInfoResponseEntity = restTemplate.exchange(userInfoRequestUrl, HttpMethod.POST, userInfoRequestEntity, String.class);


            JsonNode userInfoJson = objectMapper.readTree(userInfoResponseEntity.getBody());
            String nickname = userInfoJson.path("properties").path("nickname").asText();
            // String kakaoId = userInfoJson.path("id").asText(); // 필요시 카카오 ID도 사용

            session.setAttribute("kakaoNickname", nickname);
            // session.setAttribute("kakaoId", kakaoId);


        } catch (Exception e) {
            e.printStackTrace();
            // 에러 처리 (예: 에러 페이지로 리다이렉트 또는 메시지 전달)
            return "redirect:/login?error=kakao_error";
        }
        return "redirect:/join";
    }
}