package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class DensityQcDto implements Serializable {

	private static final long serialVersionUID = 5858608351073742914L;

	/**
	 * 经营本部编码
	 */
	private String hqCode;

	/**
	 * 外场编码
	 */
	private String tfrCtrCode;

	/**
	 * 统计日期
	 */
	private Date staDate;

	/**
	 * 统计月份
	 */
	private int staMonth;

	/**
	 * 统计时间点
	 */
	private int staHour;

	/**
	 * 库区编码
	 */
	private String goodsAreaCode;

	/**
	 * 库区类别,qt,L,S 其他、长途、短途
	 */
	private String goodsAreaUsage;

	/**
	 * 用于密度趋势查询，是否查询待叉区密度(0/1)
	 */
	private int flagFork;

	/**
	 * 用于密度趋势查询，是否查询长途库区密度(0/1)
	 */
	private int flagLong;

	/**
	 * 用于密度趋势查询，是否查询短途库区密度(0/1)
	 */
	private int flagShort;

	/**
	 * 用于密度趋势查询，是否查询派送库区密度(0/1)
	 */
	private int flagDpt;

	/**
	 * 用于密度趋势查询，是否查询外场密度(0/1)
	 */
	private int flagTfr;

	public String getHqCode() {
		return hqCode;
	}

	public void setHqCode(String hqCode) {
		this.hqCode = hqCode;
	}

	public String getTfrCtrCode() {
		return tfrCtrCode;
	}

	public void setTfrCtrCode(String tfrCtrCode) {
		this.tfrCtrCode = tfrCtrCode;
	}

	public Date getStaDate() {
		return staDate;
	}

	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}

	public int getStaMonth() {
		return staMonth;
	}

	public void setStaMonth(int staMonth) {
		this.staMonth = staMonth;
	}

	public int getStaHour() {
		return staHour;
	}

	public void setStaHour(int staHour) {
		this.staHour = staHour;
	}

	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	public String getGoodsAreaUsage() {
		return goodsAreaUsage;
	}

	public void setGoodsAreaUsage(String goodsAreaUsage) {
		this.goodsAreaUsage = goodsAreaUsage;
	}

	public int getFlagFork() {
		return flagFork;
	}

	public void setFlagFork(int flagFork) {
		this.flagFork = flagFork;
	}

	public int getFlagLong() {
		return flagLong;
	}

	public void setFlagLong(int flagLong) {
		this.flagLong = flagLong;
	}

	public int getFlagShort() {
		return flagShort;
	}

	public void setFlagShort(int flagShort) {
		this.flagShort = flagShort;
	}

	public int getFlagDpt() {
		return flagDpt;
	}

	public void setFlagDpt(int flagDpt) {
		this.flagDpt = flagDpt;
	}

	public int getFlagTfr() {
		return flagTfr;
	}

	public void setFlagTfr(int flagTfr) {
		this.flagTfr = flagTfr;
	}

}
