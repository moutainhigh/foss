package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.IValidateRfcAndCancelService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ValidateRfcAndCancelDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IBillBadAccountService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillCashCollectionService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsOutvehicleFeeAdjustService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService;
import com.deppon.foss.util.define.FossConstants;

/** 
 * foss对接整车运单更改、作废财务规则校验
 * @author foss结算-306579-guoxinru 
 * @date 2016-5-18 下午4:48:39    
 */
public class ValidateRfcAndCancelService implements IValidateRfcAndCancelService {
	
	/**
	 * 日志
	 */
	private static final Logger logger = LogManager.getLogger(ValidateRfcAndCancelService.class);
	
	/**
	 * 运单服务类
	 */
	private IWaybillManagerService waybillManagerService;
	
    /**
     * 接送货偏线service,始发更改时判断是否存在有效的偏线外发单
     * */
	private IExternalBillService externalBillService;
	
	/**
	 * 应收单服务
	 */
	private IBillReceivableService billReceivableService;
	
	/**
	 * 应付单服务
	 */
	private IBillPayableService billPayableService;
	
	/**
	 * 对账单服务
	 */
	private IStatementOfAccountService statementOfAccountService;
	
	/**
	 * 代收货款通用服务
	 */
	private ICodCommonService codCommonService;
	
	/**
	 * 坏账服务
	 */
	private IBillBadAccountService billBadAccountService;
	
	/**
	 * 现金收款单服务
	 */
	private IBillCashCollectionService billCashCollectionService;
	
	/**
	 * 系统配置参数服务
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 营业部查询
	 */
	private ISaleDepartmentService saleDepartmentService;
	
    /**
     * 注入 IVtsOutvehicleFeeAdjustService vtsOutvehicleFeeAdjustService
     * @param businessLockService
     */
    private IVtsOutvehicleFeeAdjustService vtsOutvehicleFeeAdjustService;
	
	/**
	 * foss对接整车运单更改、作废财务规则校验
	 * @param validateDto
	 * @param currentInfo
	 */
	@Override
	@Transactional
	public void validateRfcAndCancel(ValidateRfcAndCancelDto validateDto,CurrentInfo currentInfo) {
		//获取操作标识：1--运单更改；0--运单作废
		if("1".equals(validateDto.getOperate())){
			logger.info("foss对接vts运单更改财务校验开始...运单号："+validateDto.getWaybillNo());
			//运单更改财务单据规则校验
			validateRfc(validateDto.getWaybillNo());
			
		}else if("0".equals(validateDto.getOperate())){
			logger.info("foss对接vts运单作废财务校验开始...运单号："+validateDto.getWaybillNo());
			//运单作废财务单据规则校验
			validateCancel(validateDto.getWaybillNo(),currentInfo);
		}
	}

