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
 *  PROJECT NAME  : tfr-arrival-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/arrival/api/server/service/IArrivalService.java
 *  
 *  FILE NAME          :IArrivalService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.arrival.api.server.service;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.ArrivalEntity;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.QueryArrivalEntity;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.LeasedTruckDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.OperationDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.PlatformDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.QueryPlatformDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.TruckActionDetailDto;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.VehicleInfoDTO;
/**
 * 
 * 查询到达数据分页 车辆出发时间获取规则： 车辆出发分属营业部及外场。
 * 车辆出发时间获取方式：分为出发时间（GPS获取
 * ）、出发时间（PDA获取）、出发时间（PDA获取）；
 * 营业部：为GPS获取、交接获取（发车确认）车辆出发时间两种；
 * 外场：为GPS获取、PDA获取、交接获取（手工）三种；
 * 系统保留任一获取方式的车辆出发时间记录 ，但在到达界面中只显示唯一一个
 * ，显示默认优先级为GPS获取、PDA获取、交接获取。 点击确认分配按钮，
 * 系统保留预分配月台记录。 对应月台的可用时间段减去已经“已预分配的”。
 * 月台分配记录为已分配。 未做月台预分配的记录为未分配。
 * 车辆出发部门为本部门，才能做发车确认。
 * 车辆到达部门为本部门，才能做到达确认。
 * 车辆出发或到达部门为本部门，才能执行证件包上交管理。
 * 车辆到达部门为本部门，才能进行外请车尾款支付确认。
 * 系统默认显示发车计划中到达部门为本部门的所有车辆
 * 。车辆中配载单到达部门为本部门的才会在界面显示尾款未结清金额。
 * 只有长途外请车才可以结算。 当前登录部门为外场运作部门时，
 * “查询车辆到达情况”界面，点击发车确认的车辆，产生一条待放行信息，
 * 车辆状态记录为待放行，出发放行类型记录为“任务车辆”。
 * 当车辆到达部门为营业部时
 * ，在车辆出发时，GPS将车辆预计到达时间推送给到达部门 。分配月台按钮，
 * 可以在“查询车辆到达情况”结果列表里选择一条记录，
 * 将车牌带进来，不需要在预分配月台界面重新查询一次。
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-25 上午11:35:55
 * @see com.deppon.foss.module.transfer.arrival.api.server.dao.IArrivalDao#queryArrivalGrid(com.deppon.foss.module.transfer.arrival.api.shared.domain.QueryArrivalEntity,
 *      int, int)
 */
