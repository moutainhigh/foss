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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/CusBargainEntity.java
 * 
 * FILE NAME        	: CusBargainEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;

/**
 * 客户合同信息实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-16 下午4:24:24 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-16 下午4:24:24
 * @date 2012-08-07 上午10:24:24   增加字段        价格版本时间 priceVersionDat
 * @since
 * @version
 */
public class CusBargainEntity extends BaseEntity{
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -7982128618802193978L;
    
    /**
     * 付款方式.
     */
    private String chargeType;
    
    /**
     * 申请欠款额度 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）.
     */
    private BigDecimal arrearsAmount;
    
    /**
     * 协议联系人姓名.
     */
    private String name;
    
    /**
     * 联系人固定电话.
     */
    private String contactPhone;
    
    /**
     * 联系人手机.
     */
    private String mobilePhone;
    
    /**
     * 联系人详细地址.
     */
    private String address;
    
    /**
     * 对账日期.
     */
    private BigDecimal statementDate;
    
    /**
     * 开发票日期.
     */
    private BigDecimal invoicingDate;
    
    /**
     * 结款日期.
     */
    private BigDecimal checkoutDate;
    
    /**
     * 申请事由.
     */
    private String applyReason;
    
    /**
     * 所属部门标杆编码.
     */
    private String unifiedCode;
    
    /**
     * 所属部门名称.
     */
    private String belongDeptName;
    
    /**
     * 合同适用部门.
     */
    private String applicateOrgId;
    
    /**
     * 是否折扣.
     */
    private String discount;
    
    /**
     * 合同状态.
     */
    private String status;
    
    /**
     * 合同主体.
     */
    private String bargainSubject;
    
    /**
     * 注册资金.
     */
    private String registerFunds;
    
    /**
     * 原合同编号.
     */
    private String lastBargain;
    
    /**
     * 合同编号.
     */
    private String bargainCode;
    
    /**
     * 走货名称.
     */
    private String transName;
    
    /**
     * 客户全称.
     */
    private String customerName;
    
    /**
     * 协议联系人.
     */
    private String linkmanId;
    
    /**
     * 结算方式.
     */
    private String chargeMode;
    
    /**
     * 优惠类型.
     */
    private String preferentialType;
    
    /**
     * 已用额度.
     */
    private Long usedAmount;
    
    /**
     * 是否超期.
     */
    private String overdue;
    
    /**
     * 业务日期.
     */
    private Date bizDate;
    
    /**
     * 合同起始日期.
     */
    private Date beginTime;
    
    /**
     * 合同到期日期.
     */
    private Date endTime;
    
    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 所属客户ID.
     */
    private BigDecimal customerCode;
    
    /**
     * 虚拟编码.
     */
    private String virtualCode;
    
    /**
     * 月结合同天数.
     */
    private int debtDays;
    
    /**
     * 在CRM中fid.
     */
    private BigDecimal crmId; 
    
    /**
     * 异地调货标志.
     */
    private String asyntakegoodsCode;
    
    /**
     * 催款部门标杆编码.
     */
    private String hastenfunddeptCode;
    
    /**
     * 与客户信息是多对一关系.
     */
    private CustomerDto customerDto;
    
    /**
     * 一个客户合同对应多个合同适用部门.
     */
    private List<BargainAppOrgEntity> appOrgList;
    
    /**
     *价格版本时间.
     */
    private Date priceVersionDate;
    
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
	  * 是否单独报价
	  */
	 private String isAloneQuotation;
	 
	 /**
	     * FOSS-232608
	     * 是否电商尊享
	     * 2015-07-08 吴苏华
	     */
	 private String ifElecEnjoy;
    /**
     * 是否精准包裹
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
     * 获取 异地调货标志.
     *
     * @return  the asyntakegoodsCode
     */
    public String getAsyntakegoodsCode() {
        return asyntakegoodsCode;
    }

