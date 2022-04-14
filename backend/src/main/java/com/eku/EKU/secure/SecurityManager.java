package com.eku.EKU.secure;

import org.springframework.stereotype.Component;

import javax.crypto.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 암호화와 복호화를 담당하는 클래스, 비밀번호 보안에 사용됨
 */
@Component
public class SecurityManager {

    /**
     * 암호화 메소드
     * @param input 암호화 하려는 PlainText
     * @param key 암호화에 사용되는 SecretKey
     * @param Iv 암호화에 사용되는 Initial Vector
     * @return 암호화된 byte 배열의 cipherText
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     */
    public byte[] encrypt(byte[] input, SecretKey key, byte[] Iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, KeyGen.generateGCM(Iv));
        byte[] cipherText = cipher.doFinal(input);
        return cipherText;
    }

    /**
     * 암호화를 진행할 때 사용된 IV를 암호문앞에 Prefix 형태로 추가한 암호문을 생성하는 메소드
     * @param plain 암호화 하려는 평문
     * @param key 암호화 시 사용되는 SecretKey
     * @param iv 암호화 시 사용되는 Initial Vector
     * @return iv가 Prefix된 암호문
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     */
    public byte[] encryptWithPrefixIV(byte[] plain, SecretKey key, byte[] iv) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        byte[] cipherText = encrypt(plain, key, iv);
        System.out.println("encrypt key : "+Arrays.toString(key.getEncoded()));
        System.out.println("encrypt iv : "+Arrays.toString(iv));
        byte[] cipherTextWithIv = ByteBuffer.allocate(iv.length + cipherText.length)
                .put(iv)
                .put(cipherText)
                .array();

        return cipherTextWithIv;
    }

    /**
     * 암호문을 복호화하는 메소드
     * @param input 복호화하려는 cipherText
     * @param key 복호화 시 사용되는 SecretKey로 암호화 때 사용한 Key와 같아야 함.
     * @param Iv 복호화 시 사용되는 iv, 암호화 때 사용한 iv와 같아야 함
     * @return 복호화 된 Plain Text의 byte 배열
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     */
    public String decrypt(byte[] input, SecretKey key, byte[] Iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, KeyGen.generateGCM(Iv));
        System.out.println("decrypt key : "+Arrays.toString(key.getEncoded()));
        byte[] plainText = cipher.doFinal(input);
        return new String(plainText, StandardCharsets.UTF_8);
    }

    /**
     * iv가 prefix된 암호문을 복호화하는 메소드
     * @param cipher 복호화하려는 cipherText
     * @param key 복호화 하는데 사용되는 SecretKey
     * @return 복호화된 Plain Text의 byte 배열
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws AEADBadTagException
     */
    public String decryptWithPrefixIV(byte[] cipher, SecretKey key) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, AEADBadTagException {
        ByteBuffer bb = ByteBuffer.wrap(cipher);

        byte[] iv = new byte[KeyGen.IV_LENGTH_BYTE];
        bb.get(iv);
        System.out.println("decrypt iv : "+Arrays.toString(iv));

        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);

        String plainText = decrypt(cipherText, key, iv);
        return plainText;
    }

    public static final String ALGORITHM = "AES/GCM/NoPadding";
}
