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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillPdaDto.java
 * 
 * FILE NAME        	: WaybillPdaDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;

/**
 */
public class WaybillExpressPdaDto implements Serializable{

	/**
	 * 序列号标识
	 */
	private static final long serialVersionUID = -6894903743731583294L;
	
	/**
	 * 创建人员，司机
	 */
	private String createUserCode;
	
	/**
	 * 司机所在车队部门
	 */
	private String billOrgCode;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 状态
	 */
	private String active;
	
	/**
	 * waybillPendingId(记录补录历史必填)
	 */
	private String waybillPendingId;
	
	/**
	 * operateTime（记录补录历史必填）
	 */
	private Date operateTime;

	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 出发部门
	 */
	private String startOrg;
	
	/**
	 * 订单(转车任务)号
	 */
	private String orderNo;
	
	/**
	 * 提货方式
	 */
	private String receiveMethod;
	
	/**
	 * 目的站
	 */
	private String targetOrgCode;
	
	/**
	 * 运输性质
	 */
	private String productCode;
	
	/**
	 * 重量（单位：千克）
	 */
	private BigDecimal goodsWeightTotal;
	
	/**
	 * 体积(单位：立方米)
	 */
	private BigDecimal goodsVolumeTotal;
	
	/**
	 * 代打木架体积(单位：立方米)
	 */
	private BigDecimal woodVolume;
	
	/**
	 * 木架尺寸(单位：cm*cm*cm)
	 */
	private String woodSize;
	
	/**
	 * 代打木箱体积(单位：立方米)
	 */
	private BigDecimal woodBoxVolume;
	
	/**
	 * 代打木箱尺寸(单位：cm*cm*cm)
	 */
	private String woodBoxSize;
	
	/**
	 * 件数
	 */
	private Integer goodsQty;
	
	/**
	 * 纸
	 */ 
	private Integer paper;
	
	/**
	 * 木
	 */
	private Integer wood;
	
	/**
	 * 纤
	 */ 
	private Integer fibre;
	
	/**
	 * 托
	 */ 
	private Integer salver;
	
	/**
	 * 膜
	 */
	private Integer membrane;
	
	/**
	 * 其它
	 */
	private String otherPackageType;
	
	/**
	 * 货物类型
	 */
	private String goodsTypeCode;
	
	/**
	 * 付款方式
	 */
	private String paidMethod;
	
	/**
	 * 是否打木架
	 */
	private String isWood;
	
	/**
	 * 增值服务项
	 */
	private List<ValueAddServiceDto> valueAddServiceDtoList;
	
	/**
	 * 开单人工号
	 */
	private String billUserNo;
	
	/**
	 * PDA设备号
	 */
	private String pdaNo;
	
	/**
	 * 车牌号
	 */
	private String licensePlateNum;
	
	/**
	 * 开单时间
	 */
	private Date billStart;
	
	/**
	 * 优惠券编号
	 */
	private String discountNo;
	
	/**
	 * 返货方式
	 */
	private String returnWay;
	
	/**
	 * 优惠金额
	 */
	private BigDecimal discountAmount;
	
	private BigDecimal insuranceRate;
	
	/**
	 * 保险声明价值
	 */
	private BigDecimal insuranceAmount;
	/**
	 * 实收运费
	 */
	private BigDecimal actualFee;
	
	/**
	 * 应收运费,总运费
	 */
	private BigDecimal amount;
	
	/**
	 * 返单类别
	 */
	private String returnBillType;
	
	/**
	 * 退款类型
	 */
	private String refundType;
	
	
	/**
	 * 快递员code
	 */
	private String expressEmpCode;
	/**
	 * 快递员名称
	 */
	private String expressEmpName;

	/**
	 * 快递点部CODE
	 */
	private String expressOrgCode;

	/**
	 * 快递点部名称
	 */
	private String expressOrgName;
	
	/**
	 * PDA串号
	 */
	private String pdaSerial;
	
