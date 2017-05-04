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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/service/IVehicleSealService.java
 *  
 *  FILE NAME          :IVehicleSealService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.RelationInfoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealDestDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealOrigDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.BillInfoDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.SealDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.VehicleSealInfoDto;

/**
 *	绑定与校验装车封签service 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:zyx,date:2012-10-11 下午2:22:01 </p>
 * @author ibm-zhangyixin
 * @date 2012-10-11 下午2:22:01
 * @since
 * @version
 */
public interface IVehicleSealService extends IService {
	
	/**
	 * 根据ID得到Seal 封签
	 * @author ibm-zhangyixin
	 * @date 2012-10-23 下午5:05:51
	 */
	SealEntity getSealById(String id);
	
	/**
	 * <p>查询绑定于校验装车封签</p> 
	 * @author ibm-zhangyixin
	 * @date 2012-10-12 上午11:13:40
	 * @return
	 * @see
	 */
	List<SealDto> queryAllvehicleSealList(SealDto params, int limit, int start);

	/**
	 * <p>查询绑定于校验装车封签总记录数</p> 
	 * @author ibm-zhangyixin
	 * @date 2012-10-16 下午4:21:52
	 */
	Long getTotCount(SealDto params);
	
	/**
	 * @Title: getTotCount 
	 * @Description: 查询绑定于校验装车封签总记录数
	 * @author sunjianbo
	 * @date 2014-5-19 下午5:46:44 
	 * @param params
	 * @param limit
	 * @param start
	 * @return Long
	 * @throws 
	 */ 
	Long getTotCount(SealDto params, int limit, int start);
	
	/**
	 * 新增绑定装车封签</br>
	 * 做两个操作</br>
	 * 1,新增封签</br>
	 * 2,新增封签明细
	 * @author ibm-zhangyixin
	 * @date 2012-10-31 下午6:21:42
	 */
	int addSeal(SealEntity seal,
			List<SealOrigDetailEntity> sealOrigDetails);
	
	/**
	 * 根据车牌号查询绑定的封签明细
	 * @author ibm-zhangyixin
	 * @date 2012-10-31 下午6:22:17
	 */
	List<SealOrigDetailEntity> getSealOrigDetailsByVehicleNo(String vehicleNo);
	
	/**
	 * 根据车牌号查询绑定的封签
	 * @author ibm-zhangyixin
	 * @date 2012-10-31 下午6:22:49
	 */
	SealEntity getSealByVehicleNo(String vehicleNo);
	
	/**
	 * 修改绑定装车封签数据 
	 * 操作步骤:</br>
	 * 	1,更新封签主表</br>
	 * 	2,删除封签明细</br>
	 * 	3,新增封签</br>
	 * @author ibm-zhangyixin
	 * @date 2012-11-1 上午11:27:22
	 */
	int editSealOrig(SealEntity seal,
			List<SealOrigDetailEntity> sealOrigDetails);
	
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
	 * 新增到达封签 
	 * @author ibm-zhangyixin
	 * @date 2012-11-1 上午11:40:21
	 */
	int insertSealDestDetail(List<SealDestDetailEntity> sealDestDetails);
	
	/**
	 * 根据SealId查询出装车封签 
	 * @author ibm-zhangyixin
	 * @date 2012-10-23 下午5:17:23
	 */
	List<SealOrigDetailEntity> getSealOrigDetailsBySealId(String sealId);
	
	/**
	 * 根据SealId查询出卸车封签 
	 * @author ibm-zhangyixin
	 * @date 2012-10-23 下午5:17:23
	 */
	List<SealDestDetailEntity> getSealDestDetailsBySealId(String sealId);

	/**
	 * 更新封签, 并保存到达封签明细</br>
	 * 操作步骤</br>
	 * 1,更新封签
	 * 2,保存到达封签明细
	 * @author ibm-zhangyixin
	 * @date 2012-10-22 下午2:28:35
	 */
	void updateSeal(SealEntity seal, List<SealDestDetailEntity> sealDestDetails, Boolean isdiff);

	/**
	 * 根据车牌号查询司机信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-19 下午6:13:37
	 */
	RelationInfoEntity queryDriverNameByVehicleNo(String vehicleNo);

	/**
	 * 根据车牌号查询司机信息
	 * (先根据车牌号找到车辆任务明细ID, 再根据车辆任务明细ID关联到交接单查询)
	 * @author ibm-zhangyixin
	 * @date 2013-1-5 下午4:50:27
	 */
	/*
	 * RelationInfoEntity queryDriverInfoByVehicleNo(String vehicleNo);
	 */

	/**
	 * 根据车辆任务ID查询单据信息
	 * 只查询出一级单据
	 * @author ibm-zhangyixin
	 * @date 2013-1-8 上午11:06:21
	 */
	List<BillInfoDto> queryBillInfoByTruckTaskId(String truckTaskId);

	/**
	 * 删除封签关系
	 * 	如果封签类型为绑定封签, 并且车辆出发状态为未到达才可以修改, 删除
	 * @author ibm-zhangyixin
	 * @date 2013-1-9 下午2:14:36
	 */
	int deleteSeal(String id);
	
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

