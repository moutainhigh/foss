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
package com.deppon.foss.module.pickup.creating.client.ui.customer;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.CheckBoxList;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.EnterYokeSearchPanel;
import com.deppon.foss.module.pickup.common.client.utils.FloatDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberValidate;
import com.deppon.foss.module.pickup.common.client.vo.LabeledGoodEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.action.DialogCloseAllAction;
import com.deppon.foss.module.pickup.creating.client.action.WoodYokeEnterAction;
import com.deppon.foss.module.pickup.creating.client.action.WoodYokeResetAction;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.listener.SalverGoodsAmountListener;
import com.deppon.foss.module.pickup.creating.client.listener.SalverGoodsPiecesListener;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.vo.WoodYokeVo;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 录入代打木架信息
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-10-17 上午9:13:39
 */
@ContainerSeq(seq=1)
public class EnterYokeInfoDialog extends JDialog {

	private static final int FONTSIZE14 = 14;
	//COLUMNS NUMBER
	private static final int TEN = 10;
	private static final int FOUR = 4;
	
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 8802287401540527591L;
	
	private static final Log log = LogFactory.getLog(EnterYokeInfoDialog.class);
	
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(EnterYokeInfoDialog.class); 
	
	//绑定bean
	IBinder<WoodYokeVo> woodYokeBinder;
	
	//运单界面
	WaybillEditUI waybillEditUI;

	// ===========增加非木包装费/lianhe/2016年12月27日/start=======TODO
	//非木包装费
	@FocusSeq(seq=1)
	private JTextFieldValidate nonWoodPackingAmount;
	// ===========增加非木包装费/lianhe/2016年12月27日/end=======
	
	// 代打木架部门
	private JTextFieldValidate txtWoodDept;

	// 打木架货物件数
	@FocusSeq(seq=2)
	private JTextFieldValidate txtYokeGoodsPieces;

	// 代打木架要求
	@FocusSeq(seq=3)
	private JTextFieldValidate txtYokeRequire;

	// 木架货物尺寸
	@FocusSeq(seq=4)
	private JTextFieldValidate txtYokeGoodsSize;

	// 木架货物体积
	@FocusSeq(seq=5)
	private JTextFieldValidate txtYokeGoodsVolume;
	
	// 木架货物体积是否按键修改
	private boolean goodsVolumeChanged = false;
	
	// 打木箱货物件数
	@FocusSeq(seq=6)
	private JTextFieldValidate txtBoxGoodsPieces;

	// 代打木箱要求
	@FocusSeq(seq=7)
	private JTextFieldValidate txtBoxRequire;

	// 代打木箱货物尺寸
	@FocusSeq(seq=8)
	private JTextFieldValidate txtBoxGoodsSize;

	// 代打木箱货物体积
	@FocusSeq(seq=9)
	private JTextFieldValidate txtBoxGoodsVolume;
	
	// 木箱货物体积是否按键修改
	private boolean boxVolumeChanged = false;
	
	//zxy 20131118 ISSUE-4391 start 新增：木托属性
	// 打木托货物件数
	@FocusSeq(seq = 10)
	private JTextFieldValidate txtSalverGoodsPieces;

	// 代打木托要求
	@FocusSeq(seq = 11)
	private JTextFieldValidate txtSalverRequire;

	// 代打木托费用
	@FocusSeq(seq = 12)
	private JTextFieldValidate txtSalverGoodsAmount;

	/**
	  * 流水号选中列表
	  */
	public CheckBoxList listSalver;
	
	/**
	 * 流水号列表数据模型
	 */
	private SerialNoListModel listModelSalver;
	
	private String curSalverGoodsPieces = "";

	private EnterYokeSearchPanel enterYokeSearchPanel = null;  //zxy 20131224 MANA-285 新增
	//zxy 20131118 ISSUE-4391 end 新增：木托属性

	/**
	 * 重置
	 */
	@ButtonAction(icon = "preview.png", shortcutKey = "", type = WoodYokeResetAction.class)
	private JButton btnReset;

	/**
	 * confirm
	 */
	@ButtonAction(icon = "preview.png", shortcutKey = "", type = WoodYokeEnterAction.class)
	@FocusSeq(seq=13)
	private JButton btnConfirm;
	
	/**
	 * 关闭
	 */
	@ButtonAction(icon = "preview.png", shortcutKey = "", type = DialogCloseAllAction.class)
	private JButton btnClose;

	/**
	 * 构造函数
	 * @param waybillEditUI
	 */
	public EnterYokeInfoDialog(WaybillEditUI waybillEditUI) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		this.waybillEditUI = waybillEditUI;

