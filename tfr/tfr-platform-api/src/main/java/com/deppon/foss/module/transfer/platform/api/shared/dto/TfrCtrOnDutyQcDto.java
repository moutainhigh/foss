package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class TfrCtrOnDutyQcDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 外场编码
	 */
	private String tfrCtrCode;

	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 出勤开始时间(只用于查询)
	 */
	private Date beginDate;

	/**
	 * 出勤结束时间(只用于查询)
	 */
	private Date endDate;

	/**
	 * 外场名称(只用于新增时查询)
	 */
	private String tfrCtrName;

	/**
	 * 部门名称(只用于新增时查询)
	 */
	private String orgName;

	/**
	 * 出勤日期(只用于新增时查询)
	 */
	private Date onDutyDate;

	public String getTfrCtrCode() {
		return tfrCtrCode;
	}

	public void setTfrCtrCode(String tfrCtrCode) {
		this.tfrCtrCode = tfrCtrCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Date getOnDutyDate() {
		return onDutyDate;
	}

	public void setOnDutyDate(Date onDutyDate) {
		this.onDutyDate = onDutyDate;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTfrCtrName() {
		return tfrCtrName;
	}

	public void setTfrCtrName(String tfrCtrName) {
		this.tfrCtrName = tfrCtrName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Override
	public String toString() {
		return "TfrCtrOnDutyQcDto [tfrCtrCode=" + tfrCtrCode + ", orgCode="
				+ orgCode + ", onDutyDate=" + onDutyDate + ", beginDate="
				+ beginDate + ", endDate=" + endDate + ", tfrCtrName="
				+ tfrCtrName + ", orgName=" + orgName + "]";
	}

}
