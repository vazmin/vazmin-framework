package com.github.vazmin.framework.core.aes;

import com.github.vazmin.framework.core.util.GsonUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class WXBizMsgCryptTest {
    
    private static final Logger log = LoggerFactory.getLogger(WXBizMsgCryptTest.class);
    

    @Test
    void ed() throws AesException {
        // 需要加密的明文
        String token = "MTQ4ODYxMDY3NTgyMTpiYzRiNjhhYTc0NTJmMzAzMmEzNmRmYTIyZTM3NjIwODliNDdmYjU4NWNjYTcxMDk1ZjFmNTZhMTVjNGNhZjhhOjFfXzU4YmE1ZTY2MDBjOTYzYjYwMDAwMDAwMV9fQWlBaWVjS3ZSM1FHblNaU21hSVN0dFgxbUhQcXUyMk1KT09FS3FLbTNKelFfXzRsd09CSU1CbUVrdnAwWHpqUWRGa2cweE9nbWg5MUl6Nm9lQ0kxUGxqeXo6OTg1ZDFkZWU1YjY1NWE2NzhmMGQ4YjhhMTE3MGE2YWNjNWQ1ZmQwNjFhZTg3YWU4N2E4Yzc3MzE4MTM4ZTYxMmZjNTQ2NTNkNjQyNTQ5OWQyYjMxNGFhYTQwMGUzZTUzZDkxNmVhZTg4Mjk3ODBlNWM1NTJmM2E0YzNiNTJiZjg";
        String timestamp = "1488610680";
        String nonce = "1914910957";
        String msgSignature = "25f205e816873e0d6f426a0edeb92368e2e5a0a8";
        String aesKey = "4lwOBIMBmEkvp0XzjQdFkg0xOgmh91Iz";
        String deviceId = "AiAiecKvR3QGnSZSmaISttX1mHPqu22MJOOEKqKm3JzQ";
        AesSecret aesSecret = new AesSecret(token, aesKey, deviceId);

        //json
        String content = "abcdddd";
        log.debug("明文" + content);
        String encodingAesKey2 = RandomStringUtils.randomAlphanumeric(43);
        log.debug("encodingAesKey2：" + encodingAesKey2);

        String encrypted = GsonUtils.toJson(aesSecret.encrypt(content, timestamp, nonce));
        log.debug("密文：" + encrypted);
        EncryptedMessage encryptedMessage =
                GsonUtils.fromJson(encrypted, EncryptedMessage.class);

        Assertions.assertEquals(content, aesSecret.decrypt(encryptedMessage));
    }

}