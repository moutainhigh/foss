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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/WaybillTransactionDao.java
 * 
 * FILE NAME        	: WaybillTransactionDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillTransactionDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillTransactionDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * 运单完结状态操作DAO
 * @author 043258-foss-zhaobin
 * @date 2012-12-25 下午8:36:22
 */
public class WaybillTransactionDao extends iBatis3DaoImpl implements IWaybillTransactionDao 
{
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE_CONTRACT = "com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity.";
	/**
	 * 标识业务完结
	 */
	private static final String UPDATE_BUSINESSOVER = "updateBusinessOver"; 
	/**
	 * 标识财务完结
	 */
	private static final String UPDATE_FINANCEOVER = "updateFinanceOver"; 
	/**
	 * 标识业务完结
	 */
	private static final String UPDATE_REVERSEBUSINESSOVER = "updateReverseBusinessOver"; 
	/**
	 * 标识业务完结
	 */
	private static final String UPDATE_REVERSEFINANCEOVER = "updateReverseFinanceOver"; 
	/**
	 * 新增完结信息
	 */
	private static final String ADD_WAYBILLTRANSACTION = "addWaybillTransaction";
	/**
	 * 批处理标识财务完结
	 */
	private static final String UPDATE_FINANCEOVERFORLIST = "updateFinanceOverForList";
	
	/**
	 * 标识业务完结
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-13 上午11:23:32
	 */
	@Override
	public boolean updateBusinessOver(WaybillTransactionEntity waybillTransactionEntity) 
	{
		//修改时间
		waybillTransactionEntity.setModifyDate(new Date());
		return this.getSqlSession().update(
				NAMESPACE_CONTRACT + UPDATE_BUSINESSOVER, waybillTransactionEntity) > 0 ? true : false;

	}
	
	/**
	 * 
	 * 标识财务完结
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-13 上午11:24:15
	 */
	@Override
	public boolean updateFinanceOver(WaybillTransactionEntity waybillTransactionEntity) 
	{
		//修改时间
		waybillTransactionEntity.setModifyDate(new Date());
		return this.getSqlSession().update(
				NAMESPACE_CONTRACT + UPDATE_FINANCEOVER, waybillTransactionEntity) > 0 ? true : false;
	}

	/**
	 * 
	 * 反标识业务完结
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-13 上午11:25:29
	 */
	@Override
	public boolean updateReverseBusinessOver(WaybillTransactionEntity waybillTransactionEntity) 
	{
		//修改时间
		waybillTransactionEntity.setModifyDate(new Date());
		return this.getSqlSession().update(
				NAMESPACE_CONTRACT + UPDATE_REVERSEBUSINESSOVER, waybillTransactionEntity) > 0 ? true : false;
	}

	/**
	 * 
	 * 反标识财务完结
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-13 上午11:26:18
	 */
	@Override
	public boolean updateReverseFinanceOver(WaybillTransactionEntity waybillTransactionEntity) 
	{
		//修改时间
		waybillTransactionEntity.setModifyDate(new Date());
		return this.getSqlSession().update(
				NAMESPACE_CONTRACT + UPDATE_REVERSEFINANCEOVER, waybillTransactionEntity) > 0 ? true : false;
	}

	/**
	 * 
	 *  新增业务标识
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-4 下午2:57:46
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillTransactionDao#addWaybillTransaction(com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity)
	 */
	@Override
	public void addWaybillTransaction(WaybillTransactionEntity waybillTransactionEntity)
	{
		waybillTransactionEntity.setId(UUIDUtils.getUUID());
		//创建时间
		waybillTransactionEntity.setCreateDate(new Date());
		//修改时间
		waybillTransactionEntity.setModifyDate(waybillTransactionEntity.getCreateDate());
		getSqlSession().insert(NAMESPACE_CONTRACT + ADD_WAYBILLTRANSACTION, waybillTransactionEntity);
	}

	/**
	 * 
	 * 批处理标识财务完结
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-24 上午11:28:55
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillTransactionDao#updateFinanceOverforList(java.util.List)
	 */
	@Override
	public boolean updateFinanceOverforList(WaybillTransactionDto waybillTransactionDto)
	{
		return this.getSqlSession().update(NAMESPACE_CONTRACT + UPDATE_FINANCEOVERFORLIST, waybillTransactionDto)> 0 ? true : false;
	}
	
	/**
	 * 查询运单状态信息
	 * @author WangQianJin
	 * @param waybillTransactionEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WaybillTransactionEntity queryWaybillTransaction(WaybillTransactionEntity waybillTransactionEntity){
		List<WaybillTransactionEntity> list = this.getSqlSession().selectList(NAMESPACE_CONTRACT + "queryWaybillTransaction",waybillTransactionEntity);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * <p>删除业务完结标示</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-8 20:03:43
	 */
	@Override
	public int deleteWaybillTransactionByWaybillNo(String waybillNo) {
		return this.getSqlSession().delete(NAMESPACE_CONTRACT+"deleteWaybillTransactionByWaybillNo", waybillNo);
	}

}