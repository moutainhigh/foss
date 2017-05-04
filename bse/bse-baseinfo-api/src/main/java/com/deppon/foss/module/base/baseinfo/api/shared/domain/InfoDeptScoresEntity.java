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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/InfoDeptScoresEntity.java
 * 
 * FILE NAME        	: InfoDeptScoresEntity.java
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
 * 
 * 用来存储交互“信息部标准得分”的数据库对应实体：SUC-222
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-11 下午5:09:25</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-11 下午5:09:25
 * @since
 * @version
 */
public class InfoDeptScoresEntity extends BaseEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 9031562457420652879L;

    //标准ID
    private String stdId;

    //标准分值
    private BigDecimal infoDeptStdScore;

    //信息部ID
    private String infodeptId;

    //是否启用
    private String active;
    
    //备注
    private String notes;
    
    /**
     * @return  the stdId
     */
    public String getStdId() {
        return stdId;
    }

    /**
     * @param stdId the stdId to set
     */
    public void setStdId(String stdId) {
        this.stdId = stdId;
    }

    /**
     * @return  the infoDeptStdScore
     */
    public BigDecimal getInfoDeptStdScore() {
        return infoDeptStdScore;
    }
    
    /**
     * @param infoDeptStdScore the infoDeptStdScore to set
     */
    public void setInfoDeptStdScore(BigDecimal infoDeptStdScore) {
        this.infoDeptStdScore = infoDeptStdScore;
    }
    
    /**
     * @return  the infodeptId
     */
    public String getInfodeptId() {
        return infodeptId;
    }
    
    /**
     * @param infodeptId the infodeptId to set
     */
    public void setInfodeptId(String infodeptId) {
        this.infodeptId = infodeptId;
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
    
    /**
     * @return  the notes
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
