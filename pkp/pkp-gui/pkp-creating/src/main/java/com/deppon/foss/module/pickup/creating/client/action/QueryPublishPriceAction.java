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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/QueryPublishPriceAction.java
 * 
 * FILE NAME        	: QueryPublishPriceAction.java
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
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.ui.QueryPublishPriceUI;
import com.deppon.foss.module.pickup.creating.client.ui.PublishPriceLinkTableMode;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceEntity;
import com.deppon.foss.module.pickup.pricing.server.service.impl.PublishPriceService;
import com.google.inject.Inject;



/**
 * 
 * 查询公布价 action
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-shixiaowei
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class QueryPublishPriceAction extends
	AbstractButtonActionListener<QueryPublishPriceUI> {
	
	/**
	 * 日志
	 */
	private static final Log LOG = LogFactory.getLog(QueryPublishPriceAction.class);
	
	/**
	 * ui
	 */
	private QueryPublishPriceUI ui;
	
	/**
	 * query result list
	 */
	private List<PublishPriceEntity> list;
	
	
	/**
	 * 查询公布价 dao
	 */
	@Inject
	private IPublishPriceService publishPriceService;

	public void setIInjectUI(QueryPublishPriceUI ui) {
		this.ui = ui;
	}

	/**
	 * 查询按钮功能
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * 
	 */
	@SuppressWarnings({ "static-access" })
	public void actionPerformed(ActionEvent e) {
		JXTable table = ui.getTable();
		
		//get service 
		publishPriceService=GuiceContextFactroy.getInjector().getInstance(PublishPriceService.class);
		
		list = queryPublishPricing(ui);
		
		
	
		
		// 刷新表格
		PublishPriceLinkTableMode tableModel = new PublishPriceLinkTableMode(ui.getArray(list));

		table.setModel(tableModel);
		
		LOG.debug(tableModel);
		
		ui.refreshTable(table);
	}

	/**
	 * 查询公布价格
	 * @param ui2
	 * @return
	 */
	private List<PublishPriceEntity> queryPublishPricing(QueryPublishPriceUI ui) {
		 //始发区域code
		String createOrgCode = ui.getTxtCreateOrgCode().getText();
		//到达区域code
		String targetOrgCode =  ui.getTxtTargetOrgCode().getText();
		
		//查询公布价价格区域 
		//测试数据 XZQY-000000003   XZQY-000000002
		//GS00002   W01060302
		//W011302020106 W011305080202
		return publishPriceService.queryPublishPriceDetail(createOrgCode, targetOrgCode, new Date());
	}
}