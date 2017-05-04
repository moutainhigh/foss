package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 接收元旦开单界面发货客户录入的基本信息
 * 
 * @author 078816
 * @date  2014-03-19
 *
 */
public class NonfixedCustomerAssociatedInformationEntity extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4889141310460562147L;
	
	//发货客户编码
	private String customerCode;
	
	//发货客户名称
	private String customerName;
	
	//发货联系人编码
	private String linkManCode;
	
	//发货联系人名称
	private String linkManName;
	
	//接送货地址
	private String address;
	
	//发货客户手机
	private String cellPhone;
	
	//发货客户固定电话
	private String mobile;
	
	//发货客户省
	private String proCode;
	
	//发货客户市
	private String cityCode;
	
	//发货客户区县
	private String countyCode;
	
	//客户所属部门编码
	private String unifiedCode;
	
	//业务类别
	private String businessType;
	
	//客户属性
	private String customerAttributes;
	
	 /**
     * 一级行业
     */
    private String oneLevelIndustry;
    
    /**
     * 二级行业
     */
    private String twoLevelIndustry;
    
    /**
     * 所属客户的fossId
     */
    private String ownCustId;
    
    /**
     * 客户地址备注信息
     */
    private String custAddrRemark;
    
    /**
     *客户类型，潜客，散客，固定客户
     */
    private String custType;

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
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

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getLinkManCode() {
		return linkManCode;
	}

	public void setLinkManCode(String linkManCode) {
		this.linkManCode = linkManCode;
	}

	public String getLinkManName() {
		return linkManName;
	}

	public void setLinkManName(String linkManName) {
		this.linkManName = linkManName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUnifiedCode() {
		return unifiedCode;
	}

	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getOneLevelIndustry() {
		return oneLevelIndustry;
	}

	public void setOneLevelIndustry(String oneLevelIndustry) {
		this.oneLevelIndustry = oneLevelIndustry;
	}

	public String getTwoLevelIndustry() {
		return twoLevelIndustry;
	}

	public void setTwoLevelIndustry(String twoLevelIndustry) {
		this.twoLevelIndustry = twoLevelIndustry;
	}

	public String getCustomerAttributes() {
		return customerAttributes;
	}

	public void setCustomerAttributes(String customerAttributes) {
		this.customerAttributes = customerAttributes;
	}

	/**
	 * @return the ownCustId
	 */
	public String getOwnCustId() {
		return ownCustId;
	}

	/**
	 * @param ownCustId the ownCustId to set
	 */
	public void setOwnCustId(String ownCustId) {
		this.ownCustId = ownCustId;
	}

	/**
	 * @return the custType
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * @param custType the custType to set
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getCustAddrRemark() {
		return custAddrRemark;
	}

	public void setCustAddrRemark(String custAddrRemark) {
		this.custAddrRemark = custAddrRemark;
	}
	
	
}
