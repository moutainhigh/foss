/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/vo/QueryMemberDialogVo.java
 * 
 * FILE NAME        	: QueryMemberDialogVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-creating
 * PACKAGE NAME: com.deppon.foss.module.pickup.creating.client.vo
 * FILE    NAME: QueryMemberDialogBean.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.common.client.vo;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 客户信息VO类
 * @author 026123-foss-lifengteng
 * @date 2012-10-20 下午5:54:19
 */
public class QueryMemberDialogVo {

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 电话
     */
    private String phone;

    /**
     * 手机
     */
    private String mobilePhone;

    /**
     * 地址
     */
    private String address;
    
    /**
     * 地址
     */
    private String addressNote;
    
    /**
     * 客户编码id
     */
    private String custId;
    
    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 联系人编码
     */
    private String consignorCode;

    /**
     * 身份证号
     */
    private String identityCard;

    /**
     * 合同编号
     */
    private String auditNo;

    /**
     * 生效时间
     */
    private Date validTime;

    /**
     * 失效时间
     */
    private Date invalidTime;
    
    /**
     * 结算付款(是否月结)
     */
    private Boolean chargeMode;
    
    /**
     * 信用额度
     */
    private BigDecimal creditAmount;
    
    /**
     * 联系人id
     */
    private String consignorId;
    
    /**
     * 是否选中
     */
    private Boolean selected = false;
    
    /**
     * 客户类型
     */
    private String customerType;
    
	/**
	 * 区域编码
	 */
	private String countyCode;

	/**
	 * 城市编码
	 */
	private String cityCode;

	/**
	 * 省份编码
	 */
	private String provinceCode;
	
	/**
	 * 接送货地址ID
	 */
	private String addressId;
	
	/**
	 * 优惠类型.
	 */
	private String preferentialType;
    
	/**
	 * 部门名称
	 */
	private String deptName;
	
	//发票标记
	private String invoice;
    
	/**
	 * 是否是大客户
	 */
	private String isBigCustomer;
	
	/**
	 * DMANA-2815:添加大客户标识
	 * @author 200945-wutao
	 * 是否是大客户
	 * @date 2014-10-27
	 * 
	 * 业务逻辑：
	 * 	为了区别零担和快递大客户的标识
	 */
	private String isExpressBigCustomer;

     /** 
     * 精准代收liyongfei-DMANA-2352
     */
    private String accurateCollection;
    
    /**
	  * 客户地址备注信息
	  */
	 private String custAddrRemark;
	 
	 /**
	  * 客户接送货地址备注信息
	  */
	 private String addressRemark;
    
    /**
     * 是否统一结算
     */
    private String centralizedSettlement;
    

    /**
     * 合同部门标杆编码
     */
    private String contractOrgCode;
    /**
     * 合同部门标杆名称
     */
    private String contractOrgName;
    /**
     * 催款部门标杆编码
     */
    private String reminderOrgCode;
    
    /**
	 * 是否电商尊享
	 * @return
	 */
	 private  String isElectricityToEnjoy;
	 
	 /**
	  * 是否精准包裹
	  */
	 private String isAccuratePackage;
	 
    /**
     * 地址
     */
    private String addressInfo;
	 
	 
    /**===================================
     * 属性的get/set方法
     **===================================*/
	
	/**
	 * @return the linkman .
	 */
	public String getLinkman() {
		return linkman;
	}

	
	public String getAccurateCollection() {
		return accurateCollection;
	}


	public void setAccurateCollection(String accurateCollection) {
		this.accurateCollection = accurateCollection;
	}


	public String getPreferentialType() {
		return preferentialType;
	}


	public void setPreferentialType(String preferentialType) {
		this.preferentialType = preferentialType;
	}


	/**
	 *@param linkman the linkman to set.
	 */
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	
	/**
	 * @return the phone .
	 */
	public String getPhone() {
		return phone;
	}

	
	/**
	 *@param phone the phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	/**
	 * @return the mobilePhone .
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	
	/**
	 *@param mobilePhone the mobilePhone to set.
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	
	/**
	 * @return the address .
	 */
	public String getAddress() {
		return address;
	}

	
	/**
	 *@param address the address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	
	/**
	 * @return the custId .
	 */
	public String getCustId() {
		return custId;
	}

	
	/**
	 *@param custId the custId to set.
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	
	/**
	 * @return the customerCode .
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	
	/**
	 *@param customerCode the customerCode to set.
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	
	/**
	 * @return the customerName .
	 */
	public String getCustomerName() {
		return customerName;
	}

	
	/**
	 *@param customerName the customerName to set.
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	
	/**
	 * @return the consignorCode .
	 */
	public String getConsignorCode() {
		return consignorCode;
	}

	
	/**
	 *@param consignorCode the consignorCode to set.
	 */
	public void setConsignorCode(String consignorCode) {
		this.consignorCode = consignorCode;
	}

	
	/**
	 * @return the identityCard .
	 */
	public String getIdentityCard() {
		return identityCard;
	}

	
	/**
	 *@param identityCard the identityCard to set.
	 */
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	
	/**
	 * @return the auditNo .
	 */
	public String getAuditNo() {
		return auditNo;
	}

	
	/**
	 *@param auditNo the auditNo to set.
	 */
	public void setAuditNo(String auditNo) {
		this.auditNo = auditNo;
	}

	
	/**
	 * @return the validTime .
	 */
	public Date getValidTime() {
		return validTime;
	}

	
	/**
	 *@param validTime the validTime to set.
	 */
	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	
	/**
	 * @return the invalidTime .
	 */
	public Date getInvalidTime() {
		return invalidTime;
	}

	
	/**
	 *@param invalidTime the invalidTime to set.
	 */
	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	
	/**
	 * @return the chargeMode .
	 */
	public Boolean getChargeMode() {
		return chargeMode;
	}

	
	/**
	 *@param chargeMode the chargeMode to set.
	 */
	public void setChargeMode(Boolean chargeMode) {
		this.chargeMode = chargeMode;
	}

	
	/**
	 * @return the creditAmount .
	 */
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	
	/**
	 *@param creditAmount the creditAmount to set.
	 */
	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	
	/**
	 * @return the consignorId .
	 */
	public String getConsignorId() {
		return consignorId;
	}

	
	/**
	 *@param consignorId the consignorId to set.
	 */
	public void setConsignorId(String consignorId) {
		this.consignorId = consignorId;
	}


	