	/**
	 * 运单更改财务单据规则校验
	 * @param waybillNo
	 */
	private void validateRfc(String waybillNo) {
		// MANA-386 整车取消收款核销后不能发更改的限制 by lufeifei
		// 查询运单，主要用来判断整车是否已经首款核销
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		if(waybillEntity == null){
			throw new SettlementException("查询运单失败");
		}
		// 是否整车：是-Y 否-N
		String isWholeVehicle = waybillEntity.getIsWholeVehicle();
		
		// 接送货已经对偏线外发反馈已录入进行了校验
		//ISSUE-3260 对外发单限制 
		 externalBillService.validateIsExistExternalBill(waybillNo);		
		
		 Date now = new Date();
		 
		//一、校验应收单
		// 运单对应的应收单已核销或部分核销
		// 运单存在始发运费应收单或到付运费应收单，客户在网上营业厅进行了支付锁定
		List<String> statementBillNos = new ArrayList<String>(); // 对账单号集合
		List<BillReceivableEntity> listR = billReceivableService.queryBillReceivableByCondition(new BillReceivableConditionDto(waybillNo));
		if(null!=listR){
			for (BillReceivableEntity e : listR) {
				if(!SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__WOODEN_OTEHR_RECEIVABLE.equals(e.getBillType())){
					if(StringUtils.isNotEmpty(e.getStatementBillNo())
							&& !SettlementConstants.DEFAULT_BILL_NO.equals(e
									.getStatementBillNo())){
						statementBillNos.add(e.getStatementBillNo());
					}
					// MANA-386 整车取消收款核销后不能发更改的限制 by lufeifei
//					if(!"Y".equals(isWholeVehicle)){
						if (e.getUnverifyAmount().compareTo(e.getAmount()) < 0) {
							throw new SettlementException("对应的应收单已经核销，不能进行发更改操作");
						}
//					}
					if (e.getUnlockDateTime() != null
							&& now.before(e.getUnlockDateTime())) {
						throw new SettlementException("客户在网上已经将运单进行了锁定,不能进行发更改操作");
					}
				}
			}
		}
		
		//二、校验应付单
		// 运单对应的应付单已核销或部分核销
		// 运单存在对应服务补救、理赔申请、坏账申请，并且对应的处理状态为申请中或者处理已完结
		List<BillPayableEntity> listP = billPayableService.queryBillPayableByCondition(new BillPayableConditionDto(waybillNo));
		if(null!=listP){
			for (BillPayableEntity e : listP) {
				if(!SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__WOODEN_PAYABLE.equals(e.getBillType()) 
						&& !SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__WOODEN_OTEHR_PAYABLE.equals(e.getBillType())){
					if(StringUtils.isNotEmpty(e.getStatementBillNo()) && !SettlementConstants.DEFAULT_BILL_NO.equals(e
							.getStatementBillNo())){
						statementBillNos.add(e.getStatementBillNo());
					}
					
					// MANA-386 整车取消首款核销后不能发更改的限制 by lufeifei
//					if(!"Y".equals(isWholeVehicle)){
						if (e.getUnverifyAmount().compareTo(e.getAmount()) < 0) { // 存在核销操作
							throw new SettlementException("对应的应付单已经核销，不能进行发更改操作");
						}
//					}
					
					// 校验服务补救、理赔申请；坏账校验在应收单是否核销中校验
					if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM
							.equals(e.getBillType())
							|| SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION
									.equals(e.getBillType())) {
						throw new SettlementException("该单存在相应的服务补救、理赔申请，不能进行发更改操作");
					}
				}
			}
		}
		
		//三、校验对账单
		// 运单对应的客户对账单已确认、核销、付款、还款
		List<String> statementNos = statementOfAccountService
				.queryConfirmStatmentOfAccount(statementBillNos);
		if (CollectionUtils.isNotEmpty(statementNos)) {
			throw new SettlementException("该单存在相应的客户对账单已确认、核销、付款、还款，不能进行发更改操作");
		}
		
		//四、校验代收货款
		// 代收货校验
		CODEntity codEntity = codCommonService.queryByWaybill(waybillNo);
		if (codEntity != null && FossConstants.ACTIVE.equals(codEntity.getActive())) {

			// 代收货款应付单的支付状态为资金部冻结、汇款中、已汇款之一
			List<String> status = new ArrayList<String>();
			status.add(SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE);
			status.add(SettlementDictionaryConstants.COD__STATUS__RETURNING);
			status.add(SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE_APPLICATION);
			status.add(SettlementDictionaryConstants.COD__STATUS__NEGATIVE_RETURN_SUCCESS);
			status.add(SettlementDictionaryConstants.COD__STATUS__RETURNED);

			if (status.contains(codEntity.getStatus())) {
				throw new SettlementException("代收货款对应的状态为资金部冻结或者是汇款中时无法红冲现金收款单，不能进行发更改操作");
			}
		}
		
