package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class GoodsAreaDensityEntity extends BaseEntity {

	private static final long serialVersionUID = 2495730135810088295L;

	/**
	 * 外场编码
	 */
	private String transferCenterCode;

	/**
	 * 外场名称
	 */
	private String transferCenterName;

	/**
	 * 货区类型编码
	 */
	private String goodsAreaTypeCode;

	/**
	 * 货区类型名称
	 */
	private String goodsAreaTypeName;

	/**
	 * 货区编码
	 */
	private String goodsAreaCode;

	/**
	 * 货区名称
	 */
	private String goodsAreaName;

	/**
	 * 货量
	 */
	private BigDecimal goodsVolume;

	/**
	 * 货区容量
	 */
	private BigDecimal goodsAreaVolume;

	/**
	 * 货区密度
	 */
	private BigDecimal density;

	/**
	 * 统计日期 yyyy-MM-dd
	 */
	private Date statisticDate;

	/**
	 * 统计时间点；01~23
	 */
	private String statisticTimePoint;

	/**
	 * 统计月份 yyyy-MM
	 */
	private String statisticMonth;

	public String getStatisticTimePoint() {
		return statisticTimePoint;
	}

	public void setStatisticTimePoint(String statisticTimePoint) {
		this.statisticTimePoint = statisticTimePoint;
	}

	public String getStatisticMonth() {
		return statisticMonth;
	}

	public void setStatisticMonth(String statisticMonth) {
		this.statisticMonth = statisticMonth;
	}

	public Date getStatisticDate() {
		return statisticDate;
	}

	public void setStatisticDate(Date statisticDate) {
		this.statisticDate = statisticDate;
	}

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public String getTransferCenterName() {
		return transferCenterName;
	}

	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}

	public String getGoodsAreaTypeCode() {
		return goodsAreaTypeCode;
	}

	public void setGoodsAreaTypeCode(String goodsAreaTypeCode) {
		this.goodsAreaTypeCode = goodsAreaTypeCode;
	}

	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	public String getGoodsAreaTypeName() {
		return goodsAreaTypeName;
	}

	public void setGoodsAreaTypeName(String goodsAreaTypeName) {
		this.goodsAreaTypeName = goodsAreaTypeName;
	}

	public String getGoodsAreaName() {
		return goodsAreaName;
	}

	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}

	public BigDecimal getGoodsVolume() {
		return goodsVolume;
	}

	public void setGoodsVolume(BigDecimal goodsVolume) {
		this.goodsVolume = goodsVolume;
	}

	public BigDecimal getGoodsAreaVolume() {
		return goodsAreaVolume;
	}

	public void setGoodsAreaVolume(BigDecimal goodsAreaVolume) {
		this.goodsAreaVolume = goodsAreaVolume;
	}

	public BigDecimal getDensity() {
		return density;
	}

	public void setDensity(BigDecimal density) {
		this.density = density;
	}

	@Override
	public String toString() {
		return "GoodsAreaDensityEntity [transferCenterCode="
				+ transferCenterCode + ", transferCenterName="
				+ transferCenterName + ", goodsAreaTypeCode="
				+ goodsAreaTypeCode + ", goodsAreaTypeName="
				+ goodsAreaTypeName + ", goodsAreaCode=" + goodsAreaCode
				+ ", goodsAreaName=" + goodsAreaName + ", goodsVolume="
				+ goodsVolume + ", goodsAreaVolume=" + goodsAreaVolume
				+ ", density=" + density + ", statisticDate=" + statisticDate
				+ ", statisticTimePoint="
				+ String.format("%1$tF", statisticDate) + ", statisticMonth="
				+ statisticMonth + "]";
	}

}
