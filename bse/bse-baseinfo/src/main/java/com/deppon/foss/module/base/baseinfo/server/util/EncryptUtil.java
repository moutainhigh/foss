/**
 * PROJECT NAME: wfs
 * PACKAGE NAME: com.deppon.wfs.main.server.sso
 * FILE    NAME: EncryptUtil.java
 * COPYRIGHT: Copyright(c) 2013 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.deppon.foss.base.util.define.NumberConstants;


/**
 * 一般加密解密工具<br>
 * DESede/DES/BlowFish<br>
 * DES的密钥(key)长度是为8个字节,加密解密的速度是最快的<br>
 * DESede的密钥(key)长度是24个字节<br>
 * BlowFish的密钥(key)是可变的 加密最快，强度最高,密钥长度范围(1<=key<=16)<br>
 * 
 * @author liuhuan
 * @version 1.0
 * @date 2012-10-20
 */
public class EncryptUtil {

	/**
	 * UTF-8
	 */
    private static final String CHARSET = "UTF-8";

	/**
	 * crm_oa
	 */
    public  static final String DEFAULT_KEY="deppon_oa";
  

	/**
	* @MethodName: getCipher 
	* @description : getCipher
	* @author：chenmingyan
	* @date： 2013-9-18 下午4:12:45
	* @param isEncrypt
	* @param key
	* @return cipher
	* @throws InvalidKeyException
	* @throws NoSuchAlgorithmException
	* @throws InvalidKeySpecException
	* @throws NoSuchPaddingException Cipher
	*/
	private static Cipher getCipher(boolean isEncrypt, byte[] key)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException {
		
		/**
		 * 实例化SecretKeySpec
		 * 实例化SecureRandom
		 * 实例化Cipher
		 * 初始化cipher.init
		 * 将Cipher值返回
		 */
		SecretKeySpec keySpec = new SecretKeySpec(key, "Blowfish");
		SecureRandom sr = new SecureRandom();
		Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
		cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE,
				keySpec, sr);
		return cipher;
	}

	/**
	* @MethodName: encrypt 
	* @description : encrypt
	* @author：chenmingyan
	* @date： 2013-9-18 下午4:13:41
	* @param data
	* @param key
	* @return String
	*/
	public static String encrypt(String data, String key) {
		if(data==null){
			return null;
		}
		Cipher cipher;
		try {
			cipher = getCipher(true, getBytes(key));
			return byte2hex(cipher.doFinal(data.getBytes(CHARSET)));
		} catch (Exception e) {
			return null;
		} 
	}

	/**
	* @MethodName: decrypt 
	* @description : decrypt
	* @author：chenmingyan
	* @date： 2013-9-18 下午4:13:51
	* @param data
	* @param key
	* @return String
	*/
	public static String decrypt(String data, String key){
		if(data==null){
			return null;
		}
		Cipher cipher;
		try {
			
			cipher = getCipher(false, getBytes(key));
			byte[] b=hex2byte(data.getBytes(CHARSET));
			if(b==null){
				return null;
			}
            return new String(cipher.doFinal(b),CHARSET);
		} catch (Exception e) {
			return null;
		} 
		
	}

	/**
	* @MethodName: byte2hex 
	* @description : byte2hex
	* @author：chenmingyan
	* @date： 2013-9-18 下午4:14:00
	* @param b
	* @return String
	*/
	private static String byte2hex(byte[] b) {
		//String hs = "";
		StringBuffer hs = new StringBuffer();
		String stmp ;
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1){
				//hs = hs + "0" + stmp;
				hs.append("0");
				hs.append(stmp);
			}else{
				//hs = hs + stmp;
				hs.append(stmp);
			}
		}
		return hs.toString().toUpperCase();

	}

	/**
	* @MethodName: hex2byte 
	* @description : hex2byte
	* @author：chenmingyan
	* @date： 2013-9-18 下午4:14:08
	* @param b
	* @return
	* @throws Exception byte[]
	*/
	private static byte[] hex2byte(byte[] b) throws  UnsupportedEncodingException{
		if ((b.length % 2) != 0)
			return null;
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2,CHARSET);
			b2[n / 2] = (byte) Integer.parseInt(item, NumberConstants.NUMBER_16);
		}
		return b2;
	}
	
	/**
	* @MethodName: getBytes 
	* @description : getBytes
	* @author：chenmingyan
	* @date： 2013-9-18 下午4:14:19
	* @param str
	* @return
	* @throws UnsupportedEncodingException byte[]
	*/
	private static byte[] getBytes(String str) throws UnsupportedEncodingException {
		return str.getBytes(CHARSET);
	}


    /**
    * @MethodName: main 
    * @description : main
    * @author：chenmingyan
    * @date： 2013-9-18 下午4:14:25
    * @param args void
    */
//    public static void main(String[] args) {
//      decrypt("3B1F97D071D45DA01B65EC14CD47EDE01D215F81149ADB04FAA7A7297A9504EB511925F8A7608F1308576D3D4C610C6518361AE0AD05D79BC9CCBA50228FE809B9956EAE0F8EA25477E4CD08E5687ED7FF23E33634568EF049F5AD8800746BFA7F2C5195A64006700419BA2CA26189AC3A5DF8F9293659D6858C50FAC673173FDE5E67464600FF9476B26CC84C486E5DED50E5B26FAD215C851248DE9AA427418076CD020E6C76AB819D5BED2D7295AC1719FDA309AD3E5F1CA20B15E7406765CAE16E2C4217072420B9082E1ADA72696F5907DE362ADA80D61542FD6E042426F5D6B21D62DD8777CFFFFCA4DC9CBB71", DEFAULT_KEY);
//    }


}
