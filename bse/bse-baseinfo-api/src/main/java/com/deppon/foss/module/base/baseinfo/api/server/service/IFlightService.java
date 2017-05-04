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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IFlightService.java
 * 
 * FILE NAME        	: IFlightService.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FlightException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverException;

/**
 * 用来操作交互“航班信息”的数据库对应数据访问Service接口：SUC-785
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-11-3 上午9:31:49
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-11-3 上午9:31:49
 * @since
 * @version
 */
public interface IFlightService extends IService {

	/**
	 * <p>
	 * 新增一个“航班信息”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-12 上午11:52:39
	 * @param flight
	 *            “航班信息”实体
	 * @param createUser
	 *            创建人
	 * @param ignoreNull
	 *            true：忽略空值，false：包含空值
	 * @return 1：成功；0：失败
	 * @see
	 */
	int addFlight(FlightEntity flight, String createUser, boolean ignoreNull)
			throws FlightException;

	/**
	 * <p>
	 * 根据“航班信息”记录唯一标识作废（物理删除）一条“航班信息”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-12 上午11:52:36
	 * @param ids
	 *            记录唯一标识集合
	 * @param modifyUser
	 *            修改人
	 * @return 1：成功；0：失败
	 * @see
	 */
	int deleteFlight(List<String> ids, String modifyUser)
			throws FlightException;

	/**
	 * <p>
	 * 修改一个“航班信息”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-12 上午11:52:49
	 * @param flight
	 *            “航班信息”实体
	 * @param modifyUser
	 *            修改人
	 * @param ignoreNull
	 *            true：忽略空值，false：包含空值
	 * @return 1：成功；0：失败
	 * @see
	 */
	int updateFlight(FlightEntity flight, String modifyUser, boolean ignoreNull)
			throws FlightException;

	/**
	 * <p>
	 * 根据“航班信息”记录唯一标识查询出一条“航班信息”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-12 上午11:52:45
	 * @param id
	 *            记录唯一标识
	 * @return “航班信息”实体
	 * @see
	 */
	FlightEntity queryFlightBySelective(String id) throws FlightException;

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件一个的“航班信息”实体（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-14 上午11:23:53
	 * @param airport
	 *            以“航班信息”实体承载的条件参数实体
	 * @return 符合条件的“航班信息”实体
	 * @throws LeasedDriverException
	 * @see
	 */
	FlightEntity queryFlightBySelective(FlightEntity flight)
			throws FlightException;

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件多个的“航班信息”实体（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-14 上午11:23:53
	 * @param airport
	 *            以“航班信息”实体承载的条件参数实体
	 * @return 符合条件的“航班信息”实体列表
	 * @throws FlightException
	 * @see
	 */
	List<FlightEntity> queryFlightListBySelective(FlightEntity flight)
			throws FlightException;

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“航班信息”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 上午11:02:46
	 * @param flight
	 *            以“航班信息”实体承载的条件参数实体
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @return 分页的Action和Service通讯封装对象
	 * @see
	 */
	PaginationDto queryFlightListBySelectiveCondition(FlightEntity flight,
			int offset, int limit) throws FlightException;

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“航班信息和关联信息”的封装DTO实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-12-28 下午1:52:18
	 * @param flight
	 *            以“航班信息”实体承载的条件参数实体
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @return 分页的Action和Service通讯封装对象
	 * @throws FlightException
	 * @see
	 */
	PaginationDto queryFlightDtoListBySelectiveCondition(FlightEntity flight,
			int offset, int limit) throws FlightException;
}
