package com.tgr.springbootmybatis.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class SecurityUtil {

	public static String encryptSHA256(String text) {
		try {
			byte[] data = text.getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			sha.update(data);
			return new BigInteger(1, sha.digest()).toString(36);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * MD5
	 * 
	 * @param text
	 * @return
	 */
	public static String signatureByMD5(String text) {
		try {
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
			// 对参数进行加密活动签名
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(text.getBytes("UTF-8"));
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String encodePassword(String password, String salt) {
		return encryptSHA256(encryptSHA256(password) + salt);
	}

	public static boolean checkParametersSignature(Map<String, String[]> map) {
		String req_sign = "-req-sign-error";
		StringBuffer sb = new StringBuffer();
		TreeMap<String, String[]> sortedMap = new TreeMap<String, String[]>(map);
		Set<Entry<String, String[]>> set = sortedMap.entrySet();
		for (Entry<String, String[]> entry : set) {
			if ("sign".equals(entry.getKey())) {
				req_sign = entry.getValue()[0];
			} else {
				sb.append(entry.getKey()).append(entry.getValue()[0]);
			}
		}
		String signed = encryptSHA256("sign:" + sb.toString());
		return signed.equals(req_sign);
	}

	public static String parametersSignature(Map<String, String[]> map) {
		StringBuffer sb = new StringBuffer();
		TreeMap<String, String[]> sortedMap = new TreeMap<String, String[]>(map);
		Set<Entry<String, String[]>> set = sortedMap.entrySet();
		for (Entry<String, String[]> entry : set) {
			if (!"sign".equals(entry.getKey())) {
				sb.append(entry.getKey()).append(entry.getValue()[0]);
			}
		}
		String signed = encryptSHA256("sign:" + sb.toString());
		return signed;
	}

	/*public static String decryptWxData(String encryptedData, String sessionKey, String iv) {
		byte[] dataByte = Base64.decodeBase64(encryptedData);
		byte[] keyByte = Base64.decodeBase64(sessionKey);
		byte[] ivByte = Base64.decodeBase64(iv);
		try {
			// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
			int base = 16;
			if (keyByte.length % base != 0) {
				int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
				keyByte = temp;
			}
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters); // 初始化
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				String result = new String(resultByte, "UTF-8");
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
}
