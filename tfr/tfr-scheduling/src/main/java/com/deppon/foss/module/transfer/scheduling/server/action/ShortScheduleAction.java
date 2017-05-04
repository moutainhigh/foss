/**
 *  initial comments.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/action/ShortScheduleAction.java
 * 
 *  FILE NAME     :ShortScheduleAction.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.action
 * FILE    NAME: ShortScheduleAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnDriverException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.define.ScheduleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingZoneEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExcelHeaderDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.GridHeaderDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ScheduleExcelDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckScheduleGridDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.ShortScheduleException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.DateUtils;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ExcelUtil;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ExportExcel;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.XlsImpUtil;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.ShortScheduleVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
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
 * 2.4 线路：公共选择器，读取自发车标准基础资料；
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
 * @date 2012-10-25 上午9:16:08
 */
public class ShortScheduleAction extends AbstractAction {

	private static final long serialVersionUID = -7865197230356123500L;
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ShortScheduleAction.class);

	/**
	 * 人员月排班表Service
	 */
	private ITruckSchedulingService truckSchedulingService;
	/**
	 * 排班任务Service
	 */
	private ITruckSchedulingTaskService truckSchedulingTaskService;
	/**
	 * 业务锁Service
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 部门 复杂查询 service
	 */
	IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 获取组织信息service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 排班前台VO
	 */
	private ShortScheduleVo vo = new ShortScheduleVo();
	/**
	 * 导入文件
	 */
	private File uploadFile;
	/**
	 * 导出Excel 文件流
	 */
	transient InputStream excelStream;
	/**
	 * 导出Excel 文件名
	 */
	private String fileName;
	/**
	 * 导入文件名
	 */
	private String uploadFileFileName;
	
	/**
	 * @Title: queryTopFleetOrgCode 
	 * @Description: 根据当前部门找顶级车队 
	 * @return    
	 * @return String    返回类型 
	 * queryTopFleetOrgCode
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-6-3下午9:23:47
	 */
	@JSON
	public String queryTopFleetOrgCode() {
		try {
			OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoComplexService.getTopFleetByCode(FossUserContext.getCurrentDeptCode());
			if(orgAdministrativeInfo == null) {
				return returnError(new TfrBusinessException("找不到顶级车队!"));
			}
			vo.setTopFleetOrgCode(orgAdministrativeInfo.getCode());
		} catch (Exception e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
		
		return returnSuccess();
	}

	/**
	 * 
	 * 查询制定排班（较复杂制定排班视图）
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-10-26 下午4:44:57
	 * 
	 */
	@JSON
	public String queryTruckSchedulingList() {
		try {
			// 计划
			TruckSchedulingEntity tsEntity = null;
			// 不空
			if (vo != null) {
				// 获取
				tsEntity = vo.getTsEntity();
				// 查询排班表
				List<TruckScheduleGridDto> tsDtos = truckSchedulingService.queryTruckSchedulingList(tsEntity,
						this.getLimit(), this.getStart());
				// 排班表格
				if (tsDtos != null) {
					// 设置
					vo.setTsDtos(tsDtos);
					// 总条数s
					this.setTotalCount(truckSchedulingService.queryTruckSchedulingListTotal(tsEntity));
				}
			}
			// 成功
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (ShortScheduleException e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e);
			// 异常
		} catch (Exception e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}

	public String querybeExsitScheduling(){
		try {
			// 计划
			TruckSchedulingEntity tsEntity = null;
			// 不空
			if (vo != null) {
					// 获取
					tsEntity = vo.getTsEntity();
					// 总条数s
					vo.setTatolScheduling(truckSchedulingService.queryTruckSchedulingListTotal(tsEntity));
			}
			
			// 成功
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (ShortScheduleException e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e);
			// 异常
		} catch (Exception e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}
	/**
	 * 
	 * 导出排班（较复杂制定排班视图）
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-10-26 下午4:44:57
	 */
	public String exportTruckSchedulingList() {
		// 输出流
		ByteArrayOutputStream out = null;
		try {
			TruckSchedulingEntity tsEntity = null;
			// 判空
			if (vo != null) {
				SimpleTruckScheduleDto simDto = vo.getSimDto();
				// 判空
				if (simDto != null) {
					tsEntity = new TruckSchedulingEntity();
					// 年月
					tsEntity.setYearMonth(simDto.getYearMonth());
					// 排班类型
					tsEntity.setSchedulingType(simDto.getSchedulingtype());
					// 排班部门
					tsEntity.setSchedulingDepartCode(simDto.getSchedulingDepartCode());
					// 状态
					tsEntity.setSchedulingStatus(simDto.getSchedulingStatus());
					// 文件名
					StringBuffer fileNameBuf = new StringBuffer()
							.append(truckSchedulingService.queryDepartment(simDto.getSchedulingDepartCode()))
							.append("部门").append(simDto.getYearMonth())
							.append(ScheduleConstants.EXPORT_EXCEL_FILE_NAME_COMPLEX);
					// 导出文件名
					fileName = URLEncoder.encode(fileNameBuf.toString(), "UTF-8");
					// 查询排班表
					List<TruckScheduleGridDto> tsDtos = truckSchedulingService.queryTruckSchedulingList(tsEntity);
					// 循环获取线路和车牌信息
					truckSchedulingService.queryLineInfoAndVehicleNo(tsDtos, tsEntity);
					// 导出排班
					ExportExcel tool = new ExportExcel();
					// 表头
					List<ExcelHeaderDto> header = ExcelUtil.calcHeader(tsEntity.getYearMonth()
							+ ScheduleConstants.YEAR_MONTH_TAIL);
					// 输出流
					out = new ByteArrayOutputStream();
					// 构建输出流
					tool.exportExcel(header, tsDtos, out, fileNameBuf.toString());
					// 关闭
					out.close();
					// 写入EXCEL
					byte[] excelBytes = out.toByteArray();
					excelStream = new ByteArrayInputStream(excelBytes, 0, excelBytes.length);
				}
			}
			// 成功
			return super.returnSuccess();
		} catch (ShortScheduleException e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e);
		} catch (Exception e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 
	 * 
	 * 简单的查询排班表(简单查询视图)
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-10-30 下午5:00:22
	 * 
	 */
	@JSON
	public String querySimpleTruckSchedulingList() {
		try {
			SimpleTruckScheduleDto simDto = vo.getSimDto();
			// 判空
			if (simDto != null) {
				// 查询可用的,短途排班
				simDto.setSchedulingStatus(ScheduleConstants.SCHEDULE_STATUS_ACTIVE);
			}
			// 查询结果
			List<SimpleTruckScheduleDto> simDtos = truckSchedulingTaskService.querySchedulingAndTask(simDto,
					this.getLimit(), this.getStart());
			// 判空
			if (simDtos != null) {
				vo.setSimDtos(simDtos);
			}
			// 条数
			Long count = truckSchedulingTaskService.queryCount(simDto);
			this.setTotalCount(count);
			// 成功
			// 成功
			return super.returnSuccess();
		} catch (ShortScheduleException e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e);
		} catch (Exception e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 动态生成表头
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-10-25 上午9:43:35
	 * 
	 */
	@JSON
	public String queryGridHeader() {
		try {
			List<GridHeaderDto> gridHeaderFields = new ArrayList<GridHeaderDto>();
			// 获取本月最后一天的数值
			Calendar cdr = Calendar.getInstance();
			// 判空
			if (vo != null && vo.getSimDto() != null && vo.getSimDto().getYmd() != null) {
				cdr.setTime(com.deppon.foss.util.DateUtils.convert(vo.getSimDto().getYmd(),
						com.deppon.foss.util.DateUtils.DATE_FORMAT));
			}
			int actualMaximum = cdr.getActualMaximum(Calendar.DATE);
			// 循环取日期
			GridHeaderDto ghDto = null;
			for (int day = 1; day <= actualMaximum; day++) {
				// 设置当天日期
				cdr.set(cdr.get(Calendar.YEAR), cdr.get(Calendar.MONTH), day);
				// 获取日期，星期，等数据
				ghDto = new GridHeaderDto();
				ghDto.setDataIndex("date" + cdr.get(Calendar.DAY_OF_MONTH));
				ghDto.setRendererData("date" + cdr.get(Calendar.DAY_OF_MONTH));
				ghDto.setDataType("String");
				// 星期
				ghDto.setDayText(DateUtils.convertDayOfWeek(cdr.get(Calendar.DAY_OF_WEEK)));
				// 日期
				ghDto.setHeaderDate(cdr.get(Calendar.DAY_OF_MONTH));
				gridHeaderFields.add(ghDto);
			}

			// 传回给VO
			// 判空
			if (vo != null) {
				vo.setGridHeaderFields(gridHeaderFields);
			}

			// 成功
			return super.returnSuccess();
		} catch (ShortScheduleException e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e);
		} catch (Exception e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 
	 * 根据车队小组、年月日、司机查询排班任务,用于点击单元格时,查询任务所用
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-5 下午3:36:01
	 * 
	 */
	@JSON
	public String queryShortScheduleTaskByDriver() {
		try {
			// 判空
			if (vo != null) {
				// 查询
				List<SimpleTruckScheduleDto> taskList = truckSchedulingTaskService.queryShortScheduleTaskByDriver(vo
						.getSimDto());
				// 设值
				vo.setSimDtos(taskList);
			}
			// 成功
			return super.returnSuccess();
		} catch (ShortScheduleException e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e);
		} catch (Exception e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 保存新的任务，并查询返回本任务,用于新增或更新任务信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-5 下午3:36:01
	 * 
	 */
	@JSON
	public String saveOrUpdateTaskAndFetchTask() {
		// 锁条件
		MutexElement mutex=null;
		try {
			// 判空
			if (vo != null) {
				// 当前用户
				UserEntity user = FossUserContext.getCurrentUser();
				//锁
				mutex= fetchMutexElement(vo.getSimDto());
				// 锁定
				boolean flg = businessLockService.lock(mutex, 0);
				// 成功获取锁
				if (flg) {
					// 执行新增或修改
					List<SimpleTruckScheduleDto> taskList = truckSchedulingTaskService.saveOrUpdateTaskAndFetchTask(
							vo.getSimDto(), user);
					// 设值
					vo.setSimDtos(taskList);
					// 解锁
					businessLockService.unlock(mutex);
				} else {
					throw new ShortScheduleException("当前排班正在操作中，请稍后再试", "");
				}
				
			}
			// 成功
			return super.returnSuccess();
		} catch (ShortScheduleException e) {
			// 解锁
			businessLockService.unlock(mutex);
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e);
		} catch (Exception e) {
			// 解锁
			businessLockService.unlock(mutex);
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}
	
	/**
	 * 获取锁条件
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-22 上午10:14:03
	 */
	private MutexElement fetchMutexElement(SimpleTruckScheduleDto taskEntity) {
		// 出发部门，到达部门，发车计划类型，线路、班次、发车日期
		String lockStr = ScheduleConstants.SCHEDULE_TYPE_DELIVERY;
		if (StringUtils.isNotBlank(taskEntity.getVehicleNo())) {
			lockStr =lockStr+taskEntity.getVehicleNo();
		}
		// 锁定条件
		MutexElement mutex = new MutexElement(lockStr, "短途排班", MutexElementType.TRUCK_SCHEDULING);
		return mutex;
	}

	/**
	 * 修改计划状态，并删除对应任务,用于将工作类别转换为非“出车”状态
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-5 下午3:36:01
	 * 
	 */
	@JSON
	public String delteTaskAndModifySchedule() {
		try {
			// 判空
			if (vo != null) {
				// 当前用户
				CurrentInfo user = FossUserContext.getCurrentInfo();
				// 删除
				truckSchedulingTaskService.delteTaskAndModifySchedule(vo.getSimDto(), user);
			}
			// 成功
			return super.returnSuccess();
		} catch (ShortScheduleException e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e);
		} catch (Exception e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * @Title: delteTaskAndModifySchedule 
	 * @Description: 删除单个任务 
	 * @return    
	 * @return String    返回类型 
	 * delteTask
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-23下午3:15:00
	 */
	@JSON
	public String deleteTask() {
		try {
			// 判空
			if (vo != null) {
				// 删除
				truckSchedulingTaskService.deleteTask(vo.getSimDto().getScheduleTaskId());
			}
			// 成功
			return super.returnSuccess();
		} catch (ShortScheduleException e) {
			// 日志
			LOGGER.error("删除排班任务错误", e);
			// 异常信息
			return super.returnError(e);
		} catch (Exception e) {
			// 日志
			LOGGER.error("删除排班任务错误", e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 
	 * 根据排班任务ID 列表删除排班任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-16 下午3:35:02
	 * 
	 */
	@JSON
	public String deleteTasksByScheduleTaskIds() {
		try {
			// 判空
			if (vo != null && vo.getSimDto() != null) {
				// 当前用户
				CurrentInfo user = FossUserContext.getCurrentInfo();
				// 删除
				truckSchedulingTaskService.deleteTasksByScheduleTaskIds(vo.getSimDto(), user);
			}
			// 成功
			return super.returnSuccess();
		} catch (ShortScheduleException e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e);
		} catch (Exception e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 
	 * 通过"车牌号"查询车辆信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-5 下午3:36:01
	 * 
	 */
	@JSON
	public String queryCarInfoByVehicleNo() {
		try {
			// 判空
			if (vo != null && vo.getSimDto() != null) {
				// 查询车牌号信息
				SimpleTruckScheduleDto simDto = truckSchedulingTaskService.queryCarInfoByVehicleNo(vo.getSimDto()
						.getVehicleNo());
//				if(vo.getSimDto().getVehicleNo()!=null&&StringUtils.equals(vo.getSimDto().getSchedulingtype(),"PKP")){
//					SimpleTruckScheduleDto simd=vo.getSimDto();
//					simd.setSchedulingStatus("Y");
//					List<SimpleTruckScheduleDto> smDtoList =truckSchedulingTaskService.queryTaskByVehicleNoAndDate(simd);
//					if(CollectionUtils.isNotEmpty(smDtoList)&&smDtoList.size()>0
//							&&!StringUtils.equals(vo.getSimDto().getDriverCode(), smDtoList.get(0).getDriverCode())){
//						throw new ShortScheduleException("车牌号"+vo.getSimDto().getVehicleNo()+"，已安排司机"+smDtoList.get(0).getDriverName()+"出车，请换个车辆!","");
//					}
//				}
				// 判空
				if (simDto != null) {
					vo.setSimDto(simDto);
				} else {
					throw new ShortScheduleException("没有找到本车牌的车辆信息", "");
				}

			}
			// 成功
			return super.returnSuccess();
		} catch (ShortScheduleException e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e);
		} catch (Exception e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}
	/**
	 * 
	 * 通过"车牌号" 日期查询车辆判断同一个车辆同一天不能被多个司机排班
	 * 
	 * @author 105869-foss-heyongdong	
	 * 
	 * @date 2014年7月3日 08:28:15
	 * 
	 */
	@JSON
	public String checkSchedulingTaskByVehicleNoAndDate(){
		try {
			// 判空
			if (vo != null && vo.getSimDto() != null) {
				if(vo.getSimDto().getVehicleNo()!=null&&StringUtils.equals(vo.getSimDto().getSchedulingtype(),"PKP")){
					SimpleTruckScheduleDto simd=vo.getSimDto();
					simd.setSchedulingStatus("Y");
					List<SimpleTruckScheduleDto> smDtoList =truckSchedulingTaskService.queryTaskByVehicleNoAndDate(simd);
					if(CollectionUtils.isNotEmpty(smDtoList)&&smDtoList.size()>0
							&&!StringUtils.equals(vo.getSimDto().getDriverCode(), smDtoList.get(0).getDriverCode())){
						throw new ShortScheduleException("车牌号"+vo.getSimDto().getVehicleNo()+"，已安排司机"+smDtoList.get(0).getDriverName()+"出车，请换个车辆!","");
					}
				}
			}
			// 成功
			return super.returnSuccess();
		}catch (ShortScheduleException e) {
			// 异常信息
			return super.returnError(e);
		} catch (Exception e) {
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}
	/**
	 * 
	 * 通过"线路"、"班次"查询"发车时间"
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-5 下午3:36:01
	 * 
	 */
	@JSON
	public String queryDepartTimeByLineNoAndFrequenceNo() {
		try {
			// 判空
			if (vo != null && vo.getSimDto() != null) {
				// 查询线路班次发车时间
				SimpleTruckScheduleDto simDto = truckSchedulingTaskService.queryDepartTimeByLineNoAndFrequenceNo(vo
						.getSimDto().getLineNo(), vo.getSimDto().getFrequencyNo());
				// 设值
				// 判空
				if (simDto != null) {
					vo.setSimDto(simDto);
				} else {
					LOGGER.error("没有本线路、班次的发车时间");
					throw new ShortScheduleException("没有本线路、班次的发车时间", "");
				}

			}

			// 成功
			return super.returnSuccess();
		} catch (ShortScheduleException e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e);
		} catch (Exception e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 
	 * 通过"车牌号"、查询"区域"（接送货排班）
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-5 下午3:36:01
	 * 
	 */
	@JSON
	public String queryZoneCodeByVehicleNo() {
		try {
			// 判空
			if (vo != null && vo.getSimDto() != null) {
				// 区域信息
				SimpleTruckScheduleDto simDto = new SimpleTruckScheduleDto();
				// 判空
				if (StringUtils.isNotBlank(vo.getSimDto().getVehicleNo())) {
					// 查询区域信息
					RegionVehicleEntity regionVehicle = truckSchedulingTaskService.queryZoneCodeByVehicleNo(vo
							.getSimDto().getVehicleNo());
					// 判空
					if (regionVehicle != null) {
						// 区域名
						simDto.setZoneName(regionVehicle.getRegionName());
						// 区域code
						simDto.setZoneCode(regionVehicle.getRegionCode());
					} 
						//else {
						//throw new ShortScheduleException("没有查询到本车辆的接货区域信息", "");
					//}
					// 查询送货区域信息
					RegionVehicleEntity deliveryRegionVehicle = truckSchedulingTaskService.queryDeliveryCodeByVehicleNo(vo
							.getSimDto().getVehicleNo());
					// 判空
					if (deliveryRegionVehicle != null) {
						// 送货区域名
						simDto.setDeliveryAreaName(deliveryRegionVehicle.getRegionName());
						// 送货区域code
						simDto.setDeliveryAreaCode(deliveryRegionVehicle.getRegionCode());
					} 
					//else {
						//throw new ShortScheduleException("没有查询到本车辆的送货区域信息", "");
					//}
				}
				vo.setSimDto(simDto);
			}
			// 成功
			return super.returnSuccess();
		} catch (ShortScheduleException e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e);
		} catch (Exception e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 
	 * 导出排班表信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-10-26 上午10:42:37
	 * 
	 */
	public String exportExcel() {
		try {
			// 导出文件名
			fileName = URLEncoder.encode(ScheduleConstants.EXPORT_EXCEL_FILE_NAME, "UTF-8");
			// 导出文件流
			excelStream = truckSchedulingTaskService.exportExcelStream(vo.getSimDto());
			// 成功
			return returnSuccess();
		} catch (ShortScheduleException e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return returnError("UnsupportedEncodingException", "");
		} catch (Exception e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 
	 * 导入排班表
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-2 下午4:51:08
	 * 
	 * 
	 */
	public String importShortSchedule() {
		// 文件
		Workbook book = null;
		// 输入流
		FileInputStream inputStream = null;
		// 抓取文件类型
		try {
			// 文件名不为空
			// 判空
			if (StringUtils.isNotBlank(uploadFileFileName)) {
				// 判空
				if (uploadFileFileName.endsWith(ScheduleConstants.EXCEL_FILE_TAIL_XLS_DOWN)
						|| uploadFileFileName.endsWith(ScheduleConstants.EXCEL_FILE_TAIL_XLS_UP)
						|| uploadFileFileName.endsWith(ScheduleConstants.EXCEL_FILE_TAIL_XLSX_DOWN)
						|| uploadFileFileName.endsWith(ScheduleConstants.EXCEL_FILE_TAIL_XLSX_UP)) {
					// 验证通过
				} else {
					throw new ShortScheduleException("只支持.xls或.xlsx格式的文件,请选择正确的文件导入", "");
				}
			}
			// 判空
			if (uploadFile != null) {
				inputStream = new FileInputStream(uploadFile);
				try {
					book = XlsImpUtil.create(inputStream);
				} catch (Exception ex) {
					LOGGER.error("非2003格式文件，转2007格式");
				}
			} else {
				throw new ShortScheduleException("请选择导入文件", "请选择导入文件");
			}
			// 解析导入的数据文件
			// 判空
			if (book != null) {
				// 默认获取获取工作表1
				Sheet sheet = book.getSheetAt(ScheduleConstants.EXCEL_DEFAULT_SHEET_ONE);

				// 读取Excel的所用数据,考虑到数据量不大，暂时不适用集中缓存
				List<ScheduleExcelDto> excelDtos = new ArrayList<ScheduleExcelDto>();
				// 将Excel表格每行数据读入列表
				makeExcelDtos(excelDtos, sheet);
				// Excel中所有司机编码
				Map<String, String> driverCodes = new HashMap<String, String>();
				// 在List中 验证空及其格式,同时收集好司机编码
				validateCellValueNotBlank(excelDtos, driverCodes);
				// 获取司机信息，验证司机是否存在本部门
				Map<String, DriverAssociationDto> existDriverMap = obtainAndValidateDriverInfo(driverCodes);
				//验证当前导入部门是否为车队组
				String schedulingDepartCode = vo.getTsEntity().getSchedulingDepartCode();
				OrgAdministrativeInfoEntity department = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(schedulingDepartCode);
				if(department == null) {
					throw new ShortScheduleException("排班部门不存在!", "");
				}
				if(StringUtils.equals(department.getTransTeam(), FossConstants.NO)) {
					throw new ShortScheduleException("排班部门非车队组, 不能排班!", "");
				}
				// 校验车牌、线路、失效信息
				StringBuffer errorMsgs = truckSchedulingTaskService.validateVehicleAndLineInfo(excelDtos,
						vo.getTsEntity());
				//接送货排班中校验 一个车一天只能安排一个司机
				validateRelationVehicleAndDirver(excelDtos);
				// 存在异常
				if (errorMsgs != null && errorMsgs.length() > 0) {
					this.appendErrorMsg(errorMsgs.toString());
				}
				// 存在错误信息，则抛出错误信息
				if (vo != null && vo.getErrorMsgs() != null) {
					throw new ShortScheduleException(vo.getErrorMsgs().toString(), "");
				}
				// 如果司机信息不对，则抛出异常
				// 判空
				if (CollectionUtils.isNotEmpty(vo.getUnexistDriver())) {
					throw new ShortScheduleException("存在没有的司机信息" + vo.getUnexistDriver().toString(), "");
				}
				// 如果没有错误信息,则执行导入
				// 判空
				if (vo.getImpErros().size() < 1) {
					// 判空
					if (CollectionUtils.isNotEmpty(excelDtos)) {
						// 当前用户
						UserEntity user = FossUserContext.getCurrentUser();
						// 执行批量插入
						truckSchedulingTaskService.batchImportTaskList(excelDtos, existDriverMap, vo.getTsEntity(),
								user);
						// 传回成功导入条数
						vo.setImportTotal(excelDtos.size());
					} else {
						throw new ShortScheduleException("导入数据为空", "导入数据为空");
					}
				}
			}

			// 成功
			return super.returnSuccess();
			// 异常
		} catch (ShortScheduleException e) {
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e);
			// 异常
		} catch (FileNotFoundException e) {
			// 日志
			LOGGER.error("数据文件不存在", e);
			// 异常信息
			return returnError("数据文件不存在");
			// 异常信息
		} catch (Exception e) {
			// 日志
			LOGGER.error("数据异常", e);
			// 异常信息
			return returnError("数据异常:" + e, e);

		} finally {
			// 回收文件数据
			// 判空
			if (book != null) {
				book = null;
			}
			// 关闭流文件
			// 判空
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
					return returnError("文件关闭失败");
				}
			}
		}
	}

	/**
	 * 
	 * 将Excel表格每行数据读入列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-6 上午10:46:31
	 * 
	 */
	private void makeExcelDtos(List<ScheduleExcelDto> excelDtos, Sheet sheet) {
		// 判空
		if (sheet != null && excelDtos != null) {
			// 获取物理行数
			int rowCount = sheet.getPhysicalNumberOfRows();
			// 根据行数循环
			Row row = null;
			// 最大列数
			int colCnt = ScheduleConstants.EXCEL_COLUMN_INIT_SIZE;
			// EXCEL行记录
			ScheduleExcelDto excelDto = null;
			// 根据行数循环
			for (int rowNum = 1; rowNum < rowCount; rowNum++) {
				// 获取每行数据
				excelDto = new ScheduleExcelDto();
				// 设置行号
				excelDto.setRowNum(rowNum);
				// 取得一行的数据
				row = sheet.getRow(rowNum);
				// 如果本行第一列为空，则不继续取值
				// 判空
				if (row == null || row.getCell(ScheduleConstants.EXCEL_COLUMN_FIRST) == null
						|| StringUtils.isBlank(obtainStringVal(row.getCell(ScheduleConstants.EXCEL_COLUMN_FIRST)))) {
					continue;
				} else {
					// 如果列数小于1
					// 判空
					if (colCnt < ScheduleConstants.EXCEL_COLUMN_SECOND) {
						String firstCellVal = obtainStringVal(row.getCell(ScheduleConstants.EXCEL_COLUMN_FIRST));
						// 获取导入列数
						// 判空
						if (ScheduleConstants.SCHEDULE_TYPE_DELIVERY.equals(firstCellVal)) {
							// 接送货排班导入列数
							colCnt = ScheduleConstants.PKP_EXCEL_LAST_COLUMN_NUM;
						} else // 判空
						if (ScheduleConstants.SCHEDULE_TYPE_SHORTSCHEDULE.equals(firstCellVal)) {
							// 短途排班导入列数
							colCnt = ScheduleConstants.TFR_EXCEL_LAST_COLUMN_NUM;
						}
					}
					// 循环取列值
					for (int colNum = 0; colNum < colCnt; colNum++) {
						// 由于读取EXCEL效率低下，故先取所有的值，再到内存中校验，提升性能（前提是数据量不大）
						obtainCellValue(row, colNum, excelDto);
					}
				}
				excelDtos.add(excelDto);
			}
		}
	}

	/**
	 * 
	 * 获取单元格值
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-2 下午7:37:43
	 * 
	 */
	private void obtainCellValue(Row row, int colNum, ScheduleExcelDto excelDto) {
		// 判空
		if (row != null) {
			Cell cell = row.getCell(colNum);
			// 判空
			if (cell != null) {
				// 取单元格值
				String cellVal = obtainStringVal(cell);
				// 值不为空，则辨别字段值类型
				// 判空
				if (StringUtils.isNotBlank(cellVal)) {
					// 查询是否有.0的数据
					int idx = cellVal.indexOf(ScheduleConstants.COLUMN_TAIL);
					// 根据列号设置列值
					switch (colNum) {
					case ScheduleConstants.COL_0:// 排班类型
						excelDto.setSchedulingType(cellVal);
						return;
					case ScheduleConstants.COL_1:// 部门编号
						excelDto.setDriverOrgCode(cellVal);
						return;
					case ScheduleConstants.COL_2:// 部门名称
						excelDto.setDriverOrgName(cellVal);
						return;
					case ScheduleConstants.COL_3:// 司机编号,截取数字的.0之前的值
						// 判空
						if (idx > ScheduleConstants.IDX_NUM) {
							excelDto.setDriverCode(StringUtils.substring(cellVal, 0, idx));
						} else {
							excelDto.setDriverCode(cellVal);
						}
						return;
					case ScheduleConstants.COL_4:// 司机姓名
						excelDto.setDriverName(cellVal);
						return;
					case ScheduleConstants.COL_5:// 日期
						try {
							excelDto.setSchedulingDate(com.deppon.foss.util.DateUtils.convert(cellVal,
									com.deppon.foss.util.DateUtils.DATE_FORMAT));
						} catch (Exception e) {
							LOGGER.error("排班日期错误", e);
						}
						return;
					case ScheduleConstants.COL_6:// 任务类型编号
						excelDto.setPlanType(cellVal);
						return;
					case ScheduleConstants.COL_7:// 任务类型
						excelDto.setPlanTypeName(cellVal);
						return;
					case ScheduleConstants.COL_8:// 任务序号
						// 判空
						if (ScheduleConstants.PLAN_TYPE_WORK.equals(excelDto.getPlanType())) {
							excelDto.setTaskNo(cellVal);
						}
						return;
					case ScheduleConstants.COL_9:// 车牌号
						// 判空
						if (ScheduleConstants.PLAN_TYPE_WORK.equals(excelDto.getPlanType())) {
							// 判空
							if (idx > ScheduleConstants.IDX_NUM) {
								excelDto.setVehicleNo(StringUtils.substring(cellVal, 0, idx));
							} else {
								excelDto.setVehicleNo(cellVal);
							}
						}
						return;
					case ScheduleConstants.COL_10:// 线路虚拟code简码
						// 判空
						if (ScheduleConstants.PLAN_TYPE_WORK.equals(excelDto.getPlanType())
								&& ScheduleConstants.SCHEDULE_TYPE_SHORTSCHEDULE.equals(excelDto.getSchedulingType())) {
							excelDto.setLineNoCode(cellVal);
						}
						return;
					case ScheduleConstants.COL_11:// 线路
						// 判空
						if (ScheduleConstants.PLAN_TYPE_WORK.equals(excelDto.getPlanType())
								&& ScheduleConstants.SCHEDULE_TYPE_SHORTSCHEDULE.equals(excelDto.getSchedulingType())) {
							excelDto.setLineName(cellVal);
						}
						return;
					case ScheduleConstants.COL_12:// 班次
						// 判空
						if (ScheduleConstants.PLAN_TYPE_WORK.equals(excelDto.getPlanType())
								&& ScheduleConstants.SCHEDULE_TYPE_SHORTSCHEDULE.equals(excelDto.getSchedulingType())) {
							// 判空
							if (idx > ScheduleConstants.IDX_NUM) {
								excelDto.setFrequencyNo(StringUtils.substring(cellVal, 0, idx));
							} else {
								excelDto.setFrequencyNo(cellVal);
							}
						}
						return;
					default:
						return;
					}
				}

			}
		}
	}

	/**
	 * 
	 * 
	 * 短途排班验证单元格（空，格式） 验证的同时，将司机编号放入，以待后面批量生成数据
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-2 下午7:25:15
	 * 
	 */
	private void validateCellValueNotBlank(List<ScheduleExcelDto> excelDtos, Map<String, String> driverCodes)
			throws ShortScheduleException {
		// 循环判空
		String tip = "不能为空";
		for (ScheduleExcelDto dto : excelDtos) {
			// 排班类型
			// 判空
			if (StringUtils.isBlank(dto.getSchedulingType())) {
				pushError(dto, "排班类型", tip);
			} else {
				// 验证排班类型是否符合
				// 判空
				if (vo.getTsEntity() != null && StringUtils.isNotBlank(vo.getTsEntity().getSchedulingType())
						&& !StringUtils.equals(dto.getSchedulingType(), vo.getTsEntity().getSchedulingType())) {

					appendErrorMsg("第" + (dto.getRowNum() + 1) + "行,排班类型不符;");
				}
			}
			// 司机编码
			// 判空
			if (StringUtils.isBlank(dto.getDriverCode())) {
				pushError(dto, "司机编码", tip);
			} else {
				// 如果还不存在本司机的code,则加入
				// 判空
				if (driverCodes.get(dto.getDriverCode()) == null) {
					// 首先验证 是否存在这个司机
					// 判空
					if (truckSchedulingTaskService.queryOwnDriveByDriverCode(dto.getDriverCode()) != null) {
						driverCodes.put(dto.getDriverCode(), String.valueOf(dto.getRowNum()));
					} else {

						appendErrorMsg("第" + (dto.getRowNum() + 1) + "行,不存在此司机：" + dto.getDriverCode() + ";");
					}
				}
			}
			// 发车日期
			// 判空
			if (dto.getSchedulingDate() == null) {
				pushError(dto, "发车日期", tip);
			}
			// 验证导入日期是否属于本次选择的年月之内
			else // 判空
			if (vo.getTsEntity() != null
					&& !DateUtils.validateDateRange(vo.getTsEntity().getYearMonth(), dto.getSchedulingDate())) {
				// 提示日期操作范围
				pushError(dto, "发车日期", "不是当前选择月");
			}

			// 工作类别
			// 判空
			if (StringUtils.isBlank(dto.getPlanType())) {
				pushError(dto, "工作类别", tip);
			}
			// 如果为上班的情况,则校验其他的字段
			// 判空
			if (StringUtils.isNotBlank(dto.getPlanType()) && ScheduleConstants.PLAN_TYPE_WORK.equals(dto.getPlanType())) {
				// 接送货排班校验列
				// 判空
				if (ScheduleConstants.SCHEDULE_TYPE_DELIVERY.equals(dto.getSchedulingType())) {
					// 车牌号
					// 判空
					if (StringUtils.isBlank(dto.getVehicleNo())) {
						pushError(dto, "车牌号", tip);
					}
					// 检验车辆的合法性
					else {
						// 判空
						if (truckSchedulingTaskService.queryCarInfoByVehicleNo(dto.getVehicleNo()) == null) {

							appendErrorMsg("第" + (dto.getRowNum() + 1) + "行,无此车牌号：" + dto.getVehicleNo() + ";");
						}
					}
				} else
				// 短途排班校验列
				// 判空
				if (ScheduleConstants.SCHEDULE_TYPE_SHORTSCHEDULE.equals(dto.getSchedulingType())) {
					// 车牌号
					// 判空
					if (StringUtils.isBlank(dto.getVehicleNo())) {
						pushError(dto, "车牌号", tip);
					}
					// 线路简码//导入时 需要根据简码获取虚拟code,再进行导入
					// 判空
					if (StringUtils.isBlank(dto.getLineNoCode())) {
						pushError(dto, "线路简码", tip);
					}
					// 班次
					// 判空
					if (StringUtils.isBlank(dto.getFrequencyNo())) {
						pushError(dto, "班次", tip);
					}
				}

			}

		}
	}
	/**
	 * 校验司机和车辆一天一对一的关系(pkp)
	 * 
	 * @author heyongdong
	 * 
	 * @date 2014年5月23日 16:03:22
	 */
	private void validateRelationVehicleAndDirver(List<ScheduleExcelDto>  dtos){
		if(CollectionUtils.isNotEmpty(dtos)&&dtos.size()>0&&vo.getTsEntity() != null && StringUtils.isNotBlank(vo.getTsEntity().getSchedulingType())
				&&StringUtils.equals(ScheduleConstants.SCHEDULE_TYPE_DELIVERY, vo.getTsEntity().getSchedulingType())){
			int size= dtos.size();
			//缓存时间用的
			List<Date> dates = new ArrayList<Date>();
			for(int i=0;i<size;i++){
				ScheduleExcelDto temp=dtos.get(i);
				if(!dates.contains(temp.getSchedulingDate())&&StringUtils.equals(temp.getPlanType(), ScheduleConstants.PLAN_TYPE_WORK)){
					for(int j=i+1;j<size;j++){
						ScheduleExcelDto temp1=dtos.get(j);
						//判断是否日期、车辆相同，类型都为出车，则提示。
						if((temp.getSchedulingDate().equals(temp1.getSchedulingDate()))&& StringUtils.equals(temp.getPlanType(),temp1.getPlanType())
								&&StringUtils.equals(temp.getVehicleNo(),temp1.getVehicleNo())){
							//添加时间
							dates.add(temp.getSchedulingDate());
							//重复则提示
							if(StringUtils.equals(temp.getDriverCode(),temp1.getDriverCode())){
								throw new ShortScheduleException("司机"+temp.getDriverName()+"在同一天只能安排一个车辆！","");
							}else{
								throw new ShortScheduleException("第"+(j+2)+"行车牌号"+temp.getVehicleNo()+"，已安排司机"+temp.getDriverName()+"出车，请换个车辆","");
							}
						}
					}
				}else{
					continue;
				}
			}
		}
		
	}
	/**
	 * 
	 * 单元格取值
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-5 上午9:22:47
	 * 
	 */
	private String obtainStringVal(Cell cell) {
		// 列值
		String cellVal = "";
		// 单元格类型
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: // 数值型
			// 判空
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				// 如果是date类型则 ，获取该cell的date值
				cellVal = com.deppon.foss.util.DateUtils.convert(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()),
						com.deppon.foss.util.DateUtils.DATE_FORMAT);
			} else {// 纯数字
				cellVal = String.valueOf(cell.getNumericCellValue());
			}
			break;
		// 此行表示单元格的内容为string类型
		case HSSFCell.CELL_TYPE_STRING: // 字符串型
			cellVal = cell.getRichStringCellValue().toString();
			break;
		case HSSFCell.CELL_TYPE_FORMULA:// 公式型
			try {
				// 读公式计算值
				cellVal = String.valueOf(cell.getNumericCellValue());
			} catch (Exception e) {
				cellVal = String.valueOf(cell.getStringCellValue());
			}
			// 判空
			if (ScheduleConstants.NAN.equals(cellVal)) {// 如果获取的数据值为非法值,则转换为获取字符串
				cellVal = cell.getRichStringCellValue().toString();
			}
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔
			cellVal = " " + cell.getBooleanCellValue();
			break;
		// 此行表示该单元格值为空
		case HSSFCell.CELL_TYPE_BLANK: // 空值
//			cellVal = "";//switch特性-sonar-352203
//			break;
		case HSSFCell.CELL_TYPE_ERROR: // 故障
			cellVal = "";
			break;
		default:
			cellVal = cell.getRichStringCellValue().toString();
		}
		return cellVal.trim();
	}

	/**
	 * 
	 * 获取司机信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-5 上午9:07:41
	 * 
	 */
	private Map<String, DriverAssociationDto> obtainAndValidateDriverInfo(Map<String, String> driverCodes)
			throws ShortScheduleException {
		Map<String, DriverAssociationDto> drivers = new HashMap<String, DriverAssociationDto>();
		// 司机编号不为空
		// 判空
		if (driverCodes != null && !driverCodes.isEmpty()) {
			Set<String> set = driverCodes.keySet();
			Iterator<String> it = null;
			// 判空
			if (!set.isEmpty()) {
				it = set.iterator();
			}
			// 司机编码
			String driverCode = null;
			// 循环获取司机编码
			// 判空
			if (it != null) {
				// 循环查询司机信息
				while (it.hasNext()) {
					// 获取司机编码
					driverCode = it.next();
					// 行数
					String rowNum = null;
					// 司机编码
					// 判空
					if (StringUtils.isNotBlank(driverCode)) {
						rowNum = driverCodes.get(driverCode);
						try {
							// 已经存在的司机，则不再查询
							if (drivers.get(driverCode) == null) {
								// 查询司机信息
								DriverAssociationDto driverInfo = truckSchedulingTaskService
										.queryOwnDriveByDriverCode(driverCode);
								// 验证司机的存在性
								// 判空
								if (driverInfo == null) {
									// 如果司机信息没有查询到，将不存在的司机编号放入不存在的错误信息里面
									vo.getUnexistDriver().add(driverCode);
								} else {
									// 验证司机是否属于本车队小组
									// 判空
									if (StringUtils.isNotBlank(driverInfo.getDriverOrganizationCode())
											&& vo.getTsEntity() != null) {
										// 存在此司机，取消验证司机与车队小组归属的验证
										drivers.put(driverCode, driverInfo);
									} else {
										// 抛出异常

										appendErrorMsg("第"
												+ (Integer.valueOf(rowNum) + ScheduleConstants.ADD_INT_VALUE)
												+ "行,基础资料中司机所属部门编码为空;");
									}
								}
							}

						} catch (OwnDriverException e) {
							// 日志
							LOGGER.error(e.getErrorCode(), e);
							// 判空
							if (rowNum != null) {
								// 抛出异常

								appendErrorMsg("第" + (Integer.valueOf(rowNum) + ScheduleConstants.ADD_INT_VALUE)
										+ "行,司机编码为空;");
							} else {
								// 抛出异常
								throw new ShortScheduleException("司机编码为空", e);
							}
						}
					}

				}
			}
		}

		return drivers;
	}

	/**
	 * 
	 * 放入错误信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-2 下午8:24:27
	 * 
	 */
	private void pushError(ScheduleExcelDto dto, String fieldName, String tip) throws ShortScheduleException {
		// 判空
		if (vo.getImpErros() != null) {
			// 将行号字段等信息放入错误信息Map
			vo.getImpErros()
					.put(String.valueOf(dto.getRowNum()).concat(ScheduleConstants.COMMA).concat(fieldName), tip);
			// 抛出异常

			String errorMsg = "第" + (dto.getRowNum() + ScheduleConstants.EXCEL_ROW_ADD_ONE) + "行" + fieldName + tip
					+ ";";
			appendErrorMsg(errorMsg);

		}
	}

	/**
	 * 构建错误信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-10 上午9:59:02
	 */
	private void appendErrorMsg(String errorMsg) {
		if (vo != null) {
			if (null == vo.getErrorMsgs()) {
				StringBuffer errorMsgs = new StringBuffer();
				errorMsgs.append(errorMsg);
				vo.setErrorMsgs(errorMsgs);
			} else {
				vo.getErrorMsgs().append(errorMsg);
			}
		}
	}

	/**
	 * 
	 * 初始化车队司机排班
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-5 下午3:36:01
	 * 
	 */
	@JSON
	public String initDeaprtDriverScheduling() {
		MutexElement mutex = null;
		try {
			// 判空
			if (vo != null && vo.getTsEntity() != null) {
				// 出发部门，到达部门，发车计划类型，线路、班次、发车日期
				String lockStr = vo.getTsEntity().getSchedulingDepartCode() + vo.getTsEntity().getYearMonth()
						+ vo.getTsEntity().getSchedulingType();
				// 锁定条件
				mutex = new MutexElement(lockStr, "初始化排班", MutexElementType.TRUCK_SCHEDULING);
				// 锁定
				boolean flg = businessLockService.lock(mutex, 0);
				
				String schedulingDepartCode = vo.getTsEntity().getSchedulingDepartCode();
				OrgAdministrativeInfoEntity department = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(schedulingDepartCode);
				if(department == null) {
					throw new ShortScheduleException("排班部门不存在!", "");
				}
				if(StringUtils.equals(department.getTransTeam(), FossConstants.NO)) {
					throw new ShortScheduleException("排班部门非车队组, 不能排班!", "");
				}
				
				// 成功获取锁
				if (flg) {
					// 执行排班数据初始化
					truckSchedulingService.initDeaprtDriverScheduling(vo.getTsEntity(),
							FossUserContext.getCurrentUser());
					// 解锁
					businessLockService.unlock(mutex);
				} else {
					throw new ShortScheduleException("当前排班在操作中，请稍后再试", "");
				}

			}
			// 成功
			return super.returnSuccess();
		} catch (ShortScheduleException e) {
			// 解锁
			businessLockService.unlock(mutex);
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e);
		} catch (Exception e) {
			// 解锁
			businessLockService.unlock(mutex);
			// 日志
			LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 
	 * 通过车牌号查询最新的非工作日接货区域绑定信息
	 * 
	 * @author FOSS-heyongdong
	 * 
	 * @date 2014年5月21日 10:23:14
	 * 
	 */
	@JSON
	public String queryPkpAreaInfoByVehicleNo(){
		try {
			// 判空
			if (vo != null) {
				// 查询
				List<TruckSchedulingZoneEntity>  truckSchedulingZoneEntity= truckSchedulingTaskService.queryPkpAreaInfoByVehicleNo(vo.getSimDto());
				SimpleTruckScheduleDto smdto = new SimpleTruckScheduleDto();
				smdto.setSchedulingZoneEntity(truckSchedulingZoneEntity);
				vo.setSimDto(smdto);
			}
			// 成功
			return super.returnSuccess();
		} catch (ShortScheduleException e) {
			// 日志
			LOGGER.error("查询错误", e);
			// 异常信息
			return super.returnError(e);
		} catch (Exception e) {
			// 日志
			LOGGER.error("查询错误", e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}
	
	/**
	 * 复制排班
	 * @author 105869
	 * @date 2015年1月12日 16:44:51
	 * 
	 * */
	@JSON
	public String copyTruckScheduling(){
		try {
			// 判空
			if (vo != null) {
				// 查询
				truckSchedulingService.copyTruckScheduling(vo.getTsEntity());
			}
			// 成功
			return super.returnSuccess();
		} catch (ShortScheduleException e) {
			// 日志
			LOGGER.error("查询错误", e);
			// 异常信息
			return super.returnError(e);
		} catch (Exception e) {
			// 日志
			LOGGER.error("查询错误", e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}
	/**
	 * 获取 排班前台VO.
	 * 
	 * @return the 排班前台VO
	 */
	public ShortScheduleVo getVo() {
		return vo;
	}

	/**
	 * 设置 排班前台VO.
	 * 
	 * @param vo
	 *            the new 排班前台VO
	 */
	public void setVo(ShortScheduleVo vo) {
		this.vo = vo;
	}

	/**
	 * 设置 人员月排班表Service.
	 * 
	 * @param truckSchedulingService
	 *            the new 人员月排班表Service
	 */
	public void setTruckSchedulingService(ITruckSchedulingService truckSchedulingService) {
		this.truckSchedulingService = truckSchedulingService;
	}

	/**
	 * 设置 排班任务Service.
	 * 
	 * @param truckSchedulingTaskService
	 *            the new 排班任务Service
	 */
	public void setTruckSchedulingTaskService(ITruckSchedulingTaskService truckSchedulingTaskService) {
		this.truckSchedulingTaskService = truckSchedulingTaskService;
	}

	/**
	 * 设置 导入文件.
	 * 
	 * @param uploadFile
	 *            the new 导入文件
	 */
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	/**
	 * 获取 导出Excel 文件流.
	 * 
	 * @return the 导出Excel 文件流
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}

	/**
	 * 获取 导出Excel 文件名.
	 * 
	 * @return the 导出Excel 文件名
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 获取 导入文件名.
	 * 
	 * @return the 导入文件名
	 */
	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	/**
	 * 设置 导入文件名.
	 * 
	 * @param uploadFileFileName
	 *            the new 导入文件名
	 */
	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**   
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 * Date:2013-6-3下午10:18:22
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**   
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 * Date:2013-6-6下午9:46:35
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
}