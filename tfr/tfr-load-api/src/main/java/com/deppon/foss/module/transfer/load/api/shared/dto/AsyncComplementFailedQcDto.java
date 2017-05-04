package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class AsyncComplementFailedQcDto implements Serializable {

	private static final long serialVersionUID = -2187168247497840044L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 运单原提货网点
	 */
	private String beforePkpOrgCode;

	/**
	 * 补码提货网点编码
	 */
	private String pkpOrgCode;

	/**
	 * 补码外场编码，有可能在非外场补码
	 */
	private String tfrCtrCode;

	/**
	 * 开始时间-创建时间
	 */
	private Date beginTime;

	/**
	 * 结束时间-创建时间
	 */
	private Date endTime;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getPkpOrgCode() {
		return pkpOrgCode;
	}

	public void setPkpOrgCode(String pkpOrgCode) {
		this.pkpOrgCode = pkpOrgCode;
	}

	public String getTfrCtrCode() {
		return tfrCtrCode;
	}

	public void setTfrCtrCode(String tfrCtrCode) {
		this.tfrCtrCode = tfrCtrCode;
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

	public String getBeforePkpOrgCode() {
		return beforePkpOrgCode;
	}

	public void setBeforePkpOrgCode(String beforePkpOrgCode) {
		this.beforePkpOrgCode = beforePkpOrgCode;
	}

	@Override
	public String toString() {
		return "AsyncComplementFailedQcDto [waybillNo=" + waybillNo + ", beforePkpOrgCode=" + beforePkpOrgCode
				+ ", pkpOrgCode=" + pkpOrgCode + ", tfrCtrCode=" + tfrCtrCode + ", beginTime=" + beginTime
				+ ", endTime=" + endTime + "]";
	}

}
