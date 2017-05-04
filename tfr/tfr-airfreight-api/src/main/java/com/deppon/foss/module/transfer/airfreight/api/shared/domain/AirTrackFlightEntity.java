/**
 *  initial comments.
 */

/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirTrackFlightEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 空运跟踪实体
 * @author 038300-foss-pengzhen
 * @date 2012-12-17 下午7:02:21
 */
public class AirTrackFlightEntity extends BaseEntity{
	 
	private static final long serialVersionUID = -8861527393668790933L;
	/**
	 * 航空正单id
	 */
    private String airWaybillId;
    /**
     * 跟踪人名称
     */
    private String createUserCode;
    /**
     * 跟踪人名称
     */
    private String createUserName;
    /**
     * 跟踪信息
     */
    private String message;
    /**
     * 跟踪部门编码
     */
    private String createOrgCode;
    /**
     * 跟踪部门名称
     */
    private String createOrgName;
    /**
     * 跟踪时间
     */
    private Date createTime;

    /**
     * 获取 航空正单id.
     *
     * @return the 航空正单id
     */
    public String getAirWaybillId() {
		return airWaybillId;
	}

	/**
	 * 设置 航空正单id.
	 *
	 * @param airWaybillId the new 航空正单id
	 */
	public void setAirWaybillId(String airWaybillId) {
		this.airWaybillId = airWaybillId;
	}

	/**
	 * 获取 跟踪人名称.
	 *
	 * @return the 跟踪人名称
	 */
	public String getCreateUserCode() {
        return createUserCode;
    }

    /**
     * 设置 跟踪人名称.
     *
     * @param createUserCode the new 跟踪人名称
     */
    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    /**
     * 获取 跟踪人名称.
     *
     * @return the 跟踪人名称
     */
    public String getCreateUserName() {
        return createUserName;
    }

    /**
     * 设置 跟踪人名称.
     *
     * @param createUserName the new 跟踪人名称
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    /**
     * 获取 跟踪信息.
     *
     * @return the 跟踪信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置 跟踪信息.
     *
     * @param message the new 跟踪信息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取 跟踪部门编码.
     *
     * @return the 跟踪部门编码
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }

    /**
     * 设置 跟踪部门编码.
     *
     * @param createOrgCode the new 跟踪部门编码
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    /**
     * 获取 跟踪部门名称.
     *
     * @return the 跟踪部门名称
     */
    public String getCreateOrgName() {
        return createOrgName;
    }

    /**
     * 设置 跟踪部门名称.
     *
     * @param createOrgName the new 跟踪部门名称
     */
    public void setCreateOrgName(String createOrgName) {
        this.createOrgName = createOrgName;
    }

    /**
     * 获取 跟踪时间.
     *
     * @return the 跟踪时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置 跟踪时间.
     *
     * @param createTime the new 跟踪时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}