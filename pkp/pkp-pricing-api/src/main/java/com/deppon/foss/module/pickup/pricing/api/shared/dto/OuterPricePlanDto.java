package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OuterPricePlanDto implements Serializable {
	private static final long serialVersionUID = 1L;
	//ID
	private String outerPriceId;
	
	//版本名称
	private String name;
	
	//目的站
	private String partialLineCode;
	
	//目的站
	private String partialLineName;
	
	//最终外场
	private String outFieldCode;
	
	//最终外场Name
	private String outFieldName;
	
	//产品；类型
	private String productCode;
	
	//激活时间
	private Date beginTime;
	
	//结束时间
	private Date endTime;
	
	//修改时间
	private Date modifyDate;
	
	//修改人
	private String modifyUser;
	
	//激活状态
	private String active;
	
	//备注
	private String remark;
	
	//版本
	private String version;
	
	//重货费率
	private BigDecimal weightFeeRate;
	
	//轻货费率
	private BigDecimal volumeFeeRate;
	
	//最低一票
	private String minFee;
	
	//所在省份编码
	private String provCode;
	
	//所在城市编码
	private String cityCode;
	
	//所在区县
	private String countyCode;
	
	//所在省份名称
	private String provName;
	
	//所在城市名称
	private String cityName;
	
	//所在区县名称
	private String countyName;
	

	public String getOuterPriceId() {
		return outerPriceId;
	}

	public void setOuterPriceId(String outerPriceId) {
		this.outerPriceId = outerPriceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPartialLineCode() {
		return partialLineCode;
	}

	public void setPartialLineCode(String partialLineCode) {
		this.partialLineCode = partialLineCode;
	}

	public String getPartialLineName() {
		return partialLineName;
	}

	public void setPartialLineName(String partialLineName) {
		this.partialLineName = partialLineName;
	}

	public String getOutFieldCode() {
		return outFieldCode;
	}

	public void setOutFieldCode(String outFieldCode) {
		this.outFieldCode = outFieldCode;
	}

	public String getOutFieldName() {
		return outFieldName;
	}

	public void setOutFieldName(String outFieldName) {
		this.outFieldName = outFieldName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public BigDecimal getWeightFeeRate() {
		return weightFeeRate;
	}

	public void setWeightFeeRate(BigDecimal weightFeeRate) {
		this.weightFeeRate = weightFeeRate;
	}

	public BigDecimal getVolumeFeeRate() {
		return volumeFeeRate;
	}

	public void setVolumeFeeRate(BigDecimal volumeFeeRate) {
		this.volumeFeeRate = volumeFeeRate;
	}

	public String getMinFee() {
		return minFee;
	}

	public void setMinFee(String minFee) {
		this.minFee = minFee;
	}

	public String getProvCode() {
		return provCode;
	}

	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	
	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
}
