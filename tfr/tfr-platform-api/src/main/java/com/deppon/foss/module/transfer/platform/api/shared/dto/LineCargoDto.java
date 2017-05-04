package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LineCargoDto implements Serializable {

	private static final long serialVersionUID = 1144571092970829094L;

	/**
	 * 出发部门编码
	 */
	private String origDeptCode;

	/**
	 * 到达部门编码
	 */
	private String destDeptCode;

	/**
	 * 下一部门名称
	 */
	private String destDeptName;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 状态编码
	 */
	private String statusCode;

	/**
	 * 货物状态
	 */
	private String statusName;

	/**
	 * 运单件数
	 */
	private int waybillQty;

	/**
	 * 当前件数
	 */
	private int currentQty;

	/**
	 * 当前重量
	 */
	private BigDecimal currentWeight;

	/**
	 * 当前体积
	 */
	private BigDecimal currentVolume;

	/**
	 * 产品名称
	 */
	private String productName;

	/**
	 * 开单时间
	 */
	private Date billTime;

	/**
	 * 车次号
	 */
	private String vehicleassembleNo;

	/**
	 * 车牌号
	 */
	private String vehicleNo;

	public String getDestDeptName() {
		return destDeptName;
	}

	public void setDestDeptName(String destDeptName) {
		this.destDeptName = destDeptName;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public int getWaybillQty() {
		return waybillQty;
	}

	public void setWaybillQty(int waybillQty) {
		this.waybillQty = waybillQty;
	}

	public int getCurrentQty() {
		return currentQty;
	}

	public void setCurrentQty(int currentQty) {
		this.currentQty = currentQty;
	}

	public BigDecimal getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(BigDecimal currentWeight) {
		this.currentWeight = currentWeight;
	}

	public BigDecimal getCurrentVolume() {
		return currentVolume;
	}

	public void setCurrentVolume(BigDecimal currentVolume) {
		this.currentVolume = currentVolume;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public String getVehicleassembleNo() {
		return vehicleassembleNo;
	}

	public void setVehicleassembleNo(String vehicleassembleNo) {
		this.vehicleassembleNo = vehicleassembleNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getOrigDeptCode() {
		return origDeptCode;
	}

	public void setOrigDeptCode(String origDeptCode) {
		this.origDeptCode = origDeptCode;
	}

	public String getDestDeptCode() {
		return destDeptCode;
	}

	public void setDestDeptCode(String destDeptCode) {
		this.destDeptCode = destDeptCode;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return "LineCargoDto [origDeptCode=" + origDeptCode + ", destDeptCode="
				+ destDeptCode + ", destDeptName=" + destDeptName
				+ ", waybillNo=" + waybillNo + ", statusCode=" + statusCode
				+ ", statusName=" + statusName + ", waybillQty=" + waybillQty
				+ ", currentQty=" + currentQty + ", currentWeight="
				+ currentWeight + ", currentVolume=" + currentVolume
				+ ", productName=" + productName + ", billTime=" + billTime
				+ ", vehicleassembleNo=" + vehicleassembleNo + ", vehicleNo="
				+ vehicleNo + "]";
	}

}
