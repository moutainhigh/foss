package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.math.BigDecimal;

public class MultiCarTakeGoodsDto {

	
	private String vehicleNo;//租车编号
	private BigDecimal rentalAmount;//金额
	private BigDecimal kmsNum;//公里数
	private String departureCode;//出发部门
	private String destinationCode;//目的部门
	private String departureName;
	private String destinationName;
	
	
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	public BigDecimal getRentalAmount() {
		return rentalAmount;
	}
	public void setRentalAmount(BigDecimal rentalAmount) {
		this.rentalAmount = rentalAmount;
	}
	public BigDecimal getKmsNum() {
		return kmsNum;
	}
	public void setKmsNum(BigDecimal kmsNum) {
		this.kmsNum = kmsNum;
	}
	public String getDepartureCode() {
		return departureCode;
	}
	public void setDepartureCode(String departureCode) {
		this.departureCode = departureCode;
	}
	public String getDestinationCode() {
		return destinationCode;
	}
	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}
	public String getDepartureName() {
		return departureName;
	}
	public void setDepartureName(String departureName) {
		this.departureName = departureName;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	
	
}
