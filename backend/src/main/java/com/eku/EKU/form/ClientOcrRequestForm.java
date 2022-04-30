package com.eku.EKU.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 네이버에 ocr 리퀘스트를 보낼 때 사용하는 Form 객체
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientOcrRequestForm {
    private String version;
    private String requestId;
    private long timestamp;
    private String lang;
    private List<OcrImagesData> images;

    @Override
    public String toString(){
        return String.format("{\"version\":\"%s\", \"requestId\":\"%s\", \"timestamp\":%d, \"images\":%s}", version, requestId, timestamp, images);
    }
}
