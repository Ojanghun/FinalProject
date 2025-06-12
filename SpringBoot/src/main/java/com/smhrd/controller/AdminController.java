package com.smhrd.controller;

import com.smhrd.entity.*;
import com.smhrd.projection.PayWithLicenseDTO;
import com.smhrd.projection.PayWithLicenseDTOImpl;
import com.smhrd.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired private AdminRepository adminRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private ExamRepository examRepository;
    @Autowired private PbsLogRepository pbsLogRepository;
    @Autowired private RefundInfoRepository refundInfoRepository;

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
        // 사용자 목록
        List<Member> users = memberRepository.findAll();
        model.addAttribute("users", users);

        // 자격증명, 주제명 조회용 Map
        Map<Integer, String> licenseMap = new HashMap<>();
        licenseMap.put(1, "정보처리기사");
        licenseMap.put(2, "산업안전기사");

        Map<Integer, String> topicMap = new HashMap<>();
        topicMap.put(1, "소프트웨어 개발");
        topicMap.put(2, "DB");
        topicMap.put(3, "네트워크");
        topicMap.put(4, "보안");

        // 문제 수 집계
        List<Object[]> groupedCounts = examRepository.countByLicenseAndTopicWithNames();
        List<Map<String, Object>> topicQuestionCounts = groupedCounts.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("liName", row[0]);   // li_name
            map.put("topicName", row[1]); // topic_name
            map.put("topicIdx", row[2]);  // topic_idx
            map.put("count", row[3]);     // 문제 수
            return map;
        }).collect(Collectors.toList());
        model.addAttribute("topicQuestionCounts", topicQuestionCounts);

        // ✅ 자격증 이름 리스트를 필터용 버튼에 사용
        Set<String> licenseNames = groupedCounts.stream()
            .map(row -> (String) row[0]) // liName
            .collect(Collectors.toCollection(LinkedHashSet::new)); // 중복 제거 및 순서 유지
        model.addAttribute("licenseNames", licenseNames);

        // 오답률
        List<Object[]> wrongRateStats = pbsLogRepository.getWrongRatesGroupedByTopic();
        List<String> topicLabels = new ArrayList<>();
        List<Integer> wrongRates = new ArrayList<>();
        for (Object[] row : wrongRateStats) {
            int topic = (int) row[0];
            long total = ((Number) row[1]).longValue();
            long wrong = ((Number) row[2]).longValue();
            int rate = (int) Math.round((double) wrong / total * 100);
            topicLabels.add(topicMap.getOrDefault(topic, "주제 " + topic));
            wrongRates.add(rate);
        }
        model.addAttribute("topicLabels", topicLabels);
        model.addAttribute("wrongRates", wrongRates);

        // 환급률
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
    
    @Autowired private PayInfoRepository payInfoRepository;

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
            map.put("planPrice", p.getPlanPrice()); // ✅ 반드시 추가!!
            return map;
        }).toList();
    }
    @GetMapping("/admin/wrong-rate-page")
    @ResponseBody
    public Map<String, Object> getWrongRatePage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(required = false) String license
    ) {
        int pageSize = 10;

        List<Object[]> wrongRateStats;

        if (license != null && !license.isEmpty()) {
            // ✅ 라이선스명으로 필터링된 데이터 가져오기 (이 메서드 만들어야 함)
            wrongRateStats = pbsLogRepository.getWrongRatesByLicenseName(license);
        } else {
            // ✅ 기존 방식 유지
            wrongRateStats = pbsLogRepository.getWrongRatesGroupedByTopic();
        }

        List<Map<String, Object>> data = wrongRateStats.stream().map(row -> {
            int topic = (int) row[0];
            long total = ((Number) row[1]).longValue();
            long wrong = ((Number) row[2]).longValue();
            int rate = total > 0 ? (int) Math.round((double) wrong / total * 100) : 0;

            Map<String, Object> map = new HashMap<>();
            map.put("topic", topic);
            map.put("rate", rate);
            return map;
        }).sorted((a, b) -> Integer.compare((int) b.get("rate"), (int) a.get("rate")))
          .toList();

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
        @RequestParam(required = false) String license
    ) {
    	System.out.println("🔍 환급률 API 호출됨");
        int pageSize = 10;
        List<Object[]> stats;

        if (license != null && !license.isEmpty() && !"전체".equals(license)) {
            stats = payInfoRepository.getRefundRatesGroupedByLicenseName(license);
        } else {
            stats = payInfoRepository.getRefundRatesGroupedByLicense();
        }

        System.out.println("환급률 raw 데이터: " + stats);
        
        List<Map<String, Object>> data = stats.stream().map(row -> {
            String liName = (String) row[0];                  // li_name
            long total = ((Number) row[1]).longValue();       // total
            long refunded = ((Number) row[2]).longValue();    // refunded

            int rate = total > 0 ? (int) Math.round((double) refunded / total * 100) : 0;

            Map<String, Object> map = new HashMap<>();
            map.put("topic", liName);
            map.put("rate", rate);
            return map;
        }).sorted((a, b) -> Integer.compare((int) b.get("rate"), (int) a.get("rate")))
          .toList();

        int totalItems = data.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        int start = page * pageSize;
        int end = Math.min(start + pageSize, totalItems);

        Map<String, Object> response = new HashMap<>();
        response.put("data", data.subList(start, end));
        response.put("totalPages", totalPages);
        
        
        return response;
    }

}
