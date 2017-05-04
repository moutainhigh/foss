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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/AirlinesAccountDto.java
 * 
 * FILE NAME        	: AirlinesAccountDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity;
/**
 * 用来存储交互“航空公司账户”的数据库对应实体DTO：SUC-43
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-3 下午3:39:01</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-3 下午3:39:01
 * @since
 * @version
 */
public class AirlinesAccountDto implements Serializable {
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 523776454489470982L;

    //“航空公司账户”实体
    private AirlinesAccountEntity airlinesAccount = new AirlinesAccountEntity();
    
    //使用部门
    private String useDepartment;
    
    //航空公司
    private String airlinesName;
    
    /**
     * @return  the airlinesAccount
     */
    public AirlinesAccountEntity getAirlinesAccount() {
        return airlinesAccount;
    }
    
    /**
     * @param airlinesAccount the airlinesAccount to set
     */
    public void setAirlinesAccount(AirlinesAccountEntity airlinesAccount) {
        this.airlinesAccount = airlinesAccount;
    }

    /**
     * @return  the useDepartment
     */
    public String getUseDepartment() {
        return useDepartment;
    }

    /**
     * @param useDepartment the useDepartment to set
     */
    public void setUseDepartment(String useDepartment) {
        this.useDepartment = useDepartment;
    }

    /**
     * @return  the airlinesName
     */
    public String getAirlinesName() {
        return airlinesName;
    }
    
    /**
     * @param airlinesName the airlinesName to set
     */
    public void setAirlinesName(String airlinesName) {
        this.airlinesName = airlinesName;
    }
}
