package com.eku.EKU.form;

import com.eku.EKU.domain.FreeBoard;
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

    public FreeBoardListResponse(FreeBoard board, int comments, String writer) {
        super(board);
        this.comments = comments;
        this.setWriter(writer);
    }
}
