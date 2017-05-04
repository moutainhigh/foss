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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/domain/TemplateAppOrgEntity.java
 * 
 * FILE NAME        	: TemplateAppOrgEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 部门短信模板实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-19 上午9:10:40 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-19 上午9:10:40
 * @since
 * @version
 */
public class TemplateAppOrgEntity extends BaseEntity{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1808432377998793828L;

    /**
     * 所属部门Code.
     */
    private String orgId; 
    
    /**
     * 所属部门名称.
     */
    private String orgName; 

    /**
     * 部门短信模板内容.
     */
    private String smsContent; 

    /**
     * 是否启用.
     */
    private String active; 

    /**
     * 短信模板虚拟Code.
     */
    private String smsVirtualCode; 

    
    /**
     * 获取 所属部门Code.
     *
     * @return  the orgId
     */
    public String getOrgId() {
        return orgId;
    }

    
    /**
     * 设置 所属部门Code.
     *
     * @param orgId the orgId to set
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    
    /**
     * 获取 所属部门名称.
     *
     * @return  the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    
    /**
     * 设置 所属部门名称.
     *
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    
    /**
     * 获取 部门短信模板内容.
     *
     * @return  the smsContent
     */
    public String getSmsContent() {
        return smsContent;
    }

    
    /**
     * 设置 部门短信模板内容.
     *
     * @param smsContent the smsContent to set
     */
    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
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

    
    /**
     * 获取 短信模板虚拟Code.
     *
     * @return  the smsVirtualCode
     */
    public String getSmsVirtualCode() {
        return smsVirtualCode;
    }

    
    /**
     * 设置 短信模板虚拟Code.
     *
     * @param smsVirtualCode the smsVirtualCode to set
     */
    public void setSmsVirtualCode(String smsVirtualCode) {
        this.smsVirtualCode = smsVirtualCode;
    }
    
    

}