	/****
	 * 根据truckTaskId查询出交接单中的司机信息
	 * @author ibm-zhangyixin
	 * @date 2013-3-15 上午11:50:00
	 */
	SealDto queryDriverInfoByTruckTaskId(String truckTaskId);

	/** 
	 * @Title: queryVehicleSealInfo 
	 * @Description: 根据车牌号查询出封签的信息
	 * 	车辆任务ID, 车辆任务状态等...
	 *  提供给交接单的接口
	 * @param vehicleNo
	 * @return    
	 * @return VehicleSealInfoDto    返回类型 
	 * queryVehicleSealInfo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-3-27下午4:46:12
	 */ 
	VehicleSealInfoDto queryVehicleSealInfo(String vehicleNo);

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
	 * Date:2013-4-18下午3:12:34
	 */ 
	TruckTaskDetailEntity getTruckTaskDetailByTruckId(String truckTaskId);

	/** 
	 * @Title: querySealDestDetailsBySealNo 
	 * @Description: 根据封签号查询出所有的到达封签
	 * @param sealNo
	 * @return    
	 * @return List<SealOrigDetailEntity>    返回类型 
	 * querySealDestDetailsBySealNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-19下午5:42:51
	 */ 
	List<SealDestDetailEntity> querySealDestDetailsBySealNo(String sealNo);

	/** 
	 * @Title: querySealOrigDetailsBySealNo 
	 * @Description: 根据封签号查询出所有的出发封签
	 * @param sealNo
	 * @return    
	 * @return List<SealOrigDetailEntity>    返回类型 
	 * querySealOrigDetailsBySealNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-19下午5:43:07
	 */ 
	    
	List<SealOrigDetailEntity> querySealOrigDetailsBySealNo(String sealNo);

	/** 
	 * @Title: querySealByTruckTaskId 
	 * @Description: 根据车辆任务id找封签
	 * @param seal
	 * @return    
	 * @return List<SealEntity>    返回类型 
	 * querySealByTruckTaskId
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-27下午2:28:20
	 */ 
	List<SealEntity> querySealByTruckTaskId(SealEntity seal);

	/** 
	 * @Title: querySealOrigDetailsBySealNoAndId 
	 * @Description: 根据封签号和封签明细ID查询出所有的出发封签 
	 * @param sealNo
	 * @param id
	 * @return    
	 * @return List<SealOrigDetailEntity>    返回类型 
	 * querySealOrigDetailsBySealNoAndId
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-7下午3:56:45
	 */ 
	List<SealOrigDetailEntity> querySealOrigDetailsBySealNoAndId(String sealNo,
			String id);
	
	/**
	 * @Title: autoReportSlipError 
	 * @Description: 自动上传封签差错到OA		JOB执行     
	 * @return void    返回类型 
	 * autoReportSlipError
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-16下午5:19:58
	 */
	void autoReportSlipError();

	/** 
	 * @Title: querySealDestDetailsBySealId 
	 * @Description: 根据封签ID查出到达封签明细
	 * @param sealId
	 * @return    
	 * @return List<SealDestDetailEntity>    返回类型 
	 * querySealDestDetailsBySealId
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-16下午5:39:24
	 */ 
	List<SealDestDetailEntity> querySealDestDetailsBySealId(String sealId);

	/** 
	 * @Title: querySuperiorOrgByOrgCode 
	 * @Description: 根据部门code找顶级组织  
	 * @param orgCode
	 * @return    
	 * @return OrgAdministrativeInfoEntity    返回类型 
	 * querySuperiorOrgByOrgCode
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-6-19下午1:51:10
	 */ 
	OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(String orgCode);

	/**
	 * 车辆到达时将暂存状态的封签更新为已校验
	 * @author 163580
	 * @date 2014-2-25 上午9:26:01
	 * @param vehicleNo
	 * @return
	 * @see
	 */
	public int updateSealForArrival(String vehicleNo);
	
	/**
	 * 通过任务id 和目的站查询车辆任务
	 * @author 105869
	 * @date 2014年10月23日 15:32:18
	 * @param id
	 * @param currentDeptCode
	 * @param isStatus
	 * @returnTruckTaskDetailEntity
	 */
	TruckTaskDetailEntity qeuryTruckTaskDetailByTaskIdAndDestOrgCode(String id,
			String currentDeptCode, String isStatus);

	
	//
	/**
	 *com.deppon.foss.module.transfer.load.api.server.service  
	 *@desc 根据任务车辆ID，状态 PRE，当前校验部门顶级外场 查询预分配月台号
	 *@param 
	 *@return String
	 *@author 105795
	 *@date 2015年5月6日下午2:18:34 
	 */
	String queryPrePlatformCodeByTruckTaskId(String orgCode,String truckTaskId);
	
	/**
	 *@desc 根据到达部门、车牌号查询任务车辆id 默认查询7天之内数据
	 *@param orgCode,vehicleNo
	 *@return String
	 *@author 105795
	 *@date 2015年7月10日 下午4:32:57 
	 */
	String queryTruckTaskIdByVehicleNo(String orgCode,String vehicleNo);
	
}