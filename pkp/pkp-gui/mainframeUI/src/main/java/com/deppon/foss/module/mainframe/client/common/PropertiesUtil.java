package com.deppon.foss.module.mainframe.client.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读写properties配置文件工具类
 * 
 * @author 272311 sangwenhao 2015-11-24
 */
public class PropertiesUtil {

	private Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

	private static PropertiesUtil propertiesUtils = null;

	private PropertiesUtil() {
	}

	public synchronized static PropertiesUtil getPropertiesUtil() {
		if (propertiesUtils == null) {
			propertiesUtils = new PropertiesUtil();
		}
		return propertiesUtils;
	}

	/**
	 * 读取配置文件
	 * @param path 配置文件路径
	 * @param key key值
	 * @param defaultValue 默认值
	 * @return key对应的值
	 * @author 272311 sangwenhao
	 *  2015-11-24
	 */
	public String getProperties(String path, String key, String defaultValue) {
		String value = defaultValue;
		FileInputStream is = null;
		try {
			// 关闭日志
			Properties properties = new Properties();
			// 文件输出流
			is = new FileInputStream(path);
			properties.load(is);
			is.close();
			value = properties.getProperty(key, defaultValue);
		} catch (Exception e2) {
			logger.error("读取配置文件：" + path + " 失败 key:" + key);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				logger.error("文件流关闭失败", e);
			}
		}
		return value;
	}

	/**
	 * 写配置文件
	 * @param path 配置文件路径
	 * @param key key值
	 * @param value key对应的修改值
	 * @return boolean 值是否成功
	 * @author 272311 sangwenhao
	 *  2015-11-24
	 */
	public boolean setProperties(String path, String key, String value) {
		boolean flag = true;
		FileOutputStream os = null ;
		try { 
			// 关闭日志
			Properties properties = new Properties();
			// 文件输出流
			os = new FileOutputStream(path);
			properties.setProperty(key, value);
			// 将Properties集合保存到流中
			properties.store(os, "Copyright (c) deppon");
			os.close();// 关闭流
		} catch (Exception e2) {
			flag = false;
			logger.error("设置配置文件" + path + "失败：key=" + key + " ;value:" + value
					+ e2.getMessage(), e2);
		}finally{
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				logger.error("文件流关闭失败", e);
			}
		}

		return flag;
	}

}
