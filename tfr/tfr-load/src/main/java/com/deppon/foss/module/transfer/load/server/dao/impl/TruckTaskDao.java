/**
 * 
 * 
 * 
 * 
 *  initial comments.
 *  
 *  
 *  
 *  
 *  
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/dao/impl/TruckTaskDao.java
 *  
 *  FILE NAME          :TruckTaskDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.dao.impl
 * FILE    NAME: TaskTruckDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
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
 *  交接单与任务车辆表的关系			正	生成交接单的时候，生成任务车辆表记录（异步）	
 *  	
 *  	
 *	封签与任务车辆表的关系			正/反	封签完毕后，匹配任务车辆表， 任务车辆表生成后，匹配封签表
 *		
 *	
 *	配载单与任务车辆表的关系			正	生成配载单的时候，生成任务车辆表记录（异步）	
 *		
 *
 *  证件包与任务车辆表的关系			正/反	证件包领取完毕后，匹配任务车辆表， 任务车辆表生成后，匹配证件包表	
 *  		
 *  
 *	放行与任务车辆表的关系			正/反	系统放行后，匹配任务车辆表记录
 *			
 *
 *	到达与任务车辆表的关系			正	系统到达后，匹配任务车辆表记录	
 *		
 *
 *	外请车报到与任务车辆关系			反				
 *
 *
 *  到达与接送货卸车表关系			正/反	系统到达后，匹配接送货卸车表记录，接送货卸车表记录生成后，匹配到达表					
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
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.shared.dto.TaskVehicleDto;
import com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckGPSTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverMsgDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.TruckTaskHandOverDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.WayBillHandOverDto;

/**
 * 任务车辆dao
 * 
 * 
 * @author dp-duyi
 * @date 2012-11-7 上午10:24:23
 */
