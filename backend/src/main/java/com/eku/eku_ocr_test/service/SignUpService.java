package com.eku.eku_ocr_test.service;

import com.eku.eku_ocr_test.config.CustomProperty;
import com.eku.eku_ocr_test.domain.Member;
import com.eku.eku_ocr_test.form.ImageField;
import com.eku.eku_ocr_test.form.OcrForm;
import com.eku.eku_ocr_test.form.OcrResponseForm;
import com.eku.eku_ocr_test.repository.SignUpRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class SignUpService {

    private final CustomProperty customProperty;
    private final SignUpRepository signUpRepository;
    private final WebClient webClient;

    public SignUpService(CustomProperty customProperty, SignUpRepository signUpRepository, WebClient webClient) {
        this.customProperty = customProperty;
        this.signUpRepository = signUpRepository;
        this.webClient = webClient;
    }

    public boolean enrollMember(Member member) {
        try {
            signUpRepository.save(member);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Object ocrImage(OcrForm form) {
        try {
            /*File file = new File("C:\\Users\\Hyeonho\\Documents\\카카오톡 받은 파일\\studCard_card_resized.jpg");
            Base64.Encoder encoder = Base64.getEncoder();
            String encode = encoder.encodeToString(Files.readAllBytes(file.toPath()));*/

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(form);

            System.out.println(jsonString);

            AtomicBoolean taskDone = new AtomicBoolean(false);
            webClient
                    .post()
                    .accept(MediaType.APPLICATION_JSON)
                    .header(customProperty.getOcrKeyHeader(), customProperty.getOcrKey())
                    .body(BodyInserters.fromValue(jsonString))
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(result ->
                            {
                                System.out.println(result);
                                taskDone.set(true);
                            }
                    );

            while (!taskDone.get()) {

            }
            return null;
        /*} catch (FileNotFoundException e) {
            System.out.println("failed file not found");
            return "failed file not found";*/
        } catch (IOException e) {
            System.out.println("failed io exception");
            return "failed io exception";
        }
    }

    public void apiTest() {
        AtomicBoolean taskDone = new AtomicBoolean(false);
        WebClient client = webClient
                .mutate()
                .baseUrl(customProperty.getTestURL())
                .build();
        client
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Member.class)
                .subscribe(result ->
                        {
                            System.out.println(result);
                            taskDone.set(true);
                        }
                );

        while (!taskDone.get()) {

        }
    }

    public void comApiTest() {
        AtomicBoolean taskDone = new AtomicBoolean(false);
        WebClient client = webClient
                .mutate()
                .baseUrl(customProperty.getCompTestURL())
                .build();
        client
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ImageField.class)
                .subscribe(result ->
                        {
                            System.out.println(result);
                            taskDone.set(true);
                        }
                );

        while (!taskDone.get()) {

        }
    }
}