	/**
	 * 银行交易流水号
	 */
	private String bankTradeSerail;
	
	/**
	 * 发货人工号
	 */
	private String sendEmployeeCode;
	
	/**
	 * 是否内部带货
	 */
	private String needDepponCustomerCode;
	
	/**
	 * 短信标识
	 */
	private String isSMS;
	
	
	
	/**
	 * 快递优惠活动类型
	 */
	private String specialOffer;
	
	/**
	 *收货联系人 
	 */
	private String receiveCustomerContact;
	
	/**
	 *发货联系人 
	 */
	private String deliveryCustomerContact;
	
	/**
	 * 货物名称
	 */
	private String goodsName;
	/**
	 * PDA选择的营销活动
	 */
	MarkActivitiesQueryConditionDto activeDto;

	public MarkActivitiesQueryConditionDto getActiveDto() {
		return activeDto;
	}

	public void setActiveDto(MarkActivitiesQueryConditionDto activeDto) {
		this.activeDto = activeDto;
	}
	
	private String deliveryCustomerId;
	
	/**
	 * 发货客户联系人ID
	 */
	private String deliverCustContactId;
	
	/**
	 * 发货客户编码
	 */
	private String deliveryCustomerCode;
	
	/**
	 * 发货客户名称
	 */
	private String deliveryCustomerName;
	
	/**
	 * 发货客户手机号
	 */
	private String deliveryCustomerMobilephone;
	
	/**
	 * 发货客户电话号码
	 */
	private String deliveryCustomerPhone;
	
	/**
	 * 发货客户地址
	 */
	private String deliveryCustomerAddress;

	/**
	 * 发货客户国家
	 */
	private String deliveryCustomerNationCode;
	
	/**
	 * 发货客户省份
	 */
	private String deliveryCustomerProvCode;
	
	/**
	 * 发货客户城市
	 */
	private String deliveryCustomerCityCode;
	
	/**
	 * 发货客户地区
	 */
	private String deliveryCustomerDistCode;
	
	/**
	 * 收货客户ID
	 */
	private String receiveCustomerId;

	/**
	 * 收货客户联系人
	 */
	private String receiveCustomerContactId;

	/**
	 * 收货客户编码
	 */
	private String receiveCustomerCode;

	/**
	 * 收货客户名称
	 */
	private String receiveCustomerName;

	/**
	 * 收货客户手机电话
	 */
	private String receiveCustomerMobilephone;

	/**
	 * 收货客户电话
	 */
	private String receiveCustomerPhone;

	/**
	 * 收货客户详细地址
	 */
	private String receiveCustomerAddress;

	/**
	 * 收货客户ID
	 */
	private String contactAddressId;

	/**
	 * 收货客户所在国家
	 */
	private String receiveCustomerNationCode;

	/**
	 * 收货客户所在省份
	 */
	private String receiveCustomerProvCode;

	/**
	 * 收货客户城市
	 */
	private String receiveCustomerCityCode;

	/**
	 * 收货客户地区
	 */
	private String receiveCustomerDistCode;
	
	/**
	 * 旧单号
	 */
	private String oldWaybillNo;
	
	/**
	 * 内部带货费用承担部门
	 */
	private String innerPickupFeeBurdenDept;
	
	/**
	 * 最初的运单号
	 */
	private String originalWaybillNo;
	
	/**
	 * 是否贵重物品
	 */
	private String preciousGoods;
	
	/**
	 * 是否异形物品
	 */
	private String specialShapedGoods;
	
	/**
	 * 对外备注
	 */
	private String outerNotes;
	
	/**
	 * 对内备注
	 */
	private String innerNotes;
	
	/**
	 * 储运事项
	 */
	private String transportationRemark;
	
	/**
	 * 预付保密
	 */
	private String secretPrepaid;
	
	/**
	 * 总费用
	 */
	private BigDecimal totalFee;
	
	/**
	 * 返款帐户开户名称
	 */
	private String accountName;

