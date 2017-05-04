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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/dao/IVehicleSealDao.java
 *  
 *  FILE NAME          :IVehicleSealDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.load.api.shared.domain.RelationInfoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealDestDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealOrigDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.BillInfoDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.SealDto;

/**
 * 
 * 绑定与校验装车封签Dao 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:zyx,date:2012-10-11 下午2:28:15 </p>
 * @author zyx
 * @date 2012-10-11 下午2:28:15
 * @since
 * @version
 */
public interface IVehicleSealDao {
	
	/**
	 * <p>查询绑定于校验装车封签</p> 
	 * @author zyx
	 * @date 2012-10-12 上午11:13:40
	 * @return
	 * @see
	 */
	List<SealDto> queryAllvehicleSealList(SealDto params, int limit, int start);

	/**
	 * 获取总记录数
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 上午11:52:49
	 */
	Long getTotCount(SealDto params);

	/**
	 * 根据车牌号查询绑定的封签明细
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 上午11:53:24
	 */
	List<SealOrigDetailEntity> getSealOrigDetailsBySealVehicleNo(String vehicleNo);

	/**
	 * 根据车牌号查询出封签
	 * 查询出来的封签可能有多个, 默认取seal_time为最新的一个
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 上午11:53:35
	 */
	SealEntity getSealByVehicleNo(String vehicleNo);

	/**
	 * 根据封签ID查询出一条封签记录
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 上午11:54:46
	 */
	SealEntity getSealById(String id);

	/**
	 * 根据封签ID查询出该ID下所有的出发封签明细
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 上午11:55:14
	 */
	List<SealOrigDetailEntity> getSealOrigDetailsBySealId(String sealId);

	/**
	 * 根据封签ID查询出该ID下所有的到达封签明细
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 上午11:55:14
	 */
	List<SealDestDetailEntity> getSealDestDetailsBySealId(String sealId);
	
	/**
	 * 根据车牌号, 出发部门 ,取状态为未到达的任务车辆明细
	 * @author ibm-zhangyixin
	 * @date 2012-11-7 上午10:37:48
	 */
	List<TruckTaskDetailEntity> getTruckTaskDetailsByVehicleNo(SealDto sealDto);
	
	/**
	 * 
	 * @Title: getTruckTaskDetailsByTruckTaskId 
	 * @Description: 根据truckTaskId获取truckTaskDetail 
	 * @param truckTaskId
	 * @return    
	 * @return List<TruckTaskDetailEntity>    返回类型 
	 * getTruckTaskDetailsByTruckTaskId
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-2上午10:23:57
	 */
	List<TruckTaskDetailEntity> getTruckTaskDetailsByTruckTaskId(String truckTaskId);

	/**
	 * 更新封签操作 
	 * @author ibm-zhangyixin
	 * @date 2012-11-1 上午11:34:02
	 */
	int updateSeal(SealEntity seal);

	/**
	 * 根据封签ID删除出发封签明细
	 * @author ibm-zhangyixin
	 * @date 2012-11-1 上午11:37:09
	 */
	int deleteSealOrigDetailBySealId(String sealId);

	/**
	 * 新增出发封签 
	 * @author ibm-zhangyixin
	 * @date 2012-11-1 上午11:40:21
	 */
	int insertSealOrigDetail(List<SealOrigDetailEntity> sealOrigDetails);

	/**
	 * 新增封签 
	 * @author ibm-zhangyixin
	 * @date 2012-11-1 上午11:40:21
	 */
	int insertSeal(SealEntity seal);

	/**
	 * 新增到达封签 
	 * @author ibm-zhangyixin
	 * @date 2012-11-1 上午11:40:21
	 */
	int insertSealDestDetail(List<SealDestDetailEntity> sealDestDetails);

	/**
	 * 根据parentID获取车辆任务信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午2:10:45
	 */
	TruckTaskEntity getTruckTaskById(String parentId);

	/**
	 * 根据车牌号查询车辆任务信息
	 * 	取create_time最新的一条
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午2:10:45
	 */
	TruckTaskEntity getTruckTaskByVehicleNo(String vehicleNo);

	/**
	 * 根据单号查询车辆任务信息
	 * 	取create_time最新的一条
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午2:10:45
	 */
	TruckTaskEntity getTruckTaskByBillNo(String billNo);

	/**
	 * 用户编码获取用户名称
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 下午1:44:23
	 */
	String getUserNameByEmpCode(String empCode);

	/**
	 * 根据部门编码获取部门名称
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 下午1:44:44
	 */
	String getOrgNameByOrgCode(String orgCode);

	/**
	 * 根据封签ID获取到达部门编号
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 上午10:27:42
	 */
	String getDestOrgCodeBySealId(String id);

