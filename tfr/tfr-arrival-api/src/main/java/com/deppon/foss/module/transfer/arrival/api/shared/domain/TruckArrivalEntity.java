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
 *  PROJECT NAME  : tfr-arrival-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/arrival/api/shared/domain/TruckArrivalEntity.java
 *  
 *  FILE NAME          :TruckArrivalEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.arrival.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-24 下午6:54:24
 */
public class TruckArrivalEntity extends BaseEntity
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/**********车牌号***********/
    private String vehicleNo;             
    
    /**********pda到达人***********/
    private String pdaArriveUserCode;     
    
    /**********pda到达部门**********/
    private String pdaArriveOrgCode;      
    
    /**********pda设备号***********/
    private String pdaTerminalNo;  		  
    
    /**********到达时间***********/
    private Date pdaArriveTime;			  
    
    /**********电子围栏到达时间***********/
    private Date gpsArriveTime;			  
    
    /**********手工到达人***********/
    private String manualArriveUserCode;  
    
    /**********手工到达人部门************/
    private String manualArriveOrgCode;   
    
    /**********手工到达时间************/
    private Date manualArriveTime;       
    
    /**********状态***********/
    private String status;				  
    
    /**********创建时间************/
    private Date createTime;			  
    
    /**********创建人************/
    private String createUserCode;		  
    
    /**********创建人姓名************/
    private String createUserName;		  
    
    /**********创建人编码************/
    private String createOrgCode;		  
    
    /**********更新时间************/
    private Date updateTime;		 	  
    
    /**********更新人编码************/
    private String updateUserCode;  
    
    /**********
    
    /**
     * 获取 ********车牌号**********.
     *
     * @return the ********车牌号**********
     */
    public String getVehicleNo()
	{
		return vehicleNo;
	}

	/**
	 * 设置 ********车牌号**********.
	 *
	 * @param vehicleNo the new ********车牌号**********
	 */
	public void setVehicleNo(String vehicleNo)
	{
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 ********pda到达人**********.
	 *
	 * @return the ********pda到达人**********
	 */
	public String getPdaArriveUserCode()
	{
		return pdaArriveUserCode;
	}

	/**
	 * 设置 ********pda到达人**********.
	 *
	 * @param pdaArriveUserCode the new ********pda到达人**********
	 */
	public void setPdaArriveUserCode(String pdaArriveUserCode)
	{
		this.pdaArriveUserCode = pdaArriveUserCode;
	}

	/**
	 * 获取 ********pda到达部门*********.
	 *
	 * @return the ********pda到达部门*********
	 */
	public String getPdaArriveOrgCode()
	{
		return pdaArriveOrgCode;
	}

	/**
	 * 设置 ********pda到达部门*********.
	 *
	 * @param pdaArriveOrgCode the new ********pda到达部门*********
	 */
	public void setPdaArriveOrgCode(String pdaArriveOrgCode)
	{
		this.pdaArriveOrgCode = pdaArriveOrgCode;
	}

	/**
	 * 获取 ********pda设备号**********.
	 *
	 * @return the ********pda设备号**********
	 */
	public String getPdaTerminalNo()
	{
		return pdaTerminalNo;
	}

	/**
	 * 设置 ********pda设备号**********.
	 *
	 * @param pdaTerminalNo the new ********pda设备号**********
	 */
	public void setPdaTerminalNo(String pdaTerminalNo)
	{
		this.pdaTerminalNo = pdaTerminalNo;
	}

	/**
	 * 获取 ********到达时间**********.
	 *
	 * @return the ********到达时间**********
	 */
	public Date getPdaArriveTime()
	{
		return pdaArriveTime;
	}

	/**
	 * 设置 ********到达时间**********.
	 *
	 * @param pdaArriveTime the new ********到达时间**********
	 */
	public void setPdaArriveTime(Date pdaArriveTime)
	{
		this.pdaArriveTime = pdaArriveTime;
	}

	/**
	 * 获取 ********电子围栏到达时间**********.
	 *
	 * @return the ********电子围栏到达时间**********
	 */
	public Date getGpsArriveTime()
	{
		return gpsArriveTime;
	}

	/**
	 * 设置 ********电子围栏到达时间**********.
	 *
	 * @param gpsArriveTime the new ********电子围栏到达时间**********
	 */
	public void setGpsArriveTime(Date gpsArriveTime)
	{
		this.gpsArriveTime = gpsArriveTime;
	}

	/**
	 * 获取 ********手工到达人**********.
	 *
	 * @return the ********手工到达人**********
	 */
	public String getManualArriveUserCode()
	{
		return manualArriveUserCode;
	}

	/**
	 * 设置 ********手工到达人**********.
	 *
	 * @param manualArriveUserCode the new ********手工到达人**********
	 */
	public void setManualArriveUserCode(String manualArriveUserCode)
	{
		this.manualArriveUserCode = manualArriveUserCode;
	}

	/**
	 * 获取 ********手工到达人部门***********.
	 *
	 * @return the ********手工到达人部门***********
	 */
	public String getManualArriveOrgCode()
	{
		return manualArriveOrgCode;
	}

	/**
	 * 设置 ********手工到达人部门***********.
	 *
	 * @param manualArriveOrgCode the new ********手工到达人部门***********
	 */
	public void setManualArriveOrgCode(String manualArriveOrgCode)
	{
		this.manualArriveOrgCode = manualArriveOrgCode;
	}

	/**
	 * 获取 ********手工到达时间***********.
	 *
	 * @return the ********手工到达时间***********
	 */
	public Date getManualArriveTime()
	{
		return manualArriveTime;
	}

	/**
	 * 设置 ********手工到达时间***********.
	 *
	 * @param manualArriveTime the new ********手工到达时间***********
	 */
	public void setManualArriveTime(Date manualArriveTime)
	{
		this.manualArriveTime = manualArriveTime;
	}

	/**
	 * 获取 ********状态**********.
	 *
	 * @return the ********状态**********
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * 设置 ********状态**********.
	 *
	 * @param status the new ********状态**********
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

	/**
	 * 获取 ********创建时间***********.
	 *
	 * @return the ********创建时间***********
	 */
	public Date getCreateTime()
	{
		return createTime;
	}

	/**
	 * 设置 ********创建时间***********.
	 *
	 * @param createTime the new ********创建时间***********
	 */
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	/**
	 * 获取 ********创建人***********.
	 *
	 * @return the ********创建人***********
	 */
	public String getCreateUserCode()
	{
		return createUserCode;
	}

	/**
	 * 设置 ********创建人***********.
	 *
	 * @param createUserCode the new ********创建人***********
	 */
	public void setCreateUserCode(String createUserCode)
	{
		this.createUserCode = createUserCode;
	}

	/**
	 * 获取 ********创建人姓名***********.
	 *
	 * @return the ********创建人姓名***********
	 */
	public String getCreateUserName()
	{
		return createUserName;
	}

	/**
	 * 设置 ********创建人姓名***********.
	 *
	 * @param createUserName the new ********创建人姓名***********
	 */
	public void setCreateUserName(String createUserName)
	{
		this.createUserName = createUserName;
	}

	/**
	 * 获取 ********创建人编码***********.
	 *
	 * @return the ********创建人编码***********
	 */
	public String getCreateOrgCode()
	{
		return createOrgCode;
	}

	/**
	 * 设置 ********创建人编码***********.
	 *
	 * @param createOrgCode the new ********创建人编码***********
	 */
	public void setCreateOrgCode(String createOrgCode)
	{
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 获取 ********更新时间***********.
	 *
	 * @return the ********更新时间***********
	 */
	public Date getUpdateTime()
	{
		return updateTime;
	}

	/**
	 * 设置 ********更新时间***********.
	 *
	 * @param updateTime the new ********更新时间***********
	 */
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	/**
	 * 获取 ********更新人编码***********.
	 *
	 * @return the ********更新人编码***********
	 */
	public String getUpdateUserCode()
	{
		return updateUserCode;
	}

	/**
	 * 设置 ********更新人编码***********.
	 *
	 * @param updateUserCode the new ********更新人编码***********
	 */
	public void setUpdateUserCode(String updateUserCode)
	{
		this.updateUserCode = updateUserCode;
	}

	/**
	 * 获取 ********更信任姓名***********.
	 *
	 * @return the ********更信任姓名***********
	 */
	public String getUpdateUserName()
	{
		return updateUserName;
	}

	/**
	 * 设置 ********更信任姓名***********.
	 *
	 * @param updateUserName the new ********更信任姓名***********
	 */
	public void setUpdateUserName(String updateUserName)
	{
		this.updateUserName = updateUserName;
	}

	/**
	 * 获取 ********更新人部门编码***********.
	 *
	 * @return the ********更新人部门编码***********
	 */
	public String getUpdateOrgCode()
	{
		return updateOrgCode;
	}

	/**
	 * 设置 ********更新人部门编码***********.
	 *
	 * @param updateOrgCode the new ********更新人部门编码***********
	 */
	public void setUpdateOrgCode(String updateOrgCode)
	{
		this.updateOrgCode = updateOrgCode;
	}

	/**
	 * 获取 ********月台号***********.
	 *
	 * @return the ********月台号***********
	 */
	public String getPlatformDistributeId()
	{
		return platformDistributeId;
	}

	/**
	 * 设置 ********月台号***********.
	 *
	 * @param platformDistributeId the new ********月台号***********
	 */
	public void setPlatformDistributeId(String platformDistributeId)
	{
		this.platformDistributeId = platformDistributeId;
	}

	/**********更信任姓名************/
    private String updateUserName;		  
    
    /**********更新人部门编码************/
    private String updateOrgCode;		  
    
    /**********月台号************/
    private String platformDistributeId;
	
	
}