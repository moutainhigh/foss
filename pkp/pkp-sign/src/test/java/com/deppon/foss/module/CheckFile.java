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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/CheckFile.java
 * 
 * FILE NAME        	: CheckFile.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class CheckFile {

	/**
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-2-21
	 * @param args
	 * void
	 */
	public static void main(String[] args) {

		String pathName = "D:/DPAP/DPAPPoject/2013eclipse/FOSS_Code/foss/pkp/pkp-sign/src/test/java/com/deppon/foss/module/gjh.properties";
		CheckFile c = new CheckFile();
		c.kao(pathName);
	}

	private void kao(String filePath){
		Map<Integer,String> map = new HashMap<Integer,String>();
		BufferedReader br = null;
		String tempString = null;
		String[] newString;
		int line = 1;
		List<String> list = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		try {
			// 根据文件路径创建缓冲输入流
			br = new BufferedReader(new FileReader(filePath));
			// 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
			while ((tempString = br.readLine()) != null) {
				newString = tempString.split("=");
				if(list.contains(newString[0])){
					System.out.println("----"+newString[0]);
				}
				list.add(newString[0]);
				line++;	
			}
			Collections.sort(list);
			for (String string : list) {
				System.out.println(string);
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
	}
}