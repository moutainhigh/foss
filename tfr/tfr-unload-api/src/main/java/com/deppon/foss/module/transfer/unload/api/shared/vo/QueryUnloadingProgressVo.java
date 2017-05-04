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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/vo/QueryUnloadingProgressVo.java
 *  
 *  FILE NAME          :QueryUnloadingProgressVo.java
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
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.shared.vo
 * FILE    NAME: QueryUnloadingProgressVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.dto.LoadAndUnloadStandardDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadingProgressConditionDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadingProgressResultDto;

/**
 * 查询卸车进度VO
 * @author 046130-foss-xuduowei
 * @date 2012-12-12 上午9:18:01
 */
public class QueryUnloadingProgressVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 625103101140704049L;
	/**
	 * 卸车进度查询条件
	 */
	private QueryUnloadingProgressConditionDto queryUnloadingProgressConditionDto;
	/**
	 * 卸车进度查询结果
	 */
	private List<QueryUnloadingProgressResultDto> queryUnloadingProgressResultDtoList;
	/**
	 * 装卸车标准
	 */
	private LoadAndUnloadStandardDto loadAndUnloadStandardDto;
	/**
	 * 每页数量
	 */
	private int pageSize;
	/**
	 * 	起始页
	 */
	private int initStart;
	
	/**共几页*/
	private Long totalPageSize;
	/**当前第几页*/
	private Long currentPageSize;

	/**
	 * 获取 卸车进度查询条件.
	 *
	 * @return the 卸车进度查询条件
	 */
	public QueryUnloadingProgressConditionDto getQueryUnloadingProgressConditionDto() {
		return queryUnloadingProgressConditionDto;
	}

	/**
	 * 设置 卸车进度查询条件.
	 *
	 * @param queryUnloadingProgressConditionDto the new 卸车进度查询条件
	 */
	public void setQueryUnloadingProgressConditionDto(
			QueryUnloadingProgressConditionDto queryUnloadingProgressConditionDto) {
		this.queryUnloadingProgressConditionDto = queryUnloadingProgressConditionDto;
	}
	
	
	/**
	 * 获取 装卸车标准.
	 *
	 * @return the 装卸车标准
	 */
	public LoadAndUnloadStandardDto getLoadAndUnloadStandardDto() {
		return loadAndUnloadStandardDto;
	}

	/**
	 * 设置 装卸车标准.
	 *
	 * @param loadAndUnloadStandardDto the new 装卸车标准
	 */
	public void setLoadAndUnloadStandardDto(
			LoadAndUnloadStandardDto loadAndUnloadStandardDto) {
		this.loadAndUnloadStandardDto = loadAndUnloadStandardDto;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 获取 卸车进度查询结果.
	 *
	 * @return the 卸车进度查询结果
	 */
	public List<QueryUnloadingProgressResultDto> getQueryUnloadingProgressResultDtoList() {
		return queryUnloadingProgressResultDtoList;
	}

	/**
	 * 设置 卸车进度查询结果.
	 *
	 * @param queryUnloadingProgressResultDtoList the new 卸车进度查询结果
	 */
	public void setQueryUnloadingProgressResultDtoList(
			List<QueryUnloadingProgressResultDto> queryUnloadingProgressResultDtoList) {
		this.queryUnloadingProgressResultDtoList = queryUnloadingProgressResultDtoList;
	}

	/**
	 * 获取 每页数量.
	 *
	 * @return the 每页数量
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置 每页数量.
	 *
	 * @param pageSize the new 每页数量
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取 起始页.
	 *
	 * @return the 起始页
	 */
	public int getInitStart() {
		return initStart;
	}

	/**
	 * 设置 起始页.
	 *
	 * @param initStart the new 起始页
	 */
	public void setInitStart(int initStart) {
		this.initStart = initStart;
	}

	/**
	 * @return the totalPageSize
	 */
	public Long getTotalPageSize() {
		return totalPageSize;
	}

	/**
	 * @param totalPageSize the totalPageSize to set
	 */
	public void setTotalPageSize(Long totalPageSize) {
		this.totalPageSize = totalPageSize;
	}

	/**
	 * @return the currentPageSize
	 */
	public Long getCurrentPageSize() {
		return currentPageSize;
	}

	/**
	 * @param currentPageSize the currentPageSize to set
	 */
	public void setCurrentPageSize(Long currentPageSize) {
		this.currentPageSize = currentPageSize;
	}
	
	
}