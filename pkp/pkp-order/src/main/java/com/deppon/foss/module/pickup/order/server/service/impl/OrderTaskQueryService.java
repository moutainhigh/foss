/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-order
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/service/impl/OrderTaskQueryService.java
 * 
 * FILE NAME        	: OrderTaskQueryService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonMotorcadeDistrictService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonMotorcadeSaleDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonMotorcadeSalesAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeDistrictEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeSalesAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderActionHistoryEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchVehicleRecordEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskQueryService;
import com.deppon.foss.module.pickup.order.api.shared.define.ActionMessageType;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 订单查询Service
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-20 上午10:17:18
 */
public class OrderTaskQueryService implements IOrderTaskQueryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderTaskQueryService.class);
	
	// 空List
	private static final List<String> EMPTY_LIST = new ArrayList<String>();
	/** 
	 * 常量20000.
	 */
	private static final int NUMBER = 20000;
	
	private static final String SUFFIX_SALEDEPT = "营业部";
	
	static {
		EMPTY_LIST.add("");
	}
	// 订单DAO
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	// 派车记录DAO
	private IDispatchVehicleRecordEntityDao dispatchVehicleRecordEntityDao;
	// 组织service
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	// 基本的组织service
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	// 车队对应的行政区域service
	private ICommonMotorcadeDistrictService commonMotorcadeDistrictService;
	// 车队对应的接货小区service
	private ICommonSmallZoneService commonSmallZoneService;
	// 车队对应的营业区service
	private ICommonMotorcadeSalesAreaService commonMotorcadeSalesAreaService;
	// 车队对应的营业部service
	private ICommonMotorcadeSaleDeptService commonMotorcadeSaleDeptService;
	// 派车记录DAO
	private IDispatchOrderActionHistoryEntityDao dispatchOrderActionHistoryEntityDao;
	
	private ISaleDepartmentService saleDepartmentService;
	
	private static boolean loginIsSaleDept ; //当前登录部门是否营业部
	/**
	 * 资源加载
	 */
	private IMessageBundle messageBundle;

	/**
	 * 查询调度订单.
	 * 
	 * @param dispatchOrderConditionDto
	 * 			orderNo
	 * 				订单号
	 * 			districtList
	 * 				行政区域list
	 * 			smallZoneList
	 * 				定人定区List
	 * 			businessAreaList
	 * 				营业区List
	 * 			vehicleType
	 * 				车型
	 * 			orderStatus
	 * 				订单状态
	 * 			salesDepartmentCodes
	 * 				营业部
	 * 			serviceFleetList
	 * 				接送货车队组
	 * 			status
	 * 				签到状态
	 * 			defaultOrderStatus
	 * 				默认的订单状态
	 * 			driverCode
	 * 				司机code
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			fleetCode
	 * 				车队编码
	 * 			isPda
	 * 				是否PDA
	 * @param start the start
	 * @param limit the limit
	 * @return the dispatch order dto
	 * @author 038590-foss-wanghui
	 * @date 2012-10-20 下午4:15:55
	 * @see com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskQueryService#queryDispatchOrders(com.deppon.foss.module.pickup.order.api.shared.vo.DispatchOrderConditionVo)
	 */
	@Override
	public DispatchOrderDto queryDispatchOrders(DispatchOrderConditionDto dispatchOrderConditionDto, int start, int limit) {
		LOGGER.info("初始查询订单条件：" + dispatchOrderConditionDto);
		DispatchOrderDto dispatchOrderDto = new DispatchOrderDto();
		// 初始化查询条件
		initQueryCondition(dispatchOrderConditionDto);
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(FossUserContextHelper.getOrgCode());
		if(saleDepartmentEntity!=null&&saleDepartmentEntity.getName().endsWith(SUFFIX_SALEDEPT)) {
			dispatchOrderConditionDto.setSalesDepartmentCodes(null);
			List<String> fleetCodes = new ArrayList<String>();
			fleetCodes.add(saleDepartmentEntity.getCode());
			dispatchOrderConditionDto.setFleetList(fleetCodes);
			loginIsSaleDept = true;
		}else {
			// 设置默认的PDA绑定状态
			dispatchOrderConditionDto.setStatus(PdaSignStatusConstants.BUNDLE);
			// 判断是否车队，若是车队，赋默认查询条件，仅当所有区域范围的查询条件均为空时才执行
			if (StringUtils.isNotEmpty(dispatchOrderConditionDto.getFleetCode())) {
				// 查询顶级车队下的所有车队
				List<OrgAdministrativeInfoEntity> fleetList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoSubByMotorcade(dispatchOrderConditionDto.getFleetCode(),BizTypeConstants.ORG_TRANS_DEPARTMENT);
				// 查询车队组
				List<OrgAdministrativeInfoEntity> teamList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoSubByMotorcade(dispatchOrderConditionDto.getFleetCode(),BizTypeConstants.ORG_TRANS_TEAM);
				List<String> fleetConditionList = new ArrayList<String>();
				// 添加本顶级车队
				fleetConditionList.add(dispatchOrderConditionDto.getFleetCode());
				// 赋值
				if(fleetList != null){
					for(OrgAdministrativeInfoEntity fleet : fleetList){
						fleetConditionList.add(fleet.getCode());
					}
				}
				// 赋值
				if(teamList != null){
					for(OrgAdministrativeInfoEntity team : teamList){
						fleetConditionList.add(team.getCode());
					}
				}
				// 设置查询条件
				dispatchOrderConditionDto.setFleetList(fleetConditionList);
				// 车队，查询的集中区域的订单
				dispatchOrderConditionDto.setIsSalesDept(FossConstants.YES);
			} else {
				// 营业部，查询的非集中区域的订单
				dispatchOrderConditionDto.setIsSalesDept(FossConstants.NO);
			}
			LOGGER.info("查询订单条件：" + dispatchOrderConditionDto);
		}
		// 查询count总数
		Long count = dispatchOrderEntityDao.queryOrderCountByCommon(dispatchOrderConditionDto);
		dispatchOrderDto.setCount(count);
		// 总数不为0，再执行sql查询实体
		if (count != null && count > 0) {
			List<DispatchOrderEntity> entitys=dispatchOrderEntityDao.queryOrderByCommon(dispatchOrderConditionDto, start, limit);
			for (DispatchOrderEntity entity : entitys) {
				//on-1012  14.9.4 查询已分配记录 gcl
				String empName=dispatchOrderActionHistoryEntityDao.ldSelectOperatorByorderid(entity.getId());
				entity.setAssignedRecord(empName);
				//判断是约车车队是否本部门(是则调度不必填写实际接货小区也可受理订单)
				entity.setFleetIsSaleDept(loginIsSaleDept==true?FossConstants.ACTIVE:FossConstants.NO);
				String notes = entity.getOperateNotes();
				if(StringUtils.isNotBlank(notes)) {
					if(!notes.startsWith("其他")) {
						if(notes.endsWith("-")) {
							notes = notes.substring(0, notes.length()-1);
							entity.setOperateNotes(notes);
						}
					}
				}
			}
			dispatchOrderDto.setDispatchOrderEntities(entitys);
			return dispatchOrderDto;
		}
		return null;
	}

	/**
	 * 构造车队的查询条件 如果是车队，则需要初始化行政区域、定人定区、营业部的查询条件.
	 * 
	 * @param dispatchOrderConditionDto
	 * 			orderNo
	 * 				订单号
	 * 			districtList
	 * 				行政区域list
	 * 			smallZoneList
	 * 				定人定区List
	 * 			businessAreaList
	 * 				营业区List
	 * 			vehicleType
	 * 				车型
	 * 			orderStatus
	 * 				订单状态
	 * 			salesDepartmentCodes
	 * 				营业部
	 * 			serviceFleetList
	 * 				接送货车队组
	 * 			status
	 * 				签到状态
	 * 			defaultOrderStatus
	 * 				默认的订单状态
	 * 			driverCode
	 * 				司机code
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			fleetCode
	 * 				车队编码
	 * 			isPda
	 * 				是否PDA
	 * @param start the start
	 * @param limit the limit
	 * @author 038590-foss-wanghui
	 * @date 2013-3-12 下午5:32:01
	 */
	private void initFleetQueryCondition(DispatchOrderConditionDto dispatchOrderConditionDto, int start, int limit) {
		MotorcadeServeDistrictEntity districtEntity = new MotorcadeServeDistrictEntity();
		districtEntity.setMotorcadeCode(dispatchOrderConditionDto.getFleetCode());
		// 获得车队负责的行政区域
		List<MotorcadeServeDistrictEntity> serveDistrictList = commonMotorcadeDistrictService.queryMotorcadeServeDistrictByCondition(districtEntity, 0, Integer.MAX_VALUE);
		List<String> districtList = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(districtList)) {
			for (MotorcadeServeDistrictEntity district : serveDistrictList) {
				districtList.add(district.getMotorcadeCode());
			}
		}
		// 设置行政区域查询条件
		dispatchOrderConditionDto.setDistrictList(districtList);
		SmallZoneEntity smallZoneEntity = new SmallZoneEntity();
		smallZoneEntity.setManagement(dispatchOrderConditionDto.getFleetCode());
		smallZoneEntity.setRegionType(DictionaryValueConstants.REGION_TYPE_PK);
		// 获得车队对应的接货小区
		List<SmallZoneEntity> smallZoneList = commonSmallZoneService.querySmallZones(smallZoneEntity, 0, Integer.MAX_VALUE);
		List<String> smallZoneCodeList = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(smallZoneList)) {
			for (SmallZoneEntity smallZone : smallZoneList) {
				smallZoneCodeList.add(smallZone.getRegionCode());
			}
		}
		// 设置定人定区查询条件
		dispatchOrderConditionDto.setSmallZoneList(smallZoneCodeList);
		List<String> salesDepartmentList = new ArrayList<String>();
		MotorcadeServeSalesAreaEntity motorcadeServeSalesAreaEntity = new MotorcadeServeSalesAreaEntity();
		motorcadeServeSalesAreaEntity.setMotorcadeCode(dispatchOrderConditionDto.getFleetCode());
		// 获得车队对应的营业区
		List<MotorcadeServeSalesAreaEntity> serveSalesAreaList = commonMotorcadeSalesAreaService.queryMotorcadeServeSalesAreaByCondtion(motorcadeServeSalesAreaEntity, 0, Integer.MAX_VALUE);
		if (CollectionUtils.isNotEmpty(serveSalesAreaList)) {
			for (MotorcadeServeSalesAreaEntity motorcadeServeSalesArea : serveSalesAreaList) {
				List<OrgAdministrativeInfoEntity> salesDepartments = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntitySalesBySmall(motorcadeServeSalesArea
						.getSalesareaCode());
				for (OrgAdministrativeInfoEntity salesDepartment : salesDepartments) {
					salesDepartmentList.add(salesDepartment.getCode());
				}
			}
		}
		SalesMotorcadeEntity salesMotorcadeEntity = new SalesMotorcadeEntity();
		salesMotorcadeEntity.setMotorcadeCode(dispatchOrderConditionDto.getFleetCode());
		// 获得车队对应的营业部
		List<SalesMotorcadeEntity> salesMotorcadeList = commonMotorcadeSaleDeptService.queryMotorcadeSalesDeptByCondition(salesMotorcadeEntity, 0, Integer.MAX_VALUE);
		for (SalesMotorcadeEntity salesMotorcade : salesMotorcadeList) {
			if (StringUtils.isNotEmpty(salesMotorcade.getSalesdeptCode())) {
				salesDepartmentList.add(salesMotorcade.getSalesdeptCode());
			}
		}
		// 设置营业部
		dispatchOrderConditionDto.setSalesDepartmentCodes(salesDepartmentList);
	}

	/**
	 * 初始化查询条件.
	 * 
	 * @param dispatchOrderConditionDto
	 * 			orderNo
	 * 				订单号
	 * 			districtList
	 * 				行政区域list
	 * 			smallZoneList
	 * 				定人定区List
	 * 			businessAreaList
	 * 				营业区List
	 * 			vehicleType
	 * 				车型
	 * 			orderStatus
	 * 				订单状态
	 * 			salesDepartmentCodes
	 * 				营业部
	 * 			serviceFleetList
	 * 				接送货车队组
	 * 			status
	 * 				签到状态
	 * 			defaultOrderStatus
	 * 				默认的订单状态
	 * 			driverCode
	 * 				司机code
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			fleetCode
	 * 				车队编码
	 * 			isPda
	 * 				是否PDA
	 * @author 038590-foss-wanghui
	 * @date 2013-3-12 下午5:11:39
	 */
	private void initQueryCondition(DispatchOrderConditionDto dispatchOrderConditionDto) {
		// 订单号优先级最高
		String temp = dispatchOrderConditionDto.getOrderNo();
		loginIsSaleDept = false;
		if (StringUtils.isNotEmpty(temp)) {
			// 设置其他条件为空
			dispatchOrderConditionDto.setOrderStatus(null);
			dispatchOrderConditionDto.setVehicleType(null);
			dispatchOrderConditionDto.setOrderNo(temp.trim());
		} else {
			// 删除List集合中的空字符串对象
			if (dispatchOrderConditionDto.getSalesDepartmentCodes() != null) {
				dispatchOrderConditionDto.getSalesDepartmentCodes().removeAll(EMPTY_LIST);
			}
			if (dispatchOrderConditionDto.getSmallZoneList() != null) {
				dispatchOrderConditionDto.getSmallZoneList().removeAll(EMPTY_LIST);
			}
			if (dispatchOrderConditionDto.getDistrictList() != null) {
				dispatchOrderConditionDto.getDistrictList().removeAll(EMPTY_LIST);
			}
			// 调用综合接口获取营业区下的所有营业部
			if (dispatchOrderConditionDto.getBusinessAreaList() != null) {
				List<String> salesDepartmentList = new ArrayList<String>();
				for (String smallBusinessAreaCode : dispatchOrderConditionDto.getBusinessAreaList()) {
					List<OrgAdministrativeInfoEntity> salesDepartments = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntitySalesBySmall(smallBusinessAreaCode);
					for (OrgAdministrativeInfoEntity salesDepartment : salesDepartments) {
						salesDepartmentList.add(salesDepartment.getCode());
					}
				}
				if(CollectionUtils.isNotEmpty(dispatchOrderConditionDto.getSalesDepartmentCodes())){
					salesDepartmentList.addAll(dispatchOrderConditionDto.getSalesDepartmentCodes());
				}
				dispatchOrderConditionDto.setSalesDepartmentCodes(salesDepartmentList);
			}
		}
		
		// 设置默认的订单状态查询条件
		List<String> defaultOrderStatus = new ArrayList<String>();
		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_NONE_HANDLE);
		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP);
		dispatchOrderConditionDto.setDefaultOrderStatus(defaultOrderStatus);
		dispatchOrderConditionDto.setActive(FossConstants.ACTIVE);
	}

	/**
	 * 根据条件查询调度订单派车记录.
	 * 
	 * @param dto
	 * 			id
	 * 				派车记录Id
	 * 			orderId
	 * 				订单ID
	 * 			orderVehicleOrgName
	 * 				约车部门名称
	 * 			orderNo
	 * 				订单号
	 * 			deliverBeginTime
	 * 				派车时间 ==处理订单时间开始时间
	 * 			deliverEndTime
	 * 				派车时间 ==处理订单时间结束时间
	 * 			earliestPickupTime
	 * 				接货最早时间 == 用车时间
	 * 			latestPickupTime
	 * 				接货最晚时间
	 * 			orderVehicleTime
	 * 				约车时间
	 * 			driverName
	 * 				司机姓名
	 * 			vehicleNo
	 * 				车牌号
	 * 			orderNotes
	 * 				备注 ==订单备注
	 * 			pickupRegionCode
	 * 				定人定区
	 * 			orderSendStatus
	 * 				订单任务发送状态
	 * 			orderStatus
	 * 				订单任务完成状态 == 订单状态
	 * 			orderType
	 * 				订单类型
	 * 			usecarTime
	 * 				用车时间
	 * 			pdaStatus
	 * 				PDA使用状态
	 * 			deliverTime
	 * 				派车时间
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-7 上午10:17:49
	 */
	@Override
	public List<DispatchOrderVehicleDto> queryVehicleRecordBy(DispatchOrderVehicleDto dto, int start, int limit) {
		getAllSubOrg(dto);
		return dispatchVehicleRecordEntityDao.queryVehicleRecordBy(dto, start, limit);
	}

	private void getAllSubOrg(DispatchOrderVehicleDto dto) {
		// 根据组织code查询组织
		OrgAdministrativeInfoEntity org  = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getOrgCode());
		if(null!=org&&org.checkTransDepartment()) {
			// 查询顶级车队下的所有车队
			List<OrgAdministrativeInfoEntity> fleetList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoSubByBizType(dto.getOrgCode(),BizTypeConstants.ORG_TRANS_DEPARTMENT);
			// 查询车队组
			List<OrgAdministrativeInfoEntity> teamList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoSubByBizType(dto.getOrgCode(),BizTypeConstants.ORG_TRANS_TEAM);
			List<OrgAdministrativeInfoEntity> fleetConditionList = new ArrayList<OrgAdministrativeInfoEntity>();
			// 添加本顶级车队
			fleetConditionList.add(org);
			// 赋值
			if(fleetList != null){
				fleetConditionList.addAll(fleetList);
			}
			// 赋值
			if(teamList != null){
				fleetConditionList.addAll(teamList);
			}
			dto.setFleetList(fleetConditionList);
		}
		// 订单号优先级最高
		if(StringUtils.isNotEmpty(dto.getOrderNo())){
			dto.setDeliverBeginTime(null);
			dto.setDeliverEndTime(null);
			dto.setVehicleNo(null);
			dto.setDistrictList(null);
			dto.setOrderStatus(null);
			dto.setOrderSendStatus(null);
			dto.setPickupRegionCode(null);
			dto.setSalesDepartmentCodes(null);
		}
	}
	
	/**
	 * 
	 * 不分页查询派车记录（导出用）
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-4 上午11:55:38
	 */
	@Override
	public InputStream queryVehicleRecord(DispatchOrderVehicleDto dto) {
		// 获取所有需查询部门
		getAllSubOrg(dto);
		List<DispatchOrderVehicleDto> resultList = dispatchVehicleRecordEntityDao.queryVehicleRecord(dto);
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(DispatchOrderVehicleDto row : resultList){
			List<String> columnList = new ArrayList<String>();
			// 约车部门名称
			columnList.add(row.getOrderVehicleOrgName());
			// 订单号
			columnList.add(row.getOrderNo());
			//重量/体积  DEFECT-4093
			columnList.add(row.getWeight()+"KG/"+row.getVolume()+"方");
			// 订单发送状态
			columnList.add(DictUtil.rendererSubmitToDisplay(row.getOrderSendStatus(), DictionaryConstants.PKP_ORDER_SEND_STATUS));
			// 订单状态
			columnList.add(DictUtil.rendererSubmitToDisplay(row.getOrderStatus(), DictionaryConstants.PKP_ORDER_STATUS));
			// 接货地址
			columnList.add(row.getPickupAddress());
			// 系统分配小区
			columnList.add(row.getPickupRegionName());
			// 实际接货小区
			columnList.add(row.getActualRegionName());
			// 操作时间
			columnList.add(DateUtils.convert(row.getDeliverTime(), DateUtils.DATE_TIME_FORMAT));
			// 用车时间
			columnList.add(row.getUsecarTime());
//			// pda状态
//			columnList.add(convertPdaStatus(row.getPdaStatus()));
			//开单时间
			columnList.add(DateUtils.convert(row.getBillTime(),DateUtils.DATE_TIME_FORMAT));
			// 司机姓名
			columnList.add(row.getDriverName());
			// 司机手机
			columnList.add(row.getDriverMobile());
			// 车牌号
			columnList.add(row.getVehicleNo());
			// 客户姓名
			//以下三个字段不导出
			/*columnList.add(row.getCustomerName());
			// 客户电话
			columnList.add(row.getTel());
			// 客户手机
			columnList.add(row.getMobile());*/
			// 订单备注
			columnList.add(row.getOrderNotes());
			String reason;
			reason = DictUtil.rendererSubmitToDisplay(row.getOperateNotes(), DictionaryConstants.PKP_COMPANY_REJECT_REASON);
			if(row.getOperateNotes() != null && row.getOperateNotes().equals(reason)){
				reason = DictUtil.rendererSubmitToDisplay(row.getOperateNotes(), DictionaryConstants.PKP_CUSTOMER_REJECT_REASON);
			}
			// 退回原因
			columnList.add(reason);
			// 自动调度
			columnList.add(StringUtils.equals(DispatchOrderStatusConstants.ORDER_ACCEPT_STATUS_SYS, row.getAcceptStatus())?"自动处理":"");
			rowList.add(columnList);
		}
		// 列头
		String[] rowHeads = {"约车部门","订单/约车编号","重量/体积","订单任务发送状态","订单任务完成状态","接货地址","系统分配小区","实际接货小区","操作时间","用车时间","开单时间","司机","司机手机","车牌号"/*,"客户姓名","电话","手机 "*/,"备注","退回原因","自动调度"};
		
		ExportResource exportResource = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("派车记录");
		exportSetting.setSize(NUMBER);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		return objExporterExecutor.exportSync(exportResource, exportSetting);
	}
	
