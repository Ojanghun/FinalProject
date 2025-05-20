package com.smhrd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBoot {

	// Bean >> 객체
	// new 생성자 --> 객체
	
	// 1. ComponentScan
	// >> 특정 패키지를 Scan해서 특별한 클래스(Controller, Mapper...) Bean으로 등록(객체 생성한다.)
	// >> 특정 패키지 : Application.java 파일이 위치한 패키지를 자동으로 스캔(com.smhrd)
	// >> 처음 자동으로 만들어지는 패키지를 Group Id 까지만 지정한 이유
	
	// 2. Auto Configuration
	// >> 서버가 동작하기 위해 필요한 다양한 설정들을 자동으로 진행
	// >> ex) ViewResolver
	// >> 우리가 설정을 바꾸고 싶으면, application.properties에 정보를 작성하면
	//    Spring이 그걸 읽고 설정
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBoot.class, args);
	}

}
