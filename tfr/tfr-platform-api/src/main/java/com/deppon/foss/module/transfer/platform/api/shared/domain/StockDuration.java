package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class StockDuration extends BaseEntity {

	private static final long serialVersionUID = -2478848572858040972L;

	/**
	 * 外场编码
	 */
	private String tfrCtrCode;

	/**
	 * 外场名称
	 */
	private String tfrCtrName;

	/**
	 * 统计日期
	 */
	private Date staDate;

	/**
	 * 统计月份
	 */
	private int staMonth;

	/**
	 * 卸车等待时长
	 */
	private BigDecimal unloadWaitTime;

	/**
	 * 卸车等待票数
	 */
	private BigDecimal unloadWaitVote;

	/**
	 * 卸车时长
	 */
	private BigDecimal unloadTime;

	/**
	 * 卸车票数
	 */
	private BigDecimal unloadVote;

	/**
	 * 待叉区停留时长
	 */
	private BigDecimal forkAreaStayTime;

	/**
	 * 待叉区停留票数
	 */
	private BigDecimal forkAreaStayVote;

	/**
	 * 包装库区停留时长
	 */
	private BigDecimal pkgAreaStayTime;

	/**
	 * 包装库区停留票数
	 */
	private BigDecimal pkgAreaStayVote;

	/**
	 * 中转库区停留时长
	 */
	private BigDecimal tfrAreaTime;

	/**
	 * 中转库区停留票数
	 */
	private BigDecimal tfrAreaVote;

	/**
	 * 派送库区停留时长
	 */
	private BigDecimal dptAreaTime;

	/**
	 * 派送库区停留票数
	 */
	private BigDecimal dptAreaVote;

	/**
	 * 平均卸车等待时长
	 */
	private BigDecimal avgUnloadWaitTime;

	/**
	 * 平均卸车时长
	 */
	private BigDecimal avgUnloadTime;

	/**
	 * 平均待叉区停留时长
	 */
	private BigDecimal avgForkAreaStayTime;

	/**
	 * 平均包装库区停留时长
	 */
	private BigDecimal avgPkgAreaStayTime;

	/**
	 * 平均中转库区停留时长
	 */
	private BigDecimal avgTfrAreaTime;

	/**
	 * 平均派送库区停留时长
	 */
	private BigDecimal avgDptAreaTime;

	public String getTfrCtrCode() {
		return tfrCtrCode;
	}

	public void setTfrCtrCode(String tfrCtrCode) {
		this.tfrCtrCode = tfrCtrCode;
	}

	public String getTfrCtrName() {
		return tfrCtrName;
	}

	public void setTfrCtrName(String tfrCtrName) {
		this.tfrCtrName = tfrCtrName;
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

	public BigDecimal getUnloadWaitTime() {
		return unloadWaitTime;
	}

	public void setUnloadWaitTime(BigDecimal unloadWaitTime) {
		this.unloadWaitTime = unloadWaitTime;
	}

	public BigDecimal getUnloadWaitVote() {
		return unloadWaitVote;
	}

	public void setUnloadWaitVote(BigDecimal unloadWaitVote) {
		this.unloadWaitVote = unloadWaitVote;
	}

	public BigDecimal getUnloadTime() {
		return unloadTime;
	}

	public void setUnloadTime(BigDecimal unloadTime) {
		this.unloadTime = unloadTime;
	}

	public BigDecimal getUnloadVote() {
		return unloadVote;
	}

	public void setUnloadVote(BigDecimal unloadVote) {
		this.unloadVote = unloadVote;
	}

	public BigDecimal getForkAreaStayTime() {
		return forkAreaStayTime;
	}

	public void setForkAreaStayTime(BigDecimal forkAreaStayTime) {
		this.forkAreaStayTime = forkAreaStayTime;
	}

	public BigDecimal getForkAreaStayVote() {
		return forkAreaStayVote;
	}

	public void setForkAreaStayVote(BigDecimal forkAreaStayVote) {
		this.forkAreaStayVote = forkAreaStayVote;
	}

	public BigDecimal getPkgAreaStayTime() {
		return pkgAreaStayTime;
	}

	public void setPkgAreaStayTime(BigDecimal pkgAreaStayTime) {
		this.pkgAreaStayTime = pkgAreaStayTime;
	}

	public BigDecimal getPkgAreaStayVote() {
		return pkgAreaStayVote;
	}

	public void setPkgAreaStayVote(BigDecimal pkgAreaStayVote) {
		this.pkgAreaStayVote = pkgAreaStayVote;
	}

	public BigDecimal getTfrAreaTime() {
		return tfrAreaTime;
	}

	public void setTfrAreaTime(BigDecimal tfrAreaTime) {
		this.tfrAreaTime = tfrAreaTime;
	}

	public BigDecimal getTfrAreaVote() {
		return tfrAreaVote;
	}

	public void setTfrAreaVote(BigDecimal tfrAreaVote) {
		this.tfrAreaVote = tfrAreaVote;
	}

	public BigDecimal getDptAreaTime() {
		return dptAreaTime;
	}

	public void setDptAreaTime(BigDecimal dptAreaTime) {
		this.dptAreaTime = dptAreaTime;
	}

	public BigDecimal getDptAreaVote() {
		return dptAreaVote;
	}

	public void setDptAreaVote(BigDecimal dptAreaVote) {
		this.dptAreaVote = dptAreaVote;
	}

	public BigDecimal getAvgUnloadWaitTime() {
		return avgUnloadWaitTime;
	}

	public void setAvgUnloadWaitTime(BigDecimal avgUnloadWaitTime) {
		this.avgUnloadWaitTime = avgUnloadWaitTime;
	}

	public BigDecimal getAvgUnloadTime() {
		return avgUnloadTime;
	}

	public void setAvgUnloadTime(BigDecimal avgUnloadTime) {
		this.avgUnloadTime = avgUnloadTime;
	}

	public BigDecimal getAvgForkAreaStayTime() {
		return avgForkAreaStayTime;
	}

	public void setAvgForkAreaStayTime(BigDecimal avgForkAreaStayTime) {
		this.avgForkAreaStayTime = avgForkAreaStayTime;
	}

	public BigDecimal getAvgPkgAreaStayTime() {
		return avgPkgAreaStayTime;
	}

	public void setAvgPkgAreaStayTime(BigDecimal avgPkgAreaStayTime) {
		this.avgPkgAreaStayTime = avgPkgAreaStayTime;
	}

	public BigDecimal getAvgTfrAreaTime() {
		return avgTfrAreaTime;
	}

	public void setAvgTfrAreaTime(BigDecimal avgTfrAreaTime) {
		this.avgTfrAreaTime = avgTfrAreaTime;
	}

	public BigDecimal getAvgDptAreaTime() {
		return avgDptAreaTime;
	}

	public void setAvgDptAreaTime(BigDecimal avgDptAreaTime) {
		this.avgDptAreaTime = avgDptAreaTime;
	}

}
