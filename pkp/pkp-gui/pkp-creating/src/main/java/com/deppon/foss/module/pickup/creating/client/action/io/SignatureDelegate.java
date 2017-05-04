/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/io/SignatureDelegate.java
 * 
 * FILE NAME        	: SignatureDelegate.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数字签名生成
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105089-foss-yangtong,date:2012-10-12 上午10:56:23 </p>
 * @author 105089-foss-yangtong
 * @date 2012-10-12 上午10:56:23
 * @since
 * @version
 */
public class SignatureDelegate {
    
	//log object
	private static final Log LOGGER = LogFactory.getLog(SignatureDelegate.class);

	private static final int NUM_512 = 512;
	
	private static ObjectInputStream in;
	
	private static ObjectOutputStream out;
		/**
	     * 数字签名生成密钥
	     * 第一步生成密钥对,如果已经生成过,本过程就可以跳过,对用户来讲myprikey.dat要保存在本地
	     * 而mypubkey.dat给发布给其它用户
	     * @author 105089-foss-yangtong
	     * @date 2012-10-13 下午3:47:18
	     * @see
	     */
		public static void isExit()  throws IOException {
			if (!(new java.io.File("prikey.dat")).exists()) {  //express is  false 简化布尔表达式
				if (generatekey() == false) {
					LOGGER.error("生成密钥对败");
					return;
				}
			}
		}
 
		/**
	     * 第二步,此用户
	     * 从文件中读入私钥,对一个文件字符串进行签名后保存在一个文件(myinfo.dat)中
	     * 并且再把myinfo.dat发送出去
	     * 为了方便数字签名也放进了myifno.dat文件中,当然也可分别发送
	     * @author 105089-foss-yangtong
		 * @throws IOException 
	     * @date 2012-10-13 下午3:47:18
	     * @see
	     */
		public static void writeFile(String content, File file) throws IOException{
			try {
				isExit();
				in = new ObjectInputStream(
						new FileInputStream("prikey.dat"));
				PrivateKey myprikey = (PrivateKey) in.readObject();
				 
				// 用私钥对信息生成数字签名
				java.security.Signature signet = java.security.Signature
						.getInstance("DSA");
				signet.initSign(myprikey);
				signet.update(content.getBytes());
				byte[] signed = signet.sign(); // 对信息的数字签名
				LOGGER.info("signed(签名内容)=" + byte2hex(signed));
				// 把信息和数字签名保存在一个文件中
				out = new ObjectOutputStream(
						new FileOutputStream(file));
				out.writeObject(content);
				out.writeObject(signed);
				//输出流的时候如果没有flush，会导致签名可能失败
				out.flush();
				LOGGER.info("签名并生成文件成功");
			} catch (java.lang.Exception e) {
				LOGGER.error("签名并生成文件失败",e);
			}finally{
				in.close();
				out.close();
			}
		}
 
		/**
	     * 第三步
	     * 其他人通过公共方式得到此户的公钥和文件
	     * 其他人用此户的公钥,对文件进行检查,如果成功说明是此用户发布的信息.
	     * @author 105089-foss-yangtong
	     * @date 2012-10-13 下午3:47:18
	     * @see
	     */
		public static String verify(File file) throws IOException{
			String info = ""; 
			try {
				isExit();
				in = new ObjectInputStream(
						new FileInputStream("pubkey.dat"));
				PublicKey pubkey = (PublicKey) in.readObject();
				 
				LOGGER.info(pubkey.getFormat());
				in = new java.io.ObjectInputStream(new FileInputStream(
						file));
				info = (String) in.readObject();
				byte[] signed = (byte[]) in.readObject();
				 
				java.security.Signature signetcheck = java.security.Signature
						.getInstance("DSA");
				signetcheck.initVerify(pubkey);
				signetcheck.update(info.getBytes());
				if (signetcheck.verify(signed)) {
					LOGGER.info("info=" + info);
					LOGGER.info("签名正常");
				} else{
					LOGGER.info("非签名正常");
				}
			} catch (java.lang.Exception e) {
				LOGGER.error("签名验证失败",e);
			}finally{
				in.close();
			}
			return info;
	 }


	/**
     * 生成一对文件myprikey.dat和mypubkey.dat---私钥和公钥,
     * 公钥要用户发送(文件,网络等方法)给其它用户,私钥保存在本地
     * @author 105089-foss-yangtong
     * @date 2012-10-13 下午3:47:18
     * @see
     */
	public static boolean generatekey() throws IOException {
		try {
			java.security.KeyPairGenerator keygen = java.security.KeyPairGenerator
					.getInstance("DSA");
			
			keygen.initialize(NUM_512);
			KeyPair keys = keygen.genKeyPair();
			
			PublicKey pubkey = keys.getPublic();
			PrivateKey prikey = keys.getPrivate();
			out = new ObjectOutputStream(
					new FileOutputStream("prikey.dat"));
			out.writeObject(prikey);
			//输出流的时候如果没有flush，会导致签名可能失败
			out.flush();
			
			LOGGER.info("写入对象 prikeys ok");
			out = new ObjectOutputStream(new FileOutputStream(
					"pubkey.dat"));
			out.writeObject(pubkey);
			//输出流的时候如果没有flush，会导致签名可能失败
			out.flush();
			LOGGER.info("写入对象 pubkeys ok");
			LOGGER.info("生成密钥对成功");
			return true;
		} catch (java.lang.Exception e) {
			LOGGER.error("生成密钥对失败");
			return false;
		}finally{
			out.close();
		}
	}
	
	/** 
	 * 将一个字节转化成十六进制形式的字符串
	 * * @author 105089-foss-yangtong
     * @date 2012-10-13 下午3:47:18
     * @see   
    */
	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1){
				hs = hs.append( "0" + stmp)  ;
			}else{
				hs = hs.append(stmp);
			}	
			if (n < b.length - 1)
				hs = hs.append(":");
		}
		return hs.toString().toUpperCase();
	}
     
}