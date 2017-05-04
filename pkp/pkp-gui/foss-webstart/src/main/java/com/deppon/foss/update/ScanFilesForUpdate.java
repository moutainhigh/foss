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
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/update/ScanFilesForUpdate.java
 * 
 * FILE NAME        	: ScanFilesForUpdate.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.update;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.codec.digest.DigestUtils;


public class ScanFilesForUpdate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String basepath = "D:\\dpwork_local\\foss_trunk\\pkp\\pkp-gui\\appHome";
			String excludeItems = ",update,logs,print,src,.settings,.classpath,.project,pom,bin,";
			//String forceItems = "*.jar,";
				
			StringBuffer out = new StringBuffer();
			File pf = new File(basepath);
			ScanFilesForUpdate update = new ScanFilesForUpdate();
			update.scan(pf,out,"",excludeItems);
			System.out.println(out.toString());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void scan(File pf,StringBuffer pout,String parentfolder,String excludeItems) throws Exception {
		
		File[] files = pf.listFiles();
		for(File file : files){
			String fname = file.getName();
			if(excludeItems.indexOf(","+fname+",")==-1){
				if(file.isFile()){
					String str = "";
					String str1 = "";
					if(parentfolder.indexOf("lib")!=-1 || parentfolder.indexOf("plugins")!=-1 || parentfolder.indexOf("script")!=-1){
						str = parentfolder+"/"+fname+"=F";
					}
					else {
						str = parentfolder+"/"+fname+"=1";
					}
					if(str.startsWith("/")){
						str = str.substring(1);
					}
					str1 = parentfolder+"/"+fname;
					if(str1.startsWith("/")){
						str1 = str1.substring(1);
					}
					pout.append(str1 + "=" + DigestUtils.md5Hex(new FileInputStream(file))+"\n");
				}
				else if(file.isDirectory()){
					String folder = file.getName();
					scan(file, pout, parentfolder+"/"+folder ,excludeItems);
				}	
			}
		}
	}
}