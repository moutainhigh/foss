/**
 * Copyright 2013 STL TEAM
 * 
 * PROJECT NAME	: stl-writeoff
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/writeoff/server/service/impl/PartnerPayStatementManagerService.java
 * 
 * FILE NAME        	: PartnerPayStatementManagerService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 *  
 */

package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.service.IPartnerStatementPaymentService;
import com.deppon.foss.module.settlement.pay.api.server.service.IPayToFSSCSendService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentAddDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.PayToCostcontrolDto;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPartnerPayStatementDDao;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPartnerPayStatementDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerPayStatementManagerService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerPayStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerPayStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerPayStatementDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 付款对账单管理接口实现类
 * 
 * @author 311396-foss-wangwenbo
 * @date 2016-02-22 下午 15:05:16
 */
public class PartnerPayStatementManagerService implements IPartnerPayStatementManagerService{

	//日志
	private static final Logger logger = LoggerFactory.getLogger(PartnerPayStatementManagerService.class);


	/**
	 * 注入组织服务
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 注入结算单据编号生成service
	 */
	private ISettlementCommonService settlementCommonService;
	/**
	 * 注入应付单服务接口
	 */
	private IBillPayableService billPayableService;

	/**
	 * 注入财务共享接口
	 */
	private IPayToFSSCSendService payToFSSCSendService;
	/**
	 * 注入付款单公共服务接口
	 */
	private IBillPaymentService billPaymentService;
	/**
	 * 注入付款对账单管理dao
	 */
	private IPartnerPayStatementDao partnerPayStatementDao;
	/**
	 * 注入付款对账单明细管理dao
	 */
	private IPartnerPayStatementDDao partnerPayStatementDDao;
	/**
	 * 注入付款里的付款对账service
	 */
	private IPartnerStatementPaymentService partnerStatementPaymentService;
	
	/**
	 * 查询付款对账单信息
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-02-22 下午 15:05:16
	 */
	
