package com.deppon.pda.bdm.module.core.shared.util;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtils {
	/**
	 * 根据流数据输出文件,并且返回该文件的路径
	 * @param datas
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String writePicture(byte[] datas,String fileName) throws Exception {
		File f = new File("./picture/"+fileName);
		if(!f.getParentFile().exists()){
			f.getParentFile().mkdirs();
		}
		FileOutputStream output = new FileOutputStream(f);
		output.write(datas);
		output.flush();
		output.close();
		output = null;
		return f.getCanonicalPath();
	}
	
	/**
	 * 
	* @Description: TODO 创建不存在的路径
	* @param filePath
	* @return
	* @return boolean    
	* @author mt
	* @date 2013-8-23 上午10:44:49
	 */
	public static void mkdirs(String filePath){
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdirs();
		}
	}
}
