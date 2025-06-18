package com.smhrd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import com.smhrd.entity.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    // 년도 별 문제 불러오기
    List<Exam> findTop100ByExIdOrderByPbNum(int exId);

    // 카테고리 값에 맞는 유형 불러오기
    List<Exam> findByPbTopicOrderByPbTopicAsc(int category);
    
    // 페이지 번호(폐기)
	List<Exam> findAllByOrderByPbNum(Pageable pageable);
	
	// 랜덤 100문제
	@Query(value = """
		    SELECT *
		    FROM (
		        SELECT *, ROW_NUMBER() OVER (PARTITION BY pb_num ORDER BY RAND()) AS rn
		        FROM pb_info
		        WHERE ( pb_num BETWEEN 1 AND 100 ) AND li_idx = :liIdx
		    ) AS sub
		    WHERE sub.rn = 1
		    ORDER BY pb_num
		    """, nativeQuery = true)
	List<Exam> findRandom100(@Param("liIdx") int liIdx);


    // 유형 전체 불러오기
    List<Exam> findAllByOrderByPbTopicAsc();


    // 주어진 문제 ID 목록 기준 주제별 개수
    @Query(value = """
        SELECT topic_idx, COUNT(topic_idx) AS topic_count
        FROM pb_info
        WHERE pb_idx IN (:pbIdx)
        GROUP BY topic_idx
        ORDER BY topic_count DESC
    """, nativeQuery = true)
    List<Object[]> findTopicCountByPbIdx(@Param("pbIdx") List<Integer> pbIdx);

    // 자격증 + 주제 기준 문제 수 (Java 필드명 기준으로 작성)
    @Query(value = """
    	    SELECT li.li_name, t.topic_name, t.topic_idx, COUNT(*) AS count
    	    FROM pb_info p
    	    JOIN li_info li ON p.li_idx = li.li_idx
    	    JOIN topic_info t ON p.topic_idx = t.topic_idx
    	    GROUP BY li.li_name, t.topic_name, t.topic_idx
    	    ORDER BY count DESC
    	""", nativeQuery = true)
    	List<Object[]> countByLicenseAndTopicWithNames();
}

