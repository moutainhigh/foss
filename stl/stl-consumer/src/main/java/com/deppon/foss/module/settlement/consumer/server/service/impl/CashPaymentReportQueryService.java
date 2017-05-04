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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/CashPaymentReportQueryService.java
 * 
 * FILE NAME        	: CashPaymentReportQueryService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICashPaymentReportQueryDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICashPaymentReportQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashPaymentReportDto;

/**
 * 现金支出报表Service实现
 * 
 * @author foss-zhangxiaohui
 * @date Dec 11, 2012 4:54:50 PM
 */
public class CashPaymentReportQueryService implements
		ICashPaymentReportQueryService {

	/**
	 * 日志打印对象声明
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CashPaymentReportQueryService.class);

	/**
	 * 现金支出报表查询Dao
	 */
	private ICashPaymentReportQueryDao cashPaymentReportQueryDao;

	/**
	 * 根据Dto分页查询付款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 11, 2012 5:06:37 PM
	 */
	@Override
	public List<BillPaymentEntity> queryCashPaymentReportByDto(int offset,
			int start, CashPaymentReportDto dto, CurrentInfo cInfo) {
		// 输入参数验证
		this.validateInputParameters(dto, cInfo);
		//设置数据权限
		dto.setEmpCode(cInfo.getEmpCode());
		// Service执行的Log
		LOGGER.debug("entering service, SourceBillType: "
				+ dto.getSourceBillType());
		// 执行查询操作并赋值
		List<BillPaymentEntity> list = cashPaymentReportQueryDao
				.queryCashPaymentReportByDto(offset, start, dto);
		// Service执行的Log
		LOGGER.debug("Successfully eixting service, SourceBillType: "
				+ dto.getSourceBillType());
		// 返回查询结果
		return list;
	}

	/**
	 * 根据Dto查询符合条件的数据总记录条数
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 12, 2012 4:38:37 PM
	 */
	@Override
	public List<CashPaymentReportDto> queryTotalRecordsInDB(
			CashPaymentReportDto dto, CurrentInfo cInfo) {
		// 验证输入参数
		this.validateInputParameters(dto, cInfo);
		//设置数据权限
		dto.setEmpCode(cInfo.getEmpCode());
		// Service执行的Log
		LOGGER.debug("entering service, SourceBillType: "
				+ dto.getSourceBillType());
		// 执行查询操作并赋值
		List<CashPaymentReportDto> list = cashPaymentReportQueryDao
				.queryTotalRecordsInDB(dto);
		// Service执行的Log
		LOGGER.debug("Successfully eixting service, SourceBillType: "
				+ dto.getSourceBillType());
		// 返回查询结果
		return list;
	}

	/**
	 * 根据Dto查询付款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 11, 2012 5:06:37 PM
	 */
	@Override
	public List<BillPaymentEntity> queryCashPaymentReportByDto(
			CashPaymentReportDto dto, CurrentInfo cInfo) {
		// 输入参数验证
		this.validateInputParameters(dto, cInfo);
		//设置数据权限
		dto.setEmpCode(cInfo.getEmpCode());
		// Service执行的Log
		LOGGER.debug("entering service, SourceBillType: "
				+ dto.getSourceBillType());
		// 执行查询操作并赋值
		List<BillPaymentEntity> list = cashPaymentReportQueryDao
				.queryCashPaymentReportByDto(dto);
		// Service执行的Log
		LOGGER.debug("Successfully eixting service, SourceBillType: "
				+ dto.getSourceBillType());
		// 返回查询结果
		return list;
	}

	/**
	 * 验证输入的参数
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 26, 2012 4:38:37 PM
	 */
	private void validateInputParameters(CashPaymentReportDto dto,
			CurrentInfo cInfo) {
		// 查询的参数不能为空
		if (null == dto) {
			// 查询的参数为空则抛出异常
			throw new SettlementException("查询条件不能为空");
		}
		// 开始日期非空校验
		if (null == dto.getStartDate()) {
			// 开始日期为空则抛出异常
			throw new SettlementException("开始日期不能为空");
		}
		// 结束日期非空校验
		if (null == dto.getEndDate()) {
			// 结束日期为空则抛出异常
			throw new SettlementException("结束日期不能为空");
		}
		// 判断开始日期是否小于结束日期
		if (dto.getStartDate() != null && dto.getEndDate() != null) {
			Date startDate = DateUtils.truncate(dto.getStartDate(),
					Calendar.SECOND);
			Date endDate = DateUtils
					.truncate(dto.getEndDate(), Calendar.SECOND);
			// 判断开始日期是否在结束日期之后
			if (startDate.after(endDate)) {
				// 如果开始日期在结束日期之后则抛出异常
				throw new SettlementException("开始日期大于结束日期！");
			}
		}
	}

	/**
	 * @param cashPaymentReportQueryDao
	 */
	public void setCashPaymentReportQueryDao(
			ICashPaymentReportQueryDao cashPaymentReportQueryDao) {
		this.cashPaymentReportQueryDao = cashPaymentReportQueryDao;
	}
}
