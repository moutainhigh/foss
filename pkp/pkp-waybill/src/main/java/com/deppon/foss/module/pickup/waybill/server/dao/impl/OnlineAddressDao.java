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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/AddressDao.java
 * 
 * FILE NAME        	: AddressDao.java
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
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IOnlineAddressDao;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 行政区域数据提供类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2014-3-4 下午2:18:57,content:TODO </p>
 * @author 094463-foss-xieyantao
 * @date 2014-3-4 下午2:18:57
 * @since
 * @version
 */
public class OnlineAddressDao extends iBatis3DaoImpl implements IOnlineAddressDao{
	
	private static final String NAMESPACE = "foss.pkp.OnlineAddressMapper.";
	
	/**
	 * 
	 * <p>获取所有国家</p> 
	 * @author 354805-taodongguo
	 * @date 2016-10-11 15:26:09
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AddressFieldDto> queryAllNations() {
		return this.getSqlSession().selectList(NAMESPACE+"queryAllNations");
	}
	
	/**
	 * 
	 * <p>获取所有省份</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午4:34:39
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AddressFieldDto> queryAllProvinces() {
		return this.getSqlSession().selectList(NAMESPACE+"queryAllProvinces");
	}
	
	/**
	 * 
	 * <p>获取所有国内省份</p> 
	 * @author 354805-taodongguo
	 * @date 2016-10-11 15:26:30
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AddressFieldDto> queryAllChinaProvinces() {
		return this.getSqlSession().selectList(NAMESPACE+"queryAllChinaProvinces");
	}
	
	/**
	 * 
	 * <p>获取所有城市</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午4:34:39
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AddressFieldDto> queryAllCitys() {
		return this.getSqlSession().selectList(NAMESPACE+"queryAllCitys");
	}

	/**
	 * 
	 * <p>获取所有区域</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午4:34:39
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AddressFieldDto> queryAllCountys() {
		return this.getSqlSession().selectList(NAMESPACE+"queryAllCountys");
	}

	/**
	 * 
	 * <p>获取热门城市</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午4:34:21
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AddressFieldDto> queryHotCitys() {
		return this.getSqlSession().selectList(NAMESPACE+"queryHotCitys");
	}

	/**
	 * 
	 * <p>根据城市查询区域</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午6:41:41
	 * @param cityId
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AddressFieldDto> queryCountysByCityId(String cityId) {
		return this.getSqlSession().selectList(NAMESPACE+"queryCountysByCityId", cityId);
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
	@SuppressWarnings("unchecked")
	@Override
	public List<AddressFieldDto> queryCitysByProvinceId(String provinceId) {
		return this.getSqlSession().selectList(NAMESPACE+"queryCitysByProvinceId", provinceId);
	}

	/**
	 * 
	 * <p>根据国家查询省份</p> 
	 * @author 354805-taodongguo
	 * @date 2016-10-11 15:26:51
	 * @param nationId
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AddressFieldDto> queryProvincesByNationId(String nationId) {
		return this.getSqlSession().selectList(NAMESPACE+"queryProvincesByNationId", nationId);
	}

	/**
	 * 
	 * 根据国家Code查询名称
	 * @author 354805-foss-taodongguo
	 * @date 2016-10-15 17:17:47
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryNationByCode(String code) {
		List<String> list = this.getSqlSession().selectList(NAMESPACE+"queryNationByCode", code);
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return "";
		}
	}
	
	/**
	 * 
	 * 根据省份Code查询名称
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午7:58:00
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryProviceByCode(String code) {
		List<String> list = this.getSqlSession().selectList(NAMESPACE+"queryProviceByCode", code);
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return "";
		}
	}

	/**
	 * 
	 * 根据城市Code查询名称
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午7:58:00
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryCityByCode(String code) {
		List<String> list =  this.getSqlSession().selectList(NAMESPACE+"queryCityByCode", code);
		
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return "";
		}
	}


	/**
	 * 
	 * 根据省份区域查询名称
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午7:58:00
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryCountyByCode(String code) {
		List<String> list =   this.getSqlSession().selectList(NAMESPACE+"queryCountyByCode", code);
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return "";
		}
	}
	
	/**
	 * 根据名字来查询城市名称
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-29 上午11:00:56
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AddressFieldDto queryCityByName(String name){
		Map<String,Object> args = new HashMap<String, Object>();
		args.put("name", name);
		args.put("active", FossConstants.YES);
		List<AddressFieldDto> list = this.getSqlSession().selectList(NAMESPACE+"queryCitysByName", args);
		//若查询出多个值，则不返回数据
		if(CollectionUtils.isNotEmpty(list)){
			if(list.size() == 1){
				return list.get(0);
			}
		}
		return null;
	}

	/**
	 * 
	 * 根据省份区域查询省份数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-10 09:05:41
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AddressFieldDto onlineQueryProviceByName(String provName) {
		Map<String,Object> args = new HashMap<String, Object>();
		args.put("name", provName);
		args.put("active", FossConstants.YES);
		List<AddressFieldDto> list = this.getSqlSession().selectList(NAMESPACE+"onlineQueryProviceByName", args);
		//若查询出多个值，则不返回数据
		if(CollectionUtils.isNotEmpty(list)){
			if(list.size() > 0){
				return list.get(0);
			}
		}
		return null;
	}

	/**
	 * 
	 * 根据省份区域查询城市数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-10 09:05:41
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AddressFieldDto onlineQueryCityByName(String cityName) {
		Map<String,Object> args = new HashMap<String, Object>();
		args.put("name", cityName);
		args.put("active", FossConstants.YES);
		List<AddressFieldDto> list = this.getSqlSession().selectList(NAMESPACE+"queryCitysByName", args);
		//若查询出多个值，则不返回数据
		if(CollectionUtils.isNotEmpty(list)){
			if(list.size() > 0){
				return list.get(0);
			}
		}
		return null;
	}

	/**
	 * 
	 * 根据省份区域查询区域数据，与上面的一个方法一样，只是为了好看而已
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-10 09:05:41
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AddressFieldDto onlineQueryCountyByName(String distName) {
		Map<String,Object> args = new HashMap<String, Object>();
		args.put("name", distName);
		args.put("active", FossConstants.YES);
		List<AddressFieldDto> list = this.getSqlSession().selectList(NAMESPACE+"onlineQueryCountyByName", args);
		//若查询出多个值，则不返回数据
		if(CollectionUtils.isNotEmpty(list)){
			if(list.size() > 0){
				return list.get(0);
			}
		}
		return null;
	}

	/**
	 * 根据区域ID 查询区域下面的乡镇信息
	 * @author 218459-foss-dongsiwei
	 */
	@Override
	public List<AddressFieldDto> queryCountyListByCountyId(String countyId) {
		 return this.getSqlSession().selectList(NAMESPACE+"queryCountyListByCountyId", countyId);
	}
	
	/**
	 * 根据省份code查询国家信息
	 * @author 354805-taodongguo
	 * @date 2016-10-19 10:10:10
	 * @param provinceCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AddressFieldDto queryNationByProvinceCode(String provinceCode) {
		List<AddressFieldDto> list = this.getSqlSession().selectList(NAMESPACE+"queryNationByProvinceCode", provinceCode);
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}