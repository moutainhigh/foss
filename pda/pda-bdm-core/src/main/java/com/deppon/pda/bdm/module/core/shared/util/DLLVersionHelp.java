package com.deppon.pda.bdm.module.core.shared.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
  * @ClassName DLLVersionHelp 
  * @Description TODO 获取DLL版本
  * @author mt 
  * @date 2013-8-22 上午9:11:46
 */
public class DLLVersionHelp {
	//DLL
	public static final int DLL=32;
	//INDEX
    public static final int INDEXNUM=5;
    //VERSIONINDXE
    public static final int VERSIONINDXE=6;
    
	public static String getVersion(File file){
		BufferedReader reader = null;
		String s = "F i l e V e r s i o n";
		String version = "";
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			byte[] b = s.getBytes();
			for (int i = 0; i < b.length; i++) {
				if(b[i] == DLL){
					b[i] = 0;
				}
			}
			String str = new String(b);
			String versionLine = "";
			while(reader.ready()){
				String line = reader.readLine();
				int index = line.indexOf(str);
				if(index > 0){
					int len = index + str.length() + INDEXNUM;
					versionLine = line.substring(len);
					break;
				}
			}
			version = getVersion(versionLine);
			return version;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return version;
	}
	
	/**
	 * 
	* @Description: TODO 获取版本
	* @param data
	* @param start
	* @param end
	* @return
	* @return int    
	* @author mt
	* @date 2013-8-22 上午9:11:59
	 */
	private static String getVersion(String versionLine){
		StringBuffer sb = new StringBuffer();
		int index1 = 0,index2 = 0,index3 = 0;
		
		index1 = versionLine.indexOf(".",0);
		index2 = versionLine.indexOf(".",index1+1);
		index3 = versionLine.indexOf(".",index2+1);
		sb.append(subStr(versionLine,0,index1)).append(".");
		sb.append(subStr(versionLine,index1+1,index2)).append(".");
		sb.append(subStr(versionLine,index2+1,index3)).append(".");
		sb.append(subStr(versionLine,index3+1,index3+VERSIONINDXE));
		return sb.toString();
	}

	/**
	 * 
	* @Description: TODO
	* @param str
	* @param start
	* @param end
	* @return
	* @return String    
	* @author mt
	* @date 2013-8-24 下午3:52:42
	 */
	private static String subStr(String str,int start,int end){
		String version = str.substring(start,end);
		version = version.replaceAll(new String(new byte[]{0}), "");
		return version;
	}
}
