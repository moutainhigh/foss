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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/OtherChargeQueryAction.java
 * 
 * FILE NAME        	: OtherChargeQueryAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.creating.client.ui.customer.QueryOtherChargePanel;
import com.deppon.foss.module.pickup.creating.client.ui.customer.QueryOtherChargePanel.ChangeInfoDetailTableModel;



/**
 * 
 * 运单OtherChargeQueryAction
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class OtherChargeQueryAction extends
		AbstractButtonActionListener<QueryOtherChargePanel> {
	
	QueryOtherChargePanel ui;



	
	/**
	 * 
	 * 根据费用名称查询其他费用
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 下午05:47:04
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		queryOtherCharge();
		//默认选中查询结果的第一行
		if(ui.getTable()!=null && ui.getTable().getRowCount()>0){
			ui.getTable().requestFocus();
			ui.getTable().setRowSelectionAllowed(true);
			ui.getTable().setRowSelectionInterval(0,0);
		}
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
	

	@Override
	public void setIInjectUI(QueryOtherChargePanel ui) {

		this.ui = ui;

	}

}