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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/WaybillDisDtlPendingDao.java
 * 
 * FILE NAME        	: WaybillDisDtlPendingDao.java
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
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountTypeConstants;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlPendingDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryArgsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 待处理运单折扣DAO
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-10-30 下午6:59:26
 */
public class WaybillDisDtlPendingDao extends iBatis3DaoImpl implements IWaybillDisDtlPendingDao {

	private static final String NAMESPACE = "foss.pkp.WaybillDisDtlPendingEntityMapper.";

	/**
	 * 
	 * 删除
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-30 下午7:12:25
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlPendingDao#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return this.getSqlSession().delete(NAMESPACE + "deleteByPrimaryKey", id);
	}
	
	/**
	 * 
	 * 删除
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-30 下午7:12:25
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlPendingDao#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByWaybillNo(String waybillNo) {
		return this.getSqlSession().delete(NAMESPACE + "deleteByWaybillNo", waybillNo);
	}

	/**
	 * 
	 * 根据ID查询费用折扣信息
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-30 下午7:12:39
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlPendingDao#queryByPrimaryKey(java.lang.String)
	 */
	@Override
	public WaybillDisDtlPendingEntity queryByPrimaryKey(String id) {
		return (WaybillDisDtlPendingEntity) this.getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey", id);
	}

	/**
	 * 更新运单费用折扣信息
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-30 下午7:09:24
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlPendingDao#updateByPrimaryKey(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity)
	 */
	@Override
	public int updateByPrimaryKey(WaybillDisDtlPendingEntity record) {
		return this.getSqlSession().update(NAMESPACE + "updateByPrimaryKey", record);

	}

	/**
	 * 插入待处理运单费用折扣明细
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-30 下午7:01:37
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlPendingDao#insertSelective(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity)
	 */
	@Override
	public int addWaybillDisDtlPendingSelective(WaybillDisDtlPendingEntity record) {
		record.setId(UUIDUtils.getUUID());// 设置UUID
		return this.getSqlSession().insert(NAMESPACE + "insertSelective", record);
	}


	/** 
	 * 批量插入WaybillDisDtlPendingEntity实体数据
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-3 下午5:15:55
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlPendingDao#addWaybillDisDtlPendingBatch(java.util.List)
	 */
	@Override
	public int addWaybillDisDtlPendingBatch(List<WaybillDisDtlPendingEntity> list,WaybillSystemDto systemDto) {
		//设置实体的UUID
		for (WaybillDisDtlPendingEntity entity : list) {
			entity.setId(UUIDUtils.getUUID());
		}
		return this.getSqlSession().insert(NAMESPACE + "insertBatch", list);
	}
	
	/**
	 * 查询运单折扣明细
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlPendingDao#queryDisDtlPendingByNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillDisDtlPendingEntity> queryDisDtlPendingByNo(String waybillNo){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", waybillNo);
		parms.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "queryDisDtlPendingByNo", parms);
	}
	
	/**
	 * 根据运单号和类型查询CRM营销活动
	 * @创建时间 2014-4-22 下午8:34:33   
	 * @创建人： WangQianJin
	 */
	@Override
	public WaybillDisDtlPendingEntity queryActiveInfoByNoAndType(String waybillNo){
		WaybillQueryArgsDto args = new WaybillQueryArgsDto();
		args.setWaybillNo(waybillNo);
		args.setActive(FossConstants.ACTIVE);
		//CRM营销活动
		args.setType(DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE);
		List<WaybillDisDtlPendingEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryActiveInfoByNoAndType", args);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

}