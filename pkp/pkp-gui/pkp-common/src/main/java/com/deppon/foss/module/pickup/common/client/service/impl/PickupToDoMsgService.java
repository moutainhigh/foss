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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/PickupToDoMsgService.java
 * 
 * FILE NAME        	: PickupToDoMsgService.java
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
package com.deppon.foss.module.pickup.common.client.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.IPickupToDoMsgService;
import com.deppon.foss.module.pickup.waybill.shared.dto.PickupToDoMsgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IPickupToDoMsgHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcHessianRemoting;

/**
 * 本地定时提醒接口
 * 
 * @author 026113-foss-linwensheng
 * 
 */
public class PickupToDoMsgService implements IPickupToDoMsgService {
	

	//日志对象
	private static final Log LOG = LogFactory.getLog(PickupToDoMsgService.class);


	/*******待办事项消息*******/
	/** 
	 * <p>
	 * Description:根据传入条件查询待办事项，目前参数为业务类型（可为Null）<br />
	 * </p>
	 * @author admin
	 * @version 0.1 2012-11-5S
	 * @param toDoMsgDto
	 * @return
	 * List<ToDoMsgDto>
	 */
	public List<PickupToDoMsgDto> queryToDoMsgList(PickupToDoMsgDto toDoMsgDto) {
		try {
			IPickupToDoMsgHessianRemoting pickupToDoMsgHessianRemoting = DefaultRemoteServiceFactory
					.getService(IPickupToDoMsgHessianRemoting.class);
			return pickupToDoMsgHessianRemoting.queryToDoMsgList(toDoMsgDto.getReceiveOrgCode());
		} catch (Exception e) {
			LOG.error("queryToDoMsgList exception", e);
		}
		return null;
	}
	
	
	public Long queryTodoActionList(TodoConditionDto todoConditionDto){
		try{
			IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory
					.getService(IWaybillRfcHessianRemoting.class);
			return waybillRfcHessianRemoting.queryGoodsInfoCount(todoConditionDto);
		} catch (Exception e) {
			LOG.error("queryToDoMsgList exception", e);
		}
		return new Long(0);
	}


	/**
	 * DMANA-9463 整车运单未签收提示功能
	 * @author 218438-foss-zhulei
	 */
	public List<PickupToDoMsgDto> queryWaybillNotSignDataList(PickupToDoMsgDto toDoMsgDto) {
		try{
			IWaybillHessianRemoting waybillHessianRemoting=DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
			return waybillHessianRemoting.queryWaybillNotSignDataList(toDoMsgDto);
		} catch (Exception e) {
			LOG.error("queryToDoMsgList exception", e);
		}
		return null;
	}
	
}