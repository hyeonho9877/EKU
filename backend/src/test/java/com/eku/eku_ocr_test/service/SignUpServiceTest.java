package com.eku.eku_ocr_test.service;

import com.eku.eku_ocr_test.form.Bounding;
import com.eku.eku_ocr_test.form.ImageField;
import com.eku.eku_ocr_test.form.OcrForm;
import com.eku.eku_ocr_test.form.OcrImagesData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class SignUpServiceTest {

    @Autowired
    private SignUpService signUpService;

    @Test
    void ocrCardImage() {
        try {
            File file = new File("C:\\Users\\Hyeonho\\Documents\\카카오톡 받은 파일\\studCard_card_resized.jpg");
            Base64.Encoder encoder = Base64.getEncoder();
            String encode= encoder.encodeToString(Files.readAllBytes(file.toPath()));

            OcrForm ocrForm = new OcrForm();
            OcrImagesData testImageData = new OcrImagesData("jpg", encode, "test");
            ArrayList<OcrImagesData> ocrImagesData = new ArrayList<>();
            ocrImagesData.add(testImageData);
            ocrForm.setImages(ocrImagesData);

            ocrForm.setRequestId("0");
            ocrForm.setTimestamp(0);

            signUpService.ocrImage(ocrForm);

        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void ocrMobileImage() {
        try {
            File file = new File("C:\\Users\\Hyeonho\\Documents\\카카오톡 받은 파일\\studCard_mobile.png");
            Base64.Encoder encoder = Base64.getEncoder();
            String encode= encoder.encodeToString(Files.readAllBytes(file.toPath()));

            OcrForm ocrForm = new OcrForm();
            OcrImagesData testImageData = new OcrImagesData("png", encode, "test");
            ArrayList<OcrImagesData> ocrImagesData = new ArrayList<>();
            ocrImagesData.add(testImageData);
            ocrForm.setImages(ocrImagesData);

            ocrForm.setRequestId("0");
            ocrForm.setTimestamp(0);

            signUpService.ocrImage(ocrForm);

        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void apiTest(){
        try {
            signUpService.apiTest();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void comApiTest() {
        try {
            signUpService.comApiTest();
        } catch (Exception e) {
            fail();
        }
    }
}