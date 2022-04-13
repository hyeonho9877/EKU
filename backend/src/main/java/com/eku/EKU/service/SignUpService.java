package com.eku.EKU.service;

import com.eku.EKU.config.CustomProperty;
import com.eku.EKU.domain.MappingKey;
import com.eku.EKU.domain.Student;
import com.eku.EKU.exceptions.NoSuchAuthKeyException;
import com.eku.EKU.exceptions.NoSuchStudentException;
import com.eku.EKU.form.*;
import com.eku.EKU.repository.MappingKeyRepository;
import com.eku.EKU.repository.StudentRepository;
import com.eku.EKU.secure.KeyGen;
import com.eku.EKU.secure.SecurityManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    private final SecurityManager securityManager;

    public SignUpService(CustomProperty customProperty, StudentRepository studentRepository, MappingKeyRepository mappingKeyRepository, WebClient webClient, SecurityManager securityManager) {
        this.customProperty = customProperty;
        this.studentRepository = studentRepository;
        this.mappingKeyRepository = mappingKeyRepository;
        this.webClient = webClient;
        this.securityManager = securityManager;
    }

    /**
     * 특정 학생에 대한 데이터를 DB에 저장하고 1대1로 매핑되는 키를 발행하여 다른 DB에 저장하는 메소드
     *
     * @param form 대상 학생의 정보가 담긴 폼
     * @return 정상적인 종료시 Optional<Student> 객체를 반환, 반대의 경우 Optional.empty() 반환
     */
    public Optional<Student> enrollClient(SignUpForm form) {
        try {
            byte[] encryptedPasswordBytes = securityManager.encryptWithPrefixIV(form.getPassword().getBytes(StandardCharsets.UTF_8), KeyGen.getKeyFromPassword(form.getPassword(), form.getName()), KeyGen.generateGCM().getIV());
            Student student = Student.builder()
                    .studNo(form.getStudNo())
                    .authenticated(false)
                    .password(Base64.getEncoder().encodeToString(encryptedPasswordBytes))
                    .email(form.getEmail())
                    .department(form.getDepartment())
                    .name(form.getName())
                    .salt(form.getName())
                    .build();

            Student result = studentRepository.save(student);
            this.enrollMappingKey(student);
            return Optional.of(result);
        } catch (IllegalArgumentException | InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * 특정 학생에게 임의의 키가 아닌 특정 키를 발행하고 저장하는 메소드, 테스트목적으로 작성
     *
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
     *
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
     *
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
     *
     * @param key 서버에서 발행한 key값
     * @return 성공적으로 학생의 Authenticated 속성을 True로 바꾼 경우 True 리턴, 반대의 경우 False 리턴
     */
    public boolean authEmail(String key) {
        try {
            Long studNo = mappingKeyRepository.findById(key)
                    .orElseThrow(NoSuchAuthKeyException::new)
                    .getStudent()
                    .getStudNo();

            Student student = studentRepository.findById(studNo)
                    .orElseThrow(NoSuchStudentException::new);

            student.setAuthenticated(true);
            studentRepository.save(student);
            return true;
        } catch (NoSuchStudentException| NoSuchAuthKeyException e) {
            return false;
        }
    }

    /**
     * 네이버에 OCR 이미지 요청함
     * @param img 요청하려는 IMG 파일
     * @return OCR을 수행한 결과
     */
    public OcrResponseForm ocrImage(MultipartFile img) {
        try {
            Path tmp = Files.createTempDirectory("tmp");

            String imgName = img.getOriginalFilename();
            assert imgName != null;
            String[] split = imgName.split("\\.");
            String imgFullPath = tmp + "\\" + imgName;
            File imgFile = new File(imgFullPath);
            img.transferTo(imgFile);

            ClientOcrRequestForm requestForm = ClientOcrRequestForm.builder()
                    .version("V2")
                    .requestId(split[0])
                    .timestamp(0)
                    .images(Collections.singletonList(OcrImagesData.builder().format(split[1]).name(split[0]).build()))
                    .build();

            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            String imageHeader = String.format("form-data; name=%s; filename=%s", "file", imgFullPath);
            String jsonHeader = String.format("form-data; name=%s;", "message");

            builder.part("file", new FileSystemResource(imgFile)).header("Content-Disposition", imageHeader);
            builder.part("message", requestForm.toString()).header("Content-Disposition", jsonHeader);

            return webClient
                    .post()
                    .header(customProperty.getOcrKeyHeader(), customProperty.getOcrKey())
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(OcrResponseForm.class)
                    .onErrorStop()
                    .block();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 네이버에서 받은 OCR 결과를 이름, 학과, 학번으로 파싱하는 메소드
     * @param responseForm OCR 결과를 담고 있는 Form 객체
     * @return 파싱된 String
     */
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
