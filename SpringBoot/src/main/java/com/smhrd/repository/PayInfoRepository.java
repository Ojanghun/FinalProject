package com.smhrd.repository;

import com.smhrd.entity.Pay_Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface PayInfoRepository extends JpaRepository<Pay_Info, Integer> {

    // ✅ 유저 ID + 자격증 liIdx로 결제 정보 찾기 (조인 기반)
    @Query("SELECT p FROM Pay_Info p WHERE p.id = :userId AND p.planIdx IN (SELECT pi.planIdx FROM Plan_Info pi WHERE pi.liIdx = :liIdx )")
    List<Pay_Info> findByUserIdAndLiIdx(@Param("userId") String userId, @Param("liIdx") int liIdx);

}