public interface IArrivalService extends IService {
	/**
	 * 查询临时任务,分页
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	List<ArrivalEntity> queryArrivalGrid(QueryArrivalEntity queryEntity,
			int limit, int start);
	/**
	 * 查询临时任务,分页
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	LeasedTruckDTO queryLeasedTruck(QueryArrivalEntity queryEntity);
	/**
	 * 
	 * 查询总记录数
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-30 下午6:32:58
	 */
	Long getCount(QueryArrivalEntity queryEntity);
	/**
	 * 
	 * 根据条件查询最优月台的信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @param billNo
	 *            单号
	 * @param isIdil
	 *            是否空闲
	 * @date 2012-10-30 下午6:32:58
	 */
	List<PlatformDTO> queryPlatformGrid(QueryPlatformDTO dto);
	/**
	 * 
	 * 分配月台时显示的车辆相关信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @param billNo
	 *            单号
	 * @param isIdil
	 *            是否空闲
	 * @date 2012-10-30 下午6:32:58
	 */
	VehicleInfoDTO queryVehicleForPlatform(QueryArrivalEntity queryEntity);
	/**
	 * 
	 * 确认分配选中的月台
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @param billNo
	 *            单号
	 * @param isIdil
	 *            是否空闲
	 * @date 2012-10-30 下午6:32:58
	 */
	VehicleInfoDTO confirmPlatform(VehicleInfoDTO vehicleInfoDTO);
	/**
	 * 
	 * 发车确认
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @param billNo
	 *            单号
	 * @param isIdil
	 *            是否空闲
	 * @date 2012-10-30 下午6:32:58
	 */
	void departConfirm(String truckTaskDetailId, String truckTaskId,String truckDepartId, Date departTime);
	/**
	 * 
	 * 发车确认
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @param truckTaskId
	 *            任务车辆ID
	 * @param origOrgCode
	 *            出发部门
	 * @date 2012-10-30 下午6:32:58
	 */
	void departConfirm(OperationDTO operationDTO);
	/**
	 * 
	 * 到达确认
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @param truckTaskId
	 *            任务车辆ID
	 * @param origOrgCode
	 *            出发部门
	 * @date 2012-10-30 下午6:32:58
	 */
	List<String> arrivalConfirm(OperationDTO operationDTO);
	/**
	 * 
	 * 到达确认
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @param billNo
	 *            单号
	 * @param isIdil
	 *            是否空闲
	 * @date 2012-10-30 下午6:32:58
	 */
	void arrivalConfirm(String truckTaskDetailId, String truckTaskId,
			String vehicleNo, Date planArrivalTime, String truckArriveId,
			String beCarLoad,String actualDepartType);
	/**
	 * 
	 * 取消放行（同时取消同一任务下的所有的任务明细）（出发部门一样的）
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @param billNo
	 *            单号
	 * @param isIdil
	 *            是否空闲
	 * @date 2012-10-30 下午6:32:58
	 */
	void cancleDepart(OperationDTO operationDTO);
	/**
	 * 
	 * 取消放行
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @param billNo
	 *            单号
	 * @param isIdil
	 *            是否空闲
	 * @date 2012-10-30 下午6:32:58
	 */
	void cancleDepart(String truckTaskDetailId, String truckTaskId);
	/**
	 * 
	 * 取消到达（同时取消同一任务下的所有的任务明细）（到达部门一样的）
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @param billNo
	 *            单号
	 * @param isIdil
	 *            是否空闲
	 * @date 2012-10-30 下午6:32:58
	 */
	void cancleArrive(OperationDTO operationDTO);
	/**
	 * 
	 * 取消到达
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @param billNo
	 *            单号
	 * @param isIdil
	 *            是否空闲
	 * @date 2012-10-30 下午6:32:58
	 */
	void cancleArrive(String truckTaskDetailId, String truckTaskId,
			String truckArriveId, String beCarLoad);
	/**
	 * 
	 * 判断目标部门是不是当前部门
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-5 下午4:44:42
	 */
	void validOrgCode(String orgCode);
	/**
	 * 
	 * 校验是否做过封签
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-5 下午4:44:42
	 */
	Long validSail(String truckTaskDetailId);
	
	/**
	 * 
	 * 取消到达前要判断是否已开始
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-5 下午4:44:42
	 */
	boolean isUnloading(String truckTaskDetailId);
	
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
	 * 处理标签
	 * 
	 * @param detailIds
	 */
	void handleLabeledGoods(List<String> detailIds);
	
	/**
	 * 
	 * 提供接口给结算，根据运单和最终配载部门查询车辆到达时间
	 * @author alfred
	 * @date 2013-9-10 上午9:22:26
	 * @param waybillNO
	 * @param destOrgCode
	 * @return
	 * @see
	 */
	//List<Date> queryArrivalByWaybillAndDestOrg(String waybillNO,String destOrgCode);
	/**
	 * 
	 * 增加一条到达信息，truck_arrive
	 * @author alfred
	 * @date 2013-9-11 下午2:00:12
	 * @param vehicleNo
	 * @param planArrivalTime
	 * @return
	 * @see
	 */
	String addTruckArrival(String vehicleNo, Date planArrivalTime);
	
	/**
	 * 
	 * 当前任务车辆任务下配载单是否最终到达
	 * true最终到达 
	 * @author 163580
	 * @date 2013-12-25 上午10:40:20
	 * @param truckTaskDetailId
	 * @return
	 * @see
	 */
	boolean beFinallyArrive(String truckTaskDetailId);
	
