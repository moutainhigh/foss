/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-agent-itf
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/agent/server/service/ExceptionTypeEnum.java
 *  
 *  FILE NAME          :ExceptionTypeEnum.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.agent.server.service;
/**
 * 
 * @author wuyingjie
 *
 */
public enum ExceptionTypeEnum {
	S000001("S000001", "SYS", "系统异常", "ESB-ESBServiceCode消息头中所定义的服务编码%S不存在。", "调用端检查是否输入了正确的服务编码"),
	S000002("S000002", "SYS", "系统异常", "用户名%S/密码验证不通过", "检查是否输入了正确的用户名,检查是否对密码做了MD5摘要，并且转换成了小写的16进制字母"),
	S000003("S000003", "SYS", "系统异常", "后端服务不可用", "可再次重试，如果还是出现错误需要联系系统管理员查错"),
	S000004("S000004", "SYS", "系统异常", "参数（JSON字符串）解析错误", "查看生成的JSON是否符合接口约定的格式，如日期格式，日期是否合法等"),
	S000005("S000005", "SYS", "系统异常", "数据返回结果转换JSON对象时解析错误", "数据返回结果转换JSON对象时解析错误"),
	S000099("S000099", "SYS", "系统异常", "未知错误", "查看异常详细信息，并联系系统管理员。"),
	A000101("A000101", "APP", "业务异常", "用户%A不能查看代理公司%B的交接单", "认证过的用户与查询的代理公司不匹配,如认证了A代理，但请求消息里的编码写成了B公司"),
	A000102("A000102", "APP", "业务异常", "输入参数的交接单号和代理公司编码都是空", "输入参数既无交接单号也无代理公司编号"),
	A000103("A000103", "APP", "业务异常", "输入参数%S长度超出限制", "字符串长度超出限制"),
	A000201("A000201", "APP", "业务异常", "输入参数%S长度超出限制", "字符串长度超出限制"),
	A000202("A000202", "APP", "业务异常", "输入参数的交接单号和运单号都是空", "输入参数既无交接单号也无运单号"),
	A000301("A000301", "APP", "业务异常", "用户%A不能上传代理公司%B的交接单", "认证过的用户与查询的代理公司不匹配,如认证了A代理，但请求消息里的编码写成了B公司"),
	A000302("A000302", "APP", "业务异常", "运单号%S输入有误，该运单号不存在", "上传的运单号找不到"),
	A000303("A000303", "APP", "业务异常", "输入参数%S为空", "字段违反了非空约束"),
	A000304("A000304", "APP", "业务异常", "输入参数%S长度超出限制", "字符串长度超出限制"),
	A000305("A000305", "APP", "业务异常", "签收状态%S不存在", "输入了错误的编码"),
	A000306("A000306", "APP", "业务异常", "货物拉回异常描述%S不存在", "输入了错误的编码"),
	A000307("A000307", "APP", "业务异常", "运单已经签收，无法重复签收", "运单已经签收，无法重复签收"),
	A000308("A000308", "APP", "业务异常", "代理网点%A不属于输入的代理公司%B", "代理公司网点不属于所属的代理公司"),
	A000401("A000401", "APP", "业务异常", "用户%A不能上传代理公司%B的交接单", "认证过的用户与查询的代理公司不匹配,如认证了A代理，但请求消息里的编码写成了B公司"),
	A000402("A000402", "APP", "业务异常", "运单号%S输入有误，该运单号不存在", "上传的运单号找不到"),
	A000403("A000403", "APP", "业务异常", "输入参数%S为空", "字段违反了非空约束"),
	A000404("A000404", "APP", "业务异常", "输入参数%S长度超出限制", "字符串长度超出限制"),
	A000405("A000405", "APP", "业务异常", "操作类型%S不存在", "输入了错误的编码"),
	A000099("A000099", "APP", "业务异常", "系统业务异常", "查看异常详细信息"),
	;
	//异常编码
	private String code;
	//异常类型编号
	private String typeCode;
	//异常类型名称
	private String typeName;
	//异常消息
	private String message;
	//解决办法
	private String reason;		
	
	private ExceptionTypeEnum(String code, String typeCode, String typeName, String message, String reason){
		this.code = code;
		this.typeCode = typeCode;
		this.typeName = typeName;
		this.message = message;
		this.reason = reason;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}