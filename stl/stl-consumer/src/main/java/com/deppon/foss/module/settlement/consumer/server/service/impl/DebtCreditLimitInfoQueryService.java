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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/DebtCreditLimitInfoQueryService.java
 * 
 * FILE NAME        	: DebtCreditLimitInfoQueryService.java
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

import com.deppon.crm.inteface.foss.domain.HasUsedAmountInfo;
import com.deppon.crm.inteface.foss.domain.ReceiveCreditUsedRequest;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.foss.service.platformservice.CommonException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.deppon.foss.module.settlement.common.api.server.service.IJOBTimestampService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IDebtCreditLimitInfoQueryDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IDebtCreditLimitInfoQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.DebtCreditLimitInfoResultDto;

/**
 * 查询最早欠款客户已用额度信息Service实现
 * 
 * @author foss-zhangxiaohui
 * @date Jan 15, 2013 2:23:44 PM
 */
public class DebtCreditLimitInfoQueryService implements
		IDebtCreditLimitInfoQueryService
{

	/**
	 * 声明日志对象
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DebtCreditLimitInfoQueryService.class);

	/**
	 * 查询最早欠款客户已用额度
	 */
	private IDebtCreditLimitInfoQueryDao debtCreditLimitInfoQueryDao;
	
	/**
	 * 定时任务时间戳服务
	 */
	private IJOBTimestampService jobTimestampService;
	
	/**
	 * 最早欠款客户已用额度JOB CODE
	 */
	private static final String JOB_CODE = "SEND_DEBT_CREDIT_LIMIT_INFO";


	/**
	 * 开始查询并发送欠款客户已用额度信息
	 * 
	 * @author foss-zhangxiaohui
	 * @date Jan 15, 2013 2:26:07 PM
	 */
	@Override
	public void process()
	{
		Calendar cal = Calendar.getInstance();
		Date current = new Date();

		// JOB上次执行时间
		Date lastDate = jobTimestampService.getJOBTimestamp(JOB_CODE);

		// 如果上次执行时间为空，则新增一条;
		// 如果上次执行时间不为空，则更新
		if (lastDate == null) {
			jobTimestampService.addJOBTimestamp(JOB_CODE, current,
					"发送最早欠款及已用信用额度的客户信息");

			// 如果时间为空，默认上次执行为一个月前
			cal.add(Calendar.DATE, -31);
			lastDate = cal.getTime();
		} else {
			jobTimestampService.updateJOBTimestamp(JOB_CODE, current);
		}
		
		// 声明查询结果
		List<DebtCreditLimitInfoResultDto> resultDtoList = this
				.queryDebtCreditLimitInfo(lastDate,current);
		
		// 对查询结果进行非空判断
		if (CollectionUtils.isEmpty(resultDtoList))
		{
			// 直接返回
			return;
		}
		// 按十个一组来分割List
		List<List<DebtCreditLimitInfoResultDto>> lists = CollectionUtils
				.splitListBySize(resultDtoList, 1);
		// 遍历List
		for (List<DebtCreditLimitInfoResultDto> list : lists)
		{
			try
			{
				// 构造发送对象和表头
				ReceiveCreditUsedRequest request = buildRequest(list);
				AccessHeader header = buildHeader(list.get(0).getCustomerCode());

				ESBJMSAccessor.asynReqeust(header, request);
			}
			// 捕获异常
			catch (Exception e)
			{
				// 打印Log信息
				LOGGER.error(
						"查询并发送欠款客户已用额度信息出错DebtCreditLimitInfoQueryService process()"
								+ e.getMessage(), e);
			}
		}
	}

	private List<DebtCreditLimitInfoResultDto> queryDebtCreditLimitInfo(Date beginDate,Date endDate)
	{
		// 打印日志
		LOGGER.info("进入查询最早欠款客户已用额度信息DebtCreditLimitInfoQueryService queryDebtCreditLimitInfo方法");
		// 执行查询
		List<DebtCreditLimitInfoResultDto> resultDtoList = debtCreditLimitInfoQueryDao
				.queryDebtCreditLimitInfo(FossConstants.ACTIVE,beginDate,endDate);
		// 打印日志
		LOGGER.info("退出查询最早欠款客户已用额度信息DebtCreditLimitInfoQueryService queryDebtCreditLimitInfo方法");
		// 返回查询结果
		return resultDtoList;
	}

	/**
	 * 构建Request对象
	 * 
	 * @author foss-zhangxiaohui
	 * @date Jan 16, 2013 10:10:58 AM
	 */
	private ReceiveCreditUsedRequest buildRequest(
			List<DebtCreditLimitInfoResultDto> list) throws CommonException
	{
		// 声明一个已用额度的Request对象
		ReceiveCreditUsedRequest request = new ReceiveCreditUsedRequest();
		// 声明Request消息主体List
		List<HasUsedAmountInfo> hasUsedAmountList = request
				.getHasUsedAmountList();
		// 声明已用额度的Request的消息体对象
		HasUsedAmountInfo info = null;
		// 非空校验
		if (CollectionUtils.isNotEmpty(list))
		{
			// 遍历这个List
			for (DebtCreditLimitInfoResultDto dto : list)
			{
				// 每次遍历声明新的内存地址存放数据
				info = new HasUsedAmountInfo();
				if (StringUtils.isNotEmpty(dto.getCustomerCode()))
				{
					// set客户编码
					info.setCustNumber(dto.getCustomerCode());
				}
				if (StringUtils.isNotEmpty(dto.getCustomerName()))
				{
					// set客户名称
					info.setCustName(dto.getCustomerName());
				}
				if (null != dto.getUsedAmount())
				{
					// set已用额度
					info.setHasUsedAmount(dto.getUsedAmount());
				}
				if (null != dto.getBusinessDate()
						&& StringUtils.isNotEmpty(dto.getBusinessDate()
								.toString()))
				{
					// set最早发送日期
					info.setEarliestDebtDate(dto.getBusinessDate());
				}
				// 把info对象实体加到List
				hasUsedAmountList.add(info);
			}
		}
		// 返回结果
		return request;
	}

	/**
	 * 返回ESB请求头消息
	 * 
	 * @author foss-zhangxiaohui
	 * @date Jan 16, 2013 10:02:39 AM
	 */
	private AccessHeader buildHeader(String customerCode)
	{
		AccessHeader header = new AccessHeader();
		header.setBusinessId(customerCode);
		header.setEsbServiceCode(SettlementESBDictionaryConstants.ESB_FOSS2ESB_RECEIVE_CREDITUSED);
		header.setVersion(SettlementESBDictionaryConstants.ESB_HEADER__VERSION);
		header.setBusinessDesc1(SettlementESBDictionaryConstants.ESB_FOSS2ESB_RECEIVE_CREDITUSED_DESC);
		return header;
	}

	/**
	 * @param debtCreditLimitInfoQueryDao
	 */
	public void setDebtCreditLimitInfoQueryDao(
			IDebtCreditLimitInfoQueryDao debtCreditLimitInfoQueryDao)
	{
		this.debtCreditLimitInfoQueryDao = debtCreditLimitInfoQueryDao;
	}
	
	public void setJobTimestampService(IJOBTimestampService jobTimestampService)
	{
		this.jobTimestampService = jobTimestampService;
	}
}
