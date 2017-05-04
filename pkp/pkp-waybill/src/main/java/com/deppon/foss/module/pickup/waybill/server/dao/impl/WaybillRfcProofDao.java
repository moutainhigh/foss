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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/WaybillRfcProofDao.java
 * 
 * FILE NAME        	: WaybillRfcProofDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcProofDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcProofEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * 变更凭证DAO实现
 * @author 102246-foss-shaohongliang
 * @date 2012-12-24 下午6:08:42
 */
public class WaybillRfcProofDao extends iBatis3DaoImpl implements IWaybillRfcProofDao {

	private static final String NAMESPACE = "foss.pkp.WaybillRfcProofEntityMapper.";

	/**
	 * 
	 * 添加凭证
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午6:09:28
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcProofDao#addRfcProofEntity(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcProofEntity)
	 */
	@Override
	public void addRfcProofEntity(WaybillRfcProofEntity entity) {
		// 设置UUID
		entity.setId(UUIDUtils.getUUID());
	    getSqlSession().insert(
			NAMESPACE + "insertSelective", entity);
	}

}