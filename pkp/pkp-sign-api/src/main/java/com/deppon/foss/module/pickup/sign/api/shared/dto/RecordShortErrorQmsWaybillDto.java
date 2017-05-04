/**
 * 快递内物短少差错上报dto
 */
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @clasaName:com.deppon.foss.module.pickup.sign.api.shared.dto.RecordShortErrorQmsWaybillDto
 * @author: foss-bieyexiong
 * @description: foss记录内物短少差错 上报QMS
 * @date:2015年4月7日 下午16:09:03
 */
public class RecordShortErrorQmsWaybillDto implements Serializable{
	
	/**
	 * 类的序列号
	 */
	private static final long serialVersionUID = -1455208989944394954L;

	/**
	 * 快递差错-短少量
	 */
	private String shortAmount;
	
	/**
	 * 零担差错-短少量
	 */
    private String shortNum;
	
	/** 
	 * 快递差错-短少流水号
	 */ 
	private String innerShortSerialCode;
	
	/**
	 * 零担差错-短少流水号
	 */
	private String goodsLoseFlowcode;
	
	/**
	 * 快递差错-外包装是否完好
	 */
	private String packagingOk;
	
	/**
	 * 零担差错-外包装是否完好
	 */
	private String packageIsGood;
	
	/**
	 * 快递差错-运输类型
	 */
	private String transportType;
	
	/**
	 * 零担差错-运输类型
	 */
	private String transType;
	
	/**
	 * 快递差错-返单类型
	 */
	private String backBillingType;
	
	/**
	 * 零担差错-返单类型
	 */
	private String returnBillType;
	
	/**
	 * 快递差错-托运人(发货客户联系人)
	 */
	private String consignor;
	
	/**
	 * 零担差错-托运人(发货客户联系人)
	 */
	private String shipper;
	
	/**
	 * 快递差错-运输性质
	 */
	private String transportProperties;
	
	/**
	 * 零担差错-运输性质
	 */
	private String transNature;

	/**
	 * 收货人电话
	 */
	private String receiverPhone;
	
	/**
	 * 快递差错-提货方式
	 */
	private String deliveryMethods;
	
	/**
	 * 零担差错-提货方式
	 */
	private String pickUpType;
	
	/**
	 * 快递差错-储运事项
	 */
	private String storageItems;
	
	/**
	 * 零担差错-储运事项
	 */
	private String storageTransport;
	
	/**
	 * 快递差错-重量
	 */
	private BigDecimal weight;
	
	/**
	 * 零担差错-重量
	 */
	private BigDecimal sumWeight;
	
	/**
	 * 快递差错-体积
	 */
	private BigDecimal volume;
	
	/**
	 * 零担差错-体积
	 */
	private BigDecimal sumVolume;
	
	/**
	 * 快递差错-件数
	 */
	private Integer fnumber;
	
	/**
	 * 零担差错-件数
	 */
	private Integer sumNumber;
	
	/**
	 * 货物名称
	 */
	private String goodsName;
	
	/**
	 * 快递差错-发货时间(开单时间)
	 */
	private String deliveryTime;
	
	/**
	 * 零担差错-发货时间(开单时间)
	 */
	private String sendGoodsTime;
	
	/**
	 * 到达部门
	 */
	private String arriveDeptCode;
	
	/**
	 * 到达部门名字
	 */
	private String arriveDeptName;
	
	/**
	 * 快递差错-收货人(收货客户联系人)
	 */
	private String consignee;
	
	/**
	 * 零担差错-收货人(收货客户联系人)
	 */
	private String receiverName;
	
	/**
	 * 快递差错-(开单)付款方式
	 */
	private String paymentMethod;
	
	/**
	 * 零担差错-(开单)付款方式
	 */
	private String payType;
	
	/**
	 * 快递差错-保险金额(保价声明价值)
	 */
	private BigDecimal insuranceAmount;
	
	/**
	 * 零担差错-保险金额(保价声明价值)
	 */
	private BigDecimal safeMoney;
	
	/**
	 * 货物包装
	 */
	private String goodsPackage;
	
	/**
	 * 快递差错-运费总额
	 */
	private BigDecimal totalFreight;
	
	/**
	 * 零担差错-运费总额
	 */
	private BigDecimal freightSumFee;
	
