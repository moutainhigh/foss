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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/addressfield/popup/AbstractGridPanel.java
 * 
 * FILE NAME        	: AbstractGridPanel.java
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
package com.deppon.foss.module.pickup.common.client.ui.addressfield.popup;

import java.util.List;

import javax.swing.JPanel;

import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.CommitListener;

/**
 * 
 * 抽象网格Panel
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:102246-foss-shaohongliang,date:2012-10-24 下午6:39:22, </p>
 * @author 102246-foss-shaohongliang
 * @date 2012-10-24 下午6:39:22
 * @since
 * @version
 */
public abstract class AbstractGridPanel extends JPanel{
	
	/**
	 * 序列化版本
	 */
	private static final long serialVersionUID = -2532300416213559964L;

	/**
	 * 地址栏数据集合
	 */
	protected List<AddressFieldDto> data;
	
	/**
	 * 提交后触发监听
	 */
	protected CommitListener commitListener;
	
	public AbstractGridPanel(List<AddressFieldDto> data, CommitListener commitListener) {
		this.data = data;
		this.commitListener = commitListener;
		initComponent();
		initData();
	}
	
	/**
	 * 
	 * 初始化数据
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午8:23:16
	 */
	protected abstract void initData();

	/**
	 * 
	 * 初始化内部控件
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午8:23:25
	 */
	protected abstract void initComponent() ;


}