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
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/domain/StResultListPdaEntity.java
 *  
 *  FILE NAME          :StResultListPdaEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * pda扫描结果实体
 * @author foss-wuyingjie
 * @date 2012-12-26 上午9:47:16
 */
public class StResultListPdaEntity extends BaseEntity{
	
	private static final long serialVersionUID = -8770223145789854626L;
	/**清仓任务编号*/
    private String stTaskNo;
    /**包号*/
    private String packageNo;
    /**运单号*/
    private String waybillNo;
    /**流水号*/
    private String serialNo;
    /**扫描状态*/
    private String scanStatus;
    /**扫描时间*/
    private Date scanTime;
    /**PDA设备号*/
    private String pdaNo;
    /**创建时间*/
    private Date createTime;
    /**创建人工号*/
    private String  empCode;
    /**创建人名称*/
    private String empName;
    /**
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}

	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	/**
     * 获取 包号.
     *
     * @return the 包号
     */
	public String getPackageNo() {
		return packageNo;
	}
	
	/**
     * 设置 包号.
     *
     * @param packageNo the new 包号
     */
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	/**
     * 获取 运单号.
     *
     * @return the 运单号
     */
    public String getWaybillNo() {
        return waybillNo;
    }

    /**
     * 设置 运单号.
     *
     * @param waybillNo the new 运单号
     */
    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    /**
     * 获取 流水号.
     *
     * @return the 流水号
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * 设置 流水号.
     *
     * @param serialNo the new 流水号
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * 获取 扫描状态.
     *
     * @return the 扫描状态
     */
    public String getScanStatus() {
        return scanStatus;
    }

    /**
     * 设置 扫描状态.
     *
     * @param scanStatus the new 扫描状态
     */
    public void setScanStatus(String scanStatus) {
        this.scanStatus = scanStatus;
    }

    /**
     * 获取 扫描时间.
     *
     * @return the 扫描时间
     */
    public Date getScanTime() {
        return scanTime;
    }

    /**
     * 设置 扫描时间.
     *
     * @param scanTime the new 扫描时间
     */
    public void setScanTime(Date scanTime) {
        this.scanTime = scanTime;
    }

    /**
     * 获取 pDA设备号.
     *
     * @return the pDA设备号
     */
    public String getPdaNo() {
        return pdaNo;
    }

    /**
     * 设置 pDA设备号.
     *
     * @param pdaNo the new pDA设备号
     */
    public void setPdaNo(String pdaNo) {
        this.pdaNo = pdaNo;
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
	 * 获取 清仓任务编号.
	 *
	 * @return the 清仓任务编号
	 */
	public String getStTaskNo() {
		return stTaskNo;
	}

	/**
	 * 设置 清仓任务编号.
	 *
	 * @param stTaskNo the new 清仓任务编号
	 */
	public void setStTaskNo(String stTaskNo) {
		this.stTaskNo = stTaskNo;
	}
}