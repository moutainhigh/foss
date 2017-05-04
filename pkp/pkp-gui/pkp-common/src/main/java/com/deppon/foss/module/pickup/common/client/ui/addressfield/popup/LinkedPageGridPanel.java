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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/addressfield/popup/LinkedPageGridPanel.java
 * 
 * FILE NAME        	: LinkedPageGridPanel.java
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

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.CommitListener;
/**
 * 
 * 带链接翻页功能的网格Panel
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:102246-foss-shaohongliang,date:2012-10-24 下午6:40:34, </p>
 * @author 102246-foss-shaohongliang
 * @date 2012-10-24 下午6:40:34
 * @since
 * @version
 */
public class LinkedPageGridPanel extends AbstractGridPanel{
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -4782341267791628987L;

	private static final int TWELVE = 12;

	private static final int FIVE = 5;

	private static final int TWENTY = 20;

	private static final int FOUR = 4;

	private static final int THREE = 3;
	
	/**
	 * 当前页码
	 */
	private int currentPage;
	
	/**
	 * 总页码
	 */
	private int totalPage;
	
	/**
	 * 上一页
	 */
	private JButton prevButton;
	
	/**
	 * 下一页
	 */
	private JButton nextButton;
	
	JPanel mainPanel;

	public LinkedPageGridPanel(List<AddressFieldDto> data, CommitListener commitListener) {
		super(data, commitListener);
		initButtonListener();
		resetDefault();
	}
	
	/**
	 *
	 * 设置数据
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午8:25:43
	 */
	public void setData(List<AddressFieldDto> data){
		this.data = data;
		resetDefault();
	}

	/**
	 * 
	 * 添加按钮监听
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午8:25:54
	 */
	private void initButtonListener() {
		prevButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentPage--;
				updateUI2();
			}
		});
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentPage++;
				updateUI2();
			}
		});
		
	}

	@Override
	protected void initData() {
		
	}
	
	/**
	 * 
	 * 显示当前页
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午8:26:09
	 */
	private void loadCurrentPage(){
		mainPanel.removeAll();
		for(int i = (currentPage-1) * TWELVE; i < currentPage * TWELVE; i++){
			if(i < data.size()){
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
				mainPanel.add(lable);
			}else{
				JLabel lable = new JLabel();
				mainPanel.add(lable);
			}
		}
	}

	/**
	 * 
	 * 重置页码为首页
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午8:26:22
	 */
	private void resetDefault() {
		currentPage = 1;
		totalPage = 1;
		prevButton.setEnabled(false);
		nextButton.setEnabled(false);
		if(data != null){
			totalPage = Math.max(1, (data.size()-1)/ TWELVE + 1);
			updateUI2();
		}
	}

	/**
	 * 
	 * 初始化布局
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午8:26:36
	 * @see com.deppon.foss.module.pickup.common.client.ui.addressfield.popup.AbstractGridPanel#initComponent()
	 */
	@Override
	protected void initComponent() {
		setLayout(new BorderLayout());
		prevButton = new JButton("<");
		nextButton = new JButton(">");
		prevButton.setMargin(new Insets(FIVE, TWENTY, FIVE, TWENTY));
		nextButton.setMargin(new Insets(FIVE, TWENTY, FIVE, TWENTY));
		add(prevButton,BorderLayout.WEST);
		add(nextButton,BorderLayout.EAST);
		JPanel mainPanel = getMainPanel();
		add(mainPanel,BorderLayout.CENTER);
	}

	/**
	 * 
	 * 主页面初始化
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午8:27:07
	 */
	private JPanel getMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(FOUR, THREE, FIVE, FIVE));
		return mainPanel;
	}

	/**
	 * 
	 * 更新按钮可用状态
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午8:26:50
	 */
	public void updateUI2(){
		prevButton.setEnabled(currentPage != 1);
		nextButton.setEnabled(currentPage != totalPage);
		loadCurrentPage();
		updateUI();
	}
}