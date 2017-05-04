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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/DepartureStandardDto.java
 * 
 * FILE NAME        	: DepartureStandardDto.java
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 线路(有可能是半截)的发车标准Dto
 * @author foss-zhujunyong
 * @date Nov 9, 2012 1:59:21 PM
 * @version 1.0
 */
public class DepartureStandardDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6563289646704284683L;

    // 线路名称
    private String lineName;
    
    // 线路虚拟编码
    private String lineVirtualCode;
    
    // 线路简码
    private String lineSimpleCode;
    
    // 班次
    private Integer order;
    
    // 班次虚拟编码
    private String departureStandardVirtualCode;
    
    // 准点发车时间(eg: 0200)
    private String leaveTime;
    
    // 准点到达时间(eg: 1645)
    private String arriveTime;
    
    // 准点到达时间的天数,默认是0
    private Integer arriveDay;

    // 出发部门编码
    private String sourceCode;
    
    // 到达部门编码
    private String targetCode;
    
    // 时效类型(卡车或普车)
    private String productType;

    // 普车时效（千分之小时）
    private Long commonAging;
    
    
    // 根据传入的时间，算出该传入时间之后的本班次发车时间
    public Date getLeaveDate(Date baseDate){
	if (baseDate == null || leaveTime == null) {
	    return null;
	}
	
	SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
	Date leaveDate = null;
	try {
	    leaveDate = sdf.parse(leaveTime);
	} catch (ParseException e) {
	    return null;
	}
	Calendar leaveCal = Calendar.getInstance();
	leaveCal.setTime(leaveDate);
	
	Calendar baseCal = Calendar.getInstance();
	baseCal.setTime(baseDate);
	
	leaveCal.set(baseCal.get(Calendar.YEAR), baseCal.get(Calendar.MONTH), baseCal.get(Calendar.DATE));
	// 如果发车时间早于传入时间，则取第二天的本班次
	if (leaveCal.before(baseCal)) {
	    leaveCal.add(Calendar.DATE, 1);
	}
	
	return leaveCal.getTime();
    }

    // 根据传入的时间，算出该传入时间之后的本班次到达时间
    public Date getArriveDate(Date baseDate) {
	if (baseDate == null || arriveTime == null) {
	    return null;
	}
	
	SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
	Date arriveDate = null;
	try {
	    arriveDate = sdf.parse(arriveTime);
	} catch (ParseException e) {
	    return null;
	}
	Calendar arriveCal = Calendar.getInstance();
	arriveCal.setTime(arriveDate);
	
	Calendar baseCal = Calendar.getInstance();
	baseCal.setTime(baseDate);
	
	arriveCal.set(baseCal.get(Calendar.YEAR), baseCal.get(Calendar.MONTH), baseCal.get(Calendar.DATE));
	arriveCal.add(Calendar.DATE, arriveDay);

	// 如果不是当天的班车，则还要加一天
	Calendar leaveCal = Calendar.getInstance();
	leaveCal.setTime(getLeaveDate(baseDate));
	if (leaveCal.get(Calendar.DATE) != baseCal.get(Calendar.DATE)) {
	    arriveCal.add(Calendar.DATE, 1);
	}
	
	return arriveCal.getTime();
    }
    
    /**
     * @return  the lineName
     */
    public String getLineName() {
        return lineName;
    }

    
    /**
     * @param lineName the lineName to set
     */
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    
    /**
     * @return  the lineVirtualCode
     */
    public String getLineVirtualCode() {
        return lineVirtualCode;
    }

    
    /**
     * @param lineVirtualCode the lineVirtualCode to set
     */
    public void setLineVirtualCode(String lineVirtualCode) {
        this.lineVirtualCode = lineVirtualCode;
    }

    
    /**
     * @return  the leaveTime
     */
    public String getLeaveTime() {
        return leaveTime;
    }

    
    /**
     * @param leaveTime the leaveTime to set
     */
    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    
    /**
     * @return  the arriveTime
     */
    public String getArriveTime() {
        return arriveTime;
    }

    
    /**
     * @param arriveTime the arriveTime to set
     */
    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }


    
    /**
     * @return  the sourceCode
     */
    public String getSourceCode() {
        return sourceCode;
    }


    
    /**
     * @param sourceCode the sourceCode to set
     */
    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }


    
    /**
     * @return  the targetCode
     */
    public String getTargetCode() {
        return targetCode;
    }


    
    /**
     * @param targetCode the targetCode to set
     */
    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }


    


    
    /**
     * @return  the departureStandardVirtualCode
     */
    public String getDepartureStandardVirtualCode() {
        return departureStandardVirtualCode;
    }


    
    /**
     * @param departureStandardVirtualCode the departureStandardVirtualCode to set
     */
    public void setDepartureStandardVirtualCode(String departureStandardVirtualCode) {
        this.departureStandardVirtualCode = departureStandardVirtualCode;
    }

    
    public Integer getArriveDay() {
        return arriveDay;
    }

    
    public void setArriveDay(Integer arriveDay) {
        this.arriveDay = arriveDay;
    }

    
    public String getProductType() {
        return productType;
    }

    
    public void setProductType(String productType) {
        this.productType = productType;
    }

    
    public String getLineSimpleCode() {
        return lineSimpleCode;
    }

    
    public void setLineSimpleCode(String lineSimpleCode) {
        this.lineSimpleCode = lineSimpleCode;
    }

    
    public Integer getOrder() {
        return order;
    }

    
    public void setOrder(Integer order) {
        this.order = order;
    }

    
    /**
     * @return  the commonAging
     */
    public Long getCommonAging() {
        return commonAging;
    }

    
    /**
     * @param commonAging the commonAging to set
     */
    public void setCommonAging(Long commonAging) {
        this.commonAging = commonAging;
    }


    
    
}
