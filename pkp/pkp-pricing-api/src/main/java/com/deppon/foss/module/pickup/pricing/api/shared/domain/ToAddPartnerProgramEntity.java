package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ToAddPartnerProgramEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	//ID
	private String toAddPartnerProgramid;
	
	//方案名称
	private String planName;
	
	//目的站网点
	private String name;
	
	//目的站网点编号
	private String orgCode;
	
	//运输类型
	private String transportFlag;
	
	//重货费率
	private BigDecimal heavyPrice;
	
	//轻货费率
	private BigDecimal lightPrice;
	
	//最低一票
	private BigDecimal lowestPrice;
	
	//开始时间
	private Date beginTime;
		
	//截止时间
	private Date endTime;
	
	//状态
	private String active;
	
	//创建时间
	private Date createTime;
	
	//创建人
	private String createUserCode;
	
	//创建人组织
	private String createOrgCode;
	
	// 修改时间
	private Date modifyTime;

	// 修改人
	private String modifyUserCode;

	// 修改人组织
	private String modifyOrgCode;
	
	//币种
	private String currencyCode;	
	
	//版本号
	private Long versionNo;
	
	//版本
	private String version;

	//目的站点模式NETWORK_MODEL
	private String networkModel;
	
	private String networkModelName;
	
	


	public String getNetworkModelName() {
		return networkModelName;
	}

	public void setNetworkModelName(String networkModelName) {
		this.networkModelName = networkModelName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getToAddPartnerProgramid() {
		return toAddPartnerProgramid;
	}

	public void setToAddPartnerProgramid(String toAddPartnerProgramid) {
		this.toAddPartnerProgramid = toAddPartnerProgramid;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getTransportFlag() {
		return transportFlag;
	}

	public void setTransportFlag(String transportFlag) {
		this.transportFlag = transportFlag;
	}

	public BigDecimal getHeavyPrice() {
		return heavyPrice;
	}

	public void setHeavyPrice(BigDecimal heavyPrice) {
		this.heavyPrice = heavyPrice;
	}

	public BigDecimal getLightPrice() {
		return lightPrice;
	}

	public void setLightPrice(BigDecimal lightPrice) {
		this.lightPrice = lightPrice;
	}

	public BigDecimal getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(BigDecimal lowestPrice) {
		this.lowestPrice = lowestPrice;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public String getNetworkModel() {
		return networkModel;
	}

	public void setNetworkModel(String networkModel) {
		this.networkModel = networkModel;
	}

	
	
	
}
