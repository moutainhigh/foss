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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/server/dao/IDepartureDao.java
 *  
 *  FILE NAME          :IDepartureDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.dto.DepartArriveToTpsDto;
import com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.QueryDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckActionDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.GpsNotifyDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.GpsVehicleDailySummaryDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.VehicleInfoForGpsDTO;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartResultDTO;
/**
 * 
 * 出发底层接口
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-25 上午8:59:18
 */
public interface IDepartureDao{

	
	/**
	 * 根据车辆任务编号，到达部门编号，车牌号查找车辆任务详情，判断是否为GPS第一次到达，如果是就调用结算接口，不是什么都不做
	 * @param truckTaskDetailEntity
	 * @return
	 */
	List<TruckTaskDetailEntity> queryJudgeIsFirstArrival(
			TruckTaskDetailEntity truckTaskDetailEntity);
	/**
	 * 
	 * 查询车辆放行情况（主页面），支持分页
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:20:17
	 */
	List<TruckDepartEntity> queryDepart(QueryDepartEntity queryEntity,
			int limit, int start);

	/**
	 * 
	 * 查询车辆放行情况（主页面），不支持分页
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:20:17
	 */
	List<TruckDepartEntity> queryDepart(QueryDepartEntity queryEntity);

	/**
	 * 
	 * 查询任务车辆明细
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:20:17
	 */
	List<TruckTaskDetailEntity> queryTruckTaskDetail(
			TruckTaskDetailEntity truckTaskDetailEntity);
	

	
	/**
	 * 
	 * 查询任务车辆明细
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:20:17
	 */
	List<TruckTaskDetailEntity> queryTruckTaskDetailByDepartId(
			TruckTaskDetailEntity truckTaskDetailEntity);
	
	/**
	 * 
	 * 查询任务车辆明细
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:20:17
	 */
	List<TruckTaskDetailEntity> queryTruckTaskDetailByTaskId(
			TruckTaskDetailEntity truckTaskDetailEntity);
	
	/**
	 * 
	 * 查询任务车辆明细
	 * 
	 * @author foss-336540
	 * @date 2016年9月1日11:54:37
	 */
	List<TruckTaskDetailEntity> queryTruckTaskDetailByIDList(String   id);
	
	
	/**
	 * 
	 * 查询记录数量
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午11:40:42
	 */
	Long getCount(QueryDepartEntity queryEntity);

	/**
	 * 
	 * 增加job表记录
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午11:40:26
	 */
	void addTruckActionDetail(TruckActionDetailEntity actionDetail);

	/**
	 * 
	 * 取消放行申请
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	void cancleDepart(List ids);

	/**
	 * 
	 * 激活放行申请
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	void activeDepart(List ids);
	
	/**
	 * 
	 * 通过放行ID获取任务ID
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	List<TruckTaskDetailEntity> getTaskIdByDepartId(String departId);

	/**
	 * 
	 * 插入纸质放行条
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	void saveManual(TruckDepartEntity manualEntity);

	/**
	 * 
	 * 插入纸质放行条
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	void addManual(TruckDepartEntity manualEntity);

	/**
	 * 
	 * 打印纸质放行条需要更新任务车辆明细表
	 * 
	 * @return 返回
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	String updateTaskByManual(TruckTaskDetailEntity detail);
	/**
	 * 取消车辆到达
	 * @return 返回
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	String updateTaskDetailCancleArrive(TruckTaskDetailEntity detail);
	/**
	 * 取消车辆出发
	 * @return 返回
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	String updateTaskDetailCancleDepart(TruckTaskDetailEntity detail);
	
	
	/**
	 * 
	 * 更新任务车辆的状态
	 * 
	 * @return 返回
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	 String updateTruckTask(TruckTaskDetailEntity detail);

	/**
	 * 
	 * 激活、取消申请前判断状态
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 上午10:40:07
	 */
	Long validBeforeCancleOrActiveDepart(QueryDepartEntity queryEntity);
	/**
	 * 
	 *  取消申请为待放行(根据任务车辆明细) 
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 上午10:40:07
	 */
	void cancleDepartByTaskId(QueryDepartEntity queryEntity);
	
	/**
	 * 
	 * 放行前校验是否有相同车牌号的待放行申请或者失效的申请
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午10:43:22
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao#insertLMS(com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity)
	 */
	Long validBeforeDepart(QueryDepartEntity queryEntity);

