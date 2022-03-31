package com.eku.eku_ocr_test.service;

import com.eku.eku_ocr_test.domain.Student;
import com.eku.eku_ocr_test.repository.MappingKeyRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final MappingKeyRepository mappingKeyRepository;

    public MailService(JavaMailSender mailSender, MappingKeyRepository mappingKeyRepository) {
        this.mailSender = mailSender;
        this.mappingKeyRepository = mappingKeyRepository;
    }

    public boolean sendAuthMail(Student student, String email){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String authKey = mappingKeyRepository.findMappingKeyByStudent(student)
                .orElseThrow()
                .getAuthKey();
        String authLink = "http://localhost:8080/signUp/emailAuth?key="+authKey.replace("+", "%2B");
        mailMessage.setTo(email);
        mailMessage.setSubject("EKU! (Everywhere Kyonggi Up!) 가입 확인 메일입니다.");
        mailMessage.setText("인증 링크 : "+authLink);
        mailSender.send(mailMessage);
        return true;
    }

    public boolean validateEmail(String email){
        Pattern emailPattern = Pattern.compile("\\w+(@kyonggi.ac.kr)$");
        return emailPattern.matcher(email).matches();
    }
}
