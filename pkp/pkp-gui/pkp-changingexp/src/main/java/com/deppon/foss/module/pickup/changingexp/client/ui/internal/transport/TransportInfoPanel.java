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
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/internal/transport/TransportInfoPanel.java
 * 
 * FILE NAME        	: TransportInfoPanel.java
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
package com.deppon.foss.module.pickup.changingexp.client.ui.internal.transport;

import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.deppon.foss.framework.client.commons.binding.validation.annotations.NotNull;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArg;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArgs;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.pickup.changingexp.client.action.ShowPickupStationDialogAction;
import com.deppon.foss.module.pickup.common.client.ui.combocheckbox.JComboCheckBox;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.BoxLayout;

/**
 * 
 * 运输信息面板
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-12-24 下午6:02:46
 */
@ContainerSeq(seq=5)
public class TransportInfoPanel extends JPanel {

	/**
	 *  国际化
	 */
	private static II18n i18n = I18nManager.getI18n(TransportInfoPanel.class);

	/**
	 * DEFAULT CSS
	 */
	private static final String DEFAULTGROW = "default:grow";
	
	/**
	 * 18 FOR FONT 
	 */
	private static final int EIGHTEEN = 18;

	/**
	 * 12 FOR FONT 
	 */
	private static final int TWELVE = 12;

	/**
	 * 8 FOR FONT 
	 */
	private static final int EIGHT = 8;

	/**
	 * 4 FOR FONT 
	 */
	private static final int FOUR = 4;

	/**
	 * 10 FOR COLUMN NUMBER TOTAL
	 */
	private static final int TEN = 10;
	
	private static final int SIXTEEN = 16;
	
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -8593409862810505408L;

