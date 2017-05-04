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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/MacWhiteVo.java
 * 
 * FILE NAME        	: MacWhiteVo.java
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
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.MacWhiteEntity;


/**
 * MAC地址白名单信息VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-3-12 下午2:18:24 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-3-12 下午2:18:24
 * @since
 * @version
 * 新增属性  author:132599-foss-shenweihua,date:2013-4-25 上午10:26:24
 */
public class MacWhiteVo implements Serializable{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -1745002065426084081L;
    
    /**
     * MacWhite白名单信息维护实体类
     */
    private MacWhiteEntity entity;
    
    /**
     * MacWhite白名单信息集合
     */
    private List<MacWhiteEntity> entityList;
    
    /**
     * MacWhite白名单信息ID
     */
    private String id;
    
    /**
     * MacWhite白名单信息ID集合
     */
    private List<String> idList;
    
	/**
     * 判断MAC地址是否存在.
     */
    private boolean isExist;
    
    /**
     * MAC地址.
     */
    private String macAddress;

    
    /**
     * @return  the isExist
     */
    public boolean isExist() {
        return isExist;
    }

    
    /**
     * @param isExist the isExist to set
     */
    public void setExist(boolean isExist) {
        this.isExist = isExist;
    }

    
    /**
     * 获取 mAC地址.
     *
     * @return  the macAddress
     */
    public String getMacAddress() {
        return macAddress;
    }

    
    /**
     * 设置 mAC地址.
     *
     * @param macAddress the macAddress to set
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
    
    
    /**
     * 获取MacWhite白名单信息维护实体类
     * @return
     */
    public MacWhiteEntity getEntity() {
		return entity;
	}

    
    /**
     * 设置MacWhite白名单信息维护实体类
     * @param entity
     */
	public void setEntity(MacWhiteEntity entity) {
		this.entity = entity;
	}
	
	
	/**
     * 获取MacWhite白名单信息集合
     * @param entity
     */
	public List<MacWhiteEntity> getEntityList() {
		return entityList;
	}

	
	/**
     * 设置MacWhite白名单信息集合
     * @param entity
     */
	public void setEntityList(List<MacWhiteEntity> entityList) {
		this.entityList = entityList;
	}

	
	/**
     * 获取MacWhite白名单信息ID
     * @param entity
     */
	public String getId() {
		return id;
	}

	
	/**
     * 设置MacWhite白名单信息ID
     * @param entity
     */
	public void setId(String id) {
		this.id = id;
	}

	
	/**
     * 获取MacWhite白名单信息ID集合
     * @param entity
     */
	public List<String> getIdList() {
		return idList;
	}

	
	/**
     * 设置MacWhite白名单信息ID集合
     * @param entity
     */
	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

}
