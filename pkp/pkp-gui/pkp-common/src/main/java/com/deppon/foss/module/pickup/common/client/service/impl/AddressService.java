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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/AddressService.java
 * 
 * FILE NAME        	: AddressService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.pickup.common.client.dao.IAddressDao;
import com.deppon.foss.module.pickup.common.client.service.IAddressService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IAddressServiceHessianRemoting;
import com.google.inject.Inject;

/**
 * 
 * 行政区域实现服务类 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:102246-foss-shaohongliang,date:2012-10-24 下午6:42:55, </p>
 * @author 102246-foss-shaohongliang
 * @date 2012-10-24 下午6:42:55
 * @since
 * @version
 */
public class AddressService implements IAddressService{
	
	@Inject
	private IAddressDao addressDao;
	
	//zxy 20140428 DEFECT-2740 MANA-2018 远程地址服务
	private IAddressServiceHessianRemoting onlineAddressServiceHessianRemoting = DefaultRemoteServiceFactory.getService(IAddressServiceHessianRemoting.class);
	
	/**
	 * 
	 * <p>根据城市查询区域</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午6:41:41
	 * @param cityId
	 * @return
	 * @see
	 */
	@Override
	public List<AddressFieldDto> queryCountyListByCityId(String cityId) {
		return addressDao.queryCountysByCityId(cityId);
	}

	/**
	 * 
	 * <p>根据省份查询城市</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午6:41:45
	 * @param provinceId
	 * @return
	 * @see
	 */
	@Override
	public List<AddressFieldDto> queryCityListByProvinceId(String provinceId) {
		return addressDao.queryCitysByProvinceId(provinceId);
	}
	
	/**
	 * 
	 * <p>根据国家查询省份</p> 
	 * @author 354805-taodongguo
	 * @date 2016-10-11 15:04:06
	 * @param nationId
	 * @return
	 * @see
	 */
	@Override
	public List<AddressFieldDto> queryProvinceListByNationId(String nationId) {
		return addressDao.queryProvincesByNationId(nationId);
	}
	
	/**
	 * 
	 * <p>获取可带拼音的省份、城市、区域</p> 
	 * @author 102246-foss-shaohongliang
	 * @author 354805-taodongguo
	 * @date 2012-10-24 下午4:31:05-2016年10月11日15:08:28
	 * @return
	 * @see
	 */
	@Override
	public List<AddressFieldDto> queryPinyinDistrictList() {
		List<AddressFieldDto> countyList = addressDao.queryAllCountys();
		List<AddressFieldDto> cityList = addressDao.queryAllCitys();
		List<AddressFieldDto> provinceList = addressDao.queryAllChinaProvinces();
		List<AddressFieldDto> list = new ArrayList<AddressFieldDto>();
		list.addAll(countyList);
		list.addAll(cityList);
		list.addAll(provinceList);
		return list;
	}

	/**
	 * 
	 * <p>获取热门城市</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午4:34:21
	 * @return
	 * @see
	 */
	@Override
	public List<AddressFieldDto> queryHotCityList() {
		return addressDao.queryHotCitys();
	}
	
	/**
	 * 
	 * <p>获取所有省份</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午4:34:39
	 * @return
	 * @see
	 */
	@Override
	public List<AddressFieldDto> queryProvinceList() {
		return addressDao.queryAllProvinces();
	}
	
	/**
	 * 
	 * <p>获取所有国内省份</p> 
	 * @author 354805-taodongguo
	 * @date 2016-10-11 15:09:33
	 * @return
	 * @see
	 */
	@Override
	public List<AddressFieldDto> queryChinaProvinceList() {
		return addressDao.queryAllChinaProvinces();
	}
	
	/**
	 * 
	 * <p>获取所有国家</p> 
	 * @author 354805-taodongguo
	 * @date 2016-10-11 15:09:10
	 * @return
	 * @see
	 */
	@Override
	public List<AddressFieldDto> queryNationList() {
		return addressDao.queryAllNations();
	}

	/**
	 * 
	 * 根据Code查询国家名称
	 * @author 354805-foss-taodongguo
	 * @date 2016-10-15 17:14:30
	 */
	@Override
	public String queryNationByCode(String nationCode) {
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
			return onlineAddressServiceHessianRemoting.queryNationByCode(nationCode);
		}else{
			return addressDao.queryNationByCode(nationCode);
		}
	}
	
	/**
	 * 
	 * 根据省份Code查询名称
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午7:58:00
	 */
	@Override
	public String queryProviceByCode(String provCode) {
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
			return onlineAddressServiceHessianRemoting.queryProviceByCode(provCode);
		}else{
			return addressDao.queryProviceByCode(provCode);
		}
	}

	/**
	 * 
	 * 根据城市Code查询名称
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午7:58:00
	 * @update 20140428 zxy DEFECT-2740 MANA-2018 在线读取远程服务数据 
	 */
	@Override
	public String queryCityByCode(String cityCode) {
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
			return onlineAddressServiceHessianRemoting.queryCityByCode(cityCode);
		}else{
			return addressDao.queryCityByCode(cityCode);
		}
	}

	/**
	 * 
	 * 根据省份区域查询名称
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午7:58:00
	 */
	@Override
	public String queryCountyByCode(String distCode) {
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
			return onlineAddressServiceHessianRemoting.queryCountyByCode(distCode);
		}else{
			return addressDao.queryCountyByCode(distCode);
		}
	}
	
	/**
	 * 根据名字来查询城市名称
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-29 上午11:00:56
	 * @update 20140428 zxy DEFECT-2741 MANA-2018 在线读取远程服务数据 
	 */
	@Override
	public AddressFieldDto queryCityByName(String name){
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
			return onlineAddressServiceHessianRemoting.queryCityByName(name);
		}else{
			return addressDao.queryCityByName(name);
		}
	}
	
	/**
	 * 根据省份code查询国家信息
	 * @author 354805-foss-taodongguo
	 * @date 2016-10-25 09:54:25
	 */
	@Override
	public AddressFieldDto queryNationByProviceCode(String provCode) {
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
			return onlineAddressServiceHessianRemoting.queryNationByProvinceCode(provCode);
		}else{
			return addressDao.queryNationByProvinceCode(provCode);
		}
	}
	
	/**
	 * 根据区域ID 查询区下面所属的 乡镇(街道)
	 * @author 218459-foss-dongsiwei
	 */
	@Override
	public List<AddressFieldDto> queryCountyListByCountyId(String countyId) {
		return addressDao.queryCountyListByCountyId(countyId);
	}

}