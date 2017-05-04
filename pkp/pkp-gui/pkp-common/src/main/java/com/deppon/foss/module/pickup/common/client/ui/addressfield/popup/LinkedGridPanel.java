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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/addressfield/popup/LinkedGridPanel.java
 * 
 * FILE NAME        	: LinkedGridPanel.java
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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.CommitListener;

/**
 * 
 * 带链接的网格Panel
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:102246-foss-shaohongliang,date:2012-10-24 下午6:40:06, </p>
 * @author 102246-foss-shaohongliang
 * @date 2012-10-24 下午6:40:06
 * @since
 * @version
 */
public class LinkedGridPanel extends AbstractGridPanel{
	
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 690589118861372341L;
	
	private static final int FOUR = 4;
	
	private static final int FIVE = 5;

	public LinkedGridPanel(List<AddressFieldDto> data, CommitListener commitListener) {
		super(data, commitListener);
	}

	/**
	 * 
	 * 初始化数据
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午8:24:25
	 * @see com.deppon.foss.module.pickup.common.client.ui.addressfield.popup.AbstractGridPanel#initData()
	 */
	protected void initData() {
		for(int i = 0; i < data.size(); i++){
			final AddressFieldDto value = data.get(i);
			JButton lable = new JButton(data.get(i).toString());
			lable.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(commitListener!=null){
						commitListener.commit(value);
					}
				}
			});
			this.add(lable);
		}
	}

	/**
	 * 
	 * 初始化布局
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午8:24:34
	 * @see com.deppon.foss.module.pickup.common.client.ui.addressfield.popup.AbstractGridPanel#initComponent()
	 */
	protected void initComponent() {
		setLayout(new GridLayout(FOUR, FOUR, FIVE, FIVE));
	}
	 
}