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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/dao/impl/WaybillCHDtlPendingDao.java
 * 
 * FILE NAME        	: WaybillCHDtlPendingDao.java
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

import com.deppon.foss.module.pickup.creating.client.dao.IWaybillCHDtlPendingDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 待处理运单费用信息
 * 
 * @author 025000-foss-helong
 * @date 2012-10-30 下午7:14:23
 */
public class WaybillCHDtlPendingDao implements IWaybillCHDtlPendingDao {

	private static final String NAMESPACE = "foss.pkp.WaybillCHDtlPendingEntityMapper.";

	private SqlSession sqlSession;

	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	/**
	 * 
	 * 通过待处理运单费用信息删除
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-10-30 下午7:17:25
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillCHDtlPendingDao#deleteByPrimaryKey(java.lang.String)
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
	public int deleteByWaybillNo(String waybillNo){
		return this.sqlSession.delete(NAMESPACE + "deleteByWaybillNo",
				waybillNo);
	}
	
    /**
     * 通过主键查询
     * @author 025000-foss-helong
     * @date 2012-10-30 下午6:42:55
     */
	@Override
	public WaybillCHDtlPendingEntity queryByPrimaryKey(String id) {
		return (WaybillCHDtlPendingEntity) this.sqlSession.selectOne(NAMESPACE + "selectByPrimaryKey", id);
	}

    /**
     * 更新待处理运单费用信息
     * @author 025000-foss-helong
     * @date 2012-10-30 下午6:48:37
     */
	@Override
	public int updateByPrimaryKey(WaybillCHDtlPendingEntity record) {
		return this.sqlSession.update(NAMESPACE + "updateByPrimaryKey", record);
	}

	/**
	 * 保存待处理运单费用信息
	 * @author 025000-foss-helong
	 * @date 2012-10-30 下午7:40:20
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillCHDtlPendingDao#addWaybillCHDtlPendingSelective(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity)
	 */
	@Override
	public int addWaybillCHDtlPendingSelective(WaybillCHDtlPendingEntity record) {
		record.setId(UUIDUtils.getUUID());// 设置UUID
		return this.sqlSession.insert(NAMESPACE + "insertSelective", record);
	}

	/** 
	 * 批量插入待处理运单费用信息
	 * @author 025000-foss-helong
	 * @date 2012-11-3 下午6:13:30
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillCHDtlPendingDao#addWaybillCHDtlPendingBatch(java.util.List)
	 */
	@Override
	public void addWaybillCHDtlPendingBatch(List<WaybillCHDtlPendingEntity> record) {
		// 设置UUID
		for (WaybillCHDtlPendingEntity entity : record) {
			entity.setId(UUIDUtils.getUUID());
			this.sqlSession.insert(NAMESPACE + "insertSelective", entity);
		}
		
	}
	
	/**
	 * 通过运单ID查询待处理运单费用信息
	 * @author 025000-foss-helong
	 * @date 2012-12-6 下午2:51:04
	 */
	@Override
	public List<WaybillCHDtlPendingEntity> queryCHDtlPendingByNo(String waybillNo){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", waybillNo);
		parms.put("active", FossConstants.ACTIVE);
		return this.sqlSession.selectList(NAMESPACE + "queryCHDtlPendingByNo", parms);
	}
	
	/**
	 * 批量插入待处理运单费用信息
	 * @author 025000-foss-helong
	 * @date 2012-12-7 下午4:35:19
	 */
	@Override
    public int addBatch(List<WaybillCHDtlPendingEntity> list) {
    	for (WaybillCHDtlPendingEntity entity : list) {
    	    // 设置UUID
    	    entity.setId(UUIDUtils.getUUID());
    	}
    	return this.sqlSession.insert(NAMESPACE + "insertBatch", list);
    }
	
	/**
	 * 根据ID集合查询集合对象
	 * @author 025000-foss-helong
	 * @date 2012-12-14 下午6:26:37
	 */
	@Override
	public List<WaybillCHDtlPendingEntity>  queryChDtlPendingByListId(List<String> ids){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("ids", ids);
		parms.put("active", FossConstants.ACTIVE);
		return this.sqlSession.selectList(NAMESPACE + "selectByPrimaryKeyList", parms);
	}
	
	/**
	 * 通过运单号查询费用明细中的其它费用 
	 * @author 025000-foss-helong
	 * @date 2012-12-15 下午2:41:46
	 */
	@Override
	public List<WaybillOtherChargeDto> queryOtherChargeByNo(String waybillNo) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", waybillNo);
		parms.put("active", FossConstants.ACTIVE);
		return this.sqlSession.selectList(NAMESPACE + "queryOtherChargeByNo", parms);
	}
	
	/**
	 * 根据运单号查询费用明细中的非其它费用
	 * @author 025000-foss-helong
	 * @date 2012-12-15 下午2:45:05
	 */
	@Override
	public List<WaybillCHDtlPendingEntity> queryChargeExceptOtherChargeByNo(String waybillNo){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", waybillNo);
		parms.put("active", FossConstants.ACTIVE);
		return this.sqlSession.selectList(NAMESPACE + "queryChargeExceptOtherChargeByNo", parms);
	}
}