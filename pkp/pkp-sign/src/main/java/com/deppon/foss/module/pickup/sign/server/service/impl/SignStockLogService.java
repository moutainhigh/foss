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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/SignService.java
 * 
 * FILE NAME        	: SignService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 *****************************************************************************
 */
package com.deppon.foss.module.pickup.sign.server.service.impl;


import com.deppon.foss.module.pickup.sign.api.server.dao.ISignStockLogDao;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignStockLogService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockLogEntity;
/**
 * 
 *签收反签收同步改异步库存日志接口实现 
 * @author 
 *		foss-meiying
 * @date 
 *      2013-3-22 下午4:19:11
 * @since
 * @version
 */
public class SignStockLogService implements ISignStockLogService {
	private ISignStockLogDao signStockLogDao;
	/**
	 * 添加一条记录
	 * @author foss-meiying
	 * @date 2013-3-22 下午4:37:01
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.ISignStockLogService#add(com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockLogEntity)
	 */
	@Override
	public int add(SignStockLogEntity record) {
		return signStockLogDao.insert(record);
	}
	/**
	 * 有选择性的添加数据
	 * @author foss-meiying
	 * @date 2013-3-22 下午4:37:07
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.ISignStockLogService#addSelective(com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockLogEntity)
	 */
	@Override
	public int addSelective(SignStockLogEntity record) {
		return signStockLogDao.insertSelective(record);
	}
	public void setSignStockLogDao(ISignStockLogDao signStockLogDao) {
		this.signStockLogDao = signStockLogDao;
	}

}