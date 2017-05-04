package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DensityTrendDto implements Serializable {

	private static final long serialVersionUID = -4324718970230264160L;

	/**
	 * 待叉区密度
	 */
	private BigDecimal densityOfFork;

	/**
	 * 长途库区密度
	 */
	private BigDecimal densityOfLong;

	/**
	 * 短途库区密度
	 */
	private BigDecimal densityOfShort;

	/**
	 * 派送库区密度
	 */
	private BigDecimal densityOfDpt;

	/**
	 * 中转库区密度
	 */
	private BigDecimal densityOfTfr;

	private Date staDate;

	private int staHour;

	public Date getStaDate() {
		return staDate;
	}

	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}

	public int getStaHour() {
		return staHour;
	}

	public void setStaHour(int staHour) {
		this.staHour = staHour;
	}

	public BigDecimal getDensityOfFork() {
		return densityOfFork;
	}

	public void setDensityOfFork(BigDecimal densityOfFork) {
		this.densityOfFork = densityOfFork;
	}

	public BigDecimal getDensityOfLong() {
		return densityOfLong;
	}

	public void setDensityOfLong(BigDecimal densityOfLong) {
		this.densityOfLong = densityOfLong;
	}

	public BigDecimal getDensityOfShort() {
		return densityOfShort;
	}

	public void setDensityOfShort(BigDecimal densityOfShort) {
		this.densityOfShort = densityOfShort;
	}

	public BigDecimal getDensityOfDpt() {
		return densityOfDpt;
	}

	public void setDensityOfDpt(BigDecimal densityOfDpt) {
		this.densityOfDpt = densityOfDpt;
	}

	public BigDecimal getDensityOfTfr() {
		return densityOfTfr;
	}

	public void setDensityOfTfr(BigDecimal densityOfTfr) {
		this.densityOfTfr = densityOfTfr;
	}
	
}
