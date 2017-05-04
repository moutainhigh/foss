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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/SysNumberService.java
 * 
 * FILE NAME        	: SysNumberService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.service.impl;

import com.deppon.foss.module.pickup.sign.api.server.dao.ISysNumberDao;
import com.deppon.foss.module.pickup.sign.api.server.service.ISysNumberService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SysNumberEntity;

/**   
 * <p>提供序列的服务<br />
 * </p>
 * @title SysNumberService.java
 * @package com.deppon.foss.module.pickup.sign.server.service.impl 
 * @author ibm-lizhiguo
 * @version 0.1 2012-12-4
 */
public class SysNumberService implements ISysNumberService{
	//序列生成DAO
	private ISysNumberDao sysNumberDao;

	/**
	 * set ISysNumberDao
	 * @param sysNumberDao
	 */
	public void setSysNumberDao(ISysNumberDao sysNumberDao) {
		this.sysNumberDao = sysNumberDao;
	}
	
	/**
	 * 
	 * <p>获得序列号<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-4
	 * @param key
	 * @return
	 * Integer
	 */
	@Override
	public Long getNextID(String key) {
		return sysNumberDao.getNextID(key);
	}

	/**
	 * 
	 * <p>插入需要获得序列号的数据<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-4
	 * @param entity
	 * @return
	 * int
	 */
	@Override
	public int insertSysNumber(SysNumberEntity entity) {
		return sysNumberDao.insertSysNumber(entity);
	}

	/**
	 * 
	 * <p>更新序列号<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-4
	 * @param key
	 * @return
	 * int
	 */
	@Override
	public int updateSysNumber(String key) {
		return sysNumberDao.updateSysNumber(key);
	}
}