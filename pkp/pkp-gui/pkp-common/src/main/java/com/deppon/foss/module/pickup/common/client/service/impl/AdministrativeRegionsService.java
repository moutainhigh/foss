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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/AdministrativeRegionsService.java
 * 
 * FILE NAME        	: AdministrativeRegionsService.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.pickup.common.client.dao.IAdministrativeRegionsDao;
import com.deppon.foss.module.pickup.common.client.service.IAdministrativeRegionsService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.google.inject.Inject;

/**
 * 行政区域服务类
 * 
 * @author 105089-foss-yangtong
 * @date 2012-11-7 下午3:29:14
 */
public class AdministrativeRegionsService implements
		IAdministrativeRegionsService {

	@Inject
	private IAdministrativeRegionsDao administrativeRegionsDao;
	
	// 获得远程HessianRemoting接口
	private IWaybillHessianRemoting waybillRemotingService=DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);

	/**
	 * 
	 * 功能：插条记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void addAdministrativeRegions(
			AdministrativeRegionsEntity administrativeRegions) {
		administrativeRegionsDao
				.addAdministrativeRegions(administrativeRegions);

	}

	/**
	 * 
	 * 功能：更新条记录
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void updateAdministrativeRegions(
			AdministrativeRegionsEntity administrativeRegions) {
		administrativeRegionsDao
				.updateAdministrativeRegions(administrativeRegions);

	}

	/**
	 * 
	 * 功能：新增或更新记录
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void saveOrUpdate(AdministrativeRegionsEntity administrativeRegions) {
			if(!administrativeRegionsDao
					.addAdministrativeRegions(administrativeRegions)){
			administrativeRegionsDao
					.updateAdministrativeRegions(administrativeRegions);
		}
	}

	/**
	 * 精确查询 通过 CODE 查询
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-31 下午4:9:39
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsService#queryAdministrativeRegionsByCode(java.lang.String)
	 */
	@Override
	public AdministrativeRegionsEntity queryAdministrativeRegionsByCode(
			String code) {
		if (null == code) {
			return null;
		}
		//判断是否是在线登陆
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
			return waybillRemotingService.queryAdministrativeRegionsByCode(code);
							
		}else{			
			return administrativeRegionsDao.queryAdministrativeRegionsByCode(code);
		}
	}
	
	/**
	 * 
	 * <p>通过CODE查询新政区域</p> 
	 * @author foss-sunrui
	 * @date 2012-11-26 下午4:24:04
	 * @param code
	 * @return 
	 * @see com.deppon.foss.module.pickup.common.client.service.IAdministrativeRegionsService#queryAdministrativeRegionsNameByCode(java.lang.String)
	 */
	@Override
	public String queryAdministrativeRegionsNameByCode(String code) {
		AdministrativeRegionsEntity entity = this.queryAdministrativeRegionsByCode(code);
		if(entity!=null){
			return entity.getName();
		}else{
			return "";
		}
	}

	/**
	 * @param aministrativeRegions
	 */
	public void delete(AdministrativeRegionsEntity aministrativeRegions) {
		administrativeRegionsDao.delete(aministrativeRegions);
	}

	@Override
	public Map<String, String> queryAdministrativeRegionsByCodeActive(List<String> codes, String active) {
		List<AdministrativeRegionsEntity> list = administrativeRegionsDao.queryAdministrativeRegionsByCodeActive(codes, active);
		Map<String,String> map = new HashMap<String, String>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (AdministrativeRegionsEntity entity : list) {
				map.put(entity.getCode(), entity.getName());
			}
		}
		return map;
	}

}