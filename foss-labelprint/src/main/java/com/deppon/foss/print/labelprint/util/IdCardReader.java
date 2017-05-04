package com.deppon.foss.print.labelprint.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.idCard.info.IdCardInfo;
import com.idCard.info.Termb;


/**
 * 身份证读取器
 * @author 038590-foss-wanghui
 * @date 2013-6-28 上午11:02:21
 */
public class IdCardReader {
	
	private static final String PATH1 = "bin\\wz.txt";
	
	private static final String PATH2 = "./wz.txt";
	
	/**
	 * 
	 * 调用阅读器
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-28 上午11:05:40
	 */
	public void callReader(){
		Termb.CloseComm();
		Termb.InitComm(1001);
		Termb.Authenticate();
		Termb.ReadContent(1);
		Termb.CloseComm();
	}
	
	/**
	 * 
	 * 获取身份证信息
	 * 
	 * @author 038590-foss-wanghui
	 * @throws UnsupportedEncodingException 
	 * @date 2013-6-28 上午11:06:51
	 */
	public IdCardInfo getIdCardInfo() throws UnsupportedEncodingException{
		this.callReader();
		IdCardInfo idCardInfo = new IdCardInfo();
		String strIdCardInfo = this.readCardInfo();
		Log.info(strIdCardInfo);
		if (null != strIdCardInfo) {
			if (!"null".equals(strIdCardInfo)) {
				// 姓名
				idCardInfo.setName(strIdCardInfo.substring(0, 15).trim());
				// 性别1男2女
				idCardInfo.setSex(strIdCardInfo.substring(15, 16).trim());
				// 民族
				idCardInfo.setNation(strIdCardInfo.substring(16, 18).trim());
				// 出生年月
				idCardInfo.setBirthday(strIdCardInfo.substring(18, 26).trim());
				// 住址
				idCardInfo.setAddress(strIdCardInfo.substring(26, 61).trim());
				Log.info("--------------");
				// 身份证号
				idCardInfo.setIdNo(strIdCardInfo.substring(61, 79).trim());
				// 签发机关
				idCardInfo.setIssuingAuthority(strIdCardInfo.substring(79, 94).trim());
				// 有效期起始日期
				idCardInfo.setBeginTime(strIdCardInfo.substring(94, 102).trim());
				// 有效期截止日期
				idCardInfo.setEndTime(strIdCardInfo.substring(102, 110).trim());
				return idCardInfo;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	private String readCardInfo() {
		String path = System.getProperty("java.home");
		String filePath = path.substring(0, path.length() - 3);
		String str = "";

		File file = new File(filePath + PATH1);
		File file2 = new File(PATH2);
		boolean exist = file.exists();
		if (exist == true) {
			FileInputStream fileInput = null;
			DataOutputStream outPut = null;
			InputStreamReader inputRead = null;
			BufferedReader bufferRead = null;
			try {
				fileInput = new FileInputStream(PATH1);
				inputRead = new InputStreamReader(fileInput, "utf-16le");
				bufferRead = new BufferedReader(inputRead);
				str = bufferRead.readLine();
				bufferRead.close();
				outPut = new DataOutputStream(new FileOutputStream(filePath
						+ PATH1));
				outPut.write("null".getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fileInput.close();
				} catch (Exception e) {
				}
				try {
					inputRead.close();
				} catch (Exception e) {
				}
				try {
					bufferRead.close();
				} catch (Exception e) {
				}
				try {
					outPut.close();
				} catch (Exception e) {
				}
			}
			return str;
		} else if(file2.exists()) {
			FileInputStream fileInput = null;
			DataOutputStream outPut = null;
			InputStreamReader inputRead = null;
			BufferedReader bufferRead = null;
			try {
				fileInput = new FileInputStream(PATH2);
				inputRead = new InputStreamReader(fileInput, "utf-16le");
				bufferRead = new BufferedReader(inputRead);
				str = bufferRead.readLine();
				bufferRead.close();
				outPut = new DataOutputStream(new FileOutputStream(PATH2));
				outPut.write("null".getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fileInput.close();
				} catch (Exception e) {
				}
				try {
					inputRead.close();
				} catch (Exception e) {
				}
				try {
					bufferRead.close();
				} catch (Exception e) {
				}
				try {
					outPut.close();
				} catch (Exception e) {
				}
			}
			return str;
		} else {
			return null;
		}
	}
}
