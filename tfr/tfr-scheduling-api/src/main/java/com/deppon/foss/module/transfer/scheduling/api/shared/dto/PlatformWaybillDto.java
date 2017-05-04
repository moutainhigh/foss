package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PlatformWaybillDto implements Serializable {

	private static final long serialVersionUID = 616283595242766461L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 交接重量
	 */
	private BigDecimal handoverWeight;

	/**
	 * 交接体积
	 */
	private BigDecimal handoverVolumn;

	/**
	 * 属性性质
	 */
	private String transportTypeCode;

	/**
	 * 计划出发时间(从当前外场出发)
	 */
	private String planDepartTime;

	/**
	 * 出发部门(xx部门->当前外场 xx部门)
	 */
	private String origDeptCode;

	/**
	 * 到达部门(即当前外场)
	 */
	private String destDeptCode;

	/**
	 * 从当前外场出发的下一部门
	 */
	private String nextDeptCode;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public BigDecimal getHandoverWeight() {
		return handoverWeight;
	}

	public void setHandoverWeight(BigDecimal handoverWeight) {
		this.handoverWeight = handoverWeight;
	}

	public BigDecimal getHandoverVolumn() {
		return handoverVolumn;
	}

	public void setHandoverVolumn(BigDecimal handoverVolumn) {
		this.handoverVolumn = handoverVolumn;
	}

	public String getTransportTypeCode() {
		return transportTypeCode;
	}

	public void setTransportTypeCode(String transportTypeCode) {
		this.transportTypeCode = transportTypeCode;
	}

	public String getPlanDepartTime() {
		return planDepartTime;
	}

	public void setPlanDepartTime(String planDepartTime) {
		this.planDepartTime = planDepartTime;
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

	public String getNextDeptCode() {
		return nextDeptCode;
	}

	public void setNextDeptCode(String nextDeptCode) {
		this.nextDeptCode = nextDeptCode;
	}

	@Override
	public String toString() {
		return "PlatformWaybillDto [waybillNo=" + waybillNo
				+ ", handoverWeight=" + handoverWeight + ", handoverVolumn="
				+ handoverVolumn + ", transportTypeCode=" + transportTypeCode
				+ ", planDepartTime=" + planDepartTime + ", origDeptCode="
				+ origDeptCode + ", destDeptCode=" + destDeptCode
				+ ", nextDeptCode=" + nextDeptCode + "]";
	}

}
