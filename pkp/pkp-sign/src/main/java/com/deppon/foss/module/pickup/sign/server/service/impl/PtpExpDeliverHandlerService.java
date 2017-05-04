package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.IExpDeliverHandlerService;
import com.deppon.foss.module.pickup.sign.api.server.service.IPtpExpDeliverHandlerService;
import com.deppon.foss.module.pickup.sign.api.server.service.IPtpSignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto;
import com.deppon.foss.module.pickup.sign.api.shared.vo.DeliverbillDetailVo;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossToCubcRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICubcSettlementService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IPaymentSettlementService;
import com.deppon.foss.util.define.FossConstants;

/**
 * 合伙人快递派送处理service
 * 
 * @author gpz
 * @date 2016年1月29日
 */
public class PtpExpDeliverHandlerService implements IPtpExpDeliverHandlerService {

	/**
	 * 记录日志
	 */
	private static final Logger Logger = LoggerFactory.getLogger(PtpExpressSignService.class);

	/**
	 * 运单签收Service
	 */
	@Autowired
	private IWaybillSignResultService waybillSignResultService;

	/**
	 * 派送处理Service
	 */
	@Autowired
	private IExpDeliverHandlerService expDeliverHandlerService;
	
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
	 * 合伙人零担签收service
	 */
	@Autowired
	private IPtpSignService ptpSignService;
	
	/**
	 * CUBC结清中转接口
	 */
	private ICubcSettlementService cubcSettlementService;

	/** 
	 * 合伙人快递派送处理 无PDA签收
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IPtpExpDeliverHandlerService#addNoPdaSignForPtpExp(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity, com.deppon.foss.module.pickup.sign.api.shared.vo.DeliverbillDetailVo)
	 */
	@Override
	@Transactional
	public String addNoPdaSignForPtpExpDeliver(ArriveSheetEntity arriveSheet,
			DeliverbillDetailVo deliverbillDetailVo) {
		// 获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//
		String resultMsg = null;

		//校验同一单号运单表的“到付金额“=合伙人代收货款应收+到付运费应收+到达应收
		ptpSignService.checkToPayEqualReceivable(arriveSheet.getWaybillNo());
		
		// 调用合伙人冲销接口方法
		confirmPTPPaymentAndWriteOff(arriveSheet.getWaybillNo(),deliverbillDetailVo.getFinancialDto(),currentInfo);
		
/*		//更新派送流水状态-使其生效
		PartnerUpdateTakeEffectTimeRequest request = new PartnerUpdateTakeEffectTimeRequest();
		request.setSourceBillNo(arriveSheet.getWaybillNo());//运单号
		request.setTakeEffectTime(new Date());//生效日期
		request.setFloweffectivestatus(FossConstants.YES);  //这里使用是否作废的标识
		request.setOperatorCode(currentInfo.getEmpCode());//操作人编码
		request.setOperatorName(currentInfo.getEmpName());//操作人姓名
		boolean isSuccess = ptpSignService.validBillSaleFlowByAsynESB(request);
		if(!isSuccess) {
			throw new SignException("合伙人快递派送签收更改PTP流水状态发送ESB失败!");
		}*/
		

		Map<String, Object> queryCZMparams = new HashMap<String, Object>();
		queryCZMparams.put("waybillNo", arriveSheet.getWaybillNo());
		queryCZMparams.put("active", FossConstants.YES);
		// 查询子母件信息
		TwoInOneWaybillDto twoInOneWaybillDto = waybillSignResultService
				.queryWaybillRelateByWaybillOrOrderNo(queryCZMparams);
		// 获取签收明细结合 268377
		List<SignDetailEntity> signDetailEntitys = deliverbillDetailVo.getSignDetailEntitys();
		// 判断是否是子母件 268377
		if (null != twoInOneWaybillDto
				&& (FossConstants.YES).equals(twoInOneWaybillDto.getIsTwoInOne())) {
			// 子母件派送签收处理
			resultMsg = expDeliverHandlerService.addNoPdaCZMSign(arriveSheet,
					deliverbillDetailVo.getFinancialDto(), currentInfo,
					signDetailEntitys, twoInOneWaybillDto);
		} else {
			// 非子母件派送签收处理
			resultMsg = expDeliverHandlerService.addNoPdaSign(arriveSheet,
					deliverbillDetailVo.getFinancialDto(), currentInfo,
					signDetailEntitys);
		}
		Logger.info("提交录入无PDA签收信息返回结果:" + resultMsg);
		return resultMsg;
	}

