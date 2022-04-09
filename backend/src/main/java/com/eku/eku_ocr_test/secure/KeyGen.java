package com.eku.eku_ocr_test.secure;

import com.eku.eku_ocr_test.config.CustomProperty;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * 임의의 난수키를 생성하는 클래스. 현재는 유저의 인증메일에 대한 KEY를 발행하는 메소드만 사용됨
 */
@Component
public class KeyGen {
    public static final int AES_128 = 128;
    public static final int AES_192 = 192;
    public static final int AES_256 = 256;
    private static final int TAG_LENGTH_BIT = 128;
    private final CustomProperty customProperty;
    public static final int IV_LENGTH_BYTE = 16;

    public KeyGen(CustomProperty customProperty) {
        this.customProperty = customProperty;
    }

    /**
     * n비트의 key를 생성하는 메소드
     * @param n 비트수, 허용되는 n은 세 가지로 KeyGen 클래스에 정의된 static AES_N로 제공되고 있음
     * @return 생성된 SecretKey 객체
     * @throws NoSuchAlgorithmException
     */
    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        return keyGenerator.generateKey();
    }

    /**
     * 사용자의 패스워드를 기반으로 임의의 키를 생성 (PBKDF)하는 메소드
     * @param password 비밀번호
     * @param salt 레인보우 어택을 방지하기 위한 Salt 값
     * @return 해당 메소드에 의해 생성된 SecretKey 객체
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static SecretKey getKeyFromPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    /**
     * AES/GCM/NoPadding 알고리즘을 이용하여 암호화를 진행하기 위한 GCMParameterSpec 생성 메소드
     * @return GCMParameter 객체
     */
    public static GCMParameterSpec generateGCM() {
        byte[] Iv = new byte[IV_LENGTH_BYTE];
        new SecureRandom().nextBytes(Iv);
        return new GCMParameterSpec(TAG_LENGTH_BIT, Iv);
    }

    /**
     * 특정 iv를 가지고 GCMParameterSpec을 생성하는 메소드
     * @param Iv 초기화를 위한 iv
     * @return iv를 이용하여 생성된 GCMParameterSpec 객체
     */
    public static GCMParameterSpec generateGCM(byte[] Iv) {
        return new GCMParameterSpec(TAG_LENGTH_BIT, Iv);
    }
}
