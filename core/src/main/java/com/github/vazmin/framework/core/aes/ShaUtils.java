package com.github.vazmin.framework.core.aes;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

/**
 * ShaUtils class
 *
 * 计算公众平台的消息签名接口.
 */
public class ShaUtils {

	/**
	 * 用SHA1算法生成安全签名
	 * @param token 票据
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @param encrypt 密文
	 * @return 安全签名
	 * @throws AesException 
	 */
	public static String sha1Hex(
			String token, String timestamp, String nonce, String encrypt)
			throws AesException {
        return sha1Hex(new String[] {token, timestamp, nonce, encrypt});
	}

	/**
	 * 用SHA1算法生成安全签名
	 * @param strArray String[] 要计算签名的字符串数组
	 * @return String 安全签名
	 * @throws AesException
	 */
	public static String sha1Hex(String[] strArray) throws AesException {
		try {
			return DigestUtils.sha1Hex(getSortBytes(strArray));
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ComputeSignatureError);
		}
	}

	/**
	 * 用SHA1算法生成安全签名
	 * @param token 票据
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @param encrypt 密文
	 * @return 安全签名
	 * @throws AesException
	 */
	public static String sha256Hex(
			String token, String timestamp, String nonce, String encrypt)
			throws AesException {
		return sha256Hex(new String[] {token, timestamp, nonce, encrypt});
	}

	/**
	 * 用SHA1算法生成安全签名
	 * @param strArray String[] 要计算签名的字符串数组
	 * @return String 安全签名
	 * @throws AesException
	 */
	public static String sha256Hex(String[] strArray) throws AesException {
		try {
            return DigestUtils.sha256Hex(getSortBytes(strArray));
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ComputeSignatureError);
		}
	}

    private static byte[] getSortBytes(String[] strArray) {
        StringBuilder sb = new StringBuilder();
        // 字符串排序
        Arrays.sort(strArray);
        for (String str : strArray) {
            sb.append(str);
        }
        return sb.toString().getBytes();
    }

}
