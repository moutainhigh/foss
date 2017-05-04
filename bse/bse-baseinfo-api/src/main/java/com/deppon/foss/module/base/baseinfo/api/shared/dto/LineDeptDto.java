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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/LineDeptDto.java
 * 
 * FILE NAME        	: LineDeptDto.java
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
 * 线路出发站到达站编码、名称封装类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-12-18
 * 下午3:47:18
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-12-18 下午3:47:18
 * @since
 * @version
 */
public class LineDeptDto implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -5587804283281285146L;
    
    /**
     * 出发/到达站名称.
     */
    private String name;
    
    /**
     * 出发/到达站编码.
     */
    private String code;
    
    /**
     * 获取 出发/到达站名称.
     *
     * @return  the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * 设置 出发/到达站名称.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 获取 出发/到达站编码.
     *
     * @return  the code
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 设置 出发/到达站编码.
     *
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    

}
