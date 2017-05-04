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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IAirlinesService.java
 * 
 * FILE NAME        	: IAirlinesService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;

/**
 * 航空公司service接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-xieyantao,date:2012-10-15 上午10:56:40
 * </p>
 * 
 * @author dp-xieyantao
 * @date 2012-10-15 上午10:56:40
 * @since
 * @version
 */
public interface IAirlinesService extends IService {

	/**
	 * 新增航空公司
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-15 上午10:55:00
	 * @param entity
	 *            航空公司实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addAirlines(AirlinesEntity entity);

	/**
	 * 根据code作废航空公司
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-15 上午10:55:00
	 * @param codeStr
	 *            编码拼接字符串
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int deleteAirlinesByCode(String codeStr, String modifyUser);

	/**
	 * 修改航空公司
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-15 上午10:55:00
	 * @param entity
	 *            航空公司实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int updateAirlines(AirlinesEntity entity);

	/**
	 * 根据传入对象查询符合条件所有航空公司信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-15 上午10:55:00
	 * @param entity
	 *            航空公司实体
	 * @param limit
	 *            每页最大显示记录数
	 * @param start
	 *            开始记录数
	 * @return 符合条件的实体列表
	 * @see
	 */
	List<AirlinesEntity> queryAirlines(AirlinesEntity entity, int limit,
			int start);

	/**
	 * 统计总记录数
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-15 上午10:55:00
	 * @param entity
	 *            航空公司实体
	 * @return
	 * @see
	 */
	Long queryRecordCount(AirlinesEntity entity);

	/**
	 * <p>
	 * 查询所有有效航空公司信息
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-11-28 下午4:16:22
	 * @return
	 * @see
	 */
	List<AirlinesEntity> queryAllAirlines();

	/**
	 * 根据航空公司编码查询公司是否存在
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-10-30 上午11:35:49
	 * @param airlineCode
	 *            航空公司编码
	 * @return
	 * @see
	 */
	AirlinesEntity queryAirlineByCode(String airlineCode);

	/**
	 * 根据航空公司名称查询公司是否存在
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-10-30 上午11:35:49
	 * @param airlineCode
	 *            航空公司名称
	 * @return
	 * @see
	 */
	AirlinesEntity queryAirlineByName(String airlineName);

	/**
	 * 根据航空公司简称查询公司是否存在
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-10-30 上午11:35:49
	 * @param airlineCode
	 *            航空公司简称
	 * @return
	 * @see
	 */
	AirlinesEntity queryAirlineBySimpleName(String simpleName);

}
