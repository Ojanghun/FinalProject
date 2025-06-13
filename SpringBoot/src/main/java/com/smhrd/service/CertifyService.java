package com.smhrd.service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.smhrd.entity.MemberId;
import com.smhrd.entity.Pay_Info;
import com.smhrd.entity.Refund_Info;
import com.smhrd.repository.RefundInfoRepository;

@Service
@Transactional
public class CertifyService {

	@Autowired
	private PayInfoService payInfoService;

	@Autowired
	private RefundInfoRepository refundInfoRepository;

	public String handleUpload(MultipartFile file, MemberId memberId, int payIdx, int planIdx, String holder,
			String bank, String account) {

		try {
			// 1. 환급 가능 플랜 체크
			Pay_Info refundablePlan = payInfoService.getRefundablePlan(memberId.getId(), planIdx);
			if (refundablePlan == null) {
				return "noRefundablePlan";
			}

			// 2. 파일 저장 (경로 반환)
			String savedFilePath = saveVideoFile(file, memberId.getId());
		    if (savedFilePath == null) {
		        return "typeMismatch"; // 사용자가 동영상 파일이 아닌 파일을 올렸을 때
		    }
			
			// 3. DB 저장
			saveRefundInfo(payIdx, memberId.getId(), savedFilePath, holder, bank, account);

			return "success";

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("업로드 처리 중 에러 발생", e);
		}
	}

	// 동영상 파일 저장
	private String saveVideoFile(MultipartFile file, String userId) throws Exception {
		Tika tika = new Tika();
		String contentType = tika.detect(file.getInputStream());
		if (!contentType.startsWith("video/")) {
			return null;
		}

		String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String uploadDir = "C:\\" + userId + "\\";
		File dir = new File(uploadDir);
		if (!dir.exists())
			dir.mkdirs();

		String filePath = uploadDir + dateStr + "_" + file.getOriginalFilename();
		file.transferTo(new File(filePath));
		return filePath;
	}

	// 환급 정보 DB 저장
	private void saveRefundInfo(int payIdx, String userId, String videoPath, String holder, String bank,
			String account) {

		Refund_Info refund = new Refund_Info();
		refund.setPayIdx(payIdx);
		refund.setId(userId);
		refund.setRfVpath(videoPath);
		refund.setRfName(holder);
		refund.setRfBank(bank);
		refund.setRfAccnum(account);

		refundInfoRepository.save(refund);
	}
}