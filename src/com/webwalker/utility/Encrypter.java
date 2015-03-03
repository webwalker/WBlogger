/**
 * 
 */
package com.webwalker.utility;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import com.webwalker.utils.AppConstants;

public class Encrypter {

	private Key key;
	// 算法名称
	private String KEY_ALGORITHM = "DES";
	// 算法名称/加密模式/填充方式
	private String CIPHER_ALGORITHM = "";

	//长度8位
	private static String EncryptKey = AppConstants.EncryptKEY;

	public Encrypter() {

	}

	public Encrypter(Algorithm a) {
		KEY_ALGORITHM = getAlgorithm(a);
		// CIPHER_ALGORITHM = getAlgorithmCipher(a);
	}

	/**
	 * MD5签名
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String MD5Encrypt(String key, String value) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance(KEY_ALGORITHM);
			messageDigest.reset();
			messageDigest.update(value.getBytes());
		} catch (NoSuchAlgorithmException e) {
		}
		byte[] byteArray = null;
		if (value == null)
			byteArray = messageDigest.digest();
		else
			byteArray = messageDigest.digest(key.getBytes());
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	/**
	 * 加密以 byte[] 明文输入 ,byte[] 密文输出
	 * 
	 * @param byteS
	 * @return
	 */
	byte[] encryptByte(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 解密以 byte[] 密文输入 , 以 byte[] 明文输出
	 * 
	 * @param byteD
	 * @return
	 */
	byte[] decryptByte(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 
	 * DESEDE 三重DES
	 * 
	 */
	public enum Algorithm {
		DES, AES, DESEDE, IDEA, MD5
	}

	private String getAlgorithm(Algorithm a) {
		switch (a) {
		case DES:
			return "DES";
		case DESEDE:
			return "DESede";
		case AES:
			return "AES";
		case IDEA:
			return "IDEA";
		case MD5:
			return "MD5";
		}
		return "DES";
	}

	private String getAlgorithmCipher(Algorithm a) {
		switch (a) {
		case DES:
			return "DES/ECB/PKCS5Padding";
		case DESEDE:
			return "DESede/ECB/PKCS5Padding";
		case AES:
			return "AES/ECB/PKCS5Padding";
		case IDEA:
			return "IDEA/ECB/PKCS5Padding";
		}

		return "DES";
	}

	// good
	private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

	public static String encryptDES(String encryptString) throws Exception {
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		SecretKeySpec key = new SecretKeySpec(EncryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes());

		return Base64.encode(encryptedData);
	}

	public static String decryptDES(String decryptString) throws Exception {
		byte[] byteMi = Base64.decode(decryptString);
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		SecretKeySpec key = new SecretKeySpec(EncryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		byte decryptedData[] = cipher.doFinal(byteMi);

		return new String(decryptedData);
	}

	public static class Base64 {
		private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
				.toCharArray();

		/**
		 * data[]进行编码
		 * 
		 * @param data
		 * @return
		 */
		public static String encode(byte[] data) {
			int start = 0;
			int len = data.length;
			StringBuffer buf = new StringBuffer(data.length * 3 / 2);

			int end = len - 3;
			int i = start;
			int n = 0;

			while (i <= end) {
				int d = ((((int) data[i]) & 0x0ff) << 16)
						| ((((int) data[i + 1]) & 0x0ff) << 8)
						| (((int) data[i + 2]) & 0x0ff);

				buf.append(legalChars[(d >> 18) & 63]);
				buf.append(legalChars[(d >> 12) & 63]);
				buf.append(legalChars[(d >> 6) & 63]);
				buf.append(legalChars[d & 63]);

				i += 3;

				if (n++ >= 14) {
					n = 0;
					buf.append(" ");
				}
			}

			if (i == start + len - 2) {
				int d = ((((int) data[i]) & 0x0ff) << 16)
						| ((((int) data[i + 1]) & 255) << 8);

				buf.append(legalChars[(d >> 18) & 63]);
				buf.append(legalChars[(d >> 12) & 63]);
				buf.append(legalChars[(d >> 6) & 63]);
				buf.append("=");
			} else if (i == start + len - 1) {
				int d = (((int) data[i]) & 0x0ff) << 16;

				buf.append(legalChars[(d >> 18) & 63]);
				buf.append(legalChars[(d >> 12) & 63]);
				buf.append("==");
			}

			return buf.toString();
		}

		private static int decode(char c) {
			if (c >= 'A' && c <= 'Z')
				return ((int) c) - 65;
			else if (c >= 'a' && c <= 'z')
				return ((int) c) - 97 + 26;
			else if (c >= '0' && c <= '9')
				return ((int) c) - 48 + 26 + 26;
			else
				switch (c) {
				case '+':
					return 62;
				case '/':
					return 63;
				case '=':
					return 0;
				default:
					throw new RuntimeException("unexpected code: " + c);
				}
		}

		/**
		 * Decodes the given Base64 encoded String to a new byte array. The byte
		 * array holding the decoded data is returned.
		 */

		public static byte[] decode(String s) {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				decode(s, bos);
			} catch (IOException e) {
				throw new RuntimeException();
			}
			byte[] decodedBytes = bos.toByteArray();
			try {
				bos.close();
				bos = null;
			} catch (IOException ex) {
				System.err.println("Error while decoding BASE64: "
						+ ex.toString());
			}
			return decodedBytes;
		}

		private static void decode(String s, OutputStream os) throws IOException {
			int i = 0;

			int len = s.length();

			while (true) {
				while (i < len && s.charAt(i) <= ' ')
					i++;

				if (i == len)
					break;

				int tri = (decode(s.charAt(i)) << 18)
						+ (decode(s.charAt(i + 1)) << 12)
						+ (decode(s.charAt(i + 2)) << 6)
						+ (decode(s.charAt(i + 3)));

				os.write((tri >> 16) & 255);
				if (s.charAt(i + 2) == '=')
					break;
				os.write((tri >> 8) & 255);
				if (s.charAt(i + 3) == '=')
					break;
				os.write(tri & 255);

				i += 4;
			}
		}

	}
}