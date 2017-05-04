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
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/OtherChargeQueryAction.java
 * 
 * FILE NAME        	: OtherChargeQueryAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
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
package com.deppon.foss.module.pickup.changingexp.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.QueryOtherChargeDialog;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.QueryOtherChargeDialog.ChangeInfoDetailTableModel;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;


/**
 * 
 * 其他费用查询Action
 * @author 102246-foss-shaohongliang
 * @date 2012-12-24 下午5:32:31
 */
public class OtherChargeQueryAction implements
	IButtonActionListener<QueryOtherChargeDialog> {
	
	/**
	 * DIALOG FOR OTHER QUERY
	 */
	private QueryOtherChargeDialog ui;

	/**
	 * 
	 * 根据费用名称查询其他费用
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 下午05:47:04
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		queryOtherCharge();
	}
	
	/**
	 * 
	 * 根据名称查询费用
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 下午07:37:35
	 */
	private void queryOtherCharge()
	{
		String chargeName = ui.getTxtPrivilegeName().getText();
		ChangeInfoDetailTableModel model = (ChangeInfoDetailTableModel) ui.getTable().getModel();
		List<OtherChargeVo> bakData = model.getBakList();
		List<OtherChargeVo> newData = new ArrayList<OtherChargeVo>();
		for(OtherChargeVo vo : bakData)
		{
			if(vo.getChargeName().indexOf(chargeName) >= 0)
			{
				newData.add(vo);
			}
		}
		ui.setChangeDetail(newData,bakData);
	}
	
	/**
	 * 
	 * UI注入
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:48:26
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
	@Override
	public void setInjectUI(QueryOtherChargeDialog ui) {
		this.ui = ui;
	}

}