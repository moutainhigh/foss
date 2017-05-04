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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/DialogCloseAllAction.java
 * 
 * FILE NAME        	: DialogCloseAllAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changing.client.action;

import java.awt.event.ActionEvent;

import javax.swing.JDialog;

import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;


/**
 * 
 * 关闭Dialog适用所有
 * @author 025000-FOSS-helong
 * @date 2013-1-15 上午09:59:24
 */
public class DialogCloseAllAction extends
		AbstractButtonActionListener<JDialog> {

	JDialog dialog;

	/**
	 * 
	 * 关闭dialog
	 * @author 025000-FOSS-helong
	 * @date 2013-1-15 上午10:00:38
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		dialog.dispose();
	}

	@Override
	public void setIInjectUI(JDialog dialog) {

		this.dialog = dialog;

	}
}