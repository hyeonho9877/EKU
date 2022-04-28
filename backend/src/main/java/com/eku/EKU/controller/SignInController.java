package com.eku.EKU.controller;

import com.eku.EKU.exceptions.NoAuthExceptions;
import com.eku.EKU.exceptions.NoSuchStudentException;
import com.eku.EKU.form.SignInForm;
import com.eku.EKU.service.SignInService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 로그인 요청을 담당하는 컨트롤러
 */
@RestController
public class SignInController {

    private final SignInService signInService;

    public SignInController(SignInService signInService) {
        this.signInService = signInService;
    }

    /**
     * 로그인 요청
     * @param form 로그인 하려는 사용자의 정보가 담긴 Form 객체
     * @return 정보가 db와 일치하면 ok, 아닐 경우 bad request 혹은 internal server error
     */
    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody SignInForm form) {
        try {
            if (signInService.authStudent(form))
                return ResponseEntity.ok().body("SignIn Success.");
            else return ResponseEntity.badRequest().body("Password not matching");
        } catch (NoSuchStudentException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            return ResponseEntity.internalServerError().body("Server in Error.");
        } catch (BadPaddingException e) {
            return ResponseEntity.badRequest().body("Password not matching.");
        } catch (NoAuthExceptions exceptions) {
            return ResponseEntity.badRequest().body("not authorized");
        }
    }
}
