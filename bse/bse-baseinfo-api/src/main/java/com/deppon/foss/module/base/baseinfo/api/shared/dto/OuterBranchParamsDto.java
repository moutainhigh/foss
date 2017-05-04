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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/OuterBranchParamsDto.java
 * 
 * FILE NAME        	: OuterBranchParamsDto.java
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
import java.util.Date;

/**
 * 查询外部网点输入参数的DTO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-1 下午2:13:45 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-1 下午2:13:45
 * @since
 * @version
 */
public class OuterBranchParamsDto implements Serializable{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 2203985059831902056L;
    
    /**
     * 目的站（城市编码）.
     */
    private String cityCode;
    
    /**
     * 代理网点名称.
     */
    private String agentDeptName;
    
    /**
     * 代理网点编码.
     */
    private String agentDeptCode;
    
    /**
     * 代理网点简称.
     */
    private String simplename;
    
    /**
     * 代理网点类型：DictionaryValueConstants.OUTERBRANCH_TYPE_KY：空运代理网点
     *           DictionaryValueConstants.OUTERBRANCH_TYPE_PX：偏线代理网点
     */
    private String branchType;
    
    /**
     * 是否可自提  是：FossConstants.YES 否：FossConstants.NO
     */
    private String pickupSelf;

    /**
     * 是否送货上门 是：FossConstants.YES 否：FossConstants.NO
     */
    private String pickupToDoor;
    
    /**
     * 用于版本控制时间查询 date为空时，查询当前有效记录；不为空时：可以查询该时间段的记录.
     */
    private Date date;
 
    /**
     * 是否机场
     */
    private String isAirport;
    
    /**
     * 获取 目的站（城市编码）.
     *
     * @return  the cityCode
     */
    public String getCityCode() {
        return cityCode;
    }

    
    /**
     * 设置 目的站（城市编码）.
     *
     * @param cityCode the cityCode to set
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    
    /**
     * 获取 代理网点名称.
     *
     * @return  the agentDeptName
     */
    public String getAgentDeptName() {
        return agentDeptName;
    }

    
    /**
     * 设置 代理网点名称.
     *
     * @param agentDeptName the agentDeptName to set
     */
    public void setAgentDeptName(String agentDeptName) {
        this.agentDeptName = agentDeptName;
    }

    
    /**
     * 获取 代理网点编码.
     *
     * @return  the agentDeptCode
     */
    public String getAgentDeptCode() {
        return agentDeptCode;
    }

    
    /**
     * 设置 代理网点编码.
     *
     * @param agentDeptCode the agentDeptCode to set
     */
    public void setAgentDeptCode(String agentDeptCode) {
        this.agentDeptCode = agentDeptCode;
    }

    
    /**
     * 获取 代理网点简称.
     *
     * @return  the simplename
     */
    public String getSimplename() {
        return simplename;
    }

    
    /**
     * 设置 代理网点简称.
     *
     * @param simplename the simplename to set
     */
    public void setSimplename(String simplename) {
        this.simplename = simplename;
    }

    
    /**
     * 获取 代理网点类型：DictionaryValueConstants.
     *
     * @return  the branchType
     */
    public String getBranchType() {
        return branchType;
    }

    
    /**
     * 设置 代理网点类型：DictionaryValueConstants.
     *
     * @param branchType the branchType to set
     */
    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    
    /**
     * 获取 是否可自提  是：FossConstants.
     *
     * @return  the pickupSelf
     */
    public String getPickupSelf() {
        return pickupSelf;
    }

    
    /**
     * 设置 是否可自提  是：FossConstants.
     *
     * @param pickupSelf the pickupSelf to set
     */
    public void setPickupSelf(String pickupSelf) {
        this.pickupSelf = pickupSelf;
    }

    
    /**
     * 获取 是否送货上门 是：FossConstants.
     *
     * @return  the pickupToDoor
     */
    public String getPickupToDoor() {
        return pickupToDoor;
    }

    
    /**
     * 设置 是否送货上门 是：FossConstants.
     *
     * @param pickupToDoor the pickupToDoor to set
     */
    public void setPickupToDoor(String pickupToDoor) {
        this.pickupToDoor = pickupToDoor;
    }

    
    /**
     * 获取 用于版本控制时间查询 date为空时，查询当前有效记录；不为空时：可以查询该时间段的记录.
     *
     * @return  the date
     */
    public Date getDate() {
        return date;
    }

    
    /**
     * 设置 用于版本控制时间查询 date为空时，查询当前有效记录；不为空时：可以查询该时间段的记录.
     *
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }


	public String getIsAirport() {
		return isAirport;
	}


	public void setIsAirport(String isAirport) {
		this.isAirport = isAirport;
	}
    
    
    
    
}
