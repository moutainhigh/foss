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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/domain/StTaskPdaEntity.java
 *  
 *  FILE NAME          :StTaskPdaEntity.java
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
 * 清仓任务pda实体
 * @author foss-wuyingjie
 * @date 2012-12-26 上午9:45:19
 */
public class StTaskPdaEntity extends BaseEntity{
	
	private static final long serialVersionUID = 4003755417630934943L;
	/**清仓任务编号*/
    private String stTaskNo;
    /**状态*/
    private String status;
    /**pda编号*/
    private String pdaNo;
    /**扫描时间*/
    private Date scanTime;
    /**创建时间*/
    private Date createTime;

    /**
     * 获取 状态.
     *
     * @return the 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置 状态.
     *
     * @param status the new 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取 pda编号.
     *
     * @return the pda编号
     */
    public String getPdaNo() {
        return pdaNo;
    }

    /**
     * 设置 pda编号.
     *
     * @param pdaNo the new pda编号
     */
    public void setPdaNo(String pdaNo) {
        this.pdaNo = pdaNo;
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