package com.eku.EKU.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.File;
@Configuration
public class DocumentRootConfig {
    @Value("${server.tomcat.document-root}")
    private String documentRoot;
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                if (factory instanceof TomcatServletWebServerFactory) {
                    TomcatServletWebServerFactory tomcat = (TomcatServletWebServerFactory) factory;
                    if (!StringUtils.isEmpty(documentRoot)) {
                        File root = new File(documentRoot);
                        tomcat.setDocumentRoot(root);
                    }
                }
            }
        };
    }

}
