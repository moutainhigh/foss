package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.util.Date;

import com.deppon.foss.module.transfer.platform.api.shared.domain.GoodsAreaDensityEntity;

public class GoodsAreaDensityQcDto extends GoodsAreaDensityEntity {

	private static final long serialVersionUID = -8305623666905858540L;

	/**
	 * 统计开始日期
	 */
	private Date beginStatisticDate;

	/**
	 * 统计结束日期
	 */
	private Date endStatisticDate;

	/**
	 * 统计开始月份；
	 */
	private String beginStatisticMonth;

	/**
	 * 统计结束月份；
	 */
	private String endStatisticMonth;

	public Date getBeginStatisticDate() {
		return beginStatisticDate;
	}

	public void setBeginStatisticDate(Date beginStatisticDate) {
		this.beginStatisticDate = beginStatisticDate;
	}

	public Date getEndStatisticDate() {
		return endStatisticDate;
	}

	public void setEndStatisticDate(Date endStatisticDate) {
		this.endStatisticDate = endStatisticDate;
	}

	public String getBeginStatisticMonth() {
		return beginStatisticMonth;
	}

	public void setBeginStatisticMonth(String beginStatisticMonth) {
		this.beginStatisticMonth = beginStatisticMonth;
	}

	public String getEndStatisticMonth() {
		return endStatisticMonth;
	}

	public void setEndStatisticMonth(String endStatisticMonth) {
		this.endStatisticMonth = endStatisticMonth;
	}



}
