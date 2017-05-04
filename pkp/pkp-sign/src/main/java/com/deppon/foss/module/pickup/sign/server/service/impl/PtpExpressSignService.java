package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.IExpSignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IPtpExpressSignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IPtpSignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.RepaymentConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.vo.PtpSignVo;
import com.deppon.foss.module.pickup.sign.api.shared.vo.SignVo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCResponseDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossToCubcRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICubcSettlementService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IPaymentSettlementService;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CubcSettleException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 合伙人快递签收service
 * 
 * @author gpz
 * @date 2016-1-22
 */
public class PtpExpressSignService implements IPtpExpressSignService {
	private IActualFreightService actualFreightService;
	public void setActualFreightService(IActualFreightService actualFreightService){
		this.actualFreightService = actualFreightService;
	}
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.sign.server.service.impl.PtpExpressSignService";

	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PtpExpressSignService.class);

	/**
	 * 快递签收出库service
	 */
	@Autowired
	private IExpSignService expSignService;
	
	/**
	 * 合伙人零担签收service
	 */
	@Autowired
	private IPtpSignService ptpSignService;
	
	/**
	 * 付款记录service
	 */
	@Autowired
	private IRepaymentService repaymentService;
	
	/**
	 * 签收结算货款服务
	 */
	@Autowired
	private IPaymentSettlementService paymentSettlementService;
	
	/**
	 * CUBC结清中转接口
	 */
	private ICubcSettlementService cubcSettlementService;
	
	/**
	 * 运单签收结果信息
	 */
	private IWaybillSignResultService waybillSignResultService;
	/**
	 * @param waybillSignResultService the waybillSignResultService to set
	 */
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	/**
	 * 合伙人快递自提签收----冲销并签收
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IPtpExpressSignService#writeOffAndSign(com.deppon.foss.module.pickup.sign.api.shared.vo.PtpSignVo, java.util.List, com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity, com.deppon.foss.module.pickup.sign.api.shared.vo.SignVo, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	@Transactional
	public String writeOffAndSign(PtpSignVo ptpSignVo,List<SignDetailEntity> signDetailList,
			ArriveSheetEntity arriveSheetEntity, SignVo signVo,CurrentInfo currentInfo) {
		//1.校验同一单号运单表的“到付金额“=合伙人代收货款应收+到付运费应收+到达应收
		ptpSignService.checkToPayEqualReceivable(ptpSignVo.getWaybillNo());
		
		//二级网点历史单据签收校验
		ptpSignService.checkTwoLevelNetworkBill(ptpSignVo.getWaybillNo());
		
		// 根据 付款对象 查询有效付款信息 如果查询出结果 说明此单号没有反结清
		RepaymentEntity conPayEntity = new RepaymentEntity();//付款entity
		conPayEntity.setWaybillNo(ptpSignVo.getWaybillNo());//运单号
		//查询有效的付款信息
		RepaymentEntity payEntity = repaymentService.queryOneRepaymentInfo(conPayEntity);

		//2. ptp合伙人冲销
		PaymentSettlementDto paymentDto = new PaymentSettlementDto();
		paymentDto.setWaybillNo(ptpSignVo.getWaybillNo());//运单号
		if (null != payEntity) {
			//来源单号-存放（到达实收单号）
			paymentDto.setSourceBillNo(payEntity.getRepaymentNo());
		}
		// 付款方式
		paymentDto.setPaymentType(ptpSignVo.getPaymentType());
		// 到达部门
		paymentDto.setDestOrgCode(currentInfo.getDept().getCode());
		// 实收到付运费
		paymentDto.setToPayFee(ptpSignVo.getToPayFee());
		//------CUBC灰度结清服务开始--------add by 378375
				String vestSystemCode = null;
				try {
		          	List<String> arrayList = new ArrayList<String>();
		          	arrayList.add(paymentDto.getWaybillNo());
		          	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
		          			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".writeOffAndSign",
		          			SettlementConstants.TYPE_FOSS);
		          	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
		          	List<VestBatchResult> list1 = response.getVestBatchResult();
		          	vestSystemCode = list1.get(0).getVestSystemCode();	
		  			} catch (Exception e) {
		  				LOGGER.info("灰度分流失败,"+"运单号："+paymentDto.getWaybillNo());
						throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
		  			}
				if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
					paymentSettlementService.confirmPTPPaymentAndWriteOff(paymentDto,currentInfo);
					//3.判断是否已结清，未结清则生成结清记录，同时更新结清状态
					if(!repaymentService.isPayment(ptpSignVo.getWaybillNo())){
						ptpSignService.paymentInfoOperation(ptpSignVo, currentInfo);
					}
				}
				if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
					FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
					//判断是否是合伙人
					fossDto.setIsPtp(FossConstants.YES);
					fossDto.setDto(paymentDto);
					fossDto.setCurrentInfo(currentInfo);
					CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
					if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
						if(StringUtils.isNotBlank(resultDto1.getMessage())){
							LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
							throw new CubcSettleException(resultDto1.getMessage());
						}
					}
					if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
						// 结清实体
						RepaymentEntity payEntity1 = new RepaymentEntity();
						// 运单号
						payEntity1.setWaybillNo(ptpSignVo.getWaybillNo());
						// 结清方式
						payEntity1.setSettleApproach(SettlementDictionaryConstants.SETTLE_APPROACH_PC);
						// 生成付款编号
						StringBuffer dateStr = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
						// 拼接付款编号
						dateStr = dateStr.append(ptpSignVo.getWaybillNo());
						payEntity1.setRepaymentNo(dateStr.toString());
						// 操作人
						payEntity1.setOperator(currentInfo.getEmpName());
						// 操作人编码
						payEntity1.setOperatorCode(currentInfo.getEmpCode());
						// 操作部门
						payEntity1.setOperateOrgName(currentInfo.getCurrentDeptName());
						// 操作部门编码
						payEntity1.setOperateOrgCode(currentInfo.getCurrentDeptCode());
						// 付款方式--合伙人签收后台默认都是现金
						payEntity1.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
						// 客户编码
						payEntity1.setConsigneeCode(ptpSignVo.getConsigneeCode());
						// 客户名称
						payEntity1.setConsigneeName(ptpSignVo.getConsigneeName());
						// 实付运费
						BigDecimal toPayFee = (null == ptpSignVo.getToPayFee() ? BigDecimal.ZERO  : ptpSignVo.getToPayFee());
						payEntity1.setActualFreight(toPayFee);
						// 代收货款
						BigDecimal codAmount = (null == ptpSignVo.getCodAmount() ? BigDecimal.ZERO : ptpSignVo.getCodAmount());
						payEntity1.setCodAmount(codAmount);
						// 财务单据无需生成
						payEntity1.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
						// 结清是否有效
						payEntity1.setActive(FossConstants.YES);
						// 付款时间
						payEntity1.setPaymentTime(new Date());
						// 币种
						payEntity1.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);

						// 更新ActualFreight表中的结清状态为已结清
						ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
						// 运单号
						actualFreightEntity.setWaybillNo(payEntity1.getWaybillNo());
						// 结清状态-已结清
						actualFreightEntity.setSettleStatus(FossConstants.YES);
						// 结款日期
						actualFreightEntity.setSettleTime(new Date());
						// 收货人
						actualFreightEntity.setDeliverymanName(payEntity1.getDeliverymanName());
						// 证件类型
						actualFreightEntity.setIdentifyType(payEntity1.getIdentifyType());
						// 证件号
						actualFreightEntity.setIdentifyCode(payEntity1.getIdentifyCode());
						if (StringUtil.isNotBlank(payEntity1.getCodIdentifyCode())) {
							// 证件类型(代收人)
							actualFreightEntity.setCodIdentifyType(payEntity1.getCodIdentifyType());
							// 证件号码（代收）
							actualFreightEntity.setCodIdentifyCode(payEntity1.getCodIdentifyCode());
						} else {
							// 证件类型(代收人)
							actualFreightEntity.setCodIdentifyType("");
							// 证件号码（代收）
							actualFreightEntity.setCodIdentifyCode("");
						}
						// 更新actualFreight表-更新结清相关信息
						actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
					}
				}
				//------CUBC灰度结清服务结束--------add by 378375
		
		
		
		//是合伙人快递
		arriveSheetEntity.setIsPtpExp(FossConstants.YES);
		//4.提交录入的签收信息
		String resultMsg = expSignService.addSign(signDetailList,
				arriveSheetEntity, signVo.getLineSignDto(), currentInfo,
				signVo.getOrderNo());
		LOGGER.info("提交录入签收信息返回结果:" + resultMsg);
		
		//5.查询签收结果,确认签收成功在推送签收时间
/* 	WaybillSignResultEntity signEntity = new WaybillSignResultEntity();
		signEntity.setActive(FossConstants.YES);//有效
		signEntity.setWaybillNo(ptpSignVo.getWaybillNo());//运单号
		//查询签收结果表
		WaybillSignResultEntity result = waybillSignResultService
				.queryWaybillSignResultByWaybillNo(signEntity);
		// 说明已经签收成功
		if (null != result) {
			// 向ptp推送签收时间
			if (!ptpSignService.validBillSaleFlowByAsynESB(ptpSignVo.getWaybillNo())) {
				throw new SignException("合伙人签收生效应付流水发送ESB失败!");
			}
		} else {
			LOGGER.info("--合伙人签收结果表无签收结果记录,不推送签收时间到PTP");
		}*/	
		if (StringUtil.isNotBlank(resultMsg)) {
			return resultMsg;
		}
		return null;
	}
    
	public void setCubcSettlementService(
			ICubcSettlementService cubcSettlementService) {
		this.cubcSettlementService = cubcSettlementService;
	}
	
	
}
