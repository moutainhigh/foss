package com.deppon.foss.module.settlement.pay.server.service.impl;

import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IFinanceOnlinePayWSProxy;
import com.deppon.foss.module.settlement.common.api.server.service.IOperatingLogService;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillingQueryRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.OnlinePayInfoDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StlBillDetailDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICustInvoiceMarkQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CustInvoiceMarkEntity;
import com.deppon.foss.module.settlement.pay.api.server.dao.IBillDepositReceivedPayDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillDepositReceivedPayService;
import com.deppon.foss.module.settlement.pay.api.server.service.IFossToFinanceRemittanceService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPayDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentAddDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.RemitTransferQueryResultDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 预收单service
 * 
 * @author foss-pengzhen
 * @date 2012-11-19 下午4:29:16
 * @since
 * @version
 */
public class BillDepositReceivedPayService implements IBillDepositReceivedPayService {
	
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(BillDepositReceivedPayService.class);

	/**
	 * 注入service
	 */
	private IBillDepositReceivedPayDao billDepositReceivedPayDao;

	/**
	 * 注入预收单公共接口
	 */
	private IBillDepositReceivedService billDepositReceivedService;

	/**
	 * 注入对账单公共接口
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 注入自动获取单号的公共接口
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 注入电汇获取汇款编号的接口
	 */
	private IFossToFinanceRemittanceService fossToFinanceRemittanceService;

	/**
	 * 注入获取组织信息的公共接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 注入获取发票标记Service
	 */
	private ICustInvoiceMarkQueryService custInvoiceMarkQueryService;

	private IBillReceivableService billReceivableService;

	/**
	 * 注入日志接口
	 */
	private IOperatingLogService operatingLogService;

    /**
     * 网上支付处理代理
     */
    private IFinanceOnlinePayWSProxy financeOnlinePayWSProxy;
    
    /**
     * 营业部service
     */
    private ISaleDepartmentService saleDepartmentService;
    /**
     * POS报表服务
     * @author 309603 yangqiang 
     * @date 2016-02-23
     */
    private IPdaPosManageService pdaPosManageService;

