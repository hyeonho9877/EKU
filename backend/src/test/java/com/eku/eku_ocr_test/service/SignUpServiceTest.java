package com.eku.eku_ocr_test.service;

import com.eku.eku_ocr_test.domain.Student;
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

    /*@Test
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
    }*/

    /*@Test
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
    }*/

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

}