	/**
	 * 根据封签ID获取车辆任务明细信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 上午11:41:09
	 */
	TruckTaskDetailEntity getTruckTaskDetailBySealId(String id);

	/**
	 * 根据车牌号查询司机信息
	 * (先根据车牌号找到车辆任务明细ID, 再根据车辆任务明细ID关联到交接单查询)
	 * @author ibm-zhangyixin
	 * @date 2013-1-5 下午4:50:27
	 */
	RelationInfoEntity queryDriverInfoByTaskDetailId(String id);

	/**
	 * 根据车辆任务ID查询单据信息
	 * @author ibm-zhangyixin
	 * @date 2013-1-8 上午11:07:38
	 */
	List<BillInfoDto> queryBillInfoByTruckTaskId(Map<String, String> map);

	/**
	 * 根据封签ID删除封签
	 * @author ibm-zhangyixin
	 * @date 2013-1-9 下午2:23:58
	 */
	void deleteSealById(String id);

	/**
	 * 根据车辆任务ID更新车辆任务明细中的封签ID(vehicle_seal_id)
	 * 	绑定封签时, 车辆状态为 "未出发", "在途" 需更新车辆任务明细vehicle_seal_id
	 *	删除绑定关系时, 同上, 更新车辆任务明细 vehicle_seal_id
	 * @author ibm-zhangyixin
	 * @date 2013-1-14 下午5:12:35
	 */
	void updateTruckTaskDetailByTruckTaskId(SealEntity seal);

	/**
	 * 绑定封签后, 如果车辆任务表中的司机为空, 就使用绑定封签时的司机 将其更新
	 * @author ibm-zhangyixin
	 * @date 2013-1-14 下午5:12:35
	 */
	void updateTruckTaskDriverByTruckTaskId(SealEntity seal);

	/**
	 * 根据车辆任务ID在封签表中找封签
	 * @author ibm-zhangyixin
	 * @date 2013-1-18 下午5:37:04
	 */
	List<SealEntity> querySealByTruckTaskId(SealEntity seal);

	/****
	 * 根据truckTaskId查询出交接单中的司机信息
	 * @author ibm-zhangyixin
	 * @date 2013-3-15 上午11:50:00
	 */
	SealDto queryDriverInfoByTruckTaskId(String truckTaskId);

	/** 
	 * @Title: getSealsByVehicleNo 
	 * @Description: 根据车牌号查询出封签 return List
	 * @param vehicleNo
	 * @return    
	 * @return List<SealEntity>    返回类型 
	 * getSealsByVehicleNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-2上午10:13:40
	 */ 
	List<SealEntity> getSealsByVehicleNo(String vehicleNo);

	/** 
	 * @Title: getTruckTaskDetailByTruckId 
	 * @Description: 根据t_opt_truck_task.id找t_opt_truck_task_detail
	 * 返回 t_opt_truck_task_detail.create_time最早的一条
	 * @param truckTaskId
	 * @return    
	 * @return TruckTaskDetailEntity    返回类型 
	 * getTruckTaskDetailByTruckId
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-18下午2:39:32
	 */ 
	TruckTaskDetailEntity getTruckTaskDetailByTruckId(String truckTaskId);

	/** 
	 * @Title: querySealOrigDetailsBySealNo 
	 * @Description: 根据封签号查询出所有的出发封签
	 * @param sealNo
	 * @return    
	 * @return List<SealOrigDetailEntity>    返回类型 
	 * querySealOrigDetailsBySealNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-19下午5:43:22
	 */ 
	List<SealOrigDetailEntity> querySealOrigDetailsBySealNo(String sealNo);

	/** 
	 * @Title: querySealDestDetailsBySealNo 
	 * @Description: 根据封签号查询出所有的到达封签
	 * @param sealNo
	 * @return    
	 * @return List<SealOrigDetailEntity>    返回类型 
	 * querySealDestDetailsBySealNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-19下午5:43:35
	 */ 
	List<SealDestDetailEntity> querySealDestDetailsBySealNo(String sealNo);

	/** 
	 * @Title: querySealOrigDetailsBySealNoAndId 
	 * @Description: 根据封签号和封签明细ID查询出所有的出发封签
	 * @param sealNo
	 * @param sealId
	 * @return    
	 * @return List<SealOrigDetailEntity>    返回类型 
	 * querySealOrigDetailsBySealNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-7下午3:57:23
	 */ 
	List<SealOrigDetailEntity> querySealOrigDetailsBySealNoAndId(String sealNo,
			String sealId);

