package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class CargoFcstDto implements Serializable {

	private static final long serialVersionUID = 7589138053676021529L;

	/**
	 * 外场编码
	 */
	private String tfrCtrCode;

	/**
	 * 出发(到达)部门编码
	 */
	private String lineCode;

	/**
	 * 统计日期
	 */
	private Date staDate;

	/**
	 * 统计各类型货量的起始时间
	 */
	private Date beginTime;

	/**
	 * 统计各类型货量的结束时间
	 */
	private Date endTime;

	/**
	 * 货物类型
	 */
	private String goodsType;

	public String getTfrCtrCode() {
		return tfrCtrCode;
	}

	public void setTfrCtrCode(String tfrCtrCode) {
		this.tfrCtrCode = tfrCtrCode;
	}

	public String getLineCode() {
		return lineCode;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	public Date getStaDate() {
		return staDate;
	}

	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
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

}
