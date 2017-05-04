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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/NetWorkGroupDto.java
 * 
 * FILE NAME        	: NetWorkGroupDto.java
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


/**
 * 网点组信息封装类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-12-18 下午10:55:03</p>
 * @author 094463-foss-xieyantao
 * @date 2012-12-18 下午10:55:03
 * @since
 * @version
 */
public class NetWorkGroupDto extends NetGroupDto{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -719219409563835083L;

    /**
     * 出发营业部名称字符串（冗余）.
     */
    private String sourceOrgName;
    
    /**
     * 到达营业部名称字符串（冗余）.
     */
    private String targetOrgName;

    
    /**
     * 获取 出发营业部名称字符串（冗余）.
     *
     * @return  the sourceOrgName
     */
    public String getSourceOrgName() {
        return sourceOrgName;
    }

    
    /**
     * 设置 出发营业部名称字符串（冗余）.
     *
     * @param sourceOrgName the sourceOrgName to set
     */
    public void setSourceOrgName(String sourceOrgName) {
        this.sourceOrgName = sourceOrgName;
    }

    
    /**
     * 获取 到达营业部名称字符串（冗余）.
     *
     * @return  the targetOrgName
     */
    public String getTargetOrgName() {
        return targetOrgName;
    }

    
    /**
     * 设置 到达营业部名称字符串（冗余）.
     *
     * @param targetOrgName the targetOrgName to set
     */
    public void setTargetOrgName(String targetOrgName) {
        this.targetOrgName = targetOrgName;
    }
    

}
