package com.eku.EKU.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BoardList {
    private Long id;
    private String title;
}
