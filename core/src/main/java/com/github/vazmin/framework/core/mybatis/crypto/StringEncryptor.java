package com.github.vazmin.framework.core.mybatis.crypto;

/**
 * 字符加密器
 * 相同参数，返回值应该幂等
 * Created by wangzm on 2019/10/18.
 */
public interface StringEncryptor {

  /**
   * Encrypt the byte array.
   */
  String encrypt(String str);

  /**
   * Decrypt the byte array.
   */
  String decrypt(String encryptedStr);


}
