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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/editui/ButtonPanel.java
 * 
 * FILE NAME        	: ButtonPanel.java
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
package com.deppon.foss.module.pickup.creating.client.ui.editui;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.pickup.creating.client.action.CreatePrintTimesDialogAction;
import com.deppon.foss.module.pickup.creating.client.action.FullScreenAction;
import com.deppon.foss.module.pickup.creating.client.action.GISDialogAction;
import com.deppon.foss.module.pickup.creating.client.action.LoadPickupStationAction;
import com.deppon.foss.module.pickup.creating.client.action.NewAddAction;
import com.deppon.foss.module.pickup.creating.client.action.OpenCrmNonfixedDialogAction;
import com.deppon.foss.module.pickup.creating.client.action.QueryMemberDialogAction;
import com.deppon.foss.module.pickup.creating.client.action.QueryPublicPriceLocalUIAction;
import com.deppon.foss.module.pickup.creating.client.action.QuerySamplePriceUIAction;
import com.deppon.foss.module.pickup.creating.client.action.WaybillPendingCompleteAction;
import com.deppon.foss.module.pickup.creating.client.action.WaybillSubmitAction;
import com.deppon.foss.module.pickup.creating.client.action.WaybillTempSaveAction;
import com.deppon.foss.module.pickup.creating.client.action.WebOrderAction;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;


/**
 * 
 * (按钮面板)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:15:50,content:
 * </p>
 * 
 * @author 025000-FOSS-helong
 * @date 2012-10-17 上午11:15:50
 * @since
 * @version
 */
public class ButtonPanel extends JPanel {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -5616693037458986938L;

	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(ButtonPanel.class);

	private static final int FIVE = 5;
	
	private String pictureWaybillType ;
	
	/**
	 * 新建
	 */
	@ButtonAction(icon = IconConstants.NEW, shortcutKey = "", type = NewAddAction.class)
	JButton btnNew = new JButton(i18n.get("foss.gui.creating.buttonPanel.new.label"));

	/**
	 * 暂存
	 */
	@ButtonAction(icon = IconConstants.SAVE, shortcutKey = "F2", type = WaybillTempSaveAction.class)
	JButton btnSave = new JButton(i18n.get("foss.gui.creating.buttonPanel.save.label"));

	/**
	 * 提交
	 */
	@ButtonAction(icon = IconConstants.SUBMIT, shortcutKey = "F3", type = WaybillSubmitAction.class)
	JButton btnSubmit = new JButton(i18n.get("foss.gui.creating.buttonPanel.submit.label"));

	/**
	 * 补录运单
	 */
	@ButtonAction(icon = IconConstants.SUPPLYWWAYBILL, shortcutKey = "F4", type = WaybillPendingCompleteAction.class)
	JButton btnSupplyWaybill = new JButton(i18n.get("foss.gui.creating.buttonPanel.supplyWaybill.label"));

	/**
	 * 网上订单
	 */
	@ButtonAction(icon = IconConstants.ORDER, shortcutKey = "F5", type = WebOrderAction.class)
	JButton btnOnLineOrder = new JButton(i18n.get("foss.gui.creating.buttonPanel.onLineOrder.label"));

	/**
	 * 运单打印
	 */
	@ButtonAction(icon = IconConstants.PRINT, shortcutKey = "", type = CreatePrintTimesDialogAction.class)
	JButton btnPrint = new JButton(i18n.get("foss.gui.creating.buttonPanel.print.label"));

	/**
	 * 打印预览
	 */
	@ButtonAction(icon = IconConstants.PREVIEWPNG, shortcutKey = "", type = CreatePrintTimesDialogAction.class)
	JButton btnPreview = new JButton(i18n.get("foss.gui.creating.buttonPanel.preview.label"));

	/**
	 * 打印标签
	 */
	@ButtonAction(icon = IconConstants.PRINTLABEL, shortcutKey = "", type = CreatePrintTimesDialogAction.class)
	JButton btnPrintLabel = new JButton(i18n.get("foss.gui.creating.buttonPanel.printLabel.label"));

	/**
	 * 查询客户
	 */
	@ButtonAction(icon = IconConstants.SEARCHMEMBER, shortcutKey = "", type = QueryMemberDialogAction.class)
	JButton btnSearchMember = new JButton(i18n.get("foss.gui.creating.buttonPanel.searchMember.label"));
	
	/**
	 * 新增CMR散客 
	 */
	@ButtonAction(icon = IconConstants.SEARCHMEMBER, shortcutKey = "", type = OpenCrmNonfixedDialogAction.class)
	JButton btnAddCrmCustomer = new JButton(i18n.get("foss.gui.creating.buttonPanel.addCrmCustomer.label"));

	/**
	 * 查询网点
	 */
	@ButtonAction(icon = IconConstants.SEARCHBRANCH, shortcutKey = "F6", type = LoadPickupStationAction.class)
	JButton btnSearchBranch = new JButton(i18n.get("foss.gui.creating.buttonPanel.searchBranch.label"));

	/**
	 * 电子地图
	 */
	@ButtonAction(icon = IconConstants.SEARCHBRANCH, shortcutKey = "F7", type = GISDialogAction.class)
	JButton btnGIS = new JButton(i18n.get("foss.gui.creating.buttonPanel.gis.label"));

	/**
	 * 公布价查询
	 */
	@ButtonAction(icon = IconConstants.PUBLIC, shortcutKey = "F8", type = QueryPublicPriceLocalUIAction.class)
	JButton btnSearchPrice = new JButton(i18n.get("foss.gui.creating.buttonPanel.searchPrice.label"));
	
