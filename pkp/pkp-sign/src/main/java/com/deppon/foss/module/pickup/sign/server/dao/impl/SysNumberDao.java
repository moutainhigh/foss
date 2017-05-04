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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/SysNumberDao.java
 * 
 * FILE NAME        	: SysNumberDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISysNumberDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SysNumberEntity;

/**
 * <p>
 * 获得流水号<br />
 * </p>
 * 
 * @title SysNumberDao.java
 * @package com.deppon.foss.module.pickup.sign.server.dao.impl
 * @author ibm-lizhiguo
 * @version 0.1 2012-12-4
 */
public class SysNumberDao extends iBatis3DaoImpl implements ISysNumberDao {
	// 流水号的NAMESPACE
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.domain.SysNumberEntity.";
	// 获得流水号
	private static final String GETNEXTID = "getNextID";
	// 插入流水号key
	private static final String INSERTSYSNUMBER = "insertSysNumber";
	// 更新流水号
	private static final String UPDATESYSNUMBER = "updateSysNumber";

	/**
	 * 
	 * <p>获得流水号<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-4
	 * @param key
	 * @return
	 * Long
	 */
	@Override
	public Long getNextID(String key) {
		return (Long) this.getSqlSession()
				.selectOne(NAMESPACE + GETNEXTID, key);
	}

	/**
	 * 
	 * <p>插入要生成流水好的CODE<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-4
	 * @param entity
	 * @return
	 * Object
	 */
	@Override
	public int insertSysNumber(SysNumberEntity entity) {
		return getSqlSession().insert(NAMESPACE + INSERTSYSNUMBER, entity);
	}

	/**
	 * 
	 * <p>更新流水号<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-4
	 * @param key
	 * @return
	 * int
	 */
	@Override
	public int updateSysNumber(String key) {
		return getSqlSession().update(NAMESPACE + UPDATESYSNUMBER, key);
	}
}