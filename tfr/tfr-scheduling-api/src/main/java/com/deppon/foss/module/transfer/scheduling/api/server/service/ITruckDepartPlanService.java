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
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/ITruckDepartPlanService.java
 * 
 *  FILE NAME     :ITruckDepartPlanService.java
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
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.server.service
 * FILE    NAME: ITruckDepartPlanService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ForecastForPlanDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.MergeLogDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.TruckDepartPlanException;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.TruckDepartPlanVo;

/**
 * 计划service接口
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-21 下午6:18:06
 */
public interface ITruckDepartPlanService extends IService {
	/**
	 * 新增计划
	 * <dl>
	 * <dd>1、根据Dto的构建发车计划</dd>
	 * <dd>2、根据发车计划的出发部门、到达部门线路信息</dd>
	 * <dd>3、根据线路信息 查询线路发车时效（班次）列表</dd>
	 * <dd>4、根据发车时效（班次）列表，初始化本发车计划的发车计划明细</dd>
	 * <dd>5、持久化发车计划及发车计划明细</dd>
	 * </dl>
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午7:04:08
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService#addTruckDepartPlan(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto)
	 */
	int addTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto, CurrentInfo user) throws TruckDepartPlanException;

	/**
	 * 删除发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:15:46
	 */
	int deleteTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto, CurrentInfo user) throws TruckDepartPlanException;

	/**
	 * 查询发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:16:25
	 */
	List<TruckDepartPlanDto> queryTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto, int limit, int start)
			throws TruckDepartPlanException;

	/**
	 * 查询发车计划总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-3 下午2:12:29
	 */
	Long queryTruckDepartPlanTotal(TruckDepartPlanDto truckDepartPlanDto) throws TruckDepartPlanException;

	/**
	 * 更新发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:16:36
	 */
	int updateTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto, CurrentInfo user) throws TruckDepartPlanException;

	/**
	 * 通过司机Code查询司机信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-1 下午4:40:06
	 */
	DriverAssociationDto queryDriverInfoByDriverCode(String driverCode, String truckType)
			throws TruckDepartPlanException;

	/**
	 * 查询合发记录
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-3 下午3:25:22
	 */
	List<MergeLogDto> queryMergeLogs(TruckDepartPlanDetailDto detailDto);

	/**
	 * 查询货量预测信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-10 上午11:29:33
	 */
	ForecastForPlanDto queryForecastInfo(TruckDepartPlanDto truckDepartPlanDto);

	/**
	 * 更新保存备注及异常标记
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-19 下午1:53:38
	 */
	void updatePlanRemark(TruckDepartPlanDto planDto, CurrentInfo user);

	/**
	 * 查询本发车计划是否已经存在
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-29 下午1:07:58
	 */
	public boolean hasExsitTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto);

	/**
	 * 获取当前部门对应的外场
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-12 下午4:58:59
	 */
	public OrgAdministrativeInfoEntity queryTransferCenter(String deptCode);

	/**
	 * 获取车队
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-15 上午10:30:57
	 */
	public OrgAdministrativeInfoEntity queryTransDepartment(String deptCode);

	/**
	 * 改变车辆归属类型
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-15 下午7:27:38
	 */
	void changeTruckType(TruckDepartPlanDetailDto detailDto);

	/**
	 * 外请司机查询外请车
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-16 下午3:07:30
	 */
	void queryLeasedDriverVechile(TruckDepartPlanDetailDto detailDto);

	/**
	 * 
	 * 根据时间，线路查询最大班次号
	 * 
	 * 
	 * 包括停发班次的
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2013-3-20 上午11:03:35
	 */
	Integer queryMaxfrequencyNo(TruckDepartPlanDetailDto detailDto);

	/**
	 * 导出发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-26 下午2:41:19
	 */
	InputStream exportExcel(TruckDepartPlanVo vo, String name);

	/**
	 * 外请车通过车牌查询司机
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-2 下午2:14:23
	 */
	DriverAssociationDto queryDriverInfoByVechileNo(TruckDepartPlanVo vo);

	/**
	 * 查询月台号
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-2 下午5:28:50
	 */
	TruckDepartPlanDetailDto queryPlatformNo(TruckDepartPlanVo vo);

	/**
	 * 批量生成发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-7 上午9:51:28
	 */
	void batchCreateDepartPlan(TruckDepartPlanVo vo, CurrentInfo user);

}