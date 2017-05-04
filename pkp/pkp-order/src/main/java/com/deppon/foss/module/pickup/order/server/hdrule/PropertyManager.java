package com.deppon.foss.module.pickup.order.server.hdrule;
/**
 * @author halley.w
 * @version 1.0
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


public class PropertyManager {
	
	private static final Logger log = Logger.getLogger(PropertyManager.class);
	
	private Properties property = new Properties();

	public static PropertyManager load(String[] configFileNames, Class loaderClass){
		return new PropertyManager(configFileNames, loaderClass);
	}

	/**
	 * constructor of PropertyManager which is used to create configure file.
	 */
	private PropertyManager(String[] configFileNames, Class loaderClass) {
		for (int i = 0; i < configFileNames.length; i++) {
			loadFile(configFileNames[i],loaderClass);
		}

	}

	/**
	 * Read configure file form specific file.
	 * 
	 * @param pFileName
	 */
	private void loadFile(String pFileName, Class loaderClass) {
		InputStream fin = null;
		try {
			fin = loaderClass.getResourceAsStream(pFileName);
			property.load(fin);
		} catch (IOException ex) {
		} finally {
			if(fin != null){
				try {
					fin.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * GET configure value by configure name
	 * 
	 * @param pItemName
	 * @return configure name item's value.
	 */
	public String getString(String pItemName) {
		return property.getProperty(pItemName);
	}
	
	public String getString(String pItemName,String... args) {
		String value = getString(pItemName);
		if(value!=null && args != null){
			for(int i=0;i<args.length;i++){
				value = value.replace("{"+i+"}", args[i]==null?"":args[i]);
			}
		}
		return value;
	}
	/**
	 * 
	 * @param pItemName
	 * @return
	 */
	public int getInt(String pItemName){
		String value = getString(pItemName);
		int intValue = 0;
		try{
			intValue = new Integer(value).intValue();
		}catch(NumberFormatException e){
		}
		return intValue;
	}
	
	public Properties getProperties(){
		return property;
	}
}
 