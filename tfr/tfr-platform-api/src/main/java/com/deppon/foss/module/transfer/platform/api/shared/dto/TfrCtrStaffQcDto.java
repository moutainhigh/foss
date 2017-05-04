package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class TfrCtrStaffQcDto implements Serializable {

	private static final long serialVersionUID = -7345977881406468142L;

	/**
	 * 外场编码
	 */
	private String transferCenterCode;

	/**
	 * 查询日期
	 */
	private Date queryDate;

	/**
	 * 查询日期所在月份
	 */
	private String queryMonth;

	/**
	 * 当月第1天0点
	 */
	private Date firstMomOfQueryMonth;

	/**
	 * 查询日期的23点59分59秒
	 */
	private Date lastMomOfQueryDate;

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	public String getQueryMonth() {
		return queryMonth;
	}

	public void setQueryMonth(String queryMonth) {
		this.queryMonth = queryMonth;
	}

	public Date getFirstMomOfQueryMonth() {
		return firstMomOfQueryMonth;
	}

	public void setFirstMomOfQueryMonth(Date firstMomOfQueryMonth) {
		this.firstMomOfQueryMonth = firstMomOfQueryMonth;
	}

	public Date getLastMomOfQueryDate() {
		return lastMomOfQueryDate;
	}

	public void setLastMomOfQueryDate(Date lastMomOfQueryDate) {
		this.lastMomOfQueryDate = lastMomOfQueryDate;
	}

}
