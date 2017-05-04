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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBigRegionDistrService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DistrictDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderActionHistoryEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchVehicleRecordEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderExpressService;
import com.deppon.foss.module.pickup.order.api.shared.define.ActionMessageType;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderQueryHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * 小件订单查询Service
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-20 上午10:17:18
 */
public class OrderExpressService implements IOrderExpressService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderExpressService.class);

	// 空List
	private static final List<String> EMPTY_LIST = new ArrayList<String>();

	static {
		EMPTY_LIST.add("");
	}
	// 订单DAO
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	// PDA签到DAO
	private IPdaSignEntityDao pdaSignEntityDao;
	// 派车记录DAO
	private IDispatchVehicleRecordEntityDao dispatchVehicleRecordEntityDao;
	// 派车记录DAO
	private IDispatchOrderActionHistoryEntityDao dispatchOrderActionHistoryEntityDao;
	//  部门查询
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private IExpressPartSalesDeptService expressPartSalesDeptService;
	/**
	 * 快递大区
	 */
	private IExpressBigRegionDistrService expressBigRegionDistrService;
	/**
	 * 常量20000.
	 */
	private static final int NUMBER = 20000;
	/**
	 * 资源加载
	 */
	private IMessageBundle messageBundle;

	public void setPdaSignEntityDao(IPdaSignEntityDao pdaSignEntityDao) {
		this.pdaSignEntityDao = pdaSignEntityDao;
	}

	public void setDispatchOrderEntityDao(IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}

	public IDispatchOrderActionHistoryEntityDao getDispatchOrderActionHistoryEntityDao() {
		return dispatchOrderActionHistoryEntityDao;
	}

	public void setDispatchOrderActionHistoryEntityDao(
			IDispatchOrderActionHistoryEntityDao dispatchOrderActionHistoryEntityDao) {
		this.dispatchOrderActionHistoryEntityDao = dispatchOrderActionHistoryEntityDao;
	}

	/**
	 * 
	 * 查询小件订单
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public DispatchOrderDto queryExpressDispatchOrders(DispatchOrderConditionDto dispatchOrderConditionDto, int start, int limit) {
		LOGGER.info("初始查询订单条件：" + dispatchOrderConditionDto);
		DispatchOrderDto dispatchOrderDto = new DispatchOrderDto();
		// 初始化查询条件
		initExpressQueryCondition(dispatchOrderConditionDto);
		if (CollectionUtils.isEmpty(dispatchOrderConditionDto.getExpressOrderCityCodes()) || CollectionUtils.isEmpty(dispatchOrderConditionDto.getExpressOrderCountyCodes())) {
			// 如果行政区域未null，直接返回空
			return null;
		}
		// 设置默认的PDA绑定状态
		dispatchOrderConditionDto.setStatus(PdaSignStatusConstants.BUNDLE);
		LOGGER.info("查询订单条件：" + dispatchOrderConditionDto);
		// 查询count总数
		Long count = dispatchOrderEntityDao.queryExpressOrderCountByCommon(dispatchOrderConditionDto);
		dispatchOrderDto.setCount(count);
		// 总数不为0，再执行sql查询实体
		if (count != null && count > 0) {
			List<DispatchOrderEntity> entitys = dispatchOrderEntityDao.queryExpressOrderByCommon(dispatchOrderConditionDto, start, limit);
			for (int i=0;i<entitys.size();i++) {
				DispatchOrderEntity entity=entitys.get(i);
				entity.setIsSms(FossConstants.NO);
				//AUTO-90  14.7.3 查询已分配记录 gaochunling
				String empName=dispatchOrderActionHistoryEntityDao.selectOperatorByorderid(entity.getId());

				entity.setAssignedRecord(empName);
			    entitys.set(i, entity);
			}
			dispatchOrderDto.setDispatchOrderEntities(entitys);
			return dispatchOrderDto;
		}
		return null;
	}

	/**
	 * 初始化查询条件.
	 * 
	 * @param dispatchOrderConditionDto orderNo 
	 * 			订单号 districtList 行政区域list
	 *            smallZoneList 定人定区List 
	 *            businessAreaList 营业区List 
	 *            vehicleType 车型
	 *            orderStatus 订单状态 
	 *            salesDepartmentCodes 营业部 
	 *            serviceFleetList
	 *            接送货车队组 status 
	 *            签到状态 defaultOrderStatus 
	 *            默认的订单状态 driverCode
	 *            司机code vehicleNo 
	 *            车牌号 deviceNo 
	 *            设备号 fleetCode 
	 *            车队编码 isPda 
	 *            是否PDA
	 * @author 038590-foss-wanghui
	 * @date 2013-3-12 下午5:11:39
	 */
	private void initExpressQueryCondition(DispatchOrderConditionDto dispatchOrderConditionDto) {
		// 订单号优先级最高
		if (StringUtils.isNotEmpty(dispatchOrderConditionDto.getOrderNo())) {
			// 设置其他条件为空
			dispatchOrderConditionDto.setOrderStatus(null);
			dispatchOrderConditionDto.setVehicleType(null);
			dispatchOrderConditionDto.setExpressOrderAreas(null);
		}
		OrderQueryHandleDto orderQueryHandleDto = setOrderQueryHandleDto(dispatchOrderConditionDto.getExpressOrderAreas());
		// 设置行政区域(市)
		dispatchOrderConditionDto.setExpressOrderCityCodes(orderQueryHandleDto.getExpressOrderCityCodes());
		// 设置行政区域(区)
		dispatchOrderConditionDto.setExpressOrderCountyCodes(orderQueryHandleDto.getExpressOrderCountyCodes());
		// 设置行政区域(查询输入的区)
		dispatchOrderConditionDto.setExpressOrderNewCountyCodes(orderQueryHandleDto.getExpressOrderNewCountyCodes());
		// 设置运输性质为快递
		dispatchOrderConditionDto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		// 设置默认的订单状态查询条件
		List<String> defaultOrderStatus = new ArrayList<String>();
		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_NONE_HANDLE);
		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP);
		dispatchOrderConditionDto.setDefaultOrderStatus(defaultOrderStatus);
		dispatchOrderConditionDto.setActive(FossConstants.ACTIVE);
	}

	/**
	 * 查询可用人员
	 * 
	 * @param ownTruckConditionDto
	 * @return
	 */
	@Override
	public List<OwnTruckDto> queryUsedUser(OwnTruckConditionDto ownTruckConditionDto) {
		initOwnTruckConditionDto(ownTruckConditionDto);
		if (CollectionUtils.isEmpty(ownTruckConditionDto.getExpressOrderCountyCodes())) {
			// 如果行政区域未null，直接返回空
			return null;
		}
		// 查询可用快递员
		List<OwnTruckDto> pdaSignResult = pdaSignEntityDao.queryUsedUser(ownTruckConditionDto);
		return pdaSignResult;
	}
	/**
	 * 查询全部人员
	 * 
	 * @param ownTruckConditionDto
	 * @return
	 */
	@Override
	public Long queryAllUserCount(OwnTruckConditionDto ownTruckConditionDto) {
		initOwnTruckConditionDto(ownTruckConditionDto);
		if (CollectionUtils.isEmpty(ownTruckConditionDto.getExpressOrderCountyCodes())) {
			// 如果行政区域未null，直接返回空
			return Long.valueOf(NumberConstants.ZERO);
		}
		// 查询可用快递员
		return pdaSignEntityDao.queryAllUserCount(ownTruckConditionDto);
	}
	
	/**
	 * 查询全部人员
	 * 
	 * @param ownTruckConditionDto
	 * @return
	 */
	@Override
	public List<OwnTruckDto> queryAllUser(OwnTruckConditionDto ownTruckConditionDto, int start, int limit) {
		if (CollectionUtils.isEmpty(ownTruckConditionDto.getExpressOrderCountyCodes())) {
			// 如果行政区域未null，直接返回空
			return null;
		}
		// 查询可用快递员
		List<OwnTruckDto> pdaSignResult = pdaSignEntityDao.queryAllUser(ownTruckConditionDto, start, limit);
		return pdaSignResult;
	}
	
	/**
	 * 初始化可用人员查询条件.
	 * @param ownTruckConditionDto
     * 			vehicleNo
     * 				车牌号
     * 			vehicleType
     * 				车型
     * 			serviceFleetCode
     * 				接送货车队
     * 			driverCode
     * 				司机编码
     * 			regionType
     * 				区域类型（接货or送货）
     * 			regionNature
     * 				区域大小
     * 			active
     * 				是否激活
     * 			bundleStatus
     * 				绑定状态
     * 			schedulingType
     * 				排班类型
     * 			schedulingStatus
     * 				排班状态
     * 			schedulingPlanType
     * 				计划状态
     * 			departPlanType
     * 				物流班车类型
     * 			orgCode
     * 				组织code（营业部或车队）
     * 			departPlanStatus
     * 				发车计划状态
	 * @author 038590-foss-wanghui
	 * @date 2012-11-16 下午4:36:44
	 */
	private void initOwnTruckConditionDto(OwnTruckConditionDto ownTruckConditionDto) {
		ownTruckConditionDto.setActive(FossConstants.ACTIVE);
		// PDA绑定状态
		ownTruckConditionDto.setBundleStatus(PdaSignStatusConstants.BUNDLE);
		ownTruckConditionDto.setPdaSignUserType(PdaSignStatusConstants.USER_TYPE_COURIER);
		// 行政区域CODE
		OrderQueryHandleDto orderQueryHandleDto = setOrderQueryHandleDto(ownTruckConditionDto.getExpressOrderCountyCode());
		// 设置行政区域(市)
		ownTruckConditionDto.setExpressOrderCityCodes(orderQueryHandleDto.getExpressOrderCityCodes());
		// 设置行政区域(区)
		ownTruckConditionDto.setExpressOrderCountyCodes(orderQueryHandleDto.getExpressOrderCountyCodes());
		// 设置行政区域(查询输入的区)
		ownTruckConditionDto.setExpressOrderNewCountyCodes(orderQueryHandleDto.getExpressOrderNewCountyCodes());
	}
	
	/**
	 * 根据页面录入的城市code获取查询需要的行政区域列表
	 * @param countyCodes
	 * @return
	 */
	@Override
	public OrderQueryHandleDto setOrderQueryHandleDto(String countyCodes) {
		OrderQueryHandleDto orderQueryHandleDto = new OrderQueryHandleDto();
		//根据营业部编码和创建时间查询快递点部信息
//		ExpressPartSalesDeptResultDto expressPartSalesDeptResultDto = expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime(FossUserContextHelper.getOrgCode(), new Date());
//		if (expressPartSalesDeptResultDto == null || StringUtils.isEmpty(expressPartSalesDeptResultDto.getPartCode())) {
//			return orderQueryHandleDto;
//		}
//		List<OrgAdministrativeInfoEntity>  expressBigRegionList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpByBizType(expressPartSalesDeptResultDto.getPartCode(), BizTypeConstants.EXPRESS_BIG_REGION);
		// 综合接口 根据快递点部获取当前登录人所在的快递大区
		List<OrgAdministrativeInfoEntity>  expressBigRegionList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpByBizType(FossUserContextHelper.getOrgCode(), BizTypeConstants.EXPRESS_BIG_REGION);
		// 快递大区所在市code
		List<String> expressOrderCityCodes = new ArrayList<String>();
		// 快递大区映射的区县code
		List<String>  expressOrderCountyCodes = new ArrayList<String>();
		// 页面现在的区县code
		List<String>  expressOrderNewCountyCodes = new ArrayList<String>();
		StringBuffer newCountyCodes = new StringBuffer();
		
		if(CollectionUtils.isEmpty(expressBigRegionList)) {
			// 如果当前登录人不属于快递大区
			return orderQueryHandleDto;
		}
		
		for (OrgAdministrativeInfoEntity org : expressBigRegionList) {
			if(StringUtils.isNotBlank(org.getCityCode()))
			{
				expressOrderCityCodes.add(org.getCityCode());
			}
			// 综合接口  获取当前登录人所在快递大区下的最小一级的行政区域code列表
			List<DistrictDto> districtDtos =  expressBigRegionDistrService.queryDistrictDtoListForOrgCode(org.getCode());
			if (CollectionUtils.isEmpty(districtDtos)) {
				continue;
			}
			for (DistrictDto districtDto : districtDtos) {
				if (StringUtils.isNotBlank(districtDto.getCountyCode())) {
					newCountyCodes.append(districtDto.getCountyCode()).append(",");
					expressOrderCountyCodes.add(districtDto.getCountyCode());
				}
			}
		}
		if(expressOrderCityCodes.size() > 0)
		{
			// 获取快递大区所在城市code
			orderQueryHandleDto.setExpressOrderCityCodes(expressOrderCityCodes);
		}
		// 登录人所属快递大区下的所有行政区域（区县一级单位）
		orderQueryHandleDto.setExpressOrderCountyCodes(expressOrderCountyCodes);
		orderQueryHandleDto.setCountyCodes(newCountyCodes.toString());
		if (StringUtils.isBlank(countyCodes)) {
			return orderQueryHandleDto;
		}
		String [] countys = countyCodes.split(",");
		if (countys.length == 0) {
			return orderQueryHandleDto;
		}
		for (String county : countys) {
			if (expressOrderCountyCodes.contains(county)) {
				// 在选择的行政区域中，添加
				expressOrderNewCountyCodes.add(county);
			} else {
				continue;
			}
		}
		// 登录人查询录入的行政区域（区县一级单位，必须在所属对应的快递大区下）
		if (CollectionUtils.isEmpty(expressOrderNewCountyCodes)) {
			//获取快递大区所在城市code
			orderQueryHandleDto.setExpressOrderCityCodes(null);
			// 登录人所属快递大区下的所有行政区域（区县一级单位）
			orderQueryHandleDto.setExpressOrderCountyCodes(null);
		} else {
			orderQueryHandleDto.setExpressOrderNewCountyCodes(expressOrderNewCountyCodes);
		}
		return orderQueryHandleDto;
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
		return dispatchVehicleRecordEntityDao.queryExpressVehicleRecordBy(dto, start, limit);
	}

	public void setDispatchVehicleRecordEntityDao(IDispatchVehicleRecordEntityDao dispatchVehicleRecordEntityDao) {
		this.dispatchVehicleRecordEntityDao = dispatchVehicleRecordEntityDao;
	}

	/**
	 * 查询派车记录
	 */
	@Override
	public Long getVehicleRecordByCount(DispatchOrderVehicleDto dto) {
		initDispatchOrderVehicleDto(dto);
		if (CollectionUtils.isEmpty(dto.getExpressOrderCityCodes())) {
			// 如果行政区域未null，直接返回空
			return Long.valueOf(0);
		}
		return dispatchVehicleRecordEntityDao.getExpressVehicleRecordByCount(dto);
	}

	/**
	 * 
	 */
	private void initDispatchOrderVehicleDto(DispatchOrderVehicleDto dto) {
		if (StringUtils.isNotBlank(dto.getOrderNo())) {
			// 如果订单不为空
			dto.setDeliverBeginTime(null);
			dto.setDeliverEndTime(null);
			dto.setOrderVehicleBeginTime(null);
			dto.setOrderVehicleEndTime(null);
			dto.setOrderSendStatus(null);
			dto.setOrderStatus(null);
			dto.setVehicleNo(null);
			dto.setDriverName(null);
			dto.setCountyCode(null);
		}
		// 设置行政区域
		// 行政区域CODE
		OrderQueryHandleDto orderQueryHandleDto = setOrderQueryHandleDto(dto.getCountyCode());
		// 设置行政区域(市)
		dto.setExpressOrderCityCodes(orderQueryHandleDto.getExpressOrderCityCodes());
		// 设置行政区域(区)
		dto.setExpressOrderCountyCodes(orderQueryHandleDto.getExpressOrderCountyCodes());
		// 设置行政区域(查询输入的区)
		dto.setExpressOrderNewCountyCodes(orderQueryHandleDto.getExpressOrderNewCountyCodes());
		// 设置运输性质为快递
		dto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
	}

	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
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
		initDispatchOrderVehicleDto(dto);
		List<DispatchOrderVehicleDto> resultList = new ArrayList<DispatchOrderVehicleDto> ();
		if (!CollectionUtils.isEmpty(dto.getExpressOrderCityCodes())) {
			// 如果行政区域未null，直接返回空
			resultList = dispatchVehicleRecordEntityDao.queryExpressVehicleRecordBy(dto, NumberConstants.ZERO, NUMBER);
		}
		// 获取所有需查询部门
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(DispatchOrderVehicleDto row : resultList){
			List<String> columnList = new ArrayList<String>();
			// 订单号
			columnList.add(row.getOrderNo());
			// 订单发送状态
			columnList.add(DictUtil.rendererSubmitToDisplay(row.getOrderSendStatus(), DictionaryConstants.PKP_ORDER_SEND_STATUS));
			// 订单状态
			columnList.add(DictUtil.rendererSubmitToDisplay(row.getOrderStatus(), DictionaryConstants.PKP_ORDER_STATUS));
			// 接货地址
			columnList.add(row.getPickupAddress());
			//分配方式
			String acceptStatus = row.getAcceptStatus();
			if(!StringUtils.isBlank(acceptStatus)) {
				if(acceptStatus.equals("SYS")) {
					columnList.add("系统分配");
				}else if(acceptStatus.equals("HANDLE")) {
					columnList.add("人工分配");
				}
			}
			// 客户电话
			columnList.add(row.getTel());
			// pda状态
			columnList.add(convertPdaStatus(row.getPdaStatus()));
			// 司机姓名
			columnList.add(row.getDriverName());
			// 车牌号
			columnList.add(row.getVehicleNo());
//			// 客户姓名
//			columnList.add(row.getCustomerName());
//			// 客户手机
//			columnList.add(row.getMobile());
			// 订单备注
			columnList.add(row.getOrderNotes());
			
			String reason;
			reason = DictUtil.rendererSubmitToDisplay(row.getOperateNotes(), DictionaryConstants.PKP_COMPANY_REJECT_REASON);
			if(row.getOperateNotes() != null && row.getOperateNotes().equals(reason)){
				reason = DictUtil.rendererSubmitToDisplay(row.getOperateNotes(), DictionaryConstants.PKP_CUSTOMER_REJECT_REASON);
			}
			// 退回原因
			columnList.add(reason);
			rowList.add(columnList);
		}
		// 列头
