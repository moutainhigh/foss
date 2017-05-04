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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/TruckSchedulingTaskService.java
 * 
 *  FILE NAME     :TruckSchedulingTaskService.java
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
 * FILE    NAME: TruckSchedulingTaskService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOrgService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnDriverException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonOrgVo;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IEditVechileSchedulingService;
import com.deppon.foss.module.transfer.scheduling.api.define.ScheduleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingZoneEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ScheduleExcelDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckSchedulingDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckSchedulingTaskDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.ShortScheduleException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.scheduling.server.action.TruckDepartPlanAction;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 排班任务Service *
 * 
 * 短途排班相关操作
 * 
 * 接送货排班相关操作
 * 
 * 界面主要分为三个部分：排班列表区域、出车详细信息、功能按钮。
 * 
 * 1. 排班列表区域：该区域以列表形式展现，第一行、第二行为该月的日期和周别，
 * 
 * 第一列为本小组的司机，交叉点的单元格为该司机在该日的工作内容，包括出车、休息、
 * 
 * 值班、培训，离岗。该单元格为下拉框形式，下拉选项为：出车、值班、休息、
 * 
 * 培训、离岗； 2. 出车详细信息：当排班区域列表中的内容为车牌号时，
 * 
 * 可在此处输入出车的详细信息，包括车牌号、车型、车辆所属车队、
 * 
 * 线路、司机电话、发车时间、班次；
 * 
 * 2.1 车牌号：可输入，修改后的值与列表中的车牌号值保持一致，
 * 
 * 车牌号来源车辆基础资料；公共选择器，模糊匹配输入时，必须为本部门的车辆。
 * 
 * （要系统验证）； 2.2 车型：不可输入，根据车牌号关联车辆基础资料自动读取车型；
 * 
 * 2.3 车辆所属车队：不可输入，根据车牌号关联车辆基础资料读取车辆所属车队；
 * 
 * 4 线路：公共选择器，读取自发车标准基础资料；
 * 
 * 2.5 司机电话：根据列表中的司机关联司机基础资料自动读取；
 * 
 * 2.6 发车时间：根据输入的线路关联发车标准基础资料自动读取
 * 
 * ，发车时间和班次是匹配的，来源班车发车时效标准基础资料
 * 
 * （根据时间前后默认显示对应班次）输入发车时效标准以外的班次时，
 * 
 * 系统要提示一下，不做强制要求（要验证）；
 * 
 * 2.7 班次：需要操作人员人工录入，要与发车时间匹配，
 * 
 * 来源于班车发车时效标准基础资料，输入发车时效表以外的班次时，
 * 
 * 给出提示，不做强制要求；
 * 
 * 3. 功能按钮区：按钮包括保存、添加人员、提交至调度；
 * 
 * 保存：保存修改或者新增的排班信息；
 * 
 * 添加人员：在排班信息列表中添加一行，可输入其他小组的司机，参见业务规则SR-1；
 * 
 * 提交至调度：点击此按钮，将本月排班信息提交至调度，参见业务规则SR-4。
 * 
 * 1.6.1 制作短途排班表
 * 
 * 序号 基本步骤 相关数据 补充步骤
 * 
 * 1 进入制作短途排班表界面
 * 
 * 由查询短途排班表用例进入，参见查询短途排班表用例
 * 
 * 2 在排班信息列表的单元格内输入工作类别
 * 
 * 输入车牌号时，校验输入的车牌号是否合法，参见业务规则SR-1
 * 
 * 3 输入【出车详细信息】
 * 
 * 4 点击“添加人员”按钮，输入司机姓名
 * 
 * 本步骤可跳过，也可反复执行，SR-1
 * 
 * 5 输入【排班信息】，点击“保存”按钮
 * 
 * 保存输入的排班信息
 * 
 * 6 点击“提交至调度”
 * 
 * 更开该月的排班信息状态，车队调度可查看到本小组的排班
 * 
 * 序号 扩展事件 相关数据 备注
 * 
 * 5a 步骤5中，如果排班信息不完整，则弹出“确认/取消”的提示
 * 
 * ，点击“确认”后保存排班信息
 * 
 * 提示内容：排班信息不完整，您确定要保存排班信息吗？
 * 
 * 参见业务规则SR-5
 * 
 * 1.7 业务规则
 * 
 * 序号 描述
 * 
 * SR-1 新增某月排班信息时，排班信息列表第一列默认为本小组的司机，
 * 
 * 点击“添加人员”可在列表中新增一行，输入其他车队的司机，对该司机进行排班；
 * 
 * 排班信息列表的单元格内，只能输入属于本车队的当前可用的车辆；
 * 
 * 当修改当月排班信息时，只有拥有权限的用户(例如车队高级经理)才能添加司机及车辆；
 * 
 * SR-2 在排班信息列表的单元格内输入车牌号及对应的【出车详细信息】后，
 * 
 * 复制该单元格内的车牌号，粘帖到本行的其他单元格内时，
 * 
 * 自动填充单元格对应的【出车详细信息】，内容和已复制的车牌号
 * 
 * 对应的【出车详细信息】相同；
 * 
 * SR-3 只有排班信息列表中输入合法车牌号后，
 * 
 * 【出车详细信息】各字段方可输入，且相同日期、
 * 
 * 相同线路的班次不可重复； SR-4 点击“提交至调度”后，车队调度才可查询到排班信息；
 * 
 * 
 * 每个月的排班信息只可“提交至调度”一次，不可重复提交；
 * 
 * 出勤天数：出勤天数（累计）=出车天数+值班天数+培训天数
 * 
 * SR-5 保存前，需要校验本车队所有司机、所有车辆在该月的
 * 
 * 每一天是否都已经进行排班，若不符合此条件，则保存前需提示； S R-6 可通过车牌号自动获取车型信息，司机电话；根据线路
 * 
 * 和班次获取发车时间
 * 
 * SR-7 当选择接送货排班表时,下方的录入表单包含以下字段：
 * 
 * 车辆所属部门（车队）、车牌号、车型、司机姓名、区域、联系电话、
 * 
 * 工作类别（出车、休息、值班、培训，离岗）、日期；接送货排班，
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
 * @date 2012-10-31 上午11:00:39
 */
public class TruckSchedulingTaskService implements ITruckSchedulingTaskService {

	/**
	 * 日志管理器
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TruckDepartPlanAction.class);
	/**
	 * 排班任务Dao注入
	 */
	private ITruckSchedulingTaskDao truckSchedulingTaskDao;
	/**
	 * 排班数据dao
	 */
	private ITruckSchedulingDao truckSchedulingDao;
	/**
	 * 排班数据dao
	 */
	private ITruckSchedulingService truckSchedulingService;
	/**
	 * 查询线路发车时间Service
	 */
	private IDepartureStandardService departureStandardService;
	/**
	 * 查询司机信息Service
	 */
	private IOwnDriverService ownDriverService;
	/**
	 * 查询车辆信息Service
	 */
	private IVehicleService vehicleService;
	/**
	 * 定人定区Service
	 */
	private IRegionalVehicleService regionalVehicleService;
	/**
	 * 线路Service
	 */
	private ILineService lineService;

	private IEditVechileSchedulingService editVechileSchedulingService;

	private IExpressLineService expresslineService;

	/**
	 * 组织Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	private ICommonOrgService commonOrgService;

	public ICommonOrgService getCommonOrgService() {
		return commonOrgService;
	}

	public void setCommonOrgService(ICommonOrgService commonOrgService) {
		this.commonOrgService = commonOrgService;
	}

	public void setEditVechileSchedulingService(
			IEditVechileSchedulingService editVechileSchedulingService) {
		this.editVechileSchedulingService = editVechileSchedulingService;
	}

	/**
	 * 设置 排班任务Dao注入.
	 * 
	 * @param truckSchedulingTaskDao
	 *            the new 排班任务Dao注入
	 */
	public void setTruckSchedulingTaskDao(
			ITruckSchedulingTaskDao truckSchedulingTaskDao) {
		this.truckSchedulingTaskDao = truckSchedulingTaskDao;
	}

	/**
	 * 设置 排班数据dao.
	 * 
	 * @param truckSchedulingDao
	 *            the new 排班数据dao
	 */
	public void setTruckSchedulingDao(ITruckSchedulingDao truckSchedulingDao) {
		this.truckSchedulingDao = truckSchedulingDao;
	}

	/**
	 * 设置 查询线路发车时间Service.
	 * 
	 * @param departureStandardService
	 *            the new 查询线路发车时间Service
	 */
	public void setDepartureStandardService(
			IDepartureStandardService departureStandardService) {
		this.departureStandardService = departureStandardService;
	}

	/**
	 * 设置 查询司机信息Service.
	 * 
	 * @param ownDriverService
	 *            the new 查询司机信息Service
	 */
	public void setOwnDriverService(IOwnDriverService ownDriverService) {
		this.ownDriverService = ownDriverService;
	}

	/**
	 * 设置 查询车辆信息Service.
	 * 
	 * @param vehicleService
	 *            the new 查询车辆信息Service
	 */
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	/**
	 * 设置 排班数据dao.
	 * 
	 * @param truckSchedulingService
	 *            the new 排班数据dao
	 */
	public void setTruckSchedulingService(
			ITruckSchedulingService truckSchedulingService) {
		this.truckSchedulingService = truckSchedulingService;
	}

