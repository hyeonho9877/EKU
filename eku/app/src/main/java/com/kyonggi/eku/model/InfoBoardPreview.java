package com.kyonggi.eku.model;

public class InfoBoardPreview extends BoardPreview{
    public InfoBoardPreview(BoardPreview preview) {
        super(preview.getId(), preview.getWriter(), preview.getTitle(), preview.getNo(), preview.getTime(), preview.getView());
    }
}
