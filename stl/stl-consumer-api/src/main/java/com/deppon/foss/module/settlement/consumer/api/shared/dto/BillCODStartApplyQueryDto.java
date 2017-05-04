package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.util.Date;
import java.util.List;

/**
 * 代收货款查询DTO.
 *
 * @author dp-zengbinwen
 * @date 2012-10-26 上午10:19:35
 */
public class BillCODStartApplyQueryDto {
	/**
	 * @author 218392 zhangyongxue 2015-11-09 08:48:10
	 * 业务日期起
	 */
	private Date timeBegin;
	
	/**
	 * @author 218392 zhangyongxue 2015-11-09 08:48:10
	 * 业务日期止
	 */
	private Date timeEnd;
	
	/**
	 * @author 218392 zhangyongxue 2015-11-09 08:48:10
	 * 收款方
	 */
	private String cusAccountName;
	
	/**
	 * @author 218392 zhangyongxue 2015-11-09 14:39:10
	 * 是否按业务日期查询
	 */
	private String timeType;
	
	/**
	 * @author 218392 张永雪 2015-11-12 09:51:00
	 * 当前登陆部门
	 */
	private String currentOrgCode;

	/** 代收货款状态. */
	private List<String> statuses;

	/** 用户编码. */
	private String userCode;
	
	/** 是否有效. */
	private String active;

	/** 分页开始. */
	private int offset;

	/** 分页大小. */
	private int limit;

	/** 应付部门. */
	private String payableOrgCode;
	
	/** 应付部门集合. */
	private List<String> payableOrgCodeList;

	/** 应付单是否有效. */
	private String payableActive;

	/** 应付单类型. */
	private String payableBillType;
	
	/** 生效状态. */
	private String effectiveStatus;
	
	/**
	 * 核销类型
	 */
	private String writeoffType;

	/**
	 * Gets the statuses.
	 *
	 * @return statuses
	 */
	public List<String> getStatuses() {
		return statuses;
	}

	/**
	 * Sets the statuses.
	 *
	 * @param statuses the new statuses
	 */
	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}

	/**
	 * Gets the active.
	 *
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Gets the offset.
	 *
	 * @return offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Sets the offset.
	 *
	 * @param offset the new offset
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * Gets the limit.
	 *
	 * @return limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * Sets the limit.
	 *
	 * @param limit the new limit
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * Gets the payable org code.
	 *
	 * @return payableOrgCode
	 */
	public String getPayableOrgCode() {
		return payableOrgCode;
	}

	/**
	 * Sets the payable org code.
	 *
	 * @param payableOrgCode the new payable org code
	 */
	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}

	/**
	 * Gets the payable active.
	 *
	 * @return payableActive
	 */
	public String getPayableActive() {
		return payableActive;
	}

	/**
	 * Sets the payable active.
	 *
	 * @param payableActive the new payable active
	 */
	public void setPayableActive(String payableActive) {
		this.payableActive = payableActive;
	}

	/**
	 * Gets the payable bill type.
	 *
	 * @return payableBillType
	 */
	public String getPayableBillType() {
		return payableBillType;
	}

	/**
	 * Sets the payable bill type.
	 *
	 * @param payableBillType the new payable bill type
	 */
	public void setPayableBillType(String payableBillType) {
		this.payableBillType = payableBillType;
	}

	/**
	 * Gets the payable org code list.
	 *
	 * @return payableOrgCodeList
	 */
	public List<String> getPayableOrgCodeList() {
		return payableOrgCodeList;
	}

	/**
	 * Sets the payable org code list.
	 *
	 * @param payableOrgCodeList the new payable org code list
	 */
	public void setPayableOrgCodeList(List<String> payableOrgCodeList) {
		this.payableOrgCodeList = payableOrgCodeList;
	}

	/**
	 * Gets the effective status.
	 *
	 * @return effectiveStatus
	 */
	public String getEffectiveStatus() {
		return effectiveStatus;
	}

	/**
	 * Sets the effective status.
	 *
	 * @param effectiveStatus the new effective status
	 */
	public void setEffectiveStatus(String effectiveStatus) {
		this.effectiveStatus = effectiveStatus;
	}

	/**
	 * Gets the user code.
	 *
	 * @return the user code
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * Sets the user code.
	 *
	 * @param userCode the new user code
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return writeoffType
	 */
	public String getWriteoffType() {
		return writeoffType;
	}

	/**
	 * @param writeoffType
	 */
	public void setWriteoffType(String writeoffType) {
		this.writeoffType = writeoffType;
	}

	public Date getTimeBegin() {
		return timeBegin;
	}

	public void setTimeBegin(Date timeBegin) {
		this.timeBegin = timeBegin;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getCusAccountName() {
		return cusAccountName;
	}

	public void setCusAccountName(String cusAccountName) {
		this.cusAccountName = cusAccountName;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getCurrentOrgCode() {
		return currentOrgCode;
	}

	public void setCurrentOrgCode(String currentOrgCode) {
		this.currentOrgCode = currentOrgCode;
	}
	

}