		init();
		bind();
//		initData(); zxy 20131118 ISSUE-4391 修改：由show函数调用
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
				ColumnSpec.decode("63dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(34dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(34dlu;default):grow"),
				ColumnSpec.decode("20dlu:grow"),
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(89dlu;default):grow"),
				ColumnSpec.decode("20dlu:grow"),
				ColumnSpec.decode("left:40dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("60dlu:grow"),
				ColumnSpec.decode("20dlu:grow"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("90dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("1dlu:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("max(9dlu;default)"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(4dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("10dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("10dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("4dlu:grow"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("1dlu"),}));
		// ===========添加“非木包装费”文本框/lianhe/2016年12月25日/start/=======
		//非木包装费
		JLabel lblnonWoodPackingInfo = new JLabel(i18n.get("foss.gui.creating.nonWoodEnterYokeInfoDialog.title"));
		lblnonWoodPackingInfo.setFont(new Font("宋体", Font.BOLD, 14));
		getContentPane().add(lblnonWoodPackingInfo, "2, 2, 5, 1");
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		getContentPane().add(separator_1, "2, 3, 6, 1");
		
		JLabel lblnonWoodPackingAmount = new JLabel(i18n.get("foss.gui.creating.nonWoodEnterYokeInfoDialog.nonWoodPackingAmount.label")+"：");
		getContentPane().add(lblnonWoodPackingAmount, "2, 4, left, default");
		
		nonWoodPackingAmount = new JTextFieldValidate();
		nonWoodPackingAmount.setColumns(TEN);
		nonWoodPackingAmount.setEditable(true);
		getContentPane().add(nonWoodPackingAmount, "3, 4, 5, 1, fill, default");
		//设置最大输入6位(含小数)
		nonWoodPackingAmount.setDocument(new FloatDocument(NumberConstants.NUMBER_6, 2));
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.BLACK);
		getContentPane().add(separator_2, "2, 6, 6, 1");
		//离焦校验输入内是否合法
		nonWoodPackingAmount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(StringUtils.isNotEmpty(nonWoodPackingAmount.getText())){
					if (!NumberValidate.checkIntOrFloat(nonWoodPackingAmount.getText())){
						MsgBox.showInfo(i18n.get("foss.gui.creating.enterYokeInfoDialog.msgbox.inputNumber"));
						nonWoodPackingAmount.setText(null);
					}
					//判断非木包装费长度不为空
					if (nonWoodPackingAmount.getText().length() > 6) {
						MsgBox.showInfo("非木包装费只能输入0~6位数字");
						nonWoodPackingAmount.setText(null);
					}
				}
			}
		});
		// ===========添加“非木包装费”文本框/lianhe/2016年12月25日/end=======
		
		//zxy 20131118 ISSUE-4391 end 修改：布局修改
		//代打木架信息录入
		JLabel lblTitle = new JLabel(i18n.get("foss.gui.creating.enterYokeInfoDialog.title"));
		lblTitle.setFont(new Font("宋体", Font.BOLD, FONTSIZE14));
		getContentPane().add(lblTitle, "2, 8, 5, 1, left, default");

		JSeparator separator3 = new JSeparator();
		separator3.setForeground(Color.BLACK);
		getContentPane().add(separator3, "2, 9, 6, 1, fill, default");

		//代打木架部门
		JLabel lblWoodDept = new JLabel(i18n.get("foss.gui.creating.enterYokeInfoDialog.woodDept.label")+"：");
		getContentPane().add(lblWoodDept, "2, 10, left, default");

		txtWoodDept = new JTextFieldValidate();
		txtWoodDept.setEditable(false);
		getContentPane().add(txtWoodDept, "3, 10, 5, 1, fill, default");
		txtWoodDept.setColumns(TEN);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		getContentPane().add(separator, "2, 12, 17, 1");

		//木架货物件数
		JLabel lblNewLabel2 = new JLabel(i18n.get("foss.gui.creating.enterYokeInfoDialog.yokeGoodsPieces.label")+"：");
		getContentPane().add(lblNewLabel2, "2, 14, left, default");

