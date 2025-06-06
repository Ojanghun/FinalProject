// com.smhrd.projection.PayWithLicenseDTO.java
package com.smhrd.projection;

import java.time.LocalDateTime;

public interface PayWithLicenseDTO {
    String getLiName();
    Boolean getPlanType();
    Integer getPlanIdx();
    LocalDateTime getPlanStd();
    LocalDateTime getPlanEd();
    Integer getPlanAct(); // ✅ 결제 상태 추가
}
