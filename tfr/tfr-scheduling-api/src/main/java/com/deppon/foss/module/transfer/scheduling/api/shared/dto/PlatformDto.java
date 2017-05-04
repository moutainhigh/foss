package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PlatformDto implements Serializable {

	private static final long serialVersionUID = -2461562992480496904L;

	/**
	 * 外场
	 */
	private String transferCenterCode;

	/**
	 * 月台虚拟编码
	 */
	private String virtualCode;

	/**
	 * 月台号
	 */
	private String platformCode;

	/**
	 * 月台类型
	 */
	private String platformType;

	/**
	 * 最大可停用车型
	 */
	private String vehicleLengthName;

	/**
	 * 月台状态
	 */
	private String status;

	/**
	 * 是否有升降台
	 */
	private String hasLift;

	/**
	 * 功
	 */
	private BigDecimal work;

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public String getVirtualCode() {
		return virtualCode;
	}

	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}

	public BigDecimal getWork() {
		return work;
	}

	public void setWork(BigDecimal work) {
		this.work = work;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getVehicleLengthName() {
		return vehicleLengthName;
	}

	public void setVehicleLengthName(String vehicleLengthName) {
		this.vehicleLengthName = vehicleLengthName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHasLift() {
		return hasLift;
	}

	public void setHasLift(String hasLift) {
		this.hasLift = hasLift;
	}

}
