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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/LineInfoDto.java
 * 
 * FILE NAME        	: LineInfoDto.java
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
 * 线路信息DTO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-12-11 下午1:44:02 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-12-11 下午1:44:02
 * @since
 * @version
 */
public class LineInfoDto implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 8403540256846958377L;
    
    /**
     * 线路唯一标识.
     */
    private String lineId;
    
    /**
     * 始发站唯一标识.
     */
    private String startsiteId;
    
    /**
     * 到达站唯一标识.
     */
    private String arrivalsiteId;
    
    /**
     * 线路名称（不可重复）.
     */
    private String lineName;
    
    /**
     * 操作标识：0：新增 1：删除 默认0.
     */
    private int isDeleted;
    
    /**
     * 卡车时效（千分之小时）
     */
    private Float runtime;
    
    /**
     * 线路距离(公里)
     */
    private Float mile;
    
	/**
     * 获取 线路唯一标识.
     *
     * @return  the lineId
     */
    public String getLineId() {
        return lineId;
    }
    
    /**
     * 设置 线路唯一标识.
     *
     * @param lineId the lineId to set
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }
    
    /**
     * 获取 始发站唯一标识.
     *
     * @return  the startsiteId
     */
    public String getStartsiteId() {
        return startsiteId;
    }
    
    /**
     * 设置 始发站唯一标识.
     *
     * @param startsiteId the startsiteId to set
     */
    public void setStartsiteId(String startsiteId) {
        this.startsiteId = startsiteId;
    }
    
    /**
     * 获取 到达站唯一标识.
     *
     * @return  the arrivalsiteId
     */
    public String getArrivalsiteId() {
        return arrivalsiteId;
    }
    
    /**
     * 设置 到达站唯一标识.
     *
     * @param arrivalsiteId the arrivalsiteId to set
     */
    public void setArrivalsiteId(String arrivalsiteId) {
        this.arrivalsiteId = arrivalsiteId;
    }
    
    /**
     * 获取 线路名称（不可重复）.
     *
     * @return  the lineName
     */
    public String getLineName() {
        return lineName;
    }
    
    /**
     * 设置 线路名称（不可重复）.
     *
     * @param lineName the lineName to set
     */
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
    
    /**
     * 获取 操作标识：0：新增 1：删除 默认0.
     *
     * @return  the isDeleted
     */
    public int getIsDeleted() {
        return isDeleted;
    }
    
    /**
     * 设置 操作标识：0：新增 1：删除 默认0.
     *
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

	public Float getRuntime() {
		return runtime;
	}

	public void setRuntime(Float runtime) {
		this.runtime = runtime;
	}

	public Float getMile() {
		return mile;
	}

	public void setMile(Float mile) {
		this.mile = mile;
	}


   

    
}
