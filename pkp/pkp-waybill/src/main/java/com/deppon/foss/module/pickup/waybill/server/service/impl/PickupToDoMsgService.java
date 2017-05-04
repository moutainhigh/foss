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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/PickupToDoMsgService.java
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
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IToDoMsgService;
import com.deppon.foss.module.base.common.api.shared.domain.ToDoMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.ToDoMsgDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPickupToDoMsgService;
import com.deppon.foss.module.pickup.waybill.shared.dto.PickupToDoMsgDto;

/**
 * 
 * GUI消息提醒
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2013-3-11 下午5:30:01, </p>
 * @author foss-sunrui
 * @date 2013-3-11 下午5:30:01
 * @since
 * @version
 */
public class PickupToDoMsgService implements IPickupToDoMsgService {

	/**
	 * 系统管理员
	 */
	private static final String SYSTEM = "system";
	/**
	 * 消息提醒服务
	 */
	private IToDoMsgService toDoMsgService;
	
	/**
	 * @return  the toDoMsgService
	 */
	public IToDoMsgService getToDoMsgService() {
		return toDoMsgService;
	}

	/**
	 * @param toDoMsgService the toDoMsgService to set
	 */
	public void setToDoMsgService(IToDoMsgService toDoMsgService) {
		this.toDoMsgService = toDoMsgService;
	}

	/**
	 * 
	 * 通过收货部门编号查询通知信息
	 * @author foss-sunrui
	 * @date 2013-3-11 下午5:31:02
	 * @param receiveOrgCode
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IPickupToDoMsgService#queryToDoMsgList(java.lang.String)
	 */
	public List<PickupToDoMsgDto> queryToDoMsgList(String receiveOrgCode) {
		if(StringUtil.isBlank(receiveOrgCode)){
			return null;
		}
		//根据接收网点编码,接收方角色,待办类型,和状态 查询待办明细
		List<ToDoMsgDto> list = 
				toDoMsgService.countToDoMsgDetail(receiveOrgCode,DictionaryValueConstants.TODOMSG__STATUS_TYPE__PROCESSING);
		if (list != null) {
			Map<String,PickupToDoMsgDto> toDoMsgMap = new HashMap<String,PickupToDoMsgDto>();
			
			for (ToDoMsgDto o : list) {
				String biztype = o.getBusinessType();//业务类型
//				if(toDoMsgMap.containsKey(biztype)){
//					PickupToDoMsgDto dto = (PickupToDoMsgDto)toDoMsgMap.get(biztype);
//					dto.setNoDealNum(dto.getNoDealNum()+1);
//					toDoMsgMap.put(biztype, dto);
//				}else {
//					
//				}
				
				
				PickupToDoMsgDto dto = new PickupToDoMsgDto();//定时提醒
				dto.setBusinessType(o.getBusinessType());
				dto.setNoDealNum(o.getNoDealNum());
				dto.setReceiveOrgCode(receiveOrgCode);
				
				List<ToDoMsgDto> list2 = toDoMsgService.queryToDoMsgDetailOne( receiveOrgCode,null, o.getBusinessType(), DictionaryValueConstants.TODOMSG__STATUS_TYPE__PROCESSING);
				
				if(list2!=null&& list2.size()>0){
					ToDoMsgDto o2 = list2.get(0);
					dto.setReceiveRoleCode(o2.getReceiveRoleCode());
				}
				
			
				toDoMsgMap.put(biztype, dto);
			}
			
			Collection<PickupToDoMsgDto> col = toDoMsgMap.values();
			return  new ArrayList<PickupToDoMsgDto>(col);
		}
		return null;
	}

