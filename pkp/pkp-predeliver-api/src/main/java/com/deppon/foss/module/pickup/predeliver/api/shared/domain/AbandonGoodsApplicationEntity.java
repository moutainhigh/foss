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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/domain/AbandonGoodsApplicationEntity.java
 * 
 * FILE NAME        	: AbandonGoodsApplicationEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
* @ClassName: 	AbandonGoodsApplicationEntity
* @Description: 弃货实体对象 弃货操作类
* @author 
* @date 		2012-10-25 上午10:06:11
*
*/
public class AbandonGoodsApplicationEntity extends BaseEntity{
    /**
	* @Fields serialVersionUID :序列化版本号
	*/
	private static final long serialVersionUID = 1L;
	
	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 异常_ID--页面
	 */
    private String tSrvExceptionId;

    /**
     * 弃货类型--（页面提交==客户弃货类型）
     */
    private String abandonedgoodsType;

    /**
     * 预弃货人--（记录的创建人姓名）
     */
    private String createUserName;

    /**
     * 预弃货人编码--（记录的创建人员工编码）
     */
    private String createUserCode;

    /**
     * 预弃货部门
     */
    private String createOrgName;

    /**
     * 预弃货部门编码
     */
    private String createOrgCode;

    /**
     * 预弃货时间
     */
    private Date preabandonedgoodsTime;

    /**
     * 弃货事由
     */
    private String notes;

    /**
     * 状态（新增--待处理状态）
     */
    private String status;

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

    /**
     * 操作时间
     */
    private Date operateTime;
    
    /**
     * 导入状态 
     */
    private String importStatus;
    
    /**
     * 工作流id 
     */
    private String processId;
    
    /**
     * 工作流     审批具体事由细节
     */
    private String processReason;
    /**
     * 客户配合状态
     */
    private String customerCooperateStatus;
    
    private String operateContent;
    
    /**
     * 流水号
     */
    private String serialNumber;
    
    /**
     * OA差错编号
     */
    private String errorNumber;
    
    /**
     * 货物名称
     */
    private String goodsName;
    
    /**
     * 货物总重量
     */
    private BigDecimal goodsWeightTotal;
    
    /**
     * 货物总体积
     */
    private BigDecimal goodsVolumeTotal;
    
    /**
     * 货物总件数
     */
    private Integer goodsQtyTotal;
    
    /**
     * 库存时长
     */
    private Integer storageDay;
    
    /**
     * 仓储部门code
     */
    private String lastStorageOrgCode;
    
    /**
     * 所属区域code
     */
    private String belongAreaCode;
    
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
	 * @return the tSrvExceptionId
	 */
	public String gettSrvExceptionId() {
		return tSrvExceptionId;
	}

	/**
	 * @param tSrvExceptionId the tSrvExceptionId to set
	 */
	public void settSrvExceptionId(String tSrvExceptionId) {
		this.tSrvExceptionId = tSrvExceptionId;
	}

	/**
	 * @return the abandonedgoodsType
	 */
	public String getAbandonedgoodsType() {
		return abandonedgoodsType;
	}

	/**
	 * @param abandonedgoodsType the abandonedgoodsType to set
	 */
	public void setAbandonedgoodsType(String abandonedgoodsType) {
		this.abandonedgoodsType = abandonedgoodsType;
	}

	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return the createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode the createUserCode to set
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return the createOrgName
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}

	/**
	 * @param createOrgName the createOrgName to set
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	/**
	 * @return the createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param createOrgCode the createOrgCode to set
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return the preabandonedgoodsTime
	 */
	public Date getPreabandonedgoodsTime() {
		return preabandonedgoodsTime;
	}

	/**
	 * @param preabandonedgoodsTime the preabandonedgoodsTime to set
	 */
	public void setPreabandonedgoodsTime(Date preabandonedgoodsTime) {
		this.preabandonedgoodsTime = preabandonedgoodsTime;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * @param operatorCode the operatorCode to set
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * @return the operateOrgName
	 */
	public String getOperateOrgName() {
		return operateOrgName;
	}

	/**
	 * @param operateOrgName the operateOrgName to set
	 */
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	/**
	 * @return the operateOrgCode
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	/**
	 * @param operateOrgCode the operateOrgCode to set
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	/**
	 * @return the operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime the operateTime to set
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * @return the importStatus
	 */
	public String getImportStatus() {
		return importStatus;
	}

	/**
	 * @param importStatus the importStatus to set
	 */
	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}

	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	/**
	 * @return the processReason
	 */
	public String getProcessReason() {
		return processReason;
	}

	/**
	 * @param processReason the processReason to set
	 */
	public void setProcessReason(String processReason) {
		this.processReason = processReason;
	}

	public String getCustomerCooperateStatus() {
		return customerCooperateStatus;
	}

	public void setCustomerCooperateStatus(String customerCooperateStatus) {
		this.customerCooperateStatus = customerCooperateStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
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

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public Integer getStorageDay() {
		return storageDay;
	}

	public void setStorageDay(Integer storageDay) {
		this.storageDay = storageDay;
	}

	public String getLastStorageOrgCode() {
		return lastStorageOrgCode;
	}

	public void setLastStorageOrgCode(String lastStorageOrgCode) {
		this.lastStorageOrgCode = lastStorageOrgCode;
	}

	/**
	 * 获取所属区域
	 * @return
	 */
	public String getBelongAreaCode() {
		return belongAreaCode;
	}

	/**
	 * 设置所属区域
	 * @param belongAreaCode
	 */
	public void setBelongAreaCode(String belongAreaCode) {
		this.belongAreaCode = belongAreaCode;
	}

}