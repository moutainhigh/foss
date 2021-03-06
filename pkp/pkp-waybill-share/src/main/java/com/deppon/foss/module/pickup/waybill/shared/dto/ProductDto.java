/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dto/ProductDto.java
 * 
 * FILE NAME        	: ProductDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

/**
 * 产品DTO
 * @author WangQianJin
 * @date 2013-7-22 下午9:01:04
 */
public class ProductDto {

	//营业部编码
    private String salesDeptCode;
    
    //营业部类型
    private String salesType;
    
    //产品级别
    private String levels;
    
    //是否有效
    private String active;

    
    public String getSalesDeptCode() {
        return salesDeptCode;
    }

    
    public void setSalesDeptCode(String salesDeptCode) {
        this.salesDeptCode = salesDeptCode;
    }

    
    public String getLevels() {
        return levels;
    }

    
    public void setLevels(String levels) {
        this.levels = levels;
    }

    
    public String getActive() {
        return active;
    }

    
    public void setActive(String active) {
        this.active = active;
    }


	public String getSalesType() {
		return salesType;
	}


	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}
}