	/**
	 * 查询对应的预收单信息
	 * 
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	@Override
	public BillDepositReceivedPayDto queryDepositReceived(BillDepositReceivedPayDto billDepositReceivedPayDto,
			CurrentInfo currentInfo, int start, int limit) {
		
		//是否是合伙人所在部门人员查询（合伙人预收单根据预收单创建人查询，非合伙人预收单根据预收部门查询）
		String currDept = currentInfo.getCurrentDeptCode();//当前用户所在部门
		logger.info("查询预收单，用户所在部门是："+currDept);
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentInfoByCode(currDept);
		if(null != saleDepartmentEntity && "Y".equals(saleDepartmentEntity.getIsLeagueSaleDept())){//合伙人所在部门
			logger.info("合伙人部门下人员查询预收单，合伙人部门编码是："+currDept);
			billDepositReceivedPayDto.setIsLeagueSaleDept(saleDepartmentEntity.getIsLeagueSaleDept());
			billDepositReceivedPayDto.setCreateOrgCode(currDept);
			
			//单独用于按单号查询预收单信息
			currentInfo.getUser().setUserName(saleDepartmentEntity.getIsLeagueSaleDept());//是否是合伙人
			currentInfo.getUser().setTitle(currDept);//当前用户所在部门
		}else{//非合伙人所在部门
			
		}
		
		// 除会计外的用户只能查到本部门的预收单信息，会计可以查到管辖范围内部门的预收单信息
		billDepositReceivedPayDto.setEmpCode(currentInfo.getEmpCode());
		List<BillDepositReceivedEntity> billDepositReceivedEntities = null;
		// 当按时间查询条件
		if (StringUtils.equals(billDepositReceivedPayDto.getQueryByTab(), SettlementConstants.TAB_QUERY_BY_DATE)) {
			// 业务结束时间

			Date dateTemp = DateUtils.addDayToDate(billDepositReceivedPayDto.getEndBusinessDate(), 1);
			billDepositReceivedPayDto.setEndBusinessDate(dateTemp);

			BillDepositReceivedPayDto dto = billDepositReceivedPayDao.queryDepositReceivedParamsCountAndAmount(billDepositReceivedPayDto);
			if (dto!=null && dto.getTotalNum() > 0) {
				billDepositReceivedEntities = billDepositReceivedPayDao.queryDepositReceivedParams(billDepositReceivedPayDto,
						start, limit);
				billDepositReceivedPayDto.setTotalNum(dto.getTotalNum());
				billDepositReceivedPayDto.setTotalAmount(dto.getTotalAmount());
			}else{
				billDepositReceivedPayDto.setTotalAmount(BigDecimal.ZERO);
			}
		}
		// 按预收单号来查询
		BillDepositReceivedPayDto resultTotalDto = null;
		if (StringUtils.equals(billDepositReceivedPayDto.getQueryByTab(), SettlementConstants.TAB_QUERY_BY_DEPOIST_RECEIVED_NO)) {
			String[] depositReceivedNo = billDepositReceivedPayDto.getDepositReceivedNo().split(",|;");
			List<String> depositReceivedNos = Arrays.asList(depositReceivedNo);

			billDepositReceivedPayDto.setDepositReceivedNos(depositReceivedNos);
			resultTotalDto = billDepositReceivedPayDao.queryDepositReceivedNosCount(billDepositReceivedPayDto, currentInfo);
			if (null != resultTotalDto) {
				billDepositReceivedEntities = billDepositReceivedPayDao.queryDepositReceivedNos(depositReceivedNos, currentInfo,
						start, limit);
				billDepositReceivedPayDto.setTotalNum(resultTotalDto.getTotalNum());
			}
			// 用于返回的DTO
			if (CollectionUtils.isNotEmpty(billDepositReceivedEntities)) {
				// 设置查询到的应收单的总条数、总金额
				BigDecimal totalAmount = BigDecimal.ZERO;
				for (BillDepositReceivedEntity entity : billDepositReceivedEntities) {
					totalAmount = totalAmount.add(entity.getAmount());
				}
				// 设置返回dto
				billDepositReceivedPayDto.setTotalAmount(totalAmount);
			}
		}
		
		billDepositReceivedPayDto.setBillDepositReceivedEntityList(billDepositReceivedEntities);
		return billDepositReceivedPayDto;
	}

	/**
	 * 新增预收单
	 * 
	 * @author foss-pengzhen
	 * @date 2012-11-26 下午7:17:49
	 * @param billDepositReceivedPayDto
	 * @param currentInfo
	 * @see
	 */
	@Transactional
	public BillDepositReceivedPayDto addBillDepositReceivedPay(BillDepositReceivedPayDto billDepositReceivedPayDto,CurrentInfo currentInfo) {
		BillDepositReceivedEntity entity = new BillDepositReceivedEntity();
		// 代理编码不能为空
		if (StringUtils.isEmpty(billDepositReceivedPayDto.getCustomerCode())) {
			throw new SettlementException("代理编码为空");
		}

		// 金额为空
		if (null == billDepositReceivedPayDto.getAmount()) {
			throw new SettlementException("金额为空！");
		}

		// 金额大于0
		if (billDepositReceivedPayDto.getAmount().compareTo(BigDecimal.ZERO) != 1) {
			throw new SettlementException("金额必须要大于0！");
		}

		// 部门编码不能为空
		if (StringUtils.isEmpty(billDepositReceivedPayDto.getGeneratingOrgCode())) {
			throw new SettlementException("部门编码为空");
		}

		if (StringUtils.isEmpty(currentInfo.getEmpCode())) {
			throw new SettlementException("制单人为空！");
		}

        //网上支付或者电汇付款方式时编码不能为空
        if (StringUtils.equals(entity.getPaymentType(), SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER)
                || StringUtils.equals(entity.getPaymentType(), SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE)) {
            if (StringUtils.isBlank(entity.getRemitNo())) {
                throw new SettlementException("电汇或者网上支付编码为空");
            }
        }
		//银行卡付款方式时交易流水号不能为空且必须为数字
		if (StringUtils.equals(entity.getPaymentType(), SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD)) {
			if (StringUtils.isBlank(entity.getRemitNo())) {
				throw new SettlementException("银行卡支付交易流水号不能为空");
			}
			if (!entity.getRemitNo().matches("^\\d*$")) {
				throw new SettlementException("银行卡支付交易流水号须为数字");
			}
		}
		/**
		 * 银行卡检验
		 * @author yangqiang 309603
		 * @date 2016-02-23
		 */
		PosCardEntity    posCardEntity    = null;			//POS实体
		BigDecimal 		 unUsedAmount     = null; 			//未使用金额
		BigDecimal       payAmount 		  = null;			//预收金额
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(billDepositReceivedPayDto.getPaymentType())
				&& (!StringUtil.isBlank(billDepositReceivedPayDto.getRemitNo())
				&& billDepositReceivedPayDto.getRemitNo().matches("[0-9]+"))
				) {
			//查询T+0报表获取未使用金额
			//准备查询数据
			PosCardManageDto posCardManageDto = new PosCardManageDto();
			posCardManageDto.setTradeSerialNo(billDepositReceivedPayDto.getRemitNo());
			//设置部门code
			posCardManageDto.setOrgCode(billDepositReceivedPayDto.getGeneratingOrgCode());
			//posCardManageDto.setBelongMoudleCode(SettlementDictionaryConstants.NCI_DEPOSIT);
			//查询T+0报表数据
			List<PosCardEntity> posCardEntitys = null;
			posCardEntitys = pdaPosManageService.queryPosCardData(posCardManageDto).getPosCardEntitys();
			//是否存在
			if (posCardEntitys==null||posCardEntitys.isEmpty()) {
				throw new SettlementException("输入流水号不存在或者输入流水号有误或者所属部门不正确。");
			}else{
				posCardEntity = posCardEntitys.get(0);
			}
			if (posCardEntity==null) {
				throw new SettlementException("输入流水号不存在或者输入流水号有误或者所属部门不正确。");
			}
			//获取未使用金额
			unUsedAmount =posCardEntity.getUnUsedAmount();
			//比较
			//预收金额
			payAmount = billDepositReceivedPayDto.getAmount();
			//添加校验T+0报表数据的冻结金额校验
			// 可用金额小于预收金额
			if (unUsedAmount.compareTo(payAmount)<0) {	
					throw new SettlementException("可用余额不足。");
			}
			//添加校验T+0报表数据的冻结状态
			logger.info("输入的交易流水号:"+posCardEntity.getTradeSerialNo()+"的冻结状态为:"+posCardEntity.getFrozenStatus());
			if(posCardEntity.getFrozenStatus() != SettlementConstants.POS_CARD_FROZEN_STATUS_1
					&& posCardEntity.getFrozenStatus() != SettlementConstants.POS_CARD_FROZEN_STATUS_0
					&& posCardEntity.getFrozenStatus() != SettlementConstants.POS_CARD_FROZEN_STATUS_2){
				//未知的冻结状态
				throw new SettlementException("输入的交易流水号:"+posCardEntity.getTradeSerialNo()+"的冻结状态为未知的冻结状态!!");
			}
			if(posCardEntity.getFrozenStatus() == SettlementConstants.POS_CARD_FROZEN_STATUS_1){
				//如果交易流水冻结状态为全额冻结,不允许还款操作
				throw new SettlementException("该交易流水号:"+posCardEntity.getTradeSerialNo()+"已冻结，请联系资金支持部解除锁定后使用！");
				
			}
			BigDecimal frozenAmount = posCardEntity.getFrozenAmount() == null? new BigDecimal(0): posCardEntity.getFrozenAmount();
			// 获取未使用金额
			unUsedAmount =posCardEntity.getUnUsedAmount().subtract(frozenAmount);
			payAmount = billDepositReceivedPayDto.getAmount();
			logger.info("输入的交易流水号:"+posCardEntity.getTradeSerialNo()+"的未使用金额为:"+posCardEntity.getUnUsedAmount());
			logger.info("输入的交易流水号:"+posCardEntity.getTradeSerialNo()+"的实际未使用金额为:"+unUsedAmount);
			//比较
			//预收金额
			payAmount = billDepositReceivedPayDto.getAmount();
			//添加校验T+0报表数据的冻结金额校验
			if(posCardEntity.getFrozenStatus() == SettlementConstants.POS_CARD_FROZEN_STATUS_2){
				// 可用金额小于还款金额
				if (unUsedAmount.compareTo(payAmount)<0) {	
					throw new SettlementException("可用余额不足。");
				}	
		
			}
		}

