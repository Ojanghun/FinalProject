package com.smhrd.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) // 모든 예외 처리
    public ModelAndView handleException(Exception ex, Model model) {
        ex.printStackTrace(); // 로그 출력
        return new ModelAndView("redirect:/main"); // 메인으로 이동
    }
}