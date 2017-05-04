package com.deppon.foss.module.transfer.common.server.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertiesUtil {

	
	/**
	 * 日志初始化
	 */
	private static final Log LOG = LogFactory.getLog(PropertiesUtil.class);

	/**
	 * properties文件处理类
	 * 用于存放从properties文件中读取的键值对
	 * 在启动的时候加载
	 */
	private static Properties properties;
	
	
	/**
	 * clientVersion.properties文件处理类
	 * 用于存放从clientVersion.properties文件中读取的键值对
	 * 在启动的时候加载
	 */
	private static Properties clientVersionProperties;
	
	
	/**
	 * 静态初始化代码块
	 * 在启动类装载的时候读取配置文件
	 */
	static {
		//如果配置信息为空
		if(properties==null){
			//输入流
			InputStream in = null;
			try {
				//从配置文件中 根据class path来读取配置文件
				in = Thread.currentThread().getContextClassLoader() .getResourceAsStream("foss.properties");
				//创建properties对象
				properties = new Properties();
				properties.load(in);//从流中读取properties对象信息
			} catch (Exception e) {
				// 异常处理
				LOG.error("未找到foss.properties配置文件", e);
			} finally {
				if (in != null) {//判断流不是null
					try {
						//关闭流
						in.close();//再finally中关闭流
					} catch (IOException e) {
						//异常处理
						LOG.error("文件流关闭失败", e);
					}
				}
			}
		}
		
		//如果配置信息为空
		if(clientVersionProperties==null){
			//输入流
			InputStream in = null;
			try {
				//从配置文件中 根据class path来读取配置文件
				in = Thread.currentThread().getContextClassLoader() .getResourceAsStream("clientVersion.properties");
				//创建properties对象
				clientVersionProperties = new Properties();
				clientVersionProperties.load(in);//从流中读取properties对象信息
			} catch (Exception e) {
				// 异常处理
				LOG.error("未找到clientVersion.properties配置文件", e);
			} finally {
				if (in != null) {//判断流不是null
					try {
						//关闭流
						in.close();//再finally中关闭流
					} catch (IOException e) {
						//异常处理
						LOG.error("文件流关闭失败", e);
					}
				}
			}
		}
		
	}
	
	/**
	 * properties键值判断
	 * 根据propeties中的key获取value
	 * @param resource
	 * @param key
	 * @return String
	 */
	public static String getKeyValue(String key){
		//得到value
		String retValue = properties.getProperty(key);//从properties中读取value
		if(retValue==null){//如果value是null
			return "";//返回空字符串
		}else{
			return retValue;//否则返回value
		}
	}
	
	
	/**
	 * clientVersion.properties键值判断
	 * 根据clientVersion.propeties中的key获取value
	 * @param resource
	 * @param key
	 * @return String
	 */
	public static String getClientVersionKeyValue(String key){
		//得到value
		String retValue = clientVersionProperties.getProperty(key);//从properties中读取value
		if(retValue==null){//如果value是null
			return "";//返回空字符串
		}else{
			return retValue;//否则返回value
		}
	}
}
