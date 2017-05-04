package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;

public class OuterPriceDetailDto implements Serializable {
	private static final long serialVersionUID = 1L;
	//ID
	private String outPriceId;
	
	//名称
	private String outerPriceName;
	
	//目的站
	private String partialLineCode;
	
	//国家
	private String nationCode;
	
	//省份
	private String provCode;
	
	//城市
	private String cityCode;
	
	//县区
	private String countyCode;
	
	//外发外场
	private String outFieldCode;
	
	//重货价格
	private String heavyPrice;
	
	//轻货价格
	private String lightPrice;
	
	//最低一票
	private String minimumOneTicket;
	
	//备注
	private String remark;

	public String getOutPriceId() {
		return outPriceId;
	}

	public void setOutPriceId(String outPriceId) {
		this.outPriceId = outPriceId;
	}

	public String getOuterPriceName() {
		return outerPriceName;
	}

	public void setOuterPriceName(String outerPriceName) {
		this.outerPriceName = outerPriceName;
	}

	public String getPartialLineCode() {
		return partialLineCode;
	}

	public void setPartialLineCode(String partialLineCode) {
		this.partialLineCode = partialLineCode;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
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

	public String getOutFieldCode() {
		return outFieldCode;
	}

	public void setOutFieldCode(String outFieldCode) {
		this.outFieldCode = outFieldCode;
	}

	public String getHeavyPrice() {
		return heavyPrice;
	}

	public void setHeavyPrice(String heavyPrice) {
		this.heavyPrice = heavyPrice;
	}

	public String getLightPrice() {
		return lightPrice;
	}

	public void setLightPrice(String lightPrice) {
		this.lightPrice = lightPrice;
	}

	public String getMinimumOneTicket() {
		return minimumOneTicket;
	}

	public void setMinimumOneTicket(String minimumOneTicket) {
		this.minimumOneTicket = minimumOneTicket;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
