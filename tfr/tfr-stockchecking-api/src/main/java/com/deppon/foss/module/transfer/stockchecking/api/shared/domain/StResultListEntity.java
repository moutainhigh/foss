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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/domain/StResultListEntity.java
 *  
 *  FILE NAME          :StResultListEntity.java
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
 * 清仓结果清单实体类
 * @author foss-wuyingjie
 * @date 2012-10-16 下午3:46:20
 */
public class StResultListEntity extends BaseEntity{
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 8838222493974507243L;
	/**包号*/
    private String packageNo;
	/**运单号*/
    private String waybillNo;
    /**流水号*/
    private String serialNo;
    /**清仓任务ID*/
    private String stTaskId;
    /**扫描方式*/
    private String scanStatus;
    /**扫描结果*/
    private String goodsStatus;
    /**创建时间*/
    private Date createTime;
    /**PDA提交时间*/
    private Date pdaUploadTime;
    /**PDA编号*/
    private String pdaNo;
    /**库位号*/
    private String positionNo;

	
	/**
     * 获取库位信息
     * @return String
     */
	 public String getPositionNo() {
			return positionNo;
	}
    /**
     * 设置库位
     * @param position
     */
	 public void setPositionNo(String positionNo) {
			this.positionNo = positionNo;
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
     * 获取 清仓任务ID.
     *
     * @return the 清仓任务ID
     */
    public String getStTaskId() {
        return stTaskId;
    }

    /**
     * 设置 清仓任务ID.
     *
     * @param stTaskId the new 清仓任务ID
     */
    public void setStTaskId(String stTaskId) {
        this.stTaskId = stTaskId;
    }

	/**
	 * 获取 扫描方式.
	 *
	 * @return the 扫描方式
	 */
	public String getScanStatus() {
		return scanStatus;
	}

	/**
	 * 设置 扫描方式.
	 *
	 * @param scanStatus the new 扫描方式
	 */
	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
	}

	/**
	 * 获取 扫描结果.
	 *
	 * @return the 扫描结果
	 */
	public String getGoodsStatus() {
		return goodsStatus;
	}

	/**
	 * 设置 扫描结果.
	 *
	 * @param goodsStatus the new 扫描结果
	 */
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
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
	 * 获取 pDA提交时间.
	 *
	 * @return the pDA提交时间
	 */
	public Date getPdaUploadTime() {
		return pdaUploadTime;
	}

	/**
	 * 设置 pDA提交时间.
	 *
	 * @param pdaUploadTime the new pDA提交时间
	 */
	public void setPdaUploadTime(Date pdaUploadTime) {
		this.pdaUploadTime = pdaUploadTime;
	}

	/**
	 * 获取 pDA编号.
	 *
	 * @return the pDA编号
	 */
	public String getPdaNo() {
		return pdaNo;
	}

	/**
	 * 设置 pDA编号.
	 *
	 * @param pdaNo the new pDA编号
	 */
	public void setPdaNo(String pdaNo) {
		this.pdaNo = pdaNo;
	}
}