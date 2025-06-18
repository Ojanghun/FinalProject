package com.smhrd.controller;

import com.smhrd.entity.*;
import com.smhrd.projection.PayWithLicenseDTO;
import com.smhrd.projection.PayWithLicenseDTOImpl;
import com.smhrd.projection.PlanUsageDTO;
import com.smhrd.projection.RefundRequestDTO;
import com.smhrd.repository.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired private AdminRepository adminRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private ExamRepository examRepository;
    @Autowired private PbsLogRepository pbsLogRepository;
    @Autowired private RefundInfoRepository refundInfoRepository;
    @Autowired private PayInfoRepository payInfoRepository;

    @GetMapping("/adminLogin.do")
    public String showAdminLoginPage() {
        return "admin_login";
    }

    @PostMapping("/adminLoginCheck")
    public String checkAdminLogin(@RequestParam String id, @RequestParam String pw, HttpSession session, Model model) {
        Admin admin = adminRepository.findByAdminIdAndAdminPw(id, pw);
        if (admin != null) {
            session.setAttribute("admin", admin);
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("loginError", "관리자 인증 실패");
            return "admin_login";
        }
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
    	System.out.println("✅ Admin Dashboard 진입");
        List<Member> users = memberRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("userCount", users.size());
        
        Map<Integer, String> licenseMap = new HashMap<>();
        licenseMap.put(1, "정보처리기사");
        licenseMap.put(2, "산업안전기사");

        Map<Integer, String> topicMap = new HashMap<>();
        topicMap.put(1, "소프트웨어 개발");
        topicMap.put(2, "DB");
        topicMap.put(3, "네트워크");
        topicMap.put(4, "보안");

        List<Object[]> groupedCounts = examRepository.countByLicenseAndTopicWithNames();

        List<Object[]> wrongRateStats = pbsLogRepository.getWrongRatesGroupedByLicenseAndTopic();
        Map<String, Integer> wrongRateMap = new HashMap<>();
        for (Object[] row : wrongRateStats) {
            String liName = (String) row[0];
            int topicIdx = (int) row[1];
            long total = ((Number) row[2]).longValue();
            long wrong = ((Number) row[3]).longValue();
            int rate = total > 0 ? (int) Math.round((double) wrong / total * 100) : 0;
            wrongRateMap.put(liName + "_" + topicIdx, rate);
        }

        List<Map<String, Object>> topicQuestionCounts = groupedCounts.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            String liName = (String) row[0];
            int topicIdx = (int) row[2];
            map.put("liName", liName);
            map.put("topicName", row[1]);
            map.put("topicIdx", topicIdx);
            map.put("count", row[3]);
            map.put("wrongRate", wrongRateMap.getOrDefault(liName + "_" + topicIdx, 0));
            return map;
        }).collect(Collectors.toList());

        model.addAttribute("topicQuestionCounts", topicQuestionCounts);

        Set<String> licenseNames = groupedCounts.stream()
            .map(row -> (String) row[0])
            .collect(Collectors.toCollection(LinkedHashSet::new));
        model.addAttribute("licenseNames", licenseNames);

        List<Object[]> wrongRateOnlyStats = pbsLogRepository.getWrongRatesGroupedByTopic();
        List<String> topicLabels = new ArrayList<>();
        List<Integer> wrongRates = new ArrayList<>();
        for (Object[] row : wrongRateOnlyStats) {
            int topic = (int) row[0];
            long total = ((Number) row[1]).longValue();
            long wrong = ((Number) row[2]).longValue();
            int rate = (int) Math.round((double) wrong / total * 100);
            topicLabels.add(topicMap.getOrDefault(topic, "주제 " + topic));
            wrongRates.add(rate);
        }
        model.addAttribute("topicLabels", topicLabels);
        model.addAttribute("wrongRates", wrongRates);

        List<Refund_Info> allRefunds = refundInfoRepository.findAll();
        Map<String, List<Refund_Info>> groupedByUser = allRefunds.stream()
            .collect(Collectors.groupingBy(Refund_Info::getId));
        List<String> refundLabels = new ArrayList<>();
        List<Integer> refundRates = new ArrayList<>();
        for (Map.Entry<String, List<Refund_Info>> entry : groupedByUser.entrySet()) {
            long total = entry.getValue().size();
            long refunded = entry.getValue().stream().filter(r -> r.getRfVpath() != null).count();
            refundLabels.add(entry.getKey());
            refundRates.add((int) Math.round((double) refunded / total * 100));
        }
        model.addAttribute("refundLabels", refundLabels);
        model.addAttribute("refundRates", refundRates);

        return "admin_dashboard";
    }

    @GetMapping("/admin/pay-info")
    @ResponseBody
    public List<Map<String, Object>> getPayInfo(@RequestParam("userId") String userId) {
        List<PayWithLicenseDTOImpl> list = payInfoRepository.findDetailedPaymentsByUserId(userId);
        return list.stream().map(p -> {
            Map<String, Object> map = new HashMap<>();
            map.put("liName", p.getLiName());
            map.put("planStd", p.getPlanStd().toString());
            map.put("planEd", p.getPlanEd().toString());
            map.put("planType", p.getPlanType());
            map.put("planAct", p.getPlanAct() == 1);
            map.put("planPrice", p.getPlanPrice());
            return map;
        }).toList();
    }

    @GetMapping("/admin/wrong-rate-page")
    @ResponseBody
    public Map<String, Object> getWrongRatePage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(required = false) String license) {
        int pageSize = 10;
        List<Object[]> wrongRateStats = (license != null && !license.isEmpty())
            ? pbsLogRepository.getWrongRatesByLicenseName(license)
            : pbsLogRepository.getWrongRatesGroupedByTopic();

        List<Map<String, Object>> data = wrongRateStats.stream().map(row -> {
            int topic = (int) row[0];
            long total = ((Number) row[1]).longValue();
            long wrong = ((Number) row[2]).longValue();
            int rate = total > 0 ? (int) Math.round((double) wrong / total * 100) : 0;
            Map<String, Object> map = new HashMap<>();
            map.put("topic", topic);
            map.put("rate", rate);
            return map;
        }).sorted((a, b) -> Integer.compare((int) b.get("rate"), (int) a.get("rate"))).toList();

        int totalItems = data.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        int start = page * pageSize;
        int end = Math.min(start + pageSize, totalItems);

        Map<String, Object> response = new HashMap<>();
        response.put("data", data.subList(start, end));
        response.put("totalPages", totalPages);
        return response;
    }

    @GetMapping("/admin/users")
    @ResponseBody
    public List<Map<String, Object>> getAllUsers() {
        return memberRepository.findAll().stream().map(user -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("name", user.getName());
            map.put("phone", user.getPhone());
            map.put("userpoint", user.getUserpoint());
            map.put("joinedat", user.getJoinedat());
            return map;
        }).toList();
    }

    @GetMapping("/admin/refund-rate-page")
    @ResponseBody
    public Map<String, Object> getRefundRatePage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(required = false) String license) {
        System.out.println("🔍 환급률 API 호출됨");
        int pageSize = 10;
        List<Object[]> stats = (license != null && !license.isEmpty() && !"전체".equals(license))
            ? payInfoRepository.getRefundRatesGroupedByLicenseName(license)
            : payInfoRepository.getRefundRatesGroupedByLicense();

        System.out.println("환급률 raw 데이터: " + stats);

        List<Map<String, Object>> data = stats.stream().map(row -> {
            String liName = (String) row[0];
            long total = ((Number) row[1]).longValue();
            long refunded = ((Number) row[2]).longValue();
            int rate = total > 0 ? (int) Math.round((double) refunded / total * 100) : 0;
            Map<String, Object> map = new HashMap<>();
            map.put("topic", liName);
            map.put("rate", rate);
            return map;
        }).sorted((a, b) -> Integer.compare((int) b.get("rate"), (int) a.get("rate"))).toList();

        int totalItems = data.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        int start = page * pageSize;
        int end = Math.min(start + pageSize, totalItems);

        Map<String, Object> response = new HashMap<>();
        response.put("data", data.subList(start, end));
        response.put("totalPages", totalPages);
        return response;
    }
    


    @GetMapping("/admin/plan-usage")
    @ResponseBody
    public List<PlanUsageDTO> getPlanUsage(@RequestParam String licenseName) {
        List<Object[]> rawData = payInfoRepository.getPlanUsageByLicenseName(licenseName);

        long totalUserCount = rawData.stream()
                .mapToLong(row -> ((Number) row[1]).longValue())
                .sum();

        return rawData.stream().map(row -> {
            boolean planType = Boolean.TRUE.equals(row[0]); // ⬅️ 여기가 중요!
            long userCount = ((Number) row[1]).longValue();
            long refundCount = ((Number) row[2]).longValue();

            double ratio = totalUserCount > 0 ? Math.round(userCount * 1000.0 / totalUserCount) / 10.0 : 0.0;
            double refundRate = userCount > 0 ? Math.round(refundCount * 1000.0 / userCount) / 10.0 : 0.0;

            return new PlanUsageDTO(
                    planType ? "필수형" : "탐구형",
                    userCount,
                    ratio,
                    refundRate
            );
        }).collect(Collectors.toList());
    }



    
    @GetMapping("/admin/refund-session")
    public String getAllRefundRequests(HttpSession session) {
        List<Object[]> rawData = refundInfoRepository.findAllRefundDetails();

        List<RefundRequestDTO> requests = new ArrayList<>();
        List<RefundRequestDTO> completed = new ArrayList<>();

        for (Object[] row : rawData) {
            int rfIdx = (int) row[0];
            String userId = (String) row[1];
            String rfVpath = (String) row[2];
            String rfName = (String) row[3];
            String rfBank = (String) row[4];
            String rfAccnum = (String) row[5];
            LocalDateTime rfAt = ((Timestamp) row[6]).toLocalDateTime();
            String liName = (String) row[7];

            String planType;
            if (row[8] instanceof Boolean) {
                planType = ((Boolean) row[8]) ? "필수형" : "탐구형";
            } else {
                planType = ((Number) row[8]).intValue() == 1 ? "필수형" : "탐구형";
            }

            int planPrice = ((Number) row[9]).intValue();
            int rfCp = ((Number) row[10]).intValue();
            LocalDateTime apAt = row[11] != null ? ((Timestamp) row[11]).toLocalDateTime() : null;

            RefundRequestDTO dto = new RefundRequestDTO(
                rfIdx, userId, rfVpath, rfName, rfBank, rfAccnum,
                rfAt, liName, planType, planPrice, rfCp, apAt
            );

            if (rfCp == 1) {
                completed.add(dto);
            } else {
                requests.add(dto);
            }
        }

        session.setAttribute("refundInfoList", requests);
        session.setAttribute("refundCompletedList", completed);

        return "redirect:/admin/dashboard?section=refund";
    }
    
    @PostMapping("/admin/approve-refund")
    @Transactional
    public ResponseEntity<String> approveRefund(@RequestParam int rfIdx) {
        // 1. Refund_Info에서 payIdx 조회
        Refund_Info refund = refundInfoRepository.findById(rfIdx).orElse(null);
        if (refund == null) return ResponseEntity.notFound().build();

        int payIdx = refund.getPayIdx();

        // 2. pay_info 테이블의 rf_cp 값을 1로 변경
        int updated = payInfoRepository.updateRfCpByPayIdx(payIdx);

        // 3. refund_info 테이블의 ap_at 값을 현재 시간으로 업데이트
        refundInfoRepository.updateApprovalTimestamp(rfIdx);

        if (updated == 1) {
            return ResponseEntity.ok("승인 완료");
        } else {
            return ResponseEntity.status(500).body("변경 실패");
        }
    }

    
    
}

