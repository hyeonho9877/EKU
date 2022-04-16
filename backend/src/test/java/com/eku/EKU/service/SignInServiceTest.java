package com.eku.EKU.service;

import com.eku.EKU.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SignInServiceTest {

    @Autowired
    private SignInService signInService;

    @Autowired
    private StudentRepository studentRepository;


    /*@Test
    void authStudent() {
        SignInForm signInForm = new SignInForm();
        signInForm.setEmail("ruldarm00@kyonggi.ac.kr");
        signInForm.setPassword("qlalfqjsgh");
        try {
            boolean result = signInService.authStudent(signInForm);
            assertThat(result).isTrue();
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
            fail();
        }
    }*/
}