	/**
	 * 设置 定人定区Service.
	 * 
	 * @param regionalVehicleService
	 *            the new 定人定区Service
	 */
	public void setRegionalVehicleService(
			IRegionalVehicleService regionalVehicleService) {
		this.regionalVehicleService = regionalVehicleService;
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

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 新增任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午11:00:40
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#insertTruckSchedulingTask(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity)
	 */
	@Override
	@Transactional
	public void insertTruckSchedulingTask(TruckSchedulingTaskEntity entity)
			throws ShortScheduleException {
		truckSchedulingTaskDao.insertTruckSchedulingTask(entity);
	}

	/**
	 * 查询任务单表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午11:00:40
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#queryTruckSchedulingTask(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity)
	 */
	@Override
	public SimpleTruckScheduleDto queryTruckSchedulingTask(
			TruckSchedulingTaskEntity entity) throws ShortScheduleException {

		return truckSchedulingTaskDao.queryTruckSchedulingTask(entity);
	}

	/**
	 * 联合排班计划、排班任务多表查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午11:00:40
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#querySchedulingAndTask(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@Override
	public List<SimpleTruckScheduleDto> querySchedulingAndTask(
			SimpleTruckScheduleDto dto) throws ShortScheduleException {

		return truckSchedulingTaskDao.querySchedulingAndTask(dto);
	}

	/**
	 * 更新任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午11:00:40
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#updateTruckSchedulingTask(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity)
	 */
	@Override
	@Transactional
	public void updateTruckSchedulingTask(TruckSchedulingTaskEntity entity)
			throws ShortScheduleException {
		truckSchedulingTaskDao.updateTruckSchedulingTask(entity);
	}

	/**
	 * 删除任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午11:00:40
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#deleteTruckSchedulingTask(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity)
	 */
	@Override
	@Transactional
	public void deleteTruckSchedulingTask(TruckSchedulingTaskEntity entity)
			throws ShortScheduleException {
		truckSchedulingTaskDao.deleteTruckSchedulingTask(entity);
	}

	/**
	 * 分页查询联合排班计划、排班任务多表查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 下午3:14:23
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#querySchedulingAndTask(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto,
	 *      int, int)
	 */
	@Override
	public List<SimpleTruckScheduleDto> querySchedulingAndTask(
			SimpleTruckScheduleDto simDto, int limit, int start)
			throws ShortScheduleException {
		// 查询的部门编码列表
		simDto.setDepartmentCodes(queryParentChildrenOrg(simDto));
		// 清除查询条件车队小组（排班车队）
		simDto.setSchedulingDepartCode(null);
		LOGGER.info("排班查询,根据子孙节点查询排班表");
		// 执行查询
		List<SimpleTruckScheduleDto> tempList = truckSchedulingTaskDao
				.querySchedulingAndTaskByWork(simDto, limit, start);
		if (CollectionUtils.isNotEmpty(tempList)) {
			// 线路信息
			LineEntity lineInfo = null;
			// 定人定区
			RegionVehicleEntity regionVehicleDto = null;
			RegionVehicleEntity deliveryRegionVehicleDto = null;
			// 车辆信息
			SimpleTruckScheduleDto carInfoDto = null;
			for (SimpleTruckScheduleDto dto : tempList) {
				// 根据车牌获取区域和车队信息
				if (StringUtils.isNotBlank(dto.getVehicleNo())) {
					// 查询区域信息
					regionVehicleDto = queryZoneCodeByVehicleNo(dto
							.getVehicleNo());
					if (regionVehicleDto != null
							&& StringUtils.isNotBlank(regionVehicleDto
									.getRegionName())) {
						dto.setZoneName(regionVehicleDto.getRegionName());
					}
					deliveryRegionVehicleDto = queryDeliveryCodeByVehicleNo(dto
							.getVehicleNo());
					if (deliveryRegionVehicleDto != null
							&& StringUtils.isNotBlank(deliveryRegionVehicleDto
									.getRegionName())) {
						dto.setDeliveryAreaName(deliveryRegionVehicleDto
								.getRegionName());
					}
					// 查询车辆所属车队信息
					carInfoDto = this.queryCarInfoByVehicleNo(dto
							.getVehicleNo());
					if (carInfoDto != null) {
						dto.setCarOwnerName(carInfoDto.getCarOwnerName());
						// 车型编码
						dto.setTruckModel(carInfoDto.getTruckModel());
						// 车型名
						dto.setTruckModelValue(carInfoDto.getTruckModelValue());
					}

				}// 带货部门
				if (StringUtils.isNotBlank(dto.getTakeGoodsDepartment())) {
					String name = truckSchedulingTaskDao
							.searchOrgInfoByCode(dto.getTakeGoodsDepartment());
					if (StringUtils.isNotEmpty(name)) {
						dto.setTakeGoodsDepartmentName(name);
					}

				}// 转货部门
				if (StringUtils.isNotBlank(dto.getTransferGoodsDepartment())) {
					String name1 = truckSchedulingTaskDao
							.searchOrgInfoByCode(dto
									.getTransferGoodsDepartment());
					if (StringUtils.isNotEmpty(name1)) {
						dto.setTransferGoodsDepartmentName(name1);
					}
				}
				// 查询线路信息
				if (StringUtils.isNotBlank(dto.getLineNo())) {
					// 根据保存的徐牛编码查询线路信息
					lineInfo = lineService.queryLineByVirtualCode(dto
							.getLineNo());
					if (lineInfo != null) {
						dto.setLineName(lineInfo.getLineName());
					} else {
						ExpressLineEntity explineInfo = expresslineService
								.queryLineByVirtualCode(dto.getLineNo());
						if (explineInfo != null) {
							dto.setLineName(explineInfo.getLineName());
						}
					}
				}

			}
		}
		return tempList;
	}

	/**
	 * 查询父节点及其子部门列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-7 下午5:18:36
	 */
	private List<String> queryParentChildrenOrg(SimpleTruckScheduleDto simDto) {
		// 返回结果
		List<String> reStr = null;
		// 部门编码列表
		Map<String, String> departCodes = new HashMap<String, String>();
		// 查询父节点
		if (simDto != null
				&& StringUtils.isNotBlank(simDto.getSchedulingDepartCode())) {
			// 默认的本节点加入
			departCodes.put(simDto.getSchedulingDepartCode(),
					simDto.getSchedulingDepartCode());
			// 当前部门
			OrgAdministrativeInfoEntity currentOrg = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(simDto
							.getSchedulingDepartCode());
			// 车队调度组
			if (currentOrg != null
					&& ScheduleConstants.DISPATCH_TEAM_Y.equals(currentOrg
							.getDispatchTeam())) {
				// 父节点
				OrgAdministrativeInfoEntity parentOrg = orgAdministrativeInfoComplexService
						.getTopFleetByCode(simDto.getSchedulingDepartCode());
				// 父节点取子孙节点
				if (parentOrg != null
						&& StringUtils.isNotBlank(parentOrg.getCode())) {
					// 父节点
					departCodes.put(parentOrg.getCode(), parentOrg.getCode());
					// 父节点的子孙节点
					List<OrgAdministrativeInfoEntity> tempOrgs = orgAdministrativeInfoComplexService
							.queryOrgAdministrativeInfoEntityAllSubByCode(parentOrg
									.getCode());
					// 循环获取并加入
					if (CollectionUtils.isNotEmpty(tempOrgs)) {
						for (OrgAdministrativeInfoEntity o : tempOrgs) {
							// 加入列表
							departCodes.put(o.getCode(), o.getCode());
						}
					}
				}
			}
		}
		// 转换
		if (departCodes != null) {
			reStr = new ArrayList<String>(departCodes.keySet());
		}
		return reStr;
	}

	/**
	 * 查询总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 下午3:14:23
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#queryCount(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@Override
	public Long queryCount(SimpleTruckScheduleDto simDto)
			throws ShortScheduleException {
		return truckSchedulingTaskDao.queryCount(simDto);
	}

	/**
	 * 批量导入排班任务表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-3 下午12:49:49
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#batchImportTaskList(java.util.List)
	 */
	@Override
	@Transactional
	public void batchImportTaskList(List<ScheduleExcelDto> excelDtos,
			Map<String, DriverAssociationDto> existDriverMap,
			TruckSchedulingEntity truckSchedulingEntity, UserEntity user)
			throws ShortScheduleException {
		LOGGER.info("批量导入排班任务表");
		// 根据司机、年月、车队代码，初始化本月的计划数据，以待后面update工作类别
		List<TruckSchedulingEntity> initScheduleList = initShortScheduleList(
				truckSchedulingEntity, existDriverMap, user);
		// 初始化计划数据
		if (CollectionUtils.isNotEmpty(initScheduleList)) {
			// 校验计划数据
			validateTruckSchedulingEntity(initScheduleList);
			// 批量初始化排班计划
			truckSchedulingService.batchInsertTruckScheduling(initScheduleList);
		}
		// 初始化排班计划数据List(只会修改工作类别)
		List<TruckSchedulingDto> impScheduleList = new ArrayList<TruckSchedulingDto>();
		// 初始化排班任务数据List
		List<TruckSchedulingTaskDto> impTaskList = new ArrayList<TruckSchedulingTaskDto>();

		// 根据EXCEL的数据，构建创建 排班计划数据 List,同时构建相应的排班计划任务List
		makeImpScheduleList(excelDtos, existDriverMap, truckSchedulingEntity,
				user, impScheduleList, impTaskList);

		// 更新计划数据(只会修改工作类别)
		if (CollectionUtils.isNotEmpty(impScheduleList)) {
			// 在初始化的前提下，更新排班计划数据(只会修改工作类别)
			truckSchedulingDao.batchUpdateTruckScheduling(impScheduleList);
			// 在排班计划数据列表中，找到非工作状态的排班计划数据，并批量删除
			makeUnWorkTruckSchedulingDtoList(impScheduleList);
		}
		// 初始化的未重复的排班任务数据List
		List<TruckSchedulingTaskDto> unDuplicateimpTaskList = new ArrayList<TruckSchedulingTaskDto>();
		// 根据前面导入的创建的排班任务信息，插入或更新排班任务
		if (CollectionUtils.isNotEmpty(impTaskList)) {
			// 重复的任务列表
			List<ScheduleExcelDto> tempList = null;
			// 查询重复任务的查询条件
			SimpleTruckScheduleDto simDto = null;
			// 校验当天、同一线路班次是否有重复
			for (TruckSchedulingTaskDto tempTask : impTaskList) {
				simDto = new SimpleTruckScheduleDto();
				// 创建查询条件
				makeSearchCondition(tempTask, simDto);
				// 如果是短途排班, 查询校验当天、同一线路班次是否有重复
				if (ScheduleConstants.SCHEDULE_TYPE_SHORTSCHEDULE
						.equals(tempTask.getSchedulingType())) {
					tempList = queryHasLineNoFrequencyNoCurrentDay(simDto);
				}
				// 如果是接送货排班
				else {
					// 查询接送货排班，车队小组、当天、司机、车是否已经安排过
					tempList = truckSchedulingTaskDao
							.queryPKPDuliVehicleCurrentDay(simDto);
				}

				// 如果不为空，则表示存在重复，则需要更新，否则加入直接待新增列表
				if (CollectionUtils.isNotEmpty(tempList)) {
					// 获取库里的ID值作为更新条件
					tempTask.setId(tempList.get(0).getScheduleTaskId());
					truckSchedulingTaskDao.updateTruckSchedulingTask(tempTask);
				} else {
					// 如果车牌号相同，则允许排班,支持同一天、同一线路、同一车牌可以有多个司机排班
					unDuplicateimpTaskList.add(tempTask);
				}
			}
			// 非重复的计划任务
			if (CollectionUtils.isNotEmpty(unDuplicateimpTaskList)) {
				truckSchedulingTaskDao.batchImportTruckSchedulingTask(unDuplicateimpTaskList);
			}
		}

	}

	/**
	 * 校验计划数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-26 上午11:48:03
	 */
	private void validateTruckSchedulingEntity(
			List<TruckSchedulingEntity> initScheduleList) {
		// 判空
		if (CollectionUtils.isNotEmpty(initScheduleList)) {
			// 循环
			for (TruckSchedulingEntity scheduling : initScheduleList) {
				// 判空
				if (StringUtils.isBlank(scheduling.getDriverPhone())) {
					// 异常
					throw new ShortScheduleException(scheduling.getDriverCode()
							+ "的电话为空，请先设置！", "");
				}
			}
		}

	}

	/**
	 * 创建查询校验当天、同一线路班次是否有重复的查询条件
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-7 上午9:24:29
	 */
	private void makeSearchCondition(TruckSchedulingTaskDto tempTask,
			SimpleTruckScheduleDto simDto) {
		if (simDto != null && tempTask != null) {
			// 计划任务状态
			simDto.setSchedulingStatus(ScheduleConstants.SCHEDULE_STATUS_ACTIVE);
			// 计划类型
			simDto.setSchedulingtype(tempTask.getSchedulingType());
			// 日期
			simDto.setYmd(tempTask.getSchedulingDate());
			// 司机归属机构
			simDto.setDriverOrgCode(tempTask.getDriverOrgCode());
			// 排班部门
			simDto.setSchedulingDepartCode(tempTask.getSchedulingDepartCode());
			// 短途排班
			if (ScheduleConstants.SCHEDULE_TYPE_SHORTSCHEDULE.equals(tempTask
					.getSchedulingType())) {
				// 线路虚拟编码
				simDto.setLineNo(tempTask.getLineCode());
				// 班次
				simDto.setFrequencyNo(tempTask.getFrequencyNo());
			}
			// 接送货排班
			else {
				// 司机编码
				simDto.setDriverCode(tempTask.getDriverCode());
				// 车牌号
				simDto.setVehicleNo(tempTask.getVehicleNo());
				// 定人定区的区码
				simDto.setZoneCode(tempTask.getZoneCode());
			}
		}

	}

	/**
	 * 在排班计划数据列表中，找到非工作状态的排班计划数据，并批量删除
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午8:25:33
	 */
	private void makeUnWorkTruckSchedulingDtoList(
			List<TruckSchedulingDto> impScheduleList) {
		// 针对非工作状态的数据，则需要删除原本的任务
		List<SimpleTruckScheduleDto> unWorkList = new ArrayList<SimpleTruckScheduleDto>();
		// 非工作状态的计划查询条件
		SimpleTruckScheduleDto scheduleDto = null;
		// 获取非工作状态的数据
		if (CollectionUtils.isNotEmpty(impScheduleList)) {
			for (TruckSchedulingDto dto : impScheduleList) {
				if (StringUtils.isNotBlank(dto.getPlanType())
						&& !ScheduleConstants.PLAN_TYPE_WORK.equals(dto
								.getPlanType())) {
					scheduleDto = new SimpleTruckScheduleDto();
					// 司机代码
					scheduleDto.setDriverCode(dto.getDriverCode());
					// 司机直属部门代码
					scheduleDto.setDriverOrgCode(dto.getDriverOrgCode());
					// 排班部门
					scheduleDto.setSchedulingDepartCode(dto
							.getSchedulingDepartCode());
					// 工作类别
					scheduleDto.setPlanType(dto.getPlanType());
					// 排班类型
					scheduleDto.setSchedulingtype(dto.getSchedulingType());
					// 计划状态
					scheduleDto
							.setSchedulingStatus(ScheduleConstants.SCHEDULE_STATUS_ACTIVE);
					// 年月日
					scheduleDto.setYmd(dto.getYmd());
					unWorkList.add(scheduleDto);
				}
			}
		}
		// 执行删除非工作状态的数据
		if (CollectionUtils.isNotEmpty(unWorkList)) {
			truckSchedulingTaskDao.deleteTasksForUnworkType(unWorkList);
		}
	}

	/**
	 * 创建计划数据列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午8:03:37
	 */
	private void makeImpScheduleList(List<ScheduleExcelDto> excelDtos,
			Map<String, DriverAssociationDto> existDriverMap,
			TruckSchedulingEntity truckSchedulingEntity, UserEntity user,
			List<TruckSchedulingDto> impScheduleList,
			List<TruckSchedulingTaskDto> impTaskList) {
		// 排班计划Dto
		TruckSchedulingDto schedulingDto = null;
		// 排班任务Dto
		TruckSchedulingTaskDto taskDto = null;
		// 用于查询计划
		SimpleTruckScheduleDto simDto = null;
		// 根据Excel读入的数据,循环构建排班计划Dto,排班任务Dto
		for (ScheduleExcelDto excelDto : excelDtos) {
			// 获取存在的司机信息
			DriverAssociationDto driver = existDriverMap.get(excelDto
					.getDriverCode());
			// 司机存在
			if (driver != null) {
				// 创建一个新的计划
				schedulingDto = new TruckSchedulingDto();
				// 创建排班计划数据schedulingDto
				makeTruckSchedulingDto(schedulingDto, driver, excelDto,
						truckSchedulingEntity, user);
				// 将创建好的排班计划数据加入导入计划数据List
				impScheduleList.add(schedulingDto);
				// 1.车牌号不为空、2.且为上班，新建排班任务taskDto
				if (StringUtils.isNotBlank(excelDto.getVehicleNo())
						&& ScheduleConstants.PLAN_TYPE_WORK.equals(excelDto
								.getPlanType())) {
					simDto = new SimpleTruckScheduleDto();
					// 创建一个新的任务
					taskDto = new TruckSchedulingTaskDto();
					// 创建排班计划任务数据taskDto
					makeTruckSchedulingTaskDto(simDto, taskDto, driver,
							excelDto, truckSchedulingEntity, user);
					// 加入列表
					impTaskList.add(taskDto);
				}
			}
		}

	}

	/**
	 * 创建排班计划任务数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午8:13:43
	 */
	private void makeTruckSchedulingTaskDto(SimpleTruckScheduleDto simDto,
			TruckSchedulingTaskDto taskDto, DriverAssociationDto driver,
			ScheduleExcelDto excelDto,
			TruckSchedulingEntity truckSchedulingEntity, UserEntity user) {
		if (simDto != null && taskDto != null) {
			// 司机直属部门
			taskDto.setDriverOrgCode(driver.getDriverOrganizationCode());
			// 排班部门
			simDto.setSchedulingDepartCode(truckSchedulingEntity
					.getSchedulingDepartCode());
			taskDto.setSchedulingDepartCode(truckSchedulingEntity
					.getSchedulingDepartCode());
			// 排班日
			taskDto.setSchedulingDate(com.deppon.foss.util.DateUtils.convert(
					excelDto.getSchedulingDate(),
					com.deppon.foss.util.DateUtils.DATE_FORMAT));
			simDto.setYmd(taskDto.getSchedulingDate());
			// 工作类别
			taskDto.setPlanType(excelDto.getPlanType());
			simDto.setPlanType(excelDto.getPlanType());
			// 司机编码
			taskDto.setDriverCode(excelDto.getDriverCode());
			simDto.setDriverCode(excelDto.getDriverCode());
			// 状态为可用
			taskDto.setSchedulingStatus(ScheduleConstants.SCHEDULE_STATUS_ACTIVE);
			simDto.setSchedulingStatus(ScheduleConstants.SCHEDULE_STATUS_ACTIVE);
			// 排班类型
			taskDto.setSchedulingType(truckSchedulingEntity.getSchedulingType());
			simDto.setSchedulingtype(truckSchedulingEntity.getSchedulingType());
			// 车牌号
			taskDto.setVehicleNo(excelDto.getVehicleNo());
			// 车牌号查询车辆信息
			SimpleTruckScheduleDto vehicle = queryCarInfoByVehicleNo(excelDto
					.getVehicleNo());
			if (vehicle != null) {
				if (StringUtils.isNotBlank(vehicle.getCarOwner())) {
					// 车辆所直属单位CODE
					taskDto.setCarOwner(vehicle.getCarOwner());
					// 车型
					taskDto.setTruckModel(vehicle.getTruckModel());
					// 校验本车牌是不是本部门的车
					if (!vehicle.getCarOwner().equals(
							truckSchedulingEntity.getSchedulingDepartCode())) {
						LOGGER.info("本车牌是不是本部门的车,"
								+ vehicle.getCarOwner()
								+ ","
								+ truckSchedulingEntity
										.getSchedulingDepartCode());
						taskDto.setTruckFlag(FossConstants.NO);
					} else {
						taskDto.setTruckFlag(FossConstants.YES);
					}
				} else {
					LOGGER.error(excelDto.getVehicleNo()
							+ "车牌没有对应的车辆所属部门，请先维护再导入");
					throw new ShortScheduleException(excelDto.getVehicleNo()
							+ "车牌没有对应的车辆所属部门，请先维护再导入", "");
				}

			} else {
				LOGGER.error(excelDto.getVehicleNo() + "车牌没有查询到相应的车辆信息，请先维护再导入");
				throw new ShortScheduleException(excelDto.getVehicleNo()
						+ "车牌没有查询到相应的车辆信息，请先维护再导入", "");
			}
			// 短途排班才需要
			if (ScheduleConstants.SCHEDULE_TYPE_SHORTSCHEDULE
					.equals(truckSchedulingEntity.getSchedulingType())) {
				// 获取短途排班相关线路、班次、发车时间等信息
				shortScheduleLineInfoAndFrequencyInfo(excelDto, taskDto);
			}
			// 接送货排班获取定人定区区域
			else {
				// 如果是接送货，则需要区域字段、车辆定人定区（区域）
				RegionVehicleEntity zoneDto = null;
				// 查询车辆定人定区（区域）
				if (StringUtils.isNotBlank(excelDto.getVehicleNo())) {
					zoneDto = queryZoneCodeByVehicleNo(excelDto.getVehicleNo());
					if (zoneDto != null
							&& StringUtils.isNotBlank(zoneDto.getRegionCode())) {
						// 设置区域编码
						taskDto.setZoneCode(zoneDto.getRegionCode());
					} else {
						LOGGER.error(excelDto.getVehicleNo()
								+ "车牌号无定人定区配置，请先配置，再重试");
						throw new ShortScheduleException(
								excelDto.getVehicleNo() + "车牌号无定人定区配置，请先配置，再重试",
								"");
					}
				} else {
					LOGGER.error("车牌号为空");
					throw new ShortScheduleException("车牌号为空", "");
				}
			}
			// 查询车辆状态为可用的状态
			taskDto.setTaskStatus(ScheduleConstants.SCHEDULE_STATUS_ACTIVE);
			// 设置UUID
			taskDto.setId(UUIDUtils.getUUID());
			// 查询计划主键
			TruckSchedulingEntity entity = truckSchedulingDao
					.queryTruckSchedulingByParams(simDto);
			// 设置需要更新计划的的Id
			if (entity != null) {
				taskDto.setSchedulingId(entity.getId());
			}
			// 设置用户信息
			setUserInfo(taskDto.getClass().getName(), taskDto, user,
					ScheduleConstants.ACTION_TYPE_INSERT);
		}

	}

	/**
	 * 验证车牌和线路信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-10 下午1:50:23
	 */
	@Override
	public StringBuffer validateVehicleAndLineInfo(
			List<ScheduleExcelDto> excelDtos,
			TruckSchedulingEntity truckSchedulingEntity) {
		// 错误消息
		StringBuffer errorMsg = new StringBuffer();
		// 判空
		if (CollectionUtils.isNotEmpty(excelDtos)
				&& truckSchedulingEntity != null) {
			// 默认行
			int currentRowNo = 0;
			// 循环校验
			for (ScheduleExcelDto excelDto : excelDtos) {
				if (excelDto != null) {
					// 加1行
					currentRowNo = (excelDto.getRowNum() + 1);
					// 判空
					if (ScheduleConstants.PLAN_TYPE_WORK.equals(excelDto
							.getPlanType())) {
						// 车牌号查询车辆信息
						if (StringUtils.isNotBlank(excelDto.getVehicleNo())) {
							// 验证车牌信息
							validateVehicleNo(excelDto, currentRowNo, errorMsg);
						}
						// 短途排班
						if (ScheduleConstants.SCHEDULE_TYPE_SHORTSCHEDULE
								.equals(truckSchedulingEntity
										.getSchedulingType())) {
							// 验证短途排班线路、班次、时效信息
							validateLineinfo(excelDto, currentRowNo, errorMsg);
						}
						// 接送货排班获取定人定区区域
						else if (ScheduleConstants.SCHEDULE_TYPE_DELIVERY
								.equals(truckSchedulingEntity
										.getSchedulingType())) {
							// 验证接送货排班获取定人定区区域
							validatePkpVehicle(excelDto, currentRowNo, errorMsg);
						}
					}
				}
			}
		}
		return errorMsg;
	}

	/**
	 * 验证接送货车辆定人定区
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-10 下午4:43:11
	 */
	private void validatePkpVehicle(ScheduleExcelDto excelDto,
			int currentRowNo, StringBuffer errorMsg) {
		if (excelDto != null && errorMsg != null) {
			// 存在的线路
			Map<String, RegionVehicleEntity> regions = new HashMap<String, RegionVehicleEntity>();
			// 如果是接送货，则需要区域字段、车辆定人定区（区域）
			RegionVehicleEntity zoneDto = null;
			// 查询车辆定人定区（区域）
			if (StringUtils.isNotBlank(excelDto.getVehicleNo())) {
				// 没有则查询，有则获取
				if (null == regions.get(excelDto.getVehicleNo())) {
					zoneDto = queryZoneCodeByVehicleNo(excelDto.getVehicleNo());
					if (zoneDto != null) {
						regions.put(excelDto.getVehicleNo(), zoneDto);
					}
				} else {
					zoneDto = regions.get(excelDto.getVehicleNo());
				}
				// 验证定人定去信息
				//sonal-352203
				if (zoneDto == null
						|| StringUtils.isBlank(zoneDto.getRegionCode())) {
					errorMsg.append("第" + currentRowNo + "行"
							+ excelDto.getVehicleNo() + "车牌号无定人定区配置;");
				}
			}
		}
	}

	/**
	 * 验证线路信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-10 下午4:41:39
	 */
	private void validateLineinfo(ScheduleExcelDto excelDto, int currentRowNo,
			StringBuffer errorMsg) {
		if (excelDto != null && errorMsg != null) {
			// 存在的线路
			Map<String, LineEntity> existLines = new HashMap<String, LineEntity>();
			Map<String, ExpressLineEntity> existLines2 = new HashMap<String, ExpressLineEntity>();

			// 由简码获取线路虚拟code
			if (StringUtils.isBlank(excelDto.getLineNoCode())) {
				// 什么也不做
			} else {
				// 线路信息
				LineEntity lineInfo = null;
				// 存在则不查询，不存在，则查询
				if (null == existLines.get(excelDto.getLineNoCode())) {
					lineInfo = lineService.queryLineBySimpleCode(excelDto
							.getLineNoCode());
					if (lineInfo != null) {
						existLines.put(excelDto.getLineNoCode(), lineInfo);
					} else {
						ExpressLineEntity explineInfo = expresslineService
								.queryLineBySimpleCode(excelDto.getLineNoCode());
						if (explineInfo != null) {
							existLines2.put(excelDto.getLineNoCode(),
									explineInfo);
						}
					}
				} else {
					lineInfo = existLines.get(excelDto.getLineNoCode());
				}
				// 虚拟code
				String virtualCode = null;
				if (lineInfo != null) {
					// 获取虚拟code
					virtualCode = lineInfo.getVirtualCode();
					if (StringUtils.isNotBlank(virtualCode)) {
						// 班次不能为空
						if (StringUtils.isBlank(excelDto.getFrequencyNo())) {
							errorMsg.append("第" + currentRowNo + "行" + "班次为空;");
						} else {
							// 根据线路虚拟CODE、班次获取发车时间(综合提供接口)
							if (null == queryDepartTimeByLineNoAndFrequenceNo(
									virtualCode, excelDto.getFrequencyNo())) {
								errorMsg.append("第" + currentRowNo + "行"
										+ excelDto.getLineNoCode() + "线路"
										+ excelDto.getFrequencyNo()
										+ "班次的发车发车时间为空;");
							}
						}

					} else {
						errorMsg.append("第" + currentRowNo + "行,线路虚拟编码为空;");
					}
				} else {
					errorMsg.append("第" + currentRowNo + "行"
							+ excelDto.getLineNoCode() + "没有找到相关线路;");
				}
			}

		}

	}

	/**
	 * 验证车辆信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-10 下午4:40:03
	 */
	private void validateVehicleNo(ScheduleExcelDto excelDto, int currentRowNo,
			StringBuffer errorMsg) {
		if (excelDto != null && errorMsg != null) {
			// 存在的车辆
			Map<String, SimpleTruckScheduleDto> existVehicles = new HashMap<String, SimpleTruckScheduleDto>();
			// 查询车辆信息
			SimpleTruckScheduleDto vehicle = null;
			// 存在则不查询，不存在，则查询
			if (null == existVehicles.get(excelDto.getVehicleNo())) {
				vehicle = queryCarInfoByVehicleNo(excelDto.getVehicleNo());
				if (vehicle != null) {
					existVehicles.put(excelDto.getVehicleNo(), vehicle);
				}
			} else {
				vehicle = existVehicles.get(excelDto.getVehicleNo());
			}
			// 不为空
			if (vehicle != null) {
				// 车牌所属车队为空
				if (StringUtils.isBlank(vehicle.getCarOwner())) {
					errorMsg.append("第" + currentRowNo + "行"
							+ excelDto.getVehicleNo() + "车牌所属车队为空;");
				}
			}
			// 车牌不存在
			else {
				errorMsg.append("第" + currentRowNo + "行"
						+ excelDto.getVehicleNo() + "车牌不存在;");
			}
		}
	}

	/**
	 * 获取短途排班相关线路、班次、发车时间等信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午8:17:18
	 */
	private void shortScheduleLineInfoAndFrequencyInfo(
			ScheduleExcelDto excelDto, TruckSchedulingTaskDto taskDto) {
		// 由简码获取线路虚拟code
		// 线路信息
		LineEntity lineInfo = lineService.queryLineBySimpleCode(excelDto
				.getLineNoCode());
		// 虚拟code
		String virtualCode = null;
		if (lineInfo != null) {
			// 获取虚拟code
			virtualCode = lineInfo.getVirtualCode();
			if (StringUtils.isNotBlank(virtualCode)) {
				// 设置保存虚拟
				taskDto.setLineCode(virtualCode);
				taskDto.setLineNo(virtualCode);
				// 班次
				taskDto.setFrequencyNo(excelDto.getFrequencyNo());
				// 根据线路虚拟CODE、班次获取发车时间(综合提供接口)
				SimpleTruckScheduleDto dto = queryDepartTimeByLineNoAndFrequenceNo(
						virtualCode, excelDto.getFrequencyNo());
				if (dto != null) {
					// 解析发车时间
					String departTimeShort = dto.getDepartTimeShort();
					// 取得计划日期
					if (excelDto.getSchedulingDate() != null) {
						String schedulingDate = com.deppon.foss.util.DateUtils
								.convert(
										excelDto.getSchedulingDate(),
										com.deppon.foss.util.DateUtils.DATE_FORMAT);
						// 将日期和时间组合起来
						schedulingDate = schedulingDate.concat(" ").concat(
								departTimeShort);
						// 转换为日期类型
						Date departTime = com.deppon.foss.util.DateUtils
								.convert(
										schedulingDate,
										com.deppon.foss.util.DateUtils.DATE_TIME_FORMAT);
						taskDto.setDepartTime(departTime);
					}
				} else {
					throw new ShortScheduleException(excelDto.getLineNoCode()
							+ "线路" + excelDto.getFrequencyNo()
							+ "班次的发车发车时间为空，请先配置再导入", "");
				}
			} else {
				throw new ShortScheduleException(excelDto.getLineNoCode()
						+ "线路虚拟编码为空", "");
			}
		} else {
			ExpressLineEntity explineInfo = expresslineService
					.queryLineBySimpleCode(excelDto.getLineNoCode());
			if (explineInfo != null) {
				// 获取虚拟code
				virtualCode = explineInfo.getVirtualCode();
				if (StringUtils.isNotBlank(virtualCode)) {
					// 设置保存虚拟
					taskDto.setLineCode(virtualCode);
					taskDto.setLineNo(virtualCode);
					// 班次
					taskDto.setFrequencyNo(excelDto.getFrequencyNo());
					// 根据线路虚拟CODE、班次获取发车时间(综合提供接口)
					SimpleTruckScheduleDto dto = queryDepartTimeByLineNoAndFrequenceNo(
							virtualCode, excelDto.getFrequencyNo());
					if (dto != null) {
						// 解析发车时间
						String departTimeShort = dto.getDepartTimeShort();
						// 取得计划日期
						if (excelDto.getSchedulingDate() != null) {
							String schedulingDate = com.deppon.foss.util.DateUtils
									.convert(
											excelDto.getSchedulingDate(),
											com.deppon.foss.util.DateUtils.DATE_FORMAT);
							// 将日期和时间组合起来
							schedulingDate = schedulingDate.concat(" ").concat(
									departTimeShort);
							// 转换为日期类型
							Date departTime = com.deppon.foss.util.DateUtils
									.convert(
											schedulingDate,
											com.deppon.foss.util.DateUtils.DATE_TIME_FORMAT);
							taskDto.setDepartTime(departTime);
						}
					} else {
						throw new ShortScheduleException(
								excelDto.getLineNoCode() + "线路"
										+ excelDto.getFrequencyNo()
										+ "班次的发车发车时间为空，请先配置再导入", "");
					}
				} else {
					throw new ShortScheduleException(excelDto.getLineNoCode()
							+ "线路虚拟编码为空", "");
				}
			}
			throw new ShortScheduleException(excelDto.getLineNoCode()
					+ "没有找到相关线路", "");
		}

	}

	/**
	 * 创建排班计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午8:09:50
	 */
	private void makeTruckSchedulingDto(TruckSchedulingDto schedulingDto,
			DriverAssociationDto driver, ScheduleExcelDto excelDto,
			TruckSchedulingEntity truckSchedulingEntity, UserEntity user) {
		// 司机直属车队
		schedulingDto.setDriverOrgCode(driver.getDriverOrganizationCode());
		// 排班部门
		schedulingDto.setSchedulingDepartCode(truckSchedulingEntity
				.getSchedulingDepartCode());
		// 排班日
		schedulingDto.setYmd(com.deppon.foss.util.DateUtils.convert(
				excelDto.getSchedulingDate(),
				com.deppon.foss.util.DateUtils.DATE_FORMAT));
		// 工作类别
		schedulingDto.setPlanType(excelDto.getPlanType());
		// 司机编码
		schedulingDto.setDriverCode(excelDto.getDriverCode());
		// 联系电话
		schedulingDto.setDriverPhone(driver.getDriverPhone());
		// 状态为可用
		schedulingDto
				.setSchedulingStatus(ScheduleConstants.SCHEDULE_STATUS_ACTIVE);
		// 排班类型
		schedulingDto.setSchedulingType(truckSchedulingEntity
				.getSchedulingType());
		// 更新信息
		if (user != null) {
			if (user.getEmployee() != null) {
				if (user.getEmployee().getDepartment() != null) {
					schedulingDto.setUpdateOrgCode(user.getEmployee()
							.getDepartment().getCode());
				}
				schedulingDto
						.setUpdateUserCode(user.getEmployee().getEmpCode());
				schedulingDto
						.setUpdateUserName(user.getEmployee().getEmpName());
			}
			schedulingDto.setUpdateTime(Calendar.getInstance().getTime());
		}
		// 用户信息
		setUserInfo(schedulingDto.getClass().getName(), schedulingDto, user,
				ScheduleConstants.ACTION_TYPE_UPDATE);

	}

	/**
	 * 查询车队司机，并根据下月的时间 构建司机下月计划基础数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-2 下午2:31:26
	 */
	private List<TruckSchedulingEntity> initShortScheduleList(
			TruckSchedulingEntity truckSchedulingEntity,
			Map<String, DriverAssociationDto> existDriverMap, UserEntity user)
			throws ShortScheduleException {
		// 初始计划数据列表
		List<TruckSchedulingEntity> tempList = new ArrayList<TruckSchedulingEntity>();
		// 获取当前时间
		Calendar cdr = Calendar.getInstance();
		// 当前时间
		String yearMonth = truckSchedulingEntity.getYearMonth();
		if (StringUtils.isNotBlank(yearMonth)) {
			cdr.setTime(com.deppon.foss.util.DateUtils.convert(
					yearMonth.concat("-01"),
					com.deppon.foss.util.DateUtils.DATE_FORMAT));

		}
		// 司机编码
		String driverCode;
		// 本月最大天数
		int actualMaximum = cdr.getActualMaximum(Calendar.DATE);
		// 计划实体
		TruckSchedulingEntity scheduleEntity = null;
		// 司机信息
		DriverAssociationDto driver = null;
		// 根据本车队司机的数量循环构建初始化数据
		if (existDriverMap != null) {
			for (Map.Entry<String, DriverAssociationDto> em : existDriverMap
					.entrySet()) {
				// 司机编码
				driverCode = em.getKey();
				// 司机信息
				driver = existDriverMap.get(driverCode);
				if (driver != null) {
					// 准备某司机的计划数据
					for (int day = 1; day <= actualMaximum; day++) {
						// 动态构建日期时间
						cdr.set(cdr.get(Calendar.YEAR),
								cdr.get(Calendar.MONTH), day);
						// 开始构建实体
						scheduleEntity = new TruckSchedulingEntity();
						// 构建需要初始化的排班计划实体
						makeInitTruckSchedulingEntity(cdr, driver,
								scheduleEntity, truckSchedulingEntity, user,
								tempList);
					}
				} else {
					throw new ShortScheduleException(driverCode + "在初始化时，不存在！",
							"");
				}
			}

		}
		return tempList;
	}

	/**
	 * 构建需要初始化的排班计划实体
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午8:48:39
	 */
	private void makeInitTruckSchedulingEntity(Calendar cdr,
			DriverAssociationDto driver, TruckSchedulingEntity scheduleEntity,
			TruckSchedulingEntity truckSchedulingEntity, UserEntity user,
			List<TruckSchedulingEntity> tempList) {

		if (scheduleEntity != null) {
			// 设置UUID
			scheduleEntity.setId(UUIDUtils.getUUID());
			// 司机代码
			scheduleEntity.setDriverCode(driver.getDriverCode());
			// 排班部门编码
			scheduleEntity.setSchedulingDepartCode(truckSchedulingEntity
					.getSchedulingDepartCode());
			// 司机直属部门代码
			scheduleEntity.setDriverOrgCode(driver.getDriverOrganizationCode());
			// 司机信息获取司机电话
			scheduleEntity.setDriverPhone(driver.getDriverPhone());

			// 工作类别(初始化为未知)
			scheduleEntity.setPlanType(ScheduleConstants.PLAN_TYPE_UNKNOWN);
			// 计划日期
			scheduleEntity.setSchedulingDate(cdr.getTime());
			// 计划状态
			scheduleEntity
					.setSchedulingStatus(ScheduleConstants.SCHEDULE_STATUS_ACTIVE);
			if (cdr.getTime() != null) {
				String currentTime = com.deppon.foss.util.DateUtils.convert(
						cdr.getTime(),
						com.deppon.foss.util.DateUtils.DATE_TIME_FORMAT);
				// 日期数字
				scheduleEntity.setDateNum(Integer.parseInt(currentTime
						.substring(ScheduleConstants.TIME_SUBSTRING_EIGHT,
								ScheduleConstants.TIME_SUBSTRING_TEN)));
				// 年月
				scheduleEntity.setYearMonth(currentTime.substring(
						ScheduleConstants.TIME_SUBSTRING_ZERO,
						ScheduleConstants.TIME_SUBSTRING_SEVEN));
			}
			// 排班类型（TFR短途PKP接送货）排班
			scheduleEntity.setSchedulingType(truckSchedulingEntity
					.getSchedulingType());
			// 查询 是否已经初始化
			Long initedTotal = truckSchedulingDao
					.queryHasInitedList(scheduleEntity);
			// 如果未初始化，则无需初始化本排班类型、司机、车队小组、年月的的排班计划
			if (initedTotal != null
					&& ScheduleConstants.RESULT_INT_VALUE_ZERO
							.compareTo(initedTotal) == ScheduleConstants.ZERO) {
				// 设置创建信息
				setUserInfo(scheduleEntity.getClass().getName(),
						scheduleEntity, user,
						ScheduleConstants.ACTION_TYPE_INSERT);
				// 加入列表
				tempList.add(scheduleEntity);
			}
		}

	}

	/**
	 * 根据车队小组、年月日、司机、排班类型、工作类别查询排班任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-5 下午3:38:05
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#queryShortScheduleTaskByDriver(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@Override
	public List<SimpleTruckScheduleDto> queryShortScheduleTaskByDriver(
			SimpleTruckScheduleDto simDto) throws ShortScheduleException {
		// 数据有效的
		simDto.setSchedulingStatus(ScheduleConstants.SCHEDULE_STATUS_ACTIVE);
		// 查询计划排班列表
		List<SimpleTruckScheduleDto> dtoList = truckSchedulingTaskDao
				.querySchedulingAndTask(simDto);
		// 循环查询车辆所直属名
		if (CollectionUtils.isNotEmpty(dtoList)) {
			// 线路信息
			LineEntity lineInfo = null;
			// 车辆信息
			SimpleTruckScheduleDto carInfoDto = null;
			// 区域信息
			RegionVehicleEntity regionVehicleDto = null;
			RegionVehicleEntity deliveryregionVehicleDto = null;
			// 循环查询转换缺失的信息
			for (SimpleTruckScheduleDto dto : dtoList) {
				// 根据车牌获取区域和车队信息
				if (StringUtils.isNotBlank(dto.getVehicleNo())) {
					// 查询区域信息
					regionVehicleDto = queryZoneCodeByVehicleNo(dto.getVehicleNo());
					if (regionVehicleDto != null&& StringUtils.isNotBlank(regionVehicleDto.getRegionName())) {
						dto.setZoneName(regionVehicleDto.getRegionName());
					}
					deliveryregionVehicleDto = queryDeliveryCodeByVehicleNo(dto.getVehicleNo());
					if (deliveryregionVehicleDto != null
							&& StringUtils.isNotBlank(deliveryregionVehicleDto.getRegionName())) {
						dto.setDeliveryAreaName(deliveryregionVehicleDto.getRegionName());
					}
					// 带货部门
					if (StringUtils.isNotBlank(dto.getTakeGoodsDepartment())) {
						String name = truckSchedulingTaskDao.searchOrgInfoByCode(dto.getTakeGoodsDepartment());
						dto.setTakeGoodsDepartmentName(name);
						// 带货部门code
						dto.setTakeGoodsDepartment(dto.getTakeGoodsDepartment());
					}// 转货部门
					if (StringUtils.isNotBlank(dto.getTransferGoodsDepartment())) {
						String name = truckSchedulingTaskDao.searchOrgInfoByCode(dto.getTransferGoodsDepartment());
						dto.setTransferGoodsDepartmentName(name);
						// 转货部门code
						dto.setTransferGoodsDepartment(dto.getTransferGoodsDepartment());
					}
					// 查询车辆所属车队信息
					carInfoDto = this.queryCarInfoByVehicleNo(dto.getVehicleNo());
					if (carInfoDto != null) {
						dto.setCarOwnerName(carInfoDto.getCarOwnerName());
						// 车型名
						dto.setTruckModelValue(carInfoDto.getTruckModelValue());
					} else {
						throw new ShortScheduleException(dto.getVehicleNo()
								+ "在本车队小组没有查询到！", "");
					}
				}
				// 查询线路信息
				if (StringUtils.isNotBlank(dto.getLineNo())) {
					// 根据保存的线路编码查询线路信息
					lineInfo = lineService.queryLineByVirtualCode(dto.getLineNo());
					if (lineInfo != null) {
						dto.setLineName(lineInfo.getLineName());
					} else {
						ExpressLineEntity explineInfo = expresslineService.queryLineByVirtualCode(dto.getLineNo());
						if (explineInfo != null) {
							dto.setLineName(explineInfo.getLineName());
						}
					}
				}

			}
		}
		return dtoList;
	}

	/**
	 * 
	 * @Title: queryTruckSchedulingTask
	 * @Description: 根据排班id, 排班任务id, 车牌号, 线路号, 班次号查询排班任务.
	 * @param simDto
	 * @return
	 * @return List<TruckSchedulingTaskDto> 返回类型 queryTruckSchedulingTask
	 * @author: ibm-zhangyixin
	 * @throws Date
	 *             :2013-5-24上午10:12:22
	 */
	public List<TruckSchedulingTaskEntity> validateTruckSchedulingTask(
			SimpleTruckScheduleDto simDto) {
		return truckSchedulingTaskDao.validateTruckSchedulingTask(simDto);
	}

	/**
	 * 保存新的任务，并查询返回本计划相关的任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-6 下午2:42:11
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#saveOrUpdateTaskAndFetchTask(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@Override
	@Transactional
	public List<SimpleTruckScheduleDto> saveOrUpdateTaskAndFetchTask(
			SimpleTruckScheduleDto simDto, UserEntity user)
			throws ShortScheduleException {
		// 根据dto装换实体信息
		TruckSchedulingTaskEntity taskEntity = convertToScheduleEntity(simDto,
				user);
		// 查询条件
		SimpleTruckScheduleDto querySimDto = new SimpleTruckScheduleDto();
		//节假日小区名称和之前是否相等
		Boolean falage=true;
		// 保存新的任务
		if (simDto != null && taskEntity != null) {
			// 短途排班验证 需要验证当日 线路、班次不重复
			if (ScheduleConstants.SCHEDULE_TYPE_SHORTSCHEDULE.equals(simDto.getSchedulingtype())) {
				// 如果传回了任务ID ，则表明只是修改
				if (StringUtils.isNotBlank(simDto.getScheduleTaskId())) {
					// 校验当天线路班次是否重复
					validateDuplicateLineCurrentDay(simDto, taskEntity);

					// 根据排班id, 排班任务id, 车牌号, 线路号, 班次号查询排班任务.
					// 当排班任务id不为空时查询语句为and id <> #{scheduleTaskId}
					List<TruckSchedulingTaskEntity> truckSchedulingTasks = validateTruckSchedulingTask(simDto);
					// 如果查出来有值, 说明有重复的数据, 则不做操作
					if (CollectionUtils.isNotEmpty(truckSchedulingTasks)) {
						throw new ShortScheduleException("保存失败,当前线路、班次重复", "当前线路、班次重复");
					}
					// 执行更新任务操作
					truckSchedulingTaskDao.updateTruckSchedulingTask(taskEntity);
				}
				// 没有任务ID，表明新增任务
				else {
					// 查询车队小组、当天、线路、班次是否已经存在本班次的排班
					List<ScheduleExcelDto> tempList = null;
					// 如果查询车队小组、当天、线路、班次的已经存在
					tempList = queryHasLineNoFrequencyNoCurrentDay(simDto);
					// 校验车队小组、当天、线路、班次是否已经存在本班次的排班
					validateDupliSchedulingTask(tempList, taskEntity,ScheduleConstants.ACTION_TYPE_INSERT);
					// 根据排班id, 排班任务id, 车牌号, 线路号, 班次号查询排班任务.
					List<TruckSchedulingTaskEntity> truckSchedulingTasks = validateTruckSchedulingTask(simDto);
					// 如果查出来有值, 说明有重复的数据, 则不做操作
					if (CollectionUtils.isNotEmpty(truckSchedulingTasks)) {
						throw new ShortScheduleException("保存失败,当前线路、班次重复","当前线路、班次重复");
					}
					// 执行新增任务操作
					truckSchedulingTaskDao.insertTruckSchedulingTask(taskEntity);
				}
			}
			// 接送货排班
			else if (simDto != null && ScheduleConstants.SCHEDULE_TYPE_DELIVERY.equals(simDto.getSchedulingtype())) {
				if (StringUtils.isNotBlank(simDto.getScheduleTaskId())) {
					// 创建查询条件
					TruckSchedulingTaskEntity entity = new TruckSchedulingTaskEntity();
					entity.setId(simDto.getScheduleTaskId());
					// 查询计划排班列表
					SimpleTruckScheduleDto truckScheduleDto = truckSchedulingTaskDao.queryTruckSchedulingTask(entity);
					// 原来的接货区域
					String oldRegionalName = truckScheduleDto.getPkpRegionalName();
					if (null == oldRegionalName) {
						oldRegionalName = "";
					}
					String oldDeliveryRegionalName = truckScheduleDto.getDeliveryRegionalName();
					if (null == oldDeliveryRegionalName) {
						oldDeliveryRegionalName = "";
					}
					// 现有的接货区域
					String newRegionalName = simDto.getPkpRegionalName();
					String newDeliveryRegionalName = simDto.getDeliveryRegionalName();
					falage=StringUtils.equals(oldDeliveryRegionalName,newDeliveryRegionalName);
					if (!StringUtils.equals(oldRegionalName, newRegionalName)) {
						// 校验一个非工作日接货小区当天只能绑定一个车辆
						validatepkpAreas(simDto);
						// 删除原有接货区域
						truckSchedulingTaskDao.deleteTaskspkpArea(simDto.getScheduleTaskId(), "PK");
						// 新增现有接货区域
						List<TruckSchedulingZoneEntity> list = convertToSchedulingZoneEntity(simDto, taskEntity, user);
						if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
							truckSchedulingTaskDao.addTaskspkpArea(list);
						}
					}
					if (!StringUtils.equals(oldDeliveryRegionalName,newDeliveryRegionalName)) {
						// 校验一个非工作日送货小区当天只能绑定一个车辆
						validatepkpAreas(simDto);
						// 删除原有送货区域
						truckSchedulingTaskDao.deleteTaskspkpArea(simDto.getScheduleTaskId(), "DE");
						// 新增现有送货区域
						List<TruckSchedulingZoneEntity> list = convertToSchedulingZoneEntity(simDto, taskEntity, user);
						if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
							truckSchedulingTaskDao.addTaskspkpArea(list);
						}
					}
					// 执行更新任务操作
					truckSchedulingTaskDao.updateTruckSchedulingTask(taskEntity);
				} else {
					// 新增接货区域
					List<TruckSchedulingZoneEntity> list = convertToSchedulingZoneEntity(simDto, taskEntity, user);
					// 校验一个非工作日接货小区当天只能绑定一个车辆
					validatepkpAreas(simDto);
					// 新增非工作日接货小区
					if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
						truckSchedulingTaskDao.addTaskspkpArea(list);
					}
					// 执行新增任务操作
					truckSchedulingTaskDao.insertTruckSchedulingTask(taskEntity);
				}
			}
			// 使用最新的scheduleId进行查询
			querySimDto.setScheduleId(taskEntity.getSchedulingId());

		}
		if (simDto != null && simDto.getSchedulingDate() != null) {
		    Long day = judgeTimeRange(simDto.getYmd(),new Date());
			if (0 <= day && day <= ConstantsNumberSonar.SONAR_NUMBER_3) {
			sysTruckSchedulingToPkp(simDto,falage);
			}
		}
		// 查询最新的排班任务
		return queryShortScheduleTaskByDriver(querySimDto);
	}
	/**
	 * 
	 * <p>同步近四天的排班给结算</p> 
	 * @author 189284 
	 * @date 2015-12-11 下午5:44:04
	 * @param simDto
	 * @param String smallZoneCodes,小区编码集合
	 * @param String vechileNo,车牌号
	 * @param  Date editDate,日期
	 * @param  String schedulingStatus 状态
	 */
	public void sysTruckSchedulingToPkp(SimpleTruckScheduleDto simDto,Boolean falage) {
		LOGGER.info("同步近四天的排班给结算---begin");
		// String smallZoneCodes,小区编码集合
		// String vechileNo,车牌号
		// Date editDate,日期
		// String schedulingStatus 状态
		String smallZoneCodes = null;
		// 判空
		if (StringUtils.isNotBlank(simDto.getVehicleNo())) {
			// 查询送货区域信息
			RegionVehicleEntity deliveryRegionVehicle = queryDeliveryCodeByVehicleNo(simDto.getVehicleNo());
			// 判空                                                                           queryDeliveryCodeByVehicleNo
			if (deliveryRegionVehicle != null&&StringUtils.isNotEmpty(deliveryRegionVehicle.getCode())) {
				// 送货区域code
				smallZoneCodes = deliveryRegionVehicle.getCode();
			}
			if(CollectionUtils.isEmpty(simDto.getSchedulingZoneEntity())){
				if(StringUtils.isNotEmpty(simDto.getDeliveryRegionalName())&&falage){
					List<TruckSchedulingZoneEntity> list=truckSchedulingTaskDao.queryPkpAreaInfoByVehicleNo(simDto);
					simDto.setSchedulingZoneEntity(list);
				}
			}
			if(CollectionUtils.isNotEmpty(simDto.getSchedulingZoneEntity())){
			List<TruckSchedulingZoneEntity> list=simDto.getSchedulingZoneEntity();
			for (TruckSchedulingZoneEntity truckSchedulingZoneEntity : list) {
				if(truckSchedulingZoneEntity!=null
						&&StringUtils.isNotEmpty(truckSchedulingZoneEntity.getZoneCode())
						&&StringUtils.equals("DE", truckSchedulingZoneEntity.getRegionType())
				){
					smallZoneCodes=smallZoneCodes+","+truckSchedulingZoneEntity.getZoneCode();
				}
			}
			}
			LOGGER.info("smallZoneCodes="+smallZoneCodes+";vechileNo="+simDto.getVehicleNo()+
					";editDate="+simDto.getSchedulingDate()+",=simDto.getPlanType()"+simDto.getPlanType());
			if(smallZoneCodes!=null&&smallZoneCodes.length()>0){
				editVechileSchedulingService.insertEditInfo(smallZoneCodes, 
						simDto.getVehicleNo(), simDto.getSchedulingDate(),
						StringUtils.equals(simDto.getPlanType(),ScheduleConstants.PLAN_TYPE_WORK) ? "Y" : "N");
			}
		}
		LOGGER.info("同步近四天的排班给结算---end");
	}
	/**
	 * 
	 * <p>计算开始时间和结束时间查查多少天 yyyy-MM-dd</p> 
	 * @author 189284 
	 * @date 2015-12-11 下午5:40:44
	 * @param stratTime
	 * @return
	 * @see
	 */
	public Long judgeTimeRange(String stratTime,Date endTime) {
		// 截取放行时间的时、分，转成HHmm形式，24小时制
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String lTimeStr = stratTime;
		String nTimeStr = df.format(endTime);
		Date lTimeDate = null;
		Date nTimeDate = null;
		long l = 0l;
		try {
			lTimeDate = df.parse(lTimeStr);
			nTimeDate = df.parse(nTimeStr);
			l = lTimeDate.getTime() - nTimeDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		long day = l / (ConstantsNumberSonar.SONAR_NUMBER_24 * ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_1000);
		return day;
	}

	/**
	 * 构造非工作日接货小区实体
	 */
	private List<TruckSchedulingZoneEntity> convertToSchedulingZoneEntity(
			SimpleTruckScheduleDto simDto,
			TruckSchedulingTaskEntity taskEntity, UserEntity user) {
		List<TruckSchedulingZoneEntity> list = simDto.getSchedulingZoneEntity();
		if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setId(UUIDUtils.getUUID());
				list.get(i).setTruckSchedulingTaskId(taskEntity.getId());
				list.get(i).setCreateTime(new Date());
				list.get(i).setCreateUserName(user.getEmployee().getEmpName());
				list.get(i).setCreateUserCode(user.getEmployee().getEmpCode());
			}
		}
		return list;
	}

