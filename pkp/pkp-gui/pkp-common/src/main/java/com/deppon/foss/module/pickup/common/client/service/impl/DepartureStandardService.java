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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/DepartureStandardService.java
 * 
 * FILE NAME        	: DepartureStandardService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service.impl;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;
import com.deppon.foss.module.pickup.common.client.dao.IDepartureStandardDao;
import com.deppon.foss.module.pickup.common.client.service.IDepartureStandardService;
import com.google.inject.Inject;

/**
 * 获取发出信息
 * @author 026123-foss-lifengteng
 *
 */
public class DepartureStandardService  implements IDepartureStandardService {

	@Inject
	IDepartureStandardDao departureStandardDao;
	
	
	public void setDepartureStandardDao(IDepartureStandardDao departureStandardDao) {
		this.departureStandardDao = departureStandardDao;
	}


	/**
	 * 新增或更新记录
	 */
	public void saveOrUpdate(DepartureStandardEntity departureStandard) {
		if(!departureStandardDao.addDepartureStandard(departureStandard)){
			departureStandardDao.updateDepartureStandard(departureStandard); 
		}
	}
}