/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-closing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/closing/server/service/impl/WaybillFinancialStatusService.java
 * 
 * FILE NAME        	: WaybillFinancialStatusService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.deppon.foss.module.settlement.closing.api.server.dao.IWaybillFinancialStatusEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IWaybillFinancialStatusService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.WaybillFinancialStatusEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 结算业务状态完结服务.
 *
 * @author 000123-foss-huangxiaobo
 * @date 2012-12-3 下午2:46:30
 */
public class WaybillFinancialStatusService implements IWaybillFinancialStatusService {

	/** 运单财务状态DAO. */
	private IWaybillFinancialStatusEntityDao waybillFinancialStatusEntityDao;
	
	/**
	 * 批量添加财务完结状态.
	 *
	 * @param list the list
	 * @author ibm-zhuwei
	 * @date 2012-12-3 上午11:14:43
	 */
	@Override
	public void addByBatch(List<WaybillFinancialStatusEntity> list) {
		// 为空退出
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		// 批量添加财务完结状态
		waybillFinancialStatusEntityDao.addByBatch(list);
	}

	/**
	 * 批量更新财务完结状态.
	 *
	 * @param list the list
	 * @author ibm-zhuwei
	 * @date 2012-12-3 上午11:15:41
	 */
	@Override
	public void updateStatusByBatch(List<WaybillFinancialStatusEntity> list) {
		// 为空退出
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		// 批量更新财务完结状态
		waybillFinancialStatusEntityDao.updateStatusByBatch(list);
	}

	/**
	 * 批量删除财务完结状态.
	 *
	 * @param list the list
	 * @author ibm-zhuwei
	 * @date 2012-12-5 下午2:00:58
	 */
	@Override
	public void deleteByBatch(List<WaybillFinancialStatusEntity> list) {
		// 为空退出
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		// 批量删除财务完结状态
		waybillFinancialStatusEntityDao.deleteByBatch(list);
	}

	/**
	 * 通过运单查询财务完结状态.
	 *
	 * @param waybillNos the waybill nos
	 * @return the list
	 * @author ibm-zhuwei
	 * @date 2012-12-5 下午2:36:29
	 */
	@Override
	public List<WaybillFinancialStatusEntity> queryByWaybillNos(
			List<String> waybillNos) {

		if (CollectionUtils.isEmpty(waybillNos)) {
			throw new SettlementException("查询财务完结失败");
		}
		// 通过运单查询财务完结状态
		return waybillFinancialStatusEntityDao.queryByWaybillNos(waybillNos);
	}

	
	/**
	 * Sets the waybill financial status entity dao.
	 *
	 * @param waybillFinancialStatusEntityDao the new waybill financial status entity dao
	 */
	public void setWaybillFinancialStatusEntityDao(
			IWaybillFinancialStatusEntityDao waybillFinancialStatusEntityDao) {
		this.waybillFinancialStatusEntityDao = waybillFinancialStatusEntityDao;
	}

}
