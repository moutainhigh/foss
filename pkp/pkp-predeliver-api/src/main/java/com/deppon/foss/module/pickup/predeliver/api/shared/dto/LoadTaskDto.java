/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/LoadTaskDto.java
 * 
 * FILE NAME        	: LoadTaskDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 派送装车任务DTO
 * @author ibm-wangxiexu
 * @date 2012-11-30 下午6:42:39
 */
public class LoadTaskDto implements Serializable {
	private static final long serialVersionUID = 1777624216226412296L;

	private String taskId; // 任务ID
	private String taskNo; // 任务编号
	private String loadGaprepId; // 装车差异报告ID

	private String deliverbillNo; // 派送单单号
	private List<String> stateList; // 任务状态
	private String loadGaprepValid; // 装车差异报告状态

	/**
	 * @return the deliverbillNo
	 */
	public String getDeliverbillNo() {
		return deliverbillNo;
	}

	/**
	 * @param deliverbillNo the deliverbillNo to see
	 */
	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
	}

	/**
	 * @return the stateList
	 */
	public List<String> getStateList() {
		return stateList;
	}

	/**
	 * @param stateList the stateList to see
	 */
	public void setStateList(List<String> stateList) {
		this.stateList = stateList;
	}

	/**
	 * @return the loadGaprepValid
	 */
	public String getLoadGaprepValid() {
		return loadGaprepValid;
	}

	/**
	 * @param loadGaprepValid the loadGaprepValid to see
	 */
	public void setLoadGaprepValid(String loadGaprepValid) {
		this.loadGaprepValid = loadGaprepValid;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to see
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the taskNo
	 */
	public String getTaskNo() {
		return taskNo;
	}

	/**
	 * @param taskNo the taskNo to see
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	/**
	 * @return the loadGaprepId
	 */
	public String getLoadGaprepId() {
		return loadGaprepId;
	}

	/**
	 * @param loadGaprepId the loadGaprepId to see
	 */
	public void setLoadGaprepId(String loadGaprepId) {
		this.loadGaprepId = loadGaprepId;
	}

}