package com.eku.EKU.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardList {
    private Long id;
    private String title;
    private String writer;
    private Long no;
    private String time;
    private Integer view;
}
