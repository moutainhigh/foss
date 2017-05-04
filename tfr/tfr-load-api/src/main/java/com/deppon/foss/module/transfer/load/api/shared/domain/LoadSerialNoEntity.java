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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/LoadSerialNoEntity.java
 *  
 *  FILE NAME          :LoadSerialNoEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * LoadSerialNoEntity
 * @author dp-duyi
 * @date 2012-11-19 上午8:52:54
 */
public class LoadSerialNoEntity extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 454261692725563841L;

	/**
	 * 
	 */
	private String id;
	/**LOAD_WAYBILL_DETAIL_ID*/
    private String loadWaybillDetailId;	
    /**是否装车*/
    private String beLoaded;	
    /**扫描状态*/
    private String scanState;		
    /**货物状态*/
    private String goodsState;		
    /**操作时间*/
    private Date loadTime;		
    /**创建时间*/
    private Date createTime;	
    /**设备编号*/
    private String deviceNo;		
    /**流水号*/
    private String serialNo;	
    /**建立任务时间*/
    private Date taskBeginTime;	
    /**出发部门编号*/
    private String origOrgCode;
    
	/**悟空运单类型**/
	private String cargoType;
	
	/**悟空运单编号**/
	private String cargoNo;

   
    public String getCargoType() {
		return cargoType;
	}


	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}


	public String getCargoNo() {
		return cargoNo;
	}


	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}


	public String getId() {
        return id;
    }

   
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the lOAD_WAYBILL_DETAIL_ID.
     *
     * @return the lOAD_WAYBILL_DETAIL_ID
     */
    public String getLoadWaybillDetailId() {
        return loadWaybillDetailId;
    }

    /**
     * Sets the lOAD_WAYBILL_DETAIL_ID.
     *
     * @param loadWaybillDetailId the new lOAD_WAYBILL_DETAIL_ID
     */
    public void setLoadWaybillDetailId(String loadWaybillDetailId) {
        this.loadWaybillDetailId = loadWaybillDetailId;
    }

    /**
     * Gets the 是否装车.
     *
     * @return the 是否装车
     */
    public String getBeLoaded() {
        return beLoaded;
    }

    /**
     * Sets the 是否装车.
     *
     * @param beLoaded the new 是否装车
     */
    public void setBeLoaded(String beLoaded) {
        this.beLoaded = beLoaded;
    }

    /**
     * Gets the 扫描状态.
     *
     * @return the 扫描状态
     */
    public String getScanState() {
        return scanState;
    }

    /**
     * Sets the 扫描状态.
     *
     * @param scanState the new 扫描状态
     */
    public void setScanState(String scanState) {
        this.scanState = scanState;
    }

    /**
     * Gets the 货物状态.
     *
     * @return the 货物状态
     */
    public String getGoodsState() {
        return goodsState;
    }

    /**
     * Sets the 货物状态.
     *
     * @param goodsState the new 货物状态
     */
    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    /**
     * Gets the 操作时间.
     *
     * @return the 操作时间
     */
    public Date getLoadTime() {
        return loadTime;
    }

    /**
     * Sets the 操作时间.
     *
     * @param loadTime the new 操作时间
     */
    public void setLoadTime(Date loadTime) {
        this.loadTime = loadTime;
    }

    /**
     * Gets the 创建时间.
     *
     * @return the 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Sets the 创建时间.
     *
     * @param createTime the new 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * Gets the 设备编号.
     *
     * @return the 设备编号
     */
    public String getDeviceNo() {
        return deviceNo;
    }

    /**
     * Sets the 设备编号.
     *
     * @param deviceNo the new 设备编号
     */
    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    /**
     * Gets the 流水号.
     *
     * @return the 流水号
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * Sets the 流水号.
     *
     * @param serialNo the new 流水号
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * Gets the 建立任务时间.
     *
     * @return the 建立任务时间
     */
    public Date getTaskBeginTime() {
        return taskBeginTime;
    }

    /**
     * Sets the 建立任务时间.
     *
     * @param taskBeginTime the new 建立任务时间
     */
    public void setTaskBeginTime(Date taskBeginTime) {
        this.taskBeginTime = taskBeginTime;
    }

    /**
     * Gets the 出发部门编号.
     *
     * @return the 出发部门编号
     */
    public String getOrigOrgCode() {
        return origOrgCode;
    }

    /**
     * Sets the 出发部门编号.
     *
     * @param origOrgCode the new 出发部门编号
     */
    public void setOrigOrgCode(String origOrgCode) {
        this.origOrgCode = origOrgCode;
    }


	@Override
	public String toString() {
		return "LoadSerialNoEntity [id=" + id + ", loadWaybillDetailId=" + loadWaybillDetailId + ", beLoaded="
				+ beLoaded + ", scanState=" + scanState + ", goodsState=" + goodsState + ", loadTime=" + loadTime
				+ ", createTime=" + createTime + ", deviceNo=" + deviceNo + ", serialNo=" + serialNo
				+ ", taskBeginTime=" + taskBeginTime + ", origOrgCode=" + origOrgCode + ", cargoType=" + cargoType
				+ ", cargoNo=" + cargoNo + ", getCreateUser()=" + getCreateUser() + ", getModifyUser()="
				+ getModifyUser() + ", getCreateDate()=" + getCreateDate() + ", getModifyDate()=" + getModifyDate()
				+ ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()=" + super.toString()
				+ "]";
	}

}