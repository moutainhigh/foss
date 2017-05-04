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
 * PROJECT NAME	: pkp-gui
 * 
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/webstart/ConfigurationReader.java
 * 
 * FILE NAME        	: ConfigurationReader.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.webstart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationReader {
	private transient Map configuration = new HashMap();

	public Map getConfiguration(){
		return this.configuration;
	}

	public ConfigurationReader(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		read(reader);
		reader.close();
	}

	protected void read(BufferedReader reader) throws IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			parseLine(line);
		}
	}

	protected void parseLine(String line) {
		String newline = line.trim();
		if (newline.indexOf('=') >= 0) {
			int i = newline.indexOf('=');
			String name = newline.substring(0, i).trim();
			String value = newline.substring(i + 1).trim();
			configuration.put(name, value);
		}
	}

	/**
	 * 读取相关配置
	 * @param section段落
	 * @param name键值
	 * @return
	 */
	public String getValue(String name) {
		if (configuration == null) {
			return null;
		}
		String value = (String) configuration.get(name.trim());
		return value;
	}
}