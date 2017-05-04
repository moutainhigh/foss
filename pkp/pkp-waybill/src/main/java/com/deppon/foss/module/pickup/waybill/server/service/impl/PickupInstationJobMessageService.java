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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/PickupInstationJobMessageService.java
 * 
 * FILE NAME        	: PickupInstationJobMessageService.java
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
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPickupInstationJobMessageService;
import com.deppon.foss.module.pickup.waybill.shared.dto.PickupInstationJobMessageDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 人员对组织的站内消息发送
 * 调用综合提供的接口bse-common-api.IMessageService
 * @author niujian
 * @date 2012-12-28
 */
public class PickupInstationJobMessageService implements
		IPickupInstationJobMessageService {
	/**
	 * 站内消息Service
	 */
	IMessageService messageService=null;
	/**
	 * 返回站内消息Service
	 * @return
	 */
	public IMessageService getMessageService() {
		return messageService;
	}
	/**
	 * 设置站内消息Service
	 * @return
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
	/**
	 * 人员对组织的站内消息发送
	 * 调用综合提供的接口bse-common-api.IMessageService
	 * @author niujian
	 * @date 2012-12-28
	 */
	public void createBatchInstationMsg(PickupInstationJobMessageDto msgDto) {
		
		InstationJobMsgEntity entity = new InstationJobMsgEntity();//站内消息Job分发表
		entity.setSenderCode(msgDto.getSenderCode());
		entity.setReceiveOrgCode(msgDto.getReceiveOrgCode());//接收方组织编码 
		entity.setContext(msgDto.getContext());//消息内容
		entity.setReceiveType(msgDto.getReceiveType());//接收方类型
		entity.setMsgType(msgDto.getMsgType());//MSG_TYPE__NORMAL 普通消息
		
		messageService.createBatchInstationMsg(entity);//人员对组织的站内消息发送
	}
	
	/**
	 * 人员对组织的站内消息发送,发送系统消息，发送目标固定是组织
	 * 调用综合提供的接口bse-common-api.IMessageService
	 * @author niujian
	 * @date 2012-12-28
	 */
	public void createSysInstationMsgToOrg(String pReceiveOrgCode,
			String pContext) {
		
		InstationJobMsgEntity entity = new InstationJobMsgEntity();//站内消息Job分发表
		String uuid = UUIDUtils.getUUID();//uuid
		entity.setId(uuid);//id
		entity.setSenderCode(MessageConstants.MSG__SYS_USER_CODE);// 发送方编码
		entity.setSenderName(MessageConstants.MSG__SYS_USER_CODE);// 发送方
		
		entity.setSenderOrgCode(MessageConstants.MSG__SYS_ORG_CODE);//发送方部门编码
		entity.setSenderOrgName(MessageConstants.MSG__SYS_ORG_CODE);//发送方部门
		entity.setReceiveOrgCode(pReceiveOrgCode);//接收方组织编码 
		entity.setContext(pContext);//消息内容
		entity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);//接收方类型组织
		entity.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL);//普通消息
		
		messageService.createBatchInstationMsg(entity);//人员对组织的站内消息发送
	}

}