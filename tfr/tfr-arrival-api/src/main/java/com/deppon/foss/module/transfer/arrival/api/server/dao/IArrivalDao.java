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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/arrival/api/server/dao/IArrivalDao.java
 *  
 *  FILE NAME          :IArrivalDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.arrival.api.server.dao;
import java.util.List;

import com.deppon.foss.module.transfer.arrival.api.shared.domain.ArrivalEntity;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.QueryArrivalEntity;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.TodoWhenArriveTruckEntity;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.TruckArrivalEntity;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.LeasedTruckDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.OperationDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.TruckActionDetailDto;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.WaybillPlanArriveTimeDto;
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
public interface IArrivalDao {
	/**
	 * 查询临时任务,分页
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	List<ArrivalEntity> queryArrivalGrid(QueryArrivalEntity queryEntity,
			int limit, int start);
	/**
	  * 查询任务车辆明细里的交接单。配载单
	  */
	List<ArrivalEntity> queryBillNos(QueryArrivalEntity queryEntity);
	/**
	 * 查询外请车的信息
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
	 * 查询总记录数
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-30 下午6:32:58
	 */
	void insertTruckArrival(TruckArrivalEntity truckArrivalEntity);
	/**
	 * 
	 * 查询总记录数
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-30 下午6:32:58
	 */
	Long isLastTask(ArrivalEntity arrivalEntity);
	/**
	 * 
	 * 更新车辆到达
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-30 下午6:32:58
	 */
	void updateTruckArrival(TruckArrivalEntity truckArrivalEntity);
	/**
	 * 根据车牌号找到最近的一个车辆任务
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	String queryTaskByVehicleNo(String vehicleNo);
	/**
	 * 根据任务车辆id找到所有的交接单号
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	List<String> queryBillByTaskId(String taskId);
	/**
	 * 
	 * 取消到达前要判断是否已开始
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-5 下午4:44:42
	 */
	List<String> isUnloading(String truckTaskDetailId);
	/**
	 * 根据任务车辆id找到所有的明细ID
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	List<OperationDTO> queryTruckDetailByTaskId(OperationDTO operationDTO);
	/**
	 * 
	 * 校验是否做过封签
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-5 下午4:44:42
	 */
	Long validSail(String truckTaskDetailId);
	/**
	 * 到达时查看是否有代办事项
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	List<TodoWhenArriveTruckEntity> queryTodoWhenArriveTruck(String id);
	
	/**
	 * 
	 * <p>返回该车辆任务是否有被GPS跟踪的记录</p> 
	 * @author Administrator
	 * @date 2013-10-17 上午9:05:54
	 * @param truckTaskDetailId
	 * @return
	 * @see
	 */
	Long hasTruckTaskTracking(String truckTaskDetailId);
	
	/**
	 * 当前车辆任务下配载单是否最终到达
	 * 返回值大于零则说明有配载单为最终到达
	 * @author 163580
	 * @date 2013-12-10 上午10:58:50
	 * @param truckTaskDetailId
	 * @return
	 * @see
	 */
	Long beFinallyArrive(String truckTaskDetailId);
	
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
	 * 校验整车运单是否做过配载
	 * @author 163580
	 * @date 2014-2-18 下午12:03:27
	 * @param waybillNo
	 * @return
	 * @see
	 */
	public Long validateWaybillNoAssemble(String waybillNo);
	
	/**
	 * 校验整车运单是否存在未到达的
	 * @author 163580
	 * @date 2014-2-18 下午12:03:42
	 * @param waybillNo
	 * @return
	 * @see
	 */
	public Long validateWaybillNoArrival(String waybillNo);
	
