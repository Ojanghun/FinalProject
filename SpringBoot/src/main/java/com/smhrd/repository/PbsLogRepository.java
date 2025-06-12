package com.smhrd.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.Pbs_Log;

@Repository
public interface PbsLogRepository extends JpaRepository<Pbs_Log, Integer> {
	
	public List<Pbs_Log> findByUserIdAndPbsCheck(String id, int pbsCheck);
	
	
	@Query(value = """
		    SELECT li.li_name, pb.topic_idx,
		           COUNT(*) AS total_cnt,
		           SUM(CASE WHEN pbs.pbs_check = 0 THEN 1 ELSE 0 END) AS wrong_cnt
		    FROM pbs_log pbs
		    JOIN pb_info pb ON pbs.pb_idx = pb.pb_idx
		    JOIN li_info li ON pb.li_idx = li.li_idx
		    GROUP BY li.li_name, pb.topic_idx
		    """, nativeQuery = true)
		List<Object[]> getWrongRatesGroupedByLicenseAndTopic();
		
		
	@Query(value = """
		    SELECT e.topic_idx AS topic,
		           COUNT(*) AS total,
		           SUM(CASE WHEN p.pbs_check = 0 THEN 1 ELSE 0 END) AS wrong
		    FROM pbs_log p
		    JOIN pb_info e ON p.pb_idx = e.pb_idx
		    GROUP BY e.topic_idx
		    ORDER BY e.topic_idx
		""", nativeQuery = true)
		List<Object[]> getWrongRatesGroupedByTopic();
		
		@Query(value = """
			    SELECT pb.topic_idx, COUNT(*) AS total_cnt,
			           SUM(CASE WHEN pbs.pbs_check = 0 THEN 1 ELSE 0 END) AS wrong_cnt
			    FROM pbs_log pbs
			    JOIN pb_info pb ON pbs.pb_idx = pb.pb_idx
			    JOIN li_info li ON pb.li_idx = li.li_idx
			    WHERE li.li_name = :licenseName
			    GROUP BY pb.topic_idx
			""", nativeQuery = true)
			List<Object[]> getWrongRatesByLicenseName(@Param("licenseName") String licenseName);
			
	@Query("SELECT p FROM Pbs_Log p WHERE p.userId = :userId AND p.pbsAt = :pbsAt")
	List<Pbs_Log> findByUserIdAndPbsAt(@Param("userId") String userId, @Param("pbsAt") LocalDateTime pbsAt);

}
