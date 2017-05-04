package com.deppon.foss.module.transfer.lostwarning.api.shared.dto;


/**
 * 运单信息
 * 
 * 项目名称：tfr-lostwarning-api
 * 
 * 类名称：WayBillInfoDto
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-6-15 下午4:30:21
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class WayBillInfoDto {

	// 运单号
	private String waybillNum;

	// 收货部门编码
	private String takeOverDeptCode;

	// 收货部门名称
	private String takeOverDeptName;

	// 到达部门编码
	private String arriveDeptCode;

	// 到达部门名称
	private String arriveDeptName;

	// 运输性质
	private String transNature;

	// 发货客户名
	private String sendClientName;

	// 发货客户编码
	private String sendClientCode;

	// 收货客户名称
	private String takeOverClientName;

	// 收货客户编码
	private String takeOverClientCode;

	// 提货方式
	private String pickUpType;
	
	// 付款方式
	private String payType;
	
	// 发货客户手机
	private String sendClientMobile;
	
	// 开单部门编码
	private String billingDeptCode;
	
	// 开单部门名称
	private String billingDeptName;
	
	// 货物名称
	private String goodsName;
	
	// 货物重量
	private double weight;
	
	// 货物体积
	private double volume;
	
	// 货物包装
	private String goodsPackage;
	
	// 货物件数
	private String goodsNum;
	
	// 开单时
	private String billingTime;
	
	// 开单金
	private String billingFee;
	
	// 保价金
	private String safeMoney;
	
	// 代收货款
	private String agentFee;
	
	// 返单类
	private String returnBillType;
	
	// 储运事项
	private String storageTransport;
	
	// 是否为快递  Y是 N否
	private String is_express;
	
	//是否集中接货 Y是  N否
	private String pickup_centralized;
	
	//提货网点
	private String customerPickupOrgCode;

	public String getWaybillNum() {
		return waybillNum;
	}

	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}

	public String getTakeOverDeptCode() {
		return takeOverDeptCode;
	}

	public void setTakeOverDeptCode(String takeOverDeptCode) {
		this.takeOverDeptCode = takeOverDeptCode;
	}

	public String getTakeOverDeptName() {
		return takeOverDeptName;
	}

	public void setTakeOverDeptName(String takeOverDeptName) {
		this.takeOverDeptName = takeOverDeptName;
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

	public String getTransNature() {
		return transNature;
	}

	public void setTransNature(String transNature) {
		this.transNature = transNature;
	}

	public String getSendClientName() {
		return sendClientName;
	}

	public void setSendClientName(String sendClientName) {
		this.sendClientName = sendClientName;
	}

	public String getSendClientCode() {
		return sendClientCode;
	}

	public void setSendClientCode(String sendClientCode) {
		this.sendClientCode = sendClientCode;
	}

	public String getTakeOverClientName() {
		return takeOverClientName;
	}

	public void setTakeOverClientName(String takeOverClientName) {
		this.takeOverClientName = takeOverClientName;
	}

	public String getTakeOverClientCode() {
		return takeOverClientCode;
	}

	public void setTakeOverClientCode(String takeOverClientCode) {
		this.takeOverClientCode = takeOverClientCode;
	}

	public String getPickUpType() {
		return pickUpType;
	}

	public void setPickUpType(String pickUpType) {
		this.pickUpType = pickUpType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getSendClientMobile() {
		return sendClientMobile;
	}

	public void setSendClientMobile(String sendClientMobile) {
		this.sendClientMobile = sendClientMobile;
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

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public String getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getBillingTime() {
		return billingTime;
	}

	public void setBillingTime(String billingTime) {
		this.billingTime = billingTime;
	}

	public String getBillingFee() {
		return billingFee;
	}

	public void setBillingFee(String billingFee) {
		this.billingFee = billingFee;
	}

	public String getSafeMoney() {
		return safeMoney;
	}

	public void setSafeMoney(String safeMoney) {
		this.safeMoney = safeMoney;
	}

	public String getAgentFee() {
		return agentFee;
	}

	public void setAgentFee(String agentFee) {
		this.agentFee = agentFee;
	}

	public String getReturnBillType() {
		return returnBillType;
	}

	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	public String getStorageTransport() {
		return storageTransport;
	}

	public void setStorageTransport(String storageTransport) {
		this.storageTransport = storageTransport;
	}

	public String getIs_express() {
		return is_express;
	}

	public void setIs_express(String is_express) {
		this.is_express = is_express;
	}

	public String getPickup_centralized() {
		return pickup_centralized;
	}

	public void setPickup_centralized(String pickup_centralized) {
		this.pickup_centralized = pickup_centralized;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}
	
}
