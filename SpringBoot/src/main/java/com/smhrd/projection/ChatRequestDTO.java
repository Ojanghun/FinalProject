package com.smhrd.projection;

import java.util.List;

import lombok.Data;

@Data
public class ChatRequestDTO {
    private String question;
    private String pbQues;
    private List<String> choices;
}