		// 当前登录人为收银员，系统检查出该客户存在未核销金额不为0的应收单时，不允许提交；
		if (StringUtils.equals(billDepositReceivedPayDto.getCashierAccounting(), SettlementConstants.DEPOSIT_RECEIVED_CASHIER)) {
			// 传入客户编码，查询客户是否存在未核销的应收单：
			List<BillReceivableEntity> billReceivableEntities = billReceivableService
					.queryReceivableAmountByCustomerCode(billDepositReceivedPayDto.getCustomerCode());
			if (CollectionUtils.isNotEmpty(billReceivableEntities)) {
				// StringBuffer receiveNos=new StringBuffer();
				int receiveNum = 0;
				List<String> receiveNos = new ArrayList<String>();
				int step = 0;
				if (billReceivableEntities.size() > SettlementConstants.BILL_NOS_MAX) {
					for (BillReceivableEntity billReceivableEntity : billReceivableEntities) {
						if (StringUtils.isNotEmpty(billReceivableEntity.getReceivableNo()) && step < SettlementConstants.BILL_NOS_MAX) {
							receiveNos.add(billReceivableEntity.getReceivableNo());
							receiveNum++;
							step++;
						}
					}
					throw new SettlementException("客户存在未核销金额不为0的应收单" + receiveNos.toString() + "......总条数为：" + receiveNum
							+ ";不允许提交!");
				} else {
					for (BillReceivableEntity billReceivableEntity : billReceivableEntities) {
						if (StringUtils.isNotEmpty(billReceivableEntity.getReceivableNo())) {
							receiveNos.add(billReceivableEntity.getReceivableNo());
							receiveNum++;
						}
					}
					throw new SettlementException("客户存在未核销金额不为0的应收单" + receiveNos.toString() + "总条数为：" + receiveNum + ";不允许提交!");
				}

			}
		}
		// 当前登录人为会计，系统检查出该客户存在未核销金额不为0的应收单时，允许提交；
		// 作废网上支付自动生成的还款单，会计可先做预收。
		// 设置id
		entity.setId(UUIDUtils.getUUID());
		// 设置预收单号,预收单号系统自动生成，生成规则“US+流水号”
		entity.setDepositReceivedNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.US));

		entity.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);

		// 设置预收单的代理、航空公司名称、代理信息为界面输入信息
		entity.setCustomerCode(billDepositReceivedPayDto.getCustomerCode());
		entity.setCustomerName(billDepositReceivedPayDto.getCustomerName());

		// 设置制单人的工号
		entity.setCreateUserCode(currentInfo.getEmpCode());
		entity.setCreateUserName(currentInfo.getEmpName());

		// 设置部门预收部门名称、编码
		entity.setGeneratingOrgCode(billDepositReceivedPayDto.getGeneratingOrgCode());
		entity.setGeneratingOrgName(billDepositReceivedPayDto.getGeneratingOrgName());

		// 设置部门收款部门名称、编码
		entity.setCollectionOrgCode(billDepositReceivedPayDto.getGeneratingOrgCode());
		entity.setCollectionOrgName(billDepositReceivedPayDto.getGeneratingOrgName());
		// 获取当前登录组织的的实体信息
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentInfo
				.getCurrentDeptCode());
		entity.setCollectionCompanyCode(orgEntity.getSubsidiaryCode());// 当前登录用户操作公司
		entity.setCollectionCompanyName(orgEntity.getSubsidiaryName());
		// 设置部门制单部门名称、编码
		entity.setCreateOrgCode(billDepositReceivedPayDto.getGeneratingOrgCode());
		entity.setCreateOrgName(billDepositReceivedPayDto.getGeneratingOrgName());

		// 设置金额
		final int numberOfTenMillion = 10000000;
		BigDecimal amount = billDepositReceivedPayDto.getAmount();
		if (amount.compareTo(BigDecimal.valueOf(numberOfTenMillion)) == 1) {
			throw new SettlementException("金额不能大于10000000！");
		}
		entity.setAmount(amount);
		entity.setUnverifyAmount(amount);
		entity.setVerifyAmount(BigDecimal.ZERO);

		// 设置收款方式,
		entity.setPaymentType(billDepositReceivedPayDto.getPaymentType());

		// 退款状态默认为未退款
		entity.setRefundStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__REFUND_STATUS__NOT_REFUND);

		// 收银状态默认为已提交
		entity.setStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS__SUBMIT);

		// 设置汇款编号
		String remitNo = billDepositReceivedPayDto.getRemitNo();
		entity.setRemitNo(remitNo);

		// 单据类型
		entity.setBillType(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE__DEPOSIT_RECEIVED);

		// 是否有效\是否红单
		entity.setActive(FossConstants.ACTIVE);
		entity.setIsRedBack(FossConstants.NO);

		// 设置预收单的币种
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);

		// 设置业务、记账、创建时间
		Date nowDate = new Date();
		entity.setBusinessDate(nowDate);
		entity.setAccountDate(nowDate);
		entity.setCreateTime(nowDate);
		entity.setModifyTime(nowDate);

		// 设置是否初始化
		entity.setIsInit(FossConstants.NO);

		// 版本号
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);

		// 默认单号
		entity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);

		// 运输类型
		entity.setTransportType(billDepositReceivedPayDto.getTransportType());

		// 客户类型 默认先放界面传入的运输类型

		/*
		 * 税务咨询项目，设置发票标记
		 * 
		 * 杨书硕 2013-11-27 上午9:17:31
		 */
		// 专线客户则查询客户发票标记，其他类型客户为02
        CustInvoiceMarkEntity custInvoiceMarkEntity = custInvoiceMarkQueryService.queryCustInvoiceMarkByCustNum(billDepositReceivedPayDto
                .getCustomerCode());
		if (SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER.equals(billDepositReceivedPayDto
				.getTransportType())) {

			// 设置发票标记，为空则设置为 02-非运输专票
			if (StringUtils.isBlank(custInvoiceMarkEntity.getInvoiceMark())) {
				entity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);

				// 发票标记非01 02 则设置为02-非运输专票
			} else if (!SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO.equals(custInvoiceMarkEntity.getInvoiceMark())
					&& !SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_ONE.equals(custInvoiceMarkEntity.getInvoiceMark())) {
				entity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
			} else {
				entity.setInvoiceMark(custInvoiceMarkEntity.getInvoiceMark());
			}
		} else {
			// 非专线客户设置为02
			entity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
		}

        /**
         * 统一结算需求 设置统一结算标记和合同部门
         * 发票标记为02且客户统一结算标记为“是”则设置统一结算标记和对应部门 收款方式固定为电汇
         *
         * @Author 105762
         */
		/**
		 * 统一结算变更
		 * 1、	统一结算客户的预收，保留预收单的“是否统一结算”标记和“合同部门”信息不变，统一结算标记和合同部门信息根据录入预收时的客户读取；
		 * 2、	统一结算客户的预收部门取预收单录入时的预收部门，改掉之前预收部门默认统一结算合同部门的信息；
		 */
        if(StringUtils.equals(entity.getInvoiceMark(),SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO)
                && StringUtils.equals(custInvoiceMarkEntity.getUnifiedSettlement(),FossConstants.YES)){
            entity.setUnifiedSettlement(FossConstants.YES);

            if(StringUtils.isBlank(custInvoiceMarkEntity.getContractOrgCode())){
                throw new SettlementException("客户合同部门编码为空");
            }
            if(StringUtils.isBlank(custInvoiceMarkEntity.getContractOrgName())){
                throw new SettlementException("客户合同部门名称为空");
            }
            //合同部门编码 名称
            entity.setContractOrgCode(custInvoiceMarkEntity.getContractOrgCode());
            entity.setContractOrgName(custInvoiceMarkEntity.getContractOrgName());
            // 设置部门收款部门名称、编码
//            entity.setGeneratingOrgCode(custInvoiceMarkEntity.getContractOrgCode());
//            entity.setGeneratingOrgName(custInvoiceMarkEntity.getContractOrgName());

            //统一结算收款方式必须为电汇
//            if(!StringUtils.equals(entity.getPaymentType()
//                    ,SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER)){
//                throw new SettlementException("统一结算客户预收付款方式必须为电汇");
//            }

        } else {
            entity.setUnifiedSettlement(FossConstants.NO);
        }

        // 获取当前选择的预收部门组织的的实体信息
        OrgAdministrativeInfoEntity generatOrgEntity = orgAdministrativeInfoService
                .queryOrgAdministrativeInfoByCode( entity.getGeneratingOrgCode());
        if (generatOrgEntity == null) {
            throw new SettlementException("当前选择的预收部门组织信息为空！");
        }
        entity.setGeneratingCompanyCode(generatOrgEntity.getSubsidiaryCode());
        entity.setGeneratingCompanyName(generatOrgEntity.getSubsidiaryName());

		// 设置备注
		entity.setNotes(billDepositReceivedPayDto.getNotes());

		billDepositReceivedService.addBillDepositReceived(entity, currentInfo);

		// 收款方式,如果为电汇调用费控系统
		if (StringUtils.equals(billDepositReceivedPayDto.getPaymentType(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER)
				|| StringUtils.equals(billDepositReceivedPayDto.getPaymentType(),
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE)) {
			fossToFinanceRemittanceService.obtainClaim(amount, remitNo, billDepositReceivedPayDto.getGeneratingOrgCode(),
					entity.getDepositReceivedNo());

        //收款方式,如果为网上支付调用费控系统
		} else if(StringUtils.equals(billDepositReceivedPayDto.getPaymentType(),SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE)){
            financeOnlinePayWSProxy.obtain(remitNo,billDepositReceivedPayDto.getAmount());
        }

		// 设置参数返回到界面
		// 设置预收单号
		billDepositReceivedPayDto.setDepositReceivedNo(entity.getDepositReceivedNo());
		/**
		 *更新T+0数据 调用更新数据
		 * @author yangqiang 309603
		 * @date 2016-02-23
		 */
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(billDepositReceivedPayDto.getPaymentType())
				&& (!StringUtil.isBlank(billDepositReceivedPayDto.getRemitNo())
				&& billDepositReceivedPayDto.getRemitNo().matches("[0-9]+"))
				) {
			//更新POS刷卡信息
			//准备数据
			posCardEntity.setModifyUser(currentInfo.getEmpName());										//更新人名称
			posCardEntity.setModifyUserCode(currentInfo.getEmpCode());									//更新人编码
			posCardEntity.setUsedAmount(posCardEntity.getUsedAmount().add(payAmount));					//已使用金额
			posCardEntity.setUnUsedAmount(posCardEntity.getUnUsedAmount().subtract(payAmount));   							//未使用金额
			pdaPosManageService.updatePosCardMessageAmount(posCardEntity);
			//插入新的POS刷卡明细
			//准备数据
			PosCardDetailEntity posCardDetailEntity = new PosCardDetailEntity();
			posCardDetailEntity.setTradeSerialNo(posCardEntity.getTradeSerialNo()); 					//交易流水号
			posCardDetailEntity.setInvoiceType("US");													//预收单
			posCardDetailEntity.setInvoiceNo(entity.getDepositReceivedNo());							//预收单号
			posCardDetailEntity.setAmount(payAmount);													//发生金额
			posCardDetailEntity.setOccupateAmount(payAmount);											//已占用金额
			posCardDetailEntity.setUnVerifyAmount(BigDecimal.ZERO);										//未核销金额
			posCardDetailEntity.setCreateUser(currentInfo.getEmpName());								//创建人名称
			posCardDetailEntity.setCreateUserCode(currentInfo.getEmpCode());							//创建人编码
    		pdaPosManageService.insertOrUpdateDetail(posCardDetailEntity);//调用接口插入数据	
		}

		return billDepositReceivedPayDto;
	}

	/**
	 * 根据汇款单号获取汇款信息
	 * 
	 * @author foss-pengzhen
	 * @date 2013-1-15 下午2:24:30
	 * @return
	 * @see
	 */
	@Override
	public BillDepositReceivedPayDto queryPayRemittanceDetail(BillDepositReceivedPayDto billDepositReceivedPayDto,
			CurrentInfo currentInfo) {
		if (billDepositReceivedPayDto == null) {
			throw new SettlementException("内部异常，传入参数为空，不能获取汇款信息!");
		}

		// 调用费控接口
		RemitTransferQueryResultDto remitTransferQueryResultDto = fossToFinanceRemittanceService.queryTransfer(
				billDepositReceivedPayDto.getRemitNo(), billDepositReceivedPayDto.getPaymentType());

		if (remitTransferQueryResultDto == null) {
			throw new SettlementException("汇款信息不存在,请重新输入汇款编号！");
		}

		if (StringUtils.isEmpty(remitTransferQueryResultDto.getClaimDeptNo())) {
			throw new SettlementException("汇款信息未被认领，请先认领此汇款单号！");
		}

		// 获取当前登录组织的的实体信息
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(billDepositReceivedPayDto.getGeneratingOrgCode());

		// 当前登录用户操作公司
		if (!StringUtils.equals(orgEntity.getUnifiedCode(), remitTransferQueryResultDto.getClaimDeptNo())) {
			throw new SettlementException("该汇款信息不是本部门认领，无法新增");
		}

		// 设置返回金额
		billDepositReceivedPayDto.setAmount(remitTransferQueryResultDto.getNoCancelAmount());
		return billDepositReceivedPayDto;
	}	/**
	 * 根据网上支付编号获取信息
	 *
	 * @author foss-pengzhen
	 * @date 2013-1-15 下午2:24:30
	 * @see
	 */
	@Override
	public BillDepositReceivedPayDto queryOnlinePayDetail(BillDepositReceivedPayDto billDepositReceivedPayDto,
			CurrentInfo currentInfo) {
		if (billDepositReceivedPayDto == null) {
			throw new SettlementException("内部异常，传入参数为空，不能获取汇款信息!");
		}

        OnlinePayInfoDto onlinePayDto = financeOnlinePayWSProxy.query(billDepositReceivedPayDto.getRemitNo());

		if (onlinePayDto == null) {
			throw new SettlementException("网上支付信息不存在,请重新输入汇款编号！");
		}

		// 设置返回金额
		billDepositReceivedPayDto.setAmount(onlinePayDto.getUnuseredAmounts());
		return billDepositReceivedPayDto;
	}

	/**
	 * 导出预收单
	 * 
	 * @author foss-pengzhen
	 * @date 2012-12-11 下午4:16:38
	 * @param billDepositReceivedPayDto
	 * @param currentInfo
	 * @see
	 */
	public HSSFWorkbook depositReceivedListExport(BillDepositReceivedPayDto billDepositReceivedPayDto, CurrentInfo currentInfo) {
		HSSFWorkbook work = null;
		if (billDepositReceivedPayDto == null) {
			throw new SettlementException("内部异常，传入参数为空，不能执行导出操作!");
		}
		billDepositReceivedPayDto.setEmpCode(currentInfo.getEmpCode());
		List<BillDepositReceivedEntity> billDepositReceivedEntities = null;
		if (StringUtils.equals(billDepositReceivedPayDto.getQueryByTab(), SettlementConstants.TAB_QUERY_BY_DATE)) {
			// 业务结束时间
			Date dateTemp = DateUtils.addDayToDate(billDepositReceivedPayDto.getEndBusinessDate(), 1);
			billDepositReceivedPayDto.setEndBusinessDate(dateTemp);

			billDepositReceivedEntities = billDepositReceivedPayDao.queryDepositReceivedParam(billDepositReceivedPayDto);
		}

		if (StringUtils.equals(billDepositReceivedPayDto.getQueryByTab(), SettlementConstants.TAB_QUERY_BY_DEPOIST_RECEIVED_NO)) {
			String[] depositReceivedNo = billDepositReceivedPayDto.getDepositReceivedNo().split(",");
			List<String> depositReceivedNos = Arrays.asList(depositReceivedNo);
			billDepositReceivedEntities = billDepositReceivedService.queryByDepositReceivedNOs(depositReceivedNos, "");
		}
		// 查询到的结果集不为空时，执行导出操作
		if (CollectionUtils.isNotEmpty(billDepositReceivedEntities)) {
			if (billDepositReceivedEntities.size() > SettlementConstants.EXPORT_EXCEL_MAX_COUNTS) {
				throw new SettlementException("每次最多只能导出" + SettlementConstants.EXPORT_EXCEL_MAX_COUNTS + "条数据,请重新查询并导出");
			}
			ExcelExport export = new ExcelExport();
			billDepositReceivedPayDto.setBillDepositReceivedEntityList(billDepositReceivedEntities);
			// 设置每次最多导出10万条数据
			work = export.exportExcel(fillSheetDataByDepositReceived(billDepositReceivedPayDto), "sheet",
					SettlementConstants.EXPORT_MAX_COUNT);
		} else {
			throw new SettlementException("导出数据为空！");
		}
		return work;
	}

	/**
	 * 生成预收单的excel
	 * 
	 * @author foss-pengzhen
	 * @date 2012-12-6 上午11:13:38
	 */
	private SheetData fillSheetDataByDepositReceived(BillDepositReceivedPayDto dto) {
		SheetData sheetData = new SheetData();
		sheetData.setRowHeads(new String[] { "预收编号", "部门名称", "预收部门编码", "录入部门", "录入部门编码", "客户名称", "客户编码", "客户类型", "收款方式", "付款金额",
				"汇款编号", "汇款人名称", "汇款所属部门", "金额", "已核销金额", "未核销金额", "单据状态", "记账日期", "备注", "制单人", "制单人编号", "版本号", "是否有效版本", "是否红单",
				"创建时间", "更新时间","发票标记" });

		List<BillDepositReceivedEntity> lists = dto.getBillDepositReceivedEntityList();
		if (CollectionUtils.isNotEmpty(lists)) {
			List<List<String>> rowList = new ArrayList<List<String>>();

			// 声明字典集合
			List<String> termCodes = new ArrayList<String>();
			// 添加单据类型数据字典到集合中
			termCodes.add(DictionaryConstants.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE);
			// 添加收款方式数据字典到集合中
			termCodes.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);
			// 添加状态数据字典到集合中
			termCodes.add(DictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS);
			termCodes.add(DictionaryConstants.FOSS_ACTIVE);
			termCodes.add(DictionaryConstants.SETTLEMENT__IS_RED_BACK);
			// 后台获取数据字典对应的数据
			Map<String, Map<String, Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);

			for (BillDepositReceivedEntity entity : lists) {
				List<String> cellList = new ArrayList<String>();
				cellList.add(entity.getDepositReceivedNo());// 预收编号
				cellList.add(entity.getGeneratingOrgName());// 预收部门
				cellList.add(entity.getGeneratingOrgCode());// 预收部门编码
				cellList.add(entity.getCollectionOrgName());// 录入部门
				cellList.add(entity.getCollectionOrgCode());// 录入部门编码
				cellList.add(entity.getCustomerName());// 客户名称
				cellList.add(entity.getCustomerCode());// 客户编码
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE, entity.getTransportType()));// 运输类型
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map, DictionaryConstants.SETTLEMENT__PAYMENT_TYPE,
						entity.getPaymentType()));// 收款方式
				cellList.add(entity.getPaymentAmount() != null ? entity.getPaymentAmount() + "" : "");// 付款金额
				cellList.add(entity.getRemitNo());// 汇款编号
				cellList.add(entity.getRemiterName());// 汇款人名称
				cellList.add(entity.getRemitOrgName());// 汇款所属部门
				cellList.add(entity.getAmount() != null ? entity.getAmount() + "" : "");// 金额
				cellList.add(entity.getVerifyAmount() != null ? entity.getVerifyAmount() + "" : "");// 已核销金额
				cellList.add(entity.getUnverifyAmount() != null ? entity.getUnverifyAmount() + "" : "");// 未核销金额
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map, DictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS,
						entity.getStatus()));// 单据状态
				cellList.add(DateUtils.convert(entity.getAccountDate(), DateUtils.DATE_TIME_FORMAT));// 记账日期
				cellList.add(entity.getNotes());// 备注
				cellList.add(entity.getCreateUserName());// 制单人
				cellList.add(entity.getCreateUserCode());// 制单人编号
				cellList.add(entity.getVersionNo().toString());// 版本号
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map, DictionaryConstants.FOSS_ACTIVE, entity.getActive()));// 是否有效版本
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map, DictionaryConstants.SETTLEMENT__IS_RED_BACK,
						entity.getIsRedBack()));// 是否红单
				cellList.add(DateUtils.convert(entity.getCreateTime(), DateUtils.DATE_TIME_FORMAT));// 创建时间
				cellList.add(DateUtils.convert(entity.getCreateTime(), DateUtils.DATE_TIME_FORMAT));// 更新时间
				
				//发票标记
				if(entity.getInvoiceMark()!=null){
					if(entity.getInvoiceMark().equals(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_ONE)){
						cellList.add("01-运输专票11%");
					}else if(entity.getInvoiceMark().equals(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO)){
						cellList.add("02-非运输专票");
					}else{
						cellList.add(entity.getInvoiceMark());
					}
				}else{
					cellList.add(null);
				}
				


				rowList.add(cellList);
			}
			sheetData.setRowList(rowList);
		}
		return sheetData;
	}

	/**
	 * 预付单作废
	 * 
	 * update by dp-wujiangtao-099995
	 * 
	 * @author foss-pengzhen
	 * @date 2012-11-26 下午6:49:24
	 * @param billDepositReceivedPayDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	@Transactional
	public void writeBackBillDepositReceived(BillDepositReceivedPayDto billDepositReceivedPayDto, CurrentInfo currentInfo) {
		// 判断dto是否为空
		if (billDepositReceivedPayDto == null) {
			throw new SettlementException("内部异常，传入参数为空，不能执行作废操作!");
		}
		// 该预收单为无效版本或红单；
		if (StringUtils.equals(billDepositReceivedPayDto.getActive(), FossConstants.NO)) {
			throw new SettlementException("作废失败，有效的预收单才能执行作废操作！");
		}
		BillDepositReceivedEntity billDepositReceivedEntity = billDepositReceivedService.queryByDepositReceivedNo(
				billDepositReceivedPayDto.getDepositReceivedNo(), FossConstants.ACTIVE);
		if (billDepositReceivedEntity == null) {
			throw new SettlementException("作废失败，有效的预收单才能执行作废操作！");
		}

		// 调用公共作预收单废接口
		billDepositReceivedService.disableBillDepositReceived(billDepositReceivedEntity, currentInfo);

		// 插入预收单日志
		OperatingLogEntity operatingLogEntity = new OperatingLogEntity();
		operatingLogEntity.setOperateBillNo(billDepositReceivedEntity.getDepositReceivedNo());
		operatingLogEntity
				.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__BILL_DEPOSIT_RECEIVED);
		operatingLogEntity.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__DISABLE);
		operatingLogService.addOperatingLog(operatingLogEntity, currentInfo);

		// 收款方式,如果为电汇调用费控系统
		if ((StringUtils.equals(billDepositReceivedEntity.getPaymentType(), SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER)
				|| StringUtils.equals(billDepositReceivedEntity.getPaymentType(), SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE))
				&& StringUtils.equals(billDepositReceivedEntity.getIsInit(), FossConstants.NO)) {
			BigDecimal amount = billDepositReceivedEntity.getAmount();
			String remitNo = billDepositReceivedEntity.getRemitNo();
			String code = billDepositReceivedEntity.getGeneratingOrgCode();
			fossToFinanceRemittanceService.releaseClaim(amount, remitNo, code, billDepositReceivedEntity.getDepositReceivedNo());

		//收款方式,如果为网上支付调用费控系统
		} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(billDepositReceivedEntity.getPaymentType())) {
			if (StringUtils.isNotBlank(billDepositReceivedEntity.getRemitNo())) {
				financeOnlinePayWSProxy.release(billDepositReceivedEntity.getRemitNo(), billDepositReceivedEntity.getAmount());
			}
		}
	}

	/**
	 * 退预收
	 * 
	 * @author foss-pengzhen
	 * @date 2012-12-4 上午11:30:08
	 * @param billDepositReceivedPayDto
	 *            预收单信息
	 * @param currentInfo
	 *            用户信息
	 * @return
	 * @see
	 */
	public BillDepositReceivedPayDto backDepositReceived(BillDepositReceivedPayDto billDepositReceivedPayDto,CurrentInfo currentInfo, String operateType) {
		// 判断dto是否为空
		if (billDepositReceivedPayDto == null) {
			throw new SettlementException("内部异常，传入参数为空，不能进行退预收操作！");
		}

		if (billDepositReceivedPayDto.getBillDepositReceivedEntityList() == null) {
			throw new SettlementException("内部异常，传入参数为空，不能进行退预收操作！");
		}

		List<BillDepositReceivedEntity> formDepositReceivedEntities = billDepositReceivedPayDto
				.getBillDepositReceivedEntityList();

		// 将预收单号添加到预收单号集合
		List<String> depositReceivedNos = new ArrayList<String>();
		for (BillDepositReceivedEntity billDepositReceivedEntity : formDepositReceivedEntities) {
			depositReceivedNos.add(billDepositReceivedEntity.getDepositReceivedNo());
		}

		// load库中最新的数据
		List<BillDepositReceivedEntity> billDepositReceivedEntities = billDepositReceivedService.queryByDepositReceivedNOs(
				depositReceivedNos, FossConstants.ACTIVE);

		if (CollectionUtils.isEmpty(billDepositReceivedEntities)) {
			throw new SettlementException("库中数据已被修改，不能进行退预收操作！");
		}

		if (StringUtils.isEmpty(billDepositReceivedEntities.get(0).getCustomerCode())) {
			throw new SettlementException("客户不存在，不能进行退预收操作！");
		}

		String customerCode = billDepositReceivedEntities.get(0).getCustomerCode();
		
		//和合伙人相关的预收单（客户编码CUSTOMER_CODE字段是合伙人部门），非合伙人不能退预收
		//当前用户所在部门
		String currDept = currentInfo.getCurrentDeptCode();
		logger.info("预收单的客户编码是："+customerCode);
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentInfoByCode(customerCode);
		if(null != saleDepartmentEntity && "Y".equals(saleDepartmentEntity.getIsLeagueSaleDept())){//合伙人的预收单
			logger.info("合伙人部门下人员提现，合伙人部门编码（客户编码）是："+customerCode);
			if(!currDept.equals(customerCode)){
				throw new SettlementException("非合伙人不能对合伙人的预收单退款！");
			}
		}else{//非合伙人预收单
			
		}
				

		// 实例付款dto，后面做赋值操作
		List<BillPaymentAddDto> billPaymentAddDtos = new ArrayList<BillPaymentAddDto>();
		List<BillDepositReceivedEntity> depositReceivedList = new ArrayList<BillDepositReceivedEntity>();
		// 声名明细总和
		BigDecimal totalAmount = BigDecimal.ZERO;

		for (BillDepositReceivedEntity billDepositReceivedEntity : billDepositReceivedEntities) {

			if (!StringUtils.equals(customerCode, billDepositReceivedEntity.getCustomerCode())) {
				throw new SettlementException("选择的预收单必须是同一个客户才能执行退预收操作!");
			}

			if (!StringUtils.equals(SettlementConstants.DEFAULT_BILL_NO, billDepositReceivedEntity.getPaymentNo())) {
				throw new SettlementException("已退过预收的单据不能再次退款！");
			}
			// 校验已核销或部分核销的预收单不能作废
			if (billDepositReceivedEntity.getUnverifyAmount() == null
					|| billDepositReceivedEntity.getUnverifyAmount().compareTo(BigDecimal.ZERO) == 0) {
				throw new SettlementException("你选中的预收单中有未核销金额等于0的，请选择未核销金额大于0的预收单进行退款!");
			}// 红单或无效版本不能退款
			else if (FossConstants.INACTIVE.equals(billDepositReceivedEntity.getActive())) {
				throw new SettlementException("你选中的预收单中有无效的版本，请选择有效的版本进行退款！");
				// 已处于退款中的预收单不能退款
			} else if (SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__REFUND_STATUS__REFUNDING
					.equals(billDepositReceivedEntity.getRefundStatus())) {
				throw new SettlementException("该预收单已经在退款中，无需重复进行退款操作！");
				// 校验预收单已制作对账单，不能进行退款操作！
			} else if (StringUtils.isNotEmpty(billDepositReceivedEntity.getStatementBillNo())
					&& !SettlementConstants.DEFAULT_BILL_NO.equals(billDepositReceivedEntity.getStatementBillNo())) {
				throw new SettlementException("预收单已制作对账单，不能进行退款操作！");
			}
			if (SettlementConstants.OPERATETYPE_DEPOSITRECEIVED.equals(operateType)) {
				// 把预收单的数据拷贝到付款dto，传到界面
				BillPaymentAddDto billPaymentAddDto = new BillPaymentAddDto();
				// 进行数据转化
				billPaymentAddDto.setId(billDepositReceivedEntity.getId());
				billPaymentAddDto.setPayableNo(billDepositReceivedEntity.getDepositReceivedNo());
				billPaymentAddDto.setBillType(billDepositReceivedEntity.getBillType());
				billPaymentAddDto.setAmount(billDepositReceivedEntity.getAmount());
				billPaymentAddDto.setVerifyAmount(billDepositReceivedEntity.getVerifyAmount());
				billPaymentAddDto.setUnverifyAmount(billDepositReceivedEntity.getUnverifyAmount());
				billPaymentAddDto.setCurrentPaymentAmount(billDepositReceivedEntity.getUnverifyAmount());
				billPaymentAddDto.setNotes(billDepositReceivedEntity.getNotes());
				billPaymentAddDto.setVersionNo(billDepositReceivedEntity.getVersionNo());
				billPaymentAddDto.setAccountDate(billDepositReceivedEntity.getAccountDate());
				billPaymentAddDtos.add(billPaymentAddDto);
				depositReceivedList.add(billDepositReceivedEntity);
				// 对明细未核销金额进行汇总
				totalAmount = totalAmount.add(billDepositReceivedEntity.getUnverifyAmount());
			}
		}

		// 如果为退预收类型，则需要封装返回值
		if (SettlementConstants.OPERATETYPE_DEPOSITRECEIVED.equals(operateType)) {
			// 将预收单部门、公司、客户等信息封装到付款单实体中，带到付款单界面
			if (depositReceivedList.size() > 0) {
				// 获取预收单实体
				BillDepositReceivedEntity entity = depositReceivedList.get(0);
				// 声明付款单实体
				BillPaymentEntity paymentEntity = new BillPaymentEntity();
				// 进行数据转化
				paymentEntity.setAmount(totalAmount);
				paymentEntity.setCustomerCode(entity.getCustomerCode());
				paymentEntity.setCustomerName(entity.getCustomerName());
				paymentEntity.setPaymentOrgCode(entity.getCollectionOrgCode());
				paymentEntity.setPaymentOrgName(entity.getCollectionOrgName());
				// 获取当前选择的应收部门组织的的实体信息
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity
						.getCollectionOrgCode());
				if (orgEntity == null) {
					throw new SettlementException("当前选择的应收部门组织信息为空！");
				}
				paymentEntity.setPaymentCompanyCode(orgEntity.getSubsidiaryCode());
				paymentEntity.setPaymentCompanyName(orgEntity.getSubsidiaryName());
				// 默认为现金
				paymentEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
				// 将付款单实体封装到dto中
				billDepositReceivedPayDto.setPaymentEntity(paymentEntity);
			}
			billDepositReceivedPayDto.setAddDtoList(billPaymentAddDtos);
		}
		return billDepositReceivedPayDto;
	}

	/**
	 * 根据付款单号获取预收单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-3-20 下午7:09:35
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillDepositReceivedPayService#queryListByPaymentNo(com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPayDto)
	 */
	@Override
	public List<BillDepositReceivedEntity> queryListByPaymentNo(BillDepositReceivedPayDto dto) {
		// 查询传入参数不能为空
		if (dto == null) {
			// 查看付款单预收明细传入参数为空
			throw new SettlementException("查看付款单预收明细传入参数为空");
		}
		// 付款单号、付款单明细来源单号类型不能为空
		if (StringUtils.isEmpty(dto.getPaymentNo()) || StringUtils.isEmpty(dto.getSourceBillTypeFkd())) {
			// 查看付款单应付明细传入付款单号、来源单号类型为空
			throw new SettlementException("查看付款单应付明细传入付款单号、来源单号类型为空");
		}
		// 获取预收单明细
		return billDepositReceivedPayDao.queryListByPaymentNo(dto);
	}
	
	
	/**
	 * ptp监控查询合伙人预收单总记录数和总金额数
	 */
	@Override
	public StlBillDetailDto countDepositReceivedBills(
			BillingQueryRequestDto requestDto) {
		
		return billDepositReceivedPayDao.countDepositReceivedBills(requestDto);
	}
	

	/**
	 * @return the billDepositReceivedPayDao
	 */
	public IBillDepositReceivedPayDao getBillDepositReceivedPayDao() {
		return billDepositReceivedPayDao;
	}

	/**
	 * @param billDepositReceivedPayDao
	 *            the billDepositReceivedPayDao to set
	 */
	public void setBillDepositReceivedPayDao(IBillDepositReceivedPayDao billDepositReceivedPayDao) {
		this.billDepositReceivedPayDao = billDepositReceivedPayDao;
	}

	/**
	 * @return the billDepositReceivedService
	 */
	public IBillDepositReceivedService getBillDepositReceivedService() {
		return billDepositReceivedService;
	}

	/**
	 * @param billDepositReceivedService
	 *            the billDepositReceivedService to set
	 */
	public void setBillDepositReceivedService(IBillDepositReceivedService billDepositReceivedService) {
		this.billDepositReceivedService = billDepositReceivedService;
	}

	/**
	 * @return the statementOfAccountService
	 */
	public IStatementOfAccountService getStatementOfAccountService() {
		return statementOfAccountService;
	}

	/**
	 * @param statementOfAccountService
	 *            the statementOfAccountService to set
	 */
	public void setStatementOfAccountService(IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	/**
	 * @return the settlementCommonService
	 */
	public ISettlementCommonService getSettlementCommonService() {
		return settlementCommonService;
	}

	/**
	 * @param settlementCommonService
	 *            the settlementCommonService to set
	 */
	public void setSettlementCommonService(ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @return the fossToFinanceRemittanceService
	 */
	public IFossToFinanceRemittanceService getFossToFinanceRemittanceService() {
		return fossToFinanceRemittanceService;
	}

	/**
	 * @param fossToFinanceRemittanceService
	 *            the fossToFinanceRemittanceService to set
	 */
	public void setFossToFinanceRemittanceService(IFossToFinanceRemittanceService fossToFinanceRemittanceService) {
		this.fossToFinanceRemittanceService = fossToFinanceRemittanceService;
	}

	/**
	 * @return the orgAdministrativeInfoService
	 */
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	/**
	 * @param orgAdministrativeInfoService
	 *            the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @return the billReceivableService
	 */
	public IBillReceivableService getBillReceivableService() {
		return billReceivableService;
	}

	/**
	 * @param billReceivableService
	 *            the billReceivableService to set
	 */
	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	/**
	 * @return the operatingLogService
	 */
	public IOperatingLogService getOperatingLogService() {
		return operatingLogService;
	}

	/**
	 * @param operatingLogService
	 *            the operatingLogService to set
	 */
	public void setOperatingLogService(IOperatingLogService operatingLogService) {
		this.operatingLogService = operatingLogService;
	}

    public ICustInvoiceMarkQueryService getCustInvoiceMarkQueryService() {
        return custInvoiceMarkQueryService;
    }

    public void setCustInvoiceMarkQueryService(ICustInvoiceMarkQueryService custInvoiceMarkQueryService) {
        this.custInvoiceMarkQueryService = custInvoiceMarkQueryService;
    }

    public void setFinanceOnlinePayWSProxy(IFinanceOnlinePayWSProxy financeOnlinePayWSProxy) {
        this.financeOnlinePayWSProxy = financeOnlinePayWSProxy;
    }

	public ISaleDepartmentService getSaleDepartmentService() {
		return saleDepartmentService;
	}

	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}


}
