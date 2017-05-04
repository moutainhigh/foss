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
package com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 快递代理理价格计算的结果DTO
 * @author DP-Foss-zdp
 * @date 2013-7-24 下午6:33:52
 */
public class QueryPartBusinessPriceResultDto implements Serializable {
    
     
    
    /**
     *  
     */
    private static final long serialVersionUID = 6066535034390670151L;


    /**
     * 经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值  单位（元） 四舍五入保留到分
     */
    private BigDecimal caculateFee;
    
    
    /**
     * 费用类型 请参考 PriceEntityConstants 相关定义 
     */
    private String subType;
 

    
    public String getSubType() {
        return subType;
    }


    
    public void setSubType(String subType) {
        this.subType = subType;
    }



    
    public BigDecimal getCaculateFee() {
        return caculateFee;
    }



    
    public void setCaculateFee(BigDecimal caculateFee) {
        this.caculateFee = caculateFee;
    }
   
  
    
     
}