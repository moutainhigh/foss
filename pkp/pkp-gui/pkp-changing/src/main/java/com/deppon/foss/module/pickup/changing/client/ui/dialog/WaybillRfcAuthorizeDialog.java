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
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/dialog/WaybillRfcAuthorizeDialog.java
 * 
 * FILE NAME        	: WaybillRfcAuthorizeDialog.java
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
package com.deppon.foss.module.pickup.changing.client.ui.dialog;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.calender.JXDateTimePicker;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.changing.client.action.WaybillRfcAuthorizeCloseAction;
import com.deppon.foss.module.pickup.changing.client.action.WaybillRfcAuthorizeCommitAction;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCAuthorizeUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentPersonEntity;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * 审核授权对话框
 * @author 102246-foss-shaohongliang
 * @date 2012-12-15 下午5:32:58
 */
public class WaybillRfcAuthorizeDialog extends JDialog {
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(WaybillRfcAuthorizeDialog.class);

	/**
	 * 300
	 */
	private static final int THREEHUNDRED = 300;

	/**
	 * 750
	 */
	private static final int SEVENHUNDRED = 750;

	/**
	 * 30
	 */
	private static final int THIRDTY = 30;
	
	/**
	 * 50
	 */
	private static final int FIFTY = 50;
	
	private static final int TEN = 10;

	/**
	 * default css
	 */
	private static final String DEFAULTGROW = "default:grow";

	/**
	 * 序列化版本
	 */
	private static final long serialVersionUID = 3863356346579330971L;

	
	/**
	 * 操作人
	 */
	private JTextField operateUserName;
	
	/**
	 * 代理人
	 */
	private MultiUserChooser delegateUserName;
	
	/**
	 * 生效时间
	 */
	private JXDateTimePicker dateTimePickerStart;
	
	/**
	 * 结束时间
	 */
	private JXDateTimePicker dateTimePickerEnd;

	/**
	 * 提交按钮
	 */
	@ButtonAction(icon="", shortcutKey="", type=WaybillRfcAuthorizeCommitAction.class)
	private JButton btnSubmit;
	
	/**
	 * 返回按钮
	 */
	@ButtonAction(icon="", shortcutKey="", type=WaybillRfcAuthorizeCloseAction.class)
	private JButton btnReturn;
	
	/**
	 * 代理原因
	 */
	private JTextArea delegateReason;
	
	/**
	 * 操作审核代理对象
	 */
	private WaybillRfcAgentEntity bean;
	
	/**
	 * 审核代理主页面
	 */
	private WaybillRFCAuthorizeUI parent;

	/**
	 * 
	 * 实例化代理审核对话框
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:25:11
	 */
	public WaybillRfcAuthorizeDialog(WaybillRFCAuthorizeUI parent) {
		this(parent, null);
	}

