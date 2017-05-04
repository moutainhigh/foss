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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/dao/impl/WaybillDisDtlPendingDao.java
 * 
 * FILE NAME        	: WaybillDisDtlPendingDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.pickup.creating.client.dao.IWaybillDisDtlPendingDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 待处理运单折扣DAO
 * 
 * @author 025000-foss-helong
 * @date 2012-10-30 下午6:59:26
 */
public class WaybillDisDtlPendingDao implements IWaybillDisDtlPendingDao {

	private static final String NAMESPACE = "foss.pkp.WaybillDisDtlPendingEntityMapper.";

	private SqlSession sqlSession;

	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	/**
	 * 
	 * 删除
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-10-30 下午7:12:25
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlPendingDao#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return this.sqlSession.delete(NAMESPACE + "deleteByPrimaryKey", id);
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
	/**
	 * 
	 * 根据ID查询费用折扣信息
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-10-30 下午7:12:39
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlPendingDao#queryByPrimaryKey(java.lang.String)
	 */
	@Override
	public WaybillDisDtlPendingEntity queryByPrimaryKey(String id) {
		return (WaybillDisDtlPendingEntity) this.sqlSession.selectOne(NAMESPACE + "selectByPrimaryKey", id);
	}

	/**
	 * 更新运单费用折扣信息
	 * @author 025000-foss-helong
	 * @date 2012-10-30 下午7:09:24
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlPendingDao#updateByPrimaryKey(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity)
	 */
	@Override
	public int updateByPrimaryKey(WaybillDisDtlPendingEntity record) {
		return this.sqlSession.update(NAMESPACE + "updateByPrimaryKey", record);

	}

	/**
	 * 插入待处理运单费用折扣明细
	 * @author 025000-foss-helong
	 * @date 2012-10-30 下午7:01:37
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlPendingDao#insertSelective(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity)
	 */
	@Override
	public int addWaybillDisDtlPendingSelective(WaybillDisDtlPendingEntity record) {
		record.setId(UUIDUtils.getUUID());// 设置UUID
		return this.sqlSession.insert(NAMESPACE + "insertSelective", record);
	}


	/** 
	 * 批量插入WaybillDisDtlPendingEntity实体数据
	 * @author 025000-foss-helong
	 * @date 2012-11-3 下午5:15:55
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlPendingDao#addWaybillDisDtlPendingBatch(java.util.List)
	 */
	@Override
	public void addWaybillDisDtlPendingBatch(List<WaybillDisDtlPendingEntity> list,WaybillSystemDto systemDto) {
		//设置实体的UUID
		for (WaybillDisDtlPendingEntity entity : list) {
			entity.setId(UUIDUtils.getUUID());
			this.sqlSession.insert(NAMESPACE + "insertSelective", entity);
		}
	}
	
	/**
	 * 查询运单折扣明细
	 * @author 025000-foss-helong
	 * @date 2012-12-24
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlPendingDao#queryDisDtlPendingByNo(java.lang.String)
	 */
	@Override
	public List<WaybillDisDtlPendingEntity> queryDisDtlPendingByNo(String waybillNo){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", waybillNo);
		parms.put("active", FossConstants.ACTIVE);
		return this.sqlSession.selectList(NAMESPACE + "queryDisDtlPendingByNo", parms);
	}

}