		//五、校验坏账
		// 坏账校验
		int i = billBadAccountService.queryByWaybillNO(waybillNo);
		if (i > 0) {
			throw new SettlementException("坏账申请审批完成，不能进行发更改操作");
		}

	}
	
	/**
	 * 运单作废财务单据规则校验
	 * @param waybillNo
	 * @param currentInfo
	 */
	private void validateCancel(String waybillNo, CurrentInfo currentInfo) {
		// 坏账校验
		int i = billBadAccountService.queryByWaybillNO(waybillNo);
		if (i > 0) {
			throw new SettlementException("坏账申请审批完成，运单不允许更改");
		}
		
		//ddw
		String status = querydiscountPayable(waybillNo);
		
		//一、校验现金收款单
		// 查询现金收款单
		List<BillCashCollectionEntity> billCashes = billCashCollectionService.queryBySourceBillNOs(Arrays.asList(waybillNo),
						SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL,
						FossConstants.ACTIVE);
		// 校验现金收款单
		billCashCollectionService.validateWaybillForBillCashCollection(billCashes);
		
		// 红冲现金收款单校验
		if (CollectionUtils.isNotEmpty(billCashes)) {
			for (BillCashCollectionEntity entity : billCashes) {
				// 已签收，不能红冲
				if (entity.getConrevenDate() != null) {
					throw new SettlementException("该单已经被签收，不允许进行红冲现金收款单操作");
				}
				
				//当集中接货时，红单的现金收款单的收款部门是发更改的部门
				if(null!=currentInfo){
					entity.setCollectionOrgCode(currentInfo.getCurrentDeptCode());
					entity.setCollectionOrgName(currentInfo.getCurrentDeptName());
				}

				validateWriteBackBillCashCollection(entity,currentInfo);
			}
		}
		
		//二、校验应收单
		// 查询应收单（始发应收、到达应收）
		BillReceivableConditionDto billReceivableConditionDto = new BillReceivableConditionDto(waybillNo);
		//ddw,如果折扣单已经确认，不红冲始发应收和到达应收
		if(SettlementDictionaryConstants.DISCOUNT_BILL_STATUS_CONFIRM.equals(status)){
			billReceivableConditionDto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD});
		}else{
			billReceivableConditionDto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD});
		}
		List<BillReceivableEntity> billReceives = billReceivableService
				.queryBillReceivableByCondition(billReceivableConditionDto);

		// 校验应收单合法性
		billReceivableService.validateWaybillForBillReceivable(billReceives);
		
		// 红冲应收单，包括代收货款
		if (CollectionUtils.isNotEmpty(billReceives)) {
			for (BillReceivableEntity entity : billReceives) {
				validateWriteBackReceivable(entity,currentInfo);
			}
		}
		
		//三、校验应付单
		// 查询应付单（装卸费应付）
		BillPayableConditionDto billPayableConditionDto = new BillPayableConditionDto(
				waybillNo);
		billPayableConditionDto
				.setBillTypes(new String[] {
						SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE,
						SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD,
						SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__FREIGHT_DISCOUNT});//添加零担事后折应付,ddw
		List<BillPayableEntity> billPayables = billPayableService
				.queryBillPayableByCondition(billPayableConditionDto);

		// 校验应付单合法性
		billPayableService.validateWaybillForBillPayable(billPayables);
		if (CollectionUtils.isNotEmpty(billPayables)) {
			for (BillPayableEntity entity : billPayables) {
				//红冲应付单校验
				validateWriteBackPayable(entity, currentInfo);
			}
		}
		
		//四、整车首尾款应付单校验
		BillPayableConditionDto conDto = new BillPayableConditionDto();
		conDto.setWaybillNo(waybillNo);//单号
		conDto.setActive("Y");//有效
		conDto.setBillTypes(new String[]{SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST});//整车尾款TL2
		conDto.setIsRedBack("N");//非红单
		//查询整车尾款应付单list
		List<BillPayableEntity> TL2list = vtsOutvehicleFeeAdjustService.queryTLBillPayableByWaybillNo(conDto);
		//如果整车尾款应付单list非空
		if (CollectionUtils.isNotEmpty(TL2list)) {
			BillPayableEntity entity = TL2list.get(0);
			this.validateBillPayableEntity(entity);
		}
		conDto.setBillTypes(new String[]{SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST});//整车首款TF2
		//查询整车首款应付单list
		List<BillPayableEntity> TF2List = vtsOutvehicleFeeAdjustService.queryTLBillPayableByWaybillNo(conDto);
		//如果整车首款应付单list非空
		if (CollectionUtils.isNotEmpty(TF2List)) {
			BillPayableEntity entity = TF2List.get(0);
			this.validateBillPayableEntity(entity);
		}
	}
	
	/**
	 * 红冲应付单校验
	 * @param entity
	 * @param currentInfo
	 */
