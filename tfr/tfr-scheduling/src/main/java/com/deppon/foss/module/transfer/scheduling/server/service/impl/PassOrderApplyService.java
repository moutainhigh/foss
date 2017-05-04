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
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/PassOrderApplyService.java
 * 
 *  FILE NAME     :PassOrderApplyService.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService;
import com.deppon.foss.module.transfer.departure.api.server.service.IWebDepartureService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.dto.AutoDepartDTO;
import com.deppon.foss.module.transfer.scheduling.api.define.OrderVehicleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IPassOrderApplyDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IAuditOrderApplyService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IOrderVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPassBorrowApplyService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPassOrderApplyService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditOrderApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.OrderVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassOrderApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.AuditOrderApplyDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OrderVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PassOrderApplyDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.QueryDispatchOrderDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.VehicleDriverWithDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.OrderVehicleException;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.OrderVehicleStatusErrorException;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.ParameterException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 受理约车Service
 * 
 * @author 104306-foss-wangLong
 * @date 2012-11-21 下午5:55:59
 */
public class PassOrderApplyService implements IPassOrderApplyService {

	private static final Logger LOGGER = LogManager.getLogger(PassOrderApplyService.class);

	private IPassOrderApplyDao passOrderApplyDao;

	/** 约车审核logService */
	private IAuditOrderApplyService auditOrderApplyService;

	/** 约车申请Service */
	private IOrderVehicleService orderVehicleService;

	/** 车辆放行 申请Service */
	private IWebDepartureService webDepartureService;

	/** 车辆放行 取消Service */
	private IDepartureService departureService;

	/** 组织机构Service */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/** 车辆信息Service */
	private IVehicleService vehicleService;

	/** 借车审核Service */
	@SuppressWarnings("unused")
	private IPassBorrowApplyService passBorrowApplyService;

	/** 车辆信息Service */
	private IOwnVehicleService ownVehicleService;

	private IMotorcadeService motorcadeService;

	/**
	 * 新增
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-21 下午6:07:20
	 * @param passOrderApplyEntity
	 * @return 受影响的行数
	 */
	@Transactional
	public int addPassOrderApply(PassOrderApplyEntity passOrderApplyEntity) {
		return passOrderApplyDao.addPassOrderApply(passOrderApplyEntity);
	}

	/**
	 * 修改
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-21 下午6:07:20
	 * @param passOrderApplyEntity
	 * @return 受影响的行数
	 */
	@Transactional
	public int updatePassOrderApply(PassOrderApplyEntity passOrderApplyEntity) {
		return passOrderApplyDao.updatePassOrderApply(passOrderApplyEntity);
	}

	/**
	 * 查询集合
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-21 下午6:07:20
	 * @param passOrderApplyEntity
	 * @return java.util.List
	 */
	@Transactional(readOnly = true)
	public List<PassOrderApplyEntity> queryPassOrderApplyList(PassOrderApplyEntity passOrderApplyEntity) {
		return passOrderApplyDao.queryPassOrderApplyList(passOrderApplyEntity);
	}

