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
 * FILE PATH        	: boot/src/main/java/com/deppon/foss/module/boot/client/autorun/AutoRun.java
 * 
 * FILE NAME        	: AutoRun.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.boot.client.autorun;


/**
 * 自动运行任务对象
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:29:42, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:29:42
 * @since
 * @version
 */
public class AutoRun {
    	/**
    	 * 插件ID
    	 */
	public String pluginId;
	/**
    	 * 自动运行任务接口
    	 */
	public IAutoRunner autoRunner;
	/**
    	 * 
    	 */
	public boolean haltOnError;
	/**
    	 * 自动运行任务阶段对象
    	 */
	public AutoRunPhase phase;
	/**
    	 * 排序
    	 */
	public int order;
	/**
    	 * 是否可注销lable
    	 */
	public boolean cancelable;
	/**
    	 * 是否可以后台运行
    	 */
	public boolean canRunBackground;
	/**
	 * 是否隐藏
	 */
	public boolean hiddenDialog;
}