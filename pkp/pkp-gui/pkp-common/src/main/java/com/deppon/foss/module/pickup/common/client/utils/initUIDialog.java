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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/initUIDialog.java
 * 
 * FILE NAME        	: initUIDialog.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.utils;

import java.util.concurrent.ExecutionException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.application.IApplication;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:在设置的时间内，停止dialog</small></b>
 * </br> <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 2012-4-3 linws 新增 </div>
 ******************************************** 
 */
public class initUIDialog extends SwingWorker<JDialog, Void> {

	int continueTome = NumberConstants.NUMBER_100;
	IApplication application;
	
	//Logger
	private static final Log LOG = LogFactory.getLog(DateUtils.class);
	
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(initUIDialog.class);
		
	/**
	 * 
	 * @功能：创建一个新的实例 initUIDialog.制定持续时间
	 * @作者：linws
	 * @since
	 */
	public initUIDialog(int continueTome, IApplication application) {

		this.continueTome = continueTome;
		this.application = application;
	}

	/**
	 * 
	 * @功能：创建一个新的实例 initUIDialog.不需要指定时间
	 * @作者：linws
	 * @since
	 */
	public initUIDialog(IApplication application) {

		this.application = application;
	}

	@SuppressWarnings("static-access")
	@Override
	protected JDialog doInBackground() throws Exception {

		JFrame frame = (JFrame) application.getWorkbench().getFrame();

		final JDialog dialog = new JDialog(frame, true);
		dialog.setSize(NumberConstants.NUMBER_1000, NumberConstants.NUMBER_50);
		dialog.setLocation(frame.getX() + NumberConstants.NUMBER_200, frame.getY() + NumberConstants.NUMBER_200);
		dialog.setTitle(i18n.get("foss.gui.common.initUIDialog.exception.setTitle"));
		dialog.toFront();
		dialog.setModal(true);
		dialog.setModalExclusionType(dialog.getModalExclusionType().TOOLKIT_EXCLUDE);
		
		dialog.setDefaultCloseOperation(dialog.DISPOSE_ON_CLOSE);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JProgressBar progressBar = new JProgressBar();
				progressBar.setValue(NumberConstants.NUMBER_100);
				dialog.add(progressBar);
				dialog.setVisible(true);
			}
		});
		Thread.sleep(continueTome);
		return dialog;
	}
/**
 * 
 * 功能：done在EDT线程中，关闭dialog
 * @param:
 * @return:
 * @since:1.6
 */
	@Override
	protected void done() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					get().setVisible(false);
				} catch (InterruptedException e) {
					LOG.error("InterruptedException", e);
				} catch (ExecutionException e) {
					LOG.error("ExecutionException", e);
				}

			}
		});

		super.done();

	}

}