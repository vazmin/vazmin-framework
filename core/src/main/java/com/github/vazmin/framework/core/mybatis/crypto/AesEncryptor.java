package com.github.vazmin.framework.core.mybatis.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES 加密，输入相等输出也相等
 * Created by wangzm on 2019/10/18.
 */
public class AesEncryptor implements StringEncryptor {
    private static final Logger log = LoggerFactory.getLogger(AesEncryptor.class);

    private String password;

    private static final String AES_ECB_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String ALGORITHM = "AES";
    public AesEncryptor(String password) {
        this.password = password;
    }

    @Override
    public String encrypt(String str) {
        try {
            byte[] raw = password.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, ALGORITHM);
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance(AES_ECB_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.warn("decrypt message fail. original string: {}, exception: {}", str, e.getMessage());
        }
        return str;
    }

    @Override
    public String decrypt(String encryptedStr) {
        try {
            byte[] bytes = Base64.getDecoder()
                    .decode(encryptedStr.getBytes(StandardCharsets.UTF_8));
            byte[] raw = password.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_ECB_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] original = cipher.doFinal(bytes);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.warn("decrypt message fail. encrypted string: {}, exception: {}", encryptedStr, e.getMessage());
        }
       return encryptedStr;
    }

    public String getPassword() {
        return password;
    }

}
