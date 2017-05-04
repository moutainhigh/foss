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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/QueryLeadTruckEntity.java
 * 
 *  FILE NAME     :QueryLeadTruckEntity.java
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

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * Entity开发规范 1.必须继承com.deppon.foss.framework.entity.BaseEntity 2.类名必须以Entity结尾
 * 3.必须生成serialVersionUID 4.建议属性名称与数据库字段命名规则一致
 */
public class QueryLeadTruckEntity extends BaseEntity
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;
	/********** 起始时间 ************/
	private Date startTime;
	/********** 结束时间 ************/
	private Date endTime;
	/********** 出发部门 ************/
	private String origOrgCode;
	/********** 到达部门 ************/
	private String destOrgCode;
	private List<String> ids;
	
	/**
	 * 获取 ******** 起始时间 ***********.
	 *
	 * @return the ******** 起始时间 ***********
	 */
	public Date getStartTime()
	{
		return startTime;
	}
	
	/**
	 * 设置 ******** 起始时间 ***********.
	 *
	 * @param startTime the new ******** 起始时间 ***********
	 */
	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}
	
	/**
	 * 获取 ******** 结束时间 ***********.
	 *
	 * @return the ******** 结束时间 ***********
	 */
	public Date getEndTime()
	{
		return endTime;
	}
	
	/**
	 * 设置 ******** 结束时间 ***********.
	 *
	 * @param endTime the new ******** 结束时间 ***********
	 */
	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}
	
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

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

}