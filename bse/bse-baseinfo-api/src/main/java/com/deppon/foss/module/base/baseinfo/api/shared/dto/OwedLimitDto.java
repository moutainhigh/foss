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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/OwedLimitDto.java
 * 
 * FILE NAME        	: OwedLimitDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
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
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 营业部最近三个月最大营业收入信息DTO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-2-26 上午8:27:28 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-2-26 上午8:27:28
 * @since
 * @version
 */
public class OwedLimitDto implements Serializable{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -4627256852728561414L;
    
    /**
     * 营业部部门编码.[必填]
     */
    private String deptCode;
    
    /**
     * 营业部最近3个月最大营业收入.[必填]
     */
    private BigDecimal taking;
    
    /**
     * 营业部临欠额度.
     */
    private BigDecimal owedLimit;

    
    /**
     * 获取 营业部部门编码.
     *
     * @return  the deptCode
     */
    public String getDeptCode() {
        return deptCode;
    }

    
    /**
     * 设置 营业部部门编码.
     *
     * @param deptCode the deptCode to set
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    
    /**
     * 获取 营业部最近3个月最大营业收入.
     *
     * @return  the taking
     */
    public BigDecimal getTaking() {
        return taking;
    }

    
    /**
     * 设置 营业部最近3个月最大营业收入.
     *
     * @param taking the taking to set
     */
    public void setTaking(BigDecimal taking) {
        this.taking = taking;
    }

    
    /**
     * 获取 营业部临欠额度.
     *
     * @return  the owedLimit
     */
    public BigDecimal getOwedLimit() {
        return owedLimit;
    }

    
    /**
     * 设置 营业部临欠额度.
     *
     * @param owedLimit the owedLimit to set
     */
    public void setOwedLimit(BigDecimal owedLimit) {
        this.owedLimit = owedLimit;
    }
    
    

}
