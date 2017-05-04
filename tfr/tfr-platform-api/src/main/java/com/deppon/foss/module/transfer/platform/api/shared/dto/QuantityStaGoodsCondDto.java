package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class QuantityStaGoodsCondDto implements Serializable {

	private static final long serialVersionUID = 5160763690906275730L;

	/**
	 * 外场编码
	 */
	private String transferCenterCode;

	/**
	 * 外场货量预测配载参数FORECAST_START对应config_value
	 */
	private String configValue;

	/**
	 * 从配置参数取的货量统计对应外场的开始时间
	 */
	private Date beginDate;

	/**
	 * 从配置参数取的货量统计对应外场的结束时间
	 */
	private Date endDate;

	/**
	 * 当前时间
	 */
	private Date createTime;

	/**
	 * 货量所属日期
	 */
	private Date dataBelongDate;

	/**
	 * 统计时间点(0-24)
	 */
	private Integer staHh;
	
	private Date staDate;

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDataBelongDate() {
		return dataBelongDate;
	}

	public void setDataBelongDate(Date dataBelongDate) {
		this.dataBelongDate = dataBelongDate;
	}

	public Integer getStaHh() {
		return staHh;
	}

	public void setStaHh(Integer staHh) {
		this.staHh = staHh;
	}

	public Date getStaDate() {
		return staDate;
	}

	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}

	@Override
	public String toString() {
		return "QuantityStaGoodsCondDto [transferCenterCode="
				+ transferCenterCode + ", configValue=" + configValue
				+ ", beginDate=" + beginDate + ", endDate=" + endDate
				+ ", createTime=" + createTime + ", dataBelongDate="
				+ dataBelongDate + ", staHh=" + staHh + ", staDate=" + staDate
				+ "]";
	}
	
	
}
