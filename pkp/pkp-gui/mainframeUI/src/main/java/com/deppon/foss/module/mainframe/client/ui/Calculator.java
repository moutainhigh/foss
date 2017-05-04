/**
 *  initial comments.
 */
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.mainframe.client.ui;

import java.util.TimerTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午11:47:10, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午11:47:10
 * @since
 * @version
 */
public class Calculator {
	
	/**
	 * log 从日志工厂获取日志
	 */
	private static final Log LOG = LogFactory.getLog(Calculator.class);
	
	/**
	 * time task 
	 * @author admin
	 *
	 */
	public static class MyTimerTask extends TimerTask {

		/**
		 * 构造方法
		 * @param tm
		 */
		public MyTimerTask() {
		}

		/**
		 * run method for task
		 */
		public void run() {
			try {
				//exec windows calculator
			    	/**
				 * 启动
				 */
				Runtime.getRuntime().exec("calc.exe");
			} catch (Exception ex) {
				LOG.error("exception", ex);
			}

		}

	}

}