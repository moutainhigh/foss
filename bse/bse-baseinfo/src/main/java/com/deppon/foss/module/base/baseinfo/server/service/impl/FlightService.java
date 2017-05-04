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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/FlightService.java
 * 
 * FILE NAME        	: FlightService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *      * 修订记录 日期 修订内容 修订人员 版本号 2012-04-18 新增 李强 V0.1 2012-5-24 根据系统用例模板修改 谢艳涛 V0.2
 * 2012-7-02 根据王偕旭、赵鹏点评修改 谢艳涛 V0.3 2012-7-05 根据梁冠立、赵鹏点评修改 周春来 V0.4
 * 2012-08-02 通过业务部门审核签字版本升级到V0.9 周春来 V0.9
 * 
 * 1. SUC-56-新增_修改_作废_查询航班信息 1.1 相关业务用例 BUC_FOSS_5.70.30_060制作航空正单
 * BUC_FOSS_5.70.30_050 制作空运交接单 1.2 用例描述
 * 此用例用于维护航班信息基础资料，包括新增、修改、作废、查询，该基础资料主要在空运配载时使用。 1.3 用例条件 条件类型 描述 引用系统用例
 * 前置条件 1、 航空公司基础资料齐备 2、 航班信息完备 3、 机场信息基础资料完备 4、 机型基础资料完备 SUC-42
 * 新增_修改_删除_查询航空公司 SUC-52 新增_修改_作废_查询机场信息 SUC-785 新增_修改_作废_查询机型信息 后置条件 1、
 * 为SUC-247 录入航空正单明细等其他系统用例提供航班基础资料查询 SUC-247 录入航空正单明细 SUC-607生成航空正单交接单 1.4
 * 操作用户角色 操作用户 描述 空运管理员 空运管理员对航班信息进行新增，修改，作废，查询操作。 1.5 界面要求 1.5.1 表现方式 Web页面
 * 1.5.2 界面原型-主界面
 * 
 * 
 * 图一：航班信息主界面 1.5.3 界面描述-主界面 1. 功能按钮区域 1)
 * 新增按钮：点击新增按钮进入新增界面，参见【图二：航班信息新增和修改界面】。 2)
 * 查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。 3) 重置按钮：点击重置按钮，重置查询条件。 4)
 * 作废按钮：选中列表中一行或多行记录，点击作废按钮，选中的记录被作废；或点击各行的作废按钮，作废各行记录；需要弹出确认提示框。 5)
 * 修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：航班信息新增和修改界面】。 6) 分页按钮：实现分页功能。 2. 列表区域 1)
 * 列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。 2)
 * 列表中显示：航班号、航班类别、机型、航空公司、始发站代码、始发站、计划起飞时间、目的站代码、目的站、计划到达时间、班期、航行时间（小时）。 3.
 * 字段输入区域 1) 录入查询条件，点击查询按钮后在列表区显示满足条件的查询结果。 1.5.4 界面原型-新增和修改界面
 * 
 * 图二：航班信息新增和修改界面 1.5.5 界面描述-新增和修改界面 1. 字段输入区域 1) 航班类别： 必填，下拉框，包含：早班、中班、晚班；
 * 2) 航班号：必填，文本，航班编号 3) 航空公司：必填，选择框，从航空公司基础资料中选取 4) 始发站：必填，选择框，从机场信息基础资料中选取
 * 5) 目的站：必填，选择框，从机场信息基础资料中选取 6) 计划起飞时间：必填，时间选择框，使用时间控件，格式：12:30； 7)
 * 始发站代码：与“始发站”联动带出 8) 目的站代码：与“目的站站”联动带出 9)
 * 计划到达时间：必填，时间选择框，使用时间控件，格式：14:30；计划到达时间必须大于计划起飞时间 10)
 * 飞机机型：选填，选择框，从机型基础资料中选择 11) 班期：必填，多选框，包含：周一、周二、周三、周四、周五、周六、周日，默认为不选择 12)
 * 航行时间（小时）：通过“计划到达时间”-“计划起飞时间”计算算出； 2. 功能按钮区域 1) 保存按钮：点击保存按钮，成功保存界面信息至数据库。
 * 2) 重置按钮：点击重置按钮，回到当前界面的初始状态。 3) 取消按钮：点击取消按钮，退出当前界面，返回主界面。 1.6 操作步骤 1.6.1
 * 添加航班信息操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入航班信息管理主界面 【航班列表信息】 2 点击新增按钮，进入新增和修改界面 3
 * 输入航班详细信息，点击保存。 参见业务规则SR-1、SR-2、SR-3、SR-4、SR-5 【航班信息新增/修改信息】 成功保存至数据库 4
 * 返回航班信息管理主界面
 * 
 * 序号 扩展事件 相关数据 备注 3a 取消按钮，退出当前界面，返回主界面
 * 
 * 1.6.2 修改航班信息操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入航班信息管理主界面 【航班列表信息】 2
 * 点击修改按钮，进入新增和修改界面 3 输入航班详细信息，修改航班详细信息,点击保存 参见业务规则SR-1、SR-2、SR-3、SR-4、SR-5
 * 【航班信息新增/修改信息】 成功保存至数据库 4 返回航班信息管理主界面
 * 
 * 序号 扩展事件 相关数据 备注 3a 取消按钮，退出当前界面，返回主界面
 * 
 * 1.6.3 作废航班信息操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入航班信息管理主界面 【航班列表信息】 2
 * 选择一行或者多行记录，点击作废按钮。 弹出确认对话框。 3 点击确定按钮。 成功保存至系统。
 * 
 * 序号 扩展事件 相关数据 备注 2a 取消按钮，退出当前界面，返回主界面
 * 
 * 1.6.4 查询航班信息操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入航班信息管理主界面 【航班列表信息】 2
 * 输入查询条件，点击查询按钮。参见业务规则SR-6,SR-7 【航班信息查询条件】 系统返回查询结果 1.7 业务规则 序号 描述 SR-1
 * 航班号唯一； SR-2 航班类别为下拉框，选项为：早班、中班、晚班； SR-3 航空公司不支持手动输入，只能从航空公司基础资料中手动选取 SR-4
 * 始发站、目的站不支持手动输入，只能从机场基础资料中手动获取。“始发站代码”只读，与“始发站”连动；“目的站代码”只读，与“目的站”连动。 SR-5
 * 飞机机型允许为空，也支持从飞机机型基础资料中选取 SR-6 查询支持模糊查询 SR-7
 * 使用时间控件，可选可手输。计划起飞开始时间不能大于计划起飞结束时间；
 * 
 * 1.8 数据元素 1.8.1 航班信息新增/修改信息 字段名称 说明 输入限制 输入项提示文本 长度 是否必填 备注 航班类别 航班班次 文本 2
 * 是 航班号 航班号 文本 6 是 航空公司 航空公司名称货简称 文本 10 是 始发站 始发站 文本 10 是 目的站 目的站 文本 10 是
 * 计划起飞时间 计划起飞时间 文本 10 是 计划到达时间 计划到达时间 文本 10 是 飞机机型 飞机机型 文本 4 否 班期 航班班期 数字 7
 * 是 始发站代码 始发站三字代码 文本 3 是 目的站代码 目的站三字代码 文本 3 是 航行时间 航班飞行时间 文本 10 是 1.8.2
 * 航班信息列表信息 字段名称 说明 输入限制 长度 是否必填 备注 航班类别 航班班次 N/A 2 N/A 航班号 航班号 N/A 6 N/A
 * 航空公司 航空公司名称简称 N/A 10 N/A 始发站 始发站 N/A 10 N/A 目的站 目的站 N/A 10 N/A 计划起飞时间
 * 计划起飞时间 N/A 4 N/A 计划到达时间 计划到达时间 N/A 4 N/A 飞机机型 飞机机型 N/A 4 N/A 班期 航班班期 N/A
 * 7 N/A 始发站代码 始发站三字代码 N/A 3 N/A 目的站代码 目的站三字代码 N/A 3 N/A 航行时间 航班飞行时间 N/A 10
 * N/A 1.8.3 航班信息查询条件 字段名称 说明 输入限制 长度 是否必填 备注 计划起飞开始时间 计划起飞开始时间 文本 10 否
 * 计划起飞结束时间 计划起飞结束时间 文本 10 否 始发站 始发站 文本 10 否 目的站 目的站 文本 10 否 航班号 航班号 文本 6 否
 * 航班公司 航空公司名称简称 文本 10 否 航班类型 航班班次 文本 2 否 默认为全部
 * 
 * 1.9 非功能性需求 使用量 2012年全网估计用户数 响应要求（如果与全系统要求 不一致的话） 使用时间段 高峰使用时间段
 * 
 * 1.10 接口描述 接口名称 对方系统（外部系统或内部其他模块） 接口描述
 * 
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IFlightDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirportService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFlightService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FlightDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FlightException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 用来操作交互“航班信息”的数据库对应数据访问Service接口实现类：SUC-785
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-11-3 上午10:12:31
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-11-3 上午10:12:31
 * @since
 * @version
 */
