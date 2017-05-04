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
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/update/ScanFilesToMd5HexDat.java
 * 
 * FILE NAME        	: ScanFilesToMd5HexDat.java
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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

//import org.apache.commons.lang.StringUtils;

import com.deppon.foss.util.ClassPathResourceUtil;
import com.deppon.foss.util.Md5;


public class ScanFilesToMd5HexDat {
	
	private static Map forceMap = new HashMap();
	private static Map noUpdateMap = new HashMap();
	private static Map dateUpdateMap = new HashMap();
	public static List<String> excludefileNameList;
	public static void main(String[] args) {
		try {
			
//			forceMap.put("foss-config.jar", "foss-config.jar");
			
//			dateUpdateMap.put("city.csv", "city.csv");
//			dateUpdateMap.put("county.csv", "county.csv");
//			dateUpdateMap.put("foss.data", "foss.data");
//			dateUpdateMap.put("foss.lck", "foss.lck");
//			dateUpdateMap.put("foss.log", "foss.log");
//			dateUpdateMap.put("foss.properties", "foss.properties");
//			dateUpdateMap.put("foss.script", "foss.script");
//			dateUpdateMap.put("province.csv", "province.csv");
//			dateUpdateMap.put("serivcefee.properties", "serivcefee.properties");
//			dateUpdateMap.put("foss.script", "foss.script");
//			dateUpdateMap.put("syncdata.cvs", "syncdata.cvs");
//			dateUpdateMap.put("foss.lobs", "foss.lobs");
			
			
			
			noUpdateMap.put("PKP.T_SRV_ACTUAL_FREIGHT", "PKP.T_SRV_ACTUAL_FREIGHT");
			noUpdateMap.put("PKP.T_SRV_PAYMENT_PG", "PKP.T_SRV_PAYMENT_PG");
			noUpdateMap.put("PKP.T_SRV_PAYMENT", "PKP.T_SRV_PAYMENT");
			noUpdateMap.put("PKP.T_SRV_WAYBILL_CH_DETAIL_PG", "PKP.T_SRV_WAYBILL_CH_DETAIL_PG");
			noUpdateMap.put("PKP.T_SRV_WAYBILL_CHARGE_DETAIL", "PKP.T_SRV_WAYBILL_CHARGE_DETAIL");
			noUpdateMap.put("PKP.T_SRV_WAYBILL_DIS_DETAIL_PG", "PKP.T_SRV_WAYBILL_DIS_DETAIL_PG");
			noUpdateMap.put("PKP.T_SRV_WAYBILL_DIS_DETAIL", "PKP.T_SRV_WAYBILL_DIS_DETAIL");
			noUpdateMap.put("PKP.T_SRV_WAYBILL_OFFLINE", "PKP.T_SRV_WAYBILL_OFFLINE");
			noUpdateMap.put("PKP.T_SRV_WAYBILL_PENDING", "PKP.T_SRV_WAYBILL_PENDING");
			noUpdateMap.put("PKP.T_SRV_WAYBILL", "PKP.T_SRV_WAYBILL");
			noUpdateMap.put("PKP.T_SRV_WOODEN_REQUIREMENTS_PG", "PKP.T_SRV_WOODEN_REQUIREMENTS_PG");
			noUpdateMap.put("PKP.T_SRV_WOODEN_REQUIREMENTS", "PKP.T_SRV_WOODEN_REQUIREMENTS");
			noUpdateMap.put("PKP.T_SRV_WOODEN_REQUIREMENTS", "PKP.T_SRV_WOODEN_REQUIREMENTS");
			noUpdateMap.put("succeed.log", "succeed.log");
			
			
			String excludeItems = ",update,logs,print,src,.settings,.classpath,.project,pom,bin,";
			String basepath = args[1]; //"D:\\workspace\\foss_code\\pkp\\pkp-gui\\foss-webstart\\appHome"; // //// 
			String datfile =args[2];// "D:\\workspace\\foss_code\\pkp\\pkp-gui\\foss-webstart\\appHome\\md5hex2.dat"; //  // // args[2];
			
			String excludefile  = "djnativeswing.jar,bcmail-jdk14.jar,bcprov-jdk14.jar,bctsp-jdk14.jar,"+
		 	"castor.jar,xml-apis.jar,xml-resolver.jar,xmlschema-core.jar,"+
		 	"jna_WindowUtils.jar,jna.jar,itext.jar,bse-baseinfo.jar,"+
		 	"bse-common.jar,commonservices.jar,cxf-common-utilities.jar,cxf-rt-bindings-soap.jar,"+
		 	"cxf-rt-bindings-xml.jar,cxf-rt-core.jar,cxf-rt-databinding-jaxb.jar,cxf-rt-frontend-jaxws.jar,"+
		 	"cxf-rt-frontend-simple.jar,cxf-rt-transports-common.jar,cxf-rt-transports-http.jar,cxf-rt-ws-addr.jar,"+
		 	"cxf-tools-common.jar,dhbcore.jar,esb-extends-api.jar,esb-extends-config.jar,"+
		 	"fscontext.jar,geronimo-javamail_1.4_spec.jar,jaxb-impl.jar,jfreechart.jar,jmqi.jar,"+
		 	"jms.jar,jdtcore.jar,jta.jar,junit.jar,mq.jar,mqjms.jar,oracle-jdbc.jar,pcf.jar,"+
		 	"providerutil.jar,spring-aop.jar,spring-core.jar,spring-web.jar,swt.jar,woodstox-core-asl.jar,wsdl4j.jar,ojdbc.jar,backup" ;  //args[3];
			String[] excludefileName = excludefile.split(",");
			excludefileNameList =  new ArrayList<String>(Arrays.asList(excludefileName));
			
			
			StringBuffer out = new StringBuffer();
			File pf = new File(basepath);
			ScanFilesToMd5HexDat update = new ScanFilesToMd5HexDat();
			update.scan(pf,out,"",excludeItems);
			//System.out.println(out.toString());
			File outfile = new File(datfile);
			FileWriter fw = new FileWriter(outfile);
			fw.write(out.toString());
			fw.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String parseMd5Hex(FileInputStream fin ) throws IOException {
		Md5 md5 = new Md5 (fin);
		byte b[]= md5.getDigest();
		return md5.parseToValue(b);
	}

	private long getFileDate(File file  ) throws IOException {
		Date date = null;
		try {
			String ftpConfPath = "com/deppon/foss/client/default/ftp.properties";
			ClassPathResourceUtil util = new ClassPathResourceUtil();
			InputStream in = util.getInputStream(ftpConfPath);
			Properties prop = new Properties();
			prop.load(in);
			
			String version = prop.getProperty("version_date", "2013-04-28 00:00");
			
			String dateStr = version;//日期需要后台统一指定 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
		
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			date = new Date();
		}
		return date.getTime();
	}

	public void scan(File pf,StringBuffer pout,String parentfolder,String excludeItems) throws Exception {
		
		File[] files = pf.listFiles();
		for(File file : files){
			String fname = file.getName();
			boolean exculdeIt = false;
			for(int i =0; i<excludefileNameList.size();i++){
				String excludeFileName = excludefileNameList.get(i);
				if(fname.indexOf(excludeFileName)!=-1){
					exculdeIt = true;
				}
			}
			
			if(exculdeIt){
				continue;
			}
			
			if(excludeItems.indexOf(","+fname+",")==-1){
				if(file.isFile()){
					
					String str = "";
					if(parentfolder.indexOf("lib")!=-1 || parentfolder.indexOf("plugins")!=-1 || parentfolder.indexOf("script")!=-1){
						str = parentfolder+"/"+fname+"=F";
					} else {
						str = parentfolder+"/"+fname+"=1";
					}
					if(str.startsWith("/")){
						str = str.substring(1);
					}
					
					String str1 = "";
					str1 = parentfolder+"/"+fname;
					if(str1.startsWith("/")){
						str1 = str1.substring(1);
					}
					if(forceMap.containsKey(fname)){
						pout.append(str1 + "=F\n");
					}else if (noUpdateMap.containsKey(fname)){
						pout.append(str1 + "=N\n");
					}else if (dateUpdateMap.containsKey(fname)){
						pout.append(str1 + "=D"+getFileDate(file)+"\n");
					}else {
						pout.append(str1 + "=" + parseMd5Hex(new FileInputStream(file))+"\n");
					}
				}else if(file.isDirectory()){
					String folder = file.getName();
					scan(file, pout, parentfolder+"/"+folder ,excludeItems);
				}	
			}
		}
	}
	
	
	public static void main2(String[] args) throws Exception{
		File file = new File("D:\\workspace\\foss_code\\pkp\\pkp-gui\\appHome\\script\\20130506_002_T_SRV_PRODUCT.script");
		ScanFilesToMd5HexDat d = new ScanFilesToMd5HexDat();
		String md5 = d.parseMd5Hex(new FileInputStream(file));
		System.out.println(md5);
		//37a0a9dabd35790f2282927689fdd1b3
	}
}