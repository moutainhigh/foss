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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/service/impl/QueryProgressService.java
 *  
 *  FILE NAME          :QueryProgressService.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.service.impl
 * FILE    NAME: QueryProgressService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.load.api.server.dao.IQueryProgressDao;
import com.deppon.foss.module.transfer.load.api.server.service.IQueryProgressService;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskProgressConstants;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryProgressMapperResultDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryProgressResultDto;
import com.deppon.foss.module.transfer.load.api.shared.exception.QueryProgressException;

/**
 * 提供外部查询装卸车进度的接口实现
 * @author 046130-foss-xuduowei
 * @date 2012-11-27 下午2:05:07
 */
public class QueryProgressService implements IQueryProgressService {
	//日志
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryProgressService.class);
	//dao接口
	private IQueryProgressDao queryProgressDao; 
	/** 
	 *  给月台查询是调用显示车辆的装车或卸车进度
	 * @param 车牌号
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-27 下午2:05:08
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IQueryProgressService#queryProgressResult(java.lang.String)
	 */
	@Override
	public QueryProgressResultDto queryProgressResult(String taskNo) {
		QueryProgressResultDto progressDto = new QueryProgressResultDto();	
		Map<String,Object> queryMap = new HashMap<String,Object>();
		//String vehicleNo = platformDistributeEntity.getVehicleNo();
		if(StringUtils.isBlank(taskNo)){
			//
		}
		queryMap.put("taskNo", taskNo);
		queryMap.put("taskState", TaskProgressConstants.LOADING);
		
		List<QueryProgressMapperResultDto> mapper;
		//先查装车进度
		LOGGER.info("开始执行装车进度查询，任务号" + taskNo);
		mapper = queryProgressDao.queryLoadProgressResult(queryMap);
		LOGGER.info("结束装车进度查询，任务号" + taskNo);
		//如果装车进度未找到，则进行卸车进度查询
		if(mapper != null && !mapper.isEmpty()){
			progressDto = obatinProgressResultDto(mapper);
		}else{
			LOGGER.info("装车任务未找到，需要执行卸车进度查询");
			LOGGER.info("开始执行卸车进度查询，任务号" + taskNo);
			//重新设置任务状态，未卸车进度状态
			queryMap.put("taskState", TaskProgressConstants.UNLOADING);
			mapper = queryProgressDao.queryUnloadProgressResult(queryMap);
			LOGGER.info("结束卸车进度查询，任务号" + taskNo);
			if(mapper !=null && !mapper.isEmpty()){
				progressDto = obatinProgressResultDto(mapper);
			}
		}
		
		return progressDto;
	}
	public void setQueryProgressDao(IQueryProgressDao queryProgressDao) {
		this.queryProgressDao = queryProgressDao;
	}
	
	/** 
	 *  调整进度数据格式
	 * @param 车牌号
	 * @author 046130-foss-xuduowei
	 * @date 2013-03-04 上午10:05:08
	 */
	private QueryProgressResultDto obatinProgressResultDto(List<QueryProgressMapperResultDto> mapper){
		// 设置百分比格式
		DecimalFormat df = new DecimalFormat("##%");
		// 已装体积
		BigDecimal loadedVolume;
		// 已装重量
		BigDecimal loadedWeight;
		// 额定体积
		BigDecimal ratedVolume;
		// 额定重量
		BigDecimal ratedWeight;
		
		QueryProgressResultDto progressDto = new QueryProgressResultDto();

		QueryProgressMapperResultDto mapperDto =  mapper.get(0);
		loadedVolume = mapperDto.getLoadedVolume();
		loadedWeight = mapperDto.getLoadedWeight();
		ratedVolume = mapperDto.getRatedVolume();
		ratedWeight = mapperDto.getRatedWeight();
		// 设置体积百分比
		if (loadedVolume != null && ratedVolume != null) {
			try {
				double volume = loadedVolume.divide(ratedVolume, 2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				progressDto.setVolumeProgress(df.format(volume));
			} catch (ArithmeticException e) {
				LOGGER.error("数学格式异常"+e.getMessage());
				throw new QueryProgressException("装卸体积或额定体积异常");
			}

		}
		// 设置重量百分比
		if (loadedWeight != null && ratedVolume != null) {
			try {
				double weight = loadedWeight.divide(ratedWeight, 2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				progressDto.setWeightProgress(df.format(weight));
			} catch (ArithmeticException e) {
				LOGGER.error("数学格式异常"+e.getMessage());
				throw new QueryProgressException("装卸重量或额定重量异常");
			}
		}
		//装车
		progressDto.setTaskType(TaskProgressConstants.LOAD);
		//结束时间
		progressDto.setTaskEndTime(mapperDto.getTaskEndTime());
		
		return progressDto;
	}

}