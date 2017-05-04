package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class QuantitySta4ChartDto implements Serializable {

	private static final long serialVersionUID = -6041317333876554649L;

	private String orgCode;

	private String relevantOrgCode;

	private String relevantOrgName;

	private Date staDate;

	private Integer staHh;

	private BigDecimal weightActual;

	private BigDecimal volumeActual;

	private BigDecimal weightFcst;

	private BigDecimal volumeFcst;

	private BigDecimal weightWarn;

	private BigDecimal volumeWarn;

	private String type;

	private String particularType;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParticularType() {
		return particularType;
	}

	public void setParticularType(String particularType) {
		this.particularType = particularType;
	}

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

	public BigDecimal getWeightActual() {
		return weightActual;
	}

	public void setWeightActual(BigDecimal weightActual) {
		this.weightActual = weightActual;
	}

	public BigDecimal getVolumeActual() {
		return volumeActual;
	}

	public void setVolumeActual(BigDecimal volumeActual) {
		this.volumeActual = volumeActual;
	}

	public BigDecimal getWeightFcst() {
		return weightFcst;
	}

	public void setWeightFcst(BigDecimal weightFcst) {
		this.weightFcst = weightFcst;
	}

	public BigDecimal getVolumeFcst() {
		return volumeFcst;
	}

	public void setVolumeFcst(BigDecimal volumeFcst) {
		this.volumeFcst = volumeFcst;
	}

	public BigDecimal getWeightWarn() {
		return weightWarn;
	}

	public void setWeightWarn(BigDecimal weightWarn) {
		this.weightWarn = weightWarn;
	}

	public BigDecimal getVolumeWarn() {
		return volumeWarn;
	}

	public void setVolumeWarn(BigDecimal volumeWarn) {
		this.volumeWarn = volumeWarn;
	}

	public String getRelevantOrgName() {
		return relevantOrgName;
	}

	public void setRelevantOrgName(String relevantOrgName) {
		this.relevantOrgName = relevantOrgName;
	}

}
