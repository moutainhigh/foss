package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class DptAreaDensityEntity extends BaseEntity {

	private static final long serialVersionUID = 5561829615634660053L;

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
	 * 货区编码
	 */
	private String goodsAreaCode;

	/**
	 * 分区编码
	 */
	private String zoneCode;

	/**
	 * 分区名称
	 */
	private String zoneName;

	/**
	 * 件区编码
	 */
	private String itemAreaId;

	/**
	 * 件区名称
	 */
	private String itemAreaName;

	/**
	 * 件区体积
	 */
	private BigDecimal itemAreaVolume;

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

	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getItemAreaId() {
		return itemAreaId;
	}

	public void setItemAreaId(String itemAreaId) {
		this.itemAreaId = itemAreaId;
	}

	public String getItemAreaName() {
		return itemAreaName;
	}

	public void setItemAreaName(String itemAreaName) {
		this.itemAreaName = itemAreaName;
	}

	public BigDecimal getItemAreaVolume() {
		return itemAreaVolume;
	}

	public void setItemAreaVolume(BigDecimal itemAreaVolume) {
		this.itemAreaVolume = itemAreaVolume;
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
