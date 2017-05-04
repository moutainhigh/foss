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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/PriceCriteriaDetailEntity.java
 * 
 * FILE NAME        	: PriceCriteriaDetailEntity.java
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
 * 计价方式明细Entity
 * @author dujunhui-187862
 * @date 2014-10-10 下午3:01:40
 */
public class PriceCriteriaDetailEntity extends BaseEntity {

    /**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

    /**
     *  名称
     */
    private String name;

    /**
     *  计费类别
     */
    private String caculateType;

    /**
     *  固定费用
     */
    private Long fee;

    /**
     *  费率或者单价
     */
    private BigDecimal feeRate;

    /**
     *  计价左区间
     */
    private BigDecimal leftrange;

    /**
     *  计价右区间
     */
    private BigDecimal rightrange;

    /**
     *  最低费用
     */
    private Long minFee;

    /**
     *  最高费用
     */
    private Long maxFee;

    /**
     *  服务子类型
     */
    private String subType;

    /**
     *  是否可以修改
     */
    private String canmodify;

    /**
     *  备注
     */
    private String description;

    /**
     *  特殊处理程序
     */
    private String processProgram;

    /**
     *  特殊处理程序参数
     */
    private String processParmVal;

    /**
     *  计价方式ID
     */
    private String pricingCriteriaId;

    /**
     *  计价参数2
     */
    private Long parm2;

    /**
     *  计价参数1
     */
    private Long parm1;

    /**
     *  价格计算表达式ID
     */
    private String tSrvPriceRuleId;

    /**
     *  价格计算表达式
     */
    private String caculateExpression;

    /**
     *  计价参数3
     */
    private Long parm3;

    /**
     *  计价参数4
     */
    private Long parm4;

    /**
     *  计价参数5
     */
    private Long parm5;

    /**
     *  折扣率
     */
    private BigDecimal couponRate;

    /**
     *  数据状态
     */
    private String active;

    /**
     *  数据版本
     */
    private Long versionNo;

    /**
     *  计费规则ID
     */
    private String pricingValuationId;
    
    /**
     * 始发区域ID
     */
    private String deptRegionId;
    
    /**
     * 是否可以删除
     */
    private String candelete;
    
    /**
     * 步进量纲.
     */
    private BigDecimal dimension;

    
    /**
     *  最低费率
     */
    private BigDecimal minFeeRate;

    /**
     *  最高费率
     */
    private BigDecimal maxFeeRate;

    /**
     * 合票类型名称
     */
    private String combBillTypeName;  
    
    /**
     * 合票类型Code
     */
    private String combBillTypeCode; 
    
    /**
     *  重量范围左区间
     */
    private BigDecimal weightLeftRange;
    /**
     *  重量范围右区间
     */
    private BigDecimal weightRightRange;
    /**
     *  体积范围左区间
     */
    private BigDecimal volumeLeftRange;
    /**
     *  体积范围右区间
     */
    private BigDecimal volumeRightRange;
    /**
     *  是否接货
     */
    private String isPickUp;
    /**
     *  是否作废
     */
    private String isVoid;
    /**
     *  出发部门名称（用于暂存Excel出发部门字段）
     */
    private String deptOrgName;
    /**
     *  出发部门名称（用于暂存Excel到达部门字段）
     */
    private String avvrOrgName;
    /**
     *  产品类型名称（用于暂存Excel产品类型字段）
     */
    private String productName;
    /**
     *  方案编码（用于暂存Excel方案编码字段）
     */
    private String schemeCode;
    
    /**
     * 获取 步进量纲.
     *
     * @return  the dimension
     */
    public BigDecimal getDimension() {
        return dimension;
    }
    
    /**
     * 设置 步进量纲.
     *
     * @param dimension the dimension to set
     */
    public void setDimension(BigDecimal dimension) {
        this.dimension = dimension;
    }    
    /**
     * 获取 是否可以删除.
     *
     * @return the 是否可以删除
     */
    public String getCandelete() {
        return candelete;
    }

    
    /**
     * 设置 是否可以删除.
     *
     * @param candelete the new 是否可以删除
     */
    public void setCandelete(String candelete) {
        this.candelete = candelete;
    }

    /**
     * 获取 始发区域ID.
     *
     * @return the 始发区域ID
     */
    public String getDeptRegionId() {
		return deptRegionId;
	}

	/**
	 * 设置 始发区域ID.
	 *
	 * @param deptRegionId the new 始发区域ID
	 */
	public void setDeptRegionId(String deptRegionId) {
		this.deptRegionId = deptRegionId;
	}

