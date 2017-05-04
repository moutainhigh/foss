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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/CaculateFeeDto.java
 * 
 * FILE NAME        	: CaculateFeeDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.math.BigDecimal;

/**
 * 费用计算dto
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:zhangdongping,date:2012-10-25 下午5:43:34, 
 * </p>
 * 
 * @author zhangdongping
 * @date 2012-10-25 下午5:43:34
 * @since
 * @version
 */
public class CaculateFeeDto {

	/**
	 *  计价参数1
	 */
    private Long parm1;
	/**
	 * 计价参数2
	 */
    private Long parm2;
	/**
	 *  计价参数3
	 */
    private Long parm3;
	/** 
	 * 计价参数4
	 */
    private Long parm4;
	/** 
	 * 计价参数5
	 */
    private Long parm5;
	/** 
	 * 价格规则公式
	 */
    private String expression;
	/** 
	 * 折扣率
	 */
    private BigDecimal discountRate;
	/** 
	 * 首重（体积）价格
	 */
    private Long firstWeightPrice;
	/** 
	 * 首重重量（体积）
	 */
    private Double firstWeight;
	/** 
	 * 续重步进重量（体积）
	 */
    private Double addWeightStep;
	/**
	 *  续重（体积）单价
	 */
    private Long addWeightPrice;

    /**
     * 费率（小数）或者单价（分）
     */
    private BigDecimal feeRate; 
    /**
     * 固定费用
     */
    private Long fixPrice;
    /** 
     * 价格进位方式   四舍五入 1 ; 去位取整 3
     */
    private String carryType;

    /**
     * 最低费用
     */
    private Long minFee;

    /**
     * 最高费用
     */
    private Long maxFee;




    
    /**
     * 获取 计价参数1.
     *
     * @return the 计价参数1
     */
    public Long getParm1() {
        return parm1;
    }


    
    /**
     * 设置 计价参数1.
     *
     * @param parm1 the new 计价参数1
     */
    public void setParm1(Long parm1) {
        this.parm1 = parm1;
    }


    
    /**
     * 获取 计价参数2.
     *
     * @return the 计价参数2
     */
    public Long getParm2() {
        return parm2;
    }


    
    /**
     * 设置 计价参数2.
     *
     * @param parm2 the new 计价参数2
     */
    public void setParm2(Long parm2) {
        this.parm2 = parm2;
    }


    
    /**
     * 获取 计价参数3.
     *
     * @return the 计价参数3
     */
    public Long getParm3() {
        return parm3;
    }


    
    /**
     * 设置 计价参数3.
     *
     * @param parm3 the new 计价参数3
     */
    public void setParm3(Long parm3) {
        this.parm3 = parm3;
    }


    
    /**
     * 获取 计价参数4.
     *
     * @return the 计价参数4
     */
    public Long getParm4() {
        return parm4;
    }


    
    /**
     * 设置 计价参数4.
     *
     * @param parm4 the new 计价参数4
     */
    public void setParm4(Long parm4) {
        this.parm4 = parm4;
    }


    
    /**
     * 获取 计价参数5.
     *
     * @return the 计价参数5
     */
    public Long getParm5() {
        return parm5;
    }


    
    /**
     * 设置 计价参数5.
     *
     * @param parm5 the new 计价参数5
     */
    public void setParm5(Long parm5) {
        this.parm5 = parm5;
    }


    /**
     * 获取 价格规则公式.
     *
     * @return the 价格规则公式
     */
    public String getExpression() {
        return expression;
    }

    
    /**
     * 设置 价格规则公式.
     *
     * @param expression the new 价格规则公式
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }

    
    /**
     * 获取 折扣率.
     *
     * @return the 折扣率
     */
    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    
    /**
     * 设置 折扣率.
     *
     * @param discountRate the new 折扣率
     */
    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    
    /**
     * 获取 首重（体积）价格.
     *
     * @return the 首重（体积）价格
     */
    public Long getFirstWeightPrice() {
        return firstWeightPrice;
    }

    
    /**
     * 设置 首重（体积）价格.
     *
     * @param firstWeightPrice the new 首重（体积）价格
     */
    public void setFirstWeightPrice(Long firstWeightPrice) {
        this.firstWeightPrice = firstWeightPrice;
    }

    
    /**
     * 获取 首重重量（体积）.
     *
     * @return the 首重重量（体积）
     */
    public Double getFirstWeight() {
        return firstWeight;
    }

    
    /**
     * 设置 首重重量（体积）.
     *
     * @param firstWeight the new 首重重量（体积）
     */
    public void setFirstWeight(Double firstWeight) {
        this.firstWeight = firstWeight;
    }

    
    /**
     * 获取 续重步进重量（体积）.
     *
     * @return the 续重步进重量（体积）
     */
    public Double getAddWeightStep() {
        return addWeightStep;
    }

    
    /**
     * 设置 续重步进重量（体积）.
     *
     * @param addWeightStep the new 续重步进重量（体积）
     */
    public void setAddWeightStep(Double addWeightStep) {
        this.addWeightStep = addWeightStep;
    }

    
    /**
     * 获取 续重（体积）单价.
     *
     * @return the 续重（体积）单价
     */
    public Long getAddWeightPrice() {
        return addWeightPrice;
    }

    
    /**
     * 设置 续重（体积）单价.
     *
     * @param addWeightPrice the new 续重（体积）单价
     */
    public void setAddWeightPrice(Long addWeightPrice) {
        this.addWeightPrice = addWeightPrice;
    }

    
    /**
     * 获取 费率（小数）或者单价（分）.
     *
     * @return the 费率（小数）或者单价（分）
     */
    public BigDecimal getFeeRate() {
        return feeRate;
    }

    
    /**
     * 设置 费率（小数）或者单价（分）.
     *
     * @param feeRate the new 费率（小数）或者单价（分）
     */
    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    
    /**
     * 获取 固定费用.
     *
     * @return the 固定费用
     */
    public Long getFixPrice() {
        return fixPrice;
    }

    
    /**
     * 设置 固定费用.
     *
     * @param fixPrice the new 固定费用
     */
    public void setFixPrice(Long fixPrice) {
        this.fixPrice = fixPrice;
    }

    
    /**
     * 获取 价格进位方式   四舍五入 1 ; 去位取整 3.
     *
     * @return the 价格进位方式   四舍五入 1 ; 去位取整 3
     */
    public String getCarryType() {
        return carryType;
    }

    
    /**
     * 设置 价格进位方式   四舍五入 1 ; 去位取整 3.
     *
     * @param carryType the new 价格进位方式   四舍五入 1 ; 去位取整 3
     */
    public void setCarryType(String carryType) {
        this.carryType = carryType;
    }

    
    /**
     * 获取 最低费用.
     *
     * @return the 最低费用
     */
    public Long getMinFee() {
        return minFee;
    }

    
    /**
     * 设置 最低费用.
     *
     * @param minFee the new 最低费用
     */
    public void setMinFee(Long minFee) {
        this.minFee = minFee;
    }

    
    /**
     * 获取 最高费用.
     *
     * @return the 最高费用
     */
    public Long getMaxFee() {
        return maxFee;
    }

    
    /**
     * 设置 最高费用.
     *
     * @param maxFee the new 最高费用
     */
    public void setMaxFee(Long maxFee) {
        this.maxFee = maxFee;
    }


    
   
 
 

}