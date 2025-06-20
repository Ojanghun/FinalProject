package com.smhrd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.Exam;
import com.smhrd.entity.Topic_Info;

import jakarta.transaction.Transactional;

@Repository
public interface TopicInfoRpository extends JpaRepository<Topic_Info, Integer> {
	
	List<Topic_Info> findByTopicIdxOrderByTopicNumAsc(int category);
	
	@Modifying
	@Transactional
	@Query("UPDATE Topic_Info t SET t.topicNum = t.topicNum + 100 WHERE t.topicNum < 100")
	void updateTopicNumPlus100IfLessThan100();

	@Query(value = """
		    SELECT ti.*, COALESCE(cnt.topic_count, 0) AS topic_count
		    FROM topic_info ti
		    LEFT JOIN (
		        SELECT topic_idx, COUNT(*) AS topic_count
		        FROM pb_info
		        WHERE li_idx = :liIdx
		        GROUP BY topic_idx
		    ) cnt ON ti.topic_idx = cnt.topic_idx
		    WHERE li_idx = :liIdx
		    ORDER BY topic_count DESC
		""", nativeQuery = true)
	List<Topic_Info> findTopicCounts(@Param("liIdx") int liIdx);

	
}
