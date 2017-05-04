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
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/TruckSchedulingService.java
 * 
 *  FILE NAME     :TruckSchedulingService.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.service.impl
 * FILE    NAME: TruckSchedulingService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.service.impl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.scheduling.api.define.ScheduleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingZoneEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckScheduleGridDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.ShortScheduleException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 排班表SERVICE接口实现 *
 * 
 * 短途排班相关操作
 * 
 * 
 * 
 * 接送货排班相关操作
 * 
 * 
 * 
 * 界面主要分为三个部分：排班列表区域、出车详细信息、功能按钮。
 * 
 * 
 * 
 * 
 * 1. 排班列表区域：该区域以列表形式展现，第一行、第二行为该月的日期和周别，
 * 
 * 
 * 
 * 
 * 
 * 
 * 第一列为本小组的司机，交叉点的单元格为该司机在该日的工作内容，包括出车、休息、
 * 
 * 
 * 
 * 
 * 
 * 值班、培训，离岗。该单元格为下拉框形式，下拉选项为：出车、值班、休息、
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 培训、离岗； 2. 出车详细信息：当排班区域列表中的内容为车牌号时，
 * 
 * 
 * 
 * 可在此处输入出车的详细信息，包括车牌号、车型、车辆所属车队、
 * 
 * 
 * 
 * 线路、司机电话、发车时间、班次；
 * 
 * 
 * 
 * 2.1 车牌号：可输入，修改后的值与列表中的车牌号值保持一致，
 * 
 * 
 * 
 * 车牌号来源车辆基础资料；公共选择器，模糊匹配输入时，必须为本部门的车辆。
 * 
 * 
 * 
 * （要系统验证）； 2.2 车型：不可输入，根据车牌号关联车辆基础资料自动读取车型；
 * 
 * 
 * 2.3 车辆所属车队：不可输入，根据车牌号关联车辆基础资料读取车辆所属车队；
 * 
 * 
 * 
 * 2.4 线路：公共选择器，读取自发车标准基础资料；
 * 
 * 
 * 2.5 司机电话：根据列表中的司机关联司机基础资料自动读取；
 * 
 * 
 * 2.6 发车时间：根据输入的线路关联发车标准基础资料自动读取
 * 
 * 
 * ，发车时间和班次是匹配的，来源班车发车时效标准基础资料
 * 
 * 
 * （根据时间前后默认显示对应班次）输入发车时效标准以外的班次时，
 * 
 * 
 * 系统要提示一下，不做强制要求（要验证）；
 * 
 * 
 * 2.7 班次：需要操作人员人工录入，要与发车时间匹配，
 * 
 * 
 * 来源于班车发车时效标准基础资料，输入发车时效表以外的班次时，
 * 
 * 
 * 给出提示，不做强制要求；
 * 
 * 
 * 3. 功能按钮区：按钮包括保存、添加人员、提交至调度；
 * 
 * 
 * 保存：保存修改或者新增的排班信息；
 * 
 * 
 * 添加人员：在排班信息列表中添加一行，可输入其他小组的司机，参见业务规则SR-1；
 * 
 * 
 * 提交至调度：点击此按钮，将本月排班信息提交至调度，参见业务规则SR-4。
 * 
 * 1.6.1 制作短途排班表
 * 
 * 
 * 序号 基本步骤 相关数据 补充步骤
 * 
 * 
 * 1 进入制作短途排班表界面
 * 
 * 
 * 由查询短途排班表用例进入，参见查询短途排班表用例
 * 
 * 
 * 2 在排班信息列表的单元格内输入工作类别
 * 
 * 
 * 输入车牌号时，校验输入的车牌号是否合法，参见业务规则SR-1
 * 
 * 3 输入【出车详细信息】
 * 
 * 
 * 4 点击“添加人员”按钮，输入司机姓名
 * 
 * 本步骤可跳过，也可反复执行，SR-1
 * 
 * 
 * 5 输入【排班信息】，点击“保存”按钮
 * 
 * 保存输入的排班信息
 * 
 * 
 * 6 点击“提交至调度”
 * 
 * 
 * 更开该月的排班信息状态，车队调度可查看到本小组的排班
 * 
 * 序号 扩展事件 相关数据 备注
 * 
 * 
 * 5a 步骤5中，如果排班信息不完整，则弹出“确认/取消”的提示
 * 
 * 
 * ，点击“确认”后保存排班信息
 * 
 * 
 * 提示内容：排班信息不完整，您确定要保存排班信息吗？
 * 
 * 
 * 参见业务规则SR-5
 * 
 * 1.7 业务规则
 * 
 * 
 * 序号 描述
 * 
 * 
 * SR-1 新增某月排班信息时，排班信息列表第一列默认为本小组的司机，
 * 
 * 
 * 
 * 点击“添加人员”可在列表中新增一行，输入其他车队的司机，对该司机进行排班；
 * 
 * 
 * 排班信息列表的单元格内，只能输入属于本车队的当前可用的车辆；
 * 
 * 
 * 当修改当月排班信息时，只有拥有权限的用户(例如车队高级经理)才能添加司机及车辆；
 * 
 * 
 * SR-2 在排班信息列表的单元格内输入车牌号及对应的【出车详细信息】后，
 * 
 * 
 * 复制该单元格内的车牌号，粘帖到本行的其他单元格内时，
 * 
 * 
 * 自动填充单元格对应的【出车详细信息】，内容和已复制的车牌号
 * 
 * 
 * 对应的【出车详细信息】相同；
 * 
 * 
 * 
 * 
 * SR-3 只有排班信息列表中输入合法车牌号后，
 * 
 * 
 * 【出车详细信息】各字段方可输入，且相同日期、
 * 
 * 
 * 相同线路的班次不可重复； SR-4 点击“提交至调度”后，车队调度才可查询到排班信息；
 * 
 * 每个月的排班信息只可“提交至调度”一次，不可重复提交；
 * 
 * 出勤天数：出勤天数（累计）=出车天数+值班天数+培训天数
 * 
 * SR-5 保存前，需要校验本车队所有司机、所有车辆在该月的
 * 
 * 每一天是否都已经进行排班，若不符合此条件，则保存前需提示；
 * 
 * S R-6 可通过车牌号自动获取车型信息，司机电话；根据线路
 * 
 * 和班次获取发车时间
 * 
 * SR-7 当选择接送货排班表时,下方的录入表单包含以下字段：
 * 
 * 车辆所属部门（车队）、车牌号、车型、司机姓名、区域、联系电话、
 * 
 * 工作类别（出车、休息、值班、培训，离岗）、日期；接送货排班，
 * 
 * 
 * 区域字段来源“定人定区基础资料”。接送货排班与短途排班字段有差异。 *
 * 
 * 查询排班：
 * 
 * 界面主要分为三个部分：查询条件，查询结果列表，功能按钮；
 * 
 * 1. 查询条件：排班小组、车牌号、司机、排班日期；
 * 
 * 1.1 排班小组：共用选择框，读取车队基础资料，参见业务规则SR-1；
 * 
 * 1.2 车牌号：共用选择框，读取车辆基础资料；
 * 
 * 1.3 司机：共用选择框，读取司机基础资料；
 * 
 * 1.4 排班日期：排班时指定的日期；
 * 
 * 2. 排班信息列表：数据元素参见【查询结果列表】；
 * 
 * 3. 功能按钮区：按钮包括查询、重置、修改本月排班、制定下月排班；
 * 
 * 查询：点击此按钮发起查询；
 * 
 * 重置：点击此按钮重新初始化【查询条件】，参见业务规则SR-1；
 * 
 * 制定下月排班：点击此按钮打开制定短途排班表界面，参见制定短途排班表用例；
 * 
 * 新增排班导入功能替换此功能；
 * 
 * 修改本月排班修改排班：点击此按钮打开修改短途排班表界面，参见制定短途排班表用例；
 * 
 * 报表式查看：点击此按钮，弹出图2界面，以报表形式显示排班信息，
 * 
 * 图2列表第一行为查询时设置的日期天数，升序排列，第一列为【查询结果列表】
 * 
 * 中的所有司机，纵横交叉点的单元格内显示该司机该天的工作内容，当司机该天出车时
 * 
 * ，单元格内显示车牌号，点击车牌号后，列表下方显示【出车详细信息】，
 * 
 * 具体字段参见制定短途排班表用例。
 * 
 * 下载模板：下载导入排班的模板文件。
 * 
 * 接送货查询排班：
 * 
 * 界面主要分为三个部分：查询条件，查询结果列表，功能按钮；
 * 
 * 1. 查询条件：排班小组、车牌号、司机、排班日期；
 * 
 * 1.1 排班小组：共用选择框，读取车队基础资料，参见业务规则SR-1；
 * 
 * 1.2 车牌号：共用选择框，读取车辆基础资料；
 * 
 * 1.3 司机：共用选择框，读取司机基础资料；
 * 
 * 1.4 排班日期：排班时指定的日期；
 * 
 * 2. 排班信息列表：数据元素参见【查询结果列表】；
 * 
 * 3. 功能按钮区：按钮包括查询、重置、修改本月排班、制定下月排班；
 * 
 * 查询：点击此按钮发起查询；
 * 
 * 重置：点击此按钮重新初始化【查询条件】，参见业务规则SR-1；
 * 
 * 制定下月排班：点击此按钮打开制定短途排班表界面，参见制定短途排班表用例；
 * 
 * 新增排班导入功能替换此功能；
 * 
 * 修改本月排班修改排班：点击此按钮打开修改短途排班表界面，参见制定短途排班表用例；
 * 
 * 报表式查看：点击此按钮，弹出图2界面，以报表形式显示排班信息，
 * 
 * 图2列表第一行为查询时设置的日期天数，升序排列，第一列为【查询结果列表】
 * 
 * 中的所有司机，纵横交叉点的单元格内显示该司机该天的工作内容，当司机该天出车时
 * 
 * ，单元格内显示车牌号，点击车牌号后，列表下方显示【出车详细信息】，
 * 
 * 具体字段参见制定短途排班表用例。
 * 
 * 下载模板：下载导入排班的模板文件。
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-26 下午5:01:19
 */