//		String[] rowHeads = {"约车部门","订单/约车编号","订单任务发送状态","订单任务完成状态","接货地址","用车时间","PDA使用状态","司机","司机手机","车牌号","客户姓名","电话","手机 ","备注","退回原因"};
		String[] rowHeads = {"订单号","订单任务发送状态","订单任务完成状态","接货地址","分配方式","电话","PDA使用状态","快递员","车牌号","备注","退回原因"};
		
		ExportResource exportResource = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("快递派车记录");
		exportSetting.setSize(NUMBER);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		return objExporterExecutor.exportSync(exportResource, exportSetting);
	}
	

	/**
	 * 
	 * 根据英文转换状态为汉字
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-4 下午2:02:52
	 */
	private String convertPdaStatus(String status){
		if(DispatchOrderStatusConstants.ISPDA_EXCEPTION.equals(status)){
			return messageBundle.getMessage(UserContext.getUserLocale(), ActionMessageType.PDA_EXCEPTION, new Object[0]);
		} else if(DispatchOrderStatusConstants.ISPDA_NO.equals(status)){
			return messageBundle.getMessage(UserContext.getUserLocale(), ActionMessageType.PDA_NO, new Object[0]);
		} else {
			return messageBundle.getMessage(UserContext.getUserLocale(), ActionMessageType.PDA_NORMAL, new Object[0]);
		}
	}

	public void setMessageBundle(IMessageBundle messageBundle) {
		this.messageBundle = messageBundle;
	}

	public IMessageBundle getMessageBundle() {
		return messageBundle;
	}

	public void setExpressBigRegionDistrService(IExpressBigRegionDistrService expressBigRegionDistrService) {
		this.expressBigRegionDistrService = expressBigRegionDistrService;
	}

	public void setExpressPartSalesDeptService(IExpressPartSalesDeptService expressPartSalesDeptService) {
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}
}