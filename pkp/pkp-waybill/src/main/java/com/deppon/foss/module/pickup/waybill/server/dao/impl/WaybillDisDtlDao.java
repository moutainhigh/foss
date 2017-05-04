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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/WaybillDisDtlDao.java
 * 
 * FILE NAME        	: WaybillDisDtlDao.java
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
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryArgsDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 运单折扣明细数据持久层
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Bobby,date:2012-10-17 下午7:28:27
 * </p>
 * 
 * @author Bobby
 * @date 2012-10-17 下午7:28:27
 * @since
 * @version
 */
public class WaybillDisDtlDao extends iBatis3DaoImpl implements IWaybillDisDtlDao {

	private static final String NAMESPACE = "foss.pkp.WaybillDisDtlEntityMapper.";

	@Override
	public int insert(WaybillDisDtlEntity record) {
		return this.getSqlSession().insert(NAMESPACE + "insert", record);
	}
	
	/**
     * 插入部分对象数据
     * @author WangQianJin
     * @date 2014-05-21
     */
	@Override
    public int insertSelective(WaybillDisDtlEntity record){
		return this.getSqlSession().insert(NAMESPACE + "insertSelective", record);
	}

	/**
	 * 批量插入对象数据
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 上午11:11:23
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlDao#addBatch(java.util.List)
	 */
	public int addBatch(List<WaybillDisDtlEntity> waybillDisDtl) {
		return this.getSqlSession().insert(NAMESPACE + "insertBatch", waybillDisDtl);
	}

	@Override
	public WaybillDisDtlEntity queryByPrimaryKey(String id) {
		return (WaybillDisDtlEntity) this.getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey", id);
	}

	/**
	 * 根据运单号查询折扣明细列表
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 下午5:38:57
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlDao#queryDisDtlEntityByNo(java.lang.String)
	 */
	@Override
	public List<WaybillDisDtlEntity> queryDisDtlEntityByNo(String waybillNo) {
		// 封装参数
		WaybillQueryArgsDto args = new WaybillQueryArgsDto();
		args.setWaybillNo(waybillNo);
		args.setActive(FossConstants.ACTIVE);

		@SuppressWarnings("unchecked")
		List<WaybillDisDtlEntity> list = this.getSqlSession().selectList(NAMESPACE + "selectEntityListByNo", args);
		return list;
	}

	/**
	 * 更新运单折扣明细
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param record
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlDao#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity)
	 */
	@Override
	public int updateByPrimaryKeySelective(WaybillDisDtlEntity record) {
		return this.getSqlSession().update(NAMESPACE + "updateByPrimaryKeySelective", record);
	}

	/**
	 * 
	 * <p>
	 * 运单折扣明细<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-1
	 * @param waybillNo
	 * @return
	 * List<WaybillDisDtlEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillDisDtlEntity> queryNewDisDtlEntityByNo(LastWaybillRfcQueryDto queryDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryNewDisDtlEntityByNo", queryDto);
	}

	@Override
	public int deleteWaybillDisDtlEntityById(String id) {
		return this.getSqlSession().delete(NAMESPACE + "deleteByPrimaryKey", id);
	}
	
	/**
	 * 根据运单号和类型查询CRM营销活动
	 * @创建时间 2014-4-22 下午8:34:33   
	 * @创建人： WangQianJin
	 */
	@Override
	public WaybillDisDtlEntity queryActiveInfoByNoAndType(String waybillNo){
		WaybillQueryArgsDto args = new WaybillQueryArgsDto();
		args.setWaybillNo(waybillNo);
		args.setActive(FossConstants.ACTIVE);
		//CRM营销活动
		args.setType(DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE);
		List<WaybillDisDtlEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryActiveInfoByNoAndType", args);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 根据运单号与类型修改运单状态
	 * @创建时间 2014-5-8 下午8:50:48   
	 * @创建人： WangQianJin
	 */
	@Override
	public int updateByWaybillNoAndType(WaybillDisDtlEntity record){
		return this.getSqlSession().update(NAMESPACE + "updateByWaybillNoAndType", record);
	}

	/**
	 * 激活运单折扣费用明细
	 * @author liyongfei--DMANA-2035
	 * @param waybillNoList
	 * @return
	 */
	@Override
	public int setWaybillDisDtlActive(List<String> waybillNoList) {
		// TODO Auto-generated method stub
		Map<String,Object> args = new HashMap<String, Object>();
		args.put("waybillNoList", waybillNoList);
		return (Integer)this.getSqlSession().update(NAMESPACE+"setWaybillDisDtlActive", args);
	}

	@Override
	public int deleteWaybillDisDtlEntityByWaybillNo(String waybillNo) {
		return this.getSqlSession().delete(NAMESPACE + "deleteByWaybillNo", waybillNo);
	}
	
	
}