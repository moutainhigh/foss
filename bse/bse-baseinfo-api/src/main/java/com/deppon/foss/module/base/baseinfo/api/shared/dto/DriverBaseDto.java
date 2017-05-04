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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/DriverBaseDto.java
 * 
 * FILE NAME        	: DriverBaseDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.ComnConst;

/**
 * 用来交互“司机（公司、外请车）”的数据实体信息的DTO的基类
 * <p>基本属性包括：司机信息、直属部门编码</p>
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-19 下午4:05:35</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-19 下午4:05:35
 * @since
 * @version
 */
public class DriverBaseDto implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 4149057244059655176L;
    
    /**
     * 司机编号
     */
    private String driverCode;
    
    /**
     * 司机姓名
     */
    private String driverName;
    
    /**
     * 司机电话
     */
    private String driverPhone;
    
    /**
     * 所属直属部门编码
     */
    private String driverOrganizationCode;

    /**
     * 司机身份证号
     */
    private String driverIdCard;
    
    /**
     * 车辆归属类型（公司、外请）
     */
    private String driverOwnershipType;
    
    /**
     * 司机编号
     * 
     * @return  the driverCode
     */
    public String getDriverCode() {
        return driverCode;
    }

    /**
     * @param driverCode the driverCode to set
     */
    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }

    /**
     * 司机姓名
     * 
     * @return  the driverName
     */
    public String getDriverName() {
        return driverName;
    }
    
    /**
     * @param driverName the driverName to set
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    
    /**
     * 司机电话
     * 
     * @return  the driverPhone
     */
    public String getDriverPhone() {
        return driverPhone;
    }

    /**
     * @param driverPhone the driverPhone to set
     */
    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    /**
     * 所属直属部门编码
     * 
     * @return  the driverOrganizationCode
     */
    public String getDriverOrganizationCode() {
        return driverOrganizationCode;
    }

    /**
     * @param driverOrganizationCode the driverOrganizationCode to set
     */
    public void setDriverOrganizationCode(String driverOrganizationCode) {
        this.driverOrganizationCode = driverOrganizationCode;
    }

    
    /**
     * 司机身份证号
     * 
     * @return  the driverIdCard
     */
    public String getDriverIdCard() {
        return driverIdCard;
    }

    /**
     * @param driverIdCard the driverIdCard to set
     */
    public void setDriverIdCard(String driverIdCard) {
        this.driverIdCard = driverIdCard;
    }

    /**
     * 车辆归属类型（公司、外请）
     * 
     * @return  the driverOwnershipType
     */
    public String getDriverOwnershipType() {
	if (StringUtils.isBlank(getDriverCode())) {
	    driverOwnershipType = ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED;
	}else{
	    driverOwnershipType = ComnConst.ASSETS_OWNERSHIP_TYPE_COMPANY;
	}
        return driverOwnershipType;
    }
}
