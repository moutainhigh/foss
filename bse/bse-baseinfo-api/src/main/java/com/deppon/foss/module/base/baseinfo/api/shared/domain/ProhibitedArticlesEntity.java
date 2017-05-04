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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/ProhibitedArticlesEntity.java
 * 
 * FILE NAME        	: ProhibitedArticlesEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 禁运物品.
 *
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class ProhibitedArticlesEntity  extends BaseEntity {
    
    /**
     * 序列ID
     */
    private static final long serialVersionUID = -3967231351862235765L;

    /**
     * 虚拟编码.
     */	
    private String virtualCode;

    /**
     * 违禁品名称.
     */	
    private String goodsName;

    /**
     * 违禁品类型.
     */	
    private String goodsType;

    /**
     * 违禁品级别.
     */	
    private String goodsLevel;

    /**
     * 违禁品类别.
     */	
    private String goodsSort;

    /**
     * 备注.
     */	
    private String notes;

    /**
     * 数据版本号.
     */	
    private Long versionNo;
    
    /**
     * 是否启用.
     */	
    private String active;
    
    /**
     * 关键字.
     */
    private String keyWords;
    
    /**
     * 获取 关键字.
     *
     * @return  the keyWords
     */
    public String getKeyWords() {
        return keyWords;
    }
    
    /**
     * 设置 关键字.
     *
     * @param keyWords the keyWords to set
     */
    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    /**
     * 获取 数据版本号.
     *
     * @return the 数据版本号
     */
    public Long getVersionNo() {
        return versionNo;
    }
    
    /**
     * 设置 数据版本号.
     *
     * @param versionNo the new 数据版本号
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }
    
    /**
     * 获取 虚拟编码.
     *
     * @return the 虚拟编码
     */
    public String getVirtualCode() {
		return virtualCode;
    }
    
    /**
     * 设置 虚拟编码.
     *
     * @param virtualCode the new 虚拟编码
     */
    public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
    }

    /**
     * 获取 违禁品名称.
     *
     * @return the 违禁品名称
     */
    public String getGoodsName() {
		return goodsName;
    }
    
    /**
     * 设置 违禁品名称.
     *
     * @param goodsName the new 违禁品名称
     */
    public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
    }

    /**
     * 获取 违禁品类型.
     *
     * @return the 违禁品类型
     */
    public String getGoodsType() {
		return goodsType;
    }
    
    /**
     * 设置 违禁品类型.
     *
     * @param goodsType the new 违禁品类型
     */
    public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
    }

    /**
     * 获取 违禁品级别.
     *
     * @return the 违禁品级别
     */
    public String getGoodsLevel() {
		return goodsLevel;
    }
    
    /**
     * 设置 违禁品级别.
     *
     * @param goodsLevel the new 违禁品级别
     */
    public void setGoodsLevel(String goodsLevel) {
		this.goodsLevel = goodsLevel;
    }

    /**
     * 获取 违禁品类别.
     *
     * @return the 违禁品类别
     */
    public String getGoodsSort() {
		return goodsSort;
    }
    
    /**
     * 设置 违禁品类别.
     *
     * @param goodsSort the new 违禁品类别
     */
    public void setGoodsSort(String goodsSort) {
		this.goodsSort = goodsSort;
    }

    /**
     * 获取 备注.
     *
     * @return the 备注
     */
    public String getNotes() {
		return notes;
    }
    
    /**
     * 设置 备注.
     *
     * @param notes the new 备注
     */
    public void setNotes(String notes) {
		this.notes = notes;
    }

    /**
     * 获取 是否启用.
     *
     * @return the 是否启用
     */
    public String getActive() {
		return active;
    }
    
    /**
     * 设置 是否启用.
     *
     * @param active the new 是否启用
     */
    public void setActive(String active) {
		this.active = active;
    }
}