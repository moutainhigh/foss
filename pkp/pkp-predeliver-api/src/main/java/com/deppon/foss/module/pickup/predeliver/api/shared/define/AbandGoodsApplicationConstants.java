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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/define/AbandGoodsApplicationConstants.java
 * 
 * FILE NAME        	: AbandGoodsApplicationConstants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.define;


/**
 * 弃货常量类
 * @date 2012-11-12 下午9:25:48
 */
public class AbandGoodsApplicationConstants {
	//异常转弃货状态--生成
	public static final String ABANDGOODS_STATUS_NEW = "ABANDGOODS_STATUS_NEW";
	//异常转弃货状态--审批中
	public static final String ABANDGOODS_STATUS_APPROVAL = "ABANDGOODS_STATUS_APPROVAL";
	//异常转弃货状态--通过
	public static final String ABANDGOODS_STATUS_PASS = "ABANDGOODS_STATUS_PASS";
	//异常转弃货状态--拒绝
	public static final String ABANDGOODS_STATUS_REFUSE = "ABANDGOODS_STATUS_REFUSE";
	//异常转弃货状态--已处理（内部带货）
	public static final String ABANDGOODS_STATUS_DEALED = "ABANDGOODS_STATUS_DEALED";
	//异常转弃货状态--客户主动要求变更
	public static final String ABANDGOODS_STATUS_CUSTOMERREQUIRECHANGE = "ABANDGOODS_STATUS_CUSTOMERREQUIRECHANGE";
	
	//弃货类型--客户弃货
	public static final String ABANDGOODS_TYPE_CUSTOMER = "ABANDGOODS_TYPE_CUSTOMER";
	//弃货类型--自动弃货
	public static final String ABANDGOODS_TYPE_AUTO = "ABANDGOODS_TYPE_AUTO";
	/** 弃货类型--无标签货 */
	public static final String ABANDGOODS_TYPE_NOTAG = "ABANDGOODS_TYPE_NOTAG";
	//已经导入内部带货中间导入表
	public static final String IMPORTED_STATUS_DONE = "1";
	//OA生成工作流服务 服务编码
	public static final String ESB_OA_WORKFLOW_SERVICE_CODE = "ESB_FOSS2ESB_DISCARD_WORKFLOW";
	//OA生成工作流服务  VERSION
	public static final String ESB_OA_WORKFLOW_SERVICE_VERSION = "0.1";
	//OA生成工作流服务   BUSINESSID
	public static final String ESB_OA_WORKFLOW_SERVICE_BUSINESSID ="waybillNumber";
	//OA 服务编码
	public static final String ESB_OA_WORKFLOW_SERVICE_CODE_RESULT = "FOSS_ESB2FOSS_DISCARD_RESULT";
	// TODO 这里是0和1，需求文档是1和2
	// 有弃货证明/赔偿协议/无标签货
	public static final String OA_DISCART_TYPE_WILLNESS = "0";	
	//客户不予配合处理
	public static final String OA_DISCART_TYPE_UNWILLNESS = "1";
	//默认自动转弃货天数
	public static final String DEFAULT_DAYS="90";
	//管理员用于自动转弃货
	public static final String DEFAULT_CREATE_USER_CODE="000000";
	//管理员用于自动转弃货
	public static final String DEFAULT_CREATE_USER_NAME="system";
	
	//OA 审批不通过的原因 - 数据格式不对
	public static final String OA_REFUSE_DATE_INVALID = "1";
	//OA 审批不通过的原因 - 客户要求返货
	public static final String OA_REFUSE_DATE_CUSTOMER_REFUSE = "2";
	//OA 审批不通过的原因 - 其他(自己填写) 
	public static final String OA_REFUSE_DATE_CUSTOMER_OTHER = "3";	
	
	//附件所属模块的路径
	public static final String PKP_PREDELIVER = "pkp-predeliver";	
	
	
	
}