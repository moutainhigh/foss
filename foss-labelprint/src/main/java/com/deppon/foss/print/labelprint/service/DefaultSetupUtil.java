package com.deppon.foss.print.labelprint.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class DefaultSetupUtil {
	
	private static DefaultSetupUtil instance;
	private Properties setDefProperties = null;
	private DefaultSetupUtil() throws Exception{ initSetDefaultValues(); }
	
	public static DefaultSetupUtil getInstance() throws Exception{
		if(instance==null){
			instance = new DefaultSetupUtil();
		}
		return instance;
	}

	private void initSetDefaultValues() throws Exception {
		setDefProperties = new Properties();
		
		String path = System.getProperty("user.home") + File.separator + LblPrtServiceConst.key_set_default_file;
		File f = new File(path);
		if(f.exists()){
			setDefProperties.load(new InputStreamReader(new FileInputStream(f),"UTF-8"));
		}
		else {
			f.createNewFile();
		}
	}
	
	public void storeSetDefaultValuesToFile() throws Exception {
		String path = System.getProperty("user.home") + File.separator + LblPrtServiceConst.key_set_default_file;
		File f = new File(path);
		FileOutputStream fo = new FileOutputStream(f);
		setDefProperties.save(fo, "#foss lable print ");
		fo.close();
	}
	
	public void doSetDefaultValue(String pDefaultKey, String pDefaultValue) throws Exception {
		if(pDefaultValue==null){
			removeSetDefaultValue(pDefaultKey);
		}
		else {
			setDefProperties.put(pDefaultKey, pDefaultValue);
		}
		storeSetDefaultValuesToFile();
	}
	
	public String loadSetDefaultValue(String pDefaultKey){
		return (String)setDefProperties.get(pDefaultKey);
	}
	
	public void removeSetDefaultValue(String pDefaultKey){
		setDefProperties.remove(pDefaultKey);
	}
}
