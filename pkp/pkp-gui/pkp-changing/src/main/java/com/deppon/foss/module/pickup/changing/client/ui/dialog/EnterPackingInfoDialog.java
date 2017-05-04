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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/customer/EnterYokeInfoDialog.java
 * 
 * FILE NAME        	: EnterYokeInfoDialog.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-creating
 * PACKAGE NAME: com.deppon.foss.module.pickup.creating.client.ui.customer
 * FILE    NAME: EnterYokeInfoUI.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.changing.client.ui.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.component.focuspolicy.factory.FocusPolicyFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.pickup.changing.client.action.DialogCloseAllAction;
import com.deppon.foss.module.pickup.changing.client.action.PackingYokeEnterAction;
import com.deppon.foss.module.pickup.changing.client.action.PackingYokeResetAction;
import com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.changing.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changing.client.vo.PackingYokeVo;
import com.deppon.foss.module.pickup.changing.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.ui.UIUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.FloatDocument;
import com.deppon.foss.module.pickup.common.client.utils.PackageNumberDocument;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceFibelPaperPackingEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 录入打包装信息
 * 
 * @author：218371-foss-zhaoyanjun changing
 * @date:2014-11-3下午13:51
 */
@ContainerSeq(seq=1)
public class EnterPackingInfoDialog extends JDialog {
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 8802287401540527591L;
	
	IWaybillRfcService waybillService = WaybillRfcServiceFactory
			.getWaybillRfcService();
	
	//绑定bean
	PackingYokeVo vo=new PackingYokeVo();

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(EnterPackingInfoDialog.class);
	
	//绑定bean
	IBinder<PackingYokeVo> packingYokeBinder;

	//运单界面
	WaybillRFCUI waybillRFCUI;
	
	//获取与UI绑定的waybillPanelVo对象
	WaybillInfoVo bean;
	
	//此开单网点对应的id
	String id=null;
		
	//单价信息封装类
	PriceFibelPaperPackingEntity priceFibelPaperPackingEntity=null;
	
	private static final int EIGHT = 8;
	
	private static final int TWO = 2;

	private static final int TEN = 10;

	private static final int TWELVE = 12;
	
	//浮点小数
	FloatDocument floatDocument=new FloatDocument(EIGHT,TWO);
	FloatDocument floatDocument1=new FloatDocument(NumberConstants.NUMBER_4,2);
	//整数
	PackageNumberDocument a=new PackageNumberDocument(EIGHT);
	PackageNumberDocument b=new PackageNumberDocument(EIGHT);
	PackageNumberDocument c=new PackageNumberDocument(EIGHT);
	PackageNumberDocument d=new PackageNumberDocument(EIGHT);
	PackageNumberDocument e=new PackageNumberDocument(EIGHT);
	PackageNumberDocument f=new PackageNumberDocument(EIGHT);
	PackageNumberDocument g=new PackageNumberDocument(EIGHT);
	PackageNumberDocument h=new PackageNumberDocument(EIGHT);
	PackageNumberDocument i=new PackageNumberDocument(EIGHT);
	PackageNumberDocument j=new PackageNumberDocument(EIGHT);
	PackageNumberDocument k=new PackageNumberDocument(EIGHT);
	PackageNumberDocument l=new PackageNumberDocument(EIGHT);
	PackageNumberDocument m=new PackageNumberDocument(EIGHT);
	PackageNumberDocument n=new PackageNumberDocument(EIGHT);
	PackageNumberDocument sevenInt=new PackageNumberDocument(NumberConstants.NUMBER_7);
	
	//纸箱一号客户
	@FocusSeq(seq=1)
	private JTextFieldValidate paperBoxOneTextField;
	
	//纸箱二号客户
	@FocusSeq(seq=2)
	private JTextFieldValidate paperBoxTwoTextField;
	
	//纸箱三号客户
	@FocusSeq(seq=3)
	private JTextFieldValidate paperBoxThreeTextField;
	
	//纸箱四号客户
	@FocusSeq(seq=4)
	private JTextFieldValidate paperBoxFourTextField;
	
	//纤袋一号蓝
	@FocusSeq(seq=5)
	private JTextFieldValidate fibelBagOneBlueTextField;
	
	//纤袋二号蓝
	@FocusSeq(seq=6)
	private JTextFieldValidate fibelBagTwoBlueTextField;
	
	//纤袋三号蓝
	@FocusSeq(seq=7)
	private JTextFieldValidate fibelBagThreeBlueTextField;
	
	//纤袋四号蓝
	@FocusSeq(seq=8)
	private JTextFieldValidate fibelBagFourBlueTextField;
	
	//纤袋一号白
	@FocusSeq(seq=9)
	private JTextFieldValidate fibelBagOneWhiteTextField;
	
	//纤袋二号白
	@FocusSeq(seq=10)
	private JTextFieldValidate fibelBagTwoWhiteTextField;
	
	//纤袋三号白
	@FocusSeq(seq=11)
	private JTextFieldValidate fibelBagThreeWhiteTextField;
	
	//纤袋四号白
	@FocusSeq(seq=12)
	private JTextFieldValidate fibelBagFourWhiteTextField;
	
	//纤袋五号白
	@FocusSeq(seq=13)
	private JTextFieldValidate fibelBagFiveWhiteTextField;
	
	//纤袋六号白
	@FocusSeq(seq=14)
	private JTextFieldValidate fibelBagSixWhiteTextField;
	
