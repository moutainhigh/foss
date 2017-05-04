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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/WebSiteInfoDto.java
 * 
 * FILE NAME        	: WebSiteInfoDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
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
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;

/**
 * 开单权限信息DTO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-12-11 下午3:54:53 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-12-11 下午3:54:53
 * @since
 * @version
 */
public class WebSiteInfoDto implements Serializable{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 3972103926567498804L;
    
    /**
     * 营业部.
     */
    private SaleDepartmentEntity entity;
    
    /**
     * 操作类型：只有新增（insert）、修改(update)、删除（delete）三种状态.
     */
    private String operateType;
    
    /**
     * 部门省份.
     */
    private String deptProvince;
    
    /**
     * 部门城市.
     */
    private String deptCity;
    
    /**
     * 网点城市是否有A/B货.
     */
    private boolean isAB;
    
    /**
     * 部门区域.
     */
    private String deptArea;
    
    /**
     * 部门地址.
     */
    private String deptAddress;
    
    /**
     * 部门备注.
     */
    private String descript;
    
    /**
     * 部门联系方式.
     */
    private String contactWay;
    
    /**
     * 始发外场.
     */
    private String leaveOutDept;
    
    /**
     * 始发中转.
     */
    private String leaveMiddleChange;
    
    /**
     * 是否启用.
     */
    private boolean isUsed;
    
    /**
     * 所属区域.
     */
    private String superiorArea;
    
    /**
     * 是否开业.
     */
    private boolean isOpen;
    
    /**
     * 组织ID.
     */
    private String organisationId;
    
    /**
     * 是否到达.
     */
    private boolean isArrived;
    
    /**
     * 是否出发.
     */
    private boolean isLeave;
    
    /**
     * 是否送货上门.
     */
    private boolean isSendToHome;
    
    /**
     * 是否自提.
     */
    private boolean isGetBySelf;
    
    /**
     * 是否外发.
     */
    private boolean isOutSend;
    
    /**
     * 是否车队.
     */
    private boolean isCarTeam;
    
    /**
     * 车队是否有ＰＤＡ.
     */
    private boolean isHasPDA;
    
    /**
     * 获取 营业部.
     *
     * @return  the entity
     */
    public SaleDepartmentEntity getEntity() {
        return entity;
    }
    
    /**
     * 设置 营业部.
     *
     * @param entity the entity to set
     */
    public void setEntity(SaleDepartmentEntity entity) {
        this.entity = entity;
    }
    
    /**
     * 获取 操作类型：只有新增（insert）、修改(update)、删除（delete）三种状态.
     *
     * @return  the operateType
     */
    public String getOperateType() {
        return operateType;
    }
    
    /**
     * 设置 操作类型：只有新增（insert）、修改(update)、删除（delete）三种状态.
     *
     * @param operateType the operateType to set
     */
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
    
    /**
     * 获取 部门省份.
     *
     * @return  the deptProvince
     */
    public String getDeptProvince() {
        return deptProvince;
    }
    
    /**
     * 设置 部门省份.
     *
     * @param deptProvince the deptProvince to set
     */
    public void setDeptProvince(String deptProvince) {
        this.deptProvince = deptProvince;
    }
    
    /**
     * 获取 部门城市.
     *
     * @return  the deptCity
     */
    public String getDeptCity() {
        return deptCity;
    }
    
    /**
     * 设置 部门城市.
     *
     * @param deptCity the deptCity to set
     */
    public void setDeptCity(String deptCity) {
        this.deptCity = deptCity;
    }
    
    /**
     * @return  the isAB
     */
    public boolean isAB() {
        return isAB;
    }
    
    /**
     * @param isAB the isAB to set
     */
    public void setAB(boolean isAB) {
        this.isAB = isAB;
    }
    
    /**
     * 获取 部门区域.
     *
     * @return  the deptArea
     */
    public String getDeptArea() {
        return deptArea;
    }
    
    /**
     * 设置 部门区域.
     *
     * @param deptArea the deptArea to set
     */
    public void setDeptArea(String deptArea) {
        this.deptArea = deptArea;
    }
    
    /**
     * 获取 部门地址.
     *
     * @return  the deptAddress
     */
    public String getDeptAddress() {
        return deptAddress;
    }
    
    /**
     * 设置 部门地址.
     *
     * @param deptAddress the deptAddress to set
     */
    public void setDeptAddress(String deptAddress) {
        this.deptAddress = deptAddress;
    }
    
