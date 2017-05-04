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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/dao/impl/WoodenRequirementsDao.java
 * 
 * FILE NAME        	: WoodenRequirementsDao.java
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

import com.deppon.foss.module.pickup.creating.client.dao.IWoodenRequirementsDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 代打木架，木箱数据持久层
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:025000-foss-helong,date:2012-11-5 上午11:07:06,
 * </p>
 * 
 * @author 025000-foss-helong
 * @date 2012-11-5 上午11:07:06
 * @since
 * @version
 */
public class WoodenRequirementsDao  implements IWoodenRequirementsDao {

    private static final String NAMESPACE = "foss.pkp.WoodenRequirementsEntityMapper.";

    
	private SqlSession sqlSession;

	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
    
    /**
     * 新增代打木架实体信息
     * 
     * @author 025000-foss-helong
     * @date 2012-11-5 下午5:50:17
     * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirementsDao#addWoodenRequirementsEntity(com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity)
     */
    @Override
    public int addWoodenRequirementsEntity(WoodenRequirementsEntity wooden) {
	// 设置UUID
	wooden.setId(UUIDUtils.getUUID());
	return this.sqlSession.insert(NAMESPACE + "insertSelective", wooden);
    }

    /**
     * 根据运单号查询代打木架实体信息
     * 
     * @author 025000-foss-helong
     * @date 2012-11-5 下午5:53:34
     * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirementsDao#queryWoodenByNo(java.lang.String)
     */
    @Override
    public WoodenRequirementsEntity queryWoodenByNo(String waybillNo) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("waybillNo", waybillNo);
    	map.put("active", FossConstants.ACTIVE);
    	return (WoodenRequirementsEntity) this.sqlSession.selectOne(NAMESPACE + "selectByWaybillNo", map);
    }

    
    /**
	 * 
	 * <p>
	 * 新版运单代打木架信息<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-1
	 * @param waybillNo
	 * @return
	 * WoodenRequirementsEntity
	 */
	@Override
	public WoodenRequirementsEntity queryNewWoodenRequirements(LastWaybillRfcQueryDto queryDto) {
		return (WoodenRequirementsEntity) this.sqlSession.selectOne(NAMESPACE + "queryNewWoodenRequirements",queryDto);
	}

	@Override
	public void updateWoodenRequirements(
			WoodenRequirementsEntity woodRequirements) {
		this.sqlSession.update(NAMESPACE + "updateWoodenRequirements", woodRequirements);
	}

	/**
	 * 删除打木架
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirementsDao#deleteWoodRequirements(java.lang.String)
	 */
	@Override
	public int deleteWoodRequirementsById(String id) {
		return this.sqlSession.delete(NAMESPACE + "deleteWoodRequirementsById", id);
	}

}