/*	private void validateWriteBackBillPayable(BillPayableEntity entity,CurrentInfo currentInfo) {
		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getSourceBillNo())) {
			throw new SettlementException("红冲应付单参数不能为空！");
		}

		logger.info("entering service, sourceBillNo: "
				+ entity.getSourceBillNo());

		// 已核销,不能红冲
		if (entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			throw new SettlementException("应付单已核销,不能红冲应付单");
		}

		// 以下应付单审核后才能核销
		// 长途外请车/整车成本、航空公司成本、空运出发代理成本、空运中转代理成本、空运到达代理成本、空运其他应付、偏线成本
		if (Arrays.asList(SettlementConstants.AUDIT_OR_UNAUDIT_TYPES).contains(entity.getBillType()) &&
				SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE.equals(entity.getApproveStatus())) {
			throw new SettlementException("应付单必须先反审核才能进行红冲操作");
		}

		// 校验对账单
		if (StringUtils.isEmpty(entity.getStatementBillNo())
				|| SettlementConstants.DEFAULT_BILL_NO.equals(entity
						.getStatementBillNo())) {
			// do nothing
		} else {
			//对账单号不为空时，需要查询对账单记录，
			//看对账单记录是否已经被确认了。
			List<String> list = statementOfAccountService
					.queryConfirmStatmentOfAccount(Arrays.asList(entity
							.getStatementBillNo()));
			if (CollectionUtils.isEmpty(list)) { 
				// 对账单未确认,则更新对账相关信息
				StatementOfAccountUpdateDto dto = new StatementOfAccountUpdateDto();
				dto.setPayableEntityList(Arrays.asList(entity));
				statementOfAccountService.updateStatementAndDetailForRedBack(
						dto, currentInfo);
			} else { 
				// 对账单已确认则不允许红冲单据
				throw new SettlementException("该单存在相应的客户对账单已确认、核销、付款、还款，不能进行红冲");
			}
		}		
	}*/
	
	/**
	 * 红冲应付单校验
	 * @param entity
	 * @param currentInfo
	 */
	private void validateWriteBackPayable(BillPayableEntity entity,CurrentInfo currentInfo) {
		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getSourceBillNo())) {
			throw new SettlementException("红冲应付单参数不能为空！");
		}

		logger.info("entering service, sourceBillNo: "
				+ entity.getSourceBillNo());
		
		// 已签收，不能红冲
		if (entity.getSignDate() != null) {
			throw new SettlementException("该单已经被签收，不允许进行红冲应付单操作");
		}

		// 已核销,不能红冲
		if (entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			throw new SettlementException("应付单已核销,不能红冲应付单");
		}

		// 以下应付单审核后才能核销
		// 长途外请车/整车成本、航空公司成本、空运出发代理成本、空运中转代理成本、空运到达代理成本、空运其他应付、偏线成本
		if (Arrays.asList(SettlementConstants.AUDIT_OR_UNAUDIT_TYPES).contains(entity.getBillType()) &&
				SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE.equals(entity.getApproveStatus())) {
			throw new SettlementException("应付单必须先反审核才能进行红冲操作");
		}

		// 校验对账单
		if (StringUtils.isEmpty(entity.getStatementBillNo())
				|| SettlementConstants.DEFAULT_BILL_NO.equals(entity
						.getStatementBillNo())) {
			// do nothing
		} else {
			//对账单号不为空时，需要查询对账单记录，
			//看对账单记录是否已经被确认了。
			List<String> list = statementOfAccountService
					.queryConfirmStatmentOfAccount(Arrays.asList(entity
							.getStatementBillNo()));
			if (CollectionUtils.isNotEmpty(list)) {
				// 对账单已确认则不允许红冲单据
				throw new SettlementException("该单存在相应的客户对账单已确认、核销、付款、还款，不能进行红冲");
			}
		}		
	}

	/**
	 * 红冲应收单校验
	 * @param entity
	 * @param currentInfo
	 */
	private void validateWriteBackReceivable(BillReceivableEntity entity,CurrentInfo currentInfo) {
		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| StringUtils.isEmpty(entity.getReceivableNo())
				|| entity.getAccountDate() == null) {
			throw new SettlementException("红冲应收单参数不能为空！");
		}

		logger.info("entering service, sourceBillNo: "
				+ entity.getSourceBillNo());

		Date now = Calendar.getInstance().getTime();

		// 当前系统时间，小于应收单的解锁时间时不能被红冲
		if (entity.getUnlockDateTime() != null
				&& now.before(entity.getUnlockDateTime())) {
			throw new SettlementException("应收单已被锁定，不能进行红冲应收单操作");
		}
		// 已签收，不能红冲
		if (entity.getConrevenDate() != null) {
			throw new SettlementException("该单已经被签收，不允许进行红冲应收单操作");
		}
		// 已核销,不能红冲
		if (entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			throw new SettlementException("应收单已核销，不能进行红冲应收单操作");
		}
		// 校验对账单
		if (StringUtils.isEmpty(entity.getStatementBillNo())
				|| SettlementConstants.DEFAULT_BILL_NO.equals(entity
						.getStatementBillNo())) {
			// do nothing
		} else {
			List<String> list = statementOfAccountService
					.queryConfirmStatmentOfAccount(Arrays.asList(entity
							.getStatementBillNo()));
			if (CollectionUtils.isNotEmpty(list)) { // 对账单未确认,则更新对账相关信息
				throw new SettlementException("该单存在相应的客户对账单已确认、核销、付款、还款，不能进行红冲应收单操作");
			}
		}
	}
	
	/**
	 * 红冲应收单校验
	 * @param entity
	 * @param currentInfo
	 */
