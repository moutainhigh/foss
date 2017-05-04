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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/print/PrintTimesDialog.java
 * 
 * FILE NAME        	: PrintTimesDialog.java
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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.creating.client.action.CloseDialogAction;
import com.deppon.foss.module.pickup.creating.client.action.CreatePrintDialogAction;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.vo.WaybillPrintBean;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 打印次数对话框
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:jiangfei,date:2012-10-18 上午10:50:59,
 * </p>
 * @author jiangfei
 * @date 2012-10-18 上午10:50:59
 * @since
 * @version
 */
public class PrintTimesDialog extends JDialog {
	/**
	 * 日志
	 */
	private static final Log log = LogFactory.getLog(PrintTimesDialog.class);

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 456092069517569528L;

	private static final II18n i18n = I18nManager.getI18n(SignLabelPrintDialog.class);
	
	/**
	 * BUTTION FOR ADD NEW PRINT REVCORD
	 */
	@ButtonAction(icon = "", shortcutKey = "", type = CreatePrintDialogAction.class)
	private JButton btnNewButton;

	/**
	 * BUTTION FOR CANCEL EXISTING PRINT REVCORD
	 */
	@ButtonAction(icon = "", shortcutKey = "", type = CloseDialogAction.class)
	private JButton btnNewButton1;

	/**
	 * UI
	 */
	private WaybillEditUI waybillEditUI;


	/**
	 * 打印次数
	 */
	private int printTimes = 0;
	/**
	 * 标示 是打印 还是打印预览 true为打印 false为打印预览
	 */
	private Boolean isPrintOrPrePrint;

	/**
	 * <p>
	 * 创建一个新的实例 PrintTimesDialog
	 * </p>
	 * 
	 * @author jiangfei
	 * @date 2012-10-18 上午11:31:09
	 */
	public PrintTimesDialog(Boolean isPrintOrPrePrint, WaybillEditUI ui) {
		this.isPrintOrPrePrint = isPrintOrPrePrint;
		waybillEditUI = ui;
		printTimes = queryPrintTimes(waybillEditUI);
		init();
		bind();
		this.setVisible(true);
	}

	/**
	 * getWaybillEditUI
	 * @return WaybillEditUI
	 */
	public WaybillEditUI getWaybillEditUI() {
		return waybillEditUI;
	}
	
	/**
	 * 查询打印次数
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-16 上午12:15:59
	 * @param ui
	 * @see
	 */
	private int queryPrintTimes(WaybillEditUI ui) {

		WaybillPrintBean resourceBean;

		String waybillNo;

		try {
			resourceBean = (WaybillPrintBean) ui.getWaybillPrintBean();
			waybillNo = resourceBean.getWaybillNo();
			WaybillEntity waybillEntity = WaybillServiceFactory
					.getWaybillService().queryWaybillByNumber(waybillNo);

			if ("ONLINE_LOGIN".equals(SessionContext.get("user_loginType")
					.toString())) {
				
				// 在线查询打印次数
				if(waybillEntity != null){
					printTimes = WaybillServiceFactory.getWaybillService()
						.queryPrintTimesByWaybillId(waybillEntity.getId(),waybillNo);
				}
				// WaybillEntity waybillEntity = WaybillServiceFactory
				// .getWaybillService().queryWaybillByNumber(waybillNo);
				// printTimes = waybillEntity.getPrintTimes();// 得到运单次数
			} else {
				// "离线状态的打印"
				// 离线 开单时候
				// WaybillLocal waybillLocal = WaybillServiceFactory
				// .getWaybillService().queryByWaybillNo(waybillNo);
				// printTimes = waybillLocal.getPrintTimes(); // 打印次数
			}
		} catch (Exception e) {
		    	log.error(e.getMessage());
			e.printStackTrace();
		}
		return printTimes;
	}

	/**
	 * <p>
	 * 绑定button
	 * </p>
	 * @author jiangfei
	 * @date 2012-10-18 上午11:38:28
	 * @see
	 */
	private void bind() {

		ButtonActionFactory.getIntsance().bindButtonAction(this);
	}

	/**
	 * <p>
	 * 类初始化
	 * </p>
	 * 
	 * @author jiangfei
	 * @date 2012-10-18 上午11:38:21
	 * @see
	 */
	private void init() {
		setTitle(i18n.get("foss.gui.creating.ui.print.PrintTimesDialog.chinese.printCount"));
		this.setSize(NumberConstants.NUMBER_400, NumberConstants.NUMBER_200);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("max(51dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(55dlu;pref)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(62dlu;pref)"),},
			new RowSpec[] {
				RowSpec.decode("max(19dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(14dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(25dlu;default)"),}));
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		
		JLabel labelOne = new JLabel(i18n.get("foss.gui.creating.ui.print.PrintTimesDialog.chinese.printUser") + user.getEmployee().getEmpName());
		panel.add(labelOne, "5, 3, 3, 1");
		
		JLabel labelTwo = new JLabel(i18n.get("foss.gui.creating.ui.print.PrintTimesDialog.chinese.printTime")+ sdf.format(new Date()));
		panel.add(labelTwo, "5, 5, 3, 1");
		
		JLabel labelThree = new JLabel(i18n.get("foss.gui.creating.ui.print.PrintTimesDialog.chinese.printCount")+":" + i18n.get("foss.gui.creating.ui.print.PrintTimesDialog.chinese.printRemark1") + printTimes + i18n.get("foss.gui.creating.ui.print.PrintTimesDialog.chinese.printRemark2"));
		panel.add(labelThree, "5, 7, 3, 1");

		btnNewButton = new JButton(i18n.get("foss.gui.creating.ui.print.PrintTimesDialog.chinese.ok"));
		panel.add(btnNewButton, "5, 9, fill, center");
		
		btnNewButton1 = new JButton(i18n.get("foss.gui.creating.ui.print.PrintTimesDialog.chinese.cancel"));
		btnNewButton1.setVerticalAlignment(SwingConstants.TOP);
		panel.add(btnNewButton1, "7, 9, fill, default");
		
		setModal(true);
	}
	/**
	 * getIsPrintOrPrePrint
	 * @return Boolean
	 */
	public Boolean getIsPrintOrPrePrint() {
		return isPrintOrPrePrint;
	}
	
	/**
	 * setIsPrintOrPrePrint
	 * @param isPrintOrPrePrint Boolean
	 */
	public void setIsPrintOrPrePrint(Boolean isPrintOrPrePrint) {
		this.isPrintOrPrePrint = isPrintOrPrePrint;
	}
}