	/**
	 * 合伙人冲销接口调用方法
	 * @author gpz
	 * @date 2016年1月29日
	 * @param currentInfo
	 *            当前登录信息
	 */
	private void confirmPTPPaymentAndWriteOff(String waybillNo,FinancialDto financialDto,CurrentInfo currentInfo) {
		// 根据 付款对象 查询有效付款信息 如果查询出结果 说明此单号没有反结清
		RepaymentEntity conPayEntity = new RepaymentEntity();// 付款entity
		conPayEntity.setWaybillNo(waybillNo);// 运单号
		// 查询有效的付款信息
		RepaymentEntity payEntity = repaymentService.queryOneRepaymentInfo(conPayEntity);

		// ptp合伙人冲销
		PaymentSettlementDto paymentDto = new PaymentSettlementDto();
		paymentDto.setWaybillNo(waybillNo);// 运单号
		if (null != payEntity) {
			// 来源单号-存放（到达实收单号）
			paymentDto.setSourceBillNo(payEntity.getRepaymentNo());
		}
		// 付款方式
		paymentDto.setPaymentType(financialDto.getPaymentType());
		// 到达部门
		paymentDto.setDestOrgCode(currentInfo.getDept().getCode());
		// 实收到付运费
		paymentDto.setToPayFee(financialDto.getPocketShipping());
		Logger.info("调用合伙人冲销接口开始--------");
		Logger.info("运单信息和到达部门信息Dto:" + ReflectionToStringBuilder.toString(paymentDto));
		// 调用合伙人冲销接口
		paymentSettlementService.confirmPTPPaymentAndWriteOff(paymentDto,currentInfo);
		Logger.info("调用合伙人冲销接口结束--------");
		
		/*//--------CUBC结清服务开始--------add by 378375
		Logger.info("调用CUBC结清接口开始！运单号："+paymentDto.getWaybillNo());  // 记录日志
		try {
			FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
			fossDto.setDto(paymentDto);
			fossDto.setCurrentInfo(currentInfo);
			cubcSettlementService.settleReqToCUBU(fossDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		Logger.info("调用CUBC结清接口结束");  // 记录日志
		//--------CUBC结清服务结束--------add by 378375
*/	}

	@Override
	@Transactional
	public void addPdaSignForPtpExpDeliver(ArriveSheetEntity arriveSheet,
			DeliverbillDetailVo deliverbillDetailVo) {
		// 获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		
		// 调用合伙人冲销接口方法
		confirmPTPPaymentAndWriteOff(arriveSheet.getWaybillNo(),deliverbillDetailVo.getFinancialDto(),currentInfo);
		
		//查询子母件参数 268377
		Map<String, Object> queryCZMparams = new HashMap<String, Object>();
		queryCZMparams.put("waybillNo", arriveSheet.getWaybillNo());
		queryCZMparams.put("active", FossConstants.YES);
		//根据运单号，是否有效 拿到子母件信息
		TwoInOneWaybillDto twoInOneWaybillDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(queryCZMparams);
		//判断是否子母件 
		if(null != twoInOneWaybillDto && (FossConstants.YES).equals(twoInOneWaybillDto.getIsTwoInOne())){
			expDeliverHandlerService.addCZMPdaSignInfo(arriveSheet,twoInOneWaybillDto);
		}else{
			expDeliverHandlerService.addPdaSignInfo(arriveSheet);
		}
	}
    
	public void setCubcSettlementService(
			ICubcSettlementService cubcSettlementService) {
		this.cubcSettlementService = cubcSettlementService;
	}
	

}
