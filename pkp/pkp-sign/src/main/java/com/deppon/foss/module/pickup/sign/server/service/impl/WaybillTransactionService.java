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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/WaybillTransactionService.java
 * 
 * FILE NAME        	: WaybillTransactionService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.service.impl;


import java.util.List;

import com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillTransactionDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillTransactionDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 运单完结状态操作Service
 * @author 043258-foss-zhaobin
 * @date 2012-11-13 上午11:41:49
 */
public class WaybillTransactionService implements IWaybillTransactionService 
{
	/**
	 * 获取运单完结状态操作Dao
	 */
	public IWaybillTransactionDao waybillTransactionDao;
	
	/**
	 * Gets the waybill transaction dao.
	 *
	 * @return the waybill transaction dao
	 */
	public IWaybillTransactionDao getWaybillTransactionDao() {
		return waybillTransactionDao;
	}
	
	/**
	 * Sets the waybill transaction dao.
	 *
	 * @param waybillTransactionDao the new waybill transaction dao
	 */
	public void setWaybillTransactionDao(
			IWaybillTransactionDao waybillTransactionDao) {
		this.waybillTransactionDao = waybillTransactionDao;
	}

	/**
	 * 标识业务完结.
	 *
	 * @param waybillTransactionEntity the waybill transaction entity
	 * @return true, if successful
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-13 上午11:23:32
	 */
	public boolean updateBusinessOver(WaybillTransactionEntity waybillTransactionEntity)
	{
		if(waybillTransactionEntity != null)
		{
			waybillTransactionEntity.setBusinessOver(FossConstants.YES);
			return waybillTransactionDao.updateBusinessOver(waybillTransactionEntity);
		}
		return false;
	}

	/**
	 * 标识财务完结.
	 *
	 * @param waybillTransactionEntity the waybill transaction entity
	 * @return true, if successful
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-13 上午11:24:15
	 */
	public boolean updateFinanceOver(WaybillTransactionEntity waybillTransactionEntity) 
	{
		if(waybillTransactionEntity != null)
		{
			waybillTransactionEntity.setFinanceOver(FossConstants.YES);
			return waybillTransactionDao.updateFinanceOver(waybillTransactionEntity);
		}
		return false;
	}

	/**
	 * 反标识业务完结.
	 *
	 * @param waybillTransactionEntity the waybill transaction entity
	 * @return true, if successful
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-13 上午11:25:29
	 */
	public boolean updateReverseBusinessOver(WaybillTransactionEntity waybillTransactionEntity) 
	{
		if(waybillTransactionEntity != null)
		{
			waybillTransactionEntity.setBusinessOver(FossConstants.NO);
			return waybillTransactionDao.updateReverseBusinessOver(waybillTransactionEntity);
		}
		return false;
	}	

	/**
	 * 反标识财务完结.
	 *
	 * @param waybillTransactionEntity the waybill transaction entity
	 * @return true, if successful
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-13 上午11:26:18
	 */
	public boolean updateReverseFinanceOver(WaybillTransactionEntity waybillTransactionEntity) 
	{
		if(waybillTransactionEntity != null)
		{
			waybillTransactionEntity.setFinanceOver(FossConstants.NO);
			return waybillTransactionDao.updateReverseFinanceOver(waybillTransactionEntity);
		}
		return false;
	}
	
	/**
	 * 新增业务标识.
	 *
	 * @param waybillTransactionEntity the waybill transaction entity
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-4 下午3:00:54
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService#addWaybillTransaction(com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity)
	 */
	@Override
	public void addWaybillTransaction(WaybillTransactionEntity waybillTransactionEntity) 
	{
		if(waybillTransactionEntity != null)
		{
			waybillTransactionDao.addWaybillTransaction(waybillTransactionEntity);
		}
	}
	
	/**
	 * 批处理标识财务完结.
	 *
	 * @param waybillList the waybill list
	 * @return true, if successful
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-17 下午6:32:43
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService#updateFinanceOverforList(java.util.List)
	 */
	@Override
	public boolean updateFinanceOverforList(List<String> waybillList) 
	{
		WaybillTransactionDto waybillTransactionDto = new WaybillTransactionDto();
		waybillTransactionDto.setState(FossConstants.YES);
		waybillTransactionDto.setWaybillList(waybillList);
		return waybillTransactionDao.updateFinanceOverforList(waybillTransactionDto);
	}
	
	/**
	 * 批处理反标识财务完结.
	 *
	 * @param waybillList the waybill list
	 * @return true, if successful
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-17 下午6:32:57
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService#updateReverseFinanceOverforList(java.util.List)
	 */
	@Override
	public boolean updateReverseFinanceOverforList(List<String> waybillList) 
	{
		WaybillTransactionDto waybillTransactionDto = new WaybillTransactionDto();
		waybillTransactionDto.setState(FossConstants.NO);
		waybillTransactionDto.setWaybillList(waybillList);
		return waybillTransactionDao.updateFinanceOverforList(waybillTransactionDto);
	}
	
	/**
	 * 根据运单号查询是否有运单状态信息
	 * @author WangQianJin
	 * @param waybillTransactionEntity
	 * @return
	 */
	@Override
	public boolean isExistWaybillTransaction(String waybillNo){
		boolean flag=false;
		WaybillTransactionEntity waybillTransactionEntity=new WaybillTransactionEntity();
		waybillTransactionEntity.setWaybillNo(waybillNo);
		WaybillTransactionEntity result=waybillTransactionDao.queryWaybillTransaction(waybillTransactionEntity);		
		if(result!=null){
			flag=true;
		}
		return flag;
	}

	/**
	 * <p>删除业务完结标示</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-8 20:03:43
	 */
	@Override
	public int deleteWaybillTransactionByWaybillNo(String waybillNo) {
		return waybillTransactionDao.deleteWaybillTransactionByWaybillNo(waybillNo);
	}
}