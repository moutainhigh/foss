/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/QueryBillCacilateDto.java
 * 
 * FILE NAME        	: QueryBillCacilateDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 
 * 查询快递代理理价格计算的DTO
 * @author DP-Foss-zdp
 * @date 2013-7-24 下午6:33:52
 */
public class QueryPartBusinessPriceDto implements Serializable {
    
    
 
    /**
     *  
     */
    private static final long serialVersionUID = 5144259428068403891L;
    /**
     * 配载部门 
     */
    private String loadOrgCode;
    /** 
     *快递代理公司code. 
     */
    private String expressPartbussCode;
    /** 
     *快递代理递代理公司网点code.
     */
    private String outerBranchCode;
 
    /** 
     * 营业部收货日期（可选，无则表示当前日期）,即开单日期 **/
    private Date receiveDate;
  
    /** 
     * 重量 
     */
    private BigDecimal weight;
    
    /** 
     * 到付金额
     */
    private BigDecimal toPayAmount;
        
  
 
    /** 
     * 币种 （可选，无则表示人民币）**
     */
    private String currencyCdoe;
    
    /**
     * 行政區域code
     */
    private String districtCode;
    
    
    /**
     * 费用类型 请参考 PriceEntityConstants 相关定义 
     */
    private String subType;
   
    /** 
     * 原始费用  元
     */
    private BigDecimal originnalCost;
        
    
    public String getLoadOrgCode() {
        return loadOrgCode;
    }


    
    public void setLoadOrgCode(String loadOrgCode) {
        this.loadOrgCode = loadOrgCode;
    }


    
    public String getExpressPartbussCode() {
        return expressPartbussCode;
    }


    
    public void setExpressPartbussCode(String expressPartbussCode) {
        this.expressPartbussCode = expressPartbussCode;
    }


    
    public String getOuterBranchCode() {
        return outerBranchCode;
    }


    
    public void setOuterBranchCode(String outerBranchCode) {
        this.outerBranchCode = outerBranchCode;
    }


    
    public Date getReceiveDate() {
        return receiveDate;
    }


    
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }


    
    public BigDecimal getWeight() {
        return weight;
    }


    
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }


    
    public BigDecimal getOriginnalCost() {
	if(originnalCost==null)
	    originnalCost=BigDecimal.ZERO;
	
        return originnalCost;
    }


    
    public void setOriginnalCost(BigDecimal originnalCost) {
        this.originnalCost = originnalCost;
    }


    
    public String getCurrencyCdoe() {
        return currencyCdoe;
    }


    
    public void setCurrencyCdoe(String currencyCdoe) {
        this.currencyCdoe = currencyCdoe;
    }


    
    public String getSubType() {
        return subType;
    }


    
    public void setSubType(String subType) {
        this.subType = subType;
    }



    
    public String getDistrictCode() {
        return districtCode;
    }



    
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }



	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}



	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}



	

  
    
     
}