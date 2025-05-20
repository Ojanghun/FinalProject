package com.smhrd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smhrd.entity.Member;
import com.smhrd.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository repository;

	public Member idCheck(String id) {

		Optional<Member> m = repository.findById(id);

		if (m.isPresent()) {
			Member info = m.get();
			info.setPw(null);
			info.setAddr(null);
			info.setPhone(null);
			info.setNick(null);
			return info;
		} else {
			return new Member();
		}

	}

	public void join(Member vo) {
		repository.save(vo);
	}

	public Member login(Member vo) {
		String id = vo.getId();
		String pw = vo.getPw();

		Member info = repository.findByIdAndPw(id, pw);

		if (info != null) {
			info.setPw(null);
		} 

		return info;
	}

	public void update(Member vo) {
		repository.save(vo);
	}

	public List<Member> goList() {
		return repository.findAll();
	}

	public void goDelete(String id) {
		repository.deleteById(id);
	}

}
