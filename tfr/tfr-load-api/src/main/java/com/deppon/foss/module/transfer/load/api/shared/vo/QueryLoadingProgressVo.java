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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/vo/QueryLoadingProgressVo.java
 *  
 *  FILE NAME          :QueryLoadingProgressVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.dto.QueryLoadingProgressConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryLoadingProgressResultDto;

/**
 * 查询装车进度vo
 * @author 046130-foss-xuduowei
 * @date 2012-11-19 下午1:37:24
 */
public class QueryLoadingProgressVo implements Serializable {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 6298698867995160085L;
	/**
	 * 查询装车进度条件
	 */
	private QueryLoadingProgressConditionDto queryLoadingProgressConditionDto;
	/**
	 * 查询装车进度返回结果
	 */
	private List<QueryLoadingProgressResultDto> queryLoadingProgressResultList;
	/**
	 * 每页数量
	 */
	private int pageSize;
	/**
	 * 起始页
	 */
	private int initStart;
	
	/**
	 * 总条数(总记录数)
	 */
	private Integer count;
	
	
	
	/**
	 * 获取 查询装车进度条件.
	 *
	 * @return the 查询装车进度条件
	 */
	public QueryLoadingProgressConditionDto getQueryLoadingProgressConditionDto() {
		return queryLoadingProgressConditionDto;
	}
	
	/**
	 * 获取 查询装车进度返回结果.
	 *
	 * @return the 查询装车进度返回结果
	 */
	public List<QueryLoadingProgressResultDto> getQueryLoadingProgressResultList() {
		return queryLoadingProgressResultList;
	}
	
	/**
	 * 设置 查询装车进度返回结果.
	 *
	 * @param queryLoadingProgressResultList the new 查询装车进度返回结果
	 */
	public void setQueryLoadingProgressResultList(List<QueryLoadingProgressResultDto> queryLoadingProgressResultList) {
		this.queryLoadingProgressResultList = queryLoadingProgressResultList;
	}
	
	/**
	 * 设置 查询装车进度条件.
	 *
	 * @param queryLoadingProgressConditionDto the new 查询装车进度条件
	 */
	public void setQueryLoadingProgressConditionDto(QueryLoadingProgressConditionDto queryLoadingProgressConditionDto) {
		this.queryLoadingProgressConditionDto = queryLoadingProgressConditionDto;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}