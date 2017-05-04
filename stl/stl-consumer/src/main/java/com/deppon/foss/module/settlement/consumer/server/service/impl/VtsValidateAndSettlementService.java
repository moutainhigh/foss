package com.deppon.foss.module.settlement.consumer.server.service.impl;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
//import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.BigDecimalOperationUtil;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao;
import com.deppon.foss.module.pickup.sign.api.shared.define.RepaymentConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ICUBCGrayService;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayResultDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCResponseDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossSearchTradeRequestDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossSearchTradeResponseDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossToCubcRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.TradeDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IVtsValidateAndSettlementDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICubcSettlementService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IPaymentSettlementService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ITakingService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IVtsValidateAndSettlementService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.FinancialDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.SettlementPayToVtsDto;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CubcSettleException;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/** 
 * vts参数校验及结清货款
 * @author foss结算-306579-guoxinru 
 * @date 2016-5-6 下午4:45:08    
 */
public class VtsValidateAndSettlementService implements IVtsValidateAndSettlementService {
	private String queryTradeListUrl;
	public void setQueryTradeListUrl(String queryTradeListUrl) {
		this.queryTradeListUrl = queryTradeListUrl;
	}
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	private static final String SERVICE_CODE = "com.deppon.foss.module.settlement.consumer.server.service.impl.VtsValidateAndSettlementService";
	private ICUBCGrayService cUBCGrayService;
	public void setcUBCGrayService(ICUBCGrayService cUBCGrayService) {
		this.cUBCGrayService = cUBCGrayService;
	}
	private IWaybillDao waybillDao;
    public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}
	
	/** 日志服务. */
	private static final Logger LOGGER = LoggerFactory.getLogger(VtsValidateAndSettlementService.class);
	
	/** 2013-08-01 */
	public static final String dateStr = "2013-08-01";
	
	/** 时间格式 */
	public static final String format = "yyyy-MM-dd";

	/** 登录服务service. */
//	private ILoginService loginService;
	
	/** 运单service. */
	private IWaybillManagerService waybillManagerService;
	private ITakingService takingService;
	
	/** 更改单service. */
	private IWaybillRfcService waybillRfcService;
	
	/** 实际货物服务. */
	private IActualFreightService actualFreightService;
	
	/** 中转配载校验.*/
	//private IArrivalService arrivalService;
	
	/** 结算应收单服务. */
	private IBillReceivableService billReceivableService;
	
	/** 到达联服务. */
