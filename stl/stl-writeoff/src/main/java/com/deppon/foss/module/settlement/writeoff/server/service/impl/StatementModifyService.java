package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountDetailDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountQueryResultDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IStatementByFailingInvoiceDao;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IStatementModifyDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementMakeService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementModifyService;
import com.deppon.foss.module.settlement.writeoff.api.shared.define.SettlementWriteoffConstants;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryResultDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementToPaymentResultDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.ows.inteface.domain.MailSendRequest;

/**
 * 修改对账单接口实现类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-3 上午9:37:47
 */
public class StatementModifyService implements IStatementModifyService {

	//日志
	private static final Logger logger = LoggerFactory.getLogger(StatementModifyService.class);

	/**
	 * 注入制作对账单服务
	 */
	private IStatementMakeService statementMakeService;

	/**
	 * 注入对账单明细公共SERVICE
	 */
	private IStatementOfAccountDService statementOfAccountDService;

	/**
	 * 注入对账单公共SERVICE
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 注入更改单SERVICE
	 */
	private IWaybillRfcService waybillRfcService;

	/**
	 * 注入客户service
	 */
	private ICustomerService customerService;

	/**
	 * 注入应收单service
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 注入偏线代理service
	 */
	private IVehicleAgencyCompanyService vehicleAgencyCompanyService;

	/**
	 * 注入航空代理service
	 */
	private IAirAgencyCompanyService airAgencyCompanyService;
	
	/**
	 * 互斥锁接口
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 注入组织服务
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	private IStatementByFailingInvoiceDao statementByFailingInvoiceDao;
	
	/**
	 * 版本号
	 */
	private final String VERSION = "1.0";

	/**
	 * 描叙
	 */
	private final String BUSINESS_DESC = "向官网发送客户邮件信息";
	
	private IMessageService messageService;
	
	private IStatementModifyDao statementModifyDao;

	/**
	 * 查询对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-9 下午5:14:33
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public StatementOfAccountMakeQueryResultDto queryStatement(StatementOfAccountMakeQueryDto queryDto, int start, int limit) {
		//记录日志
		logger.info("客户编码：" + queryDto.getCustomerCode());
		// 存放返回的对账单明细及对账单
		StatementOfAccountMakeQueryResultDto resultDto = new StatementOfAccountMakeQueryResultDto();
		// 存放查询参数
		StatementOfAccountUpdateDto updateDto = new StatementOfAccountUpdateDto();
		StatementOfAccountQueryResultDto queryResultDto = new StatementOfAccountQueryResultDto();
		// 设置参数
		// 对账单号或运单号
		updateDto.setBillDetailNos(queryDto.getBillDetailNos());
		// 客户编码
		updateDto.setCustomerCode(queryDto.getCustomerCode());
		// 确认状态
		updateDto.setConfirmStatus(queryDto.getConfirmStatus());
		// 结账状态
		if (StringUtil.isNotEmpty(queryDto.getSettleStatus())) {
			//设置结账状态
			updateDto.setSettleStatus(queryDto.getSettleStatus());
		}
		//设置已结清
		updateDto.setStatementSettleStatus(SettlementWriteoffConstants.STATEMENT_SETTLEMENTSTATE_SETTLETYPE);
		//设置未结清
		updateDto.setStatementUnSettleStatus(SettlementWriteoffConstants.STATEMENT_SETTLEMENTSTATE_UNSETTLEEDTYPE);
		// 组织编码
		updateDto.setOrgCode(queryDto.getOrgCode());
		// 存放结果集
		List<StatementOfAccountEntity> soaEntityList = new ArrayList<StatementOfAccountEntity>();
		// 如果用户按日期查询
		if (SettlementConstants.TAB_QUERY_BY_DATE.equals(queryDto.getQueryPage())) {
			// 开始日期
			updateDto.setPeriodBeginDate(queryDto.getPeriodBeginDate());
			// 结束日期
			updateDto.setPeriodEndDate(queryDto.getPeriodEndDate());
			//发票申请状态
			updateDto.setInvoiceStatus(queryDto.getInvoiceStatus());
			//是否申请发票
			updateDto.setApplyInvoice(queryDto.getApplyInvoice());
			
			validateDto(updateDto,queryDto);
			
			// 根据日期查询对账单信息
			soaEntityList = statementOfAccountService.queryStatementByCreateDate(updateDto,start,limit);
			queryResultDto = statementOfAccountService.countStatementByCreateDate(updateDto);
		}
		// 如果按对账单号查询
		else if (SettlementConstants.TAB_QUERY_BY_DZ_BILL_NO.equals(queryDto.getQueryPage())) {
			//根据对账单单号集合查询对账单(只查询本部门的对账单信息，供按对账单单号、明细单号查询查询对账单使用)
			soaEntityList = statementOfAccountService.queryCurrentStatementNos(Arrays.asList(queryDto.getBillDetailNos()),queryDto.getOrgCode());
		}
		// 如果按明细来源单号查询
		else if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(queryDto.getQueryPage())) {
			// 获取对账单明细信息(按明细单号查询)
			List<StatementOfAccountDEntity> soadEntityBySourcesList = statementOfAccountDService.queryByResourceNos(Arrays.asList(queryDto.getBillDetailNos()));
			// 获取对账单明细信息(按运单号查询)
			List<StatementOfAccountDEntity> soadEntityByWaybillList = statementOfAccountDService.queryByWaybillNos(Arrays.asList(queryDto.getBillDetailNos()));
			// 获取两个集合的并集
			List<StatementOfAccountDEntity> soadEntityList = (List<StatementOfAccountDEntity>) CollectionUtils.union(soadEntityBySourcesList, soadEntityByWaybillList);

			//对账单明细集合不为空
			if (CollectionUtils.isNotEmpty(soadEntityList)) {
				// 保存对账单号集合
				List<String> statementNos = new ArrayList<String>();
				//循环处理
				for (StatementOfAccountDEntity soadEntity : soadEntityList) {
					//获取对账单号加入到对账单号列表
					statementNos.add(soadEntity.getStatementBillNo());
				}
				// 根据对账单号查询对账单信息
				soaEntityList = statementOfAccountService.queryCurrentStatementNos(statementNos,queryDto.getOrgCode());
			}
		}else if (SettlementConstants.TAB_QUERY_BY_FAILING_INVOICE.equals(queryDto.getQueryPage())) {
			Map map = new HashMap();
			map.put("empCode", queryDto.getEmpCode());
			map.put("orgCode", queryDto.getStatementOrgCode());
			soaEntityList = statementByFailingInvoiceDao.queryStatementByFailingInvoice(map,start,limit);
			queryResultDto = statementByFailingInvoiceDao.countStatementByFailingInvoice(map);

		} else {
			//提示界面传入参数异常
			throw new SettlementException("界面传入参数异常！", "");
		}
		if (SettlementConstants.TAB_QUERY_BY_DATE.equals(queryDto.getQueryPage())) {
			// 设置对账单集合
			resultDto.setStatementOfAccountList(soaEntityList);
			// 设置本次剩余未还金额
			resultDto.setTotalUnpaidAmount(queryResultDto.getUnPaidAmount());
			
			resultDto.setTotalCount(queryResultDto.getTotalCount());
		}else{
			BigDecimal totalUnpaidAmount = countTotalUnpaidAmount(soaEntityList);
			
			// 设置对账单集合
			resultDto.setStatementOfAccountList(soaEntityList);
			// 设置本次剩余未还金额
			resultDto.setTotalUnpaidAmount(totalUnpaidAmount);
			
			resultDto.setTotalCount(soaEntityList.size());
		}
		//返回dto
		return resultDto;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public StatementOfAccountMakeQueryResultDto queryStatementXLS(StatementOfAccountMakeQueryDto queryDto) {
		// 存放返回的对账单明细及对账单
		StatementOfAccountMakeQueryResultDto resultDto = new StatementOfAccountMakeQueryResultDto();
		// 存放查询参数
		StatementOfAccountUpdateDto updateDto = new StatementOfAccountUpdateDto();
		StatementOfAccountQueryResultDto queryResultDto = new StatementOfAccountQueryResultDto();
		// 设置参数
		// 对账单号或运单号
		updateDto.setBillDetailNos(queryDto.getBillDetailNos());
		// 客户编码
		updateDto.setCustomerCode(queryDto.getCustomerCode());
		// 确认状态
		updateDto.setConfirmStatus(queryDto.getConfirmStatus());
		// 结账状态
		if (StringUtil.isNotEmpty(queryDto.getSettleStatus())) {
			//设置结账状态
			updateDto.setSettleStatus(queryDto.getSettleStatus());
		}
		//设置已结清
		updateDto.setStatementSettleStatus(SettlementWriteoffConstants.STATEMENT_SETTLEMENTSTATE_SETTLETYPE);
		//设置未结清
		updateDto.setStatementUnSettleStatus(SettlementWriteoffConstants.STATEMENT_SETTLEMENTSTATE_UNSETTLEEDTYPE);
		// 组织编码
		updateDto.setOrgCode(queryDto.getOrgCode());
		// 存放结果集
		List<StatementOfAccountEntity> soaEntityList = new ArrayList<StatementOfAccountEntity>();
		// 如果用户按日期查询
		if (SettlementConstants.TAB_QUERY_BY_DATE.equals(queryDto.getQueryPage())) {
			// 开始日期
			updateDto.setPeriodBeginDate(queryDto.getPeriodBeginDate());
			// 结束日期
			updateDto.setPeriodEndDate(queryDto.getPeriodEndDate());
			validateDto(updateDto,queryDto);
			queryResultDto = statementOfAccountService.countStatementByCreateDate(updateDto);
			int limit = Long.valueOf(queryResultDto.getTotalCount()).intValue();
			// 根据日期查询对账单信息
			soaEntityList = statementOfAccountService.queryStatementByCreateDate(updateDto,0,limit);

		}
		// 如果按对账单号查询
		else if (SettlementConstants.TAB_QUERY_BY_DZ_BILL_NO.equals(queryDto.getQueryPage())) {
			//根据对账单单号集合查询对账单(只查询本部门的对账单信息，供按对账单单号、明细单号查询查询对账单使用)
			soaEntityList = statementOfAccountService.queryCurrentStatementNos(Arrays.asList(queryDto.getBillDetailNos()),queryDto.getOrgCode());
		}
		// 如果按明细来源单号查询
		else if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(queryDto.getQueryPage())) {
			// 获取对账单明细信息(按明细单号查询)
			List<StatementOfAccountDEntity> soadEntityBySourcesList = statementOfAccountDService.queryByResourceNos(Arrays.asList(queryDto.getBillDetailNos()));
			// 获取对账单明细信息(按运单号查询)
			List<StatementOfAccountDEntity> soadEntityByWaybillList = statementOfAccountDService.queryByWaybillNos(Arrays.asList(queryDto.getBillDetailNos()));
			// 获取两个集合的并集
			List<StatementOfAccountDEntity> soadEntityList = (List<StatementOfAccountDEntity>) CollectionUtils.union(soadEntityBySourcesList, soadEntityByWaybillList);

			//对账单明细集合不为空
			if (CollectionUtils.isNotEmpty(soadEntityList)) {
				// 保存对账单号集合
				List<String> statementNos = new ArrayList<String>();
				//循环处理
				for (StatementOfAccountDEntity soadEntity : soadEntityList) {
					//获取对账单号加入到对账单号列表
					statementNos.add(soadEntity.getStatementBillNo());
				}
				// 根据对账单号查询对账单信息
				soaEntityList = statementOfAccountService.queryCurrentStatementNos(statementNos,queryDto.getOrgCode());
			}
		}else if (SettlementConstants.TAB_QUERY_BY_FAILING_INVOICE.equals(queryDto.getQueryPage())) {
			Map map = new HashMap();
			map.put("empCode", queryDto.getEmpCode());
			map.put("orgCode", queryDto.getStatementOrgCode());
			queryResultDto = statementByFailingInvoiceDao.countStatementByFailingInvoice(map);
			int limit = new Long(queryResultDto.getTotalCount()).intValue();
			// 根据日期查询对账单信息
			soaEntityList = statementByFailingInvoiceDao.queryStatementByFailingInvoice(map,0,limit);

		} else {
			//提示界面传入参数异常
			throw new SettlementException("界面传入参数异常！", "");
		}
		// 设置对账单集合
		resultDto.setStatementOfAccountList(soaEntityList);
		//返回dto
		return resultDto;
	}
	
	private void validateDto(StatementOfAccountUpdateDto updateDto, StatementOfAccountMakeQueryDto queryDto) {
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
	 * 修改对账单界面，新增对账单明细接口
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-3 上午9:33:00
	 */
	@Transactional
	@Override
	public StatementOfAccountMakeQueryResultDto addStatementDetail(StatementOfAccountMakeQueryResultDto resultDto, CurrentInfo info) {
		//记录日志
		logger.info("新增对账单明细 enter service,对账单号为： "+ resultDto.getStatementOfAccount().getStatementBillNo());
		// 校验对账单是否已确认，如果已经确认则不允许新增明细信息
		if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM.equals(resultDto.getStatementOfAccount().getConfirmStatus())) {
			//提示对账单已确认，不能新增明细信息
			throw new SettlementException("对账单已确认，不能新增明细信息！", "");
		}
		
