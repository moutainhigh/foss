package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CityMarketPlanDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7042287151508262336L;

	private String id; //id

    private String code;//活动编码

    private String name;//活动名称

    private String description;//活动描述

    private Long versionNo;//版本号

    private String active;//是否有效

    private String nationCode;

    private String provCode;

    private String cityCode;//活动城市编码

    private String countyCode;

    private BigDecimal shippingdiscount;//运费折扣

    private BigDecimal bfRate;//保费

    private BigDecimal r1Rate;//几日退

    private BigDecimal r3Rate;//三日退

    private BigDecimal raRate;//审核退

    private Long minFee;

    private Long maxFee;

    private Date beginTime;//开始时间

    private Date endTime;//结束时间

    private Date createTime;

    private Date modifyTime;

    private String createUserCode;

    private String modifyUserCode;

    private String createOrgCode;

    private String modifyOrgCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public BigDecimal getShippingdiscount() {
		return shippingdiscount;
	}

	public void setShippingdiscount(BigDecimal shippingdiscount) {
		this.shippingdiscount = shippingdiscount;
	}

	public BigDecimal getBfRate() {
		return bfRate;
	}

	public void setBfRate(BigDecimal bfRate) {
		this.bfRate = bfRate;
	}

	public BigDecimal getR1Rate() {
		return r1Rate;
	}

	public void setR1Rate(BigDecimal r1Rate) {
		this.r1Rate = r1Rate;
	}

	public BigDecimal getR3Rate() {
		return r3Rate;
	}

	public void setR3Rate(BigDecimal r3Rate) {
		this.r3Rate = r3Rate;
	}

	public BigDecimal getRaRate() {
		return raRate;
	}

	public void setRaRate(BigDecimal raRate) {
		this.raRate = raRate;
	}

	public Long getMinFee() {
		return minFee;
	}

	public void setMinFee(Long minFee) {
		this.minFee = minFee;
	}

	public Long getMaxFee() {
		return maxFee;
	}

	public void setMaxFee(Long maxFee) {
		this.maxFee = maxFee;
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

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}
    
    
}