/*	private void validateWriteBackBillReceivable(BillReceivableEntity entity,CurrentInfo currentInfo) {
		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| StringUtils.isEmpty(entity.getReceivableNo())
				|| entity.getAccountDate() == null) {
			throw new SettlementException("红冲应收单参数不能为空！");
		}

		logger.info("entering service, sourceBillNo: "
				+ entity.getSourceBillNo());

		Date now = Calendar.getInstance().getTime();

		// 当前系统时间，小于应收单的解锁时间时不能被红冲
		if (entity.getUnlockDateTime() != null
				&& now.before(entity.getUnlockDateTime())) {
			throw new SettlementException("应收单已被锁定，不能进行红冲应收单操作");
		}

		// 已核销,不能红冲
		if (entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			throw new SettlementException("应收单已核销，不能进行红冲应收单操作");
		}
		// 校验对账单
		if (StringUtils.isEmpty(entity.getStatementBillNo())
				|| SettlementConstants.DEFAULT_BILL_NO.equals(entity
						.getStatementBillNo())) {
			// do nothing
		} else {
			List<String> list = statementOfAccountService
					.queryConfirmStatmentOfAccount(Arrays.asList(entity
							.getStatementBillNo()));
			if (CollectionUtils.isEmpty(list)) { // 对账单未确认,则更新对账相关信息
				StatementOfAccountUpdateDto dto = new StatementOfAccountUpdateDto();
				dto.setReceivableEntityList(Arrays.asList(entity));
				statementOfAccountService.updateStatementAndDetailForRedBack(
						dto, currentInfo);
			} else { // 对账单已确认则不允许红冲单据
				throw new SettlementException(
						"该单存在相应的客户对账单已确认、核销、付款、还款，不能进行红冲应收单操作");
			}
		}
	}*/

	/**
	 * 红冲现金收款单校验
	 * @param entity
	 * @param currentInfo
	 */
	private void validateWriteBackBillCashCollection(BillCashCollectionEntity entity, CurrentInfo currentInfo) {
		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getSourceBillNo())) {
			throw new SettlementException("红冲现金收款单出错");
		}
		
		logger.info("entering service, sourceBillNo: " + entity.getSourceBillNo());

		//对记账日期进行格式化到秒
		Date now = Calendar.getInstance().getTime();
		
		//调用综合管理接口，查询结算业务最大红冲天数
		String span = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.STL_CASH_COLLECTION_WRITEBACK_DAY);
		
		//根据财务的记账日期和配置参数相加
		Date d = DateUtils.addDays(entity.getAccountDate(), Integer.valueOf(span));
		
		// 如果时间差超过30天
		if (now.after(d)) {
			throw new SettlementException("超出结算业务红冲的最大时间范围，不允许进行红冲现金收款单操作");
		}
	}

	/**
	 * 根据传入的一到多个运单单号，获取一到多条应收单信息
     * @author 邓大伟
     * @date 2015-02-03
     * @param wayBillNos  运单单号集合
	 */
	public String querydiscountPayable(String waybillNo){
		//查询折扣单
		String status = billPayableService.queryDiscountPayable(waybillNo);
		//循环处理结果集
		if(StringUtil.isNotBlank(status)){
			//判断折扣单状态是否为已确认
			if(SettlementDictionaryConstants.DISCOUNT_BILL_STATUS_NOT_CONFIRM.equals(status)){
				throw new SettlementException("该单已经做过折扣，并且折扣单是未确认状态，请作废折扣单在更改！");
			}
		}
		return status;
	}
	
	/**
	 * 验证应付单
	 * @author @218392-FOSS结算开发组-张永雪
	 * @date 2016-05-29 14：18:20
	 * @param payableEntity
	 */
	private void validateBillPayableEntity(BillPayableEntity payableEntity) {
		//判断传入应付单参数是否为空
		if (payableEntity == null) {
			throw new SettlementException("应付单信息为空");
		}
		//判断传入应付单参数是否核销
		if (payableEntity.getVerifyAmount() != null
				&& payableEntity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			throw new SettlementException("应付单已核销");
		}
		//判断传入应付单参数是否冻结
		if (SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__FROZEN
				.equals(payableEntity.getFrozenStatus())) {
			throw new SettlementException("应付单已冻结");
		}
		//判断传入应付单参数是否付款
		if (SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES
				.equals(payableEntity.getPayStatus())) {
			throw new SettlementException("应付单已付款");
		}
		
/*		//需求变更后，应付单审核后，不能进行修改和作废操作
		if (SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE
				.equals(payableEntity.getApproveStatus())) {
			throw new SettlementException("应付单已审核");
		}
*/		
		
		
	}
	
