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
public class ShortRentalMarkEntity extends BaseEntity {

	/**
	 * 序列号
	 */
	
	private static final long serialVersionUID = 2456342303170152913L;
	
	//约车编号
	private String inviteVehicleNo;
	
	//租车金额
	private BigDecimal rentalAmount;
	
	// 车牌号
	private String vehicleNo;
	
	//车辆状态
	private String vehicleState;
	
	//用车时间
	private Date useCareDate;
	
	// 目的站
	private String destination;
	
	//用车部门
	private String origOrgName;
	
	//租车编号
	private String temprentalMarkNo;
	
	//车辆任务ID，用于校验车辆是否重复标记
	private String truckTaskId;
	
	//出发部门编码
	private String origOrgCode;
	
	//到达部门编码
	private String destOrgCode;

	/**
	 * @return the truckTaskId
	 */
	public String getTruckTaskId() {
		return truckTaskId;
	}

	/**
	 * @param truckTaskId the truckTaskId to set
	 */
	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}

	/**
	 * @return the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * @param origOrgCode the origOrgCode to set
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * @return the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @param destOrgCode the destOrgCode to set
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return the inviteVehicleNo
	 */
	public String getInviteVehicleNo() {
		return inviteVehicleNo;
	}

	/**
	 * @param inviteVehicleNo the inviteVehicleNo to set
	 */
	public void setInviteVehicleNo(String inviteVehicleNo) {
		this.inviteVehicleNo = inviteVehicleNo;
	}

	/**
	 * @return the rentalAmount
	 */
	public BigDecimal getRentalAmount() {
		return rentalAmount;
	}

	/**
	 * @param rentalAmount the rentalAmount to set
	 */
	public void setRentalAmount(BigDecimal rentalAmount) {
		this.rentalAmount = rentalAmount;
	}

	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @return the vehicleState
	 */
	public String getVehicleState() {
		return vehicleState;
	}

	/**
	 * @param vehicleState the vehicleState to set
	 */
	public void setVehicleState(String vehicleState) {
		this.vehicleState = vehicleState;
	}

	/**
	 * @return the useCareDate
	 */
	public Date getUseCareDate() {
		return useCareDate;
	}

	/**
	 * @param useCareDate the useCareDate to set
	 */
	public void setUseCareDate(Date useCareDate) {
		this.useCareDate = useCareDate;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * @param origOrgName the origOrgName to set
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * @return the temprentalMarkNo
	 */
	public String getTemprentalMarkNo() {
		return temprentalMarkNo;
	}

	/**
	 * @param temprentalMarkNo the temprentalMarkNo to set
	 */
	public void setTemprentalMarkNo(String temprentalMarkNo) {
		this.temprentalMarkNo = temprentalMarkNo;
	}
	
//	String driverName;// 司机
//	String origOrgName;// 出发部门
//	String destOrgName;// 到达部门
//	BigDecimal weight;// 重量
//	BigDecimal volume;// 体积
//	String goodsName;// 货物名称
//	String packing;// 包装
//	String isDoorDeliver;// 是否上门接货
//	String customerName;// 发货客户名称
//	String sendAddress;// 发货地址
//	String pickUpWay;// 提货方式
//	String pickUpWayName;//提货方式名称
//	String receiptContacts;// 收货联系人
//	String receiptAddress;// 收货地址
//	Date billCreateTime;// 单据生成时间
//	String rentalUse;// 租车用途
//	Date   createDate;// 租车标记时间
//	String markDeptName;// 租车标记部门
//	String markOperator;// 租车标记操作人
//	String markDeptCode;//标记部门编码 
//	String driverCode;//司机编码
//	String origOrgCode;//出发部门编码
//	String destOrgCode;//到达部门编码
//	String iswaybill;//是否是快递
//	String consultPriceNo;//询价编号
//	String driver_mobile_phone;//约车司机电话
	
	
//	public String getDriver_mobile_phone() {
//		return driver_mobile_phone;
//	}
//	public void setDriver_mobile_phone(String driver_mobile_phone) {
//		this.driver_mobile_phone = driver_mobile_phone;
//	}
//	public String getIswaybill() {
//		return iswaybill;
//	}
//	public void setIswaybill(String iswaybill) {
//		this.iswaybill = iswaybill;
//	}
//	
//	public String getConsultPriceNo() {
//		return consultPriceNo;
//	}
//	public void setConsultPriceNo(String consultPriceNo) {
//		this.consultPriceNo = consultPriceNo;
//	}
//	public BigDecimal getRentalAmount() {
//		return rentalAmount;
//	}
//	public void setRentalAmount(BigDecimal rentalAmount) {
//		this.rentalAmount = rentalAmount;
//	}
//	public String getInviteVehicleNo() {
//		return inviteVehicleNo;
//	}
//	public void setInviteVehicleNo(String inviteVehicleNo) {
//		this.inviteVehicleNo = inviteVehicleNo;
//	}
//	public String getPickUpWayName() {
//		return pickUpWayName;
//	}
//	public void setPickUpWayName(String pickUpWayName) {
//		this.pickUpWayName = pickUpWayName;
//	}
//	public String getMarkDeptCode() {
//		return markDeptCode;
//	}
//	public void setMarkDeptCode(String markDeptCode) {
//		this.markDeptCode = markDeptCode;
//	}
//	public String getOrigOrgCode() {
//		return origOrgCode;
//	}
//	public void setOrigOrgCode(String origOrgCode) {
//		this.origOrgCode = origOrgCode;
//	}
//	public String getDestOrgCode() {
//		return destOrgCode;
//	}
//	public void setDestOrgCode(String destOrgCode) {
//		this.destOrgCode = destOrgCode;
//	}
//	public String getDriverCode() {
//		return driverCode;
//	}
//	public void setDriverCode(String driverCode) {
//		this.driverCode = driverCode;
//	}
//	public String getRentalNum() {
//		return rentalNum;
//	}
//	public void setRentalNum(String rentalNum) {
//		this.rentalNum = rentalNum;
//	}
//	public String getBillNo() {
//		return billNo;
//	}
//	public void setBillNo(String billNo) {
//		this.billNo = billNo;
//	}
//	public String getBillType() {
//		return billType;
//	}
//	public void setBillType(String billType) {
//		this.billType = billType;
//	}
//	public String getVehicleNo() {
//		return vehicleNo;
//	}
//	public void setVehicleNo(String vehicleNo) {
//		this.vehicleNo = vehicleNo;
//	}
//	public String getDriverName() {
//		return driverName;
//	}
//	public void setDriverName(String driverName) {
//		this.driverName = driverName;
//	}
//	public String getOrigOrgName() {
//		return origOrgName;
//	}
//	public void setOrigOrgName(String origOrgName) {
//		this.origOrgName = origOrgName;
//	}
//	public String getDestOrgName() {
//		return destOrgName;
//	}
//	public void setDestOrgName(String destOrgName) {
//		this.destOrgName = destOrgName;
//	}
//
//	public BigDecimal getWeight() {
//		return weight;
//	}
//	public void setWeight(BigDecimal weight) {
//		this.weight = weight;
//	}
//	public BigDecimal getVolume() {
//		return volume;
//	}
//	public void setVolume(BigDecimal volume) {
//		this.volume = volume;
//	}
//	public String getGoodsName() {
//		return goodsName;
//	}
//	public void setGoodsName(String goodsName) {
//		this.goodsName = goodsName;
//	}
//	public String getPacking() {
//		return packing;
//	}
//	public void setPacking(String packing) {
//		this.packing = packing;
//	}
//	public String getIsDoorDeliver() {
//		return isDoorDeliver;
//	}
//	public void setIsDoorDeliver(String isDoorDeliver) {
//		this.isDoorDeliver = isDoorDeliver;
//	}
//	public String getCustomerName() {
//		return customerName;
//	}
//	public void setCustomerName(String customerName) {
//		this.customerName = customerName;
//	}
//	public String getSendAddress() {
//		return sendAddress;
//	}
//	public void setSendAddress(String sendAddress) {
//		this.sendAddress = sendAddress;
//	}
//	public String getDestination() {
//		return destination;
//	}
//	public void setDestination(String destination) {
//		this.destination = destination;
//	}
//	public String getPickUpWay() {
//		return pickUpWay;
//	}
//	public void setPickUpWay(String pickUpWay) {
//		this.pickUpWay = pickUpWay;
//	}
//	public String getReceiptContacts() {
//		return receiptContacts;
//	}
//	public void setReceiptContacts(String receiptContacts) {
//		this.receiptContacts = receiptContacts;
//	}
//	public String getReceiptAddress() {
//		return receiptAddress;
//	}
//	public void setReceiptAddress(String receiptAddress) {
//		this.receiptAddress = receiptAddress;
//	}
//	public Date getBillCreateTime() {
//		return billCreateTime;
//	}
//	public void setBillCreateTime(Date billCreateTime) {
//		this.billCreateTime = billCreateTime;
//	}
//	public String getRentalUse() {
//		return rentalUse;
//	}
//	public void setRentalUse(String rentalUse) {
//		this.rentalUse = rentalUse;
//	}
//	public Date getCreateDate() {
//		return createDate;
//	}
//	public void setCreateDate(Date createDate) {
//		this.createDate = createDate;
//	}
//	public String getMarkDeptName() {
//		return markDeptName;
//	}
//	public void setMarkDeptName(String markDeptName) {
//		this.markDeptName = markDeptName;
//	}
//	public String getMarkOperator() {
//		return markOperator;
//	}
//	public void setMarkOperator(String markOperator) {
//		this.markOperator = markOperator;
//	}
//	
	
}
