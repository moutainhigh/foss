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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/FileModify.java
 * 
 * FILE NAME        	: FileModify.java
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * 修改文件
 */
public class FileModify {

	/**
	 * 读取文件内容
	 * 
	 * @param filePath
	 * @return
	 */
	public String read(String filePath, String i18n, String prepertiesPath) {
		BufferedReader br = null;
		String line = null;
		StringBuffer buf = new StringBuffer();
		int lineNo = 1;
		// 需要替换的行数的MAP
		Map<Integer, String> map = getOptionLine(filePath);
		// 被国际化的文件MAP
		Map<String, String> gjhmap = getGjhMap(prepertiesPath);
		try {
			// 根据文件路径创建缓冲输入流
			br = new BufferedReader(new FileReader(filePath));
			String newString = null;
			// 被替换的文字
			String reString = null;
            //国际化头
            String headString = null;
			// 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
			while ((line = br.readLine()) != null) {
				// 改行是否在替换列
				if (map.get(lineNo) != null) {
					reString = map.get(lineNo);
					System.out.println("lineNo == " + lineNo);
					newString = line.replace("'" + reString + "'", i18n + "('" + gjhmap.get(reString) + "')");
					buf.append(newString).append(" // ").append(reString);
				} else {
					buf.append(line);
				}
				buf.append(System.getProperty("line.separator"));
				lineNo++;
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

		return buf.toString();
	}

	/**
	 * 
	 * <p>
	 * 需要修改的行数和文字<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-2-19
	 * @param filePath
	 * @return Map<Integer,String>
	 */
	public Map<Integer, String> getOptionLine(String filePath) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		BufferedReader br = null;
		String tempString = null;
		String[] newString;
		int line = 1;
		try {
			// 根据文件路径创建缓冲输入流
			br = new BufferedReader(new FileReader(filePath));
			// 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
			while ((tempString = br.readLine()) != null) {
				if (tempString.indexOf("fieldLabel:") > 0 || tempString.indexOf("title:") > 0 || tempString.indexOf("text:") > 0 || tempString.indexOf("regexText:") > 0 || tempString.indexOf("header:") > 0 || tempString.indexOf("tooltip:") > 0
						|| tempString.indexOf("emptyText:") > 0 || tempString.indexOf("fieldLabel :") > 0 || tempString.indexOf("title :") > 0 || tempString.indexOf("text :") > 0 || tempString.indexOf("regexText :") > 0
						|| tempString.indexOf("header :") > 0 || tempString.indexOf("tooltip :") > 0 || tempString.indexOf("emptyText :") > 0) {
					map.put(line, tempString.split("'")[1]);
				}
				line++;
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

	public Map<String, String> getGjhMap(String prepertiesPath) {
		Map<String, String> gjhmap = new HashMap<String, String>();
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(prepertiesPath));
			// 返回Properties中包含的key-value的Set视图
			Set<Entry<Object, Object>> set = props.entrySet();
			// 返回在此Set中的元素上进行迭代的迭代器
			Iterator<Map.Entry<Object, Object>> it = set.iterator();
			String key = null, value = null;
			// 循环取出key-value
			while (it.hasNext()) {

				Entry<Object, Object> entry = it.next();

				key = String.valueOf(entry.getKey());
				value = String.valueOf(entry.getValue());
				gjhmap.put(value, key);
				/* key = key == null ? key : key.trim().toUpperCase(); value =
				 * value == null ? value : value.trim().toUpperCase(); //
				 * 将key-value放入map中 Constants.loadSqlMap.put(key, value); */
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gjhmap;
	}

	/**
	 * 将内容回写到文件中
	 * 
	 * @param filePath
	 * @param content
	 */
	public void write(String filePath, String content) {
		BufferedWriter bw = null;

		try {
			// 根据文件路径创建缓冲输出流
			bw = new BufferedWriter(new FileWriter(filePath));
			// 将内容写入文件中
			bw.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					bw = null;
				}
			}
		}
	}

	/**
	 * 主方法
	 */
	public static void main(String[] args) {
		//跟踪运单
//		String filePath = "C:/Users/IBM_ADMIN/Desktop/i18n/queryTrackingWaybill.js"; // 文件路径
//		String prepertiesPath = "D:/work/FOSS/04 Foss_code/trunk/foss/pkp/pkp-pickup/src/main/resources/com/deppon/foss/module/pickup/pickup/server/META-INF/messages/message_zh_CN.properties";
//		String il8n = "pkp.queryTrackingWaybill.i18n";
		//手工
//		String filePath = "C:/Users/IBM_ADMIN/Desktop/i18n/adjustPlan.js"; // 文件路径
//		String prepertiesPath = "D:/work/FOSS/04 Foss_code/trunk/foss/pkp/pkp-waybill/src/main/resources/com/deppon/foss/module/pickup/waybill/server/META-INF/messages/sg.message_zh_CN.properties";
//		String il8n = "pkp.waybill.i18n";
		//申请
		String filePath = "C:/Users/IBM_ADMIN/Desktop/i18n/waybillRfcAccount.js"; // 文件路径
		String prepertiesPath = "D:/work/FOSS/04 Foss_code/trunk/foss/pkp/pkp-waybill/src/main/resources/com/deppon/foss/module/pickup/waybill/server/META-INF/messages/sq.message_zh_CN.properties";
		String il8n = "pkp.waybillRfcAccount.i18n";
		FileModify obj = new FileModify();
		obj.write(filePath, obj.read(filePath, il8n, prepertiesPath)); // 读取修改文件
	}

}