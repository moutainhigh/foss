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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/dao/impl/WoodenRequirePendingDao.java
 * 
 * FILE NAME        	: WoodenRequirePendingDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.pickup.creating.client.dao.IWoodenRequirePendingDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;


/**
 * 待处理运单打木架dao接口
 * @author 025000-foss-helong
 * @date 2012-12-13 上午9:38:57
 */
public class WoodenRequirePendingDao implements IWoodenRequirePendingDao {
	private static final String NAMESPACE = "foss.pkp.WoodenRrequirePendingEntityMapper.";
	
	private SqlSession sqlSession;

	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
	/**
	 * 新增打木架记录
	 * @author 025000-foss-helong
	 * @date 2012-12-24
	 * @param wooden
	 * @return int
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirePendingDao#insert(com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity)
	 */
	@Override
	public int insert(WoodenRequirePendingEntity wooden) {
		// 设置UUID
		wooden.setId(UUIDUtils.getUUID());
		return this.sqlSession.insert(NAMESPACE + "insert", wooden);
	}

	/**
	 * 新增打木架记录
	 * @author 025000-foss-helong
	 * @date 2012-12-24
	 * @param wooden
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirePendingDao#insertSelective(com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity)
	 */
	@Override
	public int insertSelective(WoodenRequirePendingEntity wooden) {
		// 设置UUID
		wooden.setId(UUIDUtils.getUUID());
		return  this.sqlSession.insert(NAMESPACE + "insertSelective", wooden);
	}

	/**
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-12-24
	 * @param waybillNo
	 * @return WoodenRequirePendingEntity
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirePendingDao#queryWoodenRequireByNo(java.lang.String)
	 */
	@Override
	public WoodenRequirePendingEntity queryWoodenRequireByNo(String waybillNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);
		map.put("active", FossConstants.ACTIVE);
		return (WoodenRequirePendingEntity) this.sqlSession.selectOne(NAMESPACE + "selectByWaybillNo", map);
	}


	/**
	 * <p>
	 * 通过运单编号删除记录
	 * </p>
	 * @author 105089-foss-yangtong
	 * @date 2012-10-31 下午3:40:57
	 * @param waybillNo
	 * @return
	 * @see 
	 */
	@Override
	public int deleteByWaybillNo(String waybillNo) {
		return this.sqlSession.delete(NAMESPACE + "deleteByWaybillNo",
				waybillNo);
	}


	
    
}