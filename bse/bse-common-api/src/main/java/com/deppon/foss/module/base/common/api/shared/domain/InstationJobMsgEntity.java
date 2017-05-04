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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/domain/InstationJobMsgEntity.java
 * 
 * FILE NAME        	: InstationJobMsgEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 站内消息Job分发表
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-7 下午5:15:01
 */
public class InstationJobMsgEntity extends BaseEntity {

	private static final long serialVersionUID = -5142645611309855100L;
	/**
	 * 发送方编码
	 * 如果是系统发送则用
	 * 常量类 MessageConstants
	 * 属性 MSG__SYS_USER_CODE
	 */
	private String senderCode;
	private String senderName;
	/**
	 * 发送方部门编码
	 * 如果是系统发送则用
	 * 常量类 MessageConstants
	 * 属性 MSG__SYS_ORG_CODE
	 */
	private String senderOrgCode;
	private String senderOrgName;
 
	/**
	 * 接收方组织编码 
	 */
	private String receiveOrgCode;
	private String receiveOrgName;
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
	 * 有效时间
	 * 全网消息必填
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

	
	public String getSenderName() {
		return senderName;
	}

	
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	
	public String getSenderOrgCode() {
		return senderOrgCode;
	}

	
	public void setSenderOrgCode(String senderOrgCode) {
		this.senderOrgCode = senderOrgCode;
	}

	
	public String getSenderOrgName() {
		return senderOrgName;
	}

	
	public void setSenderOrgName(String senderOrgName) {
		this.senderOrgName = senderOrgName;
	}

	
	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

}
