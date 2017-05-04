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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/FeedbackDto.java
 * 
 *  FILE NAME     :FeedbackDto.java
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
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;

/**
 *  走货路径服务接口返回DTO
 * @author huyue
 * @date 2012-10-15 下午12:50:34
 */
public class FeedbackDto implements Serializable {
	
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 2503911558468096776L;
	/**
	 * 结果状态
	 */
	private int result;

	/**
	 * 走货路径明细entity
	 */
	private PathDetailEntity pathDetailEntity;

	/**
	 * 获取 走货路径明细entity.
	 *
	 * @return the 走货路径明细entity
	 */
	public PathDetailEntity getPathDetailEntity() {
		return pathDetailEntity;
	}

	/**
	 * 设置 走货路径明细entity.
	 *
	 * @param pathDetailEntity the new 走货路径明细entity
	 */
	public void setPathDetailEntity(PathDetailEntity pathDetailEntity) {
		this.pathDetailEntity = pathDetailEntity;
	}

	/**
	 * 获取 结果状态.
	 *
	 * @return the 结果状态
	 */
	public int getResult() {
		return result;
	}

	/**
	 * 设置 结果状态.
	 *
	 * @param result the new 结果状态
	 */
	public void setResult(int result) {
		this.result = result;
	}


}