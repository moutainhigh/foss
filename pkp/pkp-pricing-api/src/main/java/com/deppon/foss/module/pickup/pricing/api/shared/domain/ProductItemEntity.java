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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/ProductItemEntity.java
 * 
 * FILE NAME        	: ProductItemEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 
 * ProductItemEntity
 * 产品条目
 * DP-Foss-YueHongJie
 * 2012-11-21 上午10:43:41
 * 
 * @version 1.0.0
 *
 */
public class ProductItemEntity extends BaseEntity {
    
    private static final long serialVersionUID = 3237870471191823568L;
    /**
     *  货物ID
     */
    private String goodstypeId;
    /**
     *  货物CODE
     */
    private String goodstypeCode;
    /**
     *  货物名称
     */
    private String goodstypeName;
    /**
     *  产品ID
     */
    private String productId;
    /**
     *  产品CODE
     */
    private String productCode;
    /**
     *  条目CODE
     */
    private String code;
    /**
     *  条目名称
     */
    private String name;
    /**
     *  是否有效
     */
    private String active;
    /**
     *  描述
     */
    private String mark;
    /**
     *  版本号
     */
    private Long versionNo;
    /**
     *  最低一票
     */
    private Long feeBottom;
    /**
     *  开始时间
     */
    private Date beginTime;
    /**
     *  结束时间
     */
    private Date endTime;
    /**
     *  创建人所在组织
     */
    private String createOrgCode;
    /**
     *  修改人所在组织
     */
    private String modifyOrgCode;
    /**
     * 创建人姓名
     */
    private String createUserName;
 

    
    private String description;
    
    
    
    public String getDescription() {
    	return description;
    }
    
    
    public void setDescription(String description) {
    	this.description = description;
    }

	/**
     * 获取 货物名称.
     *
     * @return the 货物名称
     */
    public String getGoodstypeName() {
		return goodstypeName;
	}


	/**
	 * 设置 货物名称.
	 *
	 * @param goodstypeName the new 货物名称
	 */
	public void setGoodstypeName(String goodstypeName) {
		this.goodstypeName = goodstypeName;
	}


	/**
	 * 获取 货物ID.
	 *
	 * @return the 货物ID
	 */
	public String getGoodstypeId() {
        return goodstypeId;
    }

    
    /**
     * 设置 货物ID.
     *
     * @param goodstypeId the new 货物ID
     */
    public void setGoodstypeId(String goodstypeId) {
        this.goodstypeId = goodstypeId;
    }

    
    /**
     * 获取 货物CODE.
     *
     * @return the 货物CODE
     */
    public String getGoodstypeCode() {
        return goodstypeCode;
    }

    
    /**
     * 设置 货物CODE.
     *
     * @param goodstypeCode the new 货物CODE
     */
    public void setGoodstypeCode(String goodstypeCode) {
        this.goodstypeCode = goodstypeCode;
    }

    
    /**
     * 获取 产品ID.
     *
     * @return the 产品ID
     */
    public String getProductId() {
        return productId;
    }

    
    /**
     * 设置 产品ID.
     *
     * @param productId the new 产品ID
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    
    /**
     * 获取 产品CODE.
     *
     * @return the 产品CODE
     */
    public String getProductCode() {
        return productCode;
    }

    
    /**
     * 设置 产品CODE.
     *
     * @param productCode the new 产品CODE
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * 获取 条目CODE.
     *
     * @return the 条目CODE
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置 条目CODE.
     *
     * @param code the new 条目CODE
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取 条目名称.
     *
     * @return the 条目名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 条目名称.
     *
     * @param name the new 条目名称
     */
    public void setName(String name) {
        this.name = name;
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
     * @param active the new 是否有效
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * 获取 描述.
     *
     * @return the 描述
     */
    public String getMark() {
        return mark;
    }

    /**
     * 设置 描述.
     *
     * @param mark the new 描述
     */
    public void setMark(String mark) {
        this.mark = mark;
    }

    /**
     * 获取 版本号.
     *
     * @return the 版本号
     */
    public Long getVersionNo() {
        return versionNo;
    }

    /**
     * 设置 版本号.
     *
     * @param versionNo the new 版本号
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    /**
     * 获取 最低一票.
     *
     * @return the 最低一票
     */
    public Long getFeeBottom() {
        return feeBottom;
    }

    /**
     * 设置 最低一票.
     *
     * @param feeBottom the new 最低一票
     */
    public void setFeeBottom(Long feeBottom) {
        this.feeBottom = feeBottom;
    }

    /**
     * 获取 开始时间.
     *
     * @return the 开始时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置 开始时间.
     *
     * @param beginTime the new 开始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取 结束时间.
     *
     * @return the 结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置 结束时间.
     *
     * @param endTime the new 结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取 创建人所在组织.
     *
     * @return the 创建人所在组织
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }

    /**
     * 设置 创建人所在组织.
     *
     * @param createOrgCode the new 创建人所在组织
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    /**
     * 获取 修改人所在组织.
     *
     * @return the 修改人所在组织
     */
    public String getModifyOrgCode() {
        return modifyOrgCode;
    }

    /**
     * 设置 修改人所在组织.
     *
     * @param modifyOrgCode the new 修改人所在组织
     */
    public void setModifyOrgCode(String modifyOrgCode) {
        this.modifyOrgCode = modifyOrgCode;
    }


	/**
	 * 获取 创建人姓名.
	 *
	 * @return the 创建人姓名
	 */
	public String getCreateUserName() {
		return createUserName;
	}


	/**
	 * 设置 创建人姓名.
	 *
	 * @param createUserName the new 创建人姓名
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
    
}