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
 * PROJECT NAME	: stl-pay
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/pay/server/service/impl/BillRepaymentManageService.java
 * 
 * FILE NAME        	: BillRepaymentManageService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.deppon.foss.module.settlement.common.api.server.service.*;
import com.deppon.foss.module.settlement.common.api.shared.dto.OnlinePayInfoDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementConfReceiptEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.WithholdStatusDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.ListComparator;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IGrayCustomerService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.GrayCustomerEntity;
import com.deppon.foss.module.settlement.pay.api.server.dao.IBillRepaymentManageDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentManageService;
import com.deppon.foss.module.settlement.pay.api.server.service.IFossToFinanceRemittanceService;
import com.deppon.foss.module.settlement.pay.api.server.service.ISoaRepaymentManageService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.SoaRepaymentEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IReceivableStatementService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementModifyService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementReceiptService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IWoodenStatementService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.StatementRecivableEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 还款单服务
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-16 下午2:13:56
 */
public class BillRepaymentManageService implements IBillRepaymentManageService {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(BillRepaymentManageService.class);

	/**
	 * 业务日期常量
	 */
	private static final  String QUERY_BUSINESSDATE_FLAG = "1";

	/**
	 * 记账日期常量
	 */
	private static final String QUERY_ACCOUNTDATE_FLAG = "2";
	
	// 还款单dao
	private IBillRepaymentManageDao billRepaymentManageDao;

	/**
	 * 还款单公共service
	 */
	private IBillRepaymentService billRepaymentService;

	/**
	 * 核销/反核销公共service
	 */
	private IBillWriteoffService billWriteoffService;

	/**
	 * 对账单service
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 结算通用服务service
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 对账单明细service
	 */
	private IStatementOfAccountDService statementOfAccountDService;

	/**
	 * 应收单service
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 应付单service
	 */
	private IBillPayableService billPayableService;

	/**
	 * 预收单service
	 */
	private IBillDepositReceivedService billDepositReceivedService;

	/**
	 * 预付单service
	 */
	private IBillAdvancedPaymentService billAdvancedPaymentService;

	/**
	 * 对账单回执service
	 */
	private IStatementReceiptService statementReceiptService;

	/**
	 * 操作日志service
	 */
	private IOperatingLogService operatingLogService;

	/**
	 * 对账单还款单关系service
	 */
	private ISoaRepaymentManageService soaRepaymentManageService;

	/**
	 * 汇款服务接口service
	 */
	private IFossToFinanceRemittanceService fossToFinanceRemittanceService;

	/**
	 * 系统配置参数 Service接口
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 注入组织service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 注入组织管理service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private IWoodenStatementService woodenStatementService;
	
	private IStatementModifyService statementModifyService;

    /**
     * 网上支付处理代理
     */
    private IFinanceOnlinePayWSProxy financeOnlinePayWSProxy;
    /**
     * 注入POS服务service
     * @author 309603 yangqiang
     * @date 2016-02-23 
     */
    private IPdaPosManageService pdaPosManageService;
    
    /**
	 * 灰名单服务
	 */
	private IGrayCustomerService grayCustomerService;
	
	/**
	 * 更新PTP扣款状态
	 */
	 private ISendStatusToPtpService sendStatusToPtpService;
	 
	/**
     * 合伙人收款对账单services
     */
    private IReceivableStatementService receivableStatementService;
	
