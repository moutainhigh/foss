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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/predeliver/server/util/FileTest.java
 * 
 * FILE NAME        	: FileTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class FileTest {
	public static void main(String[] args) {

		try {
			List<String> l = new ArrayList<String>();
			// 建立FileReader对象，并实例化为fr
			FileReader fr = new FileReader("C:\\Users\\IBM_ADMIN\\Desktop\\12321.txt");
			BufferedReader br = new BufferedReader(fr);// 建立BufferedReader对象，并实例化为br
			String line = br.readLine();// 从文件读取一行字符串
			// 判断读取到的字符串是否不为空

			while (line != null) {
				l.add(line);
				line = br.readLine();// 从文件中继续读取一行数据
			}
			br.close();// 关闭BufferedReader对象
			fr.close();// 关闭文件
			FileWriter fw = new FileWriter("C:\\Users\\IBM_ADMIN\\Desktop\\abcd1.txt");// 建立FileWriter对象，并实例化fw
			BufferedWriter bw = new BufferedWriter(fw);
			for (String s : l) {
				String[] ss = s.split("\\.");
				StringBuffer sb = new StringBuffer();
				if (ss.length == 0) {
					sb.append(StringUtils.capitalize(s)).append(" ");
					;
				} else {
					for (int i = 0; i < ss.length; i++) {
						sb.append(StringUtils.capitalize(ss[i])).append(" ");
					}
				}
				bw.write(sb.substring(0, sb.toString().length() - 1));
				bw.newLine();
			}
			bw.flush();
			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}