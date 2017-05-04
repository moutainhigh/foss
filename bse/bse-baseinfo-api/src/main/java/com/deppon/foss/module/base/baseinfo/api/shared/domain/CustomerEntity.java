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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/CustomerEntity.java
 * 
 * FILE NAME        	: CustomerEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 客户实体类
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-16 下午4:20:17
 * @since
 * @version
 */
public class CustomerEntity extends BaseEntity{
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 4111057842160883138L;
    
    /**
     * 客户地址.
     */
    private String address;
    
    /**
     * 客户属性.
     */
    private String property;
    
    /**
     * 客户类型.
     */
    private String type;
    
    /**
     * 信用额度.
     */
    private BigDecimal creditAmount;
    
    /**
     * 客户名称.
     */
    private String name;
    
    /**
     * 营业执照号.
     */
    private String license;
    
    /**
     * 客户所属部门标杆编码.
     */
    private String unifiedCode;
    
    /**
     * 客户所属部门名称（扩展）.
     */
    private String deptName;
    
    /**
     * 客户编码.
     */
    private String cusCode;
    
    /**
     * 客户是否有效.
     */
    private String activeCus;
    
    /**
     * 月结客户总欠款.
     */
    private Long totalArrears;
    
    /**
     * 结算方式.
     */
    private String chargeMode;
    
    /**
     * 客户等级.
     */
    private String degree;
    
    /**
     * CRM客户ID.
     */
    private BigDecimal crmCusId;
    
    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 虚拟编码.
     */
    private String virtualCode;
    
    /**
     * 是否异地调货.
     */
    private String fistrangoods;
    
    /**
     * 所在省份名称.
     */
    private String provName;
    
    /**
     * 所在城市名称.
     */
    private String cityName;
    
    /**
     * 客户简称 simpleName.
     */
    private String simpleName;
    
    /**
     * 是否重要客户.
     */
    private String isImport;
    
    /**
     * 是否特殊客户.
     */
    private String isSpecial;
    
    /**
     * 发票抬头.
     */
    private String billTitle;
    
    /**
     * 公共选择器扩展字段.
     */
    private String queryParam;
    /**
     * 公共选择器扩展字段 是否按手机号查询
     */
    private String contcatFlag;
    
    /**
     * 公共选择器扩展字段. 是否查询散客信息
     */
    private String singlePeopleFlag;
    
    
    /**
     * 公共选择器扩展字段. 是否删除客户信息
     */
    private String isDelete;
    
    /**
     * 公共选择器扩展字段.查询全部客户信息
     */
    private String all;  
    
    
    /**
	 * 发件人短信
	 */
	private String shipperSms;
	
	/**
	 * 收件人短信
	 */
	private String receiverSms;


	/**
	 * 精准代收
	 */
	private String accurateCollection;
    
    /**
     * 是否大客户标记
     */
    private String isLargeCustomers;
    
    /**
     * 是否快递大客户标记
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
     * 是否电子运单大客户
     */
    private String isElecBillBigCust;
    
	
	 /**
     * 特安上限
     */
    private Integer teanLimit;
    
    /**
	 * 收货人固定手机号
	 */
	private String fixedReceiveMobile;

    /**
     * 客户地址备注信息
     */
    private String custAddrRemark;
    
    /**
     * 发票标记
     * @author 218392 zhangyongxue
     * @return
     */
    private String invoiceType;
    
    /**
     * 获取发票标记
     * @return
     */
    public String getInvoiceType() {
		return invoiceType;
	}

    /**
     * 黑名单类别
     * 2015-07-28 添加  差错延期，FOSS跟着延期
      */ 
    private String blackListCategory;
   
	 public String getBlackListCategory() {
		return blackListCategory;
	}

	public void setBlackListCategory(String blackListCategory) {
		this.blackListCategory = blackListCategory;
	}

	/**
     * 客户分群
     * 2015-07-06 添加
     * css
     */
    private String flabelleavemonth;



    public String getFlabelleavemonth() {
		return flabelleavemonth;
	}

	public void setFlabelleavemonth(String flabelleavemonth) {
		this.flabelleavemonth = flabelleavemonth;
	}	
	
	/**
	    * 快递装卸费业务类型
	    * 2015-09-07添加    css
	    * */ 
	private String expHandChargeBusiType;	
	
	public String getExpHandChargeBusiType() {
		return expHandChargeBusiType;
	}

	public void setExpHandChargeBusiType(String expHandChargeBusiType) {
		this.expHandChargeBusiType = expHandChargeBusiType;
	}
	
