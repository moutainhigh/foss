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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/FreightRouteLineService.java
 * 
 * FILE NAME        	: FreightRouteLineService.java
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

import java.util.List;

import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity;
import com.deppon.foss.module.pickup.common.client.dao.IFreightRouteLineDao;
import com.deppon.foss.module.pickup.common.client.service.IFreightRouteLineService;
import com.google.inject.Inject;


/**
 * 走货路径线路
 * @author 105089-foss-yangtong
 * @date 2012-10-24 下午3:52:25
 */
public class FreightRouteLineService implements IFreightRouteLineService{

	@Inject
	IFreightRouteLineDao freightRouteLineDao;
	
	/**
	 * 
	 * 功能：按id查询
	 * @param:
	 * @return Department
	 * @since:1.6
	 */
	@Override
	public FreightRouteLineEntity queryById(String id) {
		
		return freightRouteLineDao.queryById(id);
	}
	
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void addFreightRouteLine(FreightRouteLineEntity freightRouteLine) {
		freightRouteLineDao.addFreightRouteLine(freightRouteLine);

	}
	
	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void updateFreightRouteLine(FreightRouteLineEntity freightRouteLine) {
		freightRouteLineDao.updateFreightRouteLine(freightRouteLine); 

	}
	
	/**
	 * 
	 * 功能：新增或更新记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void saveOrUpdate(FreightRouteLineEntity freightRouteLine) {
		if(!freightRouteLineDao.addFreightRouteLine(freightRouteLine)){
			freightRouteLineDao.updateFreightRouteLine(freightRouteLine); 
		}
	}
	
	/**
	 * 功能：查询记录
	 * @param:
	 * @return List<Department>
	 * @since:1.6
	 */
	@Transactional
	@Override
	public List<FreightRouteLineEntity> queryAll() {
		
		return freightRouteLineDao.queryAll();
	}
	@Override
	public List<FreightRouteLineEntity> queryFreightRouteLineListByFreightRoute(
			String freightRouteVirtualCode) {
		FreightRouteLineEntity c = new FreightRouteLineEntity();
		c.setFreightRouteVirtualCode(freightRouteVirtualCode);
		return freightRouteLineDao.queryFreightRouteLineListByFreightRoute(c);
	}

	/**
	 * 删除
	 * @param freightRouteLineEntity
	 */
	public void delete(FreightRouteLineEntity freightRouteLineEntity) {
		freightRouteLineDao.delete(freightRouteLineEntity);
		
	}
}