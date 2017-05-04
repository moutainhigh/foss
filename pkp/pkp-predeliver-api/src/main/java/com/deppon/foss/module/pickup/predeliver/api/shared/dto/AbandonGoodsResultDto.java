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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/AbandonGoodsResultDto.java
 * 
 * FILE NAME        	: AbandonGoodsResultDto.java
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

/**
 * 弃货查询结果dto
 */
public class AbandonGoodsResultDto implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	// ID
	private String id;
	// 运单号
	private String waybillNo;
	// 状态（新增--待处理状态）
	private String status;
	// 预弃货人--（记录的创建人姓名）
	private String createUserName;
	// 收货人姓名---（运单--收货客户名称）
	private String receiveCustomerName;
	// 收货人电话--（运单--收货客户电话）
	private String receiveCustomerPhone;
	// 预弃货时间
	private Date preabandonedgoodsTime;
	//操作时间
	private Date operateTime;
    /**
     * 弃货事由
     */
    private String notes;
	// 隐藏数据,用来计算
	// 重量--运单
	private String goodsWeightTotal;
	// 体积--运单
	private String goodsVolumeTotal;
	// 总件数--运单
	private int goodsQtyTotal;

	// 导入状态
	private String importStatus;

	// 弃货类型--自动弃货 -客户弃货
	private String abandonedgoodsType;
	
	/**
     * 客户配合状态
     */
    private String customerCooperateStatus;
    /**
     * 操作人
     */
    private String operator;

    /**
     * 操作人编码
     */
    private String operatorCode;

    /**
     * 操作部门
     */
    private String operateOrgName;

    /**
     * 操作部门编码
     */
    private String operateOrgCode;
    private String processId;
    
	/**
	 * 流水号
	 */
	private String serialNumber;
	
	/**
	 * OA差错编号
	 */
	private String errorNumber;

	/**
	* 仓储部门code
     */
    private String lastStorageOrgCode;

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
	 * @return the importStatus
	 */
	public String getImportStatus() {
		return importStatus;
	}

	/**
	 * @param importStatus the importStatus to see
	 */
	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
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
	 * @return the receiveCustomerName
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * @param receiveCustomerName the receiveCustomerName to see
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * @return the receiveCustomerPhone
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * @param receiveCustomerPhone the receiveCustomerPhone to see
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * @return the preabandonedgoodsTime
	 */
	public Date getPreabandonedgoodsTime() {
		return preabandonedgoodsTime;
	}

	/**
	 * @param preabandonedgoodsTime the preabandonedgoodsTime to see
	 */
	public void setPreabandonedgoodsTime(Date preabandonedgoodsTime) {
		this.preabandonedgoodsTime = preabandonedgoodsTime;
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
	 * @return the goodsWeightTotal
	 */
	public String getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	/**
	 * @param goodsWeightTotal the goodsWeightTotal to see
	 */
	public void setGoodsWeightTotal(String goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * @return the goodsVolumeTotal
	 */
	public String getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * @param goodsVolumeTotal the goodsVolumeTotal to see
	 */
	public void setGoodsVolumeTotal(String goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * @return the goodsQtyTotal
	 */
	public int getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * @param goodsQtyTotal the goodsQtyTotal to see
	 */
	public void setGoodsQtyTotal(int goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getOperateOrgName() {
		return operateOrgName;
	}

	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCustomerCooperateStatus() {
		return customerCooperateStatus;
	}

	public void setCustomerCooperateStatus(String customerCooperateStatus) {
		this.customerCooperateStatus = customerCooperateStatus;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
	/**
	 * 获取流水号
	 * @return
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * 设置流水号
	 * @param serialNo
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * 获取OA差错编号
	 * @return
	 */
	public String getErrorNumber() {
		return errorNumber;
	}

	/**
	 * 设置OA差错编号
	 * @param errorCode
	 */
	public void setErrorNumber(String errorNumber) {
		this.errorNumber = errorNumber;
	}
	public String getLastStorageOrgCode() {
		return lastStorageOrgCode;
	}

	public void setLastStorageOrgCode(String lastStorageOrgCode) {
		this.lastStorageOrgCode = lastStorageOrgCode;
	}
}