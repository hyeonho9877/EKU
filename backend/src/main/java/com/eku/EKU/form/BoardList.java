package com.eku.EKU.form;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BoardList {
    private Long id;
    private String title;
    private String name;
    private Long no;
    private String department;
}
