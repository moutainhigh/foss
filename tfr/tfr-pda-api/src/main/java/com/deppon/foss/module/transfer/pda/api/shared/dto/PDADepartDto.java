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
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/PDADepartDto.java
 *  
 *  FILE NAME          :PDADepartDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class PDADepartDto extends BaseEntity
{
	
	private static final long serialVersionUID = -3703272448562684594L;
	
	/**********所在外场部门************/
	private String orgCode;
	
	/**********车牌号************/
	private String vehicleNo;
	
	/**********扫描时间************/
	private Date operatingTime;
	
	/**********操作人************/
	private String operator;
	
	/**********PDA设备号************/
	private String pdaTerminalNo;
	
	/**********封签状态************/
	private String sealStatus;
	
	/**********封签破损数量************/
	private int damagedSealCount;

	public String getOrgCode()
	{
		return orgCode;
	}

	public void setOrgCode(String orgCode)
	{
		this.orgCode = orgCode;
	}

	public String getVehicleNo()
	{
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo)
	{
		this.vehicleNo = vehicleNo;
	}

	

	public Date getOperatingTime()
	{
		return operatingTime;
	}

	public void setOperatingTime(Date operatingTime)
	{
		this.operatingTime = operatingTime;
	}

	public String getOperator()
	{
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	public String getPdaTerminalNo()
	{
		return pdaTerminalNo;
	}

	public void setPdaTerminalNo(String pdaTerminalNo)
	{
		this.pdaTerminalNo = pdaTerminalNo;
	}

	public String getSealStatus()
	{
		return sealStatus;
	}

	public void setSealStatus(String sealStatus)
	{
		this.sealStatus = sealStatus;
	}

	public int getDamagedSealCount()
	{
		return damagedSealCount;
	}

	public void setDamagedSealCount(int damagedSealCount)
	{
		this.damagedSealCount = damagedSealCount;
	}
	

}