	/**
	 * 开单部门
	 */
	private String billingDeptCode;
	
	/**
	 * 开单部门名字
	 */
	private String billingDeptName;
	
	/**
	 * 快递差错-经手部门
	 */
	private String handedDeptCode;
	
	/**
	 * 零担差错-经手部门
	 */
	private String goodsHandleDeptCode;
	
	/**
	 * 快递差错-经手部门名称
	 */
	private String handedDeptName;
	
	/**
	 * 零担差错-经手部门名称
	 */
	private String goodsHandleDeptName;
	
	/**
	 * 是否集中接货
	 */
	private String isConcentReceiving;
	
	/**
	 * 责任部门
	 */
	private String respDeptCode;
	
	/**
	 * 责任部门名称
	 */
	private String respDeptName;
	
	/**
	 * 责任部门负责人
	 */
	private String respDeptResperCode;
	
	/**
	 * 责任部门负责人名称
	 */
	private String respDeptResperName; 
	
	/**
	 * 责任人
	 */
	private String respEmpCode;
	
	/**
	 * 责任人姓名
	 */
	private String respEmpName;
	
	/**
	 * 快递差错-车牌号
	 */
	private String licensePlateNumber;
	
	/**
	 * 零担差错-车牌号
	 */
	private String carCode;
	
	/**
	 * 快递差错-交接单号
	 */
	private String eir;
	
	/**
	 * 零担差错-交接单号
	 */
	private String transferBill;
	
	/**
	 * 事件经过
	 */
	private String incident;

	public String getShortAmount() {
		return shortAmount;
	}

	public void setShortAmount(String shortAmount) {
		this.shortAmount = shortAmount;
	}

	public String getInnerShortSerialCode() {
		return innerShortSerialCode;
	}

	public void setInnerShortSerialCode(String innerShortSerialCode) {
		this.innerShortSerialCode = innerShortSerialCode;
	}

	public String getPackagingOk() {
		return packagingOk;
	}

