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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/internal/WaybillInfoPanel.java
 * 
 * FILE NAME        	: WaybillInfoPanel.java
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
package com.deppon.foss.module.pickup.changing.client.ui.internal;

import javax.swing.JPanel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 运单信息面板
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-17
 * 上午9:19:23,content:
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-17 上午9:19:23
 * @since
 * @version
 */
public class WaybillInfoPanel extends JPanel {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 3415682426800978120L;

	/**
	 *  运单基础信息面板
	 */
	private BasicPanel basicPanel;

	/**
	 * 发货客户信息面板
	 */
	private ConsignerPanel consignerPanel;

	/**
	 * 收货客户信息面板
	 */
	private ConsigneePanel consigneePanel;

	/**
	 * 运输信息面板
	 */
	private TransportPanel transferPanel;

	/**
	 * 货物信息面板
	 */
	private GoodsPanel goodsPanel;

	/**
	 * 增值业务信息面板
	 */
	private IncrementPanel incrementPanel;

	/**
	 * 计费付款信息面板
	 */
	private BillingPayPanel billingPayPanel;

	/**
	 * 
	 * create panel
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:54:42
	 */
	public WaybillInfoPanel() {
		init();
	}

	/**
	 * 
	 * 布局初始化
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:54:51
	 */
	private void init() {
		setLayout(new FormLayout(new ColumnSpec[] { FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), }, new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		setOpaque(false);
		
		basicPanel = new BasicPanel();
		add(basicPanel, "1, 1, 1, 3, fill, fill");

		consignerPanel = new ConsignerPanel();
		add(consignerPanel, "3, 1, fill, fill");

		consigneePanel = new ConsigneePanel();
		add(consigneePanel, "3, 3, fill, fill");

		transferPanel = new TransportPanel();
		add(transferPanel, "1, 5, 3, 1, fill, fill");

		goodsPanel = new GoodsPanel();
		add(goodsPanel, "1, 7, 3, 1, fill, fill");

		incrementPanel = new IncrementPanel();
		add(incrementPanel, "1, 9, 3, 1, fill, fill");

		billingPayPanel = new BillingPayPanel();
		add(billingPayPanel, "1, 11, 3, 1, fill, fill");
	}

	/**
	 * 
	 *  增值业务信息面板
	 *  
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:50:16
	 */
	public IncrementPanel getIncrementPanel() {
		return incrementPanel;
	}

	/**
	 * 
	 * 运输信息面板
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:49:05
	 */
	public TransportPanel getTransferPanel() {
		return transferPanel;
	}

	/**
	 * 
	 *  发货客户信息面板
	 *  
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:48:31
	 */
	public ConsignerPanel getConsignerPanel() {
		return consignerPanel;
	}

	/**
	 * 
	 * 收货客户信息面板
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:48:43
	 */
	public ConsigneePanel getConsigneePanel() {
		return consigneePanel;
	}
	
	/**
	 * 
	 *  货物信息面板
	 *  
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:49:17
	 */
	public GoodsPanel getGoodsPanel() {
		return goodsPanel;
	}

	/**
	 * 
	 * 计费付款信息面板
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:50:31
	 */
	public BillingPayPanel getBillingPayPanel() {
		return billingPayPanel;
	}
	
	/**
	 * 
	 * 运单基础信息面板
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:48:11
	 */
	public BasicPanel getBasicPanel() {
		return basicPanel;
	}

}