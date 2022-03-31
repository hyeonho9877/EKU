package com.eku.eku_ocr_test.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom.properties")
@Getter
@Setter
public class CustomProperty {
    private String clovaOcrUri;
    private String ocrKey;
    private String ocrKeyHeader;
    private String testURL;
    private String compTestURL;
    private String googleAPIKey;
    private String googleClientId;
    private String aesSecretKey;
}
