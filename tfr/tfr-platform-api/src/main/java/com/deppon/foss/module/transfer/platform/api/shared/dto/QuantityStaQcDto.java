package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class QuantityStaQcDto implements Serializable {

	private static final long serialVersionUID = -884047737788218677L;

	/**
	 * 出发DEPARTURE或到达ARRIVAL
	 */
	private String type;

	/**
	 * 外场编码
	 */
	private String transferCenterCode;

	private String relevantOrgCode;

	/**
	 * 是否是第2天的货量
	 */
	private boolean secondDay;

	/**
	 * 货量统计类型 {@see #QuantityStaConstants}
	 */
	private String dataType;

	/**
	 * 统计日期
	 */
	private Date staDate;

	/**
	 * 统计时间点
	 */
	private Integer staHh;

	/**
	 * 统计起始日期；用于查询最近几天某时间点的货量
	 */
	private Date beginStaDate;

	/**
	 * 统计结束日期；用于查询最近几天某时间点的货量
	 */
	private Date endStaDate;

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public boolean isSecondDay() {
		return secondDay;
	}

	public void setSecondDay(boolean secondDay) {
		this.secondDay = secondDay;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Date getStaDate() {
		return staDate;
	}

	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}

	public Integer getStaHh() {
		return staHh;
	}

	public void setStaHh(Integer staHh) {
		this.staHh = staHh;
	}

	public String getRelevantOrgCode() {
		return relevantOrgCode;
	}

	public void setRelevantOrgCode(String relevantOrgCode) {
		this.relevantOrgCode = relevantOrgCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getBeginStaDate() {
		return beginStaDate;
	}

	public void setBeginStaDate(Date beginStaDate) {
		this.beginStaDate = beginStaDate;
	}

	public Date getEndStaDate() {
		return endStaDate;
	}

	public void setEndStaDate(Date endStaDate) {
		this.endStaDate = endStaDate;
	}

}
