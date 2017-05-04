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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/vo/TfrJobProcessLogVo.java
 *  
 *  FILE NAME          :TfrJobProcessLogVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobProcessDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobProcessLogDto;

/**
 * JOB日志查询VO
 * 
 * @author foss-yuyongxiang
 * @date 2013年5月2日 09:55:11
 */
public class TfrJobProcessLogVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * JOB 日志查询
	 */
	private TfrJobProcessLogDto jobProcessLogDto;
	/**
	 * JOB 实体对象 list
	 */
	private List<TfrJobProcessLogEntity> jobProcessLogEList=new ArrayList<TfrJobProcessLogEntity>();
	
	/**
	 * JOB 实体对象
	 */
	private TfrJobProcessLogEntity jobProcessLogE;
	
	/**
	 * JOB 日志查询
	 */
	private TfrJobProcessDto jobProcessDto;
	/**
	 * JOB 实体对象 list
	 */
	private List<TfrJobProcessEntity> jobProcessEList=new ArrayList<TfrJobProcessEntity>();
	
	/**
	 * JOB 实体对象
	 */
	private TfrJobProcessEntity jobProcessE;
	
	
	/**
	 * JOB  ID 
	 */
	private String id;
	

	/**
	 * JOB 日志查询
	 * 
	 * @return the jobProcessLogDto
	 */
	public TfrJobProcessLogDto getJobProcessLogDto() {
		return jobProcessLogDto;
	}

	/**
	 * JOB 日志查询
	 * 
	 * @param jobProcessLogDto
	 *            the jobProcessLogDto to set
	 */
	public void setJobProcessLogDto(TfrJobProcessLogDto jobProcessLogDto) {
		this.jobProcessLogDto = jobProcessLogDto;
	}

	/**
	 * JOB 实体对象 list
	 * 
	 * @return the jobProcessLogEList
	 */
	public List<TfrJobProcessLogEntity> getJobProcessLogEList() {
		return jobProcessLogEList;
	}

	/**
	 * JOB 实体对象 list
	 * 
	 * @param jobProcessLogEList
	 *            the jobProcessLogEList to set
	 */
	public void setJobProcessLogEList(
			List<TfrJobProcessLogEntity> jobProcessLogEList) {
		this.jobProcessLogEList = jobProcessLogEList;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the jobProcessLogE
	 */
	public TfrJobProcessLogEntity getJobProcessLogE() {
		return jobProcessLogE;
	}

	/**
	 * @param jobProcessLogE the jobProcessLogE to set
	 */
	public void setJobProcessLogE(TfrJobProcessLogEntity jobProcessLogE) {
		this.jobProcessLogE = jobProcessLogE;
	}
	/**
	 * JOB 日志查询
	 * 
	 * @return the jobProcessDto
	 */
	public TfrJobProcessDto getJobProcessDto() {
		return jobProcessDto;
	}

	/**
	 * JOB 日志查询
	 * 
	 * @param jobProcessDto
	 *            the jobProcessDto to set
	 */
	public void setJobProcessDto(TfrJobProcessDto jobProcessDto) {
		this.jobProcessDto = jobProcessDto;
	}

	/**
	 * JOB 实体对象 list
	 * 
	 * @return the jobProcessEList
	 */
	public List<TfrJobProcessEntity> getJobProcessEList() {
		return jobProcessEList;
	}

	/**
	 * JOB 实体对象 list
	 * 
	 * @param jobProcessEList
	 *            the jobProcessEList to set
	 */
	public void setJobProcessEList(
			List<TfrJobProcessEntity> jobProcessEList) {
		this.jobProcessEList = jobProcessEList;
	}

	/**
	 * @return the jobProcessE
	 */
	public TfrJobProcessEntity getJobProcessE() {
		return jobProcessE;
	}

	/**
	 * @param jobProcessE the jobProcessE to set
	 */
	public void setJobProcessE(TfrJobProcessEntity jobProcessE) {
		this.jobProcessE = jobProcessE;
	}

}