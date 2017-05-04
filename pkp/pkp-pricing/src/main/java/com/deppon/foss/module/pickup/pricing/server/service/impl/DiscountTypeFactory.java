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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/DiscountTypeFactory.java
 * 
 * FILE NAME        	: DiscountTypeFactory.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import com.deppon.foss.module.pickup.pricing.api.server.service.DiscountTypeInterface;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountTypeConstants;
import com.google.inject.Inject;


/**
 * 通过不同的参数，返回不同的折扣方式实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:zhangdongping,date:2012-12-25 下午1:49:38,  </p>
 * @author zhangdongping
 * @date 2012-12-25 下午1:49:38
 * @since
 * @version
 */
public class DiscountTypeFactory {
	/**
	 * 客户合同
	 */
    @Inject
    CustomerContractDiscount customerContractDiscount;
    /**
	 * 渠道折扣
	 */
    @Inject
    ChannelDiscount channelDiscount;
    /**
	 * 产品折扣
	 */
    @Inject
    ProductDiscount productDiscount;
    
    /**
     * 获取 渠道折扣.
     *
     * @return the 渠道折扣
     */
    public ChannelDiscount getChannelDiscount() {
        return channelDiscount;
    }

    /**
     * 设置 渠道折扣.
     *
     * @param channelDiscount the new 渠道折扣
     */
    public void setChannelDiscount(ChannelDiscount channelDiscount) {
        this.channelDiscount = channelDiscount;
    }
    
    /**
     * 获取 产品折扣.
     *
     * @return the 产品折扣
     */
    public ProductDiscount getProductDiscount() {
        return productDiscount;
    }
    
    /**
     * 设置 产品折扣.
     *
     * @param productDiscount the new 产品折扣
     */
    public void setProductDiscount(ProductDiscount productDiscount) {
        this.productDiscount = productDiscount;
    }
    
    /**
     * 获取 客户合同.
     *
     * @return the 客户合同
     */
    public CustomerContractDiscount getCustomerContractDiscount() {
        return customerContractDiscount;
    }
    
    /**
     * 设置 客户合同.
     *
     * @param customerContractDiscount the new 客户合同
     */
    public void setCustomerContractDiscount(
    	CustomerContractDiscount customerContractDiscount) {
        this.customerContractDiscount = customerContractDiscount;
    }
	/**
	 * 根据不同的输入参数，返回不同的折扣实现
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-25 下午1:46:38
	 * 
	 * @param disctounType
	 * 
	 * @return
	 * 
	 * @see
	 */
	public DiscountTypeInterface getDiscountTypeImpl(String disctounType) {
		if (DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT
				.equalsIgnoreCase(disctounType)) {
		    //如果是客户合同
			return customerContractDiscount;
		} else if (DiscountTypeConstants.DISCOUNT_TYPE__CHANEL
				.equalsIgnoreCase(disctounType)) {
		    //如果是渠道折扣
			return channelDiscount;
		} else if (DiscountTypeConstants.DISCOUNT_TYPE__PRODUCT
				.equalsIgnoreCase(disctounType)) {
		    //如果是产品折扣
			return productDiscount;
		} else {
		    //参数传入错误，返回空，外部程序自己处理
			return null;
		}
	}
}