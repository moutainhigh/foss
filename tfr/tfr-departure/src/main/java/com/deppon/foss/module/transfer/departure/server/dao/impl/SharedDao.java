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
 *  PROJECT NAME  : tfr-departure
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/server/dao/impl/SharedDao.java
 *  
 *  FILE NAME          :SharedDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.server.dao.impl;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang.time.DateUtils;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.departure.api.server.dao.ISharedDao;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckActionDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.AutoTaskDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverRefershDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.WayBillRefershDTO;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckGPSTaskEntity;
import com.deppon.foss.util.UUIDUtils;
/**
 * 
 * JOB的底层实现
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-25 上午11:01:58
 */
public class SharedDao extends iBatis3DaoImpl implements ISharedDao {
	private static final String NAMESPACE = "tfr-task.";
	/**
	 * 
	 * 查询未完成任务车辆绑定信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:20:17
	 */
	@Override public List<WayBillRefershDTO> queryDataForWaybillRefresh(
			WayBillRefershDTO waybillDTO) {
		List<WayBillRefershDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "queryDataForWaybillRefresh",
				waybillDTO.getTruckTaskDetailId());
		return list;
	}
	/**
	 * 
	 * 查询未完成任务车辆绑定信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:20:17
	 */
	@Override public List<WayBillRefershDTO> queryUnloadInfoByUnloadId(
			String unloadId) {
		List<WayBillRefershDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "queryUnloadInfoByUnloadId", unloadId);
		return list;
	}
	/**
	 * 
	 * 查询未完成任务车辆绑定信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:20:17
	 */
	@Override public List<TruckActionDetailEntity> queryTruckActionDetail(
			TruckActionDetailEntity truckActionDetailEntity) {
		List<TruckActionDetailEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryTruckActionDetail", truckActionDetailEntity);
		return list;
	}
	/**
	 * 定时任务
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	@Override public void autoCancle(int rule) {
		AutoTaskDTO autoTaskDTO = new AutoTaskDTO();
		autoTaskDTO.setOldStatus(DepartureConstant.DEPART_STATUS_WAIT);
		autoTaskDTO.setNewStatus(DepartureConstant.DEPART_STATUS_Fail);
		autoTaskDTO.setCurrentTime(DateUtils.addMinutes(Calendar.getInstance()
				.getTime(), -rule));
		this.getSqlSession().update(NAMESPACE + "autoCancle", autoTaskDTO);
	}
	/**
	 * 
	 * 更新车辆任务绑定表的状态
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 下午3:13:00
	 */
	@Override public void updateActionDetail(
			TruckActionDetailEntity truckActionDetailEntity) {
		this.getSqlSession().update(NAMESPACE + "updateActionDetail",
				truckActionDetailEntity);
	}
	/**
	 * 
	 * 任务车辆明细获取交接单号
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 下午3:13:00
	 */
	@Override public List<HandoverRefershDTO> getHandoverByDetail(
			String truckTaskDetailId) {
		List<HandoverRefershDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "getHandoverByDetail", truckTaskDetailId);
		return list;
	}
	
	
	/**
	* @description 任务车辆明细获取快递交接单号(快递交接单)
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.departure.api.server.dao.ISharedDao#getWKHandoverByDetail(java.lang.String)
	* @author 283250-foss-liuyi
	* @update 2016年6月1日 上午11:18:01
	* @version V1.0
	*/
	@Override 
	public List<HandoverRefershDTO> getWKHandoverByDetail(
			String truckTaskDetailId) {
		List<HandoverRefershDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "getWKHandoverByDetail", truckTaskDetailId);
		return list;
	}
	/**
	 * 
	 * 任务车辆明细获取配载单号
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 下午3:13:00
	 */
	@Override public List<String> getVehicleAssembByDetail(
			String truckTaskDetailId) {
		List<String> list = this.getSqlSession().selectList(
				NAMESPACE + "getVehicleAssembByDetail", truckTaskDetailId);
		return list;
	}
	/**
	 * 
	 * 到达或者撤销到达时往PKP的job表插一条记录
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 下午3:13:00
	 */
	@Override public void insertTempForPKP(AutoTaskDTO dto) {
		this.getSqlSession().insert(NAMESPACE + "insertTempForPKP", dto);
	}
	/**
	 * 
	 * 通过交接单号更改发车计划的状态
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午11:06:24
	 */
	@Override public void updateTruckDepartPlanDetailStatus(
			HandoverRefershDTO dto) {
		this.getSqlSession().update(
				NAMESPACE + "updateTruckDepartPlanDetailStatus", dto);
	}
	/**
	 * 
	 * 通过交接单号更改发车计划的状态 (取消)
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午11:06:24
	 */
	@Override public void updateTruckDepartPlanNotDetailStatus(
			HandoverRefershDTO dto) {
		this.getSqlSession().update(
				NAMESPACE + "updateTruckDepartPlanNotDetailStatus", dto);
	}
	/**
	 * 
	 * 通过交接单号更改发车计划的状态
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午11:06:24
	 */
	@Override public List<String> getWaybillNoByDetail(String truckTaskDetailId) {
		List<String> list = this.getSqlSession().selectList(
				NAMESPACE + "getWaybillNoByDetail", truckTaskDetailId);
		return list;
	}
	/**
	 * 
	 * 通过交接单号更改发车计划的状态 (取消)
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午11:06:24
	 */
	@Override public List<String> queryWaybillByUnloadId(String unloadId) {
		List<String> list = this.getSqlSession().selectList(
				NAMESPACE + "queryWaybillByUnloadId", unloadId);
		return list;
	}
	/**
	 * 
	 * 单件出入库插入pkp临时表记录
	 * 
	 * @param dto
	 * @author ibm-wangfei
	 * @date Feb 25, 2013 1:42:22 PM
	 */
	@Override public void insertIOTempForPKP(AutoTaskDTO dto) {
		dto.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(NAMESPACE + "insertIOTempForPKP", dto);
	}
	/**
	 * 
	 * 更改到达部门插入临时表表记录
	 * 
	 * @param dto
	 * @author ibm-wangfei
	 * @date Mar 5, 2013 10:42:56 AM
	 */
	@Override public void insertONTempForPKP(AutoTaskDTO dto) {
		dto.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(NAMESPACE + "insertONTempForPKP", dto);
	}
	@Override
	public void updateGpsTask(TruckGPSTaskEntity truckGPSTaskEntity) {
		this.getSqlSession().update(
				NAMESPACE + "updateGpsTask", truckGPSTaskEntity);
		
	}
	@Override
	public int updateNotModify(String id) {
		
		return  this.getSqlSession().update(
				NAMESPACE + "updateNotModify", id);
		
	}
	@Override
	public void updateModify(String id) {
		this.getSqlSession().update(
				NAMESPACE + "updateModify", id);
		
		
	}
	
	/**
	 * 
	 * 任务车辆明细获取交接单号
	 * 
	 * @author foss-ruilibao
	 * @date 2016-08-15 下午06:24:00
	 */
	@Override public List<String> queryEcsLongBill(
			String truckTaskDetailId) {
		List<String> list = this.getSqlSession().selectList(
				NAMESPACE + "queryEcsLongBill", truckTaskDetailId);
		return list;
	}
	
	/**
	 * 查询整车入库信息
	 * 
	 * @author foss-wangruipeng
	 * @date 2016-09-08 下午02:49:07
	 */
	@Override
	public List<TruckActionDetailEntity> pushForWholeVehicle(
			TruckActionDetailEntity truckActionDetailEntity) {
		List<TruckActionDetailEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "pushForWholeVehicle", truckActionDetailEntity);
		return list;
	}
	
}