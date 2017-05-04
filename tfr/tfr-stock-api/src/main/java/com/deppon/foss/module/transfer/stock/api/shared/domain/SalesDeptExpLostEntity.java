package com.deppon.foss.module.transfer.stock.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class SalesDeptExpLostEntity extends BaseEntity {

	private static final long serialVersionUID = 5476191238623239707L;

	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 部门名称
	 */
	private String orgName;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 流水号
	 */
	private String serialNo;

	/**
	 * 状态{@see #SalesDeptExpLostConstants}
	 */
	private String status;

	/**
	 * 是否上报oa丢货
	 */
	private String reported;

	/**
	 * 统计时间
	 */
	private Date staTime;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReported() {
		return reported;
	}

	public void setReported(String reported) {
		this.reported = reported;
	}

	public Date getStaTime() {
		return staTime;
	}

	public void setStaTime(Date staTime) {
		this.staTime = staTime;
	}

	@Override
	public String toString() {
		return "SalesDeptExpLostEntity [orgCode=" + orgCode + ", orgName="
				+ orgName + ", waybillNo=" + waybillNo + ", serialNo="
				+ serialNo + ", status=" + status + ", reported=" + reported
				+ ", staTime=" + String.format("%1$tF %2$tT", staTime, staTime)
				+ "]";
	}
}