    /**
     * 获取 部门备注.
     *
     * @return  the descript
     */
    public String getDescript() {
        return descript;
    }
    
    /**
     * 设置 部门备注.
     *
     * @param descript the descript to set
     */
    public void setDescript(String descript) {
        this.descript = descript;
    }
    
    /**
     * 获取 部门联系方式.
     *
     * @return  the contactWay
     */
    public String getContactWay() {
        return contactWay;
    }
    
    /**
     * 设置 部门联系方式.
     *
     * @param contactWay the contactWay to set
     */
    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }
    
    /**
     * 获取 始发外场.
     *
     * @return  the leaveOutDept
     */
    public String getLeaveOutDept() {
        return leaveOutDept;
    }
    
    /**
     * 设置 始发外场.
     *
     * @param leaveOutDept the leaveOutDept to set
     */
    public void setLeaveOutDept(String leaveOutDept) {
        this.leaveOutDept = leaveOutDept;
    }
    
    /**
     * 获取 始发中转.
     *
     * @return  the leaveMiddleChange
     */
    public String getLeaveMiddleChange() {
        return leaveMiddleChange;
    }
    
    /**
     * 设置 始发中转.
     *
     * @param leaveMiddleChange the leaveMiddleChange to set
     */
    public void setLeaveMiddleChange(String leaveMiddleChange) {
        this.leaveMiddleChange = leaveMiddleChange;
    }
    
    /**
     * @return  the isUsed
     */
    public boolean isUsed() {
        return isUsed;
    }
    
    /**
     * @param isUsed the isUsed to set
     */
    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
    
    /**
     * 获取 所属区域.
     *
     * @return  the superiorArea
     */
    public String getSuperiorArea() {
        return superiorArea;
    }
    
    /**
     * 设置 所属区域.
     *
     * @param superiorArea the superiorArea to set
     */
    public void setSuperiorArea(String superiorArea) {
        this.superiorArea = superiorArea;
    }
    
    /**
     * @return  the isOpen
     */
    public boolean isOpen() {
        return isOpen;
    }
    
    /**
     * @param isOpen the isOpen to set
     */
    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
    
    /**
     * 获取 组织ID.
     *
     * @return  the organisationId
     */
    public String getOrganisationId() {
        return organisationId;
    }
    
    /**
     * 设置 组织ID.
     *
     * @param organisationId the organisationId to set
     */
    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }
    
    /**
     * @return  the isArrived
     */
    public boolean isArrived() {
        return isArrived;
    }
    
    /**
     * @param isArrived the isArrived to set
     */
    public void setArrived(boolean isArrived) {
        this.isArrived = isArrived;
    }
    
    /**
     * @return  the isLeave
     */
    public boolean isLeave() {
        return isLeave;
    }
    
    /**
     * @param isLeave the isLeave to set
     */
    public void setLeave(boolean isLeave) {
        this.isLeave = isLeave;
    }
    
    /**
     * @return  the isSendToHome
     */
    public boolean isSendToHome() {
        return isSendToHome;
    }
    
    /**
     * @param isSendToHome the isSendToHome to set
     */
    public void setSendToHome(boolean isSendToHome) {
        this.isSendToHome = isSendToHome;
    }
    
    /**
     * @return  the isGetBySelf
     */
    public boolean isGetBySelf() {
        return isGetBySelf;
    }
    
    /**
     * @param isGetBySelf the isGetBySelf to set
     */
    public void setGetBySelf(boolean isGetBySelf) {
        this.isGetBySelf = isGetBySelf;
    }
    
    /**
     * @return  the isOutSend
     */
    public boolean isOutSend() {
        return isOutSend;
    }
    
    /**
     * @param isOutSend the isOutSend to set
     */
    public void setOutSend(boolean isOutSend) {
        this.isOutSend = isOutSend;
    }
    
    /**
     * @return  the isCarTeam
     */
    public boolean isCarTeam() {
        return isCarTeam;
    }
    
    /**
     * @param isCarTeam the isCarTeam to set
     */
    public void setCarTeam(boolean isCarTeam) {
        this.isCarTeam = isCarTeam;
    }
    
    /**
     * @return  the isHasPDA
     */
    public boolean isHasPDA() {
        return isHasPDA;
    }
    
    /**
     * @param isHasPDA the isHasPDA to set
     */
    public void setHasPDA(boolean isHasPDA) {
        this.isHasPDA = isHasPDA;
    }
    
    
}
