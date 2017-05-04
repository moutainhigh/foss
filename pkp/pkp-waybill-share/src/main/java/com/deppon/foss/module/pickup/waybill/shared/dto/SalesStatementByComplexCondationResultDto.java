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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/SalesStatementByComplexCondationResultDto.java
 * 
 * FILE NAME        	: SalesStatementByComplexCondationResultDto.java
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
import com.deppon.foss.util.define.FossConstants;

/**
 * 根据不同的查询条件组合查询结果列表，结果为封装为dto的集合：1、 收货日期 2、 单号 3、 目的站 4、 件数 5、 重量 6、 预付金额 7、 尺寸
 * 8、 发货人 9、 计费重量 10、 体积 11、 包装费、 12、 运费 13、 到付金额 14、 代收货款 15、 代收货款手续费 16、 退款金额
 * 17、 开单送货费 18、 到付送货费 19、 开单金额 20、 收货部门 21、 对账部门 22、 制单部门 23、 货物名称 24、 包装 25、
 * 收货人 26、 收货人地址 27、 签收人 28、 保险费 29、 保险价值 30、 付款方式 31、 运输性质 32、 制单人 33、 运价 34、
 * 提货方式 35、 储运事项  36、收货地址省份 37、收货地址城市 38、收货地址区县
 * 
 * 
 * @author 026113-foss-linwensheng
 */
