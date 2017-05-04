/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/GuiMonitorEntity.java
 * 
 * FILE NAME        	: GuiMonitorEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.domain;


import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 记录用户登陆GUI时的电脑环境信息
 * @author 026123-foss-lifengteng
 * @date 2013-3-13 下午5:46:59
 */
public class GuiMonitorEntity extends BaseEntity{
	/**
	 * 生成序列化标识，以便进行网络传输
	 */
	private static final long serialVersionUID = 3973319250257582540L;

	/**
	 * 主键
	 */
    private String id;

    /**
     * 用户编码（即工号）
     */
    private String code;

    /**
     * 用户名称
     */
    private String name;
    
    /**
     * MAC地址
     */
    private String macAddress;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 主机名
     */
    private String hostName;

    /**
     * 登陆时间
     */
    private Date loginTime;

    /**
     * 获取 主键
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /** 
     *  设置 主键
     *  
     *  @param id the new 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取 用户编码（即工号）.
     *
     * @return the 用户编码（即工号）
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置 用户编码（即工号）.
     *
     * @param code the new 用户编码（即工号）
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取 用户名称.
     *
     * @return the 用户名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 用户名称.
     *
     * @param name the new 用户名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 mAC地址.
     *
     * @return the mAC地址
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * 设置 mAC地址.
     *
     * @param macAddress the new mAC地址
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * 获取 iP地址.
     *
     * @return the iP地址
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 设置 iP地址.
     *
     * @param ipAddress the new iP地址
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 获取 主机名.
     *
     * @return the 主机名
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * 设置 主机名.
     *
     * @param hostName the new 主机名
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * 获取 登陆时间.
     *
     * @return the 登陆时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 设置 登陆时间.
     *
     * @param loginTime the new 登陆时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}