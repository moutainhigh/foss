package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @title: RentalMarkEntity 
 * @description： 临时租车标记查询数据
 * @author： wuyaqing
 * @date： 2014-5-16 上午10:48:41
 */
public class RentalMarkEntity extends BaseEntity {

	/**
	 * 序列号
	 */
	
	private static final long serialVersionUID = 2456342303170152913L;

	String rentalNum;// 租车编号
	String billNo;// 单号
	String billType;// 单据类型
	String vehicleNo;// 车牌号
	String driverName;// 司机
	String origOrgName;// 出发部门
	String destOrgName;// 到达部门
	BigDecimal weight;// 重量
	BigDecimal volume;// 体积
	String goodsName;// 货物名称
	String packing;// 包装
	String isDoorDeliver;// 是否上门接货
	String customerName;// 发货客户名称
	String sendAddress;// 发货地址
	String destination;// 目的站
	String pickUpWay;// 提货方式
	String pickUpWayName;//提货方式名称
	String receiptContacts;// 收货联系人
	String receiptAddress;// 收货地址
	Date billCreateTime;// 单据生成时间
	String rentalUse;// 租车用途
	Date   createDate;// 租车标记时间
	String markDeptName;// 租车标记部门
	String markOperator;// 租车标记操作人
	String markDeptCode;//标记部门编码 
	String driverCode;//司机编码
	String origOrgCode;//出发部门编码
	String destOrgCode;//到达部门编码
	String inviteVehicleNo;//约车编号
	BigDecimal rentalAmount;//租车金额
	String iswaybill;//是否是快递
	String consultPriceNo;//询价编号
	String driver_mobile_phone;//约车司机电话
	
	
	public String getDriver_mobile_phone() {
		return driver_mobile_phone;
	}
	public void setDriver_mobile_phone(String driver_mobile_phone) {
		this.driver_mobile_phone = driver_mobile_phone;
	}
	public String getIswaybill() {
		return iswaybill;
	}
	public void setIswaybill(String iswaybill) {
		this.iswaybill = iswaybill;
	}
	
	public String getConsultPriceNo() {
		return consultPriceNo;
	}
	public void setConsultPriceNo(String consultPriceNo) {
		this.consultPriceNo = consultPriceNo;
	}
	public BigDecimal getRentalAmount() {
		return rentalAmount;
	}
	public void setRentalAmount(BigDecimal rentalAmount) {
		this.rentalAmount = rentalAmount;
	}
	public String getInviteVehicleNo() {
		return inviteVehicleNo;
	}
	public void setInviteVehicleNo(String inviteVehicleNo) {
		this.inviteVehicleNo = inviteVehicleNo;
	}
	public String getPickUpWayName() {
		return pickUpWayName;
	}
	public void setPickUpWayName(String pickUpWayName) {
		this.pickUpWayName = pickUpWayName;
	}
	public String getMarkDeptCode() {
		return markDeptCode;
	}
	public void setMarkDeptCode(String markDeptCode) {
		this.markDeptCode = markDeptCode;
	}
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	public String getDestOrgCode() {
		return destOrgCode;
	}
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getRentalNum() {
		return rentalNum;
	}
	public void setRentalNum(String rentalNum) {
		this.rentalNum = rentalNum;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getOrigOrgName() {
		return origOrgName;
	}
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}
	public String getDestOrgName() {
		return destOrgName;
	}
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
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
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getPacking() {
		return packing;
	}
	public void setPacking(String packing) {
		this.packing = packing;
	}
	public String getIsDoorDeliver() {
		return isDoorDeliver;
	}
	public void setIsDoorDeliver(String isDoorDeliver) {
		this.isDoorDeliver = isDoorDeliver;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSendAddress() {
		return sendAddress;
	}
	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getPickUpWay() {
		return pickUpWay;
	}
	public void setPickUpWay(String pickUpWay) {
		this.pickUpWay = pickUpWay;
	}
	public String getReceiptContacts() {
		return receiptContacts;
	}
	public void setReceiptContacts(String receiptContacts) {
		this.receiptContacts = receiptContacts;
	}
	public String getReceiptAddress() {
		return receiptAddress;
	}
	public void setReceiptAddress(String receiptAddress) {
		this.receiptAddress = receiptAddress;
	}
	public Date getBillCreateTime() {
		return billCreateTime;
	}
	public void setBillCreateTime(Date billCreateTime) {
		this.billCreateTime = billCreateTime;
	}
	public String getRentalUse() {
		return rentalUse;
	}
	public void setRentalUse(String rentalUse) {
		this.rentalUse = rentalUse;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getMarkDeptName() {
		return markDeptName;
	}
	public void setMarkDeptName(String markDeptName) {
		this.markDeptName = markDeptName;
	}
	public String getMarkOperator() {
		return markOperator;
	}
	public void setMarkOperator(String markOperator) {
		this.markOperator = markOperator;
	}
	
	
}