	/**
	    * 设置比例 
	    * 2015-09-07 添加   css
	    * */ 
	private String setProportion;
	   
	public String getSetProportion() {
		return setProportion;
	}

	public void setSetProportion(String setProportion) {
		this.setProportion = setProportion;
	}
	
    /**     * 设置发票标记
     * @param invoiceType
     */
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		this.all = all;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
    /**
     * 获取 客户所属部门名称（扩展）.
     *
     * @return  the deptName
     */
    public String getDeptName() {
        return deptName;
    }
    
    /**
     * 设置 客户所属部门名称（扩展）.
     *
     * @param deptName the deptName to set
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * 获取 客户地址.
     *
     * @return  the address
     */
    public String getAddress() {
        return address;
    }
    
    public String getSinglePeopleFlag() {
		return singlePeopleFlag;
	}

	public void setSinglePeopleFlag(String singlePeopleFlag) {
		this.singlePeopleFlag = singlePeopleFlag;
	}

	/**
     * 设置 客户地址.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * 获取 客户属性.
     *
     * @return  the property
     */
    public String getProperty() {
        return property;
    }
    
    /**
     * 设置 客户属性.
     *
     * @param property the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }
    
    /**
     * 获取 客户类型.
     *
     * @return  the type
     */
    public String getType() {
        return type;
    }
    
    /**
     * 设置 客户类型.
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * 获取 信用额度.
     *
     * @return  the creditAmount
     */
    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    
    /**
     * 设置 信用额度.
     *
     * @param creditAmount the creditAmount to set
     */
    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    /**
     * 获取 客户名称.
     *
     * @return  the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * 设置 客户名称.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 获取 营业执照号.
     *
     * @return  the license
     */
    public String getLicense() {
        return license;
    }
    
    /**
     * 设置 营业执照号.
     *
     * @param license the license to set
     */
    public void setLicense(String license) {
        this.license = license;
    }
    
    /**
     * 获取 客户所属部门标杆编码.
     *
     * @return  the unifiedCode
     */
    public String getUnifiedCode() {
        return unifiedCode;
    }
    
    /**
     * 设置 客户所属部门标杆编码.
     *
     * @param unifiedCode the unifiedCode to set
     */
    public void setUnifiedCode(String unifiedCode) {
        this.unifiedCode = unifiedCode;
    }
    
    /**
     * 获取 客户编码.
     *
     * @return  the cusCode
     */
    public String getCusCode() {
        return cusCode;
    }
    
    /**
     * 设置 客户编码.
     *
     * @param cusCode the cusCode to set
     */
    public void setCusCode(String cusCode) {
        this.cusCode = cusCode;
    }
    
    /**
     * 获取 客户是否有效.
     *
     * @return  the activeCus
     */
    public String getActiveCus() {
        return activeCus;
    }
    
    /**
     * 设置 客户是否有效.
     *
     * @param activeCus the activeCus to set
     */
    public void setActiveCus(String activeCus) {
        this.activeCus = activeCus;
    }
    
    /**
     * 获取 月结客户总欠款.
     *
     * @return  the totalArrears
     */
    public Long getTotalArrears() {
        return totalArrears;
    }
    
    /**
     * 设置 月结客户总欠款.
     *
     * @param totalArrears the totalArrears to set
     */
    public void setTotalArrears(Long totalArrears) {
        this.totalArrears = totalArrears;
    }
    
    /**
     * 获取 结算方式.
     *
     * @return  the chargeMode
     */
    public String getChargeMode() {
        return chargeMode;
    }
    
    /**
     * 设置 结算方式.
     *
     * @param chargeMode the chargeMode to set
     */
    public void setChargeMode(String chargeMode) {
        this.chargeMode = chargeMode;
    }
    
    /**
     * 获取 客户等级.
     *
     * @return  the degree
     */
    public String getDegree() {
        return degree;
    }
    
    /**
     * 设置 客户等级.
     *
     * @param degree the degree to set
     */
    public void setDegree(String degree) {
        this.degree = degree;
    }
    
    /**
     * 获取 cRM客户ID.
     *
     * @return  the crmCusId
     */
    public BigDecimal getCrmCusId() {
        return crmCusId;
    }
    
    /**
     * 设置 cRM客户ID.
     *
     * @param crmCusId the crmCusId to set
     */
    public void setCrmCusId(BigDecimal crmCusId) {
        this.crmCusId = crmCusId;
    }
    
    /**
     * 获取 是否启用.
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 是否启用.
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
    
    /**
     * 获取 虚拟编码.
     *
     * @return  the virtualCode
     */
    public String getVirtualCode() {
        return virtualCode;
    }
    