//	private IArriveSheetManngerService arriveSheetManngerService;
	
	/** 签收结算货款服务. */
	private IPaymentSettlementService paymentSettlementService;
	
	/** 财务校验及结清货款dao. */
	private IVtsValidateAndSettlementDao vtsValidateAndSettlementDao;
	
	/** 付款信息dao. */
	private IRepaymentDao repaymentDao;
	private IRepaymentService repaymentService;
	
	/**
	 * 注入CUBC结清转接口Service
	 */
	private ICubcSettlementService cubcSettlementService;
	
	/**
	 * T+0报表
	 * @author 309603 yangqiang
	 * @date 2016-02-23
	 */
	private IPdaPosManageService pdaPosManageService;
	
	
	
	/**
	 * vts参数校验及结清货款
	 */
	@Override
	@Transactional
	public void ValidateAndSettlement(SettlementPayToVtsDto dto, CurrentInfo currentInfo) {
		if(null==dto || null==currentInfo){
			throw new SettlementException("传入结清货款信息或登录用户信息为空！");
		}
//		else if(dto.getCodAmount().equals(BigDecimal.ZERO)&&dto.getActualFreight().equals(BigDecimal.ZERO)){
//			throw new SettlementException("此笔实付代收货款和实付到付运费不能同时为0！");
//		}
		// 新增付款信息
		addPaymentInfo(dto, currentInfo);
		//快速结清判断
		if(null != dto.getIsQuick()){
			if(dto.getIsQuick()){
				LOGGER.info("vts调用foss快速结清开始,运单号："+dto.getWaybillNo());
				quickSettlement(dto,currentInfo);
				LOGGER.info("VTS调用foss快速结清货款成功...运单号："+dto.getWaybillNo());
			}
		}
}

	/**
	 * 新增付款信息
	 * @param dto
	 * @param currentInfo
	 */
	@SuppressWarnings("unused")
	private void addPaymentInfo(SettlementPayToVtsDto dto,CurrentInfo currentInfo) {
		//记录日志
		LOGGER.info("新增付款信息开始"+"运单号："+dto.getWaybillNo());
		// 判断传入参数 付款 对象为空
		if (null == dto) {
			throw new RepaymentException("传入参数付款对象不能为空！运单号:"+dto.getWaybillNo());
		}
		// 运单
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
		if(null==waybillEntity){
			throw new SettlementException("根据运单号查询运单为空!");
		}
		
		// 结清前验证
		verification (dto, currentInfo, waybillEntity);
		
		// 查询实际货物信息
		ActualFreightEntity actentity = actualFreightService.queryByWaybillNo(dto.getWaybillNo());		
		// 判断运单 是否已终止或已作废
		if (actentity != null && (WaybillConstants.ABORTED.equals(actentity.getStatus()) 
				|| WaybillConstants.OBSOLETE.equals(actentity.getStatus()))) {
			// 抛出业务异常
			throw new RepaymentException("当前运单已经中止/作废/未补录，不可以进行操作!"); 
		}
		
		// 得到结算应收单信息
		FinancialDto financialDto = queryFinanceSign(dto.getWaybillNo());
		// 应收代收款
		BigDecimal receiveableAmount = financialDto.getReceiveableAmount()==null ? BigDecimal.ZERO : financialDto.getReceiveableAmount();
		// 应收到付款
		BigDecimal receiveablePayAmout = financialDto.getReceiveablePayAmoout() ==null ? BigDecimal.ZERO : financialDto.getReceiveablePayAmoout();
		//记录日志
		LOGGER.info("应收代收货款："+receiveableAmount);
		LOGGER.info("应收到付款："+receiveablePayAmout);
		
		// 是否返货单标识
		//boolean isReturnOrders = false;
		
		// 创建 付款 对象 充作查询条件
		SettlementPayToVtsDto queryParam = new SettlementPayToVtsDto();
		queryParam.setActive(FossConstants.ACTIVE);
		queryParam.setWaybillNo(dto.getWaybillNo());
		// 获得付款LIST--通过运单号
		List<SettlementPayToVtsDto> repaymentList = vtsValidateAndSettlementDao.searchRepaymentList(queryParam);
		
		// 实付运费
		BigDecimal actualFreight = dto.getActualFreight() == null ? BigDecimal.ZERO : dto
				.getActualFreight();
		// 代收货款
		BigDecimal codAmount = dto.getCodAmount() == null ? BigDecimal.ZERO : dto
				.getCodAmount();
		
		// 遍历付款LIST
		for (SettlementPayToVtsDto repayment : repaymentList) {
			// 财务单据未生成 或 财务单据生成中
			if (RepaymentConstants.STLBILL_NOGENERATE.equals(repayment.getStlbillGeneratedStatus())
					|| RepaymentConstants.STLBILL_GENERATEING.equals(repayment.getStlbillGeneratedStatus())) {
				codAmount = codAmount.add(repayment.getCodAmount());
				actualFreight = actualFreight.add(repayment.getActualFreight());
			}
		}
		//记录日志
		LOGGER.info("实付到付款："+actualFreight);
		LOGGER.info("实付代收货款："+codAmount);
		
		// 判断实付运费大于应收到付款
		if (actualFreight.compareTo(receiveablePayAmout) == 1) {
			// 抛出业务异常
			throw new RepaymentException("实付运费不能大于应收到付款！");
		// 判断实付运费小于应收到付款
		}
		
		//20160810 354830 孙小雪 版本号：FOSS20160906 整车取消一次结清限制，允许部分结清
		/*else if(actualFreight.compareTo(BigDecimal.ZERO)!=0 && actualFreight.compareTo(receiveablePayAmout) == -1){
			// 抛出业务异常
			throw new RepaymentException("运费发生改变，实付运费小于应收到付款！");
		}*/
	
		// 判断代收货款大于应收代收款
		if (codAmount.compareTo(receiveableAmount) == 1) {
			// 抛出业务异常
			throw new RepaymentException("代收货款不能大于应收代收款！");
		// 判断代收货款小于应收代收款
		}
		
		//20160810 354830 孙小雪 版本号：FOSS20160906 整车取消一次结清限制，允许部分结清
		/*else if(codAmount.compareTo(BigDecimal.ZERO)!=0 && codAmount.compareTo(receiveableAmount) == -1){
			// 抛出业务异常
			throw new RepaymentException("代收货款发生改变，实付代收货款小于应收代收货款！");
		}*/
		
		// 判断是否统一结算--306579 vts整车无统一结算
/*		if (FossConstants.ACTIVE.equals(actentity.getArriveCentralizedSettlement()) && !isReturnOrders) {
			// 判断实付运费等于 0 并且 代收货款大于 0
			if (dto.getActualFreight().compareTo(BigDecimal.ZERO) == 0
					&& dto.getCodAmount().compareTo(BigDecimal.ZERO) > 0) {
				if (Arrays.asList("DT", "CT").contains(dto.getPaymentType())) {
					throw new RepaymentException("统一结算限制:实付运费=0，代收>0时，以代收货款标准限制付款方式（不可为临欠、月结）");
				}
				// 判断实付运费大于0 并且代收货款大于0
			} else if (dto.getActualFreight().compareTo(BigDecimal.ZERO) > 0
					&& dto.getCodAmount().compareTo(BigDecimal.ZERO) > 0) {
				if (!"TT".equals(dto.getPaymentType())) {
					throw new RepaymentException("统一结算限制:实付运费>0，代收>0时，付款方式限制（只可为电汇）");
				}
			}
		}*/
		
		// 调用 根据不同的付款方式 结清货款方法
		settleUpRepayment(dto, currentInfo, receiveableAmount, receiveablePayAmout);
		//记录日志
		LOGGER.info("新增付款信息成功"+"运单号："+dto.getWaybillNo());
	}
	
	/**
	 * 快速结清--对接整车
	 * guoxinru--306579
	 */
	@Override
	@Transactional
	public void quickSettlement(SettlementPayToVtsDto dto,CurrentInfo currentInfo) {
		try {
			if (dto != null){
				// 获取运单号
				String waybillNo = dto.getWaybillNo();
				// 根据单号查询实际货物
				ActualFreightEntity actualFreightEntity = null;
				try {
					actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
				} catch (SettlementException e) {
					LOGGER.error("根据单号查询实际货物失败："+e.getErrorCode());
					throw new SettlementException("根据单号查询实际货物失败："+e.getErrorCode());
				}
				// 判断是否已结清货款
				if (actualFreightEntity != null && FossConstants.YES.equals(actualFreightEntity.getSettleStatus())) {
					return;
				}
				// 如果该单没有结清货款
				else{
					// 获得未生成财务单据的付款记录
					RepaymentEntity entity = new RepaymentEntity();
					// 运单号
					entity.setWaybillNo(waybillNo);
					// 有效
					entity.setActive(FossConstants.ACTIVE);
					// 财务单据未生成
					entity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOGENERATE);
					// 财务单据生成中 
					entity.setFirStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATEING);
					List<RepaymentEntity> repaymentEntityList = repaymentDao.queryRepaymentList(entity);
					// 如果该单不存在未付款的财务单据，返回false
					if (CollectionUtils.isEmpty(repaymentEntityList)){
						return;
					} else{
						for (RepaymentEntity irepaymentEntity : repaymentEntityList){
							//生成uuid
							String uuid = UUIDUtils.getUUID();
							// 占用付款信息jobid
							irepaymentEntity.setJobId(uuid);
							// 先更新当前付款信息jobid
							repaymentService.updateRepayment(irepaymentEntity);
							
							// 调用结算结清货款接口
							String message = operatepaymentSettlement(irepaymentEntity,currentInfo);
							if ((SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE).equals(message)){
								//返回
								return;
							}
						}
						//返回错误
						return;
					}
				}
				
				
			}
		} catch (SettlementException e) {
			LOGGER.error("vts整车调用foss快速结清失败："+e.getErrorCode());
			throw new SettlementException(e.getErrorCode());
		}
	}

	/**
	 * 快速结清--调用结算结清货款接口--对接整车
	 * @param irepaymentEntity
	 * @return
	 */
	private String operatepaymentSettlement(RepaymentEntity irepaymentEntity,CurrentInfo currentInfo) throws SettlementException{
		// 如果irepaymentEntity不为空
		if (irepaymentEntity != null){
			// 创建Map集合存放查询条件
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("waybillNo", irepaymentEntity.getWaybillNo());
			params.put("active", FossConstants.YES);
			
			//结清货款
			return paymentSettlement(irepaymentEntity, currentInfo);
		}
		return null;
	}

	/**
	 * 快速结清货款--结清货款
	 * @param irepaymentEntity
	 * @param currentInfo
	 * @return
	 */
	private String paymentSettlement(RepaymentEntity repaymentEntity,CurrentInfo currentInfo) {
		try {
			PaymentSettlementDto dto = new PaymentSettlementDto();
			String paymentType = null;
			// 得到付款方式
			if (repaymentEntity != null){
				//付款方式
				paymentType = repaymentEntity.getPaymentType();
			} else{
				//抛出异常 参数错误
				throw new RepaymentException("传入参数有误！");
			}
			
			//得到结算应收单信息
			FinancialDto financialDto = queryFinanceSign(repaymentEntity.getWaybillNo());
			//应收代收款
			BigDecimal receiveableAmount = financialDto.getReceiveableAmount()==null ? BigDecimal.ZERO : financialDto.getReceiveableAmount();
			//已收代收款
			BigDecimal receivedAmount= financialDto.getReceivedAmount()==null ? BigDecimal.ZERO : financialDto.getReceivedAmount();
			//应收到付款
			BigDecimal receiveablePayAmoout = financialDto.getReceiveablePayAmoout() ==null ? BigDecimal.ZERO : financialDto.getReceiveablePayAmoout();
			//已收到付款
			BigDecimal receivedPayAmount = financialDto.getReceivedPayAmount() ==null ? BigDecimal.ZERO : financialDto.getReceivedPayAmount();
			
			//运单号
			dto.setWaybillNo(repaymentEntity.getWaybillNo());
			//结清方式     243921
			dto.setSettleApproach(repaymentEntity.getSettleApproach());
			//付款类型
			dto.setPaymentType(repaymentEntity.getPaymentType());
			//部门cide
			dto.setDestOrgCode(repaymentEntity.getOperateOrgCode());
			//部门名称
			dto.setDestOrgName(repaymentEntity.getOperateOrgName());
			//客户code
			dto.setCustomerCode(repaymentEntity.getConsigneeCode());
			//客户名称
			dto.setCustomerName(repaymentEntity.getConsigneeName());
			//时间
			dto.setBusinessDate(repaymentEntity.getPaymentTime());
			//付款编号
			dto.setSourceBillNo(repaymentEntity.getRepaymentNo());
			//款项认领编号
			dto.setPaymentNo(repaymentEntity.getClaimNo());
			//实收代收货款费用
			dto.setCodFee(repaymentEntity.getCodAmount());
			//实收到付运费
			dto.setToPayFee(repaymentEntity.getActualFreight());
			
			//解决由于JOB并发导致现金结清付款表为0情况----start
			//应收到付款
			BigDecimal actualFreight = repaymentEntity.getActualFreight() ==null ? BigDecimal.ZERO : repaymentEntity.getActualFreight();
			//已收到付款
			BigDecimal codAmount = repaymentEntity.getCodAmount() ==null ? BigDecimal.ZERO : repaymentEntity.getCodAmount();

			if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH.equals(paymentType)
			        && BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
			        && BigDecimalOperationUtil.compare(receiveablePayAmoout, BigDecimal.ZERO)
			        && (receivedAmount.compareTo(BigDecimal.ZERO)>0
			        || receivedPayAmount.compareTo(BigDecimal.ZERO)>0)
			        && (actualFreight.compareTo(BigDecimal.ZERO)>0
			                || codAmount.compareTo(BigDecimal.ZERO)>0)) {
				if(this.isPayment(repaymentEntity.getWaybillNo())){
					return null;
				}
			}
			//解决由于JOB并发导致现金结清付款表为0情况----end
			
			//当实付运费和代收货款同时为0时 更改为无需生成
			if(BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
				&& BigDecimalOperationUtil.compare(receiveablePayAmoout, BigDecimal.ZERO)){
				//无需结清 将付款信息置0
				repaymentEntity.setActualFreight(BigDecimal.ZERO);
				//无需结清 将付款信息置0
				repaymentEntity.setCodAmount(BigDecimal.ZERO);
				// 财务单据无需生成
				repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
			}else{
				// 财务单据已生成
				repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
			}
			//更改时间
			repaymentEntity.setModifyDate(new Date());
			repaymentDao.updateRepayment(repaymentEntity);
			//当实付运费和代收货款同时为0时 更改为已结清
			if(BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
				&& BigDecimalOperationUtil.compare(receiveablePayAmoout, BigDecimal.ZERO)){
				// 更新ActualFreight表中的结清状态为已结清
				ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
				//运单号
				actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
				//已结清
				actualFreightEntity.setSettleStatus(FossConstants.YES);
				//结清时间
				actualFreightEntity.setSettleTime(new Date());
				//更新actualFreight表
				actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
			}
			else{
				//------CUBC灰度结清服务开始--------add by 353654
				String message = null;
				String vestSystemCode = null;
				try {
					ArrayList<String> arrayList = new ArrayList<String>();
					arrayList.add(dto.getWaybillNo());
					CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
							SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".operatepaymentSettlement",
							SettlementConstants.TYPE_FOSS);
					CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
					List<VestBatchResult> list = response.getVestBatchResult();
					vestSystemCode = list.get(0).getVestSystemCode();	
				} catch (Exception e) {
					LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
					throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
				}
				if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
					LOGGER.info("调用结算接口开始" + "运单号：" + repaymentEntity.getWaybillNo());// 记录日志
					message = paymentSettlementService.confirmToPayment(dto, currentInfo);
					LOGGER.info("调用结算接口结束");// 记录日志			
				}
				if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
					FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
					fossDto.setDto(dto);
					fossDto.setCurrentInfo(currentInfo);
					CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
					if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
						if(StringUtils.isNotBlank(resultDto1.getMessage())){
							LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
							throw new CubcSettleException(resultDto1.getMessage());
						}
					}
					if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
						message = SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE;
					}
				}
				//------CUBC灰度结清服务开始--------add by 353654
				if ((SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE).equals(message)) {
					// 更新ActualFreight表中的结清状态为已结清
					ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
					// 运单号
					actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
					// 已结清
					actualFreightEntity.setSettleStatus(FossConstants.YES);
					// 结清时间
					actualFreightEntity.setSettleTime(new Date());
					// 更新actualFreight表
					actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
				}
				
				repaymentService.addJobSuccess(repaymentEntity.getId());
				return message;
			}
		} catch (SettlementException e) {
			LOGGER.info("vts调用foss快速结清异常："+e.getErrorCode());
			throw new SettlementException(e.getErrorCode());
		}
		return null;
	}

	/** 
	 * 根据不同的付款方式 结清货款
	 * @param repaymentEntity 付款 对象
	 * @param currentInfo 当前信息对象
	 * @param receiveableAmount 应收代收款
	 * @param receiveablePayAmoout 应收到付款
	 */
	private void settleUpRepayment(SettlementPayToVtsDto dto,CurrentInfo currentInfo, BigDecimal receiveableAmount,BigDecimal receiveablePayAmout) {
		//记录日志
		LOGGER.info("根据不同方式结清货款..."+"运单号："+dto.getWaybillNo());
		// 生成付款编号
		StringBuffer dateStr = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		//拼接付款编号
		dateStr = dateStr.append(dto.getWaybillNo());
		// 付款方式为现金时，暂不调用结算接口，暂存到付款表中，30分钟后job再调用实收货款接口
		if ((SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH).equals(dto.getPaymentType())) {
			//记录日志
			LOGGER.info("根据不同方式结清货款,付款方式：现金;"+"运单号："+dto.getWaybillNo());
			//付款编号
			dto.setRepaymentNo(dateStr.toString());
			//付款时间
			dto.setPaymentTime(new Date());
			//操作人
			dto.setOperator(currentInfo.getEmpName());
			//操作人编码
			dto.setOperatorCode(currentInfo.getEmpCode());
			//操作部门
			dto.setOperateOrgName(currentInfo.getCurrentDeptName());
			//操作部门编码
			dto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
			//币种
			dto.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			//当实付运费和代收货款同时为0时 设置为财务单据无需生成
			if (BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
					//20160810 354830 孙小雪 版本号：FOSS20160906 结清货款状态现改为由foss传递至vts
					&& BigDecimalOperationUtil.compare(receiveablePayAmout, BigDecimal.ZERO)/*&&"Y".equals(dto.getSettleStatus())*/) {
				// 更新ActualFreight表中的结清状态为已结清
				ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
				//运单号
				actualFreightEntity.setWaybillNo(dto.getWaybillNo());
				//结清状态-已结清
				actualFreightEntity.setSettleStatus(FossConstants.YES);
				//结款日期
				actualFreightEntity.setSettleTime(new Date());
				//收货人
				actualFreightEntity.setDeliverymanName(dto.getDeliverymanName());
				//证件类型
				actualFreightEntity.setIdentifyType(dto.getIdentifyType());
				//证件号
				actualFreightEntity.setIdentifyCode(dto.getIdentifyCode());
				if (StringUtil.isNotBlank(dto.getCodIdentifyCode())) {
					//证件类型(代收人)
					actualFreightEntity.setCodIdentifyType(dto.getCodIdentifyType());
					//证件号码（代收）
					actualFreightEntity.setCodIdentifyCode(dto.getCodIdentifyCode());
				} else {
					//证件类型(代收人)
					actualFreightEntity.setCodIdentifyType("");
					//证件号码（代收）
					actualFreightEntity.setCodIdentifyCode("");
				}
				//更新actualFreight表
				actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
				// 无需结清 将付款信息置0
				dto.setActualFreight(BigDecimal.ZERO);
				// 无需结清 将付款信息置0
				dto.setCodAmount(BigDecimal.ZERO);
				// 设置财务单据无需生成
				dto.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
			} else {
				// 更新ActualFreight表中的提货人、身份证
				ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
				//运单号
				actualFreightEntity.setWaybillNo(dto.getWaybillNo());
				//收货人
				actualFreightEntity.setDeliverymanName(dto.getDeliverymanName());
				//证件类型
				actualFreightEntity.setIdentifyType(dto.getIdentifyType());
				//证件号
				actualFreightEntity.setIdentifyCode(dto.getIdentifyCode());
				if (StringUtil.isNotBlank(dto.getCodIdentifyCode())) {
					//证件类型(代收人)
					actualFreightEntity.setCodIdentifyType(dto.getCodIdentifyType());
					//证件号码（代收人）
					actualFreightEntity.setCodIdentifyCode(dto.getCodIdentifyCode());
				} else {
					//证件类型(代收人)
					actualFreightEntity.setCodIdentifyType("");
					//证件号码（代收）
					actualFreightEntity.setCodIdentifyCode("");
				}
				//更新actualFreight表
				actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity); // 不是子母件
			    //设置财务单据未生成
				dto.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOGENERATE);
			}
			// 生成付款信息
			vtsValidateAndSettlementDao.addPaymentInfo(dto);
