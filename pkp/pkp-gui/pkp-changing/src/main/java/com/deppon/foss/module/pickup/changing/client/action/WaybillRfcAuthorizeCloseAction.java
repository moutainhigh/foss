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
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/action/WaybillRfcAuthorizeCloseAction.java
 * 
 * FILE NAME        	: WaybillRfcAuthorizeCloseAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changing.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.mainframe.client.utills.ExceptionHandler;
import com.deppon.foss.module.pickup.changing.client.ui.dialog.WaybillRfcAuthorizeDialog;

/**
 * 
 * 对话框关闭事件
 * @author 102246-foss-shaohongliang
 * @date 2012-11-13 下午12:20:47
 */
public class WaybillRfcAuthorizeCloseAction implements IButtonActionListener<WaybillRfcAuthorizeDialog> {

	/**
	 * 主界面
	 */
	private WaybillRfcAuthorizeDialog ui;

	/**
	 * 
	 * 销毁窗口
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午7:54:12
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			ui.dispose();
			//刷新表格数据
//			ui.getRFCAuthorizeUI().refreshData();
		} catch (BusinessException be) {
			ExceptionHandler.alert(be);
		}
	}

	/**
	 * 
	 * 注入Dialog实例
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午7:53:57
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
	@Override
	public void setInjectUI(WaybillRfcAuthorizeDialog ui) {
		this.ui = ui;
	}

}