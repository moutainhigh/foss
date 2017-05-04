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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/WaybillCHDtlPendingDao.java
 * 
 * FILE NAME        	: WaybillCHDtlPendingDao.java
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
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillCHDtlPendingDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 待处理运单费用信息
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-10-30 下午7:14:23
 */
public class WaybillCHDtlPendingDao extends iBatis3DaoImpl implements IWaybillCHDtlPendingDao {

	private static final String NAMESPACE = "foss.pkp.WaybillCHDtlPendingEntityMapper.";

	/**
	 * 
	 * 通过待处理运单费用信息删除
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-30 下午7:17:25
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillCHDtlPendingDao#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return this.getSqlSession().delete(NAMESPACE + "deleteByPrimaryKey", id);
	}
	
	/**
	 * 
	 * 通过待处理运单费用信息删除
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-30 下午7:17:25
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillCHDtlPendingDao#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByWaybillNo(String waybillNo) {
		return this.getSqlSession().delete(NAMESPACE + "deleteByWaybillNo", waybillNo);
	}

    /**
     * 通过主键查询
     * @author 026113-foss-linwensheng
     * @date 2012-10-30 下午6:42:55
     */
	@Override
	public WaybillCHDtlPendingEntity queryByPrimaryKey(String id) {
		return (WaybillCHDtlPendingEntity) this.getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey", id);
	}

    /**
     * 更新待处理运单费用信息
     * @author 026113-foss-linwensheng
     * @date 2012-10-30 下午6:48:37
     */
	@Override
	public int updateByPrimaryKey(WaybillCHDtlPendingEntity record) {
		return this.getSqlSession().update(NAMESPACE + "updateByPrimaryKey", record);
	}

	/**
	 * 保存待处理运单费用信息
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-30 下午7:40:20
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillCHDtlPendingDao#addWaybillCHDtlPendingSelective(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity)
	 */
	@Override
	public int addWaybillCHDtlPendingSelective(WaybillCHDtlPendingEntity record) {
		record.setId(UUIDUtils.getUUID());// 设置UUID
		return this.getSqlSession().insert(NAMESPACE + "insertSelective", record);
	}

	/** 
	 * 批量插入待处理运单费用信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-3 下午6:13:30
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillCHDtlPendingDao#addWaybillCHDtlPendingBatch(java.util.List)
	 */
	@Override
	public int addWaybillCHDtlPendingBatch(List<WaybillCHDtlPendingEntity> record) {
		// 设置UUID
		for (WaybillCHDtlPendingEntity entity : record) {
			entity.setId(UUIDUtils.getUUID());
		}
		return this.getSqlSession().insert(NAMESPACE + "insertBatch", record);
	}
	
	/**
	 * 通过运单ID查询待处理运单费用信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午2:51:04
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillCHDtlPendingEntity> queryCHDtlPendingByNo(String waybillNo){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", waybillNo);
		parms.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "queryCHDtlPendingByNo", parms);
	}
	
	/**
	 * 批量插入待处理运单费用信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-7 下午4:35:19
	 */
	@Override
    public int addBatch(List<WaybillCHDtlPendingEntity> list) {
    	for (WaybillCHDtlPendingEntity entity : list) {
    	    // 设置UUID
    	    entity.setId(UUIDUtils.getUUID());
    	}
    	return this.getSqlSession().insert(NAMESPACE + "insertBatch", list);
    }
	
	/**
	 * 根据ID集合查询集合对象
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-14 下午6:26:37
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillCHDtlPendingEntity>  queryChDtlPendingByListId(List<String> ids){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("ids", ids);
		parms.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "selectByPrimaryKeyList", parms);
	}
	
	/**
	 * 通过运单号查询费用明细中的其它费用 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-15 下午2:41:46
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillOtherChargeDto> queryOtherChargeByNo(String waybillNo) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", waybillNo);
		parms.put("active", FossConstants.ACTIVE);
		parms.put("type1", PriceEntityConstants.PRICING_CODE_QT);
		parms.put("type2", PriceEntityConstants.PRICING_CODE_QS);
		return this.getSqlSession().selectList(NAMESPACE + "queryOtherChargeByNo", parms);
	}
	
	/**
	 * 根据运单号删除待补录的费用明细信息
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-1-20 下午1:43:00
	 */
	@Override
	public int deleteCHDtlPendingByWaybillNo(String waybillNo){
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("active", FossConstants.INACTIVE);
		map.put("waybillNo", waybillNo);
		map.put("conActive", FossConstants.ACTIVE);
		return this.getSqlSession().update(NAMESPACE + "deleteCHDtlPendingByWaybillNo", map);
	}
}