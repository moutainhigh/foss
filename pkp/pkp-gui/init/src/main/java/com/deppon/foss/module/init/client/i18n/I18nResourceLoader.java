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
 * FILE PATH        	: init/src/main/java/com/deppon/foss/module/init/client/i18n/I18nResourceLoader.java
 * 
 * FILE NAME        	: I18nResourceLoader.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.init.client.i18n;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.task.ITaskContext;
import com.deppon.foss.framework.client.commons.task.TaskSupport;
import com.deppon.foss.module.boot.client.app.Application;
import com.deppon.foss.module.boot.client.autorun.IAutoRunner;
import com.deppon.foss.module.init.client.dbupgrade.DBUpgradeAutoRunner;

 

/**
 * 国际化资源问题读取类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午10:11:13, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午10:11:13
 * @since
 * @version
 */
public class I18nResourceLoader extends TaskSupport implements IAutoRunner {

    	/**
    	 * 从日志工厂获取日志类
    	 */
	protected static final Log LOG = LogFactory.getLog(I18nResourceLoader.class);
	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(DBUpgradeAutoRunner.class);
	
	
	@Override
	public void execute(ITaskContext context) throws Exception {
		Application.getSplash().setAlwaysOnTop(false);
		
		/**
	    	 * 循环1到10
	    	 */
		for (int i = 1; i <= NumberConstants.NUMBER_10 ; i++) {
			progreeLogonScreen();
			try {
			    	/**
			    	 * 使得当前线程休眠200毫秒
			    	 */
				Thread.sleep(NumberConstants.NUMBER_200);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			/**
			 * 设置标题
			 */
			context.setTitle(i18n.get("init.i18n.resouceUpdate"));
			/**
			 * 设置当前运行信息
			 */
			context.setMessage(i18n.get("init.i18n.resouceUpdateWorking"));
			/**
			 * 设置进度
			 */
			context.setProgress(NumberConstants.NUMBER_10 * i);
			Application.getSplash().getProgress().setString(i18n.get("init.i18n.process",new Object[]{(NumberConstants.NUMBER_10 * i)})); // 设置显示文字
		
		}
	}
	/**
	 * 进入条
	 */
	private void progreeLogonScreen() {
		Application.getSplash().getProgress().setValue(Application.getSplash().getProgress().getValue() + 1);
	}
}