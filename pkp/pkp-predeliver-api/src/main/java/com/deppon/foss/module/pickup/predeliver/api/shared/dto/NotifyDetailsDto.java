package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @功能 通知客户明细查询条件Dto
 * @author Foss-105888-Zhangxingwang
 * @date 2013-12-27 9:22:18
 *
 */
public class NotifyDetailsDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//运单号
	private String waybillNo;
	
	//通知状态
	private String noticeStatus;
	
	//提货方式
	private String deliverMethod;
	
	//库存状态
	private String stockStatus;
	
	//入库时间
	private Date inStockTime;
	
	//预计送货时间
	private Date planToSendTime;
	
	//收货人
	private String receivecustomerName;
	
	//收货人地址
	private String delivercustomerAddr;
	
	//收货人电话
	private String customerTelePhone;
	
	//收货人手机
	private String customerMobile;
	
	//总件数
	private Integer goodsTotal;
	
	//重量
	private BigDecimal weight;
	
	//体积
	private BigDecimal volume;
	
	//到付金额
	private BigDecimal arriveFee;
	
	//提前通知
	private String deliverRequire;
	
	//创建人
	private String creatorName;
	
	//创建时间
	private Date createTime;
	
	/**
	 * 收货国家
	 */
	private String receiveCustomerNationCode;

	/**
	 * 收货省份
	 */
	private String receiveCustomerProvCode;

	/**
	 * 收货市
	 */
	private String receiveCustomerCityCode;

	/**
	 * 收货区22
	 */
	private String receiveCustomerDistCode;

	/**
	 * 收货具体地址
	 */
	private String receiveCustomerAddress;
	
	/**
	 * 收货具体地址备注
	 */
	private String receiveCustomerAddressNote;

	// DMANA-3694 增加6个字段：到达时间、送货司机、司机手机、签收时间、出发部门、到达部门
	/**
	 * 到达时间
	 */
	private Date arriveTime;
	/**
	 * 签收时间
	 */
	private Date signTime;
	/**
	 * 出发部门
	 */
	private String receiveOrgName;
	/**
	 * 到达部门
	 */
	private String customerPickupOrgName;
	/**
	 * 送货司机
	 */
	private String driverName;
	/**
	 * 司机手机
	 */
	private String driverPhone;

	/**
	 * 司机Code
	 */
	private String driverCode;
	/**
	 * 车牌号
	 */
	private String vehicleNo;

	/**
	 * 是否快递
	 */
	private String isExpress;

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

	public String getDeliverMethod() {
		return deliverMethod;
	}

	public void setDeliverMethod(String deliverMethod) {
		this.deliverMethod = deliverMethod;
	}

	public String getStockStatus() {
		return stockStatus;
	}

	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	public Date getInStockTime() {
		return inStockTime;
	}

	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}

	public Date getPlanToSendTime() {
		return planToSendTime;
	}

	public void setPlanToSendTime(Date planToSendTime) {
		this.planToSendTime = planToSendTime;
	}

	public String getDelivercustomerAddr() {
		return delivercustomerAddr;
	}

	public void setDelivercustomerAddr(String delivercustomerAddr) {
		this.delivercustomerAddr = delivercustomerAddr;
	}

	public String getCustomerTelePhone() {
		return customerTelePhone;
	}

	public void setCustomerTelePhone(String customerTelePhone) {
		this.customerTelePhone = customerTelePhone;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Integer getGoodsTotal() {
		return goodsTotal;
	}

	public void setGoodsTotal(Integer goodsTotal) {
		this.goodsTotal = goodsTotal;
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

	public BigDecimal getArriveFee() {
		return arriveFee;
	}

	public void setArriveFee(BigDecimal arriveFee) {
		this.arriveFee = arriveFee;
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

	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	public String getDeliverRequire() {
		return deliverRequire;
	}

	public void setDeliverRequire(String deliverRequire) {
		this.deliverRequire = deliverRequire;
	}

	public String getReceivecustomerName() {
		return receivecustomerName;
	}

	public void setReceivecustomerName(String receivecustomerName) {
		this.receivecustomerName = receivecustomerName;
	}

	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}

	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}
}