	/**
	 * 根据约车单号查询
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-27 下午3:49:41
	 * @param orderNo
	 *            约车编号
	 * @return {@link PassOrderApplyEntity}
	 */
	public PassOrderApplyEntity queryPassOrderApplyByOrderNo(String orderNo) {
		PassOrderApplyEntity passOrderApplyEntity = new PassOrderApplyEntity();
		passOrderApplyEntity.setOrderNo(orderNo);
		List<PassOrderApplyEntity> list = queryPassOrderApplyList(passOrderApplyEntity);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 查询集合 分页
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-21 下午6:07:20
	 * @param passOrderApplyEntity
	 * @param start
	 * @param pageSize
	 * @return java.util.List
	 */
	@Transactional(readOnly = true)
	public List<PassOrderApplyEntity> queryPassOrderApplyForPage(PassOrderApplyEntity passOrderApplyEntity, int start, int pageSize) {
		return passOrderApplyDao.queryPassOrderApplyForPage(passOrderApplyEntity, start, pageSize);
	}

	/**
	 * 统计记录数
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-21 下午6:10:11
	 * @param passOrderApplyEntity
	 * @return
	 */
	@Transactional(readOnly = true)
	public Long queryCount(PassOrderApplyEntity passOrderApplyEntity) {
		return passOrderApplyDao.queryCount(passOrderApplyEntity);
	}

	/**
	 * 根据约车单号查询约车审核信息&& 放行任务
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-27 下午4:11:38
	 * @param orderNo
	 *            约车单号
	 * @return PassOrderApplyDto
	 */
	private PassOrderApplyDto queryPassOrderApplyListAndDepartTask(String orderNo) {
		return passOrderApplyDao.queryPassOrderApplyListAndDepartTask(orderNo);
	}

	/**
	 * 根据约车单号查询 约车审核最终信息， 和约车log信息
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-29 下午12:30:47
	 * @param orderNo
	 *            约车单号
	 * @return PassOrderApplyEntity
	 */
	public PassOrderApplyDto queryPassOrderApplyAndAuditOrderApplyLog(String orderNo) {
		PassOrderApplyDto passOrderApplyDto = new PassOrderApplyDto();
		if (StringUtil.isBlank(orderNo)) {
			LOGGER.info("约车为空, 单号:" + orderNo);
			return passOrderApplyDto;
		}
		OrderVehicleEntity orderVehicleEntity = orderVehicleService.queryOrderVehicleByOrderNo(orderNo);
		if (orderVehicleEntity == null) {
			LOGGER.info("约车申请信息不存在, 约车单号:" + orderNo);
			return passOrderApplyDto;
		}
		// 集中约车信息
		if (StringUtil.equals(orderVehicleEntity.getIsGroupZone(), FossConstants.YES)) {
			passOrderApplyDto = queryPassOrderApplyByDispatchOrder(orderNo);
			
			if (StringUtils.equals(
					OrderVehicleConstants.ORDERVEHICLE_STATUS_DISMISS,
					orderVehicleEntity.getStatus())) {

				List<AuditOrderApplyEntity> list = auditOrderApplyService
						.queryAuditOrderApplyListByOrderNo(orderNo);
				if (CollectionUtils.isEmpty(list)) {
					return passOrderApplyDto;
				}
				// 审核记录log
				List<AuditOrderApplyDto> auditOrderApplyDtoList = new ArrayList<AuditOrderApplyDto>();
				for (AuditOrderApplyEntity auditOrderApplyEntity : list) {
					AuditOrderApplyDto auditOrderApplyDto = new AuditOrderApplyDto();
					BeanUtils.copyProperties(auditOrderApplyEntity,
							auditOrderApplyDto);
					auditOrderApplyDtoList.add(auditOrderApplyDto);
				}
				passOrderApplyDto
						.setAuditOrderApplyDtoList(auditOrderApplyDtoList);
			}
			
			return passOrderApplyDto;
		}
		List<AuditOrderApplyEntity> list = auditOrderApplyService.queryAuditOrderApplyListByOrderNo(orderNo);
		if (CollectionUtils.isEmpty(list)) {
			return passOrderApplyDto;
		}
		// 审核记录log
		List<AuditOrderApplyDto> auditOrderApplyDtoList = new ArrayList<AuditOrderApplyDto>();
		for (AuditOrderApplyEntity auditOrderApplyEntity : list) {
			AuditOrderApplyDto auditOrderApplyDto = new AuditOrderApplyDto();
			BeanUtils.copyProperties(auditOrderApplyEntity, auditOrderApplyDto);
			auditOrderApplyDtoList.add(auditOrderApplyDto);
		}
		passOrderApplyDto.setAuditOrderApplyDtoList(auditOrderApplyDtoList);
		// 没有审核通过的记录
		PassOrderApplyEntity passOrderApplyEntity = queryPassOrderApplyByOrderNo(orderNo);
		if (passOrderApplyEntity == null) {
			return passOrderApplyDto;
		}
		int lastItemIndex = list.size() - 1;
		AuditOrderApplyDto auditOrderApplyDto = auditOrderApplyDtoList.get(lastItemIndex);
		if (StringUtil.equals(auditOrderApplyDto.getStatus(), OrderVehicleConstants.ORDERVEHICLE_STATUS_ACCEPTED)) {
			auditOrderApplyDto.setIfNeedReleaseBill(passOrderApplyEntity.getIfNeedReleaseBill());
			auditOrderApplyDto.setPerdictArriveTime(passOrderApplyEntity.getPerdictArriveTime());
			auditOrderApplyDtoList.set(lastItemIndex, auditOrderApplyDto);
		}
		// 车型
		VehicleAssociationDto vehicleAssociationDto = vehicleService.queryVehicleAssociationDtoByVehicleNo(passOrderApplyEntity.getVehicleNo());
		if (vehicleAssociationDto != null) {
			String orderVehicleModel = vehicleAssociationDto.getVehicleLengthName();
			//
			if(StringUtils.isNotBlank(orderVehicleModel)){
				// 车型
				passOrderApplyDto.setOrderVehicleModel(orderVehicleModel);
			}else{
				Map<String, String> map = new HashMap<String, String>();
				map.put("vehicletype_tractors", "拖头");
				map.put("vehicletype_trailer", "挂车");
				map.put("vehicletype_van", "厢式车");
				// 车型
				passOrderApplyDto.setOrderVehicleModel(map.get(vehicleAssociationDto.getVehicleType()));
			}
			// 所属小组
			passOrderApplyDto.setDriverGroup(vehicleAssociationDto.getVehicleOrganizationName());
			
			
		}
		// 审核结果
		passOrderApplyDto.setPassOrderApplyEntity(passOrderApplyEntity);
		return passOrderApplyDto;
	}

	/**
	 * 约车审核通过
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-24 上午2:09:58
	 * @param passOrderApplyDto
	 * @param orderId
	 * @throws ParameterException
	 */
	@Transactional
	public void doAcceptedOrderVehicleApply(PassOrderApplyDto passOrderApplyDto, String orderId) {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		// 尝试找登陆部门所属的派车车队，如果能找到
		MotorcadeEntity org = motorcadeService.queryMotorcadeByCodeClean(currentDeptCode);
		if (null == org || (!StringUtils.equals(FossConstants.YES, org.getIsTopFleet()) && !StringUtils.equals(FossConstants.YES, org.getDispatchTeam()))) {
			throw new ParameterException("您没有顶级车队或者车队调度组权限，无法审核受理！");
		}

		PassOrderApplyEntity passOrderApplyEntity = passOrderApplyDto.getPassOrderApplyEntity();
		if (passOrderApplyEntity == null) {
			// 异常 参数错误.
			throw new ParameterException(OrderVehicleConstants.ORDERVEHICLE_PARAMETERERROR_MESSAGE);
		}
		OrderVehicleEntity orderVehicleEntity = orderVehicleService.queryOrderVehicle(orderId);
		// 判断是否未审核 只有未审核的才能审核受理
		if (orderVehicleEntity == null || !StringUtils.equals(orderVehicleEntity.getStatus(), OrderVehicleConstants.ORDERVEHICLE_STATUS_UNAPPROVED)) {
			// 状态错误 .
			throw new OrderVehicleStatusErrorException("只有未审核的约车申请才能进行受理!");
		}
		if (orderVehicleEntity == null || StringUtil.equals(OrderVehicleConstants.ORDERVEHICLE_STATUS_CONFIRMTO, orderVehicleEntity.getStatus())) {
			// 状态错误 .
			throw new OrderVehicleStatusErrorException(OrderVehicleConstants.ORDERVEHICLE_STATUSERROR_MESSAGE);
		}
		// 约车单号
		String orderNo = passOrderApplyEntity.getOrderNo();
		// 根据约车单号查询 约车审核信息和放行任务信息
		PassOrderApplyDto oldPassOrderApply = queryPassOrderApplyListAndDepartTask(orderNo);
		if (oldPassOrderApply != null) {
			// 修改约车审核
			doUpdateAcceptedOrderVehicleApply(passOrderApplyDto, oldPassOrderApply);
		}
		// 1.修改约车申请状态
		orderVehicleService.updateOrderVehicleApplyForAcceptedStatus(orderId);
		// 2.保存约车log信息
		AuditOrderApplyEntity auditOrderApplyEntity = new AuditOrderApplyEntity();
		auditOrderApplyEntity.setOrderNo(passOrderApplyEntity.getOrderNo());
		auditOrderApplyEntity.setNotes(passOrderApplyDto.getNotes());
		auditOrderApplyService.doAcceptedOrderVehicleApply(auditOrderApplyEntity);
		// 3.生成放行任务
		String departId = createDepart(passOrderApplyDto);
		// 4.保存约车审核信息
		save(passOrderApplyEntity, departId);
	}

	/**
	 * 查询公司车 & 借来的车辆
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-12-18 下午8:33:12
	 * @param vehicleDriverWithDto
	 * @return {@link java.util.List}
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<VehicleDriverWithDto> queryCompanyVehicleAndBorrowVehicle(VehicleDriverWithDto vehicleDriverWithDto) {
		if (vehicleDriverWithDto == null) {
			vehicleDriverWithDto = new VehicleDriverWithDto();
		}

		List<VehicleDriverWithDto> borrowVehicleList = new ArrayList<VehicleDriverWithDto>();
		/*
		 * List<String> statusList = new ArrayList<String>();
		 * statusList.add(BorrowVehicleConstants.BORROWVEHICLE_STATUS_ACCEPTED);
		 * statusList
		 * .add(BorrowVehicleConstants.BORROWVEHICLE_STATUS_VERIFY_ARRIVE);
		 * 
		 * vehicleDriverWithDto.setBorrowVehicleApplyStatus(statusList);
		 * vehicleDriverWithDto
		 * .setBorrowVehicleOrgCode(FossUserContext.getCurrentDeptCode()); //
		 * 借来的车辆 Map<String, VehicleDriverWithDto> borrowVehicleMap =
		 * passBorrowApplyService.queryBorrowVehicle(vehicleDriverWithDto); if
		 * (borrowVehicleMap == null) { borrowVehicleMap = new HashMap<String,
		 * VehicleDriverWithDto>(); } List<VehicleDriverWithDto>
		 * borrowVehicleList = new
		 * ArrayList<VehicleDriverWithDto>(borrowVehicleMap.values()); // 追加司机信息
		 * setDriverInfo(borrowVehicleList); // 如果输入了司机姓名 直接返回 自有车和司机没有绑定关系 if
		 * (StringUtil.isNotBlank(vehicleDriverWithDto.getDriverName())) {
		 * return doFilterDriverName(borrowVehicleList,
		 * vehicleDriverWithDto.getDriverName()); }
		 */
		// 自有车
		String orgCode = vehicleDriverWithDto.getVehcleGroupCode();
		String vehicleNo = vehicleDriverWithDto.getVehicleNo();
		String vehicleTypeCode = vehicleDriverWithDto.getVehcleLengthCode();
		// 车牌号
		List<String> vehicleNos = new ArrayList<String>();
		if (!StringUtil.isEmpty(vehicleNo)) {
			vehicleNos.add(vehicleNo);
		}
		// 查询自有车
		PaginationDto paginationDto = ownVehicleService.queryVehicleAssociationDtoListPaginationByCondition(orgCode, vehicleNos, vehicleTypeCode, null, 0, Integer.MAX_VALUE, null);
		if (paginationDto != null && !CollectionUtils.isEmpty(paginationDto.getPaginationDtos())) {
			List<VehicleAssociationDto> ownVehicleList = paginationDto.getPaginationDtos();
			for (VehicleAssociationDto ownVehicleRegionDto : ownVehicleList) {
				VehicleDriverWithDto vehicle = new VehicleDriverWithDto();
				vehicle.setVehicleNo(ownVehicleRegionDto.getVehicleNo());
				vehicle.setVehcleLengthCode(ownVehicleRegionDto.getVehicleLengthCode());
				vehicle.setVehcleLengthName(ownVehicleRegionDto.getVehicleLengthName());
				vehicle.setVehcleGroupCode(ownVehicleRegionDto.getVehicleMotorcadeCode());
				vehicle.setVehcleGroupName(ownVehicleRegionDto.getVehicleMotorcadeName());
				borrowVehicleList.add(vehicle);
			}
		} else {
			// 尝试找登陆部门所属的顶级车队，如果能找到
			OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(orgCode);
			if (null == topFleet) {
				throw new ParameterException("查找自有车辆时,未找到登陆人所在部门的顶级车队!");
			} else {
				orgCode = topFleet.getCode();
			}
			// 查询自有车
			paginationDto = ownVehicleService.queryVehicleAssociationDtoListPaginationByCondition(orgCode, vehicleNos, vehicleTypeCode, null, 0, Integer.MAX_VALUE, null);
			if (paginationDto != null && !CollectionUtils.isEmpty(paginationDto.getPaginationDtos())) {
				List<VehicleAssociationDto> ownVehicleList = paginationDto.getPaginationDtos();
				for (VehicleAssociationDto ownVehicleRegionDto : ownVehicleList) {
					VehicleDriverWithDto vehicle = new VehicleDriverWithDto();
					vehicle.setVehicleNo(ownVehicleRegionDto.getVehicleNo());
					vehicle.setVehcleLengthCode(ownVehicleRegionDto.getVehicleLengthCode());
					vehicle.setVehcleLengthName(ownVehicleRegionDto.getVehicleLengthName());
					vehicle.setVehcleGroupCode(ownVehicleRegionDto.getVehicleMotorcadeCode());
					vehicle.setVehcleGroupName(ownVehicleRegionDto.getVehicleMotorcadeName());
					borrowVehicleList.add(vehicle);
				}
			}
		}
		// setDriverInfo(borrowVehicleList);

		return borrowVehicleList;
	}

