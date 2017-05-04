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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/service/impl/ReceiveAddressRfcService.java
 * 
 * FILE NAME        	: ReceiveAddressRfcService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.order.api.shared.util.GisInterfaceUtil;
import com.deppon.foss.module.pickup.order.api.shared.util.PropertyFactory;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IReceiveAddressRfcDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IShareJobDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IReceiveAddressRfcService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobExceptionLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobSuccessLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ReceiveAddressRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 运单地址临时表Service
 * 
 * @author ibm-wangfei
 * @date 2012-10-11 15:42:37
 */
public class ReceiveAddressRfcService implements IReceiveAddressRfcService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReceiveAddressRfcService.class);
	// 注入运单地址临时表DAO
	private IReceiveAddressRfcDao receiveAddressRfcDao;
	// 通知客户Service
	private INotifyCustomerService notifyCustomerService;
	//派送单Service
	private IDeliverbillService deliverbillService;
	// 行政区域 Service接口
	IAdministrativeRegionsService administrativeRegionsService;
	// 配置
	private PropertyFactory propertyFactory;
	/**
	 * 运单地址临时表Service
	 */
	private IReceiveAddressRfcService receiveAddressRfcService;

	private static final String JOB_NAME = "DeliverRegionCodeProcessJob";

	// job日志dao
	private IShareJobDao shareJobDao;

	@Override
	public void updateDeliverRegionCode() {
		boolean hasLongRecord = true;
		ReceiveAddressRfcEntity tmpEntity;
		// 获取待处理的临时表记录-状态为0
		tmpEntity = new ReceiveAddressRfcEntity();
		tmpEntity.setStatus(NotifyCustomerConstants.HANDLE_NO);
		Long count = receiveAddressRfcDao.queryReceiveAddressRfcCount(tmpEntity);
		while (hasLongRecord) {
			if (count == null || count.intValue() <= 0) {
				break;
			}
			if (count <= NotifyCustomerConstants.ROWNUM) {
				hasLongRecord = false;
			}
			exec();
			count = count - NotifyCustomerConstants.ROWNUM;
		}
	}

	/**
	 * 
	 * 执行具体业务操作
	 * 
	 * @author ibm-wangfei
	 * @date Dec 3, 2012 3:49:00 PM
	 */
	private void exec() {
		ReceiveAddressRfcEntity tmpEntity = new ReceiveAddressRfcEntity();
		tmpEntity.setStatus(NotifyCustomerConstants.HANDLE_NO);
		tmpEntity.setNewStatus(NotifyCustomerConstants.HANDLE_ING);
		tmpEntity.setRowNum(NotifyCustomerConstants.ROWNUM);
		// 更新临时表的JOB_ID、STATUS
		receiveAddressRfcService.updateReceiveAddressRfcEntity(tmpEntity);
		// 获取刚更新的临时表记录
		tmpEntity.setStatus(NotifyCustomerConstants.HANDLE_ING);
		List<ReceiveAddressRfcEntity> receiveAddressRfcEntitys = receiveAddressRfcDao.queryReceiveAddressRfc(tmpEntity);
		if (CollectionUtils.isEmpty(receiveAddressRfcEntitys)) {
			return;
		} else {
		}
		for (ReceiveAddressRfcEntity receiveAddressRfcEntity : receiveAddressRfcEntitys) {
			receiveAddressRfcService.executeDetail(receiveAddressRfcEntity);
		}
	}

	/**
	 * 
	 * 更新临时表的JOB_ID、STATUS
	 * 
	 * @author ibm-wangfei
	 * @date Apr 19, 2013 10:43:36 AM
	 */
	@Transactional
	@Override
	public int updateReceiveAddressRfcEntity(ReceiveAddressRfcEntity tmpEntity) {
		// 更新临时表的JOB_ID、STATUS
		return receiveAddressRfcDao.updateReceiveAddressRfcEntity(tmpEntity);
	}

	/**
	 * 
	 * 执行更新
	 * 
	 * @param receiveAddressRfcEntity
	 * @author ibm-wangfei
	 * @date Apr 19, 2013 10:46:46 AM
	 */
	@Transactional
	@Override
	public void executeDetail(ReceiveAddressRfcEntity receiveAddressRfcEntity) {
		// 调综合接口，获取省市区CODE对应的NAME
		// 区域CODE
		List<String> regionCodes = new ArrayList<String>();
		// 省CODE
		if (StringUtil.isNotEmpty(receiveAddressRfcEntity.getReceiveCustomerProvCode())) {
			regionCodes.add(receiveAddressRfcEntity.getReceiveCustomerProvCode());
		}
		// 市CODE
		if (StringUtil.isNotEmpty(receiveAddressRfcEntity.getReceiveCustomerCityCode())) {
			regionCodes.add(receiveAddressRfcEntity.getReceiveCustomerCityCode());
		}
		// 区CODE
		if (StringUtil.isNotEmpty(receiveAddressRfcEntity.getReceiveCustomerCountyCode())) {
			regionCodes.add(receiveAddressRfcEntity.getReceiveCustomerCountyCode());
		}
		// 调取JIS接口
		Map<String, String> map = formatToMap(receiveAddressRfcEntity);
		try {
			// 调综合接口
			Map<String, Object> result = GisInterfaceUtil.callGisInterface(propertyFactory.getGisUrl(), map);
			if (result != null && result.size() > 0 && result.get("success").equals(true)) {
				String gisid = (String) result.get("scopeCoordinatesId"); // 区域范围编码
				
				//是否已入库标记
				String isCollect = (String)result.get("isCollect");
				if (StringUtil.isNotBlank(gisid)) {
					// 根据gis的区域id匹配接货小区id及车辆车牌号
					String regionCode = receiveAddressRfcDao.queryRegionCodeForGisId(gisid);
					if (StringUtil.isNotBlank(regionCode)) {
						ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
						if(StringUtils.isNotEmpty(isCollect) && FossConstants.YES.equals(isCollect))
						{
							actualFreightEntity.setIsCollect(isCollect);
						}
						actualFreightEntity.setWaybillNo(receiveAddressRfcEntity.getWaybillNo());
						actualFreightEntity.setDeliverRegionCode(regionCode);// 区域编码
						// 更新运单附加信息的接货区域编码
						notifyCustomerService.updateActualFreightEntityByWaybillNo(actualFreightEntity);
						// 添加成功日志
						addJobSuccessLogEntity("处理成功，接货区域编码为：" + regionCode, receiveAddressRfcEntity.getWaybillNo());
						LOGGER.info("运单号{}的接货区域编码为{}", receiveAddressRfcEntity.getWaybillNo(), regionCode);
					} else {
						LOGGER.error("根据gis的区域{}没有匹配到接货小区id及车辆车牌号", gisid);
						// 更新错误信息
						addJobSuccessLogEntity("根据gis的区域" + gisid + "没有匹配到接货小区id及车辆车牌号", receiveAddressRfcEntity.getWaybillNo());
					}
				} else {
					ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
					if(StringUtils.isNotEmpty(isCollect) && FossConstants.YES.equals(isCollect))
					{
						actualFreightEntity.setIsCollect(isCollect);
						actualFreightEntity.setWaybillNo(receiveAddressRfcEntity.getWaybillNo());
						// 更新运单附加信息的接货区域编码
						notifyCustomerService.updateActualFreightEntityByWaybillNo(actualFreightEntity);
					}
					// 没有找到区域范围编码
					LOGGER.error("GIS返回区域范围编码为空");
					// 更新错误信息
					addJobSuccessLogEntity("GIS返回区域范围编码为空", receiveAddressRfcEntity.getWaybillNo());
				}
			} else {
				// 综合接口调用GIS返回失败
				LOGGER.error("调用GIS接口返回失败");
				// 更新错误信息
				addJobSuccessLogEntity("调用GIS接口返回失败", receiveAddressRfcEntity.getWaybillNo());
			}
		} catch (BusinessException e) {
			LOGGER.error("error", e);
			// 更新错误信息
			addExceptionLog(receiveAddressRfcEntity.getWaybillNo(), e);
		} catch (Exception e) {
			LOGGER.error("error", e);
			// 更新错误信息
			addExceptionLog(receiveAddressRfcEntity.getWaybillNo(), e);
		}
		// 删除本次处理的临时表记录
		receiveAddressRfcDao.deleteReceiveAddressRfcEntity(receiveAddressRfcEntity);
	}

	/**
	 * 
	 * 设置传入JIS接口的参数
	 * 
	 * @author ibm-wangfei
	 * @date Dec 3, 2012 11:09:51 AM
	 */
	private Map<String, String> formatToMap(ReceiveAddressRfcEntity receiveAddressRfcEntity) {
		List<String> regionCodes = new ArrayList<String>();
		Map<String, AdministrativeRegionsEntity> regionNames = null;
		Map<String, String> map = new HashMap<String, String>();

		// 省CODE
		if (StringUtil.isNotEmpty(receiveAddressRfcEntity.getReceiveCustomerProvCode())) {
			regionCodes.add(receiveAddressRfcEntity.getReceiveCustomerProvCode());
		}
		// 市CODE
		if (StringUtil.isNotEmpty(receiveAddressRfcEntity.getReceiveCustomerCityCode())) {
			regionCodes.add(receiveAddressRfcEntity.getReceiveCustomerCityCode());
		}
		// 区CODE
		if (StringUtil.isNotEmpty(receiveAddressRfcEntity.getReceiveCustomerCountyCode())) {
			regionCodes.add(receiveAddressRfcEntity.getReceiveCustomerCountyCode());
		}
		//调综合接口，获取省市区CODE对应的NAME
		if (CollectionUtils.isNotEmpty(regionCodes)) {
			regionNames = administrativeRegionsService.queryAdministrativeRegionsBatchByCodeToMap(regionCodes);
		} else {
		}
		if (regionNames != null && !regionNames.isEmpty()) {
			// 省的名称赋值
			if (regionNames.get(receiveAddressRfcEntity.getReceiveCustomerProvCode()) != null) {
				map.put("province", regionNames.get(receiveAddressRfcEntity.getReceiveCustomerProvCode()).getName());
			} else {
				map.put("province", "");
			}
			// 市的名称赋值
			if (regionNames.get(receiveAddressRfcEntity.getReceiveCustomerCityCode()) != null) {
				map.put("city", regionNames.get(receiveAddressRfcEntity.getReceiveCustomerCityCode()).getName());
			} else {
				map.put("city", "");
			}
			// 区的名称赋值
			if (regionNames.get(receiveAddressRfcEntity.getReceiveCustomerCountyCode()) != null) {
				map.put("county", regionNames.get(receiveAddressRfcEntity.getReceiveCustomerCountyCode()).getName());
			} else {
				map.put("county", "");
			}
		}
		map.put("appNum", receiveAddressRfcEntity.getWaybillNo());
		map.put("otherAddress", receiveAddressRfcEntity.getReceiveCustomerAddress());
		map.put("phone", receiveAddressRfcEntity.getReceiveCustomerMobilephone());
		map.put("tel", receiveAddressRfcEntity.getReceiveCustomerPhone());
		map.put("matchtype", DictionaryValueConstants.REGION_TYPE_DE);
		return map;
	}

	/**
	 * 
	 * 添加成功日志
	 * 
	 * @param successMsg
	 * @param waybillNo
	 * @author ibm-wangfei
	 * @date Jun 1, 2013 6:50:04 PM
	 */
	private void addJobSuccessLogEntity(String successMsg, String waybillNo) {
		JobSuccessLogEntity successLogEntity = new JobSuccessLogEntity();
		successLogEntity.setId(UUIDUtils.getUUID());
		successLogEntity.setJobName(JOB_NAME);
		successLogEntity.setSuccessId(waybillNo);
		successLogEntity.setSuccessMsg(successMsg);
		shareJobDao.addJobSuccessLogEntity(successLogEntity);
		LOGGER.info(successMsg + ":" + waybillNo);
	}

	/**
	 * 
	 * 添加异常日志
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-19 上午10:59:45
	 */
	private void addExceptionLog(String waybillNo, Exception e) {
		// 添加至异常日志表
		JobExceptionLogEntity exceptionLogEntity = new JobExceptionLogEntity();
		exceptionLogEntity.setId(UUIDUtils.getUUID());
		exceptionLogEntity.setJobName(JOB_NAME);
		exceptionLogEntity.setErrorId(waybillNo);
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
	 * 新增
	 * 
	 * @author ibm-wangfei
	 * @date Nov 30, 2012 3:37:19 PM
	 */
	@Override
	public int addReceiveAddressRfcEntity(ReceiveAddressRfcEntity receiveAddressRfcEntity) {
		return this.receiveAddressRfcDao.addReceiveAddressRfcEntity(receiveAddressRfcEntity);
	}

	public void setReceiveAddressRfcDao(IReceiveAddressRfcDao receiveAddressRfcDao) {
		this.receiveAddressRfcDao = receiveAddressRfcDao;
	}

	public void setNotifyCustomerService(INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}

	public void setPropertyFactory(PropertyFactory propertyFactory) {
		this.propertyFactory = propertyFactory;
	}

	public void setAdministrativeRegionsService(IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public void setReceiveAddressRfcService(IReceiveAddressRfcService receiveAddressRfcService) {
		this.receiveAddressRfcService = receiveAddressRfcService;
	}

	public void setShareJobDao(IShareJobDao shareJobDao) {
		this.shareJobDao = shareJobDao;
	}

	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}
	
}