	/**
	 * 获取 名称.
	 *
	 * @return the 名称
	 */
	public String getName() {
		return name;
    }

    /**
     * 设置 名称.
     *
     * @param name the new 名称
     */
    public void setName(String name) {
    	this.name = name;
    }

    /**
     * 获取 计费类别.
     *
     * @return the 计费类别
     */
    public String getCaculateType() {
	return caculateType;
    }

    /**
     * 设置 计费类别.
     *
     * @param caculateType the new 计费类别
     */
    public void setCaculateType(String caculateType) {
	this.caculateType = caculateType;
    }

    /**
     * 获取 固定费用.
     *
     * @return the 固定费用
     */
    public Long getFee() {
	return fee;
    }

    /**
     * 设置 固定费用.
     *
     * @param fee the new 固定费用
     */
    public void setFee(Long fee) {
	this.fee = fee;
    }

    /**
     * 获取 费率或者单价.
     *
     * @return the 费率或者单价
     */
    public BigDecimal getFeeRate() {
	return feeRate;
    }

    /**
     * 设置 费率或者单价.
     *
     * @param feeRate the new 费率或者单价
     */
    public void setFeeRate(BigDecimal feeRate) {
	this.feeRate = feeRate;
    }

    /**
     * 获取 计价左区间.
     *
     * @return the 计价左区间
     */
    public BigDecimal getLeftrange() {
	return leftrange;
    }

    /**
     * 设置 计价左区间.
     *
     * @param leftrange the new 计价左区间
     */
    public void setLeftrange(BigDecimal leftrange) {
	this.leftrange = leftrange;
    }

    /**
     * 获取 计价右区间.
     *
     * @return the 计价右区间
     */
    public BigDecimal getRightrange() {
	return rightrange;
    }

