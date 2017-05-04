package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.server.dao.IGreenHandWrapWriteoffDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillBadAccountService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillCashCollectionService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ICUBCGrayService;
import com.deppon.foss.module.settlement.common.api.server.service.ICustomerBargainService;
import com.deppon.foss.module.settlement.common.api.server.service.IFossToFinsRemitCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IFreightDiscountService;
import com.deppon.foss.module.settlement.common.api.server.service.IGreenHandWrapWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.IPODService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCManageService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.GreenHandWrapWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PODEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.RequestGreenHandWrapEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCCodAuditRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCCodAuditResultDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayResultDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodAuditService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IEcsFossErrorLogJobService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IGrayCustomerService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ISalesPayCODService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupEcsService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.GrayCustomerEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CodAuditDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillFeeDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupWriteBackDto;
import com.deppon.foss.module.settlement.pay.api.server.service.IDiscountManagementService;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 悟空开单、更改服务
 * @author foss-231434-bieyexiong
 * @date 2016-11-4
 */
public class WaybillPickupEcsService implements IWaybillPickupEcsService{
	
	private String queryCodAuditListUrl;
	public void setQueryCodAuditListUrl(String queryCodAuditListUrl) {
		this.queryCodAuditListUrl = queryCodAuditListUrl;
	}
	
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	
	private static final String SERVICE_CODE = "com.deppon.foss.module.settlement.consumer.server.service.impl.WaybillPickupEcsService";
	
	private ICUBCGrayService cUBCGrayService;
	
	public void setcUBCGrayService(ICUBCGrayService cUBCGrayService) {
		this.cUBCGrayService = cUBCGrayService;
	}

	private static final Logger logger = LogManager
			.getLogger(WaybillPickupEcsService.class);

	/**
	 * 结算通用服务
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 现金收款单服务
	 */
	private IBillCashCollectionService billCashCollectionService;

	/**
	 * 应收单服务
	 */
	private IBillReceivableService billReceivableService;
	
	/**
	 * FOSS到财务第三方支付
	 */
	private IFossToFinsRemitCommonService fossToFinsRemitCommonService;

	/**
	 * 应付单服务
	 */
	private IBillPayableService billPayableService;

	/**
	 * 客户信用额度服务
	 */
	private ICustomerBargainService customerBargainService;

	/**
	 * 代收货款服务
	 */
	private IBillPayCODService billPayCODService;

	/**
	 * 代收货款通用服务
	 */
	private ICodCommonService codCommonService;

	/**
	 * 对账单服务
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 组织信息服务
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 系统配置参数服务
	 */
	private IConfigurationParamsService configurationParamsService;

	/**
	 * 坏账服务
	 */
	private IBillBadAccountService billBadAccountService;

	/**
	 * 综合管理-客户信息Service
	 */
	private ICustomerService customerService;

	/** 代收货款出发申请服务. */
	private ISalesPayCODService salesPayCODService;

	/**
	 * 财务签收记录Service，开单默认插入一条数据，便于后续凭证数据使用
	 */
	private IPODService podService;

	/**
	 * 保留小数位
	 */
	private static final int DIVDE_NUMBER = 0;
	
	/**
	 * 快递代理点部映射
	 */
	private IExpressPartSalesDeptService expressPartSalesDeptService;
	
	/**
	 * 运单服务类
	 */
	private IWaybillManagerService waybillManagerService;
	
	private IDiscountManagementService discountManagementService;
	
	/**
	 * 零担折扣
	 */
	private IFreightDiscountService freightDiscountService;
	
	/** 插入代收货款审核表 */
	private ICodAuditService codAuditService;
	
	/**
	 * 灰名单服务
	 */
	private IGrayCustomerService grayCustomerService;
	
	/**
	 * 注入
	 * @param billReceivableEntityDao
	 */
	private IGreenHandWrapWriteoffDao greenHandWrapWriteoffDao;
	
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 注入裹裹核销应收单的接口service
	 */
	private IGreenHandWrapWriteoffService greenHandWrapWriteoffService;
    /**
     * POS刷卡相关
     */
	private IWSCManageService wscManageService;
	
	/**
	 * 快递对接FOSS,JOB定时执行service 
	 * @author 326181
	 */
	private IEcsFossErrorLogJobService ecsFossErrorLogJobService;
    	/**
	 * 实际承运表
	 */
	private IActualFreightDao actualFreightDao;



    public void setWscManageService(IWSCManageService wscManageService) {
        this.wscManageService = wscManageService;
    }

    public void setGreenHandWrapWriteoffService(
			IGreenHandWrapWriteoffService greenHandWrapWriteoffService) {
		this.greenHandWrapWriteoffService = greenHandWrapWriteoffService;
	}

	/**
	 * 悟空新增运单
	 * @author foss-231434-bieyexiong
	 * @date 2016-11-3
	 */
	@Override
	public void addWaybillEcs(WaybillPickupInfoDto waybill, CurrentInfo currentInfo) {

		logger.info("开始新增运单：" + waybill.getWaybillNo());

		// 验证运单输入参数合法性
		this.validAddedWaybillParam(waybill, currentInfo);

		// 处理新增运单
		this.handleAddedWaybillNew(waybill,null, currentInfo, null);

		logger.info("结束新增运单：" + waybill.getWaybillNo());
	}

	/**
	 * 在财务签收流水表中，默认插入一条默认记录便于后续统计未签收的运单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-21 下午5:48:01
	 * @param waybill
	 * @param currentInfo
	 */
	private void addPODEntity(WaybillPickupInfoDto waybill,
			CurrentInfo currentInfo, Date date) {
		// 2013-02-20日凭证设计方案：在财务签收流水表中，默认插入一条默认记录便于后续统计未签收的运单信息
		PODEntity pod = new PODEntity();
		pod.setId(UUIDUtils.getUUID());

		// 运单号
		pod.setWaybillNo(waybill.getWaybillNo());

		// 开单时间
		pod.setPodDate(date);

		// 类型：开单
		pod.setPodType(SettlementDictionaryConstants.POD_ENTITY__POD_TYPE__BILLING);
		this.podService.addPOD(pod, currentInfo);

	}
	
	/**
	 * 在财务签收流水表中，默认插入一条默认记录便于后续统计未签收的运单信息,开单id取至悟空传的运单id，保证一个运单只开单一次
	 * 避免同一个运单重复调结算接口生成财务单据
	 * @author foss-231434-bieyexiong
	 * @date 2016-10-9
	 * @param waybill
	 * @param currentInfo
	 */
	private void addPODEntityEcs(WaybillPickupInfoDto waybill,
			CurrentInfo currentInfo, Date date) {
		// 2013-02-20日凭证设计方案：在财务签收流水表中，默认插入一条默认记录便于后续统计未签收的运单信息
		PODEntity pod = new PODEntity();
		pod.setId(waybill.getId());

		// 运单号
		pod.setWaybillNo(waybill.getWaybillNo());

		// 开单时间
		pod.setPodDate(date);

		// 类型：开单
		pod.setPodType(SettlementDictionaryConstants.POD_ENTITY__POD_TYPE__BILLING);
		this.podService.addPOD(pod, currentInfo);

	}
	
