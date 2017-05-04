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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/AirlinesAccountEntity.java
 * 
 * FILE NAME        	: AirlinesAccountEntity.java
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
 * 用来存储交互“航空公司账户”的数据库对应实体：SUC-43
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-11 下午5:30:23</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-11 下午5:30:23
 * @since
 * @version
 */
public class AirlinesAccountEntity extends BaseEntity {
    /**
     *  Serial Version UID
     */
    private static final long serialVersionUID = 5108782536731475175L;

    //航空公司
    private String airlines;

    //使用部门
    private String orgId;

    //接入账号
    private String accessAccount;

    //接入密码
    private String accessPassword;

    //确认接入密码
    private String confirmPassword;

    //航空公司账号
    private String airlinesAccount;

    //航空公司校验码
    private String checksum;

    //确认航空公司校验码
    private String confirmChecksum;

    //是否启用
    private String active;

    /**
     * @return  the airlines
     */
    public String getAirlines() {
        return airlines;
    }
    
    /**
     * @param airlines the airlines to set
     */
    public void setAirlines(String airlines) {
        this.airlines = airlines;
    }
    
    /**
     * @return  the orgId
     */
    public String getOrgId() {
        return orgId;
    }
    
    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    
    /**
     * @return  the accessAccount
     */
    public String getAccessAccount() {
        return accessAccount;
    }
    
    /**
     * @param accessAccount the accessAccount to set
     */
    public void setAccessAccount(String accessAccount) {
        this.accessAccount = accessAccount;
    }
    
    /**
     * @return  the accessPassword
     */
    public String getAccessPassword() {
        return accessPassword;
    }
    
    /**
     * @param accessPassword the accessPassword to set
     */
    public void setAccessPassword(String accessPassword) {
        this.accessPassword = accessPassword;
    }
    
    /**
     * @return  the confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }
    
    /**
     * @param confirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    /**
     * @return  the airlinesAccount
     */
    public String getAirlinesAccount() {
        return airlinesAccount;
    }
    
    /**
     * @param airlinesAccount the airlinesAccount to set
     */
    public void setAirlinesAccount(String airlinesAccount) {
        this.airlinesAccount = airlinesAccount;
    }
    
    /**
     * @return  the checksum
     */
    public String getChecksum() {
        return checksum;
    }
    
    /**
     * @param checksum the checksum to set
     */
    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
    
    /**
     * @return  the confirmChecksum
     */
    public String getConfirmChecksum() {
        return confirmChecksum;
    }
    
    /**
     * @param confirmChecksum the confirmChecksum to set
     */
    public void setConfirmChecksum(String confirmChecksum) {
        this.confirmChecksum = confirmChecksum;
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
