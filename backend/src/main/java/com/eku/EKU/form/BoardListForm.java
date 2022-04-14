package com.eku.EKU.form;

import jdk.jfr.BooleanFlag;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BoardListForm {
    private Long id;
    private String title;
}