	/**
	 * 根据车辆任务明细id找单据表下所有交接单
	 * @author 163580
	 * @date 2014-6-28 上午11:26:46
	 * @param id
	 * @return
	 * @see
	 */
	List<String> queryBillByTaskDetailId(String id);
	
	
	/**
	 *com.deppon.foss.module.transfer.arrival.api.server.dao  
	 *@desc 根据任务车辆ID查询封签信息 
	 *@param truckTaskIdList
	 *@return List<ArrivalEntity>
	 *@author wqh
	 *@date 2015年6月19日下午2:24:26 
	 */
	public List<ArrivalEntity> querySealByTruckTaskId(List<String> truckTaskIdList);
	
	/**
	 * 根据运单号查询预计到达时间
	 * @author zyr
	 * @date 2015-07-13 下午14:03:42
	 * @param waybillNo
	 * @return
	 * @see
	 */
	WaybillPlanArriveTimeDto queryPlanArriveTime(String waybillNo);
	
	/**
	 * (二程接驳出发到达)查询本部门出发的发车任务.
	 * @author gongjp
	 * @date 2015-08-26 下午19:08:51
	 */
  public List<ArrivalEntity> querySecondCarDepartureGrid(QueryArrivalEntity queryEntity, int limit, int start);
  
  
  
	 /**
		 * (二程接驳出发到达)查询本部门到达的发车任务.
		 * @param the query entity
		 * @param limit
		 * @param start
		 * @return the list
		 * @author gongjp
		 * @date 2015-08-28 下午09:56:51
		 */
	 public List<ArrivalEntity> querySecondCarArrivalGrid(QueryArrivalEntity queryEntity, int limit, int start);
 /**
	 * 取得信息条数.
	 * 
	 * @param queryEntity
	 *            the query entity
	 * @return the count
	 * @author gongjp
	 * @date 2015-08-26 下午 20:47:34
	 */
	public Long getSecondCarDepartureCount(QueryArrivalEntity queryEntity) ;
	
	/**
	 * 二程接驳出发发车确认.
	 * @return the string
	 * @author gongjp
	 * @date 2015-08-27 上午19:15:34
	 */
	public void updateSecondCarDepartConfirm(OperationDTO operationDTO);
	
	
	
	/**
	 * 取得到达本部门信息条数.
	 * @param queryEntity
	 * @return the count
	 * @author gongjp
	 * @date 2015-08-28 上午 11:14:34
	 */
	public Long getSecondCarArrivalCount(QueryArrivalEntity queryEntity);
	
	
	/**
	 * 二程接驳到达本部门到达确认.
	 * @return the string
	 * @author gongjp
	 * @date 2015-08-28 上午15:05:26
	 */
	public void updateSecondCarArrivalConfirm(OperationDTO operationDTO);
	
	
	 /**
	 * @function (二程接驳发车确认)根据id查询所有发车任务信息
	 * @return the string
	 * @author gongjp
	 * @date 2015-09-01 下午18:27:29
	 */
	 public List<ArrivalEntity> checkSecondCarDepartConfirm(OperationDTO dto);
	 
	 
	 /**
	 * @function (二程接驳发车确认)根据id查询所有发车任务信息
	 * @return the string
	 * @author gongjp
	 * @date 2015-09-01 下午18:33:27
	 */
	 public List<ArrivalEntity> checkSecondCarArrivalConfirm(OperationDTO dto);
	 
	 /**
	  * 查询到达id
	  * @param truckTaskDetailId
	  * @return
	  * @author wangruipeng
	  * @date 2016-09-10 下午19:57:27
	  */
	 public String queryTruckArriveId(String truckTaskDetailId);
	 
	 /**
	  * 根据到达id查询操作人等信息
	  * @param arrivedId
	  * @return
	  * @author wangruipeng
	  * @date 2016-09-10 下午20:09:27
	  */
	 public TruckArrivalEntity queryTruckArrivedInfo(String arrivedId);
	 
	 /**
	  * 跟queryLeasedTruck方法参数一样，返回配载单号
	  * @param queryEntity 配载单号
	  * @return
	  */
	String queryLeasedTruckAssmebleNo(QueryArrivalEntity queryEntity);
	 
}
