package com.eku.eku_ocr_test.secure;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * 임의의 난수키를 생성하는 클래스. 현재는 유저의 인증메일에 대한 KEY를 발행하는 메소드만 사용됨
 */
public class KeyGen {
    public static final int AES_128 = 128;
    public static final int AES_192 = 192;
    public static final int AES_256 = 256;

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
     * @param salt 딕셔너리 어택을 방지하기 위한 Salt 값
     * @return 해당 메소드에 의해 생성된 SecretKey 객체
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static SecretKey getKeyFromPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);

        return new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");
    }

    /**
     * AES 방식으로 암호화 하기 위한 IV(Initial Vector)를 생성하는 메소드
     * @return 메소드에 의해 생성된 IvParameterSpec 객체
     */
    public static IvParameterSpec generateIv() {
        byte[] Iv = new byte[16];
        new SecureRandom().nextBytes(Iv);
        return new IvParameterSpec(Iv);
    }
}
