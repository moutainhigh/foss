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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/domain/InstationMsgEntity.java
 * 
 * FILE NAME        	: InstationMsgEntity.java
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
 * 站内消息明细
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-7 下午5:03:14
 */ 
public class InstationMsgEntity extends BaseEntity {

	private static final long serialVersionUID = 2042923349402926318L;
 
	/**
	 * 发送人员编码
	 */
	private String sendUserCode;
	private String sendUserName;
	
	/**
	 * 发送方部门编码
	 * 如果是系统发送则用
	 * 常量类 MessageConstants
	 * 属性 MSG__SYS_ORG_CODE
	 */
	private String sendOrgCode;
	private String sendOrgName;
	
	/**
	 * 接收人员编码
	 * 如果是全网通知则用
	 * 常量类 MessageConstants
	 * 属性 MSG__SYS_USER_CODE
	 */
	private String receiveUserCode;
	private String receiveUserName;

	
	/**
	 * 消息内容
	 */
	private String context;

	/**
	 * 站内消息类型
	 * 站内消息/全网通知
	 * 所需常量 
	 * 数据字典类 DictionaryValueConstants 
	 * 常量
	 * MSG_TYPE__NORMAL 普通消息
	 * MSG_TYPE__ALLNET 全网消息
	 */
	private String msgType;
	
	/**
	 * 消息状态
	 * 有效/无效
	 */
	private String active;
	
	/**
	 * 生成方式
	 * 消息常量类 MessageConstants
	 * 常量
	 * MSG__CREATE_TYPE__AUTO  系统生成
	 * MSG__CREATE_TYPE__MANUAL 手工输入
	 * 
	 */
	private String createType;
	
	/**
	 * 是否已读
	 * 数据字典类  DictionaryValueConstants
	 * 常量 
	 * MSG__READ_STATUS__READ 已读
	 * MSG__READ_STATUS__UNREAD  未读
	 */
	private String isReaded;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 失效时间
	 */
	private Date expireTime;
	/**
	 * 接收方组织编码
	 */
	private String receiveOrgCode;
	/**
	 * 接收方组织名称
	 */
	private String receiveOrgName;
	/**
	 * 接收方下级组织编码
	 */
	private String receiveSubOrgCode;
	/**
	 * 接收方下级组织名称
	 */
	private String receiveSubOrgName;
	
	/**
	 * 接收方角色编码
	 */
	private String receiveRoleCode;
	
	/**
	 * 接收方类型 O:组织;OR:组织和角色
	 */
	private String receiveType;
	
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 异常操作类型
	 */
	private String exceptionOperate;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 修改人员编码
	 */
	private String modifyUserCode;
	
	/**
	 * 修改人员名称
	 */
	private String modifyUserName;
	
	/**
	 * 序号标识
	 */
	private String serialNumber;
	
	
 
	public String getExceptionOperate() {
		return exceptionOperate;
	}

	public void setExceptionOperate(String exceptionOperate) {
		this.exceptionOperate = exceptionOperate;
	}

	public InstationMsgEntity() {

	} 

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSendUserCode() {
		return sendUserCode;
	}

	public void setSendUserCode(String sendUserCode) {
		this.sendUserCode = sendUserCode;
	}

	public String getReceiveUserCode() {
		return receiveUserCode;
	}

	public void setReceiveUserCode(String receiveUserCode) {
		this.receiveUserCode = receiveUserCode;
	}

	
	public String getActive() {
		return active;
	}
	
	public void setActive(String active) {
		this.active = active;
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

	
	public String getCreateType() {
		return createType;
	}

	
	public void setCreateType(String createType) {
		this.createType = createType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	public String getIsReaded() {
		return isReaded;
	}

	
	public void setIsReaded(String isReaded) {
		this.isReaded = isReaded;
	}
	
	public Date getExpireTime() {
		return expireTime;
	}
	
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	public String getSendUserName() {
		return sendUserName;
	}
	
	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}
	
	public String getSendOrgCode() {
		return sendOrgCode;
	}

	
	public void setSendOrgCode(String sendOrgCode) {
		this.sendOrgCode = sendOrgCode;
	}

	
	public String getSendOrgName() {
		return sendOrgName;
	}

	
	public void setSendOrgName(String sendOrgName) {
		this.sendOrgName = sendOrgName;
	}

	public String getReceiveUserName() {
		return receiveUserName;
	}

	
	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public String getReceiveSubOrgCode() {
		return receiveSubOrgCode;
	}

	public void setReceiveSubOrgCode(String receiveSubOrgCode) {
		this.receiveSubOrgCode = receiveSubOrgCode;
	}

	public String getReceiveSubOrgName() {
		return receiveSubOrgName;
	}

	public void setReceiveSubOrgName(String receiveSubOrgName) {
		this.receiveSubOrgName = receiveSubOrgName;
	}
}
