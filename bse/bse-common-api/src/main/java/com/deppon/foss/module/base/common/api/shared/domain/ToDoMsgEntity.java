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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/domain/ToDoMsgEntity.java
 * 
 * FILE NAME        	: ToDoMsgEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.domain;

import java.util.Date;
import java.util.Set;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 待办事项明细.
 *
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-7 下午4:45:57
 */
public class ToDoMsgEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7032238657526149459L;

	/**
	 * ID.
	 */
	private String id;
	
	/**
	 * 待办标题.
	 */
	private String title;
	
	/**
	 * 接收方组织编码.
	 */
	private String receiveOrgCode;
	
	/**
	 * 接收方下级组织编码.
	 */
	private String receiveSubOrgCode;
	
	/**
	 * 接收方下级组织名称.
	 */
	private String receiveSubOrgName;
	
	/**
	 * 接收方角色编码.
	 */
	private String receiveRoleCode;

	/**
	 * 接收方类型；
	 * 消息常量类 MessageConstants
	 * 常量
	 * MSG__RECEIVE_TYPE__ORG  组织
	 * MSG__RECEIVE_TYPE__ORG_ROLE 组织+角色.
	 */
	private String receiveType;

	/**
	 * 业务类型
	 * 
	 * 数据字典常量类 DictionaryValueConstants
	 * 参阅 综合管理-待办事项-业务类型 "值代码".
	 */
	private String businessType;
	
	/**
	 * 业务单号.
	 */
	private String businessNo;
	
	/**
	 * 待办流水号.
	 */
	private String serialNumber;
	
	/**
	 * 处理待办链接地址.
	 */
	private String dealUrl;
 
	/**
	 * 待办状态
	 * 所需常量
	 * MessageConstants
	 * MSG__STATUS__PROCESSED 已处理
	 * MSG__STATUS__PROCESSING 未处理.
	 */
	private String status;
	
	/**
	 * 待办类型
	 * 所需常量
	 * MessageConstants
	 * MSG__URL_TYPE__WEB WEB待办
	 * MSG__URL_TYPE__GUI GUI待办
	 */
	private String urlType;
	/**
	 * 创建时间.
	 */
	private Date createTime;
	
	/**
	 * 创建人员编码.
	 */
	private String createUserCode;

	/**
	 * 创建人员名称.
	 */
	private String createUserName;
	
	/**
	 * 当前用户角色编码集合.
	 */
	private Set<String> receiveRoleCodes;
	
	
	/**
	 * 获取 当前用户角色编码集合.
	 *
	 * @return the 当前用户角色编码集合
	 */
	public Set<String> getReceiveRoleCodes() {
		return receiveRoleCodes;
	}

	
	/**
	 * 设置 当前用户角色编码集合.
	 *
	 * @param receiveRoleCodes the new 当前用户角色编码集合
	 */
	public void setReceiveRoleCodes(Set<String> receiveRoleCodes) {
		this.receiveRoleCodes = receiveRoleCodes;
	}

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 待办标题.
	 *
	 * @return the 待办标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置 待办标题.
	 *
	 * @param title the new 待办标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取 创建人员编码.
	 *
	 * @return the 创建人员编码
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * 设置 创建人员编码.
	 *
	 * @param createUserCode the new 创建人员编码
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}


	/**
	 * 获取 业务类型  数据字典常量类 DictionaryValueConstants 参阅 综合管理-待办事项-业务类型 "值代码".
	 *
	 * @return the 业务类型  数据字典常量类 DictionaryValueConstants 参阅 综合管理-待办事项-业务类型 "值代码"
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * 设置 业务类型  数据字典常量类 DictionaryValueConstants 参阅 综合管理-待办事项-业务类型 "值代码".
	 *
	 * @param businessType the new 业务类型  数据字典常量类 DictionaryValueConstants 参阅 综合管理-待办事项-业务类型 "值代码"
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * 获取 创建时间.
	 *
	 * @return the 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 创建时间.
	 *
	 * @param createTime the new 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取 接收方组织编码.
	 *
	 * @return the 接收方组织编码
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * 设置 接收方组织编码.
	 *
	 * @param receiveOrgCode the new 接收方组织编码
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * 获取 接收方角色编码.
	 *
	 * @return the 接收方角色编码
	 */
	public String getReceiveRoleCode() {
		return receiveRoleCode;
	}

	/**
	 * 设置 接收方角色编码.
	 *
	 * @param receiveRoleCode the new 接收方角色编码
	 */
	public void setReceiveRoleCode(String receiveRoleCode) {
		this.receiveRoleCode = receiveRoleCode;
	}

	/**
	 * 获取 业务单号.
	 *
	 * @return the 业务单号
	 */
	public String getBusinessNo() {
		return businessNo;
	}

	/**
	 * 设置 业务单号.
	 *
	 * @param businessNo the new 业务单号
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	
	/**
	 * 获取 待办流水号.
	 *
	 * @return the 待办流水号
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	
	/**
	 * 设置 待办流水号.
	 *
	 * @param serialNumber the new 待办流水号
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * 获取 待办状态 所需常量 MessageConstants MSG__STATUS__PROCESSED 已处理 MSG__STATUS__PROCESSING 未处理.
	 *
	 * @return the 待办状态 所需常量 MessageConstants MSG__STATUS__PROCESSED 已处理 MSG__STATUS__PROCESSING 未处理
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * 获取 处理待办链接地址.
	 *
	 * @return the 处理待办链接地址
	 */
	public String getDealUrl() {
		return dealUrl;
	}

	
	/**
	 * 设置 处理待办链接地址.
	 *
	 * @param dealUrl the new 处理待办链接地址
	 */
	public void setDealUrl(String dealUrl) {
		this.dealUrl = dealUrl;
	}

	/**
	 * 设置 待办状态 所需常量 MessageConstants MSG__STATUS__PROCESSED 已处理 MSG__STATUS__PROCESSING 未处理.
	 *
	 * @param status the new 待办状态 所需常量 MessageConstants MSG__STATUS__PROCESSED 已处理 MSG__STATUS__PROCESSING 未处理
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取 创建人员名称.
	 *
	 * @return the 创建人员名称
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * 设置 创建人员名称.
	 *
	 * @param createUserName the new 创建人员名称
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * 获取 接收方类型； 消息常量类 MessageConstants 常量 MSG__RECEIVE_TYPE__ORG  组织 MSG__RECEIVE_TYPE__ORG_ROLE 组织+角色.
	 *
	 * @return the 接收方类型； 消息常量类 MessageConstants 常量 MSG__RECEIVE_TYPE__ORG  组织 MSG__RECEIVE_TYPE__ORG_ROLE 组织+角色
	 */
	public String getReceiveType() {
		return receiveType;
	}

	/**
	 * 设置 接收方类型； 消息常量类 MessageConstants 常量 MSG__RECEIVE_TYPE__ORG  组织 MSG__RECEIVE_TYPE__ORG_ROLE 组织+角色.
	 *
	 * @param receiveType the new 接收方类型； 消息常量类 MessageConstants 常量 MSG__RECEIVE_TYPE__ORG  组织 MSG__RECEIVE_TYPE__ORG_ROLE 组织+角色
	 */
	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	
	/**
	 * 获取 接收方下级组织编码.
	 *
	 * @return the 接收方下级组织编码
	 */
	public String getReceiveSubOrgCode() {
		return receiveSubOrgCode;
	}

	
	/**
	 * 设置 接收方下级组织编码.
	 *
	 * @param receiveSubOrgCode the new 接收方下级组织编码
	 */
	public void setReceiveSubOrgCode(String receiveSubOrgCode) {
		this.receiveSubOrgCode = receiveSubOrgCode;
	}

	
	/**
	 * 获取 接收方下级组织名称.
	 *
	 * @return the 接收方下级组织名称
	 */
	public String getReceiveSubOrgName() {
		return receiveSubOrgName;
	}
	 
	/**
	 * 设置 接收方下级组织名称.
	 *
	 * @param receiveSubOrgName the new 接收方下级组织名称
	 */
	public void setReceiveSubOrgName(String receiveSubOrgName) {
		this.receiveSubOrgName = receiveSubOrgName;
	}


	
	public String getUrlType() {
		return urlType;
	}


	
	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}
	
	
}