    /**
     * 设置 计价右区间.
     *
     * @param rightrange the new 计价右区间
     */
    public void setRightrange(BigDecimal rightrange) {
	this.rightrange = rightrange;
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

    /**
     * 获取 服务子类型.
     *
     * @return the 服务子类型
     */
    public String getSubType() {
	return subType;
    }

    /**
     * 设置 服务子类型.
     *
     * @param subType the new 服务子类型
     */
    public void setSubType(String subType) {
	this.subType = subType;
    }

    /**
     * 获取 是否可以修改.
     *
     * @return the 是否可以修改
     */
    public String getCanmodify() {
	return canmodify;
    }

    /**
     * 设置 是否可以修改.
     *
     * @param canmodify the new 是否可以修改
     */
    public void setCanmodify(String canmodify) {
	this.canmodify = canmodify;
    }

    /**
     * 获取 备注.
     *
     * @return the 备注
     */
    public String getDescription() {
	return description;
    }

    /**
     * 设置 备注.
     *
     * @param description the new 备注
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * 获取 特殊处理程序.
     *
     * @return the 特殊处理程序
     */
    public String getProcessProgram() {
	return processProgram;
    }

    /**
     * 设置 特殊处理程序.
     *
     * @param processProgram the new 特殊处理程序
     */
    public void setProcessProgram(String processProgram) {
	this.processProgram = processProgram;
    }

    /**
     * 获取 特殊处理程序参数.
     *
     * @return the 特殊处理程序参数
     */
    public String getProcessParmVal() {
	return processParmVal;
    }

    /**
     * 设置 特殊处理程序参数.
     *
     * @param processParmVal the new 特殊处理程序参数
     */
    public void setProcessParmVal(String processParmVal) {
	this.processParmVal = processParmVal;
    }

    /**
     * 获取 计价方式ID.
     *
     * @return the 计价方式ID
     */
    public String getPricingCriteriaId() {
	return pricingCriteriaId;
    }

    /**
     * 设置 计价方式ID.
     *
     * @param pricingCriteriaId the new 计价方式ID
     */
    public void setPricingCriteriaId(String pricingCriteriaId) {
	this.pricingCriteriaId = pricingCriteriaId;
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
     * 
     *
     * @return 
     */
    public String gettSrvPriceRuleId() {
	return tSrvPriceRuleId;
    }

    /**
     * 
     *
     * @param tSrvPriceRuleId 
     */
    public void settSrvPriceRuleId(String tSrvPriceRuleId) {
	this.tSrvPriceRuleId = tSrvPriceRuleId;
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
     * 获取 折扣率.
     *
     */
    public BigDecimal getCouponRate() {
    	return couponRate;
    }

    /**
     * 设置 折扣率.
     *
     */
    public void setCouponRate(BigDecimal couponRate) {
    	this.couponRate = couponRate;
    }

    /**
     * 获取 数据状态.
     *
     * @return the 数据状态
     */
    public String getActive() {
	return active;
    }

    /**
     * 设置 数据状态.
     *
     * @param active the new 数据状态
     */
    public void setActive(String active) {
	this.active = active;
    }

    /**
     * 获取 数据版本.
     *
     * @return the 数据版本
     */
    public Long getVersionNo() {
	return versionNo;
    }

    /**
     * 设置 数据版本.
     *
     * @param versionNo the new 数据版本
     */
    public void setVersionNo(Long versionNo) {
	this.versionNo = versionNo;
    }

    /**
     * 获取 计费规则ID.
     *
     * @return the 计费规则ID
     */
    public String getPricingValuationId() {
	return pricingValuationId;
    }

    /**
     * 设置 计费规则ID.
     *
     * @param pricingValuationId the new 计费规则ID
     */
    public void setPricingValuationId(String pricingValuationId) {
	this.pricingValuationId = pricingValuationId;
    }

    /**
     * 获取 价格计算表达式.
     *
     * @return the 价格计算表达式
     */
    public String getCaculateExpression() {
	return caculateExpression;
    }

    /**
     * 设置 价格计算表达式.
     *
     * @param caculateExpression the new 价格计算表达式
     */
    public void setCaculateExpression(String caculateExpression) {
	this.caculateExpression = caculateExpression;
    }

    /**
     *  最低费率
     */
	public BigDecimal getMinFeeRate() {
		return minFeeRate;
	}

    /**
     *  最低费率
     */
	public void setMinFeeRate(BigDecimal minFeeRate) {
		this.minFeeRate = minFeeRate;
	}


    /**
     *  最高费率
     */
	public BigDecimal getMaxFeeRate() {
		return maxFeeRate;
	}

    /**
     *  最高费率
     */
	public void setMaxFeeRate(BigDecimal maxFeeRate) {
		this.maxFeeRate = maxFeeRate;
	}

	public String getCombBillTypeName() {
		return combBillTypeName;
	}

	public void setCombBillTypeName(String combBillTypeName) {
		this.combBillTypeName = combBillTypeName;
	}

	public String getCombBillTypeCode() {
		return combBillTypeCode;
	}

	public void setCombBillTypeCode(String combBillTypeCode) {
		this.combBillTypeCode = combBillTypeCode;
	}

	public BigDecimal getWeightLeftRange() {
		return weightLeftRange;
	}

	public void setWeightLeftRange(BigDecimal weightLeftRange) {
		this.weightLeftRange = weightLeftRange;
	}

	public BigDecimal getWeightRightRange() {
		return weightRightRange;
	}

	public void setWeightRightRange(BigDecimal weightRightRange) {
		this.weightRightRange = weightRightRange;
	}

	public BigDecimal getVolumeLeftRange() {
		return volumeLeftRange;
	}

	public void setVolumeLeftRange(BigDecimal volumeLeftRange) {
		this.volumeLeftRange = volumeLeftRange;
	}

	public BigDecimal getVolumeRightRange() {
		return volumeRightRange;
	}

	public void setVolumeRightRange(BigDecimal volumeRightRange) {
		this.volumeRightRange = volumeRightRange;
	}

	public String getIsPickUp() {
		return isPickUp;
	}

	public void setIsPickUp(String isPickUp) {
		this.isPickUp = isPickUp;
	}

	public String getIsVoid() {
		return isVoid;
	}

	public void setIsVoid(String isVoid) {
		this.isVoid = isVoid;
	}

	/**
	 * @return  the deptOrgName
	 */
	public String getDeptOrgName() {
		return deptOrgName;
	}

	/**
	 * @param deptOrgName the deptOrgName to set
	 */
	public void setDeptOrgName(String deptOrgName) {
		this.deptOrgName = deptOrgName;
	}

	/**
	 * @return  the avvrOrgName
	 */
	public String getAvvrOrgName() {
		return avvrOrgName;
	}

	/**
	 * @param avvrOrgName the avvrOrgName to set
	 */
	public void setAvvrOrgName(String avvrOrgName) {
		this.avvrOrgName = avvrOrgName;
	}

	/**
	 * @return  the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return  the schemeCode
	 */
	public String getSchemeCode() {
		return schemeCode;
	}

	/**
	 * @param schemeCode the schemeCode to set
	 */
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
}