package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * PDA下拉电子运单数据所需Dto
 * @author Foss-105888-Zhangxingwang
 * @date 2014-9-3 20:14:46
 */
public class DispatchEWaybillDto implements Serializable {
	private static final long serialVersionUID = 1L;
	//走货路由信息
	private String originDeptCode;//出发部门
	
	private String originDeptName;//出发部门
	
	private String destDeptCode;	//目的站code
	
	private String destDeptName;	//目的站name
	
	private String leaveCity;		//第二城市
	
	private String secField;		//第二外场
	
	private String finalField;	//最终外场
	
	//货物信息
	private Integer pieces;			//件数
	
	private BigDecimal weight;			//重量
	
	private BigDecimal volume;			//体积
	
	private String remark;			//备注
	
	//增值服务信息
	private BigDecimal insuredAmount;	//保价金额
	
	private String reciveLoanType;	//代收货款类型
	
	private BigDecimal reviceMoneyAmount;//代收货款金额
	
	private String reviceLoadAccount;//代收货款账号
	
	private String returnBillingType;//签收返单
	
	//其他信息
	private String userCode;			// 工号
	
	private String truckCode;		// 车牌号
	
	private String pdaCode;			// PDA编号
	
	private String orderCode;		// 订单号
	
	private String waybillCode;		// 运单号
	
	private String sysID;			// 唯一标示符
	
	private Date sendTime;			// 发送时间
	
	private String orderType;		//订单类型(电子运单：E 普通订单：N)
	
	private String transType;		//运输性质（产品）
	
	private String takeType;			//提货方式
	
	private String payType;			//付款方式
	
	private BigDecimal freightFee;		//运费
	
	private String revenueName;		//收入部门名称
	
	private String revenueCode;		//收入部门编码
	
	private String couponNum;		//优惠券编码
	
	private String isCollectGps;	//是否采集Gps
	
	private Date billingTime;		//开单时间
	
	private String marketingInfo;	//营销活动编码
	
	private String bankTradeSerail;	//银行交易流水号
	
	//走货路径信息
	private String routeInfo;//走货路径信息D02/201-D03/201如果201没有，用*代替wstring 
	
	/**
	 * 是否打印五角星
	 */
	private String isStarFlag;
	
	/**
	 * 是否外发
	 */
	private String isPrintAt;
	
	/**
	 * 收货客户姓名
	 */
	private String addresseeName;
	
	/**
	 * 收货人地址
	 */
	private String addresseeAddr;
	
	/**
	 * 收货客户手机
	 */
	private String addresseeTel;
	
	/**
	 * 货物名称(品名) 
	 */
	private String goodsName;
	
	/**
	 * 发货人客户姓名
	 */
	private String senderName;
	
	/**
	 * 发货人地址
	 */
	private String senderAddr;
	
	/**
	 * 发货人手机号
	 */
	private String senderTel;
	
	/**
	 * 最早接货时间
	 */
	private Date firstAcctTime;
	/**
	 * 最晚接货时间
	 */
	private Date lastAcctTime;
	
	/**
	 * 订单渠道
	 */
	private String channelCode;
	
	/**
	 * 提货人手机号
	 */
	private String receiveMobilePhone;
	
	/**
	 * 提货人电话号码
	 */
	private String receivePhone;
	
	/**
	 * 提货人省份
	 */
	private String receiveProCode;

	/**
	 * 提货人城市
	 */
	private String receiveCityCode;
	

	/**
	 * 提货人区域
	 */
	private String receiveDistCode;

	/**
	 * 提货人具体地址
	 */
	private String receiveAddress;

	/**
	 * 发货人手机号
	 */
	private String deliveryMobilePhone;

	/**
	 * 发货人电话号码
	 */
	private String deliveryPhone;

	/**
	 * 发货人省份
	 */
	private String deliveryProCode;

	/**
	 * 发货人城市编码
	 */
	private String deliveryCityCode;

	/**
	 * 发货人地区编码
	 */
	private String deliveryDistCode;

