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
 *  PROJECT NAME  : tfr-exceptiongoods-api
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/GoodsLabelPrintDto.java
 * 
 *  FILE NAME          :GoodsLabelPrintDto.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto;

import java.util.Date;
/**
 * 提供给综合查询标签打印信息的实体类
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:06:12
 */
public class GoodsLabelPrintDto implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4319108731101460017L;
	/** 流水号*/
	private String serialNo;
	/** 关联原流水号*/
	private String originalSerialNo;
	/** 打印时间*/
	private Date printTime;
	/** 打印人姓名*/
	private String printUserName;
	/** 打印人所在部门名称*/
	private String printUserOrgName;
	/** 打印次序*/
	private String printOrder;
	/** 打印标签类型*/
	private int lableType;
	
	/**
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}
	
	/**
	 * 设置 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * 获取 关联原流水号.
	 *
	 * @return the 关联原流水号
	 */
	public String getOriginalSerialNo() {
		return originalSerialNo;
	}
	
	/**
	 * 设置 关联原流水号.
	 *
	 * @param originalSerialNo the new 关联原流水号
	 */
	public void setOriginalSerialNo(String originalSerialNo) {
		this.originalSerialNo = originalSerialNo;
	}
	
	/**
	 * 获取 打印时间.
	 *
	 * @return the 打印时间
	 */
	public Date getPrintTime() {
		return printTime;
	}
	
	/**
	 * 设置 打印时间.
	 *
	 * @param printTime the new 打印时间
	 */
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}
	
	/**
	 * 获取 打印人姓名.
	 *
	 * @return the 打印人姓名
	 */
	public String getPrintUserName() {
		return printUserName;
	}
	
	/**
	 * 设置 打印人姓名.
	 *
	 * @param printUserName the new 打印人姓名
	 */
	public void setPrintUserName(String printUserName) {
		this.printUserName = printUserName;
	}
	
	/**
	 * 获取 打印人所在部门名称.
	 *
	 * @return the 打印人所在部门名称
	 */
	public String getPrintUserOrgName() {
		return printUserOrgName;
	}
	
	/**
	 * 设置 打印人所在部门名称.
	 *
	 * @param printUserOrgName the new 打印人所在部门名称
	 */
	public void setPrintUserOrgName(String printUserOrgName) {
		this.printUserOrgName = printUserOrgName;
	}
	
	/**
	 * 获取 打印次序.
	 *
	 * @return the 打印次序
	 */
	public String getPrintOrder() {
		return printOrder;
	}
	
	/**
	 * 设置 打印次序.
	 *
	 * @param printOrder the new 打印次序
	 */
	public void setPrintOrder(String printOrder) {
		this.printOrder = printOrder;
	}

	public int getLableType() {
		return lableType;
	}

	public void setLableType(int lableType) {
		this.lableType = lableType;
	}

}