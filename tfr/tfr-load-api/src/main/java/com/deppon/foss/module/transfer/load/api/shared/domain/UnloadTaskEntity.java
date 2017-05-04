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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/domain/UnloadTaskEntity.java
 *  
 *  FILE NAME          :UnloadTaskEntity.java
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
 * 卸车任务
 * @author ibm-zhangyixin
 * @date 2012-11-29 上午10:22:54
 */
public class UnloadTaskEntity extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -729156729545037375L;
	
	/**ID**/
	private String id;
	
	/**是否生成卸车差异报告**/
    private String beCreatedGapRep;

	/**卸车任务编号**/
    private String unloadTaskNo;	

    /**车牌号**/
    private String vehicleNo;		

    /**月台号**/
    private String platformNo;		

    /**月台ID**/
    private String platformId;		

    /**卸车开始时间**/
    private Date unloadStartTime;	

    /**卸车结束时间**/
    private Date unloadEndTime;		

    /**任务状态**/
    private String taskState;		

    /**卸车类型**/
    private String unloadType;		

    /**卸车方式**/
    private String unloadWay;		

    /**建立任务部门编码**/
    private String unloadOrgCode;	

    /**建立任务部门名称**/
    private String unloadOrgName;	

    /**是否卸车异常**/
    private String beException;		

    /**异常备注**/
    private String exceptionNotes;	

    /**计划完成时间**/
    private Date planCompleteTime;

    /**是否扫描入库**/
    private String beScanInstock;   
    
    /**是否生成分批配载差错数据**/
    private String beCreatedBL;
    /** 
     * id
     * @author ibm-zhangyixin
     * @date 2012-12-25 下午6:53:42
     * @see com.deppon.foss.framework.entity.BaseEntity#getId()
     */
    public String getId() {
        return id;
    }

    /** 
     * id
     * @author ibm-zhangyixin
     * @date 2012-12-25 下午6:53:42
     * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * 获取 是否生成卸车差异报告*.
     *
     * @return the 是否生成卸车差异报告*
     */
    public String getBeCreatedGapRep() {
		return beCreatedGapRep;
	}

	/**
	 * 设置 是否生成卸车差异报告*.
	 *
	 * @param beCreatedGapRep the new 是否生成卸车差异报告*
	 */
	public void setBeCreatedGapRep(String beCreatedGapRep) {
		this.beCreatedGapRep = beCreatedGapRep;
	}
    
    /**
     * 获取 卸车任务编号*.
     *
     * @return the 卸车任务编号*
     */
    public String getUnloadTaskNo() {
        return unloadTaskNo;
    }

    /**
     * 设置 卸车任务编号*.
     *
     * @param unloadTaskNo the new 卸车任务编号*
     */
    public void setUnloadTaskNo(String unloadTaskNo) {
        this.unloadTaskNo = unloadTaskNo;
    }

    /**
     * 获取 车牌号*.
     *
     * @return the 车牌号*
     */
    public String getVehicleNo() {
        return vehicleNo;
    }

    /**
     * 设置 车牌号*.
     *
     * @param vehicleNo the new 车牌号*
     */
    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    /**
     * 获取 月台号*.
     *
     * @return the 月台号*
     */
    public String getPlatformNo() {
        return platformNo;
    }

    /**
     * 设置 月台号*.
     *
     * @param platformNo the new 月台号*
     */
    public void setPlatformNo(String platformNo) {
        this.platformNo = platformNo;
    }

    /**
     * 获取 月台ID*.
     *
     * @return the 月台ID*
     */
    public String getPlatformId() {
        return platformId;
    }

    /**
     * 设置 月台ID*.
     *
     * @param platformId the new 月台ID*
     */
    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    /**
     * 获取 卸车开始时间*.
     *
     * @return the 卸车开始时间*
     */
    public Date getUnloadStartTime() {
        return unloadStartTime;
    }

    /**
     * 设置 卸车开始时间*.
     *
     * @param unloadStartTime the new 卸车开始时间*
     */
    public void setUnloadStartTime(Date unloadStartTime) {
        this.unloadStartTime = unloadStartTime;
    }

    /**
     * 获取 卸车结束时间*.
     *
     * @return the 卸车结束时间*
     */
    public Date getUnloadEndTime() {
        return unloadEndTime;
    }

    /**
     * 设置 卸车结束时间*.
     *
     * @param unloadEndTime the new 卸车结束时间*
     */
    public void setUnloadEndTime(Date unloadEndTime) {
        this.unloadEndTime = unloadEndTime;
    }

    /**
     * 获取 任务状态*.
     *
     * @return the 任务状态*
     */
    public String getTaskState() {
        return taskState;
    }

    /**
     * 设置 任务状态*.
     *
     * @param taskState the new 任务状态*
     */
    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    /**
     * 获取 卸车类型*.
     *
     * @return the 卸车类型*
     */
    public String getUnloadType() {
        return unloadType;
    }

    /**
     * 设置 卸车类型*.
     *
     * @param unloadType the new 卸车类型*
     */
    public void setUnloadType(String unloadType) {
        this.unloadType = unloadType;
    }

    /**
     * 获取 卸车方式*.
     *
     * @return the 卸车方式*
     */
    public String getUnloadWay() {
        return unloadWay;
    }

    /**
     * 设置 卸车方式*.
     *
     * @param unloadWay the new 卸车方式*
     */
    public void setUnloadWay(String unloadWay) {
        this.unloadWay = unloadWay;
    }

    /**
     * 获取 建立任务部门编码*.
     *
     * @return the 建立任务部门编码*
     */
    public String getUnloadOrgCode() {
        return unloadOrgCode;
    }

    /**
     * 设置 建立任务部门编码*.
     *
     * @param unloadOrgCode the new 建立任务部门编码*
     */
    public void setUnloadOrgCode(String unloadOrgCode) {
        this.unloadOrgCode = unloadOrgCode;
    }

    /**
     * 获取 建立任务部门名称*.
     *
     * @return the 建立任务部门名称*
     */
    public String getUnloadOrgName() {
        return unloadOrgName;
    }

    /**
     * 设置 建立任务部门名称*.
     *
     * @param unloadOrgName the new 建立任务部门名称*
     */
    public void setUnloadOrgName(String unloadOrgName) {
        this.unloadOrgName = unloadOrgName;
    }

    /**
     * 获取 是否卸车异常*.
     *
     * @return the 是否卸车异常*
     */
    public String getBeException() {
        return beException;
    }

    /**
     * 设置 是否卸车异常*.
     *
     * @param beException the new 是否卸车异常*
     */
    public void setBeException(String beException) {
        this.beException = beException;
    }

    /**
     * 获取 异常备注*.
     *
     * @return the 异常备注*
     */
    public String getExceptionNotes() {
        return exceptionNotes;
    }

    /**
     * 设置 异常备注*.
     *
     * @param exceptionNotes the new 异常备注*
     */
    public void setExceptionNotes(String exceptionNotes) {
        this.exceptionNotes = exceptionNotes;
    }

	/**
	 * 获取 计划完成时间*.
	 *
	 * @return the 计划完成时间*
	 */
	public Date getPlanCompleteTime() {
		return planCompleteTime;
	}

	/**
	 * 设置 计划完成时间*.
	 *
	 * @param planCompleteTime the new 计划完成时间*
	 */
	public void setPlanCompleteTime(Date planCompleteTime) {
		this.planCompleteTime = planCompleteTime;
	}

	/**
	 * 获取 是否扫描入库*.
	 *
	 * @return the 是否扫描入库*
	 */
	public String getBeScanInstock() {
		return beScanInstock;
	}

	/**
	 * 设置 是否扫描入库*.
	 *
	 * @param beScanInstock the new 是否扫描入库*
	 */
	public void setBeScanInstock(String beScanInstock) {
		this.beScanInstock = beScanInstock;
	}

	public String getBeCreatedBL() {
		return beCreatedBL;
	}

	public void setBeCreatedBL(String beCreatedBL) {
		this.beCreatedBL = beCreatedBL;
	}
}