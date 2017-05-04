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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/action/WaybillRfcAuthorizeQueryAction.java
 * 
 * FILE NAME        	: WaybillRfcAuthorizeQueryAction.java
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcVarificationService;
import com.deppon.foss.module.pickup.changing.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCAuthorizeUI;
import com.deppon.foss.module.pickup.changing.client.vo.CheckStatusVo;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcQueryAgentConDto;
import com.deppon.foss.util.DateUtils;

/**
 * 
 * 查询运单审核授权数据
 * @author 102246-foss-shaohongliang
 * @date 2012-12-17 上午9:51:56
 */
public class WaybillRfcAuthorizeQueryAction implements IButtonActionListener<WaybillRFCAuthorizeUI>{
	
	/**
	 * log
	 */
	private static final Logger LOG = Logger
			.getLogger(WaybillImportAction.class);
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(WaybillRfcAuthorizeQueryAction.class);
	/**
	 * ui object
	 */
	private WaybillRFCAuthorizeUI ui;
	
	/**
	 * 变更单审核受理Service
	 */
	private IWaybillRfcVarificationService rfcVarificationService = WaybillRfcServiceFactory.getWaybillRfcVarificationService();

	/**
	 * 
	 * 查询审核代理
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:25:37
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		LOG.debug(e);
		//生效时间不能前面事件大于后面的时间
		if(ui.getDatePickerStart().getDate().compareTo(ui.getDatePickerEnd().getDate())>0){
			MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.timeParadox"));
			return;
		}
		//不能超过一个月
		if(DateUtils.getTimeDiff(ui.getDatePickerStart().getDate(), ui.getDatePickerEnd().getDate()) > NumberConstants.NUMBER_30){
			MsgBox.showInfo(i18n.get("foss.gui.changing.waybillRfcDealQueryAction.msgBox.notPassMonthDays"));
			return;
		}
		WaybillRfcQueryAgentConDto queryAgentConDto = getQueryAgentConDto();
		//开始时间
		queryAgentConDto.setBeginDate(ui.getDatePickerStart().getDate());
		queryAgentConDto.setEndDate(ui.getDatePickerEnd().getDate());
		//设置生效状态
		queryAgentConDto.setStatus(((CheckStatusVo)ui.getComboBoxType().getSelectedItem()).getCheckStatusCode());
		try{
		List<WaybillRfcAgentEntity> rfcAgentEntities = rfcVarificationService.queryWaybillRfcAgent(queryAgentConDto);
		String status = queryAgentConDto.getStatus();
		//只针对对审核未生效的类型数据进行筛选
		List<WaybillRfcAgentEntity> removeInvalidRfcDtos = new ArrayList<WaybillRfcAgentEntity>();
		if(StringUtils.equals(WaybillRfcConstants.RFC_AGENT_STATUS_INVALID, status)){
			removeInvalidRfcDtos = removeInvalidRfcDtos(rfcAgentEntities);
		}else{
			removeInvalidRfcDtos = rfcAgentEntities;
		}		
		//给表格填充数据
		ui.getDataModel().setData(removeInvalidRfcDtos);
		ui.getDataModel().fireTableDataChanged();
		if(rfcAgentEntities.isEmpty()){
			MsgBox.showError(i18n.get("foss.gui.changing.waybillRfcAuthorizeQueryAction.msgBox.emptyRfcAgentEntities"));
			return;
		}
		
		}catch(BusinessException e2)
		{
			MsgBox.showError(MessageI18nUtil.getMessage(e2, i18n));
			return;
		}
		
	}

	private List<WaybillRfcAgentEntity> removeInvalidRfcDtos(
			List<WaybillRfcAgentEntity> rfcAgentEntities) {
		List<WaybillRfcAgentEntity> removeRfcAgentEntities = new ArrayList<WaybillRfcAgentEntity>();
		if(CollectionUtils.isNotEmpty(rfcAgentEntities)){			
			for (Iterator<WaybillRfcAgentEntity> iterator = rfcAgentEntities.iterator(); iterator.hasNext();) {
				WaybillRfcAgentEntity rfcAgentEntity = iterator.next();
				if(WaybillRfcConstants.RFC_AGENT_STATUS_AVALIABLE.equals(rfcAgentEntity.getStatus())){
					Date validTime = rfcAgentEntity.getValidTime();
					if(validTime != null && validTime.before(new Date())){
						//啥都不做，因为这个是已经生效了的，我需要不生效的
					}else{
						removeRfcAgentEntities.add(rfcAgentEntity);
					}
					
				}
			}
		}
		return removeRfcAgentEntities;
	}

	/**
	 * 
	 * 获取查询条件
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 下午7:36:09
	 */
	private WaybillRfcQueryAgentConDto getQueryAgentConDto() {
		WaybillRfcQueryAgentConDto queryAgentConDto = new WaybillRfcQueryAgentConDto();
		List<EmployeeEntity> employeeEntities = ui.getTxtUser().getEmployeeEntities();
		//代理人选择不为空
		if(employeeEntities.size()>0){
			queryAgentConDto.setAgentCode(employeeEntities.get(0).getEmpCode());
		}
		Date beginDate=DateUtils.getStartDatetime(ui.getDatePickerStart().getDate());
		Date endDate=DateUtils.getEndDatetime(ui.getDatePickerEnd().getDate());
		//开始时间
		queryAgentConDto.setBeginDate(beginDate);
		//终止时间
		queryAgentConDto.setEndDate(endDate);
		
		//审核状态
		CheckStatusVo checkStatusVo = (CheckStatusVo)ui.getComboBoxType().getSelectedItem();
		queryAgentConDto.setStatus(checkStatusVo.getCheckStatusCode());
		return queryAgentConDto;
	}

	/**
	 * 
	 * UI注入
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:48:26
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
	@Override
	public void setInjectUI(WaybillRFCAuthorizeUI ui) {
		this.ui = ui;
	}

}