	/**
	 * 返款帐户开户账户
	 */
	private String accountCode;

	/**
	 * 返款帐户开户银行
	 */
	private String accountBank;

	/**
	 * 计费重量
	 */
	private BigDecimal billWeight;

	/**
	 * 接货费
	 */
	private BigDecimal pickupFee;

	/**
	 * 装卸费
	 */
	private BigDecimal serviceFee;
	
	/**
	 * 内部员工发货人工号
	 */
	private String deliveryEmployeeNo;
	
	/**
	 * 经济自提件类型
	 */
	private String economyGoodsType;
	
	/**
	 * 公里数
	 */
	private BigDecimal kilometer;
	
	/**
	 * 最终配载部门
	 */
	private String lastLoadOrgCode;
	
	/**
	 * 预计提货时间
	 */
	private Date preCustomerPickupTime;

	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;

	/**
	 * 预付金额
	 */
	private BigDecimal prePayAmount;

	/**
	 * 送货费
	 */
	private BigDecimal deliveryGoodsFee;

	/**
	 * 其他费用
	 */
	private BigDecimal otherFee;
	
	/**
	 * 包装手续费
	 */
	private BigDecimal packageFee;
	
	/**
	 * 代收货款手续费
	 */
	private BigDecimal codFee;

	/**
	 * 保价费
	 */
	private BigDecimal insuranceFee;

	/**
	 * 代收货款
	 */
	private BigDecimal codAmount;

	/**
	 * 代收费率
	 */
	private BigDecimal codRate;
	
	private String goodsSize;
	
	/**
	 * 配载类型
	 */
	private String loadMethod;
	
	/**
	 * 代收货款类型
	 */
	private String reciveLoanType;
	
	/**
	 * 运单类型
	 */
	private String waybillType;
	
	/**
     * 原单号
     */
    private String oldWayBill;
    
    /**
     * 是否返货业务 Y/N
     */
    private String isReturnGoods;
    
	
	public String getLoadMethod() {
		return loadMethod;
	}
	
	public void setLoadMethod(String loadMethod) {
		this.loadMethod = loadMethod;
	}
	