    /**
     * 设置 虚拟编码.
     *
     * @param virtualCode the virtualCode to set
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }
    
    /**
     * 获取 是否异地调货.
     *
     * @return  the fistrangoods
     */
    public String getFistrangoods() {
        return fistrangoods;
    }
    
    /**
     * 设置 是否异地调货.
     *
     * @param fistrangoods the fistrangoods to set
     */
    public void setFistrangoods(String fistrangoods) {
        this.fistrangoods = fistrangoods;
    }
    
    /**
     * 获取 所在省份名称.
     *
     * @return  the provName
     */
    public String getProvName() {
        return provName;
    }
    
    /**
     * 设置 所在省份名称.
     *
     * @param provName the provName to set
     */
    public void setProvName(String provName) {
        this.provName = provName;
    }
    
    /**
     * 获取 所在城市名称.
     *
     * @return  the cityName
     */
    public String getCityName() {
        return cityName;
    }
    
    /**
     * 设置 所在城市名称.
     *
     * @param cityName the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    /**
     * 获取 客户简称 simpleName.
     *
     * @return  the simpleName
     */
    public String getSimpleName() {
        return simpleName;
    }
    
    /**
     * 设置 客户简称 simpleName.
     *
     * @param simpleName the simpleName to set
     */
    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }
    
    /**
     * 获取 是否重要客户.
     *
     * @return  the isImport
     */
    public String getIsImport() {
        return isImport;
    }
    
    /**
     * 设置 是否重要客户.
     *
     * @param isImport the isImport to set
     */
    public void setIsImport(String isImport) {
        this.isImport = isImport;
    }
    
    /**
     * 获取 是否特殊客户.
     *
     * @return  the isSpecial
     */
    public String getIsSpecial() {
        return isSpecial;
    }
    
    /**
     * 设置 是否特殊客户.
     *
     * @param isSpecial the isSpecial to set
     */
    public void setIsSpecial(String isSpecial) {
        this.isSpecial = isSpecial;
    }
    
    /**
     * 获取 发票抬头.
     *
     * @return  the billTitle
     */
    public String getBillTitle() {
        return billTitle;
    }
    
    /**
     * 设置 发票抬头.
     *
     * @param billTitle the billTitle to set
     */
    public void setBillTitle(String billTitle) {
        this.billTitle = billTitle;
    }

	
	/**
	 * 获取 公共选择器扩展字段.
	 *
	 * @return the 公共选择器扩展字段
	 */
	public String getQueryParam() {
		return queryParam;
	}

	
	/**
	 * 设置 公共选择器扩展字段.
	 *
	 * @param queryParam the new 公共选择器扩展字段
	 */
	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}

	/**
	 * @return contcatFlag
	 */
	public String getContcatFlag() {
		return contcatFlag;
	}

	/**
	 * @param  contcatFlag  
	 */
	public void setContcatFlag(String contcatFlag) {
		this.contcatFlag = contcatFlag;
	}


	public String getIsLargeCustomers() {
		return isLargeCustomers;
	}

	public void setIsLargeCustomers(String isLargeCustomers) {
		this.isLargeCustomers = isLargeCustomers;
	}

	public String getBusinessType() {
		return businessType;
	}

	/**
	 * 设置业务类别
	 *
	 * auther:wangpeng_078816
	 * date:2014-3-17
	 *
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getCustomserNature() {
		return customserNature;
	}

	/**
	 * 设置客户性质
	 *
	 * auther:wangpeng_078816
	 * date:2014-3-17
	 *
	 */
	public void setCustomserNature(String customserNature) {
		this.customserNature = customserNature;
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
	public String getIsElecBillBigCust() {
		return isElecBillBigCust;
	}

	public void setIsElecBillBigCust(String isElecBillBigCust) {
		this.isElecBillBigCust = isElecBillBigCust;
	}

	
	/**
	 * 获取精确代收
	 * @return
	 */
	public String getAccurateCollection() {
		return accurateCollection;
	}

	/**
	 * 设置精确代收
	 * @param accurateCollection
	 */
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

	public String getFixedReceiveMobile() {
		return fixedReceiveMobile;
	}

	public void setFixedReceiveMobile(String fixedReceiveMobile) {
		this.fixedReceiveMobile = fixedReceiveMobile;
	}
	
	/**
	 * 是否精准计算
	 */
	private String isAccuratePrice;

	public String getIsAccuratePrice() {
		return isAccuratePrice;
	}

	public void setIsAccuratePrice(String isAccuratePrice) {
		this.isAccuratePrice = isAccuratePrice;
	}

	
	
	

	
}