package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class LineCargoQcDto implements Serializable {

	private static final long serialVersionUID = 6453259495604461294L;

	/**
	 * 出发部门编码
	 */
	private String origDeptCode;

	/**
	 * 到达部门编码
	 */
	private String destDeptCode;

	/**
	 * 产品编码
	 */
	private String productCode;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 货物状态
	 */
	private String statusCode;

	/**
	 * 状态名称
	 */
	private String statusName;

	/**
	 * 开始时间
	 */
	private Date beginTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 交接单状态 {@see #LoadConstants.HANDOVERBILL_STATE_*}
	 */
	private List<Integer> handoverStatus;

	private String vehicleNo;

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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<Integer> getHandoverStatus() {
		return handoverStatus;
	}

	public void setHandoverStatus(List<Integer> handoverStatus) {
		this.handoverStatus = handoverStatus;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	@Override
	public String toString() {
		return "LineCargoQcDto [origDeptCode=" + origDeptCode
				+ ", destDeptCode=" + destDeptCode + ", productCode="
				+ productCode + ", waybillNo=" + waybillNo + ", statusCode="
				+ statusCode + ", statusName=" + statusName + ", beginTime="
				+ beginTime + ", endTime=" + endTime + ", handoverStatus="
				+ handoverStatus + ", vehicleNo=" + vehicleNo + "]";
	}

}
