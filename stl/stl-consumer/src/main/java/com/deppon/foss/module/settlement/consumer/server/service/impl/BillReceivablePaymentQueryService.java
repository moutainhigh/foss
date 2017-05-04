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
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/BillReceivablePaymentQueryService.java
 * 
 * FILE NAME        	: BillReceivablePaymentQueryService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillReceivablePaymentQueryDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivablePaymentQueryService;

/**
 * 应付应收查询Service实现
 * 
 * @author foss-zhangxiaohui
 * @date Oct 30, 2012 4:54:43 PM
 */
public class BillReceivablePaymentQueryService implements IBillReceivablePaymentQueryService {
	
	/**
	 * 日志打印对象声明
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BillReceivablePaymentQueryService.class);
	
	/**
	 * 应付应收查询Dao
	 */
	private IBillReceivablePaymentQueryDao billReceivablePaymentQueryDao;

	/**
	 * 通过运单单号查询收款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:55:43 PM
	 */
	@Override
	public List<BillRepaymentEntity> queryRepaymentBillByWayBillNOs(List<String> wayBillNos, String active) {
		// 输入参数校验
		if (CollectionUtils.isEmpty(wayBillNos)) {
			//输入参数为空则抛出异常
			throw new SettlementException("传入的运单集合不能为空");
		} 	
		//Service执行的Log
		LOGGER.debug("entering service, active: " + active);
		//执行查询操作
		List<BillRepaymentEntity> list = billReceivablePaymentQueryDao.queryRepaymentBillByWayBillNOs(wayBillNos, active);
		//Service执行结束打印日志
		LOGGER.debug("successfully exit service, active: " + active);
		//返回查询结果
		return list;
	}
	
	/**
	 * 通过运单单号,收入部门查询收款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:55:43 PM
	 */
	@Override
	public List<BillRepaymentEntity> queryRepaymentBillByWayBillNOsAndOrgCodes(List<String> wayBillNos, List<String> orgCodes, String active,String isRedBack) {
		// 输入参数校验
		if (CollectionUtils.isEmpty(wayBillNos)) {
			//输入参数为空则抛出异常
			throw new SettlementException("传入的运单集合不能为空");
		} 	
		//Service执行的Log
		LOGGER.debug("entering service, active: " + active);
		//执行查询操作
		List<BillRepaymentEntity> list = billReceivablePaymentQueryDao.queryRepaymentBillByWayBillNOsAndOrgCodes(wayBillNos, orgCodes, active,isRedBack);
		//Service执行结束打印日志
		LOGGER.debug("successfully exit service, active: " + active);
		//返回查询结果
		return list;
	}

	/**
	 * 通过运单单号查询付款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:57:43 PM
	 */
	@Override
	public List<BillPaymentEntity> queryPaymentBillByWayBillNOs(List<String> wayBillNos, String active) {
		// 输入参数校验
		if (CollectionUtils.isEmpty(wayBillNos)) {
			//输入参数为空则抛出异常
			throw new SettlementException("传入的运单集合不能为空");
		}
		//Service执行的Log
		LOGGER.debug("entering service, active: " + active);
		//执行查询操作
		List<BillPaymentEntity> list = billReceivablePaymentQueryDao.queryPaymentBillByWayBillNOs(wayBillNos, active);
		// Service执行结束打印日志
		LOGGER.debug("successfully exit service, active: " + active);
		//返回查询结果
		return list;
	}
	
	/**
	 * 通过运单单号、付款部门查询付款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:57:43 PM
	 */
	@Override
	public List<BillPaymentEntity> queryPaymentBillByWayBillNOsAndOrgCodes(List<String> wayBillNos, List<String> orgCodes, String active) {
		// 输入参数校验
		if (CollectionUtils.isEmpty(wayBillNos)) {
			//输入参数为空则抛出异常
			throw new SettlementException("传入的运单集合不能为空");
		}
		//Service执行的Log
		LOGGER.debug("entering service, active: " + active);
		//执行查询操作
		List<BillPaymentEntity> list = billReceivablePaymentQueryDao.queryPaymentBillByWayBillNOsAndOrgCodes(wayBillNos , orgCodes, active);
		// Service执行结束打印日志
		LOGGER.debug("successfully exit service, active: " + active);
		//返回查询结果
		return list;
	}
	
