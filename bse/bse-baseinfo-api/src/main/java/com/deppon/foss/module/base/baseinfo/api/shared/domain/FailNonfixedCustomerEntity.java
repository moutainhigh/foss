package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class FailNonfixedCustomerEntity extends BaseEntity {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4889141310460562147L;
	
	private String id;
	
	//客户编码
	private String customerCode;
	
	//客户名称
	private String customerName;
	
	//联系人编码
	private String linkManCode;
	
	//联系人名称
	private String linkManName;
	
	//接送货地址
	private String address;
	
	//客户手机
	private String mobile;
	
	//客户固定电话
	private String cellPhone;
	
	//客户省
	private String proCode;
	
	//客户市
	private String cityCode;
	
	//客户区县
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
    
    //创建时间
    private Date createTime;
    
    //推送失败次数（超过5次不成功 就不推送）
    private int failCount;
    
    private String errorInfo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
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

	public String getCustomerAttributes() {
		return customerAttributes;
	}

	public void setCustomerAttributes(String customerAttributes) {
		this.customerAttributes = customerAttributes;
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

	public String getOwnCustId() {
		return ownCustId;
	}

	public void setOwnCustId(String ownCustId) {
		this.ownCustId = ownCustId;
	}

	public String getCustAddrRemark() {
		return custAddrRemark;
	}

	public void setCustAddrRemark(String custAddrRemark) {
		this.custAddrRemark = custAddrRemark;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
	public FailNonfixedCustomerEntity(){
		
	}

	public FailNonfixedCustomerEntity(String customerCode, String customerName,
			String linkManCode, String linkManName, String address,
			String mobile, String cellPhone, String proCode, String cityCode,
			String countyCode, String unifiedCode, String businessType,
			String customerAttributes, String oneLevelIndustry,
			String twoLevelIndustry, String ownCustId, String custAddrRemark,
			String custType, Date createTime, int failCount) {
		super();
		this.customerCode = customerCode;
		this.customerName = customerName;
		this.linkManCode = linkManCode;
		this.linkManName = linkManName;
		this.address = address;
		this.mobile = mobile;
		this.cellPhone = cellPhone;
		this.proCode = proCode;
		this.cityCode = cityCode;
		this.countyCode = countyCode;
		this.unifiedCode = unifiedCode;
		this.businessType = businessType;
		this.customerAttributes = customerAttributes;
		this.oneLevelIndustry = oneLevelIndustry;
		this.twoLevelIndustry = twoLevelIndustry;
		this.ownCustId = ownCustId;
		this.custAddrRemark = custAddrRemark;
		this.custType = custType;
		this.createTime = createTime;
		this.failCount = failCount;
	}
    
    

}