@SuppressWarnings("unchecked")
public class TruckTaskDao  extends iBatis3DaoImpl implements ITruckTaskDao{
	private static final String NAMESPACE = "tfr-load.";
	/** 
	 * 查询未生成任务车辆交接单
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-7 下午1:50:09
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryUnCreateTaskTruckHandOver()
	 */
	@Override
	public List<TruckTaskHandOverDto> queryUnCreateTaskTruckHandOver(Date bizJobStartTime,Date bizJobEndTime,int threadNo, int threadCount,String handOverBillNo) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		//线程数
		paramsMap.put("threadCount", threadCount);
		//线程号
		paramsMap.put("threadNo", threadNo);
		//开始时间
		paramsMap.put("bizJobStartTime", bizJobStartTime);
		//结束时间
		paramsMap.put("bizJobEndTime", bizJobEndTime);
		paramsMap.put("billNo", handOverBillNo);
		return this.getSqlSession().selectList(NAMESPACE+"queryUnCreateTruckTaskHandOver",paramsMap);
	}
	/** 
	 * 查询是否需要生成任务车辆
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-7 下午1:50:09
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryBeCreateTruckTask(java.lang.String)
	 */
	@Override
	public String queryBeCreateTruckTask(TruckTaskHandOverDto truckTaskHandOverDto) {
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryBeCreateTruckTask",truckTaskHandOverDto);
	}
	/** 
	 * 插入任务车辆
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-7 下午1:50:09
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#insertTruckTask(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskEntity)
	 */
	@Override
	public int insertTruckTask(TruckTaskEntity truckTaskEntity) {
		return this.getSqlSession().insert(NAMESPACE+"insertTruckTask", truckTaskEntity);
	}
	/** 
	 * 查询是否生成任务车辆明细
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-7 下午1:50:09
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryBeCreateTruckTaskDetail(com.deppon.foss.module.transfer.load.api.shared.dto.TruckTaskHandOverDto)
	 */
	@Override
	public TruckTaskDetailEntity queryBeCreateTruckTaskDetail(TruckTaskHandOverDto truckTaskHandOverDto) {
		List<TruckTaskDetailEntity> result= this.getSqlSession().selectList(NAMESPACE+"queryBeCreateTruckTaskDetail", truckTaskHandOverDto);
		if(CollectionUtils.isNotEmpty(result)){
			return result.get(0);
		}else{
			return null;
		}
	}
	/** 
	 * 插入任务车辆明细
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-7 下午1:50:09
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#insertTruckTaskDetail(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override
	public int insertTruckTaskDetail(TruckTaskDetailEntity truckTaskDetailEntity) {
		return this.getSqlSession().insert(NAMESPACE+"insertTruckTaskDetail",truckTaskDetailEntity);
	}
	/** 
	 * 插入任务车辆单据
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-7 下午1:50:09
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#insertTruckTaskBill(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskBillEntity)
	 */
	@Override
	public int insertTruckTaskBill(TruckTaskBillEntity truckTaskBillEntity) {
		return this.getSqlSession().insert(NAMESPACE+"insertTruckTaskBill", truckTaskBillEntity);
	}
	/** 
	 * 插入gps车辆列表
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-7 下午1:50:09
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#insertTruckGPSTask(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override
	public int insertTruckGPSTask(TruckGPSTaskEntity truckGPSTaskEntity) {
		return this.getSqlSession().insert(NAMESPACE+"insertTruckGSPTask",truckGPSTaskEntity);
	}
	/** 
	 *更新封签
	 *
	 *
	 * @author dp-duyi
	 * @date 2012-11-7 下午1:50:09
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#updateVehicleSeal(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override
	public int updateVehicleSeal(TruckTaskDetailEntity truckTaskDetailEntity) {
		return this.getSqlSession().update(NAMESPACE+"updateVehicleSealTruckTaskId", truckTaskDetailEntity);
	}
	/** 
	 * 更新证件包
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-7 下午1:50:09
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#updateCertificateBag(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override
	public int updateCertificateBag(TruckTaskDetailEntity truckTaskDetailEntity) {
		return this.getSqlSession().update(NAMESPACE+"updateCertificateBagTruckTaskId", truckTaskDetailEntity);
	}
	/** 
	 * 查询封签
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-7 下午3:53:39
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryVehicleSeal(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override
	public List<TruckTaskHandOverDto> queryVehicleSeal(TruckTaskDetailEntity truckTaskDetailEntity) {
		return this.getSqlSession().selectList(NAMESPACE+"queryVehicleSealId",truckTaskDetailEntity);
	}
	/** 
	 * 更新交接单是否生成任务车辆状态
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-7 下午4:12:28
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#updateHandOverBillState(java.lang.String)
	 */
	@Override
	public int updateHandOverBillState(Map<String,String> handOverBill) {
		return this.getSqlSession().update(NAMESPACE+"updateTruckTaskHandOverBillState",handOverBill);
	}
	
	/**
	* @description 更新快递系统交接单是否生成车辆任务状态
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#updateHandOverBillStateWk(java.util.Map)
	* @author 283250-foss-liuyi
	* @update 2016年5月4日 下午4:00:35
	* @version V1.0
	*/
	@Override
	public int updateHandOverBillStateWk(Map<String,String> handOverBill) {
		return this.getSqlSession().update(NAMESPACE+"updateHandOverBillStateWk",handOverBill);
	}
	/** 
	 * 查询未创建任务车辆配载单
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-8 下午4:57:29
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryUnCreateTruckTaskAssembleBill()
	 */
	@Override
	public List<TruckTaskHandOverDto> queryUnCreateTruckTaskAssembleBill(Date bizJobStartTime,Date bizJobEndTime,int threadNo, int threadCount,String billNo) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		//线程数
		paramsMap.put("threadCount", threadCount);
		//线程号
		paramsMap.put("threadNo", threadNo);
		//开始时间
		paramsMap.put("bizJobStartTime", bizJobStartTime);
		//结束时间
		paramsMap.put("bizJobEndTime", bizJobEndTime);
		paramsMap.put("billNo", billNo);
		return this.getSqlSession().selectList(NAMESPACE+"queryUnCreateTruckTaskAssembleBill",paramsMap);
	}
	/** 
	 * 根据配载单更新任务车辆单据单据级别
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-7 下午1:50:09
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#updateTruckTaskBillStateByAssembleBill(java.lang.String)
	 */
	@Override
	public int updateTruckTaskBillStateByAssembleBill(Map<String,String> condition) {
		return this.getSqlSession().update(NAMESPACE+"updateTruckTaskBillBillLevel", condition);
	}
	/** 
	 * 更新配载单是否生成任务车辆状态
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-9 上午8:39:04
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#updateAssembleBillState(java.lang.String)
	 */
	@Override
	public int updateAssembleBillState(Map<String,String> assembleBill) {
		return this.getSqlSession().update(NAMESPACE+"updateTruckTaskAssembleBillState",assembleBill);
	}
	/** 
	 * 根据交接单查询任务车辆ID，任务车辆明细ID
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-9 下午2:31:27
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryTruckTaskIdByHandOverBill(java.lang.String)
	 */
	@Override
	public TruckTaskHandOverDto queryTruckTaskIdByHandOverBill(String handOverBill) {
		return (TruckTaskHandOverDto) this.getSqlSession().selectOne(NAMESPACE+"queryTruckTaskIdByHandOverBill", handOverBill);
	}
	/** 
	 * 查询装车任务中单据数
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-9 下午2:31:27
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryBillCountByTruckTask(java.lang.String)
	 */
	@Override
	public int queryBillCountByTruckTask(String truckTaskId) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryBillCountByTruckTask", truckTaskId);
	}
	/** 
	 * 查询装车任务明细中单据数
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-9 下午2:31:27
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryBillCountByTruckTaskDetail(java.lang.String)
	 */
	@Override
	public int queryBillCountByTruckTaskDetail(String truckTaskDetailId) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryBillCountByTruckTaskDetail", truckTaskDetailId);
	}
	/** 
	 * 删除任务车辆
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-9 下午2:31:27
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#deleteTruckTask(java.lang.String)
	 */
	@Override
	public int deleteTruckTask(String truckTaskId) {
		return this.getSqlSession().update(NAMESPACE+"deleteTruckTask", truckTaskId);
	}
	/** 
	 * 删除任务车辆明细
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-9 下午2:31:27
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#deleteTruckTaskDetail(java.lang.String)
	 */
	@Override
	public int deleteTruckTaskDetail(String truckTaskDetailId) {
		return this.getSqlSession().update(NAMESPACE+"deleteTruckTaskDetail", truckTaskDetailId);
	}
	/** 
	 * 删除GPS待跟踪车辆
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-9 下午2:31:27
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#deleteTruckTaskGPSDetail(java.lang.String)
	 */
	@Override
	public int deleteTruckTaskGPSDetail(String truckTaskDetailId) {
		return this.getSqlSession().update(NAMESPACE+"deleteTruckGPSTask", truckTaskDetailId);
	}
	/** 
	 *  删除任务车辆单据
	 *  
	 *  
	 * @author dp-duyi
	 * @date 2012-11-9 下午2:31:27
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#deleteTruckTaskBill(java.lang.String)
	 */
	@Override
	public int deleteTruckTaskBill(String billNo) {
		return this.getSqlSession().delete(NAMESPACE+"deleteTruckTaskBill", billNo);
	}
	/** 
	 * 若有放行ID，则将任务车辆状态更新为在途
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-9 下午5:33:18
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#updateTruckTaskState(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskEntity)
	 */
	@Override
	public int updateTruckTaskState(TruckTaskEntity truckTaskEntity) {
		return this.getSqlSession().update(NAMESPACE+"updateTruckTaskState", truckTaskEntity);
	}
	/** 
	 * 根据交接单号修改任务车辆单据状态
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-12 上午9:01:15
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#updateTruckTaskBillStateByHandOverBill(java.util.Map)
	 */
	@Override
	public int updateTruckTaskBillStateByHandOverBill(Map<String, String> condition) {
		return this.getSqlSession().update(NAMESPACE+"updateTruckTaskBillBillLevelByHandOverBill", condition);
	}
	/** 
	 * 更新任务车辆中费用交接单编号
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-12 下午3:30:45
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#updateChargingAssembleNo(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskEntity)
	 */
	@Override
	public int updateChargingAssembleNo(TruckTaskEntity truckTask) {
		return this.getSqlSession().update(NAMESPACE+"updateChargingAssembleNo", truckTask);
	}
	/** 
	 * 根据配载单查询任务车辆
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-12 下午3:52:49
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryTruckTaskByAssembleNo(java.lang.String)
	 */
	@Override
	public List<TruckTaskEntity> queryTruckTaskByAssembleNo(String billNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryTruckTaskByAssembleNo",billNo);
	}
	/** 
	 * 推送到营业部交接单信息
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-15 下午3:04:43
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryHandOverBillMsg(java.lang.String)
	 */
	@Override
	public HandOverMsgDto queryHandOverBillMsg(String handOverBillNo) {
		return (HandOverMsgDto) this.getSqlSession().selectOne(NAMESPACE+"queryHandOverMsg", handOverBillNo);
	}
	/** 
	 * 接送货接口：根据运单号、流水号查询在途交接单出发部门编码，到达部门编码
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-23 上午8:51:38
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryOnTheWayHandOverBillBySerialNo(java.util.Map)
	 */
	@Override
	public HandOverBillEntity queryOnTheWayHandOverBillBySerialNo(Map<String, Object> condition) {
		return (HandOverBillEntity) this.getSqlSession().selectOne(NAMESPACE+"queryOnTheWayHandOverBillBySerialNo",condition);
	}
	/** 
	 * 交接单新增后查询未修改走货路径交接单
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-23 上午11:12:19
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryUnUpdateTransportPathHandOverBill()
	 */
	@Override
	public List<HandOverBillDetailDto> queryUnUpdateTransportPathHandOverBill(Date bizJobStartTime,Date bizJobEndTime,int threadNo, int threadCount) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		//线程数
		paramsMap.put("threadCount", threadCount);
		//线程号
		paramsMap.put("threadNo", threadNo);
		//开始时间
		paramsMap.put("bizJobStartTime", bizJobStartTime);
		//结束时间
		paramsMap.put("bizJobEndTime", bizJobEndTime);
		return this.getSqlSession().selectList(NAMESPACE+"queryUnUpdateTransportPathHandOverBill",paramsMap);
	}
	/** 
	 * 更新待跟踪车辆信息：是否成功
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-27 下午2:08:45
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#updateTruckGPSDetail(com.deppon.foss.module.transfer.load.api.shared.domain.TruckGPSTaskEntity)
	 */
	@Override
	public int updateTruckGPSTask(TruckGPSTaskEntity truckGpsTask) {
		return this.getSqlSession().update(NAMESPACE+"updateTruckGPSTask", truckGpsTask);
	}
	/** 
	 * 查询未成功同步至gps系统的待跟踪车辆
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-27 下午3:03:49
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryFailedTruckGPSTasks(int)
	 */
	@Override
	public List<TaskVehicleDto> queryFailedTruckGPSTasks(int queryCount) {
		return this.getSqlSession().selectList(NAMESPACE+"queryFailedTruckGPSTasks", queryCount);
	}
	/** 
	 * 	接送货接口：根据运单号查询运单交接信息
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2013-1-5 上午10:50:27
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryWayBillHandOverInfo(java.lang.String)
	 */
	@Override
	public List<WayBillHandOverDto> queryWayBillHandOverInfo(String wayBillNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryWayBillHandOverInfo", wayBillNo);
	}
	
	/**
	 * 查询未出发的任务车辆明细记录
	 * @author 045923-foss-shiwei
	 * @date 2013-5-15 上午9:53:41
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryUndepartRecByVehicleNo(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override
	public List<String> queryUndepartRecByVehicleNo(TruckTaskDetailEntity queryCon) {
		return this.getSqlSession().selectList(NAMESPACE+"queryUndepartRecByVehicleNo", queryCon);
	}
	/** 
	 * @author dp-duyi
	 * @date 2013-6-1 下午3:47:45
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#updateTruckGPSTaskTimes(java.lang.String)
	 */
	@Override
	public int updateTruckGPSTaskTimes(String id) {
		return this.getSqlSession().update(NAMESPACE+"updateTruckGPSTaskTimes", id);
	}
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-6-10 上午11:43:11
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#updateHandOverBeUpdateTransportPath(java.lang.String)
	 */
	@Override
	public int updateHandOverBeUpdateTransportPath(String handOverNo) {
		return this.getSqlSession().update(NAMESPACE+"updateHandOverBeUpdateTransportPath", handOverNo);
	}
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-6-12 上午11:01:10
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryLastedCreateTruckTaskBill(java.lang.String, java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	public String queryLastedCreateTruckTaskBill(String taskId,
			String origCode, String destCode) {
		Map<String,String> condition  = new HashMap<String,String>();
		condition.put("taskId", taskId);
		condition.put("origCode", origCode);
		condition.put("DestCode", destCode);
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryLastedCreateTruckTaskBill", condition);
	}
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-7-9 上午11:06:38
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#selectBillForUpdateByTruckTaskId(java.lang.String)
	 */
	@Override
	public void selectBillForUpdateByTruckTaskId(String truckTaskId) {
		this.getSqlSession().selectList(NAMESPACE+"selectBillForUpdateByTruckTaskId", truckTaskId);
	}
	/**
	 * 
	 * 查询未成功同步至gps系统的待跟踪车辆中交接单信息-短途
	 * @author alfred
	 * @date 2014-3-17 上午10:13:28
	 * @param truckDetailId
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryhandOverBillBytruckDetailId(java.lang.String)
	 */
	@Override
	public List<TruckTaskHandOverDto> queryhandOverBillBytruckDetailId(
			String truckDetailId) {
		return this.getSqlSession().selectList(NAMESPACE+"queryhandOverBillBytruckDetailId", truckDetailId);
	}
	/**
	 * 
	 * 查询未成功同步至gps系统的待跟踪车辆-短途
	 * @author alfred
	 * @date 2014-3-17 上午10:13:24
	 * @param queryCount
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryFailedShortTruckGPSTasks(int)
	 */
	@Override
	public List<TaskVehicleDto> queryFailedShortTruckGPSTasks(int queryCount) {
		return this.getSqlSession().selectList(NAMESPACE+"queryFailedShortTruckGPSTasks", queryCount);
	}
	/**
	 * 
	 * <p>更新gps成功与否的状态-短途</p> 
	 * @author alfred
	 * @date 2014-4-8 下午3:54:03
	 * @param truckGpsTask
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#updateTruckShortGPSTask(com.deppon.foss.module.transfer.load.api.shared.domain.TruckGPSTaskEntity)
	 */
	@Override
	public int updateTruckShortGPSTask(TruckGPSTaskEntity truckGpsTask) {
		return this.getSqlSession().update(NAMESPACE+"updateTruckShortGPSTask", truckGpsTask);
	}
	/**
	 * 
	 * <p>更新GPS同步次数-短途</p> 
	 * @author alfred
	 * @date 2014-4-8 下午3:54:08
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#updateTruckShortGPSTaskTimes(java.lang.String)
	 */
	@Override
	public int updateTruckShortGPSTaskTimes(String id) {
		return this.getSqlSession().update(NAMESPACE+"updateTruckShortGPSTaskTimes", id);
	}
	/**
	 * 查询车辆任务明细，通过交接单、配载单
	 * @author foss-heyongdong
	 * @date 2014年4月21日 14:10:17
	 * @param billNo
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryTruckTaskDetail(java.lang.String)
	 */
	@Override
	public List<TruckTaskDetailEntity> queryTruckTaskDetail(String billNo) {
		
		return this.getSqlSession().selectList(NAMESPACE+"queryTruckTaskDetail" ,billNo);
	}
	/**
	 * 通过车辆任务ID查询封签信息
	 * @author 105869
	 * @date 2014年9月2日 16:24:02
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryVehicleSealByTaskId(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override
	public List<TruckTaskHandOverDto> queryVehicleSealByTaskId(TruckTaskDetailEntity qeurySeal) {
		
		return this.getSqlSession().selectList(NAMESPACE+"queryVehicleSealByTaskId",qeurySeal);
	}
	
	/**
	 * 更新车辆任务明细信息
	 * @author 105869
	 * @date 2014年9月2日 16:24:35
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#updateTruckTaskDetail(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override
	public int updateTruckTaskDetail(TruckTaskDetailEntity truckTaskDetail) {
		
		return this.getSqlSession().update(NAMESPACE+"updateTruckTaskDetail", truckTaskDetail);
	}
	/**
	 *通过交接单查询交接单信息 ，发车计划信息，卸车信息
	 * @author heyongdong
	 * @date 2014年9月4日 16:57:21
	 * @param handOverBillNo 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryTaskTruckHandOverBill(java.lang.String)
	 */
	@Override
	public List<TruckTaskHandOverDto> queryTaskTruckHandOverBill(String handOverBillNo) {
		
		return this.getSqlSession().selectList(NAMESPACE+"queryTaskTruckHandOverBill",handOverBillNo);
	}
	/**
	 *
	 *查询车辆任务明细信息，通过车牌号、目的站、状态
	 *@author foss-heyongdong
	 *@date 2014年9月11日 09:27:03
	 *@see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#qeuryTruckTaskDetail(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override
	public List<String> queryTruckTaskDetailAndBill(TruckTaskDetailEntity qeuryParam) {
		
		return this.getSqlSession().selectList(NAMESPACE+"queryTruckTaskDetailAndBill", qeuryParam);
	}
	/**
	 * 通过配载单号查询 新增任务车辆需要的基本信息（用于只装不卸的业务）
	 * @author 105869
	 * @date 2014年9月16日 13:50:19
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryUncreateTaskVehicleAssem(java.lang.String)
	 */
	@Override
	public List<TruckTaskHandOverDto> queryUncreateTaskVehicleAssem(String vehicleAssembleNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryUncreateTaskVehicleAssem",vehicleAssembleNo);
	}
	
	/**
	 * 通过出发站、目的站、车牌号查询在途或者未出发的车辆任务
	 * @author 105869
	 * @date 2014年9月17日 16:55:25 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryTruckTaskAndDetail(com.deppon.foss.module.transfer.load.api.shared.dto.TruckTaskHandOverDto)
	 */
	@Override
	public TruckTaskHandOverDto queryTruckTaskAndDetail(TruckTaskDetailEntity queryTask) {
		return (TruckTaskHandOverDto)this.getSqlSession().selectOne(NAMESPACE+"queryTruckTaskAndDetail",queryTask);
	}
	
	/**
	* @description 查询快递系统同步过来且未生成车辆任务的交接单信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryUnCreateTaskTruckHandOverForWk(java.lang.String)
	* @author 283250-foss-liuyi
	* @update 2016年4月27日 下午7:18:02
	* @version V1.0
	*/
	@Override
	public List<TruckTaskHandOverDto> queryUnCreateTaskTruckHandOverForWk(
			String handOverBillNo) {
		
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("billNo", handOverBillNo);
		return this.getSqlSession().selectList(NAMESPACE+"queryUnCreateTaskTruckHandOverForWk",paramsMap);
	}
	
	/**
	* @description 根据主键查询单据信息
	* @param truckTaskBillId
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月27日 下午8:15:59
	*/
	@Override
	public TruckTaskBillEntity queryTruckTaskBillById(String truckTaskBillId) {
		if(truckTaskBillId==null)
			return null;
		return (TruckTaskBillEntity)this.getSqlSession().selectOne(NAMESPACE+"queryTruckTaskBillById",truckTaskBillId);
	}
	/**
	* @description 根据主键查询车辆任务明细信息
	* @param truckTaskDetailId
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月27日 下午8:16:28
	*/
	@Override
	public TruckTaskDetailEntity queryTruckTaskDetailById(
			String truckTaskDetailId) {
		if(truckTaskDetailId==null)
			return null;
		return (TruckTaskDetailEntity)this.getSqlSession().selectOne(NAMESPACE+"queryTruckTaskDetailById",truckTaskDetailId);
	}
	/**
	* @description 根据主键查询车辆任务主表信息
	* @param truckTaskId
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月27日 下午8:16:47
	*/
	@Override
	public TruckTaskEntity queryTruckTaskById(String truckTaskId) {
		if(truckTaskId==null)
			return null;
		return (TruckTaskEntity)this.getSqlSession().selectOne(NAMESPACE+"queryTruckTaskById",truckTaskId);
	}
	
	
	
	/**
	* @description 根据单据编号查询单据信息
	* @param truckTaskBillId
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月27日 下午8:15:59
	*/
	@Override
	public List<TruckTaskBillEntity> queryTruckTaskBillByBillNo(String billNo) {
		if(billNo==null)
			return null;
		return this.getSqlSession().selectList(NAMESPACE+"queryTruckTaskBillByBillNo",billNo);
	}
	
	
	/**
	* @description 根据车辆任务明细编号 查询单据信息
	* @param truckTaskBillId
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月27日 下午8:15:59
	*/
	@Override
	public List<TruckTaskBillEntity> queryTruckTaskBillByDetailNo(String parentId) {
		if(parentId==null)
			return null;
		return this.getSqlSession().selectList(NAMESPACE+"queryTruckTaskBillByDetailNo",parentId);
	}
}