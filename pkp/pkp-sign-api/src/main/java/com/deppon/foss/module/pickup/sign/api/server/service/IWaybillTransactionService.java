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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/service/IWaybillTransactionService.java
 * 
 * FILE NAME        	: IWaybillTransactionService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity;

/**
 * 
 * 运单完结状态操作Service
 * @author 043258-foss-zhaobin
 * @date 2012-11-13 上午11:30:23
 */
public interface IWaybillTransactionService extends IService 
{
	/**
	 * 
	 * 标识业务完结
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-13 上午11:23:32
	 */
	boolean updateBusinessOver(WaybillTransactionEntity waybillTransactionEntity);

	/**
	 * 
	 * 标识财务完结
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-13 上午11:24:15
	 */
	boolean updateFinanceOver(WaybillTransactionEntity waybillTransactionEntity);

	/**
	 * 
	 * 反标识业务完结
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-13 上午11:25:29
	 */
	boolean updateReverseBusinessOver(WaybillTransactionEntity waybillTransactionEntity);

	/**
	 * 
	 * 反标识财务完结
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-13 上午11:26:18
	 */
	boolean updateReverseFinanceOver(WaybillTransactionEntity waybillTransactionEntity);
	
	/**
	 * 
	 * 新增业务标识
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-4 下午2:51:12
	 */
	void addWaybillTransaction(WaybillTransactionEntity waybillTransactionEntity);
	
	/**
	 * 
	 * 批处理标识财务完结
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-17 下午6:31:42
	 * @param waybillList 运单list
	 */
	boolean updateFinanceOverforList(List<String> waybillList);
	
	/**
	 * 
	 * 批处理反标识财务完结
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-17 下午6:32:05
	 * @param waybillList 运单list
	 */
	boolean updateReverseFinanceOverforList(List<String> waybillList);
	
	/**
	 * 根据运单号查询是否有运单状态信息
	 * @author WangQianJin
	 * @param waybillTransactionEntity
	 * @return
	 */
	boolean isExistWaybillTransaction(String waybillNo);
	
	/**
	 * <p>删除业务完结标示</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-8 20:03:43
	 */
	int deleteWaybillTransactionByWaybillNo(String waybillNo);

}