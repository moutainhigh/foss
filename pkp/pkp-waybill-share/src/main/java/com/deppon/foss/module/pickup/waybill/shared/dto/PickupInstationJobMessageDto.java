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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/PickupInstationJobMessageDto.java
 * 
 * FILE NAME        	: PickupInstationJobMessageDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * 站内消息调用接口参数传递dto
 * @author niujian
 * @date 2012-12-28
 */
public class PickupInstationJobMessageDto implements Serializable {

	private static final long serialVersionUID = 5450224940596768916L;

	private String id;

	/**
	 * 发送方编码
	 * 如果是系统发送则用
	 * 常量类 MessageConstants
	 * 属性 MSG__SENDERCODE_SYS
	 */
	private String senderCode;

	/**
	 * 接收方组织编码 
	 */
	private String receiveOrgCode;

	/**
	 * 接收方角色编码
	 */
	private String receiveRoleCode;

	/**
	 * 接收方类型；
	 * 消息常量类 MessageConstants
	 * 常量
	 * MSG__RECEIVE_TYPE__ORG  组织 
	 * MSG__RECEIVE_TYPE__ORG_ROLE 组织+角色
	 * 
	 */
	private String receiveType;

	/**
	 * 消息内容
	 */
	private String context;
	/**
	 * 站内消息类型
	 * 普通消息/全网通知
	 * 所需常量 
	 * 数据字典类 DictionaryValueConstants 
	 * 常量
	 * MSG_TYPE__NORMAL 普通消息
	 * MSG_TYPE__ALLNET 全网消息
	 */
	private String msgType;
	
	/**
	 * 发送时间
	 */
	private Date postDate;
	/**
	 * 失效时间
	 */
	private Date expireTime;
	/**
	 * 消息状态
	 * 
	 * 所需常量
	 * MessageConstants 
	 * MSG__STATUS__PROCESSED 已处理
	 * MSG__STATUS__PROCESSING 未处理
	 */
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSenderCode() {
		return senderCode;
	}

	public void setSenderCode(String senderCode) {
		this.senderCode = senderCode;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	
	public String getReceiveRoleCode() {
		return receiveRoleCode;
	}

	
	public void setReceiveRoleCode(String receiveRoleCode) {
		this.receiveRoleCode = receiveRoleCode;
	}

	
	public String getReceiveType() {
		return receiveType;
	}

	
	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public Date getExpireTime() {
		return expireTime;
	}

	
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
}