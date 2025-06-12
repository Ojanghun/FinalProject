package com.smhrd.repository;

import com.smhrd.entity.Refund_Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    
    @Query("""
    	    SELECT r.id, COUNT(r), SUM(CASE WHEN r.rfVpath IS NOT NULL THEN 1 ELSE 0 END)
    	    FROM Refund_Info r GROUP BY r.id
    	""")
    	List<Object[]> calculateRefundRates();

}
