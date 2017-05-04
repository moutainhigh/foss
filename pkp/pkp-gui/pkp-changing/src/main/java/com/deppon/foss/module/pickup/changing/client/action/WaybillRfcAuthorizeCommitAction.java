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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/action/WaybillRfcAuthorizeCommitAction.java
 * 
 * FILE NAME        	: WaybillRfcAuthorizeCommitAction.java
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
package com.deppon.foss.module.pickup.changing.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.mainframe.client.utills.ExceptionHandler;
import com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcVarificationService;
import com.deppon.foss.module.pickup.changing.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changing.client.ui.dialog.WaybillRfcAuthorizeDialog;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentPersonEntity;


/**
 * 
 * 提交运单审核授权数据
 * @author 102246-foss-shaohongliang
 * @date 2012-12-17 上午9:51:56
 */
public class WaybillRfcAuthorizeCommitAction implements IButtonActionListener<WaybillRfcAuthorizeDialog>{
	
	/**
	 * log
	 */
	private static final Logger LOG = Logger
			.getLogger(WaybillRfcAuthorizeCommitAction.class);
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(WaybillRfcAuthorizeCommitAction.class);
	/**
	 * ui object
	 */
	private WaybillRfcAuthorizeDialog ui;
	
	/**
	 * 变更单审核受理Service
	 */
	private IWaybillRfcVarificationService rfcVarificationService = WaybillRfcServiceFactory.getWaybillRfcVarificationService();

	/**
	 * 
	 * 提交审核代理
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:26:06
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		LOG.debug(ui);//sonar use it
		try {
			WaybillRfcAgentEntity bean = prepareRfcAgentEntity();
			validate(bean);
			
			
			
			if(bean.getId() == null){
				//标示首次新增
				bean.setFlagAdd(WaybillConstants.FIRST_ADD);
				//新增				
				rfcVarificationService.addWayBillRfcAgent(bean);
			}else{
				//获取状态
				String status = bean.getStatus();
				if(WaybillRfcConstants.RFC_AGENT_STATUS_AVALIABLE.equals(status)){
					/**
					 * 判断是否已生效
					 */
					if(!ui.getDateTimePickerStart().isEnabled()){
						//标示为修改已生效时新增
						bean.setFlagAdd(WaybillConstants.UPDATE_ADD);
					}
				}	
				//修改
				rfcVarificationService.updateWaybillRfcAgent(bean);
				
			}
			ui.dispose();
			//刷新表格数据
			ui.getRFCAuthorizeUI().refreshData();
		} catch (BusinessException be) {
			ExceptionHandler.alert(be);
		}
	}

	/**
	 * 
	 * 校验数据合法性
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 下午12:12:19
	 */
	private void validate(WaybillRfcAgentEntity bean) {
		if(bean.getAgentEmpList().isEmpty()){
			MsgBox.showInfo(i18n.get("foss.gui.changing.waybillRfcAuthorizeCommitAction.emptyAgentEmpList"));
			return;
//			throw new WaybillValidateException(i18n.get("foss.gui.changing.waybillRfcAuthorizeCommitAction.emptyAgentEmpList"));
		}
		if(StringUtil.isEmpty(bean.getAgentReason())){
			MsgBox.showInfo(i18n.get("foss.gui.changing.waybillRfcAuthorizeCommitAction.emptyAgentReason"));
			return;
//			throw new WaybillValidateException(i18n.get("foss.gui.changing.waybillRfcAuthorizeCommitAction.emptyAgentReason"));
		}
		if(bean.getValidTime() == null){
			MsgBox.showInfo(i18n.get("foss.gui.changing.waybillRfcAuthorizeCommitAction.nullValidTime"));
			return;
//			throw new WaybillValidateException(i18n.get("foss.gui.changing.waybillRfcAuthorizeCommitAction.nullValidTime"));
		}
		if(bean.getOverdueTime() == null){
			MsgBox.showInfo(i18n.get("foss.gui.changing.waybillRfcAuthorizeCommitAction.nullOverdueTime"));
			return;
//			throw new WaybillValidateException(i18n.get("foss.gui.changing.waybillRfcAuthorizeCommitAction.nullOverdueTime"));
		}
		if(bean.getValidTime().after(bean.getOverdueTime())){
			MsgBox.showInfo(i18n.get("foss.gui.changing.waybillRfcAuthorizeCommitAction.afterOverdueTime"));
			return;
//			throw new WaybillValidateException(i18n.get("foss.gui.changing.waybillRfcAuthorizeCommitAction.afterOverdueTime"));
		}
	}

	/**
	 * 
	 * 准备审核代理数据
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 上午10:40:32
	 */
	private WaybillRfcAgentEntity prepareRfcAgentEntity() {
		WaybillRfcAgentEntity bean = ui.getWaybillRfcAgentEntity();
		if(bean == null){
			bean = new WaybillRfcAgentEntity();
		}
		// 代理人
		List<EmployeeEntity> employeeEntities = ui.getDelegateUserName().getEmployeeEntities();
		List<WaybillRfcAgentPersonEntity> rfcAgentPersonEntities = new ArrayList<WaybillRfcAgentPersonEntity>();
		for(EmployeeEntity employeeEntity : employeeEntities){
			WaybillRfcAgentPersonEntity agentPersonEntity = new WaybillRfcAgentPersonEntity();
			agentPersonEntity.setAgentEmpCode(employeeEntity.getEmpCode());
			agentPersonEntity.setAgentEmpName(employeeEntity.getEmpName());
			rfcAgentPersonEntities.add(agentPersonEntity);
		}
		bean.setAgentEmpList(rfcAgentPersonEntities);
		// 生效时间
		bean.setValidTime(ui.getDateTimePickerStart().getDate());
		// 终止时间
		bean.setOverdueTime(ui.getDateTimePickerEnd().getDate());
		// 代理原因
		bean.setAgentReason(ui.getDelegateReason().getText());
		
		return bean;
	}

	/**
	 * 
	 * UI注入
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:48:26
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
	@Override
	public void setInjectUI(WaybillRfcAuthorizeDialog ui) {
		this.ui = ui;
	}

}