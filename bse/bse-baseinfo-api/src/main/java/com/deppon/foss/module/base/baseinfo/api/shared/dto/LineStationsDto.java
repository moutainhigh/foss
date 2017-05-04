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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/LineStationsDto.java
 * 
 * FILE NAME        	: LineStationsDto.java
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
import java.util.Map;


/**
 * 供走货路径选择线路时返回出发站，到达站列表
 * @author foss-zhujunyong
 * @date Dec 13, 2012 5:01:01 PM
 * @version 1.0
 */
public class LineStationsDto implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1209005716875710992L;

    // 线路简码
    private String lineSimpleCode;
    
    // 站点部门编码列表
    private Map<String, String> stationMap;

    
    public String getLineSimpleCode() {
        return lineSimpleCode;
    }

    
    public void setLineSimpleCode(String lineSimpleCode) {
        this.lineSimpleCode = lineSimpleCode;
    }


    
    public Map<String, String> getStationMap() {
        return stationMap;
    }


    
    public void setStationMap(Map<String, String> stationMap) {
        this.stationMap = stationMap;
    }


 

    
    
    
}
