/**
 * Copyright 2013 STL TEAM
 */
package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.server.dao.IWaybillEntityForEcsDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ICUBCGrayService;
import com.deppon.foss.module.settlement.common.api.server.service.IPODService;
import com.deppon.foss.module.settlement.common.api.server.service.IQueryIsVehicleassembleForEcs;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PODEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCCodAuditRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCCodAuditResultDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayResultDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.common.server.util.ListComparator;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICodAuditDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodAuditLogService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodAuditService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILineSignService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ITakingService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditLogEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CodAuditDto;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPartnerPayStatementDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IBillDepWriteoffBillRecService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerPayStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepWriteoffBillRecDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepositReceivedDto;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 签收确认收入服务（接送货专线签收、接送货空运偏线签收、接送货签收变更调用）.
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-10-13 下午1:56:11
 * @since
 * @version
 */
public class LineSignService implements ILineSignService {
	
	private String queryCodAuditListUrl;
	public void setQueryCodAuditListUrl(String queryCodAuditListUrl) {
		this.queryCodAuditListUrl = queryCodAuditListUrl;
	}
	
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	
	private static final String SERVICE_CODE = "com.deppon.foss.module.settlement.consumer.server.service.impl.LineSignService";
	
	private ICUBCGrayService cUBCGrayService;
	
	public void setcUBCGrayService(ICUBCGrayService cUBCGrayService) {
		this.cUBCGrayService = cUBCGrayService;
	}

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger(LineSignService.class);

	/** 应付单公共Service. */
	private IBillPayableService billPayableService;

	/** 确认（反确认）收入 Service. */
	private ITakingService takingService;

	/**合伙人付款对账单 Dao.*/
	private IPartnerPayStatementDao partnerPayStatementDao;  
	 	
	/** 代收货款Service. */
	private ICodCommonService codCommonService;

	/** 对账单Service. */
	private IStatementOfAccountService statementOfAccountService;

	/** 接送货更改单Service. */
	private IWaybillRfcService waybillRfcService;

	/** 接送货运单Service. */
	private IWaybillManagerService waybillManagerService;

	/** 查询配置参数表. */
	private IConfigurationParamsService configurationParamsService;

	/** 财务签收记录Service. */
	private IPODService podService;

	/** 还款单Service. */
	private IBillRepaymentService billRepaymentService;
	
	/** 查询配载单记录的 */
	private IVehicleAssembleBillService vehicleAssembleBillService;
	
	/** 插入代收货款审核表 */
	private ICodAuditService codAuditService;
	
	/**在线通知*/
    private IMessageService messageService;
	/** 返回值. */
	private static final int RESULT_VALUE = 1;

	private static final String VALIDATE_PARAM_MES_NOT = "反";
	/**
	 * 注入插入审核日志的 codAuditLogService
	 */
	private ICodAuditLogService codAuditLogService;
	
	/**
	 * 应收单公用Service
	 */
	private IBillReceivableService billReceivableService;
	
	/**
	 * 待核销预收单、应收单进行查询、核销等操作service
	 */
	private IBillDepWriteoffBillRecService billDepWriteoffBillRecService;
	
	/**
	 * 结算公用Service
	 */
	private ISettlementCommonService settlementCommonService;
	
	/**
	 * 核销service
	 */
	private IBillWriteoffService billWriteoffService;
	
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 代收审核
	 */
	private ICodAuditDao codAuditDao;
    
    /**
     * 查询第一次开单时候的代收金额
     * @return
     */
    private IWaybillDao waybillDao;
    
    /**
	 * 注入查询配载单接口
	 */
	private IQueryIsVehicleassembleForEcs queryIsVehicleassembleForEcs;
	
	/**
	 * 注入运单dao
	 */
	private IWaybillEntityForEcsDao waybillEntityForEcsDao;
    
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	/**
	 * 互斥锁接口
	 */
	private IBusinessLockService businessLockService;
	public IBusinessLockService getBusinessLockService() {
		return businessLockService;
	}


	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**pda签收运单锁*/	
	private static final String PDA_ENABLEPAYABLE_WAYBILL_LOCK = "运单锁"; 

	/**
	 * 公用的校验接口传入的参数.
	 * 
	 * @param dto
	 *            the dto
	 * @param meg
	 *            the meg
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-24 上午10:29:03
	 * @see
	 */
	private void validateParams(LineSignDto dto, boolean isSign) {

		String meg = isSign ? "" : VALIDATE_PARAM_MES_NOT;

		if (StringUtils.isEmpty(dto.getSignType())) {
			throw new SettlementException(String.format("%s签收类型不能为空", meg));
		}
		if (!SettlementConstants.LINE_SIGN.equals(dto.getSignType())//专线签收
				&& !SettlementConstants.PARTIAL_LINE_SIGN.equals(dto.getSignType())//偏线签收
				&& !SettlementConstants.AIR_SIGN.equals(dto.getSignType())//空运签收
				&& !SettlementConstants.LAND_STOWAGE_SIGN.equals(dto.getSignType())) {//快递代理签收
			throw new SettlementException(String.format("%s签收类型不合法！", meg));
		}
		if (StringUtils.isEmpty(dto.getWaybillNo())) {
			throw new SettlementException("运单号为空");
		}
		if (!StringUtils.isNumeric(dto.getWaybillNo())) {
			throw new SettlementException("输入的运单号不合法！");
		}
		if (StringUtils.isEmpty(dto.getSignOrgCode())) {
			throw new SettlementException(String.format("%s签收部门编码为空", meg));
		}

		LOGGER.info(" 开始调用接送货接口验证规则 ");

		// 调用接送货接口，判断运单信息是否存在
		if (SettlementConstants.EXTEND_SYSTEM_WAYBILL_IS_EXISTST) {// 验证运单是否存在开关
			boolean bl = waybillManagerService.isWayBillExsits(dto.getWaybillNo());
			if (!bl) {
				throw new SettlementException("运单信息不存在。");
			}
		}

		// 接送货判断是否存在未受理的更改单【接送货接口 waybillRfcService 】
		boolean b = waybillRfcService.isExsitsWayBillRfc(dto.getWaybillNo());
		if (b) {
			// 存在未受理的更改单
			throw new SettlementException("运单" + dto.getWaybillNo() + "存在未受理的更改单");
		}
		if (dto.getSignDate() == null) {
			throw new SettlementException(String.format("%s签收日期不能为空", meg));
		}

		LOGGER.info(" 调用接送货校验规则成功！ ");
	}


	/**
	 * 确认签收服务.
	 * 
	 * @param dto
	 *            【 waybillNo 运单号 productType 运输性质 signOrgCode 签收部门编码
	 *            signOrgName 签收部门名称 signDate 签收日期 isWholeVehicle 是否整车运单
	 *            isPdaSign 是否PDA签收 signType 签收类型 】
	 * @param currentInfo
	 *            the current info
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-13 下午3:47:08
	 */
	@Override
	public void confirmTaking(LineSignDto dto, CurrentInfo currentInfo) {
		if (dto == null) {
			throw new SettlementException("接口参数不能为空！");
		}
		
		List<MutexElement> elements = null;
		//如果为PDA签收则添加锁BUG-52335 
		if(FossConstants.YES.equals(dto.getIsPdaSign())){
		LOGGER.info("PDA签收加锁:"+dto.getWaybillNo());
		elements = new ArrayList<MutexElement>();
		MutexElement element = new  MutexElement(dto.getWaybillNo(),PDA_ENABLEPAYABLE_WAYBILL_LOCK,MutexElementType.PDA_SIGNAL_CASHCONFIRM);
	    elements.add(element);
	    boolean canLock =  businessLockService.lock(elements,SettlementConstants.BUSINESS_LOCK_BATCH);
	    if(!canLock){
	    throw new SettlementException("该单据正在收银确认，请稍后再签收");
	    }
		}

		LOGGER.info(" 开始确认签收操作---- 运单号： " + dto.getWaybillNo());

		this.validateParams(dto, true);
		
		// BUG-47371 不存在签收记录或者最新签收记录不为POD的可以继续签收操作
		// foss-092036-bochenlong
		PODEntity p = this.podService.queryNewestPOD(dto.getWaybillNo());
		if (p != null
				&& p.getPodType().equals(SettlementDictionaryConstants.POD_ENTITY__POD_TYPE__PROOF_OF_DELIVERY)) {
			throw new SettlementException("此单已经签收，不能再做签收");
		}
		
		Date date = new Date();

		// 签收日期与系统当前时间相差分钟数超过某一阀值M调用,大于这个值不能进行签收操作【综合管理系统】
		// 调用综合管理接口进行查询配置参数
		String confValue = configurationParamsService
				.queryConfValueByCode(ConfigurationParamsConstants.STL_POD_DELAY_MINUTE);
		if (StringUtils.isNotEmpty(confValue) && StringUtils.isNumeric(confValue)) {
			long k = Long.valueOf(confValue);// 设定分钟

			// 两个时间的相差分钟数
			Long hk = DateUtils.getMinuteDiff(dto.getSignDate(), date);// 签收日期与系统时间
			if (k > 0 && hk.longValue() > k) {// 大于两者之间的分钟数大于设定分钟k，不能进行签收
				throw new SettlementException("签收日期与系统当前时间相差分钟数不能超过" + k + "分钟");
			}
		}

		// 应收单、现金收款单确认收入
		this.takingService.confirmIncome(dto, currentInfo);

		// 生效应付单
		this.effectBillPayable(dto, currentInfo);

		// 新增签收记录表服务
		PODEntity podEntity = this.getPODEntity(dto.getWaybillNo(),
				SettlementDictionaryConstants.POD_ENTITY__POD_TYPE__PROOF_OF_DELIVERY,
				new Date());
		podService.addPOD(podEntity, currentInfo);		
		//对PDA签收的单据解锁
		if(elements!= null){
			//解锁应付单
			businessLockService.unlock(elements);
			LOGGER.info("解锁PDA签收运单成功:"+dto.getWaybillNo());
		}
		//ddw,核销始发提成、委托派费应收单
		PaymentSettlementDto paymentSettlementDto = new PaymentSettlementDto();
		paymentSettlementDto.setWaybillNo(dto.getWaybillNo());
		confirmPTPPaymentAndWriteOff(paymentSettlementDto,currentInfo);
		
		LOGGER.info(" 确认签收操作成功----  ");
	}

