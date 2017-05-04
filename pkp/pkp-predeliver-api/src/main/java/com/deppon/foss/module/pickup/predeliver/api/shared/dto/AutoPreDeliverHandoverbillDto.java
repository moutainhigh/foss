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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/NotifyCustomerConditionDto.java
 * 
 * FILE NAME        	: NotifyCustomerConditionDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 自动交单查询条件DTO
 * @author 159231 meiying
 * 2015-4-20  上午11:28:00
 */
public class AutoPreDeliverHandoverbillDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 
	 */
	private String paidMethod;

	/**
	 * 默认查询中变更状态 (WaybillRfcConstants.PRE_ACCECPT,WaybillRfcConstants.PRE_AUDIT)
	 */
	private List<String> wbrStatus;
	

	/**
	 * 默认不查询运单产品
	 */
	private String[] productCodes;
	
	/**
	 * 默认运单版本
	 */
	private String active;
	
    /**
	 * 默认查询中派送方式
	 */
	private String receiveMethodTmp;
    /**
     * 预计送货日期
     */
    private Date deliverDate;
    /**
     * 系统时间（时分秒）
     */
    private String oneDayNowTime;
    /**
     * 系统时间（时分秒）
     */
    private String twoDayNowTime;
    /**
     * 待自动交单临时表ＩＤ集合
     */
    private List<String> pid;
    /**
     * jobID
     */
    private String jobId;
    /**
     * 
     */
    private String oldJobId;

 

	

	/**
	 * 获取paidMethod  
	 * @return paidMethod paidMethod
	 */
	public String getPaidMethod() {
		return paidMethod;
	}


	/**
	 * 设置paidMethod  
	 * @param paidMethod paidMethod 
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}


	/**
	 * 
	 * 
	 * @return
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	/**
	 * 获取 默认查询中变更状态.
	 * 
	 * @return the 默认查询中变更状态
	 */
	public List<String> getWbrStatus() {
		return wbrStatus;
	}

	/**
	 * 设置 默认查询中变更状态.
	 * 
	 * @param wbrStatus the new 默认查询中变更状态
	 */
	public void setWbrStatus(List<String> wbrStatus) {
		this.wbrStatus = wbrStatus;
	}

	

	
	/**
	 * 获取 默认不查询运单产品.
	 * 
	 * @return the 默认不查询运单产品
	 */
	public String[] getProductCodes() {
		return productCodes;
	}

	/**
	 * 设置 默认不查询运单产品.
	 * 
	 * @param productCodes the new 默认不查询运单产品
	 */
	public void setProductCodes(String[] productCodes) {
		this.productCodes = productCodes;
	}

	
	/**
	 * 获取 默认运单版本.
	 * 
	 * @return the 默认运单版本
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置 默认运单版本.
	 * 
	 * @param active the new 默认运单版本
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * 获取waybillNo  
	 * @return waybillNo waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置waybillNo  
	 * @param waybillNo waybillNo 
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	

	/**
	 * 获取receiveMethodTmp  
	 * @return receiveMethodTmp receiveMethodTmp
	 */
	public String getReceiveMethodTmp() {
		return receiveMethodTmp;
	}

	/**
	 * 设置receiveMethodTmp  
	 * @param receiveMethodTmp receiveMethodTmp 
	 */
	public void setReceiveMethodTmp(String receiveMethodTmp) {
		this.receiveMethodTmp = receiveMethodTmp;
	}

	/**
	 * 获取deliverDate  
	 * @return deliverDate deliverDate
	 */
	public Date getDeliverDate() {
		return deliverDate;
	}

	/**
	 * 设置deliverDate  
	 * @param deliverDate deliverDate 
	 */
	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	/**
	 * 获取oneDayNowTime  
	 * @return oneDayNowTime oneDayNowTime
	 */
	public String getOneDayNowTime() {
		return oneDayNowTime;
	}

	/**
	 * 设置oneDayNowTime  
	 * @param oneDayNowTime oneDayNowTime 
	 */
	public void setOneDayNowTime(String oneDayNowTime) {
		this.oneDayNowTime = oneDayNowTime;
	}

	/**
	 * 获取twoDayNowTime  
	 * @return twoDayNowTime twoDayNowTime
	 */
	public String getTwoDayNowTime() {
		return twoDayNowTime;
	}

	/**
	 * 设置twoDayNowTime  
	 * @param twoDayNowTime twoDayNowTime 
	 */
	public void setTwoDayNowTime(String twoDayNowTime) {
		this.twoDayNowTime = twoDayNowTime;
	}

	/**
	 * 获取pid  
	 * @return pid pid
	 */
	public List<String> getPid() {
		return pid;
	}

	/**
	 * 设置pid  
	 * @param pid pid 
	 */
	public void setPid(List<String> pid) {
		this.pid = pid;
	}

	/**
	 * 获取jobId  
	 * @return jobId jobId
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * 设置jobId  
	 * @param jobId jobId 
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**
	 * 获取oldJobId  
	 * @return oldJobId oldJobId
	 */
	public String getOldJobId() {
		return oldJobId;
	}

	/**
	 * 设置oldJobId  
	 * @param oldJobId oldJobId 
	 */
	public void setOldJobId(String oldJobId) {
		this.oldJobId = oldJobId;
	}

	
		
}