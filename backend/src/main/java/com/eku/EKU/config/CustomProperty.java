package com.eku.EKU.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * resources/application.yml에 존재하는 custom.properties 속성들의 값을 읽어오기 위한 클래스
 */
@Component
@ConfigurationProperties(prefix = "custom.properties")
@Getter
@Setter
public class CustomProperty {
    // 네이버 OCR 서비스 요청을 위한 URI
    private String clovaOcrUri;
    // 네이버 OCR 서비스 요청을 위한 KEY
    private String ocrKey;
    // 네이버 OCR 서비스 요청을 위한 HTTP REQUEST HEADER
    private String ocrKeyHeader;
    private String testURL;
    private String compTestURL;
    private String googleAPIKey;
    private String googleClientId;
    private String aesSecretKey;
}
