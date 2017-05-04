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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/AgencyBranchOrCompanyDto.java
 * 
 * FILE NAME        	: AgencyBranchOrCompanyDto.java
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
 * 代理网点及其代理公司相关信息的DTO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-24 上午9:06:19 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-24 上午9:06:19
 * @since
 * @version
 */
public class AgencyBranchOrCompanyDto implements Serializable{
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -5386794902361661352L;

    /**
     * 代理公司联系电话.
     */
    private String partnerContactPhone;
    
    /**
     * 代理公司名称.
     */
    private String agentCompanyName;
    
    /**
     * 代理公司编码.
     */
    private String agentCompanyCode;
    
    /**
     * 代理网点编码.
     */
    private String agentDeptCode;
    
    /**
     * 代理网点名称.
     */
    private String agentDeptName;
    
    /**
     * 代理网点联系电话.
     */
    private String branchContactPhone;
    
    /**
     * 代理网点地址.
     */
    private String branchAddress;
    
    /**
     * 获取 代理公司联系电话.
     *
     * @return  the partnerContactPhone
     */
    public String getPartnerContactPhone() {
        return partnerContactPhone;
    }

    
    /**
     * 设置 代理公司联系电话.
     *
     * @param partnerContactPhone the partnerContactPhone to set
     */
    public void setPartnerContactPhone(String partnerContactPhone) {
        this.partnerContactPhone = partnerContactPhone;
    }

    
    /**
     * 获取 代理公司名称.
     *
     * @return  the agentCompanyName
     */
    public String getAgentCompanyName() {
        return agentCompanyName;
    }

    
    /**
     * 设置 代理公司名称.
     *
     * @param agentCompanyName the agentCompanyName to set
     */
    public void setAgentCompanyName(String agentCompanyName) {
        this.agentCompanyName = agentCompanyName;
    }

    
    /**
     * 获取 代理公司编码.
     *
     * @return  the agentCompanyCode
     */
    public String getAgentCompanyCode() {
        return agentCompanyCode;
    }

    
    /**
     * 设置 代理公司编码.
     *
     * @param agentCompanyCode the agentCompanyCode to set
     */
    public void setAgentCompanyCode(String agentCompanyCode) {
        this.agentCompanyCode = agentCompanyCode;
    }

    
    /**
     * 获取 代理网点编码.
     *
     * @return  the agentDeptCode
     */
    public String getAgentDeptCode() {
        return agentDeptCode;
    }

    
    /**
     * 设置 代理网点编码.
     *
     * @param agentDeptCode the agentDeptCode to set
     */
    public void setAgentDeptCode(String agentDeptCode) {
        this.agentDeptCode = agentDeptCode;
    }

    
    /**
     * 获取 代理网点名称.
     *
     * @return  the agentDeptName
     */
    public String getAgentDeptName() {
        return agentDeptName;
    }

    
    /**
     * 设置 代理网点名称.
     *
     * @param agentDeptName the agentDeptName to set
     */
    public void setAgentDeptName(String agentDeptName) {
        this.agentDeptName = agentDeptName;
    }

    
    /**
     * 获取 代理网点联系电话.
     *
     * @return  the branchContactPhone
     */
    public String getBranchContactPhone() {
        return branchContactPhone;
    }

    
    /**
     * 设置 代理网点联系电话.
     *
     * @param branchContactPhone the branchContactPhone to set
     */
    public void setBranchContactPhone(String branchContactPhone) {
        this.branchContactPhone = branchContactPhone;
    }

    
    /**
     * 获取 代理网点地址.
     *
     * @return  the branchAddress
     */
    public String getBranchAddress() {
        return branchAddress;
    }

    
    /**
     * 设置 代理网点地址.
     *
     * @param branchAddress the branchAddress to set
     */
    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    

}