	/**
     * 设置 异地调货标志.
     *
     * @param asyntakegoodsCode the asyntakegoodsCode to set
     */
    public void setAsyntakegoodsCode(String asyntakegoodsCode) {
        this.asyntakegoodsCode = asyntakegoodsCode;
    }

    
    /**
     * 获取 催款部门标杆编码.
     *
     * @return  the hastenfunddeptCode
     */
    public String getHastenfunddeptCode() {
        return hastenfunddeptCode;
    }

    
    /**
     * 设置 催款部门标杆编码.
     *
     * @param hastenfunddeptCode the hastenfunddeptCode to set
     */
    public void setHastenfunddeptCode(String hastenfunddeptCode) {
        this.hastenfunddeptCode = hastenfunddeptCode;
    }

    /**
     * 获取 付款方式.
     *
     * @return  the chargeType
     */
    public String getChargeType() {
        return chargeType;
    }
    
    /**
     * 获取 月结合同天数.
     *
     * @return  the debtDays
     */
    public int getDebtDays() {
        return debtDays;
    }
    
    /**
     * 设置 月结合同天数.
     *
     * @param debtDays the debtDays to set
     */
    public void setDebtDays(int debtDays) {
        this.debtDays = debtDays;
    }

    /**
     * 设置 付款方式.
     *
     * @param chargeType the chargeType to set
     */
    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }
    
    /**
     * 获取 申请欠款额度 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）.
     *
     * @return  the arrearsAmount
     */
    public BigDecimal getArrearsAmount() {
        return arrearsAmount;
    }
    
    /**
     * 设置 申请欠款额度 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）.
     *
     * @param arrearsAmount the arrearsAmount to set
     */
    public void setArrearsAmount(BigDecimal arrearsAmount) {
        this.arrearsAmount = arrearsAmount;
    }
    
    /**
     * 获取 协议联系人姓名.
     *
     * @return  the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * 设置 协议联系人姓名.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 获取 联系人固定电话.
     *
     * @return  the contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }
    
    /**
     * 设置 联系人固定电话.
     *
     * @param contactPhone the contactPhone to set
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    
    /**
     * 获取 联系人手机.
     *
     * @return  the mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }
    
    /**
     * 设置 联系人手机.
     *
     * @param mobilePhone the mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    
    /**
     * 获取 联系人详细地址.
     *
     * @return  the address
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * 设置 联系人详细地址.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * 获取 对账日期.
     *
     * @return  the statementDate
     */
    public BigDecimal getStatementDate() {
        return statementDate;
    }
    
    /**
     * 设置 对账日期.
     *
     * @param statementDate the statementDate to set
     */
    public void setStatementDate(BigDecimal statementDate) {
        this.statementDate = statementDate;
    }
    
    /**
     * 获取 开发票日期.
     *
     * @return  the invoicingDate
     */
    public BigDecimal getInvoicingDate() {
        return invoicingDate;
    }
    
    /**
     * 设置 开发票日期.
     *
     * @param invoicingDate the invoicingDate to set
     */
    public void setInvoicingDate(BigDecimal invoicingDate) {
        this.invoicingDate = invoicingDate;
    }
    
    /**
     * 获取 结款日期.
     *
     * @return  the checkoutDate
     */
    public BigDecimal getCheckoutDate() {
        return checkoutDate;
    }
    
    /**
     * 设置 结款日期.
     *
     * @param checkoutDate the checkoutDate to set
     */
    public void setCheckoutDate(BigDecimal checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
    
    /**
     * 获取 申请事由.
     *
     * @return  the applyReason
     */
    public String getApplyReason() {
        return applyReason;
    }
    
    /**
     * 设置 申请事由.
     *
     * @param applyReason the applyReason to set
     */
    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }
    
    /**
     * 获取 所属部门标杆编码.
     *
     * @return  the unifiedCode
     */
    public String getUnifiedCode() {
        return unifiedCode;
    }
    
    /**
     * 设置 所属部门标杆编码.
     *
     * @param unifiedCode the unifiedCode to set
     */
    public void setUnifiedCode(String unifiedCode) {
        this.unifiedCode = unifiedCode;
    }
    
    /**
     * 获取 所属部门名称.
     *
     * @return  the belongDeptName
     */
    public String getBelongDeptName() {
        return belongDeptName;
    }
    
    /**
     * 设置 所属部门名称.
     *
     * @param belongDeptName the belongDeptName to set
     */
    public void setBelongDeptName(String belongDeptName) {
        this.belongDeptName = belongDeptName;
    }
    
    /**
     * 获取 合同适用部门.
     *
     * @return  the applicateOrgId
     */
    public String getApplicateOrgId() {
        return applicateOrgId;
    }
    
    /**
     * 设置 合同适用部门.
     *
     * @param applicateOrgId the applicateOrgId to set
     */
    public void setApplicateOrgId(String applicateOrgId) {
        this.applicateOrgId = applicateOrgId;
    }
    
    /**
     * 获取 是否折扣.
     *
     * @return  the discount
     */
    public String getDiscount() {
        return discount;
    }
    
    /**
     * 设置 是否折扣.
     *
     * @param discount the discount to set
     */
    public void setDiscount(String discount) {
        this.discount = discount;
    }
    
    /**
     * 获取 合同状态.
     *
     * @return  the status
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * 设置 合同状态.
     *
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * 获取 合同主体.
     *
     * @return  the bargainSubject
     */
    public String getBargainSubject() {
        return bargainSubject;
    }
    
    /**
     * 设置 合同主体.
     *
     * @param bargainSubject the bargainSubject to set
     */
    public void setBargainSubject(String bargainSubject) {
        this.bargainSubject = bargainSubject;
    }
    
    /**
     * 获取 注册资金.
     *
     * @return  the registerFunds
     */
    public String getRegisterFunds() {
        return registerFunds;
    }
    
    /**
     * 设置 注册资金.
     *
     * @param registerFunds the registerFunds to set
     */
    public void setRegisterFunds(String registerFunds) {
        this.registerFunds = registerFunds;
    }
    
    /**
     * 获取 原合同编号.
     *
     * @return  the lastBargain
     */
    public String getLastBargain() {
        return lastBargain;
    }
    
    /**
     * 设置 原合同编号.
     *
     * @param lastBargain the lastBargain to set
     */
    public void setLastBargain(String lastBargain) {
        this.lastBargain = lastBargain;
    }
    
    /**
     * 获取 合同编号.
     *
     * @return  the bargainCode
     */
    public String getBargainCode() {
        return bargainCode;
    }
    
    /**
     * 设置 合同编号.
     *
     * @param bargainCode the bargainCode to set
     */
    public void setBargainCode(String bargainCode) {
        this.bargainCode = bargainCode;
    }
    
    /**
     * 获取 走货名称.
     *
     * @return  the transName
     */
    public String getTransName() {
        return transName;
    }
    
    /**
     * 设置 走货名称.
     *
     * @param transName the transName to set
     */
    public void setTransName(String transName) {
        this.transName = transName;
    }
    
    /**
     * 获取 客户全称.
     *
     * @return  the customerName
     */
    public String getCustomerName() {
        return customerName;
    }
    
    /**
     * 设置 客户全称.
     *
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    /**
     * 获取 协议联系人.
     *
     * @return  the linkmanId
     */
    public String getLinkmanId() {
        return linkmanId;
    }
    
    /**
     * 设置 协议联系人.
     *
     * @param linkmanId the linkmanId to set
     */
    public void setLinkmanId(String linkmanId) {
        this.linkmanId = linkmanId;
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
     * 获取 优惠类型.
     *
     * @return  the preferentialType
     */
    public String getPreferentialType() {
        return preferentialType;
    }
    
    /**
     * 设置 优惠类型.
     *
     * @param preferentialType the preferentialType to set
     */
    public void setPreferentialType(String preferentialType) {
        this.preferentialType = preferentialType;
    }
    
    /**
     * 获取 已用额度.
     *
     * @return  the usedAmount
     */
    public Long getUsedAmount() {
        return usedAmount;
    }
    
    /**
     * 设置 已用额度.
     *
     * @param usedAmount the usedAmount to set
     */
    public void setUsedAmount(Long usedAmount) {
        this.usedAmount = usedAmount;
    }
    
    /**
     * 获取 是否超期.
     *
     * @return  the overdue
     */
    public String getOverdue() {
        return overdue;
    }
    
    /**
     * 设置 是否超期.
     *
     * @param overdue the overdue to set
     */
    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }
    
    /**
     * 获取 业务日期.
     *
     * @return  the bizDate
     */
    public Date getBizDate() {
        return bizDate;
    }
    
    /**
     * 设置 业务日期.
     *
     * @param bizDate the bizDate to set
     */
    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }
    
    /**
     * 获取 合同起始日期.
     *
     * @return  the beginTime
     */
    public Date getBeginTime() {
        return beginTime;
    }
    
    /**
     * 设置 合同起始日期.
     *
     * @param beginTime the beginTime to set
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    
    /**
     * 获取 合同到期日期.
     *
     * @return  the endTime
     */
    public Date getEndTime() {
        return endTime;
    }
    
    /**
     * 设置 合同到期日期.
     *
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
     * 获取 所属客户ID.
     *
     * @return  the customerCode
     */
    public BigDecimal getCustomerCode() {
        return customerCode;
    }
    
    /**
     * 设置 所属客户ID.
     *
     * @param customerCode the customerCode to set
     */
    public void setCustomerCode(BigDecimal customerCode) {
        this.customerCode = customerCode;
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
     * 获取 在CRM中fid.
     *
     * @return  the crmId
     */
    public BigDecimal getCrmId() {
        return crmId;
    }
    
    /**
     * 设置 在CRM中fid.
     *
     * @param crmId the crmId to set
     */
    public void setCrmId(BigDecimal crmId) {
        this.crmId = crmId;
    }
    
    /**
     * 获取 与客户信息是多对一关系.
     *
     * @return  the customerDto
     */
    public CustomerDto getCustomerDto() {
        return customerDto;
    }
    
    /**
     * 设置 与客户信息是多对一关系.
     *
     * @param customerDto the customerDto to set
     */
    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }
    
    /**
     * 获取 一个客户合同对应多个合同适用部门.
     *
     * @return  the appOrgList
     */
    public List<BargainAppOrgEntity> getAppOrgList() {
        return appOrgList;
    }
    
    /**
     * 设置 一个客户合同对应多个合同适用部门.
     *
     * @param appOrgList the appOrgList to set
     */
    public void setAppOrgList(List<BargainAppOrgEntity> appOrgList) {
        this.appOrgList = appOrgList;
    }
    
    /**
     * 获取价格版本时间.
     *
     * @return  the priceVersionDate
     */
    public Date getPriceVersionDate() {
  		return priceVersionDate;
  	}
    
    /**
     * 设置价格版本时间.
     *
     * @param priceVersionDate the priceVersionDate to set
     */
  	public void setPriceVersionDate(Date priceVersionDate) {
  		this.priceVersionDate = priceVersionDate;
  	}
  	
  	/**
  	 * 获取是否单独报价
  	 * @return
  	 */
	public String getIsAloneQuotation() {
		return isAloneQuotation;
	}
	
	/**
	 * 设置是否单独报价
	 * @param isAloneQuotation
	 */
	public void setIsAloneQuotation(String isAloneQuotation) {
		this.isAloneQuotation = isAloneQuotation;
	}
  	
  	
}