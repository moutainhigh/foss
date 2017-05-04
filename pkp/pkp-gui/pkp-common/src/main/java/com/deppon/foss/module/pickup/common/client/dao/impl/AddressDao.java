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
package com.deppon.foss.module.pickup.common.client.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.pickup.common.client.dao.IAddressDao;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 行政区域数据提供类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:102246-foss-shaohongliang,date:2012-10-24 下午6:46:21,content: </p>
 * @author 102246-foss-shaohongliang
 * @date 2012-10-24 下午6:46:21
 * @since
 * @version
 */
public class AddressDao implements IAddressDao{
	
	private static final String NAMESPACE = "foss.pkp.AddressMapper.";
	
	private SqlSession sqlSession;

	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	/**
	 * 
	 * <p>获取所有国家</p> 
	 * @author 354805-taodongguo
	 * @date 2016-10-11 14:59:54
	 * @return
	 * @see
	 */
	@Override
	public List<AddressFieldDto> queryAllNations() {
		return sqlSession.selectList(NAMESPACE+"queryAllNations");
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
	public List<AddressFieldDto> queryAllProvinces() {
		return sqlSession.selectList(NAMESPACE+"queryAllProvinces");
	}
	
	/**
	 * 
	 * <p>获取所有国内省份</p> 
	 * @author 354805 - taodongguo
	 * @date 2016-10-11 15:00:25
	 * @return
	 * @see
	 */
	@Override
	public List<AddressFieldDto> queryAllChinaProvinces() {
		return sqlSession.selectList(NAMESPACE+"queryAllChinaProvinces");
	}
	
	/**
	 * 
	 * <p>获取所有城市</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午4:34:39
	 * @return
	 * @see
	 */
	@Override
	public List<AddressFieldDto> queryAllCitys() {
		return sqlSession.selectList(NAMESPACE+"queryAllCitys");
	}

	/**
	 * 
	 * <p>获取所有区域</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午4:34:39
	 * @return
	 * @see
	 */
	@Override
	public List<AddressFieldDto> queryAllCountys() {
		return sqlSession.selectList(NAMESPACE+"queryAllCountys");
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
	public List<AddressFieldDto> queryHotCitys() {
		return sqlSession.selectList(NAMESPACE+"queryHotCitys");
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
	@Override
	public List<AddressFieldDto> queryCountysByCityId(String cityId) {
		return sqlSession.selectList(NAMESPACE+"queryCountysByCityId", cityId);
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
	public List<AddressFieldDto> queryCitysByProvinceId(String provinceId) {
		return sqlSession.selectList(NAMESPACE+"queryCitysByProvinceId", provinceId);
	}
	
	/**
	 * 
	 * <p>根据国家查询省份</p> 
	 * @author 354805-taodongguo
	 * @date 2016-9-27 08:19:54
	 * @param nationId
	 * @return
	 * @see
	 */
	@Override
	public List<AddressFieldDto> queryProvincesByNationId(String nationId) {
		return sqlSession.selectList(NAMESPACE+"queryProvincesByNationId", nationId);
	}

	/**
	 * 
	 * 根据国家Code查询名称
	 * @author 354805-foss-taodongguo
	 * @date 2016-10-15 17:27:10
	 */
	@Override
	public String queryNationByCode(String code) {
		List<String> list = sqlSession.selectList(NAMESPACE+"queryNationByCode", code);
		
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
	@Override
	public String queryProviceByCode(String code) {
		List<String> list = sqlSession.selectList(NAMESPACE+"queryProviceByCode", code);
		
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
	@Override
	public String queryCityByCode(String code) {
		List<String> list =  sqlSession.selectList(NAMESPACE+"queryCityByCode", code);
		
		
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
	@Override
	public String queryCountyByCode(String code) {
		List<String> list =   sqlSession.selectList(NAMESPACE+"queryCountyByCode", code);
		
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
	@Override
	public AddressFieldDto queryCityByName(String name){
		Map<String,Object> args = new HashMap<String, Object>();
		args.put("name", name);
		args.put("active", FossConstants.YES);
		List<AddressFieldDto> list = sqlSession.selectList(NAMESPACE+"queryCitysByName", args);
		//若查询出多个值，则不返回数据
		if(CollectionUtils.isNotEmpty(list)){
			if(list.size() == 1){
				return list.get(0);
			}
		}
		return null;
	}
	
	/**
	 * 根据省份code查询国家信息
	 */
	@Override
	public AddressFieldDto queryNationByProvinceCode(String provCode) {
		List<AddressFieldDto> list = sqlSession.selectList(NAMESPACE+"queryNationByProvinceCode", provCode);
		//若查询出多个值，则不返回数据
		if(CollectionUtils.isNotEmpty(list) && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据区域ID查询 区下面的乡镇(街道)
	 * @author 218459-foss-dongsiwei
	 */
	@Override
	public List<AddressFieldDto> queryCountyListByCountyId(String countyId) {
		return sqlSession.selectList(NAMESPACE+"queryCountyListByCountyId", countyId);
	}
}