	/**
	 * 新增签收记录表服务.
	 * 
	 * @param waybillNo
	 *            the waybill no
	 * @param podType
	 *            the pod type
	 * @param date
	 *            the date
	 * @return the pOD entity
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-11 上午11:05:54
	 */
	private PODEntity getPODEntity(String waybillNo, String podType, Date date) {
		PODEntity entity = new PODEntity();
		entity.setId(UUIDUtils.getUUID());// ID
		entity.setWaybillNo(waybillNo);// 运单号
		entity.setPodDate(date);// 签收/反签收日期
		entity.setPodType(podType);// 签收/反签收类型
		return entity;
	}

	/**
	 * 签收-生效应付单
	 * 
	 * 为了减少程序if判断的复杂度把原有的void方法，改成有返回值的方法.
	 * 
	 * @param dto
	 *            the dto
	 * @param currentInfo
	 *            the current info
	 * @return the int
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-13 下午3:47:33
	 */
	private int effectBillPayable(LineSignDto dto, CurrentInfo currentInfo) {
		if (dto == null) {
			throw new SettlementException("接口参数不能为空");
		}

		LOGGER.info(" 确认签收-开始生效应付单，运单号：" + dto.getWaybillNo());

		// 查询是否存在代收货款应付单、整车尾款应付单，装卸费应付单
		// 更新装卸费应付单的签收日期，便于生效装卸费应付单时使用
		BillPayableConditionDto payableConditionDto = new BillPayableConditionDto();
		payableConditionDto.setWaybillNo(dto.getWaybillNo());
		payableConditionDto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE});//增加快递代理成本应付单新增签收时间
		List<BillPayableEntity> billPayables = billPayableService
				.queryBillPayableByCondition(payableConditionDto);
		if (CollectionUtils.isEmpty(billPayables)) {
			return RESULT_VALUE;
		}

		// 设置存在结算单据标记
		dto.setStlBillCounts(1);

		// 验证应付单
		List<BillPayableEntity> lists = checkBillPayableEntity(dto, billPayables);
		BillPayableEntity codBillPayable = this.getBillPayableEntity(lists,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		
		//BUG-26904:整车尾款应付单可以有多个
		//查询整车尾款应付单
		Predicate predicate = new BeanPropertyValueEqualsPredicate(SettlementConstants.BILL_TYPE,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST);
		@SuppressWarnings("unchecked")
		List<BillPayableEntity> truckBillPayables = (List<BillPayableEntity>) CollectionUtils.select(lists,
				predicate);

		// 签收时，更新装卸费应付单的签收日期
		BillPayableEntity sfBillPayable = this.getBillPayableEntity(lists,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE);
		//签收时，更新快递代理成本应付单的签收日期
		BillPayableEntity ldBillPayable = this.getBillPayableEntity(lists,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE);
		//DDW
		BillPayableEntity fcBillPayable = this.getBillPayableEntity(lists,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE);
		List <BillPayableEntity> fBillPayable = this.getPDFPBillPayableEntity(lists,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE);
		BillPayableEntity ddBillPayable = this.getBillPayableEntity(lists,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE);
		//签收时，更新理赔应付单的签收日期       268217
		BillPayableEntity cBillPayable = this.getBillPayableEntity(lists,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
		String configString = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.PKP_PTP_SIGN_INIT_410);
		WaybillEntity waybillEntity = waybillManagerService.queryPartWaybillByNo(dto.getWaybillNo());
		long intTime = 0;
		if (StringUtils.isNotBlank(configString)) {
			intTime = DateUtils.convert(configString.trim(), DateUtils.DATE_TIME_FORMAT).getTime();
		}
        long destTime = waybillEntity.getBillTime().getTime();
		
		if (codBillPayable != null && StringUtils.isNotEmpty(codBillPayable.getId())
				&& (!isPartnerDept(codBillPayable.getDestOrgCode())
						|| destTime < intTime)) {

			// 已经应收单确认收入时，查询出了代收货款应收单了

			BillReceivableEntity billReceivableEntity = dto.getCodReceivableEntity();
			if (billReceivableEntity == null || StringUtils.isEmpty(billReceivableEntity.getId())) {
				throw new SettlementException("不存在代收货款应收单");
			}

			// 专线和空运有代收货款业务，偏线没有代收货款业务（空运审核空运代收货款后，生效代收货款应付单）
			if (SettlementConstants.LINE_SIGN.equals(dto.getSignType())) {

				// 当未核销金额大于0时，提示异常信息
				if (billReceivableEntity.getUnverifyAmount() != null
						&& billReceivableEntity.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
					throw new SettlementException("代收货款应收单存在未核销金额，不能进行签收操作");
				}
								
				/**
				 * 这个地方把是否PDA签收的判断去掉，不管是否PDA签收都要校验
				 * @218392 zhangyongxue 代收货款是否PDA签收校验优化
				 * @date 2015-12-03
				 */
				this.enableBillPayableByPdaSign(billReceivableEntity, codBillPayable, dto,
						currentInfo);
				
				
//				if (FossConstants.YES.equals(dto.getIsPdaSign())) {
//					// PDA签收生效代收款应付单，不满足生效条件时，修改代收货款应付单的签收日期
//					this.enableBillPayableByPdaSign(billReceivableEntity, codBillPayable, dto,
//							currentInfo);
//				} else {
//
//					// 生效代收货款应付单
//					this.billPayableService.enableBillPayable(codBillPayable, dto.getSignDate(),
//							currentInfo);					
//				
//				}				
				
							
				
			} else if (SettlementConstants.AIR_SIGN.equals(dto.getSignType()) 
						||SettlementConstants.LAND_STOWAGE_SIGN.equals(dto.getSignType())) {

				// 空运/快递代理签收，有代收货款的情况下需要设置代收货款应付单的-签收日期，不生效空运代收货款应付单
				codBillPayable.setSignDate(dto.getSignDate());
				this.billPayableService.updateBillPayableSignDateByConfirmIncome(codBillPayable,
						currentInfo);
			}
			
			
			
			
		}

		// 整车运单，签收时生效整车尾款应付单
		if (SettlementConstants.LINE_SIGN.equals(dto.getSignType())
				&& FossConstants.YES.equals(dto.getIsWholeVehicle())// 为整车运单
				&& CollectionUtils.isNotEmpty(truckBillPayables)) {
			
			for (BillPayableEntity truckBillPayable : truckBillPayables) {
				
				// 生效应付单方法
				this.billPayableService.enableBillPayable(truckBillPayable, dto.getSignDate(),
						currentInfo);
				
			}
		}

		// 生效装卸费是定时任务，在签收接口中只更新装卸费应付单的签收日期
		if (sfBillPayable != null && StringUtils.isNotEmpty(sfBillPayable.getId())) {

			sfBillPayable.setSignDate(dto.getSignDate());
			this.billPayableService.updateBillPayableSignDateByConfirmIncome(sfBillPayable,
					currentInfo);
		}

		// 更新快递代理成本应付单的签收时间
		if (ldBillPayable != null && StringUtils.isNotEmpty(ldBillPayable.getId())) {

			ldBillPayable.setSignDate(dto.getSignDate());
			this.billPayableService.updateBillPayableSignDateByConfirmIncome(ldBillPayable,
					currentInfo);
		}

		// 生效应付单方法    268217
		if (cBillPayable != null && StringUtils.isNotEmpty(cBillPayable.getId())) {
			if(ArriveSheetConstants.SITUATION_UNNORMAL_BREAK.equals(dto.getSignSituation())
					|| ArriveSheetConstants.SITUATION_UNNORMAL_DAMP.equals(dto.getSignSituation())
					|| ArriveSheetConstants.SITUATION_UNNORMAL_POLLUTION.equals(dto.getSignSituation())
					|| ArriveSheetConstants.SITUATION_UNNORMAL_GOODSHORT.equals(dto.getSignSituation())
					|| ArriveSheetConstants.SITUATION_UNNORMAL_ELSE.equals(dto.getSignSituation())){
				Date dd=null;
				this.billPayableService.enableBillPayable(cBillPayable, dd,currentInfo);
				this.RecAndPayChang(dto.getWaybillNo(),currentInfo);
			}
		}
		//DDW
		// 更新合伙人到付运费应付单的签收时间
		if (fcBillPayable != null && StringUtils.isNotEmpty(fcBillPayable.getId())) {

			this.billPayableService.enableBillPayable(fcBillPayable,dto.getSignDate(),currentInfo);
		}
		// 更新合伙人到达提成应付单的签收时间
		if(CollectionUtils.isNotEmpty(fBillPayable)){
			for (int i = 0; i < fBillPayable.size(); i++) {
				if(StringUtils.isNotEmpty(fBillPayable.get(i).getId())){
					this.billPayableService.enableBillPayable(fBillPayable.get(i),dto.getSignDate(),currentInfo);
				}
			}
		}
		// 更新合伙人委托派费应付单的签收时间
		if (ddBillPayable != null && StringUtils.isNotEmpty(ddBillPayable.getId())) {

			this.billPayableService.enableBillPayable(ddBillPayable,dto.getSignDate(),currentInfo);
		}
		
		//合伙人签收生效代收货款
		if (codBillPayable != null && StringUtils.isNotEmpty(codBillPayable.getId()) 
				&& isPartnerDept(codBillPayable.getDestOrgCode())
				&& destTime > intTime) {
			
			this.billPayableService.enableBillPayable(codBillPayable,dto.getSignDate(),currentInfo);
		}
		LOGGER.info("确认签收-生效应付单成功！");

		return RESULT_VALUE;
	}

	/**
	 * PDA签收时，代收货款应收单的应收金额小于等于综合管理配置参数中设定的代收货款金额， 生效应付单
	 * 新需求：签收+收银确认两者同时具备才生效代收货款应付单. @date 2015-12-15 @218392 张永雪
	 * @param billReceivableEntity
	 *            the bill receivable entity
	 * @param codBillPayable
	 *            the cod bill payable
	 * @param dto
	 *            the dto
	 * @param currentInfo
	 *            the current info
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-20 下午5:04:17
	 */
	@SuppressWarnings("null")
	private void enableBillPayableByPdaSign(BillReceivableEntity billReceivableEntity,
			BillPayableEntity codBillPayable, LineSignDto dto, CurrentInfo currentInfo) {	
		
		// 签收时，代收货款应收单的应收金额小于等于综合管理配置参数中设定的代收货款金额， 生效应付单
		BigDecimal maxAmount = null;
		Date cashConfirmTime = null;//收银确认时间 
		String paymentType = null;
		String confValue = configurationParamsService
				.queryConfValueByCode(ConfigurationParamsConstants.STL_COD_PDA_CONFIRM_AMOUNT);// 调用综合管理接口进行查询
		if (StringUtils.isNotEmpty(confValue) && StringUtils.isNumeric(confValue)) {
			maxAmount = NumberUtils.createBigDecimal(confValue);
		}

		// 代收货款应收单的应收金额小于等于maxAmount，生效应付单
		if (billReceivableEntity.getAmount() != null
				&& billReceivableEntity.getAmount().compareTo(maxAmount) < 1) {

			// 生效代收货款应付单
			this.billPayableService.enableBillPayable(codBillPayable, dto.getSignDate(),
					currentInfo);
		} else {

			/*
			 * 2013-01-21日防止签收前（代收货款还款单已经收银确认）变更， 根据运单号查询出来的实收货款还款单（来源单据类型：代收货款）
			 * 如果有数据且收银确认状态：已确认，直接生效代收货款应付单
			 */
			BillRepaymentConditionDto repaymentConDto = new BillRepaymentConditionDto();
			repaymentConDto.setWaybillNo(dto.getWaybillNo());
			repaymentConDto.setActive(FossConstants.YES);

			// 来源单据类型：代收货款
			repaymentConDto
					.setSourceBillTypes(new String[] { SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__COD });
			List<BillRepaymentEntity> repayList = this.billRepaymentService
					.queryBillRepaymentByCondition(repaymentConDto);

			// 代表对应的实收代收货款还款单是否已经收银确认
			boolean bl = false;

			// 还款单集合不为空
			if (CollectionUtils.isNotEmpty(repayList)) {

				// 对查询到得还款单repayEntity进行排序，按照记账日期accountDate从前往后进行排序
				ListComparator orderComparator = new ListComparator(
						SettlementConstants.ACCOUNT_DATE);
				Collections.sort(repayList, orderComparator);

				// 获取最后一条还款单记录，判断是否已经收银确认
				BillRepaymentEntity repayEntity = repayList.get((repayList.size() - 1));

				// 还款单状态为已经收银确认
				if (repayEntity != null
						&& SettlementDictionaryConstants.BILL_REPAYMENT__STATUS__CONFIRM
								.equals(repayEntity.getStatus())) {
					bl = true;
					cashConfirmTime = repayEntity.getCashConfirmTime();
					paymentType = repayEntity.getPaymentType();
				}
				//ISSUE-3250 如果还款单未收银确认PDA派送签收的运单，金额超过X元时（目前系统配置的X=0），需要提醒到达部门做收银确认
				else{
									
					InstationJobMsgEntity entiy = new InstationJobMsgEntity();
					//发送人和发送部门信息
					entiy.setSenderCode(currentInfo.getEmpCode());
					entiy.setSenderName(currentInfo.getEmpName());
					entiy.setSenderOrgCode(currentInfo.getCurrentDeptCode());
					entiy.setSenderOrgName(currentInfo.getCurrentDeptName());
					//设置为代收货款消息
					entiy.setMsgType(MessageConstants.MSG_TYPE__COLLECTION);						
					//接受方式为组织
					entiy.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
					//设置接收部门信息
					entiy.setReceiveOrgCode(repayEntity.getCollectionOrgCode());
					entiy.setReceiveOrgName(repayEntity.getCollectionOrgName());
					//设置
					entiy.setContext("你部门签收的还款单："+codBillPayable.getWaybillNo()+"含有代收货款应收已经签收，请及时做收银确认操作");
					messageService.createBatchInstationMsg(entiy);
					
				}
			}
			if (bl) {
				// bl为true最后一条代收货款还款单记录已经收银确认，生效代收货款应付单
				this.billPayableService.enableBillPayable(codBillPayable, dto.getSignDate(),
						currentInfo);
				/**
				 * @author 218392 张永雪
				 * @date 2015-12-15 09:45:27
				 * 针对有效的代收数据，审批筛选条件如下（如下条件满足任意一个则为需审核数据）： 
				 *	1、	单笔代收退款金额≥80000元
				 *	2、	单笔代收货款金额更改额绝对值≥50000元的
				 *	3、	出发部门=到达部门
				 *	4、	签收时间-开单时间≤12小时
				 *	5、	23：00-次日8：00之间签收的代收货款数据
				 *	6、	存在代收款的运单，货物轨迹中无配载单记录的。
				 *
				 *筛选的数据必须同时满足以下条件：
				 *	1、	此次改动，不涉及快递代理代收流程、空运代收流程。只针对专线单据。
				 *	2、	单据类型必须为有代收货款的单据
				 *	3、	单据状态必须为已经正常签收
				 *	4、	单据状态必须为已经收银确认
				 *		备注：单据为满足以上条件的零担、快递单据
				 *
				 * @author 310970 曹朋
				 * @date 2016-3-21 10:06:28
				 * 针对有效的代收数据，新增三个审核筛选条件（条件如下。若需筛选的数据满足三个筛选条件任意一个，则直接流至资金复核小组进行审核。）
				 * 1.单笔代收即日退≥30000，单笔代收三日退（审核退）≥50000
				 * 2.付款方式为“电汇”的所有单据
				 * 3.代收货款更改受理时间与签收时间之差小于1小时的
				 * 筛选的数据必须同时满足以下条件：
				 *	1、	此次改动，不涉及快递代理代收流程、空运代收流程。只针对专线单据。
				 *	2、	单据类型必须为有代收货款的单据
				 *	3、	单据状态必须为已经正常签收
				 *	4、	单据状态必须为已经收银确认
				 *	5、  单据	必须经过上述已有的6个筛选条件之后，再进行新增的3个条件的 筛选
				 */
				
				/**  1、单笔代收退款金额≥80000元   
				 * */
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				BigDecimal codeAmount= codBillPayable.getAmount();//单笔代收退款金额
				BigDecimal amount1 = new BigDecimal(80000);//定义80000元
				int result = codeAmount.compareTo(amount1);//1大于，-1小于，0是等于
				LOGGER.info("单笔代收货款退款金额为：" + codeAmount + "...和80000比较的结果是：" + result);
				
				/**  2、单笔代收货款金额更改额绝对值≥50000元的  
				 * */
				String waybillNo = codBillPayable.getWaybillNo();//运单号
				if(!StringUtils.isNotEmpty(waybillNo)){
					throw new SettlementException("运单号为空！");
				}
				WaybillEntity entity= waybillDao.queryFirstWaybillByWaybillNo(waybillNo);//通过运单编号查询第一次开单时运单信息
				BigDecimal waybillCodAmount = entity.getCodAmount();//第一次开单时候的代收金额
				/**
				 * @author 326181
				 * update 为快递是需要查找第一条代收退款应付单的amount
				 * amountCompare（差值） = codeAmount - 最初的代收退款金额
				 */
				if(SettlementUtil.isPackageProductCode(entity.getProductCode())){
					//TODO 查询第一条代收退款应付单的amount
					waybillCodAmount = this.billPayableService.selectFirstPayableAmountByWaybillNo(waybillNo);
				}
				LOGGER.info("通过运单编号查询第一次开单时的金额为：" + waybillCodAmount);
				BigDecimal amountCompare = codeAmount.subtract(waybillCodAmount);//差值
				BigDecimal amount2 = new BigDecimal(50000);//定义80000元
				//代收更改金额
				BigDecimal absAmount = amountCompare.abs();//绝对值
				int num = absAmount.compareTo(amount2);//1大于，-1小于，0是等于
				LOGGER.info("单笔代收货款金额更改额绝对值为：" + absAmount + "...和50000比较的结果是：" + num);
				
				/**  3、出发部门=到达部门
				 *   */
				String  origOrgcode = codBillPayable.getOrigOrgCode();//出发部门(取自应付单)
				String destOrgcode 	= codBillPayable.getDestOrgCode();//到达部门(取自应付单)
				LOGGER.info("出发部门编码为：" + origOrgcode +",到达部门编码为：" + destOrgcode);
				Boolean isEquals = false;
				if(StringUtils.isNotEmpty(origOrgcode) || StringUtils.isNotEmpty(destOrgcode)){
					isEquals = origOrgcode.equals(destOrgcode);
				}else{
					throw new SettlementException("出发部门或到达部门为空！");
				}
				
				/**  4、签收时间-开单时间≤12小时  
				 * */
				Date signTime = dto.getSignDate();//签收时间
				Date billTime = entity.getBillTime();//开单时间
				LOGGER.info("签收的时间是：" + dateFormat.format(signTime));
				LOGGER.info("开单的时间是：" + dateFormat.format(billTime));
				double subTime = Math.abs(signTime.getTime() - billTime.getTime());
				LOGGER.info("(收银确认操作)开单的时间差毫秒数为：" + subTime);
				Boolean isOverTime = false;
				if(subTime < (12*3600000)){//签收时间是否小于12个小时
					isOverTime = true;
				}
				//签收开单时长单位小时，用于插入代收审核表中
				int signBillDiffer = (int) (subTime/3600000.0) ;
				
				/**  
				 * 5、线上BUG修复 2016-01-11 13:41:50 放到上线版本为0114
				 * 签收时间在签收当天8:00之前, 23：00之后的代收货款数据
				 */
				Calendar cal = Calendar.getInstance();
				cal.setTime(signTime);
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 000);
				Date date23 = cal.getTime();//获取当天23:00
				LOGGER.info("(收银确认操作)当天23点00分000毫秒的时间是：" + dateFormat.format(date23));
				
				Calendar cal8 = Calendar.getInstance();
				cal8.setTime(signTime);
				cal8.set(Calendar.HOUR_OF_DAY, 8);
				cal8.set(Calendar.MINUTE, 0);
				cal8.set(Calendar.SECOND, 0);
				cal8.set(Calendar.MILLISECOND, 000);
				Date date8 = cal8.getTime();//获取次日08:00:00:000
				LOGGER.info("(收银确认操作)当天8点00分00秒000毫秒的时间是：" + dateFormat.format(date8));
				LOGGER.info("签收时间为：" + dateFormat.format(signTime));
				
				long signTimeLon = signTime.getTime();
				long date23Lon 	 = date23.getTime();
				long date8Lon	 = date8.getTime();
				
				Boolean timeIsBetween = false;
				if(signTimeLon >= date23Lon || signTimeLon <= date8Lon){
					timeIsBetween = true;
				}
				
				/**  6、存在代收款的运单，货物轨迹中无交接单记录的  
				 * */
				String isY = "";
				Boolean isExist = false;
				isY = vehicleAssembleBillService.queryIsVehicleassembleByNo(waybillNo);
				/*isY = vehicleAssembleBillService.queryIsVehicleassembleByNo(waybillNo);
				if(StringUtils.equals("N", isY)){
					isExist = true;
				}*/
				
				/**
				 * @author 325369
				 * @date 2016-09-06
				 * 根据运单号查询运单表，判断是否是悟空运单，是则请求悟空接口查询配载单数，否则调用本地接口
				 */
				if (waybillEntityForEcsDao.queryWaybillIsEcsByWaybillNo(waybillNo) > 0) {
					isY = queryIsVehicleassembleForEcs.queryIsVehicleassembleForEcs(waybillNo);
					isExist = StringUtils.equals("N", isY) == true ? true : false;
				} else {
					isY = vehicleAssembleBillService.queryIsVehicleassembleByNo(waybillNo);
					isExist = StringUtils.equals("N", isY) == true ? true : false;
				}
				/**
				 * 新增的三个筛选条件1：单笔代收即日退≥30000，单笔代收三日退（审核退）≥50000
				 * */
				//R1, 单笔代收即日退≥30000
				boolean codconditionR1=false;
				if(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY.equals(codBillPayable.getCodType())&&
						codeAmount.compareTo(new BigDecimal(30000))>=0){
					codconditionR1=true;
					LOGGER.info("单笔代收即日退退款金额为：" + codeAmount + "...和30000比较的结果是：" + codeAmount.compareTo(new BigDecimal(30000)));
				}
				//R3,单笔代收三日退≥50000  SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY
				boolean codconditionR3=false;
				if(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY.equals(codBillPayable.getCodType())&&
						codeAmount.compareTo(new BigDecimal(50000))>=0){
					codconditionR3=true;
					LOGGER.info("单笔代收三日退退款金额为：" + codeAmount + "...和50000比较的结果是：" + codeAmount.compareTo(new BigDecimal(50000)));
				}
				//RA,单笔代收审核退≥50000  SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE
				boolean codconditionRA=false;
				if(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE.equals(codBillPayable.getCodType())&&
						codeAmount.compareTo(new BigDecimal(50000))>=0){
					codconditionRA=true;
					LOGGER.info("单笔代收审核退退款金额为：" + codeAmount + "...和50000比较的结果是：" + codeAmount.compareTo(new BigDecimal(50000)));
				}
				
				/**新增的三个筛选条件2:付款方式为“电汇”的所有单据
				 * */
				boolean codpayType = false;
				//获取应收单里的付款类型
				String  payType = billReceivableEntity.getPaymentType();
				//做判断，是否是电汇
				if(payType.equals(SettlementDictionaryConstants.BILL_CLAIM__PAYMENT_CATEGORIES__TELEGRAPH_TRANSFER)){
					codpayType = true;
				}
				
				/**新增的三个筛选条件3.代收货款更改受理时间与签收时间之差小于1小时的
				 * */
				boolean isChangeOverTime = false;
				//定义Dto存放单号
				CodAuditDto codAuditDto = new CodAuditDto();
				codAuditDto.setWaybillNo(waybillNo);
				//获取更改时间
				List<CodAuditEntity>  codAuditEntityList= codAuditService.queryCodChangeTime(codAuditDto);
				if(CollectionUtils.isNotEmpty(codAuditEntityList)){
					//排序,按照
					ListComparator codChangeEntity = new ListComparator("changeTime");
					Collections.sort(codAuditEntityList, codChangeEntity);
					//获取最后一条更改记录
					CodAuditEntity   codAuditEntity = codAuditEntityList.get((codAuditEntityList.size() - 1));
					//签收跟更改时间差
					double subChangeTime = Math.abs(signTime.getTime()-codAuditEntity.getChangeTime().getTime());
					LOGGER.info("(收银确认操作)运单更改的时间差毫秒数为：" + subChangeTime);
					if(subChangeTime<(1*3600000)){
						isChangeOverTime = true;
					}
				}
				/** 判断:满足任意一个则为需审核数据
				 *  */
				if((result>-1) || (num>-1) || (isEquals) || (isOverTime) || (timeIsBetween) || (isExist)){
					//1.插入 stl.t_stl_cod_audit,状态为 复核会计待审核-RA
					CodAuditEntity record = new CodAuditEntity();
					record.setId(UUIDUtils.getUUID());
					record.setWaybillNo(waybillNo);//单号
					record.setActive("Y");
					record.setCodAmount(codeAmount);//代收金额
					record.setLockStatus(SettlementDictionaryConstants.SETTLE_CODAUDIT_FUNDAUDIT);//资金部待审核状态
					record.setSigTime(signTime);//签收时间
					record.setOrigOrgCode(origOrgcode);//出发部门编码
					record.setOrigOrgName(codBillPayable.getOrigOrgName());//出发部门名称
					record.setDestOrgCode(destOrgcode);//到达部门编码
					record.setDestOrgName(codBillPayable.getDestOrgName());//到达部门名称
					record.setHasTrack(isY);//是否有走货路径Y/N 
					record.setCodType(codBillPayable.getCodType());//代收货款类型
					record.setPaymentType(paymentType);//付款方式
					record.setBillTime(billTime);//开单时间
					if(cashConfirmTime != null){
						record.setComfirmTime(cashConfirmTime);//收银确定时间
					}else{
						throw new SettlementException("收银时间为空！");
					}
					record.setAccountNo(codBillPayable.getAccountNo());//银行行号
					record.setCustomerCode(codBillPayable.getCustomerCode());//客户编码
					record.setCustomerName(codBillPayable.getCustomerName());//客户名称
					record.setMobileNo(codBillPayable.getCustomerPhone());//客户手机号码
					record.setCreateDate(new Date());//创建日期
					//悟空快递签收时无法获取FossUserContext数据 --add by 243921
					if(FossConstants.YES.equals(dto.getIsExpress())){ //快递
						record.setCreateUser(currentInfo.getUserName());//创建人
					}else{
						UserEntity user =  FossUserContext.getCurrentUser();
						String userCode = user != null ?user.getUserName():"";
						record.setCreateUser(userCode);//创建人
					}
					record.setBillSignDiffer(signBillDiffer);//签收开单时长
					record.setChangeAmount(absAmount);//代收更改金额
					
					/**
					 * @author 218392 ZYX
					 * 2016-10-17 签收的时候判断代收支付审核表中是否有数据，如果有则不再插入
					 */
					List<CodAuditEntity> codAuditEntities = codAuditService.queryCodDtoByWaybillNo(waybillNo);
					if(CollectionUtils.isNotEmpty(codAuditEntities)){
						LOGGER.info("单号：" + waybillNo +"在代收支付审核界面已存在，无需签收的时候再次插入");
					}else{
						codAuditService.addCodAudit(record);
						
						//2.同时还要往stl.t_stl_cod_audit_log日志表中插入,记录时效的时候把这表里的是否有效改成N 
		    			CodAuditDto audiDto = new CodAuditDto();
		    			List<String> waybillListNo = new ArrayList<String>();
						List<CodAuditLogEntity> logList = new ArrayList<CodAuditLogEntity>();
						waybillListNo.add(waybillNo);
						audiDto.setWaybillNos(waybillListNo);
						logList = buildCodAuditLogs(audiDto.getWaybillNos(),logList,"资金部待审核",currentInfo,dto.getIsExpress());
						if(logList != null && logList.size() > 0){
							codAuditLogService.insertBath(logList);//还是调用的批量插入
						}else{
							LOGGER.info("生效应付单的时候，插入日志的实体信息为空！");
						}
					}
				}else{
					if(codconditionR1||codconditionR3||codconditionRA||codpayType||isChangeOverTime){
						//1.插入 stl.t_stl_cod_audit,状态为 复核会计待审核-RA
						CodAuditEntity record = new CodAuditEntity();
						record.setId(UUIDUtils.getUUID());
						//单号
						record.setWaybillNo(waybillNo);
						record.setActive("Y");
						//代收金额
						record.setCodAmount(codeAmount);
						//SETTLE_CODAUDIT_REVIEWAUDIT复合会计待审核
						record.setLockStatus(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWAUDIT);
						//签收时间
						record.setSigTime(signTime);
						//出发部门编码
						record.setOrigOrgCode(origOrgcode);
						//出发部门名称
						record.setOrigOrgName(codBillPayable.getOrigOrgName());
						//到达部门编码
						record.setDestOrgCode(destOrgcode);
						//到达部门名称
						record.setDestOrgName(codBillPayable.getDestOrgName());
						//是否有走货路径Y/N
						record.setHasTrack(isY); 
						//代收货款类型
						record.setCodType(codBillPayable.getCodType());
						//付款方式
						record.setPaymentType(paymentType);
						//开单时间
						record.setBillTime(billTime);
						if(cashConfirmTime != null){
							//收银确定时间	
							record.setComfirmTime(cashConfirmTime);
						}else{
							throw new SettlementException("收银时间为空！");
						}
						//银行行号
						record.setAccountNo(codBillPayable.getAccountNo());
						//客户编码
						record.setCustomerCode(codBillPayable.getCustomerCode());
						//客户名称
						record.setCustomerName(codBillPayable.getCustomerName());
						//客户手机号码
						record.setMobileNo(codBillPayable.getCustomerPhone());
						//创建日期
						record.setCreateDate(new Date());
						//悟空快递签收时无法获取FossUserContext数据   --add by 243921
						if(FossConstants.YES.equals(dto.getIsExpress())){ //快递
							record.setCreateUser(currentInfo.getUserName());//创建人
						}else{
							UserEntity user =  FossUserContext.getCurrentUser();
							String userCode = user != null ?user.getUserName():"";
							//创建人
							record.setCreateUser(userCode);
						}
						//签收开单时长
						record.setBillSignDiffer(signBillDiffer);
						//代收更改金额
						record.setChangeAmount(absAmount);
						
						/**
						 * @author 218392 ZYX
						 * 2016-10-17 签收的时候判断代收支付审核表中是否有数据，如果有则不再插入
						 */
    					List<CodAuditEntity> codAuditEntities = codAuditService.queryCodDtoByWaybillNo(waybillNo);
    					if(CollectionUtils.isNotEmpty(codAuditEntities)){
    						LOGGER.info("单号：" + waybillNo +"在代收支付审核界面已存在，无需签收的时候再次插入");
    					}else{
    						//插入代收货款
    						codAuditService.addCodAudit(record);
    						
    						//2.同时还要往stl.t_stl_cod_audit_log日志表中插入,记录时效的时候把这表里的是否有效改成N 
    		    			CodAuditDto audiDto = new CodAuditDto();
    		    			List<String> waybillListNo = new ArrayList<String>();
    						List<CodAuditLogEntity> logList = new ArrayList<CodAuditLogEntity>();
    						waybillListNo.add(waybillNo);
    						audiDto.setWaybillNos(waybillListNo);
    						logList = buildCodAuditLogs(audiDto.getWaybillNos(),logList,"复合会计组待审核",currentInfo,dto.getIsExpress());
    						if(logList != null && logList.size() > 0){
    							//还是调用的批量插入
    							codAuditLogService.insertBath(logList);
    						}else{
    							LOGGER.info("生效应付单的时候，插入日志的实体信息为空！");
    					
    						}
    					}
					}	
				}
				
			}else {
				// 更新代收货款应付单的签收日期
				codBillPayable.setSignDate(dto.getSignDate());
				this.billPayableService.updateBillPayableSignDateByConfirmIncome(codBillPayable,
						currentInfo);
			}
		}// 2013-01-21 需求变更结束
		
	}

	public IMessageService getMessageService() {
		return messageService;
	}


	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

    /**
     * 创建日志集合
     * @param waybillNos
     * @param logEntities
     * @param operatContext
     * update by 231434-bieyexiong 悟空反签收时，无法获取FossUserContext数据所以换成CurrentInfo
     * update by 243921-zhangtingting 添加是否快递的标识
     */
    public List<CodAuditLogEntity> buildCodAuditLogs(List<String> waybillNos ,
                                   List<CodAuditLogEntity> logEntities,
                                   String operatContext,CurrentInfo currentInfo,String isExpress){
    	String userCode = "";
    	String deptCode = "";
    	String deptName = "";
    	
    	//是快递
    	if(FossConstants.YES.equals(isExpress)){
    		userCode = currentInfo.getEmpCode();
    		deptCode = currentInfo.getCurrentDeptCode();
    		deptName = currentInfo.getCurrentDeptName();
    	}else{
    		UserEntity user =  FossUserContext.getCurrentUser();
    		userCode = user != null ?user.getUserName():"";
    		deptCode = FossUserContext.getCurrentDeptCode();
    		deptName = FossUserContext.getCurrentDeptName();
    	}
    	
        //遍历运单号，进行封装日志信息
        if(CollectionUtils.isNotEmpty(waybillNos)){
            for(String waybillNo:waybillNos){
                CodAuditLogEntity entity = new CodAuditLogEntity();
                entity.setId(UUID.randomUUID().toString());
                entity.setWaybillNo(waybillNo);
                entity.setModifyUser(userCode);
                entity.setModifyDate(new Date());
                entity.setCreateUser(userCode);
                entity.setOperateContent(operatContext);
                entity.setOperateDeptcode(deptCode);
                entity.setOperateDeptname(deptName);
                entity.setOperateTime(new Date());
                entity.setCreateDate(new Date());
                logEntities.add(entity);
            }
        }
        return logEntities;
    }

	/**
	 * 根据一个单据类型获取应付单实体.
	 * 
	 * @param lists
	 *            the lists
	 * @param billType
	 *            the bill type
	 * @return the bill payable entity
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-3 下午4:01:20
	 * @see
	 */
	@SuppressWarnings("unchecked")
	private BillPayableEntity getBillPayableEntity(List<BillPayableEntity> lists, String billType) {
		if (CollectionUtils.isEmpty(lists)) {
			return null;
		}
		Predicate predicate = new BeanPropertyValueEqualsPredicate(SettlementConstants.BILL_TYPE,
				billType);
		List<BillPayableEntity> list = (List<BillPayableEntity>) CollectionUtils.select(lists,
				predicate);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据一个单据类型获取应付单实体.
	 * 
	 * @param lists
	 *            the lists
	 * @param billType
	 *            the bill type
	 * @return the bill payable entity
	 * @author 367638-foss-caodajun
	 * @date 2016-12-20 下午4:01:20
	 * @see
	 */
	@SuppressWarnings("unchecked")
	private List<BillPayableEntity> getPDFPBillPayableEntity(List<BillPayableEntity> lists, String billType) {
		
			if (CollectionUtils.isEmpty(lists)) {
				return null;
			}
			Predicate predicate = new BeanPropertyValueEqualsPredicate(SettlementConstants.BILL_TYPE,
					billType);
			List<BillPayableEntity> list = (List<BillPayableEntity>) CollectionUtils.select(lists,
					predicate);
			if (CollectionUtils.isNotEmpty(list)) {	
				return list;
			}
			return null;
	}
	
	/**
	 * 签收生效应付单-验证应付单数据.
	 * 
	 * @param dto
	 *            dto
	 * @param billPayables
	 *            the bill payables
	 * @return the list
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 下午4:13:06
	 * @see
	 */
	private List<BillPayableEntity> checkBillPayableEntity(LineSignDto dto,
			List<BillPayableEntity> billPayables) {
		if (dto == null) {
			throw new SettlementException("接口参数不能为空");
		}
		if (CollectionUtils.isEmpty(billPayables)) {
			return null;
		}

		// 验证同一个运单相同类型的应付单是否存在多条记录
		this.billPayableService.validateWaybillForBillPayable(billPayables);
		
		List<BillPayableEntity> list = new ArrayList<BillPayableEntity>();
		for (int i = 0; i < billPayables.size(); i++) {
			BillPayableEntity payableEntity = billPayables.get(i);

			// 代收货款应付单
			if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD
					.equals(payableEntity.getBillType())) {

				// 运输性质是偏线的情况下，偏线不存在代收货款业务
				if (SettlementConstants.PARTIAL_LINE_SIGN.equals(dto.getSignType())) {
					throw new SettlementException("偏线无代收货款业务,不能存在代收货款应付单，不能进行生效应付单操作");
				}
				list.add(payableEntity);
			}
			// 应付单类型：整车尾款应付单
			else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST.equals(payableEntity.getBillType())) {

				// 专线存在整车尾款应付单，签收类型不为专线时，抛出异常信息
				if (!SettlementConstants.LINE_SIGN.equals(dto.getSignType())) {
					throw new SettlementException("非专线签收，不能存在整车尾款应付单");
				}
				list.add(payableEntity);
			}
			// 应付单类型：装卸费应付单
			else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(payableEntity.getBillType())) {
				list.add(payableEntity);
			//快递代理成本应付单	
			}else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE.equals(payableEntity.getBillType())){
				list.add(payableEntity);
				//理赔应付单
			}else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(payableEntity.getBillType())){
				list.add(payableEntity);
			//合伙人到付运费应付单
			}else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE.equals(payableEntity.getBillType())){
				list.add(payableEntity);
			//合伙人到达提成应付单
			}else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE.equals(payableEntity.getBillType())){
				list.add(payableEntity);
			//合伙人到达提成应付单
			}else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE.equals(payableEntity.getBillType())){
				list.add(payableEntity);
			}
		}
		return list;
	}

	/**
	 * 反确认签收.
	 * 
	 * @param dto
	 *            【 waybillNo 运单号 productType 运输性质 signOrgCode 反签收部门编码
	 *            signOrgName 反签收部门名称 businessDate 业务日期 】
	 * @param currentInfo
	 *            the current info
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-13 下午3:47:16
	 */
	@Override
	public void reverseConfirmTaking(LineSignDto dto, CurrentInfo currentInfo) {
		if (dto == null) {
			throw new SettlementException("接口传入参数不能为空");
		}

		LOGGER.info(" 开始反签收操作---- 运单号： " + dto.getWaybillNo());

		// 反签收，验证
		this.validateParams(dto, false);
		
		// BUG-47371 不存在签收单、未签收状态的运单不允许反签收
		// foss-092036-bochenlong
		PODEntity p = this.podService.queryNewestPOD(dto.getWaybillNo());
		if (p == null) {
			throw new SettlementException("不存在签收记录，不能反签收");
		} else if (!p.getPodType().equals(SettlementDictionaryConstants.POD_ENTITY__POD_TYPE__PROOF_OF_DELIVERY)) {
			throw new SettlementException("运单未签收，不能反签收");
		}
		
		Date date = new Date();

		// 2013-04-16：反签收日期与系统当前时间相差分钟数超过某一阀值M调用,大于这个值不能进行签收操作【综合管理系统】
		String confValue = configurationParamsService
				.queryConfValueByCode(ConfigurationParamsConstants.STL_POD_DELAY_MINUTE);
		if (StringUtils.isNotEmpty(confValue) && StringUtils.isNumeric(confValue)) {
			long k = Long.valueOf(confValue);// 设定分钟

			// 两个时间的相差分钟数
			Long hk = DateUtils.getMinuteDiff(dto.getSignDate(), date);// 签收日期与系统时间
			if (k > 0 && hk.longValue() > k) {// 大于两者之间的分钟数大于设定分钟k，不能进行签收
				throw new SettlementException("签收日期与系统当前时间相差分钟数不能超过" + k + "分钟");
			}
		}

		// 坏账申请，不能反签收

		// 2013-05-20修改: ISSUE-2874

		// int i =
		// this.billBadAccountService.queryByWaybillNO(dto.getWaybillNo());
		// if (i > 0) {
		// throw new SettlementException("该运单存在坏账信息，不能进行反签收操作！");
		// }

		// 只有专线和空运有代收货款 不能存在资金部冻结、退款中、退款成功的代收货款信息
		if (SettlementConstants.LINE_SIGN.equals(dto.getSignType())
				|| SettlementConstants.AIR_SIGN.equals(dto.getSignType())
				|| SettlementConstants.LAND_STOWAGE_SIGN.equals(dto.getSignType())) {
			List<String> codWaybillNos = new ArrayList<String>();
			codWaybillNos.add(dto.getWaybillNo());
			CODEntity cod = this.codCommonService.queryByWaybill(dto.getWaybillNo());
			if (cod != null) {

				// 空运反签收时
				if ((SettlementConstants.AIR_SIGN.equals(dto.getSignType())
						||SettlementConstants.LAND_STOWAGE_SIGN.equals(dto.getSignType()))
						&& SettlementDictionaryConstants.COD__AIR_STATUS__AUDIT_AGREE.equals(cod
								.getAirStatus())) {
					throw new SettlementException("空运/快递代理代收货款已审核，不能反签收操作");
				}
				if (SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE.equals(cod.getStatus())
						|| SettlementDictionaryConstants.COD__STATUS__RETURNING.equals(cod
								.getStatus())
						|| SettlementDictionaryConstants.COD__STATUS__RETURNED.equals(cod
								.getStatus())) {
					throw new SettlementException("存在退款中或退款成功的代收货款，不能反签收操作");
				}
			}
		}

		// 专线反签收需要判断是否存在手工核销的应收单
		// 2013-05-20修改：ISSUE-2874
		// if (SettlementConstants.LINE_SIGN.equals(dto.getSignType())) {
		//
		// List<BillWriteoffEntity> bills = this.billWriteoffService
		// .queryHandWriteoffReceivableByWaybillNo(dto.getWaybillNo());
		// if (CollectionUtils.isNotEmpty(bills)) {
		// throw new SettlementException("存在手工核销的应收单，不能反签收");
		// }
		//
		// }

		// 判断不能存在服务补救应付单和理赔应付单
		BillPayableConditionDto billPayableConDto = new BillPayableConditionDto();
		billPayableConDto.setWaybillNo(dto.getWaybillNo());

		// 专线反签收
		if (SettlementConstants.LINE_SIGN.equals(dto.getSignType())) {

			billPayableConDto.setBillTypes(new String[] {
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM,// 理赔应付单
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION,// 服务补救应付单
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD,// 代收货款应付单
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST,// 整车尾款应付单
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE, // 装卸费应付单
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE, // 合伙人到付运费应付
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE, // 合伙人到达提成应付
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE, // 合伙人委托派费应付
					SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__COD // 合伙人代收货款应付
					});
		} else if (SettlementConstants.AIR_SIGN.equals(dto.getSignType())) {
			// 空运失效装卸费应付单、后续去掉代收货款应付单的签收日期
			billPayableConDto.setBillTypes(new String[] {
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE // 装卸费应付单
					, SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD // 代收货款应付单
					,SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE, // 合伙人到付运费应付
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE, // 合伙人到达提成应付
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE, // 合伙人委托派费应付
					SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__COD // 合伙人代收货款应付
					});
		} else if (SettlementConstants.PARTIAL_LINE_SIGN.equals(dto.getSignType())) {
			// 偏线只需要失效装卸费应付单
			billPayableConDto
					.setBillTypes(new String[] { SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE // 装卸费应付单
							,SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE, // 合伙人到付运费应付
							SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE, // 合伙人到达提成应付
							SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE, // 合伙人委托派费应付
							SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__COD, // 合伙人代收货款应付
							SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__PARTIAL_LINE // 外发单
					});
		}else if(SettlementConstants.LAND_STOWAGE_SIGN.equals(dto.getSignType())){
			// 偏线只需要失效装卸费应付单
			billPayableConDto
					.setBillTypes(new String[] { SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE // 装卸费应付单
					 , SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD,
					 SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE // 装卸费应付单
					 ,SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE, // 合伙人到付运费应付
						SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE, // 合伙人到达提成应付
						SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE, // 合伙人委托派费应付
						SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__COD // 合伙人代收货款应付
					});
		}
		List<BillPayableEntity> billPayables = this.billPayableService
				.queryBillPayableByCondition(billPayableConDto);
		if (CollectionUtils.isNotEmpty(billPayables)) {

			// 设置是否存在财务单据，存在设置为1
			dto.setStlBillCounts(1);

			// 需要判断对应的对账单是否已经确认，已确认不能进行反签收操作
			List<String> sourcesStatementNos = new ArrayList<String>();

			// 反签收时，验证应付单信息
			checkBillPayableByReverseConfirmIncome(dto, billPayables, sourcesStatementNos);

			if (CollectionUtils.isNotEmpty(sourcesStatementNos)) {
				List<String> soaIds = statementOfAccountService
						.queryConfirmStatmentOfAccount(sourcesStatementNos);
				// 对账单已被确认，不能进行反签收操作
				if (CollectionUtils.isNotEmpty(soaIds)) {
					throw new SettlementException("应付单对应的对账单已被客户/代理确认，不能进行反签收操作");
				}
				//反签收操作时生成对账单的单据需要校验对应的对账单明细是否删除
				List <PartnerPayStatementEntity> partnerPayStatementEntity=partnerPayStatementDao.partnerPayqueryByStatementNos(sourcesStatementNos);
				if(CollectionUtils.isNotEmpty(partnerPayStatementEntity)&&partnerPayStatementEntity.size()>0){
					throw new SettlementException("此单号已生成付款对账单，请先删除此单号对应的对账单明细!");
				}
			}
		}

		// 反确认收入
		this.takingService.reverseConfirmIncome(dto, currentInfo);

		
		// 失效应付单
		/*
		 * 3.60特惠件 快递代理新增加的一种三级产品
		 * 
		 * 10562 2014年8月6日 上午11:37:12
		 */
		this.disableBillPayable(dto, billPayables, currentInfo);
		if (dto.getStlBillCounts() != 1 && !SettlementUtil.isPackageProductCode(dto.getProductType())) {
			throw new SettlementException("不存在财务单据，不能进行反签收操作！");
		}

		// 新增签收记录表服务
		PODEntity podEntity = this.getPODEntity(dto.getWaybillNo(),
				SettlementDictionaryConstants.POD_ENTITY__POD_TYPE__UN_PROOF_DELIVERY,
				new Date());
		podService.addPOD(podEntity, currentInfo);
		
		//ddw,反核销始发提成、委托派费应收单
		PaymentSettlementDto paymentSettlementDto = new PaymentSettlementDto();
		paymentSettlementDto.setWaybillNo(dto.getWaybillNo());
		unConfirmPTPPaymentAndWriteOff(paymentSettlementDto,currentInfo);
		
		LOGGER.info(" 反签收操作成功----  ");
	}

	/**
	 * 反签收时，验证应付单
	 * 
	 * 专线反签收校验---不能存在理赔/服务补救应付单,专线反签收代收货款/整车尾款/装卸费都需要验证 偏线和空运只需要验证装卸费应付单.
	 * 
	 * @param dto
	 *            the dto
	 * @param billPayables
	 *            the bill payables
	 * @param billType
	 *            the bill type
	 * @param sourcesStatementNos
	 *            the sources statement nos
	 * @return the list
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-25 上午8:15:28
	 */
	private List<String> checkBillPayableByReverseConfirmIncome(LineSignDto dto,
			List<BillPayableEntity> billPayables, List<String> sourcesStatementNos) {

		// 验证同一个运单相同类型的应付单是否存在多条记录
		this.billPayableService.validateWaybillForBillPayable(billPayables);

		String billName = null;

		for (int i = 0; i < billPayables.size(); i++) {
			BillPayableEntity billPayableEntity = billPayables.get(i);

			// 专线反签收校验---不能存在理赔/服务补救应付单
			if (SettlementConstants.LINE_SIGN.equals(dto.getSignType())) {
				if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM
						.equals(billPayableEntity.getBillType())) {
					throw new SettlementException("存在理赔类型应付单，不能进行反签收操作！");
				} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION
						.equals(billPayableEntity.getBillType())) {
					throw new SettlementException("存在服务补救应付单，不能进行反签收操作！");
				}
			}

			// 收集对账单号码集合
			if (StringUtils.isNotEmpty(billPayableEntity.getStatementBillNo())
					&& !SettlementConstants.DEFAULT_BILL_NO.equals(billPayableEntity
							.getStatementBillNo())) {
				sourcesStatementNos.add(billPayableEntity.getStatementBillNo());
			}
			if (SettlementConstants.LINE_SIGN.equals(dto.getSignType())) {
				// 专线反签收代收货款/整车尾款/装卸费都需要验证；偏线和空运只需要验证装卸费应付单
				billName = this.getBillPayableName(billPayableEntity.getBillType());
				validateBillPayable(billPayableEntity, billName);
			}
			// 偏线、空运反签收只验证装卸费应付单
			else if ((SettlementConstants.PARTIAL_LINE_SIGN.equals(dto.getSignType())// 偏线
					|| SettlementConstants.AIR_SIGN.equals(dto.getSignType())
					|| SettlementConstants.LAND_STOWAGE_SIGN.equals(dto.getSignType()))
					&& SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE
							.equals(billPayableEntity.getBillType())) {

				// 验证应付单信息
				billName = this.getBillPayableName(billPayableEntity.getBillType());
				validateBillPayable(billPayableEntity, billName);

				// 偏线和空运装卸费应付单验证通过之后，直接返回
				return sourcesStatementNos;
			}
		}
		return sourcesStatementNos;
	}

	/**
	 * 验证应付单公共方法提取.
	 * 
	 * @param billPayableEntity
	 *            the bill payable entity
	 * @param billName
	 *            the bill name
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-7 上午10:59:32
	 */
	private void validateBillPayable(BillPayableEntity billPayableEntity, String billName) {
		// 已核销金额大于0
		if (billPayableEntity.getVerifyAmount() != null
				&& billPayableEntity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			throw new SettlementException(billName + "应付单已核销，不能进行反签收操作！");
		}

		// 应付单正在付款中或已付款
		if (SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.equals(billPayableEntity
				.getPayStatus())) {
			throw new SettlementException(billName + "应付单付款中或已付款，不能进行反签收操作");
		}

		// 应付单在已冻结
		if (SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__FROZEN
				.equals(billPayableEntity.getFrozenStatus())) {
			throw new SettlementException(billName + "应付单已冻结，不能进行反签收操作");
		}
	}

	/**
	 * 根据单据类型获取应付单名称.
	 * 
	 * @param billType
	 *            the bill type
	 * @return the bill payable name
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-10 上午10:19:55
	 */
	private String getBillPayableName(String billType) {
		return DictUtil.rendererSubmitToDisplay(billType,
				DictionaryConstants.BILL_PAYABLE__BILL_TYPE);
	}

	/**
	 * 反签收-失效应付单 红冲原有应付单，生成蓝单.
	 * 
	 * @param dto
	 *            the dto
	 * @param billPayables
	 *            the bill payables
	 * @param currentInfo
	 *            the current info
	 * @return the int
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-29 上午10:26:25
	 * @see
	 */
	private int disableBillPayable(LineSignDto dto, List<BillPayableEntity> billPayables,
			CurrentInfo currentInfo) {
		if (dto == null) {
			throw new SettlementException("接口输入参数不能为空");
		}

		LOGGER.info(" 反签收-开始失效应付单操作，运单号为： " + dto.getWaybillNo());

		if (CollectionUtils.isEmpty(billPayables)) {
			return RESULT_VALUE;
		}
		CodAuditDto audiDto = new CodAuditDto();
		List<String> waybillNList = new ArrayList<String>();
		for (int i = 0; i < billPayables.size(); i++) {
			BillPayableEntity entity = billPayables.get(i);

			// 专线反签收失效代收货款、整车尾款
			if (SettlementConstants.LINE_SIGN.equals(dto.getSignType())
					&& (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD
							.equals(entity.getBillType()) || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST
							.equals(entity.getBillType()))) {
				/**
				 * @author 218392 zhangyongxue 2015-12-20 11:41:26
				 *资金代收货款审批状态为：FL（资金部锁定 ）  RL（复核会计锁定）  不能失效
				 */
				CodAuditDto codAuditDto = new CodAuditDto();
				List<String> waybillListNo = new ArrayList<String>();
				waybillListNo.add(dto.getWaybillNo());
				codAuditDto.setWaybillNos(waybillListNo);
				//代收货款审核灰度   353654 ------------------------ start
				String vestSystemCode = null;
	            try {
	            	ArrayList<String> arrayList = new ArrayList<String>();
                	arrayList.add(dto.getWaybillNo());
                	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".disableBillPayable",
                			SettlementConstants.TYPE_FOSS);
                	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
	            	List<VestBatchResult> list = response.getVestBatchResult();
	            	vestSystemCode = list.get(0).getVestSystemCode();		
				} catch (Exception e) {
					LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
					throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
				}
	            if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
	            	List<CodAuditEntity> codAuditEntityList = codAuditService.queryCodAuditByCondition(codAuditDto);
					if(CollectionUtils.isNotEmpty(codAuditEntityList)){
						CodAuditEntity codAuditEntity = codAuditEntityList.get(0);
						//判断是否是FL RL
						if((SettlementDictionaryConstants.SETTLE_CODAUDIT_FUNDLOCK.equals(codAuditEntity.getLockStatus()))
								||(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWLOCK.equals(codAuditEntity.getLockStatus()))){
							throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁!");
						}
					}
	            }
	            if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
						CUBCCodAuditRequestDto requestDto = new CUBCCodAuditRequestDto();
						requestDto.setWaybillNo(waybillListNo);
						CUBCCodAuditResultDto resultDto = null;
						try {
							resultDto = (CUBCCodAuditResultDto)HttpClientUtils.postMethod(requestDto,new CUBCCodAuditResultDto(),queryCodAuditListUrl);
						} catch (Exception e) {
							LOGGER.error("调用CUBC代收货款审核接口连接异常...");
							throw new SettlementException("服务器正忙,CUBC代收货款审核失败,请稍后重试");
						}
						if(resultDto != null){
							if(StringUtils.isNotBlank(resultDto.getMeg())){
								LOGGER.error("调用CUBC代收货款审核接口失败，异常信息：" + resultDto.getMeg());
								throw new SettlementException(resultDto.getMeg());	
							}
							List<com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto> auditList = resultDto.getCodAuditList();
							if(CollectionUtils.isNotEmpty(auditList)){
								com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto codAuditDto1 = auditList.get(0);
								if(codAuditDto1 != null){
									//判断是否是FL RL
									if((SettlementDictionaryConstants.SETTLE_CODAUDIT_FUNDLOCK.equals(codAuditDto1.getLockStatus()))
											||(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWLOCK.equals(codAuditDto1.getLockStatus()))){
										throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁!");
									}
								}
							}else{
								LOGGER.info("CUBC,单号："+dto.getWaybillNo()+"没有进入代收货款支付审核！");
							}
						}
					
	            }
	          //代收货款审核灰度   353654 ------------------------ end
				// 失效应付单
				entity.setSignDate(null);
				this.billPayableService.disableBillPayable(entity, currentInfo);
				
				/**
				 * @author 218392 zhangyongxue 
				 * 失效的时候把代收审核表中Y 变成N ，同时插入日志
				 */
				CodAuditEntity record = new CodAuditEntity();
				record.setWaybillNo(dto.getWaybillNo());//设置运单号
				record.setActive("N");//有效变成无效
				record.setModifyDate(new Date());// 修改日期
				record.setModifyUser(currentInfo.getEmpCode());//修改人
				
				codAuditDao.updateByWaybillNo(record);
				
				waybillNList.add(dto.getWaybillNo());
				List<CodAuditLogEntity> logList = new ArrayList<CodAuditLogEntity>();
				
				audiDto.setWaybillNos(waybillNList);
				logList = buildCodAuditLogs(audiDto.getWaybillNos(),logList,"失效应付单",currentInfo,dto.getIsExpress());
				if(logList != null && logList.size() > 0){
					codAuditLogService.insertBath(logList);//还是调用的批量插入
				}else{
					LOGGER.info("失效应付单的时候，插入日志的实体为空");
				}
				
				
			}
			else if (SettlementConstants.AIR_SIGN.equals(dto.getSignType())
					&& SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD
							.equals(entity.getBillType())) {

				// 空运反签收--去掉代收货款的签收日期
				this.billPayableService.updateBillPayableSignDateByReverseConfirmIncome(entity,
						currentInfo);
			}
			//快递代理代收货款
			else if(SettlementConstants.LAND_STOWAGE_SIGN.equals(dto.getSignType())&&
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD.equals(entity.getBillType())
					){
				//快递代理反签收
				this.billPayableService.updateBillPayableSignDateByReverseConfirmIncome(entity,
						currentInfo);
			}

			// 专线、偏线、空运、快递代理反签收都会失效装卸费应付单
			if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(entity
					.getBillType())) {

				// 失效应付单
				entity.setSignDate(null);
				this.billPayableService.disableBillPayable(entity, currentInfo);
			}
			//快递代理应付单--去掉签收日期
			if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE.equals(entity
					.getBillType())){
				// 空运反签收--去掉代收货款的签收日期
				this.billPayableService.updateBillPayableSignDateByReverseConfirmIncome(entity,
						currentInfo);
			}
			//ddw,失效合伙人应付单,合伙人到付运费应付,合伙人到达提成应付,合伙人委托派费应付
			if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE.equals(entity.getBillType())
					|| SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE.equals(entity.getBillType())
					|| SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE.equals(entity.getBillType())){
				//签收时间设置为空--SPBP-224
				entity.setSignDate(null);
				this.billPayableService.disableBillPayable(entity,	currentInfo);
			}
		}

		LOGGER.info("反签收-失效应付单成功");

		return RESULT_VALUE;
	}
	/**
	 * ISSUE-3464 由异常签收转正常签收
	 * @author lianghaisheng
	 * @date 2013-08-05
	 * */
	@Override
	public void reverseToNormalSignal(String waybillNo, CurrentInfo currentInfo) {
		if(waybillNo== null ||
				"".equals(waybillNo)){
			throw new SettlementException("运单号码不能为空！");
		}
		//根据运单号查询对应的应付单
		BillPayableConditionDto billPayableConDto = new BillPayableConditionDto(waybillNo);	
		//查询有效版本的理赔应付单、服务补救应付单、退运费应付单
		billPayableConDto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM// 理赔应付单
				});
		//有效应付单
		billPayableConDto.setActive(FossConstants.ACTIVE);
		//非红单
		billPayableConDto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		List<BillPayableEntity> billPayables = this.billPayableService
				.queryBillPayableByCondition(billPayableConDto);
		//循环遍历应付单是否存在理赔单据
		if(CollectionUtils.isNotEmpty(billPayables)){
		for (int i = 0; i < billPayables.size(); i++) {
			BillPayableEntity billPayableEntity = billPayables.get(i);
			if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM
						.equals(billPayableEntity.getBillType())) {
					throw new SettlementException("存在理赔类型应付单，不能将异常签收变为正常签收！");
				}
			}
		}
		
		
	}

	/**
	 * 签收核销合伙人始发应收
	 * @author 042792
	 * @param dto
	 */
	private void confirmPTPPaymentAndWriteOff(PaymentSettlementDto dto,CurrentInfo currentInfo) {
		if (dto == null) {
			throw new SettlementException("接口传入参数不能为空");
		}
		
		LOGGER.info("开始合伙人冲销，运单号为：" + dto.getWaybillNo());

		if (SettlementConstants.EXTEND_SYSTEM_WAYBILL_IS_EXISTST) {
			boolean bl = waybillManagerService.isWayBillExsits(dto.getWaybillNo());
			if (!bl) {
				throw new SettlementException("运单信息不存在。");
			}
		}

		// 接送货判断是否存在未受理的更改单【接送货接口 】
		boolean b = waybillRfcService.isExsitsWayBillRfc(dto.getWaybillNo());
		if (b) {
			throw new SettlementException("运单" + dto.getWaybillNo() + "存在未受理的更改单");
		}
		LOGGER.info(" 调用接送货校验规则成功！ ");
		
		//存放应收单查询过滤条件 dto
		BillReceivableConditionDto receivableDto = new BillReceivableConditionDto();
		//预收单参数dto
		BillDepositReceivedDto billDepositReceivedDto = new BillDepositReceivedDto();
		//核销操作Dto
		BillWriteoffOperationDto writeoffDto = new BillWriteoffOperationDto();
		receivableDto.setWaybillNo(dto.getWaybillNo());
		//查询出以下四类应收单
		receivableDto.setBillTypes(new String[]{SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE,
												SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE});
		// 查询合伙人应收单信息
		List<BillReceivableEntity> billReceivableLists = billReceivableService.queryBillReceivableByCondition(receivableDto);
		//如果应收单不为空
		if (CollectionUtils.isNotEmpty(billReceivableLists)) {
			//循环遍历应收单
			for (BillReceivableEntity billReceivableEntity : billReceivableLists) {
				//扣款状态不为空并且不等于扣款成功
				if(StringUtil.isNotEmpty(billReceivableEntity.getWithholdStatus()) 
						&& !SettlementDictionaryConstants.STL__WITHHOLD_STATUS__WITHHOLD_SUCCESS.equals(billReceivableEntity.getWithholdStatus())
						&& !SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(billReceivableEntity.getPaymentType())){
					//扣款不成功，不能签收。
					throw new SettlementException("该运单未扣款或扣款失败，无法签收！");
				}else{
					if(!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(billReceivableEntity.getPaymentType())){
						//设置客户编码
						billDepositReceivedDto.setCustomerCode(billReceivableEntity.getCustomerCode());
						//设置部门编码
						billDepositReceivedDto.setCollectionOrgCode(billReceivableEntity.getReceivableOrgCode());
						billDepositReceivedDto.setQueryType(SettlementConstants.TAB_QUERY_BY_DATE);
						//按照客户编码和部门编码查询预收单
						BillDepWriteoffBillRecDto billDepWriteoffBillRecDto = billDepWriteoffBillRecService.queryBillDep(billDepositReceivedDto);
						if(CollectionUtils.isEmpty(billDepWriteoffBillRecDto.getBillDepositreceivedEntityList())){
							throw new SettlementException("合伙人没有预存款，不能签收，合伙人编码：" + billReceivableEntity.getCustomerCode() + 
									"，应收部门：" + billReceivableEntity.getReceivableOrgCode());
						}
						//核销操作Dto加入预收单List
						writeoffDto.setBillDepositReceivedEntitys(billDepWriteoffBillRecDto.getBillDepositreceivedEntityList());
						//插入应收单
						List<BillReceivableEntity> billReceivableEntitys = new ArrayList<BillReceivableEntity>();
						billReceivableEntitys.add(billReceivableEntity);
						//核销操作Dto加入应收单List
						writeoffDto.setBillReceivableEntitys(billReceivableEntitys);
						
						// 获取核销批次号
						String writeoffBillBatchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);
						writeoffDto.setWriteoffBatchNo(writeoffBillBatchNo);
						writeoffDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
						
						//如果预收单和应收单不为空-预收冲应收
						if (CollectionUtils.isNotEmpty(writeoffDto.getBillDepositReceivedEntitys())&& CollectionUtils.isNotEmpty(writeoffDto.getBillReceivableEntitys())) {
							//调用统一核销方法
							billWriteoffService.writeoffDepositAndReceivable(writeoffDto, currentInfo);
						}
					}
				}	
			}
		}
	}
	
	/**
	 * 反签收反核销合伙人始发应收
	 * @author 042792
	 * @param dto
	 */
	private void unConfirmPTPPaymentAndWriteOff(PaymentSettlementDto dto, CurrentInfo currentInfo){
		LOGGER.error("开始反签收合伙人收款操作，运单号为：" + dto.getWaybillNo());
		BillReceivableConditionDto reDto = new BillReceivableConditionDto(dto.getWaybillNo());
		reDto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE
				});
		List<BillReceivableEntity> billReceivables = billReceivableService.queryBillReceivableByCondition(reDto);
		String receivableNo = "";
		String active = FossConstants.ACTIVE;
		String createType = SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO;
		if(null != billReceivables){
			for(int i = 0; i < billReceivables.size(); i++){
				BillReceivableEntity recEntity = billReceivables.get(i);
				if(!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(recEntity.getPaymentType())
						&& !isPartnerDept(recEntity.getDestOrgCode())){
					receivableNo = recEntity.getReceivableNo();
					List<BillWriteoffEntity> list = billWriteoffService.queryBillWriteoffByBeginOrEndNo(receivableNo, active, createType);
					for(BillWriteoffEntity writeoffEntity : list){
						billWriteoffService.writeBackBillWriteoffById(writeoffEntity.getId(),currentInfo);
					}
				}
			}
		}
	}
	
	private boolean isPartnerDept(String orgCode){
		//DDW
		SaleDepartmentEntity entity = saleDepartmentService.querySaleDepartmentInfoByCode(orgCode);
		//判断到达部门是否为合伙人，如果是合伙人则在PTP系统生成应收单
		if(entity != null && FossConstants.YES.equals(entity.getIsLeagueSaleDept())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 到付理赔签收时自动核销
	 * @author 268217 
	 * @Date  2016-08-18
	 * @param waybillNo
	 * @param currentInfo
	 */
	private void RecAndPayChang(String waybillNo,CurrentInfo currentInfo){
		//268217 如果有代收货款应收单，提示不能理赔出库
		BillWriteoffOperationDto dto =new BillWriteoffOperationDto();
		// 生成新的核销批次号
		String writeoffBatchNo = settlementCommonService
						.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);
		dto.setWriteoffBatchNo(writeoffBatchNo);
		dto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		//查询应收单
    	BillReceivableConditionDto receivDto = new BillReceivableConditionDto(waybillNo);
		receivDto.setBillTypes(new String[]{
			SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
		});
		receivDto.setActive(FossConstants.ACTIVE);
		List<BillReceivableEntity> receivables = 
				billReceivableService.queryBillReceivableByCondition(receivDto);
		//查询应付单
		BillPayableConditionDto payableConditionDto = new BillPayableConditionDto();
		payableConditionDto.setWaybillNo(waybillNo);
		payableConditionDto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM });
		List<BillPayableEntity> payables = billPayableService
				.queryBillPayableByCondition(payableConditionDto);
		if(CollectionUtils.isEmpty(receivables)){
			throw new SettlementException("没有有效的应收单，无法签收！");
		}
		if(CollectionUtils.isEmpty(payables)){
			throw new SettlementException("没有有效的应付单，无法签收！");
		}
		dto.setBillReceivableEntitys(receivables);
		dto.setBillPayableEntitys(payables);
		//核销--应收冲应付
		billWriteoffService.writeoffReceibableAndPayable(dto, currentInfo);
	}
	
	/**
	 * Sets the bill payable service.
	 * 
	 * @param billPayableService
	 *            the billPayableService to set
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * Sets the taking service.
	 * 
	 * @param takingService
	 *            the takingService to set
	 */
	public void setTakingService(ITakingService takingService) {
		this.takingService = takingService;
	}

	/**
	 * Sets the cod common service.
	 * 
	 * @param codCommonService
	 *            the codCommonService to set
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	/**
	 * Sets the statement of account service.
	 * 
	 * @param statementOfAccountService
	 *            the statementOfAccountService to set
	 */
	public void setStatementOfAccountService(IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	/**
	 * Sets the waybill rfc service.
	 * 
	 * @param waybillRfcService
	 *            the waybillRfcService to set
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	/**
	 * Sets the waybill manager service.
	 * 
	 * @param waybillManagerService
	 *            the waybillManagerService to set
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * Sets the configuration params service.
	 * 
	 * @param configurationParamsService
	 *            the configurationParamsService to set
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * Sets the pod service.
	 * 
	 * @param podService
	 *            the podService to set
	 */
	public void setPodService(IPODService podService) {
		this.podService = podService;
	}

	/**
	 * Sets the bill repayment service.
	 * 
	 * @param billRepaymentService
	 *            the billRepaymentService to set
	 */
	public void setBillRepaymentService(IBillRepaymentService billRepaymentService) {
		this.billRepaymentService = billRepaymentService;
	}


	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}


	public void setCodAuditService(ICodAuditService codAuditService) {
		this.codAuditService = codAuditService;
	}


	public void setCodAuditLogService(ICodAuditLogService codAuditLogService) {
		this.codAuditLogService = codAuditLogService;
	}


	public void setCodAuditDao(ICodAuditDao codAuditDao) {
		this.codAuditDao = codAuditDao;
	}


	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}


	public void setBillDepWriteoffBillRecService(
			IBillDepWriteoffBillRecService billDepWriteoffBillRecService) {
		this.billDepWriteoffBillRecService = billDepWriteoffBillRecService;
	}


	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}


	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}

	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
	public void setQueryIsVehicleassembleForEcs(
			IQueryIsVehicleassembleForEcs queryIsVehicleassembleForEcs) {
		this.queryIsVehicleassembleForEcs = queryIsVehicleassembleForEcs;
	}

	public void setWaybillEntityForEcsDao(
			IWaybillEntityForEcsDao waybillEntityForEcsDao) {
		this.waybillEntityForEcsDao = waybillEntityForEcsDao;
	}

	public void setPartnerPayStatementDao(
			IPartnerPayStatementDao partnerPayStatementDao){
		this.partnerPayStatementDao=partnerPayStatementDao;
	}
}
