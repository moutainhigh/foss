package com.deppon.foss.module.transfer.stock.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class SalesDeptExpLostOaLogEntity extends BaseEntity {

	private static final long serialVersionUID = 3301603961146549253L;

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
	 * 是否上报
	 */
	private String reported;

	/**
	 * 统计时间
	 */
	private Date staTime;

	/**
	 * oa差错编号
	 */
	private String oaErrorNo;

	/**
	 * oa返回的错误信息
	 */
	private String message;

	/**
	 * 操作时间
	 */
	private Date operateTime;

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

	public String getOaErrorNo() {
		return oaErrorNo;
	}

	public void setOaErrorNo(String oaErrorNo) {
		this.oaErrorNo = oaErrorNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Override
	public String toString() {
		return "SalesDeptExpLostOaLogEntity [orgCode=" + orgCode + ", orgName="
				+ orgName + ", waybillNo=" + waybillNo + ", reported="
				+ reported + ", staTime=" + staTime + ", oaErrorNo="
				+ oaErrorNo + ", message=" + message + ", operateTime="
				+ operateTime + "]";
	}

}