	/**
	 * 通过运单单号查询核销单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:59:43 PM
	 */
	@Override
	public List<BillWriteoffEntity> queryWriteoffBillByWayBillNOs(List<String> wayBillNos, String active) {
		// 输入参数校验
		if (CollectionUtils.isEmpty(wayBillNos)) {
			//输入参数为空则抛出异常
			throw new SettlementException("传入的运单集合不能为空");
		}
		//Service执行的Log
		LOGGER.debug("entering service, active: " + active);
		//声明查询出来的结果集
		List<BillWriteoffEntity> list = billReceivablePaymentQueryDao.queryWriteoffBillByWayBillNOs(wayBillNos, active);
		// Service执行结束打印日志
		LOGGER.debug("successfully exit service, active: " + active);
		//返回查询结果
		return list;
	}
	
	/**
	 * 通过运单单号查询核销单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:59:43 PM
	 */
	@Override
	public List<BillWriteoffEntity> queryWriteoffBillByWayBillNOsAndOrgCodes(List<String> wayBillNos, List<String> orgCodes, String active) {
		// 输入参数校验
		if (CollectionUtils.isEmpty(wayBillNos)) {
			//输入参数为空则抛出异常
			throw new SettlementException("传入的运单集合不能为空");
		}
		//Service执行的Log
		LOGGER.debug("entering service, active: " + active);
		//声明查询出来的结果集
		List<BillWriteoffEntity> list = billReceivablePaymentQueryDao.queryWriteoffBillByWayBillNOsAndOrgCodes(wayBillNos, orgCodes, active);
		// Service执行结束打印日志
		LOGGER.debug("successfully exit service, active: " + active);
		//返回查询结果
		return list;
	}
	
	/**
	 * @param billReceivablePaymentQueryDao
	 */
	public void setBillReceivablePaymentQueryDao(
			IBillReceivablePaymentQueryDao billReceivablePaymentQueryDao) {
		this.billReceivablePaymentQueryDao = billReceivablePaymentQueryDao;
	}

	/**
	 * 根据单号查询应收单
	 * @author foss-chenmingchun
	 * @date Oct 30, 2012 4:59:43 PM
	 */
	
	@Override
	public List<BillReceivableEntity> queryByWaybillNosAndOrgCodes(
			List<String> waybillNos, List<String> orgCodeList, String active,
			CurrentInfo currentInfo) {
		if (CollectionUtils.isEmpty(waybillNos)) {
			throw new SettlementException(" 按照运单号和应付部门编码集合查询应收单信息参数不能为空");
		}
		return this.billReceivablePaymentQueryDao.queryByWaybillNosAndOrgCodes(
				waybillNos, orgCodeList, active, currentInfo);
	}



	/**
	 * 按照运单号和应付部门编码集合查询应付单信息
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-28 下午3:25:04
	 * @param waybillNos 运单号
	 * @param orgCodeList 应付部门编码集合
	 * @param active  是否有效
	 * @return
	 */
	@Override
	public List<BillPayableEntity> queryPayableByWaybillNosAndOrgCodes(
			List<String> waybillNos, List<String> orgCodeList, String active,CurrentInfo currentInfo) {
		//运单号集合不能为空
		if(CollectionUtils.isEmpty(waybillNos)){
			throw new SettlementException(" 按照运单号和应付部门编码集合查询应付单信息参数不能为空");
		}
		return this.billReceivablePaymentQueryDao.queryPayableByWaybillNosAndOrgCodes(waybillNos, orgCodeList, active,currentInfo);
	}
	/**
	 * 按照来源单号和部门编码集合查询现金收款单信息
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-28 下午3:25:04
	 * @param waybillNos 运单号
	 * @param orgCodeList 应付部门编码集合
	 * @param active  是否有效
	 * @return
	 */
	@Override
	public List<BillCashCollectionEntity> queryCashinBySourceBillNOsAndOrgCodes(
			List<String> sourceBillNos, String sourceBillType,
			List<String> orgCodes, String active, CurrentInfo currentInfo) {	
		//来源单号不能为空，为空抛出异常信息
		if(CollectionUtils.isEmpty(sourceBillNos)){
			throw new SettlementException("按照来源单号集合和部门编码集合，查询现金收款单信息参数不能为空！");
		}
		return this.billReceivablePaymentQueryDao.queryBySourceBillNOsAndOrgCodes
				(sourceBillNos, sourceBillType, orgCodes, active,currentInfo);
		}
	

}
