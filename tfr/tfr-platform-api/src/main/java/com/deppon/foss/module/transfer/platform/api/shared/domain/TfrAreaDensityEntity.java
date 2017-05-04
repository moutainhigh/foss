package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class TfrAreaDensityEntity extends BaseEntity {

	private static final long serialVersionUID = -1581752997438773162L;

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
	 * 库区编码
	 */
	private String areaCode;

	/**
	 * 库区名称
	 */
	private String areaName;

	/**
	 * 库区类型编码
	 */
	private String areaTypeCode;

	/**
	 * 库区类型名称
	 */
	private String areaTypeName;

	/**
	 * 库区类别,qt,L,S 其他、长途、短途
	 */
	private String areaUsage;

	/**
	 * 库区体积
	 */
	private BigDecimal areaVolume;

	/**
	 * 货物体积
	 */
	private BigDecimal goodsVolume;

	/**
	 * 密度
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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaTypeCode() {
		return areaTypeCode;
	}

	public void setAreaTypeCode(String areaTypeCode) {
		this.areaTypeCode = areaTypeCode;
	}

	public String getAreaTypeName() {
		return areaTypeName;
	}

	public void setAreaTypeName(String areaTypeName) {
		this.areaTypeName = areaTypeName;
	}

	public String getAreaUsage() {
		return areaUsage;
	}

	public void setAreaUsage(String areaUsage) {
		this.areaUsage = areaUsage;
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
