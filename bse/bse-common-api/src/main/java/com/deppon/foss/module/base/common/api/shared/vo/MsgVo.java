/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/vo/MsgVo.java
 * 
 * FILE NAME        	: MsgVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.domain.WinFormSettingEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MessagesDto;
import com.deppon.foss.module.base.common.api.shared.dto.ToDoMsgDto;

/**
 * 待办消息Vo
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-10 上午8:58:42
 */
public class MsgVo implements Serializable {

	private static final long serialVersionUID = 5408182178268252309L;

	/**
	 * 待办事项实体Dto
	 */
	private ToDoMsgDto  toDoDto;

	/**
	 * 站内消息明细Dto
	 */
	private MessagesDto msgDto;
	
	/**
	 * 消息分发Entity
	 */
	private InstationJobMsgEntity msgJobEntity;
	/**
	 * 弹出框设置间隔
	 */
	private WinFormSettingEntity winEntity;

	/**
	 * 未处理总记录数
	 */
	private int noDealtotal;
	
	/**
	 * 消息类型
	 */
	private String msgType;
	
	/**
	 *待办业务类型
	 */
	private String  bisType;
	/**
	 * 待办事项列
	 */
	private List<ToDoMsgDto> toDoMsgList;

	/**
	 * 站内消息明细列
	 */
	private List<MessagesDto> messageList;

	/**
	 * 普通消息明细列表
	 */
	private List<MessagesDto> normalMsgList;
	/**
	 * 代收货款在线提醒列表
	 */
	private List<MessagesDto> collectionMsgList;
	private List<MessagesDto> failingInvoiceMsgList;
	//设置未对账月结客户消息187862-dujunhui
	private List<MessagesDto> unReconciliationMsgList;
	//设置距结款时间不足5日还未还款月结客户消息187862-dujunhui
	private List<MessagesDto> noRepaymentMsgList;

	/**
	 * 全网消息明细列表
	 */
	private List<MessagesDto> netMsgList;
 
	/**
	 * 站内消息Id集合
	 */
	private List<String> ids;
	/**
	 * 弃货集合
	 */
	private List<ToDoMsgDto> autoAbandMsgList;
	
	/**
	 * CC催单信息集合
	 */
	private List<ToDoMsgDto> callCenterWaybillMsgList;
	/**
	 * 结算天数小于10天提醒
	 */
	private List<MessagesDto> debitWillOverList;
	
	/**
	 * 派送退回提醒
	 */
	private List<MessagesDto> deliveryReturnList;
	
	
	public List<MessagesDto> getDeliveryReturnList() {
		return deliveryReturnList;
	}

	public void setDeliveryReturnList(List<MessagesDto> deliveryReturnList) {
		this.deliveryReturnList = deliveryReturnList;
	}

	public List<MessagesDto> getDebitWillOverList() {
		return debitWillOverList;
	}

	public void setDebitWillOverList(List<MessagesDto> debitWillOverList) {
		this.debitWillOverList = debitWillOverList;
	}

	public List<MessagesDto> getCollectionMsgList() {
		return collectionMsgList;
	}

	public void setCollectionMsgList(List<MessagesDto> collectionMsgList) {
		this.collectionMsgList = collectionMsgList;
	}

	public String getBisType() {
		return bisType;
	}
	
	public void setBisType(String bisType) {
		this.bisType = bisType;
	}

	public String getMsgType() {
		return msgType;
	}
	
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public List<String> getIds() {
		return ids;
	}
	
	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public WinFormSettingEntity getWinEntity() {
		return winEntity;
	}

	public void setWinEntity(WinFormSettingEntity winEntity) {
		this.winEntity = winEntity;
	}

	 

	
	public ToDoMsgDto getToDoDto() {
		return toDoDto;
	}

	
	public void setToDoDto(ToDoMsgDto toDoDto) {
		this.toDoDto = toDoDto;
	}

	
	public MessagesDto getMsgDto() {
		return msgDto;
	}

	
	public void setMsgDto(MessagesDto msgDto) {
		this.msgDto = msgDto;
	}
 
	
	public List<ToDoMsgDto> getToDoMsgList() {
		return toDoMsgList;
	}

	
	public void setToDoMsgList(List<ToDoMsgDto> toDoMsgList) {
		this.toDoMsgList = toDoMsgList;
	}

	
	public List<MessagesDto> getMessageList() {
		return messageList;
	}

	
	public void setMessageList(List<MessagesDto> messageList) {
		this.messageList = messageList;
	}

	
	public List<MessagesDto> getNormalMsgList() {
		return normalMsgList;
	}

	
	public void setNormalMsgList(List<MessagesDto> normalMsgList) {
		this.normalMsgList = normalMsgList;
	}

	
	public List<MessagesDto> getNetMsgList() {
		return netMsgList;
	}

	
	public void setNetMsgList(List<MessagesDto> netMsgList) {
		this.netMsgList = netMsgList;
	}

	public int getNoDealtotal() {
		return noDealtotal;
	}

	public void setNoDealtotal(int noDealtotal) {
		this.noDealtotal = noDealtotal;
	}


	
	public InstationJobMsgEntity getMsgJobEntity() {
		return msgJobEntity;
	}


	
	public void setMsgJobEntity(InstationJobMsgEntity msgJobEntity) {
		this.msgJobEntity = msgJobEntity;
	}

	public List<ToDoMsgDto> getAutoAbandMsgList() {
		return autoAbandMsgList;
	}

	public void setAutoAbandMsgList(List<ToDoMsgDto> autoAbandMsgList) {
		this.autoAbandMsgList = autoAbandMsgList;
	}

	public List<MessagesDto> getFailingInvoiceMsgList() {
		return failingInvoiceMsgList;
	}

	public void setFailingInvoiceMsgList(List<MessagesDto> failingInvoiceMsgList) {
		this.failingInvoiceMsgList = failingInvoiceMsgList;
	}

	public List<ToDoMsgDto> getCallCenterWaybillMsgList() {
		return callCenterWaybillMsgList;
	}

	public void setCallCenterWaybillMsgList(
			List<ToDoMsgDto> callCenterWaybillMsgList) {
		this.callCenterWaybillMsgList = callCenterWaybillMsgList;
	}

	/**
	 * @return  the unReconciliationMsgList
	 */
	public List<MessagesDto> getUnReconciliationMsgList() {
		return unReconciliationMsgList;
	}

	/**
	 * @param unReconciliationMsgList the unReconciliationMsgList to set
	 */
	public void setUnReconciliationMsgList(List<MessagesDto> unReconciliationMsgList) {
		this.unReconciliationMsgList = unReconciliationMsgList;
	}

	/**
	 * @return  the noRepaymentMsgList
	 */
	public List<MessagesDto> getNoRepaymentMsgList() {
		return noRepaymentMsgList;
	}

	/**
	 * @param noRepaymentMsgList the noRepaymentMsgList to set
	 */
	public void setNoRepaymentMsgList(List<MessagesDto> noRepaymentMsgList) {
		this.noRepaymentMsgList = noRepaymentMsgList;
	}

}
