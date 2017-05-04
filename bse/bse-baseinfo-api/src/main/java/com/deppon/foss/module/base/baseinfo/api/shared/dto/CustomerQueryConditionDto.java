/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/CustomerQueryConditionDto.java
 * 
 * FILE NAME        	: CustomerQueryConditionDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 查询客户相关信息输入参数DTO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-20 下午1:44:39
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-20 下午1:44:39
 * @since
 * @version
 */
public class CustomerQueryConditionDto implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2528202466724410576L;

	/**
	 * ********* 运单传给综合的查询信息的封装 *********************.
	 */

	/**
	 * 客户编码.
	 */
	private String custCode;

	/**
	 * 客户名称.
	 */
	private String custName;

	/**
	 * 是否有效.
	 */
	private String active;

	/**
	 * 联系人编码.
	 */
	private String linkmanCode;

	/**
	 * 联系人姓名.
	 */
	private String contactName;

	/**
	 * 联系人办公电话.
	 */
	private String contactPhone;

	/**
	 * 联系人移动电话.
	 */
	private String mobilePhone;

	/**
	 * 身份证号.
	 */
	private String idCard;

	/**
	 * 客户所属部门编码.
	 */
	private String deptCode;

	/**
	 * 发/收货人地址（业务往来常用地址）.
	 */
	private String address;

	/**
	 * 是否查询全公司客户信息.
	 */
	private boolean exactQuery;

	/**
	 * ********* 返回给运单的客户信息的封装 *********************.
	 */
	/**
	 * 客户ID
	 */
	private String custId;

	/**
	 * 联系人ID.
	 */
	private String linkmanId;

	/**
	 * 客户信息用额度.
	 */
	private BigDecimal creditAmount;

	/**
	 * 付款方式.
	 */
	private String chargeType;

	/**
	 * 合同编号.
	 */
	private String bargainCode;

	/**
	 * 合同起始日期.
	 */
	private Date beginTime;

	/**
	 * 合同到期日期.
	 */
	private Date endTime;

	/**
	 * 客户类型（CRM正式客户、CRM散客）.
	 */
	private String customerType;

	/**
	 * 区域编码.
	 */
	private String countyCode;

	/**
	 * 城市编码.
	 */
	private String cityCode;

	/**
	 * 省份编码.
	 */
	private String provinceCode;

	/**
	 * 接送货地址ID.
	 */
	private String addressId;

	/**
	 * 优惠类型.
	 */
	private String preferentialType;

	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 修改时间
	 */
	private Date modifyDate;

	/**
	 * 是否财务作废
	 */
	private String isDelete;

	/**
	 * 部门名称（冗余字段，减少前台查询，节省网络带宽）
	 */
	private String deptName;

	/** 快递开单新增属性 **/
	/**
	 * 快递价格版本时间
	 */
	private Date exPriceVersionDate;
	/**
	 * 快递优惠类型
	 */
	private String exPreferentialType;

	/**
	 * 快递结款方式
	 */
	private String exPayWay;

	/**
	 * 发票标记
	 */
	private String invoiceType;

	/**
	 * 发票标记时间
	 */
	private Date invoiceDate;

	/**
	 * 合同的CRM_ID
	 */
	private BigDecimal bargainCrmId;

	/**
	 * 发件人短信
	 */
	private String shipperSms;

	/**
	 * 收件人短信
	 */
	private String receiverSms;

	/**
	 * 精确代收
	 */
	private String accurateCollection;

	// 078816 2014-03-17
	/**
	 * 是否大客户标记
	 */
	private String isLargeCustomers;

	/**
	 * 132599 2014-10-28 是否大客户标记
	 */
	private String isExpressBigCust;

	/**
	 * 客户性质
	 */
	private String customserNature;

	/**
	 * 业务类别
	 */
	private String businessType;

	/**
	 * 一级行业
	 */
	private String oneLevelIndustry;

	/**
	 * 二级行业
	 */
	private String twoLevelIndustry;

	/**
	 * 是否电子客单大客户
	 * 
	 */
	private String isElecBillBigCust;

	/**
	 * 特安上限
	 */
	private Integer teanLimit;

	/**
	 * 客户地址备注信息
	 */
	private String custAddrRemark;

	/**
	 * 客户接送货地址备注信息
	 */
	private String addressRemark;
	/**
	 * 是否电商尊享
	 * 
	 * @return
	 */
	private String ifElecEnjoy;

	/**
	 * 是否精准计算
	 */
	private String isAccuratePrice;
	
	/**
	 * 收货人手机号码弹窗地址显示信息
	 */
	private String addressInfo;

	public String getIsAccuratePrice() {
		return isAccuratePrice;
	}

	public void setIsAccuratePrice(String isAccuratePrice) {
		this.isAccuratePrice = isAccuratePrice;
	}

	
	 /**
	  * 是否精准电商
	  */
	 private String isAccuratePackage;
	

	public String getIsAccuratePackage() {
		return isAccuratePackage;
	}

	public void setIsAccuratePackage(String isAccuratePackage) {
		this.isAccuratePackage = isAccuratePackage;
	}

	public String getIfElecEnjoy() {
		return ifElecEnjoy;
	}

	public void setIfElecEnjoy(String ifElecEnjoy) {
		this.ifElecEnjoy = ifElecEnjoy;
	}

	/*
	 * 客户分群 2015-07-10 添加 css 261997
	 */
	private String flabelleavemonth;

	public String getFlabelleavemonth() {
		return flabelleavemonth;
	}

	public void setFlabelleavemonth(String flabelleavemonth) {
		this.flabelleavemonth = flabelleavemonth;
	}

	public String getIsLargeCustomers() {
		return isLargeCustomers;
	}

	public void setIsLargeCustomers(String isLargeCustomers) {
		this.isLargeCustomers = isLargeCustomers;
	}

	public String getCustomserNature() {
		return customserNature;
	}

	public void setCustomserNature(String customserNature) {
		this.customserNature = customserNature;
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

	public BigDecimal getBargainCrmId() {
		return bargainCrmId;
	}

	public void setBargainCrmId(BigDecimal bargainCrmId) {
		this.bargainCrmId = bargainCrmId;
	}

	/**
	 * ********* set和get方法 *********************.
	 * 
	 * @return the ********* 运单传给综合的查询信息的封装 *********************
	 */

	public Date getExPriceVersionDate() {
		return exPriceVersionDate;
	}

	public void setExPriceVersionDate(Date exPriceVersionDate) {
		this.exPriceVersionDate = exPriceVersionDate;
	}

	public String getExPreferentialType() {
		return exPreferentialType;
	}

	public void setExPreferentialType(String exPreferentialType) {
		this.exPreferentialType = exPreferentialType;
	}

	public String getExPayWay() {
		return exPayWay;
	}

	public void setExPayWay(String exPayWay) {
		this.exPayWay = exPayWay;
	}

	/**
	 * 获取 客户编码.
	 * 
	 * @return the custCode
	 */
	public String getCustCode() {
		return custCode;
	}

	/**
	 * 设置 客户编码.
	 * 
	 * @param custCode
	 *            the custCode to set
	 */
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	/**
	 * 获取 优惠类型.
	 * 
	 * @return the preferentialType
	 */
	public String getPreferentialType() {
		return preferentialType;
	}

	/**
	 * 设置 优惠类型.
	 * 
	 * @param preferentialType
	 *            the preferentialType to set
	 */
	public void setPreferentialType(String preferentialType) {
		this.preferentialType = preferentialType;
	}

	/**
	 * 获取 客户名称.
	 * 
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * 设置 客户名称.
	 * 
	 * @param custName
	 *            the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * 获取 联系人姓名.
	 * 
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * 设置 联系人姓名.
	 * 
	 * @param contactName
	 *            the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * 获取 联系人办公电话.
	 * 
	 * @return the contactPhone
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * 设置 联系人办公电话.
	 * 
	 * @param contactPhone
	 *            the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * 获取 联系人移动电话.
	 * 
	 * @return the mobilePhone
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * 设置 联系人移动电话.
	 * 
	 * @param mobilePhone
	 *            the mobilePhone to set
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	/**
	 * 获取 身份证号.
	 * 
	 * @return the idCard
	 */
	public String getIdCard() {
		return idCard;
	}

	/**
	 * 设置 身份证号.
	 * 
	 * @param idCard
	 *            the idCard to set
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	/**
	 * 获取 客户所属部门编码.
	 * 
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * 设置 客户所属部门编码.
	 * 
	 * @param deptCode
	 *            the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * 获取 发/收货人地址.
	 * 
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 获取 联系人编码.
	 * 
	 * @return the 联系人编码
	 */
	public String getLinkmanCode() {
		return linkmanCode;
	}

	/**
	 * 设置 联系人编码.
	 * 
	 * @param linkmanCode
	 *            the new 联系人编码
	 */
	public void setLinkmanCode(String linkmanCode) {
		this.linkmanCode = linkmanCode;
	}

	/**
	 * 设置 发/收货人地址.
	 * 
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 判断是否 是否查询全公司客户信息.
	 * 
	 * @return the exactQuery .
	 */
	public boolean isExactQuery() {
		return exactQuery;
	}

	/**
	 * 设置 是否查询全公司客户信息.
	 * 
	 * @param exactQuery
	 *            the exactQuery to set.
	 */
	public void setExactQuery(boolean exactQuery) {
		this.exactQuery = exactQuery;
	}

	/**
	 * 获取 ********* 返回给运单的客户信息的封装 *********************.
	 * 
	 * @return the ********* 返回给运单的客户信息的封装 *********************
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * 设置 ********* 返回给运单的客户信息的封装 *********************.
	 * 
	 * @param custId
	 *            the new ********* 返回给运单的客户信息的封装 *********************
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * 获取 联系人ID.
	 * 
	 * @return the 联系人ID
	 */
	public String getLinkmanId() {
		return linkmanId;
	}

	/**
	 * 设置 联系人ID.
	 * 
	 * @param linkmanId
	 *            the new 联系人ID
	 */
	public void setLinkmanId(String linkmanId) {
		this.linkmanId = linkmanId;
	}

	/**
	 * 获取 客户信息用额度.
	 * 
	 * @return the 客户信息用额度
	 */
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	/**
	 * 设置 客户信息用额度.
	 * 
	 * @param creditAmount
	 *            the new 客户信息用额度
	 */
	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	/**
	 * 获取 付款方式.
	 * 
	 * @return the 付款方式
	 */
	public String getChargeType() {
		return chargeType;
	}

	/**
	 * 设置 付款方式.
	 * 
	 * @param chargeType
	 *            the new 付款方式
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	/**
	 * 获取 合同编号.
	 * 
	 * @return the 合同编号
	 */
	public String getBargainCode() {
		return bargainCode;
	}

	/**
	 * 设置 合同编号.
	 * 
	 * @param bargainCode
	 *            the new 合同编号
	 */
	public void setBargainCode(String bargainCode) {
		this.bargainCode = bargainCode;
	}

	/**
	 * 获取 合同起始日期.
	 * 
	 * @return the 合同起始日期
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * 设置 合同起始日期.
	 * 
	 * @param beginTime
	 *            the new 合同起始日期
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * 设置 合同到期日期.
	 * 
	 * @param endTime
	 *            the new 合同到期日期
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 获取 合同到期日期.
	 * 
	 * @return the 合同到期日期
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 获取 是否有效.
	 * 
	 * @return the 是否有效
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置 是否有效.
	 * 
	 * @param active
	 *            the new 是否有效
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * 获取 客户类型（CRM正式客户、CRM散客）.
	 * 
	 * @return the customerType .
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * 设置 客户类型（CRM正式客户、CRM散客）.
	 * 
	 * @param customerType
	 *            the customerType to set.
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * 获取 区域编码.
	 * 
	 * @return the countyCode .
	 */
	public String getCountyCode() {
		return countyCode;
	}

	/**
	 * 设置 区域编码.
	 * 
	 * @param countyCode
	 *            the countyCode to set.
	 */
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	/**
	 * 获取 城市编码.
	 * 
	 * @return the cityCode .
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * 设置 城市编码.
	 * 
	 * @param cityCode
	 *            the cityCode to set.
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * 获取 省份编码.
	 * 
	 * @return the provinceCode .
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 * 设置 省份编码.
	 * 
	 * @param provinceCode
	 *            the provinceCode to set.
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	/**
	 * 获取 接送货地址ID.
	 * 
	 * @return the addressId .
	 */
	public String getAddressId() {
		return addressId;
	}

	/**
	 * 设置 接送货地址ID.
	 * 
	 * @param addressId
	 *            the addressId to set.
	 */
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * 获取发票标记
	 * 
	 * @return
	 */
	public String getInvoiceType() {
		return invoiceType;
	}

	/**
	 * 设置发票标记
	 * 
	 * @param invoiceType
	 */
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	/**
	 * 获取发票标记时间
	 * 
	 * @return
	 */
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * 设置发票标记时间
	 * 
	 * @param invoiceDate
	 */
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getIsElecBillBigCust() {
		return isElecBillBigCust;
	}

	public void setIsElecBillBigCust(String isElecBillBigCust) {
		this.isElecBillBigCust = isElecBillBigCust;
	}

	public String getAccurateCollection() {
		return accurateCollection;
	}

	public void setAccurateCollection(String accurateCollection) {
		this.accurateCollection = accurateCollection;
	}

	public String getShipperSms() {
		return shipperSms;
	}

	public void setShipperSms(String shipperSms) {
		this.shipperSms = shipperSms;
	}

	public String getReceiverSms() {
		return receiverSms;
	}

	public void setReceiverSms(String receiverSms) {
		this.receiverSms = receiverSms;
	}

	public Integer getTeanLimit() {
		return teanLimit;
	}

	public void setTeanLimit(Integer teanLimit) {
		this.teanLimit = teanLimit;
	}

	public String getIsExpressBigCust() {
		return isExpressBigCust;
	}

	public void setIsExpressBigCust(String isExpressBigCust) {
		this.isExpressBigCust = isExpressBigCust;
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

	public String getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}

}