	/**
	 * 发货人具体地址
	 */
	private String deliveryAddress;
	
	/**
	 * 出发城市
	 */
	private String departCityName;
	
	//get、set方法
	public String getOriginDeptCode() {
		return originDeptCode;
	}

	public void setOriginDeptCode(String originDeptCode) {
		this.originDeptCode = originDeptCode;
	}

	public String getDestDeptCode() {
		return destDeptCode;
	}

	public void setDestDeptCode(String destDeptCode) {
		this.destDeptCode = destDeptCode;
	}

	public String getDestDeptName() {
		return destDeptName;
	}

	public void setDestDeptName(String destDeptName) {
		this.destDeptName = destDeptName;
	}

	public String getLeaveCity() {
		return leaveCity;
	}
	
	public void setLeaveCity(String leaveCity) {
		this.leaveCity = leaveCity;
	}
	
	public String getSecField() {
		return secField;
	}

	public void setSecField(String secField) {
		this.secField = secField;
	}

	public String getFinalField() {
		return finalField;
	}

	public void setFinalField(String finalField) {
		this.finalField = finalField;
	}

	public Integer getPieces() {
		return pieces;
	}

	public void setPieces(Integer pieces) {
		this.pieces = pieces;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getInsuredAmount() {
		return insuredAmount;
	}

	public void setInsuredAmount(BigDecimal insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	public String getReciveLoanType() {
		return reciveLoanType;
	}

	public void setReciveLoanType(String reciveLoanType) {
		this.reciveLoanType = reciveLoanType;
	}

	public BigDecimal getReviceMoneyAmount() {
		return reviceMoneyAmount;
	}

	public void setReviceMoneyAmount(BigDecimal reviceMoneyAmount) {
		this.reviceMoneyAmount = reviceMoneyAmount;
	}

	public String getReviceLoadAccount() {
		return reviceLoadAccount;
	}

	public void setReviceLoadAccount(String reviceLoadAccount) {
		this.reviceLoadAccount = reviceLoadAccount;
	}

	public String getReturnBillingType() {
		return returnBillingType;
	}

	public void setReturnBillingType(String returnBillingType) {
		this.returnBillingType = returnBillingType;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getTruckCode() {
		return truckCode;
	}

	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

	public String getPdaCode() {
		return pdaCode;
	}

	public void setPdaCode(String pdaCode) {
		this.pdaCode = pdaCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getWaybillCode() {
		return waybillCode;
	}

	public void setWaybillCode(String waybillCode) {
		this.waybillCode = waybillCode;
	}

	public String getSysID() {
		return sysID;
	}

	public void setSysID(String sysID) {
		this.sysID = sysID;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTakeType() {
		return takeType;
	}

	public void setTakeType(String takeType) {
		this.takeType = takeType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public BigDecimal getFreightFee() {
		return freightFee;
	}

	public void setFreightFee(BigDecimal freightFee) {
		this.freightFee = freightFee;
	}

	public String getRevenueName() {
		return revenueName;
	}

	public void setRevenueName(String revenueName) {
		this.revenueName = revenueName;
	}

	public String getRevenueCode() {
		return revenueCode;
	}

	public void setRevenueCode(String revenueCode) {
		this.revenueCode = revenueCode;
	}

	public String getCouponNum() {
		return couponNum;
	}

	public void setCouponNum(String couponNum) {
		this.couponNum = couponNum;
	}

	public String getIsCollectGps() {
		return isCollectGps;
	}

	public void setIsCollectGps(String isCollectGps) {
		this.isCollectGps = isCollectGps;
	}

	public Date getBillingTime() {
		return billingTime;
	}

	public void setBillingTime(Date billingTime) {
		this.billingTime = billingTime;
	}
	
	public String getMarketingInfo() {
		return marketingInfo;
	}
	
	public void setMarketingInfo(String marketingInfo) {
		this.marketingInfo = marketingInfo;
	}
	public String getBankTradeSerail() {
		return bankTradeSerail;
	}

	public void setBankTradeSerail(String bankTradeSerail) {
		this.bankTradeSerail = bankTradeSerail;
	}
	
	public String getRouteInfo() {
		return routeInfo;
	}
	
	public void setRouteInfo(String routeInfo) {
		this.routeInfo = routeInfo;
	}
	
	public String getOriginDeptName() {
		return originDeptName;
	}
	
	public void setOriginDeptName(String originDeptName) {
		this.originDeptName = originDeptName;
	}

	public String getIsStarFlag() {
		return isStarFlag;
	}

	public void setIsStarFlag(String isStarFlag) {
		this.isStarFlag = isStarFlag;
	}

	public String getIsPrintAt() {
		return isPrintAt;
	}

	public void setIsPrintAt(String isPrintAt) {
		this.isPrintAt = isPrintAt;
	}

	public String getAddresseeName() {
		return addresseeName;
	}

	public void setAddresseeName(String addresseeName) {
		this.addresseeName = addresseeName;
	}

	public String getAddresseeAddr() {
		return addresseeAddr;
	}

	public void setAddresseeAddr(String addresseeAddr) {
		this.addresseeAddr = addresseeAddr;
	}

	public String getAddresseeTel() {
		return addresseeTel;
	}

	public void setAddresseeTel(String addresseeTel) {
		this.addresseeTel = addresseeTel;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderAddr() {
		return senderAddr;
	}

	public void setSenderAddr(String senderAddr) {
		this.senderAddr = senderAddr;
	}

	public String getSenderTel() {
		return senderTel;
	}

	public void setSenderTel(String senderTel) {
		this.senderTel = senderTel;
	}

	public Date getFirstAcctTime() {
		return firstAcctTime;
	}

	public void setFirstAcctTime(Date firstAcctTime) {
		this.firstAcctTime = firstAcctTime;
	}

	public Date getLastAcctTime() {
		return lastAcctTime;
	}

	public void setLastAcctTime(Date lastAcctTime) {
		this.lastAcctTime = lastAcctTime;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getReceiveMobilePhone() {
		return receiveMobilePhone;
	}

	public void setReceiveMobilePhone(String receiveMobilePhone) {
		this.receiveMobilePhone = receiveMobilePhone;
	}

	public String getReceivePhone() {
		return receivePhone;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}

	public String getReceiveProCode() {
		return receiveProCode;
	}

	public void setReceiveProCode(String receiveProCode) {
		this.receiveProCode = receiveProCode;
	}

	public String getReceiveCityCode() {
		return receiveCityCode;
	}

	public void setReceiveCityCode(String receiveCityCode) {
		this.receiveCityCode = receiveCityCode;
	}

	public String getReceiveDistCode() {
		return receiveDistCode;
	}

	public void setReceiveDistCode(String receiveDistCode) {
		this.receiveDistCode = receiveDistCode;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getDeliveryMobilePhone() {
		return deliveryMobilePhone;
	}

	public void setDeliveryMobilePhone(String deliveryMobilePhone) {
		this.deliveryMobilePhone = deliveryMobilePhone;
	}

	public String getDeliveryPhone() {
		return deliveryPhone;
	}

	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
	}

	public String getDeliveryProCode() {
		return deliveryProCode;
	}

	public void setDeliveryProCode(String deliveryProCode) {
		this.deliveryProCode = deliveryProCode;
	}

	public String getDeliveryCityCode() {
		return deliveryCityCode;
	}

	public void setDeliveryCityCode(String deliveryCityCode) {
		this.deliveryCityCode = deliveryCityCode;
	}

	public String getDeliveryDistCode() {
		return deliveryDistCode;
	}

	public void setDeliveryDistCode(String deliveryDistCode) {
		this.deliveryDistCode = deliveryDistCode;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public void setDepartCityName(String departCityName) {
		this.departCityName = departCityName;
	}
	
	public String getDepartCityName() {
		return departCityName;
	}
}