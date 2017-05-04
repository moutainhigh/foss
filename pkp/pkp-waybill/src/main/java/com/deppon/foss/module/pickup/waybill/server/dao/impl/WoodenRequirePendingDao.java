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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/WoodenRequirePendingDao.java
 * 
 * FILE NAME        	: WoodenRequirePendingDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirePendingDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 待处理运单打木架dao接口
 * @author 026123-foss-lifengteng
 * @date 2012-12-13 上午9:38:57
 */
public class WoodenRequirePendingDao  extends iBatis3DaoImpl implements IWoodenRequirePendingDao {
	private static final String NAMESPACE = "foss.pkp.WoodenRrequirePendingEntityMapper.";
	/**
	 * 新增打木架记录
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param wooden
	 * @return int
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirePendingDao#insert(com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity)
	 */
	@Override
	public int insert(WoodenRequirePendingEntity wooden) {
		// 设置UUID
		wooden.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE + "insert", wooden);
	}

	/**
	 * 新增打木架记录
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param wooden
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirePendingDao#insertSelective(com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity)
	 */
	@Override
	public int insertSelective(WoodenRequirePendingEntity wooden) {
		// 设置UUID
		wooden.setId(UUIDUtils.getUUID());
		return  this.getSqlSession().insert(NAMESPACE + "insertSelective", wooden);
	}

	/**
	 * 
	 * @author 043260-foss-suyujun
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
		return (WoodenRequirePendingEntity) this.getSqlSession().selectOne(NAMESPACE + "selectByWaybillNo", map);
	}


	/**
	 * 
	 * 根据运单号删除待处理运单的打木架信息
	 * @author foss-sunrui
	 * @date 2013-3-22 下午1:22:15
	 * @param waybillNo
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirePendingDao#deleteByWaybillNo(java.lang.String)
	 */
	@Override
	public int deleteByWaybillNo(String waybillNo) {
		return this.getSqlSession().delete(NAMESPACE + "deleteByWaybillNo", waybillNo);
	}
	
	/**
	 * 根据运单号删除待处理运单的打木架信息
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-1-20 上午11:23:47
	 */
	@Override
	public int deleteWoodenRequirementsByWaybillNo(String waybillNo){
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("active", FossConstants.INACTIVE);
		map.put("waybillNo", waybillNo);
		map.put("conActive", FossConstants.ACTIVE);
		return this.getSqlSession().update(NAMESPACE + "deleteWoodenRequirementsByWaybillNo", map);				
	}
	@Override
	public int batchInsert(List<WoodenRequirePendingEntity> woodenRequire) {		
		return this.getSqlSession().insert(NAMESPACE+"insertBatch",woodenRequire);
	}

	@Override
	public void deleteWoodByWaybillNos(List<String> waybillNos) {
		this.getSqlSession().delete(NAMESPACE + "deleteWoodByWaybillNos", waybillNos);
		
	}
    
}