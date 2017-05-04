package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class QuantityStaTfrCtrCondDto implements Serializable {

	private static final long serialVersionUID = 5160763690906275730L;

	/**
	 * 外场编码
	 */
	private String transferCenterCode;

	/**
	 * 从外场出发(到达)线路的下(上)一部门编码
	 */
	private String relevantOrgCode;

	/**
	 * 货量类型
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

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public String getRelevantOrgCode() {
		return relevantOrgCode;
	}

	public void setRelevantOrgCode(String relevantOrgCode) {
		this.relevantOrgCode = relevantOrgCode;
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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
