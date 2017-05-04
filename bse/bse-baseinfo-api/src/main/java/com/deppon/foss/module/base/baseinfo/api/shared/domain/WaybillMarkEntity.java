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
 * PROJECT NAME	: bse-querying
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/shared/domain/WaybillMarkEntity.java
 * 
 * FILE NAME        	: WaybillMarkEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class WaybillMarkEntity extends BaseEntity {

    private static final long serialVersionUID = 2741859797235072349L;

    private String waybillNo;

    private String markStatus;

    private Date createTime;

    private Date modifyTime;

    private String createUserCode;

    private String createUserName;

    private String modifyUserCode;

    private String modifyUserName;

    
    /**
     * @return  the waybillNo
     */
    public String getWaybillNo() {
        return waybillNo;
    }

    
    /**
     * @param waybillNo the waybillNo to set
     */
    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    
    /**
     * @return  the markStatus
     */
    public String getMarkStatus() {
        return markStatus;
    }

    
    /**
     * @param markStatus the markStatus to set
     */
    public void setMarkStatus(String markStatus) {
        this.markStatus = markStatus;
    }

    
    /**
     * @return  the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    
    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    
    /**
     * @return  the modifyTime
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    
    /**
     * @param modifyTime the modifyTime to set
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    
    /**
     * @return  the createUserCode
     */
    public String getCreateUserCode() {
        return createUserCode;
    }

    
    /**
     * @param createUserCode the createUserCode to set
     */
    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    
    /**
     * @return  the createUserName
     */
    public String getCreateUserName() {
        return createUserName;
    }

    
    /**
     * @param createUserName the createUserName to set
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    
    /**
     * @return  the modifyUserCode
     */
    public String getModifyUserCode() {
        return modifyUserCode;
    }

    
    /**
     * @param modifyUserCode the modifyUserCode to set
     */
    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode;
    }

    
    /**
     * @return  the modifyUserName
     */
    public String getModifyUserName() {
        return modifyUserName;
    }

    
    /**
     * @param modifyUserName the modifyUserName to set
     */
    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }
}
