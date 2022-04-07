package com.eku.eku_ocr_test;

import com.eku.eku_ocr_test.config.CustomProperty;
import com.eku.eku_ocr_test.domain.Grade;
import com.eku.eku_ocr_test.secure.KeyGen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EkuOcrTestApplicationTests {

    @Autowired
    private CustomProperty customProperty;

    @Test
    void contextLoads() {
    }

    @Test
    void configKeyTest(){
        String hardKey = "TE1VTEllVGdTeXBacGNVekFVd2x1aERNVElaRk9teVE=";
        String ocrKey = customProperty.getOcrKey();

        assertThat(hardKey).isEqualTo(ocrKey);
    }

    @Test
    void configURLTest(){
        String hardURL = "https://g82r5armbu.apigw.ntruss.com/custom/v1/14864/0aefe0dd10994650efabc42e9bf0bac17703fa8a15b701a5036d690aead9140b/general";
        String clovaOcrUri = customProperty.getClovaOcrUri();

        assertThat(hardURL).isEqualTo(clovaOcrUri);
    }

    @Test
    void keyGenTest(){
        try {
            SecretKey secretKey = KeyGen.generateKey(192);
            String encode = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            System.out.println(encode);
        } catch (NoSuchAlgorithmException e) {
            Assertions.fail();
        }
    }

    @Test
    void enumToCodeTest(){
        for(Grade g: Grade.values()){
            System.out.println(g.getScore());
            System.out.println(g.name());
        }
    }
}
