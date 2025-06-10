package com.smhrd.repository;

import com.smhrd.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByAdminIdAndAdminPw(String adminId, String atminPw);
}