	/**
	 * 查询公司车 & 借来的车辆 分页
	 * 
	 * @author huyue
	 * @date 20123-4-16 上午8:33:12
	 * @param vehicleDriverWithDto
	 * @return {@link java.util.List}
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public OrderVehicleDto queryCompanyVehicleAndBorrowVehicleByPage(VehicleDriverWithDto vehicleDriverWithDto, int start, int limit) {
		if (vehicleDriverWithDto == null) {
			vehicleDriverWithDto = new VehicleDriverWithDto();
		}

		List<VehicleDriverWithDto> borrowVehicleList = new ArrayList<VehicleDriverWithDto>();
		/*
		 * List<String> statusList = new ArrayList<String>();
		 * statusList.add(BorrowVehicleConstants.BORROWVEHICLE_STATUS_ACCEPTED);
		 * statusList
		 * .add(BorrowVehicleConstants.BORROWVEHICLE_STATUS_VERIFY_ARRIVE);
		 * 
		 * vehicleDriverWithDto.setBorrowVehicleApplyStatus(statusList);
		 * vehicleDriverWithDto
		 * .setBorrowVehicleOrgCode(FossUserContext.getCurrentDeptCode()); //
		 * 借来的车辆 Map<String, VehicleDriverWithDto> borrowVehicleMap =
		 * passBorrowApplyService.queryBorrowVehicle(vehicleDriverWithDto); if
		 * (borrowVehicleMap == null) { borrowVehicleMap = new HashMap<String,
		 * VehicleDriverWithDto>(); } List<VehicleDriverWithDto>
		 * borrowVehicleList = new
		 * ArrayList<VehicleDriverWithDto>(borrowVehicleMap.values()); // 追加司机信息
		 * setDriverInfo(borrowVehicleList); // 如果输入了司机姓名 直接返回 自有车和司机没有绑定关系 if
		 * (StringUtil.isNotBlank(vehicleDriverWithDto.getDriverName())) {
		 * return doFilterDriverName(borrowVehicleList,
		 * vehicleDriverWithDto.getDriverName()); }
		 */
		// 自有车
		String orgCode = vehicleDriverWithDto.getVehcleGroupCode();
		String vehicleNo = vehicleDriverWithDto.getVehicleNo();
		String vehicleTypeCode = vehicleDriverWithDto.getVehcleLengthCode();
		// 车牌号
		List<String> vehicleNos = new ArrayList<String>();
		if (!StringUtil.isEmpty(vehicleNo)) {
			vehicleNos.add(vehicleNo);
		}
		// 查询自有车
		PaginationDto paginationDto = ownVehicleService.queryVehicleAssociationDtoListPaginationByCondition(orgCode, vehicleNos, vehicleTypeCode, null, start, limit, null);
		if (paginationDto != null && !CollectionUtils.isEmpty(paginationDto.getPaginationDtos())) {
			List<VehicleAssociationDto> ownVehicleList = paginationDto.getPaginationDtos();
			for (VehicleAssociationDto ownVehicleRegionDto : ownVehicleList) {
				VehicleDriverWithDto vehicle = new VehicleDriverWithDto();
				vehicle.setVehicleNo(ownVehicleRegionDto.getVehicleNo());
				vehicle.setVehcleLengthCode(ownVehicleRegionDto.getVehicleLengthCode());
				vehicle.setVehcleLengthName(ownVehicleRegionDto.getVehicleLengthName());
				vehicle.setVehcleGroupCode(ownVehicleRegionDto.getVehicleMotorcadeCode());
				vehicle.setVehcleGroupName(ownVehicleRegionDto.getVehicleMotorcadeName());
				borrowVehicleList.add(vehicle);
			}
		} else {
			if (StringUtils.isNotEmpty(orgCode)) {
				// 尝试找登陆部门所属的顶级车队，如果能找到
				OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(orgCode);
				if (null == topFleet) {
					throw new ParameterException("查找自有车辆时,未找到登陆人所在部门的顶级车队!");
				} else {
					orgCode = topFleet.getCode();
				}
				// 查询自有车
				paginationDto = ownVehicleService.queryVehicleAssociationDtoListPaginationByCondition(orgCode, vehicleNos, vehicleTypeCode, null, 0, Integer.MAX_VALUE, null);
				if (paginationDto != null && !CollectionUtils.isEmpty(paginationDto.getPaginationDtos())) {
					List<VehicleAssociationDto> ownVehicleList = paginationDto.getPaginationDtos();
					for (VehicleAssociationDto ownVehicleRegionDto : ownVehicleList) {
						VehicleDriverWithDto vehicle = new VehicleDriverWithDto();
						vehicle.setVehicleNo(ownVehicleRegionDto.getVehicleNo());
						vehicle.setVehcleLengthCode(ownVehicleRegionDto.getVehicleLengthCode());
						vehicle.setVehcleLengthName(ownVehicleRegionDto.getVehicleLengthName());
						vehicle.setVehcleGroupCode(ownVehicleRegionDto.getVehicleMotorcadeCode());
						vehicle.setVehcleGroupName(ownVehicleRegionDto.getVehicleMotorcadeName());
						borrowVehicleList.add(vehicle);
					}
				}
			}
		}
		// setDriverInfo(borrowVehicleList);
		OrderVehicleDto orderVehicleDto = new OrderVehicleDto();
		orderVehicleDto.setVehicleDriverWithList(borrowVehicleList);
		orderVehicleDto.setTotalCount(paginationDto == null ? 0 : paginationDto.getTotalCount());
		return orderVehicleDto;
	}

	/**
	 * 按司机姓名筛选
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-12-17 下午5:49:27
	 * @param borrowVehicleList
	 *            要筛选的集合
	 * @param driverName
	 *            司机姓名
	 */
	@SuppressWarnings("unused")
	private List<VehicleDriverWithDto> doFilterDriverName(List<VehicleDriverWithDto> borrowVehicleList, String driverName) {
		if (CollectionUtils.isEmpty(borrowVehicleList) || StringUtil.isBlank(driverName)) {
			return borrowVehicleList;
		}
		List<VehicleDriverWithDto> resultList = new ArrayList<VehicleDriverWithDto>();
		for (VehicleDriverWithDto vehicleDriverWithDto : borrowVehicleList) {
			if (StringUtil.isBlank(vehicleDriverWithDto.getDriverName())) {
				continue;
			}
			if (vehicleDriverWithDto.getDriverName().startsWith(driverName) || vehicleDriverWithDto.getDriverName().endsWith(driverName)) {
				resultList.add(vehicleDriverWithDto);
			}
		}
		return resultList;
	}

	/**
	 * 追加司机信息
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 下午5:23:54
	 */
	@SuppressWarnings("unused")
	private void setDriverInfo(List<VehicleDriverWithDto> borrowVehicleList) {
		if (CollectionUtils.isEmpty(borrowVehicleList)) {
			return;
		}
		List<String> vehicleNoList = getVehicleNoList(borrowVehicleList);
		if (CollectionUtils.isEmpty(vehicleNoList)) {
			return;
		}
		List<String> orderVehicleStatusList = new ArrayList<String>();
		orderVehicleStatusList.add(OrderVehicleConstants.ORDERVEHICLE_STATUS_ACCEPTED);
		orderVehicleStatusList.add(OrderVehicleConstants.ORDERVEHICLE_STATUS_CONFIRMTO);
		String useVehicleOrgCode = FossUserContext.getCurrentDeptCode();
		Map<String, VehicleDriverWithDto> driverMap = passOrderApplyDao.queryDriverInfoByVehicleNos(vehicleNoList, orderVehicleStatusList, useVehicleOrgCode);
		if (CollectionUtils.isEmpty(driverMap)) {
			return;
		}
		for (VehicleDriverWithDto vehicleDriverWithDto : borrowVehicleList) {
			String vehicleNo = vehicleDriverWithDto.getVehicleNo();
			VehicleDriverWithDto driver = driverMap.get(vehicleNo);
			if (driver != null) {
				vehicleDriverWithDto.setDriverName(driver.getDriverName());
				vehicleDriverWithDto.setDriverCode(driver.getDriverCode());
				vehicleDriverWithDto.setDriverPhone(driver.getDriverPhone());
			}
		}
	}

	/**
	 * 根据约车单号查询 集中转货处理结果
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-12-1 下午4:39:16
	 * @param orderNo
	 *            约车单号
	 */
	private PassOrderApplyDto queryPassOrderApplyByDispatchOrder(String orderNo) {
		PassOrderApplyDto passOrderApplyDto = new PassOrderApplyDto();
		List<QueryDispatchOrderDto> queryDispatchOrderDtoList = passOrderApplyDao.queryPassOrderApplyByDispatchOrder(orderNo);
		if (CollectionUtils.isEmpty(queryDispatchOrderDtoList)) {
			return passOrderApplyDto;
		}
		QueryDispatchOrderDto queryDispatchOrderDto = queryDispatchOrderDtoList.get(0);
		PassOrderApplyEntity passOrderApplyEntity = new PassOrderApplyEntity();
		// 车辆
		passOrderApplyEntity.setVehicleNo(queryDispatchOrderDto.getVehicleNo());
		// 司机姓名
		passOrderApplyEntity.setDriverName(queryDispatchOrderDto.getDriverName());
		
		// 司机联系方式
		passOrderApplyEntity.setDriverPhone(queryDispatchOrderDto.getDriverPhone());
		
		// 车型
		VehicleAssociationDto vehicleAssociationDto = null;
		if (StringUtil.isNotBlank(passOrderApplyEntity.getVehicleNo())) {
			vehicleAssociationDto = vehicleService.queryVehicleAssociationDtoByVehicleNo(passOrderApplyEntity.getVehicleNo());
		}
		if (vehicleAssociationDto != null) {
			String orderVehicleModel = vehicleAssociationDto.getVehicleLengthName();
			// 车型
			passOrderApplyDto.setOrderVehicleModel(orderVehicleModel);
			// 所属小组
			passOrderApplyDto.setDriverGroup(vehicleAssociationDto.getVehicleOrganizationName());
		}
		passOrderApplyDto.setPassOrderApplyEntity(passOrderApplyEntity);
		return passOrderApplyDto;
	}

	/**
	 * 保存 根据id区分是新增操作 还是修改操作
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-29 上午11:52:14
	 * @param passOrderApplyEntity
	 *            约车审核对象
	 * @param departId
	 *            放行任务id
	 */
	private void save(PassOrderApplyEntity passOrderApplyEntity, String departId) {
		UserEntity user = FossUserContext.getCurrentUser();
		// 工号
		String userCode = user.getEmployee().getEmpCode();
		// 名称
		String userName = user.getEmployee().getEmpName();
		// 部门名称
		String orgName = FossUserContext.getCurrentDept().getName();
		// 部门编码
		String orgCode = FossUserContext.getCurrentDeptCode();
		// 放行任务id
		passOrderApplyEntity.setTruckDepartId(departId);
		// 网点编码
		passOrderApplyEntity.setAcceptOrgName(orgName);
		// 网点名称
		passOrderApplyEntity.setAcceptOrgCode(orgCode);
		// 人员编码
		passOrderApplyEntity.setAcceptEmpCode(userCode);
		// 人员名称
		passOrderApplyEntity.setAcceptEmpName(userName);
		// 通过时间
		passOrderApplyEntity.setPassTime(new Date());
		passOrderApplyEntity.setPassStatus(OrderVehicleConstants.ORDERVEHICLE_STATUS_ACCEPTED);
		// 如果没有主键id 认为是新增操作
		if (StringUtil.isBlank(passOrderApplyEntity.getId())) {
			addPassOrderApply(passOrderApplyEntity);
		} else {
			updatePassOrderApply(passOrderApplyEntity);
		}
	}

	/**
	 * 修改约车审核
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-27 下午4:18:34
	 * @param passOrderApplyDto
	 * @param oldPassOrderApply
	 */
	private void doUpdateAcceptedOrderVehicleApply(PassOrderApplyDto passOrderApplyDto, PassOrderApplyDto oldPassOrderApply) {
		PassOrderApplyEntity passOrderApplyEntity = passOrderApplyDto.getPassOrderApplyEntity();
		// 任务
		String truckDepartId = oldPassOrderApply.getTruckDepartId();
		// 放行状态
		String truckDepartStatus = oldPassOrderApply.getTruckDepartStatus();
		// 非等于已出发状态 作废放行任务
		if (StringUtil.isNotBlank(truckDepartId) && StringUtil.equals(DepartureConstant.DEPART_STATUS_WAIT, truckDepartStatus)) {
			// 取消放行任务.
			List<String> list = new ArrayList<String>();
			list.add(truckDepartId);
			departureService.cancleDepart(list);
		} else if (StringUtil.isNotBlank(truckDepartId) && StringUtil.equals(DepartureConstant.DEPART_STATUS_DEPARTURE, truckDepartStatus)) {
			// 如果原车已经出场(放行需求已经使用),则不需要再推送信息.
			passOrderApplyEntity.setIfNeedReleaseBill(FossConstants.NO);
		}
		// 标示为修改操作
		passOrderApplyEntity.setId(oldPassOrderApply.getPassOrderApplyId());
	}

	/**
	 * 获得上级外场
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-27 下午2:42:40
	 */
	private OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByCode(String code) {
		// 外场类型
		List<String> bizTypes = new ArrayList<String>();
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		return orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(code, bizTypes);
	}

	/**
	 * 生成放行任务
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-26 下午4:42:50
	 * @param passOrderApplyEntity
	 * @return 返回放行任务主键id
	 */
	private String createDepart(PassOrderApplyDto passOrderApplyDto) {
		PassOrderApplyEntity passOrderApplyEntity = passOrderApplyDto.getPassOrderApplyEntity();
		// 不需要生成放行任务
		if (!StringUtil.equals(passOrderApplyEntity.getIfNeedReleaseBill(), FossConstants.YES)) {
			return null;
		}
		UserEntity user = FossUserContext.getCurrentUser();
		// 工号
		String userCode = user.getEmployee().getEmpCode();
		// 名称
		String userName = user.getEmployee().getEmpName();
		// 部门编码
		String orgCode = FossUserContext.getCurrentDeptCode();

		// 车辆部门及申请使用部门进行匹配,如果是同一外场,则不需要生成放行需求
		String orderNo = passOrderApplyEntity.getOrderNo();
		OrderVehicleEntity orderVehicleEntity = orderVehicleService.queryOrderVehicleByOrderNo(orderNo);
		if (orderVehicleEntity == null) {
			// 异常 约车申请单号不存在
			throw new OrderVehicleException("约车申请不存在, 约车单号:" + orderNo);
		}
		// 审核人对应的上级外场
		OrgAdministrativeInfoEntity dispatchTransDept = queryOrgAdministrativeInfoByCode(orgCode);
		// 用车部门对应的上级外场
		OrgAdministrativeInfoEntity useVehicleNoDept = queryOrgAdministrativeInfoByCode(orderVehicleEntity.getApplyOrgCode());
		
		if (useVehicleNoDept != null && dispatchTransDept != null && dispatchTransDept.equals(useVehicleNoDept)) {
			// 如果是同一外场 不需要生成放行单
			return null;
		}
		// 车牌号
		String vehicleNo = passOrderApplyEntity.getVehicleNo();
		// 放行任务对象
		AutoDepartDTO autoDepartDTO = new AutoDepartDTO();
		// 车牌号
		autoDepartDTO.setVehicleNo(vehicleNo);
		// 司机编号
		autoDepartDTO.setDriverCode(passOrderApplyEntity.getDriverCode());
		// 司机姓名
		autoDepartDTO.setDriverName(passOrderApplyEntity.getDriverName());
		// 司机联系方式
		autoDepartDTO.setDriverPhone(passOrderApplyEntity.getDriverPhone());
		// 申请人code
		autoDepartDTO.setApplyUserCode(userCode);
		// 申请人部门编码
		autoDepartDTO.setApplyOrgCode(orgCode);
		// 申请人name
		autoDepartDTO.setApplyUserName(userName);
		// 放行类型 任务放行 | 非任务放行
		autoDepartDTO.setDepartType(DepartureConstant.DEPART_TYPE_TASK_VEHICLE);
		// 自动放行类型
		autoDepartDTO.setAutoDepartType(DepartureConstant.AUTO_DEPART_TYPE_ABOUT_CARS);
		// 放行事项
		autoDepartDTO.setDepartItems(DepartureConstant.DEPART_ITEM_TYPE_APPOINT);
		return webDepartureService.autoDepart(autoDepartDTO);
	}

	/**
	 * 获取车牌号
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午5:03:04
	 * @param borrowVehicleList
	 */
	private List<String> getVehicleNoList(List<VehicleDriverWithDto> borrowVehicleList) {
		List<String> vehicleNoList = new CopyOnWriteArrayList<String>();
		for (VehicleDriverWithDto driverWithDto : borrowVehicleList) {
			if (driverWithDto == null || StringUtil.isBlank(driverWithDto.getVehicleNo())) {
				continue;
			}
			vehicleNoList.add(driverWithDto.getVehicleNo());
		}
		return vehicleNoList;
	}

	/**
	 * 设置passOrderApplyDao
	 * 
	 * @param passOrderApplyDao
	 *            the passOrderApplyDao to set
	 */
	public void setPassOrderApplyDao(IPassOrderApplyDao passOrderApplyDao) {
		this.passOrderApplyDao = passOrderApplyDao;
	}

	/**
	 * 设置orderVehicleService
	 * 
	 * @param orderVehicleService
	 *            the orderVehicleService to set
	 */
	public void setOrderVehicleService(IOrderVehicleService orderVehicleService) {
		this.orderVehicleService = orderVehicleService;
	}

	/**
	 * 设置auditOrderApplyService
	 * 
	 * @param auditOrderApplyService
	 *            the auditOrderApplyService to set
	 */
	public void setAuditOrderApplyService(IAuditOrderApplyService auditOrderApplyService) {
		this.auditOrderApplyService = auditOrderApplyService;
	}

	/**
	 * 设置webDepartureService
	 * 
	 * @param webDepartureService
	 *            the webDepartureService to set
	 */
	public void setWebDepartureService(IWebDepartureService webDepartureService) {
		this.webDepartureService = webDepartureService;
	}

	/**
	 * 设置orgAdministrativeInfoComplexService
	 * 
	 * @param orgAdministrativeInfoComplexService
	 *            the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 设置departureService
	 * 
	 * @param departureService
	 *            the departureService to set
	 */
	public void setDepartureService(IDepartureService departureService) {
		this.departureService = departureService;
	}

	/**
	 * 设置vehicleService
	 * 
	 * @param vehicleService
	 *            the vehicleService to set
	 */
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	/**
	 * 设置passBorrowApplyService
	 * 
	 * @param passBorrowApplyService
	 *            the passBorrowApplyService to set
	 */
	public void setPassBorrowApplyService(IPassBorrowApplyService passBorrowApplyService) {
		this.passBorrowApplyService = passBorrowApplyService;
	}

	/**
	 * 设置ownVehicleService
	 * 
	 * @param ownVehicleService
	 *            the ownVehicleService to set
	 */
	public void setOwnVehicleService(IOwnVehicleService ownVehicleService) {
		this.ownVehicleService = ownVehicleService;
	}

	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}
}