public class FlightService implements IFlightService {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(FlightService.class);

    // "航班信息"DAO
    private IFlightDao flightDao;

    // "航空公司"Service
    private IAirlinesService airlinesService;

    // "行政区域"Service
    private IAdministrativeRegionsService administrativeRegionsService;

    // "机场信息"Service
    private IAirportService airportService;

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
     * @throws FlightException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IFlightService#addFlight(com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity,
     *      java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int addFlight(FlightEntity flight, String createUser,
	    boolean ignoreNull) throws FlightException {
	if (null == flight) {
	    throw new FlightException("", "航班信息为空");
	}
	if (StringUtils.isBlank(flight.getFlightNo())) {
	    throw new FlightException("", "航班号为空");
	}
	if (StringUtils.isBlank(createUser)) {
	    throw new FlightException("", "创建人信息为空");
	}

	// 验证数据
//	FlightEntity oldFlight, tempFlight = new FlightEntity();
//	tempFlight.setFlightNo(flight.getFlightNo());
//	oldFlight = flightDao.queryFlightBySelective(tempFlight);

	
//BUG-25673 【空运】联程航班无法录入 BUG-25693   先去掉验证，跟需求商量过，责任先由客户承担
//	if (null != oldFlight) {
//	    throw new FlightException("", "航班信息已经存在");
//	} else {
//	    flight.setCreateUser(createUser);
//	    if (ignoreNull) {
//		flightDao.addFlightBySelective(flight);
//	    } else {
//		flightDao.addFlight(flight);
//	    }
	
	
 //   }
	    
	    
		    flight.setCreateUser(createUser);
		    if (ignoreNull) {
			flightDao.addFlightBySelective(flight);
		    } else {
			flightDao.addFlight(flight);
		    }
	
	return FossConstants.SUCCESS;
    }

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
     * @throws FlightException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IFlightService#deleteFlight(java.util.List,
     *      java.lang.String)
     */
    @Override
    @Transactional
    public int deleteFlight(List<String> ids, String modifyUser)
	    throws FlightException {
	if (CollectionUtils.isEmpty(ids)) {
	    throw new FlightException("", "ID 为空");
	}
	if (StringUtils.isBlank(modifyUser)) {
	    throw new FlightException("", "修改人信息为空");
	}

	for (String id : ids) {
	    // 验证数据
	    FlightEntity oldFlight = queryFlightBySelective(id);
	    if (null == oldFlight) {
		throw new FlightException("", "航班信息不经存在");
	    } else {
		oldFlight.setModifyUser(modifyUser);
		oldFlight.setActive(FossConstants.INACTIVE);
		flightDao.updateFlightBySelective(oldFlight);
	    }
	}
	return FossConstants.SUCCESS;
    }

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
     * @throws FlightException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IFlightService#updateFlight(com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity,
     *      java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int updateFlight(FlightEntity flight, String modifyUser,
	    boolean ignoreNull) throws FlightException {

	if (null == flight) {
	    throw new FlightException("", "航班信息为空");
	}
	if (StringUtils.isBlank(flight.getId())) {
	    throw new FlightException("", "ID 为空");
	}
	if (StringUtils.isBlank(modifyUser)) {
	    throw new FlightException("", "修改人信息为空");
	}

	// 验证数据
	FlightEntity oldFlight, tempFlight = new FlightEntity();
	tempFlight.setFlightNo(flight.getFlightNo());
	oldFlight = flightDao.queryFlightBySelective(tempFlight);
	if (null == oldFlight) {
	    throw new FlightException("", "航班信息不经存在");
	} else {
	    flight.setModifyUser(modifyUser);
	    if (ignoreNull) {
		flightDao.updateFlightBySelective(flight);
	    } else {
		flightDao.updateFlight(flight);
	    }
	}
	return FossConstants.SUCCESS;
    }

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
     * @throws FlightException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IFlightService#queryFlightBySelective(java.lang.String)
     */
    @Override
    public FlightEntity queryFlightBySelective(String id)
	    throws FlightException {
	if (StringUtils.isBlank(id)) {
	    throw new FlightException("", "ID 为空");
	}
	FlightEntity flight = new FlightEntity();
	flight.setId(id);
	return flightDao.queryFlightBySelective(flight);
    }

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
     * @throws FlightException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IFlightService#queryFlightBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity)
     */
    @Override
    public FlightEntity queryFlightBySelective(FlightEntity flight)
	    throws FlightException {
	if (null == flight) {
	    throw new FlightException("", "航班信息参数为空");
	}
	return flightDao.queryFlightBySelective(flight);
    }

    /**
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-27 下午8:51:57
     * @param flight
     * @return
     * @throws FlightException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IFlightService#queryFlightListBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity)
     */
    @Override
    public List<FlightEntity> queryFlightListBySelective(FlightEntity flight)
	    throws FlightException {
	if (null == flight) {
	    flight = new FlightEntity();
	}
	return flightDao.queryFlightListBySelective(flight);
    }

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
     * @throws FlightException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IFlightService#queryFlightListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity,
     *      int, int)
     */
    @Override
    public PaginationDto queryFlightListBySelectiveCondition(
	    FlightEntity flight, int offset, int limit) throws FlightException {
	PaginationDto paginationDto = new PaginationDto();
	if (offset < 0 || limit < 0) {
	    return paginationDto;
	}
	if (null == flight) {
	    flight = new FlightEntity();
	}
	//由于航班信息中始发城市和目的城市只有2个汉字，所以需要对传入的始发和目的城市进行处理
	String departureAirport=null;
	String destinationAirport=null;
	if(StringUtil.isNotEmpty(flight.getDepartureAirport())&& flight.getDepartureAirport().length()>2){
		departureAirport=flight.getDepartureAirport().substring(0, 2);
		flight.setDepartureAirport(departureAirport);
	}
    if(StringUtil.isNotEmpty(flight.getDestinationAirport())&& flight.getDestinationAirport().length()>2){
    	destinationAirport=flight.getDestinationAirport().substring(0, 2);
    	flight.setDestinationAirport(destinationAirport);
	}
	List<FlightEntity> flightList = flightDao
		.queryFlightListBySelectiveCondition(flight, offset, limit);
	if (CollectionUtils.isNotEmpty(flightList)) {
	    Long totalCount = flightDao
		    .queryFlightCountBySelectiveCondition(flight);
	    paginationDto.setPaginationDtos(flightList);
	    paginationDto.setTotalCount(totalCount);
	}
	return paginationDto;
    }

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
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IFlightService#queryFlightDtoListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity,
     *      int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public PaginationDto queryFlightDtoListBySelectiveCondition(
	    FlightEntity flight, int offset, int limit) throws FlightException {
	// 获取数据、处理关联数据
	PaginationDto paginationDto = queryFlightListBySelectiveCondition(
		flight, offset, limit);

	List<FlightEntity> flightList = paginationDto.getPaginationDtos();

	if (CollectionUtils.isEmpty(flightList)) {
	    return paginationDto;
	}

	// 初始化准备数据
	List<FlightDto> flightDtos = new ArrayList<FlightDto>(flightList.size());
	FlightDto flightDto = null;
	AirlinesEntity airlines = null;
	AdministrativeRegionsEntity administrativeRegions = null;
	String originatingCode = null, destinationCode = null;
	AirportEntity tempAirport, airport = new AirportEntity();
	// 执行数据获取
	for (FlightEntity flightEntity : flightList) {
	    if (null != flightEntity) {
		try {
		    flightDto = new FlightDto(flightEntity);
		} catch (FlightException e) {
		    throw new FlightException("", "航班信息Entity数据复制到Dto失败", e);
		}
		// 航空公司
		String airlineCode = flightEntity.getAirlines();
		if (StringUtils.isNotBlank(airlineCode)) {
		    airlines = airlinesService.queryAirlineByCode(airlineCode);
		    if (null != airlines) {
			flightDto.setAirlinesName(airlines.getName());
		    } else {
			LOGGER.warn("数据不完成，航班对应的航空公司不存在");
		    }
		}
		// 始发站
		originatingCode = flightEntity.getOrigCode();
		if (StringUtils.isNotBlank(originatingCode)) {
		    airport.setAirportCode(originatingCode);
		    tempAirport = airportService
			    .queryAirportBySelective(airport);
		    if (null != tempAirport) {
			administrativeRegions = administrativeRegionsService
				.queryAdministrativeRegionsByCode(tempAirport
					.getCityCode());
			if (null != administrativeRegions) {
			    flightDto
				    .setOriginatingStationName(administrativeRegions
					    .getName());
			} else {
			    LOGGER.warn("数据不完成，航班对应的始发站不存在");
			}
		    }
		}
		// 目的站
		destinationCode = flightEntity.getTargetCode();
		if (StringUtils.isNotBlank(destinationCode)) {
		    airport.setAirportCode(destinationCode);
		    tempAirport = airportService
			    .queryAirportBySelective(airport);
		    if (null != tempAirport) {
			administrativeRegions = administrativeRegionsService
				.queryAdministrativeRegionsByCode(tempAirport
					.getCityCode());
			if (null != administrativeRegions) {
			    flightDto
				    .setDestinationStationName(administrativeRegions
					    .getName());
			} else {
			    LOGGER.warn("数据不完成，航班对应的目的站不存在");
			}
		    }
		}
	    }
	    if (null != flightDto) {
		flightDtos.add(flightDto);
	    }
	}
	paginationDto.setPaginationDtos(flightDtos);
	return paginationDto;
    }

    /**
     * @param flightDao
     *            the flightDao to set
     */
    public void setFlightDao(IFlightDao flightDao) {
	this.flightDao = flightDao;
    }

    /**
     * @param airlinesService
     *            the airlinesService to set
     */
    public void setAirlinesService(IAirlinesService airlinesService) {
	this.airlinesService = airlinesService;
    }

    /**
     * @param administrativeRegionsService
     *            the administrativeRegionsService to set
     */
    public void setAdministrativeRegionsService(
	    IAdministrativeRegionsService administrativeRegionsService) {
	this.administrativeRegionsService = administrativeRegionsService;
    }

    /**
     * @param airportService
     *            the airportService to set
     */
    public void setAirportService(IAirportService airportService) {
	this.airportService = airportService;
    }
}
