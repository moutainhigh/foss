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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonMotorcadeSaleDeptService.java
 * 
 * FILE NAME        	: ICommonMotorcadeSaleDeptService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.server.service
 * FILE    NAME: ICommonMotorcadeSaleDeptService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;

/**
 * 公共查询组件--车队对应营业部service接口定义
 * @author 078823-foss-panGuangJun
 * @date 2012-12-5 下午2:43:20
 */
public interface ICommonMotorcadeSaleDeptService {
	/**
	 * 精确查询 动态的查询条件。 如果传入的对象为空，
	 * 传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 14:55:28
	 * @param entity
	 *            :查询条件 --包含车队编码,营业部名称
	 * @param start
	 *            :开始条数
	 * @param limit
	 *            :页数大小
	 * @return :车队对应营业部集合
	 */
	List<SalesMotorcadeEntity> queryMotorcadeSalesDeptByCondition(
			SalesMotorcadeEntity entity, int start, int limit);

	/**
	 * 精确查询-查询总条数，用于分页 动态的查询条件。
	 * 如果传入的对象为空，传入一个对象，
	 * 可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * @author 078823-foss-panGuangJun
	 *@param entity
	 *            :查询条件 --包含车队编码,营业部名称
	 * @date 2012-12-5 14:55:28
	 */
	long queryMotorcadeSalesDeptByConditionCount(SalesMotorcadeEntity entity);
}
