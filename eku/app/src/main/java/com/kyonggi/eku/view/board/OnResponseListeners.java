package com.kyonggi.eku.view.board;

import com.kyonggi.eku.model.BoardPreview;
import com.kyonggi.eku.model.InfoBoardPreview;

import java.util.List;

public interface OnResponseListeners {
    void onSuccess(List<? extends BoardPreview> articles);
    void onFailed();
}
