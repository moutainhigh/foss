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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/PDADepartResultDTO.java
 *  
 *  FILE NAME          :PDADepartResultDTO.java
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
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

public class PDADepartResultDTO extends BaseEntity{

	private static final long serialVersionUID = -3703272448562684594L;

	private String status;        //放行状态
	private String driverName;    //司机姓名
	private String driverPhone;   //司机电话
	private String vehicleNo;     //车牌号
	private Date departTime;	  //放行时间
	private String departUser;    //放行人
	private String departType;    //放行类型
	private List<String> handoverbills;  //交接单号
	private List<String> sealNos;  //封签号
	private String vehicleStatus;  //车辆状态
	private String departItems;    //放行事项
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getDriverName()
	{
		return driverName;
	}
	public void setDriverName(String driverName)
	{
		this.driverName = driverName;
	}
	public String getDriverPhone()
	{
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone)
	{
		this.driverPhone = driverPhone;
	}
	public String getVehicleNo()
	{
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo)
	{
		this.vehicleNo = vehicleNo;
	}
	public Date getDepartTime()
	{
		return departTime;
	}
	public void setDepartTime(Date departTime)
	{
		this.departTime = departTime;
	}
	public String getDepartUser()
	{
		return departUser;
	}
	public void setDepartUser(String departUser)
	{
		this.departUser = departUser;
	}
	public String getDepartType()
	{
		return departType;
	}
	public void setDepartType(String departType)
	{
		this.departType = departType;
	}
	public List<String> getHandoverbills()
	{
		return handoverbills;
	}
	public void setHandoverbills(List<String> handoverbills)
	{
		this.handoverbills = handoverbills;
	}
	public List<String> getSealNos()
	{
		return sealNos;
	}
	public void setSealNos(List<String> sealNos)
	{
		this.sealNos = sealNos;
	}
	public String getVehicleStatus()
	{
		return vehicleStatus;
	}
	public void setVehicleStatus(String vehicleStatus)
	{
		this.vehicleStatus = vehicleStatus;
	}
	public String getDepartItems()
	{
		return departItems;
	}
	public void setDepartItems(String departItems)
	{
		this.departItems = departItems;
	}
	
	
}