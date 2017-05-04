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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/ValueAddDto.java
 * 
 * FILE NAME        	: ValueAddDto.java
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
import java.util.List;


/**
 *  增值服務dto
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:zhangdongping,date:2012-10-25 下午5:43:34,content: </p>
 * @author zhangdongping
 * @date 2012-10-25 下午5:43:34
 * @since
 * @version
 */
public class ValueAddDto implements Serializable{
    
    
    /**
     * 
     */
    private static final long serialVersionUID = -8064916106171005920L;

    /**
     *PriceCriteriaDetailEntity 的id
     */
    private String id;

    /**
     *  产品CODE 第三级的产品代码
     */
    private String productCode;
    
    /**
     * 产品name
     */
    private String productName; 
    
    
    /**
     * 费用类型代码
     */
    private String  priceEntityCode;
    
    /**
     *  费用类型名称
     */
    private String priceEntityName;
    
    
    /**
     * 货物类型CODE
     */
    private String goodsTypeCode; 
    
    
    /**
     * 货物类型name
     */
    private String goodsTypeName; 
    
     
    
    /**
     * 费率（小数）或者单价（分）
     */
     
    private BigDecimal feeRate;
    
    /**
     * 最低费用
     */
    private BigDecimal minFee;
    
    
    /**
     * 最高费用
     */
    private BigDecimal maxFee;
    
     
    /**
     * 固定费用
     */
    private BigDecimal fee;
    
   
    
    /**
     * 经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值
     */
    private BigDecimal caculateFee;
    
    
  /**code
         服务子类型:只针对增值服务该栏位有用
               对于代收货款：为退款类型(即日退，三日退，审核退)
             对于签收回单：为返单类型（传真返单，原件返单）
            对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等）     
         其它增值服务：空
     */
    private String subType;
    
    /** name
    服务子类型:只针对增值服务该栏位有用
          对于代收货款：为退款类型(即日退，三日退，审核退)
        对于签收回单：为返单类型（传真返单，原件返单）
       对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等）     
    其它增值服务：空
*/
    private String subTypeName;
    

    /**
     * 计费类别:WEIGHT，按重量计费，单位为公斤，VOLUME 按体积计费，单位为立方；ORIGINALCOST 原始費用（單位為分）
     */
    private String caculateType;
    
    /**
     * 增值计费类型
     */
    private String valueAddCaculateType;
    /**
     * 轻货费率（小数）或者单价（分）
     */
     
    private BigDecimal lightFeeRate;
    
    /**
     * 重货费率（小数）或者单价（分）
     */
    private BigDecimal heavyFeeRate;
    
    /**
     * 最终实际计算的费率
     */
    private BigDecimal actualFeeRate;
    
    /**
     * 打折后的费用
     */
    private BigDecimal discountFee;

    /**
     * 是否可以修改
     */
    private String canmodify;
    
    /**
     * 是否可以删除
     */
    private String candelete;
    
    /**
     * 始发区域ID
     */
    private String deptRegionId;
    
    /**
     * 到达区域ID
     */
    private String arrvRegionId;
    
    /**
     * 其他费用归集类别代码
     */
    private String belongToPriceEntityCode;
    /**
     * 其他费用归集类别名称
     */
    private String belongToPriceEntityName;
    
    
     /**
    * 默认保费申明
    */
    private BigDecimal defaultBF;
    
    /**
     * 采用的折扣方案
     */
    private List<PriceDiscountDto> discountPrograms;
    
    /**
     * 最低费率
     */
    private BigDecimal minFeeRate;
    
    /**
     * 最高费率
     */
    private BigDecimal maxFeeRate;
    
    /**
     * 费用条目描述
     */
    private String description;
    
    /**
     * FOSS折扣前费率
     */
    private BigDecimal initFeeRate;    
    
    /**
     * @BUG编号：DEFECT-8681
     * @功能：合同种类
     * @author:218371-foss-zhaoyanjun
     * @date:2015-05-28
     * @说明：
     * 1.代表合同客户
     */
     private int contractType;

 	public int getContractType() {
 		return contractType;
 	}

 	public void setContractType(int contractType) {
 		this.contractType = contractType;
 	}
    
    public BigDecimal getInitFeeRate() {
		return initFeeRate;
	}

	public void setInitFeeRate(BigDecimal initFeeRate) {
		this.initFeeRate = initFeeRate;
	}
    
    public String getValueAddCaculateType() {
		return valueAddCaculateType;
	}

	public void setValueAddCaculateType(String valueAddCaculateType) {
		this.valueAddCaculateType = valueAddCaculateType;
	}

	/**
     * 获取 默认保费申明.
     *
     * @return the 默认保费申明
     */
    public BigDecimal getDefaultBF() {
        return defaultBF;
    }
    
    /**
     * 设置 默认保费申明.
     *
     * @param defaultBF the new 默认保费申明
     */
    public void setDefaultBF(BigDecimal defaultBF) {
        this.defaultBF = defaultBF;
    }






