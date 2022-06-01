package com.kyonggi.eku.model;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class InfoBoardPreview extends BoardPreview{
    private Bitmap representativeImage;
    public InfoBoardPreview(BoardPreview preview) {
        super(preview.getId(), preview.getWriter(), preview.getTitle(), preview.getNo(), preview.getTime(), preview.getView());
    }

    @NonNull
    @Override
    public String toString() {
        return "InfoBoardPreview(id="+getId()+", writer="+getWriter()+", title="+getTitle()+", no="+getNo()+", time="+getTime()+", view="+getView();
    }

    public Bitmap getRepresentativeImage() {
        return representativeImage;
    }

    public void setRepresentativeImage(Bitmap representativeImage) {
        this.representativeImage = representativeImage;
    }
}
