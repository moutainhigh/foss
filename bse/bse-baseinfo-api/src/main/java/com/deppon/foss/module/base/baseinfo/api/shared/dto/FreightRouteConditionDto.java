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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/FreightRouteConditionDto.java
 * 
 * FILE NAME        	: FreightRouteConditionDto.java
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

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;


/**
 * 查询走货路径时需要用到的查询条件
 * @author foss-zhujunyong
 * @date Nov 21, 2012 4:15:04 PM
 * @version 1.0
 */
public class FreightRouteConditionDto implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -1767125047153892910L;
    
    // 查询条件，出发部门，可能是营业部，驻地营业部，外场，空运总调
    private String sourceCode;
    // 查询条件，到达部门，可能是营业部，驻地营业部，外场，空运总调，偏线代理，空运代理
    private String targetCode;
    // 出发营业部（如果是外场，需要转换成驻地营业部），用于查询网点组
    private String netGroupStartCode;
    // 到达营业部（如果是外场，需要转换成驻地营业部），用于查询网点组
    private String netGroupEndCode;
    // 出发外场（空运总调）
    private String routeStartCode;
    // 到达外场（空运总调）
    private String routeEndCode;
//    // 出发外场驻地营业部部门编码,只有传入编码是营业部或外场的时候才有值
//    private String stationStartCode;
//    // 到达外场驻地营业部部门编码,只有传入编码是营业部或外场的时候才有值
//    private String stationEndCode;
    // 第三级产品代码
    private String productCode;
    // 产品名称
    private String productName;
    // 优先级（卡车，普车）
    private String priority;
    // 代理网点编码(当查询偏线或空运路径时，将把代理公司的编码用于替代代理网点编码查找走货路径，找完要再换回成代理网点的编码返回 ，这里用于存储网点编码)
    private String agencySiteCode;

    // 出发部门实体
    private OrgAdministrativeInfoEntity sourceDP;

    // 到达部门是否空运总调
    private boolean isTargetDoAirDispatch;
    
    // 是否用外场的部门编码替换了空运总调的部门编码去计算走货路径。默认是false，如果替换了则设置为true，返回时要替换回来
    private boolean isReplaced;
    // 空运总调部门编码，用于外场替换空运总调时计算走货路径，保留原空运总调的编码，以便返回时替换回来。
    private String airDispatchCode;
    // 日期
    private Date date;
    
    // 验证该实体是否合法
    public boolean validate(){
	return StringUtils.isNotBlank(sourceCode) 
		&& StringUtils.isNotBlank(targetCode)
		&& StringUtils.isNotBlank(routeStartCode)
		&& StringUtils.isNotBlank(routeEndCode)
		&& StringUtils.isNotBlank(productCode)
		&& StringUtils.isNotBlank(priority)
		&& sourceDP != null;
    }
    
    /**
     * @return  the agencySiteCode
     */
    public String getAgencySiteCode() {
        return agencySiteCode;
    }

    
    /**
     * @param agencySiteCode the agencySiteCode to set
     */
    public void setAgencySiteCode(String agencySiteCode) {
        this.agencySiteCode = agencySiteCode;
    }

    
    public String getSourceCode() {
        return sourceCode;
    }

    
    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    
    public String getTargetCode() {
        return targetCode;
    }

    
    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

    
    public String getNetGroupStartCode() {
        return netGroupStartCode;
    }

    
    public void setNetGroupStartCode(String netGroupStartCode) {
        this.netGroupStartCode = netGroupStartCode;
    }

    
    public String getNetGroupEndCode() {
        return netGroupEndCode;
    }

    
    public void setNetGroupEndCode(String netGroupEndCode) {
        this.netGroupEndCode = netGroupEndCode;
    }

    
    public String getRouteStartCode() {
        return routeStartCode;
    }

    
    public void setRouteStartCode(String routeStartCode) {
        this.routeStartCode = routeStartCode;
    }

    
    public String getRouteEndCode() {
        return routeEndCode;
    }

    
    public void setRouteEndCode(String routeEndCode) {
        this.routeEndCode = routeEndCode;
    }


    
    public OrgAdministrativeInfoEntity getSourceDP() {
        return sourceDP;
    }


    
    public void setSourceDP(OrgAdministrativeInfoEntity sourceDP) {
        this.sourceDP = sourceDP;
    }


    
    public String getProductCode() {
        return productCode;
    }


    
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }


    
    public String getPriority() {
        return priority;
    }


    
    public void setPriority(String priority) {
        this.priority = priority;
    }


    
//    public String getStationStartCode() {
//        return stationStartCode;
//    }
//
//
//    
//    public void setStationStartCode(String stationStartCode) {
//        this.stationStartCode = stationStartCode;
//    }
//
//
//    
//    public String getStationEndCode() {
//        return stationEndCode;
//    }
//
//
//    
//    public void setStationEndCode(String stationEndCode) {
//        this.stationEndCode = stationEndCode;
//    }

    /**
     * @return  the isTargetDoAirDispatch
     */
    public boolean isTargetDoAirDispatch() {
        return isTargetDoAirDispatch;
    }

    /**
     * @param isTargetDoAirDispatch the isTargetDoAirDispatch to set
     */
    public void setTargetDoAirDispatch(boolean isTargetDoAirDispatch) {
        this.isTargetDoAirDispatch = isTargetDoAirDispatch;
    }

    public boolean isReplaced() {
        return isReplaced;
    }

    
    public void setReplaced(boolean isReplaced) {
        this.isReplaced = isReplaced;
    }

    public String getAirDispatchCode() {
        return airDispatchCode;
    }

    
    public void setAirDispatchCode(String airDispatchCode) {
        this.airDispatchCode = airDispatchCode;
    }



    /** 
     * <p>toString</p> 
     * @author foss-zhujunyong
     * @date Jan 18, 2013 10:19:24 AM
     * @return 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("FreightRouteConditionDto [sourceCode=");
	builder.append(sourceCode);
	builder.append(", targetCode=");
	builder.append(targetCode);
	builder.append(", netGroupStartCode=");
	builder.append(netGroupStartCode);
	builder.append(", netGroupEndCode=");
	builder.append(netGroupEndCode);
	builder.append(", routeStartCode=");
	builder.append(routeStartCode);
	builder.append(", routeEndCode=");
	builder.append(routeEndCode);
//	builder.append(", stationStartCode=");
//	builder.append(stationStartCode);
//	builder.append(", stationEndCode=");
//	builder.append(stationEndCode);
	builder.append(", productCode=");
	builder.append(productCode);
	builder.append(", priority=");
	builder.append(priority);
	builder.append(", sourceDP=");
	builder.append(sourceDP);
	builder.append("]");
	return builder.toString();
    }

	
	public Date getDate() {
		return date;
	}

	
	public void setDate(Date date) {
		this.date = date;
	}

	
	public String getProductName() {
		return productName;
	}

	
	public void setProductName(String productName) {
		this.productName = productName;
	}

    
    

    


    
}
