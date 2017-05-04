package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPublicBankAccountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.settlement.common.api.server.service.IBillAdvancedPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillAdvancedPaymentEntityDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillDepositReceivedEntityDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountDetailDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IStatementMakeDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementMakeService;
import com.deppon.foss.module.settlement.writeoff.api.shared.define.SettlementWriteoffConstants;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryResultDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 制作对账单服务接口实现类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-11 下午5:30:13
 */
public class StatementMakeService implements IStatementMakeService {

	/**
	 * 注入制作对账单DAO
	 */
	private IStatementMakeDao statementMakeDao;

	/**
	 * 注入生成序列Service
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 注入应收单公共SERVICE
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 注入应付单公共SERVICE
	 */
	private IBillPayableService billPayableService;

	/**
	 * 注入预收单公共SERVICE
	 */
	private IBillDepositReceivedService billDepositReceivedService;

	/**
	 * 注入预付单公共SERVICE
	 */
	private IBillAdvancedPaymentService billAdvancedPaymentService;

	/**
	 * 注入对账单公共SERVICE
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 注入对账单明细公共SERVICE
	 */
	private IStatementOfAccountDService statementOfAccountDService;

	/**
	 * 注入组织SERVICE
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 子公司银行信息
	 */
	private IPublicBankAccountService publicBankAccountService;
	
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(StatementMakeService.class);
	private static final int SELECTPARAM = 3000;
	
	/**
	 * 查询对账单明细包含的（应收、应付、预收、预付）单据及对账单信息服务接口
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-12 上午8:29:03
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementMakeService#queryForStatementMake(com.deppon.foss.module.settlement.writeoff.api.shared.vo.StatementOfAccountMakeQueryVo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public StatementOfAccountMakeQueryResultDto queryForStatementMake(StatementOfAccountMakeQueryDto queryDto, CurrentInfo info) {
		//参数dto不能为空
		if (queryDto == null) {
			//提示查询DTO为空
			throw new SettlementException("查询DTO为空！", "");
		}
		//记录日志
		logger.info("制作对账单，enter service ,客户编码：" + queryDto.getCustomerCode());
		//当前登录用户不能为空
		if (info == null) {
			//提示当前登录用户信息为空
			throw new SettlementException("当前登录用户信息为空！", "");
		}
		queryDto.setOrgCode(info.getCurrentDeptCode());
		// 存放对账单期初明细集合
		List<StatementOfAccountDEntity> beginPeriodList = new ArrayList<StatementOfAccountDEntity>();
		// 存放对账单本期明细集合
		List<StatementOfAccountDEntity> periodList = new ArrayList<StatementOfAccountDEntity>();
		// 存放返回的对账单明细及对账单
		StatementOfAccountMakeQueryResultDto resultDto = new StatementOfAccountMakeQueryResultDto();
		/*
		 * 设置SQL查询语句中需要的常量值，调用私有方法
		 */
		setConstantsForStatementMake(queryDto);
		/*
		 * 首先进行判断，用户是按照客户制作对账单还是按单号制作对账单
		 */
		// 如果是按单号制作，则需要对前台传入的单号集合进行识别转换，转换成按 财务单据编号查询和来源单号查询集合
		if (SettlementConstants.TAB_QUERY_BY_BILL_NO.equals(queryDto.getQueryPage())) {
			if(queryDto.getBillDetailNos() == null || queryDto.getBillDetailNos().length == 0){
				throw new SettlementException("按单制作对账单时，输入的单号不能为空");
			}
			// 按单号查询时，为queryDto设置界面传入的单号集合属性，调用私有方法
			setBillNosForQueryDto(queryDto);
			/*
			 * 获取本期对账单明细数据 分别判断应收单号集合、应付单号集合、预收单号集合、预付单号集合及来源单号集合是否为空
			 * 如果不为空，则分别调用对应的按单号集合查询单据信息方法 并将查询出的集合添加到对账单本期明细集合中 调用私有方法
			 */
			//按单号查询本期对账单明细数据
			Map<String, Object> periodMap = queryPeriodDetailByBillNos(queryDto, periodList);
			//获取本期明细集合
			periodList = (List<StatementOfAccountDEntity>) periodMap.get(SettlementWriteoffConstants.periodList);
			//获取本期客户编码和名称
			String customerCode = (String) periodMap.get(SettlementWriteoffConstants.customerCode);
			String customerName = (String) periodMap.get(SettlementWriteoffConstants.customerName);
			//设置本期账单
			queryDto.setCustomerCode(customerCode);
			queryDto.setCustomerName(customerName);
			/*
			 * 获取期初对账单明细数据 将账期设为空，则查询 如果不为空，则查询出所有为未核销且未进入对账单明细信息
			 * 则期初明细信息为所有数据信息减去本期明细信息 并将查询出的集合添加到对账单本期明细集合中
			 */
			// 获取账期起始时间和结束时间,起始时间取对账单明细中最早的业务日期,结束时间取对账单明细中最晚的业务日期
			if (CollectionUtils.isNotEmpty(periodList)) {
				Date periodBeginDate = null;
				Date periodEndDate = null;
				for (StatementOfAccountDEntity dEntity : periodList) {
					if (null == periodBeginDate) {
						periodBeginDate = dEntity.getBusinessDate();
					} else if (periodBeginDate.after(dEntity.getBusinessDate())) {
						periodBeginDate = dEntity.getBusinessDate();

					}
					if (null == periodEndDate) {
						periodEndDate = dEntity.getBusinessDate();
					} else if (periodEndDate.before(dEntity.getBusinessDate())) {
						periodEndDate = dEntity.getBusinessDate();
					}
				}
				queryDto.setPeriodBeginDate(periodBeginDate);
				queryDto.setPeriodEndDate(periodEndDate);
			}
		}
		// 按空运正单号制作
		else if(SettlementConstants.TAB_QUERY_BY_AIR_WAYBILL_NO.equals(queryDto.getQueryPage())){
			periodList = queryPeriodDetailByAirWaybillNo(queryDto);
		}
		// 否则为按客户日期制作
		else {
			// 调用私有方法获取期初和本期明细数据
			Map<String, Object> allPeriodMap = queryDetailByBillDate(queryDto,beginPeriodList, periodList);
			//获取查询参数对象
			queryDto = (StatementOfAccountMakeQueryDto) allPeriodMap.get("queryDto");
			//获取期初明细信息集合
			beginPeriodList = (List<StatementOfAccountDEntity>) allPeriodMap.get(SettlementWriteoffConstants.beginPeriodList);
			//获取本期明细集合
			periodList = (List<StatementOfAccountDEntity>) allPeriodMap.get(SettlementWriteoffConstants.periodList);
		}
		// 将对账单本期明细保存到返回结果集中
		resultDto.setStatementOfAccountDPeriodList(periodList);
		//记录日志
		logger.debug("制作对账单时，查询对账单明细，本期明细条数："+ resultDto.getStatementOfAccountDPeriodList().size());
		// 将对账单期初明细保存到返回结果集中
		resultDto.setStatementOfAccountDBeginPeriodList(beginPeriodList);
		//记录日志
		logger.debug("制作对账单时，查询对账单明细，期初明细条数："+ resultDto.getStatementOfAccountDBeginPeriodList().size());