//			// 生成到达联信息
//			ArriveSheetEntity entity = new ArriveSheetEntity();
//			//运单号
//			entity.setWaybillNo(dto.getWaybillNo());
//			//收货人
//			entity.setDeliverymanName(dto.getDeliverymanName());
//			//证件类型
//			entity.setIdentifyType(dto.getIdentifyType());
//			//证件号
//			entity.setIdentifyCode(dto.getIdentifyCode());
//			// 调用到达联接口
//			arriveSheetManngerService.checkGenerateArriveSheet(entity);
//			//更新到达联,通过运单号
//			arriveSheetManngerService.updateArriveSheetEntityByWaybillNo(dto.getWaybillNo(), dto.getDeliverymanName(), dto.getIdentifyType(), dto.getIdentifyCode());
		} else {
			// 结算传入
			PaymentSettlementDto paymentSettlementDto = new PaymentSettlementDto();
			// 如果付款方式为临欠或月结时 调用结算接口-到付运费结转临欠月结
			//20160810 354830 孙小雪 版本号：FOSS20160906 结清货款状态现改为由foss传递至vts
			if ((SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT).equals(dto.getPaymentType()) || (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT).equals(dto.getPaymentType())/*&&"Y".equals(dto.getSettleStatus())*/) {
				//记录日志
				LOGGER.info("根据不同方式结清货款,付款方式：临欠或月结;"+"运单号："+dto.getWaybillNo());
				// 运单号
				paymentSettlementDto.setWaybillNo(dto.getWaybillNo());
				// 付款类型
				paymentSettlementDto.setPaymentType(dto.getPaymentType());
				// 部门cide
				paymentSettlementDto.setDestOrgCode(currentInfo.getCurrentDeptCode());
				// 部门名称
				paymentSettlementDto.setDestOrgName(currentInfo.getCurrentDeptName());
				// 客户code
				paymentSettlementDto.setCustomerCode(dto.getConsigneeCode());
				// 客户名称
				paymentSettlementDto.setCustomerName(dto.getConsigneeName());
				// 时间
				paymentSettlementDto.setBusinessDate(new Date());
				// 实收代收货款费用
				paymentSettlementDto.setCodFee(dto.getCodAmount());
				// 实收到付运费
				paymentSettlementDto.setToPayFee(dto.getActualFreight());
				//设置系统传输唯一标识
				paymentSettlementDto.setBusinessId(dto.getBusinessId());
				//------CUBC灰度结清服务开始-------add by 353654
				String vestSystemCode = null;
	            try {
	            	ArrayList<String> arrayList = new ArrayList<String>();
                	arrayList.add(paymentSettlementDto.getWaybillNo());
                	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".settleUpRepayment",
                			SettlementConstants.TYPE_FOSS);
                	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
	            	List<VestBatchResult> list = response.getVestBatchResult();
	            	vestSystemCode = list.get(0).getVestSystemCode();		
				} catch (Exception e) {
					LOGGER.info("灰度分流失败,"+"运单号："+paymentSettlementDto.getWaybillNo());
					throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
				}
				if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
					LOGGER.info("调用结算接口开始"+"运单号："+dto.getWaybillNo());//记录日志
					paymentSettlementService.confirmToBillReceivable(paymentSettlementDto, currentInfo);
					LOGGER.info("调用结算接口结束");//记录日志
					// 更新ActualFreight表中的结清状态为已结清
					updateActualFreightEntity(dto);
				}
				if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
					FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
					fossDto.setDto(paymentSettlementDto);
					fossDto.setCurrentInfo(currentInfo);
					CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
					if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
						if(StringUtils.isNotBlank(resultDto1.getMessage())){
							LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
							throw new CubcSettleException(resultDto1.getMessage());
						}
					}
					if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
						// 更新ActualFreight表中的结清状态为已结清
						updateActualFreightEntity(dto);
					}
				}
				//------CUBC灰度结清服务结束--------add by 353654
                
				// 付款方式为其他方式时,调用结算接口-实收货款
				} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(dto.getPaymentType())
						|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE.equals(dto.getPaymentType())
					|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(dto.getPaymentType())
					|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(dto.getPaymentType())) {
				//记录日志
				LOGGER.info("根据不同方式结清货款,付款方式：其他;"+"运单号："+dto.getWaybillNo());
				// 付款方式是‘银行卡’时，款项确认编号必须输入数字。
				if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(dto.getPaymentType())
						&& (StringUtil.isBlank(dto.getClaimNo())
						|| !dto.getClaimNo().matches("[0-9]+"))) {
					throw new RepaymentException("付款方式为银行卡时，款项确认编号必须输入数字。");
				}
				
				/**
				 * 银行卡检验
				 * @author yangqiang 309603
				 * @date   2016-2-23
				 */
				PosCardEntity    posCardEntity    = null;	//POS实体
				BigDecimal amount = null;					//POS未使用金额
				BigDecimal codAmount = BigDecimal.ZERO;				// 实收代收货款费用
				BigDecimal actualFreight = null;			// 实收到实付运费
				if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(dto.getPaymentType())
						&& (!StringUtil.isBlank(dto.getClaimNo())
						&& dto.getClaimNo().matches("[0-9]+"))
						) {
					//查询T+0报表获取未使用金额
					//准备查询数据
					PosCardManageDto posCardManageDto = new PosCardManageDto();
					posCardManageDto.setTradeSerialNo(dto.getClaimNo());
					posCardManageDto.setOrgCode(currentInfo.getCurrentDeptCode());
					//posCardManageDto.setBelongMoudleCode(SettlementDictionaryConstants.NCI_SETTLE);
					//查询T+0报表数据
					//根据交流水号和部门编码查询POS刷卡数据   灰度  353654---start
		            String vestSystemCode = null;
		            List<PosCardEntity> posCardEntitys = null;
		            try {
		            	List<String> arrayList = new ArrayList<String>();
		            	arrayList.add(dto.getWaybillNo());
		            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
		            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".settleUpRepayment",
		            			SettlementConstants.TYPE_FOSS);
		            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
		            	List<VestBatchResult> list = response.getVestBatchResult();
		            	vestSystemCode = list.get(0).getVestSystemCode();	
					} catch (Exception e) {
						LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
						throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
					}
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
						posCardEntitys = pdaPosManageService.queryPosCardData(posCardManageDto).getPosCardEntitys();
						//是否存在
						if (posCardEntitys==null||posCardEntitys.isEmpty()) {
							throw new RepaymentException("输入流水号不存在或者输入流水号有误或者流水所属部门不正确。");
						}else{
							posCardEntity = posCardEntitys.get(0);
						}
						//判断所属模块，结清货款，
						if(SettlementDictionaryConstants.NCI_DEPOSIT.equals(posCardEntity.getBelongModule())){
							throw new RepaymentException("该交易流水号仅能做预收或做小票！");
						}
						//获取未使用金额
						amount =posCardEntity.getUnUsedAmount();
						
						//比较
						// 实收代收货款费用
						codAmount = dto.getCodAmount();
						// 实收到实付运费
						actualFreight = dto.getActualFreight();
						
						if (amount.compareTo(codAmount.add(actualFreight))<0) {//可用金额小于实收代收货款+实收到付运费
							
							throw new RepaymentException("可用余额不足。");
						}	
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
						//TODO  待定
					}
					//根据交流水号和部门编码查询POS刷卡数据   灰度  353654---end
				}
				
				// 款项认领编号
				paymentSettlementDto.setPaymentNo(dto.getClaimNo());
				// 运单号
				paymentSettlementDto.setWaybillNo(dto.getWaybillNo());
				// 付款类型
				paymentSettlementDto.setPaymentType(dto.getPaymentType());
				// 部门cide
				paymentSettlementDto.setDestOrgCode(currentInfo.getCurrentDeptCode());
				// 部门名称
				paymentSettlementDto.setDestOrgName(currentInfo.getCurrentDeptName());
				// 客户code
				paymentSettlementDto.setCustomerCode(dto.getConsigneeCode());
				// 客户名称
				paymentSettlementDto.setCustomerName(dto.getConsigneeName());
				// 时间
				paymentSettlementDto.setBusinessDate(new Date());
				// 付款编号
				paymentSettlementDto.setSourceBillNo(dateStr.toString());
				// 实收代收货款费用
				paymentSettlementDto.setCodFee(dto.getCodAmount());
				// 实收到付运费
				paymentSettlementDto.setToPayFee(dto.getActualFreight());
				
				// 当实付运费和代收货款同时为0时 更改为已结清
				if (BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
						&& BigDecimalOperationUtil.compare(receiveablePayAmout, BigDecimal.ZERO)) {
					// 更新ActualFreight表中的结清状态为已结清
					updateActualFreightEntity(dto);
				} else {
					//------CUBC灰度结清服务开始--------add by 353654
					String message = null;
					String vestSystemCode = null;
					try {
						ArrayList<String> arrayList = new ArrayList<String>();
						arrayList.add(paymentSettlementDto.getWaybillNo());
						CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
								SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".validaRepayment",
								SettlementConstants.TYPE_FOSS);
						CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
						List<VestBatchResult> list = response.getVestBatchResult();
						vestSystemCode = list.get(0).getVestSystemCode();	
					} catch (Exception e) {
						LOGGER.info("灰度分流失败,"+"运单号："+paymentSettlementDto.getWaybillNo());
						throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
					}
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){	
						LOGGER.info("调用结算接口结清货款开始"+"运单号："+dto.getWaybillNo());//记录日志
						message = paymentSettlementService.confirmToPayment(paymentSettlementDto, currentInfo);
						LOGGER.info("调用结算接口结清货款结束");//记录日志		
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
						FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
						fossDto.setDto(paymentSettlementDto);
						fossDto.setCurrentInfo(currentInfo);
						CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
							if(StringUtils.isNotBlank(resultDto1.getMessage())){
								LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
								throw new CubcSettleException(resultDto1.getMessage());
							}
						}
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
			    			message = SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE;
			    		}
					}
					//------CUBC灰度结清服务开始--------add by 353654
				if (SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE.equals(message)) {
						// 更新ActualFreight表中的结清状态为已结清
						updateActualFreightEntity(dto);
					}
				}
				
				//更新T+0刷卡金额
				/**
				 *更新T+0数据 调用更新数据
				 * @author yangqiang 309603
				 * @date   2016-2-23
				 */
				if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(dto.getPaymentType())
						&& (!StringUtil.isBlank(dto.getClaimNo())
						&& dto.getClaimNo().matches("[0-9]+"))
						){
					//更新POS刷卡信息
					//准备数据
					BigDecimal payAmount = codAmount.add(actualFreight);										//使用总金额
					posCardEntity.setModifyUser(currentInfo.getEmpName());										//更新人名称
					posCardEntity.setModifyUserCode(currentInfo.getEmpCode());									//更新人编码
					posCardEntity.setUsedAmount(posCardEntity.getUsedAmount().add(payAmount));					//已使用金额
					posCardEntity.setUnUsedAmount(amount.subtract(payAmount));   								//未使用金额
					//根据交流水号去更新POS刷卡已使用流水号金额，未使用金额   灰度  353654---start
                    String vestSystemCode1 = null;
                    try {
                    	List<String> arrayList = new ArrayList<String>();
                    	arrayList.add(dto.getWaybillNo());
                    	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                    			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".expPayment",
                    			SettlementConstants.TYPE_FOSS);
                    	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                    	List<VestBatchResult> list = response.getVestBatchResult();
                    	vestSystemCode1 = list.get(0).getVestSystemCode();	
        			} catch (Exception e) {
        				LOGGER.info("灰度分流失败,"+"运单号："+ dto.getWaybillNo());
        				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
        			}
        			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode1)){
        				pdaPosManageService.updatePosCardMessageAmount(posCardEntity);
        				//插入新的POS刷卡明细
    					//准备数据
    					PosCardDetailEntity posCardDetailEntity = new PosCardDetailEntity();
    					posCardDetailEntity.setTradeSerialNo(posCardEntity.getTradeSerialNo()); 					//交易流水号
    					posCardDetailEntity.setInvoiceType("W2");													//运单
    					posCardDetailEntity.setInvoiceNo(dto.getWaybillNo());							//运单号
    					posCardDetailEntity.setAmount(queryToPayAmount(dto.getWaybillNo()));									//发生金额
    					BigDecimal totalAmount = repaymentDao.getTotalAmount(dto.getWaybillNo());
    					posCardDetailEntity.setAmount(totalAmount);									//发生金额
    					posCardDetailEntity.setOccupateAmount(payAmount);											//已占用金额
    					BigDecimal unVerifyAmount = (receiveableAmount.add(receiveablePayAmout)).subtract(payAmount);
    					posCardDetailEntity.setUnVerifyAmount(unVerifyAmount);										//未核销金额
    					posCardDetailEntity.setCreateUser(currentInfo.getEmpName());								//创建人名称
    					posCardDetailEntity.setCreateUserCode(currentInfo.getEmpCode());							//创建人编码
    					pdaPosManageService.insertOrUpdateDetail(posCardDetailEntity);//调用接口插入数据
        			}
        			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode1)){
        				//TODO  待定
        			}
        			//根据交流水号去更新POS刷卡已使用流水号金额，未使用金额   灰度  353654---end
				}
			}
			//付款编号
			dto.setRepaymentNo(dateStr.toString());
			//付款时间
			dto.setPaymentTime(new Date());
			//操作人
			dto.setOperator(currentInfo.getEmpName());
			//操作人编码
			dto.setOperatorCode(currentInfo.getEmpCode());
			//操作部门
			dto.setOperateOrgName(currentInfo.getCurrentDeptName());
			//操作部门编码
			dto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
			//当实付运费和代收货款同时为0时 更改为无需生成
			if (BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
					&& BigDecimalOperationUtil.compare(receiveablePayAmout, BigDecimal.ZERO)) {
				// 无需结清 将付款信息置0
				dto.setActualFreight(BigDecimal.ZERO);
				// 无需结清 将付款信息置0
				dto.setCodAmount(BigDecimal.ZERO);
				// 财务单据无需生成
				dto.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
			} else {
				// 财务单据已生成
			 	dto.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
			}
			// 币种
			dto.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			// job id
			dto.setJobId(UUIDUtils.getUUID());
			// 生成付款信息
			vtsValidateAndSettlementDao.addPaymentInfo(dto);
