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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/VehicleUnavilableEntity.java
 * 
 * FILE NAME        	: VehicleUnavilableEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
import java.util.Date;

public class VehicleUnavilableEntity extends BaseEntity {
	
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 2635999907902972638L;

    /**
     * LMS同步唯一标识符
     */
    private String lmsCode;

    /**
     * 车牌号
     */
    private String vehicleNo;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 原因代码
     */
    private String unavilableCode;

    /**
     * 是否启用
     */
    private String active;

    /**
     * @return  the lmsCode
     */
    public String getLmsCode() {
        return lmsCode;
    }

    /**
     * @param lmsCode the lmsCode to set
     */
    public void setLmsCode(String lmsCode) {
        this.lmsCode = lmsCode;
    }
    
    /**
     * @return  the vehicleNo
     */
    public String getVehicleNo() {
        return vehicleNo;
    }

    /**
     * @param vehicleNo the vehicleNo to set
     */
    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }
    
    /**
     * @return  the beginTime
     */
    public Date getBeginTime() {
        return beginTime;
    }
    
    /**
     * @param beginTime the beginTime to set
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * @return  the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return  the unavilableCode
     */
    public String getUnavilableCode() {
        return unavilableCode;
    }
    
    /**
     * @param unavilableCode the unavilableCode to set
     */
    public void setUnavilableCode(String unavilableCode) {
        this.unavilableCode = unavilableCode;
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