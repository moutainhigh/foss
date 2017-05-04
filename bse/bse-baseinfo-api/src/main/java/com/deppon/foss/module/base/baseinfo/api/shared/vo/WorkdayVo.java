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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/WorkdayVo.java
 * 
 * FILE NAME        	: WorkdayVo.java
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
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkdayEntity;


/**
 * 工作日信息VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-12-24 下午3:36:21</p>
 * @author 094463-foss-xieyantao
 * @date 2012-12-24 下午3:36:21
 * @since
 * @version
 */
public class WorkdayVo implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -1866354327165188491L;
    
    /**
     * 工作日.
     */
    private WorkdayEntity entity;
    
    /**
     * 工作月份,唯一标识,例：201203.
     */
    private String workMonth;
    
    /**
     * 获取 工作日.
     *
     * @return  the entity
     */
    public WorkdayEntity getEntity() {
        return entity;
    }
    
    /**
     * 设置 工作日.
     *
     * @param entity the entity to set
     */
    public void setEntity(WorkdayEntity entity) {
        this.entity = entity;
    }

    
    /**
     * 获取 工作月份,唯一标识,例：201203.
     *
     * @return  the workMonth
     */
    public String getWorkMonth() {
        return workMonth;
    }

    
    /**
     * 设置 工作月份,唯一标识,例：201203.
     *
     * @param workMonth the workMonth to set
     */
    public void setWorkMonth(String workMonth) {
        this.workMonth = workMonth;
    }
    
    
    

}
