package com.smhrd.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.projection.ChatRequestDTO;
import com.smhrd.service.ChatbotService;


@RestController
public class ChatbotController {
	
	@Autowired
	ChatbotService chatbotService;
	
	@PostMapping("/askChatbot")
	@ResponseBody
	public Map<String, String> askChatbot(@RequestBody ChatRequestDTO request) {
	    String prompt = String.format(
	        "[문제]\n%s\n\n선택지:\n① %s\n② %s\n③ %s\n④ %s\n\n사용자 질문: %s"
	        + "\n질문에 대한 답변을 HTML 태그(`<ul>`, `<li>`, `<strong>` 등)를 사용해 간단하게 작성해주세요.",
	        request.getPbQues(),
	        request.getChoices().get(0),
	        request.getChoices().get(1),
	        request.getChoices().get(2),
	        request.getChoices().get(3),
	        request.getQuestion()
	    );

	    String answer = chatbotService.askGemini(prompt);
	    return Map.of("answer", answer);
	}
}
