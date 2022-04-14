package com.eku.EKU.service;

import com.eku.EKU.domain.Student;
import com.eku.EKU.repository.MappingKeyRepository;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * 메일을 보내고 인증하는 기능을 담당하는 서비스 클래스
 */
@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final MappingKeyRepository mappingKeyRepository;

    public MailService(JavaMailSender mailSender, MappingKeyRepository mappingKeyRepository) {
        this.mailSender = mailSender;
        this.mappingKeyRepository = mappingKeyRepository;
    }

    /**
     * 사용자와 1대1로 매핑되는 인증키를 기반으로 링크를 생성하고 이메일을 발송하는 메소드
     * @param student 발송 되상이 되는 학생
     * @param email 학생의 이메일 주소
     * @return 메일을 성공적으로 발송한 경우 True를 리턴, 반대의 경우 False 리턴
     */
    public void sendAuthMail(Student student, String email){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            String authKey = mappingKeyRepository.findMappingKeyByStudent(student)
                    .orElseThrow()
                    .getAuthKey();
            String authLink = "http://115.85.182.126:8080/signUp/emailAuth?key=" + authKey.replace("+", "%2B");
            mailMessage.setTo(email);
            mailMessage.setSubject("EKU! (Everywhere Kyonggi Up!) 가입 확인 메일입니다.");
            mailMessage.setText("인증 링크 : " + authLink);
            mailSender.send(mailMessage);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 회원가입 진행 시, 사용자가 입력한 이메일이 경기대학교의 이메일이 맞는지 검증하는 메소드
     * @param email 사용자가 입력한 이메일 주소
     * @return 경기대학교 이메일이 맞을 경우 True, 아닐 경우 False 리턴
     */
    public boolean validateEmail(String email){
        Pattern emailPattern = Pattern.compile("\\w+(@kyonggi.ac.kr)$");
        return emailPattern.matcher(email).matches();
    }
}
