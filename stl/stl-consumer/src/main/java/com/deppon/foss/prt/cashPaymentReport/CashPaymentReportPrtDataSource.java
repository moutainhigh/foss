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
 * FILE PATH        	: src/main/java/com/deppon/foss/prt/cashPaymentReport/CashPaymentReportPrtDataSource.java
 * 
 * FILE NAME        	: CashPaymentReportPrtDataSource.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.prt.cashPaymentReport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICashPaymentReportQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashPaymentReportDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.NumberUtils;

/**
 * 现金支出报表打印类.
 * 
 * @author foss-zhangxiaohui
 * @date Dec 13, 2012 7:57:45 PM
 */
public class CashPaymentReportPrtDataSource implements JasperDataSource {

	/** 日志实体对象. */
	private static Logger LOGGER = LoggerFactory
			.getLogger(CashPaymentReportPrtDataSource.class);

	/** 现金报表支出分类金额显示结果List. */
	List<CashPaymentReportDto> dtoList;

	/** 现金报表支出显示结果List. */
	List<BillPaymentEntity> resultList;

	/**
	 * 填充打印内容.
	 *
	 * @param jasperContext the jasper context
	 * @return the map
	 * @throws Exception the exception
	 * @author foss-zhangxiaohui
	 * @date Jan 8, 2013 2:51:44 PM
	 */
	@Override
	public Map<String, Object> createParameterDataSource(
			JasperContext jasperContext) throws Exception {
		// 打印日志
		LOGGER.info("开始填充打印数据源");
		// 声明传值对象的Map
		Map<String, Object> parameter = new HashMap<String, Object>();
		// 判断jpsperContext对象及传查询条件的Map是否为空
		if (jasperContext == null || jasperContext.getParamMap() == null) {
			// 为空则抛出异常
			throw new SettlementException("现金支出报表打印参数不正确！");
		}
		// 判断查询的参数是否为空
		if (StringUtils.isBlank((String) jasperContext.get("startDate"))
				|| StringUtils.isBlank((String) jasperContext.get("endDate"))) {
			// 为空则抛出异常
			throw new SettlementException("查询参数不正确！");
		}
		// 声明Dto对象做查询参数的对象
		CashPaymentReportDto dto = new CashPaymentReportDto();
		// 获得开始日期
		String startDate = (String) jasperContext.get("startDate");
		// 获得结束日期
		String endDate = (String) jasperContext.get("endDate");
		//非空校验
		if(StringUtils.isEmpty(startDate)||StringUtils.isEmpty(endDate)){
			// 超过31天则抛出异常
			throw new SettlementException("开始日期和结束日期不能为空");
		}
		// 获得来源单据类型
		String sourceBillType = (String) jasperContext.get("sourceBillType");
		// 获得付款部门
		String paymentOrgCode = (String) jasperContext.get("paymentOrgCode");
		parameter.put("startDate", startDate
				+ SettlementConstants.PRINT_DATE_TIME_MERGE_OPERATOR + endDate);
		//jasperContext.getParamMap().put("startDate",
				//DateUtils.convert(startDate, DateUtils.DATE_FORMAT));
		//jasperContext.getParamMap().put("sourceBillType", sourceBillType);
		//jasperContext.getParamMap().put("paymentOrgCode", paymentOrgCode);
		// 从前台传入的Map值，直接转换成dto对象。
		//BeanUtils.populate(dto, jasperContext.getParamMap());
		
		dto.setSourceBillType(sourceBillType);
		dto.setPaymentOrgCode(paymentOrgCode);
		Date tempStartDate = DateUtils.convert(startDate,DateUtils.DATE_TIME_FORMAT);
		Date tempEndDate = DateUtils.convert(endDate,DateUtils.DATE_TIME_FORMAT);
		//设置开始日期
		dto.setStartDate(tempStartDate);
		dto.setEndDate(tempEndDate);
		// 处理结束日期，在原结束日期上加1
//		dto.setEndDate(DateUtils.convert(DateUtils.addDay(tempEndDate, 1),
//				DateUtils.DATE_FORMAT));
		// 判断开始日期和结束日期
		Long i = DateUtils.getTimeDiff(
				DateUtils.convert(dto.getStartDate(), DateUtils.DATE_TIME_FORMAT),
				DateUtils.convert(dto.getEndDate(), DateUtils.DATE_TIME_FORMAT));
		// 最大的天数,默认31天
		int maxDays = SettlementConstants.DATE_THREE_DAYS_WEEK;
		// 如果开始日期和结束日期相差是否超过31天
		if (i != null && i.longValue() > maxDays) {
			// 超过31天则抛出异常
			throw new SettlementException("查询开始日期和结束日期不能超过"
					+ SettlementConstants.DATE_THREE_DAYS_WEEK + "天！");
		}
		// 判断开始日期是否在结束日期之后
		if (dto.getEndDate().before(dto.getStartDate())) {
			// 如果开始日期在后则抛出异常
			throw new SettlementException("开始日期不能在结束日期之后");
		}

		// 构建用户
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		user.setEmployee(employee);
		// 构建部门
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(paymentOrgCode);
		//获取页面传过来的员工工号
		String empCode = (String) jasperContext.get("cInfo");
		// 获取用户、部门信息
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		//设置员工工号
		user.setEmpCode(empCode);
		//获取小票Service实例
		ICashPaymentReportQueryService cashPaymentReportQueryService = (ICashPaymentReportQueryService) jasperContext.getApplicationContext().getBean("cashPaymentReportQueryService");
		// 根据查询参数返回总金额之类的信息
		dtoList = cashPaymentReportQueryService.queryTotalRecordsInDB(dto,currentInfo);
		//非空判断查询结果
		if (CollectionUtils.isNotEmpty(dtoList)) {
			// 执行查询操作 查询应付单实体对象
			resultList = cashPaymentReportQueryService
					.queryCashPaymentReportByDto(dto,currentInfo);
			// 声明现金总金额
			BigDecimal totalCashAmount = BigDecimal.ZERO;
			// 声明电汇总金额
			BigDecimal totalTelegraphicAmount = BigDecimal.ZERO;
			// 声明银行卡总金额
			BigDecimal totalBankCardAmount = BigDecimal.ZERO;
			// 声明支票总金额
			BigDecimal totalChequeAmount = BigDecimal.ZERO;
			// 审核总金额
			BigDecimal totalAuditAmount = BigDecimal.ZERO;
			// 未审核总金额
			BigDecimal totalUnauditAmount = BigDecimal.ZERO;
			// 查询总记录条数不为零的话继续遍历查询结果得到总金额
			for (int j = 0; j < dtoList.size(); j++) {
				// 当付款方式为现金时
				if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH
						.equals(dtoList.get(j).getPaymentType())) {
					// 计算按现金付款的总金额并赋值到Vo
					totalCashAmount = NumberUtils.add(totalCashAmount, dtoList
							.get(j).getTotalAmount());
				}
				// 当付款方式为银行卡时
				if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD
						.equals(dtoList.get(j).getPaymentType())) {
					// 计算按银行卡付款的总金额并赋值到Vo
					totalBankCardAmount = NumberUtils.add(totalBankCardAmount,
							dtoList.get(j).getTotalAmount());
				}
				// 当付款方式为电汇时
				if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
						.equals(dtoList.get(j).getPaymentType())) {
					// 计算按电汇付款的总金额并赋值到Vo
					totalTelegraphicAmount = NumberUtils.add(
							totalTelegraphicAmount, dtoList.get(j)
									.getTotalAmount());
				}
				// 当付款方式为支票时
				if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE
						.equals(dtoList.get(j).getPaymentType())) {
					// 计算按支票付款的总金额并赋值到Vo
					totalChequeAmount = NumberUtils.add(totalChequeAmount,
							dtoList.get(j).getTotalAmount());
				}
				// 当单据状态为审核时来计算审核总金额
				if (SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE
						.equals(dtoList.get(j).getAuditStatus())) {
					// 计算按现金付款的总金额并赋值到Vo
					totalAuditAmount = NumberUtils.add(totalAuditAmount,
							dtoList.get(j).getTotalAmount());
				}
				// 当单据状态为未审核时来计算审核总金额
				if (SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT
						.equals(dtoList.get(j).getAuditStatus())) {
					totalUnauditAmount = NumberUtils.add(totalUnauditAmount,
							dtoList.get(j).getTotalAmount());
				}
			}
			// 现金总金额
			parameter.put("cashAmount", totalCashAmount);
			// 银行卡总金额
			parameter.put("bankCardAmount", totalBankCardAmount);
			// 电汇总金额
			parameter.put("telegraphAmount", totalTelegraphicAmount);
			// 支票总金额
			parameter.put("chequeAmount", totalChequeAmount);
			// 审核总金额
			parameter.put("auditAmount", totalAuditAmount);
			// 未审核总金额
			parameter.put("unauditAmount", totalUnauditAmount);
		}
		return parameter;
	}

	/**
	 * 填充打印内容.
	 *
	 * @param jasperContext the jasper context
	 * @return the list
	 * @throws Exception the exception
	 * @author foss-zhangxiaohui
	 * @date Jan 8, 2013 2:51:44 PM
	 */
	@Override
	public List<Map<String, Object>> createDetailDataSource(
			JasperContext jasperContext) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		// 遍历查询结果List
		for (int i = 0; i < resultList.size(); i++) {
			// 声明一个付款单实体对象来取集合的子集
			BillPaymentEntity entity = resultList.get(i);
			// 声明一个Map来存遍历出来的子集
			Map<String, Object> map = new HashMap<String, Object>();
			// 对子集非空判断
			if (null != entity) {
				// 添加付款单号
				map.put("paymentNo", entity.getPaymentNo());
				// 添加付款公司
				map.put("paymentCompanyName", entity.getPaymentCompanyName());
				// 添加付款部门
				map.put("paymentOrgName", entity.getPaymentOrgName());
				// 添加记账日期
				//map.put("accountDate", sdf.format(entity.getAccountDate()));
				map.put("accountDate",DateUtils.convert(entity.getAccountDate(), "yyyy-MM-dd "));
				// 添加首款客户
				map.put("payeeName", entity.getPayeeName());
				// 添加金额
				map.put("amount", entity.getAmount());
				// 添加付款方式
				map.put("paymentType", changePaymentTypeToName(entity.getPaymentType()));
				// 添加来源单据类型
				map.put("sourceBillType", changeBillTypeToName(entity.getSourceBillType()));
				// 添加单据状态
				map.put("remitStatus", changeRemitStatusToName(entity.getRemitStatus()));
				// 添加版本号
				map.put("versionNo", entity.getVersionNo());
			}
			// 添加Map到List里面
			list.add(map);
		}
		// 返回结果
		return list;
	}
	
	/**
	 * 单据类型转换
	 * 
	 * @author foss-zhangxiaohui
	 * @date 2013-4-2 下午7:20:42
	 */
	private String changeBillTypeToName(String billType){
		//判断是否是应付单
		if(StringUtils.equals(billType, SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT)){
			//返回应付单
			return "应付单";
		}
		//判断是否是预收单
		else if(StringUtils.equals(billType, SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED)){
			//返回预收单
			return "预收单";
		}
		//判断是否是对账单
		else if(StringUtils.equals(billType, SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__STATEMENT)){
			//返回对账单
			return "对账单";
		}
		//判断是否是外发单
		else if(StringUtils.equals(billType, SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__PARTIAL_LINE)){
			//返回外发单
			return "外发单";
		}
		else{
			//返回未知单据子类型
			return "未知单据子类型";
		}
	}
	
	/**
	 * 付款方式编码转换
	 * 
	 * @author foss-zhangxiaohui
	 * @param 
	 * @date Jan 9, 2013 11:03:03 AM
	 * @return 
	 */
	private String changePaymentTypeToName(String paymentType) {
		//判断是否是现金
		if (StringUtils.equals(paymentType, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH)) {
			//返回现金
			return "现金";
		} 
		//判断是否是银行卡
		else if (StringUtils.equals(paymentType, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD)) {
			//返回银行卡
			return "银行卡";
		}
		//判断是否是电汇
		else if (StringUtils.equals(paymentType, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER)) {
			//返回电汇
			return "电汇";
		}
		//判断是否是支票
		else if (StringUtils.equals(paymentType, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE)) {
			//返回支票
			return "支票";
		}
		//判断是否是网上支付
		else if (StringUtils.equals(paymentType, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE)) {
			//返回网上支付
			return "网上支付";
		}
		//判断是否是月结
		else if (StringUtils.equals(paymentType, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT)) {
			//返回月结
			return "月结";
		}
		//判断是否是临时欠款
		else if (StringUtils.equals(paymentType, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT)) {
			//返回临时欠款
			return "临时欠款";
		}
		//判断是否是到付
		else if (StringUtils.equals(paymentType, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT)) {
			//返回到付
			return "到付";
		}
		//不符合所有类型则返回为未知
		else {
			return "未知收款类别";
		}
	}
	
	/**
	 * 汇款状态转换
	 * 
	 * @author foss-zhangxiaohui
	 * @date Mar 20, 2013 2:35:33 PM
	 */
	private String changeRemitStatusToName(String remitStatus){
		//判断是否是未汇款
		if(StringUtils.equals(remitStatus, SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER)){
			//返回未汇款
			return "未汇款";
		}
		//判断是否是汇款中
		else if(StringUtils.equals(remitStatus, SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING)){
			//返回汇款中
			return "汇款中";
		}
		//判断是否是已汇款
		else if(StringUtils.equals(remitStatus, SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRED)){
			//返回已汇款
			return "已汇款";
		}
		else{
			//返回位置汇款状态
			return "未知汇款状态";
		}
	}
	
}
