package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class QuantityStaFcstEntity extends BaseEntity {

	private static final long serialVersionUID = -2813687836599300207L;

	private String orgCode;

	private String relevantOrgCode;

	private String dataType;

	private BigDecimal weightTotal;

	private BigDecimal volumeTotal;

	private Integer qtyTotal;

	private BigDecimal weightNoBilling;

	private BigDecimal volumeNoBilling;

	private Integer qtyNoBilling;

	private Date createTime;

	private Date dataBelongDate;

	private Date staDate;

	private Integer staHh;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getRelevantOrgCode() {
		return relevantOrgCode;
	}

	public void setRelevantOrgCode(String relevantOrgCode) {
		this.relevantOrgCode = relevantOrgCode;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public BigDecimal getWeightTotal() {
		return weightTotal;
	}

	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}

	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}

	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	public Integer getQtyTotal() {
		return qtyTotal;
	}

	public void setQtyTotal(Integer qtyTotal) {
		this.qtyTotal = qtyTotal;
	}

	public BigDecimal getWeightNoBilling() {
		return weightNoBilling;
	}

	public void setWeightNoBilling(BigDecimal weightNoBilling) {
		this.weightNoBilling = weightNoBilling;
	}

	public BigDecimal getVolumeNoBilling() {
		return volumeNoBilling;
	}

	public void setVolumeNoBilling(BigDecimal volumeNoBilling) {
		this.volumeNoBilling = volumeNoBilling;
	}

	public Integer getQtyNoBilling() {
		return qtyNoBilling;
	}

	public void setQtyNoBilling(Integer qtyNoBilling) {
		this.qtyNoBilling = qtyNoBilling;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDataBelongDate() {
		return dataBelongDate;
	}

	public void setDataBelongDate(Date dataBelongDate) {
		this.dataBelongDate = dataBelongDate;
	}

	public Date getStaDate() {
		return staDate;
	}

	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}

	public Integer getStaHh() {
		return staHh;
	}

	public void setStaHh(Integer staHh) {
		this.staHh = staHh;
	}

}
