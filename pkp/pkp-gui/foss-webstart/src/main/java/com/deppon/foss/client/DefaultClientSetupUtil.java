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
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/client/DefaultClientSetupUtil.java
 * 
 * FILE NAME        	: DefaultClientSetupUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class DefaultClientSetupUtil {
	
	/**
	 * 
	 */
	private static final String C_FOSS_DOWN_LOAD_RES = "/C:\\FOSSDownLoadRes\\";
	private static DefaultClientSetupUtil instance;
	private static final int NUM_10 = 10 ;
	private static final int NUM_100 = 100 ;
	private static final int NUM_3 = 3 ;
	private Properties setDefProperties = null;
	private static final String key_foss_client_default_file_name = "foss.client.default.properties";
	public static final String key_ftp_server = "ftp.server";
	public static final String key_ftp_port = "ftp.port";
	public static final String key_ftp_username = "ftp.username";
	public static final String key_ftp_password = "ftp.password";
	public static final String key_ftp_mode = "ftp.mode";
	
	public static final String key_hessian_server_port="server-port";
	public static final String key_hessian_service_path="service-path";
	public static final String key_hessian_service_host="service-host";
	public static final String key_hessian_service_waittimeout="service-waittimeout";
	public static final String key_hessian_connection_waittimeout="connection-waittimeout";
	
	public static final String key_download_server = "download.server";
	public static final String key_download_type = "download.type";
	
	private DefaultClientSetupUtil() throws Exception{ initSetDefaultValues(); }
	
	public static DefaultClientSetupUtil getInstance() throws Exception{
		if(instance==null){
			instance = new DefaultClientSetupUtil();
		}
		return instance;
	}

	private void initSetDefaultValues() throws Exception {
		setDefProperties = new Properties();
		
		mkRootDir();
		
		String path =C_FOSS_DOWN_LOAD_RES + key_foss_client_default_file_name;
		File f = new File(path);
		if(f.exists()){
			setDefProperties.load(new InputStreamReader(new FileInputStream(f),"UTF-8"));
		}
		else {
			f.createNewFile();
		}
	}

	private void mkRootDir() {
		File fDir = new File(C_FOSS_DOWN_LOAD_RES);
		
		if(!fDir.exists()){
			fDir.mkdir();
		}
	}
	
	public void storeSetDefaultValuesToFile() throws Exception {
		
		mkRootDir();
		
		String path =C_FOSS_DOWN_LOAD_RES + key_foss_client_default_file_name;
		File f = new File(path);
		FileOutputStream fo = new FileOutputStream(f);
		setDefProperties.store(fo, "#foss client default setup ");
		fo.close();
	}
	
	public void clearSetDefaultValues() throws Exception {
		String path = C_FOSS_DOWN_LOAD_RES + key_foss_client_default_file_name;
		File f = new File(path);
		FileOutputStream fo = new FileOutputStream(f);
		if(setDefProperties!=null){
			setDefProperties.clear();
		}
		else {
			setDefProperties = new Properties();
		}
		setDefProperties.store(fo, "#foss client default setup ");
		fo.close();
	}
	
	public void doSetDefaultValue(String pDefaultKey, String pDefaultValue) throws Exception {
		if(pDefaultValue==null){
			removeSetDefaultValue(pDefaultKey);
		}else {
			
			if(pDefaultKey!=null && pDefaultKey.equals(key_ftp_password)){
				//加密
				setDefProperties.put(pDefaultKey, setEncrypt(pDefaultValue));
			}else{
				setDefProperties.put(pDefaultKey, pDefaultValue);
			}
			
		}
		storeSetDefaultValuesToFile();
	}
	
	
	public static String setEncrypt(String str){
        String sn="ziyu"; //密钥
        int[] snNum=new int[str.length()];
        StringBuilder result = new StringBuilder("");
        String temp="";

        for(int i=0,j=0;i<str.length();i++,j++){
            if(j==sn.length())
                j=0;
            snNum[i]=str.charAt(i)^sn.charAt(j);
        }

        for(int k=0;k<str.length();k++){

            if(snNum[k]<NUM_10){
                temp="00"+snNum[k];
            }else{
                if(snNum[k]<NUM_100){
                    temp="0"+snNum[k];
                }
            }
            result.append(temp);
        }
        return result.toString();
    }

    /**
     * 密码解密,虽然用不到
     * @return <code>String[]</code> 加密后字符串
     * @author Administrator
     * @since 1.0 2005/11/28
     */
    public static String getEncrypt(String str){
        String sn="ziyu"; //密钥
        char[] snNum=new char[str.length()/NUM_3];
        StringBuilder result = new StringBuilder("");

        for(int i=0,j=0;i<str.length()/NUM_3;i++,j++){
            if(j==sn.length())
                j=0;
            int n=Integer.parseInt(str.substring(i*NUM_3,i*NUM_3+NUM_3));
            snNum[i]=(char)((char)n^sn.charAt(j));
        }

        for(int k=0;k<str.length()/NUM_3;k++){
            result.append(snNum[k]);
        }
        return result.toString();
    }

    
    public static void main(String[] args){
    	
    	String name = "foss*ftp";
    	String name2 = setEncrypt(name);
     	String name3 = getEncrypt(name2);
    	System.out.println(name);
    	System.out.println(name2);
    	System.out.println(name3);
    }
	
	
	public String loadSetDefaultValue(String pDefaultKey){
		return (String)setDefProperties.get(pDefaultKey);
	}
	
	public void removeSetDefaultValue(String pDefaultKey){
		setDefProperties.remove(pDefaultKey);
	}
}