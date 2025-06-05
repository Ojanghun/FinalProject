// com.smhrd.projection.PayWithLicenseDTO.java
package com.smhrd.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PayWithLicenseDTO {
    String getLiName();
    Boolean getPlanType();
    Integer getPlanIdx();
    LocalDateTime getPlanStd(); // ✅ 이렇게
    LocalDateTime getPlanEd();  // ✅ 이렇게
}

