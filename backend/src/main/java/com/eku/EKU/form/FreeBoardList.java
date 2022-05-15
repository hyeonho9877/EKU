package com.eku.EKU.form;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class FreeBoardList extends BoardList{
    private Integer comments;
}