	/***************************** 下面是申请车辆放行（人工） ******************************/
	/**
	 * 查询临时任务
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	List<TruckDepartEntity> queryTemporaryAssignments(
			QueryDepartEntity queryEntity, int limit, int start);
	
	/**
	 * 
	 * 测试用例中使用
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-5 下午1:55:48
	 */
	void deleteTruckActionDetail();
	
	/**
	 * GPS将车辆出发时间发送给FOSS
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-27 下午3:05:49
	 */
	void notifyStarttime(
			GpsNotifyDTO gpsNotifyDTO);
	
	/**
	 * GPS将车辆出发时间发送给FOSS
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-27 下午3:05:49
	 */
	void notifyArrivaltime(
			GpsNotifyDTO gpsNotifyDTO);
	/**
	 * GPS将车辆出发时间发送给FOSS
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-27 下午3:05:49
	 */
	void updateVehicleTrack(
			GpsNotifyDTO gpsNotifyDTO);
	
	/**
	 * 通过放行ID获得PDA的返回结果
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-27 下午3:05:49
	 */
	PDADepartResultDTO getPDADepartResult(
			String departId);
	
	/**
	 * 通过放行ID获得交接单
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-27 下午3:05:49
	 */
	List<String> getHandoverBillsByDepartId(
			String departId);

	/**
	 * 通过放行ID获得封签号
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-27 下午3:05:49
	 */
	List<String> getSealNosByDepartId(
			String departId);
	/**
	 * 
	 * 根据GPS的ID查询车辆明细信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:20:17
	 */
	List<VehicleInfoForGpsDTO> queryTruckTaskDetailByGpsId(
			String gpsId);
	
	/**
	 * 同步LMS记录
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-27 下午3:05:49
	 */
	void insertLMS(LmsTruckDepartPlanEntity lms);
	
	/**
	 * 
	 * 放行的时候判断是否是集配交接单，集配交接单不能放行
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-25 下午3:37:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService#queryBigOrgCode(java.lang.String)
	 */
	List<String> isDistanceHandover(QueryDepartEntity queryEntity);
	
	/**
	 * 更新车辆放行的时间
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-27 下午3:05:49
	 */
	void updateTruckDepartTimeByTask(TruckDepartEntity manualEntity);
	
	/**
	 * 
	 *  提供接口给接送货，根据运单查询在途车辆任务
	 * @author alfred
	 * @date 2013-9-11 上午10:38:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	List<TruckTaskDetailEntity> querycarLoadTruckTaskDetailList(String waybillNo);

	/**
	 * 根据车辆主任务ID查询未到达的车辆任务
	 * @author 163580
	 * @date 2013-12-10 下午3:19:06
	 * @param truckTaskId
	 * @return
	 * @see
	 */
	List<TruckTaskDetailEntity> queryUnArrivalTaskByTaskId(String truckTaskId);
		
	/**
	 * 根据任务车辆明细ID查询任务车辆明细
	 * @author 105795
	 * @date 2014年11月26日 16:55:25
	 * @param id
	 * @return
	 */
	public TruckTaskDetailEntity queryTruckTaskDetailById(String id);
	
	/**GPS供应商同步车辆信息经过DOP到ESB再到FOSS
	 * @author heyongdong
	 * @param GpsVehicleDailySummaryDTO
	 * @date 2014年11月20日 14:22:34 
	 */
	void insertVehicleDailySummary(List<GpsVehicleDailySummaryDTO> vehicleDailys);
	
	/**
	 * 根据车辆任务明细和TPS信息部 查询 发车要传递的信息
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-15
	 * @param truckTaskDetailId
	 * @param infoDept
	 * @return
	 */
	List<DepartArriveToTpsDto> synchDepartArriveInfoToTps(String truckTaskDetailId,String infoDept);
	
	/**
	 * 计算配载单的总重量和总体积
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-16
	 * @param id
	 * @return
	 */
	DepartArriveToTpsDto queryWeightVolumeByVehicleassembleNo(String id);
	
	/**
	 * 
	 * 查询要放行车牌号下是否有多个已确认状态的派单单号车辆未放行
	 * @author foss-zyr
	 * @date 2015-06-23 09:02:55
	 * 
	 */
	List<String> queryDeliverbill(String id);
	
	/**
	 * 
	 * 查询全部要放行的记录
	 * @author foss-zyr
	 * @date 2015-06-23 09:02:55
	 * 
	 */
	List<TruckDepartEntity> queryManualAll(String vehicleNo);
	
	/**
	 * 营业部交接卸车改到达时间
	 * 更新任务车辆明细
	 * @author 360903
	 * @date 2016年12月14日 12:49:19
	 */
	void updateTaskByByHandOverBillSale(String handOverBillNo);
	
}