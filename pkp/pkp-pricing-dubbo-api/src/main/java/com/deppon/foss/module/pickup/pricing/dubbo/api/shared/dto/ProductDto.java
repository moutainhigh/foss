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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/ProductDto.java
 * 
 * FILE NAME        	: ProductDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 产品信息DTO
 * @author DP-Foss-YueHongJie
 */
public class ProductDto implements Serializable{
    /**
     *
     */

    private static final long serialVersionUID = -4815209172264621208L;

    /**
     * 产品主键
     */
    private String id;
    /**
     * 产品CODE
     */
    private String code;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品激活状态
     */
    private String active;
    /**
     * 产品备注
     */
    private String description;
    /**
     * 产品版本号
     */
    private Long versionNo;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 产品性质(空运/汽运)
     */
    private String transportType;
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
     * 创建机构编码	
     */
    private String createOrgCode;
    /**
     * 修改机构编码
     */
    private String modifyOrgCode;
    /**
     * 产品层级
     */
    private Long levels;
    /**
     * 父级产品编号
     */
    private String parentCode;
    /**
     * 父级产品ID
     */
    private String refId;
    /**
     * 简称
     */
    private String shortName;
    /**
     * 优先级  	快-慢
     */
    private String priority;
    /**
     * 业务时间
     */
    private Date billDate;
    /**
     * 目的地适用网点类型
     */
    private String destNetType;
    /**
     * 显示排序用
     */
    private int seq;
    
    
    /**
     * 获取 显示排序用.
     *
     * @return the 显示排序用
     */
    public int getSeq() {
        return seq;
    }
    
    /**
     * 设置 显示排序用.
     *
     * @param seq the new 显示排序用
     */
    public void setSeq(int seq) {
        this.seq = seq;
    }
    
    
    /**
     * 获取 目的地适用网点类型.
     *
     * @return the 目的地适用网点类型
     */
    public String getDestNetType() {
        return destNetType;
    }
    
    /**
     * 设置 目的地适用网点类型.
     *
     * @param destNetType the new 目的地适用网点类型
     */
    public void setDestNetType(String destNetType) {
        this.destNetType = destNetType;
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
     * 获取 产品主键.
     *
     * @return the 产品主键
     */
    public String getId() {
        return id;
    }
    
    /**
     * 设置 产品主键.
     *
     * @param id the new 产品主键
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * 获取 产品CODE.
     *
     * @return the 产品CODE
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 设置 产品CODE.
     *
     * @param code the new 产品CODE
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * 获取 产品名称.
     *
     * @return the 产品名称
     */
    public String getName() {
        return name;
    }
    
    /**
     * 设置 产品名称.
     *
     * @param name the new 产品名称
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 获取 产品激活状态.
     *
     * @return the 产品激活状态
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 产品激活状态.
     *
     * @param active the new 产品激活状态
     */
    public void setActive(String active) {
        this.active = active;
    }
    
    /**
     * 获取 产品备注.
     *
     * @return the 产品备注
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 设置 产品备注.
     *
     * @param description the new 产品备注
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * 获取 产品版本号.
     *
     * @return the 产品版本号
     */
    public Long getVersionNo() {
        return versionNo;
    }
    
    /**
     * 设置 产品版本号.
     *
     * @param versionNo the new 产品版本号
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }
    
    /**
     * 获取 开始时间.
     *
     * @return the 开始时间
     */
    public Date getBeginTime() {
        return beginTime;
    }
    
    /**
     * 设置 开始时间.
     *
     * @param beginTime the new 开始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    
    /**
     * 获取 结束时间.
     *
     * @return the 结束时间
     */
    public Date getEndTime() {
        return endTime;
    }
    
    /**
     * 设置 结束时间.
     *
     * @param endTime the new 结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    /**
     * 获取 产品性质(空运/汽运).
     *
     * @return the 产品性质(空运/汽运)
     */
    public String getTransportType() {
        return transportType;
    }
    
    /**
     * 设置 产品性质(空运/汽运).
     *
     * @param transportType the new 产品性质(空运/汽运)
     */
    public void setTransportType(String transportType) {
        this.transportType = transportType;
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
     * 获取 创建机构编码.
     *
     * @return the 创建机构编码
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }
    
    /**
     * 设置 创建机构编码.
     *
     * @param createOrgCode the new 创建机构编码
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }
    
    /**
     * 获取 修改机构编码.
     *
     * @return the 修改机构编码
     */
    public String getModifyOrgCode() {
        return modifyOrgCode;
    }
    
    /**
     * 设置 修改机构编码.
     *
     * @param modifyOrgCode the new 修改机构编码
     */
    public void setModifyOrgCode(String modifyOrgCode) {
        this.modifyOrgCode = modifyOrgCode;
    }
    
    /**
     * 获取 产品层级.
     *
     * @return the 产品层级
     */
    public Long getLevels() {
        return levels;
    }
    
    /**
     * 设置 产品层级.
     *
     * @param levels the new 产品层级
     */
    public void setLevels(Long levels) {
        this.levels = levels;
    }
    
    /**
     * 获取 父级产品编号.
     *
     * @return the 父级产品编号
     */
    public String getParentCode() {
        return parentCode;
    }
    
    /**
     * 设置 父级产品编号.
     *
     * @param parentCode the new 父级产品编号
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
    
    /**
     * 获取 父级产品ID.
     *
     * @return the 父级产品ID
     */
    public String getRefId() {
        return refId;
    }
    
    /**
     * 设置 父级产品ID.
     *
     * @param refId the new 父级产品ID
     */
    public void setRefId(String refId) {
        this.refId = refId;
    }
    
    /**
     * 获取 简称.
     *
     * @return the 简称
     */
    public String getShortName() {
        return shortName;
    }
    
    /**
     * 设置 简称.
     *
     * @param shortName the new 简称
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    /**
     * 获取 优先级  	快-慢.
     *
     * @return the 优先级  	快-慢
     */
    public String getPriority() {
        return priority;
    }
    
    /**
     * 设置 优先级  	快-慢.
     *
     * @param priority the new 优先级  	快-慢
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }
    

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
    /**
     * 获取 业务时间.
     *
     * @return the 业务时间
     */
    public Date getBillDate() {
        return billDate;
    }
}