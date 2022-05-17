package com.kyonggi.eku.model;

import androidx.annotation.NonNull;

public class InfoBoardPreview extends BoardPreview{
    public InfoBoardPreview(BoardPreview preview) {
        super(preview.getId(), preview.getWriter(), preview.getTitle(), preview.getNo(), preview.getTime(), preview.getView());
    }

    @NonNull
    @Override
    public String toString() {
        return "InfoBoardPreview(id="+getId()+", writer="+getWriter()+", title="+getTitle()+", no="+getNo()+", time="+getTime()+", view="+getView();
    }
}
