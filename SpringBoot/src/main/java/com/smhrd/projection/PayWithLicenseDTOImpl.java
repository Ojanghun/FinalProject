// com.smhrd.projection.PayWithLicenseDTOImpl.java
package com.smhrd.projection;

import java.time.LocalDateTime;

public class PayWithLicenseDTOImpl implements PayWithLicenseDTO {

	private Integer payIdx;
    private Integer planIdx;
    private String liName;
    private LocalDateTime planStd;
    private LocalDateTime planEd;
    private Boolean planType;
    private Integer planAct; // ✅ 추가
    private Integer planPrice;  // ✅ 추가

    // ✅ 모든 필드를 포함한 생성자 추가
    public PayWithLicenseDTOImpl(Integer payIdx, Integer planIdx, String liName, LocalDateTime planStd,
            LocalDateTime planEd, Boolean planType, Integer planAct, Integer planPrice) {
        this.payIdx = payIdx;
        this.planIdx = planIdx;
        this.liName = liName;
        this.planStd = planStd;
        this.planEd = planEd;
        this.planType = planType;
        this.planAct = planAct;
        this.planPrice = planPrice;
        
    }

    public Integer getPayIdx() { return payIdx; }
    public Integer getPlanIdx() { return planIdx; }
    public String getLiName() { return liName; }
    public LocalDateTime getPlanStd() { return planStd; }
    public LocalDateTime getPlanEd() { return planEd; }
    public Boolean getPlanType() { return planType; }
    public Integer getPlanAct() { return planAct; } // ✅ 추가
    public Integer getPlanPrice() { return planPrice; }  // ✅ 추가
}
