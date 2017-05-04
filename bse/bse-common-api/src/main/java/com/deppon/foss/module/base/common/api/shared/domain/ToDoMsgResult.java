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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/domain/ToDoMsgResult.java
 * 
 * FILE NAME        	: ToDoMsgResult.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.domain;

import java.io.Serializable;

import com.deppon.foss.framework.shared.util.string.StringUtil;

/**
 * 生成待办返回的状态结果
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-15 下午3:14:15
 */
public class ToDoMsgResult implements Serializable {

	private static final long serialVersionUID = -56726176246894537L;

	/**
	 * 是否成功 true 成功 false 失败
	 */
	private Boolean isSuccess;

	/**
	 * 错误信息
	 */
	private String errorMsg;

	/**
	 * 待办事项实体
	 */
	private ToDoMsgEntity toDoMsgEntity; 
	
	
	public ToDoMsgResult(ToDoMsgEntity checkEntity){
		this.toDoMsgEntity=checkEntity;
	}
	/**
	 * 新增待办校验
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午8:37:15
	 */
	public String getInsertChkRlt(){
		String errorMsg=this.checkCommonToDoEntity();
		errorMsg=errorMsg+
				this.checkBusinessNo(this.toDoMsgEntity.getBusinessNo())+
				this.checkCreateUserCode(this.toDoMsgEntity.getCreateUserCode())+
				this.checkCreateUserName(this.toDoMsgEntity.getCreateUserName())+
				this.checkDealUrl(this.toDoMsgEntity.getDealUrl())+
				this.checkSerialNumber(this.toDoMsgEntity.getSerialNumber())+
				this.checkStatus(this.toDoMsgEntity.getStatus());
		return errorMsg;
	}
	
	/**
	 * 删除待办校验
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午8:37:34
	 */
	public String getDeleteChkRlt(){
		return checkCommonToDoEntity();
	}
	
	/**
	 * 新增和删除待办公共校验方法
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午8:37:46
	 */
	private String checkCommonToDoEntity(){
		String errorMsg="";
		if(null!=toDoMsgEntity){
			errorMsg=this.checkBusinessType(this.toDoMsgEntity.getBusinessType())+
					 this.checkReceiveOrgCode(this.toDoMsgEntity.getReceiveOrgCode())+
					 this.checkReceiveType(this.toDoMsgEntity.getReceiveType());
					
		}else{
			errorMsg="输入参数有误;";
		}
		
		return errorMsg;
	}
	
	/**
	 * 校验接收组织不能为空
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-3-14 上午10:27:01
	 */
	public String checkReceiveOrgCode(String receiveOrgCdoe){
		if(StringUtil.isBlank(receiveOrgCdoe)){
			return "接收组织不能为空";
		}
		return "";
	}
	
	/**
	 * 校验接收方类型不能为空
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-3-14 上午10:27:01
	 */
	public String checkReceiveType(String receiveType){
		if(StringUtil.isBlank(receiveType)){
			return "接收方类型不能为空";
		}
		return "";
	}
	/**
	 * 校验业务类型不能为空
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-3-14 上午10:27:01
	 */
	public String checkBusinessType(String businessType){
		if(StringUtil.isBlank(businessType)){
			return "业务类型不能为空";
		}
		return "";
	}
	/**
	 * 校验业务流水号不能为空
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-3-14 上午10:27:01
	 */
	public String checkSerialNumber(String serialNumber){
		if(StringUtil.isBlank(serialNumber)){
			return "业务流水号不能为空";
		}
		return "";
	}
	/**
	 * 校验业务单号不能为空
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-3-14 上午10:27:01
	 */
	public String checkBusinessNo(String businessNo){
		if(StringUtil.isBlank(businessNo)){
			return "业务单号不能为空";
		}
		return "";
	}
	/**
	 * 校验待办业务处理地址不能为空
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-3-14 上午10:27:01
	 */
	public String checkDealUrl(String dealUrl){
		if(StringUtil.isBlank(dealUrl)){
			return "待办业务处理地址不能为空";
		}
		return "";
	}
	/**
	 * 校验待办状态不能为空
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-3-14 上午10:27:01
	 */
	public String checkStatus(String status){
		if(StringUtil.isBlank(status)){
			return "待办状态不能为空";
		}
		return "";
	}
	/**
	 * 校验创建人编码不能为空
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-3-14 上午10:27:01
	 */
	public String checkCreateUserCode(String createUserCode){
		if(StringUtil.isBlank(createUserCode)){
			return "创建人编码不能为空";
		}
		return "";
	}
	/**
	 * 校验创建人名称不能为空
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-3-14 上午10:27:01
	 */
	public String checkCreateUserName(String createUserName){
		if(StringUtil.isBlank(createUserName)){
			return "创建人名称不能为空";
		}
		return "";
	}
	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public ToDoMsgEntity getToDoMsgEntity() {
		return toDoMsgEntity;
	}

	public void setToDoMsgEntity(ToDoMsgEntity toDoMsgEntity) {
		this.toDoMsgEntity = toDoMsgEntity;
	}

}
