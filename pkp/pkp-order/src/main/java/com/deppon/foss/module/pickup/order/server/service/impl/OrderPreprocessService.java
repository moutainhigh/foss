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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/service/impl/OrderPreprocessService.java
 * 
 * FILE NAME        	: OrderPreprocessService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderChangeEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderBusinessLockService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderPreprocessService;
import com.deppon.foss.module.pickup.order.api.server.service.ISpecialPickupAddressService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.OrderMutexElementConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderChangeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.SpecialAddressEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderMutexElement;
import com.deppon.foss.module.pickup.order.api.shared.dto.QueryOrderChangeDto;
import com.deppon.foss.module.pickup.order.api.shared.util.GisInterfaceUtil;
import com.deppon.foss.module.pickup.order.api.shared.util.PropertyFactory;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IShareJobDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobExceptionLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobSuccessLogEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 预处理建议处理服务
 * 
 * @author 038590-foss-wanghui
 * @date 2012-11-2 下午5:24:15
 */
public class OrderPreprocessService implements IOrderPreprocessService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderPreprocessService.class);
	// 变更表dao
	private IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao;
	// 订单dao
	private IDispatchOrderEntityDao dispatchOrderEntityDao;

	private IRegionalVehicleService regionalVehicleService;
	// pda签到dao
	private IPdaSignEntityDao pdaSignEntityDao;
	// 应用监控服务
	private IBusinessMonitorService businessMonitorService;
	// job日志dao
	private IShareJobDao shareJobDao;
	// 配置
	private PropertyFactory propertyFactory;
	// 注入自己
	private IOrderPreprocessService orderPreprocessService;
	
	private IPickupAndDeliverySmallZoneService pickupAndDeliverySmallZoneService;

	private static final String JOB_NAME = "ORDER_PREPROCESS_JOB";

	private IConfigurationParamsService configurationParamsService;
	
	//特殊地址service
	private ISpecialPickupAddressService specialPickupAddressService;
	//业务互斥锁服务
	private IOrderBusinessLockService orderBusinessLockService;
	/**
	 * 处理方法.
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2012-11-2 下午5:24:30
	 */
	@Override
	public void preprocess() {
		// 查询变更表中的记录
		List<DispatchOrderChangeEntity> changeList = null;
		//从配置参数中读取自动调度开关值
		String flag = FossConstants.NO;//默认开关关闭
		String[] codes = new String[1];
		codes[0]=ConfigurationParamsConstants.PKP_AUTO_SCHEDULE_MANAGE;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
		if(null!=configurationParamsEntitys&&configurationParamsEntitys.size()>0){
			flag = configurationParamsEntitys.get(0).getConfValue();//开关是否关闭
		}
		if(StringUtils.equals(FossConstants.YES, flag)){//总开关开启
			QueryOrderChangeDto queryOrderChangeDto = new QueryOrderChangeDto();
			String jobId = "B";
			queryOrderChangeDto.setJob_id(jobId);
			int num = 1000;//查询数量
			queryOrderChangeDto.setNum(num);
			queryOrderChangeDto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
			changeList = dispatchOrderChangeEntityDao.queryChangebyJobId(queryOrderChangeDto);
		}else{//总开关关闭状态
			changeList = dispatchOrderChangeEntityDao.queryChange();
		}
		if (changeList != null && changeList.size() != 0) {
			// 获得待处理的订单
			List<DispatchOrderEntity> orderList = dispatchOrderChangeEntityDao.queryOrder(changeList);
			//获取订单业务锁自动释放时间
			String orderLockTtl = configurationParamsService.queryConfValueByCode(DispatchOrderStatusConstants.ORDER_LOCK_TTL);
			// 调用GIS接口返回接货小区编码
			for (DispatchOrderEntity dispatchOrderEntity : orderList) {
				OrderMutexElement mutexElement = null;
				if (StringUtils.isBlank(orderLockTtl)) {
					mutexElement = new OrderMutexElement(dispatchOrderEntity.getOrderNo(), "订单更新_预处理建议", 
							OrderMutexElementConstants.DISPATCHORDERNO_LOCK);
					LOGGER.info("预处理订单"+dispatchOrderEntity.getOrderNo()+"时订单业务锁自动释放时间采用默认值");
				} else {
					mutexElement = new OrderMutexElement(dispatchOrderEntity.getOrderNo(), "订单更新_预处理建议", 
							OrderMutexElementConstants.DISPATCHORDERNO_LOCK,Integer.valueOf(orderLockTtl));
				}
				
				//互斥锁定
		     	boolean isLocked = orderBusinessLockService.lock(mutexElement, DispatchOrderStatusConstants.ZERO);	
		     	if(!isLocked){
		     		LOGGER.info("自动受理订单"+dispatchOrderEntity.getOrderNo()+"预处理建议时订单已锁定");
		     		continue;
		     	}
				//150325 foss-chengdaolin 特殊地址不给出预处理信息
				StringBuffer sb = new StringBuffer();
				sb.append(dispatchOrderEntity.getPickupProvince());
				sb.append(dispatchOrderEntity.getPickupCity());
				sb.append(dispatchOrderEntity.getPickupCounty());
				sb.append(dispatchOrderEntity.getPickupElseAddress());
				SpecialAddressEntity specialAddressEntity = specialPickupAddressService
						.selectByAddress(sb.toString());
				if (specialAddressEntity!=null) {
					continue;
				}
				
				Map<String, String> map = formatToMap(dispatchOrderEntity);
				Map<String, Object> result = null;
				// 调用GIS接口获得坐标id
				try {
					result = GisInterfaceUtil.callGisInterface(propertyFactory.getGisUrl(), map);
					orderPreprocessService.updateOrderPreprocess(dispatchOrderEntity, result);
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					orderPreprocessService.addExceptionLog(dispatchOrderEntity, e);
					continue;
				} finally {
					orderBusinessLockService.unlock(mutexElement);
					LOGGER.info("自动受理订单"+dispatchOrderEntity.getOrderNo()+"预处理建议结束");
				}
				
			}
			orderPreprocessService.deleteChange(changeList);
		} else {
			LOGGER.info("无订单进行预处理");
		}
	}

	/**
	 * 
	 * 删除待处理记录
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-19 上午11:00:31
	 */
	@Transactional
	public void deleteChange(List<DispatchOrderChangeEntity> changeList) {
		// 删除变更表中记录
		dispatchOrderChangeEntityDao.deleteChange(changeList);
	}

	/**
	 * 
	 * 添加异常日志
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-19 上午10:59:45
	 */
	@Transactional
	public void addExceptionLog(DispatchOrderEntity dispatchOrderEntity, Exception e) {
		// 添加至异常日志表
		JobExceptionLogEntity exceptionLogEntity = new JobExceptionLogEntity();
		exceptionLogEntity.setId(UUIDUtils.getUUID());
		exceptionLogEntity.setJobName(JOB_NAME);
		exceptionLogEntity.setErrorId(dispatchOrderEntity.getId());
		exceptionLogEntity.setErrorCode(e.getMessage());
		StringBuffer sb = new StringBuffer();
		// 堆栈信息
		for (StackTraceElement element : e.getStackTrace()) {
			if (element.toString().indexOf("com.deppon") != -1) {
				sb.append(element.toString());
				sb.append(",");
			}
		}
		exceptionLogEntity.setErrorMsg(sb.substring(0, sb.length() - 1));
		shareJobDao.addJobExceptionLogEntity(exceptionLogEntity);
	}

	/**
	 * 
	 * 根据GIS返回结果，查询接货小区，更新订单预处理建议
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-19 上午10:50:12
	 */
	@Transactional
	public void updateOrderPreprocess(DispatchOrderEntity dispatchOrderEntity, Map<String, Object> result) {
		if (result != null) {
			dispatchOrderEntity.setOrderStatus("");
			// 获得GIS坐标id
			String scopeCoordinatesId = (String) result.get("scopeCoordinatesId");
			LOGGER.info("GIS坐标ID:" + scopeCoordinatesId);
			//是否已入库标记
			String isCollect = (String)result.get("isCollect");
			if(StringUtils.isNotEmpty(isCollect) && FossConstants.YES.equals(isCollect))
			{
				dispatchOrderEntity.setIsCollect(isCollect);
				dispatchOrderEntityDao.updateByPrimaryKeySelective(dispatchOrderEntity);
			}
			// scopeCoordinatesId为空
			if (scopeCoordinatesId == null) {
				JobSuccessLogEntity successLogEntity = new JobSuccessLogEntity();
				successLogEntity.setId(UUIDUtils.getUUID());
				successLogEntity.setJobName(JOB_NAME);
				successLogEntity.setSuccessId(dispatchOrderEntity.getId());
				successLogEntity.setSuccessMsg("调用GIS接口GIS坐标ID为空");
				shareJobDao.addJobSuccessLogEntity(successLogEntity);
				// 不处理
				LOGGER.info("调用GIS接口GIS坐标ID为空");
				return;
			}
			// 调用综合接口获得对应的车辆
			RegionVehicleEntity regionVehicleEntity = null;
			try {
				regionVehicleEntity = regionalVehicleService.querySmallZoneInfoByGisId(scopeCoordinatesId);
			} catch (BusinessException e) {
				LOGGER.info(e.getMessage(),e);
			} catch (Exception e){
				LOGGER.info(e.getMessage(),e);
			}
			if(regionVehicleEntity != null){
				dispatchOrderEntity.setPreprocessId(scopeCoordinatesId);//regionVehicleEntity.getRegionCode() 14.7.23 gcl AUTO-195 gisid
				if(StringUtils.isNotEmpty(regionVehicleEntity.getVehicleNo())){
					PdaSignEntity condition = new PdaSignEntity();
					condition.setVehicleNo(regionVehicleEntity.getVehicleNo());
					condition.setStatus(PdaSignStatusConstants.BUNDLE);
					// 查询签到获得对应司机
					PdaSignEntity pdaSignEntity = pdaSignEntityDao.querySignByVehicleNo(condition);
					// 车牌号
					dispatchOrderEntity.setVehicleNoSuggested(regionVehicleEntity.getVehicleNo());
					// 司机姓名
					dispatchOrderEntity.setDriverNameSuggested(pdaSignEntity.getDriverName());
					// 司机编码
					dispatchOrderEntity.setDriverCodeSuggested(pdaSignEntity.getDriverCode());
				}
				SmallZoneEntity smallZoneEntity = pickupAndDeliverySmallZoneService.querySmallZoneByVirtualCode(regionVehicleEntity.getRegionCode());
				//预处理建议小区code
				dispatchOrderEntity.setSmallZoneCodeSuggested(smallZoneEntity.getRegionCode());
				//预处理建议小区name
				dispatchOrderEntity.setSmallZoneNameSuggested(smallZoneEntity.getRegionName());
				// 更新数据库中对应订单的预处理建议、车辆和司机
				dispatchOrderEntityDao.updateByPrimaryKeySelective(dispatchOrderEntity);
			} else {
				JobSuccessLogEntity successLogEntity = new JobSuccessLogEntity();
				successLogEntity.setId(UUIDUtils.getUUID());
				successLogEntity.setJobName(JOB_NAME);
				successLogEntity.setSuccessId(dispatchOrderEntity.getId());
				successLogEntity.setSuccessMsg("订单综合匹配接货小区失败，为空,GIS坐标ID:" + scopeCoordinatesId);
				shareJobDao.addJobSuccessLogEntity(successLogEntity);
				LOGGER.info("-------------订单预处理完成, 根据GIS返回坐标id请求综合的定人定区返回为空-----------");
			}
		} else {
			JobSuccessLogEntity successLogEntity = new JobSuccessLogEntity();
			successLogEntity.setId(UUIDUtils.getUUID());
			successLogEntity.setJobName(JOB_NAME);
			successLogEntity.setSuccessId(dispatchOrderEntity.getId());
			successLogEntity.setSuccessMsg("调用GIS接口返回空");
			shareJobDao.addJobSuccessLogEntity(successLogEntity);
			// 不处理
			LOGGER.info("调用Gis接口返回空");
		}
	}

	/**
	 * Sets the dispatchOrderChangeEntityDao.
	 * 
	 * @param dispatchOrderChangeEntityDao the dispatchOrderChangeEntityDao to
	 *            set
	 */
	public void setDispatchOrderChangeEntityDao(IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao) {
		this.dispatchOrderChangeEntityDao = dispatchOrderChangeEntityDao;
	}

	/**
	 * 将订单实体转为Map.
	 * 
	 * @param dispatchOrderEntity the dispatchOrderEntity
	 * @return the map
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午7:54:16
	 */
	private static Map<String, String> formatToMap(DispatchOrderEntity dispatchOrderEntity) {
		Map<String, String> map = new HashMap<String, String>();
		// 订单号
		map.put("appNum", dispatchOrderEntity.getOrderNo());
		// 省份
		map.put("province", dispatchOrderEntity.getPickupProvince());
		// 城市
		map.put("city", dispatchOrderEntity.getPickupCity());
		// 区县
		map.put("county", dispatchOrderEntity.getPickupCounty());
		// 其他详细地址
		map.put("otherAddress", dispatchOrderEntity.getPickupElseAddress());
		// 手机
		map.put("phone", dispatchOrderEntity.getMobile());
		// 电话
		map.put("tel", dispatchOrderEntity.getTel());
		// 接货区域
		map.put("matchtype", DictionaryValueConstants.REGION_TYPE_PK);
		return map;
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
	 * Sets the regionalVehicleService.
	 * 
	 * @param regionalVehicleService the regionalVehicleService to set
	 */
	public void setRegionalVehicleService(IRegionalVehicleService regionalVehicleService) {
		this.regionalVehicleService = regionalVehicleService;
	}

	/**
	 * Sets the pdaSignEntityDao.
	 * 
	 * @param pdaSignEntityDao the pdaSignEntityDao to set
	 */
	public void setPdaSignEntityDao(IPdaSignEntityDao pdaSignEntityDao) {
		this.pdaSignEntityDao = pdaSignEntityDao;
	}

	/**
	 * Sets the businessMonitorService.
	 * 
	 * @param businessMonitorService the businessMonitorService to set
	 */
	public void setBusinessMonitorService(IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}

	/**
	 * Sets the shareJobDao.
	 * 
	 * @param shareJobDao the shareJobDao to set
	 */
	public void setShareJobDao(IShareJobDao shareJobDao) {
		this.shareJobDao = shareJobDao;
	}

	/**
	 * Sets the propertyFactory.
	 * 
	 * @param propertyFactory the propertyFactory to set
	 */
	public void setPropertyFactory(PropertyFactory propertyFactory) {
		this.propertyFactory = propertyFactory;
	}

	public void setOrderPreprocessService(IOrderPreprocessService orderPreprocessService) {
		this.orderPreprocessService = orderPreprocessService;
	}

	public void setPickupAndDeliverySmallZoneService(
			IPickupAndDeliverySmallZoneService pickupAndDeliverySmallZoneService) {
		this.pickupAndDeliverySmallZoneService = pickupAndDeliverySmallZoneService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	public void setSpecialPickupAddressService(
			ISpecialPickupAddressService specialPickupAddressService) {
		this.specialPickupAddressService = specialPickupAddressService;
	}

	public void setOrderBusinessLockService(
			IOrderBusinessLockService orderBusinessLockService) {
		this.orderBusinessLockService = orderBusinessLockService;
	}
	
}