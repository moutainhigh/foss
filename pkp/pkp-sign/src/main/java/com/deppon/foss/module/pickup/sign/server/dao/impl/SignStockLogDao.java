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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/StayHandoverDao.java
 * 
 * FILE NAME        	: StayHandoverDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;


import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignStockLogDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockLogEntity;
import com.deppon.foss.util.UUIDUtils;
/**
 * 签收反签收同步改异步库存日志接口实现
 *
 * @author 
 *		foss-meiying
 * @date 
 *      2013-3-22 下午4:22:07
 * @since
 * @version
 */
public class SignStockLogDao extends iBatis3DaoImpl implements ISignStockLogDao {
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockLogEntity.";
	/**
	 * 添加一条记录
	 * @author foss-meiying
	 * @date 2013-3-22 下午4:30:58
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.ISignStockLogDao
	 * #insert(com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockLogEntity)
	 */
	@Override
	public int insert(SignStockLogEntity record) {
		//主键id
		record.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE+"insert",record);
	}
	/**
	 * 有选择性的添加数据
	 * @author foss-meiying
	 * @date 2013-3-22 下午4:31:05
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.ISignStockLogDao
	 * #insertSelective(com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockLogEntity)
	 */
	@Override
	public int insertSelective(SignStockLogEntity record) {
		//主键id
		record.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE+"insertSelective",record);
	}
	
	
}