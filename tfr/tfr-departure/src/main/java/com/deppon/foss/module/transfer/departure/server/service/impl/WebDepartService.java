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
 *  沪A123457
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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/server/service/impl/WebDepartService.java
 *  
 *  FILE NAME          :WebDepartService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.server.service.impl;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.handle.DepartureHandle;
import com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao;
import com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService;
import com.deppon.foss.module.transfer.departure.api.server.service.IWebDepartureService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.define.ErrorConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.QueryDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.AutoDepartDTO;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * 提供放行外部接口的实现
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-6 上午11:31:13
 */
public class WebDepartService implements IWebDepartureService {
	/**
	 * 
	 * 自动放行----------封签、接送货、约车 1.长途、短途系统交接车辆： 
	 * 车辆“已录入封签号”的，系统自动生成一条车辆放行记录信息。
	 * （异常说明：无车牌，则不生成放行记录，待补充车牌后生成）。
	 *  1.1 车辆未离开出发外场时：
	 * 车牌号对应的封签号作废时，车辆放行状态由“待放行”变为“已取消”。
	 *  车牌号对应的封签号修改时，原封签号对应的车牌号的车辆放行状态不变
	 * ，系统更新封签号信息且系统保留封签号更换后的“待放行时间。 
	 * 1.2 车辆离场时 不允许修改封签号。 2.派送车辆：
	 * 系统生成带有派送任务编号的派送任务单时，系统自动生成一条放行记录。
	 *  （异常说明：无车牌，则不生成放行记录，待补充车牌后生成）。
	 * 2.1 业务说明 ：车辆放行状态记录为待放行，当车辆为派送时出发放行类型记录为 ：接送货。
	 * 2.2 车辆未离开出发运作时： 带派送任务编号的派送任务单作废时
	 * ，车辆放行状态由“待放行”变为“已取消”。 带派送任务编号的派送任务单修改车牌号时 ，原车牌号的车辆放行状态由“
	 * 待放行”变为“已取消”；更换后的车牌号，变为“待放行”。 
	 * 2.3 车辆离开出发运作时（车辆已出发时）：
	 * 带派送任务编号的派送任务单作废及车牌号修改，则无车辆放行状态变化。
	 * 2.4 接送货调度点击确认派送时，生成最终派送任务单时， 系统产生放行信息。
	 * 3.系统受理约车： 车队调度在受理约车时，勾选车辆出发放行时 ，系统自动生成一条放行记录信息。
	 * （异常说明：无车牌，则不生成放行记录，待补充车牌后生成）。 
	 * 3.1 业务说明：车辆放行状态记录为待放行，
	 * 当调度受理的用车部门为非本运作部门外场部门时 （一个本运作部门包含：上海外场配载组 、上海外场后勤组、上海车队修理组
	 * …….等部门），系统产生的出发放行类型为 ：临时放空出发(约车)。备注 ：本运作的外场约车，都有装车任务，走任务车辆通道
	 * ，无需约车受理时，系统放行。 
	 * 3.2 车辆未离开出发运作时 车队调度取消已受理的派车申请时 ，车辆放行状态由“待放行”变为已取消。 
	 * 3.3 车辆离开出发运作时 车队调度取消已受理的派车申请时，车辆出发状态则无变化。
	 *  4.人工发车确认： 4.1 “查询车辆到达情况”界面，发车确认按钮点击后可生成自动放行记录。 
	 *  4.2 发车确认点击后生成的申请放行，需要取消时，
	 * 在查询车辆放行情况界面，车队调度人工取消。
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	@Override
	@Transactional
	public String autoDepart(AutoDepartDTO autoDto) {
		// 必须要传入的参数：1、车牌号；2、司机编号；3、司机姓名；4、司机联系方式；5、申请人code；6、申请部门；7、申请人姓名
		// 自动放行类型：1---封签；2---接送货3---约车；
		// 放行事项:长途---11；短途---12；接送货---13
		// 车票号、司机姓名、申请人code、申请部门、自动放行类型
		// 自动放行类型为封签时必须输入任务车辆ID
		if (null == autoDto) {// 传入对象不能为空
			throw new TfrBusinessException(
					ErrorConstant.SEND_OBJECT_IS_NOT_NULL);
		}
		if (StringUtils.isBlank(autoDto.getVehicleNo())
				|| StringUtils.isBlank(autoDto.getApplyOrgCode())
				|| StringUtils.isBlank(autoDto.getAutoDepartType())) {
			// 请输入必填的条件
			throw new TfrBusinessException(
					ErrorConstant.INPUT_CENTAIN_CONDITION);
		}
		if (DepartureConstant.AUTO_DEPART_TYPE_SEAL.equals(autoDto
				.getAutoDepartType())
				&& StringUtils.isBlank(autoDto.getTruckTaskId())) {
			// 如果为封签，任务车辆ID必填
			throw new TfrBusinessException(
					ErrorConstant.INPUT_CENTAIN_CONDITION);
		}
		// 已有该车辆的放行申请，不能重复记录
		QueryDepartEntity queryEntity = new QueryDepartEntity();
		queryEntity.setVehicleNo(autoDto.getVehicleNo());
		queryEntity.setStatus(DepartureConstant.DEPART_STATUS_WAIT);
		List<TruckDepartEntity> truckList = departureDao
				.queryDepart(queryEntity);
		if (truckList != null && truckList.size() > 0) {
			throw new TfrBusinessException("此车牌号已经被（"
					+ truckList.get(0).getApplyOrgName()
					+ "）的（"
					+ truckList.get(0).getApplyUserName()
					+ "）在"
					+ DepartureHandle.getDateStr(truckList.get(0)
							.getApplyDepartTime()) + "号做放行申请，无法再次申请");
		}
		autoDto.setId(UUIDUtils.getUUID());
		// 放行状态置为已出发
		autoDto.setStatus(DepartureConstant.DEPART_STATUS_WAIT);
		// 自动类型为已录入封签号的，设置为任务车辆
		autoDto.setDepartType(DepartureConstant.DEPART_TYPE_TASK_VEHICLE);
		autoDto.setCreateTime(DepartureHandle.getCurrentDate());
		autoDto.setCreateUserCode(autoDto.getApplyUserCode());
		autoDto.setCreateOrgCode(autoDto.getApplyOrgCode());
		autoDto.setCreateUserName(autoDto.getApplyUserName());
		autoDto.setUpdateUserCode(autoDto.getApplyUserCode());
		autoDto.setUpdateOrgCode(autoDto.getApplyOrgCode());
		autoDto.setUpdateUserName(autoDto.getApplyUserName());
		autoDto.setUpdateTime(DepartureHandle.getCurrentDate());
		// 申请方式，设为自动
		autoDto.setApplyType(DepartureConstant.DEPART_APPLY_TYPE_AUTO);
		autoDto.setApplyDepartTime(DepartureHandle.getCurrentDate());
		// 申请部门必须为当前部门对应的外场
		autoDto.setApplyOrgCode(departureService.queryBigOrgCode(autoDto
				.getApplyOrgCode()));
		// 如果是封签，需要找到未出发的车辆任务明细
		if (DepartureConstant.AUTO_DEPART_TYPE_SEAL.equals(autoDto
				.getAutoDepartType())) {
			TruckTaskDetailEntity truckTaskDetailEntity = new TruckTaskDetailEntity();
			truckTaskDetailEntity
					.setStatus(DepartureConstant.ARRIVAL_VEHICLE_UNDEPART);
			truckTaskDetailEntity.setVehicleNo(autoDto.getVehicleNo());
			truckTaskDetailEntity.setTruckTaskId(autoDto.getTruckTaskId());
			List<TruckTaskDetailEntity> taskDetailList = departureDao
					.queryTruckTaskDetail(truckTaskDetailEntity);
			if (taskDetailList != null && taskDetailList.size() > 0) {
				autoDto.setApplyOrgCode(taskDetailList.get(0).getOrigOrgCode());
			}
		}
		TruckDepartEntity tr = DepartureHandle
				.convertDtoToEntityForDepart(autoDto);
		tr.setActiveTime(new Date());
		departureDao.addManual(tr);
		return autoDto.getId();
	}

	/****************************************************/
	private IDepartureDao departureDao;

	private IDepartureService departureService;

	public void setDepartureDao(IDepartureDao departureDao) {
		this.departureDao = departureDao;
	}

	public void setDepartureService(IDepartureService departureService) {
		this.departureService = departureService;
	}
}