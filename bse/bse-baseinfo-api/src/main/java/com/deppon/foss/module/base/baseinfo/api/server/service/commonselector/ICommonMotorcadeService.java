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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonMotorcadeService.java
 * 
 * FILE NAME        	: ICommonMotorcadeService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.server.service.commonselector
 * FILE    NAME: ICommonMotorcadeService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonMotorcadeDto;

/**
 * 公共查询选择器--车队查询service接口
 * @author 078823-foss-panGuangJun
 * @date 2012-12-11 下午2:28:49
 */
public interface ICommonMotorcadeService {
	/**
	 * 根据dto模糊查询，
	 * 
	 * @author 078823-foss-panGuangjun
	 * @date 2012-12-11 下午2:32:19
	 * @param entity:查询条件 
	 * @param start:开始条数
	 * @param limit:页数大小
	 * @return List<MotorcadeEntity>
	 */
	List<MotorcadeEntity> queryMotorcadeByCondition(CommonMotorcadeDto dto,	int start, int limit);

	/**
	 * 查询queryMotorcadeByEntity返回的记录总数,用于分页
	 * 
	 * @author 078823-foss-panGuangjun
	 * @date 2012-12-11 下午2:32:19
	 *  @param entity:查询条件 
	 *  @return long
	 */
	long queryMotorcadeByConditionCount(CommonMotorcadeDto dto);
}
