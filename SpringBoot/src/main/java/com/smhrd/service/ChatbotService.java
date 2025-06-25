package com.smhrd.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

@Service
public class ChatbotService {

    private static final String API_KEY = "AIzaSyAP3OnjriyLnDqNID3_7kcI45g4zsIuq84"; // 🔑 여기만 바꾸세요

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent")
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .build();

    public String askGemini(String prompt) {
        // ✅ Gemini에 보낼 JSON 포맷
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        try {
            Map<?, ?> response = webClient.post()
                    .uri(uriBuilder -> uriBuilder.queryParam("key", API_KEY).build())
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            // ✅ 응답에서 텍스트 추출
            List<?> candidates = (List<?>) response.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                Map<?, ?> content = (Map<?, ?>) ((Map<?, ?>) candidates.get(0)).get("content");
                List<?> parts = (List<?>) content.get("parts");
                
                // ✅ 여기서 text만 추출
                Map<?, ?> firstPart = (Map<?, ?>) parts.get(0);
                return (String) firstPart.get("text");
            } else {
                return "답변이 없습니다.";
            }

        } catch (Exception e) {
            return "Gemini 응답 오류: " + e.getMessage();
        }
    }
}
