package com.eku.EKU.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자에게 ocr 결과를 송신할 때 사용되는 Form 객체
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientOcrResponseForm {
    private String name;
    private String department;
    private int studNo;
}
