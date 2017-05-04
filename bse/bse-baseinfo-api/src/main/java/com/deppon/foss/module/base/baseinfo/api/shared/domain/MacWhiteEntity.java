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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/MacWhiteEntity.java
 * 
 * FILE NAME        	: MacWhiteEntity.java
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
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * MAC地址白名单信息
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-3-12 上午9:27:12
 * @since
 * @version
 */
public class MacWhiteEntity extends BaseEntity{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -5029628798850183910L;
    
    /**
     * MAC地址.
     */
    private String macAddress;
    
    /**
     * 是否有效 Y：有效 N:无效.
     */
    private String active;
    
    /**
     * 工号
     */
    private String userCode;
    
    /**
     * 姓名
     */
    private String userName;
    
    /**
     * 部门名
     */
    private String deptName;
    
    /**
     * 大区名
     */
    private String bigZone;
    
    /**
     * 获取工号
     * @return
     */
    public String getUserCode() {
		return userCode;
	}
    
    /**
     * 设置工号
     * @return
     */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	/**
     * 获取姓名
     * @return
     */
	public String getUserName() {
		return userName;
	}
	
	/**
     * 设置姓名
     * @return
     */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
     * 获取部门名
     * @return
     */
	public String getDeptName() {
		return deptName;
	}
	
	/**
     * 设置部门名
     * @return
     */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	/**
     * 获取大区名
     * @return
     */
	public String getBigZone() {
		return bigZone;
	}
	
	/**
     * 设置大区名
     * @return
     */
	public void setBigZone(String bigZone) {
		this.bigZone = bigZone;
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
     * 获取 是否有效 Y：有效 N:无效.
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 是否有效 Y：有效 N:无效.
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
}