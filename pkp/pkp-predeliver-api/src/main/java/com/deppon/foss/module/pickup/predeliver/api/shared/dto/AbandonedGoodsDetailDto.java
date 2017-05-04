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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/AbandonedGoodsDetailDto.java
 * 
 * FILE NAME        	: AbandonedGoodsDetailDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.upload.AttachementEntity;

/**
 * 弃货详细DTO
 * 
 * @author ibm-lizhiguo
 * @date 2012-10-29 下午3:41:41
 */
public class AbandonedGoodsDetailDto implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	//ID
	private String id;
	// 运单号
	private String waybillNo;
	//收货日期
	private String billTime;
	// 货物名称--运单
	private String goodsName;
	// 重量--运单
	private BigDecimal goodsWeightTotal;
	// 体积--运单
	private BigDecimal goodsVolumeTotal;
	// 发货人--(运单--发货客户联系人)
	private String deliveryCustomerContact;
	// 发货人电话
	private String deliveryCustomerPhone;
	// 发货人手机
	private String deliveryCustomerMobilephone;
	// 发货部门
	private String receiveOrgCode;
	// 发货部门名称
	private String receiveOrgName;
	// 所属区域(运单中的部门编码--调用李俊给的接口，获得所属区域)
	private String respectiveRegional;
	// 所属区域名称
	private String respectiveRegionalName;
	// 发货人code
	private String deliveryCustomerCode;
	// 发货人name
	private String deliveryCustomerName;
	// 收货人
	private String receiveCustomerContact;
	// 收货人电话
	private String receiveCustomerPhone;
	// 到达部门(运单--最终配载部门--在营业部表中，把CODE改成NAME)
	private String lastLoadOrgCode;
	// 到达部门名称
	private String lastLoadOrgName;
	// 最终配载部门CODE
	private String lastStorageCode;
	// 最终配载部门NAME
	private String lastStorageName;
	// 代收金额
	private BigDecimal codAmount;
	// 保险金额
	private BigDecimal insuranceAmount;
	// 预付金额
	private BigDecimal prePayAmount;
	// 到付金额
	private BigDecimal toPayAmount;
	// 弃货类型--(申请转弃货--弃货类型)
	private String abandonedgoodsType;
	// 处理状态----(申请转弃货--状态)
	private String status;
	// 导入状态
	private String importStatus;
	// 预弃货处理时间--(申请转弃货--预弃货时间)
	private Date preabandonedgoodsTime;
	// 预弃货人--(申请转弃货--预弃货人)
	private String createUserName;
	// 仓储时长--(T_SRV_ACTUAL_FREIGHT--库存天数)
	private int storageDay;
	// 弃货事由--(申请转弃货--弃货事由)
	private String notes;
	// 弃货凭证
	/**
     * 客户配合状态
     */
    private String customerCooperateStatus;
	// 货物总件数
	private Integer goodsQtyTotal;

	// 附件dto
	private List<AttachementEntity> abandonedGoodsFiles = new ArrayList<AttachementEntity>();
	
	/**
	 * 流水号
	 */
	private String serialNumber;
	
	/**
	 * OA差错编号
	 */
	private String errorNumber;

	/**
	 * @return the lastStorageCode
	 */
	public String getLastStorageCode() {
		return lastStorageCode;
	}

	/**
	 * @param lastStorageCode the lastStorageCode to see
	 */
	public void setLastStorageCode(String lastStorageCode) {
		this.lastStorageCode = lastStorageCode;
	}

	/**
	 * @return the lastStorageName
	 */
	public String getLastStorageName() {
		return lastStorageName;
	}

	/**
	 * @param lastStorageName the lastStorageName to see
	 */
	public void setLastStorageName(String lastStorageName) {
		this.lastStorageName = lastStorageName;
	}

	/**
	 * @return the receiveOrgName
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	/**
	 * @param receiveOrgName the receiveOrgName to see
	 */
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	/**
	 * @return the respectiveRegionalName
	 */
	public String getRespectiveRegionalName() {
		return respectiveRegionalName;
	}

	/**
	 * @param respectiveRegionalName the respectiveRegionalName to see
	 */
	public void setRespectiveRegionalName(String respectiveRegionalName) {
		this.respectiveRegionalName = respectiveRegionalName;
	}

	/**
	 * @return the lastLoadOrgName
	 */
	public String getLastLoadOrgName() {
		return lastLoadOrgName;
	}

	/**
	 * @param lastLoadOrgName the lastLoadOrgName to see
	 */
	public void setLastLoadOrgName(String lastLoadOrgName) {
		this.lastLoadOrgName = lastLoadOrgName;
	}

	/**
	 * @return the goodsQtyTotal
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * @param goodsQtyTotal the goodsQtyTotal to see
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @return the deliveryCustomerCode
	 */
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	/**
	 * @param deliveryCustomerCode the deliveryCustomerCode to see
	 */
	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
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
	 * @param waybillNo the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName the goodsName to see
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return the goodsWeightTotal
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	/**
	 * @param goodsWeightTotal the goodsWeightTotal to see
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * @return the goodsVolumeTotal
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * @param goodsVolumeTotal the goodsVolumeTotal to see
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * @return the deliveryCustomerContact
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	/**
	 * @param deliveryCustomerContact the deliveryCustomerContact to see
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	/**
	 * @return the deliveryCustomerPhone
	 */
	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	/**
	 * @param deliveryCustomerPhone the deliveryCustomerPhone to see
	 */
	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
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
	 * @return the respectiveRegional
	 */
	public String getRespectiveRegional() {
		return respectiveRegional;
	}

	/**
	 * @param respectiveRegional the respectiveRegional to see
	 */
	public void setRespectiveRegional(String respectiveRegional) {
		this.respectiveRegional = respectiveRegional;
	}

	/**
	 * @return the receiveCustomerContact
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * @param receiveCustomerContact the receiveCustomerContact to see
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
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
	 * @return the lastLoadOrgCode
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * @param lastLoadOrgCode the lastLoadOrgCode to see
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	/**
	 * @return the codAmount
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * @param codAmount the codAmount to see
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * @return the insuranceAmount
	 */
	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	/**
	 * @param insuranceAmount the insuranceAmount to see
	 */
	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	/**
	 * @return the prePayAmount
	 */
	public BigDecimal getPrePayAmount() {
		return prePayAmount;
	}

	/**
	 * @param prePayAmount the prePayAmount to see
	 */
	public void setPrePayAmount(BigDecimal prePayAmount) {
		this.prePayAmount = prePayAmount;
	}

	/**
	 * @return the toPayAmount
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * @param toPayAmount the toPayAmount to see
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
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
	 * @return the storageDay
	 */
	public int getStorageDay() {
		return storageDay;
	}

	/**
	 * @param storageDay the storageDay to see
	 */
	public void setStorageDay(int storageDay) {
		this.storageDay = storageDay;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to see
	 */
	public void setNotes(String notes) {
		this.notes = notes;
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
	 * @return the deliveryCustomerMobilephone
	 */
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	/**
	 * @param deliveryCustomerMobilephone the deliveryCustomerMobilephone to see
	 */
	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	public void setAbandonedGoodsFiles(List<AttachementEntity> abandonedGoodsFiles) {
		this.abandonedGoodsFiles = abandonedGoodsFiles;
	}

	public List<AttachementEntity> getAbandonedGoodsFiles() {
		return abandonedGoodsFiles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getBillTime() {
		return billTime;
	}

	public void setBillTime(String billTime) {
		this.billTime = billTime;
	}

	
}