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
 *  FILE PATH          :/AirSpaceEntity.java
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
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
/**
 * 航空公司舱位实体
 * @author dp-pengzhen
 * @date 2012-10-12 上午10:30:30
 */
public class AirSpaceEntity extends BaseEntity{
	
	private static final long serialVersionUID = -3954077554001376359L;
	/**配载部门编号*/
    private String assembleOrgCode; //配载部门编号
    /**配载部门名称*/
    private String assembleOrgName; //配载部门名称
    /**目的站编号*/
    private String arrvRegionCode; //目的站编号
    /**目的站名称*/
    private String arrvRegionName; //目的站名称
    /**出发日期*/
    private Date takeOffDate; //出发日期
    /**录入人编码*/
    private String createUserCode; //录入人编码
    /**录入人名称*/
    private String createUserName; //录入人名称
    /**
     * 录入时间
     */
    private Date createTime; //录入时间
    /**
     * 修改人编号
     */
    private String updateUserCode; 

    /**
     * 修改人名称
     */
    private String updateUserName;  
    /**
     * 修改时间
     */
    private Date updateTime; 
    /**
     * 获取 配载部门编号.
     *
     * @return the 配载部门编号
     */
    public String getAssembleOrgCode() {
        return assembleOrgCode;
    }

    /**
     * 设置 配载部门编号.
     *
     * @param assembleOrgCode the new 配载部门编号
     */
    public void setAssembleOrgCode(String assembleOrgCode) {
        this.assembleOrgCode = assembleOrgCode;
    }

    /**
     * 获取 配载部门名称.
     *
     * @return the 配载部门名称
     */
    public String getAssembleOrgName() {
        return assembleOrgName;
    }

    /**
     * 设置 配载部门名称.
     *
     * @param assembleOrgName the new 配载部门名称
     */
    public void setAssembleOrgName(String assembleOrgName) {
        this.assembleOrgName = assembleOrgName;
    }

    /**
     * 获取 目的站编号.
     *
     * @return the 目的站编号
     */
    public String getArrvRegionCode() {
        return arrvRegionCode;
    }

    /**
     * 设置 目的站编号.
     *
     * @param arrvRegionCode the new 目的站编号
     */
    public void setArrvRegionCode(String arrvRegionCode) {
        this.arrvRegionCode = arrvRegionCode;
    }

    /**
     * 获取 目的站名称.
     *
     * @return the 目的站名称
     */
    public String getArrvRegionName() {
        return arrvRegionName;
    }

    /**
     * 设置 目的站名称.
     *
     * @param arrvRegionName the new 目的站名称
     */
    public void setArrvRegionName(String arrvRegionName) {
        this.arrvRegionName = arrvRegionName;
    }
    
    /**
     * 获取 出发日期.
     *
     * @return the 出发日期
     */
    @DateFormat(formate="yyyy-MM-dd")
    public Date getTakeOffDate() {
        return takeOffDate;
    }
    
    /**
     * 设置 出发日期.
     *
     * @param takeOffDate the new 出发日期
     */
    @DateFormat(formate="yyyy-MM-dd")
    public void setTakeOffDate(Date takeOffDate) {
        this.takeOffDate = takeOffDate;
    }

    /**
     * 获取 录入人编码.
     *
     * @return the 录入人编码
     */
    public String getCreateUserCode() {
        return createUserCode;
    }

    /**
     * 设置 录入人编码.
     *
     * @param createUserCode the new 录入人编码
     */
    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    /**
     * 获取 录入人名称.
     *
     * @return the 录入人名称
     */
    public String getCreateUserName() {
        return createUserName;
    }

    /**
     * 设置 录入人名称.
     *
     * @param createUserName the new 录入人名称
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    /**
     * 获取 录入时间.
     *
     * @return the 录入时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置 录入时间.
     *
     * @param createTime the new 录入时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     *
     * @return 
     */
    public String getUpdateUserCode() {
        return updateUserCode;
    }

    /**
     * 
     *
     * @param updateUserCode 
     */
    public void setUpdateUserCode(String updateUserCode) {
        this.updateUserCode = updateUserCode;
    }

    /**
     * 
     *
     * @return 
     */
    public String getUpdateUserName() {
        return updateUserName;
    }

    /**
     * 
     *
     * @param updateUserName 
     */
    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    /**
     * @return 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime 
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}