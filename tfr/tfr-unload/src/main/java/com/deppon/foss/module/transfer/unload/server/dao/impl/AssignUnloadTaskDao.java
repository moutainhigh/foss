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
 *  PROJECT NAME  : tfr-unload
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/server/dao/impl/AssignUnloadTaskDao.java
 *  
 *  FILE NAME          :AssignUnloadTaskDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.server.dao.IBatchSaveProcessDao;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskTotalDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.TruckTaskDetailDto;
import com.deppon.foss.util.CollectionUtils;


public class AssignUnloadTaskDao extends iBatis3DaoImpl implements IAssignUnloadTaskDao{
	private static final String NAMESPACE = "tfr-unload.";
	private IBatchSaveProcessDao batchSaveProcessDao;
	public void setBatchSaveProcessDao(IBatchSaveProcessDao batchSaveProcessDao) {
		this.batchSaveProcessDao = batchSaveProcessDao;
	}

	/** 
	 * 查询到达车辆
	 * @author 042795-foss-duyi
	 * @date 2012-10-19 下午2:24:35
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryArriveVehicle(com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveVehicleDto, int, int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AssignUnloadTaskTotalDto> queryArriveTransferVehicle(AssignUnloadTaskTotalDto vehicle) {
		return this.getSqlSession().selectList(NAMESPACE+"queryArriveTransferVehicle", vehicle);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<AssignUnloadTaskTotalDto> queryArrivePickUpVehicle(AssignUnloadTaskTotalDto vehicle) {
		return this.getSqlSession().selectList(NAMESPACE+"queryArrivePickUpVehicle", vehicle);
	}
	/** 
	 * 查询已到达中转单据
	 * @author 042795-foss-duyi
	 * @date 2012-10-19 下午2:24:35
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryArriveBill(com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveBillDto)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ArriveBillDto> queryArriveTransderBill(ArriveBillDto bill) {
		return this.getSqlSession().selectList(NAMESPACE+"queryArriveTransderBill",bill);
	}

	/** 
	 * 新增分配记录
	 * @author 042795-foss-duyi
	 * @date 2012-10-19 下午2:24:35
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#insert(java.util.List)
	 */
	@Override
	public int insert(List<AssignUnloadTaskDto> tasks) {
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_INSERT, NAMESPACE+"inserta", tasks);
	}
	
	/**
	* @description 分配卸车任务后批量更新快递交接单状态
	* @param tasks
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年6月15日 下午6:22:44
	 */
	public int update(List<AssignUnloadTaskDto> tasks){
		return batchSaveProcessDao.batchUpdateProcess(UnloadConstants.BATCH_SAVE_TYPE_UPDATE, NAMESPACE+"batchUpdate", tasks);
	}

	/** 
	 * 查询中转单据状态
	 * @author 042795-foss-duyi
	 * @date 2012-10-23 上午11:23:30
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryBillState(java.util.List)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ArriveBillDto> queryTransferBillState(List<ArriveBillDto> bills) {
		return this.getSqlSession().selectList(NAMESPACE+"queryTransferBillsState", bills);
	}

	/** 
	 * 更改单据状态
	 * @author 042795-foss-duyi
	 * @date 2012-10-23 下午12:17:24
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#updateArriveBillState(java.util.List)
	 */
	@Override
	public int updateArriveTransferBillState(List<ArriveBillDto> bills) {
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_UPDATE, NAMESPACE+"updateArriveTransferBillState", bills);
	}

	/** 
	 * 刷新未开始任务
	 * @author 042795-foss-duyi
	 * @date 2012-10-23 下午3:54:56
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryUnStartTask(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AssignUnloadTaskTotalDto> queryUnStartTask(
			AssignUnloadTaskDto taskState,int limit,int start) {
		return this.getSqlSession().selectList(NAMESPACE+"queryUnStartTask",taskState,new RowBounds(start,limit));
	}

	/** 
	 * 将任务逻辑删除
	 * @author 042795-foss-duyi
	 * @date 2012-10-24 下午1:56:58
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#cancelAssignUnloadTask(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	@Override
	public int cancelAssignUnloadTask(AssignUnloadTaskDto task) {
		return this.getSqlSession().update(NAMESPACE+"cancelAssignUnloadTask", task);
	}

	/** 
	 * 找到一个车里同时分配的任务
	 * @author 042795-foss-duyi
	 * @date 2012-10-24 下午2:06:31
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryTaskByVehicle(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AssignUnloadTaskDto> queryTaskByVehicle(
			AssignUnloadTaskDto assignMsg) {
		return this.getSqlSession().selectList(NAMESPACE+"queryTaskByVehicle", assignMsg);
	}

	/** 
	 * 查询刷新记录条数
	 * @author dp-duyi
	 * @date 2012-10-31 上午8:21:58
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryUnStartTask(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	@Override
	public Long queryUnStartTaskCount(AssignUnloadTaskDto taskState) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryUnStartTaskCount",taskState);
	}

	/** 
	 * 查询分配记录
	 * @author dp-duyi
	 * @date 2012-10-31 下午3:04:34
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryAssignUnloadTask(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AssignUnloadTaskTotalDto> queryAssignUnloadTask(
			AssignUnloadTaskDto task,int limit,int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryAssignUnloadTask",task,rowBounds);
	}

	/** 
	 *查询中转分配明细
	 * @author dp-duyi
	 * @date 2012-10-31 下午3:04:34
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryAssignUnloadTaskDetail(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ArriveBillDto> queryAssignTransferUnloadTaskDetail(
			AssignUnloadTaskDto task) {
		return this.getSqlSession().selectList(NAMESPACE+"queryAssignTransferUnloadTaskDetail",task);
	}

	/** 
	 * 查询分配记录条数
	 * @author dp-duyi
	 * @date 2012-10-31 下午3:09:37
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryAssignUnloadTaskCount(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	@Override
	public Long queryAssignUnloadTaskCount(AssignUnloadTaskDto task) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryAssignUnloadTaskCount",task);
	}

	/** 
	 * 查询理货员状态:在线、离线
	 * @author dp-duyi
	 * @date 2012-11-21 上午10:59:44
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryLoaderState(java.util.List)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<LoaderDto> queryLoaderState(List<LoaderDto> loaderCodes) {
		return this.getSqlSession().selectList(NAMESPACE+"queryLoaderState",loaderCodes);
	}

	/** 
	 * 查询理货员已完成货量
	 * @author dp-duyi
	 * @date 2012-11-21 上午11:23:37
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryLoaderFinishedWorkLoad(java.util.List)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<LoaderDto> queryLoaderFinishedWorkLoad(Map<String,Object> condition) {
		return this.getSqlSession().selectList(NAMESPACE+"queryLoaderFinishedWorkLoad",condition);
	}

	/** 
	 * 查询理货员未完成货量
	 * @author dp-duyi
	 * @date 2012-11-21 上午11:23:37
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryLoaderUnFinishedWorkLoad(java.util.List)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<LoaderDto> queryLoaderUnFinishedWorkLoad(
			List<LoaderDto> loaderCodes) {
		return this.getSqlSession().selectList(NAMESPACE+"queryLoaderUnfinishedWorkLoad",loaderCodes);
	}

	/** 
	 * 查询已到达接送货单据
	 * @author dp-duyi
	 * @date 2012-11-21 下午8:07:32
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryArrivePickUpBill(com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveBillDto)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ArriveBillDto> queryArrivePickUpBill(ArriveBillDto bill) {
		return this.getSqlSession().selectList(NAMESPACE+"queryArrivePickUpBill",bill);
	}

	/** 
	 * 新增前查询到达接送货单据状态
	 * @author dp-duyi
	 * @date 2012-11-21 下午8:10:52
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryPickUpBillState(java.util.List)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ArriveBillDto> queryPickUpBillState(List<ArriveBillDto> bills) {
		return this.getSqlSession().selectList(NAMESPACE+"queryPickUpBillsState", bills);
	}

	/** 
	 * 新增和取消分配时修改接送货单据状态
	 * @author dp-duyi
	 * @date 2012-11-21 下午8:10:52
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#updateArrivePickUpBillState(java.util.List)
	 */
	@Override
	public int updateArrivePickUpBillState(List<ArriveBillDto> bills) {
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_UPDATE, NAMESPACE+"updateArrivePickUpBillState", bills);
	}

	/** 
	 * 查询接送货分配记录明细
	 * @author dp-duyi
	 * @date 2012-11-21 下午8:35:12
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryAssignPickUpUnloadTaskDetail(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ArriveBillDto> queryAssignPickUpUnloadTaskDetail(
			AssignUnloadTaskDto task) {
		return this.getSqlSession().selectList(NAMESPACE+"queryAssignPickUpUnloadTaskDetail", task);
	}

	/** 
	 * 查询进行中卸车任务
	 * @author dp-duyi
	 * @date 2013-4-15 下午5:48:42
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryUnfinishedTask(java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<String> queryUnfinishedTask(String loaderCode, String vehicleNo) {
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("loaderCode", loaderCode);
		condition.put("vehicleNo", vehicleNo);
		return this.getSqlSession().selectList(NAMESPACE+"queryUnfinishedTask", condition);
	}

	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-4-20 上午11:39:16
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#updateArriveTransferBillStateToAssigned(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int updateArriveTransferBillByState(String billNo,
			String billLevel, String beforeBillState, String afterBillState) {
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("billNo", billNo);
		condition.put("billLevel", billLevel);
		condition.put("beforeBillState", beforeBillState);
		condition.put("afterBillState", afterBillState);
		return this.getSqlSession().update(NAMESPACE+"updateArriveTransferBillByState", condition);
	}

	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-4-20 上午11:39:16
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#updateArrivePickUpBillStateToAssigned(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int updateArrivePickUpBillByState(String billNo,
			String beforeBillState, String afterBillState) {
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("billNo", billNo);
		condition.put("beforeBillState", beforeBillState);
		condition.put("afterBillState", afterBillState);
		return this.getSqlSession().update(NAMESPACE+"updateArrivePickUpBillByState", condition);
	}

	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-4-20 上午11:39:16
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryPickUpArrivedCount(java.util.List<String>)
	 */
	@Override
	public int queryPickUpArrivedCount(List<String> orgCode) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("orgCode", orgCode);
		return  (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryPickUpArrivedCount",condition);
	}

	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-4-20 上午11:39:16
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryTransferArrivedCount(java.util.List<String>, java.lang.String)
	 */
	@Override
	public int queryTransferArrivedCount(List<String> orgCodes,
			String vehicleType) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("vehicleType", vehicleType);
		condition.put("orgCodes", orgCodes);
		return  (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryTransferArrivedCount",condition);
	}

	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-4-20 上午11:39:16
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryAssignUnloadedTask( com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AssignUnloadTaskTotalDto> queryAssignUnloadedTask(
			AssignUnloadTaskDto task) {
		return this.getSqlSession().selectList(NAMESPACE+"queryAssignUnloadTask",task);
	}

	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-4-20 上午11:39:16
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryArriveTransferVehicle(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskTotalDto, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<AssignUnloadTaskTotalDto> queryArriveTransferVehicle(
			AssignUnloadTaskTotalDto vehicle, int limit, int start) {
		return this.getSqlSession().selectList(NAMESPACE+"queryArriveTransferVehicle", vehicle,new RowBounds(start,limit));
	}
	
	
	@Override
	public List<AssignUnloadTaskTotalDto> querySalesDeptArriveTransferVehicle(
			AssignUnloadTaskTotalDto vehicle, int limit, int start) {
		return this.getSqlSession().selectList(NAMESPACE+"querySalesDeptArriveTransferVehicle", vehicle,new RowBounds(start,limit));
	}
	
	
	@Override
	public List<AssignUnloadTaskTotalDto> querySalesDeptArriveTransferVehicle(
			AssignUnloadTaskTotalDto vehicle) {
		return this.getSqlSession().selectList(NAMESPACE+"querySalesDeptArriveTransferVehicle", vehicle);
	}

	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-4-20 上午11:39:16
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao1#queryArrivePickUpVehicle(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskTotalDto, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<AssignUnloadTaskTotalDto> queryArrivePickUpVehicle(
			AssignUnloadTaskTotalDto vehicle, int limit, int start) {
		return this.getSqlSession().selectList(NAMESPACE+"queryArrivePickUpVehicle", vehicle,new RowBounds(start,limit));
	}



	/**
	 * 
	 * <p>新增前查询快递集中交接单单据状态</p> 
	 * @author chenmingyan
	 * @date 2015-2-2 上午11:05:54
	 * @param bills
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao#queryExpressBillState(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArriveBillDto> queryExpressBillState(List<ArriveBillDto> bills) {
		return this.getSqlSession().selectList(NAMESPACE+"queryExpressBillState", bills);
	}
	
	/**
	 * 
	 * <p>根据状态修改快递集中交接单单据状态</p> 
	 * @author chenmingyan
	 * @date 2015-2-2 下午1:58:01
	 * @param billNo
	 * @param beforeBillState
	 * @param afterBillState
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao#updateArriveExpressBillByState(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int updateArriveExpressBillByState(String billNo,
			String beforeBillState, String afterBillState) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("billNo", billNo);
		map.put("beforeBillState", beforeBillState);
		map.put("afterBillState", afterBillState);
		return this.getSqlSession().update(NAMESPACE+"updateArriveExpressBillByState", map);
	}
	
	/**
	 * 
	 * <p>新增和取消分配时修改快递集中交接单单据状态</p> 
	 * @author chenmingyan
	 * @date 2015-2-2 下午1:58:06
	 * @param bills
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao#updateArriveExpressBillState(java.util.List)
	 */
	@Override
	public int updateArriveExpressBillState(List<ArriveBillDto> bills) {
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_UPDATE, NAMESPACE+"updateArriveExpressBillState", bills);
	}
	
	/**
	 * 
	 * <p>新增和取消分配时修改零担电子面单交接单单据状态</p> 
	 * @author 322610
	 * @date 2016-7-28 17:30:15
	 * @param bills
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao#updateElecWayBillState(java.util.List)
	 */
	@Override
	public int updateElecWayBillState(List<ArriveBillDto> bills) {
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_UPDATE, NAMESPACE+"updateElecWayBillState", bills);
	}
	
	/**
	 * 
	 * <p>查询已到快递集中</p> 
	 * @author chenmingyan
	 * @date 2015-2-2 下午5:28:49
	 * @param vehicle
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao#queryArriveExpressVehicle(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskTotalDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignUnloadTaskTotalDto> queryArriveExpressVehicle(
			AssignUnloadTaskTotalDto vehicle) {
		return this.getSqlSession().selectList(NAMESPACE+"queryArriveExpressVehicle", vehicle);
	}
	
	/***
	 * 
	 * <p>查询快递集中卸车分配记录明细</p> 
	 * @author chenmingyan
	 * @date 2015-2-3 上午9:21:15
	 * @param task
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao#queryAssignExpressUnloadTaskDetail(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArriveBillDto> queryAssignExpressUnloadTaskDetail(
			AssignUnloadTaskDto task) {
		return this.getSqlSession().selectList(NAMESPACE+"queryAssignExpressUnloadTaskDetail", task);
	}
	
	/**
	 * 
	 * <p>查询快递集中卸车单据</p> 
	 * @author chenmingyan
	 * @date 2015-2-4 上午9:39:43
	 * @param bill
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao#queryArriveExpressBill(com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveBillDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArriveBillDto> queryArriveExpressBill(ArriveBillDto bill) {
		if(null != bill.getArriveTime() && !"".equals(bill.getArriveTime())){
			String arriveTime = bill.getArriveTime().substring(0, ConstantsNumberSonar.SONAR_NUMBER_10);
			bill.setUnloadBeginTime(arriveTime + " 00:00:00");
			bill.setUnloadEndTime(arriveTime + " 23:59:59");
		}
		bill.setAssignState(TaskTruckConstant.BILL_ASSIGN_STATE_UNASSIGN);
		return this.getSqlSession().selectList(NAMESPACE+"queryArriveExpressBill",bill);
	}
	
	/**
	 * 
	 * <p>查询电子运单卸车单据</p> 
	 * @author 322610
	 * @date 2016-7-23 16:52:20
	 * @param bill
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao#queryElecWayBill(com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveBillDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArriveBillDto> queryElecWayBill(ArriveBillDto bill) {
		if(null != bill.getArriveTime() && !bill.getArriveTime().equals("")){
			String arriveTime = bill.getArriveTime().substring(0, ConstantsNumberSonar.SONAR_NUMBER_10);
			bill.setUnloadBeginTime(arriveTime + " 00:00:00");
			bill.setUnloadEndTime(arriveTime + " 23:59:59");
		}
		bill.setAssignState(TaskTruckConstant.BILL_ASSIGN_STATE_UNASSIGN);
		return this.getSqlSession().selectList(NAMESPACE+"queryElecWayBill", bill);
	}
	
	/**
	 * 
	 * <p>分页查询快递集中卸车</p> 
	 * @author chenmingyan
	 * @date 2015-2-5 下午1:59:42
	 * @param vehicle
	 * @param limit
	 * @param start
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao#queryArriveExpressVehicle(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskTotalDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignUnloadTaskTotalDto> queryArriveExpressVehicle(
			AssignUnloadTaskTotalDto vehicle, int limit, int start) {
		return this.getSqlSession().selectList(NAMESPACE+"queryArriveExpressVehicle", vehicle,new RowBounds(start,limit));
	}
	
	/**
	 * @desc: 根据车牌号查询任务车辆id
	 * @date :2015-05-06
	 * @author:wqh
	 * 
	 * */
	public String queryTruckTaskIdByVehicleNo(String vehicleNo) {
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryTruckTaskIdByVehicleNo",vehicleNo);
	}
	/**
	 * @desc: 根据数据字典valueCode查询valueName
	 * @date :2015-05-06
	 * @author:wqh
	 * 
	 * */
	public String queryDictionaryValueNameByValueCode(String valueCode) {
		@SuppressWarnings("unchecked")
		List<String> resultList= this.getSqlSession().selectList(NAMESPACE+"queryDictionaryValueNameByValueCode", valueCode);
        if(CollectionUtils.isNotEmpty(resultList)){
          return resultList.get(0);	
        }else{
        	return null;
        }
	}
	/**
	 * 根据单据编号，车牌号查询分配卸车任务 by wqh
	 * @author:wqh
	 * */
   public AssignUnloadTaskTotalDto queryAssUnloadTaskByBillNo(String vechicleNo,String billNo ){
	   Map<String,String> map=new HashMap<String,String>();
	   map.put("vechicleNo", vechicleNo);
	   map.put("billNo", billNo);
	   @SuppressWarnings("unchecked")
	List<AssignUnloadTaskTotalDto> resultList =this.getSqlSession().selectList(NAMESPACE+"queryAssUnloadTaskByBillNo", map);
	   if(resultList!=null&&resultList.size()>0){
		   return resultList.get(0);
	   }else{
		   return null;
	   }
  }

	/**
	 * 根据 到达部门、单据编号，车牌号查询部分任务车辆信息by wqh
	 * @author:wqh
	 * */
	public TruckTaskDetailDto queryTruckTaskDetailByBillNoAndVechicleNo(String vechicleNo,String billNo,String destOrgCode){
		Map<String,String> map=new HashMap<String,String>();
	    map.put("vechicleNo", vechicleNo);
	    map.put("billNo", billNo);
	    map.put("destOrgCode", destOrgCode);
		@SuppressWarnings("unchecked")
		List<TruckTaskDetailDto> resultList=this.getSqlSession().selectList(NAMESPACE+"queryTruckTaskDetailByBillNoAndVechicleNo", map);
		if(resultList!=null&&resultList.size()>0){
			   return resultList.get(0);
		   }else{
			   return null;
		 }
		
	}
	/**
	 * 根据卸车任务id查询所有的交接单 
	 * @author:wqh
	 * */
	public List<String> queryHandNosByTruckTaskId(String truckTaskId){
		
        return this.getSqlSession().selectList(NAMESPACE+"queryHandNosByTruckTaskId", truckTaskId);		
 
	}
	
	
	/**
	* @description 查询是否是已分配过的卸车任务，用于判断是否二次分配
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao#isExistAllotedUnload(java.lang.String)
	* @author 276198-foss-duhao
	* @update 2015-10-24 下午3:51:16
	* @version V1.0
	*/
	@Override
	public int isExistAllotedUnload(String billNo) {
		
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"isExistAllotedUnload", billNo);
	}
	
	/**
	 * 
	 * <p>新增和取消分配时修改商务专递单据状态和卸车状态</p> 
	 * @author chenlei 272681
	 * @param bills
	 */
	@Override
	public int updateArriveBusinessAirBillState(List<ArriveBillDto> bills) {
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_UPDATE, NAMESPACE+"updateArriveBusinessBillState", bills);
	}
	
	/** 
	 * 新增前查询到达商务专递卸车单据状态
	 * @author chenlei 272681
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ArriveBillDto> queryAirBusinessBillState(List<ArriveBillDto> bills) {
		return this.getSqlSession().selectList(NAMESPACE+"queryAirBusinessBillState", bills);
	}
	
	/** 
	 * 新增前查询电子面单卸车单据状态
	 * @author chenlei 272681
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ArriveBillDto> queryElecTransportBillState(List<ArriveBillDto> bills) {
		return this.getSqlSession().selectList(NAMESPACE+"queryElecTransportBillState", bills);
	}
	
	/**
	 * @desc:分页查询商务专递
	 * @author chenlei 272681
	 * @date:2015-8-14
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignUnloadTaskTotalDto> queryArriveBusinessVehicle(
				AssignUnloadTaskTotalDto vehicle, int limit, int start) {
	   return this.getSqlSession().selectList(NAMESPACE+"queryArriveBusinessVehice", vehicle,new RowBounds(start,limit));
			
	}

	/**
	 * @desc:分页查询商务专递
	 * @author 322610
	 * @date:2016-7-22
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignUnloadTaskTotalDto> queryElecTransportVehicle(
				AssignUnloadTaskTotalDto vehicle, int limit, int start) {
	   return this.getSqlSession().selectList(NAMESPACE+"queryElecTransportVehicle", vehicle,new RowBounds(start,limit));
	}
	
	
	/**
	* @description 分页查询营业部商务专递(不包含快递)
	* @param vehicle
	* @param limit
	* @param start
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年6月25日 下午3:15:18
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignUnloadTaskTotalDto> querySalesDeptArriveBusinessVehicle(
				AssignUnloadTaskTotalDto vehicle, int limit, int start) {
	   return this.getSqlSession().selectList(NAMESPACE+"querySalesDeptArriveBusinessVehicle", vehicle,new RowBounds(start,limit));
			
	}
	
	/**
	* @description 分页查询营业部商务专递(不包含快递)
	* @param vehicle
	* @param limit
	* @param start
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年6月25日 下午3:15:18
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignUnloadTaskTotalDto> querySalesDeptArriveBusinessVehicle(
				AssignUnloadTaskTotalDto vehicle) {
	   return this.getSqlSession().selectList(NAMESPACE+"querySalesDeptArriveBusinessVehicle", vehicle);
			
	}
	
	
	/**
	 * @desc:查询商务专递
	 * @author chenlei 272681
	 * @date:2015-8-14
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignUnloadTaskTotalDto> queryArriveBusinessVehicle(
			AssignUnloadTaskTotalDto vehicle) {
		return this.getSqlSession().selectList(NAMESPACE+"queryArriveBusinessVehice", vehicle);
	}

	/**
	 * @desc:查询零担电子面单
	 * @author songjl 322610
	 * @date:2016-8-9
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignUnloadTaskTotalDto> queryElecTransportVehicle(
			AssignUnloadTaskTotalDto vehicle) {
		return this.getSqlSession().selectList(NAMESPACE+"queryElecTransportVehicle", vehicle);
	}
	
	
	/**
	 * 
	 * <p>查询商务专递卸车单据</p> 
	 * @author chenlei 272681
	 * @date 2015-8-20 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArriveBillDto> queryArriveBusinessBill(ArriveBillDto bill) {
		return this.getSqlSession().selectList(NAMESPACE+"queryArriveBusinessBill",bill);
	}
	
	/**
	 * 
	 * <p>根据状态修改商务专递交接单单据状态</p> 
	 * @author chenlei 272681
	 * @param billNo
	 * @param beforeBillState
	 * @param afterBillState
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao#updateArriveBusinessAirBillByState(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int updateArriveBusinessAirBillByState(String billNo,
			String beforeBillState, String afterBillState) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("billNo", billNo);
		map.put("beforeBillState", beforeBillState);
		map.put("afterBillState", afterBillState);
		return this.getSqlSession().update(NAMESPACE+"updateArriveBusinessAirBillByState", map);
	}
	
	/**
	 * 
	 * <p>根据状态修改电子运单交接单单据状态</p> 
	 * @author 322610
	 * @param billNo
	 * @param beforeBillState
	 * @param afterBillState
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao#updateElecTransportBillByState(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int updateElecTransportBillByState(String billNo,
			String beforeBillState, String afterBillState) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("billNo", billNo);
		map.put("beforeBillState", beforeBillState);
		map.put("afterBillState", afterBillState);
		return this.getSqlSession().update(NAMESPACE+"updateElecTransportBillByState", map);
	}
	
	/**
	 * 
	 * <p>查询已分配任务取消任务时修改商务专递单据状态</p> 
	 * @author chenlei 272681
	 * @date 2015/8/26
	 * @param bills
	 */
	@Override
	public int updateBusinessAirBillState(List<ArriveBillDto> bills) {
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_UPDATE, NAMESPACE+"updateBusinessAirBillState", bills);

	}
	
	/***
	 * 
	 * <p>查询商务专递卸车分配记录明细</p> 
	 * @author chenlei 272681
	 * @date 2015/8/26
	 * @param task
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao#queryAssignBusinessUnloadTaskDetail(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArriveBillDto> queryAssignBusinessUnloadTaskDetail(
			AssignUnloadTaskDto task) {
		return this.getSqlSession().selectList(NAMESPACE+"queryAssignBusinessUnloadTaskDetail", task);
	}
	
	/***
	 * <p>查询零担电子运单卸车分配记录明细</p> 
	 * @author 322610
	 * @date 2016-7-26
	 * @param task
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao#queryElecWayBillTaskDetail(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArriveBillDto> queryElecWayUnloadTaskDetail(
			AssignUnloadTaskDto task) {
		return this.getSqlSession().selectList(NAMESPACE+"queryElecWayUnloadTaskDetail", task);
	}
	
	/**
	 * 修改空运正单交接单卸车状态
	 * @Author 263072
	 * 2015-9-19 14:16:10
	 * @return
	 */
	public int updateAirHandOverBillState(List<ArriveBillDto> bills){
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_UPDATE, NAMESPACE+"updateAirHandoverBillState", bills);
	}
}