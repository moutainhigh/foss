/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/server/service/impl/PDAService.java
 *  
 *  FILE NAME          :PDAService.java
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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.handle.DepartureHandle;
import com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao;
import com.deppon.foss.module.transfer.departure.api.server.service.IUpdateTaskStatusService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.define.ErrorConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.QueryDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
import com.deppon.foss.module.transfer.departure.api.utils.Constants;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDADepartService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartResultDTO;

/**
 * PDA放行.
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2013-3-18 下午3:56:04
 */
public class PDAService implements IPDADepartService {
	/** ***日志*****. */
	private static final Logger LOGGER = LogManager.getLogger(PDAService.class);

	/**
	 * 记录PDA发车时间. 车辆出发的运作部门为本运作时，才会产生保安PDA车辆出发放行。
	 *  车辆出发信息，来源“查询车辆放行情况”的待放行列表中。
	 * 有车辆出发时间，则需要记录下次车辆返回本外场时间（车辆到达时间）。
	 *  交接单/配载单有多个，但产生放行需求的车牌号，只能对应一个。
	 * 对应一个已录入封签号的车辆系统自动给其分配一个任务车辆对应的任务唯一标识号 ，
	 * 只产生一个放行任务。
	 * 车辆出发：PDA发送请求，未获得FOSS响应时，不允许放行。
	 *  请接口规范： 车辆出发：发送请求失败时，间隔10秒，持续发送三次，
	 * 直至获取FOSS响应。
	 *  假如未响应，则不允许放行车辆。 可接受的系统延迟时间为30s，30s释放一次，再重新发送。
	 * 
	 * @param pdaDto
	 *            the pda dto
	 * @return the pDA depart result dto
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-31 下午6:27:07
	 */
	@Transactional
	@Override
	public PDADepartResultDTO writeDepartData(PDADepartDto pdaDto) {
		if (pdaDto == null) {
			LOGGER.error("PDA出发，传入参数不能为空");
			// PDA出发，传入参数不能为空
			throw new TfrBusinessException(
					ErrorConstant.PDA_DEPART_CONDITION_NOT_NULL, "");
		}
		if (pdaDto.getVehicleNo() == null || "".equals(pdaDto.getVehicleNo())) {
			LOGGER.error("PDA出发，车牌号不能为空");
			// PDA出发，车牌号不能为空
			throw new TfrBusinessException("车牌号不能为空", "");
		}
		try {
			// 转换车牌号编码为车牌号
			pdaDto.setVehicleNo(Constants.convertVehicleCode2Name(pdaDto
					.getVehicleNo()));
		} catch (BusinessException e) {
			LOGGER.error(e.getErrorCode());
			throw new TfrBusinessException(e.getErrorCode(), "");
		}
		// 找到该车牌号对应的车辆放行信息,先拼装预置的条件
		QueryDepartEntity queryDepartEntity = new QueryDepartEntity();
		queryDepartEntity.setVehicleNo(pdaDto.getVehicleNo());
		// 设置状态为待放行、或则失效
		List<String> statuslist = new ArrayList<String>();
		statuslist.add(DepartureConstant.DEPART_STATUS_WAIT);
		statuslist.add(DepartureConstant.DEPART_STATUS_Fail);
		queryDepartEntity.setStatuses(statuslist);
		// 查询所有待放行的信息
		List<TruckDepartEntity> truckList = departureDao
				.queryDepart(queryDepartEntity);
		TruckDepartEntity truckEntity = new TruckDepartEntity();
		if (null != truckList && truckList.size() > 0) {
			// 不做处理
		}
		else
		{
			queryDepartEntity.setStatuses(null);
			queryDepartEntity.setStatus(DepartureConstant.DEPART_STATUS_DEPARTURE);
			truckList = departureDao
					.queryDepart(queryDepartEntity);
			if (null == truckList || truckList.size() <= 0) {
				// 如果没有取得记录，直接返回不能放行
				LOGGER.error("未找到放行记录，不能放行");
				throw new TfrBusinessException(
						ErrorConstant.PDA_DEPART_RESULT_NOT_FIND, "");
			}
		}
		// 取得第一条，原则上最多只有一条
		truckEntity = truckList.get(0);
		// 更新PDA传过来的
		truckEntity.setStatus(DepartureConstant.DEPART_STATUS_DEPARTURE);
		// 更新放行部门
		truckEntity.setPdaDepartOrgCode(pdaDto.getOrgCode());
		// 更新P用户编码
		truckEntity.setPdaDepartUserCode(pdaDto.getOperator());
		// 更新放行时间
		truckEntity.setPdaDepartTime(pdaDto.getOperatingTime());
		// 更新PDA设备号
		truckEntity.setPdaTerminalNo(pdaDto.getPdaTerminalNo());
		departureDao.saveManual(truckEntity);
		PDADepartResultDTO dto = departureDao.getPDADepartResult(truckEntity
				.getId());
		// 查看是不是临时任务
		if (!(DepartureConstant.DEPART_ITEM_TYPE_LONG.equals(truckEntity
				.getDepartItems())
				|| DepartureConstant.DEPART_ITEM_TYPE_SHORT.equals(truckEntity
						.getDepartItems()) || DepartureConstant.DEPART_ITEM_TYPE_PKP
					.equals(truckEntity.getDepartItems()))) {
			// 不做任何处理
		} else {
			// 更改需要改变的业务信息
			// 状态设置为已放行
			truckEntity.setStatus(DepartureConstant.DEPART_STATUS_DEPARTURE);
			// 更新其他的信息
			truckEntity.setUpdateUserCode(pdaDto.getOperator());
			truckEntity.setUpdateOrgCode(pdaDto.getOrgCode());
			truckEntity.setUpdateTime(DepartureHandle.getCurrentDate());
			// 更新状态
			updateTaskStatusService.updateTaskStatus(truckEntity,
					DepartureConstant.ACTUAL_DEPART_TYPE_PDA);
			// 获取交接单
			dto.setHandoverbills(departureDao
					.getHandoverBillsByDepartId(truckEntity.getTruckTaskId()));
			dto.setSealNos(departureDao.getSealNosByDepartId(truckEntity
					.getTruckTaskId()));
		}
		if (dto.getDepartType() != null) {
			// 放行事项转成中文
			dto.setDepartType(DepartureConstant.departItemMap.get(dto
					.getDepartType()));
		}
		// 返回成功
		return dto;
	}

	/**
	 * ********************************* ****************.
	 */
	/********* 放行底层数据库操作 *********/
	private IDepartureDao departureDao;
//	private IDepartureService departureService;

	/**
	 * Sets the departure dao.
	 * 
	 * @param departureDao
	 *            the new departure dao
	 */
	public void setDepartureDao(IDepartureDao departureDao) {
		this.departureDao = departureDao;
	}

	/** The update task status service. */
	private IUpdateTaskStatusService updateTaskStatusService;

	/**
	 * Sets the update task status service.
	 * 
	 * @param updateTaskStatusService
	 *            the new update task status service
	 */
	public void setUpdateTaskStatusService(
			IUpdateTaskStatusService updateTaskStatusService) {
		this.updateTaskStatusService = updateTaskStatusService;
	}

	/*public void setDepartureService(IDepartureService departureService) {
		this.departureService = departureService;
	}*/
}