	//纸箱总金额
	@FocusSeq(seq=15)
	private JTextFieldValidate paperBoxTotlePriceTextField;
	
	//纤袋总金额
	@FocusSeq(seq=16)
	private JTextFieldValidate fibelBagTotlePriceTextField;
	
	//缓冲物金额
	@FocusSeq(seq=17)
	private JTextFieldValidate bufferLabel;
	
	//折扣
	@FocusSeq(seq=18)
	private JTextFieldValidate discountLabel;
	
	//总计金额
	
	@FocusSeq(seq=19)
	private JTextFieldValidate allTotlePriceTextField;
	
	@ButtonAction(icon = "preview.png", shortcutKey = "", type = PackingYokeResetAction.class)
	JButton resetButton;
	
	@ButtonAction(icon = "preview.png", shortcutKey = "", type = DialogCloseAllAction.class)
	JButton closeButton;
	
	@ButtonAction(icon = "preview.png", shortcutKey = "", type = PackingYokeEnterAction.class)
	JButton sureInsertButton;
	
	/**
	 * 构造函数
	 * @param waybillEditUI
	 */
	public EnterPackingInfoDialog(WaybillRFCUI waybillRFCUI) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		getContentPane().setForeground(Color.BLACK);
		this.waybillRFCUI = waybillRFCUI;
		// 绑定更改单VO
		bean = waybillRFCUI.getBinderWaybill();
		//获取其它参数合集
		id=getId();
		//获取单价信息
		priceFibelPaperPackingEntity=waybillService.queryPriceFibelPaperPackingEntity(id);
		init();
		bind();	
		initData(); //zxy 20131118 ISSUE-4391 修改：由show函数调用
		//包装费优化木包装需求，出第一外场不可以进行更改 - 272311-sangwenhao
		checkGoodsStatus();
	}
			
	private void checkGoodsStatus() {
		WaybillInfoVo bean = waybillRFCUI.getBinderWaybill();
		if (!WaybillRfcConstants.RECEIVE_STOCK.equals(bean.getGoodsStatus().getValueCode())) {
			// 货物已出开单部门，无法修改打包装信息
			MsgBox.showInfo(i18n.get("foss.gui.changing.enterYokeInfoDialog.msgBox.pack.label"));
			UIUtils.disableUI(this);
		}
	}
	
	/**
	 * 获取id参数
	 * @return
	 */
	private String getId() {
		//获取其他费用集合
		QueryBillCacilateValueAddDto queryDto=CommonUtils.getQueryOtherChargeParamByZQBZ(bean);
		//检索增值费用
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(queryDto);
		//检索出当前匹配的PriceCriteriaDetailEntity 的id
		String tempId=null;
		if(list==null){
			return null;
		}
		for(ValueAddDto every:list){
			if(PriceEntityConstants.PRICING_CODE_ZQBZ.equals(every.getPriceEntityCode())){
				tempId=every.getId();
				break;
			}
		}
		return tempId;
	}

	/**
	 * 
	 * （将按钮以及空间进行绑定）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-30 上午09:12:46
	 */
	private void bind() {
		//获取光标绑定
		FocusPolicyFactory.getIntsance().setFocusTraversalPolicy(this);
		ButtonActionFactory.getIntsance().bindButtonAction(this);
	}

	// 初始化界面
	private void init() {
		//zxy 20131118 ISSUE-4391 start 修改：布局修改
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("40dlu"),
				ColumnSpec.decode("40dlu"),
				ColumnSpec.decode("40dlu:grow"),
				ColumnSpec.decode("40dlu:grow"),
				ColumnSpec.decode("40dlu:grow"),
				ColumnSpec.decode("40dlu:grow"),
				ColumnSpec.decode("40dlu:grow"),
				ColumnSpec.decode("40dlu:grow"),
				ColumnSpec.decode("40dlu:grow"),
				ColumnSpec.decode("40dlu:grow"),
				ColumnSpec.decode("40dlu:grow"),
				ColumnSpec.decode("40dlu:grow"),
				ColumnSpec.decode("40dlu"),
				ColumnSpec.decode("40dlu"),
				ColumnSpec.decode("80dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("20dlu"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("18dlu"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("15dlu:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("18dlu:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("15dlu"),
				RowSpec.decode("15dlu:grow"),
				RowSpec.decode("15dlu"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("18dlu:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("18dlu"),
				RowSpec.decode("18dlu:grow"),
				RowSpec.decode("1dlu"),}));
		//zxy 20131118 ISSUE-4391 end 修改：布局修改
		
		//打包装明细
		JLabel totleTitle = new JLabel("打包装明细");
		totleTitle.setForeground(Color.BLACK);
		totleTitle.setBackground(Color.BLUE);
		totleTitle.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_14));
		getContentPane().add(totleTitle, "2, 1, 15, 1, left, default");
		
		//分隔线
		JSeparator jseparator1 = new JSeparator();
		jseparator1.setForeground(Color.BLACK);
		getContentPane().add(jseparator1, "2, 2, 15, 1, fill, default");

		//纸箱信息录入
		JLabel levelOneTitleOne = new JLabel("纸箱信息录入");
		levelOneTitleOne.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_12));
		getContentPane().add(levelOneTitleOne, "2, 3, 4, 1, left, center");
		
		//分隔线
		JSeparator jseparator2 = new JSeparator();
		jseparator2.setForeground(Color.BLACK);
		getContentPane().add(jseparator2, "2, 4, 15, 1");
		
		//纸箱1号 客户
		JLabel paperBoxOneLabel = new JLabel("纸箱1号 客户：");
		getContentPane().add(paperBoxOneLabel, "2, 5, 2, 1, center, center");
		
		paperBoxOneTextField = new JTextFieldValidate();
		paperBoxOneTextField.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(paperBoxOneTextField, "4, 5, 2, 1, fill, default");
		paperBoxOneTextField.setColumns(TEN);
		paperBoxOneTextField.setDocument(a);
		
		paperBoxOneTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				sum();
			}
		});
		
		
		//纸箱2号 客户
		JLabel paperBoxTwoLabel = new JLabel("纸箱2号 客户：");
		getContentPane().add(paperBoxTwoLabel, "6, 5, 2, 1, center, center");
		
		paperBoxTwoTextField = new JTextFieldValidate();
		paperBoxTwoTextField.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(paperBoxTwoTextField, "8, 5, 2, 1, fill, center");
		paperBoxTwoTextField.setColumns(TEN);
		paperBoxTwoTextField.setDocument(b);
		
		paperBoxTwoTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				sum();
			}
		});
		
		//纸箱3号 客户
		JLabel paperBoxThreeLabel = new JLabel("纸箱3号 客户：");
		getContentPane().add(paperBoxThreeLabel, "10, 5, 2, 1, center, center");
		
		paperBoxThreeTextField = new JTextFieldValidate();
		paperBoxThreeTextField.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(paperBoxThreeTextField, "12, 5, 2, 1, fill, center");
		paperBoxThreeTextField.setColumns(TEN);
		paperBoxThreeTextField.setDocument(c);
		
		paperBoxThreeTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				sum();
			}
		});
		
		//纸箱4号 客户
		JLabel paperBoxFourLabel = new JLabel("纸箱4号 客户：");
		getContentPane().add(paperBoxFourLabel, "14, 5, 2, 1, center, center");
		
		paperBoxFourTextField = new JTextFieldValidate();
		paperBoxFourTextField.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(paperBoxFourTextField, "16, 5, fill, center");
		paperBoxFourTextField.setColumns(TEN);
		paperBoxFourTextField.setDocument(d);
		
		paperBoxFourTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				sum();
			}
		});
		
		//分隔线
		JSeparator jseparator3 = new JSeparator();
		jseparator3.setForeground(Color.BLACK);
		getContentPane().add(jseparator3, "2, 6, 15, 1");
		
		//纤袋信息录入
		JLabel levelOneTitleTwo = new JLabel("纤袋信息录入");
		levelOneTitleTwo.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_12));
		getContentPane().add(levelOneTitleTwo, "2, 7, 4, 1, left, center");
		
		//分隔线
		JSeparator jseparator4 = new JSeparator();
		jseparator4.setForeground(Color.BLACK);
		getContentPane().add(jseparator4, "2, 8, 15, 1");
		
		//纤袋1号蓝 客户
		JLabel fibelBagOneBuleLabel = new JLabel("纤袋 1号 零担 蓝");
		getContentPane().add(fibelBagOneBuleLabel, "2, 9, 2, 1, center, center");
		
		fibelBagOneBlueTextField = new JTextFieldValidate();
		fibelBagOneBlueTextField.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(fibelBagOneBlueTextField, "4, 9, 2, 1, fill, default");
		fibelBagOneBlueTextField.setColumns(TEN);
		fibelBagOneBlueTextField.setDocument(e);
		
		fibelBagOneBlueTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				sum();
			}
		});
		
		//纤袋2号蓝 客户
		JLabel fibelBagTwoBlueLabel = new JLabel("纤袋 2号 零担 蓝");
		getContentPane().add(fibelBagTwoBlueLabel, "6, 9, 2, 1, center, center");
		
		fibelBagTwoBlueTextField = new JTextFieldValidate();
		fibelBagTwoBlueTextField.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(fibelBagTwoBlueTextField, "8, 9, 2, 1, fill, center");
		fibelBagTwoBlueTextField.setColumns(TEN);
		fibelBagTwoBlueTextField.setDocument(f);
		
		fibelBagTwoBlueTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				sum();
			}
		});
		
		//纤袋3号蓝 客户
		JLabel fibelBagThreeBlueLabel = new JLabel("纤袋 3号 零担 蓝");
		getContentPane().add(fibelBagThreeBlueLabel, "10, 9, 2, 1, center, center");
		
		fibelBagThreeBlueTextField = new JTextFieldValidate();
		fibelBagThreeBlueTextField.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(fibelBagThreeBlueTextField, "12, 9, 2, 1, fill, default");
		fibelBagThreeBlueTextField.setColumns(TEN);
		fibelBagThreeBlueTextField.setDocument(g);
		
		fibelBagThreeBlueTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				sum();
			}
		});
		
		//纤袋4号蓝 客户
		JLabel fibelBagFourBlueLabel = new JLabel("纤袋 4号 零担 蓝");
		getContentPane().add(fibelBagFourBlueLabel, "14, 9, 2, 1, center, center");
		
		fibelBagFourBlueTextField = new JTextFieldValidate();
		fibelBagFourBlueTextField.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(fibelBagFourBlueTextField, "16, 9, fill, default");
		fibelBagFourBlueTextField.setColumns(TEN);
		fibelBagFourBlueTextField.setDocument(h);
		
		fibelBagFourBlueTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				sum();
			}
		});
		
		//纤袋1号白 客户
		JLabel fibelBagOneWhiteLabel = new JLabel("纤袋 1号 零担 白");
		getContentPane().add(fibelBagOneWhiteLabel, "2, 10, 2, 1, center, center");
		
		fibelBagOneWhiteTextField = new JTextFieldValidate();
		fibelBagOneWhiteTextField.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(fibelBagOneWhiteTextField, "4, 10, 2, 1, fill, center");
		fibelBagOneWhiteTextField.setColumns(TEN);
		fibelBagOneWhiteTextField.setDocument(i);
		
		fibelBagOneWhiteTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				sum();
			}
		});
		
		//纤袋2号白 客户
		JLabel fibelBagTwoWhiteLabel = new JLabel("纤袋 2号 零担 白");
		getContentPane().add(fibelBagTwoWhiteLabel, "6, 10, 2, 1, center, center");
		
		fibelBagTwoWhiteTextField = new JTextFieldValidate();
		fibelBagTwoWhiteTextField.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(fibelBagTwoWhiteTextField, "8, 10, 2, 1, fill, default");
		fibelBagTwoWhiteTextField.setColumns(TEN);
		fibelBagTwoWhiteTextField.setDocument(j);
		
		fibelBagTwoWhiteTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				sum();
			}
		});
		
		//纤袋3号白 客户
		JLabel fibelBagThreeWhiteLabel = new JLabel("纤袋 3号 零担 白");
		getContentPane().add(fibelBagThreeWhiteLabel, "10, 10, 2, 1, center, center");
		
		fibelBagThreeWhiteTextField = new JTextFieldValidate();
		fibelBagThreeWhiteTextField.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(fibelBagThreeWhiteTextField, "12, 10, 2, 1, fill, default");
		fibelBagThreeWhiteTextField.setColumns(TEN);
		fibelBagThreeWhiteTextField.setDocument(k);
		
		fibelBagThreeWhiteTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				sum();
			}
		});
		
		///纤袋4号白 客户/
		JLabel fibelBagFourWhiteLabel = new JLabel("纤袋 4号 零担 白");
		getContentPane().add(fibelBagFourWhiteLabel, "14, 10, 2, 1, center, center");
		
		fibelBagFourWhiteTextField = new JTextFieldValidate();
		fibelBagFourWhiteTextField.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(fibelBagFourWhiteTextField, "16, 10, fill, default");
		fibelBagFourWhiteTextField.setColumns(TEN);
		fibelBagFourWhiteTextField.setDocument(l);
		
		fibelBagFourWhiteTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				sum();
			}
		});
		
		//纤袋5号白 客户
		JLabel fibelBagFiveWhiteLabel = new JLabel("纤袋 5号 零担 白");
		getContentPane().add(fibelBagFiveWhiteLabel, "2, 11, 2, 1, center, center");
		
		fibelBagFiveWhiteTextField = new JTextFieldValidate();
		fibelBagFiveWhiteTextField.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(fibelBagFiveWhiteTextField, "4, 11, 2, 1, fill, default");
		fibelBagFiveWhiteTextField.setColumns(TEN);
		fibelBagFiveWhiteTextField.setDocument(m);
		
		fibelBagFiveWhiteTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				sum();
			}
		});
		
		//纤袋6号白 客户
		JLabel fibelBagSixWhiteLabel = new JLabel("纤袋 6号 零担 白");
		getContentPane().add(fibelBagSixWhiteLabel, "6, 11, 2, 1, center, center");
		
		fibelBagSixWhiteTextField = new JTextFieldValidate();
		fibelBagSixWhiteTextField.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(fibelBagSixWhiteTextField, "8, 11, 2, 1, fill, default");
		fibelBagSixWhiteTextField.setColumns(TEN);
		fibelBagSixWhiteTextField.setDocument(n);
		
		fibelBagSixWhiteTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				sum();
			}
		});
		
		//分隔线
		JSeparator jseparator5 = new JSeparator();
		jseparator5.setForeground(Color.BLACK);
		getContentPane().add(jseparator5, "2, 12, 15, 1");
		
		//费用
		JLabel levelOneTitleThree = new JLabel("费用");
		levelOneTitleThree.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_12));
		getContentPane().add(levelOneTitleThree, "2, 13, 4, 1");
		
		//分隔线
		JSeparator jseparator6 = new JSeparator();
		jseparator6.setForeground(Color.BLACK);
		getContentPane().add(jseparator6, "2, 14, 15, 1");
		
		//小计
		JLabel otherLaberOne = new JLabel("小计：");
		otherLaberOne.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_12));
		getContentPane().add(otherLaberOne, "2, 15, center, center");
		
		//纸箱总额
		JLabel paperBoxTotlePriceLabel = new JLabel("纸箱：");
		getContentPane().add(paperBoxTotlePriceLabel, "3, 15, center, center");
		
		paperBoxTotlePriceTextField = new JTextFieldValidate();
		paperBoxTotlePriceTextField.setEnabled(false);
		paperBoxTotlePriceTextField.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(paperBoxTotlePriceTextField, "4, 15, 2, 1, fill, center");
		paperBoxTotlePriceTextField.setColumns(TEN);
		
		//纤袋总额
		JLabel fibelBagTotlePriceLabel = new JLabel("纤袋：");
		getContentPane().add(fibelBagTotlePriceLabel, "6, 15, center, center");
		
		fibelBagTotlePriceTextField = new JTextFieldValidate();
		fibelBagTotlePriceTextField.setHorizontalAlignment(SwingConstants.CENTER);
		fibelBagTotlePriceTextField.setEnabled(false);
		getContentPane().add(fibelBagTotlePriceTextField, "7, 15, 2, 1, fill, center");
		fibelBagTotlePriceTextField.setColumns(TEN);
		
		//缓冲物
		JLabel bufferLabel1 = new JLabel("缓冲物：");
		getContentPane().add(bufferLabel1, "9, 15, center, center");
		
		bufferLabel = new JTextFieldValidate();
		bufferLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(bufferLabel, "10, 15, 2, 1, fill, default");
		bufferLabel.setColumns(NumberConstants.NUMBER_7);
		bufferLabel.setDocument(sevenInt);
		
		bufferLabel.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				sum();
			}
		});
		//折扣
		JLabel discountLabel1 = new JLabel("折扣：");
		getContentPane().add(discountLabel1, "12, 15, center, center");
		
		discountLabel = new JTextFieldValidate();
		discountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(discountLabel, "13, 15, 2, 1, fill, default");
		discountLabel.setColumns(NumberConstants.NUMBER_4);
		discountLabel.setDocument(floatDocument1);
		
		discountLabel.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				sum();
			}
		});
		
		//合计总额
		JLabel allTotlePriceLabel = new JLabel("合计：");
		allTotlePriceLabel.setFont(new Font("宋体", Font.BOLD, TWELVE));
		getContentPane().add(allTotlePriceLabel, "15, 15, center, center");
		
		allTotlePriceTextField = new JTextFieldValidate();
		allTotlePriceTextField.setHorizontalAlignment(SwingConstants.CENTER);
		allTotlePriceTextField.setEnabled(false);
		getContentPane().add(allTotlePriceTextField, "16, 15, 2, 1, fill, default");
		allTotlePriceTextField.setColumns(TEN);
		
		//重置按钮
		resetButton = new JButton("重置");
		resetButton.setFont(new Font("宋体", Font.BOLD, TWELVE));
		getContentPane().add(resetButton, "3, 16, 2, 1, fill, center");
		
		//关闭按钮
		closeButton = new JButton("关闭");
		closeButton.setFont(new Font("宋体", Font.BOLD, TWELVE));
		getContentPane().add(closeButton, "6, 16, 2, 1, fill, center");
		
		//确定按钮
		sureInsertButton = new JButton("确定录入");
		sureInsertButton.setFont(new Font("宋体", Font.BOLD, TWELVE));
		getContentPane().add(sureInsertButton, "11, 16, 2, 1, fill, center");

		pack();
	}
	
	/**
	 * 动态计算总费用
	 * @param name
	 * @param value
	 */
	public void sum(){
		//将文本框值放入vo中
		setPackingYokeVo();
		//通过id查询纸纤包装单价信息
		if(priceFibelPaperPackingEntity!=null){
			//计算纸箱总包装费
			setPaperTotle();
			//计算包装袋总包装费
			setFibelBag();
			//计算总包装费
			setTotle();
		}
	}

	/**
	 * 计算总包装费
	 */
	private void setTotle() {
		// TODO Auto-generated method stub
		vo.setTotle(vo.getPaperBoxTotlePrice().add(vo.getFibelBagTotlePrice()).add(vo.getOther()==null?BigDecimal.ZERO:vo.getOther()));
		vo.setTotle(vo.getDiscount()==null || vo.getDiscount().compareTo(BigDecimal.ZERO)==0?vo.getTotle():vo.getTotle().multiply(vo.getDiscount()));
		this.getAllTotlePriceTextField().setText(vo.getTotle().toString());
	}

	/**
	 * 将文本框的内容封装到PackingYokeVo中
	 * @param vo2
	 */
	private void setPackingYokeVo() {
		// TODO Auto-generated method stub
				if(this.getPaperBoxOneTextField().getText()!=null&&!("".equals(this.getPaperBoxOneTextField().getText()))){
					vo.setPaperBoxOne(Integer.parseInt(this.getPaperBoxOneTextField().getText().trim()));
				}else{
					vo.setPaperBoxOne(0);
				}
				if(this.getPaperBoxTwoTextField().getText()!=null&&!("".equals(this.getPaperBoxTwoTextField().getText()))){
					vo.setPaperBoxTwo(Integer.parseInt(this.getPaperBoxTwoTextField().getText().trim()));
				}else{
					vo.setPaperBoxTwo(0);
				}
				if(this.getPaperBoxThreeTextField().getText()!=null&&!("".equals(this.getPaperBoxThreeTextField().getText()))){
					vo.setPaperBoxThree(Integer.parseInt(this.getPaperBoxThreeTextField().getText().trim()));
				}else{
					vo.setPaperBoxThree(0);
				}
				if(this.getPaperBoxFourTextField().getText()!=null&&!("".equals(this.getPaperBoxFourTextField().getText()))){
					vo.setPaperBoxFour(Integer.parseInt(this.getPaperBoxFourTextField().getText().trim()));
				}else{
					vo.setPaperBoxFour(0);
				}
				if(this.getFibelBagOneBlueTextField().getText()!=null&&!("".equals(this.getFibelBagOneBlueTextField().getText()))){
					vo.setFibelBagBlueOne(Integer.parseInt(this.getFibelBagOneBlueTextField().getText().trim()));
				}else{
					vo.setFibelBagBlueOne(0);
				}
				if(this.getFibelBagTwoBlueTextField().getText()!=null&&!("".equals(this.getFibelBagTwoBlueTextField().getText()))){
					vo.setFibelBagBlueTwo(Integer.parseInt(this.getFibelBagTwoBlueTextField().getText().trim()));
				}else{
					vo.setFibelBagBlueTwo(0);
				}
				if(this.getFibelBagThreeBlueTextField().getText()!=null&&!("".equals(this.getFibelBagThreeBlueTextField().getText()))){
					vo.setFibelBagBlueThree(Integer.parseInt(this.getFibelBagThreeBlueTextField().getText().trim()));
				}else{
					vo.setFibelBagBlueThree(0);
				}
				if(this.getFibelBagFourBlueTextField().getText()!=null&&!("".equals(this.getFibelBagFourBlueTextField().getText()))){
					vo.setFibelBagBlueFour(Integer.parseInt(this.getFibelBagFourBlueTextField().getText().trim()));
				}else{
					vo.setFibelBagBlueFour(0);
				}
				if(this.getFibelBagOneWhiteTextField().getText()!=null&&!("".equals(this.getFibelBagOneWhiteTextField().getText()))){
					vo.setFibelBagWhiteOne(Integer.parseInt(this.getFibelBagOneWhiteTextField().getText().trim()));
				}else{
					vo.setFibelBagWhiteOne(0);
				}
				if(this.getFibelBagTwoWhiteTextField().getText()!=null&&!("".equals(this.getFibelBagTwoWhiteTextField().getText()))){
					vo.setFibelBagWhiteTwo(Integer.parseInt(this.getFibelBagTwoWhiteTextField().getText().trim()));
				}else{
					vo.setFibelBagWhiteTwo(0);
				}
				if(this.getFibelBagThreeWhiteTextField().getText()!=null&&!("".equals(this.getFibelBagThreeWhiteTextField().getText()))){
					vo.setFibelBagWhiteThree(Integer.parseInt(this.getFibelBagThreeWhiteTextField().getText().trim()));
				}else{
					vo.setFibelBagWhiteThree(0);
				}
				if(this.getFibelBagFourWhiteTextField().getText()!=null&&!("".equals(this.getFibelBagFourWhiteTextField().getText()))){
					vo.setFibelBagWhiteFour(Integer.parseInt(this.getFibelBagFourWhiteTextField().getText().trim()));
				}else{
					vo.setFibelBagWhiteFour(0);
				}
				if(this.getFibelBagFiveWhiteTextField().getText()!=null&&!("".equals(this.getFibelBagFiveWhiteTextField().getText()))){
					vo.setFibelBagWhiteFiveCloth(Integer.parseInt(this.getFibelBagFiveWhiteTextField().getText().trim()));
				}else{
					vo.setFibelBagWhiteFiveCloth(0);
				}
				if(this.getFibelBagSixWhiteTextField().getText()!=null&&!("".equals(this.getFibelBagSixWhiteTextField().getText()))){
					vo.setFibelBagWhiteSixCloth(Integer.parseInt(this.getFibelBagSixWhiteTextField().getText().trim()));
				}else{
					vo.setFibelBagWhiteSixCloth(0);
				}
				if(this.getBufferLabel().getText()!=null&&!("".equals(this.getBufferLabel().getText()))){
					vo.setBuffer(new BigDecimal(this.getBufferLabel().getText().trim()));
				}else{
					vo.setBuffer(BigDecimal.ZERO);
					vo.setOther(BigDecimal.ZERO);
				}
				if(this.getDiscountLabel().getText()!=null&&!("".equals(this.getDiscountLabel().getText()))){
					vo.setDiscount(new BigDecimal(this.getDiscountLabel().getText().trim()));
				}else{
					vo.setDiscount(BigDecimal.ZERO);
	}
	}

	/**
	 * 计算纤袋总包装费
	 */
	private void setFibelBag() {
		BigDecimal fibelBagTotlePrice=new BigDecimal(
				priceFibelPaperPackingEntity.getFibelBagOneBlue().doubleValue()*vo.getFibelBagBlueOne().intValue()+
				priceFibelPaperPackingEntity.getFibelBagTwoBlue().doubleValue()*vo.getFibelBagBlueTwo().intValue()+
				priceFibelPaperPackingEntity.getFibelBagThreeBlue().doubleValue()*vo.getFibelBagBlueThree().intValue()+
				priceFibelPaperPackingEntity.getFibelBagFourBlue().doubleValue()*vo.getFibelBagBlueFour().intValue()+
				priceFibelPaperPackingEntity.getFibelBagOneWhite().doubleValue()*vo.getFibelBagWhiteOne().intValue()+
				priceFibelPaperPackingEntity.getFibelBagTwoWhite().doubleValue()*vo.getFibelBagWhiteTwo().intValue()+
				priceFibelPaperPackingEntity.getFibelBagThreeWhite().doubleValue()*vo.getFibelBagWhiteThree().intValue()+
				priceFibelPaperPackingEntity.getFibelBagFourWhite().doubleValue()*vo.getFibelBagWhiteFour().intValue()+
				priceFibelPaperPackingEntity.getFibelBagFiveWhite().doubleValue()*vo.getFibelBagWhiteFiveCloth().intValue()+
				priceFibelPaperPackingEntity.getFibelBagSixWhite().doubleValue()*vo.getFibelBagWhiteSixCloth().intValue()
				);
		vo.setFibelBagTotlePrice(fibelBagTotlePrice);
		this.getFibelBagTotlePriceTextField().setText(fibelBagTotlePrice.toString());
	}

	/**
	 * 计算纸箱总包装费
	 */
	private void setPaperTotle() {
		BigDecimal paperBoxTotlePrice=new BigDecimal(
				priceFibelPaperPackingEntity.getPaperBoxOne().doubleValue()*vo.getPaperBoxOne().intValue()+
				priceFibelPaperPackingEntity.getPaperBoxTwo().doubleValue()*vo.getPaperBoxTwo().intValue()+
				priceFibelPaperPackingEntity.getPaperBoxThree().doubleValue()*vo.getPaperBoxThree().intValue()+
				priceFibelPaperPackingEntity.getPaperBoxFour().doubleValue()*vo.getPaperBoxFour().intValue()
				);
		vo.setPaperBoxTotlePrice(paperBoxTotlePrice);
		this.getPaperBoxTotlePriceTextField().setText(paperBoxTotlePrice.toString());
	}

	/**
	 * 页面显示时调用
	 * ISSUE-4391
	 * @author	157229-zxy 
	 * @date 2013-11-18  
	 */
	@Override
	public void show(){
		super.show();
		initData();			//因为确定的时候把值反写回bean了,所以不会还原
	}
	
	private void initData() {
		//获取其它参数合集
		id=getId();
		//获取单价信息
		priceFibelPaperPackingEntity=waybillService.queryPriceFibelPaperPackingEntity(id);
		// TODO Auto-generated method stub
		paperBoxOneTextField.setText(bean.getPaperBoxOne()==null?"":bean.getPaperBoxOne().toString());
		paperBoxTwoTextField.setText(bean.getPaperBoxTwo()==null?"":bean.getPaperBoxTwo().toString());
		paperBoxThreeTextField.setText(bean.getPaperBoxThree()==null?"":bean.getPaperBoxThree().toString());
		paperBoxFourTextField.setText(bean.getPaperBoxFour()==null?"":bean.getPaperBoxFour().toString());
		fibelBagOneBlueTextField.setText(bean.getFibelBagBlueOne()==null?"":bean.getFibelBagBlueOne().toString());
		fibelBagTwoBlueTextField.setText(bean.getFibelBagBlueTwo()==null?"":bean.getFibelBagBlueTwo().toString());
		fibelBagThreeBlueTextField.setText(bean.getFibelBagBlueThree()==null?"":bean.getFibelBagBlueThree().toString());
		fibelBagFourBlueTextField.setText(bean.getFibelBagBlueFour()==null?"":bean.getFibelBagBlueFour().toString());
		fibelBagOneWhiteTextField.setText(bean.getFibelBagWhiteOne()==null?"":bean.getFibelBagWhiteOne().toString());
		fibelBagTwoWhiteTextField.setText(bean.getFibelBagWhiteTwo()==null?"":bean.getFibelBagWhiteTwo().toString());
		fibelBagThreeWhiteTextField.setText(bean.getFibelBagWhiteThree()==null?"":bean.getFibelBagWhiteThree().toString());
		fibelBagFourWhiteTextField.setText(bean.getFibelBagWhiteFour()==null?"":bean.getFibelBagWhiteFour().toString());
		fibelBagFiveWhiteTextField.setText(bean.getFibelBagWhiteClothFive()==null?"":bean.getFibelBagWhiteClothFive().toString());
		fibelBagSixWhiteTextField.setText(bean.getFibelBagWhiteClothSix()==null?"":bean.getFibelBagWhiteClothSix().toString());
//		paperBoxTotlePriceTextField.setText(bean.getPaperBoxTotlePrice()==null?"":bean.getPaperBoxTotlePrice().toString());
//		fibelBagTotlePriceTextField.setText(bean.getFibelBagTotlePrice()==null?"":bean.getFibelBagTotlePrice().toString());
//		allTotlePriceTextField.setText(bean.getPackingTotle()==null?"":bean.getPackingTotle().toString());
		sum();
//		if(vo.getPaperBoxTotlePrice().doubleValue()==0&&vo.getFibelBagTotlePrice().doubleValue()==0){
//			if(bean.getOtherTotle()!=null&&vo.getOther().doubleValue()==bean.getOtherTotle().doubleValue()){
//				initFirstData();
//			}else if(bean.getOtherTotle()==null){
//				initFirstData();
//			}
//		}
	}

	/**
	 * getWaybillEditUI
	 * @return WaybillEditUI
	 */ 
	public WaybillRFCUI getWaybillRFCUI() {
		return waybillRFCUI;
	}

