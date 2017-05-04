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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/AbandonedGoodsSearchDto.java
 * 
 * FILE NAME        	: AbandonedGoodsSearchDto.java
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

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * TODO（描述类的职责）
 * @author ibm-lizhiguo
 * @date 2012-10-26 上午11:24:09
 */
public class AbandonedGoodsSearchDto implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	// id
	private String id;

	// 运单号
	private String waybillNo;

	// 状态（新增--待处理状态）
	private String status;
	// 状态（新增--待处理状态）
	private String[] statusArr;

	// 弃货类型--（页面提交==客户弃货类型）
	private String abandonedgoodsType;

	// 始发部门CODE--(运单--收货部门)
	private String receiveOrgCode;

	// 发货人(运单--发货客户名称)
	private String deliveryCustomerName;

	// 预弃货人--（记录的创建人姓名）
	private String createUserName;

	// 预弃货时间--开始
	private Date preabandonedgoodsTimeBegin;

	// 预弃货时间--结束
	private Date preabandonedgoodsTimeEnd;

	// 天数
	private String storageDay;

	// 货物库存状态在本部门库存
	private String currentOrgCode;
	
	/**
     * 工作流id 
     */
    private String processId;
    
    /**
     * 流水号
     */
    private String serialNumber;
    /**
     * 处理弃货状态集合
     */
    private List<String> abandgoodsStatus;
    /**
     * 是否通过在线提醒跳转到处理弃货界面查询的  是 为Y  
     */
    private String isByMsg;

	/**
	 * @return the currentOrgCode
	 */
	public String getCurrentOrgCode() {
		return currentOrgCode;
	}

	/**
	 * @param currentOrgCode the currentOrgCode to see
	 */
	public void setCurrentOrgCode(String currentOrgCode) {
		this.currentOrgCode = currentOrgCode;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to see
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the storageDay
	 */
	public String getStorageDay() {
		return storageDay;
	}

	/**
	 * @param storageDay the storageDay to see
	 */
	public void setStorageDay(String storageDay) {
		this.storageDay = storageDay;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to see
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the abandonedgoodsType
	 */
	public String getAbandonedgoodsType() {
		return abandonedgoodsType;
	}

	/**
	 * @param abandonedgoodsType the abandonedgoodsType to see
	 */
	public void setAbandonedgoodsType(String abandonedgoodsType) {
		this.abandonedgoodsType = abandonedgoodsType;
	}

	/**
	 * @return the receiveOrgCode
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * @param receiveOrgCode the receiveOrgCode to see
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * @return the deliveryCustomerName
	 */
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	/**
	 * @param deliveryCustomerName the deliveryCustomerName to see
	 */
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName the createUserName to see
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return the preabandonedgoodsTimeBegin
	 */
	public Date getPreabandonedgoodsTimeBegin() {
		return preabandonedgoodsTimeBegin;
	}

	/**
	 * @param preabandonedgoodsTimeBegin the preabandonedgoodsTimeBegin to see
	 */
	@DateFormat(formate = "yyyy-MM-dd")
	public void setPreabandonedgoodsTimeBegin(Date preabandonedgoodsTimeBegin) {
		this.preabandonedgoodsTimeBegin = preabandonedgoodsTimeBegin;
	}

	/**
	 * @return the preabandonedgoodsTimeEnd
	 */
	public Date getPreabandonedgoodsTimeEnd() {
		return preabandonedgoodsTimeEnd;
	}

	/**
	 * @param preabandonedgoodsTimeEnd the preabandonedgoodsTimeEnd to see
	 */
	@DateFormat(formate = "yyyy-MM-dd")
	public void setPreabandonedgoodsTimeEnd(Date preabandonedgoodsTimeEnd) {
		this.preabandonedgoodsTimeEnd = preabandonedgoodsTimeEnd;
	}

	public String[] getStatusArr() {
		return statusArr;
	}

	public void setStatusArr(String[] statusArr) {
		this.statusArr = statusArr;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public List<String> getAbandgoodsStatus() {
		return abandgoodsStatus;
	}

	public void setAbandgoodsStatus(List<String> abandgoodsStatus) {
		this.abandgoodsStatus = abandgoodsStatus;
	}

	public String getIsByMsg() {
		return isByMsg;
	}

	public void setIsByMsg(String isByMsg) {
		this.isByMsg = isByMsg;
	}

}