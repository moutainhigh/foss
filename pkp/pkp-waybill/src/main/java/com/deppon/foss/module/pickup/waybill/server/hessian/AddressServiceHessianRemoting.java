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
package com.deppon.foss.module.pickup.waybill.server.hessian;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.deppon.foss.framework.server.deploy.hessian.annotation.Remote;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IOnlineAddressDao;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IAddressServiceHessianRemoting;

/**
 * 行政区域实现服务类 (在线)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2014-3-4 上午11:12:31,content:TODO </p>
 * @author 094463-foss-xieyantao
 * @date 2014-3-4 上午11:12:31
 * @since
 * @version
 */
@Remote
public class AddressServiceHessianRemoting implements IAddressServiceHessianRemoting {
	
	/**
	 * 行政区域数据提供接口
	 */
	@Resource
	private IOnlineAddressDao onlineAddressDao;

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
		return onlineAddressDao.queryCountysByCityId(cityId);
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
		return onlineAddressDao.queryCitysByProvinceId(provinceId);
	}
	
	/**
	 * 
	 * <p>根据国家查询省份</p> 
	 * @author 354805
	 * @date 2016-10-11 15:33:21
	 * @param nationId
	 * @return
	 * @see
	 */
	@Override
	public List<AddressFieldDto> queryProvinceListByNationId(String nationId) {
		return onlineAddressDao.queryProvincesByNationId(nationId);
	}
	
	/**
	 * 
	 * <p>获取可带拼音的城市、区域</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午4:31:05
	 * @return
	 * @see
	 */
	@Override
	public List<AddressFieldDto> queryPinyinDistrictList() {
		List<AddressFieldDto> countyList = onlineAddressDao.queryAllCountys();
		List<AddressFieldDto> cityList = onlineAddressDao.queryAllCitys();
		List<AddressFieldDto> provinceList = onlineAddressDao.queryAllProvinces();
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
		return onlineAddressDao.queryHotCitys();
	}
	
	/**
	 * 
	 * <p>获取所有国家</p> 
	 * @author 354805-taodongguo
	 * @date 2016-10-11 15:35:15
	 * @return
	 * @see
	 */
	@Override
	public List<AddressFieldDto> queryNationList() {
		return onlineAddressDao.queryAllNations();
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
		return onlineAddressDao.queryAllProvinces();
	}

	/**
	 * 
	 * <p>获取所有国内省份</p> 
	 * @author 354805-taodongguo
	 * @date 2016-10-11 15:35:51
	 * @return
	 * @see
	 */
	@Override
	public List<AddressFieldDto> queryChinaProvinceList() {
		return onlineAddressDao.queryAllChinaProvinces();
	}
	
	/**
	 * 
	 * 根据国家Code查询名称
	 * @author 354805-foss-taodongguo
	 * @date 2016-10-15 17:16:03
	 */
	@Override
	public String queryNationByCode(String nationCode) {
		return onlineAddressDao.queryNationByCode(nationCode);
	}
	
	/**
	 * 
	 * 根据省份Code查询名称
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午7:58:00
	 */
	@Override
	public String queryProviceByCode(String provCode) {
		return onlineAddressDao.queryProviceByCode(provCode);
	}

	/**
	 * 
	 * 根据城市Code查询名称
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午7:58:00
	 */
	@Override
	public String queryCityByCode(String cityCode) {
		return onlineAddressDao.queryCityByCode(cityCode);
	}

	/**
	 * 
	 * 根据省份区域查询名称
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午7:58:00
	 */
	@Override
	public String queryCountyByCode(String distCode) {
		return onlineAddressDao.queryCountyByCode(distCode);
	}
	
	/**
	 * 根据名字来查询城市名称
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-29 上午11:00:56
	 */
	@Override
	public AddressFieldDto queryCityByName(String name){
		return onlineAddressDao.queryCityByName(name);
	}

	/**
	 * 
	 * 根据省份区域查询省份数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-10 09:05:41
	 */
	@Override
	public AddressFieldDto onlineQueryProviceByName(String provName) {
		return onlineAddressDao.onlineQueryProviceByName(provName);
	}

	/**
	 * 
	 * 根据省份区域查询城市数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-10 09:05:41
	 */
	@Override
	public AddressFieldDto onlineQueryCityByName(String cityName) {
		return onlineAddressDao.onlineQueryCityByName(cityName);
	}

	/**
	 * 
	 * 根据省份区域查询区域数据，与上面的一个方法一样，只是为了好看而已
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-10 09:05:41
	 */
	@Override
	public AddressFieldDto onlineQueryCountyByName(String distName) {
		return onlineAddressDao.onlineQueryCountyByName(distName);
	}

	/**
	 * 根据区域ID查询 区下面所属的乡镇(街道)
	 * @author 218459-foss-dongsiwei
	 */
	@Override
	public List<AddressFieldDto> queryCountyListByCountyId(String countyId) {
		return onlineAddressDao.queryCountyListByCountyId(countyId);
	}

	/**
	 * 根据省份code查询国家信息
	 * @author 354805-taodongguo
	 * @date 2016-10-19 10:08:30
	 * @param provinceCode
	 * @return
	 */
	@Override
	public AddressFieldDto queryNationByProvinceCode(String provinceCode) {
		return onlineAddressDao.queryNationByProvinceCode(provinceCode);
	}
}