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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/SignRfcChangeDetailDao.java
 * 
 * FILE NAME        	: SignRfcChangeDetailDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignRfcChangeDetailDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcChangeDetailEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 变更签收明细操作
 * @author ibm-lizhiguo
 * @date 2012-11-16 下午3:03:05
 */
public class SignRfcChangeDetailDao extends iBatis3DaoImpl implements ISignRfcChangeDetailDao {
	//命名空间
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcChangeDetailEntity.";
	
	//变更明细LIST
	private static final String SIGNRFCCHANGEDETAILLIST = "selectSignRfcChangeDetailBySignRfcId";
	
	//变更明细LIST1
	private static final String SIGNRFCCHANGEDETAILLIST1 = "selectSignRfcChangeDetailBySignRfcIdRfcType";
	
	//添加变更明细
	private static final String INSERTSIGNRFCCHANGEDETAIL = "insertSignRfcChangedetail";
	 /**
     * 
     * 插入变更签收明细
     * @author ibm-lizhiguo
     * @date 2012-11-16 下午3:10:53
     */
	@Override
	public int insertSelective(SignRfcChangeDetailEntity record) {
		record.setId(UUIDUtils.getUUID());
		return getSqlSession().insert(NAMESPACE+INSERTSIGNRFCCHANGEDETAIL, record);
	}

	  /**
     * 
     * 获得变更签收明细LIST
     * @author ibm-lizhiguo
     * @date 2012-11-16 下午3:11:07
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<SignRfcChangeDetailEntity> selectList(SignRfcChangeDetailEntity entity) {
		return getSqlSession().selectList(NAMESPACE+SIGNRFCCHANGEDETAILLIST, entity);
	}
	
	/**
     * 
     * 获得变更签收明细LIST
     * @author WeiXing
     * @date 2015-01-07 下午3:11:07
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<SignRfcChangeDetailEntity> selectList1(SignRfcChangeDetailEntity entity) {
		return getSqlSession().selectList(NAMESPACE+SIGNRFCCHANGEDETAILLIST1, entity);
	}

    
}