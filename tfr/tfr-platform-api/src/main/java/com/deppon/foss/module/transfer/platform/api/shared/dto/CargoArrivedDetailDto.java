package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CargoArrivedDetailDto implements Serializable {

	private static final long serialVersionUID = 1657691898339299974L;

	/**
	 * 车牌
	 */
	private String vehicleNo;

	/**
	 * 预计到达时间
	 */
	private Date eta;

	/**
	 * 重量
	 */
	private BigDecimal weight;

	/**
	 * 体积
	 */
	private BigDecimal volume;

	/**
	 * 票数
	 */
	private int vote;

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public Date getEta() {
		return eta;
	}

	public void setEta(Date eta) {
		this.eta = eta;
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

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	@Override
	public String toString() {
		return "CargoArrivedDetailDto [vehicleNo=" + vehicleNo + ", eta=" + eta
				+ ", weight=" + weight + ", volume=" + volume + ", vote="
				+ vote + "]";
	}

}
