/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonFlightService.java
 * 
 * FILE NAME        	: CommonFlightService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonFlightDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonFlightService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FlightDto;
/**
 * 公共查询组件--“航班信息”的数据库对应数据访问Service接口
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-8 下午2:34:03
 */
public class CommonFlightService implements ICommonFlightService {
	
	 
	/**
	 * 航班信息DAO
	 */
	private ICommonFlightDao commonFlightDao;
	
	 /**
     * 根据传入对象查询符合条件所有航班信息
     * @author 101911-foss-zhouChunlai
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @date 2013-1-8 下午2:33:41
     * @return 符合条件的实体列表
     */
	@Override
	public List<FlightEntity> queryFlight(FlightDto dto,int start,int limit){
		return commonFlightDao.queryFlightListByCondition(dto, limit, start);
	}

	 /**
     * 统计总记录数 
     * @author 101911-foss-zhouChunlai
     * @param dto 航班信息实体
     * @date 2013-1-8 下午2:33:23
     * @return Long
     */ 
	@Override
	public Long queryRecordCount(FlightDto dto) {
		return commonFlightDao.countFlightListByCondition(dto);
	}

	
	public void setCommonFlightDao(ICommonFlightDao commonFlightDao) {
		this.commonFlightDao = commonFlightDao;
	}
 

}
