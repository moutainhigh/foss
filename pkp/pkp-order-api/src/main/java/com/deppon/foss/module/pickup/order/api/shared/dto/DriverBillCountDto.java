package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DriverBillCountDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 司机工号
	 * */
	private String driverCode;
	
	/**
	 * 车牌号
	 * */
	private String vehicleNo;
	
	/**
	 * 应完成票数
	 * */
	private Integer receiveCount;
	/**
	 * 完成票数
	 * */
	private Integer completeCount;
	

	/**
	 * 重量和体积总和
	 */
	private String weightAndVolume;
	
	 /**
	  * 总体积
	  */
	private BigDecimal totalVolume;
	
	/**
	 * 重量
	 */
	private BigDecimal totalWeight;
	
	
	
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
	public Integer getReceiveCount() {
		return receiveCount;
	}
	public void setReceiveCount(Integer receiveCount) {
		this.receiveCount = receiveCount;
	}
	public Integer getCompleteCount() {
		return completeCount;
	}
	public void setCompleteCount(Integer completeCount) {
		this.completeCount = completeCount;
	}
	public String getWeightAndVolume() {
		return weightAndVolume;
	}
	public void setWeightAndVolume(String weightAndVolume) {
		this.weightAndVolume = weightAndVolume;
	}
	public BigDecimal getTotalVolume() {
		return totalVolume;
	}
	public void setTotalVolume(BigDecimal totalVolume) {
		this.totalVolume = totalVolume;
	}
	public BigDecimal getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}
	
}
