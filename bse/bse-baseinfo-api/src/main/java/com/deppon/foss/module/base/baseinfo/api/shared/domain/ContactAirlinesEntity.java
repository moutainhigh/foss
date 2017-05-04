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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/ContactAirlinesEntity.java
 * 
 * FILE NAME        	: ContactAirlinesEntity.java
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
 * 用来存储交互“正单交货人”的数据库对应实体：SUC-37
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-11 下午5:23:23</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-11 下午5:23:23
 * @since
 * @version
 */
public class ContactAirlinesEntity extends BaseEntity {
    /**
     *  Serial Version UID
     */
    private static final long serialVersionUID = 3728912800460950753L;

    //交货人编码
    private String contactCode;

    //单位名称
    private String company;

    //交货人
    private String contact;

    //是否启用
    private String active;

    /**
     * @return  the contactCode
     */
    public String getContactCode() {
        return contactCode;
    }
    
    /**
     * @param contactCode the contactCode to set
     */
    public void setContactCode(String contactCode) {
        this.contactCode = contactCode;
    }

    /**
     * @return  the company
     */
    public String getCompany() {
        return company;
    }
    
    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }
    
    /**
     * @return  the contact
     */
    public String getContact() {
        return contact;
    }
    
    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
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
