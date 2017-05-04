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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/OrderVehicleService.java
 * 
 *  FILE NAME     :OrderVehicleService.java
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/*import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.esb.pojo.domain.foss2oms.SyncTransitGoodsRequest;*/
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskHandleService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.dto.TransferOrderDto;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.scheduling.api.define.OrderVehicleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IAuditOrderApplyDao;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IOrderVehicleDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IOrderVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditOrderApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.OrderVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OrderVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.OrderVehicleStatusErrorException;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.ParameterException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.TimeUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 约车申请Service
 * 
 * @author 104306-foss-wangLong
 * @date 2012-10-15 下午12:51:34
 */
public class OrderVehicleService implements IOrderVehicleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderVehicleService.class);

	/** 可以做确认车辆到达操作的状态 */
	private static Map<String, String> canDoConfirmToStatusMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 8851853615081722747L;
		{
			put(OrderVehicleConstants.ORDERVEHICLE_STATUS_ACCEPTED, OrderVehicleConstants.ORDERVEHICLE_STATUS_ACCEPTED);
		}
	};

	/** 可以做撤销操作的状态 */
	private static Map<String, String> canDoUndoOrderVehicleApplyStatusMap = new HashMap<String, String>() {
		private static final long serialVersionUID = -6039673472830361328L;
		{
			put(OrderVehicleConstants.ORDERVEHICLE_STATUS_UNAPPROVED, OrderVehicleConstants.ORDERVEHICLE_STATUS_UNAPPROVED);
			put(OrderVehicleConstants.ORDERVEHICLE_STATUS_STAGING, OrderVehicleConstants.ORDERVEHICLE_STATUS_STAGING);
		}
	};

	/** 可以做修改动作的状态 */
	private static Map<String, String> canUpdateOrderVehicleApplyStatusMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 1547363877711280437L;
		{
			put(OrderVehicleConstants.ORDERVEHICLE_STATUS_STAGING, OrderVehicleConstants.ORDERVEHICLE_STATUS_STAGING);
			put(OrderVehicleConstants.ORDERVEHICLE_STATUS_UNAPPROVED, OrderVehicleConstants.ORDERVEHICLE_STATUS_UNAPPROVED);
			put(OrderVehicleConstants.ORDERVEHICLE_STATUS_RETURN, OrderVehicleConstants.ORDERVEHICLE_STATUS_RETURN);
			put(OrderVehicleConstants.ORDERVEHICLE_STATUS_UNDO, OrderVehicleConstants.ORDERVEHICLE_STATUS_UNDO);
		}
	};

	/** 可以做受理的状态 */
	private static Map<String, String> canAcceptedOrderVehicleApplyStatusMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 1547363877711280437L;
		{
			put(OrderVehicleConstants.ORDERVEHICLE_STATUS_UNAPPROVED, OrderVehicleConstants.ORDERVEHICLE_STATUS_UNAPPROVED);
			put(OrderVehicleConstants.ORDERVEHICLE_STATUS_ACCEPTED, OrderVehicleConstants.ORDERVEHICLE_STATUS_ACCEPTED);
		}
	};

	private IOrderVehicleDao orderVehicleDao;

	/** 订单处理Service */
	private IOrderTaskHandleService orderTaskHandleService;

	/** 组织机构Service */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	private ITfrCommonService tfrCommonService;

	/** 营业部 Service接口 */
	private ISaleDepartmentService saleDepartmentService;

	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	private IMotorcadeService motorcadeService;

	private ITruckSchedulingTaskService truckSchedulingTaskService;

	private IOutfieldService outfieldService;

	private IDepartureStandardService departureStandardService;
	
	private IAuditOrderApplyDao auditOrderApplyDao;

	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}

	public void setDepartureStandardService(IDepartureStandardService departureStandardService) {
		this.departureStandardService = departureStandardService;
	}

	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	/**
	 * 线路service
	 */
	private ILineService lineService;

	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @return the auditOrderApplyDao
	 */
	public IAuditOrderApplyDao getAuditOrderApplyDao() {
		return auditOrderApplyDao;
	}

	/**
	 * @param auditOrderApplyDao the auditOrderApplyDao to set
	 */
	public void setAuditOrderApplyDao(IAuditOrderApplyDao auditOrderApplyDao) {
		this.auditOrderApplyDao = auditOrderApplyDao;
	}

	/**
	 * 新增
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleEntity
	 * @return
	 */
	@Transactional
	public int addOrderVehicle(OrderVehicleEntity orderVehicleEntity) {
		settings(orderVehicleEntity);
		processHerdedCoahGoods(orderVehicleEntity);
		int result = orderVehicleDao.addOrderVehicle(orderVehicleEntity);
		//asynOrderVehicleToOms(orderVehicleEntity);
		addCoahGoodsOrderVehicle(orderVehicleEntity);
		return result;
	}

	/**
	 * 处理订单类型为转货， 并且为集中区域
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-29 上午11:03:12
	 * @param orderVehicleEntity
	 */
	private void processHerdedCoahGoods(OrderVehicleEntity orderVehicleEntity) {
		// 必须为转货
		String orderType = orderVehicleEntity.getOrderType();
		// 不是集中转货
		orderVehicleEntity.setIsGroupZone(FossConstants.NO);
		// 不是转货
		if (!StringUtil.equals(OrderVehicleConstants.ORDERVEHICLE_ORDERTYPE_TRANSIT_GOODS, orderType)) {
			return;
		}
		// 用车部门是否集中区域
		String useVehicleOrgCode = orderVehicleEntity.getUseVehicleOrgCode();
		SaleDepartmentEntity saleDepartmentEntity = null;
		try {
			saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(useVehicleOrgCode);
		} catch (BusinessException e) {
			throw new ParameterException(OrderVehicleConstants.ORDERVEHICLE_PARAMETERERROR_ARGMESSAGE, new Object[] { "调用综合接口出错.错误信息:" + e.getMessage() });
		}
		// 是否集中开单组 如果为空 认为非集中区域
		if (saleDepartmentEntity == null || StringUtil.isBlank(saleDepartmentEntity.getInCentralizedShuttle())) {
			return;
		}
		if (!StringUtil.equals(saleDepartmentEntity.getInCentralizedShuttle(), FossConstants.YES)) {
			return;
		}
		// 标示是集中区域转货申请
		orderVehicleEntity.setIsGroupZone(FossConstants.YES);
		// 如果是暂存，则不修改状态
		if (!StringUtils.equals(OrderVehicleConstants.ORDERVEHICLE_STATUS_STAGING, orderVehicleEntity.getStatus())) {
			// 已受理状态
			orderVehicleEntity.setStatus(OrderVehicleConstants.ORDERVEHICLE_STATUS_UNAPPROVED);
		}
	}
	/**
	 * 调用OMS接口, 保存转货订单申请
	 * 
	 * @author 332153-foss-zm
	 * @date 2016年11月22日11:17:19
	 * @param orderVehicleEntity
	 */
	/*private static String ESB_CODE="ESB_FOSS2ESB_TRANSFER_CAR";
	private void asynOrderVehicleToOms(OrderVehicleEntity orderVehicleEntity){
		LOGGER.info("调用OMS接口 start");
		LOGGER.info("传入参数 ："+orderVehicleEntity.toString());
		if (!StringUtil.equals(orderVehicleEntity.getIsGroupZone(), FossConstants.YES)) {
			return;
		}else if(StringUtils.equals(OrderVehicleConstants.ORDERVEHICLE_STATUS_STAGING, orderVehicleEntity.getStatus())){//如果是暂存则不需要保存到接送货的表
			return;
		}
		try {
			*//**头信息 start**//*
			final  AccessHeader header = new AccessHeader();
			header.setBusinessId(orderVehicleEntity.getOrderNo());
			header.setEsbServiceCode(ESB_CODE);
			header.setVersion("1.0");
			header.setBusinessDesc1("同步转货订单到OMS");
			*//**头信息 end**//*
			*//**实体信息 start**//*
			SyncTransitGoodsRequest request=new SyncTransitGoodsRequest();
			request.setOrderNo(orderVehicleEntity.getOrderNo());
			request.setApplyTime(orderVehicleEntity.getApplyTime());
			request.setOrderType(orderVehicleEntity.getOrderType());
			request.setOrderVehicleModel(orderVehicleEntity.getOrderVehicleModel());
			request.setDispatchTransDept(orderVehicleEntity.getDispatchTransDept());
			request.setUseAddress(orderVehicleEntity.getUseAddress());
			request.setGoodsType(orderVehicleEntity.getGoodsType());
			request.setIsTailboard(orderVehicleEntity.getIsTailboard());
			request.setPredictUseTime(orderVehicleEntity.getPredictUseTime());
			request.setGoodsName(orderVehicleEntity.getGoodsName());
			request.setWeight(orderVehicleEntity.getWeight());
			request.setVolume(orderVehicleEntity.getVolume());
			request.setGoodsQty(orderVehicleEntity.getGoodsQty());
			request.setNotes(orderVehicleEntity.getNotes());
			request.setStatus(orderVehicleEntity.getStatus());
			request.setApplyOrgName(orderVehicleEntity.getApplyOrgName());
			request.setApplyOrgCode(orderVehicleEntity.getApplyOrgCode());
			request.setApplyEmpCode(orderVehicleEntity.getApplyEmpCode());
			request.setApplyEmpName(orderVehicleEntity.getApplyEmpName());
			request.setTelephoneNo(orderVehicleEntity.getTelephoneNo());
			request.setMobilephoneNo(orderVehicleEntity.getMobilephoneNo());
			request.setWayBillNo(orderVehicleEntity.getWayBillNo());
			request.setIsGroupZone(orderVehicleEntity.getIsGroupZone());
			request.setUseVehicleOrgName(orderVehicleEntity.getUseVehicleOrgName());
			request.setUseVehicleOrgCode(orderVehicleEntity.getUseVehicleOrgCode());
			request.setTopFleetCode(orderVehicleEntity.getTopFleetCode());
			request.setApplyTime(orderVehicleEntity.getApplyTime());
			*//**实体信息 end**//*
			ESBJMSAccessor.asynReqeust(header, request);
			LOGGER.info("调用OMS接口 success");
		} catch (ESBException e) {
			LOGGER.error("调用OMS接口 error："+e.getMessage());
			throw new ParameterException(OrderVehicleConstants.ORDERVEHICLE_PARAMETERERROR_ARGMESSAGE, new Object[] { "同步转货订单到OMS, 保存转货约车申请出错." });
		}
		LOGGER.info("调用OMS接口 end");
	}*/
	/**
	 * 调用接送货接口, 保存集中转货申请
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-12-1 下午1:15:21
	 * @param orderVehicleEntity
	 */
	private void addCoahGoodsOrderVehicle(OrderVehicleEntity orderVehicleEntity) {
		if (!StringUtil.equals(orderVehicleEntity.getIsGroupZone(), FossConstants.YES) || StringUtils.equals(OrderVehicleConstants.ORDERVEHICLE_STATUS_STAGING, orderVehicleEntity.getStatus())) {
			return;
		}
		//352203-sonar
		/*else if(StringUtils.equals(OrderVehicleConstants.ORDERVEHICLE_STATUS_STAGING, orderVehicleEntity.getStatus())){//如果是占村则不需要保存到接送货的表
			return;
		}*/
		TransferOrderDto transferOrderDto = new TransferOrderDto();
		BeanUtils.copyProperties(orderVehicleEntity, transferOrderDto);
		// 车型
		transferOrderDto.setVehicleType(orderVehicleEntity.getOrderVehicleModel());
		// 接货地址
		transferOrderDto.setPickupElseAddress(orderVehicleEntity.getUseAddress());
		// 用车部门名称
		transferOrderDto.setCustomerName(orderVehicleEntity.getApplyOrgName());
		// 电话
		transferOrderDto.setTel(orderVehicleEntity.getTelephoneNo());
		// 手机
		transferOrderDto.setMobile(orderVehicleEntity.getMobilephoneNo());
		// 备注
		transferOrderDto.setOrderNotes(orderVehicleEntity.getNotes());
		// 用车部门名称
		transferOrderDto.setSalesDepartmentName(orderVehicleEntity.getUseVehicleOrgName());
		// 用车部门编码
		transferOrderDto.setSalesDepartmentCode(orderVehicleEntity.getUseVehicleOrgCode());
		// 预计用车时间
		transferOrderDto.setLatestPickupTime(orderVehicleEntity.getPredictUseTime());
		// 申请部门编码
		transferOrderDto.setOrderVehicleOrgCode(orderVehicleEntity.getApplyOrgCode());
		// 申请部门名称
		transferOrderDto.setOrderVehicleOrgName(orderVehicleEntity.getApplyOrgName());
		// 申请时间
		transferOrderDto.setOrderVehicleTime(new Date());
		// 申请人
		transferOrderDto.setCreateUserName(orderVehicleEntity.getApplyEmpName());
		// 申请人编码
		transferOrderDto.setCreateUserCode(orderVehicleEntity.getApplyEmpCode());
		// 派车车队编码
		transferOrderDto.setFleetCode(orderVehicleEntity.getDispatchTransDept());
		try {
			orderTaskHandleService.addTransferOrder(transferOrderDto);
		} catch (BusinessException e) {
			throw new ParameterException(OrderVehicleConstants.ORDERVEHICLE_PARAMETERERROR_ARGMESSAGE, new Object[] { "调用接送货订单处理, 保存转货约车申请出错." });
		}
	}

	/**
	 * 设置默认值
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-23 下午4:23:29
	 * @param orderVehicleEntity
	 */
	private void settings(OrderVehicleEntity orderVehicleEntity) {
		// 约车单号
		String orderNo = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.YC);
		orderVehicleEntity.setOrderNo(orderNo);
		UserEntity user = FossUserContext.getCurrentUser();
		// 工号
		String userCode = user.getEmployee().getEmpCode();
		// 名称
		String userName = user.getEmployee().getEmpName();
		// 网点编码
		String orgCode = FossUserContext.getCurrentDeptCode();
		// 网点名称
		String orgName = FossUserContext.getCurrentDept().getName();
		// 申请网点名称 取当前登录用户部门
		if (StringUtil.isBlank(orderVehicleEntity.getApplyOrgCode())) {
			// 申请网点编码
			orderVehicleEntity.setApplyOrgCode(orgCode);
			orderVehicleEntity.setApplyOrgName(orgName);
		}
		// 申请人员编码 名称
		if (StringUtil.isBlank(orderVehicleEntity.getApplyEmpCode())) {
			orderVehicleEntity.setApplyEmpCode(userCode);
			orderVehicleEntity.setApplyEmpName(userName);
		}
		// 用车部门编码 如果用车部门为空 取当前登陆用户所在部门
		if (StringUtil.isBlank(orderVehicleEntity.getUseVehicleOrgCode())) {
			orderVehicleEntity.setUseVehicleOrgCode(orgCode);
			orderVehicleEntity.setUseVehicleOrgName(orgName);
		}
		checkeOrgAdministrative(orderVehicleEntity);
	}

	/**
	 * 修改单个对象
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleEntity
	 * @return 受影响的行数
	 */
	@Transactional
	public int updateOrderVehicle(OrderVehicleEntity orderVehicleEntity) {
		checkeOrgAdministrative(orderVehicleEntity);
		processHerdedCoahGoods(orderVehicleEntity);
		int result = orderVehicleDao.updateOrderVehicle(orderVehicleEntity);
		//asynOrderVehicleToOms(orderVehicleEntity);
		addCoahGoodsOrderVehicle(orderVehicleEntity);
		return result;
	}

	/**
	 * 查询单个对象（按主键查询） <br>
	 * ------------------------------<br>
	 * 存在 返回该对象 不存在返回null
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleEntity
	 * @return com.deppon.foss.module.transfer.scheduling.api.shared.domain.
	 *         OrderVehicleEntity
	 */
	@Transactional(readOnly = true)
	public OrderVehicleEntity queryOrderVehicle(String id) {
		OrderVehicleEntity orderVehicleEntity = orderVehicleDao.queryOrderVehicle(id);
		enhanceDispatchTransDeptName(orderVehicleEntity);
		return orderVehicleEntity;
	}

	/**
	 * 查询集合
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleDto
	 * @return {@link java.util.List<OrderVehicleEntity>}
	 */
	@Transactional(readOnly = true)
	public List<OrderVehicleEntity> queryOrderVehicleList(OrderVehicleDto orderVehicleDto) {
		return orderVehicleDao.queryOrderVehicleList(orderVehicleDto);
	}

	/**
	 * 根据约车编号查询
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderNo
	 *            约车编号
	 * @return
	 */
	public OrderVehicleEntity queryOrderVehicleByOrderNo(String orderNo) {
		OrderVehicleDto orderVehicleDto = new OrderVehicleDto();
		OrderVehicleEntity orderVehicleEntity = new OrderVehicleEntity();
		orderVehicleEntity.setOrderNo(orderNo);
		orderVehicleDto.setOrderVehicleEntity(orderVehicleEntity);
		List<OrderVehicleEntity> list = orderVehicleDao.queryAuditOrderVehicleList(orderVehicleDto);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 查询未审核状态的约车申请
	 * <p>
	 * 1.从约车申请到约车审核 {@link orderIdList} 不为null 按orderIdList查询</br> 2.左边菜单直接打开
	 * 查询当前登录部门对应的所有约车申请 {@link orderIdList} 为null
	 * </p>
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderIdList
	 *            约车单申请主键id
	 * @param isLoadAll
	 *            是否加载全部数据
	 * @return
	 */
	public List<OrderVehicleEntity> queryOrderVehicleListByCanAcceptedStatus(List<String> orderIdList, boolean isLoadAll) {
		if (isLoadAll && CollectionUtils.isEmpty(orderIdList)) {
			return new ArrayList<OrderVehicleEntity>();
		}
		OrderVehicleDto orderVehicleDto = new OrderVehicleDto();
		List<String> statusList = new ArrayList<String>();
		OrderVehicleEntity orderVehicleEntity = new OrderVehicleEntity();

		// 未审核状态
		statusList.add(OrderVehicleConstants.ORDERVEHICLE_STATUS_UNAPPROVED);
		orderVehicleDto.setStatusList(statusList);

		// 网点编码
		String orgCode = FossUserContext.getCurrentDeptCode();

		orderVehicleEntity.setTopFleetCode(orgCode);

		MotorcadeEntity org = motorcadeService.queryMotorcadeByCodeClean(orgCode);
		if (null == org || (!StringUtils.equals(FossConstants.YES, org.getIsTopFleet()) && !StringUtils.equals(FossConstants.YES, org.getDispatchTeam()))) {
			throw new ParameterException("您没有顶级车队或者车队调度组权限，无法审核受理！");
		} else {
			// 尝试找登陆部门所属的顶级车队，如果能找到
			OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(orgCode);
			if (null == topFleet) {
				throw new ParameterException("此登陆部门未找到对应的顶级车队，无法审核受理！");
			} else {
				orderVehicleEntity.setTopFleetCode(topFleet.getCode());
			}
		}

		orderVehicleDto.setOrderVehicleEntity(orderVehicleEntity);
		// 集中转货申请不查询
		orderVehicleEntity.setIsGroupZone(FossConstants.NO);
		// 按约车申请主键id查询
		if (!CollectionUtils.isEmpty(orderIdList)) {
			String[] idArray = orderIdList.get(0).split(",");
			orderIdList = Arrays.asList(idArray);
			orderVehicleDto.setOrderIdList(orderIdList);
			// 状态检查
			checkVehicleApplyParameterAndStatus(orderIdList, canAcceptedOrderVehicleApplyStatusMap);
		}
		List<OrderVehicleEntity> list = orderVehicleDao.queryAuditOrderVehicleList(orderVehicleDto);
		enhanceDispatchTransDeptName(list);
		return list;
	}

	/**
	 * 查询集合 分页
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleDto
	 * @param start
	 * @param pageSize
	 * @return {@link java.util.List<OrderVehicleEntity>}
	 */
	@Transactional(readOnly = true)
	public List<OrderVehicleEntity> queryOrderVehicleForPage(OrderVehicleDto orderVehicleDto, int start, int pageSize) {
		// 将登陆人作为申请人
		OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
		if (currentDept == null) {
			currentDept = new OrgAdministrativeInfoEntity();
		}

		orderVehicleDto.getOrderVehicleEntity().setCurrentDeptCode(currentDept.getCode());
		// 尝试找登陆部门所属的派车车队的顶级车队，如果能找到
		OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(currentDept.getCode());
		if (null == topFleet) {
			orderVehicleDto.getOrderVehicleEntity().setTopFleetCode("");
		} else {
			orderVehicleDto.getOrderVehicleEntity().setTopFleetCode(topFleet.getCode());
		}
		
		// 网点编码
		String orgCode = FossUserContext.getCurrentDeptCode();
		// 获取当前车队的属性
		MotorcadeEntity org = motorcadeService.queryMotorcadeByCodeClean(orgCode);
		//判断是不是受理部门,如果是,则不能查询集中接送货的信息
		if (null != org &&  ((!StringUtils.isEmpty(org.getIsTopFleet()) && StringUtils.equals(FossConstants.YES, org.getIsTopFleet())) || (!StringUtils.isEmpty(org.getDispatchTeam()) && StringUtils.equals(FossConstants.YES, org.getDispatchTeam())))) {
			orderVehicleDto.getOrderVehicleEntity().setIsGroupZone(FossConstants.NO);
		}
		
		return orderVehicleDao.queryOrderVehicleForPage(orderVehicleDto, start, pageSize);
	}

	/**
	 * 统计记录数
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleDto
	 * @return
	 */
	@Transactional(readOnly = true)
	public Long queryCount(OrderVehicleDto orderVehicleDto) {
		// 将登陆人作为申请人
		OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
		if (currentDept == null) {
			currentDept = new OrgAdministrativeInfoEntity();
		}

		orderVehicleDto.getOrderVehicleEntity().setCurrentDeptCode(currentDept.getCode());
		// 尝试找登陆部门所属的派车车队的顶级车队，如果能找到
		OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(currentDept.getCode());
		if (null == topFleet) {
			orderVehicleDto.getOrderVehicleEntity().setTopFleetCode("");
		} else {
			orderVehicleDto.getOrderVehicleEntity().setTopFleetCode(topFleet.getCode());
		}
		
		// 网点编码
		String orgCode = FossUserContext.getCurrentDeptCode();
		// 获取当前车队的属性
		MotorcadeEntity org = motorcadeService.queryMotorcadeByCodeClean(orgCode);
		//判断是不是受理部门,如果是,则不能查询集中接送货的信息
		if (null != org &&  ((!StringUtils.isEmpty(org.getIsTopFleet()) && StringUtils.equals(FossConstants.YES, org.getIsTopFleet())) || (!StringUtils.isEmpty(org.getDispatchTeam()) && StringUtils.equals(FossConstants.YES, org.getDispatchTeam())))) {
			orderVehicleDto.getOrderVehicleEntity().setIsGroupZone(FossConstants.NO);
		}
		return orderVehicleDao.queryCount(orderVehicleDto);
	}

	/**
	 * 撤销约车申请
	 * <p>
	 * 参数,状态不对 ，会抛出异常,
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午6:48:34
	 * @param orderVehicleApplyIdList
	 * @throws OrderVehicleStatusErrorException
	 * @throws ParameterException
	 */
	@Transactional
	public void doUndoOrderVehicleApply(List<String> orderVehicleApplyIdList) {
		// 参数 状态检查
		List<OrderVehicleEntity> list = checkVehicleApplyParameterAndStatus(
				orderVehicleApplyIdList, canDoUndoOrderVehicleApplyStatusMap);

		// 查询此约车是否集中接货.如果是则调用接送货的接口撤销转货订单
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(list)) {
			TransferOrderDto dto = null;
			for (OrderVehicleEntity orderVehicleEntity : list) {
				if (StringUtil.equals(orderVehicleEntity.getIsGroupZone(),
						FossConstants.YES)
						&& !StringUtils
								.equals(OrderVehicleConstants.ORDERVEHICLE_STATUS_STAGING,
										orderVehicleEntity.getStatus())) {
					dto = new TransferOrderDto();
					dto.setOrderNo(orderVehicleEntity.getOrderNo());
					orderTaskHandleService.revokeTransferOrder(dto);
				}
			}
			//
		}

		// 批量更新
		updateOrderVehicleApplyStatus(orderVehicleApplyIdList,
				OrderVehicleConstants.ORDERVEHICLE_STATUS_UNDO);
	}

	/**
	 * 确认车辆到达，
	 * <p>
	 * 参数错误,状态不对 ，会抛出异常,
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午6:48:34
	 * @param orderVehicleApplyIdList
	 * @throws OrderVehicleStatusErrorException
	 * @throws ParameterException
	 */
	@Transactional
	public void doConfirmTo(List<String> orderVehicleApplyIdList) {
		// 参数 状态检查
		// 只有已受理状态的 才能做确认车辆到达操作
		checkVehicleApplyParameterAndStatus(orderVehicleApplyIdList, canDoConfirmToStatusMap);
		updateOrderVehicleApplyStatus(orderVehicleApplyIdList, OrderVehicleConstants.ORDERVEHICLE_STATUS_CONFIRMTO);
	}

	/**
	 * 保存操作
	 * <p>
	 * 根据主键id,来区分是新增操作 还是修改操作
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-22 下午5:16:15
	 * @param orderVehicleEntity
	 * @return
	 * @throws ParameterException
	 */
	@Transactional
	public OrderVehicleEntity saveOrderVehicle(final OrderVehicleEntity orderVehicleEntity) {
		// 参数验证
		if (orderVehicleEntity == null) {
			throw new ParameterException(OrderVehicleConstants.ORDERVEHICLE_PARAMETERERROR_MESSAGE);
		}

		// 尝试找所选择的派车车队的顶级车队，如果能找到
		OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(orderVehicleEntity.getDispatchTransDept());
		if (null == topFleet) {
			throw new ParameterException("您选择的派车车队未配置其对应的顶级车队，请配置后再尝试！");
		} else {
			orderVehicleEntity.setTopFleetCode(topFleet.getCode());
		}

		// 时间判断 如果找的到部门对应 ISSUE-2678
		Date date = orderVehicleEntity.getApplyTime();
		long dateLong = date.getTime();
		long lineTime = 0;
		// 根据顶级车对查询出所属外场
		OutfieldEntity entity = new OutfieldEntity();
		entity.setMotorcadeCode(topFleet.getCode());
		List<OutfieldEntity> outfieldList = outfieldService.queryOutfieldExactByEntity(entity, 0, ConstantsNumberSonar.SONAR_NUMBER_10);
		if (!CollectionUtils.isEmpty(outfieldList) && null != outfieldList.get(0) && null != outfieldList.get(0).getCode()) {
			// 根据当前部门和派车部门查询线路时效
			LineEntity lineEntity = new LineEntity();
			lineEntity.setOrginalOrganizationCode(outfieldList.get(0).getCode());
			lineEntity.setDestinationOrganizationCode(orderVehicleEntity.getUseVehicleOrgCode());
			List<LineEntity> lineList = lineService.querySimpleLineListByCondition(lineEntity);
			if (!CollectionUtils.isEmpty(lineList)) {
				if (null != lineList.get(0).getCommonAging()) {
					lineTime = lineList.get(0).getCommonAging();
				} else {
					// 去取到达线路出发到达时间
					List<DepartureStandardEntity> departureStandardList = departureStandardService.queryDepartureStandardListByLineVirtualCode(lineList.get(0).getVirtualCode());
					if (!CollectionUtils.isEmpty(departureStandardList)) {
						Date newD = new Date();
						if (null != departureStandardList.get(0).getLeaveTime() && null != departureStandardList.get(0).getArriveTime() && null != departureStandardList.get(0).getArriveDay()) {
							Date leaveDate = TimeUtils.createStartTime(newD, departureStandardList.get(0).getLeaveTime());
							Date arriveDate = TimeUtils.convertStringToDate(newD, departureStandardList.get(0).getArriveTime(), departureStandardList.get(0).getArriveDay().intValue());
							if (null != leaveDate && null != arriveDate) {
								lineTime = arriveDate.getTime() - leaveDate.getTime();
							}
						}
					}
				}
			}
		}
		Long needLong = dateLong + ConstantsNumberSonar.SONAR_NUMBER_45 * ConstantsNumberSonar.SONAR_NUMBER_60000 + lineTime;
		Date needDate = new Date(needLong);
		// 判断时效
		if (!orderVehicleEntity.getPredictUseTime().after(needDate)) {
			throw new ParameterException("您输入的预计用车时间与申请时间差距过小！预计用车时间需大于申请时间+发车部门到用车部门线路时效+45分钟!");
		}

		// 如果id为空 新增操作
		if (StringUtil.isBlank(orderVehicleEntity.getId())) {
			addOrderVehicle(orderVehicleEntity);
		} else {
			// 状态检查
			List<String> orderVehicleApplyIdList = new ArrayList<String>();
			orderVehicleApplyIdList.add(orderVehicleEntity.getId());
			checkVehicleApplyParameterAndStatus(orderVehicleApplyIdList, canUpdateOrderVehicleApplyStatusMap);
			updateOrderVehicle(orderVehicleEntity);
		}

		return orderVehicleEntity;
	}

	/**
	 * 更新到退回状态
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-23 下午9:09:03
	 * @param orderId
	 *            约车申请主键id
	 */
	@Transactional
	public void updateOrderVehicleApplyForReturnStatus(String orderId) {
		List<String> orderVehicleApplyIdList = new ArrayList<String>();
		orderVehicleApplyIdList.add(orderId);
		updateOrderVehicleApplyStatus(orderVehicleApplyIdList, OrderVehicleConstants.ORDERVEHICLE_STATUS_RETURN);
	}

	/**
	 * 更新到已拒绝
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-24 上午1:34:35
	 * @param orderId
	 */
	@Transactional
	public void updateOrderVehicleApplyForDismissStatus(String orderId) {
		List<String> orderVehicleApplyIdList = new ArrayList<String>();
		orderVehicleApplyIdList.add(orderId);
		updateOrderVehicleApplyStatus(orderVehicleApplyIdList, OrderVehicleConstants.ORDERVEHICLE_STATUS_DISMISS);
	}

	/**
	 * 更新到已受理(通过)状态
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-23 下午9:09:03
	 * @param orderId
	 *            约车申请主键id
	 */
	public void updateOrderVehicleApplyForAcceptedStatus(String orderId) {
		List<String> orderVehicleApplyIdList = new ArrayList<String>();
		orderVehicleApplyIdList.add(orderId);
		updateOrderVehicleApplyStatus(orderVehicleApplyIdList, OrderVehicleConstants.ORDERVEHICLE_STATUS_ACCEPTED);
	}

	/**
	 * 更新约车申请状态 - 接送货
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-12-1 下午3:28:29
	 * @param orderNo
	 *            约车申请单号
	 * @param status
	 *            状态 接送那边的状态
	 * @param returnReason
	 *            退回原因
	 * @return 受影响的行数
	 */
	public int updateOrderVehicleApplyStatusByOrderNo(String orderNo, String status, String returnReason) {
		OrderVehicleEntity orderVehicleEntity = queryOrderVehicleByOrderNo(orderNo);
		if (orderVehicleEntity == null) {
			throw new ParameterException(OrderVehicleConstants.ORDERVEHICLE_PARAMETERERROR_MESSAGE);
		}
		Map<String, String> statusMap = new HashMap<String, String>();
		// 已派车 -> 已受理
		statusMap.put(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE, OrderVehicleConstants.ORDERVEHICLE_STATUS_ACCEPTED);
		// 接货中 -> 已受理
		statusMap.put(DispatchOrderStatusConstants.STATUS_PICKUPING, OrderVehicleConstants.ORDERVEHICLE_STATUS_ACCEPTED);
		// 待改接 -> 已受理
		statusMap.put(DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP, OrderVehicleConstants.ORDERVEHICLE_STATUS_ACCEPTED);
		// 退回 -> 已拒绝
		statusMap.put(DispatchOrderStatusConstants.STATUS_RETURN, OrderVehicleConstants.ORDERVEHICLE_STATUS_DISMISS);
		// 已开单 -> 确认车辆到达
		statusMap.put(DispatchOrderStatusConstants.STATUS_WAYBILL, OrderVehicleConstants.ORDERVEHICLE_STATUS_CONFIRMTO);
		if (!statusMap.containsKey(status)) {
			return 0;
		}
		String tfrOrderVehicleStatus = statusMap.get(status);
		List<String> orderVehicleApplyIdList = new ArrayList<String>();
		orderVehicleApplyIdList.add(orderVehicleEntity.getId());
		int i=updateOrderVehicleApplyStatus(orderVehicleApplyIdList, tfrOrderVehicleStatus);
		
		//判定是否是回退状态,如果是则记录一条LOG信息
		if(StringUtils.equals(status, DispatchOrderStatusConstants.STATUS_RETURN)){
			AuditOrderApplyEntity auditOrderApplyEntity=new AuditOrderApplyEntity();
			auditOrderApplyEntity.setOrderNo(orderNo);
			auditOrderApplyEntity.setNotes(returnReason);
			settingAndAddAuditOrderApply(auditOrderApplyEntity,tfrOrderVehicleStatus);
		}
		
		return i;
	}
	
	/**
	 * 新增约车审核log
	 * 
	 * @author 134019-foss-yuyongxiang
	 * @date 2013年6月14日 14:32:08
	 */
	private void settingAndAddAuditOrderApply(AuditOrderApplyEntity auditOrderApplyEntity, String status) {
		UserEntity user = FossUserContext.getCurrentUser();
		// 工号
		String userCode = user.getEmployee().getEmpCode();
		// 名称
		String userName = user.getEmployee().getEmpName();
		// 网点编码
		String orgCode = FossUserContext.getCurrentDeptCode();
		String orgName = FossUserContext.getCurrentDept().getName();
		// 受理时间
		auditOrderApplyEntity.setAuditTime(new Date());
		// 受理序号
		Integer auditNo = getNextAuditNo(auditOrderApplyEntity.getOrderNo());
		auditOrderApplyEntity.setAuditNo(auditNo);
		// 审核车队名称
		auditOrderApplyEntity.setAuditOrgName(orgName);
		// 审核车队编码
		auditOrderApplyEntity.setAuditOrgCode(orgCode);
		// 审核人员名称
		auditOrderApplyEntity.setAuditEmpName(userName);
		// 审核人员编码
		auditOrderApplyEntity.setAuditEmpCode(userCode);
		// 状态
		auditOrderApplyEntity.setStatus(status);
		// 保存
		addAuditOrderApply(auditOrderApplyEntity);
	}
	
	/**
	 * 获取下一个受理序号
	 * 
	 * @author 134019-foss-yuyongxiang
	 * @date 2013年6月14日 14:32:08
	 */
	private int getNextAuditNo(String orderNo) {
		int no = queryAuditNo(orderNo);
		AtomicInteger atomicNo = new AtomicInteger(no);
		return atomicNo.incrementAndGet();
	}

	/**
	 * 查询受理序号 每次+1
	 * 
	 * @author 134019-foss-yuyongxiang
	 * @date 2013年6月14日 14:32:08
	 */
	public Integer queryAuditNo(String orderNo) {
		return auditOrderApplyDao.queryAuditNo(orderNo);
	}
	
	/**
	 * 新增
	 * 
	 * @author 134019-foss-yuyongxiang
	 * @date 2013年6月14日 14:32:08
	 * @param auditOrderApplyEntity
	 * @return 受影响的行数
	 */
	@Transactional
	public int addAuditOrderApply(AuditOrderApplyEntity auditOrderApplyEntity) {
		return auditOrderApplyDao.addAuditOrderApply(auditOrderApplyEntity);
	}
	
	

	/**
	 * 检查约车申请单据状态
	 * <p>
	 * 如果map中存在状态 则认为正常 否则抛出异常
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-22 下午2:30:49
	 * @param orderVehicleApplyIdList
	 * @param statusMap
	 * @throws ParameterException
	 * @throws OrderVehicleStatusErrorException
	 */
	private List<OrderVehicleEntity> checkVehicleApplyParameterAndStatus(List<String> orderVehicleApplyIdList, Map<String, String> statusMap) {
		// 参数错误
		if (CollectionUtils.isEmpty(orderVehicleApplyIdList)) {
			throw new ParameterException(OrderVehicleConstants.ORDERVEHICLE_PARAMETERERROR_MESSAGE);
		}
		// 状态检查
		List<OrderVehicleEntity> queryList = queryOrderVehicleListByIds(orderVehicleApplyIdList);
		for (OrderVehicleEntity orderVehicleEntity : queryList) {
			String status = orderVehicleEntity.getStatus();
			if (StringUtil.isBlank(status) || !statusMap.containsKey(status)) {
				LOGGER.info("状态错误.约车申请单号:{}", orderVehicleEntity.getOrderNo());
				throw new OrderVehicleStatusErrorException(OrderVehicleConstants.ORDERVEHICLE_STATUSERROR_MESSAGE);
			}
		}
		
		return queryList;
	}

	/**
	 * 根据List集合查询对象
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-22 下午12:28:59
	 * @param orderVehicleApplyIdList
	 * @return
	 */
	@Transactional(readOnly = true)
	private List<OrderVehicleEntity> queryOrderVehicleListByIds(List<String> orderVehicleApplyIdList) {
		return orderVehicleDao.queryOrderVehicleListByIds(orderVehicleApplyIdList);
	}

	/**
	 * 更新状态, <br>
	 * ------------------------------<br>
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午6:48:34
	 * @param orderVehicleApplyIdList
	 *            id集合
	 * @param status
	 *            状态
	 * @return
	 */
	private int updateOrderVehicleApplyStatus(List<String> orderVehicleApplyIdList, String status) {
		return orderVehicleDao.updateOrderVehicleApplyStatus(orderVehicleApplyIdList, status);
	}

	/**
	 * 检查参数, 用车部门，派车车队
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午3:08:42
	 * @param orderVehicleEntity
	 * @throws ParameterException
	 */
	private void checkeOrgAdministrative(OrderVehicleEntity orderVehicleEntity) {
		OrgAdministrativeInfoEntity useVehicleOrg = null;
		// 用车部门
		try {
			useVehicleOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orderVehicleEntity.getUseVehicleOrgCode());
		} catch (BusinessException e) {
			throw new ParameterException(OrderVehicleConstants.ORDERVEHICLE_PARAMETERERROR_ARGMESSAGE, new Object[] { "调用综合接口出错.错误信息:" + e.getMessage() });
		}
		if (useVehicleOrg == null) {
			throw new ParameterException(OrderVehicleConstants.ORDERVEHICLE_PARAMETERERROR_ARGMESSAGE, new Object[] { "用车部门信息 不存在." });
		}
		if (StringUtil.isBlank(orderVehicleEntity.getUseVehicleOrgName())) {
			orderVehicleEntity.setUseVehicleOrgName(useVehicleOrg.getName());
		}
		OrgAdministrativeInfoEntity dispatchTransDept = null;
		// 派车车队
		try {
			dispatchTransDept = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orderVehicleEntity.getDispatchTransDept());
		} catch (BusinessException e) {
			throw new ParameterException(OrderVehicleConstants.ORDERVEHICLE_PARAMETERERROR_ARGMESSAGE, new Object[] { "调用综合接口出错.错误信息:" + e.getMessage() });
		}
		if (dispatchTransDept == null) {
			throw new ParameterException(OrderVehicleConstants.ORDERVEHICLE_PARAMETERERROR_ARGMESSAGE, new Object[] { "派车车队部门信息 不存在." });
		}
	}

	/**
	 * 派车车队信息
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午3:24:27
	 * @param entity
	 */
	private void enhanceDispatchTransDeptName(OrderVehicleEntity entity) {
		if (entity == null || StringUtil.isBlank(entity.getDispatchTransDept())) {
			return;
		}
		OrgAdministrativeInfoEntity dispatchTransDept = null;
		try {
			dispatchTransDept = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getDispatchTransDept());
		} catch (BusinessException e) {
			throw new ParameterException(OrderVehicleConstants.ORDERVEHICLE_PARAMETERERROR_ARGMESSAGE, new Object[] { "调用综合接口出错.错误信息:" + e.getMessage() });
		}
		if (dispatchTransDept != null) {
			entity.setDispatchTransDeptName(dispatchTransDept.getName());
		}
	}

	/**
	 * 派车车队信息
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午3:24:27
	 * @param list
	 */
	private void enhanceDispatchTransDeptName(List<OrderVehicleEntity> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		for (OrderVehicleEntity orderVehicleEntity : list) {
			enhanceDispatchTransDeptName(orderVehicleEntity);
		}
	}

	/**
	 * 设置orderVehicleDao
	 * 
	 * @param orderVehicleDao
	 *            the orderVehicleDao to set
	 */
	public void setOrderVehicleDao(IOrderVehicleDao orderVehicleDao) {
		this.orderVehicleDao = orderVehicleDao;
	}

	/**
	 * 设置orderTaskHandleService
	 * 
	 * @param orderTaskHandleService
	 *            the orderTaskHandleService to set
	 */
	public void setOrderTaskHandleService(IOrderTaskHandleService orderTaskHandleService) {
		this.orderTaskHandleService = orderTaskHandleService;
	}

	/**
	 * 设置orgAdministrativeInfoService
	 * 
	 * @param orgAdministrativeInfoService
	 *            the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 设置tfrCommonService
	 * 
	 * @param tfrCommonService
	 *            the tfrCommonService to set
	 */
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	/**
	 * 设置saleDepartmentService
	 * 
	 * @param saleDepartmentService
	 *            the saleDepartmentService to set
	 */
	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	@Override
	public void doOrderVehicleGiveBack(List<String> orderNoList) {
		updateOrderVehicleApplyStatus(orderNoList, OrderVehicleConstants.ORDERVEHICLE_STATUS_GIVE_BACK);
	}

	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}

	@Override
	public void checkTopFleet(String motorcadeCode) {
		// 网点编码
		String orgCode = FossUserContext.getCurrentDeptCode();

		// 获取当前车队的属性
		MotorcadeEntity org = motorcadeService.queryMotorcadeByCodeClean(orgCode);
		// 获取派车车队的顶级车队
		OrgAdministrativeInfoEntity dispatchDeptTopFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(motorcadeCode);

		if (null == dispatchDeptTopFleet) {
			OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(motorcadeCode);
			throw new ParameterException("此派车车队" + orgEntity.getName() + "没有配置顶级车队，无法审核受理！");
		} else if (null == org || (!StringUtils.equals(FossConstants.YES, org.getIsTopFleet()) && !StringUtils.equals(FossConstants.YES, org.getDispatchTeam()))) {
			throw new ParameterException("您没有顶级车队或者车队调度组权限，无法审核受理！");
		} else {
			// 尝试找登陆部门所属的顶级车队，如果能找到
			OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(orgCode);
			if (null == topFleet) {
				throw new ParameterException("您没有顶级车队或者车队调度组权限，无法审核受理！");
			} else if (!StringUtils.equals(dispatchDeptTopFleet.getCode(), topFleet.getCode())) {

				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(motorcadeCode);
				throw new ParameterException("此约车记录的派车车队对应的顶级车队为：" + orgEntity.getName() + ", 您所对应的的顶级派车车队: " + topFleet.getName() + " 与之不符，无权审核受理");
			}
		}
	}

	@Override
	public SimpleTruckScheduleDto queryDriverInfoInShortPlan(String vehicleNo) {
		SimpleTruckScheduleDto dto = new SimpleTruckScheduleDto();
		dto.setVehicleNo(vehicleNo);
		dto.setYmd(DateUtils.convert(Calendar.getInstance().getTime(), "yyyy-MM-dd"));
		dto.setSchedulingStatus(FossConstants.YES);
		List<SimpleTruckScheduleDto> list = truckSchedulingTaskService.queryTaskByVehicleNoAndDate(dto);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			return list.get(0);
		}
	}

	public void setTruckSchedulingTaskService(ITruckSchedulingTaskService truckSchedulingTaskService) {
		this.truckSchedulingTaskService = truckSchedulingTaskService;
	}
}