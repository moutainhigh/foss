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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/GuiEnvCollectEntity.java
 * 
 * FILE NAME        	: GuiEnvCollectEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * GUI环境采集实体
 * @author 038590-foss-wanghui
 * @date 2013-2-23 下午3:28:56
 */
public class GuiEnvCollectEntity extends BaseEntity {
	/**
	 * 生成序列化标识
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private String id;

	/**
	 * cpu总量
	 */
    private String cpuTotal;
    /**
     * cpu供应商
     */
    private String cpuVendor;
    /**
     * cpu类型
     */
    private String cpuType;
    /**
     * 总物理内存
     */
    private String ramTotal;
    /**
     * 已用物理内存
     */
    private String ramUsed;
    /**
     * 空闲物理内存
     */
    private String ramFree;
    /**
     * 版本号
     */
    private String progVersion;
    /**
     * 程序包大小
     */
    private String progSize;
    /**
     * 程序最后更新时间
     */
    private Date progLastUpdateTime;
    /**
     * 数据最后更新时间
     */
    private Date dataLastUpdateTime;
    /**
     * jvm供应商
     */
    private String jvmVendor;
    /**
     * jvm版本号
     */
    private String jvmVersion;
    /**
     * jvm home
     */
    private String jvmHome;
    /**
     * jvm path
     */
    private String jvmPath;
    /**
     * jvm位数
     */
    private String jvmBit;
    /**
     * 操作系统版本
     */
    private String osVersion;
    /**
     * 操作系统位数
     */
    private String osBit;
    /**
     * 主机名称
     */
    private String hostName;
    /**
     * 操作系统类型
     */
    private String osType;
    /**
     * mac地址
     */
    private String macAddress;
    /**
     * ip地址
     */
    private String ipAddress;
    /**
     * 创建时间
     */
    private Date createTime;
    private String resolution;
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the cpuTotal
	 */
	public String getCpuTotal() {
		return cpuTotal;
	}
	/**
	 * @param cpuTotal the cpuTotal to set
	 */
	public void setCpuTotal(String cpuTotal) {
		this.cpuTotal = cpuTotal;
	}
	/**
	 * @return the cpuVendor
	 */
	public String getCpuVendor() {
		return cpuVendor;
	}
	/**
	 * @param cpuVendor the cpuVendor to set
	 */
	public void setCpuVendor(String cpuVendor) {
		this.cpuVendor = cpuVendor;
	}
	/**
	 * @return the cpuType
	 */
	public String getCpuType() {
		return cpuType;
	}
	/**
	 * @param cpuType the cpuType to set
	 */
	public void setCpuType(String cpuType) {
		this.cpuType = cpuType;
	}
	/**
	 * @return the ramTotal
	 */
	public String getRamTotal() {
		return ramTotal;
	}
	/**
	 * @param ramTotal the ramTotal to set
	 */
	public void setRamTotal(String ramTotal) {
		this.ramTotal = ramTotal;
	}
	/**
	 * @return the ramUsed
	 */
	public String getRamUsed() {
		return ramUsed;
	}
	/**
	 * @param ramUsed the ramUsed to set
	 */
	public void setRamUsed(String ramUsed) {
		this.ramUsed = ramUsed;
	}
	/**
	 * @return the ramFree
	 */
	public String getRamFree() {
		return ramFree;
	}
	/**
	 * @param ramFree the ramFree to set
	 */
	public void setRamFree(String ramFree) {
		this.ramFree = ramFree;
	}
	/**
	 * @return the progVersion
	 */
	public String getProgVersion() {
		return progVersion;
	}
	/**
	 * @param progVersion the progVersion to set
	 */
	public void setProgVersion(String progVersion) {
		this.progVersion = progVersion;
	}
	/**
	 * @return the progSize
	 */
	public String getProgSize() {
		return progSize;
	}
	/**
	 * @param progSize the progSize to set
	 */
	public void setProgSize(String progSize) {
		this.progSize = progSize;
	}
	/**
	 * @return the progLastUpdateTime
	 */
	public Date getProgLastUpdateTime() {
		return progLastUpdateTime;
	}
	/**
	 * @param progLastUpdateTime the progLastUpdateTime to set
	 */
	public void setProgLastUpdateTime(Date progLastUpdateTime) {
		this.progLastUpdateTime = progLastUpdateTime;
	}
	/**
	 * @return the dataLastUpdateTime
	 */
	public Date getDataLastUpdateTime() {
		return dataLastUpdateTime;
	}
	/**
	 * @param dataLastUpdateTime the dataLastUpdateTime to set
	 */
	public void setDataLastUpdateTime(Date dataLastUpdateTime) {
		this.dataLastUpdateTime = dataLastUpdateTime;
	}
	/**
	 * @return the jvmVendor
	 */
	public String getJvmVendor() {
		return jvmVendor;
	}
	/**
	 * @param jvmVendor the jvmVendor to set
	 */
	public void setJvmVendor(String jvmVendor) {
		this.jvmVendor = jvmVendor;
	}
	/**
	 * @return the jvmVersion
	 */
	public String getJvmVersion() {
		return jvmVersion;
	}
	/**
	 * @param jvmVersion the jvmVersion to set
	 */
	public void setJvmVersion(String jvmVersion) {
		this.jvmVersion = jvmVersion;
	}
	/**
	 * @return the jvmHome
	 */
	public String getJvmHome() {
		return jvmHome;
	}
	/**
	 * @param jvmHome the jvmHome to set
	 */
	public void setJvmHome(String jvmHome) {
		this.jvmHome = jvmHome;
	}
	/**
	 * @return the jvmPath
	 */
	public String getJvmPath() {
		return jvmPath;
	}
	/**
	 * @param jvmPath the jvmPath to set
	 */
	public void setJvmPath(String jvmPath) {
		this.jvmPath = jvmPath;
	}
	/**
	 * @return the jvmBit
	 */
	public String getJvmBit() {
		return jvmBit;
	}
	/**
	 * @param jvmBit the jvmBit to set
	 */
	public void setJvmBit(String jvmBit) {
		this.jvmBit = jvmBit;
	}
	/**
	 * @return the osVersion
	 */
	public String getOsVersion() {
		return osVersion;
	}
	/**
	 * @param osVersion the osVersion to set
	 */
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	/**
	 * @return the osBit
	 */
	public String getOsBit() {
		return osBit;
	}
	/**
	 * @param osBit the osBit to set
	 */
	public void setOsBit(String osBit) {
		this.osBit = osBit;
	}
	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}
	/**
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	/**
	 * @return the osType
	 */
	public String getOsType() {
		return osType;
	}
	/**
	 * @param osType the osType to set
	 */
	public void setOsType(String osType) {
		this.osType = osType;
	}
	/**
	 * @return the macAddress
	 */
	public String getMacAddress() {
		return macAddress;
	}
	/**
	 * @param macAddress the macAddress to set
	 */
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

   
}