	public void setPackagingOk(String packagingOk) {
		this.packagingOk = packagingOk;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getBackBillingType() {
		return backBillingType;
	}

	public void setBackBillingType(String backBillingType) {
		this.backBillingType = backBillingType;
	}

	public String getConsignor() {
		return consignor;
	}

	public void setConsignor(String consignor) {
		this.consignor = consignor;
	}

	public String getTransportProperties() {
		return transportProperties;
	}

	public void setTransportProperties(String transportProperties) {
		this.transportProperties = transportProperties;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getDeliveryMethods() {
		return deliveryMethods;
	}

	public void setDeliveryMethods(String deliveryMethods) {
		this.deliveryMethods = deliveryMethods;
	}

	public String getStorageItems() {
		return storageItems;
	}

	public void setStorageItems(String storageItems) {
		this.storageItems = storageItems;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public Integer getFnumber() {
		return fnumber;
	}

	public void setFnumber(Integer fnumber) {
		this.fnumber = fnumber;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getArriveDeptCode() {
		return arriveDeptCode;
	}

	public void setArriveDeptCode(String arriveDeptCode) {
		this.arriveDeptCode = arriveDeptCode;
	}

	public String getArriveDeptName() {
		return arriveDeptName;
	}

	public void setArriveDeptName(String arriveDeptName) {
		this.arriveDeptName = arriveDeptName;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public BigDecimal getTotalFreight() {
		return totalFreight;
	}

	public void setTotalFreight(BigDecimal totalFreight) {
		this.totalFreight = totalFreight;
	}

	public String getBillingDeptCode() {
		return billingDeptCode;
	}

	public void setBillingDeptCode(String billingDeptCode) {
		this.billingDeptCode = billingDeptCode;
	}

	public String getBillingDeptName() {
		return billingDeptName;
	}

	public void setBillingDeptName(String billingDeptName) {
		this.billingDeptName = billingDeptName;
	}

	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}

	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}

	public String getEir() {
		return eir;
	}

	public void setEir(String eir) {
		this.eir = eir;
	}

	public String getIncident() {
		return incident;
	}

	public void setIncident(String incident) {
		this.incident = incident;
	}

	public String getHandedDeptCode() {
		return handedDeptCode;
	}

	public void setHandedDeptCode(String handedDeptCode) {
		this.handedDeptCode = handedDeptCode;
	}

	public String getHandedDeptName() {
		return handedDeptName;
	}

	public void setHandedDeptName(String handedDeptName) {
		this.handedDeptName = handedDeptName;
	}

	public String getRespDeptResperCode() {
		return respDeptResperCode;
	}

	public void setRespDeptResperCode(String respDeptResperCode) {
		this.respDeptResperCode = respDeptResperCode;
	}

	public String getRespDeptResperName() {
		return respDeptResperName;
	}

	public void setRespDeptResperName(String respDeptResperName) {
		this.respDeptResperName = respDeptResperName;
	}

	public String getRespDeptCode() {
		return respDeptCode;
	}

	public void setRespDeptCode(String respDeptCode) {
		this.respDeptCode = respDeptCode;
	}

	public String getRespDeptName() {
		return respDeptName;
	}

	public void setRespDeptName(String respDeptName) {
		this.respDeptName = respDeptName;
	}

	public String getGoodsLoseFlowcode() {
		return goodsLoseFlowcode;
	}

	public void setGoodsLoseFlowcode(String goodsLoseFlowcode) {
		this.goodsLoseFlowcode = goodsLoseFlowcode;
	}

	public String getPackageIsGood() {
		return packageIsGood;
	}

	public void setPackageIsGood(String packageIsGood) {
		this.packageIsGood = packageIsGood;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getReturnBillType() {
		return returnBillType;
	}

	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getTransNature() {
		return transNature;
	}

	public void setTransNature(String transNature) {
		this.transNature = transNature;
	}

	public String getPickUpType() {
		return pickUpType;
	}

	public void setPickUpType(String pickUpType) {
		this.pickUpType = pickUpType;
	}

	public String getStorageTransport() {
		return storageTransport;
	}

	public void setStorageTransport(String storageTransport) {
		this.storageTransport = storageTransport;
	}

	public BigDecimal getSumWeight() {
		return sumWeight;
	}

	public void setSumWeight(BigDecimal sumWeight) {
		this.sumWeight = sumWeight;
	}

	public BigDecimal getSumVolume() {
		return sumVolume;
	}

	public void setSumVolume(BigDecimal sumVolume) {
		this.sumVolume = sumVolume;
	}

	public Integer getSumNumber() {
		return sumNumber;
	}

	public void setSumNumber(Integer sumNumber) {
		this.sumNumber = sumNumber;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public BigDecimal getSafeMoney() {
		return safeMoney;
	}

	public void setSafeMoney(BigDecimal safeMoney) {
		this.safeMoney = safeMoney;
	}

	public BigDecimal getFreightSumFee() {
		return freightSumFee;
	}

	public void setFreightSumFee(BigDecimal freightSumFee) {
		this.freightSumFee = freightSumFee;
	}

	public String getGoodsHandleDeptCode() {
		return goodsHandleDeptCode;
	}

	public void setGoodsHandleDeptCode(String goodsHandleDeptCode) {
		this.goodsHandleDeptCode = goodsHandleDeptCode;
	}

	public String getGoodsHandleDeptName() {
		return goodsHandleDeptName;
	}

	public void setGoodsHandleDeptName(String goodsHandleDeptName) {
		this.goodsHandleDeptName = goodsHandleDeptName;
	}

	public String getIsConcentReceiving() {
		return isConcentReceiving;
	}

	public void setIsConcentReceiving(String isConcentReceiving) {
		this.isConcentReceiving = isConcentReceiving;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getTransferBill() {
		return transferBill;
	}

	public void setTransferBill(String transferBill) {
		this.transferBill = transferBill;
	}

	public String getShortNum() {
		return shortNum;
	}

	public void setShortNum(String shortNum) {
		this.shortNum = shortNum;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getSendGoodsTime() {
		return sendGoodsTime;
	}

	public void setSendGoodsTime(String sendGoodsTime) {
		this.sendGoodsTime = sendGoodsTime;
	}

	public String getRespEmpCode() {
		return respEmpCode;
	}

	public void setRespEmpCode(String respEmpCode) {
		this.respEmpCode = respEmpCode;
	}

	public String getRespEmpName() {
		return respEmpName;
	}

	public void setRespEmpName(String respEmpName) {
		this.respEmpName = respEmpName;
	}
	
}