		// 打木架货物件数
		txtYokeGoodsPieces = new JTextFieldValidate();
		txtYokeGoodsPieces.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(StringUtils.isNotEmpty(txtYokeGoodsPieces.getText())){
					if (!NumberValidate.checkIsAllNumber(txtYokeGoodsPieces.getText())){
						MsgBox.showInfo(i18n.get("foss.gui.creating.enterYokeInfoDialog.msgbox.inputNumber"));
						txtYokeGoodsPieces.setText(null);
					}
				}
			}
		});
		getContentPane().add(txtYokeGoodsPieces, "3, 14, 4, 1, fill, default");
		txtYokeGoodsPieces.setColumns(TEN);
		NumberDocument pieceDocument = new NumberDocument(FOUR);
		txtYokeGoodsPieces.setDocument(pieceDocument);


		//代打木架要求
		JLabel label2 = new JLabel(i18n.get("foss.gui.creating.enterYokeInfoDialog.yokeRequire.label")+"：");
		getContentPane().add(label2, "8, 14, left, default");

		// 代打木架要求
		txtYokeRequire = new JTextFieldValidate();
		getContentPane().add(txtYokeRequire, "10, 14, fill, default");
		txtYokeRequire.setColumns(TEN);

		//货物尺寸
		JLabel lblNewLabel3 = new JLabel(i18n.get("foss.gui.creating.enterYokeInfoDialog.yokeGoodsSize.label")+"：");
		getContentPane().add(lblNewLabel3, "12, 14, left, default");

		// 木架货物尺寸
		txtYokeGoodsSize = new JTextFieldValidate();
		txtYokeGoodsSize.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(StringUtils.isNotEmpty(txtYokeGoodsSize.getText()))
				{
					if (!NumberValidate.checkIsGoodsSize(txtYokeGoodsSize.getText())) {
						StringBuffer str = new StringBuffer(i18n.get("foss.gui.creating.waybillDescriptor.size.rule"));
						str.append("(\n").append(i18n.get("foss.gui.creating.waybillDescriptor.example"));
						str.append("：0.5*0.5*0.5*2").append(i18n.get("foss.gui.creating.waybillDescriptor.or")).append("\n");
						str.append("0.5*0.5*0.5*2+1*1*1*5").append(i18n.get("foss.gui.creating.waybillDescriptor.or")).append("\n");
						str.append("0.5*0.5*0.5*2+1*1*1*5-0.3*0.3*0.6*1)");
						MsgBox.showInfo(str.toString());
					}else
					{
						//计算打木架体积
						//将计算出来的体积设置到打木架体积控件中
						txtYokeGoodsVolume.setText(calculateVolume(txtYokeGoodsSize.getText()));
					}
				}
			}
		});
		getContentPane().add(txtYokeGoodsSize, "14, 14, fill, default");
		txtYokeGoodsSize.setColumns(TEN);

		JLabel label4 = new JLabel(i18n.get("foss.gui.creating.enterYokeInfoDialog.yokeGoodsVolume.label")+"：");
		getContentPane().add(label4, "16, 14, left, default");

		// 木架货物体积
		txtYokeGoodsVolume = new JTextFieldValidate();
		// 木架体积失去焦点监听
		txtYokeGoodsVolume.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(StringUtils.isNotEmpty(txtYokeGoodsVolume.getText())&&goodsVolumeChanged){
					if (!NumberValidate.checkIntOrFloat(txtYokeGoodsVolume.getText())){
						MsgBox.showInfo(i18n.get("foss.gui.creating.enterYokeInfoDialog.msgbox.inputNumber"));
						txtYokeGoodsVolume.setText(null);
					}else{
						if(!StringUtils.isEmpty(txtYokeGoodsVolume.getText()))
						{
							txtYokeGoodsVolume.setText(reCalculateVolume(txtYokeGoodsSize.getText(),txtYokeGoodsVolume.getText()));
							goodsVolumeChanged = false;
						}
					}
				}
			}
		});
		// 木架体积键盘输入监听
		txtYokeGoodsVolume.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				goodsVolumeChanged = true;
			}
		});
		getContentPane().add(txtYokeGoodsVolume, "18, 14, fill, default");
		txtYokeGoodsVolume.setColumns(TEN);
		FloatDocument yokeVolume = new FloatDocument(NumberConstants.NUMBER_8,2);
		txtYokeGoodsVolume.setDocument(yokeVolume);
		

		JSeparator separator1 = new JSeparator();
		separator1.setForeground(Color.BLACK);
		getContentPane().add(separator1, "2, 16, 17, 1");

		//木箱货物件数
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.enterYokeInfoDialog.boxGoodsPieces.label")+"：");
		getContentPane().add(label1, "2, 18, left, default");

		// 打木箱货物件数
		txtBoxGoodsPieces = new JTextFieldValidate();
		// 木箱件数失去焦点监听
		txtBoxGoodsPieces.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(StringUtils.isNotEmpty(txtBoxGoodsPieces.getText())){
					if (!NumberValidate.checkIsAllNumber(txtBoxGoodsPieces.getText())){
						MsgBox.showInfo(i18n.get("foss.gui.creating.enterYokeInfoDialog.msgbox.inputNumber"));
						txtBoxGoodsPieces.setText(null);
					}
				}
			}
		});
		getContentPane().add(txtBoxGoodsPieces, "3, 18, 4, 1, fill, default");
		txtBoxGoodsPieces.setColumns(TEN);
		NumberDocument boxGoodsPieces = new NumberDocument(FOUR);
		txtBoxGoodsPieces.setDocument(boxGoodsPieces);

		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.enterYokeInfoDialog.boxRequire.label")+"：");
		getContentPane().add(label3, "8, 18, left, default");

		// 代打木箱要求
		txtBoxRequire = new JTextFieldValidate();
		getContentPane().add(txtBoxRequire, "10, 18, fill, default");
		txtBoxRequire.setColumns(TEN);

		JLabel lblNewLabel4 = new JLabel(i18n.get("foss.gui.creating.enterYokeInfoDialog.boxGoodsSize.label")+"：");
		getContentPane().add(lblNewLabel4, "12, 18, left, default");

		// 代打木箱货物尺寸
		txtBoxGoodsSize = new JTextFieldValidate();
		// 木箱尺寸失去焦点监听
		txtBoxGoodsSize.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(StringUtils.isNotEmpty(txtBoxGoodsSize.getText()))
				{
					// 判断尺寸输入格式是否合法
					if (!NumberValidate.checkIsGoodsSize(txtBoxGoodsSize.getText())) {
						StringBuffer str = new StringBuffer(i18n.get("foss.gui.creating.waybillDescriptor.size.rule"));
						str.append("(\n").append(i18n.get("foss.gui.creating.waybillDescriptor.example"));
						str.append("：0.5*0.5*0.5*2").append(i18n.get("foss.gui.creating.waybillDescriptor.or")).append("\n");
						str.append("0.5*0.5*0.5*2+1*1*1*5").append(i18n.get("foss.gui.creating.waybillDescriptor.or")).append("\n");
						str.append("0.5*0.5*0.5*2+1*1*1*5-0.3*0.3*0.6*1)");
						MsgBox.showInfo(str.toString());
					} else {
						//计算打木架体积
						//将计算出来的体积设置到打木架体积控件中
						txtBoxGoodsVolume.setText(calculateVolume(txtBoxGoodsSize.getText()));
					}
				}
			}
		});
		getContentPane().add(txtBoxGoodsSize, "14, 18, fill, default");
		txtBoxGoodsSize.setColumns(TEN);

		JLabel label6 = new JLabel(i18n.get("foss.gui.creating.enterYokeInfoDialog.boxGoodsVolume.label")+"：");
		getContentPane().add(label6, "16, 18, left, default");

		// 代打木箱货物体积
		txtBoxGoodsVolume = new JTextFieldValidate();
		// 木箱体积失去焦点监听
		txtBoxGoodsVolume.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(StringUtils.isNotEmpty(txtBoxGoodsVolume.getText())&&boxVolumeChanged){
					if (!NumberValidate.checkIntOrFloat(txtBoxGoodsVolume.getText())){
						MsgBox.showInfo(i18n.get("foss.gui.creating.enterYokeInfoDialog.msgbox.inputNumber"));
						txtBoxGoodsVolume.setText(null);
					}else{
						if(!StringUtils.isEmpty(txtBoxGoodsVolume.getText()))
						{
							txtBoxGoodsVolume.setText(reCalculateVolume(txtBoxGoodsSize.getText(),txtBoxGoodsVolume.getText()));
							boxVolumeChanged = false;
						}
					}
				}
			}
		});
		// 木箱体积键盘输入监听
		txtBoxGoodsVolume.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				// 有键盘操作设置体积已修改
				boxVolumeChanged = true;
			}
		});
		getContentPane().add(txtBoxGoodsVolume, "18, 18, fill, default");
		txtBoxGoodsVolume.setColumns(TEN);
		FloatDocument boxVolume = new FloatDocument(NumberConstants.NUMBER_8,2);
		txtBoxGoodsVolume.setDocument(boxVolume);
		
		
		
		//zxy 20131118 ISSUE-4391 start 新增：创建木托组建
		
		// 代打木托件数
		JLabel lbSalverGoodsPieces = new JLabel(
				i18n.get("foss.gui.creating.enterYokeInfoDialog.salverGoodsPieces.label")
						+ "：");
		getContentPane().add(lbSalverGoodsPieces, "2, 22, left, default");

		// 打木托件数
		txtSalverGoodsPieces = new JTextFieldValidate();

		getContentPane().add(txtSalverGoodsPieces, "3, 22, 4, 1, fill, default");
		txtSalverGoodsPieces.setColumns(TEN);
		NumberDocument salverGoodsPieces = new NumberDocument(FOUR);
		txtSalverGoodsPieces.setDocument(salverGoodsPieces);

		JLabel lbSalverRequire = new JLabel(
				i18n.get("foss.gui.creating.enterYokeInfoDialog.salverRequire.label")
						+ "：");
		getContentPane().add(lbSalverRequire, "8, 22, left, default");

		// 代打木托要求
		txtSalverRequire = new JTextFieldValidate();
		getContentPane().add(txtSalverRequire, "10, 22, fill, default");
		txtSalverRequire.setColumns(TEN);

		

		JLabel lbSalverGoodsAmount = new JLabel(
				i18n.get("foss.gui.creating.enterYokeInfoDialog.salverGoodsAmount.label")
						+ "：");
		getContentPane().add(lbSalverGoodsAmount, "12, 22, left, default");

		// 木托费用
		txtSalverGoodsAmount = new JTextFieldValidate();
		
		if(StringUtils.isNotBlank(this.waybillEditUI.getPictureWaybillType()) && 
				WaybillConstants.WAYBILL_PICTURE.equals(this.waybillEditUI.getPictureWaybillType())){
			txtSalverGoodsPieces.addFocusListener(new SalverGoodsPiecesListener(this.waybillEditUI));
			txtSalverGoodsAmount.addFocusListener(new SalverGoodsAmountListener(this.waybillEditUI));
		}else{
		// 木托件数失去焦点监听
		txtSalverGoodsPieces.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					if (StringUtils.isNotEmpty(txtSalverGoodsPieces.getText())) {
						if (!NumberValidate
								.checkIsAllNumber(txtSalverGoodsPieces
										.getText())) {
							MsgBox.showInfo(i18n
									.get("foss.gui.creating.enterYokeInfoDialog.msgbox.inputNumber"));
							txtSalverGoodsPieces.setText(null);
						} else {
							if(!curSalverGoodsPieces.equals(txtSalverGoodsPieces.getText())){ //防止不做修改但是焦点离开导致费用重新计算问题
								if(txtSalverGoodsPieces.getText().equals("0")){
									//zxy 20131224 MANA-285 start 修改:当托个数输入0时，只把托费用重置成0
//									txtSalverGoodsPieces.setText(null);
//									txtSalverRequire.setText(null);
									txtSalverGoodsAmount.setText("0");
									//zxy 20131224 MANA-285 end 修改:当托个数输入0时，只把托费用重置成0
								}else{
									BigDecimal salverGoodsAmount = calculateSalverFee(txtSalverGoodsPieces
											.getText());
									txtSalverGoodsAmount.setText(salverGoodsAmount
											.toString());
								}
								curSalverGoodsPieces = txtSalverGoodsPieces.getText();
							}
						}
					} else {
						txtSalverGoodsPieces.setText(null);
						txtSalverRequire.setText(null);
						txtSalverGoodsAmount.setText(null);
					}
				} catch (WaybillValidateException w) {
					MsgBox.showInfo(w.getMessage());
					txtSalverGoodsPieces.setText(null);
				}
			}

		});
	
		txtSalverGoodsAmount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					if (StringUtils.isNotEmpty(txtSalverGoodsPieces.getText())) {
						if (StringUtils.isNotEmpty(txtSalverGoodsAmount
								.getText())) {
							if (!NumberValidate.checkIntOrFloat(txtSalverGoodsAmount.getText())) {
								MsgBox.showInfo(i18n.get("foss.gui.creating.enterYokeInfoDialog.msgbox.inputNumber"));
								txtSalverGoodsAmount.setText(null);
							}
						}
					}
				} catch (WaybillValidateException w) {
					MsgBox.showInfo(w.getMessage());
					txtSalverGoodsAmount.setText(null);
				}
			}
		});
		}
		getContentPane().add(txtSalverGoodsAmount, "14, 22, fill, center");
		txtSalverGoodsAmount.setColumns(TEN);
		FloatDocument salverVolume = new FloatDocument(NumberConstants.NUMBER_8, 2);
		txtSalverGoodsAmount.setDocument(salverVolume);
		
		//木托列表
		JLabel lblSalverList = new JLabel(i18n.get("foss.gui.creating.enterYokeInfoDialog.salverSerialNos.label")+"：");
		getContentPane().add(lblSalverList, "11, 24, 2, 1, left, default");
		
		listModelSalver = new SerialNoListModel();
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "14, 24, 5, 3, fill, fill");
		listSalver = new CheckBoxList();
		listSalver.setBackground(scrollPane.getBackground());
		scrollPane.setViewportView(listSalver);
		listSalver.setModel(listModelSalver);
		
		//木托区间勾选组建
		JLabel lbSalverGoodsAmount1 = new JLabel("批量勾选:");
		getContentPane().add(lbSalverGoodsAmount1, "16, 22, fill, fill");
		enterYokeSearchPanel = new EnterYokeSearchPanel();
		enterYokeSearchPanel.setList(listSalver);
		getContentPane().add(enterYokeSearchPanel, "18, 22, fill, fill");
		
		//zxy 20131118 ISSUE-4391 end 新增：创建木托组建
		

		JSeparator separator2 = new JSeparator();
		separator2.setForeground(Color.BLACK);
		getContentPane().add(separator2, "2, 20, 17, 1");
		
		//重置
		btnReset = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.reset"));
		getContentPane().add(btnReset, "6, 27, 2, 1, center, default");
		
		//关闭
		btnClose = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.close"));
		getContentPane().add(btnClose, "8, 27");

		//确定录入
		btnConfirm = new JButton(i18n.get("foss.gui.creating.enterYokeInfoDialog.confirm.label"));
		getContentPane().add(btnConfirm, "16, 27, 3, 1, center, default");

		pack();
	}
	
	
	/**
	 * 
	 * 计算木架体积
	 * @author 025000-FOSS-helong
	 * @date 2013-3-10 下午03:57:07
	 * @param size
	 */
	private String calculateVolume(String size)
	{
		String volumeParam = getVolumeParam();
		if(StringUtils.isNotEmpty(volumeParam))
		{
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			try {
				Object result = engine.eval(size);
				BigDecimal bigDecimal = new BigDecimal(result.toString());
				bigDecimal = bigDecimal.setScale(NumberConstants.NUMBER_3, BigDecimal.ROUND_HALF_UP);
				BigDecimal m = new BigDecimal(WaybillConstants.VOLUME_M);// 将厘米转换成米
				bigDecimal = bigDecimal.divide(m);
				//乘以参数1.4，参数是可以配置的
				bigDecimal = bigDecimal.multiply(new BigDecimal(volumeParam));
				bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
				//四舍五入后如果变为0.00，那么需要给成默认的0.01，以免丢失体积
				if(bigDecimal.compareTo(BigDecimal.ZERO)==0){
					bigDecimal = new BigDecimal("0.01");
				}
				return bigDecimal.toString();
			} catch (ScriptException e) {
				log.error("ScriptException", e);
				throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.one"), e);
			}
		}else
		{
			return null;
		}
	}
	
	/**
	 * 
	 * 计算木架体积
	 * @author foss-sunrui
	 * @date 2013-3-13 下午5:38:32
	 * @param size
	 * @return
	 * @see
	 */
	private String reCalculateVolume(String size,String volume){
		String volumeParam = getVolumeParam();
		//当没有录入体积的时候才根据录入的体积去乘以配置的体积计算参数
		if(StringUtils.isEmpty(size)&&StringUtils.isNotEmpty(volumeParam)){
			BigDecimal bigDecimal = new BigDecimal(volume.toString());
			bigDecimal = bigDecimal.setScale(NumberConstants.NUMBER_3, BigDecimal.ROUND_HALF_UP);
			//乘以参数1.4，参数是可以配置的
			bigDecimal = bigDecimal.multiply(new BigDecimal(volumeParam));
			bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
			//四舍五入后如果变为0.00，那么需要给成默认的0.01，以免丢失体积
			if(bigDecimal.compareTo(BigDecimal.ZERO)==0){
				bigDecimal = new BigDecimal("0.01");
			}
			return bigDecimal.toString();
		}else{
			//如果录入了尺寸则以尺寸计算的体积为准
			return calculateVolume(size);
		}
	}
	
	/**
	 * 
	 * 获取体积参数
	 * @author 025000-FOSS-helong
	 * @date 2013-3-12 下午02:40:57
	 * @return
	 */
	private String getVolumeParam()
	{
		HashMap<String, IBinder<WaybillPanelVo>> map = waybillEditUI.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();
		
		String param = waybillService.queryGoodsPackingVolume(bean.getReceiveOrgCode(),WaybillConstants.GOODS_PACKING_VOLUME,DictionaryConstants.SYSTEM_CONFIG_PARM__PKP);
		if (StringUtils.isEmpty(param)) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullGoodsPackingVolume"));
		}
		return param;
	}
	
	
	/**
	 * 
	 * 初始化代打木架部门
	 * @author 025000-FOSS-helong
	 * @date 2012-11-29 下午03:02:59
	 */
	private void initData()
	{
		Map<String, IBinder<WaybillPanelVo>> map = waybillEditUI.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();
		
		//代打木架部门
		txtWoodDept.setText(bean.getPackingOrganizationName());
		if(bean.getStandGoodsNum() != null && bean.getStandGoodsNum().intValue()>0)
		{
			// ===========lianhe/非木包装费/2017年1月4日/start/TODO=======
			//非木包装费
			this.nonWoodPackingAmount.setText(bean.getNonWoodPackingAmount().toString());
			// ===========lianhe/非木包装费/2017年1月4日/start=======
			// 打木架货物件数
			this.txtYokeGoodsPieces.setText(bean.getStandGoodsNum().toString());
			// 代打木架要求
			this.txtYokeRequire.setText(bean.getStandRequirement());
			// 打木架货物尺寸
			this.txtYokeGoodsSize.setText(bean.getStandGoodsSize());
			// 打木架货物体积
			this.txtYokeGoodsVolume.setText(bean.getStandGoodsVolume().toString());
		}
		
		if(bean.getBoxGoodsNum() != null && bean.getBoxGoodsNum().intValue()>0)
		{
			// 打木箱货物件数
			this.txtBoxGoodsPieces.setText(bean.getBoxGoodsNum().toString());
			// 代打木箱要求
			this.txtBoxRequire.setText(bean.getBoxRequirement());
			// 打木箱货物尺寸
			this.txtBoxGoodsSize.setText(bean.getBoxGoodsSize());
			// 打木箱货物体积
			this.txtBoxGoodsVolume.setText(bean.getBoxGoodsVolume().toString());
		}
		
		//zxy 20131118 ISSUE-4391 start 新增：初始化木托数据
		if(bean.getSalverGoodsNum() != null && bean.getSalverGoodsNum().intValue()>0)
		{
			// 打木托货物件数
			this.txtSalverGoodsPieces.setText(bean.getSalverGoodsNum().toString());
			// 代打木托要求
			this.txtSalverRequire.setText(bean.getSalverRequirement());
			// 打木托货物体积
			this.txtSalverGoodsAmount.setText(bean.getSalverGoodsCharge().toString());
		}
		enterYokeSearchPanel.clearText();	//zxy 20131224 MANA-285 新增:清空区间
		refreshListModel(bean);
		curSalverGoodsPieces = this.txtSalverGoodsPieces.getText();
		//zxy 20131118 ISSUE-4391 end 新增：初始化木托数据
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
	
	/**
	 * 初始化木托列表
	 * ISSUE-4391
	 * @author	157229-zxy 
	 * @date 2013-11-18  
	 */
	public void refreshListModel(WaybillPanelVo bean){
		List<LabeledGoodEntity> labeledGoodEntities = bean.getLabeledGoodEntities();
		listModelSalver.clear();
		if(labeledGoodEntities != null){
			for(LabeledGoodEntity entity : labeledGoodEntities){
				listModelSalver.addElement(new LabeledGoodEntityVo(entity));
			}
		}
		//填充默认选中的流水号
		if(labeledGoodEntities != null){
			int size = listModelSalver.getSize();
			for(int i=0; i<size; i++){
				LabeledGoodEntity entity = labeledGoodEntities.get(i);
				if(WaybillConstants.PACKAGE_TYPE_SALVER.equals(entity.getPackageType())){
					listSalver.addSelectionInterval(i ,i);
				}
			}
		}
	}
	
	/**
	 * 计算木托费用
	 * ISSUE-4391
	 * @author	157229-zxy 
	 * @date 2013-11-18  
	 * @param salverGoodsPieces
	 * @return
	 */
	public BigDecimal calculateSalverFee(String salverGoodsPieces) {
		HashMap<String, IBinder<WaybillPanelVo>> map = waybillEditUI.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();
		ValueAddDto packageCollectionSalver = Common.querySalverBillCalculateDto(bean,salverGoodsPieces);
		if(packageCollectionSalver == null){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.woodListener.ten"));
		}
		return packageCollectionSalver.getCaculateFee();
	}
	
	/**
	 * 验证木托费用是否小于最小值
	 * ISSUE-4391
	 * @author	157229-zxy 
	 * @date 2013-11-18  
	 * @param salverGoodsAmount 木托费用
	 * @param salverGoodsPieces 木托个数
	 */
	public boolean validSalverMinFee(String salverGoodsAmount,
			String salverGoodsPieces) {
		HashMap<String, IBinder<WaybillPanelVo>> map = waybillEditUI.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();
		ValueAddDto packageCollectionSalver = Common.querySalverBillCalculateDto(bean,salverGoodsPieces);
		if(packageCollectionSalver == null){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.woodListener.ten"));
		}
		BigDecimal calculateSalverMinFee = packageCollectionSalver.getMinFee()
				.multiply(new BigDecimal(salverGoodsPieces));
		if (calculateSalverMinFee.compareTo(new BigDecimal(salverGoodsAmount)) > 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证木托费用是否大于最大值
	 * @param salverGoodsAmount 木托费用
	 * @param salverGoodsPieces 木托个数
	 */
	public boolean validSalverMaxFee(String salverGoodsAmount,
			String salverGoodsPieces) {
		HashMap<String, IBinder<WaybillPanelVo>> map = waybillEditUI.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();
		ValueAddDto packageCollectionSalver = Common.querySalverBillCalculateDto(bean,salverGoodsPieces);
		if(packageCollectionSalver == null){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.woodListener.ten"));
		}
		//定价体系优化项目POP-500 木托最高一票问题
		BigDecimal calculateSalverMaxFee = packageCollectionSalver.getMaxFee()
				.multiply(new BigDecimal(salverGoodsPieces));
		if(calculateSalverMaxFee!=null && calculateSalverMaxFee.compareTo(BigDecimal.ZERO)>0){			
			if (calculateSalverMaxFee.compareTo(new BigDecimal(salverGoodsAmount)) < 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * getWaybillEditUI
	 * @return WaybillEditUI
	 */ 
	public WaybillEditUI getWaybillEditUI() {
		return waybillEditUI;
	}

	/**
	 * getWoodYokeBinder
	 * @return IBinder<WoodYokeVo>
	 */
	public IBinder<WoodYokeVo> getWoodYokeBinder() {
		return woodYokeBinder;
	}
	
	// ===========lianhe/新增非木包装费/2017年1月4日/start=======
	public JTextFieldValidate getNonWoodPackingAmount() {
		return nonWoodPackingAmount;
	}
	
	public void setNonWoodPackingAmount(JTextFieldValidate nonWoodPackingAmount) {
		this.nonWoodPackingAmount = nonWoodPackingAmount;
	}
	// ===========lianhe/新增非木包装费/2017年1月4日/start=======

	public JTextFieldValidate getTxtWoodDept() {
		return txtWoodDept;
	}

	public void setTxtWoodDept(JTextFieldValidate txtWoodDept) {
		this.txtWoodDept = txtWoodDept;
	}

	public JTextFieldValidate getTxtYokeGoodsPieces() {
		return txtYokeGoodsPieces;
	}

	public void setTxtYokeGoodsPieces(JTextFieldValidate txtYokeGoodsPieces) {
		this.txtYokeGoodsPieces = txtYokeGoodsPieces;
	}

	public JTextFieldValidate getTxtYokeRequire() {
		return txtYokeRequire;
	}

	public void setTxtYokeRequire(JTextFieldValidate txtYokeRequire) {
		this.txtYokeRequire = txtYokeRequire;
	}

	public JTextFieldValidate getTxtYokeGoodsSize() {
		return txtYokeGoodsSize;
	}

	public void setTxtYokeGoodsSize(JTextFieldValidate txtYokeGoodsSize) {
		this.txtYokeGoodsSize = txtYokeGoodsSize;
	}

	public JTextFieldValidate getTxtYokeGoodsVolume() {
		return txtYokeGoodsVolume;
	}

	public void setTxtYokeGoodsVolume(JTextFieldValidate txtYokeGoodsVolume) {
		this.txtYokeGoodsVolume = txtYokeGoodsVolume;
	}

	public JTextFieldValidate getTxtBoxGoodsPieces() {
		return txtBoxGoodsPieces;
	}

	public void setTxtBoxGoodsPieces(JTextFieldValidate txtBoxGoodsPieces) {
		this.txtBoxGoodsPieces = txtBoxGoodsPieces;
	}

	public JTextFieldValidate getTxtBoxRequire() {
		return txtBoxRequire;
	}

	public void setTxtBoxRequire(JTextFieldValidate txtBoxRequire) {
		this.txtBoxRequire = txtBoxRequire;
	}

	public JTextFieldValidate getTxtBoxGoodsSize() {
		return txtBoxGoodsSize;
	}

	public void setTxtBoxGoodsSize(JTextFieldValidate txtBoxGoodsSize) {
		this.txtBoxGoodsSize = txtBoxGoodsSize;
	}

	public JTextFieldValidate getTxtBoxGoodsVolume() {
		return txtBoxGoodsVolume;
	}

	public void setTxtBoxGoodsVolume(JTextFieldValidate txtBoxGoodsVolume) {
		this.txtBoxGoodsVolume = txtBoxGoodsVolume;
	}
	
	/**
	 * 流水号model
	 * ISSUE-4391
	 * @author	157229-zxy 
	 * @date 2013-11-18  
	 */
	private class SerialNoListModel extends DefaultListModel{

		private static final long serialVersionUID = -199518416463485592L;

	}
	
	public JTextFieldValidate getTxtSalverGoodsPieces() {
		return txtSalverGoodsPieces;
	}

	public void setTxtSalverGoodsPieces(JTextFieldValidate txtSalverGoodsPieces) {
		this.txtSalverGoodsPieces = txtSalverGoodsPieces;
	}

	public JTextFieldValidate getTxtSalverRequire() {
		return txtSalverRequire;
	}

	public void setTxtSalverRequire(JTextFieldValidate txtSalverRequire) {
		this.txtSalverRequire = txtSalverRequire;
	}

	public JTextFieldValidate getTxtSalverGoodsAmount() {
		return txtSalverGoodsAmount;
	}

	public void setTxtSalverGoodsAmount(JTextFieldValidate txtSalverGoodsAmount) {
		this.txtSalverGoodsAmount = txtSalverGoodsAmount;
	}

	public CheckBoxList getListSalver() {
		return listSalver;
	}

	public void setListSalver(CheckBoxList listSalver) {
		this.listSalver = listSalver;
	}
	public String getCurSalverGoodsPieces() {
		return curSalverGoodsPieces;
	}
	public void setCurSalverGoodsPieces(String curSalverGoodsPieces) {
		this.curSalverGoodsPieces = curSalverGoodsPieces;
	}
}