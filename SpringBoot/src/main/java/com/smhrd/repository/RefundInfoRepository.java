package com.smhrd.repository;

import com.smhrd.entity.Refund_Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundInfoRepository extends JpaRepository<Refund_Info, Integer> {

    // 자격증별 환급률 계산 (예시)
    @Query("""
        SELECT e.liIdx, COUNT(DISTINCT r.rfIdx) * 100.0 / COUNT(DISTINCT p.payIdx)
        FROM Refund_Info r
        JOIN Pay_Info p ON r.payIdx = p.payIdx
        JOIN Exam e ON p.planIdx = e.liIdx
        GROUP BY e.liIdx
    """)
    List<Object[]> findRefundRateByLicense();
    
    @Query(value = """
    	    SELECT r.RF_IDX, r.USER_ID, r.RF_VPATH, r.RF_NAME, r.RF_BANK, r.RF_ACCNUM, r.RF_AT,
    	           l.LI_NAME, p.PLAN_TYPE, p.PLAN_PRICE
    	    FROM refund_info r  -- ← ✅ 대소문자 정확히 맞춰야 함!
    	    JOIN pay_info pi ON r.PAY_IDX = pi.PAY_IDX
    	    JOIN plan_info p ON pi.PLAN_IDX = p.PLAN_IDX
    	    JOIN li_info l ON p.LI_IDX = l.LI_IDX
    	""", nativeQuery = true)
    	List<Object[]> findAllRefundDetails();

}