//	/**
//	 * 
//	 * 根据英文转换状态为汉字
//	 * 
//	 * @author 038590-foss-wanghui
//	 * @date 2013-6-4 下午2:02:52
//	 */
//	private String convertPdaStatus(String status){
//		if(DispatchOrderStatusConstants.ISPDA_EXCEPTION.equals(status)){
//			return messageBundle.getMessage(UserContext.getUserLocale(), ActionMessageType.PDA_EXCEPTION, new Object[0]);
//		} else if(DispatchOrderStatusConstants.ISPDA_NO.equals(status)){
//			return messageBundle.getMessage(UserContext.getUserLocale(), ActionMessageType.PDA_NO, new Object[0]);
//		} else {
//			return messageBundle.getMessage(UserContext.getUserLocale(), ActionMessageType.PDA_NORMAL, new Object[0]);
//		}
//	}
	
	/**
	 * 根据查询条件查询调度订单派车记录总条数.
	 * 
	 * @param dto
	 * 			id
	 * 				派车记录Id
	 * 			orderId
	 * 				订单ID
	 * 			orderVehicleOrgName
	 * 				约车部门名称
	 * 			orderNo
	 * 				订单号
	 * 			deliverBeginTime
	 * 				派车时间 ==处理订单时间开始时间
	 * 			deliverEndTime
	 * 				派车时间 ==处理订单时间结束时间
	 * 			earliestPickupTime
	 * 				接货最早时间 == 用车时间
	 * 			latestPickupTime
	 * 				接货最晚时间
	 * 			orderVehicleTime
	 * 				约车时间
	 * 			driverName
	 * 				司机姓名
	 * 			vehicleNo
	 * 				车牌号
	 * 			orderNotes
	 * 				备注 ==订单备注
	 * 			pickupRegionCode
	 * 				定人定区
	 * 			orderSendStatus
	 * 				订单任务发送状态
	 * 			orderStatus
	 * 				订单任务完成状态 == 订单状态
	 * 			orderType
	 * 				订单类型
	 * 			usecarTime
	 * 				用车时间
	 * 			pdaStatus
	 * 				PDA使用状态
	 * 			deliverTime
	 * 				派车时间
	 * @return the vehicleRecordByCount
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-7 下午1:58:49
	 */
	@Override
	public Long getVehicleRecordByCount(DispatchOrderVehicleDto dto) {
		return dispatchVehicleRecordEntityDao.getVehicleRecordByCount(dto);
	}

	
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * Gets the dispatchOrderEntityDao.
	 * 
	 * @return the dispatchOrderEntityDao
	 */
	public IDispatchOrderEntityDao getDispatchOrderEntityDao() {
		return dispatchOrderEntityDao;
	}

	/**
	 * Sets the dispatchOrderEntityDao.
	 * 
	 * @param dispatchOrderEntityDao the dispatchOrderEntityDao to set
	 */
	public void setDispatchOrderEntityDao(IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}

	/**
	 * Sets the dispatchVehicleRecordEntityDao.
	 * 
	 * @param dispatchVehicleRecordEntityDao the dispatchVehicleRecordEntityDao
	 *            to set
	 */
	public void setDispatchVehicleRecordEntityDao(IDispatchVehicleRecordEntityDao dispatchVehicleRecordEntityDao) {
		this.dispatchVehicleRecordEntityDao = dispatchVehicleRecordEntityDao;
	}

	/**
	 * Sets the orgAdministrativeInfoComplexService.
	 * 
	 * @param orgAdministrativeInfoComplexService the
	 *            orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * Sets the commonMotorcadeDistrictService.
	 * 
	 * @param commonMotorcadeDistrictService the commonMotorcadeDistrictService
	 *            to set
	 */
	public void setCommonMotorcadeDistrictService(ICommonMotorcadeDistrictService commonMotorcadeDistrictService) {
		this.commonMotorcadeDistrictService = commonMotorcadeDistrictService;
	}

	/**
	 * Sets the commonSmallZoneService.
	 * 
	 * @param commonSmallZoneService the commonSmallZoneService to set
	 */
	public void setCommonSmallZoneService(ICommonSmallZoneService commonSmallZoneService) {
		this.commonSmallZoneService = commonSmallZoneService;
	}

	/**
	 * Sets the commonMotorcadeSalesAreaService.
	 * 
	 * @param commonMotorcadeSalesAreaService the
	 *            commonMotorcadeSalesAreaService to set
	 */
	public void setCommonMotorcadeSalesAreaService(ICommonMotorcadeSalesAreaService commonMotorcadeSalesAreaService) {
		this.commonMotorcadeSalesAreaService = commonMotorcadeSalesAreaService;
	}

	/**
	 * Sets the commonMotorcadeSaleDeptService.
	 * 
	 * @param commonMotorcadeSaleDeptService the commonMotorcadeSaleDeptService
	 *            to set
	 */
	public void setCommonMotorcadeSaleDeptService(ICommonMotorcadeSaleDeptService commonMotorcadeSaleDeptService) {
		this.commonMotorcadeSaleDeptService = commonMotorcadeSaleDeptService;
	}

	public IMessageBundle getMessageBundle() {
		return messageBundle;
	}

	public void setMessageBundle(IMessageBundle messageBundle) {
		this.messageBundle = messageBundle;
	}

	public void setDispatchOrderActionHistoryEntityDao(
			IDispatchOrderActionHistoryEntityDao dispatchOrderActionHistoryEntityDao) {
		this.dispatchOrderActionHistoryEntityDao = dispatchOrderActionHistoryEntityDao;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
}