	/**
	 * 若为空则设置默认值为false
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-22 下午4:42:37
	 */
	public Boolean getSelected() {
		if(selected == null){
			return false;
		}
		return selected;
	}


	
	/**
	 *@param selected the selected to set.
	 */
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}


	
	/**
	 * @return the customerType .
	 */
	public String getCustomerType() {
		return customerType;
	}


	
	/**
	 *@param customerType the customerType to set.
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}


	
	/**
	 * @return the countyCode .
	 */
	public String getCountyCode() {
		return countyCode;
	}


	
	/**
	 *@param countyCode the countyCode to set.
	 */
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	
	/**
	 * @return the cityCode .
	 */
	public String getCityCode() {
		return cityCode;
	}
	
	/**
	 *@param cityCode the cityCode to set.
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	/**
	 * @return the provinceCode .
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 *@param provinceCode the provinceCode to set.
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}


	
	/**
	 * @return the addressId .
	 */
	public String getAddressId() {
		return addressId;
	}


	
	/**
	 *@param addressId the addressId to set.
	 */
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}


	/**
	 * 部门名称
	 */
	public String getDeptName() {
		return deptName;
	}


	/**
	 * 部门名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public String getInvoice() {
		return invoice;
	}


	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
   

	public String getIsBigCustomer() {
		return isBigCustomer;
	}


	public void setIsBigCustomer(String isBigCustomer) {
		this.isBigCustomer = isBigCustomer;
	}


	//GETTER AND SETTER
	public String getIsExpressBigCustomer() {
		return isExpressBigCustomer;
	}
	public void setIsExpressBigCustomer(String isExpressBigCustomer) {
		this.isExpressBigCustomer = isExpressBigCustomer;
	}
	//END


	public String getAddressNote() {
		return addressNote;
	}


	public void setAddressNote(String addressNote) {
		this.addressNote = addressNote;
	}


	public String getCustAddrRemark() {
		return custAddrRemark;
	}


	public void setCustAddrRemark(String custAddrRemark) {
		this.custAddrRemark = custAddrRemark;
	}


	public String getAddressRemark() {
		return addressRemark;
	}


	public void setAddressRemark(String addressRemark) {
		this.addressRemark = addressRemark;
	}

	/**
	 * 特安客户保价上限
	 * @author 218371-foss-zhaoyanjun
	 * @date 2014-10-11 上午08:11
	 */
	private BigDecimal vipInsuranceAmount;
	
	public BigDecimal getVipInsuranceAmount() {
		return vipInsuranceAmount;
	}

	public void setVipInsuranceAmount(BigDecimal vipInsuranceAmount) {
		this.vipInsuranceAmount = vipInsuranceAmount;
	}

	
	public String getCentralizedSettlement() {
		return centralizedSettlement;
	}


	public void setCentralizedSettlement(String centralizedSettlement) {
		this.centralizedSettlement = centralizedSettlement;
	}


	public String getContractOrgCode() {
		return contractOrgCode;
	}


	public void setContractOrgCode(String contractOrgCode) {
		this.contractOrgCode = contractOrgCode;
	}


	public String getContractOrgName() {
		return contractOrgName;
	}


	public void setContractOrgName(String contractOrgName) {
		this.contractOrgName = contractOrgName;
	}


	public String getReminderOrgCode() {
		return reminderOrgCode;
	}


	public void setReminderOrgCode(String reminderOrgCode) {
		this.reminderOrgCode = reminderOrgCode;
	}
	/*
	 * 客户分群
	 * zhangchengfu
	 */
	private String flabelleavemonth;

	public String getFlabelleavemonth() {
		return flabelleavemonth;
	}

	public void setFlabelleavemonth(String flabelleavemonth) {
		this.flabelleavemonth = flabelleavemonth;
	}

	public String getIsElectricityToEnjoy() {
		return isElectricityToEnjoy;
	}


	public void setIsElectricityToEnjoy(String isElectricityToEnjoy) {
		this.isElectricityToEnjoy = isElectricityToEnjoy;
	}


	public String getIsAccuratePackage() {
		return isAccuratePackage;
	}


	public String getAddressInfo() {
		return addressInfo;
	}


	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}


	public void setIsAccuratePackage(String isAccuratePackage) {
		this.isAccuratePackage = isAccuratePackage;
	}
}