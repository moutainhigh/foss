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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/DeliverbillDto.java
 * 
 * FILE NAME        	: DeliverbillDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class PermissionControlDto implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;


    /**
     * 最后库存code
     */
    private String endStockOrgCode;

    /**
     * 库区
     */
    private String goodsAreaCode;
    /**
     * 最终配载部门
     */
    private String lastLoadOrgCode;
    /**
     * 部门角色类型
     */
    private int OrgRoleType;
    /**
     * 是否可以继续下一步
     */
    private boolean isNext;

    public void setNext(boolean isNext) {
        this.isNext = isNext;
    }

    public boolean isNext() {

        return isNext;
    }



    public void setEndStockOrgCode(String endStockOrgCode) {
        this.endStockOrgCode = endStockOrgCode;
    }

    public void setGoodsAreaCode(String goodsAreaCode) {
        this.goodsAreaCode = goodsAreaCode;
    }

    public void setLastLoadOrgCode(String lastLoadOrgCode) {
        this.lastLoadOrgCode = lastLoadOrgCode;
    }

    public void setOrgRoleType(int orgRoleType) {
        OrgRoleType = orgRoleType;
    }


    public int getOrgRoleType() {
        return OrgRoleType;
    }

    public String getEndStockOrgCode() {
        return endStockOrgCode;
    }

    public String getGoodsAreaCode() {
        return goodsAreaCode;
    }

    public String getLastLoadOrgCode() {
        return lastLoadOrgCode;
    }



}