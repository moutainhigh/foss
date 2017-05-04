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
 * FILE PATH        	: information/src/main/java/com/deppon/foss/module/information/client/message/MonitorInfo.java
 * 
 * FILE NAME        	: MonitorInfo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.information.client.message;

import java.net.InetSocketAddress;

import com.deppon.foss.framework.client.widget.network.NetworkMonitor;


/**
 * 定时提醒定时执行类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:56:31, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:56:31
 * @since
 * @version
 */
public class MonitorInfo {
	
	private static final int PORT = 8080;
	/**
	 * 
	 * 测试用入口
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public static void main(String[] args) {
		
		InetSocketAddress address = new InetSocketAddress("localhost", PORT);		
		if (NetworkMonitor.getNetworkMonitor(address).check()) {
			
			new Thread(new Runnable() {
				public void run() {
					
				}
			}).start();
		}
	}
	
	 

	

}