    /**
	 * 查询还款单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-2 下午4:43:36
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentManageService#queryBillRepayment(com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto)
	 */
	@Override
	public BillRepaymentManageResultDto queryBillRepayment(
			BillRepaymentManageDto billRepayDto, int start, int limit,CurrentInfo currentInfo) {
		
		//查询条件校验
		cheackQueryParm(billRepayDto);
		
		// 设置查询条件
		setQueryParam(billRepayDto);
		
		// 按大区、小区、部门查询权限
		setSearchArea(billRepayDto,currentInfo);
		
		LOGGER.info("查询还款单 enter service.........");
		
		// 查询还款单、分页符合条件的还款单、还款单返回dto
		List<BillRepaymentManageDto> billRepayQueryList = null;
		List<BillRepaymentManageDto> billRepayQListByPage = null;
		BillRepaymentManageResultDto billRepayMRenDto = new BillRepaymentManageResultDto();
		
		
		// 判断是按对账单查询
		if (SettlementConstants.TAB_QUERY_BY_DZ_BILL_NO.equals(billRepayDto.getQueryType())) {
			
			billRepayQueryList = billRepaymentManageDao.queryBillRepaymentByStNos(billRepayDto);

			// 增加大区小区
			addAreas(billRepayQueryList);
			
			billRepayMRenDto.setBillRepaymentQueryList(billRepayQueryList);
			billRepayMRenDto.setTotalCount(billRepayQueryList.size());
			// 判断是否按照还款单查询
		} else if (SettlementConstants.TAB_QUERY_BY_REPAYMENT_NO.equals(billRepayDto.getQueryType())) {
			billRepayQueryList = billRepaymentManageDao.queryBillRepaymentByNos(billRepayDto);
			// 增加大区小区
			addAreas(billRepayQueryList);
			billRepayMRenDto.setBillRepaymentQueryList(billRepayQueryList);
			billRepayMRenDto.setTotalCount(billRepayQueryList.size());
			// 判断是否按照运单查询
		} else if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(billRepayDto.getQueryType())) {
			billRepayQueryList = billRepaymentManageDao.queryBillRepaymentByWbNos(billRepayDto);
			// 增加大区小区
			addAreas(billRepayQueryList);
			billRepayMRenDto.setBillRepaymentQueryList(billRepayQueryList);
			billRepayMRenDto.setTotalCount(billRepayQueryList.size());
		// 按来源单号
		} else if (SettlementConstants.TAB_QUERY_BY_SOURCE_BILL_NO.equals(billRepayDto.getQueryType())) {
			billRepayQueryList = billRepaymentManageDao.queryBySourceBillNOs(billRepayDto);
			// 增加大区小区
			addAreas(billRepayQueryList);
			billRepayMRenDto.setBillRepaymentQueryList(billRepayQueryList);
			billRepayMRenDto.setTotalCount(billRepayQueryList.size());
		
			//按银联交易流水号查询
		} else if (SettlementConstants.TAB_QUERY_BY_LANDSTOWAGE_NO.equals(billRepayDto.getQueryType())) {
			billRepayQueryList = billRepaymentManageDao.queryByBatchBillNOs(billRepayDto);
			// 增加大区小区
			addAreas(billRepayQueryList);
			billRepayMRenDto.setBillRepaymentQueryList(billRepayQueryList);
			billRepayMRenDto.setTotalCount(billRepayQueryList.size());
			// 按日期查询
		} else if (SettlementConstants.TAB_QUERY_BY_DATE.equals(billRepayDto.getQueryType())) {

			// 按业务日期或者是记账日期查询
			// 按业务日期查询
			if (QUERY_BUSINESSDATE_FLAG.equals(billRepayDto.getQueryDateFlag())) {
				
				LOGGER.info("还款单业务日期查询.........");
				
				billRepayMRenDto = billRepaymentManageDao.queryCountBillBusinessDateRepayment(billRepayDto);
				
				if (billRepayMRenDto.getTotalCount() > 0) {
					// 按照业务日期查询
					billRepayQListByPage = billRepaymentManageDao.queryBillBusinessDateRepayment(billRepayDto,start, limit);
					// 增加大区小区
					addAreas(billRepayQListByPage);

					// 将分页的结果集，设置到返回对象中
					billRepayMRenDto.setBillRepaymentQueryList(billRepayQListByPage);
					LOGGER.info("还款单业务日期查询 successfully.........");
				}
				// 按记账日期查询
			} else if (QUERY_ACCOUNTDATE_FLAG.equals(billRepayDto.getQueryDateFlag())) {
				LOGGER.info("还款单记账日期查询.........");
				// 查询记账日期条数
				billRepayMRenDto = billRepaymentManageDao.queryCountBillAccountDateRepayment(billRepayDto);
				// 条数大于0
				if (billRepayMRenDto.getTotalCount() > 0) {
					// 查询记账日期列表
					billRepayQListByPage = billRepaymentManageDao.queryBillAccountDateRepayment(billRepayDto, start,limit);
					
					// 增加大区小区
					addAreas(billRepayQListByPage);

					// 将分页的结果集，设置到返回对象中
					billRepayMRenDto.setBillRepaymentQueryList(billRepayQListByPage);
					LOGGER.info("还款单记账日期查询 successfully.........");
				}
			}
		}
		getRepayTotalAmount(billRepayQueryList,billRepayMRenDto);
		LOGGER.info("查询还款单 successfully exit Service.class.........");
		return billRepayMRenDto;
	}
	
	
	
	/**
	 * 计算总金额
	 * @author 095793-foss-LiQin
	 * @date 2013-1-23 下午5:21:35
	 */
	private void getRepayTotalAmount(List<BillRepaymentManageDto> billRepayQueryList,BillRepaymentManageResultDto billRepayMRenDto){
		// 循环计算核销总金额，并设置到返回对象中
		BigDecimal repayTotalAmount = BigDecimal.ZERO;
		// 实际金额
		BigDecimal repayTrueAmount = BigDecimal.ZERO;
		// 反核销金额
		BigDecimal reBVeToAmount = BigDecimal.ZERO;
		if (CollectionUtils.isNotEmpty(billRepayQueryList)) {
			for (BillRepaymentManageDto entity : billRepayQueryList) {
				repayTotalAmount = repayTotalAmount.add(entity.getAmount());
				repayTrueAmount = repayTrueAmount.add(entity.getTrueAmount());
				reBVeToAmount = reBVeToAmount.add(entity.getBverifyAmount());
			}
			// 总金额
			billRepayMRenDto.setTotalAmount(repayTotalAmount);
			// 实际金额
			billRepayMRenDto.setTotalTrueAmount(repayTrueAmount);
			// 反核销金额
			billRepayMRenDto.setTotalBVerifyAmount(reBVeToAmount);
		}
	}
	
	
	/**
	 * 还款单按设置区域（大区、小区）、部门查询条件 大区下有多个部门、小区下多个部门或按指定部门查询
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2013-1-12 下午5:03:26
	 */
	private void setSearchArea(BillRepaymentManageDto billRepayDto,
			CurrentInfo currentInfo) {
		//设置当前登录用户员工编码
		billRepayDto.setEmpCode(currentInfo.getEmpCode());
		// 当前操作部门，查询时最终可以查询的部门
		List<String> orgList = new ArrayList<String>();
		// 如果指定部门按照部门查询
		if (StringUtils.isNotEmpty(billRepayDto.getCollectionOrgCode())) {
			orgList.add(billRepayDto.getCollectionOrgCode());

			// 如果按小区查询获取小区下所有部门
		} else if (StringUtils.isEmpty(billRepayDto.getCollectionOrgCode())
				&& StringUtils.isNotEmpty(billRepayDto.getSmallArea())) {
			//查询小区
			List<OrgAdministrativeInfoEntity> smallOrgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(billRepayDto.getSmallArea());
			// 添加小区能够查询的权限
			if (CollectionUtils.isNotEmpty(smallOrgList)) {
				for (OrgAdministrativeInfoEntity entity : smallOrgList) {
					orgList.add(entity.getCode());
				}
			}

			// 如果按部门空、小区为空查询获取大区下所有部门
		} else if (StringUtils.isEmpty(billRepayDto.getCollectionOrgCode())&& StringUtils.isEmpty(billRepayDto.getSmallArea())&& StringUtils.isNotEmpty(billRepayDto.getLargeArea())) {
			//查询所有的大区
			List<OrgAdministrativeInfoEntity> bigOrgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(billRepayDto.getLargeArea());
			if (CollectionUtils.isNotEmpty(bigOrgList)) {
				// 添加大区下当前登录用户
				for (OrgAdministrativeInfoEntity entity : bigOrgList) {
					orgList.add(entity.getCode());
				}
			}
		}
		if (CollectionUtils.isNotEmpty(orgList)) {
			billRepayDto.setCollectionOrgCodeList(orgList);
		}
	}
	
	
	
	
	/**
	 * 增加大区小区
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-11 下午4:26:13
	 * @param resultList
	 */
	private void addAreas(List<BillRepaymentManageDto> resultList){
		
		//如果为空，则直接返回
		if(CollectionUtils.isEmpty(resultList)){
			return ;
		}
		//声明小区编码
		String smallAreaCode = null;
		//小区
		Map<String,Object> smallAreaMap = new HashMap<String,Object>();
		//大区
		Map<String,Object> largeMap = new HashMap<String,Object>();
		
		for(BillRepaymentManageDto entity:resultList){
			//声明部门编码
			String deptCode = entity.getCollectionOrgCode();
			//判断还款部门编码
			if(StringUtils.isBlank(deptCode)){
				return ;
			}
			//获取小区名称
			if(smallAreaMap.get(entity.getCollectionOrgCode())!=null){
				entity.setSmallArea((String) smallAreaMap.get(entity.getCollectionOrgCode()));// 大区
			}else{
				//声明小区
				List<String> bizTypes = new ArrayList<String>();
				//添加类型为小区类型				
				bizTypes.add(BizTypeConstants.ORG_SMALL_REGION);
				
				//获取营业区
				OrgAdministrativeInfoEntity smallOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(deptCode, bizTypes);
				//非空判断
				if(smallOrg!=null){
					entity.setSmallArea(smallOrg.getName());
					smallAreaMap.put(entity.getCollectionOrgCode(),smallOrg.getName());
					//设置小区编码
					smallAreaCode = smallOrg.getCode();
				}
			}
			
			//如果map中没有该大区，则需要去根据小区查询
			if(largeMap.get(entity.getCollectionOrgCode())!=null){
				entity.setLargeArea((String) largeMap.get(entity.getCollectionOrgCode()));// 大区
			}else{
				//声明大区
				List<String> bizTypes = new ArrayList<String>();
				//设置List类型为大区类型				
				bizTypes.add(BizTypeConstants.ORG_BIG_REGION);
				//获取营业区
				OrgAdministrativeInfoEntity bigOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(deptCode, bizTypes);
				//大区非空判断
				if(bigOrg!=null){
					entity.setLargeArea(bigOrg.getName());
					largeMap.put(entity.getCollectionOrgCode(), bigOrg.getName());
				}
			}
		}
	}

	/**
	 * 设置按日期查询，参数
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-7 上午11:33:46
	 */
	private void setQueryParam(BillRepaymentManageDto billRepayDto) {
		// 还款单按日期查询
		if (SettlementConstants.TAB_QUERY_BY_DATE.equals(billRepayDto
				.getQueryType())) {
			// 设置执行时业务如期小于结束日期+1
			if (QUERY_BUSINESSDATE_FLAG.equals(billRepayDto.getQueryDateFlag())) {
				Date dateTemp = DateUtils.addDays(
						billRepayDto.getBusinessEndDate(), 1);
				billRepayDto.setBusinessEndDate(dateTemp);
				// 设置执行时使用记账日期小于结束日期+1天
			} else if (QUERY_ACCOUNTDATE_FLAG.equals(billRepayDto
					.getQueryDateFlag())) {
				Date dateTemp = DateUtils.addDays(
						billRepayDto.getAccountEndDate(), 1);
				billRepayDto.setAccountEndDate(dateTemp);
			}
			// 还款单按运单查询
		} else if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(billRepayDto.getQueryType())) {
			// 核销单的单据类型还款冲应收
			List<String> writeoffType = new ArrayList<String>();
			writeoffType.add(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE);
			billRepayDto.setWriteoffType(writeoffType);
			
			//根据来源单号查询			
		}else if(SettlementConstants.TAB_QUERY_BY_SOURCE_BILL_NO.equals(billRepayDto.getQueryType())){
			// 核销单的单据类型还款冲应收
			List<String> writeoffType = new ArrayList<String>();
			writeoffType.add(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE);
			billRepayDto.setWriteoffType(writeoffType);
		}
		//查询有效地还款单
	}
	
	
	
	/**
	 * 查询时，检验参数
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2013-1-24 上午9:19:40
	 */
	private void cheackQueryParm(BillRepaymentManageDto billRepayDto) {
		if (SettlementConstants.TAB_QUERY_BY_DATE.equals(billRepayDto.getQueryType())) {
			if (QUERY_BUSINESSDATE_FLAG.equals(billRepayDto.getQueryDateFlag())) {
				if (null == billRepayDto.getBusinessStartDate()) {
					throw new SettlementException("还款单按业务日期查询异常,业务开始日期不能为空!",
							"");
				}
				if (null == billRepayDto.getBusinessEndDate()) {
					throw new SettlementException("还款单按记账日期查询异常,业务结束日期不能为空!",
							"");
				}
				// 设置执行时使用记账日期小于结束日期+1天
			} else if (QUERY_ACCOUNTDATE_FLAG.equals(billRepayDto
					.getQueryDateFlag())) {
				if (null == billRepayDto.getAccountStartDate()) {
					throw new SettlementException("还款单按业务日期查询异常,记账开始日期不能为空!",
							"");
				}
				if (null == billRepayDto.getAccountEndDate()) {
					throw new SettlementException("还款单按记账日期查询异常,记账结束日期不能为空!",
							"");
				}
			}
			
		} else if (SettlementConstants.TAB_QUERY_BY_REPAYMENT_NO
				.equals(billRepayDto.getQueryType())) {

			if (CollectionUtils.isEmpty(billRepayDto.getRepaymentNos())) {
				throw new SettlementException("还款单按还款单号查询异常,还款单号不能为空!", "");
			}
		} else if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO
				.equals(billRepayDto.getQueryType())) {

			if (CollectionUtils.isEmpty(billRepayDto.getWayBillNos())) {
				throw new SettlementException("还款单按运单号查询异常,运单号不能为空!", "");
			}
		} else if (SettlementConstants.TAB_QUERY_BY_SOURCE_BILL_NO
				.equals(billRepayDto.getQueryType())) {

			if (CollectionUtils.isEmpty(billRepayDto.getSourceBillNos())) {
				throw new SettlementException("还款单按来源单号查询异常,来源单号不能为空!", "");
			}
		} else if (SettlementConstants.TAB_QUERY_BY_DZ_BILL_NO
				.equals(billRepayDto.getQueryType())) {

			if (CollectionUtils.isEmpty(billRepayDto.getStatementBillNos())) {
				throw new SettlementException("还款单按对账单号查询异常,对账单号不能为空!", "");
			}
		}else if(SettlementConstants.TAB_QUERY_BY_LANDSTOWAGE_NO.equals(billRepayDto.getQueryType())){
			if (CollectionUtils.isEmpty(billRepayDto.getBatchNos())) {
				//抛出异常
				throw new SettlementException("按流水号查询还款单时,流水号不能为空!");
			}
		}
	}
	

	/**
	 * 导出还款单查询
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-6 下午3:35:47
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentManageService#queryExportBillRepayment(com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public HSSFWorkbook queryExportBillRepayment(
			BillRepaymentManageDto billRepayDto, CurrentInfo cInfo) {
		/**
		 * 如果导出列头名称不存在，则抛出异常提示
		 */
		if (billRepayDto.getArrayColumnNames() == null
				|| billRepayDto.getArrayColumnNames().length == 0) {
			throw new SettlementException("导出Excel的列头名称不存在，请至少存在一列!");
		}
		/**
		 * 如果导出列头名称不存在，则抛出异常提示
		 */
		if (billRepayDto.getArrayColumns() == null
				|| billRepayDto.getArrayColumns().length == 0) {
			throw new SettlementException("导出Excel的列头不存在，请至少存在一列!");
		}
		// 调用执行方法，获取结果集
		BillRepaymentManageResultDto resultDto = queryBillRepayment(
				billRepayDto, 0, Integer.MAX_VALUE,cInfo);
		// 判断要导出数据是否存在，若不存在，则抛出异常提示
		if (resultDto == null || resultDto.getBillRepaymentQueryList() == null
				|| resultDto.getBillRepaymentQueryList().size() == 0) {
			throw new SettlementException("没有要导出的数据!");
		}
		// 将要导出东西封装转化成Excel导出要用的List<List<String>> 格式
		List<List<String>> rowList = convertListFormEntity(
				resultDto.getBillRepaymentQueryList(),
				billRepayDto.getArrayColumns());

		// 获取导出数据
		SheetData data = new SheetData();
		// 设置导出列头
		data.setRowHeads(billRepayDto.getArrayColumnNames());
		data.setRowList(rowList);
		// 获取平台提供导出函数
		ExcelExport export = new ExcelExport();
		// 返回wookbook
		HSSFWorkbook wookbook = export.exportExcel(data, "sheet",
				SettlementConstants.EXPORT_MAX_COUNT);
		return wookbook;
	}

	/**
	 * list的实体转化成list<list<String>
	 * 
	 * @author 095793
	 * @@date 2012-12-6 下午3:35:47
	 */
	private List<List<String>> convertListFormEntity(
			List<BillRepaymentManageDto> list, String[] header) {
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.BILL_PAYABLE__APPROVE_STATUS);
		termCodes.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);
		termCodes.add(DictionaryConstants.SETTLEMENT__IS_RED_BACK);
		termCodes.add(DictionaryConstants.FOSS_ACTIVE);
		termCodes.add(DictionaryConstants.FOSS_BOOLEAN);
		termCodes.add(DictionaryConstants.SETTLEMENT__CREATE_TYPE);
		termCodes.add(DictionaryConstants.SETTLEMENT__CURRENCY_CODE);
		termCodes.add(DictionaryConstants.BILL_REPAYMENT__STATUS);
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		
		// 循环进行封装
		for (BillRepaymentManageDto entity : list) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			for (String columnName : header) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(
						BillRepaymentManageDto.class, columnName);
				if (field != null) {
					// 通过传入字段获取值
					ReflectionUtils.makeAccessible(field);
					Object fieldValue = ReflectionUtils.getField(field, entity);
					// //如果为日期，需要进行格式化
					if (Date.class.equals(field.getType())
							&& fieldValue != null) {
						fieldValue = com.deppon.foss.util.DateUtils.convert(
								(Date) fieldValue, "yyyy-MM-dd HH:mm:ss");
					}
					// 将字段封装到list中
					if (fieldValue != null) {
						// 如果为审核状态，则需要转化
						if (columnName.equals("auditStatus")) {
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map, 
									DictionaryConstants.BILL_PAYABLE__APPROVE_STATUS,
									fieldValue.toString());
						}
						// 还款方式
						if (columnName.equals("paymentType")) {
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.SETTLEMENT__PAYMENT_TYPE,
									fieldValue.toString());
						}
						//是否红单						
						if (columnName.equals("isRedBack")) {
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map, 
									DictionaryConstants.SETTLEMENT__IS_RED_BACK,
											fieldValue.toString());
						}
						//是否有效						
						if (columnName.equals("active")) {
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.FOSS_ACTIVE,
									fieldValue.toString());
						}
						//是否初始化						
						if(columnName.equals("isInit")){
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.FOSS_BOOLEAN,
									fieldValue.toString());
						}
						
						//生成方式					
						if(columnName.equals("createType")){
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.SETTLEMENT__CREATE_TYPE,
									fieldValue.toString());
						}
						
						//币种						
						if(columnName.equals("currencyCode")){
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.SETTLEMENT__CURRENCY_CODE,fieldValue.toString());
						}
						
						//来源单据类型					
						if(columnName.equals("sourceBillType")){
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.BILL_REPAYMENT_SOURCE_BILL_TYPE,
									fieldValue.toString());
						}
						//来源单据类型					
						if(columnName.equals("status")){
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.BILL_REPAYMENT__STATUS,
									fieldValue.toString());
						}
						
						rowList.add(fieldValue.toString());
					} else {
						rowList.add(null);
					}
				}
			}
			sheetList.add(rowList);
		}
		return sheetList;
	}

	/**
	 * 查询还款单明细
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-2 下午4:43:36
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentManageService#queryBillRepaymentDetail()
	 */
	@Override
	public List<BillWriteoffEntity> queryBillWriteoffEntityDetail(
			String repaymentNo) {
		LOGGER.debug("查询还款单的核销明细,还款单号：" + repaymentNo);
		
		
		// 判断还款单明细单号是否合法
		if (StringUtils.isEmpty(repaymentNo.trim())) {
			throw new SettlementException("没有选中的还款单号!");
		}

		return billWriteoffService.queryByRepayment(repaymentNo,null);
	}

	/**
	 * 审核还款单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-2 下午4:43:36
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentManageService#auditBillRepayment(com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto)
	 */
	@Override
	@Transactional
	public BillRepaymentManageResultDto auditBillRepayment(
			BillRepaymentManageDto billRepaymentManageDto, CurrentInfo cInfo) {

		LOGGER.info("审核还款单开始...");

		// 根据传入的还款单字串，生产还款单集合
		List<String> repaymentNos = getRepaymentNos(billRepaymentManageDto
				.getSelectBillRepayNos());

		// 根据还款单号查询还款单列表
		List<BillRepaymentEntity> list = billRepaymentService
				.queryByRepaymentNOs(repaymentNos, null);

		if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException("没有符合条件的单据!");
		}

		//生成待审核还款单列表
		List<BillRepaymentEntity> toAduitList = new ArrayList<BillRepaymentEntity>();
		
		
		// 验证是否可以审核
		for (BillRepaymentEntity entity : list) {
			LOGGER.info("审核还款单,还款单号：" + entity.getRepaymentNo());
			
			//只有还款金额>0的才可以审核反审核
			if(entity.getAmount().compareTo(BigDecimal.ZERO)>0){
				
				// 无效的还款单不能被审核
				if (FossConstants.INACTIVE.equals(entity.getActive())) {
					throw new SettlementException("还款单号：" + entity.getRepaymentNo()
							+ "无效的还款单不能被审核！", "");
				}
				// 已审核的单据不能被再次审核
				if (entity.getAuditStatus() != null
						&& entity
								.getAuditStatus()
								.equals(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__AUDIT_AGREE)) {
					throw new SettlementException("已审核的还款单: "
							+ entity.getRepaymentNo() + " 不能被再次审核!");
				}

				// 记账日期不能为空
				if (entity.getAccountDate() == null) {
					throw new SettlementException("还款单: " + entity.getRepaymentNo()
							+ " 记账日期不能为空,请验证数据正确性!");
				}
				
				if(entity.getBverifyAmount().compareTo(BigDecimal.ZERO)>0){
					throw new SettlementException("执行过反核消的还款单: " + entity.getRepaymentNo()
							+ " 不能被审核!");
				}
				
				toAduitList.add(entity);
			}
		}

		if(CollectionUtils.isEmpty(toAduitList)){
			throw new SettlementException("所选还款单为无效、已审核或者已经存在同号的反核销生成还款单，请重新选择");
		}
		
		// 将还款单设置入Dto参数
		BillRepaymentDto dto = new BillRepaymentDto();
		dto.setBillRepayments(toAduitList);

		// 调用批量审核功能
		billRepaymentService.auditBillRepayments(dto, cInfo);

		// 记录操作日志（单据信息、审核人、审核时间），功能暂无
		Date operateDate = new Date();
		for (BillRepaymentEntity entity : toAduitList) {

			// 插入操作日志
			OperatingLogEntity logEntity = new OperatingLogEntity();
			logEntity.setOperateBillNo(entity.getRepaymentNo());// 操作单据编号
			logEntity.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__BILL_REPAYMENT);// 操作单据类型
			logEntity.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__AUDIT);
			logEntity.setOperateOrgCode(cInfo.getCurrentDeptCode());// 操作部门编码
			logEntity.setOperateOrgName(cInfo.getCurrentDeptName());// 操作部门名称
			logEntity.setOperatorName(cInfo.getEmpName());// 操作人名称
			logEntity.setOperatorCode(cInfo.getEmpCode());// 操作人编码
			logEntity.setOperateTime(operateDate);// 操作时间
			operatingLogService.addOperatingLog(logEntity, cInfo);
		}

		LOGGER.debug("审核还款单结束...");
		return null;
	}

	/**
	 * 反审核还款单
	 * 
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-9 下午4:06:08
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentManageService#auditBackBillRepayment(com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto)
	 */
	@Override
	@Transactional
	public BillRepaymentManageResultDto auditBackBillRepayment(
			BillRepaymentManageDto billRepaymentManageDto, CurrentInfo cInfo) {

		LOGGER.debug("反审核还款单开始...");
		
		// 根据传入的还款单字串，生产还款单集合
		List<String> repaymentNos = this.getRepaymentNos(billRepaymentManageDto
				.getSelectBillRepayNos());

		// 根据还款单号查询还款单列表
		List<BillRepaymentEntity> list = billRepaymentService
				.queryByRepaymentNOs(repaymentNos, null);

		if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException("没有符合条件的单据!");
		}

		//生成待审核还款单列表
		List<BillRepaymentEntity> toAduitBackList = new ArrayList<BillRepaymentEntity>();
		
		// 验证是否可以反审核
		for (BillRepaymentEntity entity : list) {
			LOGGER.info("反审核还款单,还款单号：" + entity.getRepaymentNo());
	
			//只有还款金额>0的才可以审核反审核
			if(entity.getAmount().compareTo(BigDecimal.ZERO)>0){
				
				// 无效的还款单不能被审核
				if (FossConstants.INACTIVE.equals(entity.getActive())) {
					throw new SettlementException("还款单号：" + entity.getRepaymentNo()
							+ "无效的还款单不能被反审核！", "");
				}

				// 已审核的单据不能被再次审核
				if (entity.getAuditStatus() != null
						&& entity
								.getAuditStatus()
								.equals(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT)) {
					throw new SettlementException("未审核的还款单: "
							+ entity.getRepaymentNo() + " 不能被反审核!");
				}

				// 记账日期不能为空
				if (entity.getAccountDate() == null) {
					throw new SettlementException("还款单: " + entity.getRepaymentNo()
							+ " 记账日期不能为空,请确认数据正确性!");
				}
				
				if(entity.getBverifyAmount().compareTo(BigDecimal.ZERO)>0){
					throw new SettlementException("执行过反核消的还款单: " + entity.getRepaymentNo()
							+ " 不能被反审核!");
				}
				
				//系统生成的单据不能被反审核
				if(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO.equals(entity.getCreateType())){
					throw new SettlementException("系统自动生成的还款单: " + entity.getRepaymentNo()
							+ " 不能被反审核!");
				}
				
				toAduitBackList.add(entity);
			}
		}

		if(CollectionUtils.isEmpty(toAduitBackList)){
			throw new SettlementException("所选还款单为无效、未审核或者已经存在同号的反核销生成还款单，请重新选择");
		}
		
		
		// 将还款单设置入Dto参数
		BillRepaymentDto dto = new BillRepaymentDto();
		dto.setBillRepayments(toAduitBackList);

		// 调用批量反审核功能
		billRepaymentService.reverseAuditBillRepayments(dto, cInfo);

		// 记录操作日志（单据信息、反审核人、反审核时间），功能暂无
		Date operateDate = new Date();
		for (BillRepaymentEntity entity : toAduitBackList) {

			// 插入操作日志
			OperatingLogEntity logEntity = new OperatingLogEntity();
			logEntity.setOperateBillNo(entity.getRepaymentNo());// 操作单据编号
			logEntity.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__BILL_REPAYMENT);// 操作单据类型
			logEntity.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__REVERSE_AUDIT);
			logEntity.setOperateOrgCode(cInfo.getCurrentDeptCode());// 操作部门编码
			logEntity.setOperateOrgName(cInfo.getCurrentDeptName());// 操作部门名称
			logEntity.setOperatorName(cInfo.getEmpName());// 操作人名称
			logEntity.setOperatorCode(cInfo.getEmpCode());// 操作人编码
			logEntity.setOperateTime(operateDate);// 操作时间
			operatingLogService.addOperatingLog(logEntity, cInfo);
		}

		LOGGER.info("反审核还款单结束...");
		return null;
	}

	/**
	 * 作废还款单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-9 下午4:07:44
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentManageService#disableBillRepayment(com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto)
	 */
	@Override
	@Transactional
	public BillRepaymentManageResultDto disableBillRepayment(
			BillRepaymentManageDto billRepaymentManageDto, CurrentInfo cInfo) {

		LOGGER.info("作废还款单开始...");

		// 根据传入的还款单字串，生产还款单集合
		List<String> repaymentNos = getRepaymentNos(billRepaymentManageDto
				.getSelectBillRepayNos());

		// 根据还款单号查询还款单列表
		List<BillRepaymentEntity> list = billRepaymentService
				.queryByRepaymentNOs(repaymentNos, FossConstants.ACTIVE);

		if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException("没有选择单据不能进行作废操作!");
		}
		
		// 校验作废状态
		validateBillRepaymentStatus(list, cInfo);
		
		// 作废还款单
		disableBillRepaymentList(list, cInfo);
		
		/*
		 * modify by 269044-zhurongrong-2016-12-23
		 * 合伙人需求
		 */
		//更新PTP扣款状态DTO
		WithholdStatusDto withholdStatusDto = new WithholdStatusDto();
		//更新PTP扣款状态DTO集合
		List<WithholdStatusDto> withholdStatusDtoList = new ArrayList<WithholdStatusDto>();
		
		//start add by 269044-zhurongrong--灰名单需求 2016-05-09 15:19
		//发生反核销时，先判断该客户是否在灰名单中，不在的话需判断是否添加进去，在的话，需要更新最久欠款日期
		//存放客户编码集合
		List<String> customerCodeList = new ArrayList<String>();
		//获取所有的客户编码集合
		if(list.size() > 0) {
			for(BillRepaymentEntity billRepaymentEntity : list) {
				//如果核销单的客户编码不为空
				if(StringUtils.isNotEmpty(billRepaymentEntity.getCustomerCode())) {
					//添加到集合中
					customerCodeList.add(billRepaymentEntity.getCustomerCode());
				} else {
					continue;
				}
				
				//bug-SPBP-572:开单存在始发和到达应收，网上支付始发应收后，车辆达到后，作废始发应收，调用ptp进行红冲
				//还款单中运单号不为空，且支付方式为网上支付
				if(StringUtils.isNotEmpty(billRepaymentEntity.getWaybillNo()) 
						&& SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(billRepaymentEntity.getPaymentType())) {
					//根据还款单号查询核销单
					List<BillWriteoffEntity> writeoffEntitys = billWriteoffService
							.queryByRepayment(billRepaymentEntity.getRepaymentNo(),FossConstants.NO);
					//核销单集合不为空
					if(CollectionUtils.isNotEmpty(writeoffEntitys) && StringUtils.isNotEmpty(writeoffEntitys.get(0).getEndNo())) {
						//根据核销单中的end_no查询应收单
						BillReceivableEntity receivablebillentity = billReceivableService
								.queryByReceivableNO(writeoffEntitys.get(0).getEndNo(),FossConstants.YES);
						//判空
						if(null !=receivablebillentity && SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
								.equals(receivablebillentity.getBillType())) {
							//设置运单号
							withholdStatusDto.setWaybillNo(receivablebillentity.getWaybillNo());
							//设置单据子类型
							withholdStatusDto.setBillType(receivablebillentity.getBillType());
							//设置到达部门编码
							withholdStatusDto.setDestOrgCode(receivablebillentity.getDestOrgCode());
							//设置到达部门名称
							withholdStatusDto.setDestOrgName(receivablebillentity.getDestOrgName());
							//设置场景
							withholdStatusDto.setScene(SettlementDictionaryConstants.FOSS_PTP_SEND_WITHHOLD_STATUS_DISABLE);
							//添加到集合中
							withholdStatusDtoList.add(withholdStatusDto);
						}
					}
				}
			}
		}
		//此时已经去掉重复的数据保存在hashset中
		Set<String> hs = new HashSet<String>(customerCodeList); 
		//将hashSet数据转化为ArrayList中
		List<String> newList = new ArrayList<String>(hs);
		try{
			//调用判断时候修改灰名单接口
			grayCustomerService.updateGrayCustomerToECS(newList);
		} catch (Exception e) {
			//打印异常
			LOGGER.info("调用悟空更改灰名单接口异常" + e.getMessage());
		}		
		//end
		
		try{
			//将集合传给PTP
			sendStatusToPtpService.sendStatusToPtp(withholdStatusDtoList);
		} catch (Exception e) {
			//打印异常
			LOGGER.info("更新ptp扣款状态失败" + e.getMessage());
		}		
		LOGGER.info("作废还款单结束...");
		return null;
	}

	/**
	 * 根据汇款单号查询汇款信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-5 下午1:57:45
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentManageService#queryRemittanceDetail(com.deppon.foss.module.settlement.pay.api.shared.dto.BillStatementToPaymentQueryDto)
	 */
	@Override
	public BillStatementToPaymentResultDto queryRemittanceDetail(
			BillStatementToPaymentQueryDto billStatementToPaymentQueryDto,CurrentInfo cInfo) {

		if (billStatementToPaymentQueryDto == null
				|| StringUtils.isEmpty(billStatementToPaymentQueryDto.getRemittanceNumber())
				||StringUtils.isEmpty(billStatementToPaymentQueryDto.getRepaymentType())) {
			throw new SettlementException("查询还款单输入汇款单参数异常");
		}
		// 生成汇款单信息的返回dto
		BillStatementToPaymentResultDto billStatementToPaymentResultDto = new BillStatementToPaymentResultDto();

		if(StringUtils.equals(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE,
				billStatementToPaymentQueryDto.getRepaymentType())) {
			//调用查询网上支付信息方法
			OnlinePayInfoDto dto = financeOnlinePayWSProxy.query(billStatementToPaymentQueryDto.getRemittanceNumber());

			if (dto != null) {
				billStatementToPaymentResultDto.setRemittanceNumber(billStatementToPaymentQueryDto.getRemittanceNumber());
				billStatementToPaymentResultDto.setRemittanceName(dto.getRemitterName());
				billStatementToPaymentResultDto.setRemittanceDate(dto.getRemittanceTime());
				billStatementToPaymentResultDto.setAvailableAmount(dto.getUnuseredAmounts());
			} else {
				throw new SettlementException("网上支付编号为【" + billStatementToPaymentQueryDto.getRemittanceNumber()
						+ "】的信息不存在,请重新输入!");
			}
		} else {
			// 调用费控接口，传入汇款单号，获取汇款的信息
			RemitTransferQueryResultDto remitTransferQueryResultDto = fossToFinanceRemittanceService
					.queryTransfer(billStatementToPaymentQueryDto.getRemittanceNumber(),
							billStatementToPaymentQueryDto.getRepaymentType());

			//如果返回值为空，或者返回汇款人为空，提示汇款信息不存在
			if (remitTransferQueryResultDto == null) {
				throw new SettlementException("汇款" + billStatementToPaymentQueryDto.getRemittanceNumber() + "信息不存在,请重新输入!");
			} else {

				//如果返回值的部门标杆编码为空，提示汇款未被认领
				if (StringUtils.isEmpty(remitTransferQueryResultDto.getClaimDeptNo())) {
					throw new SettlementException("该汇款" + billStatementToPaymentQueryDto.getRemittanceNumber() + "信息未被认领,请先认领汇款!");
				}
				// 根据登录用户部门获取组织的的实体信息
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(cInfo.getCurrentDeptCode());

				//如果标杆编码不一致，提示
				if (!remitTransferQueryResultDto.getClaimDeptNo().equals(orgEntity.getUnifiedCode())) {
					throw new SettlementException("该汇款" + billStatementToPaymentQueryDto.getRemittanceNumber() + "信息不是本部门认领，不能还款!");
				}

				billStatementToPaymentResultDto.setRemittanceNumber(billStatementToPaymentQueryDto.getRemittanceNumber());
				billStatementToPaymentResultDto.setRemittanceName(remitTransferQueryResultDto.getRemitName());
				billStatementToPaymentResultDto.setRemittanceDate(remitTransferQueryResultDto.getRemitDate());
				billStatementToPaymentResultDto.setAvailableAmount(remitTransferQueryResultDto.getNoCancelAmount());
			}
		}

		return billStatementToPaymentResultDto;
	}

	/**
	 * 还款/批量还款含事务
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-30 下午4:37:59
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentManageService#repaymentForStatement(com.deppon.foss.module.settlement.pay.api.shared.dto.BillStatementToPaymentQueryDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	@Transactional
	public void repaymentForStatement(
			BillStatementToPaymentQueryDto billStatementToPaymentQueryDto,
			CurrentInfo cInfo) {
		// 调用公用还款方法
		repayment(billStatementToPaymentQueryDto, cInfo);
	}

	/**
	 * 还款/批量还款无事务公用
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-26 下午4:45:52
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentManageService#repayment(com.deppon.foss.module.settlement.pay.api.shared.dto.BillStatementToPaymentQueryDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void repayment(BillStatementToPaymentQueryDto billStatementToPaymentQueryDto,CurrentInfo cInfo) {
		LOGGER.info("还款/批量还款单开始...");

		//验证还款dto
		if (billStatementToPaymentQueryDto == null||billStatementToPaymentQueryDto.getRepaymentAmount()==null) {
			LOGGER.info("还款/批量还款单时,传入的还款信息为空");
			throw new SettlementException("传入的还款信息为空!");
		}

		//还款金额必须>0,否则提示还款金额小于0异常
		if(billStatementToPaymentQueryDto.getRepaymentAmount().compareTo(BigDecimal.ZERO)!=1){
			throw new SettlementException("还款/批量还款单时还款金额必须大于0!");
		}
		/**
		 * 银行卡检验
		 * @author yangqiang 309603
		 * @date 2016-02-23
		 */
		PosCardEntity    posCardEntity    = null;			//POS实体
		BigDecimal 		 amount		      = null; 			//未使用金额
		BigDecimal       repaymentAmount  = null;			//还款金额
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(billStatementToPaymentQueryDto.getRepaymentType())
				&& (!StringUtil.isBlank(billStatementToPaymentQueryDto.getRemittanceNumber())
				&& billStatementToPaymentQueryDto.getRemittanceNumber().matches("[0-9]+"))
				){
			//查询T+0报表获取未使用金额
			//准备查询数据
			PosCardManageDto posCardManageDto = new PosCardManageDto();
			posCardManageDto.setTradeSerialNo(billStatementToPaymentQueryDto.getRemittanceNumber());
			posCardManageDto.setOrgCode(cInfo.getCurrentDeptCode());
			//posCardManageDto.setBelongMoudleCode(SettlementDictionaryConstants.NCI_STATEMENT);
			//查询T+0报表数据
            List<PosCardEntity> posCardEntitys = null;
			posCardEntitys = pdaPosManageService.queryPosCardData(posCardManageDto).getPosCardEntitys();
			//是否存在
			if (posCardEntitys==null||posCardEntitys.isEmpty()) {
				throw new SettlementException("输入流水号不存在或者输入流水号有误或者所属部门不正确..");
			}else{
				posCardEntity = posCardEntitys.get(0);
			}
			if(SettlementDictionaryConstants.NCI_DEPOSIT.equals(posCardEntity.getBelongModule())){
				throw new SettlementException("该交易流水号仅能做预收或做小票！");
			}
			//获取未使用金额
			amount =posCardEntity.getUnUsedAmount();
			//比较
			// 实收代收货款费用
			repaymentAmount = billStatementToPaymentQueryDto.getRepaymentAmount();
			LOGGER.info("输入的交易流水号:"+posCardEntity.getTradeSerialNo()+"的未使用金额为:"+posCardEntity.getUnUsedAmount());
			LOGGER.info("输入的交易流水号:"+posCardEntity.getTradeSerialNo()+"的实际未使用金额为:"+amount);
			//添加校验T+0报表数据的冻结金额校验
			// 可用金额小于还款金额
			if(amount.compareTo(repaymentAmount)<0){
				throw new SettlementException("可用余额不足。");
			}	
		}
		
		// 获取对账单号集合
		List<String> statementBillNoList = Arrays
				.asList(billStatementToPaymentQueryDto.getStatementBillNos());

		// 对账单号不能为空
		if (CollectionUtils.isEmpty(statementBillNoList)) {
			LOGGER.info("还款/批量还款单时,传入的对账单号为空不能正常还款");
			throw new SettlementException("传入的对账单号为空不能正常还款!");
		}

		// 根据对账单号集合获取对账单列表
		List<StatementOfAccountEntity> statementList = new ArrayList<StatementOfAccountEntity>();

		// 如果版本号数组有值
		if (billStatementToPaymentQueryDto.getVersionNos() != null
				&& billStatementToPaymentQueryDto.getVersionNos().length > 0) {

			// 获取对账单version版本号集合
			List<Short> versionNoList = new ArrayList<Short>();
			for (String versionNo : billStatementToPaymentQueryDto
					.getVersionNos()) {
				versionNoList.add(Short.parseShort(versionNo.trim()));
			}

			// 对账单号和版本号集合数量不匹配，还款失败
			if (statementBillNoList.size() != versionNoList.size()) {
				LOGGER.info("还款/批量还款单时,对账单号和版本号数量不匹配,请重新查询对账单并还款");
				throw new SettlementException("对账单号和版本号数量不匹配,请重新查询对账单并还款");
			}

			// 循环调用对账单接口，按对账单号和版本号查询对账单
			for (int i = 0; i < statementBillNoList.size(); i++) {

				// 根据对账单号和版本号查询对账单
				StatementOfAccountEntity entity = statementOfAccountService
						.queryByStatementNoAndVersion(
								statementBillNoList.get(i),
								(short) versionNoList.get(i));
				if (entity == null) {
					LOGGER.info("还款/批量还款单时,对账单数据已更新,请重新查询对账单并还款");
					throw new SettlementException("对账单数据已更新,请重新查询对账单并还款");
				}
				statementList.add(entity);
			}
			// 如果版本号数组没有值，直接根据对账单号查询
		} else {
			StatementOfAccountEntity entity = statementOfAccountService.queryByStatementNo(statementBillNoList.get(0));
			if (entity == null) {
				LOGGER.info("网上支付还款时输入的对账单号有误，无法查询到该对账单信息!");
				throw new SettlementException("网上支付还款时没有该对账单信息，请确认对账单号是否有误!");
			}
			statementList.add(entity);
		}
	
		// 1、新增还款单实体，单位保存
		BillRepaymentEntity billRepaymentEntity = addBillRepayment(
				billStatementToPaymentQueryDto, cInfo);

		// 排序类，所有对账单按照业务日期排序
		ListComparator orderComparator = new ListComparator("businessDate");
		// 按时间排序，预收收单核销时按业务时间先后排序
		Collections.sort(statementList, orderComparator);

		// 2、调用核销操作
		// 还款单执行每次按对账单还款后剩余金额
		BigDecimal unpaidAmountLeft = billRepaymentEntity.getAmount();

		// 获取核销批次号
		String writeoffBatchNo = settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);

		//生成其中待核销的一个应收单，永远设置还款单的收入部门
		BillReceivableEntity rtnBillReceivableEntity = null;
		final int numberOfOneFifty = 150;
		final int numberOfOneFortyNine = 149;
		final int numberOfOneNinetyNine = 999;
		
		for (StatementOfAccountEntity entity : statementList) {

			LOGGER.info("还款/批量还款单时,循环核销对账单开始,对账单号："
					+ entity.getStatementBillNo());

			// 如果还款单金额小于等于单个对账单的未还款金额，按还款单金额核销还款，并break
			if (unpaidAmountLeft.compareTo(entity.getUnpaidAmount()) <= 0) {
				billRepaymentEntity.setAmount(unpaidAmountLeft);
				
				// 调用核销操作
				if(rtnBillReceivableEntity==null){
					rtnBillReceivableEntity = billRepayWriteoff(entity.getStatementBillNo(),
							billRepaymentEntity, cInfo, writeoffBatchNo);
				}else{
					billRepayWriteoff(entity.getStatementBillNo(),
							billRepaymentEntity, cInfo, writeoffBatchNo);
				}
				
				

				// 修改对账单未还款金额
				entity.setUnpaidAmount(entity.getUnpaidAmount().subtract(
						unpaidAmountLeft));
				//修改对账单备注信息
				if(StringUtils.isNotEmpty(entity.getNotes())) {
                    if(StringUtils.isNotBlank(billStatementToPaymentQueryDto.getNotes())) {
                        if( billStatementToPaymentQueryDto.getNotes().length()>numberOfOneFifty) {
                            entity.setNotes(billStatementToPaymentQueryDto.getNotes().substring(0, numberOfOneFortyNine));
                        } else {
                            entity.setNotes(billStatementToPaymentQueryDto.getNotes());
                        }
                    }
					if(entity.getNotes().length()>SettlementConstants.MAX_LIST_SIZE){
						entity.getNotes().substring(0,numberOfOneNinetyNine);
		            }
					statementOfAccountService.updateStatementForWriteOff(entity,
						    cInfo);
				}else{
                    if(StringUtils.isNotBlank(billStatementToPaymentQueryDto.getNotes())) {
                        if( billStatementToPaymentQueryDto.getNotes().length()>numberOfOneFifty) {
                            entity.setNotes(billStatementToPaymentQueryDto.getNotes().substring(0, numberOfOneFortyNine));
                        } else {
                            entity.setNotes(billStatementToPaymentQueryDto.getNotes());
                        }
                    }

					statementOfAccountService.updateStatementForWriteOff(entity,
							cInfo);
				}
				// 新增对账单还款数据(账单号、还款单号、还款金额)
				addSoaRepayment(entity.getStatementBillNo(),
						billRepaymentEntity.getRepaymentNo(),
						billRepaymentEntity.getAmount());
				break;

				// 反之按对账单的未还款金额去核销
			} else if (unpaidAmountLeft.compareTo(entity.getUnpaidAmount()) > 0) {
				billRepaymentEntity.setAmount(entity.getUnpaidAmount());

				// 调用核销操作
				rtnBillReceivableEntity = billRepayWriteoff(entity.getStatementBillNo(),
						billRepaymentEntity, cInfo, writeoffBatchNo);
				unpaidAmountLeft = unpaidAmountLeft.subtract(entity
						.getUnpaidAmount());

				// 修改对账单未还款金额
				entity.setUnpaidAmount(BigDecimal.ZERO);
				//修改对账单备注信息
				if(StringUtils.isNotEmpty(entity.getNotes())){
                    if(StringUtils.isNotBlank(billStatementToPaymentQueryDto.getNotes())) {
                        if( billStatementToPaymentQueryDto.getNotes().length()>numberOfOneFifty) {
                            entity.setNotes(billStatementToPaymentQueryDto.getNotes().substring(0, numberOfOneFortyNine));
                        } else {
                            entity.setNotes(billStatementToPaymentQueryDto.getNotes());
                        }
                    }
					if(entity.getNotes().length()>SettlementConstants.MAX_LIST_SIZE)	{
						entity.getNotes().substring(0,numberOfOneNinetyNine);
		            }
					statementOfAccountService.updateStatementForWriteOff(entity,
						    cInfo);
				}else{
					if(StringUtils.isNotBlank(billStatementToPaymentQueryDto.getNotes())) {
                        if( billStatementToPaymentQueryDto.getNotes().length()>numberOfOneFifty) {
						    entity.setNotes(billStatementToPaymentQueryDto.getNotes().substring(0, numberOfOneFortyNine));
					    } else {
					    	entity.setNotes(billStatementToPaymentQueryDto.getNotes());
					    }
                    }
					statementOfAccountService.updateStatementForWriteOff(entity,
							cInfo);
				}
				// 新增对账单还款数据(账单号、还款单号、还款金额)
				addSoaRepayment(entity.getStatementBillNo(),
						billRepaymentEntity.getRepaymentNo(),
						billRepaymentEntity.getAmount());
			}
		}
		for (StatementOfAccountEntity entity : statementList) {
			//ddw
			statementModifyService.updateInstationMsgByIds(entity,cInfo);
		}
		//根据核销的应收单修改收款、收入部门，且重设全额还款金额
		billRepaymentEntity.setAmount(billStatementToPaymentQueryDto.getRepaymentAmount());//重设还款金额
		billRepaymentEntity.setCollectionOrgCode(rtnBillReceivableEntity.getDunningOrgCode());// 收款部门编码
		billRepaymentEntity.setCollectionOrgName(rtnBillReceivableEntity.getDunningOrgName());// 收款部门名称
		//billRepaymentEntity.setGeneratingOrgCode(rtnBillReceivableEntity.getGeneratingOrgCode());// 收入部门编码
		//billRepaymentEntity.setGeneratingOrgName(rtnBillReceivableEntity.getGeneratingOrgName());// 收入部门名称
		// 保存还款单
		billRepaymentEntity.setCreateOrgCode(cInfo.getCurrentDeptCode());
		billRepaymentEntity.setCreateOrgName(cInfo.getCurrentDeptName());
		billRepaymentService.addBillRepayment(billRepaymentEntity, cInfo);
		LOGGER.info("还款/批量还款单时,新增还款单成功,还款单号：" + billRepaymentEntity.getRepaymentNo());
		
		// 3、如果不是网上支付的，查询并修改对账单回执
		if (StringUtils.isEmpty(billStatementToPaymentQueryDto
				.getOnlinePaymentNo())) {
			updateStatementReceipt(statementBillNoList,
					billStatementToPaymentQueryDto, cInfo);
		}

		// 4、调用费控接口处理数据
		// 如果是电汇或支票还款，且校验通过，则调用费控占用汇款接口
		if (StringUtils.isNotEmpty(billStatementToPaymentQueryDto.getRemittanceNumber())
			&& (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(billStatementToPaymentQueryDto.getRepaymentType())
				||SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE.equals(billStatementToPaymentQueryDto.getRepaymentType()))) {

			LOGGER.info("还款/批量还款单时,调用费控接口占用汇款开始,汇款单号："
					+ billStatementToPaymentQueryDto.getRemittanceNumber());
			fossToFinanceRemittanceService.obtainClaim(
					billStatementToPaymentQueryDto.getRepaymentAmount(),
					billStatementToPaymentQueryDto.getRemittanceNumber(),
					cInfo.getCurrentDeptCode(),billRepaymentEntity.getRepaymentNo());
		}

		// 5.网上支付占用财务自助对应编号的金额
		// DMANA-6876 FOSS-网上支付发更改重新还款
		if (StringUtils.isNotEmpty(billStatementToPaymentQueryDto.getOnlinePaymentNo())
				&& SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(billStatementToPaymentQueryDto.getRepaymentType())) {

			//判断是否官网支付调用，官网支付会自动占用故此处无需占用金额 否则需要占用
			if(!SettlementConstants.BHO_CODE.equals(cInfo.getUser().getEmployee().getEmpCode())){

				LOGGER.info("还款/批量还款单时,调用费控接口占用网上支付金额开始网上支付编号："
						+ billStatementToPaymentQueryDto.getOnlinePaymentNo());
				financeOnlinePayWSProxy.obtain(billStatementToPaymentQueryDto.getOnlinePaymentNo(),billStatementToPaymentQueryDto.getRepaymentAmount());
			}

		}
		/**
		 * 更新POS报表和明细
		 * @author yangqiang 309603
		 * @date 2016-02-23
		 */
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(billStatementToPaymentQueryDto.getRepaymentType())
				&& (!StringUtil.isBlank(billStatementToPaymentQueryDto.getRemittanceNumber())
				&& billStatementToPaymentQueryDto.getRemittanceNumber().matches("[0-9]+"))
				){
			//更新POS刷卡信息
			//准备数据
			posCardEntity.setModifyUser(cInfo.getEmpName());											//更新人名称
			posCardEntity.setModifyUserCode(cInfo.getEmpCode());										//更新人编码
			posCardEntity.setUsedAmount(posCardEntity.getUsedAmount().add(repaymentAmount));			//已使用金额
			posCardEntity.setUnUsedAmount(posCardEntity.getUnUsedAmount().subtract(repaymentAmount));   							//未使用金额
			pdaPosManageService.updatePosCardMessageAmount(posCardEntity);
			//插入新的POS刷卡明细
			//准备数据
			for (StatementOfAccountEntity entity : statementList) {
				PosCardDetailEntity posCardDetailEntity = new PosCardDetailEntity();
				//交易流水号
				posCardDetailEntity.setTradeSerialNo(posCardEntity.getTradeSerialNo());
				//对账单
				posCardDetailEntity.setInvoiceType("DZ");	
				//创建人名称
				posCardDetailEntity.setCreateUser(cInfo.getEmpName());	
				//创建人编码
				posCardDetailEntity.setCreateUserCode(cInfo.getEmpCode());	
				//单据号
				posCardDetailEntity.setInvoiceNo(entity.getStatementBillNo());	
				//发生金额
				posCardDetailEntity.setAmount(entity.getPeriodUnverifyRecAmount());						
				if (repaymentAmount.compareTo(entity.getPeriodUnverifyRecAmount())==1) {
					//已占用金额
					posCardDetailEntity.setOccupateAmount(entity.getPeriodUnverifyRecAmount());		
					//未核销金额
					posCardDetailEntity.setUnVerifyAmount(BigDecimal.ZERO);								
					repaymentAmount = repaymentAmount.subtract(entity.getPeriodUnverifyRecAmount());
		    		pdaPosManageService.insertOrUpdateDetail(posCardDetailEntity);//调用接口插入数据
				}else{
					//已占用金额
					posCardDetailEntity.setOccupateAmount(repaymentAmount);		
					//未核销金额
					posCardDetailEntity.setUnVerifyAmount(entity.getUnpaidAmount());
		    		pdaPosManageService.insertOrUpdateDetail(posCardDetailEntity);//调用接口插入数据
					repaymentAmount=new BigDecimal("-1");
				}
				if (repaymentAmount.compareTo(BigDecimal.ZERO)==-1) {
					break;
				}	
			}
		}
		
		//start add by 269044-zhurongrong 2016-05-09 15:20
		//获取客户编码
		String customerNo = billStatementToPaymentQueryDto.getCustomerCode();
		//存放待处理客户编码集合
		List<String> customerCodeList = new ArrayList<String>();
		customerCodeList.add(customerNo);
		//发生核销时，先判断该客户是否在灰名单中，在的话需判断是否拉出来，不在的话，直接pass
		GrayCustomerEntity entity = grayCustomerService.queryGrayCustomerByCustomerCode(customerNo);
		if(entity!=null) {
			try{
				//调用判断时候修改灰名单接口
				grayCustomerService.updateGrayCustomerToECS(customerCodeList);
			} catch (Exception e) {
				//打印异常
				LOGGER.info("调用悟空更改灰名单接口异常" + e.getMessage());
			}
		}
		//end
		LOGGER.debug("还款/批量还款单结束...");
	}

	/**
	 * 生成对账单还款单关系数据
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-29 下午7:19:04
	 */
	private void addSoaRepayment(String statementNo, String repaymentNo,
			BigDecimal repaymentAmount) {

		// 生成对账单还款单关系数据
		SoaRepaymentEntity entity = new SoaRepaymentEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setStatementBillNo(statementNo);
		entity.setRepaymentNo(repaymentNo);
		entity.setRepaymentAmount(repaymentAmount);
		entity.setPaymentDate(new Date());

		// 保存对账单还款单关系数据
		soaRepaymentManageService.add(entity);

		LOGGER.info("还款/批量还款单时,生成对账单还款单关系数据成功,对账单号："
				+ entity.getStatementBillNo() + ",还款单号："
				+ entity.getRepaymentNo());
	}

	/**
	 * 根据对账单号修改对账单 回执
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-27 下午1:22:21
	 */
	private void updateStatementReceipt(List<String> statementList,
			BillStatementToPaymentQueryDto dto, CurrentInfo cInfo) {

		// 循环处理对账单
		for (String statementBillNo : statementList) {

			LOGGER.info("还款/批量还款单时,修改对账单回执开始,对账单号：" + statementBillNo);

			// 根据对账单号，查询对最后打印的对账单回执
			StatementConfReceiptEntity entity = statementReceiptService
					.queryLastPrintReceipt(statementBillNo);
			if (entity != null) {

				// 设置还款方式、还款金额、还款日期、客户意见、收款人
				entity.setPaymentType(dto.getRepaymentType());
				entity.setReceivedAmount(dto.getRepaymentAmount());
				entity.setPaymentDate(new Date());
				entity.setCustomerIdea(dto.getDescription());
				entity.setReceiveEmpName(cInfo.getEmpName());

				// 修改对账单回执
				statementReceiptService.updateStatementConfReceipt(entity);

			}
		}

	}

	/**
	 * 按对账单进行核销
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-26 下午6:27:49
	 */
	private BillReceivableEntity billRepayWriteoff(String statementNo,
			BillRepaymentEntity entity, CurrentInfo cInfo,String writeoffBatchNo) {

		// 循环按每个对账单处理

		// 根据对账单号查询对账单明细
		List<StatementOfAccountDEntity> statementDetailEntitys = statementOfAccountDService
				.queryByStatementBillNo(statementNo);
		if (CollectionUtils.isEmpty(statementDetailEntitys)) {

			LOGGER.info("还款/批量还款按对账单核销时,对账单明细记录为空，无法核销");
			throw new SettlementException("对账单明细记录为空，无法核销");
		}

		// 从对账单明细中获取应收单号、应付单号、预收单号、预付单号
		List<String> recNos = new ArrayList<String>();
		List<String> payNos = new ArrayList<String>();
		List<String> depNos = new ArrayList<String>();
		List<String> advNos = new ArrayList<String>();
		for (StatementOfAccountDEntity statmentDetailEntity : statementDetailEntitys) {
			// 应收单号
			if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE
					.equals(statmentDetailEntity.getBillParentType())) {
				recNos.add(statmentDetailEntity.getSourceBillNo());
			}
			// 应付单号
			else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE
					.equals(statmentDetailEntity.getBillParentType())) {
				payNos.add(statmentDetailEntity.getSourceBillNo());
			}
			// 预收单号
			else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED
					.equals(statmentDetailEntity.getBillParentType())) {
				depNos.add(statmentDetailEntity.getSourceBillNo());
			}
			// 预付单号
			else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT
					.equals(statmentDetailEntity.getBillParentType())) {
				advNos.add(statmentDetailEntity.getSourceBillNo());
			}
			// 其他
			else {
				LOGGER.info("还款/批量还款按对账单核销时,对账单明细单据大类型异常，无法核销");
				throw new SettlementException("对账单明细单据大类型异常，无法核销");
			}
		}

		List<BillReceivableEntity> recs = new ArrayList<BillReceivableEntity>();
		List<BillPayableEntity> pays = new ArrayList<BillPayableEntity>();
		List<BillDepositReceivedEntity> deps = new ArrayList<BillDepositReceivedEntity>();
		List<BillAdvancedPaymentEntity> advs = new ArrayList<BillAdvancedPaymentEntity>();

		// 根据来源单号查询应收单
		if (CollectionUtils.isNotEmpty(recNos)) {
			recs = billReceivableService.queryByReceivableNOs(recNos,
					FossConstants.ACTIVE);
			// 校应收单原始单据数量和对账单明细数量是否匹配
			if ((CollectionUtils.isNotEmpty(recs) && recs.size() != recNos
					.size())
					|| (CollectionUtils.isEmpty(recs) && recNos.size() > 0)) {
				LOGGER.info("还款/批量还款按对账单核销时,对账单明细数量和应收单原始单据数量不相等，无法核销");
				throw new SettlementException("对账单明细数量和应收单原始单据数量不相等，无法核销");
			}
		}

		// 根据来源单号查询应付单
		if (CollectionUtils.isNotEmpty(payNos)) {
			pays = billPayableService.queryByPayableNOs(payNos,
					FossConstants.ACTIVE);
			// 校应付单原始单据数量和对账单明细数量是否匹配
			if ((CollectionUtils.isNotEmpty(pays) && pays.size() != payNos
					.size())
					|| (CollectionUtils.isEmpty(pays) && payNos.size() > 0)) {
				LOGGER.info("还款/批量还款按对账单核销时,对账单明细数量和应付单原始单据数量不相等，无法核销");
				throw new SettlementException("对账单明细数量和应付单原始单据数量不相等，无法核销");
			}
		}

		// 根据来源单号查询预收单
		if (CollectionUtils.isNotEmpty(depNos)) {
			deps = billDepositReceivedService.queryByDepositReceivedNOs(depNos,
					FossConstants.ACTIVE);
			// 校预收单原始单据数量和对账单明细数量是否匹配
			if ((CollectionUtils.isNotEmpty(deps) && deps.size() != depNos
					.size())
					|| (CollectionUtils.isEmpty(deps) && depNos.size() > 0)) {
				LOGGER.info("还款/批量还款按对账单核销时,对账单明细数量和预收单原始单据数量不相等，无法核销");
				throw new SettlementException("对账单明细数量和预收单原始单据数量不相等，无法核销");
			}
		}

		// 根据来源单号查询预付单
		if (CollectionUtils.isNotEmpty(advNos)) {
			advs = billAdvancedPaymentService.queryBillAdvancedPaymentNos(
					advNos, FossConstants.ACTIVE);
			// 校预付单原始单据数量和对账单明细数量是否匹配
			if ((CollectionUtils.isNotEmpty(advs) && advs.size() != advNos
					.size())
					|| (CollectionUtils.isEmpty(advs) && advNos.size() > 0)) {
				LOGGER.info("还款/批量还款按对账单核销时,对账单明细数量和预付单原始单据数量不相等，无法核销");
				throw new SettlementException("对账单明细数量和预付单原始单据数量不相等，无法核销");
			}
		}

		BillWriteoffOperationDto writeoffDto = new BillWriteoffOperationDto();

		//获取其中待核销的一个应收单
		BillReceivableEntity rtnBillReceivableEntity = recs.get(0);
		
		// 设置核销参数中的核销方列表：应收单列表、应付单列表、预收单列表、预付单列表
		writeoffDto.setBillReceivableEntitys(recs);
		writeoffDto.setBillPayableEntitys(pays);
		writeoffDto.setBillDepositReceivedEntitys(deps);
		writeoffDto.setBillAdvancedPaymentEntitys(advs);

		// 设置还款单
		writeoffDto.setBillRepaymentEntity(entity);

		writeoffDto.setWriteoffBatchNo(writeoffBatchNo);

		// 核销类型为手工核销
		writeoffDto
				.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);

		// 核销对账单编号取对账单上的单据编号
		writeoffDto.setStatementBillNo(statementNo);

		// 核销对账单明细
		writeoffStatementDetailForRepayment(writeoffDto, cInfo);

		// 释放预付单
		releaseFromStatement(writeoffDto, cInfo);
		
		return rtnBillReceivableEntity;
	}

	/**
	 * 释放预付单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-27 上午11:21:26
	 */
	private void releaseFromStatement(
			BillWriteoffOperationDto writeoffResultDto, CurrentInfo currentInfo) {

		// 预付单未核销金额大于零时，从对账单中释放
		if (CollectionUtils.isNotEmpty(writeoffResultDto
				.getBillAdvancedPaymentEntitys())) {
			for (BillAdvancedPaymentEntity adv : writeoffResultDto
					.getBillAdvancedPaymentEntitys()) {

				LOGGER.info("还款/批量还款按对账单核销完成,释放对账单中的预付单,对预付号："
						+ adv.getAdvancesNo());
				// 修改预收单的对账单号字段
				adv.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
				int row = billAdvancedPaymentService
						.updateBillAdvancedPaymentByMakeStatement(adv,
								currentInfo);
				if (row != 1) {
					throw new SettlementException("更新条数错误，更新异常！");
				}
			}
		}
	}

	/**
	 * 对账单明细核销，包括：预收冲应收、应收冲应付、还款冲应收 、预付冲应付
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 9, 2012 10:29:41 AM
	 */
	private BillWriteoffOperationDto writeoffStatementDetailForRepayment(
			BillWriteoffOperationDto writeoffDto, CurrentInfo currentInfo) {
		// 初始化返回结果
		BillWriteoffOperationDto writeoffResultDto = new BillWriteoffOperationDto();
		writeoffResultDto.setBillPayableEntitys(writeoffDto
				.getBillPayableEntitys());
		writeoffResultDto.setBillDepositReceivedEntitys(writeoffDto
				.getBillDepositReceivedEntitys());
		writeoffResultDto.setBillAdvancedPaymentEntitys(writeoffDto
				.getBillAdvancedPaymentEntitys());

		// 预收冲应收
		if (CollectionUtils.isNotEmpty(writeoffDto
				.getBillDepositReceivedEntitys())
				&& CollectionUtils.isNotEmpty(writeoffDto
						.getBillReceivableEntitys())) {
			LOGGER.info("还款/批量还款按对账单核销时,核销预收冲应收...");
			writeoffResultDto = billWriteoffService
					.writeoffDepositAndReceivable(writeoffDto, currentInfo);
			writeoffDto.setBillDepositReceivedEntitys(writeoffResultDto
					.getBillDepositReceivedEntitys());
			writeoffDto.setBillReceivableEntitys(writeoffResultDto
					.getBillReceivableEntitys());
		}

		// 应收冲应付
		if (CollectionUtils.isNotEmpty(writeoffDto.getBillReceivableEntitys())
				&& CollectionUtils.isNotEmpty(writeoffDto
						.getBillPayableEntitys())) {
			LOGGER.info("还款/批量还款按对账单核销时,核销应收冲应付...");
			writeoffResultDto = billWriteoffService
					.writeoffReceibableAndPayable(writeoffDto, currentInfo);
			writeoffDto.setBillReceivableEntitys(writeoffResultDto
					.getBillReceivableEntitys());
			writeoffDto.setBillPayableEntitys(writeoffResultDto
					.getBillPayableEntitys());
		}

		// 还款冲应收
		if (CollectionUtils.isNotEmpty(writeoffDto.getBillReceivableEntitys())
				&& writeoffDto.getBillRepaymentEntity() != null) {
			LOGGER.info("还款/批量还款按对账单核销时,核销还款冲应收...");
			billWriteoffService.writeoffRepaymentAndReceibable(writeoffDto,
					currentInfo);
			writeoffDto.setBillReceivableEntitys(writeoffResultDto
					.getBillReceivableEntitys());
			writeoffDto.setBillPayableEntitys(writeoffResultDto
					.getBillPayableEntitys());
		}

		// 预付冲应付
		if (CollectionUtils.isNotEmpty(writeoffDto
				.getBillAdvancedPaymentEntitys())
				&& CollectionUtils.isNotEmpty(writeoffDto
						.getBillPayableEntitys())) {
			LOGGER.info("还款/批量还款按对账单核销时,核销预付冲应付...");
			writeoffResultDto = billWriteoffService.writeoffAdvancedAndPayable(
					writeoffDto, currentInfo);
			writeoffDto.setBillAdvancedPaymentEntitys(writeoffResultDto
					.getBillAdvancedPaymentEntitys());
			writeoffDto.setBillPayableEntitys(writeoffResultDto
					.getBillPayableEntitys());
		}
		return writeoffResultDto;
	}

	/**
	 * 新增还款单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-26 下午4:57:26
	 */
	private BillRepaymentEntity addBillRepayment(
			BillStatementToPaymentQueryDto dto, CurrentInfo cInfo) {

		LOGGER.info("还款/批量还款单时,新增还款单开始...");
		// 生成还款单
		BillRepaymentEntity entity = new BillRepaymentEntity();
		Date now = new Date();

		entity.setId(UUIDUtils.getUUID());// ID
		entity.setRepaymentNo(settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.HK1));// 还款单号
		entity.setCustomerCode(dto.getCustomerCode());// 客户编码
		entity.setCustomerName(dto.getCustomerName());// 客户名称
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// 币种
		entity.setAmount(dto.getRepaymentAmount());// 金额
		entity.setTrueAmount(dto.getRepaymentAmount());// 实际还款金额
		entity.setBverifyAmount(BigDecimal.ZERO);// 反核销金额
		entity.setAuditStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT);// 审核状态
		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);// 生成方式
		entity.setCreateOrgCode(cInfo.getCurrentDeptCode());// 录入部门编码
		entity.setCreateOrgName(cInfo.getCurrentDeptCode());// 录入部门名称
