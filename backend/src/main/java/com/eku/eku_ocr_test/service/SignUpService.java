package com.eku.eku_ocr_test.service;

import com.eku.eku_ocr_test.callbacks.OcrCallBack;
import com.eku.eku_ocr_test.config.CustomProperty;
import com.eku.eku_ocr_test.domain.MappingKey;
import com.eku.eku_ocr_test.domain.Student;
import com.eku.eku_ocr_test.form.*;
import com.eku.eku_ocr_test.repository.MappingKeyRepository;
import com.eku.eku_ocr_test.repository.StudentRepository;
import com.eku.eku_ocr_test.secure.KeyGen;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SignUpService {
    private final CustomProperty customProperty;
    private final StudentRepository studentRepository;
    private final MappingKeyRepository mappingKeyRepository;
    private final WebClient webClient;


    public SignUpService(CustomProperty customProperty, StudentRepository studentRepository, MappingKeyRepository mappingKeyRepository, WebClient webClient) {
        this.customProperty = customProperty;
        this.studentRepository = studentRepository;
        this.mappingKeyRepository = mappingKeyRepository;
        this.webClient = webClient;
    }

    public Optional<Student> enrollClient(Student student) {
        try {
            studentRepository.save(student);
            this.enrollMappingKey(student);
            return Optional.of(student);
        } catch (IllegalArgumentException | NoSuchAlgorithmException e) {
            return Optional.empty();
        }
    }

    public Optional<Student> enrollClient(Student student, String authKey) {
        try {
            studentRepository.save(student);
            this.enrollMappingKey(student, authKey);
            return Optional.of(student);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private void enrollMappingKey(Student student) throws NoSuchAlgorithmException {
        MappingKey mappingKey = new MappingKey();
        mappingKey.setAuthKey(Base64.getEncoder().encodeToString(KeyGen.generateKey(KeyGen.AES_192).getEncoded()));
        mappingKey.setStudent(student);
        mappingKeyRepository.save(mappingKey);
    }

    private void enrollMappingKey(Student student, String authKey) {
        MappingKey mappingKey = new MappingKey();
        mappingKey.setAuthKey(authKey);
        mappingKey.setStudent(student);
        mappingKeyRepository.save(mappingKey);
    }

    public boolean authEmail(String id) {
        try {
            Long studNo = mappingKeyRepository.findById(id)
                    .orElseThrow()
                    .getStudent()
                    .getStudNo();

            Student student = studentRepository.findById(studNo)
                    .orElseThrow();

            student.setAuthenticated(true);
            studentRepository.save(student);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /*    public Object ocrImage(OcrForm form, OcrCallBack callBack) {
            try {
                *//*File file = new File("C:\\Users\\Hyeonho\\Documents\\카카오톡 받은 파일\\studCard_card_resized.jpg");
            Base64.Encoder encoder = Base64.getEncoder();
            String encode = encoder.encodeToString(Files.readAllBytes(file.toPath()));*//*

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
                    .bodyToMono(OcrResponseForm.class)
                    .subscribe(result ->
                            {
                                callBack.onSuccess(result);
                            }
                    );

            while (!taskDone.get()) {

            }
            return null;
        *//*} catch (FileNotFoundException e) {
            System.out.println("failed file not found");
            return "failed file not found";*//*
        } catch (IOException e) {
            System.out.println("failed io exception");
            return "failed io exception";
        }
    }*/
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
                .bodyToMono(OcrResponseForm.class)
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
                .bodyToMono(OcrResponseForm.class)
                .subscribe(result ->
                        {
                            System.out.println(result);
                            taskDone.set(true);
                        }
                );

        while (!taskDone.get()) {

        }
    }

    public void compCompsApiTest(OcrForm form, OcrCallBack callBack) throws IOException {
        OcrImagesData imagesData = OcrImagesData.builder()
                .format(form.getFormat())
                .name(form.getName())
                .data(form.getData())
                .build();
        ClientOcrRequestForm requestForm = ClientOcrRequestForm.builder()
                .images(Collections.singletonList(imagesData))
                .requestId(form.getName())
                .build();

        webClient
                .post()
                .header(customProperty.getOcrKeyHeader(), customProperty.getOcrKey())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new ObjectMapper().writeValueAsString(requestForm))
                .retrieve()
                .bodyToMono(OcrResponseForm.class)
                .subscribe(
                        result -> {
                            try {
                                callBack.onSuccess(result);
                            } catch (IOException e) {
                            }
                        }
                );

    }

    public String parseOcrResponse(OcrResponseForm responseForm) {
        Pattern namePattern = Pattern.compile("^[김|신|박|이|정|고|방|][가-힣]{1,4}");
        Pattern deptPattern = Pattern.compile("[가-힣]{4,8}학[과|부]$");
        Pattern studNoPattern = Pattern.compile("20\\d{7}");

        List<String> texts = responseForm.getImages().get(0).getFields()
                .stream()
                .map(ImageField::getInferText)
                .collect(Collectors.toList());

        ClientOcrResponseForm.ClientOcrResponseFormBuilder responseFormBuilder = ClientOcrResponseForm.builder();
        texts.forEach(
                text -> {
                    if (namePattern.matcher(text).matches())
                        responseFormBuilder.name(text);
                    else if (deptPattern.matcher(text).matches())
                        responseFormBuilder.department(text);
                    else if (studNoPattern.matcher(text).matches())
                        responseFormBuilder.studNo(Integer.parseInt(text));
                });
        try {
            return new ObjectMapper().writeValueAsString(responseFormBuilder.build());
        } catch (IOException e) {
            return "";
        }
    }


}