	/**
	 * 悟空校验能否起草更改
	 * @author foss-231434-bieyexiong
	 * @date 2016-11-3
	 */
	@Override
	public void canChange(String waybillNo) {
		
		Date now = new Date();

		// 运单对应的应收单已核销或部分核销
		// 运单存在始发运费应收单或到付运费应收单，客户在网上营业厅进行了支付锁定
		List<String> statementBillNos = new ArrayList<String>(); // 对账单号集合
		List<BillReceivableEntity> listR = billReceivableService
				.queryBillReceivableByCondition(new BillReceivableConditionDto(waybillNo));
		if (listR != null) {
			for (BillReceivableEntity e : listR) {
			  //modify by 354658-duyijun  取消  合伙人 合伙人罚款应收、合伙人差错应收、合伙人培训会务应收、合伙人其他应收  核销后不能发更改的校验
                if (!SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__WOODEN_OTEHR_RECEIVABLE
                        .equals(e.getBillType())
                        && !SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__PENALTY
                                .equals(e.getBillType())
                        && !SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__TRAIN_FEE
                                .equals(e.getBillType())
                        && !SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER_ERROR_RECEIVABLE
                                .equals(e.getBillType())
                        && !SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER_OTHER_FEE_RECEIVABLE
                                .equals(e.getBillType())){
					if (StringUtils.isNotEmpty(e.getStatementBillNo())
							&& !SettlementConstants.DEFAULT_BILL_NO.equals(e
									.getStatementBillNo())) {
						statementBillNos.add(e.getStatementBillNo());
					}
					if (e.getUnverifyAmount().compareTo(e.getAmount()) < 0) {
						throw new SettlementException("对应的应收单已经核销，不能进行发更改操作");
					}
					if (e.getUnlockDateTime() != null
							&& now.before(e.getUnlockDateTime())) {
						throw new SettlementException("客户在网上已经将运单进行了锁定,不能进行发更改操作");
					}
				}
			}
		}

		// 运单对应的应付单已核销或部分核销
		// 运单存在对应服务补救、理赔申请、坏账申请，并且对应的处理状态为申请中或者处理已完结
		List<BillPayableEntity> listP = billPayableService
				.queryBillPayableByCondition(new BillPayableConditionDto(
						waybillNo));
		if (listP != null) {
			for (BillPayableEntity e : listP) {
			  //modify by 354658-duyijun  取消  合伙人  合伙人快递差错应付、合伙人奖励应付、合伙人其他应付  核销后不能发更改的校验
                if (!SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__WOODEN_PAYABLE
                        .equals(e.getBillType())
                        && !SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__WOODEN_OTEHR_PAYABLE
                                .equals(e.getBillType())
                        && !SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__LTL_ERROR
                                .equals(e.getBillType())
                        && !SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__BONUS
                                .equals(e.getBillType())
                        && !SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER_OTHER_PAYABLE
                                .equals(e.getBillType())) {
					if (StringUtils.isNotEmpty(e.getStatementBillNo())
							&& !SettlementConstants.DEFAULT_BILL_NO.equals(e
									.getStatementBillNo())) {
						statementBillNos.add(e.getStatementBillNo());
					}
					if (e.getUnverifyAmount().compareTo(e.getAmount()) < 0) { // 存在核销操作
						throw new SettlementException("对应的应付单已经核销，不能进行发更改操作");
					}
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

		// 运单对应的客户对账单已确认、核销、付款、还款
		List<String> statementNos = statementOfAccountService
				.queryConfirmStatmentOfAccount(statementBillNos);
		if (CollectionUtils.isNotEmpty(statementNos)) {
			throw new SettlementException("该单存在相应的客户对账单已确认、核销、付款、还款，不能进行发更改操作");
		}

		// 代收货校验
		CODEntity codEntity = codCommonService.queryByWaybill(waybillNo);

		if (codEntity != null
				&& FossConstants.ACTIVE.equals(codEntity.getActive())) {

			// 代收货款应付单的支付状态为资金部冻结、汇款中、已汇款之一
			List<String> status = new ArrayList<String>();
			status.add(SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE);
			status.add(SettlementDictionaryConstants.COD__STATUS__RETURNING);
			status.add(SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE_APPLICATION);
			status.add(SettlementDictionaryConstants.COD__STATUS__NEGATIVE_RETURN_SUCCESS);
			status.add(SettlementDictionaryConstants.COD__STATUS__RETURNED);

			if (status.contains(codEntity.getStatus())) {
				throw new SettlementException(
						"代收货款对应的状态为资金部冻结或者是汇款中时无法红冲现金收款单，不能进行发更改操作 ");
			}

		}
		
		/**
		 * @author 218392 zhangyongxue 2016-07-12 15:16:26
		 * 长期未退款代收货款，如果被短期冻结或者长期冻结，那么冻结状态期间，不允许运单更改
		 */
		CodAuditDto codAuditDto = new CodAuditDto();
		List<String> waybillListNo = new ArrayList<String>();
		waybillListNo.add(waybillNo);
		codAuditDto.setWaybillNos(waybillListNo);
		//代收货款审核灰度   353654 ------------------------ start
        String vestSystemCode = null;
        try {
        	ArrayList<String> arrayList = new ArrayList<String>();
        	arrayList.add(waybillNo);
        	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
        			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".canChange",
        			SettlementConstants.TYPE_FOSS);
        	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
        	List<VestBatchResult> list = response.getVestBatchResult();
        	vestSystemCode = list.get(0).getVestSystemCode();		
		} catch (Exception e) {
			logger.info("灰度分流失败,"+"运单号："+waybillNo);
			throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
		}
        if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
        	List<CodAuditEntity> codAuditEntityList = codAuditService.queryCodAuditByCondition(codAuditDto);
    		if(CollectionUtils.isNotEmpty(codAuditEntityList)){
    			CodAuditEntity codAuditEntity = codAuditEntityList.get(0);
    			//判断是否是SSL SLL
    			if((SettlementDictionaryConstants.SETTLE_SHORT_LOCK.equals(codAuditEntity.getLockStatus()))
    					||(SettlementDictionaryConstants.SETTLE_LONG_LOCK.equals(codAuditEntity.getLockStatus()))){
    				throw new SettlementException("此单据已被冻结(或长期冻结)，如需操作，请联系资金复核组进行解冻!");
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
					logger.error("调用CUBC代收货款审核接口连接异常...");
					throw new SettlementException("服务器正忙,CUBC代收货款审核失败,请稍后重试");
				}
				if(resultDto != null){
					if(StringUtils.isNotBlank(resultDto.getMeg())){
						logger.error("调用CUBC代收货款审核接口失败，异常信息：" + resultDto.getMeg());
						throw new SettlementException(resultDto.getMeg());	
					}
					List<com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto> auditList = resultDto.getCodAuditList();
					if(CollectionUtils.isNotEmpty(auditList)){
						com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto codAuditDto1 = auditList.get(0);
						if(codAuditDto1 != null){
							//判断是否是SSL SLL
			    			if((SettlementDictionaryConstants.SETTLE_SHORT_LOCK.equals(codAuditDto1.getLockStatus()))
			    					||(SettlementDictionaryConstants.SETTLE_LONG_LOCK.equals(codAuditDto1.getLockStatus()))){
			    				throw new SettlementException("此单据已被冻结(或长期冻结)，如需操作，请联系资金复核组进行解冻!");
			    			}
						}
					}else{
						logger.info("CUBC,单号："+waybillNo+"没有进入代收货款支付审核！");
					}
				}
			
        }
        //代收货款审核灰度   353654 ------------------------ end
		// 坏账校验
		int i = billBadAccountService.queryByWaybillNO(waybillNo);
		if (i > 0) {
			throw new SettlementException("坏账申请审批完成，不能进行发更改操作");
		}

	}
	
	/**
	 * 悟空作废运单
	 * @author foss-231434-bieyexiong
	 * @date 2016-11-2
	 */
	@Override
	public void cancelWaybillEcs(String waybillNo, CurrentInfo currentInfo) {

		logger.error("开始作废运单：" + waybillNo);

		this.handleCanceledWaybill(waybillNo,currentInfo);

		logger.error("结束作废运单：" + waybillNo);
	}
	
	/**
	 * 悟空单据处理取消运单
	 * 
	 * @author foss-231434-bieyexiong
	 * @date 2016-11-02
	 * @param waybillNo
	 * @param currentInfo
	 * @return 红冲的原始单据信息
	 */
	private WaybillPickupWriteBackDto handleCanceledWaybill(String waybillNo,CurrentInfo currentInfo) {

		// 坏账校验
		int i = billBadAccountService.queryByWaybillNO(waybillNo);
		if (i > 0) {
			throw new SettlementException("坏账申请审批完成，运单不允许更改");
		}

		//ddw
		String status = querydiscountPayable(waybillNo);
		// 红冲现金收款单
		List<BillCashCollectionEntity> billCashes = billCashCollectionService
				.queryBySourceBillNOs(
						Arrays.asList(waybillNo),
						SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL,
						FossConstants.ACTIVE);

		// 校验现金收款单
		billCashCollectionService
		.validateWaybillForBillCashCollection(billCashes);

		// 红冲现金收款单
		if (CollectionUtils.isNotEmpty(billCashes)) {
			for (BillCashCollectionEntity entity : billCashes) {
				// 已签收，不能红冲
				if (entity.getConrevenDate() != null) {
					throw new SettlementException("该单已经被签收，不允许进行红冲现金收款单操作");
				}

				billCashCollectionService.writeBackBillCashCollection(entity,
						currentInfo);
			}
		}

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
		
		//更改前到达部门是否合伙人
		boolean isPartnerDeptBeforeChange = false;
		
		// 红冲应收单，包括代收货款
		if (CollectionUtils.isNotEmpty(billReceives)) {
			for (BillReceivableEntity entity : billReceives) {
				// 已签收，不能红冲
				if (entity.getConrevenDate() != null) {
					throw new SettlementException("该单已经被签收，不允许进行红冲应收单操作");
				}
				
				//用应收单的到达部门判断是否合伙人
				//判断应收单到达部门是否合伙人部门，是：不红冲到达应收单、代收货款应收单；否：全部红冲
				isPartnerDeptBeforeChange = isPartnerDept(entity.getDestOrgCode());
				if(!isPartnerDeptBeforeChange){
					billReceivableService.writeBackBillReceivable(entity,currentInfo);
				}else{
					if(!(entity.getBillType().equals(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE)
							||entity.getBillType().equals(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE)))
						billReceivableService.writeBackBillReceivable(entity,currentInfo);
				}
			}
		}

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

		// 红冲应付单，包括代收货款
		if (CollectionUtils.isNotEmpty(billPayables)) {
			for (BillPayableEntity entity : billPayables) {
				// 已签收，不能红冲
				if (entity.getSignDate() != null) {
					throw new SettlementException("该单已经被签收，不允许进行红冲应付单操作");
				}

				billPayableService.writeBackBillPayable(entity, currentInfo);
			}
		}

		// 作废代收货款单
		CODEntity codEntity = codCommonService.queryByWaybill(waybillNo);
		if (codEntity != null
				&& FossConstants.ACTIVE.equals(codEntity.getActive())) {
			this.billPayCODService.cancelBillCOD(codEntity, currentInfo);
		}

		// 红冲的结算单据信息
		WaybillPickupWriteBackDto dto = new WaybillPickupWriteBackDto();

		dto.setWriteBackBillCashCollections(billCashes); // 红冲的现金收款单
		dto.setWriteBackBillReceivables(billReceives); // 红冲的应收单
		dto.setWriteBackBillPayables(billPayables); // 红冲的应付单
		dto.setWriteBackCOD(codEntity); // 红冲的代收货款
		dto.setPartnerDeptBeforeChange(isPartnerDeptBeforeChange);//更改前到达部门是否合伙人

		return dto;
	}
	
	/**
	 * 悟空更改运单
	 * @author foss-231434-bieyexiong
	 * @date 2016-11-2
	 */
	@Override
	public void modifyWaybillEcs(WaybillPickupInfoDto oldWaybill,
			WaybillPickupInfoDto newWaybill, CurrentInfo currentInfo) {
		logger.info("开始更改运单：" + oldWaybill.getWaybillNo());	
		
		// 作废运单，返回红冲的单据，在新增时读取单据类型、客户等信息
		WaybillPickupWriteBackDto dto = this.handleCanceledWaybill(oldWaybill.getWaybillNo(),currentInfo);

		// 验证新增运单
		this.validAddedWaybillParam(newWaybill, currentInfo);

		// 处理新增运单
		this.handleAddedWaybill(newWaybill,oldWaybill, currentInfo, dto);
		
		//start 269044-zhurongrong 2016-05-09
		//先判断该客户是否在灰名单中，不在的话需判断是否添加进去，在的话，需要更新最久欠款日期
		//更改付款方式由非月结为月结
		if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(newWaybill.getPaidMethod())
				&& !(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(oldWaybill.getPaidMethod()))) {
			//存放待处理客户编码集合
			List<String> customerCodeList = new ArrayList<String>();
			//添加客户编码
			customerCodeList.add(newWaybill.getDeliveryCustomerCode());
			try{
				//调用判断时候修改灰名单接口
				grayCustomerService.updateGrayCustomerToECS(customerCodeList);
			} catch (Exception e) {
				//打印异常
				logger.info("调用悟空更改灰名单接口异常" + e.getMessage());
			}
		}
		//将付款方式由月结改成其他
		else if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(oldWaybill.getPaidMethod())
				&& !(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(newWaybill.getPaidMethod()))) {
			//先判断该客户是否在灰名单中，在的话，是否拉出来，不在的话直接pass
			GrayCustomerEntity grayCustomerEntity = grayCustomerService
					.queryGrayCustomerByCustomerCode(newWaybill.getDeliveryCustomerCode());
			//存放待处理客户编码集合
			List<String> customerCodeList = new ArrayList<String>();
			//添加客户编码
			customerCodeList.add(newWaybill.getDeliveryCustomerCode());
			if(grayCustomerEntity != null) {
				try{
					//调用判断时候修改灰名单接口
					grayCustomerService.updateGrayCustomerToECS(customerCodeList);
				} catch (Exception e) {
					//打印异常
					logger.info("调用悟空更改灰名单接口异常" + e.getMessage());
				}
			}
		}
		//end

		logger.info("结束更改运单：" + oldWaybill.getWaybillNo());
	}
	