	/**
	 * 目的站
	 */
	@Bind("targetOrgCode")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "目的站") })
	@FocusSeq(seq=3)
	private JTextFieldValidate txtDestination;

	/**
	 * 提货网点
	 */
	@Bind("customerPickupOrgName")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "提货网点") })
	@FocusSeq(seq=5)
	private JTextFieldValidate txtPickBranch;

	/**
	 * 对内备注
	 */
	@Bind("innerNotes")
	@BindingArgs({ @BindingArg(name = "fieldName", value = "对内备注") })
	@FocusSeq(seq=12)
	private JTextFieldValidate txtInsideRemark;

	/**
	 * 货物状态
	 */
	@Bind("goodsStatus")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "货物状态") })
	@FocusSeq(seq=1)
	private JComboBox combGoodsStatus;

	/**
	 * 返单类别
	 */
	@Bind("returnBillType")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "返单类别") })
	@FocusSeq(seq=8)
	private JComboBox combReturnBillType;

	/**
	 * 对外备注
	 */
	@Bind("outerNotes")
	@FocusSeq(seq=11)
	private JComboBox combOutSideRemark;

	/**
	 * 提货方式
	 */
	@Bind("receiveMethod")
	@BindingArgs({ @BindingArg(name = "fieldName", value = "提货方式") })
	@FocusSeq(seq=2)
	private JComboBox combPickMode;

	/**
	 * 运输性质
	 */
	@Bind("productCode")
	@BindingArgs({ @BindingArg(name = "fieldName", value = "运输性质") })
	@FocusSeq(seq=6)
	private JComboBox combProductType;

	/**
	 * 预配航班
	 */
	@Bind("flightNumberType")
	@FocusSeq(seq=7)
	private JComboBox combPredictFlight;

	/**
	 * 预配航班Model
	 */
	private DefaultComboBoxModel predictFlightModel;

	/**
	 * 提货方式Model
	 */
	private DefaultComboBoxModel pickModeModel;

	/**
	 * 运输性质Model
	 */
	private DefaultComboBoxModel productTypeModel;

	/**
	 * 返单类型Model
	 */
	private DefaultComboBoxModel returnBillTypeModel;

	/**
	 * 货物状态Model
	 */
	private DefaultComboBoxModel goodsStatusModel;
	
	/**
	 * 合票方式Model
	 */
	private DefaultComboBoxModel combFreightMethodModel;
	
	
	/**
	 * 查询目的站放大镜
	 */
	@ButtonAction(icon = "query.png", shortcutKey = "", type = ShowPickupStationDialogAction.class)
	@FocusSeq(seq=4)
	private JButton btnQueryBranch;
	public JButton getBtnQueryBranch() {
		return btnQueryBranch;
	}
	public void setBtnQueryBranch(JButton btnQueryBranch) {
		this.btnQueryBranch = btnQueryBranch;
	}
	private JLabel label;
	//合票方式
	@Bind("freightMethod")
	@FocusSeq(seq=9)
	private JComboBox combFreightMethod;
	private JLabel label_1;
	@Bind("flightShift")
	@FocusSeq(seq=10)
	private JTextFieldValidate txtFlightShift;
	private JPanel panel;

	/**
	 * 
	 * TransportInfoPanel
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:56:54
	 */
	public TransportInfoPanel() {
		init();
	}

	/**
	 * 
	 * 布局初始化
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:53:21
	 */
	private void init() {

		FormLayout formLayout = new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,});
		formLayout.setColumnGroups(new int[][]{new int[]{FOUR, EIGHT, TWELVE, SIXTEEN}});
		setLayout(formLayout);

		//货物状态
		JLabel lblGoodsStatus = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.goodsStatus.label")+"：");
		add(lblGoodsStatus, "2, 2, right, default");

		combGoodsStatus = new JComboBox();
		goodsStatusModel = (DefaultComboBoxModel) combGoodsStatus.getModel();
		add(combGoodsStatus, "4, 2, fill, default");

		//提货方式
		JLabel lblPickMode = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.pickMode.label")+"：");
		add(lblPickMode, "6, 2, right, default");

		combPickMode = new JComboBox();
		pickModeModel = (DefaultComboBoxModel) combPickMode.getModel();
		add(combPickMode, "8, 2, fill, default");

		//目的站：
		JLabel lblDestination = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.destination.label")+"：");
		add(lblDestination, "10, 2, right, default");
		
		panel = new JPanel();
		add(panel, "12, 2, fill, fill");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		txtDestination = new JTextFieldValidate();
		panel.add(txtDestination);
		txtDestination.setColumns(TEN);

		//匹配提货网点
		btnQueryBranch = new JButton();
		btnQueryBranch.setMargin(new Insets(-2, 1, -2, 1));
		panel.add(btnQueryBranch);

		//提货网点：
		JLabel lblPickBranch = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.pickBranch.label")+"：");
		add(lblPickBranch, "14, 2, right, default");

		txtPickBranch = new JTextFieldValidate();
		add(txtPickBranch, "16, 2, fill, default");
		txtPickBranch.setColumns(TEN);

		//运输性质：
		JLabel lblProductType = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.productType.label")+"：");
		add(lblProductType, "2, 4, right, default");

		combProductType = new JComboBox();
		productTypeModel = (DefaultComboBoxModel) combProductType.getModel();
		add(combProductType, "4, 4, fill, default");

		//航班类型：
		JLabel lblPredictFlight = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.predictFlight.label")+"：");
		add(lblPredictFlight, "6, 4, right, default");

		combPredictFlight = new JComboBox();
		predictFlightModel = (DefaultComboBoxModel) combPredictFlight
				.getModel();
		add(combPredictFlight, "8, 4, fill, default");

		//返单类别：
		JLabel lblReturnBillType = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.returnBillType.label")+"：");
		add(lblReturnBillType, "10, 4");

		combReturnBillType = new JComboBox();
		returnBillTypeModel = (DefaultComboBoxModel) combReturnBillType
				.getModel();
		add(combReturnBillType, "12, 4, fill, default");

		//合票方式：
		label = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.freightMethod.label")+"：");
		add(label, "2, 6, left, default");

		combFreightMethod = new JComboBox();
		combFreightMethod.setEnabled(false);
		combFreightMethodModel = (DefaultComboBoxModel) combFreightMethod
				.getModel();
		add(combFreightMethod, "4, 6, fill, default");

		//航班时间：
		label_1 = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.flightShift.label")+"：");
		add(label_1, "6, 6, right, default");

		txtFlightShift = new JTextFieldValidate();
		txtFlightShift.setEnabled(false);
		txtFlightShift.setColumns(TEN);
		add(txtFlightShift, "8, 6, fill, default");

		//对外备注：
		JLabel lblOutSideRemark = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.outSideRemark.label")+"：");
		add(lblOutSideRemark, "2, 8, right, default");

		combOutSideRemark = new JComboCheckBox();
		add(combOutSideRemark, "4, 8, 5, 1, fill, default");

		//对内备注：
		JLabel lblInsideRemark = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.insideRemark.label")+"：");
		add(lblInsideRemark, "10, 8, right, default");

		txtInsideRemark = new JTextFieldValidate();
		add(txtInsideRemark, "12, 8, 5, 1, fill, default");
		txtInsideRemark.setColumns(TEN);

	}

	/**
	 * @return the txtDestination
	 */
	public JTextFieldValidate getTxtDestination() {
		return txtDestination;
	}

	/**
	 * @return the txtPickBranch
	 */
	public JTextFieldValidate getTxtPickBranch() {
		return txtPickBranch;
	}

	/**
	 * @return the txtInsideRemark
	 */
	public JTextFieldValidate getTxtInsideRemark() {
		return txtInsideRemark;
	}

	/**
	 * @return the combGoodsStatus
	 */
	public JComboBox getCombGoodsStatus() {
		return combGoodsStatus;
	}

	/**
	 * @return the combReturnBillType
	 */
	public JComboBox getCombReturnBillType() {
		return combReturnBillType;
	}

	/**
	 * @return the combOutSideRemark
	 */
	public JComboBox getCombOutSideRemark() {
		return combOutSideRemark;
	}

	/**
	 * @return the combPickMode
	 */
	public JComboBox getCombPickMode() {
		return combPickMode;
	}

	/**
	 * @return the combProductType
	 */
	public JComboBox getCombProductType() {
		return combProductType;
	}

	/**
	 * @return the combPredictFlight
	 */
	public JComboBox getCombPredictFlight() {
		return combPredictFlight;
	}

	/**
	 * @return the predictFlightModel
	 */
	public DefaultComboBoxModel getPredictFlightModel() {
		return predictFlightModel;
	}

	/**
	 * @return the pickModeModel
	 */
	public DefaultComboBoxModel getPickModeModel() {
		return pickModeModel;
	}

	/**
	 * @return the productTypeModel
	 */
	public DefaultComboBoxModel getProductTypeModel() {
		return productTypeModel;
	}

	/**
	 * @return the returnBillTypeModel
	 */
	public DefaultComboBoxModel getReturnBillTypeModel() {
		return returnBillTypeModel;
	}

	/**
	 * @return the goodsStatusModel
	 */
	public DefaultComboBoxModel getGoodsStatusModel() {
		return goodsStatusModel;
	}
	
	/**
	 * getCombFreightMethod
	 * @return JComboBox
	 */
	public JComboBox getCombFreightMethod() {
		return combFreightMethod;
	}

	/**
	 * @return the combFreightMethodModel
	 */
	public DefaultComboBoxModel getCombFreightMethodModel() {
		return combFreightMethodModel;
	}

	/**
	 * @return  the txtFlightShift
	 */
	public JTextFieldValidate getTxtFlightShift() {
		return txtFlightShift;
	}
}