	/**
	 * 
	 * 根据代理对象实例化代理审核对话框
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:25:28
	 */
	public WaybillRfcAuthorizeDialog(WaybillRFCAuthorizeUI parent, WaybillRfcAgentEntity bean) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		this.bean = bean;
		this.parent = parent;
		init();
		bind();
		setUIValue();
	}

	/**
	 * 
	 * 数据 按钮绑定
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 上午11:36:08
	 */
	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
	}

	/**
	 * 
	 * 设置页面默认值
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:25:48
	 */
	private void setUIValue() {
		// 委托人为登陆人，不能修改
		operateUserName.setEnabled(false);
		UserEntity user = (UserEntity)SessionContext.getCurrentUser();
		operateUserName.setText(user.getEmployee().getEmpName());
		if(bean == null){
			setDefaultValue();
			return;
		}else{
			setBeanValue();
		}
	}
	
	/**
	 * 
	 * 从修改对象赋值
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-28 上午8:19:25
	 */
	private void setBeanValue() {
		// 代理人
		List<WaybillRfcAgentPersonEntity> rfcAgentPersonEntities = bean.getAgentEmpList();
		List<EmployeeEntity> employeeEntities = new ArrayList<EmployeeEntity>();
		for(WaybillRfcAgentPersonEntity agentPersonEntity : rfcAgentPersonEntities){
			EmployeeEntity employeeEntity = new EmployeeEntity();
			employeeEntity.setEmpCode(agentPersonEntity.getAgentEmpCode());
			employeeEntity.setEmpName(agentPersonEntity.getAgentEmpName());
			employeeEntities.add(employeeEntity);
		}
		delegateUserName.setEmployeeEntities(employeeEntities);
		delegateUserName.refreshData();
		// 生效时间
		dateTimePickerStart.setDate(bean.getValidTime());
		// 终止时间
		dateTimePickerEnd.setDate(bean.getOverdueTime());
		// 代理原因
		delegateReason.setText(bean.getAgentReason());
		//生效状态
		String status = bean.getStatus();
		if(WaybillRfcConstants.RFC_AGENT_STATUS_AVALIABLE.equals(status)){
			Date validTime = bean.getValidTime();
			//生效时间早于当前时间
			if(validTime != null && validTime.before(new Date())){
				dateTimePickerStart.setEnabled(false);
			}
		}
	}

	/**
	 * 
	 * 设置默认值
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 下午5:38:40
	 */
	private void setDefaultValue() {
		Calendar calendar = Calendar.getInstance();
		dateTimePickerStart.setDate(calendar.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, NumberConstants.NUMBER_30);
		dateTimePickerEnd.setDate(calendar.getTime());
	}

	/**
	 * 
	 * 初始化布局
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:18:23
	 */
	private void init() {
		//"运单变更审核权限"
		setTitle(i18n.get("foss.gui.changing.waybillRfcAuthorizeDialog.init.auditRfcPrivileges.label"));
		FormLayout formLayout = new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,});
		getContentPane().setLayout(formLayout);
		
		//委托人：
		JLabel label = new JLabel(i18n.get("foss.gui.changing.waybillRfcAuthorizeDialog.init.client.label"));
		getContentPane().add(label, "2, 2, right, default");
		
		operateUserName = new JTextField();
		getContentPane().add(operateUserName, "4, 2, fill, default");
		operateUserName.setColumns(TEN);
		
		//代理人：
		JLabel label1 = new JLabel(i18n.get("foss.gui.changing.waybillRfcAuthorizeDialog.init.agent.label"));
		getContentPane().add(label1, "6, 2, right, default");
		
		delegateUserName = new MultiUserChooser(false,parent);
		delegateUserName.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		getContentPane().add(delegateUserName, "8, 2, fill, default");
		
		//生效时间：
		JLabel label2 = new JLabel(i18n.get("foss.gui.changing.waybillRfcAuthorizeDialog.init.effectiveTime.label"));
		getContentPane().add(label2, "10, 2");
		
		dateTimePickerStart = new JXDateTimePicker();
		dateTimePickerStart.setFormats( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		dateTimePickerStart.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
		getContentPane().add(dateTimePickerStart, "12, 2");
		
		//终止时间：
		JLabel label3 = new JLabel(i18n.get("foss.gui.changing.waybillRfcAuthorizeDialog.init.terminateTime.label"));
		getContentPane().add(label3, "14, 2");
		
		dateTimePickerEnd = new JXDateTimePicker();
		dateTimePickerEnd.setFormats( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		dateTimePickerEnd.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
		getContentPane().add(dateTimePickerEnd, "16, 2");
		
		//代理原因：
		JLabel label4 = new JLabel(i18n.get("foss.gui.changing.waybillRfcAuthorizeDialog.init.agentReason.label"));
		getContentPane().add(label4, "2, 4");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane, "4, 4, 13, 1, fill, fill");
		
		delegateReason = new JTextArea();
		delegateReason.setLineWrap(true);
		scrollPane.setViewportView(delegateReason);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setHgap(THIRDTY);
		getContentPane().add(panel, "2, 6, 15, 1, fill, fill");
		
		Dimension dimension = new Dimension(FIFTY, THIRDTY);
		
		//提交
		btnSubmit = new JButton(i18n.get("foss.gui.changing.waybillRFCUI.common.confirm"));
		btnSubmit.setMinimumSize(dimension);
		btnSubmit.setMaximumSize(dimension);
		btnSubmit.setPreferredSize(dimension);
		panel.add(btnSubmit);
		
		//返回
		btnReturn = new JButton(i18n.get("foss.gui.changing.waybillRFCUI.common.cancel"));
		btnReturn.setMinimumSize(dimension);
		btnReturn.setMaximumSize(dimension);
		btnReturn.setPreferredSize(dimension);
		panel.add(btnReturn);
		
		
		setSize(SEVENHUNDRED,THREEHUNDRED);
		setModal(true);
		setResizable(false);
	}

	/**
	 * 
	 * 返回添加、修改页面绑定对象
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 上午10:41:26
	 */
	public WaybillRfcAgentEntity getWaybillRfcAgentEntity() {
		return bean;
	}

	
	/**
	 * @return the operateUserName
	 */
	public JTextField getOperateUserName() {
		return operateUserName;
	}

	
	/**
	 * @return the delegateUserName
	 */
	public MultiUserChooser getDelegateUserName() {
		return delegateUserName;
	}

	
	/**
	 * @return the dateTimePickerStart
	 */
	public JXDateTimePicker getDateTimePickerStart() {
		return dateTimePickerStart;
	}

	
	/**
	 * @return the delegateReason
	 */
	public JTextArea getDelegateReason() {
		return delegateReason;
	}

	
	/**
	 * @return the dateTimePickerEnd
	 */
	public JXDateTimePicker getDateTimePickerEnd() {
		return dateTimePickerEnd;
	}

	
	/**
	 * @return the parent
	 */
	public WaybillRFCAuthorizeUI getRFCAuthorizeUI() {
		return parent;
	}
	
}