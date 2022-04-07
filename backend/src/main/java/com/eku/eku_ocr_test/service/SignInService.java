package com.eku.eku_ocr_test.service;

import com.eku.eku_ocr_test.config.CustomProperty;
import com.eku.eku_ocr_test.domain.Student;
import com.eku.eku_ocr_test.exceptions.NoSuchStudentException;
import com.eku.eku_ocr_test.form.SignInForm;
import com.eku.eku_ocr_test.repository.StudentRepository;
import com.eku.eku_ocr_test.secure.KeyGen;
import com.eku.eku_ocr_test.secure.SecurityManager;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * 로그인에 관련된 로직을 수행하는 Service 클래스
 */
@Service
public class SignInService {
    private final StudentRepository studentRepository;
    private final SecurityManager securityManager;
    private final CustomProperty customProperty;

    public SignInService(StudentRepository studentRepository, SecurityManager securityManager, CustomProperty customProperty) {
        this.studentRepository = studentRepository;
        this.securityManager = securityManager;
        this.customProperty = customProperty;
    }

    /**
     * db에 저장된 암호화된 비밀번호와 사용자가 제시한 비밀번호를 비교하는 사용자 인증을 수행하는 메소드
     * @param form 사용자의 id와 비밀번호가 담긴 Form 객체
     * @return 제공된 비밀번호와 db에 저장된 비밀번호의 일치 여부
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws NoSuchStudentException
     * @throws InvalidAlgorithmParameterException
     * @throws BadPaddingException
     */
    public boolean authStudent(SignInForm form) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchStudentException, InvalidAlgorithmParameterException, BadPaddingException {
        Student target = studentRepository.findStudentByEmail(form.getEmail()).orElseThrow(NoSuchStudentException::new);
        String password = target.getPassword();
        byte[] decode = Base64.getDecoder().decode(password);
        String decryptedPassword = securityManager.decryptWithPrefixIV(decode, KeyGen.getKeyFromPassword(form.getPassword(), target.getName()));
        return form.getPassword().equals(decryptedPassword);
    }
}
