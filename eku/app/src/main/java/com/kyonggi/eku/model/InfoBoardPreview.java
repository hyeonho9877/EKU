package com.kyonggi.eku.model;

import androidx.annotation.NonNull;

import java.util.List;

public class InfoBoardPreview extends BoardPreview{
    private List<String> imageList;
    public InfoBoardPreview(BoardPreview preview) {
        super(preview.getId(), preview.getWriter(), preview.getTitle(), preview.getNo(), preview.getTime(), preview.getView());
    }

    @NonNull
    @Override
    public String toString() {
        return "InfoBoardPreview(id="+getId()+", writer="+getWriter()+", title="+getTitle()+", no="+getNo()+", time="+getTime()+", view="+getView();
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