		// 获取系统当期日期
		Date now = new Date();

		/*
		 * 首先校验本期明细单据和所操作明细单据是否发生了变化(是否符合对账单的生成规则)
		 */
		// 调用验证对账单明细单据是否发生变化方法,如果发生变化，则抛出异常信息
		// 校验新增明细单据信息
		List<StatementOfAccountDEntity> soadOperationList = statementMakeService
				.validateForAddDetail(
						resultDto.getStatementOfAccountDetailPeriodList(), "",
						info);
		// 调用service,查询对账单明细
		List<StatementOfAccountDEntity> statementOfAccountDPeriodList = this.statementOfAccountDService
				.queryByStatementBillNo(resultDto.getStatementOfAccount()
						.getStatementBillNo());
		// 如果明细为null
		if (CollectionUtils.isEmpty(statementOfAccountDPeriodList)) {
			//新建明细信息并传递到前台
			statementOfAccountDPeriodList = new ArrayList<StatementOfAccountDEntity>();
		}
		// 查询出的对账单明细设置到Dto中
		resultDto.setStatementOfAccountDPeriodList(statementOfAccountDPeriodList);
		// 新增的对账单明细设置到Dto中
		resultDto.setStatementOfAccountOperateList(soadOperationList);
		// 更新对账单、对账单期初明细信息、对账单本期明细信息
		resultDto = updateForaddStatementDetail(resultDto, info);
		// 校验对账单金额信息和对账单明细统计信息是否一致
		StatementOfAccountEntity soaEntity = resultDto.getStatementOfAccount();
		//如果对账单统计金额和明细信息不一致
		statementMakeService.validateTotalAmountForAddDetail(resultDto.getStatementOfAccountDBeginPeriodList(), soaEntity,resultDto.getStatementOfAccountDPeriodList(), info);
		// 存放新增明细信息单号
		List<String> list = new ArrayList<String>();
		// 保存对账单明细信息
		for (Iterator<StatementOfAccountDEntity> it = resultDto.getStatementOfAccountOperateList().iterator(); it.hasNext();) {
			//获取当前对账单
			StatementOfAccountDEntity soadEntity = (StatementOfAccountDEntity) it.next();
			// 保存对账单明细信息
			soadEntity.setIsDelete(FossConstants.NO);//设置是否删除
			soadEntity.setId(UUIDUtils.getUUID());//设置ID
			soadEntity.setStatementBillNo(resultDto.getStatementOfAccount().getStatementBillNo());//设置对账单号
			soadEntity.setCreateTime(now);//设置创建时间
			// 将来源单号保存到list集合中
			list.add(soadEntity.getSourceBillNo());
			// 新增对账单明细信息
			statementOfAccountDService.add(soadEntity);
		}
		// 批量重置新增明细的对账单号信息
		statementMakeService.updateDetailBillNoForRelease(list,soaEntity.getStatementBillNo(), info);
		// 同时更新对账单信息
		soaEntity.setModifyTime(new Date());//设置修改日期
		soaEntity.setModifyUserCode(info.getEmpCode());//设置修改人编码
		soaEntity.setModifyUserName(info.getEmpName());//设置修改人名称
		//新增对账单明细时修改对账单信息
		statementOfAccountService.updateStatementForAddDetail(soaEntity, info);
		// 增加版本号
		Short version = soaEntity.getVersionNo();
		soaEntity.setVersionNo((short) (version + 1));//版本号加1
		resultDto.setStatementOfAccount(soaEntity);//设置返回dto的对账单参数
		//返回dto
		return resultDto;
	}

	/**
	 * 修改对账单功能：删除对账单明细方法
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 上午11:03:59
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementModifyService#deleteStatementDetail(com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryResultDto)
	 */
	@Transactional
	@Override
	public StatementOfAccountMakeQueryResultDto deleteStatementDetail(StatementOfAccountMakeQueryResultDto resultDto, CurrentInfo info) {
		// 校验对账单是否已确认，如果已经确认则不允许删除明细信息
		if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM.equals(resultDto.getStatementOfAccount().getConfirmStatus())) {
			//提示对账单已确认，不能删除明细信息
			throw new SettlementException("对账单已确认，不能删除明细信息！", "");
		}
		// 调用service,查询对账单明细
		List<StatementOfAccountDEntity> statementOfAccountDPeriodList = this.statementOfAccountDService
				.queryByStatementBillNo(resultDto.getStatementOfAccount()
						.getStatementBillNo());
		// 如果明细为null
		if (CollectionUtils.isEmpty(statementOfAccountDPeriodList)) {
			//新建明细信息并传递到前台
			statementOfAccountDPeriodList = new ArrayList<StatementOfAccountDEntity>();
		}
		resultDto.setStatementOfAccountDPeriodList(statementOfAccountDPeriodList);
		// 更新对账单、对账单期初明细信息、对账单本期明细信息
		resultDto = updateForDeleteStatementDetail(resultDto, info);
		// 校验对账单金额信息和对账单明细统计信息是否一致
		StatementOfAccountEntity soaEntity = resultDto.getStatementOfAccount();
		//如果对账单统计金额与明细信息不一致
		statementMakeService.validateTotalAmountForAddDetail(
				resultDto.getStatementOfAccountDBeginPeriodList(), soaEntity,
				resultDto.getStatementOfAccountDPeriodList(), info);
		// 删除本期对账单明细信息（逻辑删除，修改对账单明细上的是否删除字段信息）
		Date nowTime = new Date();
		//循环处理
		for (Iterator<StatementOfAccountDEntity> it = resultDto.getStatementOfAccountOperateList().iterator(); it.hasNext();) {
			//获取当前对账单
			StatementOfAccountDEntity soadEntity = it.next();
			// 删除时间
			soadEntity.setDisableTime(nowTime);
			// 删除标识
			soadEntity.setIsDelete(FossConstants.YES);
			//修改对账单明细
			statementOfAccountDService.updateStatementDetailForDeleteDetail(soadEntity);
			// 将对账单明细从对账单中释放
			//生成对账单号列表
			List<String> list = new ArrayList<String>();
			//将对账单号加入对账单号列表
			list.add(soadEntity.getSourceBillNo());
			//重置与对账单明细相关联的应收单、应付单、预收单、预付单单据信息
			statementMakeService.updateDetailBillNoForRelease(list,SettlementConstants.DEFAULT_BILL_NO, info);
		}
		// 更新对账单信息（根据版本号更新，如果发生并发操作或由于其他原因对账单信息已被修改时，更新失败）
		soaEntity.setModifyTime(nowTime);//设置修改时间
		soaEntity.setModifyUserCode(info.getEmpCode());//设置修改人编码
		soaEntity.setModifyUserName(info.getEmpName());//设置修改名称
		//删除对账单明细时修改对账单信息
		statementOfAccountService.updateStatementForDeleteDetail(soaEntity,info);
		// 增加版本号
		Short version = soaEntity.getVersionNo();//获取版本号
		soaEntity.setVersionNo((short) (version + 1));//版本号+1
		resultDto.setStatementOfAccount(soaEntity);//设置返回dto的对账单
		//返回dto
		return resultDto;
	}

	/**
	 * 修改对账单界面：确认对账单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 下午4:46:59
	 */
	@Transactional
	@Override
	public void confirmStatement(StatementOfAccountMakeQueryResultDto resultDto, CurrentInfo info) {
		//核销、还款、付款时：确认对账单
		confirmStatementForWriteOff(resultDto, info);
	}

	/**
	 * 核销、还款、付款时：确认对账单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 下午4:46:59
	 */
	@Override
	public void confirmStatementForWriteOff(StatementOfAccountMakeQueryResultDto resultDto, CurrentInfo info) {
		//记录日志
		logger.info("确认对账单，enter service...");
		// 更新对账单确认状态
		if (CollectionUtils.isNotEmpty(resultDto.getStatementOfAccountList())) {
			//添加互斥锁收集数据
			List<MutexElement> mutexElements = new ArrayList<MutexElement>();
			// 校验对账单明细是否存在未受理的更改单
			//循环处理对账单
			for (StatementOfAccountEntity entity : resultDto.getStatementOfAccountList()) {
				//根据对账单号查询对账单明细信息
				List<StatementOfAccountDEntity> soadList = statementOfAccountDService.queryByStatementBillNo(entity.getStatementBillNo());
				// 存放所有待校验的运单号集合
				List<String> waybillNoList = new ArrayList<String>();
				// 存放所有的应收单号集合
				List<String> receiveableNoList = new ArrayList<String>();
				//如果对账单明细不为空
				if (CollectionUtils.isNotEmpty(soadList)) {
					// 如果对账单明细集合不为空，则循环取出对账单明细的运单号
					//循环处理对账单明细
					for (StatementOfAccountDEntity soadEntity : soadList) {
						//如果对账单明细的运单号不为空
						if (StringUtil.isNotEmpty(soadEntity.getWaybillNo())) {
							//将运单号加入到运单号列表
							waybillNoList.add(soadEntity.getWaybillNo());
							// 业务互锁运单号
							MutexElement mutexElement = new MutexElement(soadEntity.getWaybillNo(), "对账单确认操作", MutexElementType.WAYBILL_NO);
							//加入互斥对象集合
							mutexElements.add(mutexElement);
						}
						//如果对账单明细的来源单号不为空
						if (SettlementUtil.isReceiveable(soadEntity.getSourceBillNo())) {
							//将来源单号加入到应收单明细
							receiveableNoList.add(soadEntity.getSourceBillNo());
						}
					}
				} else {
					throw new SettlementException("对账单" + entity.getStatementBillNo() + "的对账单明细记录为空，不能进行确认对账单操作！");
				}
				// 校验该对账单明细中的单据所关联的运单是否存在有未受理的更改单
				//如果运单列表不为空
				if (CollectionUtils.isNotEmpty(waybillNoList)) {
					// 应收单号个数小于等于1000个时，直接查询返回
					if (waybillNoList.size() <= SettlementConstants.MAX_BILL_NO_SIZE) {
						// 传入运单号或者运单号数组集合
						// 判断传入的运单号是否存在未处理的更改单
						List<String> changeWaybillNoList = waybillRfcService.isExsitsWayBillRfcs(waybillNoList);

						// 如果有则抛出异常,并抛出对账单号和运单号
						//如果存在未受理的更改单
						if (CollectionUtils.isNotEmpty(changeWaybillNoList)) {
							//生成储存运单buffer
							StringBuffer waybillNos = new StringBuffer();
							//循环处理运单列表
							for (int i = 0; i < changeWaybillNoList.size(); i++) {
								//如果第一次循环
								if (i != 0) {
									//拼接,
									waybillNos.append(",");
								}
								//拼接运单号
								waybillNos.append(changeWaybillNoList.get(i));
							}
							//对账单XXX存在有未受理的更改单，不能进行确认对账单操作！
							//存在更改的运单号为XXX
							throw new SettlementException("对账单"+ entity.getStatementBillNo()+ "存在有未受理的更改单，不能进行确认对账单操作！存在更改的运单号为"+ waybillNos, "");
						}
					} 
					// 应收单号个数大于1000个时，分批查询
					else {
						List<String> queryWaybillNos = new ArrayList<String>();
						for (int index = 0; index < waybillNoList.size(); index++) {
							queryWaybillNos.add(waybillNoList.get(index));
							if (queryWaybillNos.size() == SettlementConstants.MAX_BILL_NO_SIZE) {
								// 传入运单号或者运单号数组集合
								// 判断传入的运单号是否存在未处理的更改单
								List<String> changeWaybillNoList = waybillRfcService.isExsitsWayBillRfcs(queryWaybillNos);

								// 如果有则抛出异常,并抛出对账单号和运单号
								//如果存在未受理的更改单
								if (CollectionUtils.isNotEmpty(changeWaybillNoList)) {
									//生成储存运单buffer
									StringBuffer waybillNos = new StringBuffer();
									//循环处理运单列表
									for (int i = 0; i < changeWaybillNoList.size(); i++) {
										//如果第一次循环
										if (i != 0) {
											//拼接,
											waybillNos.append(",");
										}
										//拼接运单号
										waybillNos.append(changeWaybillNoList.get(i));
									}
									//对账单XXX存在有未受理的更改单，不能进行确认对账单操作！
									//存在更改的运单号为XXX
									throw new SettlementException("对账单"+ entity.getStatementBillNo()+ "存在有未受理的更改单，不能进行确认对账单操作！存在更改的运单号为"+ waybillNos, "");
								}
								queryWaybillNos.clear();
							}
						}
						if (CollectionUtils.isNotEmpty(queryWaybillNos)) {
							// 传入运单号或者运单号数组集合
							// 判断传入的运单号是否存在未处理的更改单
							List<String> changeWaybillNoList = waybillRfcService.isExsitsWayBillRfcs(queryWaybillNos);

							// 如果有则抛出异常,并抛出对账单号和运单号
							//如果存在未受理的更改单
							if (CollectionUtils.isNotEmpty(changeWaybillNoList)) {
								//生成储存运单buffer
								StringBuffer waybillNos = new StringBuffer();
								//循环处理运单列表
								for (int i = 0; i < changeWaybillNoList.size(); i++) {
									//如果第一次循环
									if (i != 0) {
										//拼接,
										waybillNos.append(",");
									}
									//拼接运单号
									waybillNos.append(changeWaybillNoList.get(i));
								}
								//对账单XXX存在有未受理的更改单，不能进行确认对账单操作！
								//存在更改的运单号为XXX
								throw new SettlementException("对账单"+ entity.getStatementBillNo()+ "存在有未受理的更改单，不能进行确认对账单操作！存在更改的运单号为"+ waybillNos, "");
							}
						}
					}
				}
				// 校验应收单是否被网厅锁定
				//如果应收单号不为空
				if (CollectionUtils.isNotEmpty(receiveableNoList)) {
					//根据传入的一到多个应收单号，获取一到多条应收单信息
					List<BillReceivableEntity> receiveableEntityList = billReceivableService.queryByReceivableNOs(receiveableNoList,FossConstants.ACTIVE);
					//生成应收单锁定列表
					List<String> lockBillList = new ArrayList<String>();
					//循环处理应收单
					for (BillReceivableEntity receiveEntity : receiveableEntityList) {
						//如果应收单被锁定
						if (receiveEntity.getUnlockDateTime() != null&& receiveEntity.getUnlockDateTime().after(new Date())) {
							//加入到锁定应收单列表
							lockBillList.add(receiveEntity.getReceivableNo());
						}
					}
					// 如果有则抛出异常,并抛出对账单号和运单号
					//如果锁定应收单不为空
					if (CollectionUtils.isNotEmpty(lockBillList)) {
						//生成应收单号buffer
						StringBuffer receiveNos = new StringBuffer();
						//循环处理应收单号列表
						for (int i = 0; i < lockBillList.size(); i++) {
							//循环第一次
							if (i != 0) {
								//拼接,
								receiveNos.append(",");
							}
							//拼接应收单号
							receiveNos.append(lockBillList.get(i));
						}
						//提示对账单XXXX存在被网厅锁定的应收单，不能进行确认对账单操作！应收单号为XXXXX
						throw new SettlementException("对账单"+ entity.getStatementBillNo()+ "存在被网厅锁定的应收单，不能进行确认对账单操作！应收单号为" + receiveNos);
					}
				}
				if(SettlementDictionaryConstants.SETTLEMENT_INVOICE_APPLY_INVOICE_YES.equals(entity.getApplyInvoice())){
					if(entity.getUnpaidAmount().compareTo(BigDecimal.valueOf(0)) != 0){
						//ddw
						createBatchInstationMsg(entity,info);
					}
				}
			}
			//添加互斥锁
			//如果互斥对象集合不为空
			if(CollectionUtils.isNotEmpty(mutexElements)){
				//锁定
				businessLockService.lock(mutexElements, SettlementConstants.BUSINESS_LOCK_BATCH);
			}
			//确认对账单信息
			statementOfAccountService.confirmStatement(resultDto.getStatementOfAccountList(), info);
			// 确认完毕给官网发送对账单号、客户编码和联系人邮箱地址信息，只有当前台用户选择确定发送才会发邮件给客户
			//如果发送邮件标志为是
			if (FossConstants.YES.equals(resultDto.getSendMailFlag())) {
				//循环处理对账单
				for (StatementOfAccountEntity soaEntity : resultDto.getStatementOfAccountList()) {
					// 只有客户对账单确认后才会给官网发送信息
					if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__CUSTOMER_ACCOUNT.equals(soaEntity.getBillType())) {
						// 发送信息
						sendMailToOWS(soaEntity.getStatementBillNo(),soaEntity.getCustomerCode());
					}
				}
			}
			//去除互斥锁
			//如果互斥对象集合不为空
			if(CollectionUtils.isNotEmpty(mutexElements)){
				//解锁
				businessLockService.unlock(mutexElements);
			}
		//没有确认的对账单	
		} else {
			//提示没有需要确认的对账单信息
			throw new SettlementException("没有需要确认的对账单信息！", "");
		}
		//记录日志
		logger.info("确认对账单，successfully exit service...");
	}

	private void createBatchInstationMsg(StatementOfAccountEntity entity, CurrentInfo info) {
		InstationJobMsgEntity instationEntity = new InstationJobMsgEntity();
		//发送人和发送部门信息
		instationEntity.setSenderCode(info.getEmpCode());
		instationEntity.setSenderName(info.getEmpName());
		instationEntity.setSenderOrgCode(info.getCurrentDeptCode());
		instationEntity.setSenderOrgName(info.getCurrentDeptName());
		//设置为代收货款消息
		instationEntity.setMsgType(MessageConstants.MSG_TYPE__FAILINGINVOICE);					
		//接受方式为组织
		instationEntity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
		//设置接收部门信息
		instationEntity.setReceiveOrgCode(entity.getCreateOrgCode());
		instationEntity.setReceiveOrgName(entity.getCreateOrgName());
		//设置
		instationEntity.setContext(entity.getStatementBillNo());
		messageService.createBatchInstationMsg(instationEntity);
		
	}

	/**
	 * 确认对账单后给官网发送客户电子邮件信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-1-26 上午10:06:53
	 */
	private void sendMailToOWS(String statementNo, String customerCode) {
		//记录日志
		logger.info("确认对账单后给官网发送客户电子邮件信息 开始.....：对账单号：" + statementNo + "客户编码:"+ customerCode);
		// 校验对账单号、客户编码是否为空
		if (StringUtil.isNotEmpty(statementNo)&& StringUtil.isNotEmpty(customerCode)) {
			// 获取客户信息
			CustomerDto customerDto = queryCustInfoByCode(customerCode);
			//如果客户不为空
			if (customerDto != null) {
				// 获取客户联系人集合信息
				List<ContactEntity> contactList = customerDto.getContactList();
				// 校验客户联系人集合信息是否为空
				if (CollectionUtils.isNotEmpty(contactList)) {
					// 循环取出联系人的邮件信息放入集合中
					//生成邮件列表
					List<String> emailList = new ArrayList<String>();
					//循环处理客户联系人信息
					for (ContactEntity entity : contactList) {
						//联系人类型是否为空
						if (StringUtils.isNotEmpty(entity.getContactType())){
							//联系人类型为(1,0,0,0,0)->>('财务联系人','业务联系人','发货联系人','收货联系人','协议联系人')
							String contactTypeFlag = entity.getContactType().substring(0, 1);
							//判断是否为财务联系人，非财务联系人不需要发邮件
							if (StringUtils.isNotEmpty(contactTypeFlag)
									&& SettlementConstants.CONTACT_TYPE__FINANCE
											.equals(contactTypeFlag)) {
								//将邮件地址加入到邮件列表
								emailList.add(entity.getEmail());
							}
						}
					}
					//记录日志
					logger.info("联系人邮件集合信息长度：" + emailList.size(), "");
					//如果邮件地址列表大于0
					if (emailList.size() > 0) {
						// 创建发送邮件信息实体对象
						MailSendRequest mailSendRequest = new MailSendRequest();
						// 设置信息(客户编码、对账单号、邮件信息集合)
						mailSendRequest.setCustomerCode(customerCode);
						//设置编号
						mailSendRequest.setStatementNo(statementNo);
						//设置邮件地址信息
						mailSendRequest.getEmailList().addAll(emailList);
						// 发送邮件信息
						try {
							//生成esb调用实体头
							AccessHeader header = buildHeader(new Date());
							//esb异步调用
							ESBJMSAccessor.asynReqeust(header, mailSendRequest);
						//异常处理	
						} catch (Exception e) {
							//记录日志
							logger.error("发送邮件信息发生错误" + e.getMessage(), e);
						}
					} else {
						//记录日志
						logger.info("联系人邮件集合信息长度为0，不发送邮件信息", "");
					}
				}
			}
		}
		//记录日志
		logger.info("对账单确认后给官网发送邮箱地址信息接口结束", "");

	}

	/**
	 * 接口头消息
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-24 下午8:40:49
	 */
	private AccessHeader buildHeader(Date sendDate) {

		//esb消息头
		AccessHeader header = new AccessHeader();
		// 发送流水号
		header.setBusinessId(DateUtils.convert(sendDate));
		// 服务场景
		header.setEsbServiceCode(SettlementESBDictionaryConstants.ESB_FOSS2ESB_SEND_MAIL);
		// 版本号
		header.setVersion(VERSION);
		// 场景描叙
		header.setBusinessDesc1(BUSINESS_DESC);
		//返回esb消息头
		return header;

	}

	/**
	 * 修改对账单界面：反确认对账单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 下午4:46:59
	 */
	@Transactional
	@Override
	public void unConfirmStatement(StatementOfAccountMakeQueryResultDto resultDto, CurrentInfo info) {
		// 如果要反确认的对账单集合不为空
		if (CollectionUtils.isNotEmpty(resultDto.getStatementOfAccountList())) {
			//记录日志
			logger.info("反确认对账单，enter service...");
			//ddw
			for(StatementOfAccountEntity entity : resultDto.getStatementOfAccountList()){
				List<String> ids = statementByFailingInvoiceDao.queryInstationMsgByIds(entity);
				if(ids != null && ids.size() > 0){
					messageService.updateInstationMsgByIds(ids, info);
				}
			}
			//反确认
			statementOfAccountService.unConfirmStatement(resultDto.getStatementOfAccountList(), info);
		//不为空	
		} else {
			//提示没有需要反确认的对账单信息
			throw new SettlementException("没有需要反确认的对账单信息！", "");
		}
		//记录日志
		logger.info("反确认对账单，successfully exit service...");
	}

	/**
	 * 计算对账单列表本次剩余未还总金额
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-17 下午3:45:23
	 */
	private BigDecimal countTotalUnpaidAmount(List<StatementOfAccountEntity> soaEntityList) {
		// 声明本次剩余未还总金额
		BigDecimal totalUnpaidAmount = BigDecimal.ZERO;
		// 循环对账单列表计算
		for (StatementOfAccountEntity en : soaEntityList) {
			//设置未还款金额
			totalUnpaidAmount = totalUnpaidAmount.add(en.getUnpaidAmount());
		}
		// 返回值
		return totalUnpaidAmount;
	}

	/**
	 * 添加对账单明细时，更新对账单上的金额信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-3 上午10:40:48
	 */
	@SuppressWarnings("unchecked")
	private StatementOfAccountMakeQueryResultDto updateForaddStatementDetail(StatementOfAccountMakeQueryResultDto resultDto, CurrentInfo info) {
		// 获取对账单实体
		StatementOfAccountEntity soaEntity = resultDto.getStatementOfAccount();
		// 获取对账单期初明细信息
		List<StatementOfAccountDEntity> beginPeriodList = resultDto.getStatementOfAccountDBeginPeriodList();
		// 获取对账单本期明细信息
		List<StatementOfAccountDEntity> periodList = resultDto.getStatementOfAccountDPeriodList();
		// 获取新增明细信息
		List<StatementOfAccountDEntity> operateList = resultDto.getStatementOfAccountOperateList();
		//如果新增明细信息为空
		if (CollectionUtils.isEmpty(operateList)) {
			//提示新增对账单明细集合为空
			throw new SettlementException("新增对账单明细集合为空！", "");
		}
		// 将新增明细增加到本期对账单明细中，并同时在期初明细中删除
		if (CollectionUtils.isNotEmpty(beginPeriodList)) {
			//生成待删除明细
			List<StatementOfAccountDEntity> deleteList = new ArrayList<StatementOfAccountDEntity>();
			//循环新增明细信息
			for (StatementOfAccountDEntity operaEntity : operateList) {
				//循环期初明细
				for (StatementOfAccountDEntity beginEntity : beginPeriodList) {
					//如果两者来源单号相等
					if (operaEntity.getSourceBillNo().equals(beginEntity.getSourceBillNo())) {
						//加入到待删除明细
						deleteList.add(beginEntity);
					}
				}
			}
			//从期初明细减去待删除明细
			beginPeriodList = (List<StatementOfAccountDEntity>) CollectionUtils.subtract(beginPeriodList, deleteList);
		}
		//将待删除明细加入到本期明细中
		periodList = (List<StatementOfAccountDEntity>) CollectionUtils.union(periodList, operateList);
		// 跟据新增的对账单明细信息修改对账单期初、本期及本期剩余发生金额信息
		//如果新增明细信息为空
		if (CollectionUtils.isNotEmpty(operateList)) {
			//循环新增明细
			for (Iterator<StatementOfAccountDEntity> it = operateList.iterator(); it.hasNext();) {
				BigDecimal beginPeriodAmount = BigDecimal.ZERO; // 期初金额
				BigDecimal beginPeriodRecAmount = BigDecimal.ZERO; // 期初应收
				BigDecimal beginPeriodPayAmount = BigDecimal.ZERO; // 期初应付
				BigDecimal beginPeriodPreAmount = BigDecimal.ZERO; // 期初预收
				BigDecimal beginPeriodAdvAmount = BigDecimal.ZERO; // 期初预付
				BigDecimal periodAmount = BigDecimal.ZERO; // 本期发生金额
				BigDecimal periodRecAmount = BigDecimal.ZERO; // 本期应收金额
				BigDecimal periodPayAmount = BigDecimal.ZERO; // 本期应付金额
				BigDecimal periodPreAmount = BigDecimal.ZERO; // 本期预收金额
				BigDecimal periodAdvAmount = BigDecimal.ZERO; // 本期预付金额
				StatementOfAccountDEntity soadEntity = it.next();
				// 如果为应收单,则期初应收金额减少；期初发生金额减少；本期应收金额增加；本期发生金额增加
				if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE.equals(soadEntity.getBillParentType())) {
					//生成期初应收金额
					beginPeriodRecAmount = soaEntity.getBeginPeriodRecAmount().subtract(soadEntity.getUnverifyAmount());
					//生成期初发生金额
					beginPeriodAmount = soaEntity.getBeginPeriodAmount().subtract(soadEntity.getUnverifyAmount());
					//生成本期应收金额
					periodRecAmount = soaEntity.getPeriodRecAmount().add(soadEntity.getUnverifyAmount());
					//生成本期发生金额
					periodAmount = soaEntity.getPeriodAmount().add(soadEntity.getUnverifyAmount());
					// 修改对账单的金额信息
					//修改期初应收金额
					soaEntity.setBeginPeriodRecAmount(beginPeriodRecAmount);
					//修改本期发生金额
					soaEntity.setPeriodAmount(periodAmount);
					//修改本期应收金额
					soaEntity.setPeriodRecAmount(periodRecAmount);
					//修改笨发生金额
					soaEntity.setPeriodAmount(periodAmount);
				}
				// 如果为应付单,则期初应付金额减少；期初发生金额增加；本期应付金额增加；本期发生金额减少
				else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE.equals(soadEntity.getBillParentType())) {
					//生成期初应付金额
					beginPeriodPayAmount = soaEntity.getBeginPeriodPayAmount().subtract(soadEntity.getUnverifyAmount());
					//生成期初发生金额
					beginPeriodAmount = soaEntity.getBeginPeriodAmount().add(soadEntity.getUnverifyAmount());
					//生成本期应付金额
					periodPayAmount = soaEntity.getPeriodPayAmount().add(soadEntity.getUnverifyAmount());
					//生成本期发生金额
					periodAmount = soaEntity.getPeriodAmount().subtract(soadEntity.getUnverifyAmount());
					// 修改对账单的金额信息
					//修改期初应付金额
					soaEntity.setBeginPeriodPayAmount(beginPeriodPayAmount);
					//修改期初发生金额
					soaEntity.setBeginPeriodAmount(beginPeriodAmount);
					//修改本期应付金额
					soaEntity.setPeriodPayAmount(periodPayAmount);
					//修改本期发生金额
					soaEntity.setPeriodAmount(periodAmount);
				}
				// 如果为预收单,则期初预收金额减少；期初发生金额增加；本期预收金额增加；本期发生金额减少
				else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED.equals(soadEntity.getBillParentType())) {
					//生成期初预收金额
					beginPeriodPreAmount = soaEntity.getBeginPeriodPreAmount().subtract(soadEntity.getUnverifyAmount());
					//生成期初发生金额
					beginPeriodAmount = soaEntity.getBeginPeriodAmount().add(soadEntity.getUnverifyAmount());
					//生成本期预收金额
					periodPreAmount = soaEntity.getPeriodPreAmount().add(soadEntity.getUnverifyAmount());
					//生成本期发生金额
					periodAmount = soaEntity.getPeriodAmount().subtract(soadEntity.getUnverifyAmount());
					// 修改对账单的金额信息
					//修改期初预收金额
					soaEntity.setBeginPeriodPreAmount(beginPeriodPreAmount);
					//修改期初发生金额
					soaEntity.setBeginPeriodAmount(beginPeriodAmount);
					//修改本期预收金额
					soaEntity.setPeriodPreAmount(periodPreAmount);
					//修改本期发生金额
					soaEntity.setPeriodAmount(periodAmount);
				}
				// 如果为预付单,则期初预付金额减少；期初发生金额减少；本期预付金额增加；本期发生金额增加
				else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT.equals(soadEntity.getBillParentType())) {
					//生成期初预付金额
					beginPeriodAdvAmount = soaEntity.getBeginPeriodAdvAmount().subtract(soadEntity.getUnverifyAmount());
					//生成期初发生金额
					beginPeriodAmount = soaEntity.getBeginPeriodAmount().subtract(soadEntity.getUnverifyAmount());
					//生成本期预付金额
					periodAdvAmount = soaEntity.getPeriodAdvAmount().add(soadEntity.getUnverifyAmount());
					//生成本期发生金额
					periodAmount = soaEntity.getPeriodAmount().add(soadEntity.getUnverifyAmount());
					// 修改对账单的金额信息
					//修改期初预付金额
					soaEntity.setBeginPeriodAdvAmount(beginPeriodAdvAmount); 
					//修改期初发生金额
					soaEntity.setBeginPeriodAmount(beginPeriodAmount);
					//修改本期预付金额
					soaEntity.setPeriodAdvAmount(periodAdvAmount);
					//修改本期发生金额
					soaEntity.setPeriodAmount(periodAmount);
				}
			}
		}

		// 更新对账单的未核销发生金额
		soaEntity = statementOfAccountService.updatePeriodUnverifyAmountCommon(soaEntity);
		// 更新对账单的本期剩余未还金额
		soaEntity = statementMakeService.updateUnpaidAmount(soaEntity);
		// 将更新后的对账单、对账单期初明细信息、本期明细信息
		resultDto.setStatementOfAccount(soaEntity);
		//设置返回dto期初数据列表
		resultDto.setStatementOfAccountDBeginPeriodList(beginPeriodList);
		//设置返回dto本期数据列表
		resultDto.setStatementOfAccountDPeriodList(periodList);
		//返回dto
		return resultDto;
	}

	/**
	 * 删除对账单明细时，更新对账单上的金额信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-3 上午10:40:48
	 */
	@SuppressWarnings("unchecked")
	private StatementOfAccountMakeQueryResultDto updateForDeleteStatementDetail(StatementOfAccountMakeQueryResultDto resultDto, CurrentInfo info) {
		// 获取对账单实体
		StatementOfAccountEntity soaEntity = resultDto.getStatementOfAccount();
		// 获取对账单期初明细信息
		List<StatementOfAccountDEntity> beginPeriodList = resultDto.getStatementOfAccountDBeginPeriodList();
		// 获取对账单本期明细信息
		List<StatementOfAccountDEntity> periodList = resultDto.getStatementOfAccountDPeriodList();
		// 获取新增明细信息
		List<StatementOfAccountDetailDto> operateList = resultDto.getStatementOfAccountDetailPeriodList();
		// 将删除明细从本期对账单明细中删除
		beginPeriodList = (List<StatementOfAccountDEntity>) CollectionUtils.union(beginPeriodList, operateList);
		if(CollectionUtils.isNotEmpty(operateList)){
			for(StatementOfAccountDetailDto operateDto : operateList){
				if(CollectionUtils.isNotEmpty(periodList)){
					for(StatementOfAccountDEntity periodEntity : periodList){
						if(operateDto.getSourceBillNo().equals(periodEntity.getSourceBillNo())){
							resultDto.getStatementOfAccountOperateList().add(periodEntity);
							periodList.remove(periodEntity);
							break;
						}
					}
				}
			}
		}
		// 跟据删除的对账单明细信息修改对账单期初、本期及本期剩余发生金额信息
		//如果新增明细信息不为空
		if (CollectionUtils.isNotEmpty(operateList)) {
			//循环处理新增明细信息
			for (Iterator<StatementOfAccountDetailDto> it = operateList.iterator(); it.hasNext();) {
				// 期初金额
				BigDecimal beginPeriodAmount = BigDecimal.ZERO;
				// 期初应收
				BigDecimal beginPeriodRecAmount = BigDecimal.ZERO;
				// 期初应付
				BigDecimal beginPeriodPayAmount = BigDecimal.ZERO;
				// 期初预收
				BigDecimal beginPeriodPreAmount = BigDecimal.ZERO;
				// 期初预付
				BigDecimal beginPeriodAdvAmount = BigDecimal.ZERO;
				// 本期发生金额
				BigDecimal periodAmount = BigDecimal.ZERO;
				// 本期应收金额
				BigDecimal periodRecAmount = BigDecimal.ZERO;
				// 本期应付金额
				BigDecimal periodPayAmount = BigDecimal.ZERO;
				// 本期预收金额
				BigDecimal periodPreAmount = BigDecimal.ZERO;
				// 本期预付金额
				BigDecimal periodAdvAmount = BigDecimal.ZERO;
				StatementOfAccountDetailDto soadEntity = it.next();
				// 如果为应收单,则期初应收金额增加；期初发生金额增加；本期应收金额减少；本期发生金额减少
				if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE.equals(soadEntity.getBillParentType())) {
					//计算期初应收金额
					beginPeriodRecAmount = soaEntity.getBeginPeriodRecAmount().add(soadEntity.getUnverifyAmount());
					//计算期初发生金额
					beginPeriodAmount = soaEntity.getBeginPeriodAmount().add(soadEntity.getUnverifyAmount());
					//计算本期应收金额
					periodRecAmount = soaEntity.getPeriodRecAmount().subtract(soadEntity.getUnverifyAmount());
					//计算本期发生金额
					periodAmount = soaEntity.getPeriodAmount().subtract(soadEntity.getUnverifyAmount());
					// 修改对账单的期初应收金额
					soaEntity.setBeginPeriodRecAmount(beginPeriodRecAmount);
					// 修改对账单的期初发生金额
					soaEntity.setPeriodAmount(beginPeriodAmount);
					// 修改对账单的本期应收发生金额
					soaEntity.setPeriodRecAmount(periodRecAmount);
					// 修改对账单的本期发生金额
					soaEntity.setPeriodAmount(periodAmount);
				}
				// 如果为应付单,则期初应付金额增加；期初发生金额减少；本期应付金额减少；本期发生金额增加
				else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE.equals(soadEntity.getBillParentType())) {
					//计算期初应付金额
					beginPeriodPayAmount = soaEntity.getBeginPeriodPayAmount().add(soadEntity.getUnverifyAmount());
					//计算期初发生金额
					beginPeriodAmount = soaEntity.getBeginPeriodAmount().subtract(soadEntity.getUnverifyAmount());
					//计算本期应付金额
					periodPayAmount = soaEntity.getPeriodPayAmount().subtract(soadEntity.getUnverifyAmount());
					//计算本期发生金额
					periodAmount = soaEntity.getPeriodAmount().add(soadEntity.getUnverifyAmount());
					// 修改对账单期初应付金额
					soaEntity.setBeginPeriodPayAmount(beginPeriodPayAmount);
					// 修改对账单期初发生金额
					soaEntity.setBeginPeriodAmount(beginPeriodAmount);
					// 修改对账单本期应付金额
					soaEntity.setPeriodPayAmount(periodPayAmount);
					// 修改对账单本期发生金额
					soaEntity.setPeriodAmount(periodAmount);
				}
				// 如果为预收单,则期初预收金额增加；期初发生金额减少；本期预收金额减少；本期发生金额增加
				else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED.equals(soadEntity.getBillParentType())) {
					//计算期初预收金额
					beginPeriodPreAmount = soaEntity.getBeginPeriodPreAmount().add(soadEntity.getUnverifyAmount());
					// 计算期初发生金额
					beginPeriodAmount = soaEntity.getBeginPeriodAmount().subtract(soadEntity.getUnverifyAmount());
					//计算本期预收金额
					periodPreAmount = soaEntity.getPeriodPreAmount().subtract(soadEntity.getUnverifyAmount());
					//计算本期发生金额
					periodAmount = soaEntity.getPeriodAmount().add(soadEntity.getUnverifyAmount());
					// 修改对账单期初预收发生金额
					soaEntity.setBeginPeriodPreAmount(beginPeriodPreAmount);
					// 修改对账单期初发生金额
					soaEntity.setBeginPeriodAmount(beginPeriodAmount);
					// 修改对账单本期预收金额
					soaEntity.setPeriodPreAmount(periodPreAmount);
					// 修改对账单本期发生金额
					soaEntity.setPeriodAmount(periodAmount);
				}
				// 如果为预付单,则期初预付金额增加；期初发生金额增加；本期预付金额减少；本期发生金额减少
				else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT.equals(soadEntity.getBillParentType())) {
					//计算期初预付金额
					beginPeriodAdvAmount = soaEntity.getBeginPeriodAdvAmount().add(soadEntity.getUnverifyAmount());
					//计算期初发生金额
					beginPeriodAmount = soaEntity.getBeginPeriodAmount().add(soadEntity.getUnverifyAmount());
					//计算本期预付金额
					periodAdvAmount = soaEntity.getPeriodAdvAmount().subtract(soadEntity.getUnverifyAmount());
					//计算本期发生金额
					periodAmount = soaEntity.getPeriodAmount().subtract(soadEntity.getUnverifyAmount());
					// 修改对账单期初预付金额
					soaEntity.setBeginPeriodAdvAmount(beginPeriodAdvAmount);
					// 修改对账单期初发生金额
					soaEntity.setBeginPeriodAmount(beginPeriodAmount);
					// 修改对账单本期预付发生金额
					soaEntity.setPeriodAdvAmount(periodAdvAmount);
					// 修改对账单本期发生金额
					soaEntity.setPeriodAmount(periodAmount);
				}
			}
		}
		// 更新对账单的未核销发生金额
		soaEntity = statementOfAccountService.updatePeriodUnverifyAmountCommon(soaEntity);
		// 更新对账单的本期剩余未还金额
		soaEntity = statementMakeService.updateUnpaidAmount(soaEntity);
		// 将更新后的对账单、对账单期初明细信息、本期明细信息
		resultDto.setStatementOfAccount(soaEntity);
		//设置返回dto的期初数据列表
		resultDto.setStatementOfAccountDBeginPeriodList(beginPeriodList);
		//设置返回dto的本期数据列表
		resultDto.setStatementOfAccountDPeriodList(periodList);
		//返回dto
		return resultDto;
	}

	/**
	 * 对账单核销操作时，释放对账单明细后修改对账单的本期未还金额信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-15 上午11:05:07
	 */
	@Override
	public void releaseSOADUpdateStatement(StatementOfAccountEntity entity,CurrentInfo info) {
		//设置未还金额
		entity.setUnpaidAmount(BigDecimal.ZERO);
		//对账单核销时修改对账单的本期未还金额
		statementOfAccountService.updateStatementForWriteOff(entity, info);
		//ddw
		updateInstationMsgByIds(entity, info);
		
	}

	/**
	 * 根据客户编码查询客户信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-26 上午11:47:04
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementModifyService#queryCustInfoByCode(java.lang.String)
	 */
	@Override
	public CustomerDto queryCustInfoByCode(String custCode) {

		// 根据客户编码查询客户信息
		return customerService.queryCustInfoByCode(custCode);
	}

	/**
	 * 批量还款
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-26 下午8:19:23
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementModifyService#repaymentBatch(com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryResultDto)
	 */
	@Override
	public StatementToPaymentResultDto repaymentBatch(StatementOfAccountMakeQueryResultDto queryDto, CurrentInfo info) {

		// 对账单号list,并获取所有选中的对账单号
		List<String> statementNoList = new ArrayList<String>();

		// 获取对账单号list集合
		for (StatementOfAccountEntity entity : queryDto.getStatementOfAccountList()) {

			// 将对账单号加入到对账单集合中
			statementNoList.add(entity.getStatementBillNo());
		}

		// 根据对账单号list获取实时对账单信息
		List<StatementOfAccountEntity> statementList = new ArrayList<StatementOfAccountEntity>();

		// 循环处理对账单号list集合
		for (String statementNo : statementNoList) {

			// 根据对账单号获取对账单
			StatementOfAccountEntity entity = statementOfAccountService.queryByStatementNo(statementNo);

			// 如果对账单不为空
			if (entity != null) {

				// 获取当前时间
				Date nowDate = new Date();

				// 对比当前时间和对账单上的官网解锁时间，如果当前时间还未解锁，提示对账单被官网锁定
				if (entity.getUnlockTime() != null&& nowDate.before(entity.getUnlockTime())) {
					//提示所选对账单:XXXX已被官网锁定,不能还款
					throw new SettlementException("所选对账单:"+ entity.getStatementBillNo() + "已被官网锁定,不能还款!");
				}

				// 如果当前时间下已经解锁，加入对账单列表，继续处理
				statementList.add(entity);
			} else {

				// 对账单不存在，表明数据库该对账单已完成还款或者不存在该数据，提示重新查询并还款
				throw new SettlementException("所选对账单已不存在,请重新查询并还款!");
			}
		}

		// 获取对账单上的客户信息
		String customerCode = statementList.get(0).getCustomerCode();
		// 获取对账单上的对账单类型
		String billType = statementList.get(0).getBillType();

		// 判断该客户信息不能为空，否则提示还款时对账单客户信息为空，不能还款
		if (StringUtils.isEmpty(customerCode)) {
			//提示还款时客户信息为空,不能还款
			throw new SettlementException("还款时客户信息为空,不能还款!");
		}

		// 初始化本期还款金额、本期未还金额
		BigDecimal periodAmount = BigDecimal.ZERO;//本期还款金额
		BigDecimal unpaidAmount = BigDecimal.ZERO;//本期未还金额

		// 循环验证对账单列表的对账单是否可以进行还款操作
		for (StatementOfAccountEntity entity : statementList) {

			// 如果对账单的客户信息不一致，提示选择还款的对账单必须是同一客户
			if (!customerCode.equals(entity.getCustomerCode())) {
				//提示还款时所选对账单必须为同一客户
				throw new SettlementException("还款时所选对账单必须为同一客户!");

				// 如果选择的对账单本期发生金额小于0，为应付类型对账单，提示所选择的对账单不能进行还款
			} else if (entity.getPeriodAmount().compareTo(BigDecimal.ZERO) < 0) {
				//提示所选择的单据包含应付对账单，不能批量还款
				throw new SettlementException("所选择的单据包含应付对账单，不能批量还款");

				// 如果选择的对账单未还金额小于等于0，提示所选择的对账单不能进行还款
			} else if (entity.getUnpaidAmount().compareTo(BigDecimal.ZERO) <= 0) {
				//提示所选择的对账单本期未还金额小于等于0,,不能进行还款操作
				throw new SettlementException("所选择的对账单本期未还金额小于等于0，不能进行还款操作");
			}

			// 上述验证通过，累加所选对账单的本期发生金额
			periodAmount = periodAmount.add(entity.getPeriodAmount());
			// 上述验证通过，累加所选对账单的本期未还金额
			unpaidAmount = unpaidAmount.add(entity.getUnpaidAmount());
		}

		// 验证通过，初始化待确认对账单的列表
		List<StatementOfAccountEntity> statementListToConfirm = new ArrayList<StatementOfAccountEntity>();

		// 出是否待确认对账单的Dto
		StatementOfAccountMakeQueryResultDto statementListToConfirmDto = new StatementOfAccountMakeQueryResultDto();

		// 如果对账单为确认，放入待确认对账单list
		for (StatementOfAccountEntity entity : statementList) {
			// 如果对账单确认状态不等于已确认
			if (!SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM.equals(entity.getConfirmStatus())) {
				// 将该对账单加入到待确认对账单列表
				statementListToConfirm.add(entity);
			}
		}

		// 如果待确认对账单不为空，调用确认对账单
		if (CollectionUtils.isNotEmpty(statementListToConfirm)) {

			// 将待确认对账单列表赋值给带确认对账单Dto
			statementListToConfirmDto.setStatementOfAccountList(statementListToConfirm);
			// 调用确认对账单借口去确认对账单
			confirmStatementForWriteOff(statementListToConfirmDto, info);

			// 如果做了确认的数据变更，重新load下数据,以保证和数据库数据版本等信息保持一致
			statementList = statementOfAccountService.queryByStatementNos(statementNoList);
		}

		// 生成用于返回的对账单号字符串
		StringBuffer statementBillNos = new StringBuffer();
		// 生成用于返回的对账单版本号字符串
		StringBuffer versionNos = new StringBuffer();

		// 本次确认的对账单号和版本号的处理
		for (int i = 0; i < statementList.size(); i++) {

			// 如果对账单号字符串为空，将对账单号加入到待返回的对账单号字符串中
			if (StringUtils.isEmpty(statementBillNos.toString())) {
				//拼接对账单号
				statementBillNos.append(statementList.get(i).getStatementBillNo());
				// 如果对账单号字符串不为空，先拼接",",再将对账单号加入到待返回的对账单号字符串中
			} else {
				//拼接,
				statementBillNos.append(",");
				//拼接对账单号
				statementBillNos.append(statementList.get(i).getStatementBillNo());
			}

			// 如果待返回的对账单版本号字符串为空，将对账单版本号加入到待返回的对账单号字符串中
			if (StringUtils.isEmpty(versionNos.toString())) {
				//拼接版本号
				versionNos.append(statementList.get(i).getVersionNo().toString());
				// 如果待返回的对账单版本号字符串不为空，先拼接",",将对账单版本号加入到待返回的对账单号字符串中
			} else {
				//拼接,
				versionNos.append(",");
				//拼接版本号
				versionNos.append(statementList.get(i).getVersionNo().toString());
			}
		}

		// 设置返回值Dto
		StatementToPaymentResultDto rtnDto = new StatementToPaymentResultDto();

		// 赋值带返回的对账单号字符串
		rtnDto.setStatementBillNos(statementBillNos.toString());

		// 赋值带返回的对账单版本号字符串
		rtnDto.setVersionNos(versionNos.toString());

		// 赋值所选对账单的客户名称
		rtnDto.setCustomerName(statementList.get(0).getCustomerName());
		// 赋值所选对账单的客户编码
		rtnDto.setCustomerCode(customerCode);

		// 如果进入借口来源自对账单明细界面的还款操作
		if (SettlementConstants.REPAYMENT_BY_STATEMENTENTRY.equals(queryDto.getRepayType())) {

			// 将该对账单实体信息赋值并返回给界面使用
			rtnDto.setStatementOfAccount(statementList.get(0));
		}

		// 如果所选择的对账单类型为客户对账单
		if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__CUSTOMER_ACCOUNT.equals(billType)) {
			// 根据对账单客户编码查询客户信息
			CustomerDto customerDto = queryCustInfoByCode(customerCode);

			// 如果客户信息不为空
			if (customerDto != null) {

				// 获取对账单上的主联系人信息
				if (CollectionUtils.isNotEmpty(customerDto.getContactList())) {
					// 设置客户的主联系人信息到dto中
					//循环处理客户联系人
					for (ContactEntity entity : customerDto.getContactList()) {
						//如果联系人有效
						if (FossConstants.ACTIVE.equals(entity.getActive())&& FossConstants.ACTIVE.equals(entity.getMainContract())) {

							// 设置联系地址
							rtnDto.setAddress(entity.getAddress());
							// 设置客户身份证
							rtnDto.setIdCard(entity.getIdCard());

							// 设置联系电话， 如果移动电话存在，取移动电话
							if (StringUtils.isNotEmpty(entity.getMobilePhone())) {
								//设置联系电话
								rtnDto.setPhone(entity.getMobilePhone());
								// 否则取办公电话
							} else {
								rtnDto.setPhone(entity.getContactPhone());
							}
						}
					}
				}
			}

			// 如果对账单类型为代理对账单
		} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__AGENT_ACCOUNT.equals(billType)) {

			// 获取代理信息
			BusinessPartnerEntity entity = vehicleAgencyCompanyService.queryEntityByCode(customerCode);

			// 代理信息存在，设置代理名称、编码、联系地址、联系电话
			if (entity != null) {
				// 设置联系地址
				rtnDto.setAddress(entity.getContactAddress());
				// 设置联系电话， 如果移动电话存在，取移动电话
				if (StringUtils.isNotEmpty(entity.getMobilePhone())) {
					//设置联系电话
					rtnDto.setPhone(entity.getMobilePhone());
				} else {
					// 否则取办公电话
					rtnDto.setPhone(entity.getContactPhone());
				}
			}

			// 如果对账单类型为空运对账单
		} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__AIR_FREIGHT_ACCOUNT.equals(billType)) {

			// 获取空运代理信息
			BusinessPartnerEntity entity = airAgencyCompanyService.queryEntityByCode(customerCode);

			// 空运代理信息存在，设置空运代理名称、编码、联系地址、联系电话
			if (entity != null) {
				// 设置联系地址
				rtnDto.setAddress(entity.getContactAddress());
				// 设置联系电话， 如果移动电话存在，取移动电话
				if (StringUtils.isNotEmpty(entity.getMobilePhone())) {
					//设置联系电话
					rtnDto.setPhone(entity.getMobilePhone());
				} else {
					// 否则取办公电话
					rtnDto.setPhone(entity.getContactPhone());
				}
			}
		}

		// 设置本期发生金额
		rtnDto.setCurrentAmount(periodAmount);
		// 设置本期未还金额
		rtnDto.setCurrentRemainAmount(unpaidAmount);

		// 返回Dto结果
		return rtnDto;
	}

	@Override
	public void appliedStatement(StatementOfAccountMakeQueryResultDto queryDto,	CurrentInfo currentInfo) {
		// 如果要反确认的对账单集合不为空
		if (CollectionUtils.isNotEmpty(queryDto.getStatementOfAccountList())) {
			List<StatementOfAccountEntity> list = statementByFailingInvoiceDao.queryStatementBystatementBillNo(queryDto.getStatementOfAccountList());
			for(StatementOfAccountEntity entity : list){
				if(SettlementDictionaryConstants.SETTLEMENT_INVOICE_APPLY_INVOICE_NO.equals(entity.getApplyInvoice())
						|| "".equals(entity.getApplyInvoice()) || null == entity.getApplyInvoice()){
					throw new SettlementException("对账单单号：" + entity.getStatementBillNo() + "不需要申请发票，不可以进行已申请操作！");
				}
				if(SettlementDictionaryConstants.SETTLEMENT_INVOICE_STATUS_APPLIED.equals(entity.getInvoiceStatus())){
					throw new SettlementException("对账单单号：" + entity.getStatementBillNo() + "发票状态是已申请，不可以进行已申请操作！");
				}
				if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM.equals(entity.getConfirmStatus())
						|| "".equals(entity.getConfirmStatus())){
					throw new SettlementException("对账单单号：" + entity.getStatementBillNo() + "确认状态为未确认，不可以进行已申请操作！");
				}
			}
			//记录日志
			logger.info("已申请发票，enter service...");
			int appliedRows = statementByFailingInvoiceDao.appliedStatement(queryDto.getStatementOfAccountList(), currentInfo);
			if(appliedRows == queryDto.getStatementOfAccountList().size()){
				//ddw
				for(StatementOfAccountEntity entity : queryDto.getStatementOfAccountList()){
					List<String> ids = statementByFailingInvoiceDao.queryInstationMsgByIds(entity);
					if(ids != null && ids.size() > 0){
						messageService.updateInstationMsgByIds(ids, currentInfo);
					}
				}
			}else{
				throw new SettlementException("已申请发票失败！");
			}
		//不为空	
		} else {
			//提示没有需要反确认的对账单信息
			throw new SettlementException("没有需要已申请的对账单信息！", "");
		}
		//记录日志
		logger.info("已申请发票，successfully exit service...");
	}

	@Override
	public void notApplyStatement(StatementOfAccountMakeQueryResultDto queryDto, CurrentInfo currentInfo) {
		// 如果要反确认的对账单集合不为空
		if (CollectionUtils.isNotEmpty(queryDto.getStatementOfAccountList())) {
			List<StatementOfAccountEntity> list = statementByFailingInvoiceDao.queryStatementBystatementBillNo(queryDto.getStatementOfAccountList());
			for(StatementOfAccountEntity entity : list){
				if(SettlementDictionaryConstants.SETTLEMENT_INVOICE_APPLY_INVOICE_NO.equals(entity.getApplyInvoice())
						|| "".equals(entity.getApplyInvoice()) || null == entity.getApplyInvoice()){
					throw new SettlementException("对账单单号：" + entity.getStatementBillNo() + "不需要申请发票，不可以进行未申请操作！");
				}
				if(SettlementDictionaryConstants.SETTLEMENT_INVOICE_STATUS_NO_APPLY.equals(entity.getInvoiceStatus())){
					throw new SettlementException("对账单单号：" + entity.getStatementBillNo() + "发票状态是未申请，不可以进行未申请操作！");
				}
				if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM.equals(entity.getConfirmStatus())
						|| "".equals(entity.getConfirmStatus())){
					throw new SettlementException("对账单单号：" + entity.getStatementBillNo() + "确认状态为未确认，不可以进行未申请操作！");
				}
			}
			//记录日志
			logger.info("未申请发票，enter service...");
			int notApplyRows = statementByFailingInvoiceDao.notApplyStatement(queryDto.getStatementOfAccountList(), currentInfo);
			if(notApplyRows == queryDto.getStatementOfAccountList().size()){
				//ddw
				for(StatementOfAccountEntity entity : queryDto.getStatementOfAccountList()){
					if(entity.getUnpaidAmount().compareTo(BigDecimal.valueOf(0)) != 0){
						createBatchInstationMsg(entity,currentInfo);
					}
				}
			}else{
				throw new SettlementException("未申请发票失败！");
			}
		//不为空	
		} else {
			//提示没有需要反确认的对账单信息
			throw new SettlementException("没有需要未申请的对账单信息！", "");
		}
		//记录日志
		logger.info("未申请发票，successfully exit service...");
	}	

	@Override
	public void updateInstationMsgByIds(StatementOfAccountEntity entity,CurrentInfo info) {
		//ddw
		StatementOfAccountEntity statementOfAccountEntity = statementOfAccountService.queryByStatementNo(entity.getStatementBillNo());
		if(statementOfAccountEntity.getUnpaidAmount().compareTo(BigDecimal.valueOf(0)) == 0){
			List<String> ids = statementByFailingInvoiceDao.queryInstationMsgByIds(entity);
			if(ids != null && ids.size() > 0){
				messageService.updateInstationMsgByIds(ids, info);
			}
		}
	}
	
	/**
	 * 查询对账单章
	 * @author ddw
	 * @date 2014-10-10
	 */
	@Override
	public String queryStatementChapter(String companyCode) {
		String chapterUrl = statementModifyDao.queryStatementChapter(companyCode);
		return chapterUrl;
	}
	
	/**
	 * @param statementMakeService
	 */
	public void setStatementMakeService(IStatementMakeService statementMakeService) {
		this.statementMakeService = statementMakeService;
	}

	/**
	 * @param statementOfAccountDService
	 */
	public void setStatementOfAccountDService(IStatementOfAccountDService statementOfAccountDService) {
		this.statementOfAccountDService = statementOfAccountDService;
	}

	/**
	 * @param statementOfAccountService
	 */
	public void setStatementOfAccountService(IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	/**
	 * @param waybillRfcService
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	/**
	 * @param customerService
	 */
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * @param billReceivableService
	 */
	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	public IVehicleAgencyCompanyService getVehicleAgencyCompanyService() {
		return vehicleAgencyCompanyService;
	}

	public void setVehicleAgencyCompanyService(IVehicleAgencyCompanyService vehicleAgencyCompanyService) {
		this.vehicleAgencyCompanyService = vehicleAgencyCompanyService;
	}

	public IAirAgencyCompanyService getAirAgencyCompanyService() {
		return airAgencyCompanyService;
	}

	public void setAirAgencyCompanyService(IAirAgencyCompanyService airAgencyCompanyService) {
		this.airAgencyCompanyService = airAgencyCompanyService;
	}
	
	/**
	 * @param businessLockService
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setStatementByFailingInvoiceDao(
			IStatementByFailingInvoiceDao statementByFailingInvoiceDao) {
		this.statementByFailingInvoiceDao = statementByFailingInvoiceDao;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setStatementModifyDao(IStatementModifyDao statementModifyDao) {
		this.statementModifyDao = statementModifyDao;
	}

}