	/**
	 * 简单报价
	 */
	@ButtonAction(icon = IconConstants.PUBLIC, shortcutKey = "F9", type = QuerySamplePriceUIAction.class)
	private final JButton btnSamplePrice = new JButton(i18n.get("foss.gui.creating.buttonPanel.samplePrice.label"));
	
//	/**
//	 * 整车报价
//	 */
//	@ButtonAction(icon = IconConstants.PUBLIC, shortcutKey = "", type = VehiclePriceDialogUIAction.class)
//	JButton btnVehiclePrice = new JButton(i18n.get("foss.gui.creating.buttonPanel.vehiclePrice.label"));

	/*
	 * 全屏/缩小
	 */
	@ButtonAction(icon = IconConstants.fullScreen, shortcutKey = "F11", type = FullScreenAction.class)
	public JButton fullScreen = new JButton(i18n.get("foss.gui.creating.buttonPanel.fullScreen.label"));

	/**
	 * 构造方法
	 */
	public ButtonPanel(String pictureWaybillType) {
		this.pictureWaybillType = pictureWaybillType;
		init();
	}

	/**
	 * 
	 * <p>
	 * (初始化)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 上午10:27:04
	 * @see
	 */
	private void init() {
		setLayout(new FlowLayout(FlowLayout.LEFT, FIVE, FIVE));

		//判断是图片开单还是其他开单
		if(WaybillConstants.WAYBILL_PICTURE.equals(pictureWaybillType)){
			pictureWaybill();
		}else{
			otherWaybill();
		}
		
	}
	
	//图片开单
	public void pictureWaybill(){
		if (!"ONLINE_LOGIN".equals(SessionContext.get("user_loginType").toString())) {
			btnOnLineOrder.setEnabled(false);
		}
		add(btnOnLineOrder);// 网上订单
		add(btnPrint);// 运单打印
		btnPrint.setEnabled(false);
		add(btnPreview);// 打印预览
		btnPreview.setEnabled(false);
		add(btnPrintLabel);// 打印标签
		btnPrintLabel.setEnabled(false);
		// 查找会员
		add(btnSearchMember);
		// 新增CRM散客
		add(btnAddCrmCustomer); 
		add(btnSearchBranch);// 查询网点
		add(btnGIS);// 电子地图
		add(btnSearchPrice);// 公布价查询
		//添加简单报价
		add(btnSamplePrice);
		fullScreen.setMnemonic(KeyEvent.VK_A);
		add(fullScreen);
	}
	
	//其他开单
	public void otherWaybill(){
		add(btnNew);// 新建
		add(btnSave);// 暂存
		btnSave.setEnabled(false);
		add(btnSubmit);// 提交
		btnSubmit.setEnabled(false);
		add(btnSupplyWaybill);// 补录运单
		if (!"ONLINE_LOGIN".equals(SessionContext.get("user_loginType").toString())) {
			btnOnLineOrder.setEnabled(false);
		}
		add(btnOnLineOrder);// 网上订单
		add(btnPrint);// 运单打印
		btnPrint.setEnabled(false);
		add(btnPreview);// 打印预览
		btnPreview.setEnabled(false);
		add(btnPrintLabel);// 打印标签
		btnPrintLabel.setEnabled(false);
		// 查找会员
		add(btnSearchMember);
		// 新增CRM散客
		add(btnAddCrmCustomer); 
		add(btnSearchBranch);// 查询网点
		add(btnGIS);// 电子地图
		add(btnSearchPrice);// 公布价查询	
		add(btnSamplePrice);//简单报价
	}
	
	/**
	 * getBtnNew
	 * @return JButton
	 */
	public JButton getBtnNew() {
		return btnNew;
	}
	
	/**
	 * getBtnSave
	 * @return JButton
	 */
	public JButton getBtnSave() {
		return btnSave;
	}
	
	/**
	 * getBtnSubmit
	 * @return JButton
	 */
	public JButton getBtnSubmit() {
		return btnSubmit;
	}

	/**
	 * getBtnPrint
	 * @return JButton
	 */ 
	public JButton getBtnPrint() {
		return btnPrint;
	}

	/**
	 * getBtnPreview
	 * @return JButton
	 */
	public JButton getBtnPreview() {
		return btnPreview;
	}

	/**
	 * getBtnPrintLabel
	 * @return JButton
	 */
	public JButton getBtnPrintLabel() {
		return btnPrintLabel;
	}

	/**
	 * getBtnSearchBranch
	 * @return JButton
	 */
	public JButton getBtnSearchBranch() {
		return btnSearchBranch;
	}

	/**
	 * getBtnGIS
	 * @return JButton
	 */
	public JButton getBtnGIS() {
		return btnGIS;
	}

	/**
	 * getBtnSearchPrice
	 * @return JButton
	 */
	public JButton getBtnSearchPrice() {
		return btnSearchPrice;
	}
	
	/**
	 * 
	 * 获取查询会员
	 * @author 025000-FOSS-helong
	 * @date 2013-2-25 上午09:21:37
	 * @return
	 */
	public JButton getBtnSearchMember() {
		return btnSearchMember;
	}

	/**
	 * 获取新增散客的按钮  
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-18 上午10:24:37
	 */
	public JButton getBtnAddCrmCustomer() {
		return btnAddCrmCustomer;	
	}
	public JButton getFullScreen() {
		return fullScreen;
	}
	public void setFullScreen(JButton fullScreen) {
		this.fullScreen = fullScreen;
	}
	
}