	/**
	 * 校验份工作日（节假日）一个接货小区当天只能绑定一个车辆
	 * */
	private void validatepkpAreas(SimpleTruckScheduleDto simDto) {
		Date scheduingDate = simDto.getSchedulingDate();
		String cqueryTime = com.deppon.foss.util.DateUtils.convert(
				scheduingDate, com.deppon.foss.util.DateUtils.DATE_FORMAT);
		List<TruckSchedulingZoneEntity> list = simDto.getSchedulingZoneEntity();
		if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
			for (TruckSchedulingZoneEntity temp : list) {
				String zoneCode = temp.getZoneCode();
				// 通过区域和时间查询任务
				TruckSchedulingZoneEntity zoneEntity = truckSchedulingTaskDao
						.queryVehicleByZoneCode(zoneCode, cqueryTime);
				// 如果存在且于当前任务不一样的任务则提示
				if (zoneEntity != null
						&& !StringUtils.equals(
								zoneEntity.getTruckSchedulingTaskId(),
								simDto.getScheduleTaskId())) {
					throw new ShortScheduleException(temp.getZoneName()
							+ "，已经绑定了车牌号" + zoneEntity.getVehicleNo()
							+ "，无法再次绑定，请先删除此车牌号已绑定的小区!", "");
				}
			}
		}
	}

	/**
	 * // 校验车队小组、当天、线路、班次是否已经存在本班次的排班
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-29 上午10:16:03
	 */
	private void validateDupliSchedulingTask(List<ScheduleExcelDto> tempList,
			TruckSchedulingTaskEntity taskEntity, String actionType) {
		// 判空
		if (CollectionUtils.isNotEmpty(tempList)) {
			// 获取第一条
			ScheduleExcelDto tmpTask = tempList.get(0);
			if (tmpTask != null && taskEntity != null) {
				// 支持同一天、同一线路、同一车牌可以有多个司机排班
				if (StringUtils.equals(tmpTask.getVehicleNo(),
						taskEntity.getVehicleNo())) {
					// 验证通过
				}// 修改车牌
				else if (ScheduleConstants.ACTION_TYPE_UPDATE
						.equals(actionType)
						&& !StringUtils.equals(tmpTask.getVehicleNo(),
								taskEntity.getVehicleNo())
						&& StringUtils.equals(tmpTask.getLineNo(),
								taskEntity.getLineNo())
						&& StringUtils.equals(tmpTask.getFrequencyNo(),
								taskEntity.getFrequencyNo())) {
					// 验证通过
				}
				// 支持同一天、同一线路、不同车牌不可以重复
				else {
					// 不能执行新增操作，跑出异常信息
					throw new ShortScheduleException("保存失败,当前线路、班次重复",
							"当前线路、班次重复");
				}
			}
		}
	}

	/**
	 * 校验当天线路班次是否重复
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-14 下午5:16:21
	 */
	private void validateDuplicateLineCurrentDay(SimpleTruckScheduleDto simDto,
			TruckSchedulingTaskEntity taskEntity) {
		// 查询车队小组、当天、线路、班次是否已经存在本班次的排班
		List<ScheduleExcelDto> tempList = null;
		// 传回ID ，则使用任务Id进行查询,设置查询条件
		SimpleTruckScheduleDto tempSimDto = new SimpleTruckScheduleDto();
		tempSimDto.setScheduleTaskId(simDto.getScheduleTaskId());
		tempSimDto
				.setSchedulingStatus(ScheduleConstants.SCHEDULE_STATUS_ACTIVE);
		tempSimDto.setSchedulingtype(simDto.getSchedulingtype());

		// 查询车队小组、当天、线路、班次是否已经存在本班次的排班
		tempList = queryHasLineNoFrequencyNoCurrentDay(tempSimDto);

		// 如果查询车队小组、当天、线路、班次的已经存在
		if (CollectionUtils.isNotEmpty(tempList)) {
			ScheduleExcelDto excelDto = tempList.get(0);
			if (StringUtils.isNotBlank(excelDto.getLineNo())
					&& StringUtils.isNotBlank(excelDto.getFrequencyNo())) {
				// 如果线路、编号无改动,说明修改了除线路、班次之外的数据
				if (excelDto.getLineNo().equals(taskEntity.getLineNo())
						&& excelDto.getFrequencyNo().equals(
								taskEntity.getFrequencyNo())) {
					// 校验当天、线路、班次，不同车牌重复
					validateDupliSchedulingTask(tempList, taskEntity,
							ScheduleConstants.ACTION_TYPE_UPDATE);
					// 执行更新任务操作
					truckSchedulingTaskDao.updateTruckSchedulingTask(taskEntity);
				}// 如果线路编号有改动,则需要重复查询验证是否重复,说明是线路、班次改到其他已经有的线路和班次，与其他重复
				else {
					// 年月日
					tempSimDto.setYmd(simDto.getYmd());
					// 排班部门
					tempSimDto.setSchedulingDepartCode(simDto
							.getSchedulingDepartCode());
					// 排班日期
					tempSimDto.setSchedulingDate(simDto.getSchedulingDate());
					// 班次
					tempSimDto.setFrequencyNo(simDto.getFrequencyNo());
					// 线路编码
					tempSimDto.setLineNo(simDto.getLineNo());
					// 任务ID
					tempSimDto.setScheduleTaskId(null);
					// 第二次查询是否当天存在重复的班次和线路
					tempList = queryHasLineNoFrequencyNoCurrentDay(tempSimDto);
					// 判空
					if (CollectionUtils.isNotEmpty(tempList)) {
						// 校验车队小组、当天、线路、班次是否已经存在本班次的排班
						validateDupliSchedulingTask(tempList, taskEntity,
								ScheduleConstants.ACTION_TYPE_UPDATE);
					}
				}
			} else {
				// 异常
				throw new ShortScheduleException("线路或班次为空", "");
			}
		}

	}

	/**
	 * 保存或更新计划任务时，用于参数与实体的转换，还涉及到根据车牌号查询车辆信息、线路、班次查询发车时间
	 * 
	 * SimpleTruckScheduleDto转换为TruckSchedulingTaskEntity
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-6 下午3:19:09
	 */
	@Transactional
	private TruckSchedulingTaskEntity convertToScheduleEntity(
			SimpleTruckScheduleDto simDto, UserEntity user)
			throws ShortScheduleException {
		// 校验带货部门转货部门是传递过来的code还是name
		String regEx = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regEx);
		// 排班计划任务
		TruckSchedulingTaskEntity taskEntity = null;
		// 根据车队编号、年月日、司机编号、计划状态、排班类型（短途排班）、工作类别查询任务
		TruckSchedulingEntity tempSchedule = truckSchedulingDao
				.queryTruckSchedulingByParams(simDto);
		// 改变排班计划工作状态
		changeTruckSchedulingPlanType(user, tempSchedule);

		// 开始根据现有数据构建保存或者更新的排班任务实体
		if (simDto != null) {
			taskEntity = new TruckSchedulingTaskEntity();
			// 如果传回了任务ID ，则表明只是修改
			if (StringUtils.isNotBlank(simDto.getScheduleTaskId())) {
				// 任务ID
				taskEntity.setId(simDto.getScheduleTaskId());
				// 计划ID
				taskEntity.setSchedulingId(simDto.getScheduleId());
				// 更新用户信息
				setUserInfo(taskEntity.getClass().getName(), taskEntity, user,
						ScheduleConstants.ACTION_TYPE_UPDATE);

			}// 没有任务ID，表明新增任务,新的任务
			else {
				if (tempSchedule != null) {
					// UUID
					taskEntity.setId(UUIDUtils.getUUID());
					// 计划Id（根据参数组合查询得到）
					taskEntity.setSchedulingId(tempSchedule.getId());
					// 新增用户信息
					setUserInfo(taskEntity.getClass().getName(), taskEntity,
							user, ScheduleConstants.ACTION_TYPE_INSERT);
				} else {
					throw new ShortScheduleException("未查询到计划", "");
				}
			}

			// 车牌
			if (StringUtils.isNotBlank(simDto.getVehicleNo())) {
				// 根据车牌号补齐车辆相关信息
				makeTruckSchedulingTaskEntityVehicleInfo(simDto, taskEntity);
			} else {
				throw new ShortScheduleException("车牌号为空", "");
			}
			// 根据排班类型决定线路、班次、发车时间（用于短途排班）或定人定区的区域（接送货排班）
			if (StringUtils.isNotBlank(simDto.getSchedulingtype())) {
				// 短途排班
				if (ScheduleConstants.SCHEDULE_TYPE_SHORTSCHEDULE.equals(simDto.getSchedulingtype())) {
					// 短途排班线路、班次、发车时间相关信息
					shortScheduleLineInfoAndFrequency(simDto, taskEntity);
				}// 接送货排班
				else if (ScheduleConstants.SCHEDULE_TYPE_DELIVERY.equals(simDto.getSchedulingtype())) {
					// 接送货排班是否带快递货、预计带货方数、班次、定人定区信息
					deliveryScheduleZoneInfo(simDto, taskEntity);
					// 新增代码(2015-07-22)
					if (!StringUtils.isBlank(simDto.getDispatchVehicleTask())) {
						// 出车任务
						taskEntity.setDispatchVehicleTask(simDto.getDispatchVehicleTask());
					}
					if (!StringUtils.isBlank(simDto.getExpectedDispatchVehicleTime())) {
						// 预计出车时间
						taskEntity.setExpectedDispatchVehicleTime(simDto.getExpectedDispatchVehicleTime());
					}
					if (!StringUtils.isBlank(simDto.getIsTheTwoTripTime())) {
						// 预计二次出车时间
						taskEntity.setIsTheTwoTripTime(simDto.getIsTheTwoTripTime());
					}
					if (!StringUtils.isBlank(simDto.getTakeGoodsDepartment())) {
						Matcher matcher = pat.matcher(simDto.getTakeGoodsDepartment());
						if (matcher.find()) {
							// 包含中文
							CommonOrgVo v = new CommonOrgVo();
							v.setName(simDto.getTakeGoodsDepartment());
							List<CommonOrgEntity> list = commonOrgService.searchOrgByCondition(v);
							if (list.size() > 0) {
								String takeGoods = list.get(0).getCode();
								taskEntity.setTakeGoodsDepartment(takeGoods);
							}
						} else {
							// 带货部门code
							taskEntity.setTakeGoodsDepartment(simDto.getTakeGoodsDepartment());
						}

					}
					if (!StringUtils.isBlank(simDto.getTransferGoodsDepartment())) {
						Matcher matcher1 = pat.matcher(simDto.getTransferGoodsDepartment());
						if (matcher1.find()) {
							// 包含中文
							CommonOrgVo v = new CommonOrgVo();
							v.setName(simDto.getTransferGoodsDepartment());
							List<CommonOrgEntity> list = commonOrgService
									.searchOrgByCondition(v);
							if (list.size() > 0) {
								String trans = list.get(0).getCode();
								taskEntity.setTransferGoodsDepartment(trans);
							}
						} else {
							// 转货部门code
							taskEntity.setTransferGoodsDepartment(simDto.getTransferGoodsDepartment());
						}
					}

				}
			} else {
				throw new ShortScheduleException("排班类型为空", "");
			}
			if (!StringUtils.isBlank(simDto.getPkpRegionalName())) {
				// 设置定人定区接货区域
				taskEntity.setPkpAreaNames(simDto.getPkpRegionalName());
			}
			if (!StringUtils.isBlank(simDto.getDeliveryRegionalName())) {
				// 设置定人定区送货区域
				taskEntity.setDeliveryRegionalNames(simDto
						.getDeliveryRegionalName());
			}
			// 任务状态
			taskEntity.setTaskStatus(ScheduleConstants.SCHEDULE_STATUS_ACTIVE);
		}
		return taskEntity;
	}

	/**
	 * 接送货排班定人定区信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午9:21:21
	 */
	private void deliveryScheduleZoneInfo(SimpleTruckScheduleDto simDto,
			TruckSchedulingTaskEntity taskEntity) {
		// 如果是接送货，则需要区域字段、车辆定人定区（区域）、是否带快递货、预计带货方数、班次
		RegionVehicleEntity zoneDto = null;
		RegionVehicleEntity deliveryDto = null;
		if (StringUtils.isNotBlank(simDto.getVehicleNo())) {
			// 查询车辆定人定区接货（区域）
			zoneDto = queryZoneCodeByVehicleNo(simDto.getVehicleNo());
			// 查询车辆定人定区送货（区域）
			deliveryDto = queryDeliveryCodeByVehicleNo(simDto.getVehicleNo());
		} else {
			throw new ShortScheduleException("车牌号为空", "车牌号为空");
		}
		// 保存车辆定人定区接货（区域）
		if (zoneDto != null && StringUtils.isNotBlank(zoneDto.getRegionCode())) {
			taskEntity.setZoneCode(zoneDto.getRegionCode());
		}
		// 保存车辆定人送货（区域）
		if (deliveryDto != null
				&& StringUtils.isNotBlank(deliveryDto.getRegionCode())) {
			taskEntity.setDeliveryAreaCode(deliveryDto.getRegionCode());
		}
		// 班次
		if (StringUtils.isNotBlank(simDto.getFrequencyNo())) {
			taskEntity.setFrequencyNo(simDto.getFrequencyNo());
		} else {
			throw new ShortScheduleException("班次为空", "班次为空");
		}
		if ((null != simDto.getExpectedBringVolume())) {
			taskEntity.setExpectedBringVolume(simDto.getExpectedBringVolume());
		}
		// 是否带快递货
		// if(StringUtils.equals("Y", simDto.getIsBringExpress())) {
		// 是否带快递货
		// taskEntity.setIsBringExpress("Y");
		// 预计带货方数
		// taskEntity.setExpectedBringVolume(simDto.getExpectedBringVolume());
		// }else {
		// taskEntity.setIsBringExpress("N");
		// taskEntity.setExpectedBringVolume(null);
		// }

	}

	/**
	 * 短途排班线路、班次、发车时间相关信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午9:19:30
	 */
	private void shortScheduleLineInfoAndFrequency(
			SimpleTruckScheduleDto simDto, TruckSchedulingTaskEntity taskEntity) {
		// 线路虚拟code不为空
		if (StringUtils.isNotBlank(simDto.getLineNo())) {
			// 线路信息
			LineEntity lineInfo = lineService.queryLineByVirtualCode(simDto
					.getLineNo());
			if (lineInfo != null) {
				String virtualCode = lineInfo.getVirtualCode();
				// 设置保存虚拟
				taskEntity.setLineNo(virtualCode);
			} else {
				ExpressLineEntity explineInfo = expresslineService
						.queryLineByVirtualCode(simDto.getLineNo());
				if (explineInfo != null) {
					String virtualCode = explineInfo.getVirtualCode();
					// 设置保存虚拟
					taskEntity.setLineNo(virtualCode);
				}
				throw new ShortScheduleException(simDto.getLineNo()
						+ "没有找到相关线路", "");
			}
		} else {
			throw new ShortScheduleException("线路为空", "线路为空");
		}
		// 班次
		if (StringUtils.isNotBlank(simDto.getFrequencyNo())) {
			taskEntity.setFrequencyNo(simDto.getFrequencyNo());
		} else {
			throw new ShortScheduleException("班次为空", "班次为空");
		}
		// 线路班次同时不为空
		if (StringUtils.isNotBlank(simDto.getLineNo())
				&& StringUtils.isNotBlank(simDto.getFrequencyNo())) {
			SimpleTruckScheduleDto departDto = queryDepartTimeByLineNoAndFrequenceNo(
					simDto.getLineNo(), simDto.getFrequencyNo());
			if (departDto != null
					&& StringUtils.isNotBlank(departDto.getDepartTimeShort())) {
				// 处理出发时间的转换
				String departTimeShort = departDto.getDepartTimeShort();
				// 出发时间必须如"08:30:00"
				if (StringUtils.isNotBlank(departTimeShort)
						&& departTimeShort.length() == ScheduleConstants.TIME_SUBSTRING_EIGHT) {
					// 取得计划日期
					if (simDto.getSchedulingDate() != null) {
						String schedulingDate = com.deppon.foss.util.DateUtils
								.convert(
										simDto.getSchedulingDate(),
										com.deppon.foss.util.DateUtils.DATE_FORMAT);
						// 将日期和时间组合起来
						schedulingDate = schedulingDate.concat(" ").concat(
								departTimeShort);
						// 转换为日期类型
						Date departTime = com.deppon.foss.util.DateUtils
								.convert(
										schedulingDate,
										com.deppon.foss.util.DateUtils.DATE_TIME_FORMAT);
						// 保存发车时间
						taskEntity.setDepartTime(departTime);
					} else {
						throw new ShortScheduleException("排班日期为空", "排班日期为空");
					}
				} else {
					throw new ShortScheduleException("发车时间不正确", "发车时间不正确");
				}
			} else {
				throw new ShortScheduleException("无相关线路、班次的发车时效信息",
						"无相关线路、班次的发车时效信息");
			}
		}

	}

	/**
	 * 根据车牌号补齐车辆相关信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午9:15:46
	 */
	private void makeTruckSchedulingTaskEntityVehicleInfo(
			SimpleTruckScheduleDto simDto, TruckSchedulingTaskEntity taskEntity) {
		taskEntity.setVehicleNo(simDto.getVehicleNo());
		// 根据车牌号查询车辆信息
		SimpleTruckScheduleDto carDto = this.queryCarInfoByVehicleNo(simDto
				.getVehicleNo());

		if (carDto != null) {
			// 首先校验是否本部门的车
			if (StringUtils.isNotBlank(carDto.getCarOwner())) {
				if (StringUtils.isNotBlank(simDto.getSchedulingDepartCode())) {
					// 如果传入的车队小组编号与车辆对应的直属部门不一致，则不允许使用该车排班
					if (!simDto.getSchedulingDepartCode().equals(
							carDto.getCarOwner())) {
						// 当前车辆不属于本车队小组
						taskEntity.setTruckFlag(FossConstants.NO);
					} else {
						taskEntity.setTruckFlag(FossConstants.YES);
					}
				} else {
					throw new ShortScheduleException("未传入车队小组编码", "");
				}
			} else {
				throw new ShortScheduleException("车牌号对应的车队为空", "");
			}
			// 补全车辆信息
			// 车型
			taskEntity.setTruckModel(carDto.getTruckModel());
			// 车辆所属车队
			taskEntity.setCarOwner(carDto.getCarOwner());
		} else {
			throw new ShortScheduleException("没有相关的车辆信息", "没有相关的车辆信息");
		}

	}

	/**
	 * 改变排班计划工作状态
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午9:09:23
	 */
	@Transactional
	private void changeTruckSchedulingPlanType(UserEntity user,
			TruckSchedulingEntity tempSchedule) {
		// 将当前的工作状态改变为"出车"
		if (tempSchedule != null) {
			// 更新状态为“出车”状态
			tempSchedule.setPlanType(ScheduleConstants.PLAN_TYPE_WORK);
			// 用户信息
			setUserInfo(tempSchedule.getClass().getName(), tempSchedule, user,
					ScheduleConstants.ACTION_TYPE_UPDATE);
			// 执行更新操作
			truckSchedulingDao.updateTruckScheduling(tempSchedule);
		}
	}

	/**
	 * 设置用户信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-20 下午3:02:50
	 */
	private void setUserInfo(String className, Object o, UserEntity user,
			String actionType) {
		if (user != null && o != null) {
			// 任务实体
			if (TruckSchedulingTaskEntity.class.getName().equals(className)) {
				TruckSchedulingTaskEntity taskEntity = (TruckSchedulingTaskEntity) o;
				// 新增
				if (ScheduleConstants.ACTION_TYPE_INSERT.equals(actionType)) {
					if (user.getEmployee() != null) {
						if (user.getEmployee().getDepartment() != null) {
							taskEntity.setCreateOrgCode(user.getEmployee()
									.getDepartment().getCode());
							taskEntity.setUpdateOrgCode(user.getEmployee()
									.getDepartment().getCode());
						}
						taskEntity.setCreateUserCode(user.getEmployee()
								.getEmpCode());
						taskEntity.setCreateUserName(user.getEmployee()
								.getEmpName());
						taskEntity.setUpdateUserCode(user.getEmployee()
								.getEmpCode());
						taskEntity.setUpdateUserName(user.getEmployee()
								.getEmpName());
					}
					taskEntity.setCreateTime(Calendar.getInstance().getTime());
					taskEntity.setUpdateTime(Calendar.getInstance().getTime());
				} else {
					// 更新
					if (user.getEmployee() != null) {
						if (user.getEmployee().getDepartment() != null) {
							taskEntity.setUpdateOrgCode(user.getEmployee()
									.getDepartment().getCode());
						}
						taskEntity.setUpdateUserCode(user.getEmployee()
								.getEmpCode());
						taskEntity.setUpdateUserName(user.getEmployee()
								.getEmpName());
					}
					taskEntity.setUpdateTime(Calendar.getInstance().getTime());
				}

			} else
			// 计划实体
			if (TruckSchedulingEntity.class.getName().equals(className)) {
				TruckSchedulingEntity schedulingEntity = (TruckSchedulingEntity) o;
				// 新增
				if (ScheduleConstants.ACTION_TYPE_INSERT.equals(actionType)) {
					if (user.getEmployee() != null) {
						if (user.getEmployee().getDepartment() != null) {
							schedulingEntity.setCreateOrgCode(user
									.getEmployee().getDepartment().getCode());
						}
						schedulingEntity.setCreateUserCode(user.getEmployee()
								.getEmpCode());
						schedulingEntity.setCreateUserName(user.getEmployee()
								.getEmpName());
					}
					schedulingEntity.setCreateTime(Calendar.getInstance()
							.getTime());
				} else {
					// 更新
					if (user.getEmployee() != null) {
						if (user.getEmployee().getDepartment() != null) {
							schedulingEntity.setUpdateOrgCode(user
									.getEmployee().getDepartment().getCode());
						}
						schedulingEntity.setUpdateUserCode(user.getEmployee()
								.getEmpCode());
						schedulingEntity.setUpdateUserName(user.getEmployee()
								.getEmpName());
					}
					schedulingEntity.setUpdateTime(Calendar.getInstance()
							.getTime());
				}

			} else
			// 如果是任务Dto
			if (TruckSchedulingTaskDto.class.getName().equals(className)) {
				TruckSchedulingTaskDto truckSchedulingTaskDto = (TruckSchedulingTaskDto) o;
				// 新增
				if (ScheduleConstants.ACTION_TYPE_INSERT.equals(actionType)) {
					if (user.getEmployee() != null) {
						if (user.getEmployee().getDepartment() != null) {
							truckSchedulingTaskDto.setCreateOrgCode(user
									.getEmployee().getDepartment().getCode());
							truckSchedulingTaskDto.setUpdateOrgCode(user
									.getEmployee().getDepartment().getCode());
						}
						truckSchedulingTaskDto.setCreateUserCode(user
								.getEmployee().getEmpCode());
						truckSchedulingTaskDto.setCreateUserName(user
								.getEmployee().getEmpName());
						truckSchedulingTaskDto.setUpdateUserCode(user
								.getEmployee().getEmpCode());
						truckSchedulingTaskDto.setUpdateUserName(user
								.getEmployee().getEmpName());
					}
					truckSchedulingTaskDto.setCreateTime(Calendar.getInstance()
							.getTime());
					truckSchedulingTaskDto.setUpdateTime(Calendar.getInstance()
							.getTime());
				} else {
					// 更新
					if (user.getEmployee() != null) {
						if (user.getEmployee().getDepartment() != null) {
							truckSchedulingTaskDto.setUpdateOrgCode(user
									.getEmployee().getDepartment().getCode());
						}
						truckSchedulingTaskDto.setUpdateUserCode(user
								.getEmployee().getEmpCode());
						truckSchedulingTaskDto.setUpdateUserName(user
								.getEmployee().getEmpName());
					}
					truckSchedulingTaskDto.setUpdateTime(Calendar.getInstance()
							.getTime());
				}
			} else
			// 如果是计划Dto
			if (TruckSchedulingDto.class.getName().equals(className)) {
				TruckSchedulingDto truckSchedulingDto = (TruckSchedulingDto) o;
				// 新增
				if (ScheduleConstants.ACTION_TYPE_INSERT.equals(actionType)) {
					if (user.getEmployee() != null) {
						if (user.getEmployee().getDepartment() != null) {
							truckSchedulingDto.setCreateOrgCode(user
									.getEmployee().getDepartment().getCode());
						}
						truckSchedulingDto.setCreateUserCode(user.getEmployee()
								.getEmpCode());
						truckSchedulingDto.setCreateUserName(user.getEmployee()
								.getEmpName());
					}
					truckSchedulingDto.setCreateTime(Calendar.getInstance()
							.getTime());
				} else {
					// 更新
					if (user.getEmployee() != null) {
						if (user.getEmployee().getDepartment() != null) {
							truckSchedulingDto.setUpdateOrgCode(user
									.getEmployee().getDepartment().getCode());
						}
						truckSchedulingDto.setUpdateUserCode(user.getEmployee()
								.getEmpCode());
						truckSchedulingDto.setUpdateUserName(user.getEmployee()
								.getEmpName());
					}
					truckSchedulingDto.setUpdateTime(Calendar.getInstance()
							.getTime());
				}

			}

		}
	}

	/**
	 * 修改计划状态，并删除对应任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-6 下午8:15:27
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#delteTaskAndModifySchedule(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@Override
	@Transactional
	public void delteTaskAndModifySchedule(SimpleTruckScheduleDto simDto,
			CurrentInfo user) throws ShortScheduleException {
		// 根据车队编号、年月日、司机编号、计划状态、排班类型（短途排班）、工作类别查询任务
		TruckSchedulingEntity tempSchedule = truckSchedulingDao.queryTruckSchedulingByParams(simDto);
			/**
			 *1/05  189284 同步近4天排班 
			 **/
		Boolean falage=false;/**默认不同步**/
		if(StringUtils.equals(tempSchedule.getPlanType(), ScheduleConstants.PLAN_TYPE_WORK)
				//&&StringUtils.equals(simDto.getPlanType(), ScheduleConstants.PLAN_TYPE_REST)
		  ){
			if (StringUtils.isNotBlank(simDto.getYmd())) {
				SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd");
				try {
					simDto.setSchedulingDate(df.parse(simDto.getYmd()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Long day = judgeTimeRange(simDto.getYmd(),new Date());
				/**排班时间 近四天**/
				if (0 <= day && day <= ConstantsNumberSonar.SONAR_NUMBER_3) {
					falage=true;
				}
			}
		}
		// 更新工作类别
		tempSchedule.setPlanType(simDto.getPlanType());
		// 执行更新操作
		truckSchedulingDao.updateTruckScheduling(tempSchedule);
		//  查询出排班任务 并删除定人定区接货区域
		SimpleTruckScheduleDto temp = new SimpleTruckScheduleDto();
		temp.setScheduleId(tempSchedule.getId());
		temp.setSchedulingStatus(ScheduleConstants.SCHEDULE_STATUS_ACTIVE);
		List<SimpleTruckScheduleDto> dtoList = truckSchedulingTaskDao.querySchedulingAndTask(temp);
		if (CollectionUtils.isNotEmpty(dtoList) && dtoList.size() > 0) {
			for (int i = 0; i < dtoList.size(); i++) {
				truckSchedulingTaskDao.deleteTaskspkpAreabyId(dtoList.get(i).getScheduleTaskId());
			}
		}
		// 删除对应的任务列表
		TruckSchedulingTaskEntity entity = new TruckSchedulingTaskEntity();
		entity.setSchedulingId(tempSchedule.getId());
		
		// 执行删除
		truckSchedulingTaskDao.deleteTruckSchedulingTask(entity);
		if (CollectionUtils.isNotEmpty(dtoList) && dtoList.size() > 0) {
			for (int i = 0; i < dtoList.size(); i++) {
				simDto.setVehicleNo(dtoList.get(i).getVehicleNo());
			    List<TruckSchedulingZoneEntity> list=truckSchedulingTaskDao.queryPkpAreaInfoByVehicleNo(simDto);
				simDto.setSchedulingZoneEntity(list);
				if(falage){
					sysTruckSchedulingToPkp(simDto,true);
				}
			}
		}
		
	}
	/**
	 * 通过"线路"、"班次"查询"发车时间"
	 * 
	 * @param lineNoCode
	 *            虚拟code
	 * @param frequencyNo
	 *            班次
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-5 下午3:36:01
	 */
	@Override
	public SimpleTruckScheduleDto queryDepartTimeByLineNoAndFrequenceNo(
			String lineNoCode, String frequencyNo)
			throws ShortScheduleException {
		SimpleTruckScheduleDto simDto = null;
		Integer frequencyNoLong = null;
		// 班次转换为long
		if (StringUtils.isNotBlank(frequencyNo)
				&& StringUtils.isNumeric(frequencyNo)) {
			frequencyNoLong = Integer.valueOf(frequencyNo);
		} else {
			throw new ShortScheduleException(frequencyNo + "班次不合法", "");
		}
		// 虚拟code
		String virtualCode = lineNoCode;
		// 线路信息
		if (StringUtils.isNotBlank(virtualCode)) {
			// 查询发车时效信息
			DepartureStandardEntity departureIfno = departureStandardService
					.queryDepartureStandardByLineVirtualCodeAndSequence(
							virtualCode, frequencyNoLong);
			// 获取发车时间
			if (departureIfno != null
					&& StringUtils.isNotBlank(departureIfno.getLeaveTime())) {

				simDto = new SimpleTruckScheduleDto();
				// 解析发车时间
				String departTimeShort = departureIfno
						.getLeaveTime()
						.substring(ScheduleConstants.TIME_SUBSTRING_ZERO,
								ScheduleConstants.TIME_SUBSTRING_TWO)
						.concat(ScheduleConstants.COLON)
						.concat(departureIfno.getLeaveTime().substring(
								ScheduleConstants.TIME_SUBSTRING_TWO,
								ScheduleConstants.TIME_SUBSTRING_FOUR))
						.concat(ScheduleConstants.TIME_TAIL);
				// 设置发车时间
				simDto.setDepartTimeShort(departTimeShort);

			}
		} else {
			// 异常
			throw new ShortScheduleException(lineNoCode + "线路虚拟编码为空", "");
		}
		return simDto;
	}

	/**
	 * 调用综合接口查询定人定区信息
	 * 
	 * @param vehicleNo
	 *            车牌号
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-12 上午11:45:47
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#queryZoneCode(java.lang.String)
	 */
	@Override
	public RegionVehicleEntity queryZoneCodeByVehicleNo(String vehicleNo)
			throws ShortScheduleException {
		// 区域列表
		List<RegionVehicleEntity> vehicleList = null;
		// 车牌列表
		List<String> vehicleNoList = new ArrayList<String>();
		// 调用综合接口查询定人定区信息
		if (StringUtils.isNotBlank(vehicleNo)) {
			// 车牌
			vehicleNoList.add(vehicleNo);
			// 车辆列表
			vehicleList = regionalVehicleService.queryRegionVehiclesByParam(
					vehicleNoList, null, null);
			// 为空则返回空
			if (CollectionUtils.isEmpty(vehicleList) || vehicleList.size() <= 0) {
				return null;
			}
			// 不为空则 循环
			for (RegionVehicleEntity temp : vehicleList) {
				if (StringUtils.equals(temp.getRegionType(), "PK")) {
					return temp;
				}
			}
			return null;
		} else {
			return null;
		}
	}

	/**
	 * 调用综合接口查询定人定区送货区域信息
	 * 
	 * @param vehicleNo
	 *            车牌号
	 * @author 218442-foss-zhuyunrong
	 * @date 2015-04-20 上午11:45:47
	 * @see
	 */
	@Override
	public RegionVehicleEntity queryDeliveryCodeByVehicleNo(String vehicleNo)
			throws ShortScheduleException {
		// 区域列表
		List<RegionVehicleEntity> vehicleList = null;
		// 车牌列表
		List<String> vehicleNoList = new ArrayList<String>();
		// 调用综合接口查询定人定区信息
		if (StringUtils.isNotBlank(vehicleNo)) {
			// 车牌
			vehicleNoList.add(vehicleNo);
			// 车辆列表
			vehicleList = regionalVehicleService.queryRegionVehiclesByParam(
					vehicleNoList, null, null);
			// 为空则返回空
			if (CollectionUtils.isEmpty(vehicleList) || vehicleList.size() <= 0) {
				return null;
			}
			// 不为空则 循环
			for (RegionVehicleEntity temp : vehicleList) {
				if (StringUtils.equals(temp.getRegionType(), "DE")) {
					return temp;
				}
			}
			// 如果没有绑定送货小区则 返货接货小区
			return null;
		} else {
			return null;
		}
	}

	/**
	 * 车牌号查询车辆信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-12 下午3:59:30
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#queryCarInfoByVehicleNo(java.lang.String)
	 */
	@Override
	public SimpleTruckScheduleDto queryCarInfoByVehicleNo(String vehicleNo)
			throws ShortScheduleException {
		// 排班信息
		SimpleTruckScheduleDto simDto = null;
		// 车辆信息
		VehicleAssociationDto vehicleAssociationDto = vehicleService
				.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
		// 查出的车辆需要是公司车
		if (vehicleAssociationDto != null
				&& ComnConst.ASSETS_OWNERSHIP_TYPE_COMPANY
						.equals(vehicleAssociationDto.getVehicleOwnershipType())) {
			// 排班计划信息
			simDto = new SimpleTruckScheduleDto();
			// 车辆直属部门（或车队小组）名称
			simDto.setCarOwner(vehicleAssociationDto
					.getVehicleOrganizationCode());
			// 车辆直属部门编码
			simDto.setCarOwnerName(vehicleAssociationDto
					.getVehicleOrganizationName());
			// 车型
			if (StringUtils.isNotBlank(vehicleAssociationDto
					.getVehicleLengthCode())) {
				// 车型编码
				simDto.setTruckModel(vehicleAssociationDto
						.getVehicleLengthCode());
				// 车型名
				simDto.setTruckModelValue(vehicleAssociationDto
						.getVehicleLengthName());
			}

		}
		return simDto;
	}

	/**
	 * 通过司机编号查询司机信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-13 下午2:03:24
	 */
	@Override
	public DriverAssociationDto queryOwnDriveByDriverCode(String driverCode)
			throws OwnDriverException {
		// 查询司机
		return ownDriverService
				.queryOwnDriverAssociationDtoByDriverCode(driverCode);
	}

	/**
	 * 导出排班表信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-13 下午5:20:53
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#exportExcelStream(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@Override
	public InputStream exportExcelStream(SimpleTruckScheduleDto simDto)
			throws ShortScheduleException {
		InputStream excelStream = null;
		// 定义表头
		String[] rowHeads = null;
		// 选择表头
		if (simDto != null
				&& StringUtils.isNotBlank(simDto.getSchedulingtype())) {
			// 接送货排班
			if (ScheduleConstants.SCHEDULE_TYPE_DELIVERY.equals(simDto
					.getSchedulingtype())) {
				rowHeads = ScheduleConstants.PKP_ROW_HEADERS;
			} else
			// 短途排班
			if (ScheduleConstants.SCHEDULE_TYPE_SHORTSCHEDULE.equals(simDto
					.getSchedulingtype())) {
				rowHeads = ScheduleConstants.TFR_ROW_HEADERS;
			}
		} else {
			// 异常
			throw new ShortScheduleException("排班类型为空", "排班类型为空");
		}
		SheetData sheetData = new SheetData();
		// 头数据
		sheetData.setRowHeads(rowHeads);
		// 查询出的所有数据
		List<List<String>> rowList = new ArrayList<List<String>>();
		// 查询出需要导出的排班计划和任务
		List<ScheduleExcelDto> simDtos = querySchedulingForExport(simDto);
		// 循环转换
		List<String> columnList = null;
		for (ScheduleExcelDto dto : simDtos) {
			columnList = new ArrayList<String>();
			// 排班类型
			columnList.add(dto.getSchedulingType());
			// 部门编号
			columnList.add(dto.getDriverOrgCode());
			// 部门名称
			columnList.add(dto.getDriverOrgName());
			// 司机编号
			columnList.add(dto.getDriverCode());
			// 司机姓名
			columnList.add(dto.getDriverName());
			// 日期
			columnList.add(DateUtils.convert(dto.getSchedulingDate(),
					DateUtils.DATE_FORMAT));
			// 任务类型编号（工作类别）
			columnList.add(dto.getPlanType());
			// 排班类型（工作类别）
			columnList.add(dto.getPlanTypeName());
			if (StringUtils.isNotBlank(dto.getSchedulingType())) {
				// 接送货排班
				if (ScheduleConstants.SCHEDULE_TYPE_DELIVERY.equals(dto
						.getSchedulingType())) {
					// 任务序号
					columnList.add(dto.getTaskNo());
					// 车牌号
					columnList.add(dto.getVehicleNo());
				} else
				// 短途排班
				if (ScheduleConstants.SCHEDULE_TYPE_SHORTSCHEDULE.equals(dto
						.getSchedulingType())) {
					// 任务序号
					columnList.add(dto.getTaskNo());
					// 车牌号
					columnList.add(dto.getVehicleNo());
					// 线路简码
					columnList.add(dto.getLineNoCode());
					// 线路
					columnList.add(dto.getLineName());
					// 班次
					columnList.add(dto.getFrequencyNo());
				}
			}
			// 加入列表
			rowList.add(columnList);
		}
		sheetData.setRowList(rowList);
		// 导出工具
		ExcelExport excelExportUtil = new ExcelExport();
		// 导出成文件
		excelStream = excelExportUtil.inputToClient(excelExportUtil
				.exportExcel(sheetData, "排班",
						ScheduleConstants.EXCEL_DEFAULT_SHEET_SIZE));
		// 返回
		return excelStream;
	}

	/**
	 * 查询出需要导出的排班计划和任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-14 下午3:22:22
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#querySchedulingForExport(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@Override
	public List<ScheduleExcelDto> querySchedulingForExport(
			SimpleTruckScheduleDto truckSchedulingDto)
			throws ShortScheduleException {
		// 查询上班的数据
		List<String> list = new ArrayList<String>();
		// 值班
		list.add(ScheduleConstants.PLAN_TYPE_ON_DUTY);
		// 培训
		list.add(ScheduleConstants.PLAN_TYPE_TRAIN);
		// 上班
		list.add(ScheduleConstants.PLAN_TYPE_WORK);
		// 休息
		list.add(ScheduleConstants.PLAN_TYPE_REST);
		// 离岗
		list.add(ScheduleConstants.PLAN_TYPE_UNDERGO);
		// 列表
		truckSchedulingDto.setList(list);
		// 导出列表
		List<ScheduleExcelDto> excelList = truckSchedulingTaskDao
				.querySchedulingForExport(truckSchedulingDto);
		// 如果不为空，则需要进行转换
		String planTypeName = null;
		if (CollectionUtils.isNotEmpty(excelList)) {
			for (ScheduleExcelDto excelDto : excelList) {
				// 查询司机信息
				DriverAssociationDto driverInfo = this
						.queryOwnDriveByDriverCode(excelDto.getDriverCode());
				if (driverInfo != null) {
					// 补全司机信息
					excelDto.setDriverName(driverInfo.getDriverName());
					// 补全司机部门信息
					// 所属直属部门编码
					excelDto.setDriverOrgName(driverInfo
							.getDriverOrganizationName());
					// 所属直属部门编码
					excelDto.setDriverOrgCode(driverInfo
							.getDriverOrganizationCode());
				}
				// 任务类型名转换
				planTypeName = planTypeMap().get(excelDto.getPlanType());
				excelDto.setPlanTypeName(planTypeName);
				// 通过线路虚拟code补全线路信息
				if (StringUtils.isNotBlank(excelDto.getLineNo())) {
					LineEntity lienInfo = lineService
							.queryLineByVirtualCode(excelDto.getLineNo());
					if (lienInfo != null) {
						// 线路名称
						excelDto.setLineName(lienInfo.getLineName());
						// 线路简码
						excelDto.setLineNoCode(lienInfo.getSimpleCode());
					} else {
						ExpressLineEntity explineInfo = expresslineService
								.queryLineByVirtualCode(excelDto.getLineNo());
						if (explineInfo != null) {
							// 线路名称
							excelDto.setLineName(explineInfo.getLineName());
							// 线路简码
							excelDto.setLineNoCode(explineInfo.getSimpleCode());
						}
					}
				}
				// 班次
				if (StringUtils.isNotBlank(excelDto.getFrequencyNo())) {
					excelDto.setFrequencyNo(excelDto.getFrequencyNo());
				}
			}
		}
		return excelList;
	}

	/**
	 * 工作类别
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-14 下午3:40:29
	 */
	private Map<String, String> planTypeMap() {
		Map<String, String> planTypes = new HashMap<String, String>();
		// 值班
		planTypes.put(ScheduleConstants.PLAN_TYPE_ON_DUTY,
				ScheduleConstants.PLAN_TYPE_ON_DUTY_TXT);
		// 修改
		planTypes.put(ScheduleConstants.PLAN_TYPE_REST,
				ScheduleConstants.PLAN_TYPE_REST_TXT);
		// 培训
		planTypes.put(ScheduleConstants.PLAN_TYPE_TRAIN,
				ScheduleConstants.PLAN_TYPE_TRAIN_TXT);
		// 离岗
		planTypes.put(ScheduleConstants.PLAN_TYPE_UNDERGO,
				ScheduleConstants.PLAN_TYPE_UNDERGO_TXT);
		// 未知
		planTypes.put(ScheduleConstants.PLAN_TYPE_UNKNOWN,
				ScheduleConstants.PLAN_TYPE_UNKNOWN_TXT);
		// 出车
		planTypes.put(ScheduleConstants.PLAN_TYPE_WORK,
				ScheduleConstants.PLAN_TYPE_WORK_TXT);
		return planTypes;
	}

	/**
	 * 查询车队小组、当天、线路、班次是否已经存在本班次的排班
	 * 
	 * @param schedulingStatus
	 *            计划状态（必填）
	 * @param schedulingtype
	 *            计划类型（必填）
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-15 上午9:42:32
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#queryHasLineNoFrequencyNoCurrentDay(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity)
	 */
	@Override
	public List<ScheduleExcelDto> queryHasLineNoFrequencyNoCurrentDay(
			SimpleTruckScheduleDto simpleTruckScheduleDto)
			throws ShortScheduleException {
		// 不空
		if (simpleTruckScheduleDto != null
				&& simpleTruckScheduleDto.getSchedulingDate() != null) {
			// 转换设置
			simpleTruckScheduleDto.setYmd(DateUtils.convert(
					simpleTruckScheduleDto.getSchedulingDate(),
					DateUtils.DATE_FORMAT));
		}
		// 查询
		return truckSchedulingTaskDao
				.queryHasLineNoFrequencyNoCurrentDay(simpleTruckScheduleDto);
	}

	/**
	 * 根据ID列表删除计划任务列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-16 下午3:31:21
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#deleteTasksByScheduleTaskIds(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@Override
	public void deleteTasksByScheduleTaskIds(
			SimpleTruckScheduleDto truckScheduling, CurrentInfo user) {
		List<String> readyDeletescheduleIds = new ArrayList<String>();
		// 根据排班任务ID 列表删除排班任务
		if (truckScheduling != null) {
			if (CollectionUtils.isNotEmpty(truckScheduling.getList())) {
				// 查询排班任务ID对应的计划数据
				List<TruckSchedulingEntity> scheduleList = truckSchedulingDao
						.queryTruckScheduleByTaskIds(truckScheduling);
				// 将需要删除任务对应的计划ID 加入列表，查看是否还有引用的数据，如果无引用的数据，则将计划改变为“未知”
				for (TruckSchedulingEntity tempSchedule : scheduleList) {
					readyDeletescheduleIds.add(tempSchedule.getId());
				}
				// 删除份工作日排班接货区域
				for (int i = 0; i < truckScheduling.getList().size(); i++) {
					truckSchedulingTaskDao.deleteTaskspkpAreabyId(truckScheduling.getList().get(i));
				}
				// 执行删除任务操作
				truckSchedulingTaskDao.deleteTasksByScheduleTaskIds(truckScheduling);
			}
			// 如果选择删除的不为空，则加入删除队列
			if (CollectionUtils.isNotEmpty(truckScheduling.getScheduleIds())) {
				// 加入待删除列表
				readyDeletescheduleIds.addAll(truckScheduling.getScheduleIds());
			}
			// 如果删除的是直接为没有任务数据的计划数据，则需要将本列表的所有工作类型删除为未知状态
			if (CollectionUtils.isNotEmpty(readyDeletescheduleIds)) {
				// 执行删除
				truckSchedulingService.deleteTruckScheduling(readyDeletescheduleIds, user);
			}
		}

	}

	/**
	 * 根据线路列表和日期 ，查询短途排班对应的排班列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-18 下午1:41:04
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#queryFrequencyNosBylineVirtualCode(java.util.ArrayList,
	 *      java.util.Date)
	 */
	@Override
	public List<SimpleTruckScheduleDto> queryFrequencyNosBylineVirtualCode(
			List<String> lineVirtualCode, Date planDate) {
		if (CollectionUtils.isNotEmpty(lineVirtualCode) && planDate != null) {
			SimpleTruckScheduleDto dto = new SimpleTruckScheduleDto();
			// 线路虚拟编码列表
			dto.setList(lineVirtualCode);
			// 排班日期
			dto.setSchedulingDate(planDate);
			// 可用
			dto.setSchedulingStatus(ScheduleConstants.SCHEDULE_STATUS_ACTIVE);
			// 类型为短途排班
			dto.setSchedulingtype(ScheduleConstants.SCHEDULE_TYPE_SHORTSCHEDULE);
			// 工作类别为出车
			dto.setPlanType(ScheduleConstants.PLAN_TYPE_WORK);
			// 执行查询
			return truckSchedulingTaskDao
					.queryFrequencyNosBylineVirtualCode(dto);
		} else {
			return null;
		}
	}

	/**
	 * 通过车牌号及约车受理时间，读取短途排版表中的司机，如果获取多个司机
	 * 
	 * @param ymd
	 *            排班日期
	 * 
	 * @param vehicleNo
	 *            车牌号
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-8 下午2:52:07
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#queryTaskByVehicleNoAndDate(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@Override
	public List<SimpleTruckScheduleDto> queryTaskByVehicleNoAndDate(
			SimpleTruckScheduleDto simpleTruckScheduleDto) {
		// 判空
		if (simpleTruckScheduleDto != null) {
			if (StringUtils.isBlank(simpleTruckScheduleDto.getVehicleNo())) {
				throw new ShortScheduleException("车牌号不能为空", "");
			}
			if (StringUtils.isBlank(simpleTruckScheduleDto.getYmd())) {
				throw new ShortScheduleException("排班日期不能为空", "");
			}
			return truckSchedulingTaskDao
					.queryTaskByVehicleNoAndDate(simpleTruckScheduleDto);
		}
		return null;
	}

	/**
	 * 通过小区编码及日期查询是否节假日，排班车牌号
	 * 
	 * @param zoneCode
	 *            小区编码
	 * @param Ymd
	 *            日期
	 * @author foss-zyr
	 * @date 2015-5-21 11:04:07
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#queryTaskByZoneCodeAndDate(String,String)
	 */
	@Override
	public SimpleTruckScheduleDto queryTaskByZoneCodeAndDate(String zoneCode,
			String ymd) {
		if (StringUtils.isBlank(zoneCode)) {
			throw new ShortScheduleException("小区不能为空", "小区不能为空");
		}
		if (StringUtils.isBlank(ymd)) {
			throw new ShortScheduleException("日期不能为空", "日期不能为空");
		}
		SimpleTruckScheduleDto sDto = new SimpleTruckScheduleDto();
		SimpleTruckScheduleDto qDto = truckSchedulingTaskDao
				.queryTaskByZoneCodeAndDate(zoneCode, ymd);
		if (qDto == null) {
			sDto.setIsHoliday("N");
		} else {
			sDto = qDto;
			sDto.setIsHoliday("Y");
		}
		return sDto;
	}

	/**
	 * @Title: delteTask
	 * @Description: 删除单个任务
	 * @param simDto
	 * @param user
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#deleteTask(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 * @author: ibm-zhangyixin
	 * @throws Date
	 *             :2013-5-23下午3:21:12
	 */
	@Transactional
	@Override
	public void deleteTask(String scheduleTaskId) {
		// 先删除定人定区接货区域 
		truckSchedulingTaskDao.deleteTaskspkpAreabyId(scheduleTaskId);
		// 执行删除
		truckSchedulingTaskDao.delteTask(scheduleTaskId);
	}

	/**
	 * 根据区域编号和时间查询当日绑定的车辆
	 * 
	 * @author foss-heyongdong
	 * @date 2014年5月8日 14:56:14
	 * @param zoneCode
	 * @param queryTime
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#queryVehicleByZoneCode(java.lang.String,
	 *      java.util.Date)
	 */
	@Override
	public TruckSchedulingZoneEntity queryVehicleByZoneCode(String zoneCode,
			Date queryTime) {
		String cqueryTime = com.deppon.foss.util.DateUtils.convert(queryTime,
				com.deppon.foss.util.DateUtils.DATE_FORMAT);
		return truckSchedulingTaskDao.queryVehicleByZoneCode(zoneCode,
				cqueryTime);
	}

	/**
	 * 根据车牌号和时间查询当日车辆绑定司机
	 * 
	 * @author foss-heyongdong
	 * @date 2014年5月8日 14:56:14
	 * @param vehicleNo
	 * @param queryTime
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#queryDriverByVehicle(java.lang.String,
	 *      java.util.Date)
	 */
	@Override
	public TruckSchedulingZoneEntity queryDriverByVehicle(String vehilceNo,
			Date queryTime) {
		String cqueryTime = com.deppon.foss.util.DateUtils.convert(queryTime,
				com.deppon.foss.util.DateUtils.DATE_FORMAT);
		return truckSchedulingTaskDao.queryDriverByVehicle(vehilceNo,
				cqueryTime);
	}

	/**
	 * 根据车牌号和时间查询车辆净空，车辆载重，是否带快递货， 带快递货方数，班次，车型
	 * 
	 * @author foss-zyr
	 * @date 2015年5月12日 09:56:14
	 * @param vehicleNo
	 * @param queryTime
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#queryTruckByVehicle(java.lang.String,
	 *      java.util.Date)
	 */
	@Override
	public SimpleTruckScheduleDto queryTruckByVehicle(String vehilceNo,
			String queryTime) throws ShortScheduleException {
		SimpleTruckScheduleDto simDto = new SimpleTruckScheduleDto();
		// 排班信息
		SimpleTruckScheduleDto simpleTruckScheduleDto = truckSchedulingTaskDao
				.queryTruckByVehicle(vehilceNo, queryTime);
		if (simpleTruckScheduleDto != null) {
			simDto.setIsBringExpress(simpleTruckScheduleDto.getIsBringExpress());
			simDto.setExpectedBringVolume(simpleTruckScheduleDto
					.getExpectedBringVolume());
			simDto.setFrequencyNo(simpleTruckScheduleDto.getFrequencyNo());
		}
		// 车辆信息
		VehicleAssociationDto vehicleAssociationDto = vehicleService
				.queryVehicleAssociationDtoByVehicleNo(vehilceNo);
		if (vehicleAssociationDto != null) {
			// 车辆净空
			simDto.setSelfVolume(vehicleAssociationDto.getVehicleSelfVolume());
			// 车辆载重
			simDto.setDeadLoad(vehicleAssociationDto.getVehicleDeadLoad());
			// 车型
			simDto.setTruckModelValue(vehicleAssociationDto
					.getVehicleLengthName());
		}
		return simDto;
	}

	/**
	 * 根据车牌号和送货时间查询车辆净空，车辆载重,
	 * 带快递货方数，班次，车型,出车任务，预计出车时间，带货部门code，转货部门code,预计二次出车时间 带货部门名称，转货部门名称
	 * 
	 * @author gongjp
	 * @date 2015年-07月27日 10:30
	 * @param vehicleNo
	 * @param deliverGoodsTime
	 */
	@Override
	public SimpleTruckScheduleDto queryTruckByVehicleAndDiliverGoodsTime(
			String vehilceNo, String diliverGoodsTime)
			throws ShortScheduleException {
		SimpleTruckScheduleDto simDto = new SimpleTruckScheduleDto();
		// 排班信息
		SimpleTruckScheduleDto simpleTruckScheduleDto = truckSchedulingTaskDao
				.queryTruckByVehicleAndDiliverGoodsTime(vehilceNo,
						diliverGoodsTime);
		if (simpleTruckScheduleDto != null) {
			simDto.setIsBringExpress(simpleTruckScheduleDto.getIsBringExpress());
			simDto.setExpectedBringVolume(simpleTruckScheduleDto
					.getExpectedBringVolume());
			simDto.setFrequencyNo(simpleTruckScheduleDto.getFrequencyNo());
			// 出车任务
			simDto.setDispatchVehicleTask(simpleTruckScheduleDto
					.getDispatchVehicleTask());
			// 预计出车时间
			simDto.setExpectedDispatchVehicleTime(simpleTruckScheduleDto
					.getExpectedDispatchVehicleTime());
			// 带货部门code
			simDto.setTakeGoodsDepartment(simpleTruckScheduleDto
					.getTakeGoodsDepartment());
			// 转货部门code
			simDto.setTransferGoodsDepartment(simpleTruckScheduleDto
					.getTransferGoodsDepartment());
			// 预计二次出车时间
			simDto.setIsTheTwoTripTime(simpleTruckScheduleDto
					.getIsTheTwoTripTime());
			// 带货部门名称
			simDto.setTakeGoodsDepartmentName(simpleTruckScheduleDto
					.getTakeGoodsDepartmentName());
			// 转货部门名称
			simDto.setTransferGoodsDepartmentName(simpleTruckScheduleDto
					.getTransferGoodsDepartmentName());
		}
		// 车辆信息
		VehicleAssociationDto vehicleAssociationDto = vehicleService
				.queryVehicleAssociationDtoByVehicleNo(vehilceNo);
		if (vehicleAssociationDto != null) {
			// 车辆净空
			simDto.setSelfVolume(vehicleAssociationDto.getVehicleSelfVolume());
			// 车辆载重
			simDto.setDeadLoad(vehicleAssociationDto.getVehicleDeadLoad());
			// 车型
			simDto.setTruckModelValue(vehicleAssociationDto
					.getVehicleLengthName());
		}
		return simDto;

	}

	/**
	 * 根据车牌号查询非工作日最新的绑定信息
	 * 
	 * @author foss-heyongdong
	 * @date 2014年5月21日 10:43:50
	 * @param SimpleTruckScheduleDto
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService#queryPkpAreaInfoByVehicleNo(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@Override
	public List<TruckSchedulingZoneEntity> queryPkpAreaInfoByVehicleNo(
			SimpleTruckScheduleDto smDto) {
		if (smDto == null) {
			return null;
		}

		return truckSchedulingTaskDao.queryPkpAreaInfoByVehicleNo(smDto);
	}

	/**
	 * 通过车牌号和时间查询接送货小区code，接送货类型，接送货区域名称 -
	 * 
	 * @author gongjp
	 * @date 2015-7-31 11:04:07
	 */
	public List<TruckSchedulingZoneEntity> queryReceivingAreaByVehicleAndDate(
			String vehicleNo, String ymd) {
		List<TruckSchedulingZoneEntity> list = truckSchedulingTaskDao
				.queryReceivingAreaByVehicleAndDate(vehicleNo, ymd);
		return list;
	}
}