		/*
		 * 保存预生成的对账单信息
		 */
		StatementOfAccountEntity soaEntity = new StatementOfAccountEntity();
		// 当前登录用户操作部门
		soaEntity.setCreateOrgCode(info.getCurrentDeptCode());
		// 当前登录用户操作部门
		soaEntity.setCreateOrgName(info.getCurrentDeptName());
		// 获取当前登录组织的的实体信息（调用综合管理接口）
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(info.getCurrentDeptCode());
		// 当前登录用户操作公司
		soaEntity.setCompanyCode(orgEntity.getSubsidiaryCode());
		// 当前登录用户操作公司
		soaEntity.setCompanyName(orgEntity.getSubsidiaryName());
		// 当前登录用户所属部门标杆编码
		soaEntity.setUnifiedCode(info.getDept().getUnifiedCode());
		//设置客户编码
		soaEntity.setCustomerCode(queryDto.getCustomerCode());
		//设置客户名称
		soaEntity.setCustomerName(queryDto.getCustomerName());
		/*
		 * 需要根据当前登录用户所属部门的性质判断获取
		 */
		// 如果为营业部派送部，则为客户对账单
		if(!StringUtils.isEmpty(queryDto.getBillType())){
			//快递代理对账单
			soaEntity.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__LAND_STOWAGE_ACCOUNT);
		}else if (FossConstants.YES.equals(orgEntity.getSalesDepartment())) {
			// 客户对账单
			soaEntity.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__CUSTOMER_ACCOUNT);
		}
		// 如果改部门为空运总调，则为空运对账单
		else if (FossConstants.YES.equals(orgEntity.getAirDispatch())) {
			// 空运对账单
			soaEntity.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__AIR_FREIGHT_ACCOUNT);
		}
		// 否则为代理对账单
		else {
			soaEntity.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__AGENT_ACCOUNT);
		}
		//设置参数：开始日期
		soaEntity.setPeriodBeginDate(queryDto.getPeriodBeginDate());
		//设置参数：结束日期
		soaEntity.setPeriodEndDate(queryDto.getPeriodEndDate());
		//设置参数：结账次数
		soaEntity.setSettleNum((short) 0);
		// 当前登录用户
		soaEntity.setCreateUserCode(info.getEmpCode()); 
		// 当前登录用户
		soaEntity.setCreateUserName(info.getEmpName()); 
		//设置参数：业务日期
		soaEntity.setBusinessDate(new Date());
		// 调用综合管理接口获取公司的对公银行账号信息
		PublicBankAccountEntity publicBankAccountEntity = new PublicBankAccountEntity();
		// 部门标杆编码信息
		publicBankAccountEntity.setDeptCd(info.getDept().getUnifiedCode());
		//查询部门所属银行信息
		List<PublicBankAccountEntity> list = publicBankAccountService.queryPublicBankAccountExactByEntity(publicBankAccountEntity,0, Integer.MAX_VALUE);
		//如果银行账号信息为空则抛出异常
		if (CollectionUtils.isEmpty(list)) {
			//提示当前操作用户所属部门没有银行账号信息
			//throw new SettlementException("当前操作用户所属部门没有银行账号信息！", "");
			resultDto.setAccountException("当前操作用户所属部门没有银行账号信息！");
		}else if (list.size() > 1) {
			//提示当前操作用户所属部门存在多条银行账号信息
			//throw new SettlementException("当前操作用户所属部门存在多条银行账号信息！", "");
			resultDto.setAccountException("当前操作用户所属部门存在多条银行账号信息！");
		}else{
			//获取第一条银行账号信息（业务上只允许一条，但综合提供的方法返回参数是集合）
			PublicBankAccountEntity bankAccountEntity = list.get(0);
			// 获取支行名称
			soaEntity.setBankBranchName(bankAccountEntity.getSubbranchName()); 
			// 获取开户名
			soaEntity.setAccountUserName(bankAccountEntity.getBankAccName());
			// 获取子公司账号
			soaEntity.setCompanyAccountBankNo(bankAccountEntity.getBankAcc());
		}
	
		// 获取结账次数
		soaEntity.setSettleNum((short) 0);
		//如果按日期查询
		if (SettlementConstants.TAB_QUERY_BY_DATE.equals(queryDto.getQueryPage())) {
			// 将结束日期减1天（由于前台将日期增加了一天，所以保存到数据库时必须减去一天）
			soaEntity.setPeriodEndDate(DateUtils.addDays(queryDto.getPeriodEndDate(), -1));
		}
		/*
		 * 获取期初发生金额统计信息并设置到对账单信息中
		 */
		Map<String, Object> beginPeriodAmountMap = countBeginPeriodAmount(beginPeriodList, soaEntity);
		//记录日志
		logger.info("制作对账单:获取期初发生金额信息完毕！");
		//获取期初统计金额信息的对账单
		soaEntity = (StatementOfAccountEntity) beginPeriodAmountMap.get(SettlementWriteoffConstants.soaEntity);
		/*
		 * 获取本期发生金额及本期剩余发生金额统计信息并设置到对账单信息中
		 */
		//计算对账单本期发生金额、本期剩余发生金额信息
		countPeriodAmount(periodList,soaEntity);
		logger.info("制作对账单:获取本期发生金额及本期剩余发生金额统计信息完毕！");
		// 获取对账单的本期未还金额
		soaEntity = updateUnpaidAmount(soaEntity);
		// 将对账单实体保存到DTO中
		resultDto.setStatementOfAccount(soaEntity);
		//记录日志
		logger.info("制作对账单，successfully exit service ,客户编码："+ queryDto.getCustomerCode());
		//返回对账单制作dto
		return resultDto;
	}

	/**
	 * 制作对账单时，保存对账单及对账单明细单据
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-17 下午3:46:35
	 */
	@Transactional
	@Override
	public StatementOfAccountMakeQueryResultDto addStatement(StatementOfAccountMakeQueryResultDto resultDto, CurrentInfo info) {
		//参数dto不能为空
		if (resultDto.getStatementOfAccount() == null) {
			//提示制作对账单时,传入的对账单实体为空
			throw new SettlementException("制作对账单时,传入的对账单实体为空！", "");
		}
		//记录日志
		logger.info("新增对账单 enter service；客户编码："+ resultDto.getStatementOfAccount().getCustomerCode()+ "本期明细条数"+ resultDto.getStatementOfAccountDPeriodList().size()+ ";期初明细条数"+ resultDto.getStatementOfAccountDBeginPeriodList().size());
		// 生成对账单单号
		String statementNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.DZ);
		/*
		 * 校验对账单明细单据是否发生了变化，如果发生变化则抛出异常
		 */

		// 获取对账单明细信息集合
		List<StatementOfAccountDetailDto> soaDetailList = resultDto.getStatementOfAccountDetailPeriodList();
		// 调用验证对账单明细单据是否发生变化方法,如果发生变化，则抛出异常信息，如果没有发生变化则，将对账单号修改到原始单据上
		List<StatementOfAccountDEntity> soadList = validateForSaveDetail(soaDetailList, statementNo, info);
		resultDto.setStatementOfAccountDPeriodList(soadList);
		// 获取对账单信息
		StatementOfAccountEntity soaEntity = resultDto.getStatementOfAccount();
		// 校验前台计算的发生金额和数据明细统计金额是否相等
		validatePeriodAmount(resultDto.getStatementOfAccountDPeriodList(), soaEntity);
		//获取当前日期
		Date currentTime = Calendar.getInstance().getTime();

		// 设置对账单号
		soaEntity.setStatementBillNo(statementNo);
		soaEntity.setId(UUIDUtils.getUUID());//ID
		soaEntity.setCreateTime(currentTime);
		// 获取业务日期
		soaEntity.setBusinessDate(currentTime);
		//设置创建网点编码
		soaEntity.setCreateOrgCode(info.getCurrentDeptCode());
		//设置创建网点名称
		soaEntity.setCreateOrgName(info.getCurrentDeptName());
		soaEntity.setCreateUserCode(info.getEmpCode());//设置创建用户编码
		soaEntity.setCreateUserName(info.getEmpName());//设置创建用户名称
		soaEntity.setModifyUserCode(info.getCurrentDeptCode());//设置修改用户编码
		soaEntity.setModifyUserName(info.getCurrentDeptName());//设置修改用户名称
		soaEntity.setModifyTime(currentTime);//设置修改时间
		// 设置部门标杆编码
		soaEntity.setUnifiedCode(info.getDept().getUnifiedCode());
		// 版本号
		soaEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		// 确认状态
		soaEntity.setConfirmStatus(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM);
		// 结账次数
		soaEntity.setSettleNum((short) 0);
		soaEntity.setUnifiedSettlement(resultDto.getStatementOfAccount().getUnifiedSettlement());
		// 保存对账单明细信息
		//如果对账单本期明细实体集合不为空
		if (CollectionUtils.isNotEmpty(resultDto.getStatementOfAccountDPeriodList())) {
			//遍历对账单本期明细实体集合
			Iterator<StatementOfAccountDEntity> it = resultDto.getStatementOfAccountDPeriodList().iterator();
			//存在
			while (it.hasNext()) {
				StatementOfAccountDEntity soadEntity = it.next();//获取对账单明晰实体
				soadEntity.setId(UUIDUtils.getUUID());//设置对账单ID
				soadEntity.setIsDelete(FossConstants.NO);//设置对账单是否删除状态
				soadEntity.setCreateTime(currentTime);//设置对账单创建世间
				soadEntity.setStatementBillNo(soaEntity.getStatementBillNo());//设置对账单号
				// 保存对账单明细信息
				statementOfAccountDService.add(soadEntity);
			}
		}
		// 如果本期本期明细只有预付单或预收和预付单组合时，提示不能保存对账单
		if (soaEntity.getPeriodRecAmount().compareTo(BigDecimal.ZERO) == 0&& soaEntity.getPeriodPayAmount().compareTo(BigDecimal.ZERO) == 0&& soaEntity.getPeriodPreAmount().compareTo(BigDecimal.ZERO) == 0&& soaEntity.getPeriodAdvAmount().compareTo(BigDecimal.ZERO) > 0) {
			//提示对账单只包含预付单信息，不能保存
			throw new SettlementException("对账单只包含预付单信息，不能保存！", "");
		}
		if (soaEntity.getPeriodRecAmount().compareTo(BigDecimal.ZERO) == 0&& soaEntity.getPeriodPayAmount().compareTo(BigDecimal.ZERO) == 0&& soaEntity.getPeriodPreAmount().compareTo(BigDecimal.ZERO) > 0&& soaEntity.getPeriodAdvAmount().compareTo(BigDecimal.ZERO) > 0) {
			//提示对账单只包含预收单和预付单信息，不能保存
			throw new SettlementException("对账单只包含预收单和预付单信息，不能保存！", "");
		}

		// 保存对账单信息
		statementOfAccountService.add(soaEntity);
		//设置返回dto对账单
		resultDto.setStatementOfAccount(soaEntity);
		//根据对账单号查询对账单明细信息
		List<StatementOfAccountDEntity> perioList = statementOfAccountDService.queryByStatementBillNo(soaEntity.getStatementBillNo());
		//设置返回dto对账单明细
		resultDto.setStatementOfAccountDPeriodList(perioList);
		//返回dto
		return resultDto;
	}

	/**
	 * 在新增对账单明细时校验明细单据是否发生了变化
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-24 下午5:54:37
	 */
	@Override
	public List<StatementOfAccountDEntity> validateForAddDetail(List<StatementOfAccountDetailDto> list,String statementNo, CurrentInfo info) {
		//在保存对账单明细时校验明细单据是否发生了变化
		return validateForSaveDetail(list, statementNo, info);
	}

	/**
	 * 新增对账单明细时校验对账单明细统计金额和对账单信息的金额是否一致
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-2 下午1:55:49
	 */
	@Override
	public void validateTotalAmountForAddDetail(List<StatementOfAccountDEntity> beginPeriodList,StatementOfAccountEntity soaEntity,List<StatementOfAccountDEntity> periodList, CurrentInfo info) {
		//保存对账单时校验对账单明细统计金额和对账单信息的金额是否一致
		this.validatePeriodAmount(periodList, soaEntity);
	}

	/**
	 * 新增对账单明细时时，修改进入对账单的应收单、应付单、预收单、预付单明细的对账单号
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-1 上午10:04:08
	 */
	@Override
	public void updateDetailBillNoForAddDetail(List<BillReceivableEntity> receivableEntityList,List<BillPayableEntity> payableEntityList,List<BillDepositReceivedEntity> depositReceivedEntityList,List<BillAdvancedPaymentEntity> advancedPaymentEntityList,String statementNo, CurrentInfo info) {
		//生成对账单时，批量修改进入对账单的应收单、应付单、预收单、预付单明细的对账单号
		updateDetailBillStatementBillNo(receivableEntityList,payableEntityList, depositReceivedEntityList,advancedPaymentEntityList, statementNo, info);
	}

	/**
	 * 新增对账单明细时查询明细来源单据
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-3 下午5:24:35
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementMakeService#queryForAddDetailBill(com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public StatementOfAccountMakeQueryResultDto queryForAddDetailBill(StatementOfAccountMakeQueryDto queryDto, CurrentInfo info) {
		// 存放对账单本期明细集合
		List<StatementOfAccountDEntity> periodList = new ArrayList<StatementOfAccountDEntity>();
		// 存放对账单期初明细集合
		List<StatementOfAccountDEntity> beginPeriodList = new ArrayList<StatementOfAccountDEntity>();
		// 存放返回的对账单明细及对账单
		StatementOfAccountMakeQueryResultDto resultDto = new StatementOfAccountMakeQueryResultDto();
		/*
		 * 设置SQL查询语句中需要的常量值，调用私有方法
		 */
		queryDto.setOrgCode(info.getCurrentDeptCode());
		//制作对账单时为dto赋值常量值
		setConstantsForStatementMake(queryDto);
		/*
		 * 首先进行判断，用户是按照客户制作对账单还是按单号制作对账单
		 */
		// 如果是按单号制作，则需要对前台传入的单号集合进行识别转换，转换成按 财务单据编号查询和来源单号查询集合
		if (SettlementConstants.TAB_QUERY_BY_BILL_NO.equals(queryDto.getQueryPage())&& queryDto.getBillDetailNos() != null&& queryDto.getBillDetailNos().length != 0) {
			// 按单号查询时，为queryDto设置界面传入的单号集合属性，调用私有方法
			setBillNosForQueryDto(queryDto);
			/*
			 * 获取本期对账单明细数据 分别判断应收单号集合、应付单号集合、预收单号集合、预付单号集合及来源单号集合是否为空
			 * 如果不为空，则分别调用对应的按单号集合查询单据信息方法 并将查询出的集合添加到对账单本期明细集合中 调用私有方法
			 * 
			 */
			//按单号查询本期对账单明细数据
			Map<String, Object> periodMap = queryPeriodDetailByBillNos(queryDto, periodList);
			//获取对账单制作查询dto
			queryDto = (StatementOfAccountMakeQueryDto) periodMap.get("queryDto");
			//获取本期对账单明细
			periodList = (List<StatementOfAccountDEntity>) periodMap.get(SettlementWriteoffConstants.periodList);
		}
		// 否则为按客户日期制作
		else {
			// 调用私有方法获取期初和本期明细数据
			//设置是否查询期初明细信息
			queryDto.setQueryBeginPriodFlag(FossConstants.NO);
			//按账期查询期初本期对账单明细数据
			Map<String, Object> allPeriodMap = queryDetailByBillDate(queryDto,beginPeriodList, periodList);
			//获取本期对账单明细
			periodList = (List<StatementOfAccountDEntity>) allPeriodMap.get(SettlementWriteoffConstants.periodList);
		}
		// 将对账单期初和本期明细保存到返回结果集中
		resultDto.setStatementOfAccountDPeriodList(periodList);
		//记录日志
		logger.debug("本期明细条数："+ resultDto.getStatementOfAccountDPeriodList().size());
		//返回对账单明细及对账单
		return resultDto;
	}

	/**
	 * 在保存对账单明细时校验明细单据是否发生了变化
	 * (如果未发生变化，则将对账单号修到原始单据信息上)
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-24 下午5:54:37
	 */
	@SuppressWarnings("unchecked")
	private List<StatementOfAccountDEntity> validateForSaveDetail(List<StatementOfAccountDetailDto> list,String statementNo, CurrentInfo info) {
		//记录日志
		logger.info("操作未确认的对账单时校验明细单据是否发生了变化,enter service..");
		List<StatementOfAccountDEntity> soadList = new ArrayList<StatementOfAccountDEntity>();
		// 如果明细单据发生更改变为无效状态或发生核销则保存单号进入该集合
		List<String> hasChangedNosList = new ArrayList<String>();
		// 应收单号、应付单号、预收单号、预付单号列表
		List<String> recNos = new ArrayList<String>();
		List<String> payNos = new ArrayList<String>();
		List<String> depNos = new ArrayList<String>();
		List<String> advNos = new ArrayList<String>();
		//如果对账单明细不为空
		if (CollectionUtils.isNotEmpty(list)) {
			//循环处理对账单明细
			for (Iterator<StatementOfAccountDetailDto> it = list.iterator(); it.hasNext();) {
				//获取当前对账单明细
				StatementOfAccountDetailDto soadEntity = (StatementOfAccountDetailDto) it.next();
				// 校验应收单
				if (SettlementUtil.isReceiveable(soadEntity.getSourceBillNo())) {
					recNos.add(soadEntity.getSourceBillNo());
				} 
				//判断该单号是否为应付单号
				else if (SettlementUtil.isPayable(soadEntity.getSourceBillNo())) {
					payNos.add(soadEntity.getSourceBillNo());
				}
				//判断该单号是否为预收单号
				else if (SettlementUtil.isDepositReceived(soadEntity.getSourceBillNo())) {
					depNos.add(soadEntity.getSourceBillNo());
				} 
				//判断该单号是否为预付单号
				else if (SettlementUtil.isAdvancedPayment(soadEntity.getSourceBillNo())) {
					advNos.add(soadEntity.getSourceBillNo());
				}
			}
		}
		//应收单集合
		List<BillReceivableEntity> recEntitys = null;
		//应付单集合
		List<BillPayableEntity> payEntitys = null;
		//预收单集合
		List<BillDepositReceivedEntity> depEntitys = null;
		//预付单集合
		List<BillAdvancedPaymentEntity> advEntitys = null;
		if (CollectionUtils.isNotEmpty(recNos)) {
			//邓大伟，2013-08-15，按单号查询每一千条查一次
			List<String> listRecNos = new ArrayList<String>();
			List<BillReceivableEntity> recEntitysList = new ArrayList<BillReceivableEntity>();
			for(int i = 0; i < recNos.size();i++){
					
				listRecNos.add(recNos.get(i));
				if(listRecNos.size() == SettlementConstants.MAX_LIST_SIZE){
					recEntitysList = (List<BillReceivableEntity>) CollectionUtils.union(recEntitysList,statementMakeDao.queryByReceivableNOs(listRecNos, FossConstants.ACTIVE));
					listRecNos.clear();
				}
			}
			if(0 < listRecNos.size() && listRecNos.size() < SettlementConstants.MAX_LIST_SIZE){
				recEntitysList = (List<BillReceivableEntity>) CollectionUtils.union(recEntitysList,statementMakeDao.queryByReceivableNOs(listRecNos, FossConstants.ACTIVE));
				listRecNos.clear();
			}
			//邓大伟，2013-07-22，修改对账单制作应收单查询方法，从公共方法改成私有方法
			recEntitys = recEntitysList;
//			recEntitys = statementMakeDao.queryByReceivableNOs(recNos, FossConstants.ACTIVE);
//			recEntitys = billReceivableService
//					.queryByReceivableNOs(recNos, FossConstants.ACTIVE);
			// 校验查询出的应收单记录是否为空
			if (CollectionUtils.isEmpty(recEntitys)){
				throw new SettlementException("应收单记录已经发生过变化，请重新查询后保存");
			}
			// 校验查询出的应收单记录数量是否和传入的应收单号数量一致
			if (recNos.size() != recEntitys.size()) {
				throw new SettlementException("应收单记录已经发生过变化，请重新查询后保存");
			}
			// 校验查询出的应收单的单号和传入的应收单号是否一致
			for (String recNo : recNos) {
				boolean isMaching = false;
				for (BillReceivableEntity rec : recEntitys) {
					if (rec.getReceivableNo().equals(recNo)) {
						isMaching = true;
						break;
					}
				}
				if(!isMaching){
					throw new SettlementException("应收单记录已经发生过变化，请重新查询后核销");
				}
			}
		}
		if (CollectionUtils.isNotEmpty(payNos)) {
			//邓大伟，2013-08-15，按单号查询每一千条查一次
			List<String> listPayNos = new ArrayList<String>();
			List<BillPayableEntity> payEntitysList = new ArrayList<BillPayableEntity>();
			for(int i = 0; i < payNos.size();i++){
					
				listPayNos.add(payNos.get(i));
				if(listPayNos.size() == SettlementConstants.MAX_LIST_SIZE){
					payEntitysList = (List<BillPayableEntity>) CollectionUtils.union(payEntitysList,billPayableService.queryByPayableNOs(listPayNos, FossConstants.ACTIVE));
					listPayNos.clear();
				}
			}
			if(0 < listPayNos.size() && listPayNos.size() < SettlementConstants.MAX_LIST_SIZE){
				payEntitysList = (List<BillPayableEntity>) CollectionUtils.union(payEntitysList,billPayableService.queryByPayableNOs(listPayNos, FossConstants.ACTIVE));
				listPayNos.clear();
			}
			payEntitys = payEntitysList;
//			payEntitys = billPayableService.queryByPayableNOs(payNos, FossConstants.ACTIVE);
			// 校验查询出的应付单记录是否为空
			if (CollectionUtils.isEmpty(payEntitys)){
				throw new SettlementException("应付单记录已经发生过变化，请重新查询后保存");
			}
			// 校验查询出的应付单记录数量是否和传入的应付单号数量一致
			if (payNos.size() != payEntitys.size()) {
				throw new SettlementException("应付单记录已经发生过变化，请重新查询后保存");
			}
			// 校验查询出的应付单的单号和传入的应付单号是否一致
			for (String payNo : payNos) {
				boolean isMaching = false;
				for (BillPayableEntity pay : payEntitys) {
					if (pay.getPayableNo().equals(payNo)) {
						isMaching = true;
						break;
					}
				}
				if(!isMaching){
					throw new SettlementException("应收单记录已经发生过变化，请重新查询后核销");
				}
			}
		}
		if (CollectionUtils.isNotEmpty(depNos)) {
			
			//邓大伟，2013-09-05，按单号查询每一千条查一次
			List<String> listDepNos = new ArrayList<String>();
			List<BillDepositReceivedEntity> depEntitysList = new ArrayList<BillDepositReceivedEntity>();
			for(int i = 0; i < depNos.size();i++){
					
				listDepNos.add(depNos.get(i));
				if(listDepNos.size() == SettlementConstants.MAX_LIST_SIZE){
					depEntitysList = (List<BillDepositReceivedEntity>) CollectionUtils.union(depEntitysList,billDepositReceivedService.queryByDepositReceivedNOs(listDepNos, FossConstants.ACTIVE));
					listDepNos.clear();
				}
			}
			if(0 < listDepNos.size() && listDepNos.size() < SettlementConstants.MAX_LIST_SIZE){
				depEntitysList = (List<BillDepositReceivedEntity>) CollectionUtils.union(depEntitysList,billDepositReceivedService.queryByDepositReceivedNOs(listDepNos, FossConstants.ACTIVE));
				listDepNos.clear();
			}
			//邓大伟，2013-07-22，修改对账单制作应收单查询方法，从公共方法改成私有方法
			depEntitys = depEntitysList;
			
//			depEntitys = billDepositReceivedService.queryByDepositReceivedNOs(depNos, FossConstants.ACTIVE);
			// 校验查询出的预收单记录是否为空
			if(CollectionUtils.isEmpty(depEntitys)){
				throw new SettlementException("预收单记录已经发生过变化，请重新查询后保存");
			}
			// 校验查询出的预收单记录数量是否和传入的预收单号数量一致
			if (depNos.size() != depEntitys.size()) {
				throw new SettlementException("预收单记录已经发生过变化，请重新查询后保存");
			}
			// 校验查询出的预收单的单号和传入的预收单号是否一致
			for (String depNo : depNos) {
				boolean isMaching = false;
				for (BillDepositReceivedEntity dep : depEntitys) {
					if (dep.getDepositReceivedNo().equals(depNo)) {
						isMaching = true;
						break;
					}
				}
				if(!isMaching){
					throw new SettlementException("应收单记录已经发生过变化，请重新查询后核销");
				}
			}
		}
		if (CollectionUtils.isNotEmpty(advNos)) {
			advEntitys = billAdvancedPaymentService
					.queryBillAdvancedPaymentNos(advNos, FossConstants.ACTIVE);
			// 校验查询出的预付单记录是否为空
			if(CollectionUtils.isEmpty(advEntitys)){
				throw new SettlementException("预付单记录已经发生过变化，请重新查询后保存");
			}
			// 校验查询出的预付单记录数量是否和传入的预付单号数量一致
			if (advNos.size() != advEntitys.size()) {
				throw new SettlementException("预付单记录已经发生过变化，请重新查询后保存");
			}
			// 校验查询出的预付单的单号和传入的预付单号是否一致
			for (String advNo : advNos) {
				boolean isMaching = false;
				for (BillAdvancedPaymentEntity adv : advEntitys) {
					if (adv.getAdvancesNo().equals(advNo)) {
						isMaching = true;
						break;
					}
				}
				if(!isMaching){
					throw new SettlementException("应收单记录已经发生过变化，请重新查询后核销");
				}
			}
		}
		//如果对账单明细不为空
		if (CollectionUtils.isNotEmpty(list)) {
			//循环处理对账单明细
			for (Iterator<StatementOfAccountDetailDto> it = list.iterator(); it.hasNext();) {
				//获取当前对账单明细
				StatementOfAccountDetailDto soadEntity = (StatementOfAccountDetailDto) it.next();
				// 校验应收单
				if (SettlementUtil.isReceiveable(soadEntity.getSourceBillNo())) {
					for (BillReceivableEntity recEntity : recEntitys){
						// 校验应收单是否发生红冲或核销或空运其他应收单的审核状态发生了变化，则不保存明细
						if(recEntity.getReceivableNo().equals(soadEntity.getSourceBillNo())){
							if(!recEntity.getVersionNo().equals(soadEntity.getVersionNo())){
								//放入已发生改变单据集合
								hasChangedNosList.add(soadEntity.getSourceBillNo());
							} else {
								// 如果发生核销则，两个实体的未核销金额不相等，则不保存明细
								if (soadEntity.getUnverifyAmount().compareTo(recEntity.getUnverifyAmount()) != 0) {
									//放入已发生改变单据集合
									hasChangedNosList.add(soadEntity.getSourceBillNo());
								} else {
									// 如果已被网厅锁定则不能保存明细
									if (recEntity.getUnlockDateTime() == null|| new Date().after(recEntity.getUnlockDateTime())) {
										// 如果审核状态发生了变化,则不保存明细
										if (StringUtil.isNotEmpty(soadEntity.getAuditStatus())&& !soadEntity.getAuditStatus().equals(recEntity.getApproveStatus())) {
											//放入已发生改变单据集合
											hasChangedNosList.add(soadEntity.getSourceBillNo());
										} else {
											//从原始单据获取明细信息
											soadList.add(this.setStatementOfAccountDetailByReceivable(recEntity));
										}
									} else {
										//放入已发生改变单据集合
										hasChangedNosList.add(soadEntity.getSourceBillNo());
									}
								}
							}
						}
					}
				//判断该单号是否为应付单号	
				} else if (SettlementUtil.isPayable(soadEntity.getSourceBillNo())) {
					for (BillPayableEntity payEntity : payEntitys){
						// 校验应收单是否发生红冲或核销或空运其他应收单的审核状态发生了变化，则不保存明细
						if(payEntity.getPayableNo().equals(soadEntity.getSourceBillNo())){
							if(!payEntity.getVersionNo().equals(soadEntity.getVersionNo())){
								//放入已发生改变单据集合
								hasChangedNosList.add(soadEntity.getSourceBillNo());
							} else {
								// 如果发生核销则，两个实体的未核销金额不相等
								if (soadEntity.getUnverifyAmount().compareTo(payEntity.getUnverifyAmount()) != 0) {
									//放入已发生改变单据集合
									hasChangedNosList.add(soadEntity.getSourceBillNo());
								} else {
									if(payEntity.getBillType().equals(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR)
											||payEntity.getBillType().equals(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_ORIGINAL)
											||payEntity.getBillType().equals(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_DELIVERY)
											||payEntity.getBillType().equals(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_OTHER)
											||payEntity.getBillType().equals(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE_OTHER)
											||payEntity.getBillType().equals(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER)){
										//从原始单据获取明细信息
										soadList.add(this.setStatementOfAccountDetailByPayable(payEntity));
									}else{
										// 如果审核状态发生了变化,则不保存明细
										if (SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT.equals(payEntity.getApproveStatus())) {
											//放入已发生改变单据集合
											hasChangedNosList.add(soadEntity.getSourceBillNo());
										} else {
											//从原始单据获取明细信息
											soadList.add(this.setStatementOfAccountDetailByPayable(payEntity));
										}
									}
								}
							}
						}
					}
				//判断该单号是否为预收单号		
				} else if (SettlementUtil.isDepositReceived(soadEntity.getSourceBillNo())) {
					for (BillDepositReceivedEntity depEntity : depEntitys){
						// 校验应收单是否发生红冲或核销或空运其他应收单的审核状态发生了变化，则不保存明细
						if(depEntity.getDepositReceivedNo().equals(soadEntity.getSourceBillNo())){
							// 如果发生红冲则单据为无效状态，集合为空
							if(!depEntity.getVersionNo().equals(soadEntity.getVersionNo())){
								//放入已发生改变单据集合
								hasChangedNosList.add(soadEntity.getSourceBillNo());
							} else {
								// 如果发生核销则，两个实体的未核销金额不相等
								if (soadEntity.getUnverifyAmount().compareTo(depEntity.getUnverifyAmount()) != 0) {
									//放入已发生改变单据集合
									hasChangedNosList.add(soadEntity.getSourceBillNo());
								} else {
									//从原始单据获取明细信息
									soadList.add(this.setStatementOfAccountDetailByDepositReceived(depEntity));
								}
							}
						}
					}
					//判断该单号是否为预付单号			
				} else if (SettlementUtil.isAdvancedPayment(soadEntity.getSourceBillNo())) {
					for (BillAdvancedPaymentEntity advEntity : advEntitys){
						// 校验应收单是否发生红冲或核销或空运其他应收单的审核状态发生了变化，则不保存明细
						if(advEntity.getAdvancesNo().equals(soadEntity.getSourceBillNo())){
							// 如果发生红冲则单据为无效状态，实体为空
							if(!advEntity.getVersionNo().equals(soadEntity.getVersionNo())){
								//放入已发生改变单据集合
								hasChangedNosList.add(soadEntity.getSourceBillNo());
							} else {
								// 如果发生核销则，两个实体的未核销金额不相等
								if (soadEntity.getUnverifyAmount().compareTo(advEntity.getUnverifyAmount()) != 0) {
									//放入已发生改变单据集合
									hasChangedNosList.add(soadEntity.getSourceBillNo());
								} else {
									//从原始单据获取明细信息
									soadList.add(this.setStatementOfAccountDetailByAdvancedPayment(advEntity));
								}
							}
						}
					}
				}
			}
		}
		//记录日志
		logger.info("发生异常的单号集合条数为" + hasChangedNosList.size());
		//校验完毕，判断已发生改变单据集合是否为空
		if (CollectionUtils.isNotEmpty(hasChangedNosList)) {
			//生成储存变更号的buffer
			StringBuffer changedBillNos = new StringBuffer();
			//循环取出已发生改变单据信息
			for (int i = 0; i < hasChangedNosList.size(); i++) {
				//不是第一次
				if (i != 0) {
					//拼接,
					changedBillNos = changedBillNos.append(",");
				}
				//拼接变更的单号
				changedBillNos = changedBillNos.append(hasChangedNosList.get(i));
			}
			//抛出所有已发生改变的单据编号信息
			throw new SettlementException("对账单明细已经发生变化，不能继续生成对账单，请重新查询!,发生改变的明细单号为：" + changedBillNos);
		} else {
			// 如果对账单号不为空，则代表校验过过后，需要将对账单号修改到原始单据上（批量更新）
			if (StringUtil.isNotEmpty(statementNo)) {
				//将对账单号批量更新到原始单据上
				updateDetailBillStatementBillNo(recEntitys, payEntitys,
						depEntitys, advEntitys, statementNo, info);
			}
		}
		//记录日志
		logger.info("保存对账单时校验明细单据是否发生了变化,successfully exit service..");
		return soadList;
	}

	/**
	 * 重置与对账单明细相关联的应收单、应付单、预收单、预付单单据信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 下午3:38:55
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateDetailBillNoForRelease(List<String> list,String statementBillNo, CurrentInfo info) {
		//如果明细单号列表不为空
		if (CollectionUtils.isNotEmpty(list)) {
			//根据明细单号查询对账单明细对应的应收、应付、预收、预付等单据
			Map<Object, Object> map = statementOfAccountDService.queryBySourceBillNos(list);
			// 存放各种明细单据集合(应收)
			List<BillReceivableEntity> receivableEntityList = (List<BillReceivableEntity>) map.get("receivableEntityList");
			// 存放各种明细单据集合(应付)
			List<BillPayableEntity> payableEntityList = (List<BillPayableEntity>) map.get("payableEntityList");
			// 存放各种明细单据集合(预收)
			List<BillDepositReceivedEntity> depositReceivedEntityList = (List<BillDepositReceivedEntity>) map.get("depositReceivedEntityList");
			// 存放各种明细单据集合(预付)
			List<BillAdvancedPaymentEntity> advancedPaymentEntityList = (List<BillAdvancedPaymentEntity>) map.get("advancedPaymentEntityList");
			// 批量更新应收单、应付、预收、预付的对账单号
			updateDetailBillStatementBillNo(receivableEntityList,payableEntityList, depositReceivedEntityList,advancedPaymentEntityList,statementBillNo, info);
		}

	}

	/**
	 * 保存对账单时校验对账单明细统计金额和对账单信息的金额是否一致
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-2 下午1:55:49
	 */
	private void validatePeriodAmount(List<StatementOfAccountDEntity> periodList,
			StatementOfAccountEntity soaEntity){
		if (CollectionUtils.isNotEmpty(periodList)) {
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
			//循环取出本期明细信息
			for (int i = 0; i < periodList.size(); i++) {
				//获取当前对账单明细
				StatementOfAccountDEntity statementOfAccountDEntity = periodList.get(i);
				// 如果为应收单
				if (statementOfAccountDEntity.getBillParentType().equals(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE)) {
					//设置本期应收发生金额
					periodRecAmount = periodRecAmount.add(statementOfAccountDEntity.getUnverifyAmount());
					// 如果为应付单
				} else if (statementOfAccountDEntity.getBillParentType().equals(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE)) {
					//设置本期应付发生金额
					periodPayAmount = periodPayAmount.add(statementOfAccountDEntity.getUnverifyAmount());
					// 如果为预收单
				} else if (statementOfAccountDEntity.getBillParentType().equals(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED)) {
					//设置本期预收发生金额
					periodPreAmount = periodPreAmount.add(statementOfAccountDEntity.getUnverifyAmount());
					// 如果为预付单
				} else if (statementOfAccountDEntity.getBillParentType().equals(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT)) {
					//设置本期预付发生金额
					periodAdvAmount = periodAdvAmount.add(statementOfAccountDEntity.getUnverifyAmount());
				}
			}
			
			// 本期发生金额=本期应收+本期预付-本期应付-本期预收
			periodAmount = periodRecAmount.add(periodAdvAmount).subtract(periodPayAmount).subtract(periodPreAmount);
			// 本期发生金额
			if (periodAmount.compareTo(soaEntity.getPeriodAmount()) != 0) {
				//提示对账单统计金额与对账单明细统计金额不符
				throw new SettlementException("对账单统计金额与对账单明细统计金额不符！");
			}
			// 本期预付金额
			else if (periodAdvAmount.compareTo(soaEntity.getPeriodAdvAmount()) != 0) {
				//提示对账单预付统计金额与对账单预付明细统计金额不符
				throw new SettlementException("对账单预付统计金额与对账单预付明细统计金额不符！");
			}
			// 本期应付金额
			else if (periodPayAmount.compareTo(soaEntity.getPeriodPayAmount()) != 0) {
				//提示对账单预付统计金额与对账单预付明细统计金额不符
				throw new SettlementException("对账单预付统计金额与对账单预付明细统计金额不符！");
			}
			// 本期预收金额
			else if (periodPreAmount.compareTo(soaEntity.getPeriodPreAmount()) != 0) {
				//提示对账单预收统计金额与对账单预收明细统计金额不符
				throw new SettlementException("对账单统预收计金额与对账单预收明细统计金额不符！");
			}
			// 本期应收金额
			else if (periodRecAmount.compareTo(soaEntity.getPeriodRecAmount()) != 0) {
				//提示对账单应收统计金额与对账单应收明细统计金额不符
				throw new SettlementException("对账单应收统计金额与对账单应收明细统计金额不符！");
			}
			logger.info("本期发生金额periodAmount:" + periodAmount);
			// 更新对账单的未核销发生金额
			soaEntity = statementOfAccountService.updatePeriodUnverifyAmountCommon(soaEntity);
		}
	}

	/**
	 * 生成对账单时，批量修改进入对账单的应收单、应付单、预收单、预付单明细的对账单号
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-1 上午10:04:08
	 */
	private void updateDetailBillStatementBillNo(
			List<BillReceivableEntity> receivableEntityList,//应收单
			List<BillPayableEntity> payableEntityList,//应付单
			List<BillDepositReceivedEntity> depositReceivedEntityList,//预收单
			List<BillAdvancedPaymentEntity> advancedPaymentEntityList,//预付单
			String statementNo, CurrentInfo currentInfo) {//对账单号、当前登录客户
		//对账单号不能为空
		if (StringUtil.isEmpty(statementNo)) {
			//提示对账单号为空，不能修改原始单据的对账单号
			throw new SettlementException("对账单号为空，不能修改原始单据的对账单号", "");
		}
		// 批量更新应收单
		if (CollectionUtils.isNotEmpty(receivableEntityList)) {
			// 应收单号个数小于等于1000个时，直接查询返回
			if (receivableEntityList.size() <= SettlementConstants.MAX_LIST_SIZE) {
				//生成应收单dto
				BillReceivableDto dto = new BillReceivableDto();
				//设置对账单号
				dto.setStatementBillNo(statementNo);
				//设置应收单参数
				dto.setBillReceivables(receivableEntityList);
				//批量设置/取消应收单的对账单号
				billReceivableService.batchUpdateByMakeStatement(dto, currentInfo);
			} 
			// 应收单号个数大于1000个时，分批查询
			else {
				List<BillReceivableEntity> recEntitys = new ArrayList<BillReceivableEntity>();
				for (int index = 0; index < receivableEntityList.size(); index++) {
					recEntitys.add(receivableEntityList.get(index));
					if (recEntitys.size() == SettlementConstants.MAX_LIST_SIZE) {
						//生成应收单dto
						BillReceivableDto dto = new BillReceivableDto();
						//设置对账单号
						dto.setStatementBillNo(statementNo);
						//设置应收单参数
						dto.setBillReceivables(recEntitys);
						//批量设置/取消应收单的对账单号
						billReceivableService.batchUpdateByMakeStatement(dto, currentInfo);
						recEntitys.clear();
					}
				}
				if (CollectionUtils.isNotEmpty(recEntitys)) {
					//生成应收单dto
					BillReceivableDto dto = new BillReceivableDto();
					//设置对账单号
					dto.setStatementBillNo(statementNo);
					//设置应收单参数
					dto.setBillReceivables(recEntitys);
					//批量设置/取消应收单的对账单号
					billReceivableService.batchUpdateByMakeStatement(dto, currentInfo);
				}
			}
		}
		// 批量更新应付单
		if (CollectionUtils.isNotEmpty(payableEntityList)) {
			// 应付单号个数小于等于1000个时，直接查询返回
			if (payableEntityList.size() <= SettlementConstants.MAX_LIST_SIZE) {
				//生成应付单dto
				BillPayableDto dto = new BillPayableDto();
				//设置对账单号
				dto.setStatementBillNo(statementNo);
				//设置应付单参数
				dto.setBillPayables(payableEntityList);
				//批量设置/取消应付单的对账单号
				billPayableService.batchUpdateByMakeStatement(dto, currentInfo);
			} 
			// 应付单号个数大于1000个时，分批查询
			else {
				List<BillPayableEntity> payEntitys = new ArrayList<BillPayableEntity>();
				for (int index = 0; index < payableEntityList.size(); index++) {
					payEntitys.add(payableEntityList.get(index));
					if (payEntitys.size() == SettlementConstants.MAX_LIST_SIZE) {
						//生成应付单dto
						BillPayableDto dto = new BillPayableDto();
						//设置对账单号
						dto.setStatementBillNo(statementNo);
						//设置应付单参数
						dto.setBillPayables(payEntitys);
						//批量设置/取消应付单的对账单号
						billPayableService.batchUpdateByMakeStatement(dto, currentInfo);
						payEntitys.clear();
					}
				}
				if (CollectionUtils.isNotEmpty(payEntitys)) {
					//生成应付单dto
					BillPayableDto dto = new BillPayableDto();
					//设置对账单号
					dto.setStatementBillNo(statementNo);
					//设置应付单参数
					dto.setBillPayables(payEntitys);
					//批量设置/取消应付单的对账单号
					billPayableService.batchUpdateByMakeStatement(dto, currentInfo);
				}
			}
		}
		// 批量更新预收单
		if (CollectionUtils.isNotEmpty(depositReceivedEntityList)) {
			// 应收单号个数小于等于1000个时，直接查询返回
			if (depositReceivedEntityList.size() <= SettlementConstants.MAX_LIST_SIZE) {
				//生成预收单dto
				BillDepositReceivedEntityDto dto = new BillDepositReceivedEntityDto();
				//设置对账单号
				dto.setStatementBillNo(statementNo);
				//设置预收单参数
				dto.setBillDepositReceivedEntityList(depositReceivedEntityList);
				//批量设置/取消预收单的对账单号
				billDepositReceivedService.batchUpdateBillDepositReceivedByMakeStatement(dto,currentInfo);
			} 
			// 应收单号个数大于1000个时，分批查询
			else {
				List<BillDepositReceivedEntity> depEntitys = new ArrayList<BillDepositReceivedEntity>();
				for (int index = 0; index < depositReceivedEntityList.size(); index++) {
					depEntitys.add(depositReceivedEntityList.get(index));
					if (depEntitys.size() == SettlementConstants.MAX_LIST_SIZE) {
						//生成应收单dto
						BillDepositReceivedEntityDto dto = new BillDepositReceivedEntityDto();
						//设置对账单号
						dto.setStatementBillNo(statementNo);
						//设置应收单参数
						dto.setBillDepositReceivedEntityList(depEntitys);
						//批量设置/取消应收单的对账单号
						billDepositReceivedService.batchUpdateBillDepositReceivedByMakeStatement(dto,currentInfo);
						depEntitys.clear();
					}
				}
				if (CollectionUtils.isNotEmpty(depEntitys)) {
					//生成应收单dto
					BillDepositReceivedEntityDto dto = new BillDepositReceivedEntityDto();
					//设置对账单号
					dto.setStatementBillNo(statementNo);
					//设置应收单参数
					dto.setBillDepositReceivedEntityList(depEntitys);
					//批量设置/取消应收单的对账单号
					billDepositReceivedService.batchUpdateBillDepositReceivedByMakeStatement(dto,currentInfo);
				}
			}
		}
		// 批量更新预付单
		if (CollectionUtils.isNotEmpty(advancedPaymentEntityList)) {	
			//生成预付单dto
			BillAdvancedPaymentEntityDto dto = new BillAdvancedPaymentEntityDto();
			//设置对账单号
			dto.setStatementBillNo(statementNo);
			//设置预付单参数
			dto.setBillAdvancedPayment(advancedPaymentEntityList);
			//批量设置/取消预付单的对账单号
			billAdvancedPaymentService.batchUpdateByMakeStatement(dto,currentInfo);
		}
	}

	/**
	 * 制作对账单时为dto赋值常量值
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-29 下午1:54:16
	 */
	private void setConstantsForStatementMake(StatementOfAccountMakeQueryDto queryDto) {

		// 公共常量
		// 有效
		queryDto.setActive(FossConstants.ACTIVE);
		// 默认单号N/A
		queryDto.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
		// 默认单号N/A
		queryDto.setPaymentBillNo(SettlementConstants.DEFAULT_BILL_NO);
		//已审核
		queryDto.setApproveStatus(FossConstants.YES);
		// 应收单查询条件常量
		List<String> productTypeList = new ArrayList<String>();
		// 空运应收
		queryDto.setrBillTypeAirOtherReceivable(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE);
		// 代收货款应收
		queryDto.setrBillTypeCODReceivable(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
		// 设置产品类型为"三级产品-精准空运"
		productTypeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT);
		//设置产品类型参数
		queryDto.setProductTypeList(productTypeList);
		// 应收单父类型
		queryDto.setYsBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE);
		// 审核状态
		queryDto.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE);
		// 应付单查询条件常量
		List<String> yfBillTypeList = new ArrayList<String>();
		// 代收货款应付
		yfBillTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		// 装卸费
		yfBillTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE);
		// 服务补救
		yfBillTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION);
		// 理赔应付单
		yfBillTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
		
		//邓大伟，2013-07-30，空运应付：航空公司运费、空运出发代理应付、空运到达代理应付、空运其他应付
		yfBillTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR);
		yfBillTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_ORIGINAL);
		yfBillTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_DELIVERY);
		yfBillTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_OTHER);
		yfBillTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE_OTHER);
		//2013-8-15   
		yfBillTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER);
		
		// 应付单子类型
		queryDto.setpBillTypeList(yfBillTypeList);
		// 生效状态
		queryDto.setEffectiveStatus(FossConstants.YES);
		// 支付状态
		queryDto.setPayStatus(FossConstants.NO);
		// 应付单父类型
		queryDto.setYfBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE);
		// 预收单查询条件常量*********************************
		// 预收单父类型
		queryDto.setUsBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED);
		// 预付单查询条件常量*********************************
		// 预付单审批状态
		queryDto.setAuditStatus(SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDIT_AGREE);
		// 预付单父类型
		queryDto.setUfBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT);
		// 付款单付款状态(未付款、付款中)
		List<String> remitStatusList = new ArrayList<String>();
		//未付款
		remitStatusList.add(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER);
		//付款中
		remitStatusList.add(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING);
		//设置付款状态参数
		queryDto.setRemitStatusList(remitStatusList);
	}

	/**
	 * 按单号查询时，为queryDto设置界面传入的单号集合属性
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-31 下午5:37:58
	 */
	private void setBillNosForQueryDto(StatementOfAccountMakeQueryDto queryDto) {
		// 调用单号公共处理方法，返回map集合
		Map<String, List<String>> resultMap = SettlementUtil.convertBillNos(queryDto.getBillDetailNos());
		// 判断map中是否有应收单号集合
		if (CollectionUtils.isNotEmpty(resultMap.get(SettlementConstants.BILL_PREFIX_YS))) {
			//设置应收单号
			queryDto.setReceivableBillNosList(resultMap.get(SettlementConstants.BILL_PREFIX_YS));
		}
		// 判断map中是否有应付单号集合
		if (CollectionUtils.isNotEmpty(resultMap.get(SettlementConstants.BILL_PREFIX_YF))) {
			//设置应付单号
			queryDto.setPayableBillNosList(resultMap.get(SettlementConstants.BILL_PREFIX_YF));
		}
		// 判断map中是否有预收单号集合
		if (CollectionUtils.isNotEmpty(resultMap.get(SettlementConstants.BILL_PREFIX_US))) {
			//设置预收单号
			queryDto.setDepositReceivedBillNosList(resultMap.get(SettlementConstants.BILL_PREFIX_US));
		}
		// 判断map中是否有预付单号集合
		if (CollectionUtils.isNotEmpty(resultMap.get(SettlementConstants.BILL_PREFIX_UF))) {
			//设置预付单号
			queryDto.setAdvancedPaymentBillNosList(resultMap.get(SettlementConstants.BILL_PREFIX_UF));
		}
		// 判断map中是否有运单号或小票单号集合
		if (CollectionUtils.isNotEmpty(resultMap.get(SettlementConstants.BILL_PREFIX_LY))) {
			//设置来源单号
			queryDto.setSourceBillNosList(resultMap.get(SettlementConstants.BILL_PREFIX_LY));
		}

	}

	/**
	 * 按单号查询本期对账单明细数据
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-31 下午5:40:49
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> queryPeriodDetailByBillNos(StatementOfAccountMakeQueryDto queryDto,List<StatementOfAccountDEntity> periodList) {
		// 将账期设为本期
		queryDto.setPeriodType(SettlementWriteoffConstants.STATEMENT_PERIOD_TYPE);
		// 应收单号
		if (CollectionUtils.isNotEmpty(queryDto.getReceivableBillNosList()) && queryDto.getIsReceivable()) {
			//增加应收单明细
			periodList.addAll(statementMakeDao.queryYSMakeSOAByReceivabeNos(queryDto));
		}
		// 应付单号
		if (CollectionUtils.isNotEmpty(queryDto.getPayableBillNosList()) && queryDto.getIsPayable()) {
			//增加应付单明细
			periodList.addAll(statementMakeDao.queryYFMakeSOAByPayableNos(queryDto));
			//邓大伟，2013-07-30， 查询空运应付单信息
			queryDto.getpBillTypeList().clear();
			queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR);
			queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_ORIGINAL);
			queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_DELIVERY);
			queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_OTHER);
			queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE_OTHER);
			queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER);
			periodList.addAll(statementMakeDao.queryYFKYMakeSOAByPayableNos(queryDto));
		}
		// 预收单号
		if (CollectionUtils.isNotEmpty(queryDto.getDepositReceivedBillNosList())&& queryDto.getIsDepositReceived()) {
			//增加预收单明细
			periodList.addAll(statementMakeDao.queryUSMakeSOAByReceivedNos(queryDto));
		}
		// 预付单号
		if (CollectionUtils.isNotEmpty(queryDto.getAdvancedPaymentBillNosList()) && queryDto.getIsAdvancedPayment()) {
			//增加预付单明细
			periodList.addAll(statementMakeDao.queryUFMakeSOAByAdvancedNos(queryDto));
		}
		// 来源单号（运单或小票单号）
		if (CollectionUtils.isNotEmpty(queryDto.getSourceBillNosList())) {
			if(queryDto.getIsReceivable()){
				// 查询应收单信息
				periodList = (List<StatementOfAccountDEntity>) CollectionUtils.union(periodList, statementMakeDao.queryYSMakeSOAByWaybillNos(queryDto));
			}
			if(queryDto.getIsPayable()){
				// 查询应付单信息
				periodList = (List<StatementOfAccountDEntity>) CollectionUtils.union(periodList, statementMakeDao.queryYFMakeSOAByWaybillNos(queryDto));
				//邓大伟，2013-07-30， 查询空运应付单信息
				queryDto.getpBillTypeList().clear();
				queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR);
				queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_ORIGINAL);
				queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_DELIVERY);
				queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_OTHER);
				queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE_OTHER);
				queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER);
				if(null != periodList && periodList.size() > 0){
					queryDto.setCustomerCode(periodList.get(0).getCustomerCode());
				}
				periodList = (List<StatementOfAccountDEntity>) CollectionUtils.union(periodList, statementMakeDao.queryYFKYMakeSOAByWaybillNos(queryDto));
			}
		}
		/*
		 * 校验用户查询的对账单明细是否为同一客户，如果客户编码不同，则抛出异常
		 */
		Iterator<StatementOfAccountDEntity> it = periodList.iterator();
		//生成客户编码和名称
		String customerCode = null;
		String customerName = null;
		//存在
		while (it.hasNext()) {
			//获取当前对账单明细
			StatementOfAccountDEntity entity = it.next();
			//如果客户编码为空
			if (StringUtil.isEmpty(customerCode)) {
				//设置为当前对账单明细的客户编码
				customerCode = entity.getCustomerCode();
				customerName = entity.getCustomerName();
			} else {
				//如果对账单明细客户编码不等于当前客户编码
				if (!customerCode.equals(entity.getCustomerCode())) {
					//提示所输入的单号的客户编码不同，请检查后重新输入
					throw new SettlementException("所输入的单号的客户编码不同，请检查后重新输入！");
				}
			}
		}
		// 如果本期明细信息为空，则抛出异常
		if (CollectionUtils.isEmpty(periodList)) {
			//提示所输入的单号不存在符合条件的明细单据，请重新输入其他单号信息
			throw new SettlementException("所输入的单号不存在符合条件的明细单据，请重新输入其他单号信息！");
		}
		//生成返回结果map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//设置本期对账单明细
		resultMap.put(SettlementWriteoffConstants.periodList, periodList);
		//设置客户编码和名称
		resultMap.put(SettlementWriteoffConstants.customerCode, customerCode);
		resultMap.put(SettlementWriteoffConstants.customerName, customerName);
		//返回结果map
		return resultMap;
	}
	
	/**
	 * 
	 * 按航空正单开始结束单号查询本期对账单明细
	 * @author foss-wangxuemin
	 * @date May 27, 2013 11:10:12 AM
	 */
	@SuppressWarnings("unchecked")
	private List<StatementOfAccountDEntity> queryPeriodDetailByAirWaybillNo(StatementOfAccountMakeQueryDto queryDto) {
		// 航空公司或者代理编码不能为空
		if(StringUtils.isEmpty(queryDto.getCustomerCode())){
			throw new SettlementException("航空公司或者代理编码为空，无法查询");
		}
		String startNumberStr = queryDto.getStartNumber();
		String endNumberStr = queryDto.getEndNumber();
		// 正单开始编号不能为空
		if(StringUtils.isEmpty(startNumberStr)){
			throw new SettlementException("开始正单号为空，无法查询");
		}
		// 正单结束编号不能为空
		if(StringUtils.isEmpty(endNumberStr)){
			throw new SettlementException("结束正单号为空，无法查询");
		}
		// 查询的开始结束正单号长度必须相等
		if(startNumberStr.length() != endNumberStr.length()){
			throw new SettlementException("开始正单号和结束正单号长度不相等，无法查询");
		}
		// 如果单号为"DP"开头，则开始和结束单号都必须以"DP"开头，否则都为数字
		if ((startNumberStr.startsWith(SettlementConstants.AIR_WAYBILL_NO_PREFIX) && !endNumberStr.startsWith(SettlementConstants.AIR_WAYBILL_NO_PREFIX))
				|| (!startNumberStr.startsWith(SettlementConstants.AIR_WAYBILL_NO_PREFIX) && endNumberStr.startsWith(SettlementConstants.AIR_WAYBILL_NO_PREFIX))) {
			throw new SettlementException("开始正单号和结束正单号规则不匹配，不是同一种类型的单号，无法查询");
		}
		// 输入单号长度
		boolean isDPStart = false;
		int numberLength = 0;
		long startNumber = 0;
		long endNumber = 0;
		if (startNumberStr.startsWith(SettlementConstants.AIR_WAYBILL_NO_PREFIX)) {
			isDPStart = true;
			numberLength = startNumberStr.length() - 2;
			startNumber = Long.parseLong(startNumberStr.substring(3));
			endNumber = Long.parseLong(endNumberStr.substring(3));
		} else {
			numberLength = endNumberStr.length();
			startNumber = Long.parseLong(startNumberStr);
			endNumber = Long.parseLong(endNumberStr);
		}
		// 查询的正单号不能查过1000个
		if (endNumber - startNumber < 0){
			throw new SettlementException("开始正单号不能大于结束正单号！");
		}
		if (endNumber
				- startNumber >= SettlementConstants.MAX_AIR_WAYBILL_NO_SIZE) {
			throw new SettlementException("开始正单号和结束正单号之间包含的单号个数超过查询最大限制个数"
					+ SettlementConstants.MAX_AIR_WAYBILL_NO_SIZE + "，无法查询");
		}
		List<String> airWaybillNos = new ArrayList<String>();
		// 生成查询所需的正单号列表
		for (long i = startNumber; i <= endNumber; i++) {
			if (isDPStart) {
				airWaybillNos.add(SettlementConstants.AIR_WAYBILL_NO_PREFIX
						+ String.format("%0" + numberLength + "d", i));
			} else {
				airWaybillNos.add(String.format("%0" + numberLength + "d", i));
			}
		}
		// 设置查询的张单号列表
		queryDto.setAirWaybillNos(airWaybillNos);
		
		List<StatementOfAccountDEntity> periodList = new ArrayList<StatementOfAccountDEntity>();
		
		// 将账期设为本期
		queryDto.setPeriodType(SettlementWriteoffConstants.STATEMENT_PERIOD_TYPE);
		
		// 查询应收单信息
		periodList = (List<StatementOfAccountDEntity>) CollectionUtils.union(
				periodList,
				statementMakeDao.queryYSMakeSOAByAirWaybillBeginAndEndNo(queryDto));
		// 查询应付单信息
		periodList = (List<StatementOfAccountDEntity>) CollectionUtils.union(
				periodList,
				statementMakeDao.queryYFMakeSOAByAirWaybillBeginAndEndNo(queryDto));
		
		//邓大伟，2013-07-30， 查询空运应付单信息
		queryDto.getpBillTypeList().clear();
		queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR);
		queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_ORIGINAL);
		queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_DELIVERY);
		queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_OTHER);
		queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE_OTHER);
		queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER);
		periodList = (List<StatementOfAccountDEntity>) CollectionUtils.union(
				periodList,
				statementMakeDao.queryYFKYMakeSOAByAirWaybillBeginAndEndNo(queryDto));

		// 如果本期明细信息为空，则抛出异常
		if (CollectionUtils.isEmpty(periodList)) {
			//提示所输入的单号不存在符合条件的明细单据，请重新输入其他单号信息
			throw new SettlementException("所输入的航空正单号不存在符合条件的明细单据，请重新输入其他单号信息！");
		}
		//返回结果
		return periodList;
	}

	/**
	 * 按账期查询期初本期对账单明细数据
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-31 下午5:40:49
	 */
	private Map<String, Object> queryDetailByBillDate(StatementOfAccountMakeQueryDto queryDto,List<StatementOfAccountDEntity> beginPeriodList,List<StatementOfAccountDEntity> periodList) {
		
		// 根据前台用户选择的单据子类型分别查询对账单本期应收、应付、预收、预付信息
		// 设置对账单的期次，获取对账单本期明细数据集合
		queryDto.setPeriodType(SettlementWriteoffConstants.STATEMENT_PERIOD_TYPE);
		// 查询应收单信息
		if (queryDto.getIsReceivable()) {
			int countSum = statementMakeDao.queryYSCountByParms(queryDto);
			if(SELECTPARAM < countSum){
				throw new SettlementException("本期应收单总数超过3000条，数据量太大，请分期制作或走大客户对账单新增制作！");
			}
			//加入应收单
			periodList.addAll(statementMakeDao.queryYSMakeSOAByParams(queryDto));
		}
		// 查询应付单信息
		if (queryDto.getIsPayable()) {
			//加入应付单
			periodList.addAll(statementMakeDao.queryYFMakeSOAByParams(queryDto));
			//邓大伟，2013-07-30，添加空运应付：航空公司运费、空运出发代理应付、空运到达代理应付、空运其他应付
			queryDto.getpBillTypeList().clear();
			queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR);
			queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_ORIGINAL);
			queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_DELIVERY);
			queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_OTHER);
			queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE_OTHER);
			queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER);
			periodList.addAll(statementMakeDao.queryYFKYMakeSOAByParams(queryDto));
		}
		// 查询预收单信息
		if (queryDto.getIsDepositReceived()) {
			//加入预收单
			periodList.addAll(statementMakeDao.queryUSMakeSOAByParams(queryDto));
		}
		// 查询预付单信息
		if (queryDto.getIsAdvancedPayment()) {
			//加入预付单
			periodList.addAll(statementMakeDao.queryUFMakeSOAByParams(queryDto));
		}

		// 根据前台用户选择的单据子类型分别查询对账单期初应收、应付、预收、预付信息
		// 将对账单的期次置为空，获取对账单期初明细数据集合
		if (!FossConstants.NO.equals(queryDto.getQueryBeginPriodFlag())) {
			//设置本期类型为空
			queryDto.setPeriodType(null);
			// 查询应收单信息
			if (queryDto.getIsReceivable()) {
				int countSum = statementMakeDao.queryYSCountByParms(queryDto);
				if(SELECTPARAM < countSum){
					throw new SettlementException("期初应收单总数超过3000条，数据量太大，请走大客户对账单新增制作！");
				}
				//加入应收单
				beginPeriodList.addAll(statementMakeDao.queryYSMakeSOAByParams(queryDto));
			}
			// 查询应付单信息
			if (queryDto.getIsPayable()) {
				//邓大伟，2013-07-30，应付单据子类型： 代收货款应付、 装卸费、 服务补救、 理赔应付单
				queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
				queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE);
				queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION);
				queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
				//加入应付单
				beginPeriodList.addAll(statementMakeDao.queryYFMakeSOAByParams(queryDto));
				//邓大伟，2013-07-30，添加空运应付：航空公司运费、空运出发代理应付、空运到达代理应付、空运其他应付
				queryDto.getpBillTypeList().clear();
				queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR);
				queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_ORIGINAL);
				queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_DELIVERY);
				queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_OTHER);
				queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE_OTHER);
				queryDto.getpBillTypeList().add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER);
				beginPeriodList.addAll(statementMakeDao.queryYFKYMakeSOAByParams(queryDto));
				
			}
			// 查询预收单信息
			if (queryDto.getIsDepositReceived()) {
				//加入预收单
				beginPeriodList.addAll(statementMakeDao.queryUSMakeSOAByParams(queryDto));
			}
			// 查询预付单信息
			if (queryDto.getIsAdvancedPayment()) {
				//加入预付单
				beginPeriodList.addAll(statementMakeDao.queryUFMakeSOAByParams(queryDto));
			}
		}
		//生成返回map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//设置查询dto
		resultMap.put("queryDto", queryDto);
		//设置期初
		resultMap.put(SettlementWriteoffConstants.beginPeriodList,beginPeriodList);
		//设置期末
		resultMap.put(SettlementWriteoffConstants.periodList, periodList);
		//返回map
		return resultMap;
	}

	/**
	 * 计算对账单期初发生金额信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-31 下午5:40:49
	 */
	private Map<String, Object> countBeginPeriodAmount(List<StatementOfAccountDEntity> beginPeriodList,StatementOfAccountEntity soaEntity) {
		/*
		 * 获取期初发生金额统计信息
		 */
		if (CollectionUtils.isNotEmpty(beginPeriodList)) {
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
			//循环期初明细信息
			for (int i = 0; i < beginPeriodList.size(); i++) {
				//获取当前对账单明细
				StatementOfAccountDEntity statementOfAccountDEntity = beginPeriodList.get(i);
				// 如果为应收单
				if (statementOfAccountDEntity.getBillParentType().equals(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE)) {
					//增加期初应收金额
					beginPeriodRecAmount = beginPeriodRecAmount.add(statementOfAccountDEntity.getUnverifyAmount());
				}
				// 如果为应付单
				else if (statementOfAccountDEntity.getBillParentType().equals(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE)) {
					//增加期初应付金额
					beginPeriodPayAmount = beginPeriodPayAmount.add(statementOfAccountDEntity.getUnverifyAmount());
				}
				// 如果为预收单
				else if (statementOfAccountDEntity.getBillParentType().equals(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED)) {
					//增加期初预收金额
					beginPeriodPreAmount = beginPeriodPreAmount.add(statementOfAccountDEntity.getUnverifyAmount());
				}
				// 如果为预付单
				else if (statementOfAccountDEntity.getBillParentType().equals(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT)) {
					//增加期初预付金额
					beginPeriodAdvAmount = beginPeriodAdvAmount.add(statementOfAccountDEntity.getUnverifyAmount());
				}
			}
			// 期初发生金额=期初应收+期初预付-期初应付-期初预付
			beginPeriodAmount = beginPeriodRecAmount.add(beginPeriodAdvAmount).subtract(beginPeriodPayAmount).subtract(beginPeriodPreAmount);
			//设置期初发生金额
			soaEntity.setBeginPeriodAmount(beginPeriodAmount);
			//设置期初应收发生金额
			soaEntity.setBeginPeriodRecAmount(beginPeriodRecAmount);
			//设置期初应付发生金额
			soaEntity.setBeginPeriodPayAmount(beginPeriodPayAmount);
			//设置期初预收发生金额
			soaEntity.setBeginPeriodPreAmount(beginPeriodPreAmount);
			//设置期初预付发生金额
			soaEntity.setBeginPeriodAdvAmount(beginPeriodAdvAmount);
		} else {
			//设置金额默认为0
			soaEntity.setBeginPeriodAmount(BigDecimal.ZERO);	//设置金额默认为0
			soaEntity.setBeginPeriodRecAmount(BigDecimal.ZERO);	//设置金额默认为0
			soaEntity.setBeginPeriodPayAmount(BigDecimal.ZERO);	//设置金额默认为0
			soaEntity.setBeginPeriodPreAmount(BigDecimal.ZERO);	//设置金额默认为0
			soaEntity.setBeginPeriodAdvAmount(BigDecimal.ZERO);	//设置金额默认为0
		}
		//记录日志
		logger.info("期初发生金额beginPeriodAmount:"+ soaEntity.getBeginPeriodAmount());
		//生成返回map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//设置期初
		resultMap.put(SettlementWriteoffConstants.beginPeriodList,beginPeriodList);
		//设置期末
		resultMap.put(SettlementWriteoffConstants.soaEntity, soaEntity);
		//返回map
		return resultMap;
	}

	/**
	 * 计算对账单本期发生金额、本期剩余发生金额信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-31 下午5:40:49
	 */
	private void countPeriodAmount(
			List<StatementOfAccountDEntity> periodList,
			StatementOfAccountEntity soaEntity) {
		if (CollectionUtils.isNotEmpty(periodList)) {
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
			//循环取出本期明细信息
			for (int i = 0; i < periodList.size(); i++) {
				//获取当前对账单明细
				StatementOfAccountDEntity statementOfAccountDEntity = periodList.get(i);
				// 如果为应收单
				if (statementOfAccountDEntity.getBillParentType().equals(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE)) {
					//设置本期应收发生金额
					periodRecAmount = periodRecAmount.add(statementOfAccountDEntity.getUnverifyAmount());
					// 如果为应付单
				} else if (statementOfAccountDEntity.getBillParentType().equals(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE)) {
					//设置本期应付发生金额
					periodPayAmount = periodPayAmount.add(statementOfAccountDEntity.getUnverifyAmount());
					// 如果为预收单
				} else if (statementOfAccountDEntity.getBillParentType().equals(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED)) {
					//设置本期预收发生金额
					periodPreAmount = periodPreAmount.add(statementOfAccountDEntity.getUnverifyAmount());
					// 如果为预付单
				} else if (statementOfAccountDEntity.getBillParentType().equals(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT)) {
					//设置本期预付发生金额
					periodAdvAmount = periodAdvAmount.add(statementOfAccountDEntity.getUnverifyAmount());
				}
			}
			// 本期发生金额=本期应收+本期预付-本期应付-本期预收
			periodAmount = periodRecAmount.add(periodAdvAmount).subtract(periodPayAmount).subtract(periodPreAmount);
			//设置本期应收金额
			soaEntity.setPeriodRecAmount(periodRecAmount);
			//设置本期应付金额
			soaEntity.setPeriodPayAmount(periodPayAmount);
			//设置本期预收金额
			soaEntity.setPeriodPreAmount(periodPreAmount);
			//设置本期预付金额
			soaEntity.setPeriodAdvAmount(periodAdvAmount);
			//设置本期发生金额
			soaEntity.setPeriodAmount(periodAmount);
			logger.info("本期发生金额periodAmount:" + periodAmount);
			// 更新对账单的未核销发生金额
			soaEntity = statementOfAccountService.updatePeriodUnverifyAmountCommon(soaEntity);
		} else {
			soaEntity.setPeriodAmount(BigDecimal.ZERO);//设置金额默认为0
			soaEntity.setPeriodRecAmount(BigDecimal.ZERO);//设置金额默认为0
			soaEntity.setPeriodPayAmount(BigDecimal.ZERO);//设置金额默认为0
			soaEntity.setPeriodPreAmount(BigDecimal.ZERO);//设置金额默认为0
			soaEntity.setPeriodAdvAmount(BigDecimal.ZERO);//设置金额默认为0
			soaEntity.setPeriodUnverifyRecAmount(BigDecimal.ZERO);//设置金额默认为0
			soaEntity.setPeriodUnverifyPayAmount(BigDecimal.ZERO);//设置金额默认为0
			soaEntity.setPeriodUnverifyPreAmount(BigDecimal.ZERO);//设置金额默认为0
			soaEntity.setPeriodUnverifyAdvAmount(BigDecimal.ZERO);//设置金额默认为0
		}
	}

	/**
	 * 计算对账单的本期未还金额
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-28 下午1:00:17
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementMakeService#updateUnpaidAmount(com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity)
	 */
	@Override
	public StatementOfAccountEntity updateUnpaidAmount(StatementOfAccountEntity entity) {
		// 如果本期发生金额大于0，则本期剩余未还金额等于本期剩余应收金额
		if (entity.getPeriodAmount().compareTo(BigDecimal.ZERO) > 0) {
			//设置未还款金额
			entity.setUnpaidAmount(entity.getPeriodUnverifyRecAmount());
		} else {
			// 如果本期发生金额小于等于0，则本期剩余未还金额等于本期发生金额
			entity.setUnpaidAmount(entity.getPeriodAmount());
		}
		//返回对账单
		return entity;
	}
	
	@Override
	public List<StatementOfAccountEntity> queryStatementBIllEntity(StatementOfAccountMakeQueryDto statementQueryDto) {
		List<StatementOfAccountEntity> statementBillList = statementMakeDao.queryStatementBIllEntity(statementQueryDto);
		return statementBillList;
	}
	
	/**
	 * 
	 * 复制应收单属性到对账单明细
	 * @author foss-wangxuemin
	 * @date Apr 17, 2013 4:46:23 PM
	 */
	private StatementOfAccountDEntity setStatementOfAccountDetailByReceivable(BillReceivableEntity recEntity) {
		StatementOfAccountDEntity soadEntity = new StatementOfAccountDEntity();
		soadEntity.setBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE);
		soadEntity.setSourceBillNo(recEntity.getReceivableNo());
		soadEntity.setId(recEntity.getId());
		soadEntity.setStatementBillNo(null);
		soadEntity.setWaybillNo(recEntity.getWaybillNo());
		soadEntity.setOrigSourceBillNo(recEntity.getSourceBillNo());
		soadEntity.setBillType(recEntity.getBillType());
		soadEntity.setAmount(recEntity.getAmount());
		soadEntity.setVerifyAmount(recEntity.getVerifyAmount());
		soadEntity.setAuditStatus(recEntity.getApproveStatus());
		soadEntity.setUnverifyAmount(recEntity.getUnverifyAmount());
		soadEntity.setOrgCode(recEntity.getDunningOrgCode());
		soadEntity.setOrgName(recEntity.getDunningOrgName());
		soadEntity.setOrigOrgCode(recEntity.getOrigOrgCode());
		soadEntity.setOrigOrgName(recEntity.getOrigOrgName());
		soadEntity.setDestOrgCode(recEntity.getDestOrgCode());
		soadEntity.setDestOrgName(recEntity.getDestOrgName());
		soadEntity.setCustomerCode(recEntity.getCustomerCode());
		soadEntity.setCustomerName(recEntity.getCustomerName());
		soadEntity.setDeptRegionCode(null);
		soadEntity.setArrvRegionCode(recEntity.getTargetOrgCode());
		soadEntity.setCustomerPickupOrgName(recEntity.getCustomerPickupOrgCode());
		if (null != recEntity.getGoodsQtyTotal()) {
			soadEntity.setQty(recEntity.getGoodsQtyTotal().intValue());
		} else {
			soadEntity.setQty(null);
		}
		soadEntity.setBillingVolume(recEntity.getGoodsVolumeTotal());
		soadEntity.setBillWeight(recEntity.getBillWeight());
		soadEntity.setGoodsName(recEntity.getGoodsName());
		soadEntity.setDeliveryCustomerCode(recEntity.getDeliveryCustomerCode());
		soadEntity.setDeliveryCustomerName(recEntity.getDeliveryCustomerName());
		soadEntity.setReceiveCustomerCode(recEntity.getReceiveCustomerCode());
		soadEntity.setReceiveCustomerName(recEntity.getReceiveCustomerName());
		soadEntity.setPaymentType(recEntity.getPaymentType());
		soadEntity.setReceiveMethod(recEntity.getReceiveMethod());
		soadEntity.setProductCode(recEntity.getProductCode());
		soadEntity.setInsuranceFee(recEntity.getInsuranceFee());
		soadEntity.setTransportFee(recEntity.getTransportFee());
		soadEntity.setPackagingFee(recEntity.getPackagingFee());
		soadEntity.setDeliverFee(recEntity.getDeliveryGoodsFee());
		soadEntity.setCodFee(recEntity.getCodFee());
		soadEntity.setOtherFee(recEntity.getOtherFee());
		soadEntity.setValueAddFee(recEntity.getValueAddFee());
		soadEntity.setPromotionsFee(recEntity.getPromotionsFee());
		soadEntity.setPickupFee(recEntity.getPickupFee());
		soadEntity.setIsDelete(null);
		soadEntity.setDisableTime(null);
		soadEntity.setBusinessDate(recEntity.getBusinessDate());
		soadEntity.setAccountDate(recEntity.getAccountDate());
		soadEntity.setSignDate(recEntity.getConrevenDate());
		soadEntity.setCreateTime(recEntity.getCreateTime());
		soadEntity.setNotes(recEntity.getNotes());
		//邓大伟，2013-07-22，添加费率、保价、发货联系人三个字段
		soadEntity.setUnitPrice(recEntity.getUnitPrice());
		soadEntity.setInsuranceAmount(recEntity.getInsuranceAmount());
		soadEntity.setDeliveryCustomerContact(recEntity.getDeliveryCustomerContact());
		return soadEntity;
	}

	/**
	 * 
	 * 复制应付单属性到对账单明细
	 * @author foss-wangxuemin
	 * @date Apr 17, 2013 4:46:07 PM
	 */
	private StatementOfAccountDEntity setStatementOfAccountDetailByPayable(BillPayableEntity payEntity) {
		StatementOfAccountDEntity soadEntity = new StatementOfAccountDEntity();
		soadEntity.setBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE);
		soadEntity.setSourceBillNo(payEntity.getPayableNo());
		soadEntity.setId(payEntity.getId());
		soadEntity.setStatementBillNo(null);
		soadEntity.setWaybillNo(payEntity.getWaybillNo());
		soadEntity.setOrigSourceBillNo(payEntity.getSourceBillNo());
		soadEntity.setBillType(payEntity.getBillType());
		soadEntity.setAmount(payEntity.getAmount());
		soadEntity.setVerifyAmount(payEntity.getVerifyAmount());
		soadEntity.setAuditStatus(payEntity.getApproveStatus());
		soadEntity.setUnverifyAmount(payEntity.getUnverifyAmount());
		soadEntity.setOrgCode(payEntity.getPayableOrgCode());
		soadEntity.setOrgName(payEntity.getPayableOrgName());
		soadEntity.setOrigOrgCode(payEntity.getOrigOrgCode());
		soadEntity.setOrigOrgName(payEntity.getOrigOrgName());
		soadEntity.setDestOrgCode(payEntity.getDestOrgCode());
		soadEntity.setDestOrgName(payEntity.getDestOrgName());
		soadEntity.setCustomerCode(payEntity.getCustomerCode());
		soadEntity.setCustomerName(payEntity.getCustomerName());
		soadEntity.setDeptRegionCode(null);
		soadEntity.setArrvRegionCode(null);
		soadEntity.setCustomerPickupOrgName(null);
		soadEntity.setQty(null);
		soadEntity.setBillingVolume(null);
		soadEntity.setBillWeight(null);
		soadEntity.setGoodsName(null);
		soadEntity.setDeliveryCustomerCode(null);
		soadEntity.setDeliveryCustomerName(null);
		soadEntity.setReceiveCustomerCode(null);
		soadEntity.setReceiveCustomerName(null);
		soadEntity.setPaymentType(null);
		soadEntity.setReceiveMethod(null);
		soadEntity.setProductCode(payEntity.getProductCode());
		soadEntity.setInsuranceFee(null);
		soadEntity.setTransportFee(null);
		soadEntity.setPackagingFee(null);
		soadEntity.setDeliverFee(null);
		soadEntity.setCodFee(null);
		soadEntity.setOtherFee(null);
		soadEntity.setValueAddFee(null);
		soadEntity.setPromotionsFee(null);
		soadEntity.setPickupFee(null);
		soadEntity.setIsDelete(null);
		soadEntity.setDisableTime(null);
		soadEntity.setBusinessDate(payEntity.getBusinessDate());
		soadEntity.setAccountDate(payEntity.getAccountDate());
		soadEntity.setSignDate(payEntity.getSignDate());
		soadEntity.setCreateTime(payEntity.getCreateTime());
		soadEntity.setNotes(payEntity.getNotes());
		return soadEntity;
	}

	/**
	 * 
	 * 复制预收单属性到对账单明细
	 * @author foss-wangxuemin
	 * @date Apr 17, 2013 4:45:49 PM
	 */
	private StatementOfAccountDEntity setStatementOfAccountDetailByDepositReceived(
			BillDepositReceivedEntity depEntity) {
		StatementOfAccountDEntity soadEntity = new StatementOfAccountDEntity();
		soadEntity.setBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED);
		soadEntity.setSourceBillNo(depEntity.getDepositReceivedNo());
		soadEntity.setId(depEntity.getId());
		soadEntity.setStatementBillNo(null);
		soadEntity.setWaybillNo(null);
		soadEntity.setOrigSourceBillNo(null);
		soadEntity.setBillType(depEntity.getBillType());
		soadEntity.setAmount(depEntity.getAmount());
		soadEntity.setVerifyAmount(depEntity.getVerifyAmount());
		soadEntity.setAuditStatus(null);
		soadEntity.setUnverifyAmount(depEntity.getUnverifyAmount());
		soadEntity.setOrgCode(depEntity.getGeneratingOrgCode());
		soadEntity.setOrgName(depEntity.getGeneratingOrgName());
		soadEntity.setOrigOrgCode(null);
		soadEntity.setOrigOrgName(null);
		soadEntity.setDestOrgCode(null);
		soadEntity.setDestOrgName(null);
		soadEntity.setCustomerCode(depEntity.getCustomerCode());
		soadEntity.setCustomerName(depEntity.getCustomerName());
		soadEntity.setDeptRegionCode(null);
		soadEntity.setArrvRegionCode(null);
		soadEntity.setCustomerPickupOrgName(null);
		soadEntity.setQty(null);
		soadEntity.setBillingVolume(null);
		soadEntity.setBillWeight(null);
		soadEntity.setGoodsName(null);
		soadEntity.setDeliveryCustomerCode(null);
		soadEntity.setDeliveryCustomerName(null);
		soadEntity.setReceiveCustomerCode(null);
		soadEntity.setReceiveCustomerName(null);
		soadEntity.setPaymentType(depEntity.getPaymentType());
		soadEntity.setReceiveMethod(null);
		soadEntity.setProductCode(null);
		soadEntity.setInsuranceFee(null);
		soadEntity.setTransportFee(null);
		soadEntity.setPackagingFee(null);
		soadEntity.setDeliverFee(null);
		soadEntity.setCodFee(null);
		soadEntity.setOtherFee(null);
		soadEntity.setValueAddFee(null);
		soadEntity.setPromotionsFee(null);
		soadEntity.setPickupFee(null);
		soadEntity.setIsDelete(null);
		soadEntity.setDisableTime(null);
		soadEntity.setBusinessDate(depEntity.getBusinessDate());
		soadEntity.setAccountDate(depEntity.getAccountDate());
		soadEntity.setSignDate(null);
		soadEntity.setCreateTime(depEntity.getCreateTime());
		soadEntity.setNotes(depEntity.getNotes());
		return soadEntity;
	}

	/**
	 * 
	 * 复制预付单属性到对账单明细
	 * @author foss-wangxuemin
	 * @date Apr 17, 2013 4:44:28 PM
	 */
	private StatementOfAccountDEntity setStatementOfAccountDetailByAdvancedPayment(
			BillAdvancedPaymentEntity advEntity) {
		StatementOfAccountDEntity soadEntity = new StatementOfAccountDEntity();
		soadEntity.setBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT);
		soadEntity.setSourceBillNo(advEntity.getAdvancesNo());
		soadEntity.setId(advEntity.getId());
		soadEntity.setStatementBillNo(null);
		soadEntity.setWaybillNo(null);
		soadEntity.setOrigSourceBillNo(null);
		soadEntity.setBillType(advEntity.getBillType());
		soadEntity.setAmount(advEntity.getAmount());
		soadEntity.setVerifyAmount(advEntity.getVerifyAmount());
		soadEntity.setAuditStatus(advEntity.getAuditStatus());
		soadEntity.setUnverifyAmount(advEntity.getUnverifyAmount());
		soadEntity.setOrgCode(advEntity.getApplyOrgCode());
		soadEntity.setOrgName(advEntity.getApplyOrgName());
		soadEntity.setOrigOrgCode(null);
		soadEntity.setOrigOrgName(null);
		soadEntity.setDestOrgCode(null);
		soadEntity.setDestOrgName(null);
		soadEntity.setCustomerCode(advEntity.getCustomerCode());
		soadEntity.setCustomerName(advEntity.getCustomerName());
		soadEntity.setDeptRegionCode(null);
		soadEntity.setArrvRegionCode(null);
		soadEntity.setCustomerPickupOrgName(null);
		soadEntity.setQty(null);
		soadEntity.setBillingVolume(null);
		soadEntity.setBillWeight(null);
		soadEntity.setGoodsName(null);
		soadEntity.setDeliveryCustomerCode(null);
		soadEntity.setDeliveryCustomerName(null);
		soadEntity.setReceiveCustomerCode(null);
		soadEntity.setReceiveCustomerName(null);
		soadEntity.setPaymentType(advEntity.getPaymentType());
		soadEntity.setReceiveMethod(null);
		soadEntity.setProductCode(null);
		soadEntity.setInsuranceFee(null);
		soadEntity.setTransportFee(null);
		soadEntity.setPackagingFee(null);
		soadEntity.setDeliverFee(null);
		soadEntity.setCodFee(null);
		soadEntity.setOtherFee(null);
		soadEntity.setValueAddFee(null);
		soadEntity.setPromotionsFee(null);
		soadEntity.setPickupFee(null);
		soadEntity.setIsDelete(null);
		soadEntity.setDisableTime(null);
		soadEntity.setBusinessDate(advEntity.getBusinessDate());
		soadEntity.setAccountDate(advEntity.getAccountDate());
		soadEntity.setSignDate(null);
		soadEntity.setCreateTime(advEntity.getCreateTime());
		soadEntity.setNotes(advEntity.getNotes());
		return soadEntity;
	}

	/**
	 * @return statementMakeDao
	 */
	public IStatementMakeDao getStatementMakeDao() {
		return statementMakeDao;
	}

	/**
	 * @param statementMakeDao
	 */
	public void setStatementMakeDao(IStatementMakeDao statementMakeDao) {
		this.statementMakeDao = statementMakeDao;
	}

	/**
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @param billReceivableService
	 */
	public void setBillReceivableService(IBillReceivableService billReceivableService) {
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
	public void setBillDepositReceivedService(IBillDepositReceivedService billDepositReceivedService) {
		this.billDepositReceivedService = billDepositReceivedService;
	}

	/**
	 * @param billAdvancedPaymentService
	 */
	public void setBillAdvancedPaymentService(IBillAdvancedPaymentService billAdvancedPaymentService) {
		this.billAdvancedPaymentService = billAdvancedPaymentService;
	}

	/**
	 * @param statementOfAccountService
	 */
	public void setStatementOfAccountService(IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	/**
	 * @param statementOfAccountDService
	 */
	public void setStatementOfAccountDService(IStatementOfAccountDService statementOfAccountDService) {
		this.statementOfAccountDService = statementOfAccountDService;
	}

	/**
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @param publicBankAccountService
	 */
	public void setPublicBankAccountService(IPublicBankAccountService publicBankAccountService) {
		this.publicBankAccountService = publicBankAccountService;
	}

}
