package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class ForkAreaDensityEntity extends BaseEntity {

	private static final long serialVersionUID = -4305541318122537693L;

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
	 * 统计时间点
	 */
	private int staHour;

	/**
	 * 待叉区编码
	 */
	private String areaCode;

	/**
	 * 待叉区体积
	 */
	private BigDecimal areaVolume;

	/**
	 * 货物体积
	 */
	private BigDecimal goodsVolume;

	/**
	 * 待叉区密度
	 */
	private BigDecimal density;

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

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public BigDecimal getAreaVolume() {
		return areaVolume;
	}

	public void setAreaVolume(BigDecimal areaVolume) {
		this.areaVolume = areaVolume;
	}

	public BigDecimal getGoodsVolume() {
		return goodsVolume;
	}

	public void setGoodsVolume(BigDecimal goodsVolume) {
		this.goodsVolume = goodsVolume;
	}

	public BigDecimal getDensity() {
		return density;
	}

	public void setDensity(BigDecimal density) {
		this.density = density;
	}

}
