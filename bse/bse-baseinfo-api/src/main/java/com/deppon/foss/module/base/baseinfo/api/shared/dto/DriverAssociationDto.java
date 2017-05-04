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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/DriverAssociationDto.java
 * 
 * FILE NAME        	: DriverAssociationDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

/**
 * 用来交互“司机（公司、外请）”的数据实体相关联信息的DTO
 * <p>基本属性包括：直属部门名称、直属部门负责人信息、所属车队信息、所属车队负责人信息</p>
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-5 下午2:43:08</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-5 下午2:43:08
 * @since
 * @version
 */
public class DriverAssociationDto extends DriverBaseDto implements Serializable{

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -7469592638195848344L;

    /**
     * 所属直属部门名称
     */
    private String driverOrganizationName;
    
    /**
     * 所属部门负责人工号
     */
    private String driverOrganizationLeaderCode;
    
    /**
     * 所属部门负责人姓名
     */
    private String driverOrganizationLeaderName;
    
    /**
     * 所属部门负责人电话
     */
    private String driverOrganizationLeaderPhone;
    
    /**
     * 所属直属部门编码
     * 
     * @return  the driverOrganizationName
     */
    public String getDriverOrganizationName() {
        return driverOrganizationName;
    }
    
    /**
     * @param driverOrganizationName the driverOrganizationName to set
     */
    public void setDriverOrganizationName(String driverOrganizationName) {
        this.driverOrganizationName = driverOrganizationName;
    }
    
    /**
     * 所属部门负责人工号
     * 
     * @return  the driverOrganizationLeaderCode
     */
    public String getDriverOrganizationLeaderCode() {
        return driverOrganizationLeaderCode;
    }
    
    /**
     * @param driverOrganizationLeaderCode the driverOrganizationLeaderCode to set
     */
    public void setDriverOrganizationLeaderCode(String driverOrganizationLeaderCode) {
        this.driverOrganizationLeaderCode = driverOrganizationLeaderCode;
    }

    /**
     * 所属部门负责人姓名
     * 
     * @return  the driverOrganizationLeaderName
     */
    public String getDriverOrganizationLeaderName() {
        return driverOrganizationLeaderName;
    }
    
    /**
     * @param driverOrganizationLeaderName the driverOrganizationLeaderName to set
     */
    public void setDriverOrganizationLeaderName(String driverOrganizationLeaderName) {
        this.driverOrganizationLeaderName = driverOrganizationLeaderName;
    }

    /**
     * 所属部门负责人电话
     * 
     * @return  the driverOrganizationLeaderPhone
     */
    public String getDriverOrganizationLeaderPhone() {
        return driverOrganizationLeaderPhone;
    }
    
    /**
     * @param driverOrganizationLeaderPhone the driverOrganizationLeaderPhone to set
     */
    public void setDriverOrganizationLeaderPhone(
    	String driverOrganizationLeaderPhone) {
        this.driverOrganizationLeaderPhone = driverOrganizationLeaderPhone;
    }
}
