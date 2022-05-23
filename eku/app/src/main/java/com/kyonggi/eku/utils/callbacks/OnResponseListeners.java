package com.kyonggi.eku.utils.callbacks;

import com.kyonggi.eku.model.FreeBoardPreview;
import com.kyonggi.eku.model.InfoBoardPreview;

import java.util.List;

public interface OnResponseListeners {
    void onFreeBoardSuccess(List<FreeBoardPreview> articles, String purpose);
    void onInfoBoardSuccess(List<InfoBoardPreview> articles, String purpose);
    void onFailed();
}
