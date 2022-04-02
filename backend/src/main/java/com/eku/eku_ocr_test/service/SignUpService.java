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

/**
 * 회원 가입에 관련된 기능을 담당하는 서비스 클래스
 */
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

    /**
     * 특정 학생에 대한 데이터를 DB에 저장하고 1대1로 매핑되는 키를 발행하여 다른 DB에 저장하는 메소드
     * @param student 대상 학생
     * @return 정상적인 종료시 Optional<Student> 객체를 반환, 반대의 경우 Optional.empty() 반환
     */
    public Optional<Student> enrollClient(Student student) {
        try {
            studentRepository.save(student);
            this.enrollMappingKey(student);
            return Optional.of(student);
        } catch (IllegalArgumentException | NoSuchAlgorithmException e) {
            return Optional.empty();
        }
    }

    /**
     * 특정 학생에게 임의의 키가 아닌 특정 키를 발행하고 저장하는 메소드, 테스트목적으로 작성
     * @param student 대상 학생
     * @param authKey 특정 키
     * @return 정상적인 종료시 Optional<Student> 객체를 반환, 반대의 경우 Optional.empty() 반환
     */
    public Optional<Student> enrollClient(Student student, String authKey) {
        try {
            studentRepository.save(student);
            this.enrollMappingKey(student, authKey);
            return Optional.of(student);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    /**
     * 특정 학생에 대한 임의의 Key를 발행하고 MappingKey 테이블에 저장하는 메소드
     * @param student 대상 학생
     * @throws NoSuchAlgorithmException
     */
    private void enrollMappingKey(Student student) throws NoSuchAlgorithmException {
        MappingKey mappingKey = new MappingKey();
        mappingKey.setAuthKey(Base64.getEncoder().encodeToString(KeyGen.generateKey(KeyGen.AES_192).getEncoded()));
        mappingKey.setStudent(student);
        mappingKeyRepository.save(mappingKey);
    }

    /**
     * 특정 학생에 대한 특정한 Key를 발행하고 MappingKey 테이블에 저장하는 메소드
     * @param student 대상 학생
     * @param authKey 특정 키
     */
    private void enrollMappingKey(Student student, String authKey) {
        MappingKey mappingKey = new MappingKey();
        mappingKey.setAuthKey(authKey);
        mappingKey.setStudent(student);
        mappingKeyRepository.save(mappingKey);
    }

    /**
     * 서버에서 발송한 이메일을 사용자가 누를 경우, 해당 이메일을 검증하는 메소드
     * @param key 서버에서 발행한 key값
     * @return 성공적으로 학생의 Authenticated 속성을 True로 바꾼 경우 True 리턴, 반대의 경우 False 리턴
     */
    public boolean authEmail(String key) {
        try {
            Long studNo = mappingKeyRepository.findById(key)
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
