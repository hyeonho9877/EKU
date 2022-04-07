package com.eku.eku_ocr_test.service;

import com.eku.eku_ocr_test.domain.Student;
import com.eku.eku_ocr_test.form.SignUpForm;
import com.eku.eku_ocr_test.repository.MappingKeyRepository;
import com.eku.eku_ocr_test.repository.StudentRepository;
import com.eku.eku_ocr_test.secure.KeyGen;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class SignUpServiceTest {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MappingKeyRepository mappingKeyRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void apiTest() {
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

    @Test
    void enrollKeyMemberTest() {
        Student student = Student.builder()
                .studNo((long) 201713883)
                .email("ruldarm00@kyonggi.ac.kr")
                .name("신현호")
                .authenticated(false)
                .department("컴퓨터공학부")
                .build();
        try {
            String secretKey = Base64.getEncoder().encodeToString(KeyGen.generateKey(KeyGen.AES_192).getEncoded());
            signUpService.enrollClient(student, secretKey);

            String authKey = mappingKeyRepository.findMappingKeyByStudent(student)
                    .orElseThrow()
                    .getAuthKey();

            assertThat(authKey).isEqualTo(secretKey);
        } catch (NoSuchAlgorithmException e) {
            fail();
        }
    }

    @Test
    void emailAuthTest() {
        try {
            Student student = Student.builder()
                    .studNo((long) 201713883)
                    .email("ruldarm00@kyonggi.ac.kr")
                    .name("신현호")
                    .authenticated(false)
                    .department("컴퓨터공학부")
                    .build();
            Student target = studentRepository.findById(student.getStudNo())
                    .orElseThrow();
            String authKey = mappingKeyRepository.findMappingKeyByStudent(target)
                    .orElseThrow()
                    .getAuthKey();

            assertThat(signUpService.authEmail(authKey)).isTrue();
            //assertThat(studentRepository.findById(student.getStudNo()).orElseThrow().isAuthenticated()).isTrue();
        } catch (RuntimeException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void whenFileUploaded_thenVerifyStatus() throws Exception{
        MockMultipartFile file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes(StandardCharsets.UTF_8));

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void enrollStudent(){
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setStudNo((long)201713883);
        signUpForm.setDepartment("컴퓨터공학부");
        signUpForm.setEmail("ruldarm00@kyonggi.ac.kr");
        signUpForm.setName("신현호");
        signUpForm.setPassword("qlalfqjsgh");

        try {
            Student student = signUpService.enrollClient(signUpForm).get();
            assertThat(student.getStudNo()).isEqualTo(201713883);
        } catch (Exception e) {
            fail();
        }
    }

}