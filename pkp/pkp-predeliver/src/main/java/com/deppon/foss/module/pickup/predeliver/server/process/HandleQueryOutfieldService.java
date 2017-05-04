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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/process/HandleQueryOutfieldService.java
 * 
 * FILE NAME        	: HandleQueryOutfieldService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.process;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;

/**
 * 
 * 外场相关共通类
 * 
 * @author ibm-wangfei
 * @date Feb 26, 2013 3:49:36 PM
 */
public class HandleQueryOutfieldService implements IHandleQueryOutfieldService{

	private static final Logger LOGGER = LoggerFactory.getLogger(HandleQueryOutfieldService.class);

	/**
	 * 库区服务类
	 */
	private IGoodsAreaService goodsAreaService;

	/**
	 * 开单部门
	 */
	private ISaleDepartmentService saleDepartmentService;

	/**
	 * 行政区域 Service接口
	 */
	IAdministrativeRegionsService administrativeRegionsService;

	/**
	 * 
	 * 获取最终库存部门Code和库区编码 参照WaybillStockService.getEndStockCodeAndAreaCode方法
	 * 
	 * @param orgCode 部门CODE
	 * @return List<String>list.get(0) = '最终库存部门Code';list.get(1)='库区编码'
	 * @author ibm-wangfei
	 * @date Feb 26, 2013 3:33:05 PM
	 */
	@Override
	public List<String> getEndStockCodeAndAreaCode(String orgCode) {
		List<String> list = new ArrayList<String>();
		if (StringUtil.isEmpty(orgCode)) {
			LOGGER.error("最终配载部门为空！");
			return null;
		}

		// 货区
		String goodsAreaCode = null;
		// 设置最终库存部门
		String endStockOrgCode = null;

		// 根据部门code，查询非偏线和空运的外场和货区
		SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(orgCode);
		// 是否驻地部门
		if (saleDepartment != null && saleDepartment.checkStation()) {
			endStockOrgCode = saleDepartment.getTransferCenter();
			goodsAreaCode = getGoodsAreaCode(endStockOrgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
		} else {
			endStockOrgCode = orgCode;// 如果不是驻地部门，那么最终库存部门是最终配载部门
		}

		list.add(endStockOrgCode);// 设置最终库存部门
		list.add(StringUtil.isEmpty(goodsAreaCode) ? NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE : goodsAreaCode);// 设置对应库区
		return list;
	}

	/**
	 * 获取库区编码
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-6 上午9:17:13
	 */
	private String getGoodsAreaCode(String transferCenter, String type) {

		List<GoodsAreaEntity> goodsAreas = goodsAreaService.queryGoodsAreaListByType(transferCenter, type);
		if (goodsAreas == null || goodsAreas.isEmpty()) {
			LOGGER.error("对应库区为空！");
		} else {

			GoodsAreaEntity goodsAreaEntity = goodsAreas.get(0);
			if (goodsAreaEntity.getGoodsAreaCode() == null) {
				LOGGER.error("对应库区编码为空！");
			} else {
				return goodsAreaEntity.getGoodsAreaCode();
			}
		}
		return null;
	}

	/**
	 * 
	 * 根据传入参数，获取完整的地址信息
	 * 
	 * @param provCode 省code
	 * @param cityCode 市code
	 * @param distCode 区code
	 * @param address 地址
	 * @return 完整地址
	 * @author ibm-wangfei
	 * @date Mar 7, 2013 9:53:28 AM
	 */
	@Override
	public String getCompleteAddress(String provCode, String cityCode,String distCode, String address) {
		StringBuffer sb = new StringBuffer();
		AdministrativeRegionsEntity entity;
		// 根据编码查询 -- 省
		if (StringUtil.isNotBlank(provCode)) {
			entity = administrativeRegionsService.queryAdministrativeRegionsByCode(provCode);
			if (entity != null && StringUtil.isNotBlank(entity.getName())) {
				sb.append(entity.getName());
				sb.append(NotifyCustomerConstants.SPLIT_CHAR_DASH);
			}
		}
		// 根据编码查询 -- 市
		if (StringUtil.isNotBlank(cityCode)) {
			entity = administrativeRegionsService.queryAdministrativeRegionsByCode(cityCode);
			if (entity != null && StringUtil.isNotBlank(entity.getName())) {
				sb.append(entity.getName());
				sb.append(NotifyCustomerConstants.SPLIT_CHAR_DASH);
			}
		}
		// 根据编码查询 -- 区
		if (StringUtil.isNotBlank(distCode)) {
			entity = administrativeRegionsService.queryAdministrativeRegionsByCode(distCode);
			if (entity != null && StringUtil.isNotBlank(entity.getName())) {
				sb.append(entity.getName());
				sb.append(NotifyCustomerConstants.SPLIT_CHAR_DASH);
			}
		}
		
		if (StringUtil.isNotBlank(sb.toString()) && sb.toString().length() > 0) {
			return StringUtil.isNotBlank(address) ? sb.append(address).toString() : sb.substring(0, sb.length() - 1);
		} else {
			return StringUtil.isNotBlank(address) ? address : "";
		}
	}
	
	@Override
	public String getCompleteAddress(String provCode, String cityCode,
			String distCode, String villageCode, String address) {
		StringBuffer sb = new StringBuffer();
		AdministrativeRegionsEntity entity;
		// 根据编码查询 -- 省
		if (StringUtil.isNotBlank(provCode)) {
			entity = administrativeRegionsService.queryAdministrativeRegionsByCode(provCode);
			if (entity != null && StringUtil.isNotBlank(entity.getName())) {
				sb.append(entity.getName());
				sb.append(NotifyCustomerConstants.SPLIT_CHAR_DASH);
			}
		}
		// 根据编码查询 -- 市
		if (StringUtil.isNotBlank(cityCode)) {
			entity = administrativeRegionsService.queryAdministrativeRegionsByCode(cityCode);
			if (entity != null && StringUtil.isNotBlank(entity.getName())) {
				sb.append(entity.getName());
				sb.append(NotifyCustomerConstants.SPLIT_CHAR_DASH);
			}
		}
		// 根据编码查询 -- 区
		if (StringUtil.isNotBlank(distCode)) {
			entity = administrativeRegionsService.queryAdministrativeRegionsByCode(distCode);
			if (entity != null && StringUtil.isNotBlank(entity.getName())) {
				sb.append(entity.getName());
				sb.append(NotifyCustomerConstants.SPLIT_CHAR_DASH);
			}
		}
		
		// 根据编码查询 -- 乡镇/街道
		if (StringUtil.isNotBlank(villageCode)) {
			entity = administrativeRegionsService.queryAdministrativeRegionsByCode(villageCode);
			if (entity != null) {
				sb.append(entity.getName());
				sb.append(NotifyCustomerConstants.SPLIT_CHAR_DASH);
			}
		}

		if (StringUtil.isNotBlank(sb.toString()) && sb.toString().length() > 0) {
			return StringUtil.isNotBlank(address) ? sb.append(address).toString() : sb.substring(0, sb.length() - 1);
		} else {
			return StringUtil.isNotBlank(address) ? address : "";
		}
	}

	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}

	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setAdministrativeRegionsService(IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	/**
	 * 
	 * 根据传入参数，获取完整的地址信息
	 * 
	 * @param provCode 省code
	 * @param cityCode 市code
	 * @param distCode 区code
	 * @param address 地址
	 * @param receiveCustomerAddress 地址备注
	 * @return 完整地址
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-11-14 16:49:58
	 */
	@Override
	public String getCompleteAddressAttachAddrNote(String receiveCustomerProvCode, String receiveCustomerCityCode,
			String receiveCustomerDistCode, String receiveCustomerAddress, String receiveCustomerAddressNote) {
		String address = this.getCompleteAddress(receiveCustomerProvCode, receiveCustomerCityCode, receiveCustomerDistCode, receiveCustomerAddress);
		return StringUtils.isNotEmpty(receiveCustomerAddressNote) ? address+"("+receiveCustomerAddressNote + ")" : address;
	}
	
	@Override
	public String getCompleteAddressAttachAddrNote(
			String receiveCustomerProvCode, String receiveCustomerCityCode,
			String receiveCustomerDistCode, String receiveCustomerVillageCode,
			String receiveCustomerAddress, String receiveCustomerAddressNote) {
		String address = this.getCompleteAddress(receiveCustomerProvCode, receiveCustomerCityCode, receiveCustomerDistCode, receiveCustomerVillageCode, receiveCustomerAddress);
		return StringUtils.isNotEmpty(receiveCustomerAddressNote) ? address+"("+receiveCustomerAddressNote + ")" : address;
	}

	
	/**
	 * 
	 * 获取最终库存部门Code和库区编码 参照WaybillStockService.getEndStockCodeAndAreaCode方法
	 * 
	 * @param orgCode 部门CODE
	 * @return List<String>list.get(0) = '最终库存部门Code';list.get(1)='库区编码'
	 * @author DMANA
	 * @date 
	 */
	@Override
	public List<String> getEndStockCodeAndExpressAreaCode(String orgCode) {
		List<String> list = new ArrayList<String>();
		if (StringUtil.isEmpty(orgCode)) {
			LOGGER.error("最终配载部门为空！");
			return null;
		}

		// 货区
		String goodsAreaCode = null;
		// 设置最终库存部门
		String endStockOrgCode = null;
		//快递驻地库区编号
		String expreGoodsAreaCode=null;
		// 根据部门code，查询非偏线和空运的外场和货区
		SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(orgCode);
		// 是否驻地部门
		if (saleDepartment != null && saleDepartment.checkStation()) {
			endStockOrgCode = saleDepartment.getTransferCenter();
			expreGoodsAreaCode= getGoodsAreaCode(endStockOrgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXPRESS_STATION);
			goodsAreaCode = getGoodsAreaCode(endStockOrgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
		} else {
			endStockOrgCode = orgCode;// 如果不是驻地部门，那么最终库存部门是最终配载部门
		}
		list.add(endStockOrgCode);// 设置最终库存部门
		list.add(StringUtil.isEmpty(goodsAreaCode) ? NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE : goodsAreaCode);// 设置对应库区
		list.add(StringUtil.isEmpty(expreGoodsAreaCode) ? NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE : expreGoodsAreaCode);// 设置对应库区


		return list;
	}
}