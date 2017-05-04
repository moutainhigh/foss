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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/AirlinesAgentDto.java
 * 
 * FILE NAME        	: AirlinesAgentDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity;
/**
 * 用来存储交互“航空公司代理人”的数据库对应实体DTO：SUC-61 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-3 下午3:00:05</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-3 下午3:00:05
 * @since
 * @version
 */
public class AirlinesAgentDto implements Serializable {
    
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -3577894079057801912L;
    
    //“航空公司代理人”实体
    private AirlinesAgentEntity airlinesAgent = new AirlinesAgentEntity();
    
    //配置部门
    private String dispatchDepartment;
    
    //始发城市
    private String originatingCity;
    
    //航空公司
    private String airlinesName;
    
    //是否外部代理
    private String isOutAgent;
    /**
     * 获取 是否外部代理.
     *
     * @return  the isOutAgent
     */
    public String getIsOutAgent() {
		return isOutAgent;
	}
    /**
     * 设置 是否外部代理.
     *
     * @param isOutAgent the isOutAgent to set
     */
	public void setIsOutAgent(String isOutAgent) {
		this.isOutAgent = isOutAgent;
	}

    /**
     * @return  the dispatchDepartment
     */
    public String getDispatchDepartment() {
        return dispatchDepartment;
    }
    
    /**
     * @param dispatchDepartment the dispatchDepartment to set
     */
    public void setDispatchDepartment(String dispatchDepartment) {
        this.dispatchDepartment = dispatchDepartment;
    }

    /**
     * @return  the airlinesAgent
     */
    public AirlinesAgentEntity getAirlinesAgent() {
        return airlinesAgent;
    }
    
    /**
     * @param airlinesAgent the airlinesAgent to set
     */
    public void setAirlinesAgent(AirlinesAgentEntity airlinesAgent) {
        this.airlinesAgent = airlinesAgent;
    }
    
    /**
     * @return  the originatingCity
     */
    public String getOriginatingCity() {
        return originatingCity;
    }
    
    /**
     * @param originatingCity the originatingCity to set
     */
    public void setOriginatingCity(String originatingCity) {
        this.originatingCity = originatingCity;
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
