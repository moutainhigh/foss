package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class TfrCtrDensityPeakEntity extends BaseEntity {

	private static final long serialVersionUID = 5089963079125593138L;

	/**
	 * 经营本部编码
	 */
	private String hqCode;

	/**
	 * 经营本部名称
	 */
	private String hqName;

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
	 * 日库区密度峰值对应的货区容量
	 */
	private BigDecimal areaVolumeD;
	
	/**
	 * 日库区密度峰值
	 */
	private BigDecimal peakDensityD;

	/**
	 * 日库区密度峰值所在时间点
	 */
	private int peakHourD;

	/**
	 * 月库区密度峰值
	 */
	private BigDecimal peakDensityM;

	/**
	 * 月库区密度峰值所在日期
	 */
	private Date peakDateM;

	/**
	 * 月库区密度峰值所在时间点
	 */
	private int peakHourM;

	/**
	 * 日库区密度峰值所在时间，由peakDateD和staDate组成
	 */
	private String peakTimeD;

	/**
	 * 月库区密度峰值所在时间，由peakDateM和peakHourM组成
	 */
	private String peakTimeM;

	public String getHqCode() {
		return hqCode;
	}

	public void setHqCode(String hqCode) {
		this.hqCode = hqCode;
	}

	public String getHqName() {
		return hqName;
	}

	public void setHqName(String hqName) {
		this.hqName = hqName;
	}

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

	public BigDecimal getPeakDensityD() {
		return peakDensityD;
	}

	public void setPeakDensityD(BigDecimal peakDensityD) {
		this.peakDensityD = peakDensityD;
	}

	public int getPeakHourD() {
		return peakHourD;
	}

	public void setPeakHourD(int peakHourD) {
		this.peakHourD = peakHourD;
	}

	public BigDecimal getPeakDensityM() {
		return peakDensityM;
	}

	public void setPeakDensityM(BigDecimal peakDensityM) {
		this.peakDensityM = peakDensityM;
	}

	public Date getPeakDateM() {
		return peakDateM;
	}

	public void setPeakDateM(Date peakDateM) {
		this.peakDateM = peakDateM;
	}

	public int getPeakHourM() {
		return peakHourM;
	}

	public void setPeakHourM(int peakHourM) {
		this.peakHourM = peakHourM;
	}

	public String getPeakTimeM() {
		return peakTimeM;
	}

	public void setPeakTimeM(String peakTimeM) {
		this.peakTimeM = peakTimeM;
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

	public BigDecimal getAreaVolumeD() {
		return areaVolumeD;
	}

	public void setAreaVolumeD(BigDecimal areaVolumeD) {
		this.areaVolumeD = areaVolumeD;
	}

	public String getPeakTimeD() {
		return peakTimeD;
	}

	public void setPeakTimeD(String peakTimeD) {
		this.peakTimeD = peakTimeD;
	}

}