public class TruckSchedulingService implements ITruckSchedulingService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TruckSchedulingService.class);
	/**
	 * 排班计划Dao
	 */
	ITruckSchedulingDao truckSchedulingDao;
	/**
	 * 排班任务Dao
	 */
	ITruckSchedulingTaskDao truckSchedulingTaskDao;
	/**
	 * 部门Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 线路Service
	 */
	private ILineService lineService;
	
	private IExpressLineService expresslineService;

	/**
	 * 定人定区Service
	 */
	private IRegionalVehicleService regionalVehicleService;
	/**
	 * 自有司机Service
	 */
	private IOwnDriverService ownDriverService;
	/**
	 * 排班任务Service
	 */
	private ITruckSchedulingTaskService truckSchedulingTaskService;
	
	public void setTruckSchedulingTaskService(
			ITruckSchedulingTaskService truckSchedulingTaskService) {
		this.truckSchedulingTaskService = truckSchedulingTaskService;
	}

	/**
	 * 设置 排班计划Dao.
	 * 
	 * @param truckSchedulingDao
	 *            the new 排班计划Dao
	 */
	public void setTruckSchedulingDao(ITruckSchedulingDao truckSchedulingDao) {
		this.truckSchedulingDao = truckSchedulingDao;
	}

	/**
	 * 设置 排班任务Dao.
	 * 
	 * @param truckSchedulingTaskDao
	 *            the new 排班任务Dao
	 */
	public void setTruckSchedulingTaskDao(ITruckSchedulingTaskDao truckSchedulingTaskDao) {
		this.truckSchedulingTaskDao = truckSchedulingTaskDao;
	}

	/**
	 * 设置 部门Service.
	 * 
	 * @param orgAdministrativeInfoService
	 *            the new 部门Service
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 设置 线路Service.
	 * 
	 * @param lineService
	 *            the new 线路Service
	 */
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}

	/**
	 * 设置 定人定区Service.
	 * 
	 * @param regionalVehicleService
	 *            the new 定人定区Service
	 */
	public void setRegionalVehicleService(IRegionalVehicleService regionalVehicleService) {
		this.regionalVehicleService = regionalVehicleService;
	}

	public void setOwnDriverService(IOwnDriverService ownDriverService) {
		this.ownDriverService = ownDriverService;
	}
	/**
	 * 
	 * 
	 * 
	 * 插入计划
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * 
	 * @date 2012-10-26 下午5:01:19
	 * 
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingService#insertTruckScheduling(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@Override
	@Transactional
	public void insertTruckScheduling(TruckSchedulingEntity truckScheduling) {
		// 暂时不启用
	}

	/**
	 * 
	 * 
	 * 
	 * 将计划状态删除为未知状态
	 * 
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * 
	 * 
	 * @date 2012-10-26 下午5:01:19
	 * 
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingService#deleteTruckScheduling(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@Override
	@Transactional
	public void deleteTruckScheduling(TruckSchedulingEntity truckScheduling) {
		// 删除
		truckSchedulingDao.deleteTruckScheduling(truckScheduling);
	}

	/**
	 * 
	 * 
	 * 
	 * 更新计划
	 * 
	 * 
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * 
	 * 
	 * @date 2012-10-26 下午5:01:19
	 * 
	 * 
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingService#updateTruckScheduling(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@Override
	@Transactional
	public void updateTruckScheduling(TruckSchedulingEntity truckScheduling) {
		// 暂时不启用
	}

	/**
	 * 
	 * 
	 * 查询计划列表
	 * 
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * 
	 * 
	 * @date 2012-10-26 下午5:01:19
	 * 
	 * 
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingService#queryTruckScheduling(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@Override
	public List<TruckSchedulingEntity> queryTruckScheduling(TruckSchedulingEntity truckScheduling) {
		// 返回
		return null;
	}

	/**
	 * 
	 * 
	 * 查询计划表头列表
	 * 
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * 
	 * @date 2012-10-26 下午5:01:19
	 * 
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingService#queryTruckSchedulingList(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckScheduleGridDto)
	 */
	@Override
	public List<TruckScheduleGridDto> queryTruckSchedulingList(TruckSchedulingEntity tsEntity, int limit, int start) {
		// 返回
		return truckSchedulingDao.queryTruckSchedulingList(tsEntity, limit, start);
	}

	/**
	 * 查询计划信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 下午5:01:19
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingService#queryTruckSchedulingByParams(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@Override
	public TruckSchedulingEntity queryTruckSchedulingByParams(TruckSchedulingEntity truckScheduling) {
		// 返回
		return null;
	}

	/**
	 * 
	 * 批量插入排班表
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-10-29 上午9:07:32
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingService#batchInsertTruckScheduling(java.util.List)
	 */
	@Override
	@Transactional
	public int batchInsertTruckScheduling(List<TruckSchedulingEntity> list) {
		// 查询
		truckSchedulingDao.batchInsertTruckScheduling(list);
		// 返回
		return 1;

	}

	/**
	 * 
	 * 删除工作类型到“未知”状态
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-6 下午12:58:32
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingService#deleteTruckScheduling(java.util.List)
	 */
	@Override
	public void deleteTruckScheduling(List<String> scheduleIds, CurrentInfo user) {
		// 查询是否存在任务数据
		if (CollectionUtils.isNotEmpty(scheduleIds)) {
			// 待更改为“未知”工作类型的计划ID
			TruckSchedulingEntity truckScheduling = null;
			for (String scheduleId : scheduleIds) {
				truckScheduling = new TruckSchedulingEntity();
				truckScheduling.setId(scheduleId);
				// 查询是否存在任务数据
				Long cnt = truckSchedulingTaskDao.queryTruckSchedulingTaskByScheduleId(truckScheduling);
				//352203
				if (cnt == null || cnt <= 0) {
					if (user != null) {
						// 修改时间
						truckScheduling.setUpdateTime(Calendar.getInstance().getTime());
						// 用户编码
						truckScheduling.setUpdateUserCode(user.getEmpCode());
						// 部门编码
						truckScheduling.setUpdateOrgCode(user.getCurrentDeptCode());
						// 用户姓名
						truckScheduling.setUpdateUserName(user.getEmpName());
						// 如果无任务数据，则执行删除
						truckSchedulingDao.deleteTruckScheduling(truckScheduling);
					}
				}
			}
		}
	}

	/**
	 * 
	 * 
	 * 
	 * 获取线路和车牌信息
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-28 下午3:19:34
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingService#queryLineInfoAndVehicleNo(java.util.List,
	 *      com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@Override
	public void queryLineInfoAndVehicleNo(List<TruckScheduleGridDto> tsDtos, TruckSchedulingEntity tsEntity) {
		if (CollectionUtils.isNotEmpty(tsDtos) && tsEntity != null) {
			// 循环查询线路和车牌信息，以第一条为准
			// 线路
			String lineNameOrArea = StringUtils.EMPTY;
			// 查询结果
			SimpleTruckScheduleDto queryDto;
			for (TruckScheduleGridDto dto : tsDtos) {
				// 司机编码
				tsEntity.setDriverCode(dto.getDriverCode());
				// 查询线路或区域、车牌
				queryDto = truckSchedulingTaskDao.queryLineInfoAndVehicleNo(tsEntity);
				// 判空
				if (queryDto != null) {
					// 车牌号码
					dto.setVehicleNo(queryDto.getVehicleNo());

					// 短途排班，显示线路
					if (ScheduleConstants.SCHEDULE_TYPE_SHORTSCHEDULE.equals(tsEntity.getSchedulingType())) {
						// 查询线路信息
						LineEntity lineInfo = lineService.queryLineByVirtualCode(queryDto.getLineNo());
						if (lineInfo != null) {
							lineNameOrArea = lineInfo.getLineName();
						}else{
							ExpressLineEntity explineInfo  = expresslineService.queryLineByVirtualCode(queryDto.getLineNo());
							if (explineInfo != null) {
								lineNameOrArea = explineInfo.getLineName();
							}
						}
						// 接送货排班，显示区域
					} else {
						// 查询区域对应部门名称
						RegionVehicleEntity areaInfo = regionalVehicleService
								.queryRegionVehicleByVehicleAndVehicleType(queryDto.getVehicleNo());// this.queryDepartment(queryDto.getZoneCode());
						if (areaInfo != null) {
							lineNameOrArea = areaInfo.getRegionName();
						}
					}
					// 线路或区域
					dto.setLineNameOrArea(lineNameOrArea);
				}
				// 司机车队名称
				dto.setDriverOrgName(queryDepartment(dto.getDriverOrgCode()));
			}
		}
	}

	/**
	 * 
	 * 查询部门信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-24 上午11:51:17
	 * 
	 * 
	 */
	@Override
	public String queryDepartment(String code) {
		// 查询部门信息
		LOGGER.info("查询部门信息编码:" + code);
		// 查询部门
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(code);
		// 不为空，则取回部门信息
		if (org != null) {
			// 日志
			LOGGER.info("查询部门信息结果:" + org.getName());
			// 返回
			return org.getName();
		} else {
			// 日志
			LOGGER.info("查询部门信息结果:");
			// 返回
			return null;
		}
	}

	/**
	 * 总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-20 下午7:37:15
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingService#queryTruckSchedulingListTotal(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@Override
	public Long queryTruckSchedulingListTotal(TruckSchedulingEntity tsEntity) {
		// 总条数
		return truckSchedulingDao.queryTruckSchedulingListTotal(tsEntity);
	}

	/**
	 * 导出
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-20 下午7:42:05
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingService#queryTruckSchedulingList(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@Override
	public List<TruckScheduleGridDto> queryTruckSchedulingList(TruckSchedulingEntity tsEntity) {
		// 返回
		return truckSchedulingDao.queryTruckSchedulingList(tsEntity);
	}

	/**
	 * 初始化排班数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-18 下午2:38:31
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingService#initDeaprtDriverScheduling(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@Override
	@Transactional
	public void initDeaprtDriverScheduling(TruckSchedulingEntity schedulingEntity, UserEntity userEntity) {
		List<OwnDriverEntity> driverList = null;
		if (schedulingEntity != null) {
			driverList = queryDepartDrivers(schedulingEntity);
			// 进行初始化
			initShortScheduleList(schedulingEntity, driverList, userEntity);

		}

	}

	/**
	 * 查询司机列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-18 下午2:57:52
	 */
	private List<OwnDriverEntity> queryDepartDrivers(TruckSchedulingEntity schedulingEntity) {
		if (schedulingEntity != null) {
			if (StringUtils.isBlank(schedulingEntity.getSchedulingDepartCode())) {
				throw new ShortScheduleException("排班部门不能为空", "");
			}
			if (StringUtils.isBlank(schedulingEntity.getYearMonth())) {
				throw new ShortScheduleException("排班年月不能为空", "");
			}
			if (StringUtils.isBlank(schedulingEntity.getSchedulingType())) {
				throw new ShortScheduleException("排班类型不能为空", "");
			}
			OwnDriverEntity ownDriver = new OwnDriverEntity();
			// 排班部门
			ownDriver.setOrgId(schedulingEntity.getSchedulingDepartCode());
			// 可用
			ownDriver.setActive(FossConstants.ACTIVE);
			// 查询司机
			return ownDriverService.queryOwnDriverListBySelectiveCondition(ownDriver, ScheduleConstants.ZERO,
					Integer.MAX_VALUE);

		} else {
			return null;
		}
	}

	/**
	 * 查询车队司机，并根据下月的时间 构建司机下月计划基础数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-2 下午2:31:26
	 */
	private void initShortScheduleList(TruckSchedulingEntity schedulingEntity, List<OwnDriverEntity> drivers,
			UserEntity user) throws ShortScheduleException {
		// 初始计划数据列表
		List<TruckSchedulingEntity> tempList = new ArrayList<TruckSchedulingEntity>();
		// 获取当前时间
		Calendar cdr = Calendar.getInstance();
		// 当前时间
		String yearMonth = schedulingEntity.getYearMonth();
		if (StringUtils.isNotBlank(yearMonth)) {
			cdr.setTime(com.deppon.foss.util.DateUtils.convert(yearMonth.concat("-01"),
					com.deppon.foss.util.DateUtils.DATE_FORMAT));

		}
		// 本月最大天数
		int actualMaximum = cdr.getActualMaximum(Calendar.DATE);

		// 根据本车队司机的数量循环构建初始化数据
		if (CollectionUtils.isNotEmpty(drivers)) {
			for (OwnDriverEntity tmpDriver : drivers) {
				if (StringUtils.isBlank(tmpDriver.getEmpPhone())) {
					throw new ShortScheduleException("本部门司机[" + tmpDriver.getEmpName() + "],公司联系电话为空，请先维护", "");
				}
				// 准备某司机的计划数据
				for (int day = 1; day <= actualMaximum; day++) {
					// 动态构建日期时间
					cdr.set(cdr.get(Calendar.YEAR), cdr.get(Calendar.MONTH), day);
					// 开始构建实体

					// 构建需要初始化的排班计划实体
					makeInitTruckSchedulingEntity(cdr, tmpDriver, schedulingEntity, user, tempList);
				}

			}

		} else {
			throw new ShortScheduleException("本部门不存在可用的司机", "");
		}
		// 初始化计划数据
		if (CollectionUtils.isNotEmpty(tempList)) {
			// 批量初始化排班计划
			batchInsertTruckScheduling(tempList);
		}
	}

	/**
	 * 构建需要初始化的排班计划实体
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午8:48:39
	 */
	private void makeInitTruckSchedulingEntity(Calendar cdr, OwnDriverEntity driver,
			TruckSchedulingEntity truckSchedulingEntity, UserEntity user, List<TruckSchedulingEntity> tempList) {
		// 计划实体
		TruckSchedulingEntity scheduleEntity = new TruckSchedulingEntity();
		// 设置UUID
		scheduleEntity.setId(UUIDUtils.getUUID());
		// 司机代码
		scheduleEntity.setDriverCode(driver.getEmpCode());
		// 排班部门编码
		scheduleEntity.setSchedulingDepartCode(truckSchedulingEntity.getSchedulingDepartCode());
		// 司机直属部门代码
		scheduleEntity.setDriverOrgCode(driver.getOrgId());
		// 司机信息获取司机电话
		scheduleEntity.setDriverPhone(driver.getEmpPhone());

		// 工作类别(初始化为未知)
		scheduleEntity.setPlanType(ScheduleConstants.PLAN_TYPE_UNKNOWN);
		// 计划日期
		scheduleEntity.setSchedulingDate(cdr.getTime());
		// 计划状态
		scheduleEntity.setSchedulingStatus(ScheduleConstants.SCHEDULE_STATUS_ACTIVE);
		if (cdr.getTime() != null) {
			String currentTime = com.deppon.foss.util.DateUtils.convert(cdr.getTime(),
					com.deppon.foss.util.DateUtils.DATE_TIME_FORMAT);
			// 日期数字
			scheduleEntity.setDateNum(Integer.parseInt(currentTime.substring(ScheduleConstants.TIME_SUBSTRING_EIGHT,
					ScheduleConstants.TIME_SUBSTRING_TEN)));
			// 年月
			scheduleEntity.setYearMonth(currentTime.substring(ScheduleConstants.TIME_SUBSTRING_ZERO,
					ScheduleConstants.TIME_SUBSTRING_SEVEN));
		}
		// 排班类型（TFR短途PKP接送货）排班
		scheduleEntity.setSchedulingType(truckSchedulingEntity.getSchedulingType());
		// 查询 是否已经初始化
		Long initedTotal = truckSchedulingDao.queryHasInitedList(scheduleEntity);
		// 如果未初始化，则无需初始化本排班类型、司机、车队小组、年月的的排班计划
		if (initedTotal != null
				&& ScheduleConstants.RESULT_INT_VALUE_ZERO.compareTo(initedTotal) == ScheduleConstants.ZERO) {
			// 设置创建信息
			if (user.getEmployee() != null) {
				if (user.getEmployee().getDepartment() != null) {
					scheduleEntity.setCreateOrgCode(user.getEmployee().getDepartment().getCode());
				}
				scheduleEntity.setCreateUserCode(user.getEmployee().getEmpCode());
				scheduleEntity.setCreateUserName(user.getEmployee().getEmpName());
			}
			scheduleEntity.setCreateTime(Calendar.getInstance().getTime());
			// 加入列表
			tempList.add(scheduleEntity);
		}

	}
	/**
	 * 根据已有的排班复制新的排班:
	 *  1、只复制当前月存在的司机排班
	 *  2、只复制当前存在的车辆排班
	 *  3、只复制当前存在的节假日接货区域的排班
	 *  4、当前月比复制的月多于的司机 则 新增当前司机排班
	 *  5、复制的年月没有排班则不复制排班
	 *  6、复制年月与新增的年月相同则不复制排班
	 * @author foss-heyongdong
	 * @date 2015年1月14日 09:23:04
	 * @param tsEntity
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingService#copyTruckScheduling(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 * */
	@Override
	@Transactional
	public void copyTruckScheduling(TruckSchedulingEntity tsEntity) {
		if(tsEntity==null){
			throw new ShortScheduleException("请输入排班条件!", "");
		}
		//排班部门
		String schedulingDepartCode=tsEntity.getSchedulingDepartCode();
		//查询要复制月的司机排班
		TruckSchedulingEntity queryScheduling = new TruckSchedulingEntity();
		queryScheduling.setYearMonth(tsEntity.getCopyYearMonth());
		queryScheduling.setSchedulingType(tsEntity.getSchedulingType());
		queryScheduling.setSchedulingDepartCode(schedulingDepartCode);
		List<TruckScheduleGridDto> tsDtos = this.queryTruckSchedulingList(queryScheduling,Integer.MAX_VALUE,ScheduleConstants.ZERO);
		if(CollectionUtils.isEmpty(tsDtos)){
			throw new ShortScheduleException("复制的年月不存在排班！", "");
		}
		//查询要排班的部门是否存在
		OrgAdministrativeInfoEntity department = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(schedulingDepartCode);
		if(department == null) {
			throw new ShortScheduleException("排班部门不存在!", "");
		}
		if(StringUtils.equals(department.getTransTeam(), FossConstants.NO)) {
			throw new ShortScheduleException("排班部门非车队组, 不能排班!", "");
		}
		List<OwnDriverEntity> driverList = null;
		//查询排班部门司机
		driverList = queryDepartDrivers(queryScheduling);
		//获取当前用户信息
		UserEntity user=FossUserContext.getCurrentUser();
		//复制排班
		copyTruckSchedulingList(tsEntity,driverList,user,tsDtos);
	}

	//复制排班
	private void copyTruckSchedulingList(TruckSchedulingEntity tsEntity,List<OwnDriverEntity> driverList, UserEntity user,List<TruckScheduleGridDto> tsDtos) {
		// 初始计划数据列表
		List<TruckSchedulingEntity> tempList = new ArrayList<TruckSchedulingEntity>();
		//排班任务
		List<TruckSchedulingTaskEntity> taskTempList=new ArrayList<TruckSchedulingTaskEntity>();
		//节假日排班
		List<TruckSchedulingZoneEntity>  zoneTempList = new ArrayList<TruckSchedulingZoneEntity>();
		// 获取当前时间
		Calendar cdr = Calendar.getInstance();
		// 当前时间
		String yearMonth = tsEntity.getYearMonth();
		if (StringUtils.isNotBlank(yearMonth)) {
			cdr.setTime(com.deppon.foss.util.DateUtils.convert(yearMonth.concat("-01"),
					com.deppon.foss.util.DateUtils.DATE_FORMAT));

		}
		// 本月最大天数
		int actualMaximum = cdr.getActualMaximum(Calendar.DATE);

		// 根据本车队司机的数量循环构建初始化数据
		if (CollectionUtils.isNotEmpty(driverList)) {
			for (OwnDriverEntity tmpDriver : driverList) {
				if (StringUtils.isBlank(tmpDriver.getEmpPhone())) {
					throw new ShortScheduleException("本部门司机[" + tmpDriver.getEmpName() + "],公司联系电话为空，请先维护", "");
				}
				// 准备某司机的计划数据
				for (int day = 1; day <= actualMaximum; day++) {
					// 动态构建日期时间
					cdr.set(cdr.get(Calendar.YEAR), cdr.get(Calendar.MONTH), day);
					// 开始构建实体

					// 构建需要初始化的排班计划实体
					makeInitTruckSchedulingEntity(cdr, day,tmpDriver, tsEntity, user, tempList,taskTempList,zoneTempList,tsDtos);
				}

			}

		} else {
			throw new ShortScheduleException("本部门不存在可用的司机", "");
		}
		if(CollectionUtils.isNotEmpty(tempList)){
			batchInsertTruckScheduling(tempList);
		}
		if(CollectionUtils.isNotEmpty(taskTempList)){
			truckSchedulingTaskDao.batchInsertTruckSchedulingTask(taskTempList);
		}
		if(CollectionUtils.isNotEmpty(zoneTempList)){
			truckSchedulingTaskDao.addTaskspkpArea(zoneTempList);
		}
	}
	
	//初始化排班
	private void makeInitTruckSchedulingEntity(
			Calendar cdr,                   int day,      OwnDriverEntity                 tmpDriver, 
			TruckSchedulingEntity           tsEntity,     UserEntity                      user, 
			List<TruckSchedulingEntity>     tempList,     List<TruckSchedulingTaskEntity> taskTempList,
			List<TruckSchedulingZoneEntity> zoneTempList, List<TruckScheduleGridDto>      tsDtos) {
		
		// 计划实体
		TruckSchedulingEntity scheduleEntity = new TruckSchedulingEntity();
		// 设置UUID
		scheduleEntity.setId(UUIDUtils.getUUID());
		// 司机代码
		String driverCode=tmpDriver.getEmpCode();
		scheduleEntity.setDriverCode(driverCode);
		// 排班部门编码
		String schedulingDepartCode=tsEntity.getSchedulingDepartCode();
		scheduleEntity.setSchedulingDepartCode(schedulingDepartCode);
		// 司机直属部门代码
		scheduleEntity.setDriverOrgCode(tmpDriver.getOrgId());
		// 司机信息获取司机电话
		scheduleEntity.setDriverPhone(tmpDriver.getEmpPhone());
		boolean beExsit=false;
		for(TruckScheduleGridDto temp:tsDtos){
			if(StringUtils.equals(driverCode, temp.getDriverCode())){
				
				String planType=matchDayStatu(day,temp);
				
				if(StringUtils.isNotEmpty(planType)){
					// 工作类别(初始化为未知)
					scheduleEntity.setPlanType(planType);
					/*如果当天为WORK则复制排班任务
					 * 1、查询排班任务
					 * 2、复制排班任务
					 * 3、查看是否有节假日排班，有则复制无则不做操作
					*/
					initSchedulingTask(tmpDriver,tsEntity, planType,day,user,scheduleEntity.getId(),taskTempList,zoneTempList);
					beExsit=true;
					break;
				}
			}
		}
		if(!beExsit){
			// 工作类别(初始化为未知)
			scheduleEntity.setPlanType(ScheduleConstants.PLAN_TYPE_UNKNOWN);
		}
		// 计划日期
		scheduleEntity.setSchedulingDate(cdr.getTime());
		// 计划状态
		scheduleEntity.setSchedulingStatus(ScheduleConstants.SCHEDULE_STATUS_ACTIVE);
		if (cdr.getTime() != null) {
			String currentTime = com.deppon.foss.util.DateUtils.convert(cdr.getTime(),
					com.deppon.foss.util.DateUtils.DATE_TIME_FORMAT);
			// 日期数字
			scheduleEntity.setDateNum(Integer.parseInt(currentTime.substring(ScheduleConstants.TIME_SUBSTRING_EIGHT,
					ScheduleConstants.TIME_SUBSTRING_TEN)));
			// 年月
			scheduleEntity.setYearMonth(currentTime.substring(ScheduleConstants.TIME_SUBSTRING_ZERO,
					ScheduleConstants.TIME_SUBSTRING_SEVEN));
		}
		// 排班类型（TFR短途PKP接送货）排班
		scheduleEntity.setSchedulingType(tsEntity.getSchedulingType());
		// 查询 是否已经初始化
		Long initedTotal = truckSchedulingDao.queryHasInitedList(scheduleEntity);
		// 如果未初始化，则无需初始化本排班类型、司机、车队小组、年月的的排班计划
		if (initedTotal != null
				&& ScheduleConstants.RESULT_INT_VALUE_ZERO.compareTo(initedTotal) == ScheduleConstants.ZERO) {
			// 设置创建信息
			if (user.getEmployee() != null) {
				if (user.getEmployee().getDepartment() != null) {
					scheduleEntity.setCreateOrgCode(user.getEmployee().getDepartment().getCode());
				}
				scheduleEntity.setCreateUserCode(user.getEmployee().getEmpCode());
				scheduleEntity.setCreateUserName(user.getEmployee().getEmpName());
			}
			scheduleEntity.setCreateTime(Calendar.getInstance().getTime());
			// 加入列表
			tempList.add(scheduleEntity);
		}

		
	}
	
	/**
	 * 根据天数获取当天的任务类型
	 * */
	private String matchDayStatu(int day,TruckScheduleGridDto tGridDto){
		if(day==1){
			return tGridDto.getDate1();
		}else if(day==2){
			return tGridDto.getDate2();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_3){
			return tGridDto.getDate3();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_4){
			return tGridDto.getDate4();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_5){
			return tGridDto.getDate5();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_6){
			return tGridDto.getDate6();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_7){
			return tGridDto.getDate7();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_8){
			return tGridDto.getDate8();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_9){
			return tGridDto.getDate9();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_10){
			return tGridDto.getDate10();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_11){
			return tGridDto.getDate11();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_12){
			return tGridDto.getDate12();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_13){
			return tGridDto.getDate13();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_14){
			return tGridDto.getDate14();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_15){
			return tGridDto.getDate15();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_16){
			return tGridDto.getDate16();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_17){
			return tGridDto.getDate17();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_18){
			return tGridDto.getDate18();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_19){
			return tGridDto.getDate19();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_20){
			return tGridDto.getDate20();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_21){
			return tGridDto.getDate21();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_22){
			return tGridDto.getDate22();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_23){
			return tGridDto.getDate23();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_24){
			return tGridDto.getDate24();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_25){
			return tGridDto.getDate25();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_26){
			return tGridDto.getDate26();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_27){
			return tGridDto.getDate27();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_28){
			return tGridDto.getDate28();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_29){
			return tGridDto.getDate29();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_30){
			return tGridDto.getDate30();
		}else if(day==ConstantsNumberSonar.SONAR_NUMBER_31){
			return tGridDto.getDate31();
		}
		
		return null;
	}
	
	/**
	 * 复制排班任务和节假日接货区域
	 * */
	private String initSchedulingTask(
			OwnDriverEntity  tmpDriver, TruckSchedulingEntity   tsEntity,
			String planType, int   day,  UserEntity              user,
			String  schedulingId, List<TruckSchedulingTaskEntity> taskTempList,
			List<TruckSchedulingZoneEntity> zoneTempList){

		if(StringUtils.equals(planType, ScheduleConstants.PLAN_TYPE_WORK)){
			SimpleTruckScheduleDto dto=new SimpleTruckScheduleDto();
			dto.setDriverCode(tmpDriver.getEmpCode());
			String copyYm=tsEntity.getCopyYearMonth();
			if(day<ConstantsNumberSonar.SONAR_NUMBER_10){
				copyYm=copyYm+"-0"+day;
			}else{
				copyYm=copyYm+"-"+day;
			}
			dto.setYmd(copyYm);
			dto.setSchedulingtype(tsEntity.getSchedulingType());
			dto.setSchedulingStatus(FossConstants.YES);
			dto.setSchedulingDepartCode(tsEntity.getSchedulingDepartCode());
			//查询该司机当天的排班
			TruckSchedulingEntity entity =truckSchedulingDao.queryTruckSchedulingByParams(dto);
			//查询排班任务条件
			SimpleTruckScheduleDto sDto= new SimpleTruckScheduleDto();
			sDto.setScheduleId(entity.getId());
			//查询排班通过id
			List<TruckSchedulingTaskEntity> tasks =truckSchedulingTaskDao.queryTruckSchedulingTask(sDto);
			if(CollectionUtils.isEmpty(tasks)||tasks.size()<=0){
				return "Failure";
			}
			
			String createUserCode =user.getEmployee().getEmpCode();
			String createUserName =user.getEmployee().getEmpName();
			String createOrgCode  =user.getEmployee().getDepartment().getCode();	
			//循环更新信息
			for(int i=0;i<tasks.size();i++){
				//原有排班任务id
				String scheduleTaskId = tasks.get(i).getId();
				//设置id
				tasks.get(i).setId(UUIDUtils.getUUID());
				SimpleTruckScheduleDto vehicleInfo=truckSchedulingTaskService.queryCarInfoByVehicleNo(tasks.get(i).getVehicleNo());
				//如果车牌号不存在则不复制
				if(vehicleInfo==null){
					continue;
				}
				tasks.get(i).setSchedulingId(schedulingId);//设置排班id
				tasks.get(i).setCreateUserCode(createUserCode);//设置创建人编码
				tasks.get(i).setCreateUserName(createUserName);//设置创建人
				tasks.get(i).setCreateOrgCode(createOrgCode);//设置创建人部门
				tasks.get(i).setCreateTime(new Date());//创建时间
				tasks.get(i).setUpdateOrgCode(null);//更新信息设置为空
				tasks.get(i).setUpdateUserName(null);
				tasks.get(i).setUpdateUserCode(null);
				tasks.get(i).setUpdateTime(null);
				//添加实体
				taskTempList.add(tasks.get(i));
				//查询是否有节假日
				SimpleTruckScheduleDto pkpParam= new SimpleTruckScheduleDto();
				pkpParam.setScheduleTaskId(scheduleTaskId);
				List<TruckSchedulingZoneEntity> pkpZones=	truckSchedulingTaskDao.queryTruckSchedulingPkpAreaInfo(pkpParam);
				if(CollectionUtils.isNotEmpty(pkpZones)||pkpZones.size()>=1){
					//重新设置信息
					for(int n=0;n<pkpZones.size();n++){
						pkpZones.get(n).setId(UUIDUtils.getUUID());//设置id
						pkpZones.get(n).setTruckSchedulingTaskId(tasks.get(i).getId());//设置排班任务id
						pkpZones.get(n).setCreateUserName(createUserName);//设置创建人
						pkpZones.get(n).setCreateUserCode(createUserCode);//设置创建人工号
						pkpZones.get(n).setCreateTime(new Date());//设置创建时间
						//添加节假日排班实体
						zoneTempList.add(pkpZones.get(n));
					}
				}
			}
		}else{
			return "Failure";	
		}
		return "Success";
	}
}