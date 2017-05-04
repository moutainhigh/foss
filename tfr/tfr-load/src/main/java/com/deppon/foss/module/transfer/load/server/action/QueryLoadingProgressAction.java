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
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/action/QueryLoadingProgressAction.java
 *  
 *  FILE NAME          :QueryLoadingProgressAction.java
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
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.action
 * FILE    NAME: QueryLoadingProgressAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.action;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.load.api.server.service.IQueryLoadingProgressService;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryLoadingProgressResultDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.QueryLoadingProgressVo;

/**
 * 查询装车进度
 * @author 046130-foss-xuduowei
 * @date 2012-11-6 上午10:12:18
 */
public class QueryLoadingProgressAction extends AbstractAction {
	
	/**
	 * 查询装车进度接口
	 */
	private IQueryLoadingProgressService queryLoadingProgressService;

	/**
	 * 序列
	 */
	private static final long serialVersionUID = -439129558156685679L;
	/**
	 * 装车进度vo
	 */
	private QueryLoadingProgressVo queryLoadingProgressVo = new QueryLoadingProgressVo();
	
	/**
	 * 
	 * 查询装车进度
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-19 下午4:52:54
	 */
	@JSON
	public String queryLoadingProgressInfo(){
		try {
			//查询装车进度 参数：查询条件dto,页大小，开始
			List<QueryLoadingProgressResultDto> queryLoadingProgressResultList = 
					queryLoadingProgressService.queryLoadingProgressInfo(
					queryLoadingProgressVo.getQueryLoadingProgressConditionDto(),
					queryLoadingProgressVo.getPageSize(), 
					queryLoadingProgressVo.getInitStart());
			
			Integer count = queryLoadingProgressService
					.queryLoadingProgressInfoCount(queryLoadingProgressVo
							.getQueryLoadingProgressConditionDto(), 
							queryLoadingProgressVo.getPageSize(), 
							queryLoadingProgressVo.getInitStart());

			queryLoadingProgressVo.setQueryLoadingProgressResultList(queryLoadingProgressResultList);
			queryLoadingProgressVo.setCount(count);
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
	public QueryLoadingProgressVo getQueryLoadingProgressVo() {
		return queryLoadingProgressVo;
	}

	/**
	 * 设置 vo.
	 *
	 * @param queryLoadingProgressVo the new vo
	 */
	public void setQueryLoadingProgressVo(QueryLoadingProgressVo queryLoadingProgressVo) {
		this.queryLoadingProgressVo = queryLoadingProgressVo;
	}

	/**
	 *
	 * @param queryLoadingProgressService 
	 */
	public void setQueryLoadingProgressService(IQueryLoadingProgressService queryLoadingProgressService) {
		this.queryLoadingProgressService = queryLoadingProgressService;
	}
	
}