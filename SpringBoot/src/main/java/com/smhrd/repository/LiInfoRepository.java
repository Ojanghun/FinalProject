package com.smhrd.repository;

import com.smhrd.entity.Li_Info;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiInfoRepository extends JpaRepository<Li_Info, Integer> {

	List<Li_Info> findByLiIdx(int liIdx);
    // 필요한 경우 커스텀 쿼리 추가 가능
}
