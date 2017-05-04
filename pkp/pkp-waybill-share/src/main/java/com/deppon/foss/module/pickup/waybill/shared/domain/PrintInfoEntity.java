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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/PrintInfoEntity.java
 * 
 * FILE NAME        	: PrintInfoEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 运单打印信息
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-12-5 下午4:24:13, </p>
 * @author foss-sunrui
 * @date 2012-12-5 下午4:24:13
 * @since
 * @version
 */
public class PrintInfoEntity extends BaseEntity {
    /**
     * 序列化版本号
	 * （用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 6232997439575614227L;
	/**
	 * 打应类型
	 */
    private String printType;
    /**
     * 运单id
     */
    private String waybillId;
    /**
     * 运单编码
     */
    private String waybillNo;
    /**
     * 答应时间
     */
    private Integer printTimes;
    /**
     * 打印人编码
     */
    private String printUserCode;
    /**
     * 打印人
     */
    private String printUser;
    /**
     * 打印人组织编码
     */
    private String printOrgCode;
    /**
     * 打印人组织
     */
    private String printOrg;
    /**
     * 打印时间
     */
    private Date printTime;
    /**
     * 打印状态
     */
    private String printWaybillType;
    
	public String getPrintWaybillType() {
		return printWaybillType;
	}
	public void setPrintWaybillType(String printWaybillType) {
		this.printWaybillType = printWaybillType;
	}
	/**
	 * @return the printType
	 */
	public String getPrintType() {
		return printType;
	}
	/**
	 * @param printType the printType to set
	 */
	public void setPrintType(String printType) {
		this.printType = printType;
	}
	/**
	 * @return the waybillId
	 */
	public String getWaybillId() {
		return waybillId;
	}
	/**
	 * @param waybillId the waybillId to set
	 */
	public void setWaybillId(String waybillId) {
		this.waybillId = waybillId;
	}
	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return the printTimes
	 */
	public Integer getPrintTimes() {
		return printTimes;
	}
	/**
	 * @param printTimes the printTimes to set
	 */
	public void setPrintTimes(Integer printTimes) {
		this.printTimes = printTimes;
	}
	/**
	 * @return the printUserCode
	 */
	public String getPrintUserCode() {
		return printUserCode;
	}
	/**
	 * @param printUserCode the printUserCode to set
	 */
	public void setPrintUserCode(String printUserCode) {
		this.printUserCode = printUserCode;
	}
	/**
	 * @return the printUser
	 */
	public String getPrintUser() {
		return printUser;
	}
	/**
	 * @param printUser the printUser to set
	 */
	public void setPrintUser(String printUser) {
		this.printUser = printUser;
	}
	/**
	 * @return the printOrgCode
	 */
	public String getPrintOrgCode() {
		return printOrgCode;
	}
	/**
	 * @param printOrgCode the printOrgCode to set
	 */
	public void setPrintOrgCode(String printOrgCode) {
		this.printOrgCode = printOrgCode;
	}
	/**
	 * @return the printOrg
	 */
	public String getPrintOrg() {
		return printOrg;
	}
	/**
	 * @param printOrg the printOrg to set
	 */
	public void setPrintOrg(String printOrg) {
		this.printOrg = printOrg;
	}
	/**
	 * @return the printTime
	 */
	public Date getPrintTime() {
		return printTime;
	}
	/**
	 * @param printTime the printTime to set
	 */
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}
}