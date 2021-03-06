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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/QueryMemberDialogAction.java
 * 
 * FILE NAME        	: QueryMemberDialogAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;

/**
 * 查找会员对话框 
 * @author 026123-foss-lifengteng
 * @date 2012-12-25 上午10:29:41
 */
public class QueryMemberDialogAction implements IButtonActionListener<WaybillEditUI>{
    //主界面
    WaybillEditUI ui;
    IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
    
    /** 
     * 打开“查找会员界面”以及相关赋值处理
     * @author 026123-foss-lifengteng
     * @date 2012-10-20 下午5:48:43
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		//设置发货客户信息
		Common.setQueryDeliveryCustomer(ui);
	}

	/**
	 * 
	 * 注入UI界面
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-25 上午10:30:18
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
    @Override
    public void setInjectUI(WaybillEditUI ui) {
    	this.ui = ui;
    }

}