//	private boolean isPartnerDept(String orgCode){
//		//DDW
//		SaleDepartmentEntity entity = saleDepartmentService.querySaleDepartmentInfoByCode(orgCode);
//		//判断到达部门是否为合伙人，如果是合伙人则在PTP系统生成应收单
//		if(entity != null && FossConstants.YES.equals(entity.getIsLeagueSaleDept())){
//			return true;
//		}else{
//			return false;
//		}
//	}
//	
//	private boolean isOldWaybill(WaybillEntity waybillEntity){
//		String configString = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.PKP_PTP_SIGN_INIT_410);
//		long intTime = 0;
//		if (StringUtils.isNotBlank(configString)) {
//			intTime = com.deppon.foss.util.DateUtils.convert(configString.trim(), com.deppon.foss.util.DateUtils.DATE_TIME_FORMAT).getTime();
//		}
//        long destTime = waybillEntity.getBillTime().getTime();
//        if(destTime < intTime){
//        	return true;
//        }else{
//        	return false;
//        }
//	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setExternalBillService(IExternalBillService externalBillService) {
		this.externalBillService = externalBillService;
	}

	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	public void setBillBadAccountService(
			IBillBadAccountService billBadAccountService) {
		this.billBadAccountService = billBadAccountService;
	}

	public void setBillCashCollectionService(
			IBillCashCollectionService billCashCollectionService) {
		this.billCashCollectionService = billCashCollectionService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setVtsOutvehicleFeeAdjustService(
			IVtsOutvehicleFeeAdjustService vtsOutvehicleFeeAdjustService) {
		this.vtsOutvehicleFeeAdjustService = vtsOutvehicleFeeAdjustService;
	}
	

}
