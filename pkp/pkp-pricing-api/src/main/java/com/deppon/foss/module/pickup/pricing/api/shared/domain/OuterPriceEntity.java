package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class OuterPriceEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	//ID
	private String outerPriceId;
	
	//名称
	private String name;
	
	//版本
	private Long versionNo;
	
	//状态
	private String active;
	
	//目的站
	private String partialLineCode;
	
	//产品编码
	private String productCode;
	
	//外发外场
	private String outFieldCode;
	
	//所在国家编码
	private String nationCode;
	
	//所在省份编码
	private String provCode;
	
	//所在城市编码
	private String cityCode;
	
	//所在区县
	private String countyCode;
	
	//开始时间
	private Date beginTime;
	
	//截止时间
	private Date endTime;
	
	//创建时间
	private Date createTime;
	
	//修改时间
	private Date modifyTime;
	
	//创建人
	private String createUserCode;
	
	//创建人组织
	private String createOrgCode;
	
	//修改人
	private String modifyUserCode;
	
	//修改人组织
	private String modifyOrgCode;
	
	//重货费率
	private BigDecimal weightFeeRate;
	
	//轻货费率
	private BigDecimal volumeFeeRate;
	
	//最低一票
	private Long minFee;
	
	//描述
	private String remark;
	
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

	public String getActive() {
		return active;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getPartialLineCode() {
		return partialLineCode;
	}

	public void setPartialLineCode(String partialLineCode) {
		this.partialLineCode = partialLineCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getOutFieldCode() {
		return outFieldCode;
	}

	public void setOutFieldCode(String outFieldCode) {
		this.outFieldCode = outFieldCode;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
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

	public Long getMinFee() {
		return minFee;
	}

	public void setMinFee(Long minFee) {
		this.minFee = minFee;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
