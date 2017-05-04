/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/ProductItemDto.java
 * 
 * FILE NAME        	: ProductItemDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 */
public class ProductItemDto implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 4021980819915578938L;
    /**
     * 主键
     */
    private String id;
	/**
	 * 货物类型ID
	 */
    private String tSrvGoodstypeId;
	/**
	 * 货物类型CODe
	 */
    private String tSrvGoodstypeCode;
	/**
	 * 产品ID
	 */
    private String tSrvProductId;
	/**
	 * 产品CODE
	 */
    private String tSrvProductCode;
	/**
	 * 产品条目CODE
	 */
    private String code;
	/**
	 * 产品条目名称
	 */
    private String name;
	/**
	 * 是否激活
	 */
    private String active;
	/**
	 * 描述
	 */
    private String description;
	/**
	 * 版本号
	 */
    private Long versionNo;
	/**
	 * 
	 */
    private Long feeBottom;
	/**
	 * 适用开始时间
	 */
    private Date beginTime;
	/**
	 * 适用结束时间
	 */
    private Date endTime;
	/**
	 * 创建时间
	 */
    private Date createTime;
	/**
	 * 修改时间
	 */
    private Date modifyTime;
	/**
	 * 创建人
	 */
    private String createUserCode;
	/**
	 * 修改人
	 */
    private String modifyUserCode;
	/**
	 * 创建部门
	 */
    private String createOrgCode;
	/**
	 * 修改部门
	 */
    private String modifyOrgCode;
    /**
     * 业务时间
     */
    private Date billDate;

    
    /**
     * 获取 主键.
     *
     * @return the 主键
     */
    public String getId() {
        return id;
    }

    
    /**
     * 设置 主键.
     *
     * @param id the new 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    
    /**
     * 
     *
     * @return 
     */
    public String gettSrvGoodstypeId() {
        return tSrvGoodstypeId;
    }

    
    /**
     * 
     *
     * @param tSrvGoodstypeId 
     */
    public void settSrvGoodstypeId(String tSrvGoodstypeId) {
        this.tSrvGoodstypeId = tSrvGoodstypeId;
    }

    
    /**
     * 
     *
     * @return 
     */
    public String gettSrvGoodstypeCode() {
        return tSrvGoodstypeCode;
    }

    
    /**
     * 
     *
     * @param tSrvGoodstypeCode 
     */
    public void settSrvGoodstypeCode(String tSrvGoodstypeCode) {
        this.tSrvGoodstypeCode = tSrvGoodstypeCode;
    }

    
    /**
     * 
     *
     * @return 
     */
    public String gettSrvProductId() {
        return tSrvProductId;
    }

    
    /**
     * 
     *
     * @param tSrvProductId 
     */
    public void settSrvProductId(String tSrvProductId) {
        this.tSrvProductId = tSrvProductId;
    }

    
    /**
     * 
     *
     * @return 
     */
    public String gettSrvProductCode() {
        return tSrvProductCode;
    }

    
    /**
     * 
     *
     * @param tSrvProductCode 
     */
    public void settSrvProductCode(String tSrvProductCode) {
        this.tSrvProductCode = tSrvProductCode;
    }

    
    /**
     * 获取 产品条目CODE.
     *
     * @return the 产品条目CODE
     */
    public String getCode() {
        return code;
    }

    
    /**
     * 设置 产品条目CODE.
     *
     * @param code the new 产品条目CODE
     */
    public void setCode(String code) {
        this.code = code;
    }

    
    /**
     * 获取 产品条目名称.
     *
     * @return the 产品条目名称
     */
    public String getName() {
        return name;
    }

    
    /**
     * 设置 产品条目名称.
     *
     * @param name the new 产品条目名称
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * 获取 是否激活.
     *
     * @return the 是否激活
     */
    public String getActive() {
        return active;
    }

    
    /**
     * 设置 是否激活.
     *
     * @param active the new 是否激活
     */
    public void setActive(String active) {
        this.active = active;
    }

    
    /**
     * 获取 描述.
     *
     * @return the 描述
     */
    public String getDescription() {
        return description;
    }

    
    /**
     * 设置 描述.
     *
     * @param description the new 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
    /**
     * 获取 版本号.
     *
     * @return the 版本号
     */
    public Long getVersionNo() {
        return versionNo;
    }

    
    /**
     * 设置 版本号.
     *
     * @param versionNo the new 版本号
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Long getFeeBottom() {
        return feeBottom;
    }

    
    /**
     * 
     *
     * @param feeBottom 
     */
    public void setFeeBottom(Long feeBottom) {
        this.feeBottom = feeBottom;
    }

    
    /**
     * 获取 适用开始时间.
     *
     * @return the 适用开始时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    
    /**
     * 设置 适用开始时间.
     *
     * @param beginTime the new 适用开始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    
    /**
     * 获取 适用结束时间.
     *
     * @return the 适用结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    
    /**
     * 设置 适用结束时间.
     *
     * @param endTime the new 适用结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    
    /**
     * 获取 创建时间.
     *
     * @return the 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    
    /**
     * 设置 创建时间.
     *
     * @param createTime the new 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    
    /**
     * 获取 修改时间.
     *
     * @return the 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    
    /**
     * 设置 修改时间.
     *
     * @param modifyTime the new 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    
    /**
     * 获取 创建人.
     *
     * @return the 创建人
     */
    public String getCreateUserCode() {
        return createUserCode;
    }

    
    /**
     * 设置 创建人.
     *
     * @param createUserCode the new 创建人
     */
    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    
    /**
     * 获取 修改人.
     *
     * @return the 修改人
     */
    public String getModifyUserCode() {
        return modifyUserCode;
    }

    
    /**
     * 设置 修改人.
     *
     * @param modifyUserCode the new 修改人
     */
    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode;
    }

    
    /**
     * 获取 创建部门.
     *
     * @return the 创建部门
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }

    
    /**
     * 设置 创建部门.
     *
     * @param createOrgCode the new 创建部门
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    
    /**
     * 获取 修改部门.
     *
     * @return the 修改部门
     */
    public String getModifyOrgCode() {
        return modifyOrgCode;
    }

    
    /**
     * 设置 修改部门.
     *
     * @param modifyOrgCode the new 修改部门
     */
    public void setModifyOrgCode(String modifyOrgCode) {
        this.modifyOrgCode = modifyOrgCode;
    }

    
    /**
     * 获取 业务时间.
     *
     * @return the 业务时间
     */
    public Date getBillDate() {
        return billDate;
    }

    
    /**
     * 设置 业务时间.
     *
     * @param billDate the new 业务时间
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    
    /**
     * 
     *
     * @return 
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}