	@Override
	public PartnerPayStatementDto queryStatement(PartnerPayStatementDto queryDto, int start, int limit) { 
		//记录日志
		logger.info("客户编码：" + queryDto.getCustomerCode());
		// 存放返回的对账单
		PartnerPayStatementDto resultDto = new PartnerPayStatementDto();
		// 存放查询参数
		PartnerPayStatementDto updateDto = new PartnerPayStatementDto();
		// 客户编码
		updateDto.setCustomerCode(queryDto.getCustomerCode());
		// 确认状态
		updateDto.setConfirmStatus(queryDto.getConfirmStatus());
		// 结账状态
		if (StringUtil.isNotEmpty(queryDto.getSettleStatus())) {
			//设置结账状态
			updateDto.setSettleStatus(queryDto.getSettleStatus());
		}
		// 组织编码
		updateDto.setOrgCode(queryDto.getOrgCode());
		// 存放结果集
		List<PartnerPayStatementEntity> ppsEntityList = new ArrayList<PartnerPayStatementEntity>();
		// 如果用户按合伙人查询
		if (SettlementConstants.TAB_QUERY_BY_DATE.equals(queryDto.getQueryPage())) {
			// 开始日期
			updateDto.setPeriodBeginDate(queryDto.getPeriodBeginDate());
			// 结束日期
			updateDto.setPeriodEndDate(queryDto.getPeriodEndDate());
			//对账单子类型
			updateDto.setBillType(queryDto.getBillType());
			//设置可查询部门结果集、登录人编码
			validateDto(updateDto,queryDto);
			
			//查询总条数、总金额
			resultDto = partnerPayStatementDao.queryPayStatementByCustomerCount(updateDto);
			
			//如果存在数据，则查询
			if(resultDto != null && resultDto.getTotalCount() > 0){
				// 查询对账单信息
				ppsEntityList = partnerPayStatementDao.queryPayStatementByCustomer(updateDto, start, limit);
			}
		// 如果按对账单号查询
		}else if (SettlementConstants.TAB_QUERY_BY_DZ_BILL_NO.equals(queryDto.getQueryPage())) {
			//根据对账单单号集合查询对账单(只查询本部门的对账单信息，供按对账单单号查询对账单使用)
			List<String> statementNos = Arrays.asList(queryDto.getBillDetailNos());
			//判断对账单号是否为空
	    	if(CollectionUtils.isEmpty(statementNos)){
				throw new SettlementException("执行按对账单号集合查询对账单信息时，对账单号集合为空！","");
			}
	    	//校验网点编码是否为空
	    	if(StringUtil.isEmpty(queryDto.getOrgCode())){
	    		throw new SettlementException("当前登录用户的操作部门编码为空", "");
	    	}
	    	queryDto.setPayableBillNosList(statementNos);
	    	ppsEntityList = partnerPayStatementDao.queryPayStatementByBillNos(queryDto);
		// 如果按运单号查询
		}else if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(queryDto.getQueryPage())) {
			List<String> wayBills = Arrays.asList(queryDto.getBillDetailNos());
			if (CollectionUtils.isEmpty(wayBills)) {
				throw new SettlementException("执行按运单号集合查询对账单明细时，运单号集合为空！");
			}
			// 获取对账单明细信息(按运单号、应付单号查询)
			List<PartnerPayStatementDEntity> ppsEntityByWaybillList = partnerPayStatementDDao.queryByWaybillNos(wayBills);

			//对账单明细集合不为空
			if (CollectionUtils.isNotEmpty(ppsEntityByWaybillList)) {
				// 保存对账单号集合
				List<String> statementNos = new ArrayList<String>();
				//循环处理
				for (PartnerPayStatementDEntity ppsEntity : ppsEntityByWaybillList) {
					//获取对账单号加入到对账单号列表
					statementNos.add(ppsEntity.getStatementBillNo());
				}
				//判断对账单号是否为空
		    	if(CollectionUtils.isEmpty(statementNos)){
					throw new SettlementException("执行按对账单号集合查询对账单信息时，对账单号集合为空！","");
				}
		    	//校验网点编码是否为空
		    	if(StringUtil.isEmpty(queryDto.getOrgCode())){
		    		throw new SettlementException("当前登录用户的操作部门编码为空", "");
		    	}
		    	//设置对账单号集合属性
		    	queryDto.setPayableBillNosList(statementNos);
				// 根据对账单号查询对账单信息
				ppsEntityList = partnerPayStatementDao.queryPayStatementByBillNos(queryDto);
			}
		//按部门查询
		}else if (SettlementConstants.TAB_QUERY_BY_FAILING_INVOICE.equals(queryDto.getQueryPage())) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("empCode", queryDto.getEmpCode());
			map.put("orgCode", queryDto.getStatementOrgCode());
			//根据部门查询合伙人对账单总数和总金额
			resultDto = partnerPayStatementDao.countStatementByFailingInvoice(map);
			if(resultDto != null && resultDto.getTotalCount()>0){
				//根据部门查询合伙人对账单信息
				ppsEntityList = partnerPayStatementDao.queryStatementByFailingInvoice(map,start,limit);
			}
		} else {
			//提示界面传入参数异常
			throw new SettlementException("界面传入参数异常！", "");
		}
		if (SettlementConstants.TAB_QUERY_BY_DATE.equals(queryDto
				.getQueryPage())
				|| SettlementConstants.TAB_QUERY_BY_FAILING_INVOICE
						.equals(queryDto.getQueryPage())) {
			// 设置对账单集合
			resultDto.setPartnerPayStatementList(ppsEntityList);
		} else{
			// 声明本次应付总金额
			BigDecimal totalPeriodPayAmount = BigDecimal.ZERO;
			// 循环对账单列表计算
			for (PartnerPayStatementEntity en : ppsEntityList) {
				//设置未还款金额
				totalPeriodPayAmount = totalPeriodPayAmount.add(en.getPeriodPayAmount());
			}
			// 设置对账单集合
			resultDto.setPartnerPayStatementList(ppsEntityList);
			// 设置本次剩余未还金额
			resultDto.setTotalAmount(totalPeriodPayAmount);
			//计数
			resultDto.setTotalCount(ppsEntityList.size());
		}
		//返回dto
		return resultDto;
	}
	
	/**
	 * 根据对账单号查询对账单明细信息
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-02-23 下午 5:11:16	
	 */
	@Override
	public List<PartnerPayStatementDEntity> queryDetailByStatementBillNos(List<String> statementBillNos) {
		if (CollectionUtils.isEmpty(statementBillNos)) {
			throw new SettlementException("执行按对账单号查询对账单明细操作时，对账单号为空！", "");
		}
		return (List<PartnerPayStatementDEntity>) partnerPayStatementDDao
				.queryByStatementBillNos(statementBillNos);
	}
	
	
	/**
	 * 设置可查询部门结果集、登录人编码
	 * @param updateDto
	 * @param queryDto
	 */
	private void validateDto(PartnerPayStatementDto updateDto, PartnerPayStatementDto queryDto) {
		//声明目标部门集合
		List<String> targetLsit = new ArrayList<String>();
		
		//如果部门存在，则获取当前部门与用户所管辖部门的交集
		if (StringUtils.isNotBlank(queryDto.getStatementOrgCode())) {
			targetLsit.add(queryDto.getStatementOrgCode());
		} else{
			//如果部门不存在，小区存在，则获取小区地下所有部门与该用户所管辖部门交集	
			if(StringUtils.isNotBlank(queryDto.getSmallRegion())){
				//调用综合方法查询
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(queryDto.getSmallRegion());
				//循环部门，获取用户所管辖部门与查询到部门的交集
				for(OrgAdministrativeInfoEntity en: orgList){
					targetLsit.add(en.getCode());
				}
			}else{
				//如果部门、小区都不存在，而大区存在，	则获取大区底下所有部门与该用户所管辖部门交集	
				if(StringUtils.isNotBlank(queryDto.getLargeRegion())){
					//调用综合方法查询
					List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(queryDto.getLargeRegion());
					//循环部门，获取用户所管辖部门与查询到部门的交集
					for(OrgAdministrativeInfoEntity en: orgList){
						targetLsit.add(en.getCode());
					}
				}
			}
		}
		// 设置可查询部门结果集
		updateDto.setOrgCodeList(targetLsit);
		//包装当前登录人编码 为后数据权限传参数 
		updateDto.setEmpCode(queryDto.getEmpCode());
	}
	
	/**
	 * 对账单付款
	 * @author wwb
	 * @date 2016-03-03
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional
	public String toPayment(BillPaymentEntity paymentEntity, String[] billNos, CurrentInfo currentInfo) {
		logger.info("进入付款service: 金额 [" + paymentEntity.getAmount()
				+ "]，付款对账单号集合：" + Arrays.toString(billNos));
		
		//如果对账单制作参数dto为空
		if (null == billNos || billNos.length == 0) {
			//提示传入参数为空，无法核销
			throw new SettlementException("传入参数为空，无法付款");
		}
		//得到对账单号集合
		List<String> statementBillNos = Arrays.asList(billNos);
		//按对账单单号查询对账单
		List<PartnerPayStatementEntity> partnerPayStatementEntities = queryByStatementBillNos(statementBillNos);
		if(CollectionUtils.isEmpty(partnerPayStatementEntities)){
			throw new SettlementException("查询到的对账单为空，请重新操作");
		}
		//如果对账单超过1条，则校验是否为同一个合伙人,和对账单状态
		if(partnerPayStatementEntities.size() > 1){
			validateCommonPayer(partnerPayStatementEntities);
		}
		//对账单确认状态判断
		if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM.equals(
				partnerPayStatementEntities.get(0).getStatementStatus())) {
			throw new SettlementException("对账单确认状态为未确认不可以进行付款!");
		}
		//应付
		BillWriteoffOperationDto writeoffResultDto = writeoffStatement(statementBillNos, currentInfo);
		// 声明当前日期
		Date now = new Date();
		// 获取单据编号 （此处单号是手动生成付款单）
		String paymentNum = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.FK1);
		if (StringUtils.isBlank(paymentNum)) {
			throw new SettlementException("获取付款单号有误！");
		}
		List<BillPaymentAddDto> addDtoList = new ArrayList<BillPaymentAddDto>();
		List<BillPayableEntity> list = writeoffResultDto.getBillPayableEntitys();
		for (BillPayableEntity pay : list) {
			//判断应付单是否已经核销
			if (pay.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
				// 将属性copy到付款单明细dto中
				BillPaymentAddDto addDto = new BillPaymentAddDto();
				//属性copy
				BeanUtils.copyProperties(pay, addDto);
				//设置属性值
				addDto.setCurrentPaymentAmount(pay.getUnverifyAmount());
				addDtoList.add(addDto);
			}
		}
		// 校验传入参数  -- -包含付款单实体的工作流类型也在里面进行封装
		Map map = validateAddPaymentForFSSC(paymentEntity,addDtoList, paymentNum,currentInfo);
		//从map中获取应付单预收单集合
		List<BillPayableDto> payableList = (List<BillPayableDto>) map.get("payableList");
		
		// 封装付款单实体
		dealPaymentEntity(paymentEntity, paymentNum, now);
		// 如果为电汇，生成汇款工作流,那么付款单上需要批次号的  --新增时候需要有批次号
		//获取付款批次号
		String batchNo = null;
		if (StringUtils.isNotBlank(paymentEntity.getBatchNo())) {
			batchNo = paymentEntity.getBatchNo();
		} else {
			batchNo = settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.FK_BN);
		}
		logger.info("--付款对账单付款操作：付款单号：" + paymentNum + ",付款批次号：" + batchNo);
		//封装批次号
		paymentEntity.setBatchNo(batchNo);
		// 新增付款单
		addBillPayment(paymentEntity,currentInfo);
		//付款添加付款单明细
		partnerStatementPaymentService.addBillPaymentD(paymentNum,statementBillNos);
		Map payableMap = new HashMap();
		payableMap.put("statementBillNos", statementBillNos);
		payableMap.put("paymentNum", paymentNum);
		payableMap.put("modifyUserCode", currentInfo.getEmpCode());
		payableMap.put("modifyUserName", currentInfo.getEmpName());
		payableMap.put("paymentNotes", payableList.get(0).getPaymentNotes());
		//付款更新应付信息
		partnerStatementPaymentService.payForBillPayable(payableMap);
		//对账单DTO
		PartnerPayStatementDto partnerPayStatementDto = new PartnerPayStatementDto();
		partnerPayStatementDto.setPartnerPayStatementList(partnerPayStatementEntities);
		//此处赋值的是对账单号
		partnerPayStatementDto.setStatementBillNoList(statementBillNos);
		//调用修改对账单接口
		updateStatementForWriteOff(partnerPayStatementDto,currentInfo);
		//查询对账单明细
		List<PartnerPayStatementDEntity> partnerPayStatementDEntities = queryDetailByStatementBillNos(statementBillNos);
		// 获取传递给财务自助参数
		PayToCostcontrolDto dto = buildPayToFSSCDto(paymentEntity,payableList,partnerPayStatementDEntities,partnerPayStatementEntities,currentInfo);
		try {
			//设置事由说明
			if(StringUtils.isNotBlank(paymentEntity.getNotes())){
				//备注
				String notes = paymentEntity.getNotes();
				if(paymentEntity.getNotes().length()>SettlementReportNumber.THREE_HUNDRED){
					//截取299个字
					notes = paymentEntity.getNotes().substring(0,SettlementReportNumber.TWO_HUNDRED_AND_NINETY_NINE);
				}
				dto.setPayBillDiscription(notes);
			}
			logger.info("--付款对账单：申请付款工作流开始" + new Date());
			// 调用接口传递数据
			payToFSSCSendService.payToCostcontrol(dto);
		} catch (SettlementException e) {
			logger.error("--付款失败！" + e.getErrorCode(), e);
			throw new SettlementException("付款失败！" + e.getErrorCode());
		} catch (Exception e) {
			logger.error("--付款对账单：申请付款工作流失败！"+e.getMessage(), e);
			throw new SettlementException("申请付款工作流失败！"+e.getMessage(), e.getMessage());
		}
		logger.info("--付款对账单付款toPayment:end");
		return paymentNum;
	}
	
	/**
	 * 按对账单单号查询对账单信息
	 * @author wwb
	 * @date 2016-02-28
	 */
	@Override
	public List<PartnerPayStatementEntity> queryByStatementBillNos(List<String> statementBillNos) {
		//对账单DTO
		PartnerPayStatementDto partnerPayStatementDto = new PartnerPayStatementDto();
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户所在部门
		partnerPayStatementDto.setOrgCode(currentInfo.getCurrentDeptCode());
		//设置对账单单号
		partnerPayStatementDto.setPayableBillNosList(statementBillNos);
		//按对账单单号查询对账单
		List<PartnerPayStatementEntity> list = partnerPayStatementDao.queryPayStatementByBillNos(partnerPayStatementDto);
		
		//返回对账单实体
		return list;
	}
	
	/**
	 * 验证对账单集合为同一合伙人付款对象
	 * @author wwb
	 * @date 2016-02-28
	 */
	private void validateCommonPayer(List<PartnerPayStatementEntity> partnerPayStatementEntities) {
		//循环判断是否为同一合伙人付款
		for (int i = 0; i < partnerPayStatementEntities.size() - 1; i++) {
			PartnerPayStatementEntity p1 = partnerPayStatementEntities.get(i);
			PartnerPayStatementEntity p2 = partnerPayStatementEntities.get(i + 1);
			if(!org.apache.commons.lang3.StringUtils.equals(p1.getCustomerCode(), p2.getCustomerCode())){
				throw new SettlementException("请选择同一合伙人的对账单付款");
			}
			if(!org.apache.commons.lang3.StringUtils.equals(p1.getBillType(), p2.getBillType())){
				throw new SettlementException("请选择同一合伙人的对账单付款");
			}
			//对账单确认状态判断
			if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM.equals(
					p2.getStatementStatus())) {
				throw new SettlementException("对账单确认状态为未确认不可以进行付款!");
			}
		}
		
	}
	
	
	/**
	 * 对账单核销
	 * @author wwb
	 * @date 2016-02-28
	 */
	private BillWriteoffOperationDto writeoffStatement(List<String> statementBillNos,CurrentInfo currentInfo) {

		/*
		 * 获取应付单列表记录
		 */
		BillWriteoffOperationDto writeoffResultDto = writeoffStatementDetail(statementBillNos/*, writeoffBatchNo, currentInfo*/);
		
		//返回核销结果dto
		return writeoffResultDto;
	}
	
	/**
	 * 核销对账单明细
	 * @author wwb
	 * @date 2016-02-28
	 */
	private BillWriteoffOperationDto writeoffStatementDetail(List<String> statementBillNos/*, String writeoffBatchNo,CurrentInfo currentInfo*/) {

		//根据对账单号查询对账单明细信息
		List<PartnerPayStatementDEntity> statementDetailEntitys = partnerPayStatementDDao.queryByStatementBillNos(statementBillNos);
		//如果对账单明细列表为空
		if (CollectionUtils.isEmpty(statementDetailEntitys)) {
			//提示 对账单明细记录为空，无法核销
			throw new SettlementException("对账单明细记录为空，无法付款");
		}

		// 核销操作Dto
		BillWriteoffOperationDto writeoffDto = new BillWriteoffOperationDto();

		// 从对账单明细中获取应付单号
		List<String> payNos = new ArrayList<String>();//应付单号
		//循环处理对账单明细
		for (PartnerPayStatementDEntity statmentDetailEntity : statementDetailEntitys) {
			//加入到应付单号列表
			payNos.add(statmentDetailEntity.getPayableNo());
		}

		// 根据来源单号查询应付单
		//如果应付单号不为空
		if (CollectionUtils.isNotEmpty(payNos)) {
			//查询应付单
			List<BillPayableEntity> pays = billPayableService.queryByPayableNOs(payNos, FossConstants.ACTIVE);
			// 校应付单原始单据数量和对账单明细数量是否匹配
			if ((CollectionUtils.isNotEmpty(pays) && pays.size() != payNos.size()) || CollectionUtils.isEmpty(pays)) {
				//提示对账单明细记录中的应付单和原始记录信息不一致，无法核销，请联系管理员
				throw new SettlementException("对账单明细记录中的应付单和原始记录信息不一致，无法核销，请联系管理员");
			}
			// 设置核销参数中的核销方列表：应付单列表
			writeoffDto.setBillPayableEntitys(pays);
		}

		//返回核销结果dto
		return writeoffDto;
	}
	
	/**
	 * 新增校验,并将更新应付单需要参数进行封装到dto中
	 * @author wwb
	 * @date 2016-03-03
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map validateAddPaymentForFSSC(BillPaymentEntity paymentEntity, List<BillPaymentAddDto> addDtoList, String paymentNum,CurrentInfo currentInfo) {
		logger.info("付款单新增时校验开始");
		//合伙人--付款 工作流类型
		String workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_PAY;
		
		//声明付款类型 报销还是付款
//		String payType = SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY;//付款
//		paymentEntity.setPaymentType(payType);
		//校验界面传入参数
		validateParams(paymentEntity,addDtoList);
		
		// 声明本次付款金额总和
		BigDecimal payableUnVerifyAmount = BigDecimal.ZERO;
		
		// 声明要更新的应付单集合
		List<BillPayableDto> billPayables = new ArrayList<BillPayableDto>();
		//声明付款单明细
		List<BillPaymentDEntity> payDetailList = new ArrayList<BillPaymentDEntity>();
		//声明应付单单号列表
		List<String> payableNos = new ArrayList<String>();
		//声明付款单明细单号对应的支付金额map
		Map<String,BigDecimal> amountMapByNo = new HashMap<String,BigDecimal>();
		
		// 循环获取dto中本次付款金额
		for (BillPaymentAddDto dto : addDtoList) {
			//校验界面传入id
			if(StringUtils.isBlank(dto.getId())){
				throw new SettlementException("付款单明细中应付单号：" + dto.getPayableNo() + "的明细id为空！");
			}
			
			//如果为应付单
			if(SettlementUtil.isPayable(dto.getPayableNo())){
				payableNos.add(dto.getPayableNo());
				amountMapByNo.put(dto.getPayableNo(), dto.getCurrentPaymentAmount());
				//封装应付单要用的dtolist
				BillPayableDto entity = new BillPayableDto();
				BillPayableEntity payEntity = billPayableService.queryByPayableNO(dto.getPayableNo(),"Y");
				if(null == payEntity){
					throw new SettlementException("没有有效的应付单！请检查应付单号。");
				}
				entity.setExpenseBearCode(payEntity.getExpenseBearCode());
				entity.setId(dto.getId());// 封装应付单id
				entity.setBillType(dto.getBillType());//获取单据子类型
				entity.setPayableNo(dto.getPayableNo());//应付单号
				entity.setAccountDate(dto.getAccountDate());// 封装记账日期
				entity.setBusinessDate(dto.getBusinessDate());//封装业务日期
				entity.setVersionNo(dto.getVersionNo());// 封装版本号
				entity.setPaymentAmount(dto.getCurrentPaymentAmount());// 封装本次付款金额
				entity.setPaymentNo(paymentNum);// 封装付款单号
				entity.setPaymentNotes(dto.getNotes());// 封装本次付款明细备注
				//将数据封装到应付单dto的集合中
				billPayables.add(entity);
				//累加应付单金额
				payableUnVerifyAmount = payableUnVerifyAmount.add(dto.getCurrentPaymentAmount());
			}else{
				throw new SettlementException("付款单明细中应付单号：" + dto.getPayableNo() + "的单据类型不是应付单！");
			}
		}	
		
		//查询应付单
		if(payableNos.size()>0){
			//调用应付单接口查询应付单信息
			List<BillPayableEntity> payableList = billPayableService.queryByPayableNOs(payableNos, FossConstants.ACTIVE);
			//如果查询应付单条数与传入条数不等，则弹出提示
			if(payableList.size()!=payableNos.size()){
				throw new SettlementException("查询付款单明细与传入明细不等，请关闭界面，重新制作！");
			}
			//校验新查询数据与界面传入的dto是否一致
			for(int i=0;i<payableList.size();i++){
				//获取应付单entity
				BillPayableEntity entity = payableList.get(i);
			
				//循环界面列表集合，与查询出来数据进行比较
				for(int j=0;j<billPayables.size();j++){
					//获取dto
					BillPayableDto dto = billPayables.get(j);
					//比较新查询出数据与界面数据是否一致，根据同一单号
					if(entity.getPayableNo().equals(dto.getPayableNo())){
						//判断版本号是否一致
						if(!entity.getVersionNo().equals(dto.getVersionNo())){
							throw new SettlementException("付款单明细中应付单号："+ dto.getPayableNo() + "的数据已经发生变化，请刷新应付单或对账单页面，重新进行付款");
						}
						dto.setBusinessDate(entity.getBusinessDate());
						dto.setAccountDate(entity.getAccountDate());
						dto.setBillType(entity.getBillType());
						dto.setSourceBillNo(entity.getSourceBillNo());//封装来源单据编号 --给外请车付款用来查询配载单数据
						dto.setWaybillNo(entity.getWaybillNo());//封装运单号
						dto.setProductCode(entity.getProductCode());//获取产品类型
						//跳出循环
						break;
					}
				}
				//声明付款单明细
				BillPaymentDEntity dEntity = new BillPaymentDEntity();
				//设置值
				dEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT_D_SOURCE_BILL_TYPE_PAYABLE);
				//设置值
				dEntity.setSourceBillNo(entity.getPayableNo());
				//设置值
				dEntity.setWaybillNo(entity.getWaybillNo());
				//设置值
				dEntity.setSrcSourceBillNo(entity.getSourceBillNo());
				//设置值
				dEntity.setSourceAccountDate(entity.getAccountDate());
				//设置支付金额
				dEntity.setPayAmount(amountMapByNo.get(entity.getPayableNo()));
				//添加到明细列表中
				payDetailList.add(dEntity);
				
			}
			//校验应付单数据是否可以支付
			validateCanPay(payableList,paymentEntity.getPaymentType());
		}
	
		// 校验明细本次付款金额总和和付款单头的金额是否相等
		if (paymentEntity.getAmount() != null
				&& paymentEntity.getAmount().compareTo(payableUnVerifyAmount) != 0) {
			throw new SettlementException("本次付款金额明细之和与付款单金额不等！");
		}
		
		//在此处来确认付款单的工作流类型
		if(StringUtils.isNotBlank(workFlowType)){
			paymentEntity.setWorkFlowType(workFlowType);
		}else{
			throw new SettlementException("付款工作流类型为不能为空！");
		}
		//校验付款单明细
		if(CollectionUtils.isEmpty(payDetailList)){
			throw new SettlementException("付款单明细不能为空！");
		}
		//声明传出map	
		Map map = new HashMap();	
		//封装付款单列表
		map.put("payableList", billPayables);
		//封装付款单明细列表
		map.put("payDetailList", payDetailList);
		logger.info("付款单新增时校验结束");
		return map;
	}
	
	/**
	 * 校验界面传入参数
	 * @author wwb
	 * @date 2016-03-03
	 */
	private void validateParams(BillPaymentEntity paymentEntity,List<BillPaymentAddDto> addDtoList){
		// 如果明细size小于0，则弹出提示
		if (CollectionUtils.isEmpty(addDtoList)) {
			throw new SettlementException("录入付款单，至少有一条明细数据！");
		}
		// 校验前台必填项
		if (paymentEntity != null) {
			// 判断部门名称
			if (StringUtils.isBlank(paymentEntity.getPaymentOrgName())) {
				throw new SettlementException("部门名称不能为空！");
			}
			// 如果子公司编码为空，则抛出异常
			if (StringUtils.isBlank(paymentEntity.getPaymentCompanyCode())) {
				throw new SettlementException("子公司编码不能为空！");
			}
			// 付款方式校验
			if (StringUtils.isBlank(paymentEntity.getPaymentType())) {
				throw new SettlementException("付款方式不能为空！");
			}
			
			// 付款金额校验
			if (paymentEntity.getAmount() == null
					|| paymentEntity.getAmount().compareTo(BigDecimal.ZERO) < 1) {
				throw new SettlementException("金额不能为空且必须大于0！");
			}
			//来源单据类型校验  （作废要用到该字段）
			if (StringUtils.isBlank(paymentEntity.getSourceBillType())) {
				throw new SettlementException("来源单据类型不能为空！");
			}
			// 如果付款方式不能为空，且为电汇，则需要判断银行等信息
			// 如果银行账号为空，则抛出异常
			if (StringUtils.isBlank(paymentEntity.getAccountNo())) {
				throw new SettlementException("付款方式为电汇时银行账号不能为空！");
			}
			// 省份编码不能为空
			if (StringUtils.isBlank(paymentEntity.getProvinceCode())) {
				throw new SettlementException("省份编码不能为空！");
			}
			// 城市编码不能为空
			if (StringUtils.isBlank(paymentEntity.getCityCode())) {
				throw new SettlementException("城市编码不能为空！");
			}
			// 开户银行不能为空
			if (StringUtils.isBlank(paymentEntity.getBankHqCode())) {
				throw new SettlementException("开户银行编码不能为空！");
			}
			// 行号不能为空
			if (StringUtils.isBlank(paymentEntity.getBankBranchCode())) {
				throw new SettlementException("行号不能为空！");
			}
			// 账户类型
			if (StringUtils.isBlank(paymentEntity.getAccountType())) {
				throw new SettlementException("账户类型不能为空！");
			}
			// 收款人不能为空
			if (StringUtils.isBlank(paymentEntity.getPayeeName())) {
				throw new SettlementException("收款人不能为空！");
			}
			// 收款人手机号不能为空
			if (StringUtils.isBlank(paymentEntity.getMobilePhone())) {
				throw new SettlementException("收款人手机号不能为空！");
			}
		}
	}
	
	
	/**
	 * 校验是否可以支付
	 * @param payableList--应付单列表  ，payType--付款单的付款类型（此参数是在录入付款单调用该方法时传入）
	 * @author wwb
	 * @date 2016-03-03
	 */
	private void validateCanPay(List<BillPayableEntity> payableList,String payType) {
		//声明应付单银行账号
		String accountNo = null;
		//声明循环次数
		int cycleTimes=0;
		//理赔单的应付单号
		String calimNo=null;

		// 循环校验
		for (BillPayableEntity bill : payableList) {
			//循环次数
			cycleTimes=cycleTimes+1;
			
			//判断是否有效
			if(!FossConstants.ACTIVE.equals(bill.getActive())){
				throw new SettlementException("应付单号：" + bill.getPayableNo()+ "为无效应付单，不能进行付款操作！");
			}
			// 审核状态必须是已审核
			if(bill.getApproveStatus()==null || !SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE.equals(bill.getApproveStatus())){
				throw new SettlementException("应付单号：" + bill.getPayableNo()+ "还未审核，不能进行付款！");
			}
			// 完全核销的应付单不能付款
			if (bill.getUnverifyAmount() == null|| BigDecimal.ZERO.compareTo(bill.getUnverifyAmount()) == 0) {
				throw new SettlementException("应付单号：" + bill.getPayableNo()+ "已经核销完毕，不能付款！");
			}
			// 如果没有生效，则不能付款
			if (SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO.equals(bill.getEffectiveStatus())) {
				throw new SettlementException("应付单号：" + bill.getPayableNo()+ "还未生效，不能进行付款操作！");
			}
			// 如果存在付款单号，则表示已经付过款，不能再次付款
			if (!SettlementConstants.DEFAULT_BILL_NO.equals(bill.getPaymentNo())&& StringUtils.isNotBlank(bill.getPaymentNo())) {
				throw new SettlementException("应付单号：" + bill.getPayableNo()+ "已经付过一次款，一个付款单只能付款一次！");
			}
			//初始化银行账号字段
			if(cycleTimes==1){
				//初始化应付单号
				calimNo = bill.getPayableNo();
				accountNo = bill.getAccountNo();
			//如果银行账号不相等，则抛出提示
			}else if(!StringUtils.equals(accountNo, bill.getAccountNo())){
				throw new SettlementException("应付单号：" + bill.getPayableNo()+"的银行账号与"+calimNo+"的银行账号不相同，不能一起支付");
			}
		}
	}
	
	
	/**
	 * 新增时，处理付款单实体，保存
	 * @author wwb
	 * @date 2016-03-03
	 */
	private BillPaymentEntity dealPaymentEntity(BillPaymentEntity paymentEntity, String paymentNum, Date nowDate) {
		paymentEntity.setId(UUIDUtils.getUUID());// 获取id
		paymentEntity.setPaymentNo(paymentNum);// 设置单号
		paymentEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// 设置币种
		paymentEntity.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING);// 设置汇款状态为”汇款中“
		paymentEntity.setAuditStatus(SettlementDictionaryConstants.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT);// 设置审核状态为”未审核“
		paymentEntity.setSourceBillNo(SettlementConstants.DEFAULT_BILL_NO);// 来源单据编号 'N/A'
		paymentEntity.setActive(FossConstants.ACTIVE);// 是否有效
		paymentEntity.setIsRedBack(FossConstants.NO);// 是否红单
		paymentEntity.setBusinessDate(nowDate);// 设置业务日期
		paymentEntity.setAccountDate(nowDate);// 设置记账日期
		paymentEntity.setIsInit(FossConstants.NO);// 是否初始化
		paymentEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);// 手动录入
		paymentEntity.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号
		return paymentEntity;
	}
	
	/**
	 * 新增付款单
	 * @author wwb
	 * @date 2016-03-03
	 */
	private void addBillPayment(BillPaymentEntity entity, CurrentInfo currentInfo) {
		//实体不能为空或id不能为空，付款单号不能为空，业务日期和记账日期都不能为空
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| StringUtils.isEmpty(entity.getPaymentNo())
				|| entity.getBusinessDate() == null
				|| entity.getAccountDate() == null) {
			throw new SettlementException("新增付款单传入的参数不能为空！");
		}
		
		logger.info("新增付款单Start, 付款单号：" + entity.getPaymentNo());
		
		//设置操作者信息
		Date date = new Date();
		
		//设置创建时间
		entity.setCreateTime(date);
		
		//设置创建人编码
		entity.setCreateUserCode(currentInfo.getEmpCode());
		
		//创建人名称
		entity.setCreateUserName(currentInfo.getEmpName());
		
		//设置修改时间
		entity.setModifyTime(date);
		
		//设置修改人编码
		entity.setModifyUserCode(currentInfo.getEmpCode());
		
		//设置修改人名称
		entity.setModifyUserName(currentInfo.getEmpName());

		// 业务日期在记账日期之后 ，提示不能保存
		Date bussinessDate = entity.getBusinessDate();
		
		//记账日期格式化到秒，之后进行比较
		Date accountDate = entity.getAccountDate();
		
		//业务日期如果在记账日期之后，提示异常信息
		if (bussinessDate.after(accountDate)) {
			throw new SettlementException("记账日期小于业务日期，不能进行新增付款单操作！");
		}
		
		// FOSS生成的所有单据的初始化状态都为"N"
		entity.setIsInit(FossConstants.NO);
		
		// FOSS生成的所有单据的初始化状态都为"N"
		entity.setIsInit(FossConstants.NO);
		//保存数据后返回值
		billPaymentService.addBillWoodenPayment(entity);
		
		logger.info("新增付款单End, 付款单号：" + entity.getPaymentNo());
	}
	
	
	/**
	 * 封装给财务共享的付款接口参数
	 * @author wwb
	 * @date 2016-03-03
	 */
	private PayToCostcontrolDto buildPayToFSSCDto(BillPaymentEntity bill, List<BillPayableDto> payableList, List<PartnerPayStatementDEntity> partnerPayStatementDEntities,List<PartnerPayStatementEntity> partnerPayStatementEntities, CurrentInfo currentInfo) {
		// 声明传递给共享的dto
		PayToCostcontrolDto dto = new PayToCostcontrolDto();
		BillPayableDto payable = payableList.get(0);
		//声明付款明细
		List<com.deppon.fssc.inteface.domain.payment.ExpenseDetail> detail = new ArrayList<com.deppon.fssc.inteface.domain.payment.ExpenseDetail>();
		//对账单单据子类型
		String billType = partnerPayStatementEntities.get(0).getBillType();
		//wwb
		com.deppon.fssc.inteface.domain.payment.ExpenseDetail d = null;
		//循环对账明细插入付款明细
		for (BillPayableDto entity : payableList) {
			d = new com.deppon.fssc.inteface.domain.payment.ExpenseDetail();
			d.setBillNumber(entity.getPayableNo());//应付单号
			d.setExpenseExplain(entity.getWaybillNo());//费用说明   默认运单号
			d.setExpensesMoney(entity.getPaymentAmount());//对账单明细金额
			//转化给对应共享那边费用明细
			String payType = convertBillType(payable.getPayableNo(),billType);
			d.setExpensesType(payType);
			d.setPayBillExpDate(entity.getBusinessDate());//业务发生日期
			d.setExpenseCostCenter(entity.getExpenseBearCode());//部门标杆编码
			if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE.equals(billType)){
				//是否增值税专用发票
				d.setIsvat(bill.getIsTaxinvoice() == true ? FossConstants.YES : FossConstants.NO);//是否增值税专用发票
				//是
				if(FossConstants.YES.equals(d.getIsvat())){
					d.setInvatNum(bill.getVatInvoice());//增值税发票号码
					d.setTaxfreePrice(bill.getTaxfreePrice());//不含税金额
					d.setTaxPrice(bill.getTaxPrice());//税额
					d.setTaxpayerId(bill.getTaxpayerNumber());//纳税人识别编码
				}
			}
			detail.add(d);
		}
		// 声明付款集合
		dto.setPaymentBillNo(bill.getBatchNo());//获取付款编号
		dto.setPayBillDeptNo(currentInfo.getDept().getUnifiedCode());// 获取部门标杆编码
		dto.setPayBillDeptName(bill.getPaymentOrgName());// 获取部门名称
		dto.setPayBillAmount(bill.getAmount());// 获取总金额
		dto.setPayBillComNo(bill.getInvoiceHeadCode());// 获取公司编码
		dto.setPayBillBankNo(bill.getAccountNo());// 获取银行账号
		dto.setAccountType(bill.getAccountType());//账户类型
		dto.setBankHqCode(bill.getBankHqCode());//开户行编码
		dto.setBankBranchCode(bill.getBankBranchCode());//开户行支行编码
		dto.setProvinceCode(bill.getProvinceCode());//省份编码
		dto.setCityCode(bill.getCityCode());//城市编码
		dto.setPayBillPayeeName(bill.getPayeeName());// 获取收款人名称
		dto.setPayBillPayeeCode(bill.getPayeeCode());//收款人编码
		dto.setPayBillCelephone(bill.getMobilePhone());// 获取联系方式
		dto.setPayBillAppNo(currentInfo.getEmpCode());// 获取员工编号
		dto.setPayBillLastTime(new Date());//获取最迟汇款日期 --now time
		dto.setPayBillAppType(bill.getWorkFlowType());//工作流类型
		dto.setPayType(SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY);//支付类型  --付款
		dto.setCurrency(SettlementESBDictionaryConstants.CURRENCY_CODE_RMB);//币种--人民币
		dto.setIsAutoAbatementLoan(SettlementESBDictionaryConstants.AUTOABATEMENTLOAN_N);//电汇默认为N,只有申请备用金才可能自动冲借支
		dto.setExchangeRate(null);//汇率默认为空， foss暂时没有汇率
		dto.setExpenseDetailToFSSC(detail);
		dto.setBatchNo(bill.getBatchNo());//工作流号
		return dto;
	}
	
	/**
	 * 根据单据子类型转化为对应报账明细类型  --仅限于应付单子类型
	 * @author wwb
	 * @date 2016-03-03
	 */
	public static String convertBillType(String number,String billType){
		String payType = null;
		//生命费用明细类型
		//到达提成
		if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE.equals(billType)){
			payType = SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_DELIVERY;
		}else{
			//委托派费
			payType = SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_DELEGATION;//FOSS包装费--付款
		}
		return payType;
	}
	
	/**
	 * 更新付款对账单
	 * @author wwb
	 * @date 2016-03-16
	 */
	private void updateStatementForWriteOff(PartnerPayStatementDto partnerPayStatementDto,CurrentInfo cInfo) {
		//当前用户工号
		partnerPayStatementDto.setEmpCode(cInfo.getEmpCode());
		//当前用户姓名
		partnerPayStatementDto.setEmpName(cInfo.getEmpName());
		//设置应付金额
		partnerPayStatementDto.setSettleNum("1");
		//设置已经支付
		partnerPayStatementDto.setIsPaid("Y");
		//更新对账单
		//partnerPayStatementDao.partnerStatementUpdateByStatementBillNos(partnerPayStatementDto);
		//付款成功，更新付款次数和本期应付金额
		partnerPayStatementDao.updatePartnerStatementPayAmount(partnerPayStatementDto);
		//更新对账单明细表
		partnerPayStatementDDao.partnerStatementUpdateByStatementBillNos(partnerPayStatementDto);
	}
	
	/**
	 * 修改对账单界面：确认对账单
	 * 
	 * @author 088933-foss-wwb
	 * @date 2016-3-3 下午4:46:59
	 */
	@Transactional
	@Override
	public PartnerPayStatementDto confirmStatement(PartnerPayStatementDto queryDto) {
		//记录日志
		logger.info("更改对账单状态开始...");
		if(StringUtils.isBlank(queryDto.getStatementBillNo())){
			throw new SettlementException("对账单号为空！");
		}
		
		//判断对账单状态
		if(StringUtils.isNotBlank(queryDto.getConfirmStatus())){
			if("N".equals(queryDto.getConfirmStatus())){
				//设置已确认状态
				queryDto.setConfirmStatus("C");
			}else{
				//设置未确认状态
				queryDto.setConfirmStatus("N");
			}
			//调用dao更改对账单状态
			int updateRow;
			try {
				updateRow = this.partnerPayStatementDao.updateStatusByStatementNo(queryDto);
				//查询对账单
				List<String> billList = new ArrayList<String>();
				//设置对账单单号
				billList.add(queryDto.getStatementBillNo());
				queryDto.setPayableBillNosList(billList);
				//查询对账单
				List<PartnerPayStatementEntity> partnerPayStatementEntities = partnerPayStatementDao.queryPayStatementByBillNos(queryDto);
				if(CollectionUtils.isEmpty(partnerPayStatementEntities)){
					throw new SettlementException("操作失败，查询不到对账单信息");
				}
				queryDto.setPartnerPayStatementEntity(partnerPayStatementEntities.get(0));
			} catch (BusinessException e) {
				// 记录日志
				logger.error(e.getMessage(), e);
				throw new SettlementException(e.getErrorCode());
			}
			//判断更新行数
			if(updateRow == 0){
				throw new SettlementException("更新对账单状态失败！");
			}
			return queryDto;
		}else{
			throw new SettlementException("对账单状态为空！");
		}
	}
	
	
	/**
	 * 修改对账单功能：删除对账单明细方法
	 * 
	 * @author wwb
	 * @date 2016-03-03
	 */
	@Transactional
	@Override
	public PartnerPayStatementDto deleteStatementDetail(PartnerPayStatementDto resultDto, CurrentInfo currentInfo) {
		logger.info("删除对账单明细开始，enter service ");
		// 校验对账单是否已确认，如果已经确认则不允许删除明细信息
		if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM.equals(resultDto.getConfirmStatus())) {
			//提示对账单已确认，不能删除明细信息
			throw new SettlementException("对账单已确认，不能删除明细信息！", "");
		}
		//获取对账单明细的Id
		String id = resultDto.getId();
		//根据id查询明细信息
		PartnerPayStatementDEntity partnerPayStatementDEntity = partnerPayStatementDDao.queryStatementDById(id);
		if(currentInfo == null){
			throw new SettlementException("当前登陆信息为空，请退出重新登陆！");
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		//设置应付单号
		paramMap.put("paymentNum", partnerPayStatementDEntity.getPayableNo());
		//修改人编号
		paramMap.put("modifyUserCode", currentInfo.getEmpCode());
		//修改人姓名
		paramMap.put("modifyUserName", currentInfo.getEmpName());
		//默认对账单号
		paramMap.put("statementBillNo", SettlementConstants.DEFAULT_BILL_NO);
		//更新应付单信息
		int updateRows = partnerStatementPaymentService.updatePayableForDel(paramMap);
		if(updateRows == 0){
			throw new SettlementException("更新应付单失败，请重新操作！");
		}
		if(updateRows != 1){
			throw new SettlementException("应付单单号存在重复！");
		}
		
		partnerPayStatementDEntity = new PartnerPayStatementDEntity();
		//设置id
		partnerPayStatementDEntity.setId(id);
		//设置删除标识
		partnerPayStatementDEntity.setIsDelete(FossConstants.YES);
		//设置删除时间
		partnerPayStatementDEntity.setDisableTime(new Date());
		int rows;
		//更新对账单明细
		rows = partnerPayStatementDDao.updateStatementEntryByDEntity(partnerPayStatementDEntity);
		if(rows == 0){
			throw new SettlementException("对账单明细删除失败，请重新操作！");
		}
		if(rows != 1){
			throw new SettlementException("对账单单号存在重复！");
		}
		
		//根据对账单号查询对账单明细
		List<PartnerPayStatementDEntity> partnerPayStatementDEntities = partnerPayStatementDDao.queryByStatementBillNo(resultDto.getStatementBillNo());
		//计算明细金额
		//BigDecimal periodAmount = totalDetailAmount(partnerPayStatementDEntities);
		//设置明细金额
		//resultDto.setPeriodAmount(periodAmount);
		//获取对账单所有明细中的金额之和以及账期开始和结束日期
		PartnerPayStatementDto dto = partnerPayStatementDDao.queryTotalAmountByStatementNo(resultDto.getStatementBillNo());
		//设置总金额
		resultDto.setPeriodAmount(dto.getPeriodAmount());
		//账期开始日期
		resultDto.setPeriodBeginDate(dto.getPeriodBeginDate());
		//账期结束日期
		resultDto.setPeriodEndDate(dto.getPeriodEndDate());
		
		//更新对账单本期发生额、本期应付金额
		rows = partnerPayStatementDao.updateAmountByStatementNo(resultDto);
		if(rows == 0){
			throw new SettlementException("对账单明细删除失败，请重新操作！");
		}
		if(rows != 1){
			throw new SettlementException("对账单单号存在重复！");
		}
		//查询对账单
		List<String> payableList = new ArrayList<String>();
		//设置对账单单号
		payableList.add(resultDto.getStatementBillNo());
		resultDto.setPayableBillNosList(payableList);
		//查询对账单
		List<PartnerPayStatementEntity> partnerPayStatementEntities = partnerPayStatementDao.queryPayStatementByBillNos(resultDto);
		PartnerPayStatementEntity partnerPayStatementEntity = new PartnerPayStatementEntity();
		if(!CollectionUtils.isEmpty(partnerPayStatementEntities)){
			partnerPayStatementEntity = partnerPayStatementEntities.get(0);
		}
		//设置对账单
		resultDto.setPartnerPayStatementEntity(partnerPayStatementEntity);
		//设置对账单明细
		resultDto.setPartnerPayStatementDList(partnerPayStatementDEntities);
		return resultDto;
	}
	
	
	/* 批量删除合伙人付款对账单明细
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerPayStatementManagerService#batchDeleteStatementEntry(java.util.List, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	@Transactional
	public PartnerPayStatementDto batchDeleteStatementEntry(
			List<PartnerPayStatementDEntity> detailList, CurrentInfo currentInfo) {
		if(CollectionUtils.isEmpty(detailList)){
			throw new SettlementException("批量删除的明细为空");
		}
		//获取对账单号
		String statementBillNo = detailList.get(0).getStatementBillNo();
		//1.校验付款对账单状态
		validatePayStatementEntity(statementBillNo);
		
		//按照1000的大小分割成多个list批量更新
		List<List<PartnerPayStatementDEntity>> splitList = CollectionUtils.splitListBySize(detailList, FossConstants.ORACLE_MAX_IN_SIZE);
		
		//更新的参数map
		Map<String,Object> params = new HashMap<String, Object>();
		
		//2.根据明细id批量删除对账单明细
		//设置删除标识
		params.put("isDelete", FossConstants.YES);
		//设置删除时间
		params.put("disableTime", new Date());
		
		// 3.根据应付单号更新应付单中的对账单，将对账单号重置为默认值
		// 修改人编号
		params.put("modifyUserCode", currentInfo.getEmpCode());
		// 修改人姓名
		params.put("modifyUserName", currentInfo.getEmpName());
		// 默认对账单号
		params.put("statementBillNo", SettlementConstants.DEFAULT_BILL_NO);

		// 4.分批处理
		if (CollectionUtils.isNotEmpty(splitList)) {
			for(List<PartnerPayStatementDEntity> itemList : splitList){
				// 设置要更新的list
				params.put("detailList", itemList);
				
				// 批量删除对账单明细方法
				int deleteRow = partnerPayStatementDDao
						.batchDeleteStatementEntry(params);
				logger.info("分批删除对账单明细条数：" + deleteRow + "明细数量："
						+ itemList.size());

				// 批量更新应付单中的对账单号
				int updateRow = partnerStatementPaymentService
						.batchUpdatePayableStatementBillNo(params);
				logger.info("分批更新的应付单条数：" + updateRow + "明细数量："
						+ itemList.size());
				
			}
		}
		
		//5.获取对账单所有明细中的金额之和
		PartnerPayStatementDto dto = partnerPayStatementDDao.queryTotalAmountByStatementNo(statementBillNo);
		//设置对账单号
		dto.setStatementBillNo(statementBillNo);
		
		//6.更新对账单中的金额（本期发生额、本期应付金额）
		int rows = partnerPayStatementDao.updateAmountByStatementNo(dto);
		logger.info("更新对账单号" + statementBillNo + ",更新行数：" + rows);
		
		return dto;
	}
	
	/**
	 * 校验参数以及付款对账单状态
	 * @author gpz
	 * @date 2016年12月6日
	 */
	private void validatePayStatementEntity(String statementBillNo) {
		//根据对账单号查询对账单
		List<PartnerPayStatementEntity> entityList = partnerPayStatementDao.queryPayStatementByBillNo(statementBillNo);
		if(CollectionUtils.isEmpty(entityList)){
			throw new SettlementException("不存在对账单号是"+statementBillNo+"的对账单");
		}
		
		// 校验对账单是否已确认，如果已经确认则不允许删除明细信息
		if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM
				.equals(entityList.get(0).getStatementStatus())) {
			// 提示对账单已确认，不能删除明细信息
			throw new SettlementException("对账单已确认，不能删除明细信息！", "");
		}
	}

	/**
	 * 修改对账单功能：添加对账单明细方法
	 * 
	 * @author wwb
	 * @date 2016-03-09
	 */
	@Transactional
	@Override
	public PartnerPayStatementDto addPartnerPayStatementDetail(PartnerPayStatementDto dto) {
		logger.info("添加对账单明细，enter service ");
		//insert 明细表
		String statementBillNo = dto.getStatementBillNo();
		//如果对账单号为空
		if(org.apache.commons.lang3.StringUtils.isEmpty(statementBillNo)){
			throw new SettlementException("对账单单号获取失败！");
		}
		//获得选中的预添加明细
		PartnerPayStatementDEntity partnerPayStatementDEntity = dto.getPartnerPayStatementDEntity();
		// 获取当前登录用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		dto.setEmpCode(currentInfo.getEmpCode());
		dto.setEmpName(currentInfo.getEmpName());
		dto.setPayableNo(partnerPayStatementDEntity.getPayableNo());
		dto.setBillType(partnerPayStatementDEntity.getBillType());
		//更新应付单表
		int updateRows = partnerPayStatementDDao.updatePayableByPayableNo(dto);
		//没有更新记录
		if(0 == updateRows){
			throw new SettlementException("更新应付单失败，请重新查询");
		}
		//更新记录超过一条
		if(1 != updateRows){
			throw new SettlementException("添加失败，应付单出现重复");
		}
		//设置对账明细的对账单号
		partnerPayStatementDEntity.setStatementBillNo(statementBillNo);
		partnerPayStatementDEntity.setIsDelete(FossConstants.NO);
		//删除了未核销金额，但是数据库不能为空 设置为0
		//partnerPayStatementDEntity.setUnverifyAmount(BigDecimal.ZERO);
		//插入明细表
		updateRows = partnerPayStatementDDao.saveStatementDEntity(partnerPayStatementDEntity);
		if(updateRows == 0){
			throw new SettlementException("添加明细失败，请重新操作！");
		}
		//查询对账单
		List<String> billList = new ArrayList<String>();
		//设置对账单单号
		billList.add(statementBillNo);
		dto.setPayableBillNosList(billList);
		//查询对账单
		List<PartnerPayStatementEntity> partnerPayStatementEntities = partnerPayStatementDao.queryPayStatementByBillNos(dto);
		if(CollectionUtils.isEmpty(partnerPayStatementEntities)){
			throw new SettlementException("查询对账失败,检查对账单号是否存在");
		}
		PartnerPayStatementEntity partnerPayStatementEntity = partnerPayStatementEntities.get(0);
		Date periodBeginDate = dto.getPeriodBeginDate();
		Date periodEndDate = dto.getPeriodEndDate();
		//判断添加的明细和对账单的账期开始和结束日期对比
		/*periodBeginDate = periodBeginDate.compareTo(partnerPayStatementEntity.getPeriodBeginDate()) < 0 ?
				periodBeginDate : partnerPayStatementEntity.getPeriodBeginDate();
		periodEndDate = periodEndDate.compareTo(partnerPayStatementEntity.getPeriodEndDate()) > 0 ?
				periodEndDate : partnerPayStatementEntity.getPeriodEndDate();*/
		
		//根据对账单号查询总金额和账期开始日期以及账期结束日期
		PartnerPayStatementDto statementDto = partnerPayStatementDDao.queryTotalAmountByStatementNo(statementBillNo);
		//明细总金额之和
		BigDecimal periodAmount = statementDto.getPeriodAmount();
		//账期开始日期
		periodBeginDate = statementDto.getPeriodBeginDate();
		//账期结束日期
		periodEndDate = statementDto.getPeriodEndDate();
		
		//根据对账单号查询对账单明细
		List<PartnerPayStatementDEntity> partnerPayStatementDEntities = partnerPayStatementDDao.queryByStatementBillNo(statementBillNo);
		//设置对账单明细
		dto.setPartnerPayStatementDList(partnerPayStatementDEntities);
		//计算明细金额
//		BigDecimal periodAmount = totalDetailAmount(partnerPayStatementDEntities);
		
		//设置dto属性
		dto.setPeriodBeginDate(periodBeginDate);
		dto.setPeriodEndDate(periodEndDate);
		dto.setPeriodAmount(periodAmount);
		//设置返回的对账单信息
		partnerPayStatementEntity.setPeriodBeginDate(periodBeginDate);
		partnerPayStatementEntity.setPeriodEndDate(periodEndDate);
		partnerPayStatementEntity.setPeriodAmount(periodAmount);
		partnerPayStatementEntity.setPeriodPayAmount(periodAmount);
		dto.setPartnerPayStatementEntity(partnerPayStatementEntity);
		//更新对账单本期发生额、本期应付金额,日期
		updateRows = partnerPayStatementDao.updateAmountByStatementNo(dto);
		if(updateRows == 0){
			throw new SettlementException("对账单更新失败，请重新操作！");
		}
		if(updateRows != 1){
			throw new SettlementException("对账单单号存在重复！");
		}
		return dto;
	}
	
	/**
	 * 修改对账单功能：查询可添加对账单明细方法
	 * 
	 * @author wwb
	 * @date 2016-03-03
	 */
	@Override
	public PartnerPayStatementDto queryEntryForAdd(PartnerPayStatementDto dto, CurrentInfo currentInfo, int start,int limit) {
		logger.info("查询可添加对账单明细，enter service ");
		//参数dto不能为空
		if (dto == null) {
			//提示查询DTO为空
			throw new SettlementException("查询DTO为空！", "");
		}
		//记录日志
		logger.info("查询可添加明细开始，enter service ");
		//对账单明细列表
		List<PartnerPayStatementDEntity> partnerPayStatementDList = new ArrayList<PartnerPayStatementDEntity>();
		//获取当前用户所在部门
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		//设置总行数
		int count = 0;
		//根据时间和客户查询应付单据
		partnerPayStatementDList = partnerPayStatementDDao.queryStatementDByExistCustomer(dto,start,limit);
		//根据时间和客户查询应付单总行数
		count = partnerPayStatementDDao.countStatementDByCustomer(dto);
		//设置对账单明细返回列表
		dto.setPartnerPayStatementDList(partnerPayStatementDList);
		//设置对账单明细返回总行数
		dto.setCount(count);
		//返回参数
		return dto;
	}
	
	/**
	 * 根据对账单号集合查询对账单
	 * 
	 * @author wwb
	 * @date 2016-03-09
	 */
	@Override
	public List<PartnerPayStatementEntity> queryPartnerPayStatementEntitesByBills(PartnerPayStatementDto dto){
		logger.info("根据对账单号集合查询对账单，enter service ");
		//转化对账单号数组为集合
		List<String> statementBillNos = Arrays.asList(dto.getStatementBillNos());
		//设置对账单号集合
		dto.setPayableBillNosList(statementBillNos);
		//通过对账单号集合查询对账单
		List<PartnerPayStatementEntity> partnerPayStatementEntities = partnerPayStatementDao.queryPayStatementByBillNos(dto);
		return partnerPayStatementEntities;
	}
	
	/**
	 * 根据对账单号查询对账单明细
	 * 
	 * @author wwb
	 * @date 2016-03-09
	 */
	@Override
	public List<PartnerPayStatementDEntity> queryPartnerPayStatementEntriesByBillNo(PartnerPayStatementDto dto){
		logger.info("根据对账单号集合查询对账单明细，enter service ");
		//通过对账单号集合查询对账单
		List<PartnerPayStatementDEntity> partnerPayStatementDEntities = partnerPayStatementDDao.queryByStatementBillNo(dto.getStatementBillNo());
		return partnerPayStatementDEntities;
	}
	
	
	/**
	 * 付款失败回调更新
	 * @author wwb
	 * @date 2016-02-28
	 */
	@Transactional
	@Override
	public void updateBillAfterFailPay(List<String> statementBillNos, CurrentInfo currentInfo) {
		logger.info("付款失败回调更新 开始 【{}】", new Date());
		PartnerPayStatementDto dto = new PartnerPayStatementDto();
		//设置对账单号集合、结账次数
		dto.setStatementBillNoList(statementBillNos);
		dto.setSettleNum("0");
		//设置未支付
		dto.setIsPaid("N");
		//更新对账单表的本期应付金额和结账次数
		//partnerPayStatementDao.partnerStatementUpdateByStatementBillNos(dto);
		//付款失败，更新付款次数和本期应付金额
		partnerPayStatementDao.updatePartnerStatementForPayFail(dto);
		//更新对账单明细已核销金额
		partnerPayStatementDDao.partnerStatementUpdateVerifyAmountZero(dto);
		//获取登录人信息
		/*Map<String, Object> payableMap = new HashMap<String, Object>();
		payableMap.put("statementBillNos", statementBillNos);
		payableMap.put("modifyUserCode", currentInfo.getEmpCode());
		payableMap.put("modifyUserName", currentInfo.getEmpName());
		//更新应付单信息
		partnerStatementPaymentService.updatePayableForRollBack(payableMap);*/
		logger.info("付款失败回调更新 结束【{}】", new Date());
	}
	
	/**
	 * 查询费用承担部门
	 * @author wwb
	 * @date 2016-03-11
	 */
	@Override
	public PartnerPayStatementDto queryExpenseCenter(PartnerPayStatementDto dto) {
		//查询对账单明细
		
		List<PartnerPayStatementDEntity> partnerPayStatementDEntities = partnerPayStatementDDao.queryByStatementBillNo(dto.getStatementBillNo());
		if(CollectionUtils.isEmpty(partnerPayStatementDEntities)){
			throw new SettlementException("查询费用承担部门出错，明细为空");
		}
		//获取应付单号
		String payableNo = partnerPayStatementDEntities.get(0).getPayableNo();
		//查询应付单
		BillPayableEntity billPayableEntity = partnerStatementPaymentService.queryPayableByPayableNo(payableNo);
		//获得费用承担部门
		String expenseBearCode = billPayableEntity.getExpenseBearCode();
		if(org.apache.commons.lang3.StringUtils.isEmpty(expenseBearCode)){
			throw new SettlementException("查询费用承担部门出错， 应付单费用承担部门为空");
		}
		dto.setExpenseBearCode(expenseBearCode);
		return dto;
	}
	/**
	 * 计算明细金额
	 */
	private BigDecimal totalDetailAmount(List<PartnerPayStatementDEntity> partnerPayStatementDEntities){
		BigDecimal periodPayAmount = BigDecimal.ZERO;
		for (PartnerPayStatementDEntity partnerPayStatementDEntity : partnerPayStatementDEntities) {
			periodPayAmount = periodPayAmount.add(partnerPayStatementDEntity.getUnverifyAmount());				
		}
		return periodPayAmount;
	}
	
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setPartnerPayStatementDao(
			IPartnerPayStatementDao partnerPayStatementDao) {
		this.partnerPayStatementDao = partnerPayStatementDao;
	}

	public void setPartnerPayStatementDDao(
			IPartnerPayStatementDDao partnerPayStatementDDao) {
		this.partnerPayStatementDDao = partnerPayStatementDDao;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public void setPayToFSSCSendService(IPayToFSSCSendService payToFSSCSendService) {
		this.payToFSSCSendService = payToFSSCSendService;
	}

	public void setBillPaymentService(IBillPaymentService billPaymentService) {
		this.billPaymentService = billPaymentService;
	}

	public void setPartnerStatementPaymentService(
			IPartnerStatementPaymentService partnerStatementPaymentService) {
		this.partnerStatementPaymentService = partnerStatementPaymentService;
	}

}
