package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EWaybillExpressPdaDto implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 * 优惠金额
	 */
	private BigDecimal discountAmount;
	
	
	
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
	 * 代收货款类型
	 */
	private String reciveLoanType;
	
	/**
	 * 运单类型
	 */
	private String waybillType;

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getBillOrgCode() {
		return billOrgCode;
	}

	public void setBillOrgCode(String billOrgCode) {
		this.billOrgCode = billOrgCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getWaybillPendingId() {
		return waybillPendingId;
	}

	public void setWaybillPendingId(String waybillPendingId) {
		this.waybillPendingId = waybillPendingId;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getStartOrg() {
		return startOrg;
	}

	public void setStartOrg(String startOrg) {
		this.startOrg = startOrg;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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

	public BigDecimal getWoodVolume() {
		return woodVolume;
	}

	public void setWoodVolume(BigDecimal woodVolume) {
		this.woodVolume = woodVolume;
	}

	public String getWoodSize() {
		return woodSize;
	}

	public void setWoodSize(String woodSize) {
		this.woodSize = woodSize;
	}

	public BigDecimal getWoodBoxVolume() {
		return woodBoxVolume;
	}

	public void setWoodBoxVolume(BigDecimal woodBoxVolume) {
		this.woodBoxVolume = woodBoxVolume;
	}

	public String getWoodBoxSize() {
		return woodBoxSize;
	}

	public void setWoodBoxSize(String woodBoxSize) {
		this.woodBoxSize = woodBoxSize;
	}

	public Integer getGoodsQty() {
		return goodsQty;
	}

	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	public Integer getPaper() {
		return paper;
	}

	public void setPaper(Integer paper) {
		this.paper = paper;
	}

	public Integer getWood() {
		return wood;
	}

	public void setWood(Integer wood) {
		this.wood = wood;
	}

	public Integer getFibre() {
		return fibre;
	}

	public void setFibre(Integer fibre) {
		this.fibre = fibre;
	}

	public Integer getSalver() {
		return salver;
	}

	public void setSalver(Integer salver) {
		this.salver = salver;
	}

	public Integer getMembrane() {
		return membrane;
	}

	public void setMembrane(Integer membrane) {
		this.membrane = membrane;
	}

	public String getOtherPackageType() {
		return otherPackageType;
	}

	public void setOtherPackageType(String otherPackageType) {
		this.otherPackageType = otherPackageType;
	}

	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}

	public String getPaidMethod() {
		return paidMethod;
	}

	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	public String getIsWood() {
		return isWood;
	}

	public void setIsWood(String isWood) {
		this.isWood = isWood;
	}

	public List<ValueAddServiceDto> getValueAddServiceDtoList() {
		return valueAddServiceDtoList;
	}

	public void setValueAddServiceDtoList(
			List<ValueAddServiceDto> valueAddServiceDtoList) {
		this.valueAddServiceDtoList = valueAddServiceDtoList;
	}

	public String getBillUserNo() {
		return billUserNo;
	}

	public void setBillUserNo(String billUserNo) {
		this.billUserNo = billUserNo;
	}

	public String getPdaNo() {
		return pdaNo;
	}

	public void setPdaNo(String pdaNo) {
		this.pdaNo = pdaNo;
	}

	public String getLicensePlateNum() {
		return licensePlateNum;
	}

	public void setLicensePlateNum(String licensePlateNum) {
		this.licensePlateNum = licensePlateNum;
	}

	public Date getBillStart() {
		return billStart;
	}

	public void setBillStart(Date billStart) {
		this.billStart = billStart;
	}

	public String getDiscountNo() {
		return discountNo;
	}

	public void setDiscountNo(String discountNo) {
		this.discountNo = discountNo;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public BigDecimal getActualFee() {
		return actualFee;
	}

	public void setActualFee(BigDecimal actualFee) {
		this.actualFee = actualFee;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getReturnBillType() {
		return returnBillType;
	}

	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
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

	public String getSendEmployeeCode() {
		return sendEmployeeCode;
	}

	public void setSendEmployeeCode(String sendEmployeeCode) {
		this.sendEmployeeCode = sendEmployeeCode;
	}

	public String getNeedDepponCustomerCode() {
		return needDepponCustomerCode;
	}

	public void setNeedDepponCustomerCode(String needDepponCustomerCode) {
		this.needDepponCustomerCode = needDepponCustomerCode;
	}

	public String getIsSMS() {
		return isSMS;
	}

	public void setIsSMS(String isSMS) {
		this.isSMS = isSMS;
	}

	public String getSpecialOffer() {
		return specialOffer;
	}

	public void setSpecialOffer(String specialOffer) {
		this.specialOffer = specialOffer;
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
}