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
 *  PROJECT NAME  : tfr-unload
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/server/action/QueryUnloadingProgressAction.java
 *  
 *  FILE NAME          :QueryUnloadingProgressAction.java
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
 * PROJECT NAME: tfr-unload
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.server.action
 * FILE    NAME: QueryUnloadingProgressAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.server.action;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.transfer.unload.api.server.service.IQueryUnloadingProgressService;
import com.deppon.foss.module.transfer.unload.api.shared.dto.LoadAndUnloadStandardDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadingProgressResultDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.QueryUnloadingProgressVo;

/**
 * 查询卸车进度ACTION
 * @author 046130-foss-xuduowei
 * @date 2012-12-11 下午4:26:25
 */
public class QueryUnloadingProgressAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5830803984693538711L;
	
	/**
	 * 查询卸车进度service
	 */
	private IQueryUnloadingProgressService queryUnloadingProgressService;
	/**
	 * vo
	 */
	private QueryUnloadingProgressVo queryUnloadingProgressVo = new QueryUnloadingProgressVo();
	/**
	 * 
	 * 查询卸车进度信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-12 下午3:12:03
	 */
	public String queryUnloadingProgressInfo(){
		//查询卸车进度
		List<QueryUnloadingProgressResultDto> unloadingProgressList = 
				queryUnloadingProgressService.queryUnloadingProgressInfo(
						queryUnloadingProgressVo.getQueryUnloadingProgressConditionDto(),
						queryUnloadingProgressVo.getPageSize(),
						queryUnloadingProgressVo.getInitStart());
		//查询卸车进度
		super.totalCount = 
				queryUnloadingProgressService.queryUnloadingProgressInfoCount(
						queryUnloadingProgressVo.getQueryUnloadingProgressConditionDto());
		
		//共几页
		Long totalPageSize=super.totalCount%queryUnloadingProgressVo.getPageSize()==0?super.totalCount/queryUnloadingProgressVo.getPageSize():super.totalCount/queryUnloadingProgressVo.getPageSize()+1;
		queryUnloadingProgressVo.setTotalPageSize(totalPageSize);
		//当前第几页
		Long currentPageSize=(long) (queryUnloadingProgressVo.getInitStart()%queryUnloadingProgressVo.getPageSize()==0?queryUnloadingProgressVo.getInitStart()/queryUnloadingProgressVo.getPageSize()+1:queryUnloadingProgressVo.getInitStart()/queryUnloadingProgressVo.getPageSize()+2);
		queryUnloadingProgressVo.setCurrentPageSize(currentPageSize);
		
		
		queryUnloadingProgressVo.setQueryUnloadingProgressResultDtoList(unloadingProgressList);
		return returnSuccess();
	}
	
	/**
	 * 
	 * 查询当前部门的卸车标准
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-19 上午8:17:47
	 */
	public String queryLoadAndUnloadStd(){
		try {
			LoadAndUnloadStandardDto standardDto = queryUnloadingProgressService.queryLoadAndUnloadStd();
			queryUnloadingProgressVo.setLoadAndUnloadStandardDto(standardDto);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
		
	}
	
	
	/**
	 * 获取 vo.
	 *
	 * @return the vo
	 */
	public QueryUnloadingProgressVo getQueryUnloadingProgressVo() {
		return queryUnloadingProgressVo;
	}
	
	/**
	 * 设置 vo.
	 *
	 * @param queryUnloadingProgressVo the new vo
	 */
	public void setQueryUnloadingProgressVo(
			QueryUnloadingProgressVo queryUnloadingProgressVo) {
		this.queryUnloadingProgressVo = queryUnloadingProgressVo;
	}
	
	/**
	 * 设置 查询卸车进度service.
	 *
	 * @param queryUnloadingProgressService the new 查询卸车进度service
	 */
	public void setQueryUnloadingProgressService(
			IQueryUnloadingProgressService queryUnloadingProgressService) {
		this.queryUnloadingProgressService = queryUnloadingProgressService;
	}

}