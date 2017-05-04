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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/server/service/IDepartureService.java
 *  
 *  FILE NAME          :IDepartureService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.server.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.departure.api.shared.domain.QueryDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.RelationInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.VehiclePrintDTO;
/**
 * 
 * 人工放行逻辑接口
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-25 上午9:00:24
 */
public interface IDepartureService extends IService{
	
	/**
	 * 
	 * 分页查询放行申请记录
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-9 上午10:01:56
	 */
	List<TruckDepartEntity> queryDepart(QueryDepartEntity queryEntity, int limit, int start);

	/**
	 * 
	 * 不分页查询放行申请记录
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-9 上午10:01:56
	 */
	List<TruckDepartEntity> queryDepart(QueryDepartEntity queryEntity);
	/**
	 * 取消放行
	 * @param strWaitDepart
	 */
	public void cancleWaitDepart(List<String> strWaitDepart);
	/**
	 * 
	 * 取得记录数，用于分页
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-9 上午10:01:27
	 */
	Long getCount(QueryDepartEntity queryEntity);
	
	
	/**
	 * 通过申请信息的id取消申请信息
	 * @author dp-wangqiang
	 * @date 2012-10-17 下午2:09:50
	 */
	void cancleDeparts(List<TruckDepartEntity> activeList);
	
	/**
	 * 通过申请信息的id取消申请信息（组内接口）
	 * @author dp-wangqiang
	 * @date 2012-10-17 下午2:09:50
	 */
	void cancleDepart(List ids);
	
	/**
	 * 通过申请信息的id激活申请信息
	 * @author dp-wangqiang
	 * @date 2012-10-17 下午2:09:50
	 */
	void activeDepart(List ids);
	
	/**
	 * 打印纸质放行条（对已有申请信息进行操作）
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	void saveManual(TruckDepartEntity manualEntity);
	

	/**
	 * 全部放行.
	 * 
	 * @param manualEntity
	 *            the manual entity
	 * @author foss-zyr
	 * @date 2015-06-24 下午2:09:50
	 */
	 void saveManualAll(TruckDepartEntity manualEntity);
	
	/**
	 * 打印纸质放行条（新增）
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	void addManual(TruckDepartEntity manualEntity);
	
	/**
	 * 新增放行任务
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	void addTask(TruckDepartEntity taskEntity);
	
	/**
	 * 
	 * 对申请信息做操作前校验状态是否过期
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-26 下午5:02:55
	 */
	String validStatus(TruckDepartEntity manualEntity);
	
	/**
	 * 
	 * 查询要放行车牌号下是否有多个已确认状态的派单单号车辆未放行
	 * @author foss-zyr
	 * @date 2015-06-23 09:02:55
	 */
	String releaseReminder(TruckDepartEntity manualEntity);
	
	/**
	 * 
	 * 激活、取消申请前判断状态
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 上午10:40:07
	 */
	Long validBeforeCancleOrActiveDepart(QueryDepartEntity queryEntity);
	
	/**
	 * 
	 * 激活、取消申请前判断状态
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 上午10:40:07
	 */
	Long validBeforeDepart(QueryDepartEntity queryEntity);
	
	
	/**
	 * 查询临时任务
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	List<TruckDepartEntity> queryTemporaryAssignments(QueryDepartEntity queryEntity,
			int limit, int start);
	
	/**
	 * 通过车牌号查询车辆的打印信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	VehiclePrintDTO queryVehicleInfoForPrint(String vehicleNo);
	
	/**
	 * 
	 * 根据当前部门的编码获取对应的外场的编码
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-25 下午3:37:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService#queryBigOrgCode(java.lang.String)
	 */
	String queryBigOrgCode(String currentOrgCode);
	
	/**
	 * 
	 * 放行的时候判断是否是集配交接单，集配交接单不能放行
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-25 下午3:37:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService#queryBigOrgCode(java.lang.String)
	 */
	void isDistanceHandover(String departId,String origOrgCode,String destOrgCode);
	/**
	 * 
	 * 根据任务车辆明细判断是否是集配交接单，集配交接单不能放行
	 * @param departId 放行id
	 * @param origOrgCode 出发部门，PDA使用，其他的传空值过来
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-25 下午3:37:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService#queryBigOrgCode(java.lang.String)
	 */
	void taskIsDistanceHandover(String taskDetailId, String origOrgCode,String destOrgCode);
	
	/** 
	* @Title: convertVehicleCode2Name 
	* @Description: 将英文车牌号转换为中文(例:YUE-X-20000------->粤X20000)
	* @param vehicleNo
	* @return  设定文件 
	* @return String    返回类型 
	* @see convertVehicleCode2Name
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-9 下午2:40:10   
	* @throws 
	*/ 
	RelationInfoEntity convertVehicleCode2Name(String vehicleNo);
	/** 
	 * @Title: encodeFileName 
	 * @Description: 将文件名转成UTF-8编码以防止乱码
	 * @param string
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#encodeFileName(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午4:14:37
	 */ 
	String encodeFileName(String fileName);
	
	/** 
	 * @Title: exportLoadWayBillByTaskNo 
	 * @Description: 根据任务号导出卸车明细
	 * @param taskNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#exportLoadWayBillByTaskNo(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午4:14:37
	 */ 
	public InputStream exportTruckDepartOrArriveByTaskNo(String taskNo);
	
	/**
	 * 
	 * 提供接口给接送货，整车签收时，未到达的车辆统一做到达
	 * @author alfred
	 * @date 2013-9-11 上午9:24:22
	 * @param waybillNo
	 * @see
	 */
	void carLoadArrive(String waybillNo);
	
	/**
	 * 根据任务车辆明细ID查询任务车辆明细
	 * @author 105795
	 * @date 2014年11月26日 16:55:25
	 * @param id
	 * @return
	 */
	public TruckTaskDetailEntity queryTruckTaskDetailById(String id);
	
	/**
	 * 调用tps接口，同步发车到达信息
	 * @Author: 200978  xiaobingcheng
	 * @update: chenlei   2015-10-24 17:10:31
	 * 2015-1-15
	 * @param truckTaskDetailId
	 */
	boolean synchDepartArriveInfoToTps(String truckTaskDetailId,Date departArriveTime,String operateType);
	
}