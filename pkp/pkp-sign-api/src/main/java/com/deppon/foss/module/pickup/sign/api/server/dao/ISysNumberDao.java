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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/dao/ISysNumberDao.java
 * 
 * FILE NAME        	: ISysNumberDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.dao;

import com.deppon.foss.module.pickup.sign.api.shared.domain.SysNumberEntity;

/**   
 * <p>流水号生成接口<br />
 * </p>
 * @title ISysNumberDao.java
 * @package com.deppon.foss.module.pickup.sign.api.server.dao 
 * @author ibm-lizhiguo
 * @version 0.1 2012-12-4
 */
public interface ISysNumberDao {

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
	Long getNextID(String key);

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
	int insertSysNumber(SysNumberEntity entity);

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
	int updateSysNumber(String key);

}