	/** 
	 * @Title: queryUnReportSeal 
	 * @Description: 查询出未上报差错的封签
	 * @return    
	 * @return List<SealEntity>    返回类型 
	 * queryUnReportSeal
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-16下午5:31:41
	 */ 
	List<SealEntity> queryUnReportSeal();

	/** 
	 * @Title: querySealDestDetailsBySealId 
	 * @Description: 根据封签ID查出到达封签明细
	 * @param sealId
	 * @return    
	 * @return List<SealDestDetailEntity>    返回类型 
	 * querySealDestDetailsBySealId
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-16下午5:39:34
	 */ 
	List<SealDestDetailEntity> querySealDestDetailsBySealId(String sealId);

	/** 
	 * @Title: queryTruckTaskDetailByTruckTaskId 
	 * @Description: 根据车辆任务ID找出所有明细 
	 * @param truckTaskId
	 * @return    
	 * @return List<TruckTaskDetailEntity>    返回类型 
	 * queryTruckTaskDetailByTruckTaskId
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-17下午2:38:46
	 */ 
	List<TruckTaskDetailEntity> queryTruckTaskDetailByTruckTaskId(
			String truckTaskId, String orgCode);

	/** 
	 * @Title: queryTruckTaskDetailByTruckTaskIdAndOrigCode 
	 * @Description: 根据车辆任务ID,出发部门找出所有明细 
	 * @param truckTaskId
	 * @return    
	 * @return List<TruckTaskDetailEntity>    返回类型 
	 * queryTruckTaskDetailByTruckTaskIdAndOrigCode
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-17下午2:38:46
	 */ 
	List<TruckTaskDetailEntity> queryTruckTaskDetailByTruckTaskIdAndOrigCode(
			String truckTaskId, String orgCode);

	/** 
	 * @Title: getTruckTaskByIdCancled 
	 * @Description: 根据parentID获取车辆任务信息, 已作废的会被查询出来
	 * @param parentId
	 * @return    
	 * @return TruckTaskEntity    返回类型 
	 * getTruckTaskByIdCancled
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-6-19下午1:44:58
	 */ 
	TruckTaskEntity getTruckTaskByIdCancled(String parentId);

	/** 
	 * @Title: queryUnDistanceHandover 
	 * @Description: 查询当前部门下未配载的交接单 
	 * @param truckTaskId
	 * @param origOrgCode
	 * @return    
	 * @return List<String>    返回类型 
	 * queryUnDistanceHandover
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-6-20下午4:14:28
	 */ 
	List<String> queryUnDistanceHandover(String truckTaskId, String origOrgCode);

	/** 
	 * @Title: updateSealToInvalid 
	 * @Description: 删除封签, 逻辑删除
	 * @param seal    
	 * @return void    返回类型 
	 * updateSealToInvalid
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-12下午3:47:41
	 */ 
	void updateSealToInvalid(SealEntity seal);

	/**
	 * @Title: queryTruckTaskDetailByTruckTaskId 
	 * @Description: 根据车辆任务ID找出所有明细 
	 * @author 163580
	 * @date 2014-2-24 下午2:39:53
	 * @param truckTaskId
	 * @param destCode
	 * @return
	 * @see
	 */
	Long queryTruckTaskDetailByTruckTaskIdAndDestCode(
			String truckTaskId, String destOrgCode);
	
	/**
	 * 根据车辆任务Id和到达部门查询车辆任务明细
	 * @author 105869
	 * @date 2014年9月29日 17:03:55
	 * @param id
	 * @param currentDeptCode
	 * @param isStatus 
	 * @return
	 */
	TruckTaskDetailEntity qeuryTruckTaskDetailByTaskIdAndDestOrgCode(String id,String currentDeptCode, String isStatus);

	List<TruckTaskDetailEntity> queryTruckTaskDetailByTruckTaskIdAndCode(String id, String deptCode);
	
	
	
	/**
	 *com.deppon.foss.module.transfer.load.api.server.dao  
	 *@desc 根据任务车辆ID，状态 PRE，当前校验部门顶级外场 查询预分配月台号
	 *@param orgCode 外场编码，truckTaskId 任务车辆ID
	 *@return String
	 *@author 105795
	 *@date 2015年4月27日下午3:13:57 
	 */
	String queryPrePlatformCodeByTruckTaskId(String orgCode,String truckTaskId);
	
	/**
	 *com.deppon.foss.module.transfer.load.api.server.dao
	 *@desc 根据到达部门、车牌号查询任务车辆id 默认查询7天之内数据
	 *@param orgCode,vehicleNo
	 *@return String
	 *@author 105795
	 *@date 2015年7月10日 下午4:32:57 
	 */
	String queryTruckTaskIdByVehicleNo(String orgCode,String vehicleNo);
	
}