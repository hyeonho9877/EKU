package com.eku.EKU.controller;

import com.eku.EKU.domain.Student;
import com.eku.EKU.exceptions.DuplicateEnrollException;
import com.eku.EKU.form.OcrResponseForm;
import com.eku.EKU.form.SignUpForm;
import com.eku.EKU.service.MailService;
import com.eku.EKU.service.SignUpService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 회원가입 요청에 응답하는 컨트롤러
 */
@Controller
public class SignUpController {
    private final SignUpService signUpService;
    private final MailService mailService;

    public SignUpController(SignUpService signUpService, MailService mailService) {
        this.signUpService = signUpService;
        this.mailService = mailService;
    }

    /**
     * 회원 가입에 필요한 내용(이메일, 학번, 학과 등)을 완전히 입력한 후
     * 회원 가입 요청이 들어올 때 실행되는 메소드
     *
     * @param form 사용자의 입력 내용 폼
     * @return DB에 성공적으로 데이터가 저장되고 이메일이 발송되었을 경우 True를 리턴하며, 그렇지 못할 경우 False를 리턴한다.
     */
    @PostMapping("/signUp")
    @ResponseBody
    public ResponseEntity<?> signUp(@RequestBody SignUpForm form, HttpServletRequest request) {
        try {
            if (mailService.validateEmail(form.getEmail())) {
                Student student = signUpService.enrollClient(form).orElseThrow(DuplicateEnrollException::new);
                mailService.sendAuthMail(student, student.getEmail(), request.getRemoteAddr());
                return ResponseEntity.ok(true);
            } else return ResponseEntity.badRequest().body(false);
        } catch (DuplicateEnrollException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    /**
     * 카드형 학생증, 모바일 학생증에 대한 ocr 서비스 요청
     *
     * @param img ocr을 수행해야하는 이미지
     * @return 성공시 OcrResponseForm 객체를 담은 ok, 아닐 경우 internalService error 리턴
     */
    @PostMapping("/signUp/ocr")
    @ResponseBody
    public ResponseEntity<?> ocr(@RequestPart MultipartFile img, HttpServletRequest request) {
        try {
            OcrResponseForm response = signUpService.ocrImage(img);
            String clientResponse = signUpService.parseOcrResponse(response);
            System.out.println(clientResponse);
            return ResponseEntity.ok(clientResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("service error");
        }
    }

    /**
     * 사용자가 서버에 의해 발송된 인증 메일의 링크를 클릭할 경우 수행되는 메소드
     *
     * @param key get 방식으로 전송되는 파라미터로 사용자를 유일하게 식별할 수 있는 KEY
     * @return 성공적으로 메일을 발송한 경우, true를 담은 ok, 아닐경우 false를 담은 bad request
     */
    @GetMapping("/signUp/emailAuth")
    public String authIdentity(@RequestParam String key) {
        if (signUpService.authEmail(key)) {
            return "auth-success";
        } else return "auth-fail";
    }

    @GetMapping("/signUp/emailAuth/fail")
    public String failed() {
        return "auth-fail";
    }

    @PostMapping("/signUp/dept")
    public ResponseEntity<?> getDept(){
        return ResponseEntity.ok(signUpService.getDepartment());
    }
}
