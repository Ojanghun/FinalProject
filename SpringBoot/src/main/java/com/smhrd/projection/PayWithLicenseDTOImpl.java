// com.smhrd.projection.PayWithLicenseDTOImpl.java
package com.smhrd.projection;

import java.time.LocalDateTime;

public class PayWithLicenseDTOImpl implements PayWithLicenseDTO {

    private Integer planIdx;
    private String liName;
    private LocalDateTime planStd;
    private LocalDateTime planEd;
    private Boolean planType;
    private Integer planAct; // ✅ 추가

    // ✅ 모든 필드를 포함한 생성자 추가
    public PayWithLicenseDTOImpl(Integer planIdx, String liName, LocalDateTime planStd,
                                 LocalDateTime planEd, Boolean planType, Integer planAct) {
        this.planIdx = planIdx;
        this.liName = liName;
        this.planStd = planStd;
        this.planEd = planEd;
        this.planType = planType;
        this.planAct = planAct;
    }

    public Integer getPlanIdx() { return planIdx; }
    public String getLiName() { return liName; }
    public LocalDateTime getPlanStd() { return planStd; }
    public LocalDateTime getPlanEd() { return planEd; }
    public Boolean getPlanType() { return planType; }
    public Integer getPlanAct() { return planAct; } // ✅ 추가
}