    /**
     * 获取 其他费用归集类别代码.
     *
     * @return the 其他费用归集类别代码
     */
    public String getBelongToPriceEntityCode() {
        return belongToPriceEntityCode;
    }





    
    /**
     * 设置 其他费用归集类别代码.
     *
     * @param belongToPriceEntityCode the new 其他费用归集类别代码
     */
    public void setBelongToPriceEntityCode(String belongToPriceEntityCode) {
        this.belongToPriceEntityCode = belongToPriceEntityCode;
    }





    
    /**
     * 获取 其他费用归集类别名称.
     *
     * @return the 其他费用归集类别名称
     */
    public String getBelongToPriceEntityName() {
        return belongToPriceEntityName;
    }
    
    /**
     * 设置 其他费用归集类别名称.
     *
     * @param belongToPriceEntityName the new 其他费用归集类别名称
     */
    public void setBelongToPriceEntityName(String belongToPriceEntityName) {
        this.belongToPriceEntityName = belongToPriceEntityName;
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
	 * 获取 到达区域ID.
	 *
	 * @return the 到达区域ID
	 */
	public String getArrvRegionId() {
		return arrvRegionId;
	}

	/**
	 * 设置 到达区域ID.
	 *
	 * @param arrvRegionId the new 到达区域ID
	 */
	public void setArrvRegionId(String arrvRegionId) {
		this.arrvRegionId = arrvRegionId;
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
     * 获取 code 服务子类型:只针对增值服务该栏位有用 对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等） 其它增值服务：空.
     *
     * @return the code 服务子类型:只针对增值服务该栏位有用 对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等） 其它增值服务：空
     */
    public String getSubType() {
        return subType;
    }
    /**
     * 设置 code 服务子类型:只针对增值服务该栏位有用 对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等） 其它增值服务：空.
     *
     * @param subType the new code 服务子类型:只针对增值服务该栏位有用 对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等） 其它增值服务：空
     */
    public void setSubType(String subType) {
        this.subType = subType;
    }
    /**
     * 获取 计费类别:WEIGHT，按重量计费，单位为公斤，VOLUME 按体积计费，单位为立方；ORIGINALCOST 原始費用（單位為分）.
     *
     * @return the 计费类别:WEIGHT，按重量计费，单位为公斤，VOLUME 按体积计费，单位为立方；ORIGINALCOST 原始費用（單位為分）
     */
    public String getCaculateType() {
        return caculateType;
    }
    /**
     * 设置 计费类别:WEIGHT，按重量计费，单位为公斤，VOLUME 按体积计费，单位为立方；ORIGINALCOST 原始費用（單位為分）.
     *
     * @param caculateType the new 计费类别:WEIGHT，按重量计费，单位为公斤，VOLUME 按体积计费，单位为立方；ORIGINALCOST 原始費用（單位為分）
     */
    public void setCaculateType(String caculateType) {
        this.caculateType = caculateType;
    }
    /**
     * 价格计算表达式
     */
    private String caculateExpression;
    
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
     * 获取 priceCriteriaDetailEntity 的id.
     *
     * @return the priceCriteriaDetailEntity 的id
     */
    public String getId() {
        return id;
    }
    /**
     * 设置 priceCriteriaDetailEntity 的id.
     *
     * @param id the new priceCriteriaDetailEntity 的id
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * 获取 产品CODE 第三级的产品代码.
     *
     * @return the 产品CODE 第三级的产品代码
     */
    public String getProductCode() {
        return productCode;
    }
    /**
     * 设置 产品CODE 第三级的产品代码.
     *
     * @param productCode the new 产品CODE 第三级的产品代码
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    /**
     * 获取 产品name.
     *
     * @return the 产品name
     */
    public String getProductName() {
        return productName;
    }
    /**
     * 设置 产品name.
     *
     * @param productName the new 产品name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
    /**
     * 获取 费用类型代码.
     *
     * @return the 费用类型代码
     */
    public String getPriceEntityCode() {
        return priceEntityCode;
    }
    /**
     * 设置 费用类型代码.
     *
     * @param priceEntityCode the new 费用类型代码
     */
    public void setPriceEntityCode(String priceEntityCode) {
        this.priceEntityCode = priceEntityCode;
    }
    /**
     * 获取 费用类型名称.
     *
     * @return the 费用类型名称
     */
    public String getPriceEntityName() {
        return priceEntityName;
    }
    /**
     * 设置 费用类型名称.
     *
     * @param priceEntityName the new 费用类型名称
     */
    public void setPriceEntityName(String priceEntityName) {
        this.priceEntityName = priceEntityName;
    }
    /**
     * 获取 货物类型CODE.
     *
     * @return the 货物类型CODE
     */
    public String getGoodsTypeCode() {
        return goodsTypeCode;
    }
    /**
     * 设置 货物类型CODE.
     *
     * @param goodsTypeCode the new 货物类型CODE
     */
    public void setGoodsTypeCode(String goodsTypeCode) {
        this.goodsTypeCode = goodsTypeCode;
    }
    /**
     * 获取 货物类型name.
     *
     * @return the 货物类型name
     */
    public String getGoodsTypeName() {
        return goodsTypeName;
    }
    /**
     * 设置 货物类型name.
     *
     * @param goodsTypeName the new 货物类型name
     */
    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
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
     * 获取 最低费用.
     *
     * @return the 最低费用
     */
    public BigDecimal getMinFee() {
        return minFee;
    }
    /**
     * 设置 最低费用.
     *
     * @param minFee the new 最低费用
     */
    public void setMinFee(BigDecimal minFee) {
        this.minFee = minFee;
    }
    /**
     * 获取 最高费用.
     *
     * @return the 最高费用
     */
    public BigDecimal getMaxFee() {
        return maxFee;
    }
    /**
     * 设置 最高费用.
     *
     * @param maxFee the new 最高费用
     */
    public void setMaxFee(BigDecimal maxFee) {
        this.maxFee = maxFee;
    }
    /**
     * 获取 固定费用.
     *
     * @return the 固定费用
     */
    public BigDecimal getFee() {
        return fee;
    }
    /**
     * 设置 固定费用.
     *
     * @param fee the new 固定费用
     */
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    /**
     * 获取 经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值.
     *
     * @return the 经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值
     */
    public BigDecimal getCaculateFee() {
        return caculateFee;
    }
    /**
     * 设置 经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值.
     *
     * @param caculateFee the new 经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值
     */
    public void setCaculateFee(BigDecimal caculateFee) {
        this.caculateFee = caculateFee;
    }
    /**
     * 获取 轻货费率（小数）或者单价（分）.
     *
     * @return the 轻货费率（小数）或者单价（分）
     */
    public BigDecimal getLightFeeRate() {
        return lightFeeRate;
    }
    /**
     * 设置 轻货费率（小数）或者单价（分）.
     *
     * @param lightFeeRate the new 轻货费率（小数）或者单价（分）
     */
    public void setLightFeeRate(BigDecimal lightFeeRate) {
        this.lightFeeRate = lightFeeRate;
    }
    /**
     * 获取 重货费率（小数）或者单价（分）.
     *
     * @return the 重货费率（小数）或者单价（分）
     */
    public BigDecimal getHeavyFeeRate() {
        return heavyFeeRate;
    }
    /**
     * 设置 重货费率（小数）或者单价（分）.
     *
     * @param heavyFeeRate the new 重货费率（小数）或者单价（分）
     */
    public void setHeavyFeeRate(BigDecimal heavyFeeRate) {
        this.heavyFeeRate = heavyFeeRate;
    }
    /**
     * 获取 最终实际计算的费率.
     *
     * @return the 最终实际计算的费率
     */
    public BigDecimal getActualFeeRate() {
        return actualFeeRate;
    }
    /**
     * 设置 最终实际计算的费率.
     *
     * @param actualFeeRate the new 最终实际计算的费率
     */
    public void setActualFeeRate(BigDecimal actualFeeRate) {
        this.actualFeeRate = actualFeeRate;
    }
    
    /**
     * 获取 name 服务子类型:只针对增值服务该栏位有用 对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等） 其它增值服务：空.
     *
     * @return the name 服务子类型:只针对增值服务该栏位有用 对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等） 其它增值服务：空
     */
    public String getSubTypeName() {
        return subTypeName;
    }

    /**
     * 设置 name 服务子类型:只针对增值服务该栏位有用 对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等） 其它增值服务：空.
     *
     * @param subTypeName the new name 服务子类型:只针对增值服务该栏位有用 对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等） 其它增值服务：空
     */
    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }
	/**
	 * 获取 打折后的费用.
	 *
	 * @return the 打折后的费用
	 */
	public BigDecimal getDiscountFee() {
		return discountFee;
	}

	/**
	 * 设置 打折后的费用.
	 *
	 * @param discountFee the new 打折后的费用
	 */
	public void setDiscountFee(BigDecimal discountFee) {
		this.discountFee = discountFee;
	}

	/**
	 * 获取 采用的折扣方案.
	 *
	 * @return the 采用的折扣方案
	 */
	public List<PriceDiscountDto> getDiscountPrograms() {
		return discountPrograms;
	}

	/**
	 * 设置 采用的折扣方案.
	 *
	 * @param discountPrograms the new 采用的折扣方案
	 */
	public void setDiscountPrograms(List<PriceDiscountDto> discountPrograms) {
		this.discountPrograms = discountPrograms;
	}
	
	/**
     * 获取最低费率
     * @return
     */
	public BigDecimal getMinFeeRate() {
		return minFeeRate;
	}

	/**
	 * 设置最低费率
	 * @param minFeeRate
	 */
	public void setMinFeeRate(BigDecimal minFeeRate) {
		this.minFeeRate = minFeeRate;
	}

	/**
	 * 获取最高费率
	 * @return
	 */
	public BigDecimal getMaxFeeRate() {
		return maxFeeRate;
	}

	/**
	 * 设置最高费率
	 * @param maxFeeRate
	 */
	public void setMaxFeeRate(BigDecimal maxFeeRate) {
		this.maxFeeRate = maxFeeRate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}