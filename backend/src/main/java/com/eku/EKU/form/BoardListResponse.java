package com.eku.EKU.form;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class BoardListResponse {
    private Long id;
    private String writer;
    private String title;
    private Long no;
    private String time;
    private Integer view;
}