//		entity.setCollectionOrgCode(cInfo.getCurrentDeptCode());// 收款部门编码
//		entity.setCollectionOrgName(cInfo.getCurrentDeptName());// 收款部门名称
//		entity.setGeneratingOrgCode(cInfo.getCurrentDeptCode());// 收入部门编码
//		entity.setGeneratingOrgName(cInfo.getCurrentDeptName());// 收入部门名称
		entity.setBillType(SettlementDictionaryConstants.BILL_REPAYMENT__BILL_TYPE__REPAYMENT);// 还款单类型
		entity.setStatus(SettlementDictionaryConstants.BILL_REPAYMENT__STATUS__SUBMIT);// 状态默认为提交
		entity.setActive(FossConstants.ACTIVE);// 有效
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);// 非红单
		entity.setPaymentType(dto.getRepaymentType());// 支付方式
		entity.setCreateUserCode(cInfo.getCurrentDeptCode());// 制单人编码
		entity.setCreateUserName(cInfo.getCurrentDeptName());// 制单人名称
		entity.setBusinessDate(now);// 业务时间
		entity.setAccountDate(now);// 记账时间
		entity.setCreateTime(now);// 创建时间
		entity.setIsInit(FossConstants.NO);// 是否初始化
		entity.setNotes(dto.getRepaymentNotes());// 备注
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号
		entity.setOnlinePaymentNo(dto.getOnlinePaymentNo());// 在线支付编号
		/**
		 * 银行卡填写银联交易流水号前台传入的是汇款编号的字段，故在此处处理
		 *
		 * @author 105762
		 */
		if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(entity.getPaymentType())){
			entity.setBatchNo(dto.getRemittanceNumber());
		} else {
			entity.setOaPaymentNo(dto.getRemittanceNumber());// OA汇款编号
		}
		entity.setOaPayee(dto.getRemittanceName());//OA汇款人
		
		// 保存还款单
		//billRepaymentService.addBillRepayment(entity, cInfo);
		return entity;
	}

	/**
	 * 循环作废还款操作
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-9 下午5:04:16
	 */
	private void disableBillRepaymentList(List<BillRepaymentEntity> list,CurrentInfo cInfo) {
		// 生成操作时间
		Date operateDate = new Date();

		// 进行循环作废处理
		for (BillRepaymentEntity entity : list) {

			LOGGER.info("作废还款单,还款单号：" + entity.getRepaymentNo());
			
			// 1、调用反核销服务，红冲核销单及其关联的应收单
			billWriteoffService.writeBackBillWriteoffByRepayment(
					entity.getRepaymentNo(), cInfo);
			LOGGER.info("作废还款单时红冲核销单及关联应收单成功,还款单号：" + entity.getRepaymentNo());
			
			// 2、调用还款单服务，红冲还款单
			BillRepaymentEntity billRepaymentEntity = billRepaymentService.queryByRepaymentNO(entity.getRepaymentNo(),FossConstants.ACTIVE);
			billRepaymentEntity.setActive(FossConstants.INACTIVE);
			billRepaymentService.writeBackBillRepayment(billRepaymentEntity, cInfo);
			LOGGER.info("作废还款单时红冲还款单成功,还款单号：" + entity.getRepaymentNo());
			
			// 生成该还款单相关的所有对账单号
			List<String> statementNoList = new ArrayList<String>();
			//生成待反确认对账单号列表
			List<String> unConfirmStatementNoList = new ArrayList<String>();
			
			// 3、通过查询对账还款表(还款单号)，获取对账单，并修改对账单上的未还款金额为：未还款金额+还款单金额
			// 3.1 新增逻辑：
			//	     根据还款单号获取该还款对应所有对账单号，然后循环对账单号根据每个对账单号获取其生成的有效的核销单列表；
			//		1、如果不为空，即存在有效的核销单，此时不处理对账单确认状态
			//		2、如果为空，即不存在有效的核销单，将该对账单进行反确认
			
			// 根据还款单号查询对账单还款单关系信息
			List<SoaRepaymentEntity> soaRepaymentList = soaRepaymentManageService
					.queryListByRepaymentNo(entity.getRepaymentNo());
			
			//对账单还款单关系信息不为空
			if (CollectionUtils.isNotEmpty(soaRepaymentList)) {
				//循环处理还款单对应的对账单号
				for (SoaRepaymentEntity soaEntity : soaRepaymentList) {
					//获取该还款单相关的所有对账单号
					statementNoList.add(soaEntity.getStatementBillNo());
					//根据对账单号获取该对账单生成的全部有效的的核销单 
					List<BillWriteoffEntity> entityList = billWriteoffService.queryByStatementBillNo(soaEntity.getStatementBillNo(), null, null, null, FossConstants.ACTIVE);
					//如果核销单列表为空
					if(CollectionUtils.isEmpty(entityList)){
						//将对账单号加入到待反确认对账单号列表
						unConfirmStatementNoList.add(soaEntity.getStatementBillNo());
					}
				}
				
				// 根据对账单号集合查询出该还款单对应的所有对账单集合
				List<StatementOfAccountEntity> statementList = statementOfAccountService.queryByStatementNos(statementNoList);
				// 根据对账单号集合查询出该还款单对应的所有代打木架对账单集合
				List<WoodenStatementEntity> woodenList = woodenStatementService.queryWoodenStatementByNumber(statementNoList);
				//根据对账单号集合查询出改还款单对应的所有合伙人收款对账单信息集合
				List<StatementRecivableEntity> partnerStatementList = receivableStatementService.queryPartnerStatementList(statementNoList);
				//该还款单对应的所有对账单集合不为空
				if (CollectionUtils.isNotEmpty(statementList)) {
					// 循环修改对账单
					for (StatementOfAccountEntity stateEntity : statementList) {
						for (SoaRepaymentEntity soaEntity : soaRepaymentList) {

							// 如果该对账单号和对账单还款单关系数据中的对账单相等，则修改未还款金额并保存
							if (soaEntity.getStatementBillNo().equals(stateEntity.getStatementBillNo())) {
								//未还款金额+还款对账单关系表的还款金额
								stateEntity.setUnpaidAmount(stateEntity.getUnpaidAmount().add(soaEntity.getRepaymentAmount()));
								//修改未还款金额
								statementOfAccountService.updateStatementForWriteOff(stateEntity, cInfo);
								LOGGER.info("作废还款单时循环修改对账单的未还款金额,对账单号："+ stateEntity.getStatementBillNo());
								
								/**
								 * 判断数据是否来自于NCI项目
								 * @author 269052 zhouyuan008@deppon.com
								 */
								/**
								 * 1、判断支付方式是否为银行卡 
								 */
								if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(entity.getPaymentType())){
									/**
									 * 2、获取交易流水号
									 */
									String tradeSerialNo = entity.getBatchNo();
									
									/**
									 * 获取对账单还款单关系表总的还款金额
									 */
									BigDecimal amount = soaEntity.getRepaymentAmount();//entity.getAmount();soaEntity.
									
									/**
									 * 3、根据交易流水号和单号去查询明细
									 */
									//获取刷卡明细实体集合
									PosCardDetailEntity detail = new PosCardDetailEntity();
									detail.setInvoiceNo(stateEntity.getStatementBillNo());
									detail.setTradeSerialNo(tradeSerialNo);
									detail.setAmount(amount);
									/**
									 * 直接根据单据号和交易流水号去查询是否为NCI中数据
									 */
									List<PosCardDetailEntity> lists = pdaPosManageService.queryPosDetailsByNo(detail);
									if(CollectionUtils.isNotEmpty(lists)){
										/**
										 * 4 、释放T+0报表的金额和删除明细数据
										 */
										pdaPosManageService.disableRepayment(detail);
									}
								}
								
							}
						}
					}
				//ddw
				}else if (CollectionUtils.isNotEmpty(woodenList)) {
					// 循环修改对账单
					for (WoodenStatementEntity stateEntity : woodenList) {
						for (SoaRepaymentEntity soaEntity : soaRepaymentList) {

							// 如果该对账单号和对账单还款单关系数据中的对账单相等，则修改未还款金额并保存
							if (soaEntity.getStatementBillNo().equals(stateEntity.getStatementBillNo())) {
								//未还款金额+还款对账单关系表的还款金额
								stateEntity.setUnpaidAmount(stateEntity.getUnpaidAmount().add(soaEntity.getRepaymentAmount()));
								//修改未还款金额
								woodenStatementService.updateStatementForWriteOff(stateEntity, cInfo);
								LOGGER.info("作废还款单时循环修改对账单的未还款金额,对账单号："+ stateEntity.getStatementBillNo());
							}
						}
					}
				//处理合伙人收款对账单业务
				}else if(CollectionUtils.isNotEmpty(partnerStatementList)){
					for (StatementRecivableEntity stateReceivableEntity : partnerStatementList) {
						// 如果该对账单号和对账单还款单关系数据中的对账单相等，则修改对账单中未还款金额并保存
						for (SoaRepaymentEntity soaEntity : soaRepaymentList) {
							// 如果该对账单号和对账单还款单关系数据中的对账单号相等，则修改未还款金额并保存
							if(soaEntity.getStatementBillNo().equals(stateReceivableEntity.getStatementBillNo())){
								//本期剩余应收金额
								stateReceivableEntity.setPeriodUnverifyRecAmount(stateReceivableEntity.
										getPeriodUnverifyRecAmount().add(soaEntity.getRepaymentAmount()));
								//本期已还金额
								stateReceivableEntity.setPeriodRrpayAmount(stateReceivableEntity.
										getPeriodRrpayAmount().subtract(soaEntity.getRepaymentAmount()));
								//本期未还金额
								stateReceivableEntity.setPeriodNpayAmount(stateReceivableEntity.
										getPeriodNpayAmount().add(soaEntity.getRepaymentAmount()));
								//结账次数重置为0
								stateReceivableEntity.setSettleNum((short)-1);
								
								//作废还款单还原对账单中的金额
								receivableStatementService.updateStatementForDisableRepayment(stateReceivableEntity);
							}
						}
					}
				}
				
				//如果待反确认对账单号不为空
				if(CollectionUtils.isNotEmpty(unConfirmStatementNoList)){
					List<StatementOfAccountEntity> unConfirmStatementList = new ArrayList<StatementOfAccountEntity>() ;
					
					// 根据待反确认对账单号查询所有待反确认的对账单集合
					for(int i=0;i<unConfirmStatementNoList.size();i++){
						StatementOfAccountEntity statementOfAccountEntityTemp = statementOfAccountService.queryByStatementNo(unConfirmStatementNoList.get(i));
						if(null != statementOfAccountEntityTemp){
							if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM.equals(statementOfAccountEntityTemp.getConfirmStatus())){
								unConfirmStatementList.add(statementOfAccountEntityTemp);
							}
						}
					}
					//反确认对账单
					statementOfAccountService.unConfirmStatement(unConfirmStatementList, cInfo);
					LOGGER.info("作废还款单时反确认还款单对应的对账单完成,还款单号："+   entity.getRepaymentNo());
				}
				
			}

			// 4、记录操作日志（单据信息、作废核人、作废时间）
			OperatingLogEntity logEntity = new OperatingLogEntity();
			logEntity.setOperateBillNo(entity.getRepaymentNo());// 操作单据编号
			logEntity.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__BILL_REPAYMENT);// 操作单据类型
			logEntity.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__DISABLE);
			logEntity.setOperateOrgCode(cInfo.getCurrentDeptCode());// 操作部门编码
			logEntity.setOperateOrgName(cInfo.getCurrentDeptName());// 操作部门名称
			logEntity.setOperatorName(cInfo.getEmpName());// 操作人名称
			logEntity.setOperatorCode(cInfo.getEmpCode());// 操作人编码
			logEntity.setOperateTime(operateDate);// 操作时间
			operatingLogService.addOperatingLog(logEntity, cInfo);
		}

		for (BillRepaymentEntity entity : list) {

			// 5、如果作废的是电汇还款单，且校验通过，且是非初始化数据（Foss生成非迁移），则调用OA反占用汇款接口(开关为true时，调用费控)
			if (StringUtils.isNotEmpty(entity.getOaPaymentNo())
				&& FossConstants.NO.equals(entity.getIsInit())
				&& (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.endsWith(entity.getPaymentType())
					||SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE.endsWith(entity.getPaymentType()))) {
				LOGGER.info("作废还款单时调用费控接口释放汇款,汇款单号号：" + entity.getOaPaymentNo());
				fossToFinanceRemittanceService.releaseClaim(entity.getAmount(),
						entity.getOaPaymentNo(), entity.getCollectionOrgCode(),entity.getRepaymentNo());
			}

            //网上支付安全收款
			//Author 105762

            // 6、如果作废的是网上支付还款单，且校验通过，且是非初始化数据（Foss生成非迁移），则调用OA反占用汇款接口(开关为true时，调用费控)
            if (StringUtils.isNotEmpty(entity.getOnlinePaymentNo()) && FossConstants.NO.equals(entity.getIsInit())
                    && (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(entity.getPaymentType()))) {
                /*
                调用财务自助释放网上支付金额
                */
                financeOnlinePayWSProxy.release(entity.getOnlinePaymentNo(),entity.getAmount());
            }
		}
	}

	/**
	 * 校验待作废的还款单是否满足作废条件
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-9 下午4:34:31
	 */
	private List<BillRepaymentEntity> validateBillRepaymentStatus(List<BillRepaymentEntity> list, CurrentInfo cInfo) {

		// 当前登录用户编码
		String userCode = cInfo.getEmpCode();

		// 循环校验还款单状态
		for (BillRepaymentEntity entity : list) {
			LOGGER.info("作废还款单，还款单号：" + entity.getRepaymentNo());

			// 系统自动生成的还款单不能作废
			if (SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO
					.equals(entity.getCreateType())) {
				throw new SettlementException("系统自动生成的还款单: "
						+ entity.getRepaymentNo() + " 不能作废!");
			}

			// 已审核的还款单不能作废
			if (SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDIT_AGREE
					.equals(entity.getAuditStatus())) {
				throw new SettlementException("已审核的还款单: "
						+ entity.getRepaymentNo() + " 不能作废!");
			}

			// 自己录入的还款单不能自己作废
			if (entity.getCreateUserCode().equals(userCode)) {
				throw new SettlementException("自己录入的还款单: "
						+ entity.getRepaymentNo() + " 不能自己作废!");
			}

			// 无效或红单还款单不能作废
			if (FossConstants.INACTIVE.equals(entity.getActive())
					|| SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES
							.equals(entity.getIsRedBack())) {
				throw new SettlementException("无效或红单还款单: "
						+ entity.getRepaymentNo() + " 不能作废!");
			}

			// 根据还款单号查询是否存在反核销的还款单
			BillRepaymentEntity redBackEntity = billRepaymentManageDao
					.queryRebackBillByNo(entity.getRepaymentNo(),
							FossConstants.ACTIVE);

			// 如果还款单已做过反核销，则不能作废
			if (redBackEntity != null) {
				throw new SettlementException("已经反核销的还款单: "
						+ entity.getRepaymentNo() + " 不能作废!");
			}

			if(entity.getAmount().compareTo(BigDecimal.ZERO)<0||entity.getBverifyAmount().compareTo(BigDecimal.ZERO)>0){
				throw new SettlementException("执行过反核消的还款单: " + entity.getRepaymentNo()
						+ " 不能被审核!");
			}
			
			// 还款单的可作废时间必须为当前时间30天之内，天数从基础资料读取
			// 当前系统时间、还款单作废时间
			Date nowDate = new Date();
			String sysLimitDateStr = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.STL_REPAYMENT_CANCELABLE_DAY);
			
			if(StringUtils.isEmpty(sysLimitDateStr)){
				throw new SettlementException("还款单作废期限未配置 ，请在系统管理的配置参数中设置!");
			}
			
			Date sysLimitDate = DateUtils.addDays(nowDate,
					-Integer.parseInt(sysLimitDateStr));
			if (entity.getBusinessDate().before(sysLimitDate)) {
				throw new SettlementException("选择的还款单:"
						+ entity.getRepaymentNo() + "业务时间超过了还款单作废限制时间,请重新选择!");
			}
			
			/*作废时检查还款单冲销的应收单对应的运单是否存在劳务费应付单
			 *         7.1 不存在，直接反核消
			 *         7.2 存在，判断劳务费应付单是否已支付
			 *         		7.2.1 已支付，提示不允许反核消
			 *              7.2.1 未支付，失效劳务费应付单，进而反核消
			 */
			//如果核销单开始或结束单号为应收单,获取其对应的运单号
			BillPayableConditionDto billPayableConditionDto = new BillPayableConditionDto();
			//根据还款单号查询有效核销单
			List<BillWriteoffEntity> billWriteoffEntitys = billWriteoffService.queryByRepayment(entity.getRepaymentNo(), FossConstants.ACTIVE);
		    //如果核销单存在，循环处理核销单
			if(!CollectionUtils.isEmpty(billWriteoffEntitys)){
				for(BillWriteoffEntity billWriteoffEntity:billWriteoffEntitys){
					if(SettlementUtil.isReceiveable(billWriteoffEntity.getEndNo())){
						//将该还款单冲销的应收单的运单放入查询条件Dto中
						billPayableConditionDto.setWaybillNo(billWriteoffEntity.getEndWaybillNo());
					}
					//如果运单号存在
					if(StringUtils.isNotEmpty(billPayableConditionDto.getWaybillNo())){
						//根据运单号，查询该运单对应的应付单 
						List<BillPayableEntity> payableList = billPayableService.queryBillPayableByCondition(billPayableConditionDto);
						//如果应付单存在
						if(!CollectionUtils.isEmpty(payableList)){
							//循环应付单列表
							for(BillPayableEntity pay:payableList){
								//如果应付单为装卸费费类型，且为已支付状态，不允许反核消
								if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(pay.getBillType())
										&&SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.equals(pay.getPayStatus())){
									// 提示：选择的核销单:XXX其相关运单XX的装卸费应付单已支付,不可反核消,请重新选择
									throw new SettlementException("选择的还款单:"+ entity.getRepaymentNo()+ "其相关运单:"+billPayableConditionDto.getWaybillNo()+"的装卸费应付单已支付,不可作废,请重新选择!");
								}
								//如果应付单为装卸费类型，且为未支付状态，将装卸费应付单失效
								if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(pay.getBillType())
										&&SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO.equals(pay.getPayStatus())){
									//失效装卸费应付单
									billPayableService.disableBillPayable(pay, cInfo);
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * 处理还款单号字串，返回list
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-9 下午3:16:26
	 */
	private List<String> getRepaymentNos(String selectBillRepayNos) {

		// 还款单号不能为空
		if (StringUtils.isEmpty(selectBillRepayNos.trim())) {
			throw new SettlementException("没有选中的还款单!");
		}

		// 按“,”分割还款单字串，生成还款单号list
		String[] repaymentNos = selectBillRepayNos.split(",");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < repaymentNos.length; i++) {
			list.add(repaymentNos[i].trim());
		}

		return list;
	}

	/**
	 * @return billRepaymentManageDao
	 */
	public IBillRepaymentManageDao getBillRepaymentManageDao() {
		return billRepaymentManageDao;
	}

	/**
	 * @param billRepaymentManageDao
	 */
	public void setBillRepaymentManageDao(
			IBillRepaymentManageDao billRepaymentManageDao) {
		this.billRepaymentManageDao = billRepaymentManageDao;
	}

	/**
	 * @param billRepaymentService
	 */
	public void setBillRepaymentService(
			IBillRepaymentService billRepaymentService) {
		this.billRepaymentService = billRepaymentService;
	}

	/**
	 * @param billWriteoffService
	 */
	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}

	/**
	 * @param statementOfAccountService
	 */
	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	/**
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @param statementOfAccountDService
	 */
	public void setStatementOfAccountDService(
			IStatementOfAccountDService statementOfAccountDService) {
		this.statementOfAccountDService = statementOfAccountDService;
	}

	/**
	 * @param billReceivableService
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	/**
	 * @param billPayableService
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * @param billDepositReceivedService
	 */
	public void setBillDepositReceivedService(
			IBillDepositReceivedService billDepositReceivedService) {
		this.billDepositReceivedService = billDepositReceivedService;
	}

	/**
	 * @param billAdvancedPaymentService
	 */
	public void setBillAdvancedPaymentService(
			IBillAdvancedPaymentService billAdvancedPaymentService) {
		this.billAdvancedPaymentService = billAdvancedPaymentService;
	}

	/**
	 * @param statementReceiptService
	 */
	public void setStatementReceiptService(
			IStatementReceiptService statementReceiptService) {
		this.statementReceiptService = statementReceiptService;
	}

	/**
	 * @param operatingLogService
	 */
	public void setOperatingLogService(IOperatingLogService operatingLogService) {
		this.operatingLogService = operatingLogService;
	}

	/**
	 * @param soaRepaymentManageService
	 */
	public void setSoaRepaymentManageService(
			ISoaRepaymentManageService soaRepaymentManageService) {
		this.soaRepaymentManageService = soaRepaymentManageService;
	}

	/**
	 * @param fossToFinanceRemittanceService
	 */
	public void setFossToFinanceRemittanceService(
			IFossToFinanceRemittanceService fossToFinanceRemittanceService) {
		this.fossToFinanceRemittanceService = fossToFinanceRemittanceService;
	}

	public IConfigurationParamsService getConfigurationParamsService() {
		return configurationParamsService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * @param orgAdministrativeInfoComplexService
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setWoodenStatementService(
			IWoodenStatementService woodenStatementService) {
		this.woodenStatementService = woodenStatementService;
	}

	public void setStatementModifyService(
			IStatementModifyService statementModifyService) {
		this.statementModifyService = statementModifyService;
	}
	

    public void setFinanceOnlinePayWSProxy(IFinanceOnlinePayWSProxy financeOnlinePayWSProxy) {
        this.financeOnlinePayWSProxy = financeOnlinePayWSProxy;
    }

	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}
	
	/**
	 * @SET
	 * @param grayCustomerService
	 */
	public void setGrayCustomerService(IGrayCustomerService grayCustomerService) {
		this.grayCustomerService = grayCustomerService;
	}

    public void setSendStatusToPtpService(
			ISendStatusToPtpService sendStatusToPtpService) {
		this.sendStatusToPtpService = sendStatusToPtpService;
	}

	/**
	 * @param receivableStatementService the receivableStatementService to set
	 */
	public void setReceivableStatementService(
			IReceivableStatementService receivableStatementService) {
		this.receivableStatementService = receivableStatementService;
	}
    
}
