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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/LeadTruckEntity.java
 * 
 *  FILE NAME     :LeadTruckEntity.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * Entity开发规范 1.必须继承com.deppon.foss.framework.entity.BaseEntity 2.类名必须以Entity结尾
 * 3.必须生成serialVersionUID 4.建议属性名称与数据库字段命名规则一致
 */
public class LeadTruckEntity extends BaseEntity
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;
	/********** 出发部门 ************/
	private String origOrgCode;
	/********** 出发部门 名称************/
	private String origOrgName;
	/********** 到达部门 ************/
	private String destOrgCode;
	/********** 到达部门 名称************/
	private String destOrgName;
	/********** 车长 ************/
	private BigDecimal vehicleLength;
	/********** 货箱结构 ************/
	private String containerStructure;
	/********** 车长名称 ************/
	private String vehicleLengthName;
	/********** 运价 ************/
	private BigDecimal fee;
	/********** 载重 ************/
	private BigDecimal deadLoad;
	/********** 净空 ************/
	private BigDecimal selfVolumn;
	/********** 询价日期 ************/
	private String inquiryTime;
	/********** 询价日期 ************/
	private Date inquiryTimeDate;
	/********** 录入人 ************/
	private String createUserCode;
	/********** 录入人 ************/
	private String createUserName;
	/********** 录入时间 ************/
	private Date createTime;
	/********** 修改时间 ************/
	private Date updateTime;
	/********** 是否合同车价 ************/
	private String isContractTruck;
	/********** 信息来源 ************/
	private String sourceOfInformation;
	/********** 电话************/
	private String tele;
	/********** 备注************/
	private String notes;
	
	
	/**
	 * 获取 ******** 出发部门 ***********.
	 *
	 * @return the ******** 出发部门 ***********
	 */
	public String getOrigOrgCode()
	{
		return origOrgCode;
	}
	
	/**
	 * 设置 ******** 出发部门 ***********.
	 *
	 * @param origOrgCode the new ******** 出发部门 ***********
	 */
	public void setOrigOrgCode(String origOrgCode)
	{
		this.origOrgCode = origOrgCode;
	}
	
	/**
	 * 获取 ******** 到达部门 ***********.
	 *
	 * @return the ******** 到达部门 ***********
	 */
	public String getDestOrgCode()
	{
		return destOrgCode;
	}
	
	/**
	 * 设置 ******** 到达部门 ***********.
	 *
	 * @param destOrgCode the new ******** 到达部门 ***********
	 */
	public void setDestOrgCode(String destOrgCode)
	{
		this.destOrgCode = destOrgCode;
	}

	/**
	 * 获取 ******** 出发部门 名称***********.
	 *
	 * @return the ******** 出发部门 名称***********
	 */
	public String getOrigOrgName()
	{
		return origOrgName;
	}

	/**
	 * 设置 ******** 出发部门 名称***********.
	 *
	 * @param origOrgName the new ******** 出发部门 名称***********
	 */
	public void setOrigOrgName(String origOrgName)
	{
		this.origOrgName = origOrgName;
	}

	/**
	 * 获取 ******** 到达部门 名称***********.
	 *
	 * @return the ******** 到达部门 名称***********
	 */
	public String getDestOrgName()
	{
		return destOrgName;
	}

	/**
	 * 设置 ******** 到达部门 名称***********.
	 *
	 * @param destOrgName the new ******** 到达部门 名称***********
	 */
	public void setDestOrgName(String destOrgName)
	{
		this.destOrgName = destOrgName;
	}

	

	/**
	 * 获取 ******** 货箱结构 ***********.
	 *
	 * @return the ******** 货箱结构 ***********
	 */
	public String getContainerStructure()
	{
		return containerStructure;
	}

	/**
	 * 设置 ******** 货箱结构 ***********.
	 *
	 * @param containerStructure the new ******** 货箱结构 ***********
	 */
	public void setContainerStructure(String containerStructure)
	{
		this.containerStructure = containerStructure;
	}



	public String getInquiryTime() {
		return inquiryTime;
	}

	public void setInquiryTime(String inquiryTime) {
		this.inquiryTime = inquiryTime;
	}

	/**
	 * 获取 ******** 录入人 ***********.
	 *
	 * @return the ******** 录入人 ***********
	 */
	public String getCreateUserName()
	{
		return createUserName;
	}

	/**
	 * 设置 ******** 录入人 ***********.
	 *
	 * @param createUserName the new ******** 录入人 ***********
	 */
	public void setCreateUserName(String createUserName)
	{
		this.createUserName = createUserName;
	}

	/**
	 * 获取 ******** 是否合同车价 ***********.
	 *
	 * @return the ******** 是否合同车价 ***********
	 */
	public String getIsContractTruck()
	{
		return isContractTruck;
	}

	/**
	 * 设置 ******** 是否合同车价 ***********.
	 *
	 * @param isContractTruck the new ******** 是否合同车价 ***********
	 */
	public void setIsContractTruck(String isContractTruck)
	{
		this.isContractTruck = isContractTruck;
	}



	/**
	 * 获取 ******** 信息来源 ***********.
	 *
	 * @return the ******** 信息来源 ***********
	 */
	public String getSourceOfInformation()
	{
		return sourceOfInformation;
	}

	/**
	 * 设置 ******** 信息来源 ***********.
	 *
	 * @param sourceOfInformation the new ******** 信息来源 ***********
	 */
	public void setSourceOfInformation(String sourceOfInformation)
	{
		this.sourceOfInformation = sourceOfInformation;
	}

	/**
	 * 获取 ******** 电话***********.
	 *
	 * @return the ******** 电话***********
	 */
	public String getTele()
	{
		return tele;
	}

	/**
	 * 设置 ******** 电话***********.
	 *
	 * @param tele the new ******** 电话***********
	 */
	public void setTele(String tele)
	{
		this.tele = tele;
	}

	/**
	 * 获取 ******** 备注***********.
	 *
	 * @return the ******** 备注***********
	 */
	public String getNotes()
	{
		return notes;
	}

	/**
	 * 设置 ******** 备注***********.
	 *
	 * @param notes the new ******** 备注***********
	 */
	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	/**
	 * 获取 ******** 车长 ***********.
	 *
	 * @return the ******** 车长 ***********
	 */
	public BigDecimal getVehicleLength()
	{
		return vehicleLength;
	}

	/**
	 * 设置 ******** 车长 ***********.
	 *
	 * @param vehicleLength the new ******** 车长 ***********
	 */
	public void setVehicleLength(BigDecimal vehicleLength)
	{
		this.vehicleLength = vehicleLength;
	}

	/**
	 * 获取 ******** 运价 ***********.
	 *
	 * @return the ******** 运价 ***********
	 */
	public BigDecimal getFee()
	{
		return fee;
	}

	/**
	 * 设置 ******** 运价 ***********.
	 *
	 * @param fee the new ******** 运价 ***********
	 */
	public void setFee(BigDecimal fee)
	{
		this.fee = fee;
	}

	/**
	 * 获取 ******** 载重 ***********.
	 *
	 * @return the ******** 载重 ***********
	 */
	public BigDecimal getDeadLoad()
	{
		return deadLoad;
	}

	/**
	 * 设置 ******** 载重 ***********.
	 *
	 * @param deadLoad the new ******** 载重 ***********
	 */
	public void setDeadLoad(BigDecimal deadLoad)
	{
		this.deadLoad = deadLoad;
	}

	/**
	 * 获取 ******** 净空 ***********.
	 *
	 * @return the ******** 净空 ***********
	 */
	public BigDecimal getSelfVolumn()
	{
		return selfVolumn;
	}

	/**
	 * 设置 ******** 净空 ***********.
	 *
	 * @param selfVolumn the new ******** 净空 ***********
	 */
	public void setSelfVolumn(BigDecimal selfVolumn)
	{
		this.selfVolumn = selfVolumn;
	}

	/**
	 * 获取 ******** 录入时间 ***********.
	 *
	 * @return the ******** 录入时间 ***********
	 */
	public Date getCreateTime()
	{
		return createTime;
	}

	/**
	 * 设置 ******** 录入时间 ***********.
	 *
	 * @param createTime the new ******** 录入时间 ***********
	 */
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	/**
	 * 获取 ******** 修改时间 ***********.
	 *
	 * @return the ******** 修改时间 ***********
	 */
	public Date getUpdateTime()
	{
		return updateTime;
	}

	/**
	 * 设置 ******** 修改时间 ***********.
	 *
	 * @param updateTime the new ******** 修改时间 ***********
	 */
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	/**
	 * 获取 ******** 录入人 ***********.
	 *
	 * @return the ******** 录入人 ***********
	 */
	public String getCreateUserCode()
	{
		return createUserCode;
	}

	/**
	 * 设置 ******** 录入人 ***********.
	 *
	 * @param createUserCode the new ******** 录入人 ***********
	 */
	public void setCreateUserCode(String createUserCode)
	{
		this.createUserCode = createUserCode;
	}

	/**
	 * 获取 ******** 车长名称 ***********.
	 *
	 * @return the ******** 车长名称 ***********
	 */
	public String getVehicleLengthName()
	{
		return vehicleLengthName;
	}

	/**
	 * 设置 ******** 车长名称 ***********.
	 *
	 * @param vehicleLengthName the new ******** 车长名称 ***********
	 */
	public void setVehicleLengthName(String vehicleLengthName)
	{
		this.vehicleLengthName = vehicleLengthName;
	}

	public Date getInquiryTimeDate() {
		return inquiryTimeDate;
	}

	public void setInquiryTimeDate(Date inquiryTimeDate) {
		this.inquiryTimeDate = inquiryTimeDate;
	}

}