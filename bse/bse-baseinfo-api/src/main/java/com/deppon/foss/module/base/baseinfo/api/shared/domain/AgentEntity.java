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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/AgentEntity.java
 * 
 * FILE NAME        	: AgentEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * 用来存储交互“代理人”的数据库对应实体：SUC-61
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-11 下午5:28:56</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-11 下午5:28:56
 * @since
 * @version
 */
public class AgentEntity extends BaseEntity {
    
    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -4503437420515904950L;

    /**
     * 配载部门.
     */
    private String assemblyDeptId;

    /**
     * 航空公司代码.
     */
    private String airlinesCode;

    /**
     * 代理人编码.
     */
    private String agentCode;

    /**
     * 代理人名称.
     */
    private String agentName;

    /**
     * 是否启用.
     */
    private String active;
    /**
     * 是否外部代理
     */
    private String isOutAgent;
    
    public String getIsOutAgent() {
		return isOutAgent;
	}

	public void setIsOutAgent(String isOutAgent) {
		this.isOutAgent = isOutAgent;
	}

	/**
     * 获取 配载部门.
     *
     * @return  the assemblyDeptId
     */
    public String getAssemblyDeptId() {
        return assemblyDeptId;
    }
    
    /**
     * 设置 配载部门.
     *
     * @param assemblyDeptId the assemblyDeptId to set
     */
    public void setAssemblyDeptId(String assemblyDeptId) {
        this.assemblyDeptId = assemblyDeptId;
    }
    
    /**
     * 获取 航空公司代码.
     *
     * @return  the airlinesCode
     */
    public String getAirlinesCode() {
        return airlinesCode;
    }
    
    /**
     * 设置 航空公司代码.
     *
     * @param airlinesCode the airlinesCode to set
     */
    public void setAirlinesCode(String airlinesCode) {
        this.airlinesCode = airlinesCode;
    }
    
    /**
     * 获取 代理人编码.
     *
     * @return  the agentCode
     */
    public String getAgentCode() {
        return agentCode;
    }
    
    /**
     * 设置 代理人编码.
     *
     * @param agentCode the agentCode to set
     */
    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }
    
    /**
     * 获取 代理人名称.
     *
     * @return  the agentName
     */
    public String getAgentName() {
        return agentName;
    }
    
    /**
     * 设置 代理人名称.
     *
     * @param agentName the agentName to set
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
    
    /**
     * 获取 是否启用.
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 是否启用.
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
}
