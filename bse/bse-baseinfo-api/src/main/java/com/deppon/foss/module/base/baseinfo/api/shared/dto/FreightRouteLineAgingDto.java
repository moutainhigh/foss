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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/FreightRouteLineAgingDto.java
 * 
 * FILE NAME        	: FreightRouteLineAgingDto.java
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


/**
 * 走货路径线路时效dto
 * @author foss-zhujunyong
 * @date Nov 8, 2012 1:10:51 PM
 * @version 1.0
 */
public class FreightRouteLineAgingDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 684771134378480099L;
    // 到达该起始站点时前面经过的时间(普车)(单位：千分之小时)
    private Long beforeCommonAging;
    // 到达该起始站点时前面经过的时间(卡车)(单位：千分之小时)
    private Long beforeFastAging;
    // 该线路从出发站到到达站经过的时间(普车)(单位：千分之小时)
    private Long currentCommonAging;
    // 该线路从出发站到到达站经过的时间(卡车)(单位：千分之小时)
    private Long currentFastAging;
    // 线路虚拟编码
    private String lineVirtualCode;
    // 出发站部门编码
    private String sourceCode;
    // 到达站部门编码
    private String targetCode;
    // 线路类别 （始发，到达，中转到中转）
    private String lineSort;
    
    /**
     * @return  the beforeCommonAging
     */
    public Long getBeforeCommonAging() {
        return beforeCommonAging;
    }
    
    /**
     * @param beforeCommonAging the beforeCommonAging to set
     */
    public void setBeforeCommonAging(Long beforeCommonAging) {
        this.beforeCommonAging = beforeCommonAging;
    }
    
    /**
     * @return  the beforeFastAging
     */
    public Long getBeforeFastAging() {
        return beforeFastAging;
    }
    
    /**
     * @param beforeFastAging the beforeFastAging to set
     */
    public void setBeforeFastAging(Long beforeFastAging) {
        this.beforeFastAging = beforeFastAging;
    }
    
    /**
     * @return  the currentCommonAging
     */
    public Long getCurrentCommonAging() {
        return currentCommonAging;
    }
    
    /**
     * @param currentCommonAging the currentCommonAging to set
     */
    public void setCurrentCommonAging(Long currentCommonAging) {
        this.currentCommonAging = currentCommonAging;
    }
    
    /**
     * @return  the currentFastAging
     */
    public Long getCurrentFastAging() {
        return currentFastAging;
    }
    
    /**
     * @param currentFastAging the currentFastAging to set
     */
    public void setCurrentFastAging(Long currentFastAging) {
        this.currentFastAging = currentFastAging;
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
     * @return  the lineSort
     */
    public String getLineSort() {
        return lineSort;
    }
    
    /**
     * @param lineSort the lineSort to set
     */
    public void setLineSort(String lineSort) {
        this.lineSort = lineSort;
    }
    
    

}