	/**
	 * 查询车辆取消出发记录
	 * @author 163580
	 * @date 2014-1-20 上午10:26:42
	 * @param truckTaskDetailId
	 * @return
	 * @see
	 */
	List<TruckActionDetailDto> queryCancelDepartureGrid(String truckTaskDetailId);
	
	/**
	 * 查询车辆取消到达记录
	 * @author 163580
	 * @date 2014-1-20 上午10:27:20
	 * @param truckTaskDetailId
	 * @return
	 * @see
	 */
	List<TruckActionDetailDto> queryCancelArrivalGrid(String truckTaskDetailId);
	
	/**
	 * 校验运单是否配载是否到达
	 * @author 163580
	 * @date 2014-2-18 上午11:21:16
	 * @param waybillNo
	 * @param arrival
	 * @return
	 * @see
	 */
	public String validateWaybillNo(String waybillNo, boolean arrival);
	
	/**
	 * 处理待办
	 * @author 163580
	 * @date 2014-6-27 下午7:16:04
	 * @param id
	 * @return 
	 * @see
	 */
	String handleTodo(String id);
	
	
	/**
	 * (二程接驳出发到达)查询本部门出发的发车任务.
	 * @param the query entity
	 * @param limit
	 * @param start
	 * @return the list
	 * @author gongjp
	 * @date 2015-08-26 下午19:08:51
	 */
	public List<ArrivalEntity> querySecondCarDepartureGrid(QueryArrivalEntity queryEntity,int limit, int start);
	
	/**
	 * 二程接驳出发到达取得信息条数.
	 * 
	 * @param queryEntity
	 *            the query entity
	 * @return the count
	 * @author gongjp
	 * @date 2015-08-26 下午 20:47:34
	 */
	public Long getSecondCarDepartureCount(QueryArrivalEntity queryEntity);
	
	/**
	 * 取得到达本部门信息条数.
	 * @param queryEntity
	 * @return the count
	 * @author gongjp
	 * @date 2015-08-28 上午 11:11:34
	 */
	public Long getSecondCarArrivalCount(QueryArrivalEntity queryEntity);
	/**
	 * 二程接驳出发发车确认.
	 * @return the string
	 * @author gongjp
	 * @date 2015-08-27 上午15:11:34
	 */
	public void secondCarDepartConfirm(OperationDTO operationDTO);
	
	/**
	 * @function (二程接驳到达本部门查询)查询本部门到达的车辆信息.
	 * @author gongjp
	 * @date 2015-08-28 下午09:41:51
	 */
	public List<ArrivalEntity> querySecondCarArrivalGrid(QueryArrivalEntity queryEntity,int limit, int start);
	
	
	
	/**
	 * 二程接驳到达确认.
	 * @return the string
	 * @author gongjp
	 * @date 2015-08-28 上午15:02:35
	 */
	public void secondCarArrivalConfirm(OperationDTO operationDTO);
	
	
	/**
	 * @function (二程接驳发车确认)根据id查询所有发车任务信息
	 * @return the string
	 * @author gongjp
	 * @date 2015-09-01 下午18:24:23
	 */
	public List<ArrivalEntity> checkSecondCarDepartConfirm(OperationDTO dto);
	
	
	/**
	 * @function (二程接驳到达确认)根据id查询所有发车任务信息
	 * @return the string
	 * @author gongjp
	 * @date 2015-09-01 下午18:30:34
	 */
	public List<ArrivalEntity> checkSecondCarArrivalConfirm(OperationDTO dto);
	/**
	 * 
	 * <p>判断是否合伙人部门</p> 
	 * @author 189284 
	 * @date 2016-2-19 上午10:14:09
	 * @param destOrgCode
	 * @return
	 * @see
	 */
	Boolean isPTPOrg(String destOrgCode);

	/**
	 * <p>执行Job整车入库</p> 
	 * @author 316759-wangruipeng
	 * @date 2016-07-14 下午05:03:09
	 * @param truckTaskDetailId
	 * @return
	 * @see
	 */
	public void pushForWholeVehicle(String truckTaskDetailId);
	
}