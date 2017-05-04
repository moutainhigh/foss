/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillAcHisOfflineEntity.java
 * 
 * FILE NAME        	: WaybillAcHisOfflineEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * 定义“离线运单操作历史”实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:李凤腾,date:2012-10-11 上午10:18:16, </p>
 * @author 李凤腾
 * @date 2012-10-11 上午10:18:16
 * @since
 * @version
 */
public class WaybillAcHisOfflineEntity extends BaseEntity {

    /**
     * 序列化对象标识
     */
    private static final long serialVersionUID = -837722719566142407L;
    
    //对象 ID
    private String id;

    //操作人
    private String executeuser;

    //操作内容
    private String action;

    //操作时间
    private Date executetime;

    //运单ID
    private String waybillOfflineId;

    //运单号
    private String waybillNo;

    
    /**
     * @return  the executeuser
     */
    public String getExecuteuser() {
        return executeuser;
    }

    
    /**
     * @param executeuser the executeuser to set
     */
    public void setExecuteuser(String executeuser) {
        this.executeuser = executeuser;
    }

    
    /**
     * @return  the action
     */
    public String getAction() {
        return action;
    }

    
    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    
    /**
     * @return  the executetime
     */
    public Date getExecutetime() {
        return executetime;
    }

    
    /**
     * @param executetime the executetime to set
     */
    public void setExecutetime(Date executetime) {
        this.executetime = executetime;
    }

    
    /**
     * @return  the waybillOfflineId
     */
    public String getWaybillOfflineId() {
        return waybillOfflineId;
    }

    
    /**
     * @param waybillOfflineId the waybillOfflineId to set
     */
    public void setWaybillOfflineId(String waybillOfflineId) {
        this.waybillOfflineId = waybillOfflineId;
    }

    
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
     * @return  the id
     */
    public String getId() {
        return id;
    }
    
    

}