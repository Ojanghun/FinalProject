// com.smhrd.dto.PlanUsageDTO.java
package com.smhrd.projection;

public class PlanUsageDTO {
    private String planType; // 탐구형 / 필수형
    private long userCount;
    private double ratio; // 비율
    private double refundRate; // 환급률

    public PlanUsageDTO(String planType, long userCount, double ratio, double refundRate) {
        this.planType = planType;
        this.userCount = userCount;
        this.ratio = ratio;
        this.refundRate = refundRate;
    }

    public String getPlanType() {
        return planType;
    }

    public long getUserCount() {
        return userCount;
    }

    public double getRatio() {
        return ratio;
    }

    public double getRefundRate() {
        return refundRate;
    }
}
