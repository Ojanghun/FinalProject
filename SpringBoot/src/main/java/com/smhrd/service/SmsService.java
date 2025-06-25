package com.smhrd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.smhrd.entity.Member;
import com.smhrd.projection.UserContactDTO;
import com.smhrd.repository.MemberRepository;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
public class SmsService {
	
	@Autowired
	MemberRepository memberRepository;

	private final DefaultMessageService messageService;
	
    public SmsService() {
        this.messageService = NurigoApp.INSTANCE.initialize(
                "NCSBGZ7GBKQWOY0I",
                "HADTQ3R9WP9JCR7ZVTMBRM1Y27YVKVFC",
                "https://api.solapi.com");
    }
	
	// 필수형 플랜 사용자에게 9시마다 할당량 알림 제공
	@Scheduled(cron = "0 0 9 * * *")
	public void sendSMS() {
		List<UserContactDTO> userList = getEssentialPlanUserContacts();
		
		// Message 패키지가 중복될 경우 net.nurigo.sdk.message.model.Message로 치환하여 주세요
		Message message = new Message();
		message.setFrom("01040135280");
		message.setTo(userList.get(7).getUserPhone());
		message.setText(userList.get(7).getUserName() + "님, 오늘 할당량이 남으셨어요~ 20/80 공부합시다\n지금 바로 클릭 https://...");
		
		try {
			// send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
			messageService.send(message);
		} catch (NurigoMessageNotReceivedException exception) {
			// 발송에 실패한 메시지 목록을 확인할 수 있습니다!
			System.out.println(exception.getFailedMessageList());
			System.out.println(exception.getMessage());
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}
	
	public void sendJoinSuccessMessage(String toPhone) {
		Message message = new Message();
		message.setFrom("01040135280");
		message.setTo(toPhone);
		message.setText("[문제? Issue!]\n회원가입이 완료되었습니다.\n서비스를 이용해주셔서 감사합니다!");
		
		try {
			// send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
			messageService.send(message);
		} catch (NurigoMessageNotReceivedException exception) {
			// 발송에 실패한 메시지 목록을 확인할 수 있습니다!
			System.out.println(exception.getFailedMessageList());
			System.out.println(exception.getMessage());
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}

	public void sendPaySuccessMessage(String userId, String liName, String plan) {
		Optional<Member> userInfo = memberRepository.findById(userId);
		String userPhone = userInfo.get().getPhone();
		
		Message message = new Message();
		message.setFrom("01040135280");
		message.setTo(userPhone);
		message.setText("[문제? Issue!]\n" + liName + " " + plan + " 플랜에 가입되셨습니다. 감사합니다!");
		
		try {
			// send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
			messageService.send(message);
		} catch (NurigoMessageNotReceivedException exception) {
			// 발송에 실패한 메시지 목록을 확인할 수 있습니다!
			System.out.println(exception.getFailedMessageList());
			System.out.println(exception.getMessage());
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}
	
	// 필수형 플랜인 사용자의 이름과 휴대폰 번호 가져오기
	public List<UserContactDTO> getEssentialPlanUserContacts() {
	    List<Object[]> results = memberRepository.findUserNameAndPhonesWithTruePlanType();
	    return results.stream()
	        .map(row -> new UserContactDTO((String) row[0], (String) row[1]))
	        .toList();
	}

	// DTO로 꺼내쓰기
//	public void temp() {
//		List<UserContactDTO> list = getEssentialPlanUserContacts();
//		for (UserContactDTO dto : list) {
//			System.out.println("이름: " + dto.getUserName() + ", 전화: " + dto.getUserPhone());
//		}
//	}
	
}
