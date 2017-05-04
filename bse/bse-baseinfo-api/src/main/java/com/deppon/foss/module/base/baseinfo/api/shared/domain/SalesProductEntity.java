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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/SalesProductEntity.java
 * 
 * FILE NAME        	: SalesProductEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 营业部适用产品
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class SalesProductEntity  extends BaseEntity {
	
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -3967231352868385515L;

    /**
    * 数据版本号
    */	
    private Long versionNo;
    
    /**
    *虚拟编码
    */	
    private String virtualCode;

    /**
    *产品编码
    */	
    private String productCode;

    /**
    *产品名称
    */	
    private String productName;

    /**
    *营业部编码
    */	
    private String salesDeptCode;

    /**
    *营业部名称
    */	
    private String salesName;

    /**
    *营业部类型
    */	
    private String salesType;

    /**
    *是否启用
    */	
    private String active;

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
	 * @return virtualCode
	 */
	public String getVirtualCode() {
		return virtualCode;
	}

	/**
	 * @param  virtualCode  
	 */
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
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
	 * @return productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param  productName  
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return salesDeptCode
	 */
	public String getSalesDeptCode() {
		return salesDeptCode;
	}

	/**
	 * @param  salesDeptCode  
	 */
	public void setSalesDeptCode(String salesDeptCode) {
		this.salesDeptCode = salesDeptCode;
	}

	/**
	 * @return salesName
	 */
	public String getSalesName() {
		return salesName;
	}

	/**
	 * @param  salesName  
	 */
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	/**
	 * @return salesType
	 */
	public String getSalesType() {
		return salesType;
	}

	/**
	 * @param  salesType  
	 */
	public void setSalesType(String salesType) {
		this.salesType = salesType;
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

}
