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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/InfoDeptScoresStdEntity.java
 * 
 * FILE NAME        	: InfoDeptScoresStdEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 用来存储交互“信息部基础标准”的基础信息数据库对应实体：SUC-222
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-25 下午3:12:05</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-25 下午3:12:05
 * @since
 * @version
 */
public class InfoDeptScoresStdEntity extends BaseEntity {
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 6814178787707472896L;

    //标准内容
    private String stdContent;

    //标准分值
    private BigDecimal stdScore;
    
    //是否启用
    private String active;

    public String getStdContent() {
	return stdContent;
    }

    public void setStdContent(String stdContent) {
	this.stdContent = stdContent;
    }

    public BigDecimal getStdScore() {
	return stdScore;
    }

    public void setStdScore(BigDecimal stdScore) {
	this.stdScore = stdScore;
    }

    /**
     * @return  the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
}
