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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/AbandonedGoodsHandleUI.java
 * 
 * FILE NAME        	: AbandonedGoodsHandleUI.java
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
package com.deppon.foss.module.pickup.creating.client.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.application.IApplicationAware;
import com.deppon.foss.framework.client.widget.calender.JXDateTimePicker;
import com.deppon.foss.module.pickup.creating.client.action.AbandonedGoodsHandleCalculateAction;
import com.deppon.foss.module.pickup.creating.client.action.AbandonedGoodsImpotBillAction;
import com.deppon.foss.module.pickup.creating.client.action.AbandonedGoodsQueryAction;
import com.deppon.foss.module.pickup.creating.client.vo.AbandonedGoodsModel;
import com.deppon.foss.module.pickup.creating.client.vo.AbandonedGoodsTypeVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.AbandGoodsApplicationConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * 弃货ui
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * 
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class AbandonedGoodsHandleUI extends JPanel implements IApplicationAware {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8187145327124015223L;

	/**
	 * 10
	 */
	private static final int TEN = 10;

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(AbandonedGoodsHandleUI.class);

	/**
	 * 运单号
	 */
	private JTextField waybillNo;

	/**
	 * 发货人
	 */
	private JTextField shipper;

	/**
	 * 预处理人
	 */
	private JTextField preTreatPerson;

	/**
	 * 仓库异常货物类型标签
	 */
	private JComboBox exceptionType;

	/**
	 * 仓库异常货物类型内容
	 */
	private DefaultComboBoxModel exceptionTypeMode;

	/**
	 * 总重量
	 */
	private JLabel totalWeight;

	/**
	 * 总体积
	 */
	private JLabel totalVolume;

	/**
	 * 总件数
	 */
	private JLabel totalPieces;

	/**
	 * 导入内部带货开单按钮
	 */
	@ButtonAction(icon = "", shortcutKey = "", type = AbandonedGoodsImpotBillAction.class)
	private JButton btnImportToWaybill;

	/**
	 * 查询按钮
	 */
	@ButtonAction(icon = "", shortcutKey = "", type = AbandonedGoodsQueryAction.class)
	private JButton btnQuery;

	/**
	 * 重置按钮
	 */
	@ButtonAction(icon = "", shortcutKey = "", type = AbandonedGoodsQueryAction.class)
	private JButton btnReset;

	/**
	 * 查询开始时间
	 */
	private JXDateTimePicker beginDate;

	/**
	 * 表格模型
	 */
	private AbandonedGoodsModel tableModel;

	/**
	 * 查询结束时间
	 */
	private JXDateTimePicker endDate;

	/**
	 * 导入期货总重量、总体积、总件数计算
	 */
	private AbandonedGoodsHandleCalculateAction calculateAction;

	/**
	 * 应用接口
	 */
	private IApplication application;

	/**
	 * 
	 * 构造方法：创建一个新的实例
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-6 上午9:07:50
	 */
	public AbandonedGoodsHandleUI() {
		init();
		createListener();
	}

	/**
	 * 
	 */
	private void createListener() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		calculateAction = new AbandonedGoodsHandleCalculateAction(this);
		tableModel.addChangeListener(calculateAction);
	}

	/**
	 * 
	 */
	private void init() {
		setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), FormFactory.RELATED_GAP_ROWSPEC, }));

		JPanel panel1 = new JPanel();
		panel1.setBorder(new LineBorder(Color.DARK_GRAY));
		add(panel1, "4, 2, 3, 1, fill, fill");
		panel1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("101dlu:grow"),
				FormFactory.GROWING_BUTTON_COLSPEC,
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("15dlu"),
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("80dlu:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("max(6dlu;default)"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));

		// 查询条件
		JLabel label = new JLabel(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.title1"));
		panel1.add(label, "2, 2, 3, 1");
		label.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_16));

		// 运单号
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.waybillNo.label"));
		panel1.add(label1, "2, 4, right, default");

		waybillNo = new JTextField();
		panel1.add(waybillNo, "4, 4, fill, default");
		waybillNo.setColumns(TEN);

		exceptionTypeMode = new DefaultComboBoxModel();

		AbandonedGoodsTypeVo vo1 = new AbandonedGoodsTypeVo();
		vo1.setExceptionCode(AbandGoodsApplicationConstants.ABANDGOODS_TYPE_CUSTOMER);
		vo1.setExceptionName(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.exception.goodsType"));
		exceptionTypeMode.addElement(vo1);

		AbandonedGoodsTypeVo vo2 = new AbandonedGoodsTypeVo();
		vo2.setExceptionCode(AbandGoodsApplicationConstants.ABANDGOODS_TYPE_AUTO);
		vo2.setExceptionName(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.exception.timeOut"));
		exceptionTypeMode.addElement(vo2);

		// SORNAR 变量不能用 _ sornar报错
		// 发货人
		JLabel label2 = new JLabel(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.shipper.label"));
		panel1.add(label2, "5, 4, right, default");

		shipper = new JTextField();
		panel1.add(shipper, "6, 4, fill, default");
		shipper.setColumns(TEN);

		// 仓库异常货物类型
		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.exceptionType.label"));
		panel1.add(label3, "8, 4, right, default");

		exceptionType = new JComboBox();
		exceptionType.setModel(exceptionTypeMode);

		panel1.add(exceptionType, "9, 4, fill, fill");

		// 预处理人
		JLabel label4 = new JLabel(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.preTreatPerson.label"));
		panel1.add(label4, "2, 6, right, default");

		preTreatPerson = new JTextField();
		panel1.add(preTreatPerson, "4, 6, fill, default");
		preTreatPerson.setColumns(TEN);

		Date date = new Date();

		// 预处理时间
		JLabel label5 = new JLabel(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.beginDate.label"));
		panel1.add(label5, "5, 6, right, default");
		beginDate = new JXDateTimePicker();
		beginDate.setDate(date);
		panel1.add(beginDate, "6, 6, fill, center");

		JLabel label6 = new JLabel(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.endDate.label"));
		panel1.add(label6, "7, 6, center, center");

		endDate = new JXDateTimePicker();
		endDate.setDate(date);
		panel1.add(endDate, "8, 6, fill, center");
		
		JPanel panel = new JPanel();
		panel1.add(panel, "2, 8, 8, 1, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),
				FormFactory.BUTTON_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),}));

		btnQuery = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));
		btnQuery.setPreferredSize(new Dimension(NumberConstants.NUMBER_80, NumberConstants.NUMBER_60));
		panel.add(btnQuery, "2, 2, left, top");
		
				btnReset = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.reset"));
				btnReset.setPreferredSize(new Dimension(NumberConstants.NUMBER_80, NumberConstants.NUMBER_60));
				panel.add(btnReset, "4, 2, left, top");

		JPanel panel2 = new JPanel();
		panel2.setBorder(new LineBorder(Color.DARK_GRAY));
		add(panel2, "4, 4, 3, 1, fill, fill");
		panel2.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("80dlu"), FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"), FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, }));

		// 处理仓库异常货物
		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.title2"));
		panel2.add(lblNewLabel, "2, 2, 3, 1");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_16));

		JScrollPane scrollPane = new JScrollPane();
		panel2.add(scrollPane, "2, 4, 13, 1, fill, fill");

		JXTable table = new JXTable();
		table.setAutoscrolls(true);
		table.setSortable(false);
		table.setColumnControlVisible(true);
		scrollPane.setViewportView(table);

		tableModel = new AbandonedGoodsModel();
		table.setModel(tableModel);
		// TableFactory.createRowColumn(table);

		// 总重量
		JLabel totalWeightLabel = new JLabel(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.totalWeight.label"));
		panel2.add(totalWeightLabel, "2, 6, right, center");

		totalWeight = new JLabel("0.0");
		panel2.add(totalWeight, "4, 6");

		// 总体积
		JLabel totalVolumeLabel = new JLabel(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.totalVolume.label"));
		panel2.add(totalVolumeLabel, "6, 6, right, default");

		totalVolume = new JLabel("0.0");
		panel2.add(totalVolume, "8, 6");

		// 总件数
		JLabel totalPiecesLabel = new JLabel(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.totalPieces.label"));
		panel2.add(totalPiecesLabel, "10, 6, right, default");

		totalPieces = new JLabel("0");
		panel2.add(totalPieces, "12, 6");

		// 导入内部开单
		btnImportToWaybill = new JButton(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.importToWaybill.label"));
		panel2.add(btnImportToWaybill, "14, 6, fill, center");
	}

	/**
	 * 获取 运单号.
	 * 
	 * @return the 运单号
	 */
	public JTextField getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 获取 发货人.
	 * 
	 * @return the 发货人
	 */
	public JTextField getShipper() {
		return shipper;
	}

	/**
	 * 获取 预处理人.
	 * 
	 * @return the 预处理人
	 */
	public JTextField getPreTreatPerson() {
		return preTreatPerson;
	}

	/**
	 * 获取 仓库异常货物类型标签.
	 * 
	 * @return the 仓库异常货物类型标签
	 */
	public JComboBox getExceptionType() {
		return exceptionType;
	}

	/**
	 * 获取 查询按钮.
	 * 
	 * @return the 查询按钮
	 */
	public JButton getBtnQuery() {
		return btnQuery;
	}

	/**
	 * 获取 查询开始时间.
	 * 
	 * @return the 查询开始时间
	 */
	public JXDateTimePicker getBeginDate() {
		return beginDate;
	}

	/**
	 * 获取 表格模型.
	 * 
	 * @return the 表格模型
	 */
	public AbandonedGoodsModel getTableModel() {
		return tableModel;
	}

	/**
	 * 获取 查询结束时间.
	 * 
	 * @return the 查询结束时间
	 */
	public JXDateTimePicker getEndDate() {
		return endDate;
	}

	/**
	 * 获取 重置按钮.
	 * 
	 * @return the 重置按钮
	 */
	public JButton getBtnReset() {
		return btnReset;
	}

	/**
	 * 获取 总重量.
	 * 
	 * @return the 总重量
	 */
	public JLabel getTotalWeight() {
		return totalWeight;
	}

	/**
	 * 获取 总体积.
	 * 
	 * @return the 总体积
	 */
	public JLabel getTotalVolume() {
		return totalVolume;
	}

	/**
	 * 获取 总件数.
	 * 
	 * @return the 总件数
	 */
	public JLabel getTotalPieces() {
		return totalPieces;
	}

	/**
	 * 获取 导入内部带货开单按钮.
	 * 
	 * @return the 导入内部带货开单按钮
	 */
	public JButton getBtnImportToWaybill() {
		return btnImportToWaybill;
	}

	/**
	 *
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-6 上午9:09:53
	 * @see com.deppon.foss.framework.client.core.application.IApplicationAware#setApplication(com.deppon.foss.framework.client.core.application.IApplication)
	 */
	@Override
	public void setApplication(IApplication application) {
		this.application = application;
	}

	/**
	 * 获取 应用接口.
	 * 
	 * @return the 应用接口
	 */
	public IApplication getApplication() {
		return application;
	}

}