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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/JsTest.java
 * 
 * FILE NAME        	: JsTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.deppon.foss.framework.shared.util.string.StringUtil;

public class JsTest {

	/**
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-2-19
	 * @param args void
	 */
	public static void main(String[] args) {
//		JsTest.readFileByLines("D:/work/FOSS/04 Foss_code/trunk/foss/pkp/pkp-waybill/src/main/resources/com/deppon/foss/module/pickup/waybill/server/META-INF/scripts/waybillRfcAccount.js");
//		JsTest.readFileByLines("D:/work/FOSS/04 Foss_code/trunk/foss/pkp/pkp-waybill/src/main/resources/com/deppon/foss/module/pickup/waybill/server/META-INF/scripts/adjustPlan.js");
		JsTest.readFileByLines("D:/work/FOSS/04 Foss_code/trunk/foss/pkp/pkp-pickup/src/main/resources/com/deppon/foss/module/pickup/pickup/server/META-INF/scripts/queryTrackingWaybill.js");
		}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 * 
	 * @param fileName 文件名
	 */
	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			String[] newString;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			Map<Integer, String> map = new HashMap<Integer, String>();
			// 写文件
//			FileWriter fw = new FileWriter("D:/work/FOSS/04 Foss_code/trunk/foss/pkp/pkp-sign/src/test/java/com/deppon/foss/module/waybillRfcAccount.js");
//			FileWriter fw = new FileWriter("D:/work/FOSS/04 Foss_code/trunk/foss/pkp/pkp-sign/src/test/java/com/deppon/foss/module/adjustPlan.js");
			FileWriter fw = new FileWriter("D:/work/FOSS/04 Foss_code/trunk/foss/pkp/pkp-sign/src/test/java/com/deppon/foss/module/queryTrackingWaybill.js");
			// 写入中文字符时会出现乱码
			BufferedWriter bw = new BufferedWriter(fw);

			while ((tempString = reader.readLine()) != null) {

				/* while(tempString.startsWith(" ")){ tempString =
				 * tempString.substring(1,tempString.length()).trim(); } */
				if (tempString.indexOf("fieldLabel:") > 0 || 
						tempString.indexOf("title:") > 0 || 
						tempString.indexOf("text:") > 0 || 
						tempString.indexOf("regexText:") > 0 || 
						tempString.indexOf("header:") > 0 || 
						tempString.indexOf("tooltip:") > 0 || 
						tempString.indexOf("emptyText:") > 0 || 
						tempString.indexOf("fieldLabel :") > 0 || 
						tempString.indexOf("title :") > 0 || 
						tempString.indexOf("text :") > 0 || 
						tempString.indexOf("regexText :") > 0 || 
						tempString.indexOf("header :") > 0 || 
						tempString.indexOf("tooltip :") > 0 || 
						tempString.indexOf("emptyText :") > 0) {
					newString = tempString.split("'");
					if (newString.length < 2) {
						continue;
					}
					if (StringUtil.isEmpty(newString[1])) {
						continue;
					}
					map.put(line, newString[1]);

				}
				line++;
				bw.write(tempString + "\t\n");
			}
			reader.close();
			bw.close();
			// 过滤相同的文字
			Set<String> set = new HashSet<String>();
			// 获取所有的文字
			for (Map.Entry<Integer, String> a : map.entrySet()) {
				System.out.println(a.getKey() + ":" + a.getValue());
				set.add(a.getValue());
			}
            System.out.println("---唯一的文字---");
            int k=0;
            Map<String,String> searchMap = kao();
            for (String string : set) {
//            	System.out.println("pkp.sign.applicationSignRfc."+k+++"="+string);
				System.out.println("pkp.sign.auditSignRfc."+k+++"="+string);
//				System.out.println("pkp.sign.reverseSignRfc."+searchMap.get(string)+"="+string);
//				System.out.println("pkp.predeliver.processAbandonGoods."+k+++"="+string);
//				System.out.println("pkp.predeliver.arriveSheet."+k+++"="+string);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	private static Map<String,String> kao(){
		String filePath = "D:/DPAP/DPAPPoject/2013eclipse/FOSS_Code/foss/pkp/pkp-sign/src/test/java/com/deppon/foss/module/key.txt";
		Map<String,String> map = new HashMap<String,String>();
		BufferedReader br = null;
		String tempString = null;
		String[] newString;
		String keyString = null;
		
		String[] valueString;
		try {
			// 根据文件路径创建缓冲输入流
			br = new BufferedReader(new FileReader(filePath));
			// 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
			while ((tempString = br.readLine()) != null) {
				newString = tempString.split("=");
				if(newString.length>1){
					keyString = newString[0];
					valueString = keyString.split("\\.");
					map.put(newString[1], valueString[3]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
				}
			}
		}
		return map;
	}
}