	/**
	 * 悟空更改处理新增运单
	 * @author foss-231434-bieyexiong
	 * @date 2016-11-2
	 */
	private void handleAddedWaybill(WaybillPickupInfoDto waybill,WaybillPickupInfoDto oldWaybill,
			CurrentInfo currentInfo, WaybillPickupWriteBackDto dto) {

		// 由于到达应收单和代收货款应收单在空运合票时单据类型和客户信息发生变化
		// 这些蓝单的信息取自红单
		BillReceivableEntity writeBackBillReceivableDest = null; // 到达应收单
		BillReceivableEntity writeBackBillReceivableCod = null; // 代收货款应收单
		if (dto != null && CollectionUtils.isNotEmpty(dto.getWriteBackBillReceivables())) {
			for (BillReceivableEntity entity : dto.getWriteBackBillReceivables()) {
				if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
						.equals(entity.getBillType())
						|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE
								.equals(entity.getBillType())
						|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY
								.equals(entity.getBillType())
						|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE
								.equals(entity.getBillType())) {
					writeBackBillReceivableDest = entity;
				} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
						.equals(entity.getBillType())
						|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD
								.equals(entity.getBillType())
						|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD
								.equals(entity.getBillType())) {
					writeBackBillReceivableCod = entity;
				}
			}
		}

		String paymentType = waybill.getPaidMethod(); // 付款方式
		BigDecimal prePayAmount = waybill.getPrePayAmount(); // 预付金额（包括现金和欠款）
		BigDecimal toPayAmount = waybill.getToPayAmount().subtract(
				waybill.getCodAmount()); // 到付金额（去除代收货款）

		BillCashCollectionEntity billCashCollectionEntity = null; // 现金收款单
		BillReceivableEntity billReceivableOrig = null; // 始发应收单
		BillReceivableEntity billReceivableDest = null; // 到付应收单
		BillReceivableEntity billReceivableCod = null; // 代收货款应收单
		BillPayableEntity billPayableSF = null; // 应付装卸费
		BillPayableEntity billPayableCOD = null; // 应付代收货款

		WaybillFeeDto prePayFeeDto = new WaybillFeeDto(); // 预付费用分摊
		WaybillFeeDto toPayFeeDto = new WaybillFeeDto(); // 到付费用分摊

		splitWaybillFee(waybill, prePayFeeDto, toPayFeeDto); // 金额拆分

		// 会计时间为分区键，开单时保证各个单据的时间保持一致
		Date now = new Date();
		//ddw
		String status = querydiscountPayable(waybill.getWaybillNo());
		// 付款时存在情况有：部分预付，部分到付，此时预付必须都是现金支付；不存在以下情况：部分现付，部分临欠
		if (prePayAmount.compareTo(BigDecimal.ZERO) > 0
				&& toPayAmount.compareTo(BigDecimal.ZERO) == 0) { // 1)全部预付

			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH
					.equals(paymentType)
					|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD
							.equals(paymentType)) { // 使用现金、银行卡付款

				billCashCollectionEntity = this.getBillCashCollectoin(waybill,
						currentInfo, prePayFeeDto, now);
				billCashCollectionService.addBillCashCollection(
						billCashCollectionEntity, currentInfo);

			} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(paymentType)
					|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(paymentType)) { 
				// 月结、网上支付生成方式合并

				// 生成应收单信息
				billReceivableOrig = this.buildBillReceivableOrig(waybill,prePayFeeDto, now);

				//ddw，如果折扣单不是已确认的新增有效始发应收单
				if(!SettlementDictionaryConstants.DISCOUNT_BILL_STATUS_CONFIRM.equals(status)){
					billReceivableService.addBillReceivable(billReceivableOrig,	currentInfo);
				}
			} else {
				throw new SettlementException("付款方式有误，不能执行此操作");
			}

		} else if (prePayAmount.compareTo(BigDecimal.ZERO) == 0
				&& toPayAmount.compareTo(BigDecimal.ZERO) > 0) { // 2)全部到付

			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT
					.equals(paymentType)) {
				//ddw,判断到达部门是否为合伙人，如果是合伙人则在PTP系统生成应收单
				if(!isPartnerDept(waybill.getLastLoadOrgCode()) && !dto.isPartnerDeptBeforeChange()){
					billReceivableDest = this.buildBillReceivableDest(waybill,
							toPayFeeDto, now, writeBackBillReceivableDest,
							writeBackBillReceivableCod);
					//ddw，如果折扣单不是已确认的新增有效到达应收单
					if(!SettlementDictionaryConstants.DISCOUNT_BILL_STATUS_CONFIRM.equals(status)){
						billReceivableService.addBillReceivable(billReceivableDest, currentInfo);
					}
				}
			} else {
				throw new SettlementException("付款方式有误，不能执行此操作");
			}

		} else if (prePayAmount.compareTo(BigDecimal.ZERO) > 0
				&& toPayAmount.compareTo(BigDecimal.ZERO) > 0) { // 3)部分预付，部分到付

			// 预付处理
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH
					.equals(paymentType)
					|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD
							.equals(paymentType)) { // 使用现金、银行卡付款

				billCashCollectionEntity = this.getBillCashCollectoin(waybill,
						currentInfo, prePayFeeDto, now);
				billCashCollectionService.addBillCashCollection(
						billCashCollectionEntity, currentInfo);

			} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT
					.equals(paymentType)) { // 到付

				billCashCollectionEntity = this.getBillCashCollectoin(waybill,
						currentInfo, prePayFeeDto, now);
				// 现金收款单中的支付方式只能是现金或者银行卡
				billCashCollectionEntity
						.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
				billCashCollectionService.addBillCashCollection(
						billCashCollectionEntity, currentInfo);

			} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(paymentType)) { // 月结、临欠

				billReceivableOrig = this.buildBillReceivableOrig(waybill,
						prePayFeeDto, now);
				//ddw，如果折扣单不是已确认的新增有效始发应收单
				if(!SettlementDictionaryConstants.DISCOUNT_BILL_STATUS_CONFIRM.equals(status)){
					billReceivableService.addBillReceivable(billReceivableOrig,	currentInfo);
				}
			} else {
				throw new SettlementException("付款方式有误，不能执行此操作");
			}
			//ddw,判断到达部门是否为合伙人，如果是合伙人则在PTP系统生成应收单
			if(!isPartnerDept(waybill.getLastLoadOrgCode()) && !dto.isPartnerDeptBeforeChange()){
				// 到付处理
				billReceivableDest = this.buildBillReceivableDest(waybill,
						toPayFeeDto, now, writeBackBillReceivableDest,
						writeBackBillReceivableCod);
				//判断是否为快递
				if(SettlementUtil.isPackageProductCode(billReceivableDest.getProductCode())){
					//ddw，如果折扣单不是已确认的新增有效到达应收单
					if(!SettlementDictionaryConstants.DISCOUNT_BILL_STATUS_CONFIRM.equals(status)){
						billReceivableService.addBillReceivable(billReceivableDest,	currentInfo);
					}
				}else{
					billReceivableService.addBillReceivable(billReceivableDest,	currentInfo);
				}
			}
		}

		// 代收货款金额大于0，则生成代收货款
		if (waybill.getCodAmount() != null
				&& waybill.getCodAmount().compareTo(BigDecimal.ZERO) > 0) {

			// 处理开代收款生成应付单前验证
			validateHandleCod(waybill);
			//ddw,判断到达部门是否为合伙人，如果是合伙人则在PTP系统生成应收单
			if(!isPartnerDept(waybill.getLastLoadOrgCode()) && !dto.isPartnerDeptBeforeChange()){
				// 生成代收货款应收单
				billReceivableCod = buildBillReceivableCOD(waybill, now,
						writeBackBillReceivableCod, writeBackBillReceivableDest);
				billReceivableService.addBillReceivable(billReceivableCod,
						currentInfo);
			}
			// 生成代收货款应付单
			billPayableCOD = buildBillPayableCOD(waybill, now);
			billPayableService.addBillPayable(billPayableCOD, currentInfo);

			// 调用代收货款服务
			this.billPayCODService.addBillCOD(waybill, currentInfo);
		}

		// 装卸费金额大于0，生成装卸费应付单
		if (waybill.getServiceFee() != null
				&& waybill.getServiceFee().compareTo(BigDecimal.ZERO) > 0) {
			if (StringUtils.isEmpty(waybill.getDeliveryCustomerContact())) {
				throw new SettlementException("当运单存在装卸费时，客户联系人不能为空！");
			}

			// 发货客户手机和发货客户电话同时为空时，提示不能操作
			if (StringUtils.isEmpty(waybill.getDeliveryCustomerMobilephone())
					&& StringUtils.isEmpty(waybill.getDeliveryCustomerPhone())) {
				throw new SettlementException("当运单存在装卸费时，联系电话不能为空！");
			}
			billPayableSF = buildBillPayableSF(waybill, now);
			billPayableService.addBillPayable(billPayableSF, currentInfo);
		}

		// 生成开单流水
		addPODEntity(waybill, currentInfo, now);
		
		/**
		 * @author 218392 zhangyongxue
		 * @date 2016-02-23 12:12:12
		 * 裹裹项目：当DOP到FOSS结算的付款信息先到的情况下，补录之后生成的结算单据信息在后的情况下
		 * （1）先到裹裹暂存表中查询：根据运单号查询为未核销的；
		 * （2）如果有说明DOP的付款信息先到了；如果没有说明DOP的付款信息没有到。
		 * （3）然后，再调用裹裹的结算单据处理Service处理
		 */
		List<GreenHandWrapWriteoffEntity> greenHandList = new ArrayList<GreenHandWrapWriteoffEntity>();
		if(waybill != null){
			String waybillNo = waybill.getWaybillNo();
			//根据单号查询未核销暂存表的信息
			greenHandList = greenHandWrapWriteoffDao.queryGreenHandWrapByWaybillNo(waybillNo);
			//查询结果不为空，并且长度>0,
			RequestGreenHandWrapEntity wrapEntity = new RequestGreenHandWrapEntity();
			wrapEntity.setWaybillNo(waybillNo);
			if(greenHandList != null && greenHandList.size()>0){
				/**
				 * 运单号查询满足本次项目需求条件的应收单
				 * （条件是：始发应收单，未核销金额大于0，有效的，来源单据类型为 W-开单）
				 */
				List<BillReceivableEntity> billReceivableList = greenHandWrapWriteoffService.queryReceivableBill(wrapEntity);
				if(billReceivableList != null && billReceivableList.size() == 2){
					throw new SettlementException("单号："+ waybillNo + "存在多条始发应收单");
				}else if(billReceivableList != null && billReceivableList.size() > 0){
					/**
					 * 3.如果查询应收单有的话，那么直接核销始发应收单;
					 *   核销完之后,调用财务自助接口,将数据推送到财务自助那边
					 */
					BillReceivableEntity billReceivableEntity = billReceivableList.get(0);
					GreenHandWrapWriteoffEntity writeOffEntity = greenHandList.get(0);//获取暂存表中数据
					RequestGreenHandWrapEntity request = new RequestGreenHandWrapEntity();
					request.setCostType("0");//设置费用类型 
					request.setWaybillNo(waybillNo);//运单号
					request.setDopAmount(writeOffEntity.getAmount());//客户还款金额
					request.setDoptime(writeOffEntity.getDopTime());//客户还款时间
					greenHandWrapWriteoffService.writeoffByDoprequest(billReceivableEntity,request);
				}
			}
		}
		
	}
	
	/**
	 * 悟空通过运单信息生成现金收款单
	 * @author foss-231434-bieyexiong
	 * @date 2016-11-2
	 */
	private BillCashCollectionEntity getBillCashCollectoin(
			WaybillPickupInfoDto waybill, CurrentInfo currentInfo,
			WaybillFeeDto prePayFeeDto, Date date) {

		BillCashCollectionEntity entity = new BillCashCollectionEntity();

		entity.setId(UUIDUtils.getUUID());
		entity.setCashCollectionNo(settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.XS1)); // 现金收款单号
		entity.setActive(FossConstants.ACTIVE); // 是否有效
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO); // 是否红冲
		entity.setIsInit(FossConstants.NO); // 是否初始化
		entity.setVersionNo(FossConstants.INIT_VERSION_NO); // 初始化版本号
		entity.setCreateTime(date); // 创建时间
		entity.setModifyTime(date); // 修改时间
		entity.setAccountDate(date); // 记账日期
		entity.setBusinessDate(waybill.getBillTime()); // 业务日期
		entity.setStatus(SettlementDictionaryConstants.BILL_CASH_COLLECTION__STATUS__SUBMIT); // 单据状态
		entity.setPaymentType(waybill.getPaidMethod()); // 支付方式

		entity.setSourceBillType(SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL); // 来源单据类型
		entity.setSourceBillNo(waybill.getWaybillNo()); // 来源单据单号
		entity.setWaybillNo(waybill.getWaybillNo()); // 运单号
		entity.setWaybillId(waybill.getId()); // 运单ID
		entity.setProductCode(waybill.getProductCode()); // 产品类型
		entity.setProductId(waybill.getProductId()); // 产品Id
		entity.setBillType(SettlementDictionaryConstants.BILL_CASH_COLLECTION__BILL_TYPE__CASH_COLLECTION); // 单据类型

		entity.setCustomerCode(waybill.getDeliveryCustomerCode()); // 始发客户编码
		entity.setCustomerName(waybill.getDeliveryCustomerName()); // 始发客户名称

		// 当集中接货业务时，缴款部门是接送货组
		entity.setCreateOrgCode(waybill.getCreateOrgCode()); // 录入部门编码
		entity.setCreateOrgName(currentInfo.getCurrentDeptName()); // 录入部门名称

		// 当集中接货业务时，缴款部门是车队；非集中接送货为营业部；该部门负责缴款
		entity.setCollectionOrgCode(waybill.getCollectionOrgCode()); // 收款部门编码
		entity.setCollectionOrgName(waybill.getCollectionOrgName());
		entity.setCollectionCompanyCode(waybill.getCollectionCompanyCode()); // 收款部门所属子公司
		entity.setCollectionCompanyName(waybill.getCollectionCompanyName());

		// 运单的揽货部门、小票的收入部门
		entity.setGeneratingOrgCode(waybill.getReceiveOrgCode()); // 收入部门编码
		entity.setGeneratingOrgName(waybill.getReceiveOrgName());
		entity.setGeneratingCompanyCode(waybill.getReceiveSubsidiaryCode()); // 收入部门所属子公司编码
		entity.setGeneratingCompanyName(waybill.getReceiveSubsidiaryName());
		//快递代理 出发部门获取开单部门
		if(SettlementUtil.isPackageProductCode(waybill.getProductCode())){
			entity.setOrigOrgCode(waybill.getCreateOrgCode()); // 出发部门编码
			entity.setOrigOrgName(waybill.getCreateUserDeptName());
		}else{
			entity.setOrigOrgCode(waybill.getReceiveOrgCode()); // 出发部门编码
			entity.setOrigOrgName(waybill.getReceiveOrgName());
		}
		
		entity.setDestOrgCode(waybill.getLastLoadOrgCode()); // 到达部门编码
		entity.setDestOrgName(waybill.getLastLoadOrgName());

		entity.setCurrencyCode(waybill.getCurrencyCode()); // 币种

		// 金额
		entity.setAmount(prePayFeeDto.getTotalFee()); // 现金总额：预付金额
		entity.setTransportFee(prePayFeeDto.getTransportFee()); // 公布价运费
		entity.setPickupFee(prePayFeeDto.getPickupFee()); // 接货费
		entity.setDeliveryGoodsFee(prePayFeeDto.getDeliveryGoodsFee()); // 送货费
		entity.setPackagingFee(prePayFeeDto.getPackagingFee()); // 包装手续费
		entity.setCodFee(prePayFeeDto.getCodFee()); // 代收货款费
		entity.setInsuranceFee(prePayFeeDto.getInsuranceFee()); // 保价费
		entity.setOtherFee(prePayFeeDto.getOtherFee()); // 其他费用
		entity.setValueAddFee(prePayFeeDto.getValueAddFee()); // 增值费用
		entity.setPromotionsFee(prePayFeeDto.getPromotionsFee()); // 优惠费用
		
		entity.setPosSerialNum(waybill.getPosSerialNum());//POS串号
		entity.setBatchNo(waybill.getBatchNo());//银行交易流水号
		
		if(StringUtils.isBlank(waybill.getExpressOrigOrgCode())){
			throw new SettlementException("开单为德邦快递代理，其收入部门对应的快递代理点部不存在！");
		}
		//始发营业部点部映射
		entity.setExpressOrigOrgCode(waybill.getExpressOrigOrgCode());
		entity.setExpressOrigOrgName(waybill.getExpressOrigOrgName());
		if(StringUtils.isBlank(waybill.getExpressDestOrgCode())){
			throw new SettlementException("开单为德邦快递代理，其最终配载部门对应的快递代理点部不存在！");
		}
		//到达营业部点部映射
		entity.setExpressDestOrgCode(waybill.getExpressDestOrgCode());
		entity.setExpressDestOrgName(waybill.getExpressDestOrgName());
	  
		//设置现金收款单的发票标记
		entity.setInvoiceMark(waybill.getInvoiceMark());
		
		return entity;
	}
	
	/**
	 * 通过运单信息生成应收单
	 * 
	 * @author foss-231434-bieyexiong
	 * @date 2016-11-2
	 */
	private BillReceivableEntity buildBillReceivable(WaybillPickupInfoDto waybill, Date date) {
		BillReceivableEntity entity = new BillReceivableEntity();

		// 财务单据信息
		entity.setId(UUIDUtils.getUUID()); // ID
		entity.setActive(FossConstants.ACTIVE); // 是否有效
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO); // 是否红单
		entity.setIsInit(FossConstants.NO); // 是否初始化
		entity.setVersionNo(FossConstants.INIT_VERSION_NO); // 初始化版本号
		entity.setBusinessDate(waybill.getBillTime()); // 业务日期
		entity.setAccountDate(date); // 记账日期

		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO); // 系统生成方式
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE); // 审核状态
		entity.setWaybillId(waybill.getId()); // 运单ID
		entity.setWaybillNo(waybill.getWaybillNo()); // 运单号
		entity.setSourceBillNo(waybill.getWaybillNo()); // 来源单据单号
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL); // 来源单据类型

		// 发货客户、收获客户
		entity.setDeliveryCustomerCode(waybill.getDeliveryCustomerCode()); // 发货客户
		entity.setDeliveryCustomerName(waybill.getDeliveryCustomerName());
		//收货联系人   --2013-05-18 因为官网需要收货联系人，与许明明讨论收货人结算也用不到，故而此处存储收货联系人
		entity.setReceiveCustomerCode(waybill.getReceiveCustomerCode()); // 收货客户
		entity.setReceiveCustomerName(waybill.getReceiveCustomerContact());//此处获取收货联系人---配合官网

		// 收入部门：收货部门
		entity.setGeneratingOrgCode(waybill.getReceiveOrgCode()); // 收入部门
		entity.setGeneratingOrgName(waybill.getReceiveOrgName());
		entity.setGeneratingComCode(waybill.getReceiveSubsidiaryCode()); // 收入子公司
		entity.setGeneratingComName(waybill.getReceiveSubsidiaryName());

		//快递代理 出发部门获取开单部门
		if(SettlementUtil.isPackageProductCode(waybill.getProductCode())){
			entity.setOrigOrgCode(waybill.getCreateOrgCode()); // 出发部门编码
			entity.setOrigOrgName(waybill.getCreateUserDeptName());
		}else{
			entity.setOrigOrgCode(waybill.getReceiveOrgCode()); // 出发部门编码
			entity.setOrigOrgName(waybill.getReceiveOrgName());
		}
		entity.setDestOrgCode(waybill.getLastLoadOrgCode()); // 到达部门
		entity.setDestOrgName(waybill.getLastLoadOrgName());

		// 开单相关信息
		entity.setReceiveMethod(waybill.getReceiveMethod()); // 提货方式
		entity.setProductCode(waybill.getProductCode()); // 产品类型
		entity.setProductId(waybill.getProductId()); // 产品ID
		entity.setCurrencyCode(waybill.getCurrencyCode()); // 币种

		// 以下冗余信息，对账单需要
		entity.setGoodsName(waybill.getGoodsName()); // 货物名称
		if (waybill.getGoodsQtyTotal() != null) { // 货物总件数
			entity.setGoodsQtyTotal(NumberUtils.createBigDecimal(String
					.valueOf(waybill.getGoodsQtyTotal())));
		}
		entity.setGoodsVolumeTotal(waybill.getGoodsVolumeTotal()); // 货物总体积
		
		//空运取计费重量，否则取总重量
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybill.getProductCode())) {
			entity.setBillWeight(waybill.getBillWeight()); // 计费重量
		} else {
			entity.setBillWeight(waybill.getGoodsWeightTotal());
		}
		
		entity.setReceiveMethod(waybill.getReceiveMethod()); // 提货方式
		entity.setCustomerPickupOrgCode(waybill.getCustomerPickupOrgCode()); // 提货网点
		entity.setTargetOrgCode(waybill.getTargetOrgCode()); // 目的站
		
		if(StringUtils.isBlank(waybill.getExpressOrigOrgCode())){
			throw new SettlementException("开单为德邦快递代理，其收入部门对应的快递代理点部不存在！");
		}
		//始发营业部点部映射
		entity.setExpressOrigOrgCode(waybill.getExpressOrigOrgCode());
		entity.setExpressOrigOrgName(waybill.getExpressOrigOrgName());
		if(StringUtils.isBlank(waybill.getExpressDestOrgCode())){
			throw new SettlementException("开单为德邦快递代理，其最终配载部门对应的快递代理点部不存在！");
		}
		//到达营业部点部映射
		entity.setExpressDestOrgCode(waybill.getExpressDestOrgCode());
		entity.setExpressDestOrgName(waybill.getExpressDestOrgName());
		
		return entity;
	}

	
	/**
	 * 通过运单信息生成到付应收单
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-10-25 下午2:28:56
	 */
	/***
	 * 通过运单信息生成到付应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-3-14 下午5:02:31
	 * @param waybill
	 * @param date
	 * @param writeBackBillReceivableCOD
	 * @param writeBackBillReceivableDest
	 *            //到达运费应收单，如果为：空运到达运费应收单，
	 * 
	 *            2013-03-14日 更改处理在和大票时，运单没有代收货款应收单，只有到付运费应收单，
	 *            发更改之后，有了代收货款，那么生成的代收货款必须为代收货款应收单
	 * @return
	 */
	private BillReceivableEntity buildBillReceivableCOD(
			WaybillPickupInfoDto waybill, Date date,
			BillReceivableEntity writeBackBillReceivableCOD,
			BillReceivableEntity writeBackBillReceivableDest) {

		BillReceivableEntity entity = buildBillReceivable(waybill, date);

		// 到付，到达应收
		entity.setReceivableNo(settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.YS6)); // 应收单号
		entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT); // 付款方式
		entity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE); // 单据子类型

		// 应收部门：到达部门
		entity.setReceivableOrgCode(waybill.getLastLoadOrgCode()); // 应收部门编码
		entity.setReceivableOrgName(waybill.getLastLoadOrgName());
		// 催款部门：到达部门
		entity.setDunningOrgCode(waybill.getLastLoadOrgCode());
		entity.setDunningOrgName(waybill.getLastLoadOrgName());

		// 代收货款（属于增值业务）收入部门为空
		entity.setGeneratingOrgCode(null);
		entity.setGeneratingOrgName(null);
		entity.setGeneratingComCode(null);
		entity.setGeneratingComName(null);

		// 客户、发货客户、收货客户
		// 客户编码不为空则不能做到付签收转临欠月结
		entity.setCustomerCode(null); // 设置客户编码为空
		entity.setCustomerName(null);

		// 应收金额
		entity.setAmount(waybill.getCodAmount()); // 代收货款金额
		entity.setUnverifyAmount(entity.getAmount()); // 未核销金额
		entity.setVerifyAmount(BigDecimal.ZERO); // 已核销金额

		entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT); // 付款方式

		// 对于做过合票业务的单据，需要保留红单单据类型、客户信息
		if (writeBackBillReceivableCOD != null) {
			entity.setBillType(writeBackBillReceivableCOD.getBillType());
			entity.setCustomerCode(writeBackBillReceivableCOD.getCustomerCode());
			entity.setCustomerName(writeBackBillReceivableCOD.getCustomerName());
		}
		// 空运到达运费应收单
		else if (writeBackBillReceivableDest != null
				&& SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY
						.equals(writeBackBillReceivableDest.getBillType())) {
			// 到达应收单不为空，且类型为：空运到达运费应收单
			entity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD);// //
																										 // 空运代理代收货款应收
			entity.setCustomerCode(writeBackBillReceivableDest
					.getCustomerCode());// 应收单挂空运代理上
			entity.setCustomerName(writeBackBillReceivableDest
					.getCustomerName());// 应收单挂空运代理上
		}
		
		entity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);//代收货款应收单发票标记
        //代收货款的统一结算为否
        entity.setUnifiedSettlement(FossConstants.NO);
		return entity;
	}

	/**
	 * 通过运单信息生成到付应收单
	 * 
	 * @author IBM-zhuwei
	 * @date 2013-3-14 下午5:37:02
	 * @param waybill
	 * @param toPayFeeDto
	 * @param date
	 * @param writeBackBillReceivableDest
	 * @param writeBackBillReceivableCod
	 * 
	 *            运单在进入合大票前，只有代收货款，没有到付运费，进入合大票之后，代收货款应收单类型：空运代理代收货款应收单
	 *            合大票之后，发更改付款方式到付，存在到付金额，根据空运代理代收货款应收单设置到付运费应收单类型
	 * @return
	 */
	private BillReceivableEntity buildBillReceivableDest(
			WaybillPickupInfoDto waybill, WaybillFeeDto toPayFeeDto, Date date,
			BillReceivableEntity writeBackBillReceivableDest,
			BillReceivableEntity writeBackBillReceivableCod) {

		BillReceivableEntity entity = buildBillReceivable(waybill, date);

		// 到付，到达应收
		entity.setReceivableNo(settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.YS2)); // 应收单号
		entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT); // 付款方式
		entity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE); // 单据子类型

		// 应收部门：到达部门
		entity.setReceivableOrgCode(waybill.getLastLoadOrgCode()); // 应收部门编码
		entity.setReceivableOrgName(waybill.getLastLoadOrgName());
		// 催款部门：到达部门
		entity.setDunningOrgCode(waybill.getLastLoadOrgCode()); // 催款部门
		entity.setDunningOrgName(waybill.getLastLoadOrgName());

		// 结算客户：到付客户
		// 客户编码不为空则不能做到付签收转临欠月结
		entity.setCustomerCode(null); // 设置客户编码为空
		entity.setCustomerName(null);

		// 应收金额
		entity.setAmount(toPayFeeDto.getTotalFee()); // 到付金额
		entity.setUnverifyAmount(entity.getAmount()); // 未核销金额
		entity.setVerifyAmount(BigDecimal.ZERO); // 已核销金额

		entity.setTransportFee(toPayFeeDto.getTransportFee()); // 公布价运费
		entity.setPickupFee(toPayFeeDto.getPickupFee()); // 接货费
		entity.setDeliveryGoodsFee(toPayFeeDto.getDeliveryGoodsFee()); // 送货费
		entity.setPackagingFee(toPayFeeDto.getPackagingFee()); // 包装手续费
		entity.setCodFee(toPayFeeDto.getCodFee()); // 代收货款费
		entity.setInsuranceFee(toPayFeeDto.getInsuranceFee()); // 保价费
		entity.setOtherFee(toPayFeeDto.getOtherFee()); // 其他费用
		entity.setValueAddFee(toPayFeeDto.getValueAddFee()); // 增值费用
		entity.setPromotionsFee(toPayFeeDto.getPromotionsFee()); // 优惠费用

		// 对于做过合票业务的单据，需要保留红单单据类型、客户信息
		if (writeBackBillReceivableDest != null) {
			entity.setBillType(writeBackBillReceivableDest.getBillType());
			entity.setCustomerCode(writeBackBillReceivableDest
					.getCustomerCode());
			entity.setCustomerName(writeBackBillReceivableDest
					.getCustomerName());
		} else if (writeBackBillReceivableCod != null
				&& SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD
						.equals(writeBackBillReceivableCod.getBillType())) {
			entity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY);// 空运到达代理应收
			entity.setCustomerCode(writeBackBillReceivableCod.getCustomerCode());
			entity.setCustomerName(writeBackBillReceivableCod.getCustomerName());
		}
      
		entity.setInvoiceMark(waybill.getInvoiceMark());//发票标记

        //到达统一结算
        if (FossConstants.YES.equals(waybill.getDestUnifiedSettlement())) {
            //到达部门的合同编码和到达催款部门
            OrgAdministrativeInfoEntity orgEntity =orgAdministrativeInfoService
                    .queryOrgAdministrativeInfoByUnifiedCodeNoCache(waybill.getDestContractUnifiedCode());
            OrgAdministrativeInfoEntity duningOrgEntity =  orgAdministrativeInfoService
                    .queryOrgAdministrativeInfoByUnifiedCodeNoCache(waybill.getDestUnifiedDuningCode());
            if (orgEntity == null
                    || duningOrgEntity == null) {
                throw new SettlementException("到达应收,到达统一结算的合同部门编码或催款部门为空");
            } else {
                entity.setUnifiedSettlement(waybill.getDestUnifiedSettlement());
                //合同部门
                entity.setContractOrgCode(orgEntity.getCode());
                entity.setContractOrgName(orgEntity.getName());
                //应收部门
                entity.setReceivableOrgCode(orgEntity.getCode());
                entity.setReceivableOrgName(orgEntity.getName());
                //催款部门
                entity.setDunningOrgCode(duningOrgEntity.getCode());
                entity.setDunningOrgName(duningOrgEntity.getName());
            }
        } else {
            entity.setUnifiedSettlement(FossConstants.NO);
        }
        return entity;
    }

	/**
	 * 通过运单信息生成始发应收单
	 * 
	 * @author foss-231434-bieyexiong
	 * @date 2016-11-2
	 */
	private BillReceivableEntity buildBillReceivableOrig(
			WaybillPickupInfoDto waybill, WaybillFeeDto prePayFeeDto, Date date) {

		BillReceivableEntity entity = buildBillReceivable(waybill, date);

		entity.setReceivableNo(settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.YS1)); // 应收单号
		entity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE); // 单据子类型
		entity.setPaymentType(waybill.getPaidMethod()); // 付款方式

		// 结算客户：始发客户
		entity.setCustomerCode(waybill.getDeliveryCustomerCode()); // 客户编码
		entity.setCustomerName(waybill.getDeliveryCustomerName());

		// 应收部门：始发部门
		entity.setReceivableOrgCode(waybill.getReceiveOrgCode()); // 应收部门编码
		entity.setReceivableOrgName(waybill.getReceiveOrgName());
		// 催款部门：始发部门
		entity.setDunningOrgCode(waybill.getReceiveOrgCode()); // 催款部门
		entity.setDunningOrgName(waybill.getReceiveOrgName());

		// 应收金额
		entity.setAmount(prePayFeeDto.getTotalFee()); // 始发应收金额
		entity.setUnverifyAmount(entity.getAmount()); // 未核销金额
		entity.setVerifyAmount(BigDecimal.ZERO); // 已核销金额

		entity.setTransportFee(prePayFeeDto.getTransportFee()); // 公布价运费
		entity.setPickupFee(prePayFeeDto.getPickupFee()); // 接货费
		entity.setDeliveryGoodsFee(prePayFeeDto.getDeliveryGoodsFee()); // 送货费
		entity.setPackagingFee(prePayFeeDto.getPackagingFee()); // 包装手续费
		entity.setCodFee(prePayFeeDto.getCodFee()); // 代收货款费
		entity.setInsuranceFee(prePayFeeDto.getInsuranceFee()); // 保价费
		entity.setOtherFee(prePayFeeDto.getOtherFee()); // 其他费用
		entity.setValueAddFee(prePayFeeDto.getValueAddFee()); // 增值费用
		entity.setPromotionsFee(prePayFeeDto.getPromotionsFee()); // 优惠费用
		
		entity.setInvoiceMark(waybill.getInvoiceMark());//发票标记
        //始发应收合同部门
        if(FossConstants.YES.equals(waybill.getOrigUnifiedSettlement())){
            //到达部门的合同编码和到达催款部门
            OrgAdministrativeInfoEntity orgEntity =orgAdministrativeInfoService
                    .queryOrgAdministrativeInfoByUnifiedCodeNoCache(waybill.getOrigContractUnifiedCode());
            OrgAdministrativeInfoEntity duningOrgEntity =  orgAdministrativeInfoService
                    .queryOrgAdministrativeInfoByUnifiedCodeNoCache(waybill.getOrigUnifiedDuningCode());
            if (orgEntity == null
                    || duningOrgEntity == null) {
                throw new SettlementException("始发应收,始发统一结算的合同部门编码或催款部门为空");
            } else {
                entity.setUnifiedSettlement(waybill.getOrigUnifiedSettlement());
                //合同部门
                entity.setContractOrgCode(orgEntity.getCode());
                entity.setContractOrgName(orgEntity.getName());
                //应收部门
                entity.setReceivableOrgCode(orgEntity.getCode());
                entity.setReceivableOrgName(orgEntity.getName());
                //催款部门
                entity.setDunningOrgCode(duningOrgEntity.getCode());
                entity.setDunningOrgName(duningOrgEntity.getName());
            }
        }else{
            entity.setUnifiedSettlement(FossConstants.NO);
        }
		return entity;
	}

	/**
	 * 通过运单信息生成装卸费应付单
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-10-25 下午3:35:04
	 */
	private BillPayableEntity buildBillPayableSF(WaybillPickupInfoDto waybill,
			Date date) {

		BillPayableEntity entity = buildBillPayable(waybill, date);

		entity.setPayableNo(settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.YF2)); // 应付单号
		entity.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE); // 单据子类型

		// 应付金额
		entity.setAmount(waybill.getServiceFee()); // 应付装卸费
		entity.setVerifyAmount(BigDecimal.ZERO); // 已核销金额
		entity.setUnverifyAmount(entity.getAmount()); // 未核销金额

		// 联系人名称--接送货前端文本框手动填写的
		entity.setCustomerContactName(waybill.getDeliveryCustomerContact());

		// 联系人电话---发货客户手机号码不为空，设置为手机号码，要么设置为运单的发货客户电话
		entity.setCustomerPhone(StringUtils.isNotEmpty(waybill
				.getDeliveryCustomerMobilephone()) ? waybill
				.getDeliveryCustomerMobilephone() : waybill
				.getDeliveryCustomerPhone());
		
	   entity.setInvoiceMark(waybill.getInvoiceMark());//装卸费发票标记
       //装卸费统一结算
       if(FossConstants.YES.equals(waybill.getOrigUnifiedSettlement())){
           OrgAdministrativeInfoEntity orgEntity =orgAdministrativeInfoService
        		   .queryOrgAdministrativeInfoByUnifiedCodeNoCache(waybill.getOrigContractUnifiedCode());
           if(orgEntity == null){
        	   throw new SettlementException("生成劳务费，始发统一结算的合同部门为空");
           }else{
        	   entity.setUnifiedSettlement(waybill.getOrigUnifiedSettlement());
        	   entity.setContractOrgCode(orgEntity.getCode());
        	   entity.setContractOrgName(orgEntity.getName());
        	   entity.setPayableOrgCode(orgEntity.getCode());
        	   entity.setPayableOrgName(orgEntity.getName());
           }
       }else{
    	   entity.setUnifiedSettlement(FossConstants.NO);
       }
		
		return entity;
	}

	/**
	 * 通过运单信息生成代收货款应付单
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-10-25 下午3:35:04
	 */
	private BillPayableEntity buildBillPayableCOD(WaybillPickupInfoDto waybill,
			Date date) {

		BillPayableEntity entity = buildBillPayable(waybill, date);

		entity.setCodType(waybill.getRefundType());
		entity.setPayableNo(settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.YF1)); // 应付单号
		entity.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD); // 单据子类型

		// 应付金额
		entity.setAmount(waybill.getCodAmount()); // 应付代收货款
		entity.setVerifyAmount(BigDecimal.ZERO); // 已核销金额
		entity.setUnverifyAmount(entity.getAmount()); // 未核销金额
		
		entity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);//代收货款应付单发票标记

		return entity;
	}

	/**
	 * 通过运单信息生成应付单
	 * 
	 * @author foss-231434-bieyexiong
	 * @date 2016-11-2
	 */
	private BillPayableEntity buildBillPayable(WaybillPickupInfoDto waybill,
			Date date) {

		BillPayableEntity entity = new BillPayableEntity();

		// ID,应付单号,运单号,运单ID,生成方式,付款方
		entity.setId(UUIDUtils.getUUID());
		entity.setWaybillNo(waybill.getWaybillNo());
		entity.setWaybillId(waybill.getId());
		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		entity.setPayerType(SettlementDictionaryConstants.BILL_PAYABLE__PAYER_TYPE__ORIGIN);

		// 默认已审核，未冻结
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE); // 审核状态
		entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN); // 冻结状态
		entity.setPayableType(null);
		entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO); // 生效状态

		// 应付单单据信息
		entity.setSourceBillNo(waybill.getWaybillNo()); // 来源单据号
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL); // 来源单据类型
		entity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO); // 支付状态
		entity.setActive(FossConstants.YES); // 是否有效
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO); // 是否红单
		entity.setIsInit(FossConstants.NO); // 是否初始化
		entity.setVersionNo(FossConstants.INIT_VERSION_NO); // 版本号

		// 设置应付单部门编码、名称、子公司编码、名称
		entity.setPayableOrgCode(waybill.getReceiveOrgCode());
		entity.setPayableOrgName(waybill.getReceiveOrgName());
		entity.setPayableComCode(waybill.getReceiveSubsidiaryCode());
		entity.setPayableComName(waybill.getReceiveSubsidiaryName());

		//BUGKD-1646 代收货款应付单的始发部门信息应该取收货部门信息，而不是开单部门。
		entity.setOrigOrgCode(waybill.getReceiveOrgCode()); // 出发部门编码
		entity.setOrigOrgName(waybill.getReceiveOrgName());
		//BUGKD-1646 end

		// 到达部门名称、到达部门编码
		entity.setDestOrgCode(waybill.getLastLoadOrgCode());
		entity.setDestOrgName(waybill.getLastLoadOrgName());

		// 设置出发（应付）客户编码、名称
		entity.setCustomerCode(waybill.getDeliveryCustomerCode());
		entity.setCustomerName(waybill.getDeliveryCustomerName());
		entity.setCustomerContact(waybill.getReceiveCustomerContact());
		entity.setCustomerContactName(waybill.getReceiveCustomerContact());
		entity.setCustomerPhone(waybill.getReceiveCustomerMobilephone());

		// 设置币种、会计日期、业务日期
		entity.setCurrencyCode(waybill.getCurrencyCode());
		entity.setAccountDate(date);
		entity.setBusinessDate(waybill.getBillTime());

		// 设置日期
		entity.setEffectiveDate(null); // 生效日期
		entity.setCreateTime(date); // 创建时间
		entity.setModifyTime(date); // 修改时间

		// 运输性质
		entity.setProductCode(waybill.getProductCode());
		entity.setProductId(waybill.getProductId());
		
		if(StringUtils.isBlank(waybill.getExpressOrigOrgCode())){
			throw new SettlementException("开单为德邦快递代理，其收入部门对应的快递代理点部不存在！");
		}
		//始发营业部点部映射
		entity.setExpressOrigOrgCode(waybill.getExpressOrigOrgCode());
		entity.setExpressOrigOrgName(waybill.getExpressOrigOrgName());
		if(StringUtils.isBlank(waybill.getExpressDestOrgCode())){
			throw new SettlementException("开单为德邦快递代理，其最终配载部门对应的快递代理点部不存在！");
		}
		//到达营业部点部映射
		entity.setExpressDestOrgCode(waybill.getExpressDestOrgCode());
		entity.setExpressDestOrgName(waybill.getExpressDestOrgName());

		return entity;
	}

	/**
	 * 金额拆分
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-11-2 下午5:08:43
	 */
	private void splitWaybillFee(WaybillEntity waybill		,
			WaybillFeeDto prePayFeeDto, WaybillFeeDto toPayFeeDto) {

		BigDecimal totalFee = waybill.getTotalFee().subtract(
				waybill.getCodAmount()); // 总金额（去除代收货款）
		BigDecimal prePayAmount = waybill.getPrePayAmount(); // 预付金额

		BigDecimal toPayAmount = waybill.getToPayAmount().subtract( // 到付
				waybill.getCodAmount()); // 到付金额（去除代收货款）

		if (prePayAmount.compareTo(BigDecimal.ZERO) == 0) { // 预付金额等于0

			toPayFeeDto.setTotalFee(toPayAmount); // 现金总额
			toPayFeeDto.setTransportFee(waybill.getTransportFee()); // 公布价运费
			toPayFeeDto.setPickupFee(waybill.getPickupFee()); // 接货费
			toPayFeeDto.setDeliveryGoodsFee(waybill.getDeliveryGoodsFee()); // 送货费
			toPayFeeDto.setPackagingFee(waybill.getPackageFee()); // 包装手续费
			toPayFeeDto.setCodFee(waybill.getCodFee()); // 代收货款费
			toPayFeeDto.setInsuranceFee(waybill.getInsuranceFee()); // 保价费
			toPayFeeDto.setOtherFee(waybill.getOtherFee()); // 其他费用
			toPayFeeDto.setValueAddFee(waybill.getValueAddFee()); // 增值费用
			toPayFeeDto.setPromotionsFee(waybill.getPromotionsFee()); // 优惠费用

		} else if (toPayAmount.compareTo(BigDecimal.ZERO) == 0) { // 到付金额等于0

			prePayFeeDto.setTotalFee(prePayAmount); // 现金总额
			prePayFeeDto.setTransportFee(waybill.getTransportFee()); // 公布价运费
			prePayFeeDto.setPickupFee(waybill.getPickupFee()); // 接货费
			prePayFeeDto.setDeliveryGoodsFee(waybill.getDeliveryGoodsFee()); // 送货费
			prePayFeeDto.setPackagingFee(waybill.getPackageFee()); // 包装手续费
			prePayFeeDto.setCodFee(waybill.getCodFee()); // 代收货款费
			prePayFeeDto.setInsuranceFee(waybill.getInsuranceFee()); // 保价费
			prePayFeeDto.setOtherFee(waybill.getOtherFee()); // 其他费用
			prePayFeeDto.setValueAddFee(waybill.getValueAddFee()); // 增值费用
			prePayFeeDto.setPromotionsFee(waybill.getPromotionsFee()); // 优惠费用

		} else {

			prePayFeeDto.setTotalFee(prePayAmount);
			toPayFeeDto.setTotalFee(toPayAmount);

			// 公布价运费：运单公布价运费 × (现付/（总运费））；
			// 接货费：运单接货费 × (现付/（总运费））；
			// 送货费：运单送货费 × (现付/（总运费））；
			// 包装手续费：运单包装手续费 ×(现付/（总运费））；
			// 代收货款手续费：运单代收货款手续费 × (现付/（总运费））；
			// 保价费：运单保价费 × (现付/（总运费））；
			BigDecimal transportFee = waybill.getTransportFee();
			BigDecimal promotionsFee = waybill.getPromotionsFee();
			BigDecimal pickupFee = waybill.getPickupFee();
			BigDecimal deliveryGoodsFee = waybill.getDeliveryGoodsFee();
			BigDecimal packageFee = waybill.getPackageFee();
			BigDecimal codFee = waybill.getCodFee();
			BigDecimal insuranceFee = waybill.getInsuranceFee();

			// 费用精确两位，统一向下取整
			transportFee = transportFee.multiply(prePayAmount).divide(totalFee,
					DIVDE_NUMBER, BigDecimal.ROUND_FLOOR);
			promotionsFee = promotionsFee.multiply(prePayAmount).divide(
					totalFee, DIVDE_NUMBER, BigDecimal.ROUND_FLOOR);
			pickupFee = pickupFee.multiply(prePayAmount).divide(totalFee,
					DIVDE_NUMBER, BigDecimal.ROUND_FLOOR);
			deliveryGoodsFee = deliveryGoodsFee.multiply(prePayAmount).divide(
					totalFee, DIVDE_NUMBER, BigDecimal.ROUND_FLOOR);
			packageFee = packageFee.multiply(prePayAmount).divide(totalFee,
					DIVDE_NUMBER, BigDecimal.ROUND_FLOOR);
			codFee = codFee.multiply(prePayAmount).divide(totalFee,
					DIVDE_NUMBER, BigDecimal.ROUND_FLOOR);
			insuranceFee = insuranceFee.multiply(prePayAmount).divide(totalFee,
					DIVDE_NUMBER, BigDecimal.ROUND_FLOOR);

			// 除其他费外增值费：接货费+送货费+运单包装手续费+代收货款手续费+保价费
			BigDecimal noOtherValueFee = NumberUtils.sum(pickupFee,
					deliveryGoodsFee, packageFee, codFee, insuranceFee);

			// 其他费用：运单预付运费金额-公布价运费-接货费-送货费-运单包装手续费-代收货款手续费-保价费
			// 公布价费用包含了优惠费用
			BigDecimal otherFee = prePayAmount.subtract(transportFee).subtract(
					noOtherValueFee);

			// 增值费用：接货费+送货费+运单包装手续费+代收货款手续费+保价费+其他费
			BigDecimal valueAddFee = noOtherValueFee.add(otherFee);

			prePayFeeDto.setTotalFee(prePayAmount);
			prePayFeeDto.setTransportFee(transportFee);
			prePayFeeDto.setPickupFee(pickupFee);
			prePayFeeDto.setDeliveryGoodsFee(deliveryGoodsFee);
			prePayFeeDto.setPackagingFee(packageFee);
			prePayFeeDto.setCodFee(codFee);
			prePayFeeDto.setInsuranceFee(insuranceFee);
			prePayFeeDto.setOtherFee(otherFee);
			prePayFeeDto.setValueAddFee(valueAddFee);
			prePayFeeDto.setPromotionsFee(promotionsFee);

			transportFee = waybill.getTransportFee();
			promotionsFee = waybill.getPromotionsFee();
			pickupFee = waybill.getPickupFee();
			deliveryGoodsFee = waybill.getDeliveryGoodsFee();
			packageFee = waybill.getPackageFee();
			codFee = waybill.getCodFee();
			insuranceFee = waybill.getInsuranceFee();

			// 费用精确两位，统一向下取整
			transportFee = transportFee.multiply(toPayAmount).divide(totalFee,
					DIVDE_NUMBER, BigDecimal.ROUND_FLOOR);
			promotionsFee = promotionsFee.multiply(toPayAmount).divide(
					totalFee, DIVDE_NUMBER, BigDecimal.ROUND_FLOOR);
			pickupFee = pickupFee.multiply(toPayAmount).divide(totalFee,
					DIVDE_NUMBER, BigDecimal.ROUND_FLOOR);
			deliveryGoodsFee = deliveryGoodsFee.multiply(toPayAmount).divide(
					totalFee, DIVDE_NUMBER, BigDecimal.ROUND_FLOOR);
			packageFee = packageFee.multiply(toPayAmount).divide(totalFee,
					DIVDE_NUMBER, BigDecimal.ROUND_FLOOR);
			codFee = codFee.multiply(toPayAmount).divide(totalFee,
					DIVDE_NUMBER, BigDecimal.ROUND_FLOOR);
			insuranceFee = insuranceFee.multiply(toPayAmount).divide(totalFee,
					DIVDE_NUMBER, BigDecimal.ROUND_FLOOR);

			// 除其他费外增值费：接货费+送货费+运单包装手续费+代收货款手续费+保价费
			noOtherValueFee = NumberUtils.sum(pickupFee, deliveryGoodsFee,
					packageFee, codFee, insuranceFee);

			// 其他费用：运单预付运费金额-公布价运费-接货费-送货费-运单包装手续费-代收货款手续费-保价费
			// 公布价费用包含了优惠费用
			otherFee = toPayAmount.subtract(transportFee).subtract(
					noOtherValueFee);

			// 增值费用：接货费+送货费+运单包装手续费+代收货款手续费+保价费+其他费
			valueAddFee = noOtherValueFee.add(otherFee);

			toPayFeeDto.setTotalFee(toPayAmount);
			toPayFeeDto.setTransportFee(transportFee);
			toPayFeeDto.setPickupFee(pickupFee);
			toPayFeeDto.setDeliveryGoodsFee(deliveryGoodsFee);
			toPayFeeDto.setPackagingFee(packageFee);
			toPayFeeDto.setCodFee(codFee);
			toPayFeeDto.setInsuranceFee(insuranceFee);
			toPayFeeDto.setOtherFee(otherFee);
			toPayFeeDto.setValueAddFee(valueAddFee);
			toPayFeeDto.setPromotionsFee(promotionsFee);
		}

	}

	/**
	 * 验证新增运单合法性
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-11-5 上午10:58:44
	 */
	private void validAddedWaybillParam(WaybillPickupInfoDto waybill,
			CurrentInfo currentInfo) {

		// 接送货校验 1) 运单是否合法 2)运单是否存在或重复或作废 3)产品类型必须正确

		// 校验产品类型为空
		if (StringUtils.isEmpty(waybill.getProductCode())
				|| StringUtils.isEmpty(waybill.getProductId())) {
			throw new SettlementException("产品类型不能为空");
		}

		// 校验运单的收货部门、接货部门、到达部门、录入部门为空
		if (StringUtils.isEmpty(waybill.getCreateOrgCode())
				|| StringUtils.isEmpty(waybill.getReceiveOrgCode())
				|| StringUtils.isEmpty(waybill.getLastLoadOrgCode())) {
			throw new SettlementException("部门信息不完整");
		}

		// 开单付款方式不能为电汇或支票
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
				.equals(waybill.getPaidMethod())
				|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE
						.equals(waybill.getPaidMethod())) {
			throw new SettlementException("开单付款方式不能为支票或电汇！");
		}

		// 校验运单金额，预付、到付、公布价运费、送货费、包装费、代收货款手续费、保价费，其它费用，优惠费用单个字段的有效性，其中任意一项为空或者小于0
		if (waybill.getTotalFee() == null
				|| waybill.getTotalFee().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getPrePayAmount() == null
				|| waybill.getPrePayAmount().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getToPayAmount() == null
				|| waybill.getToPayAmount().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getCodAmount() == null
				|| waybill.getCodAmount().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getTransportFee() == null
				|| waybill.getTransportFee().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getPickupFee() == null
				|| waybill.getPickupFee().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getDeliveryGoodsFee() == null
				|| waybill.getDeliveryGoodsFee().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getPackageFee() == null
				|| waybill.getPackageFee().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getCodFee() == null
				|| waybill.getCodFee().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getInsuranceFee() == null
				|| waybill.getInsuranceFee().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getOtherFee() == null
				//ISSUE-2816
				//|| waybill.getOtherFee().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getValueAddFee() == null
				//ISSUE-2816
				//|| waybill.getValueAddFee().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getPromotionsFee() == null
				|| waybill.getPromotionsFee().compareTo(BigDecimal.ZERO) < 0) {
			logger.info("运单部分字段金额不正确，不能继续操作："+"总金额："+waybill.getTotalFee()+"，预付款："+waybill.getPrePayAmount()+
					"，到付："+waybill.getToPayAmount()+"，代收："+waybill.getCodAmount() +"，运费："+waybill.getTransportFee()+"，接货费："+waybill.getPickupFee()+"，送货费："+waybill.getDeliveryGoodsFee()+
					"，包装费："+waybill.getPackageFee()+",代收费："+waybill.getCodFee()+"，保费："+waybill.getInsuranceFee()+"，其他费用："+waybill.getOtherFee()+"，增值费："+waybill.getValueAddFee()+"，优惠金额："+waybill.getPromotionsFee());
			throw new SettlementException("运单部分字段金额不正确，不能继续操作");
		}

		// 校验运单内部金额是否正确。如果运单的（预付+到付-代收货款）不等于（公布价运费、接货费、送货费、包装费、代收货款手续费、保价费、其他费用之和时
		// 公布价运费已经包含了优惠费用，不再扣减
		BigDecimal totalTransFee = waybill.getTotalFee().subtract(
				waybill.getCodAmount());
		BigDecimal totalTransFee1 = waybill.getPrePayAmount()
				.add(waybill.getToPayAmount()).subtract(waybill.getCodAmount());
		BigDecimal totalTransFee2 = NumberUtils.sum(waybill.getTransportFee(),
				waybill.getPickupFee(), waybill.getDeliveryGoodsFee(),
				waybill.getPackageFee(), waybill.getCodFee(),
				waybill.getInsuranceFee(), waybill.getOtherFee());

		if (totalTransFee.compareTo(totalTransFee1) != 0
				|| totalTransFee.compareTo(totalTransFee2) != 0) {
			throw new SettlementException("明细之和不等于总运费，不能继续操作");
		}

		// 确定开单时代收货款金额的最大值和最小值限制
		if (waybill.getCodAmount() != null
				&& waybill.getCodAmount().compareTo(BigDecimal.ZERO) > 0) {
			
			//默认获取零担的代收货款上下限配置参数
			String codMaxParam = ConfigurationParamsConstants.STL_COD_MAX_AMOUNT;
			String codMinParam = ConfigurationParamsConstants.STL_COD_MIN_AMOUNT;
			
			//如果为快递，则获取快递的代收货款上下线
			if(SettlementUtil.isPackageProductCode(waybill.getProductCode())){
				codMaxParam = ConfigurationParamsConstants.STL_PACKAGE_COD_MAX_AMOUNT;
				codMinParam = ConfigurationParamsConstants.STL_PACKAGE_COD_MIN_AMOUNT;
			}
			String maxAmount = configurationParamsService
					.queryConfValueByCode(codMaxParam);
			String minAmount = configurationParamsService
					.queryConfValueByCode(codMinParam);

			if (waybill.getCodAmount().compareTo(
					NumberUtils.createBigDecimal(maxAmount)) > 0) {
				throw new SettlementException("代收货款金额超过最大值：" + maxAmount
						+ "，不能继续操作！");
			}
			if (waybill.getCodAmount().compareTo(
					NumberUtils.createBigDecimal(minAmount)) < 0) {
				throw new SettlementException("代收货款金额低于最小值：" + minAmount
						+ "，不能继续操作！");
			}
		}
		
		// 校验发票标记是否合法
		if (StringUtil.isEmpty(waybill.getInvoiceMark())) {
			throw new SettlementException("没有发票标记");
		}
		// 判断发票标记是否在
		else if (!SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_ONE
				.equals(waybill.getInvoiceMark())
				&& !SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO
						.equals(waybill.getInvoiceMark())) {
           throw new SettlementException("发票标记不在范围之内："+waybill.getInvoiceMark());
		}
	}

	/**
	 * 优化运单开单，新建运单新增接口
	 * 
	 * @author ddw
	 * @date 2012-11-5 上午11:06:35
	 */
	private void handleAddedWaybillNew(WaybillPickupInfoDto waybill,WaybillPickupInfoDto oldWaybill,
			CurrentInfo currentInfo, WaybillPickupWriteBackDto dto) {
		// 会计时间为分区键，开单时保证各个单据的时间保持一致
		Date now = new Date();
		
		// 生成开单流水 update by foss-231434-bieyexiong 悟空开单，走另一个逻辑
		//(根据stl.t_stl_pod的 id 判断是否重复单据，提前，若已生成过，直接异常返回)
		addPODEntityEcs(waybill, currentInfo, now);
		
		// 由于到达应收单和代收货款应收单在空运合票时单据类型和客户信息发生变化
		// 这些蓝单的信息取自红单
		BillReceivableEntity writeBackBillReceivableDest = null; // 到达应收单
		BillReceivableEntity writeBackBillReceivableCod = null; // 代收货款应收单
		if (dto != null && CollectionUtils.isNotEmpty(dto.getWriteBackBillReceivables())) {
			for (BillReceivableEntity entity : dto.getWriteBackBillReceivables()) {
				if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
						.equals(entity.getBillType())
						|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE
								.equals(entity.getBillType())
						|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY
								.equals(entity.getBillType())
						|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE
								.equals(entity.getBillType())) {
					writeBackBillReceivableDest = entity;
				} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
						.equals(entity.getBillType())
						|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD
								.equals(entity.getBillType())
						|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD
								.equals(entity.getBillType())) {
					writeBackBillReceivableCod = entity;
				}
			}
		}	

		String paymentType = waybill.getPaidMethod(); // 付款方式
		BigDecimal prePayAmount = waybill.getPrePayAmount(); // 预付金额（包括现金和欠款）
		BigDecimal toPayAmount = waybill.getToPayAmount().subtract(waybill.getCodAmount()); // 到付金额（去除代收货款）

		BillCashCollectionEntity billCashCollectionEntity = null; // 现金收款单
		BillReceivableEntity billReceivableOrig = null; // 始发应收单
		BillReceivableEntity billReceivableDest = null; // 到付应收单
		BillReceivableEntity billReceivableCod = null; // 代收货款应收单
		BillPayableEntity billPayableSF = null; // 应付装卸费
		BillPayableEntity billPayableCOD = null; // 应付代收货款

		WaybillFeeDto prePayFeeDto = new WaybillFeeDto(); // 预付费用分摊
		WaybillFeeDto toPayFeeDto = new WaybillFeeDto(); // 到付费用分摊

		splitWaybillFee(waybill, prePayFeeDto, toPayFeeDto); // 金额拆分

		// 付款时存在情况有：部分预付，部分到付，此时预付必须都是现金支付；不存在以下情况：部分现付，部分临欠
		if (prePayAmount.compareTo(BigDecimal.ZERO) > 0
				&& toPayAmount.compareTo(BigDecimal.ZERO) == 0) { // 1)全部预付

			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH
					.equals(paymentType)
					|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD
							.equals(paymentType)) { // 使用现金、银行卡付款

				billCashCollectionEntity = this.getBillCashCollectoin(waybill,
						currentInfo, prePayFeeDto, now);
				billCashCollectionService.addBillCashCollection(
						billCashCollectionEntity, currentInfo);

			} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(paymentType)
					|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(paymentType)) { 
				// 月结、网上支付合并

				// 生成应收单信息
				billReceivableOrig = this.buildBillReceivableOrig(waybill,prePayFeeDto, now);

				billReceivableService.addBillReceivable(billReceivableOrig,	currentInfo);
			} else {
				throw new SettlementException("付款方式有误，不能执行此操作");
			}

		} else if (prePayAmount.compareTo(BigDecimal.ZERO) == 0
				&& toPayAmount.compareTo(BigDecimal.ZERO) > 0) { // 2)全部到付

			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT
					.equals(paymentType)) {
				//ddw,判断到达部门是否为合伙人，如果是合伙人则在PTP系统生成应收单
				if(!isPartnerDept(waybill.getLastLoadOrgCode())){
					billReceivableDest = this.buildBillReceivableDest(waybill,
							toPayFeeDto, now, writeBackBillReceivableDest,
							writeBackBillReceivableCod);

					billReceivableService.addBillReceivable(billReceivableDest, currentInfo);
				}
			} else {
				throw new SettlementException("付款方式有误，不能执行此操作");
			}

		} else if (prePayAmount.compareTo(BigDecimal.ZERO) > 0
				&& toPayAmount.compareTo(BigDecimal.ZERO) > 0) { // 3)部分预付，部分到付

			// 预付处理
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH
					.equals(paymentType)
					|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD
							.equals(paymentType)) { // 使用现金、银行卡付款

				billCashCollectionEntity = this.getBillCashCollectoin(waybill,
						currentInfo, prePayFeeDto, now);
				billCashCollectionService.addBillCashCollection(
						billCashCollectionEntity, currentInfo);

			} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT
					.equals(paymentType)) { // 到付

				billCashCollectionEntity = this.getBillCashCollectoin(waybill,
						currentInfo, prePayFeeDto, now);
				// 现金收款单中的支付方式只能是现金或者银行卡
				billCashCollectionEntity
						.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
				billCashCollectionService.addBillCashCollection(
						billCashCollectionEntity, currentInfo);

			} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(paymentType)) { 
				// 月结
				
				billReceivableOrig = this.buildBillReceivableOrig(waybill,
						prePayFeeDto, now);

				billReceivableService.addBillReceivable(billReceivableOrig,	currentInfo);
			} else {
				throw new SettlementException("付款方式有误，不能执行此操作");
			}
			//ddw,判断到达部门是否为合伙人，如果是合伙人则在PTP系统生成应收单
			if(!isPartnerDept(waybill.getLastLoadOrgCode())){
				// 到付处理
				billReceivableDest = this.buildBillReceivableDest(waybill,
						toPayFeeDto, now, writeBackBillReceivableDest,
						writeBackBillReceivableCod);
				//判断是否为快递
				billReceivableService.addBillReceivable(billReceivableDest,	currentInfo);
				
			}
		}

		// 代收货款金额大于0，则生成代收货款
		if (waybill.getCodAmount() != null
				&& waybill.getCodAmount().compareTo(BigDecimal.ZERO) > 0) {

			// 处理开代收款生成应付单前验证
			validateHandleCod(waybill);
			//ddw,判断到达部门是否为合伙人，如果是合伙人则在PTP系统生成应收单
			if(!isPartnerDept(waybill.getLastLoadOrgCode())){
				// 生成代收货款应收单
				billReceivableCod = buildBillReceivableCOD(waybill, now,
						writeBackBillReceivableCod, writeBackBillReceivableDest);
				billReceivableService.addBillReceivable(billReceivableCod,
						currentInfo);
			}
			// 生成代收货款应付单
			billPayableCOD = buildBillPayableCOD(waybill, now);
			billPayableService.addBillPayable(billPayableCOD, currentInfo);

			// 调用代收货款服务
			this.billPayCODService.addBillCOD(waybill, currentInfo);
		}

		// 装卸费金额大于0，生成装卸费应付单
		if (waybill.getServiceFee() != null
				&& waybill.getServiceFee().compareTo(BigDecimal.ZERO) > 0) {
			if (StringUtils.isEmpty(waybill.getDeliveryCustomerContact())) {
				throw new SettlementException("当运单存在装卸费时，客户联系人不能为空！");
			}

			// 发货客户手机和发货客户电话同时为空时，提示不能操作
			if (StringUtils.isEmpty(waybill.getDeliveryCustomerMobilephone())
					&& StringUtils.isEmpty(waybill.getDeliveryCustomerPhone())) {
				throw new SettlementException("当运单存在装卸费时，联系电话不能为空！");
			}
			billPayableSF = buildBillPayableSF(waybill, now);
			billPayableService.addBillPayable(billPayableSF, currentInfo);
		}

		/**
		 * @author 218392 zhangyongxue
		 * @date 2016-02-23 12:12:12
		 * 裹裹项目：当DOP到FOSS结算的付款信息先到的情况下，补录之后生成的结算单据信息在后的情况下
		 * （1）先到裹裹暂存表中查询：根据运单号查询为未核销的；
		 * （2）如果有说明DOP的付款信息先到了；如果没有说明DOP的付款信息没有到。
		 * （3）然后，再调用裹裹的结算单据处理Service处理
		 */
		List<GreenHandWrapWriteoffEntity> greenHandList = new ArrayList<GreenHandWrapWriteoffEntity>();
		/**
	     * 是否裹裹运单<br/>
	     * 结算根据isWrap判断是否是裹裹运单，如果isWrap字段值是“Y”，则需校验暂存表是否存在支付信息，存在支付信息，就自动核销始发应收单.不存在支付信息，就不做自动核销操作；
	     * 如果isWrap字段值为“N”,则不校验暂存表中的支付信息；
	     * @author 326181
	     * @date 2016-10-03 9:50:45
	     * 
	     */
		if(waybill != null && (FossConstants.YES.equals(waybill.getIsWrap()) || !SettlementDictionaryConstants.SOURCE_SYSTEM_ECS.equals(waybill.getSourceSystem()))){
			String waybillNo = waybill.getWaybillNo();
			//根据单号查询未核销暂存表的信息
			greenHandList = greenHandWrapWriteoffDao.queryGreenHandWrapByWaybillNo(waybillNo);
			//查询结果不为空，并且长度>0,
			RequestGreenHandWrapEntity wrapEntity = new RequestGreenHandWrapEntity();
			wrapEntity.setWaybillNo(waybillNo);
			if(greenHandList != null && greenHandList.size()>0){
				/**
				 * 运单号查询满足本次项目需求条件的应收单
				 * （条件是：始发应收单，未核销金额大于0，有效的，来源单据类型为 W-开单）
				 */
				List<BillReceivableEntity> billReceivableList = greenHandWrapWriteoffService.queryReceivableBill(wrapEntity);
				try{
					if(billReceivableList != null && billReceivableList.size() > 1){
						/**
						 * 单子存在多条始发应收单，则财务单据已经生成,则需要将数据推送到报账平台
						 */
						wrapEntity.setIsPush("true");//是否需要推送
						wrapEntity.setResource("GG");//来源
						wrapEntity.setIsException("Y");//是否异常
						wrapEntity.setCostType("0");//设置费用类型 
						wrapEntity.setWaybillNo(waybillNo);//运单号
						wrapEntity.setDopAmount(greenHandList.get(0).getAmount());//客户还款金额
						wrapEntity.setDoptime(greenHandList.get(0).getDopTime());//客户还款时间
						throw new SettlementException("单号："+ waybillNo + "存在多条始发应收单");
					}else if(billReceivableList != null && billReceivableList.size() == 1){
						/**
						 * 3.如果查询应收单有的话，那么直接核销始发应收单;
						 *   核销完之后,调用财务自助接口,将数据推送到财务自助那边
						 */
						BillReceivableEntity billReceivableEntity = billReceivableList.get(0);
						GreenHandWrapWriteoffEntity writeOffEntity = greenHandList.get(0);//获取暂存表中数据
						RequestGreenHandWrapEntity request = new RequestGreenHandWrapEntity();
						request.setCostType("0");//设置费用类型 
						request.setWaybillNo(waybillNo);//运单号
						request.setDopAmount(writeOffEntity.getAmount());//客户还款金额
						request.setDoptime(writeOffEntity.getDopTime());//客户还款时间
						wrapEntity.setCostType("0");//设置费用类型 
						wrapEntity.setWaybillNo(waybillNo);//运单号
						wrapEntity.setDopAmount(greenHandList.get(0).getAmount());//客户还款金额
						wrapEntity.setDoptime(greenHandList.get(0).getDopTime());//客户还款时间
						try{
							greenHandWrapWriteoffService.writeoffByDoprequest(billReceivableEntity,request);
						}catch(Exception e){
							//核销失败之后,将异常数据推送
							wrapEntity.setIsPush("true");//是否需要推送
							wrapEntity.setResource("GG");//来源
							wrapEntity.setIsException("Y");//是否异常
							throw new SettlementException(waybillNo+"单子核销失败！");
						}
						// 假如系统核销成功,则正常推送到财务自助
						wrapEntity.setIsPush("true");//是否需要推送
						wrapEntity.setResource("GG");//来源
						wrapEntity.setIsException("N");//正常推送
					}
				}catch(SettlementException e){
					logger.info("核销失败："+e.getErrorCode());
				}catch(Exception e1){
					logger.info("核销失败："+e1.getMessage());
				}finally{
					/**
					 * 补录单子的时候，所有的情况都要推送到财务自助,异常推送和正常推送
					 */
					if("true".equals(wrapEntity.getIsPush())){
						//将DOP推送过来的数据，对接到报账平台
						logger.info("是否注入接口"+fossToFinsRemitCommonService);
						//悟空运单捕获异常，其他运单不管
						if (SettlementDictionaryConstants.SOURCE_SYSTEM_ECS.equals(waybill.getSourceSystem())) {
							try {
								fossToFinsRemitCommonService.pushRemittanceMessToFins(wrapEntity);
							} catch(SettlementException e){
								logger.info("推送到财务自助失败："+e.getErrorCode());
								ecsFossErrorLogJobService.addEcsFossErrorLogJob("ESB_FOSS2ESB_INCOME_REPORTED", "推送第三方付款数据到财务自助", wrapEntity, e.getErrorCode());
							} catch (Exception e) {
								logger.info("推送到财务自助失败："+e.toString());
								//悟空运单推送失败时使用job定时推送
								ecsFossErrorLogJobService.addEcsFossErrorLogJob("ESB_FOSS2ESB_INCOME_REPORTED", "推送第三方付款数据到财务自助", wrapEntity, e.toString());
							}
						} else {
							fossToFinsRemitCommonService.pushRemittanceMessToFins(wrapEntity);
						}
					}else{
						throw new SettlementException("该运单来源于裹裹订单,请选择付款方式为网上支付！");
					}
				}
				
			}
		}
		
	}

	/**
	 * 处理开代收款生成应付单前验证
	 * 
	 * @author guxinhua
	 * @param
	 * @date 2013-1-29 下午8:03:03
	 * @return
	 */
	private void validateHandleCod(WaybillPickupInfoDto waybill) {

		// 对公对私标记，需要根据综合管理里面的属性进行处理--运单开单信息，把综合规定的编码“对公对私标记”转为结算规定的编码
		boolean bl = false;
		if (DictionaryValueConstants.CRM_ACCOUNT_NATURE_PRIVATE_ACCOUNT
				.equals(waybill.getPublicPrivateFlag())) {
			// 对私账户
			waybill.setPublicPrivateFlag(SettlementDictionaryConstants.COD__PUBLIC_PRIVATE_FLAG__RESIDENT);
			bl = true;
		} else if (DictionaryValueConstants.CRM_ACCOUNT_NATURE_PUBLIC_ACCOUNT
				.equals(waybill.getPublicPrivateFlag())) {
			// 对公账户
			waybill.setPublicPrivateFlag(SettlementDictionaryConstants.COD__PUBLIC_PRIVATE_FLAG__COMPANY);
			bl = true;
		}

		// 代收货款类型
		String refundType = waybill.getRefundType();
		if (StringUtils.isBlank(refundType)) {
			throw new SettlementException("代收货款类型为空,不能新增代收货款");
		}

		// 代收货款应付单客户编码不能为空
		if (StringUtils.isEmpty(waybill.getDeliveryCustomerCode())) {
			throw new SettlementException("发货客户编码不能为空");
		}

		// 银行账户
		if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY
				.equals(refundType)
				|| SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY
						.equals(refundType)) {
			if (!bl) {// 即日退和3日退需要校验账号的对公对私标记是否合法
				throw new SettlementException("客户账号对公对私标记不合法！"
						+ waybill.getPublicPrivateFlag());
			}

			String headMsg = "即日退、三日退类型代收货款";
			if (StringUtils.isBlank(waybill.getAccountName())) {
				throw new SettlementException(headMsg + "收款人不能为空");
			}
			if (StringUtils.isBlank(waybill.getAccountCode())) {
				throw new SettlementException(headMsg + "银行帐号不能为空");
			}
			if (StringUtils.isBlank(waybill.getBankHQCode())) {
				throw new SettlementException(headMsg + "开户行编码不能为空");
			}
			if (StringUtils.isBlank(waybill.getAccountBank())) {
				throw new SettlementException(headMsg + "开户行不能为空");
			}
			if (StringUtils.isBlank(waybill.getProvinceCode())
					|| StringUtils.isBlank(waybill.getProvinceName())) {
				throw new SettlementException(headMsg + "省不能为空");
			}
			if (StringUtils.isBlank(waybill.getCityCode())
					|| StringUtils.isBlank(waybill.getCityName())) {
				throw new SettlementException(headMsg + "市不能为空");
			}
			if (StringUtils.isBlank(waybill.getPublicPrivateFlag())) {
				throw new SettlementException(headMsg + "对公对私标志不能为空");
			}
//			if (StringUtils.isBlank(waybill.getPayeePhone())) {
//				throw new SettlementException(headMsg + "手机号码不能为空");
//			}
			if (StringUtils.isBlank(waybill.getBankBranchCode())
					|| StringUtils.isBlank(waybill.getBankBranchName())) {
				throw new SettlementException(headMsg + "支行编码/名称不能为空");
			}

			// 如果代收货款类型为"即日退"，则代收货款的退款账号类型不能为"对公帐号"
			if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY
					.equals(refundType)
					&& (!StringUtils
							.equals(waybill.getPublicPrivateFlag(),
									SettlementDictionaryConstants.COD__PUBLIC_PRIVATE_FLAG__RESIDENT))) {
				throw new SettlementException(
						"如果代收货款类型为即日退，则代收货款的退款账号类型不能为对公帐号 ");
			}

			// 如果代收货款类型为"即日退"，则其开户银行必须在即日退所属银行范围内（即日退所属银行范围已经提交给综合管理做基础资料了）
			if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY
					.equals(refundType)) {
				BankEntity entity = new BankEntity();
				// 银行编码
				entity.setCode(waybill.getBankHQCode());
				boolean bool = salesPayCODService
						.checkBankIntraDayTypeByBankEntity(entity);
				if (!bool) {
					throw new SettlementException(
							"如果代收货款类型为即日退，则其开户银行必须在即日退所属银行范围内 ");
				}

			}

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
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @param billCashCollectionService
	 */
	public void setBillCashCollectionService(
			IBillCashCollectionService billCashCollectionService) {
		this.billCashCollectionService = billCashCollectionService;
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
	 * @param customerBargainService
	 */
	public void setCustomerBargainService(
			ICustomerBargainService customerBargainService) {
		this.customerBargainService = customerBargainService;
	}

	public void setFossToFinsRemitCommonService(
			IFossToFinsRemitCommonService fossToFinsRemitCommonService) {
		this.fossToFinsRemitCommonService = fossToFinsRemitCommonService;
	}

	/**
	 * @param billPayCODService
	 */
	public void setBillPayCODService(IBillPayCODService billPayCODService) {
		this.billPayCODService = billPayCODService;
	}

	/**
	 * @param codCommonService
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	/**
	 * @param statementOfAccountService
	 */
	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	/**
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @param configurationParamsService
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * @param billBadAccountService
	 */
	public void setBillBadAccountService(
			IBillBadAccountService billBadAccountService) {
		this.billBadAccountService = billBadAccountService;
	}

	/**
	 * @param customerService
	 *            the customerService to set
	 */
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * Sets the sales pay cod service.
	 * 
	 * @param salesPayCODService
	 *            the new sales pay cod service
	 */
	public void setSalesPayCODService(ISalesPayCODService salesPayCODService) {
		this.salesPayCODService = salesPayCODService;
	}

	/**
	 * @param podService
	 *            the podService to set
	 */
	public void setPodService(IPODService podService) {
		this.podService = podService;
	}

	/**
	 * @SET
	 * @param expressPartSalesDeptService
	 */
	public void setExpressPartSalesDeptService(
			IExpressPartSalesDeptService expressPartSalesDeptService) {
		/*
		 *@set
		 *@this.expressPartSalesDeptService = expressPartSalesDeptService
		 */
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setGreenHandWrapWriteoffDao(
			IGreenHandWrapWriteoffDao greenHandWrapWriteoffDao) {
		this.greenHandWrapWriteoffDao = greenHandWrapWriteoffDao;
	}

	public void setDiscountManagementService(IDiscountManagementService discountManagementService) {
		this.discountManagementService = discountManagementService;
	}
	
	public void setFreightDiscountService(IFreightDiscountService freightDiscountService) {
		this.freightDiscountService = freightDiscountService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	/**
	 * 
	 * @param orgCode
	 * @return
	 */
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
	 * @SET
	 * @param grayCustomerService
	 */
	public void setGrayCustomerService(IGrayCustomerService grayCustomerService) {
		this.grayCustomerService = grayCustomerService;
	}

	public void setCodAuditService(ICodAuditService codAuditService) {
		this.codAuditService = codAuditService;
	}
	
	public void setActualFreightDao(IActualFreightDao actualFreightDao) {
		this.actualFreightDao = actualFreightDao;
	}
	
	public void setEcsFossErrorLogJobService(
			IEcsFossErrorLogJobService ecsFossErrorLogJobService) {
		this.ecsFossErrorLogJobService = ecsFossErrorLogJobService;
	}

	public ISettlementCommonService getSettlementCommonService() {
		return settlementCommonService;
	}

	public IBillCashCollectionService getBillCashCollectionService() {
		return billCashCollectionService;
	}

	public IBillReceivableService getBillReceivableService() {
		return billReceivableService;
	}

	public IFossToFinsRemitCommonService getFossToFinsRemitCommonService() {
		return fossToFinsRemitCommonService;
	}

	public IBillPayableService getBillPayableService() {
		return billPayableService;
	}

	public ICustomerBargainService getCustomerBargainService() {
		return customerBargainService;
	}

	public IBillPayCODService getBillPayCODService() {
		return billPayCODService;
	}

	public ICodCommonService getCodCommonService() {
		return codCommonService;
	}

	public IStatementOfAccountService getStatementOfAccountService() {
		return statementOfAccountService;
	}

	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public IConfigurationParamsService getConfigurationParamsService() {
		return configurationParamsService;
	}

	public IBillBadAccountService getBillBadAccountService() {
		return billBadAccountService;
	}

	public ICustomerService getCustomerService() {
		return customerService;
	}

	public ISalesPayCODService getSalesPayCODService() {
		return salesPayCODService;
	}

	public IPODService getPodService() {
		return podService;
	}

	public IExpressPartSalesDeptService getExpressPartSalesDeptService() {
		return expressPartSalesDeptService;
	}

	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}

	public IDiscountManagementService getDiscountManagementService() {
		return discountManagementService;
	}

	public IFreightDiscountService getFreightDiscountService() {
		return freightDiscountService;
	}

	public ICodAuditService getCodAuditService() {
		return codAuditService;
	}

	public IGrayCustomerService getGrayCustomerService() {
		return grayCustomerService;
	}

	public IGreenHandWrapWriteoffDao getGreenHandWrapWriteoffDao() {
		return greenHandWrapWriteoffDao;
	}

	public ISaleDepartmentService getSaleDepartmentService() {
		return saleDepartmentService;
	}

	public IGreenHandWrapWriteoffService getGreenHandWrapWriteoffService() {
		return greenHandWrapWriteoffService;
	}

	public IWSCManageService getWscManageService() {
		return wscManageService;
	}

	public IEcsFossErrorLogJobService getEcsFossErrorLogJobService() {
		return ecsFossErrorLogJobService;
	}

	public IActualFreightDao getActualFreightDao() {
		return actualFreightDao;
	}


}