	/**
	 * 
	 * 创建一条消息提醒
	 * @author foss-sunrui
	 * @date 2013-3-11 下午5:31:26
	 * @param businessType
	 * @param dealUrl
	 * @param receiveRoleCode
	 * @param receiveOrgCode
	 * @param businessNo
	 * @param serialNumber 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IPickupToDoMsgService#addOneToDoMsg(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void addOneToDoMsg(String businessType, String dealUrl, String receiveRoleCode,
			String receiveOrgCode, String businessNo, String serialNumber) {
		UserEntity user = (UserEntity)UserContext.getCurrentUser();
		String empcode =null;
		String empname =null;
		//修改sonar 对user在前面判断空
		if(user==null){
			empcode = SYSTEM;
			empname = SYSTEM;
		}else{
			empcode = user.getEmployee().getEmpCode();
			empname = user.getEmployee().getEmpName();
		}
		ToDoMsgEntity toDoMsgEntity = new ToDoMsgEntity();//待办事项明细
		toDoMsgEntity.setBusinessType(businessType);//业务类型
		toDoMsgEntity.setReceiveRoleCode(receiveRoleCode);//接收方角色编码
		toDoMsgEntity.setReceiveOrgCode(receiveOrgCode);//接收方组织编码
		toDoMsgEntity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);// 接收方类型 组织
		toDoMsgEntity.setBusinessNo(businessNo);//业务单号
		toDoMsgEntity.setSerialNumber(serialNumber);//待办流水号
		toDoMsgEntity.setDealUrl(dealUrl);// 处理待办链接地址
		toDoMsgEntity.setStatus(DictionaryValueConstants.TODOMSG__STATUS_TYPE__PROCESSING);	//未处理
		toDoMsgEntity.setCreateUserCode(empcode);// 创建人员编码
		toDoMsgEntity.setCreateUserName(empname);// 创建人员
		
		if(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_AUDIT_WAYBILL.equals(businessType)
			|| DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_ACCECPT_WAYBILL.equals(businessType)
			|| DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__PDA_WAYBILLRFC.equals(businessType)
			|| DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_APPLY_WAYBILL.equals(businessType)
			||DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_AUDIT.equals(businessType)
			|| DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_ACCECPT.equals(businessType)
			|| DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__PDA_WAYBILL.equals(businessType)
			|| DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_APPLY.equals(businessType)
				){
			toDoMsgEntity.setUrlType(MessageConstants.MSG__URL_TYPE__GUI);
		}else{
			toDoMsgEntity.setUrlType(MessageConstants.MSG__URL_TYPE__WEB);
		}
		
		
		List<ToDoMsgEntity> lst = new ArrayList<ToDoMsgEntity>();
		lst.add(toDoMsgEntity);
		toDoMsgService.createIncrementToDoMsg(lst);//单个或批量创建待办(增量) 同种待办类型不同业务单号的待办进行新建待办
	}

	/**
	 * 
	 * 移除消息提醒
	 * @author foss-sunrui
	 * @date 2013-3-11 下午5:31:46
	 * @param businessType
	 * @param receiveOrgCode
	 * @param serialNumber 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IPickupToDoMsgService#removeOneToDoMsg(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void removeOneToDoMsg(String businessType, String receiveOrgCode,String serialNumber) {
	
		
		if(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__PDA_WAYBILL.equals(businessType)){
		toDoMsgService.finishToDoMsgs(receiveOrgCode,businessType,serialNumber);
		}else{
		toDoMsgService.finishToDoMsg(receiveOrgCode,businessType,serialNumber);
		}
	}

	/**
	 * 
	 * 更新提醒消息
	 * @author foss-sunrui
	 * @date 2013-3-11 下午5:32:04
	 * @param businessType
	 * @param receiveRoleCode
	 * @param receiveOrgCode
	 * @param businessNo
	 * @param serialNumberlst 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IPickupToDoMsgService#updateSomeToDoMsg(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public void updateSomeToDoMsg(String businessType, String receiveRoleCode,
			String receiveOrgCode, String businessNo, List<String> serialNumberlst) {
		
		UserEntity user = (UserEntity)UserContext.getCurrentUser();
		String empcode =null;
		String empname =null;
		//修改sonar 对user在前面判断空
		if(user==null){
			empcode = SYSTEM;
			empname = SYSTEM;
		}else{
			empcode = user.getEmployee().getEmpCode();
			empname = user.getEmployee().getEmpName();
		}
		
		List<ToDoMsgEntity> lst = new ArrayList<ToDoMsgEntity>();
		for(String serialNumber : serialNumberlst){
			ToDoMsgEntity toDoMsgEntity = new ToDoMsgEntity();//待办事项明细
			toDoMsgEntity.setBusinessType(businessType);//业务类型
			toDoMsgEntity.setReceiveRoleCode(receiveRoleCode);//接收方角色编码
			toDoMsgEntity.setReceiveOrgCode(receiveOrgCode);//接收方组织编码
			toDoMsgEntity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);// 接收方类型 组织
			toDoMsgEntity.setBusinessNo(businessNo);//业务单号
			toDoMsgEntity.setSerialNumber(serialNumber);//待办流水号
			toDoMsgEntity.setDealUrl(businessType);// 处理待办链接地址
			toDoMsgEntity.setStatus(DictionaryValueConstants.TODOMSG__STATUS_TYPE__PROCESSING);//未处理
			toDoMsgEntity.setCreateUserCode(empcode);// 创建人员编码
			toDoMsgEntity.setCreateUserName(empname);// 创建人员
			lst.add(toDoMsgEntity);
		}
		toDoMsgService.createFullToDoMsg(lst);
	}
}