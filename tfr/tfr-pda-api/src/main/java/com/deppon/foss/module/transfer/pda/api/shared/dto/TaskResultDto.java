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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/TaskResultDto.java
 *  
 *  FILE NAME          :TaskResultDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.dto
 * FILE    NAME: TaskResultDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 建立装卸车任务返回PDA的Dto父类
 * @author dp-duyi
 * @date 2013-3-6 上午8:32:03
 */
public class TaskResultDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2132651497990136374L;
	/**理货员*/
	private List<LoaderDto> loaders;
	/**贵重物品货区编码*/
	//private String valueGoodsAreaCode;
	/**包装获取编码*/
	//private String packGoodsAreaCode;
	/**创建人编码*/
	private String creatorCode;
	/**创建人名称*/
	private String creatorName;
	/**任务编号*/
	private String taskNo;
	/**
	 * Gets the 任务编号.
	 *
	 * @return the 任务编号
	 */
	public String getTaskNo() {
		return taskNo;
	}
	/**
	 * Gets the 任务编号.
	 *
	 * @return the 任务编号
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	/**
	 * Gets the 理货员.
	 *
	 * @return the 理货员
	 */
	public List<LoaderDto> getLoaders() {
		return loaders;
	}
	
	/**
	 * Sets the 理货员.
	 *
	 * @param loaders the new 理货员
	 */
	public void setLoaders(List<LoaderDto> loaders) {
		this.loaders = loaders;
	}
	/**
	 * Gets the 创建人编码.
	 *
	 * @return the 创建人编码
	 */
	public String getCreatorCode() {
		return creatorCode;
	}
	
	/**
	 * Sets the 创建人编码.
	 *
	 * @param creatorCode the new 创建人编码
	 */
	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
	}
	
	/**
	 * Gets the 创建人名称.
	 *
	 * @return the 创建人名称
	 */
	public String getCreatorName() {
		return creatorName;
	}
	
	/**
	 * Sets the 创建人名称.
	 *
	 * @param creatorName the new 创建人名称
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
}