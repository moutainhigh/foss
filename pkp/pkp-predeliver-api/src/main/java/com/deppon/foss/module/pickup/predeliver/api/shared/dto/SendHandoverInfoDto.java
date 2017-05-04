package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SendHandoverInfoDto implements Serializable{
	private static final long serialVersionUID = 1L;
	//车牌号 
	private String vehicleNo;
	//配载车次号
	private String vehicleassembleNo;
	//交接单号
	private String handoverNo;
	//派送（票数）
	private Integer deliverQty;
	//派送（重量）
	private BigDecimal deliverWeight;
	//派送（体积）
	private BigDecimal deliverVolume;
	//出发部门
	private String origOrgName;
	//到达部门
	private String destOrgName;
	//出发时间 
	private Date leaveTime;
	//到达时间
	private Date arriveTime;
	//预计到达时间
	private Date preArriveTime;
	//运单号  
	private String waybillNo;
	//开单件数
	private Integer goodsQty;
	//配载件数
	private Integer assembleQty;
	//运输性质
	private String productCode;
	//提货方式
	private String receiveMethod;
	//行政区
	private String distName;

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleassembleNo() {
		return vehicleassembleNo;
	}

	public void setVehicleassembleNo(String vehicleassembleNo) {
		this.vehicleassembleNo = vehicleassembleNo;
	}

	public String getHandoverNo() {
		return handoverNo;
	}

	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	public Integer getDeliverQty() {
		return deliverQty;
	}

	public void setDeliverQty(Integer deliverQty) {
		this.deliverQty = deliverQty;
	}

	public BigDecimal getDeliverWeight() {
		return deliverWeight;
	}

	public void setDeliverWeight(BigDecimal deliverWeight) {
		this.deliverWeight = deliverWeight;
	}

	public BigDecimal getDeliverVolume() {
		return deliverVolume;
	}

	public void setDeliverVolume(BigDecimal deliverVolume) {
		this.deliverVolume = deliverVolume;
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

	public Date getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Date getPreArriveTime() {
		return preArriveTime;
	}

	public void setPreArriveTime(Date preArriveTime) {
		this.preArriveTime = preArriveTime;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Integer getGoodsQty() {
		return goodsQty;
	}

	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	public Integer getAssembleQty() {
		return assembleQty;
	}

	public void setAssembleQty(Integer assembleQty) {
		this.assembleQty = assembleQty;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getDistName() {
		return distName;
	}

	public void setDistName(String distName) {
		this.distName = distName;
	}
	
	
}