//			// 生成到达联信息
//			ArriveSheetEntity entity = new ArriveSheetEntity();
//			// 运单号
//			entity.setWaybillNo(dto.getWaybillNo());
//			// 收货人
//			entity.setDeliverymanName(dto.getDeliverymanName());
//			// 证件类型
//			entity.setIdentifyType(dto.getIdentifyType());
//			// 证件号
//			entity.setIdentifyCode(dto.getIdentifyCode());
			// 调用到达联接口
//			arriveSheetManngerService.checkGenerateArriveSheet(entity);
//			// 更新到达联,通过运单号
//			arriveSheetManngerService.updateArriveSheetEntityByWaybillNo(dto.getWaybillNo(), dto.getDeliverymanName(), dto.getIdentifyType(), dto.getIdentifyCode());
		}
	}
	
	/** 
	 * 更新实际承运表的结清状态
	 * @author fangwenjun 237982
	 * @date 2015年9月15日 下午4:31:07 
	 */
	private void updateActualFreightEntity (SettlementPayToVtsDto dto) {
		// 更新ActualFreight表中的结清状态为已结清
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		// 运单号
		actualFreightEntity.setWaybillNo(dto.getWaybillNo());
		// 结清状态-已结清
		actualFreightEntity.setSettleStatus(FossConstants.YES);
		// 结款日期
		actualFreightEntity.setSettleTime(new Date());
		// 收货人
		actualFreightEntity.setDeliverymanName(dto.getDeliverymanName());
		// 证件类型
		actualFreightEntity.setIdentifyType(dto.getIdentifyType());
		// 证件号
		actualFreightEntity.setIdentifyCode(dto.getIdentifyCode());
		if (StringUtil.isNotBlank(dto.getCodIdentifyCode())) {
			// 证件类型(代收人)
			actualFreightEntity.setCodIdentifyType(dto.getCodIdentifyType());
			// 证件号码（代收）
			actualFreightEntity.setCodIdentifyCode(dto.getCodIdentifyCode());
		} else {
			// 证件类型(代收人)
			actualFreightEntity.setCodIdentifyType("");
			// 证件号码（代收）
			actualFreightEntity.setCodIdentifyCode("");
		}
		// 更新actualFreight表
		actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
	}

	/**
	 * 结清前验证
	 * @param waybillManagerService
	 */
	private void verification(SettlementPayToVtsDto dto,CurrentInfo currentInfo, WaybillEntity waybillEntity) {
	//记录日志
	LOGGER.info("结清前财务单据校验开始..."+"运单号："+dto.getWaybillNo());
	// 校验未受理的更改单
	if (waybillRfcService.isExsitsWayBillRfc(dto.getWaybillNo())) {
		// 抛出业务异常
		throw new RepaymentException("存在未受理的更改单，请受理后再次操作！");
	}
				
	// 判断是否 已结清
	if (isPayment(dto.getWaybillNo()))  {
		// 抛出业务异常
		throw new RepaymentException("该单已结清货款！");
	}
	
	// 校验更改单或者更改单申请未处理
	if (waybillEntity != null && WaybillConstants.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())) {					
		List<String> waybillRfcList=new ArrayList<String>();
		waybillRfcList.add(dto.getWaybillNo());
		List<String> notWaybillRfc=waybillRfcService.queryUnActiveRfcWaybillNo(waybillRfcList);	//调用接口				
		if(CollectionUtils.isNotEmpty(notWaybillRfc) && notWaybillRfc.size()>0){
			// 抛出业务异常
			throw new RepaymentException("有更改单申请未处理，请受理后再次操作！");
		}
	}
				
	// 判断如果当前运单是整车
	if (waybillEntity != null && FossConstants.YES.equals(waybillEntity.getIsWholeVehicle())) {
		boolean arrival = FossConstants.YES.equals(waybillEntity.getIsPassOwnDepartment());
		if (!arrival) {
			//如果当前部门==最终配载部门
			if (currentInfo.getCurrentDeptCode().equals(waybillEntity.getLastLoadOrgCode())) {
				arrival=true;
			}
		}
		//update by foss 231434 2015-6-2 
		//对整车(三级)产品，若开单时间在2013-08-01之前(不包含2013-08-01)，不做校验
//		Date date = DateUtils.convert(dateStr, format);
//		if (date.before(waybillEntity.getBillTime())) {
//			// 校验运单是否配载是否到达
//			String message = arrivalService.validateWaybillNo(dto.getWaybillNo(), arrival);
//			if(message != null){
//				throw new RepaymentException(message);
//			}
//		}
	} else {
		// 判断当前部门是否是最终到达部门
		if (!currentInfo.getCurrentDeptCode().equals(
				waybillEntity.getLastLoadOrgCode())) {
			throw new RepaymentException("当前部门与最终到达部门不一致，不能结清货款！");
		}
	}
				
	// 判断是否网上支付
	if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(waybillEntity.getPaidMethod())) {
		//ISSUE-4379调用结算接口判断 如果网上支付未完成时 给出相应提示
		List<String> waybillNos = new ArrayList<String>();
		waybillNos.add(dto.getWaybillNo());
		// 开单网上支付但是尚未支付的单据
		//开单网上支付但是尚未支付的单据，灰度改造   353654 ---------------------------start 
		String vestSystemCode = null;
		List<String> notPayWaybillNo = null;
        try {
        	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(waybillNos,
        			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".verification",
        			SettlementConstants.TYPE_FOSS);
        	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
        	List<VestBatchResult> list = response.getVestBatchResult();
        	vestSystemCode = list.get(0).getVestSystemCode();		
		} catch (Exception e) {
			LOGGER.info("灰度分流失败,"+"错误方法位置："+SERVICE_CODE+".verification");
			throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
		}
		if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
			notPayWaybillNo = takingService.unpaidOnlinePay(waybillNos);
			if (CollectionUtils.isNotEmpty(notPayWaybillNo)) {
				throw new RepaymentException("该运单为网上支付运单，网上支付未完成，无法进行结清货款操作");
			}
		}
		if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
			//调用CUBC查询物流交易单  应收信息校验  353654
				FossSearchTradeRequestDO requestDto1 = new FossSearchTradeRequestDO();
				requestDto1.setWaybillNos(waybillNos);
				FossSearchTradeResponseDO responseDto1 = null;
				try {
					responseDto1 = (FossSearchTradeResponseDO)HttpClientUtils.postMethod(requestDto1, new FossSearchTradeResponseDO(), queryTradeListUrl);
				} catch (Exception e) {
					LOGGER.error("调用CUBC接口出现异常,异常信息为："+e);
					throw new SettlementException("服务器正忙,CUBC查询物流交易单异常,请稍候重试");
				}
				if(responseDto1 != null){
					if(StringUtils.isNotBlank(responseDto1.getMsg()) && StringUtils.equals("N", responseDto1.getActive())){
						LOGGER.error("调用CUBC查询物流交易单接口失败，异常信息：" + responseDto1.getMsg());
						throw new SettlementException(responseDto1.getMsg());
					}
					Map<String, List<TradeDO>> dataMap = responseDto1.getDataMap();
					List<TradeDO> tradeslist = dataMap.get(dto.getWaybillNo());
					if(!CollectionUtils.isEmpty(tradeslist)){
						for (TradeDO tradeDO : tradeslist) {
							if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE.
				    				   equals(tradeDO.getOrderSubType())&&
				    				   SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.
				    				   equals(tradeDO.getOrderSubType())
				    				   &&BigDecimal.ZERO.compareTo(tradeDO.getUnverifyAmount())<0){
				    			   //添加数据
				    			   notPayWaybillNo.add(dto.getWaybillNo());
				    		   }
						}
						if (CollectionUtils.isNotEmpty(notPayWaybillNo)) {
							throw new RepaymentException("该运单为网上支付运单，网上支付未完成，无法进行结清货款操作");
						}
					}
				}
		}
		//开单网上支付但是尚未支付的单据，灰度改造   353654 ---------------------------end
	}
				
	//记录日志
	LOGGER.info("结清前财务单据校验结束..."+"运单号："+dto.getWaybillNo());			
	}

	/**
	 * 是否结清货款.
	 *
	 * @param waybillNo 
	 * 		the waybill no
	 * @return true, 
	 * 		if is payment
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-27
	 * 		 下午7:22:40
	 */
	@Override
	public boolean isPayment(String waybillNo){
		// 如果运单号不为空
		if (StringUtils.isNotEmpty(waybillNo)){
			// 查看ActualFreight表中是否已经结清
			ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
			if (actualFreightEntity != null && FossConstants.YES.equals(actualFreightEntity.getSettleStatus())){
				//返回正常
				return true;
			}
			//返回错误
			return false;
		}
		//返回错误
		return false;
	}
	
	/**
	 * 返回财务应收信息.
	 *
	 * @param waybillNo the waybill no
	 * @return the financial dto
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-11 下午3:22:00
	 */
	@Override
	public FinancialDto queryFinanceSign(String waybillNo) {
		FinancialDto financialDto = new FinancialDto();
		BillReceivableConditionDto billReceiveable = new BillReceivableConditionDto(waybillNo);
		// 根据运单号查询客户的应收单到付金额和应收代收货款金额 --结算接口
		List<BillReceivableEntity> billReceivableEntities = billReceivableService
				.queryReceivableAmountByCondition(billReceiveable);
		if(CollectionUtils.isEmpty(billReceivableEntities)){
			return financialDto;
		}
		for (BillReceivableEntity billReceivableEntity : billReceivableEntities) {
			// 到达应收单
			if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(billReceivableEntity.getBillType())) {
				// 应收到付款
				financialDto.setReceiveablePayAmoout(billReceivableEntity.getUnverifyAmount());
				// 已收到付款
				financialDto.setReceivedPayAmount(billReceivableEntity
						.getVerifyAmount());
			} // 代收货款应收单
			else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(billReceivableEntity.getBillType())) {
				// 应收代收款
				financialDto.setReceiveableAmount(billReceivableEntity.getUnverifyAmount());
				// 已收代收款
				financialDto.setReceivedAmount(billReceivableEntity.getVerifyAmount());
			}else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE.equals(billReceivableEntity.getBillType())){
				//合伙人到付运费应收
				// 应收到付款
				financialDto.setReceiveablePayAmoout(billReceivableEntity.getUnverifyAmount());
				// 已收到付款
				financialDto.setReceivedPayAmount(billReceivableEntity.getVerifyAmount());
			}
		}
		return financialDto;
	}
	
	/**
	 * 根据运单号查询运单到付金额
	 * @param waybillNo
	 * @return
	 */
	private BigDecimal queryToPayAmount(String waybillNo){
		Map<String,String> map = new HashMap<String, String>();
		map.put("waybillNo", waybillNo);
		map.put("active", "Y");
		return this.vtsValidateAndSettlementDao.queryToPayAmount(map);
		
	}
	

	/**
	 * 354830 孙小雪
	 * 根据运单号查询实际承运表结清状态
	 * @param waybillNo
	 * @return
	 */
	@Override
	public String querySettlementStatus(String waybillNo) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillNo", waybillNo);
		return this.vtsValidateAndSettlementDao.querySettlementStatus(map);
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService){
		this.waybillRfcService = waybillRfcService;
	}
	
	public void setActualFreightService(IActualFreightService actualFreightService){
		this.actualFreightService = actualFreightService;
	}
	
	/*public void setArrivalService(IArrivalService arrivalService) {
		this.arrivalService = arrivalService;
	}*/
	
	public void setTakingService(ITakingService takingService) {
		this.takingService = takingService;
	}
	
	public void setBillReceivableService(IBillReceivableService billReceivableService){
		this.billReceivableService = billReceivableService;
	}
	
//	public void setArriveSheetManngerService(IArriveSheetManngerService arriveSheetManngerService){
//		this.arriveSheetManngerService = arriveSheetManngerService;
//	}

	public void setPaymentSettlementService(IPaymentSettlementService paymentSettlementService){
		this.paymentSettlementService = paymentSettlementService;
	}
	
	public void setVtsValidateAndSettlementDao(
			IVtsValidateAndSettlementDao vtsValidateAndSettlementDao) {
		this.vtsValidateAndSettlementDao = vtsValidateAndSettlementDao;
	}
	
	public void setRepaymentDao(IRepaymentDao repaymentDao){
		this.repaymentDao = repaymentDao;
	}

	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}

	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}

	public void setCubcSettlementService(
			ICubcSettlementService cubcSettlementService) {
		this.cubcSettlementService = cubcSettlementService;
	}
	
}