public class SalesStatementByComplexCondationResultDto implements Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -1424123993269960054L;

	/**
	 * 开单时间（收货日期）
	 */
	private Date billTime;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 目的站
	 */
	private String targetOrgName;

	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal;

	/**
	 * 货物总重量
	 */
	private BigDecimal goodsWeightTotal;

	/**
	 * 预付金额
	 */
	private BigDecimal prePayAmount;

	/**
	 * 货物尺寸
	 */
	private String goodsSize;

	/**
	 * 发货客户联系人（发货人）
	 */
	private String deliveryCustomerContact;

	/**
	 * 计费重量
	 */
	private BigDecimal billWeight;

	/**
	 * 货物总体积
	 */
	private BigDecimal goodsVolumeTotal;

	/**
	 * 包装手续费
	 */
	private BigDecimal packageFee;
	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;
	/**
	 * 代收货款
	 */
	private BigDecimal codAmount;
	/**
	 * 代收货款手续费
	 */
	private BigDecimal codFee;
	/**
	 * 退款金额（暂留）
	 */
	private BigDecimal refundAmount;
	/**
	 * 开单送货费
	 */
	private BigDecimal deliveryGoodsFee;
	/**
	 * 到付送货费
	 */
	private BigDecimal arriveDeliGoodsFee;
	/**
	 * 开单费（开单金额）
	 */
	private BigDecimal totalFee;
	/**
	 * 收货部门(出发部门)
	 */
	private String receiveOrgName;
	/**
	 * 对账部门
	 */
	private String checkAccountOrgName;
	/**
	 * 制单部门	
	 */
	private String createOrgName;
	/**
	 * 货物名称
	 */
	private String goodsName;
	/**
	 * 货物包装
	 */
	private String goodsPackage;
	/**
	 * 收货客户名称（收货人）
	 */
	private String receiveCustomerName;
	/**
	 * 收货具体地址
	 */
	private String receiveCustomerAddress;
	/**
	 * 签收人
	 */
	private String signer;
	/**
	 * 保险费
	 */
	private BigDecimal insuranceFee;
	/**
	 * 保价声明价值（保险价值）
	 */
	private BigDecimal insuranceAmount;
	/**
	 * 开单付款方式
	 */
	private String paidMethod;
	/**
	 * 运输类型（运输性质）
	 */
	private String transportType;
	/**
	 * 开单人（制单人-制单员）
	 */
	private String createUserName;
	/**
	 * 运费计费费率（运价）
	 */
	private BigDecimal unitPrice;
	/**
	 * 提货方式
	 */
	private String receiveMethod;
	/**
	 * 储运事项
	 */
	private String transportationRemark;
	/**
	 * 公布价运费（运费）
	 */
	private BigDecimal transportFee;
	/**
	 * 新增显示字段
	 */
	/**
	 * 是否作废
	 */
	private String isInvalid;
	/**
	 * 派送部门
	 */
	private String deliverDeptName;
	/**
	 * 收货人电话
	 */
	private String receiverCustomerTel;
	/**
	 * 收货地址省份
	 */
	private String receiverCustomerProv;
	/**
	 * 收货地址城市
	 */
	private String receiverCustomerCity;
	/**
	 * 收货地址省份
	 */
	private String receiverCustomerDist;
	/**
	 * 收货地址乡镇编码
	 */
	private String receiverCustomerVillageCode;
	/**
	 * 收货地址乡镇名称
	 */
	private String receiverCustomerVillage;
	/**
	 * 增值服务费
	 */
	private BigDecimal valueAddFee;	
	/**
	 * 优惠费用
	 */
	private BigDecimal promotionsFee;
	
	/**
	 * 司机工号
	 */
	private String  emp_code;
	
	/**
	 * 司机名称
	 */
	private String emp_name;
	
	/**
	 * FOSSK开单时间
	 */
	private Date createTime;
	
	/**
	 * 是否PDA制单
	 */
	private String isPdaCreate;
	
	/**
	 * 装卸费（劳务费）
	 */
	private BigDecimal service_fee;
	
	/**
	 * 发货人电话
	 * @return
	 */
	private String deliveryCustomerPhone;
	
	/**
	 * 发货人手机
	 * @return
	 */
	private String deliveryCustomerMobilephone;
	
	/**
	 * 收货人电话
	 * @return
	 */
	private String receiveCustomerPhone;
	
	/**
	 * 收货人手机
	 * @return
	 */
	private String receiveCustomerMobilephone;
	
	
	/**
	 * 客户分群
	 */
	private String flabelleavemonth;
	
	public String getFlabelleavemonth() {
		return flabelleavemonth;
	}
	public void setFlabelleavemonth(String flabelleavemonth) {
		this.flabelleavemonth = flabelleavemonth;
	}
	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}
	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}
	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}
	public BigDecimal getService_fee() {
		return service_fee;
	}
	public void setService_fee(BigDecimal serviceFee) {
		this.service_fee = serviceFee;
	}
	public String getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(String empCode) {
		this.emp_code = empCode;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String empName) {
		this.emp_name = empName;
	}
	/**
	 * @return billTime : set the property billTime.
	 */
	public Date getBillTime() {
		return billTime;
	}
	/**
	 * @param billTime : return the property billTime.
	 */
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
	/**
	 * @return waybillNo : set the property waybillNo.
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo : return the property waybillNo.
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	 * @return targetOrgName : set the property targetOrgName.
	 */
	public String getTargetOrgName() {
		return targetOrgName;
	}
	/**
	 * @param targetOrgName : return the property targetOrgName.
	 */
	public void setTargetOrgName(String targetOrgName) {
		this.targetOrgName = targetOrgName;
	}
	/**
	 * @return goodsQtyTotal : set the property goodsQtyTotal.
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	/**
	 * @param goodsQtyTotal : return the property goodsQtyTotal.
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	/**
	 * @return goodsWeightTotal : set the property goodsWeightTotal.
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}
	/**
	 * @param goodsWeightTotal : return the property goodsWeightTotal.
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}
	/**
	 * @return prePayAmount : set the property prePayAmount.
	 */
	public BigDecimal getPrePayAmount() {
		return prePayAmount;
	}
	/**
	 * @param prePayAmount : return the property prePayAmount.
	 */
	public void setPrePayAmount(BigDecimal prePayAmount) {
		this.prePayAmount = prePayAmount;
	}
	/**
	 * @return goodsSize : set the property goodsSize.
	 */
	public String getGoodsSize() {
		return goodsSize;
	}
	/**
	 * @param goodsSize : return the property goodsSize.
	 */
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}
	/**
	 * @return deliveryCustomerContact : set the property deliveryCustomerContact.
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}
	/**
	 * @param deliveryCustomerContact : return the property deliveryCustomerContact.
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}
	/**
	 * @return billWeight : set the property billWeight.
	 */
	public BigDecimal getBillWeight() {
		return billWeight;
	}
	/**
	 * @param billWeight : return the property billWeight.
	 */
	public void setBillWeight(BigDecimal billWeight) {
		this.billWeight = billWeight;
	}
	/**
	 * @return goodsVolumeTotal : set the property goodsVolumeTotal.
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}
	/**
	 * @param goodsVolumeTotal : return the property goodsVolumeTotal.
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}
	/**
	 * @return packageFee : set the property packageFee.
	 */
	public BigDecimal getPackageFee() {
		return packageFee;
	}
	/**
	 * @param packageFee : return the property packageFee.
	 */
	public void setPackageFee(BigDecimal packageFee) {
		this.packageFee = packageFee;
	}
	/**
	 * @return toPayAmount : set the property toPayAmount.
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}
	/**
	 * @param toPayAmount : return the property toPayAmount.
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}
	/**
	 * @return codAmount : set the property codAmount.
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}
	/**
	 * @param codAmount : return the property codAmount.
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}
	/**
	 * @return codFee : set the property codFee.
	 */
	public BigDecimal getCodFee() {
		return codFee;
	}
	/**
	 * @param codFee : return the property codFee.
	 */
	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}
	/**
	 * @return refundAmount : set the property refundAmount.
	 */
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	/**
	 * @param refundAmount : return the property refundAmount.
	 */
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	/**
	 * @return deliveryGoodsFee : set the property deliveryGoodsFee.
	 */
	public BigDecimal getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}
	/**
	 * @param deliveryGoodsFee : return the property deliveryGoodsFee.
	 */
	public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee) {
		this.deliveryGoodsFee = deliveryGoodsFee;
	}
	/**
	 * @return arriveDeliGoodsFee : set the property arriveDeliGoodsFee.
	 */
	public BigDecimal getArriveDeliGoodsFee() {
		return arriveDeliGoodsFee;
	}
	/**
	 * @param arriveDeliGoodsFee : return the property arriveDeliGoodsFee.
	 */
	public void setArriveDeliGoodsFee(BigDecimal arriveDeliGoodsFee) {
		this.arriveDeliGoodsFee = arriveDeliGoodsFee;
	}
	/**
	 * @return totalFee : set the property totalFee.
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
	}
	/**
	 * @param totalFee : return the property totalFee.
	 */
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	/**
	 * @return receiveOrgName : set the property receiveOrgName.
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	/**
	 * @param receiveOrgName : return the property receiveOrgName.
	 */
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	/**
	 * @return checkAccountOrgName : set the property checkAccountOrgName.
	 */
	public String getCheckAccountOrgName() {
		return checkAccountOrgName;
	}
	/**
	 * @param checkAccountOrgName : return the property checkAccountOrgName.
	 */
	public void setCheckAccountOrgName(String checkAccountOrgName) {
		this.checkAccountOrgName = checkAccountOrgName;
	}
	/**
	 * @return createOrgName : set the property createOrgName.
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}
	/**
	 * @param createOrgName : return the property createOrgName.
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	/**
	 * @return goodsName : set the property goodsName.
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * @param goodsName : return the property goodsName.
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * @return goodsPackage : set the property goodsPackage.
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}
	/**
	 * @param goodsPackage : return the property goodsPackage.
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}
	/**
	 * @return receiveCustomerName : set the property receiveCustomerName.
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}
	/**
	 * @param receiveCustomerName : return the property receiveCustomerName.
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}
	/**
	 * @return receiveCustomerAddress : set the property receiveCustomerAddress.
	 */
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}
	/**
	 * @param receiveCustomerAddress : return the property receiveCustomerAddress.
	 */
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}
	/**
	 * @return signer : set the property signer.
	 */
	public String getSigner() {
		return signer;
	}
	/**
	 * @param signer : return the property signer.
	 */
	public void setSigner(String signer) {
		this.signer = signer;
	}
	/**
	 * @return insuranceFee : set the property insuranceFee.
	 */
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}
	/**
	 * @param insuranceFee : return the property insuranceFee.
	 */
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}
	/**
	 * @return insuranceAmount : set the property insuranceAmount.
	 */
	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}
	/**
	 * @param insuranceAmount : return the property insuranceAmount.
	 */
	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}
	/**
	 * @return paidMethod : set the property paidMethod.
	 */
	public String getPaidMethod() {
		return paidMethod;
	}
	/**
	 * @param paidMethod : return the property paidMethod.
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}
	/**
	 * @return transportType : set the property transportType.
	 */
	public String getTransportType() {
		return transportType;
	}
	/**
	 * @param transportType : return the property transportType.
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	/**
	 * @return createUserName : set the property createUserName.
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	/**
	 * @param createUserName : return the property createUserName.
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	/**
	 * @return unitPrice : set the property unitPrice.
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	/**
	 * @param unitPrice : return the property unitPrice.
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	/**
	 * @return receiveMethod : set the property receiveMethod.
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}
	/**
	 * @param receiveMethod : return the property receiveMethod.
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}
	/**
	 * @return transportationRemark : set the property transportationRemark.
	 */
	public String getTransportationRemark() {
		return transportationRemark;
	}
	/**
	 * @param transportationRemark : return the property transportationRemark.
	 */
	public void setTransportationRemark(String transportationRemark) {
		this.transportationRemark = transportationRemark;
	}
	/**
	 * @return transportFee : set the property transportFee.
	 */
	public BigDecimal getTransportFee() {
		return transportFee;
	}
	/**
	 * @param transportFee : return the property transportFee.
	 */
	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}
	
	/**
	 * @return  the isInvalid
	 */
	public String getIsInvalid() {
		return isInvalid;
	}
	
	/**
	 * @param isInvalid the isInvalid to set
	 */
	public void setIsInvalid(String isInvalid) {
		if (isInvalid.equals("EFFECTIVE")){
			this.isInvalid = FossConstants.YES;
		}
		else{
			this.isInvalid = FossConstants.NO;
		}
		//this.isInvalid = isInvalid;
	}
	
	/**
	 * @return  the deliverDeptName
	 */
	public String getDeliverDeptName() {
		return deliverDeptName;
	}
	
	/**
	 * @param deliverDeptName the deliverDeptName to set
	 */
	public void setDeliverDeptName(String deliverDeptName) {
		this.deliverDeptName = deliverDeptName;
	}
	
	/**
	 * @return  the receiverCustomerTel
	 */
	public String getReceiverCustomerTel() {
		return receiverCustomerTel;
	}
	
	/**
	 * @param receiverCustomerTel the receiverCustomerTel to set
	 */
	public void setReceiverCustomerTel(String receiverCustomerTel) {
		this.receiverCustomerTel = receiverCustomerTel;
	}
	/**
	 * @return  the receiverCustomerProv
	 */
	public String getReceiverCustomerProv() {
		return receiverCustomerProv;
	}	
	/**
	 * @param receiverCustomerProv the receiverCustomerProv to set
	 */
	public void setReceiverCustomerProv(String receiverCustomerProv) {
		this.receiverCustomerProv = receiverCustomerProv;
	}
	/**
	 * @return  the receiverCustomerCity
	 */
	public String getReceiverCustomerCity() {
		return receiverCustomerCity;
	}
	/**
	 * @param receiverCustomerCity the receiverCustomerCity to set
	 */
	public void setReceiverCustomerCity(String receiverCustomerCity) {
		this.receiverCustomerCity = receiverCustomerCity;
	}
	/**
	 * @return  the receiverCustomerDist
	 */
	public String getReceiverCustomerDist() {
		return receiverCustomerDist;
	}
	/**
	 * @param receiverCustomerDist the receiverCustomerDist to set
	 */
	public void setReceiverCustomerDist(String receiverCustomerDist) {
		this.receiverCustomerDist = receiverCustomerDist;
	}
	
	public BigDecimal getValueAddFee() {
		return valueAddFee;
	}
	
	public void setValueAddFee(BigDecimal valueAddFee) {
		this.valueAddFee = valueAddFee;
	}
	
	public BigDecimal getPromotionsFee() {
		return promotionsFee;
	}
	
	public void setPromotionsFee(BigDecimal promotionsFee) {
		this.promotionsFee = promotionsFee;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getIsPdaCreate() {
		return isPdaCreate;
	}
	public void setIsPdaCreate(String isPdaCreate) {
		this.isPdaCreate = isPdaCreate;
	}
	public String getReceiverCustomerVillage() {
		return receiverCustomerVillage;
	}
	public void setReceiverCustomerVillage(String receiverCustomerVillage) {
		this.receiverCustomerVillage = receiverCustomerVillage;
	}
	public String getReceiverCustomerVillageCode() {
		return receiverCustomerVillageCode;
	}
	public void setReceiverCustomerVillageCode(String receiverCustomerVillageCode) {
		this.receiverCustomerVillageCode = receiverCustomerVillageCode;
	}	
}