//------------------------------------------------
	public JTextFieldValidate getPaperBoxOneTextField() {
		return paperBoxOneTextField;
	}

	public void setPaperBoxOneTextField(JTextFieldValidate paperBoxOneTextField) {
		this.paperBoxOneTextField = paperBoxOneTextField;
	}

	public JTextFieldValidate getPaperBoxTwoTextField() {
		return paperBoxTwoTextField;
	}

	public void setPaperBoxTwoTextField(JTextFieldValidate paperBoxTwoTextField) {
		this.paperBoxTwoTextField = paperBoxTwoTextField;
	}

	public JTextFieldValidate getPaperBoxThreeTextField() {
		return paperBoxThreeTextField;
	}

	public void setPaperBoxThreeTextField(JTextFieldValidate paperBoxThreeTextField) {
		this.paperBoxThreeTextField = paperBoxThreeTextField;
	}

	public JTextFieldValidate getPaperBoxFourTextField() {
		return paperBoxFourTextField;
	}

	public void setPaperBoxFourTextField(JTextFieldValidate paperBoxFourTextField) {
		this.paperBoxFourTextField = paperBoxFourTextField;
	}

	public JTextFieldValidate getFibelBagOneBlueTextField() {
		return fibelBagOneBlueTextField;
	}

	public void setFibelBagOneBlueTextField(JTextFieldValidate fibelBagOneBlueTextField) {
		this.fibelBagOneBlueTextField = fibelBagOneBlueTextField;
	}

	public JTextFieldValidate getFibelBagTwoBlueTextField() {
		return fibelBagTwoBlueTextField;
	}

	public void setFibelBagTwoBlueTextField(JTextFieldValidate fibelBagTwoBlueTextField) {
		this.fibelBagTwoBlueTextField = fibelBagTwoBlueTextField;
	}

	public JTextFieldValidate getFibelBagThreeBlueTextField() {
		return fibelBagThreeBlueTextField;
	}

	public void setFibelBagThreeBlueTextField(JTextFieldValidate fibelBagThreeBlueTextField) {
		this.fibelBagThreeBlueTextField = fibelBagThreeBlueTextField;
	}

	public JTextFieldValidate getFibelBagFourBlueTextField() {
		return fibelBagFourBlueTextField;
	}

	public void setFibelBagFourBlueTextField(JTextFieldValidate fibelBagFourBlueTextField) {
		this.fibelBagFourBlueTextField = fibelBagFourBlueTextField;
	}

	public JTextFieldValidate getFibelBagOneWhiteTextField() {
		return fibelBagOneWhiteTextField;
	}

	public void setFibelBagOneWhiteTextField(JTextFieldValidate fibelBagOneWhiteTextField) {
		this.fibelBagOneWhiteTextField = fibelBagOneWhiteTextField;
	}

	public JTextFieldValidate getFibelBagTwoWhiteTextField() {
		return fibelBagTwoWhiteTextField;
	}

	public void setFibelBagTwoWhiteTextField(JTextFieldValidate fibelBagTwoWhiteTextField) {
		this.fibelBagTwoWhiteTextField = fibelBagTwoWhiteTextField;
	}

	public JTextFieldValidate getFibelBagThreeWhiteTextField() {
		return fibelBagThreeWhiteTextField;
	}

	public void setFibelBagThreeWhiteTextField(
			JTextFieldValidate fibelBagThreeWhiteTextField) {
		this.fibelBagThreeWhiteTextField = fibelBagThreeWhiteTextField;
	}

	public JTextFieldValidate getFibelBagFourWhiteTextField() {
		return fibelBagFourWhiteTextField;
	}

	public void setFibelBagFourWhiteTextField(JTextFieldValidate fibelBagFourWhiteTextField) {
		this.fibelBagFourWhiteTextField = fibelBagFourWhiteTextField;
	}

	public JTextFieldValidate getFibelBagFiveWhiteTextField() {
		return fibelBagFiveWhiteTextField;
	}

	public void setFibelBagFiveWhiteTextField(JTextFieldValidate fibelBagFiveWhiteTextField) {
		this.fibelBagFiveWhiteTextField = fibelBagFiveWhiteTextField;
	}

	public JTextFieldValidate getFibelBagSixWhiteTextField() {
		return fibelBagSixWhiteTextField;
	}

	public void setFibelBagSixWhiteTextField(JTextFieldValidate fibelBagSixWhiteTextField) {
		this.fibelBagSixWhiteTextField = fibelBagSixWhiteTextField;
	}

	public JTextFieldValidate getPaperBoxTotlePriceTextField() {
		return paperBoxTotlePriceTextField;
	}

	public void setPaperBoxTotlePriceTextField(
			JTextFieldValidate paperBoxTotlePriceTextField) {
		this.paperBoxTotlePriceTextField = paperBoxTotlePriceTextField;
	}

	public JTextFieldValidate getFibelBagTotlePriceTextField() {
		return fibelBagTotlePriceTextField;
	}

	public void setFibelBagTotlePriceTextField(
			JTextFieldValidate fibelBagTotlePriceTextField) {
		this.fibelBagTotlePriceTextField = fibelBagTotlePriceTextField;
	}

	public JTextFieldValidate getAllTotlePriceTextField() {
		return allTotlePriceTextField;
	}

	public void setAllTotlePriceTextField(JTextFieldValidate allTotlePriceTextField) {
		this.allTotlePriceTextField = allTotlePriceTextField;
	}

	public void setWaybillRFCUI(WaybillRFCUI waybillRFCUI) {
		this.waybillRFCUI = waybillRFCUI;
	}
	
//	public IBinder<PackingYokeVo> getPackingYokeBinder() {
//		return packingYokeBinder;
//	}
//
//	public void setPackingYokeBinder(IBinder<PackingYokeVo> packingYokeBinder) {
//		packingYokeBinder = packingYokeBinder;
//	}
	
	public PackingYokeVo getVo() {
		return vo;
	}

	public void setVo(PackingYokeVo vo) {
		this.vo = vo;
	}

	public JTextFieldValidate getBufferLabel() {
		return bufferLabel;
	}

	public void setBufferLabel(JTextFieldValidate bufferLabel) {
		this.bufferLabel = bufferLabel;
	}

	public JTextFieldValidate getDiscountLabel() {
		return discountLabel;
	}

	public void setDiscountLabel(JTextFieldValidate discountLabel) {
		this.discountLabel = discountLabel;
	}
}