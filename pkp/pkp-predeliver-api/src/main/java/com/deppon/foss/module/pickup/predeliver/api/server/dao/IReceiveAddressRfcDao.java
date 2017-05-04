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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/dao/IReceiveAddressRfcDao.java
 * 
 * FILE NAME        	: IReceiveAddressRfcDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ReceiveAddressRfcEntity;

/**
 * 
 * 运单地址临时表DAO
 * @author ibm-wangfei
 * @date Oct 19, 2012 3:21:18 PM
 */
public interface IReceiveAddressRfcDao {

	/**
	 * 更新
	 * @author ibm-wangfei
	 * @date Nov 30, 2012 3:37:04 PM
	 */
	int updateReceiveAddressRfcEntity(ReceiveAddressRfcEntity receiveAddressRfcEntity);

	/**
	 * 
	 * 删除
	 * @author ibm-wangfei
	 * @date Nov 30, 2012 3:37:19 PM
	 */
	int deleteReceiveAddressRfcEntity(ReceiveAddressRfcEntity receiveAddressRfcEntity);

	/**
	 * 
	 * 查询
	 * @author ibm-wangfei
	 * @date Nov 30, 2012 4:08:47 PM
	 */
	List<ReceiveAddressRfcEntity> queryReceiveAddressRfc(ReceiveAddressRfcEntity receiveAddressRfcEntity);

	/**
	 * 
	 * 查询count
	 * @author ibm-wangfei
	 * @date Dec 3, 2012 6:29:17 PM
	 */
	Long queryReceiveAddressRfcCount(ReceiveAddressRfcEntity receiveAddressRfcEntity);

	/**
	 * 
	 * 新增
	 * 
	 * @param receiveAddressRfcEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 2:42:12 PM
	 */
	int addReceiveAddressRfcEntity(ReceiveAddressRfcEntity receiveAddressRfcEntity);

	/**
	 * 
	 * 查询集中送货小区
	 * @param scopeCoordinatesId
	 * @return
	 * @author ibm-wangfei
	 * @date May 29, 2013 6:45:29 PM
	 */
	String queryRegionCodeForGisId(String gisid);
}