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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/print/SignLabelPrintDialog.java
 * 
 * FILE NAME        	: SignLabelPrintDialog.java
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
package com.deppon.foss.module.pickup.creating.client.ui.print;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.binding.BindingFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.vo.StartSignLabelVo;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 始发签收单标签打印
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-jiangfei,date:2012-10-26 下午4:48:33,content:
 * </p>
 * 
 * @author foss-jiangfei
 * @date 2012-10-26 下午4:48:33
 * @since
 * @version
 */
public class SignLabelPrintDialog extends JDialog {
	
	private static final int TEN = 10;
	
	private static final int NUM_260 = 260;

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	private static final II18n i18n = I18nManager.getI18n(SignLabelPrintDialog.class);
	
	/**
	 * 日志
	 */
	private static final Log log = LogFactory.getLog(SignLabelPrintDialog.class);
	
	/**
	 * 远单号码
	 */
	@Bind("waybillNo")
	private JTextField textField;

	@Bind("startOrg")
	private JTextField textField1;

	@Bind("reachOrg")
	private JTextField textField2;

	@Bind("destnationOrg")
	private JTextField textField3;

	private JButton btnNewButton;

	// 绑定接口
	private IBinder<StartSignLabelVo> signBillBinder;

	private HashMap<String, IBinder<StartSignLabelVo>> bindersMap = new HashMap<String, IBinder<StartSignLabelVo>>();

	private StartSignLabelVo tempBean = new StartSignLabelVo();

	/**
	 * <p>
	 * 构造方法
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-30 上午10:41:11
	 * @see
	 */
	public SignLabelPrintDialog() {
		init();
		addListener();
		bind();
		registToRespository();
	}

	/**
	 * <p>
	 * 放入binderMap
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-30 上午10:41:11
	 * @see
	 */
	private void registToRespository() {
		bindersMap.put("signBillBinder", signBillBinder);
	}

	/**
	 * <p>
	 * ui绑定
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-30 上午10:18:08
	 * @see
	 */
	private void bind() {
		signBillBinder = BindingFactory.getIntsance().moderateBind(
				StartSignLabelVo.class, this, null, true);

	}

	/**
	 * <p>
	 * 添加监听事件
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-29 上午10:26:46
	 * @see
	 */
	private void addListener() {
		textField.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				
				IWaybillService waybillService = WaybillServiceFactory
						.getWaybillService();
				WaybillEntity detail = waybillService
						.queryWaybillByNumber(textField.getText());
				tempBean.setTargetOrg(detail.getTargetOrgCode());
				tempBean.setWaybillNo(detail.getWaybillNo());
				tempBean.setReachOrg(detail.getCustomerPickupOrgCode()); // 到达部门																   
				tempBean.setStartOrg(detail.getReceiveOrgCode());// 收货部门
				

			}
		});
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				IBinder<StartSignLabelVo> startSignLabelBinder = (IBinder<StartSignLabelVo>) bindersMap
						.get("signBillBinder");
				tempBean = startSignLabelBinder.getBean();

			}
		});
	}

	/**
	 * <p>
	 * 界面初始化
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-29 上午10:26:14
	 * @see
	 */
	private void init() {
		setTitle(i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.oriSignPrint"));
		setSize(NumberConstants.NUMBER_400, NUM_260);
		setLocationRelativeTo(null);
		this.setVisible(true);
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(50dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(171dlu;default)"), },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(29dlu;default)"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(18dlu;default)"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(18dlu;default)"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(24dlu;default)"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(27dlu;default)"), }));

		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.billNo"));
		panel.add(lblNewLabel, "2, 2, right, default");

		textField = new JTextField();
		panel.add(textField, "4, 2, fill, default");
		textField.setColumns(TEN);

		JLabel lblNewLabel1 = new JLabel(i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.originatingOrg"));
		panel.add(lblNewLabel1, "2, 4, right, default");

		textField1 = new JTextField();
		panel.add(textField1, "4, 4, fill, default");
		textField1.setColumns(TEN);

		JLabel lblNewLabel2 = new JLabel(i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.arriveOrg"));
		panel.add(lblNewLabel2, "2, 6, right, default");

		textField2 = new JTextField();
		panel.add(textField2, "4, 6, fill, default");
		textField2.setColumns(TEN);

		JLabel lblNewLabel3 = new JLabel(i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.destination"));
		panel.add(lblNewLabel3, "2, 8, right, default");

		textField3 = new JTextField();
		panel.add(textField3, "4, 8, fill, default");
		textField3.setColumns(TEN);

		btnNewButton = new JButton(i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.print"));
		panel.add(btnNewButton, "2, 10");

		JButton btnNewButton1 = new JButton(i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.printSet"));
		panel.add(btnNewButton1, "4, 10");
	}
	
	/**
	 * <p>
	 * Date 转成 yyyy年MM月dd日 格式
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-28 下午4:49:39
	 * @param date
	 * @return
	 * @see
	 */
	@SuppressWarnings("finally")
	public static String getDate(Date date) {
		String str = "";
		SimpleDateFormat sf = new SimpleDateFormat(i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.formatDate"));
		try {
			str = sf.format(date);

		} catch (Exception e) {
			log.error(i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.printConvertDateException"),e);
		} finally {
			return str;
		}
	}
}