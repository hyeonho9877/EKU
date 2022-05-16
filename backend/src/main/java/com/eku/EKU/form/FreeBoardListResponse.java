package com.eku.EKU.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@ToString
public class FreeBoardListResponse extends BoardListResponse{
    private int comments;
}
