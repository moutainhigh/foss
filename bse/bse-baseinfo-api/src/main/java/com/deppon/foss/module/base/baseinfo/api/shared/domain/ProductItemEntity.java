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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/ProductItemEntity.java
 * 
 * FILE NAME        	: ProductItemEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.shared.domain
 * FILE    NAME: ProductItemEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 产品条目实体
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-4 下午2:15:36
 */
public class ProductItemEntity extends BaseEntity {
	
    /**
	*serialVersionUID
	*/
	private static final long serialVersionUID = -4131814570508062842L;
	
	/**
	 * 货物ID
	 */
    private String goodstypeId;

	/**
	 * 货物CODE
	 */
    private String goodstypeCode;

	/**
	 * 产品ID
	 */
    private String productId;
    
	/**
	 * 产品CODE
	 */
    private String productCode;
    
	/**
	 * 条目CODE
	 */
    private String code;

	/**
	 * 条目名称
	 */
    private String name;

	/**
	 * 是否有效
	 */
    private String active;

	/**
	 * 描述
	 */
    private String description;
    
	/**
	 * 版本号
	 */
    private Long versionNo;

	/**
	 * 最低一票
	 */
    private Long feeBottom;

	/**
	 * 开始时间
	 */
    private Date beginTime;

	/**
	 * 结束时间
	 */
    private Date endTime;
    
	/**
	 * 创建组织编码
	 */
    private String createOrgCode;

	/**
	 * 更新组织编码
	 */
    private String modifyOrgCode;

	/**
	 * @return goodstypeId
	 */
	public String getGoodstypeId() {
		return goodstypeId;
	}

	/**
	 * @param  goodstypeId  
	 */
	public void setGoodstypeId(String goodstypeId) {
		this.goodstypeId = goodstypeId;
	}

	/**
	 * @return goodstypeCode
	 */
	public String getGoodstypeCode() {
		return goodstypeCode;
	}

	/**
	 * @param  goodstypeCode  
	 */
	public void setGoodstypeCode(String goodstypeCode) {
		this.goodstypeCode = goodstypeCode;
	}

	/**
	 * @return productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param  productId  
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param  productCode  
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param  code  
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param  name  
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param  description  
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return versionNo
	 */
	public Long getVersionNo() {
		return versionNo;
	}

	/**
	 * @param  versionNo  
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @return feeBottom
	 */
	public Long getFeeBottom() {
		return feeBottom;
	}

	/**
	 * @param  feeBottom  
	 */
	public void setFeeBottom(Long feeBottom) {
		this.feeBottom = feeBottom;
	}

	/**
	 * @return beginTime
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * @param  beginTime  
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param  endTime  
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param  createOrgCode  
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return modifyOrgCode
	 */
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	/**
	 * @param  modifyOrgCode  
	 */
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}
}