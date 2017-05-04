package com.deppon.foss.module.login.shared.util;

import java.io.IOException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.encypt.base64.BASE64Decoder;
import com.deppon.foss.framework.shared.encypt.base64.BASE64Encoder;

public class DesUtil {
	public static final String ALGORITHM = "DES";
	
	/**
	 * 秘钥组
	 */
	public static final  String[] keys = {"^6a,@e-P","3%I*g/c.","b5nW$@+;","'#9k]t!X","4V(o&y2^"};
	  
	public static byte[] decryptBASE64(String key){  
	    try {
			return new BASE64Decoder().decodeBuffer(key);
		} catch (IOException e) {
			throw new BusinessException(e.getMessage());
		}
	}  
	public static String encryptBASE64(byte[] key){  
	    return new BASE64Encoder().encodeBuffer(key);  
	} 
	
	public static int randomNumber(int maxNumber){
		return new Random().nextInt(maxNumber);
	}
	
	public static String getKey(int index){
		if(index>=keys.length){
			return keys[0];
		}
		return keys[index];
	}
    
    private static Key toKey(byte[] key){  
        DESKeySpec dks = null;
        SecretKeyFactory keyFactory = null;  
        SecretKey secretKey = null;  
		try {
			dks = new DESKeySpec(key);
			keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
			secretKey = keyFactory.generateSecret(dks);
			return secretKey;  
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}  
    }  
  
      
    public static byte[] decrypt(byte[] data, String key){  
        Key k = toKey(decryptBASE64(key));  
        Cipher cipher = null;
        try {
        	cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, k);
	        return cipher.doFinal(data);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}  
    }  
  
      
    public static byte[] encrypt(byte[] data, String key){  
        Key k = toKey(decryptBASE64(key));  
        Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, k);  
	        return cipher.doFinal(data);  
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		} 
    }  
  
      
    public static String initKey(){  
        return initKey(null);  
    }  
  
      
    public static String initKey(String seed){  
        SecureRandom secureRandom = null;  
        if (seed != null) {  
            secureRandom = new SecureRandom(decryptBASE64(seed));  
        } else {  
            secureRandom = new SecureRandom();  
        }  
        KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(ALGORITHM);
			kg.init(secureRandom);  
			SecretKey secretKey = kg.generateKey();  
			return encryptBASE64(secretKey.getEncoded());  
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}  
    }  
    
    public static String encryptStr(String inputStr,String key){
    	byte[] inputData = inputStr.getBytes();  
        inputData = DesUtil.encrypt(inputData, key); 
    	return DesUtil.encryptBASE64(inputData);
    }
    
    public static String decryptStr(String inputStr,String key){
    	byte[] inputData = DesUtil.decryptBASE64(inputStr);
    	byte[] outputData = DesUtil.decrypt(inputData, key);  
    	return  new String(outputData);
    }
    
    
    public static void execute(String inputStr) throws Exception {
        String key = DesUtil.initKey();  
        System.err.println("原文:\t" + inputStr);  
  
        System.err.println("密钥:\t" + key);  
  
        byte[] inputData = inputStr.getBytes();  
        inputData = DesUtil.encrypt(inputData, key);  
  
        System.err.println("加密后:\t" + DesUtil.encryptBASE64(inputData));  
        
        inputData = DesUtil.decryptBASE64(DesUtil.encryptBASE64(inputData));
  
        byte[] outputData = DesUtil.decrypt(inputData, key);  
        String outputStr = new String(outputData);  
  
        System.err.println("解密后:\t" + outputStr);  
	}
}
