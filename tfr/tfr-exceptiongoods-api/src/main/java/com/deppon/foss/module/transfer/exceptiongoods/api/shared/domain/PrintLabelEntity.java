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
 *  FILE PATH          :/PrintLabelEntity.java
 * 
 *  FILE NAME          :PrintLabelEntity.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 记录打印货物标签的操作信息
 * @author 097457-foss-wangqiang
 * @date 2012-11-26 下午5:15:03
 */
public class PrintLabelEntity extends BaseEntity{

	private static final long serialVersionUID = 8056171758058686270L;
	/** 运单号*/
	private String waybillNo;
	/** 流水号*/
	private String serialNo;
	/** 打印时间*/
	private Date printTime;
	/** 打印人工号*/
	private String printUserCode;
	/** 打印人姓名*/
	private String printUserName;
	/** 打印标签类型*/
	private int lableType = 1;
	/**
	 * shiwei 2014-08-06
	 * 打印人所在部门编码
	 */
	private String orgCode;
	/**
	 * shiwei 2014-08-06
	 * 打印人所在部门名称
	 */
	private String orgName;
	/**
	 * 打印类型：0:老式打印机 1:针对巴枪扫描打印的
	* @fields printType
	* @author 14022-foss-songjie
	* @update 2015年2月3日 下午5:14:08
	* @version V1.0
	*/
	private int printType;
	
	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

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
	 * 获取 打印人工号.
	 *
	 * @return the 打印人工号
	 */
	public String getPrintUserCode() {
		return printUserCode;
	}

	/**
	 * 设置 打印人工号.
	 *
	 * @param printUserCode the new 打印人工号
	 */
	public void setPrintUserCode(String printUserCode) {
		this.printUserCode = printUserCode;
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
	* @description 打印类型：0:老式打印机 1:针对巴枪扫描打印的
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月3日 下午5:14:39
	*/
	public int getPrintType() {
		return printType;
	}

	
	/**
	* @description 打印类型：0:老式打印机 1:针对巴枪扫描打印的
	* @param printType
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月3日 下午5:14:41
	*/
	public void setPrintType(int printType) {
		this.printType = printType;
	}

	public int getLableType() {
		return lableType;
	}

	public void setLableType(int lableType) {
		this.lableType = lableType;
	}
	
	
	

}