	public String getGoodsSize() {
		return goodsSize;
	}
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}
	public BigDecimal getCodAmount() {
		return codAmount;
	}
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}
	public BigDecimal getCodRate() {
		return codRate;
	}
	public void setCodRate(BigDecimal codRate) {
		this.codRate = codRate;
	}
	public BigDecimal getCodFee() {
		return codFee;
	}
	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}
	public BigDecimal getPackageFee() {
		return packageFee;
	}
	public void setPackageFee(BigDecimal packageFee) {
		this.packageFee = packageFee;
	}
	
	public BigDecimal getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}
	
	public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee) {
		this.deliveryGoodsFee = deliveryGoodsFee;
	}
	
	public BigDecimal getInsuranceRate() {
		return insuranceRate;
	}
	public void setInsuranceRate(BigDecimal insuranceRate) {
		this.insuranceRate = insuranceRate;
	}
	
	public BigDecimal getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}
	
	public void setPreCustomerPickupTime(Date preCustomerPickupTime) {
		this.preCustomerPickupTime = preCustomerPickupTime;
	}
	public Date getPreCustomerPickupTime() {
		return preCustomerPickupTime;
	}
	
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}
	
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}
	
	public BigDecimal getKilometer() {
		return kilometer;
	}
	
	public void setKilometer(BigDecimal kilometer) {
		this.kilometer = kilometer;
	}
	
	public String getEconomyGoodsType() {
		return economyGoodsType;
	}
	
	public void setEconomyGoodsType(String economyGoodsType) {
		this.economyGoodsType = economyGoodsType;
	}
	
	public void setDeliveryEmployeeNo(String deliveryEmployeeNo) {
		this.deliveryEmployeeNo = deliveryEmployeeNo;
	}
	
	public String getDeliveryEmployeeNo() {
		return deliveryEmployeeNo;
	}
	
	public BigDecimal getTotalFee() {
		return totalFee;
	}
	
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	public String getSecretPrepaid() {
		return secretPrepaid;
	}
	
	public void setSecretPrepaid(String secretPrepaid) {
		this.secretPrepaid = secretPrepaid;
	}
	
	public String getTransportationRemark() {
		return transportationRemark;
	}
	
	public void setTransportationRemark(String transportationRemark) {
		this.transportationRemark = transportationRemark;
	}
	
	public String getInnerNotes() {
		return innerNotes;
	}
	
	public void setInnerNotes(String innerNotes) {
		this.innerNotes = innerNotes;
	}
	
	public String getOuterNotes() {
		return outerNotes;
	}
	
	public void setOuterNotes(String outerNotes) {
		this.outerNotes = outerNotes;
	}
	
	public String getSpecialShapedGoods() {
		return specialShapedGoods;
	}
	
	public void setSpecialShapedGoods(String specialShapedGoods) {
		this.specialShapedGoods = specialShapedGoods;
	}
	
	public String getPreciousGoods() {
		return preciousGoods;
	}
	
	public void setPreciousGoods(String preciousGoods) {
		this.preciousGoods = preciousGoods;
	}
	
	public String getOriginalWaybillNo() {
		return originalWaybillNo;
	}
	
	public void setOriginalWaybillNo(String originalWaybillNo) {
		this.originalWaybillNo = originalWaybillNo;
	}
	
	public String getInnerPickupFeeBurdenDept() {
		return innerPickupFeeBurdenDept;
	}
	
	public void setInnerPickupFeeBurdenDept(String innerPickupFeeBurdenDept) {
		this.innerPickupFeeBurdenDept = innerPickupFeeBurdenDept;
	}
	
	public String getOldWaybillNo() {
		return oldWaybillNo;
	}
	
	public void setOldWaybillNo(String oldWaybillNo) {
		this.oldWaybillNo = oldWaybillNo;
	}
	
	public String getSpecialOffer() {
		return specialOffer;
	}
	public void setSpecialOffer(String specialOffer) {
		this.specialOffer = specialOffer;
	}
	public String getIsSMS() {
		return isSMS;
	}
	public void setIsSMS(String isSMS) {
		this.isSMS = isSMS;
	}
	public String getDeliveryCustomerId() {
		return deliveryCustomerId;
	}
	
	public void setDeliveryCustomerId(String deliveryCustomerId) {
		this.deliveryCustomerId = deliveryCustomerId;
	}
	
	public String getNeedDepponCustomerCode() {
		return needDepponCustomerCode;
	}


	public void setNeedDepponCustomerCode(String needDepponCustomerCode) {
		this.needDepponCustomerCode = needDepponCustomerCode;
	}


	public String getSendEmployeeCode() {
		return sendEmployeeCode;
	}


	public void setSendEmployeeCode(String sendEmployeeCode) {
		this.sendEmployeeCode = sendEmployeeCode;
	}


	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}


	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}


	/**
	 * Gets the returnBillType.
	 * 
	 * @return the returnBillType
	 */
	public String getReturnBillType() {
		return returnBillType;
	}


	/**
	 * Sets the returnBillType.
	 * 
	 * @param returnBillType the returnBillType to see
	 */
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}


	/**
	 * Gets the refundType.
	 * 
	 * @return the refundType
	 */
	public String getRefundType() {
		return refundType;
	}


	/**
	 * Sets the refundType.
	 * 
	 * @param refundType the refundType to see
	 */
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}


	/**
	 * Gets the waybill pending id.
	 *
	 * @return the waybill pending id
	 */
	public String getWaybillPendingId() {
		return waybillPendingId;
	}

	
	/**
	 * Sets the waybill pending id.
	 *
	 * @param waybillPendingId the new waybill pending id
	 */
	public void setWaybillPendingId(String waybillPendingId) {
		this.waybillPendingId = waybillPendingId;
	}


	/**
	 * Gets the goods weight total.
	 *
	 * @return the goods weight total
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}


	/**
	 * Sets the goods weight total.
	 *
	 * @param goodsWeightTotal the new goods weight total
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}


	/**
	 * Gets the goods volume total.
	 *
	 * @return the goods volume total
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	
	/**
	 * Sets the goods volume total.
	 *
	 * @param goodsVolumeTotal the new goods volume total
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	
	/**
	 * Gets the wood volume.
	 *
	 * @return the wood volume
	 */
	public BigDecimal getWoodVolume() {
		return woodVolume;
	}


	/**
	 * Sets the wood volume.
	 *
	 * @param woodVolume the new wood volume
	 */
	public void setWoodVolume(BigDecimal woodVolume) {
		this.woodVolume = woodVolume;
	}

	
	/**
	 * Gets the wood size.
	 *
	 * @return the wood size
	 */
	public String getWoodSize() {
		return woodSize;
	}

	
	/**
	 * Sets the wood size.
	 *
	 * @param woodSize the new wood size
	 */
	public void setWoodSize(String woodSize) {
		this.woodSize = woodSize;
	}

	
	/**
	 * Gets the goods qty.
	 *
	 * @return the goods qty
	 */
	public Integer getGoodsQty() {
		return goodsQty;
	}

	
	/**
	 * Sets the goods qty.
	 *
	 * @param goodsQty the new goods qty
	 */
	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	
	/**
	 * Gets the bill start.
	 *
	 * @return the bill start
	 */
	public Date getBillStart() {
		return billStart;
	}

	
	/**
	 * Sets the bill start.
	 *
	 * @param billStart the new bill start
	 */
	public void setBillStart(Date billStart) {
		this.billStart = billStart;
	}

	
	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	
	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
	/**
	 * Ge actual fee.
	 *
	 * @return the big decimal
	 */
	public BigDecimal geActualFee() {
		return actualFee;
	}

	
	/**
	 * Sets the actual fee.
	 *
	 * @param actualFee the new actual fee
	 */
	public void setActualFee(BigDecimal actualFee) {
		this.actualFee = actualFee;
	}


	/**
	 * Gets the waybill no.
	 *
	 * @return the waybill no
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	
	/**
	 * Sets the waybill no.
	 *
	 * @param waybillNo the new waybill no
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	/**
	 * Gets the start org.
	 *
	 * @return the start org
	 */
	public String getStartOrg() {
		return startOrg;
	}

	
	/**
	 * Sets the start org.
	 *
	 * @param startOrg the new start org
	 */
	public void setStartOrg(String startOrg) {
		this.startOrg = startOrg;
	}

	
	/**
	 * Gets the order no.
	 *
	 * @return the order no
	 */
	public String getOrderNo() {
		return orderNo;
	}

	
	/**
	 * Sets the order no.
	 *
	 * @param orderNo the new order no
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	
	/**
	 * Gets the receive method.
	 *
	 * @return the receive method
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	
	/**
	 * Sets the receive method.
	 *
	 * @param receiveMethod the new receive method
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	
	/**
	 * Gets the target org code.
	 *
	 * @return the target org code
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	
	/**
	 * Sets the target org code.
	 *
	 * @param targetOrgCode the new target org code
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	
	/**
	 * Gets the product code.
	 *
	 * @return the product code
	 */
	public String getProductCode() {
		return productCode;
	}

	
	/**
	 * Sets the product code.
	 *
	 * @param productCode the new product code
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	
	/**
	 * Gets the goods type code.
	 *
	 * @return the goods type code
	 */
	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

	
	/**
	 * Sets the goods type code.
	 *
	 * @param goodsTypeCode the new goods type code
	 */
	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}

	
	/**
	 * Gets the paid method.
	 *
	 * @return the paid method
	 */
	public String getPaidMethod() {
		return paidMethod;
	}

	
	/**
	 * Sets the paid method.
	 *
	 * @param paidMethod the new paid method
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	
	/**
	 * Gets the is wood.
	 *
	 * @return the is wood
	 */
	public String getIsWood() {
		return isWood;
	}

	
	/**
	 * Sets the is wood.
	 *
	 * @param isWood the new is wood
	 */
	public void setIsWood(String isWood) {
		this.isWood = isWood;
	}

	
	/**
	 * Gets the bill user no.
	 *
	 * @return the bill user no
	 */
	public String getBillUserNo() {
		return billUserNo;
	}

	
	/**
	 * Sets the bill user no.
	 *
	 * @param billUserNo the new bill user no
	 */
	public void setBillUserNo(String billUserNo) {
		this.billUserNo = billUserNo;
	}

	
	/**
	 * Gets the pda no.
	 *
	 * @return the pda no
	 */
	public String getPdaNo() {
		return pdaNo;
	}

	
	/**
	 * Sets the pda no.
	 *
	 * @param pdaNo the new pda no
	 */
	public void setPdaNo(String pdaNo) {
		this.pdaNo = pdaNo;
	}

	
	/**
	 * Gets the license plate num.
	 *
	 * @return the license plate num
	 */
	public String getLicensePlateNum() {
		return licensePlateNum;
	}

	
	/**
	 * Sets the license plate num.
	 *
	 * @param licensePlateNum the new license plate num
	 */
	public void setLicensePlateNum(String licensePlateNum) {
		this.licensePlateNum = licensePlateNum;
	}

	
	/**
	 * Gets the operate time.
	 *
	 * @return the operate time
	 */
	public Date getOperateTime() {
		return operateTime;
	}
	
	/**
	 * Sets the operate time.
	 *
	 * @param operateTime the new operate time
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}


	/**
	 * Gets the other package type.
	 *
	 * @return the other package type
	 */
	public String getOtherPackageType() {
		return otherPackageType;
	}

	
	/**
	 * Sets the other package type.
	 *
	 * @param otherPackageType the new other package type
	 */
	public void setOtherPackageType(String otherPackageType) {
		this.otherPackageType = otherPackageType;
	}

	
	/**
	 * Gets the discount no.
	 *
	 * @return the discount no
	 */
	public String getDiscountNo() {
		return discountNo;
	}

	
	/**
	 * Sets the discount no.
	 *
	 * @param discountNo the new discount no
	 */
	public void setDiscountNo(String discountNo) {
		this.discountNo = discountNo;
	}

	
	/**
	 * Gets the discount amount.
	 *
	 * @return the discount amount
	 */
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	
	/**
	 * Sets the discount amount.
	 *
	 * @param discountAmount the new discount amount
	 */
	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	
	/**
	 * Gets the active.
	 *
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	
	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	
	/**
	 * Gets the creates the user code.
	 *
	 * @return the creates the user code
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	
	/**
	 * Sets the creates the user code.
	 *
	 * @param createUserCode the new creates the user code
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	
	/**
	 * Gets the bill org code.
	 *
	 * @return the bill org code
	 */
	public String getBillOrgCode() {
		return billOrgCode;
	}

	
	/**
	 * Sets the bill org code.
	 *
	 * @param billOrgCode the new bill org code
	 */
	public void setBillOrgCode(String billOrgCode) {
		this.billOrgCode = billOrgCode;
	}

	
	/**
	 * Gets the creates the time.
	 *
	 * @return the creates the time
	 */
	public Date getCreateTime() {
		return createTime;
	}

	
	/**
	 * Sets the creates the time.
	 *
	 * @param createTime the new creates the time
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	/**
	 * Gets the paper.
	 *
	 * @return the paper
	 */
	public Integer getPaper() {
		return paper;
	}

	
	/**
	 * Sets the paper.
	 *
	 * @param paper the new paper
	 */
	public void setPaper(Integer paper) {
		this.paper = paper;
	}

	
	/**
	 * Gets the wood.
	 *
	 * @return the wood
	 */
	public Integer getWood() {
		return wood;
	}

	
	/**
	 * Sets the wood.
	 *
	 * @param wood the new wood
	 */
	public void setWood(Integer wood) {
		this.wood = wood;
	}

	
	/**
	 * Gets the fibre.
	 *
	 * @return the fibre
	 */
	public Integer getFibre() {
		return fibre;
	}

	
	/**
	 * Sets the fibre.
	 *
	 * @param fibre the new fibre
	 */
	public void setFibre(Integer fibre) {
		this.fibre = fibre;
	}

	
	/**
	 * Gets the salver.
	 *
	 * @return the salver
	 */
	public Integer getSalver() {
		return salver;
	}

	
	/**
	 * Sets the salver.
	 *
	 * @param salver the new salver
	 */
	public void setSalver(Integer salver) {
		this.salver = salver;
	}

	
	/**
	 * Gets the membrane.
	 *
	 * @return the membrane
	 */
	public Integer getMembrane() {
		return membrane;
	}

	
	/**
	 * Sets the membrane.
	 *
	 * @param membrane the new membrane
	 */
	public void setMembrane(Integer membrane) {
		this.membrane = membrane;
	}


	
	/**
	 * Gets the wood box volume.
	 *
	 * @return the wood box volume
	 */
	public BigDecimal getWoodBoxVolume() {
		return woodBoxVolume;
	}


	
	/**
	 * Sets the wood box volume.
	 *
	 * @param woodBoxVolume the new wood box volume
	 */
	public void setWoodBoxVolume(BigDecimal woodBoxVolume) {
		this.woodBoxVolume = woodBoxVolume;
	}


	
	/**
	 * Gets the wood box size.
	 *
	 * @return the wood box size
	 */
	public String getWoodBoxSize() {
		return woodBoxSize;
	}


	
	/**
	 * Sets the wood box size.
	 *
	 * @param woodBoxSize the new wood box size
	 */
	public void setWoodBoxSize(String woodBoxSize) {
		this.woodBoxSize = woodBoxSize;
	}


	
	/**
	 * Gets the value add service dto list.
	 *
	 * @return the value add service dto list
	 */
	public List<ValueAddServiceDto> getValueAddServiceDtoList() {
		return valueAddServiceDtoList;
	}


	
	/**
	 * Sets the value add service dto list.
	 *
	 * @param valueAddServiceDtoList the new value add service dto list
	 */
	public void setValueAddServiceDtoList(
			List<ValueAddServiceDto> valueAddServiceDtoList) {
		this.valueAddServiceDtoList = valueAddServiceDtoList;
	}


	public String getExpressEmpCode() {
		return expressEmpCode;
	}


	public void setExpressEmpCode(String expressEmpCode) {
		this.expressEmpCode = expressEmpCode;
	}


	public String getExpressEmpName() {
		return expressEmpName;
	}


	public void setExpressEmpName(String expressEmpName) {
		this.expressEmpName = expressEmpName;
	}


	public String getExpressOrgCode() {
		return expressOrgCode;
	}


	public void setExpressOrgCode(String expressOrgCode) {
		this.expressOrgCode = expressOrgCode;
	}


	public String getExpressOrgName() {
		return expressOrgName;
	}


	public void setExpressOrgName(String expressOrgName) {
		this.expressOrgName = expressOrgName;
	}


	public String getPdaSerial() {
		return pdaSerial;
	}


	public void setPdaSerial(String pdaSerial) {
		this.pdaSerial = pdaSerial;
	}


	public String getBankTradeSerail() {
		return bankTradeSerail;
	}


	public void setBankTradeSerail(String bankTradeSerail) {
		this.bankTradeSerail = bankTradeSerail;
	}
	
	public BigDecimal getActualFee() {
		return actualFee;
	}
	
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}
	
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}
	
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}
	
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}
	
	public String getGoodsName() {
		return goodsName;
	}
	
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getDeliverCustContactId() {
		return deliverCustContactId;
	}
	public void setDeliverCustContactId(String deliverCustContactId) {
		this.deliverCustContactId = deliverCustContactId;
	}
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}
	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}
	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}
	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}
	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}
	public String getDeliveryCustomerAddress() {
		return deliveryCustomerAddress;
	}
	public void setDeliveryCustomerAddress(String deliveryCustomerAddress) {
		this.deliveryCustomerAddress = deliveryCustomerAddress;
	}
	public String getDeliveryCustomerNationCode() {
		return deliveryCustomerNationCode;
	}
	public void setDeliveryCustomerNationCode(String deliveryCustomerNationCode) {
		this.deliveryCustomerNationCode = deliveryCustomerNationCode;
	}
	public String getDeliveryCustomerProvCode() {
		return deliveryCustomerProvCode;
	}
	public void setDeliveryCustomerProvCode(String deliveryCustomerProvCode) {
		this.deliveryCustomerProvCode = deliveryCustomerProvCode;
	}
	public String getDeliveryCustomerCityCode() {
		return deliveryCustomerCityCode;
	}
	public void setDeliveryCustomerCityCode(String deliveryCustomerCityCode) {
		this.deliveryCustomerCityCode = deliveryCustomerCityCode;
	}
	public String getDeliveryCustomerDistCode() {
		return deliveryCustomerDistCode;
	}
	public void setDeliveryCustomerDistCode(String deliveryCustomerDistCode) {
		this.deliveryCustomerDistCode = deliveryCustomerDistCode;
	}
	public String getReceiveCustomerId() {
		return receiveCustomerId;
	}
	public void setReceiveCustomerId(String receiveCustomerId) {
		this.receiveCustomerId = receiveCustomerId;
	}
	public String getReceiveCustomerContactId() {
		return receiveCustomerContactId;
	}
	public void setReceiveCustomerContactId(String receiveCustomerContactId) {
		this.receiveCustomerContactId = receiveCustomerContactId;
	}
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}
	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}
	public String getContactAddressId() {
		return contactAddressId;
	}
	public void setContactAddressId(String contactAddressId) {
		this.contactAddressId = contactAddressId;
	}
	public String getReceiveCustomerNationCode() {
		return receiveCustomerNationCode;
	}
	public void setReceiveCustomerNationCode(String receiveCustomerNationCode) {
		this.receiveCustomerNationCode = receiveCustomerNationCode;
	}
	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}
	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}
	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}
	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}
	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}
	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getAccountBank() {
		return accountBank;
	}
	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}
	public BigDecimal getBillWeight() {
		return billWeight;
	}
	public void setBillWeight(BigDecimal billWeight) {
		this.billWeight = billWeight;
	}
	public BigDecimal getPickupFee() {
		return pickupFee;
	}
	public void setPickupFee(BigDecimal pickupFee) {
		this.pickupFee = pickupFee;
	}

	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	public BigDecimal getPrePayAmount() {
		return prePayAmount;
	}

	public void setPrePayAmount(BigDecimal prePayAmount) {
		this.prePayAmount = prePayAmount;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	public String getReciveLoanType() {
		return reciveLoanType;
	}

	public void setReciveLoanType(String reciveLoanType) {
		this.reciveLoanType = reciveLoanType;
	}

	public String getWaybillType() {
		return waybillType;
	}

	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}

	public String getOldWayBill() {
		return oldWayBill;
	}

	public void setOldWayBill(String oldWayBill) {
		this.oldWayBill = oldWayBill;
	}

	public String getIsReturnGoods() {
		return isReturnGoods;
	}

	public void setIsReturnGoods(String isReturnGoods) {
		this.isReturnGoods = isReturnGoods;
	}

	public String getReturnWay() {
		return returnWay;
	}

	public void setReturnWay(String returnWay) {
		this.returnWay = returnWay;
	}
	
}