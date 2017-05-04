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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/ContactWithAirlinesVo.java
 * 
 * FILE NAME        	: ContactWithAirlinesVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAirlinesEntity;
/**
 * 用来响应“正单交货人”的Action类的封装类VO：SUC-37
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-19 下午3:08:22</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-19 下午3:08:22
 * @since
 * @version
 */
public class ContactWithAirlinesVo implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -7560281363680344587L;

    /**
     * "正单交货人"对象
     */
    private ContactAirlinesEntity contactWithAirlines;
    
    /**
     * "正单交货人"对象列表集合
     */
    private List<ContactAirlinesEntity> contactWithAirlinesList;

    /**
     * 批量操作ID集合
     */
    private List<String> batchIds;

    
    /**
     * @return  the contactWithAirlines
     */
    public ContactAirlinesEntity getContactWithAirlines() {
        return contactWithAirlines;
    }

    
    /**
     * @param contactWithAirlines the contactWithAirlines to set
     */
    public void setContactWithAirlines(ContactAirlinesEntity contactWithAirlines) {
        this.contactWithAirlines = contactWithAirlines;
    }

    
    /**
     * @return  the contactWithAirlinesList
     */
    public List<ContactAirlinesEntity> getContactWithAirlinesList() {
        return contactWithAirlinesList;
    }

    
    /**
     * @param contactWithAirlinesList the contactWithAirlinesList to set
     */
    public void setContactWithAirlinesList(
    	List<ContactAirlinesEntity> contactWithAirlinesList) {
        this.contactWithAirlinesList = contactWithAirlinesList;
    }

    
    /**
     * @return  the batchIds
     */
    public List<String> getBatchIds() {
        return batchIds;
    }

    
    /**
     * @param batchIds the batchIds to set
     */
    public void setBatchIds(List<String> batchIds) {
        this.batchIds = batchIds;
    }
}
