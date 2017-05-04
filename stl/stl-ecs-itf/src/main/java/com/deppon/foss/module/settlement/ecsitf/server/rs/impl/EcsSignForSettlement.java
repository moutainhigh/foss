package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.IPdaRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.EcsBillReceivableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.EcsBillReceivableResponseDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.EcsLineSignRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.EcsPaymentSettlementRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.EcsResponseDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.api.shared.util.ECSCurrentInfoUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodAuditService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILineSignService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IPaymentSettlementService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CodAuditDto;
import com.deppon.foss.module.settlement.ecsitf.server.rs.IEcsSignForSettlement;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * 快递签收业务调用相关结算接口
 * @author 243921-zhangtingting
 * @date 2016-04-14 下午01:45:51
 */
public class EcsSignForSettlement implements IEcsSignForSettlement {
	
	//日志
	private final Logger logger = LogManager.getLogger(EcsSignForSettlement.class);
	
	//结算 应收单服务接口
	private IBillReceivableService billReceivableService;
	
	//签收结算货款服务
	private IPaymentSettlementService paymentSettlementService;
	
	//结算 签收接口
	private ILineSignService lineSignService;
	
	//代收货款审核
	private ICodAuditService  codAuditService;

	@Context
	HttpServletResponse res;

	//快递签收业务调用相关结算接口
	private IEcsSignForSettlement ecsSignForSettlement;

    private IPdaRepaymentService pdaRepaymentService;
    //private ILogEcsFossService logEcsFossService;


	/**
	 * 查询财务单据信息，获取应收与已收
	 * @param jsonStr
	 */
	@Override
	public @ResponseBody String queryReceivableAmount(String jsonStr) {
		
		res.setHeader("ESB-ResultCode", "1");
		
		//获取的财务信息
		EcsBillReceivableResponseDto resDto = new EcsBillReceivableResponseDto();
		//返回的集合，推送给快递
		List<EcsBillReceivableDto> list = new ArrayList<EcsBillReceivableDto>();
		//生成返回实体
		String response = "";
		
		if(StringUtils.isBlank(jsonStr)){
			// 记录日志
			logger.info("查询财务单据信息失败：请求参数为空！");
			
			resDto.setResult(SettlementConstants.RETURN_FAILURE);
			resDto.setMessage("查询财务单据信息失败：请求参数为空！");
			resDto.setList(list);
			response = JSONObject.toJSONString(resDto);
			
			return response;
		}
		try{

			JSONObject obj = JSONObject.parseObject(jsonStr);
			String waybillNo = obj.getString("waybillNo");

			BillReceivableConditionDto billReceiveable = new BillReceivableConditionDto(waybillNo);
			// 根据运单号查询客户的应收单到付金额和应收代收货款金额 --结算接口
			List<BillReceivableEntity> billReceivableEntities = billReceivableService.queryReceivableAmountByCondition(billReceiveable);
			if(CollectionUtils.isNotEmpty(billReceivableEntities)){
				for(BillReceivableEntity entity:billReceivableEntities){
					EcsBillReceivableDto dto = new EcsBillReceivableDto();
					dto.setWaybillNo(entity.getWaybillNo());
					dto.setAmount(entity.getAmount());
					dto.setVerifyAmount(entity.getVerifyAmount());
					dto.setUnverifyAmount(entity.getUnverifyAmount());
					dto.setBillType(entity.getBillType());

					list.add(dto);
				}
			}

			// 记录日志
			logger.info("查询财务单据信息成功！");

			resDto.setResult(SettlementConstants.RETURN_SUCESS);
			resDto.setMessage("查询财务单据信息成功！");
			resDto.setList(list);
			response = JSONObject.toJSONString(resDto);

			return response;
		}catch(BusinessException e){
			return this.getResponse(SettlementConstants.RETURN_FAILURE, "结算查询财务单据信息失败：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()));
		}catch(Exception e){
			return this.getResponse(SettlementConstants.RETURN_FAILURE, "结算查询财务单据信息失败：系统异常，请重新操作！："+e);
		}
	}
	
	/**
	 * 获取响应信息
	 * @author foss-231434-bieyexiong
	 * @date 2016-9-12 18:17
	 */
	private String getResponse(int result,String message){
		//获取的网上未支付完成的运单
		EcsBillReceivableResponseDto resDto = new EcsBillReceivableResponseDto();
		
		resDto.setResult(result);
		resDto.setMessage(message);
		String response = JSONObject.toJSONString(resDto);
		
		logger.info(response);
		return response;
	}
	
	/**
	 * 结清货款，确认付款 实收货款 / 到付运单结转临欠月结
	 * @param jsonStr
	 */
	@Override
	public @ResponseBody String confirmToPayment(String jsonStr) {
		
		res.setHeader("ESB-ResultCode", "1");
		
		//生成返回实体
		String response = "";
		
		if(StringUtils.isBlank(jsonStr)){
			// 记录日志
			logger.info("结清货款，调用结算接口失败：请求参数为空！");
			
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "结清货款，调用结算接口失败：请求参数为空！");

			return response;
		}
		//请求的参数转换为DTO
		EcsPaymentSettlementRequestDto request = JSONObject.parseObject(jsonStr, EcsPaymentSettlementRequestDto.class);
		try {
			logger.info("调用结算接口开始"+"运单号："+request.getWaybillNo());

			ecsSignForSettlement.confirmToPayment(request);
			
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_SUCESS, "结清货款，调用结算接口成功！");

			logger.info("调用结算接口结束。"); 
		} catch (SettlementException se){
			// 记录日志
			logger.info("结清货款，调用结算接口失败：" + se.getErrorCode());
			// 日志处理
			logger.error(se.getErrorCode(), se);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "结清货款，调用结算接口失败："+se.getErrorCode());
		}catch (BusinessException be){
			// 记录日志
			logger.info("结清货款，调用结算接口失败：" + be.getErrorCode());
			// 日志处理
			logger.error(be.getErrorCode(), be);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "结清货款，调用结算接口失败："+be.getErrorCode());
		} catch (Exception e) {
			// 记录日志
			logger.info("结清货款，调用结算接口失败：" + e.getMessage());
			// 日志处理
			logger.error(e.getMessage(), e);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "结清货款，调用结算接口失败："+e.getMessage());
		}
		
		return response;
	}
	
	/**
	 * 签收  确认收入 调用结算出库接口
	 * @param jsonStr
	 */
	@Override
	public @ResponseBody String confirmTaking(String jsonStr) {
		
		res.setHeader("ESB-ResultCode", "1");
		
		//生成返回实体
		String response = "";
				
		if(StringUtils.isBlank(jsonStr)){
			// 记录日志
			logger.info("签收确认收入，调用结算接口失败：请求参数为空！");
					
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "签收确认收入，调用结算接口失败：请求参数为空！");

			return response;
		}
		//请求的参数转换为DTO
		EcsLineSignRequestDto request = JSONObject.parseObject(jsonStr, EcsLineSignRequestDto.class);
		try {
			logger.info("调用结算接口开始"+"运单号："+request.getWaybillNo());

			ecsSignForSettlement.confirmTaking(request);
			
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_SUCESS, "签收确认收入，调用结算接口成功！");

			logger.info("调用结算接口结束。"); 
		} catch (SettlementException se){
			// 记录日志
			logger.info("签收确认收入，调用结算接口失败：" + se.getErrorCode());
			// 日志处理
			logger.error(se.getErrorCode(), se);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "签收确认收入，调用结算接口失败："+se.getErrorCode());
		} catch (Exception e) {
			// 记录日志
			logger.info("签收确认收入，调用结算接口失败：" + e.getMessage());
			// 日志处理
			logger.error(e.getMessage(), e);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "签收确认收入，调用结算接口失败："+e.getMessage());
		}
		
		return response;
	}
	
	/**
	 * 反签收 财务出库 调用结算接口
	 * @param jsonStr
	 */
	@Override
	public @ResponseBody String reverseConfirmTaking(String jsonStr) {

		res.setHeader("ESB-ResultCode", "1");
		
		//生成返回实体
		String response = "";
				
		if(StringUtils.isBlank(jsonStr)){
			// 记录日志
			logger.info("反签收反财务单据，调用结算接口失败：请求参数为空！");
					
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "反签收反财务单据，调用结算接口失败：请求参数为空！");

			return response;
		}
		//请求的参数转换为DTO
		EcsLineSignRequestDto request = JSONObject.parseObject(jsonStr, EcsLineSignRequestDto.class);
		
		//配合代收货款支付审核 校验
		CodAuditDto dto = new CodAuditDto();
		List<String> waybillNoList = new ArrayList<String>();
		waybillNoList.add(request.getWaybillNo());
		dto.setWaybillNos(waybillNoList);
		List<CodAuditEntity> codAuditEntityList = codAuditService.queryCodAuditByCondition(dto);//根据单号查询
		if(codAuditEntityList != null && codAuditEntityList.size() > 0){
			CodAuditEntity codAuditEntity = codAuditEntityList.get(0);//获取第一条
			if(codAuditEntity != null){
				//如果为资金部锁定
				if(StringUtils.equalsIgnoreCase("FL", codAuditEntity.getLockStatus())){
					logger.info("反签收反财务单据，调用结算接口失败：此单据已被资金部锁定，如需操作，请联系资金安全控制组进行解锁！");
					
					response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "反签收反财务单据，调用结算接口失败：此单据已被资金部锁定，如需操作，请联系资金安全控制组进行解锁！");
					
					return response;
				}
				if(StringUtils.equals("RL", codAuditEntity.getLockStatus())){
					logger.info("反签收反财务单据，调用结算接口失败：此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁！");
					
					response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "反签收反财务单据，调用结算接口失败：此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁！");
					
					return response;
				}
			}
		}
		
		try {
			logger.info("调用结算接口开始"+"运单号："+request.getWaybillNo());

			ecsSignForSettlement.reverseConfirmTaking(request);
			
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_SUCESS, "反签收反财务单据，调用结算接口成功！");

			logger.info("调用结算接口结束。"); 
		} catch (SettlementException se){
			// 记录日志
			logger.info("反签收反财务单据，调用结算接口失败：" + se.getErrorCode());
			// 日志处理
			logger.error(se.getErrorCode(), se);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "反签收反财务单据，调用结算接口失败："+se.getErrorCode());
		} catch (Exception e) {
			// 记录日志
			logger.info("反签收反财务单据，调用结算接口失败：" + e.getMessage());
			// 日志处理
			logger.error(e.getMessage(), e);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "反签收反财务单据，调用结算接口失败："+e.getMessage());
		}
		
		return response;
	}
	
	/**
	 * 反结清 反到付运费结转临欠/月结  反确认付款 
	 * @param jsonStr
	 */
	@Override
	public @ResponseBody String reversConfirmToPayment(String jsonStr) {
		
		res.setHeader("ESB-ResultCode", "1");
		
		//生成返回实体
		String response = "";
		
		if(StringUtils.isBlank(jsonStr)){
			// 记录日志
			logger.info("反结清，调用结算接口失败：请求参数为空！");
			
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "反结清，调用结算接口失败：请求参数为空！");

			return response;
		}
		//请求的参数转换为DTO
		EcsPaymentSettlementRequestDto request = JSONObject.parseObject(jsonStr, EcsPaymentSettlementRequestDto.class);
		try {
			logger.info("调用结算接口开始"+"运单号："+request.getWaybillNo()); 
			
			ecsSignForSettlement.reversConfirmToPayment(request);
			
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_SUCESS, "反结清，调用结算接口成功！");

			logger.info("调用结算接口结束。"); 
		} catch (SettlementException se){
			// 记录日志
			logger.info("反结清，调用结算接口失败：" + se.getErrorCode());
			// 日志处理
			logger.error(se.getErrorCode(), se);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "反结清，调用结算接口失败："+se.getErrorCode());
		} catch (BusinessException be) {
			// 记录日志
			logger.info("反结清，调用结算接口失败：" + be.getErrorCode());
			// 日志处理
			logger.error(be.getErrorCode(), be);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "反结清，调用结算接口失败：" + be.getErrorCode());
		}catch (Exception e) {
			// 记录日志
			logger.info("反结清，调用结算接口失败：" + e.getMessage());
			// 日志处理
			logger.error(e.getMessage(), e);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "反结清，调用结算接口失败："+e.getMessage());
		}
		
		return response;
	}

	/**
	 * 调用结算的接口，添加事务控制
	 */

	//结清货款
	@Override
	@Transactional
	public void confirmToPayment(EcsPaymentSettlementRequestDto dto) {
		PaymentSettlementDto paymentSettlementDto = this.getPaymentSettlementDto(dto);
		CurrentInfo currentInfo = ECSCurrentInfoUtil.getCurrentInfo(dto.getEmpCode(), dto.getEmpName(), dto.getCurrentDeptCode(), dto.getCurrentDeptName());
		// 309603
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(paymentSettlementDto.getPaymentType())) {
			String batchNo = paymentSettlementDto.getBatchNo();
			String waybillNo = paymentSettlementDto.getWaybillNo();
			BigDecimal codFee = paymentSettlementDto.getCodFee() == null ? BigDecimal.ZERO : paymentSettlementDto.getCodFee();
			BigDecimal toPayFee = paymentSettlementDto.getToPayFee() == null ? BigDecimal.ZERO : paymentSettlementDto.getToPayFee();
			PosCardEntity posCardEntity = null;
//			for (int i = 0; i < 10; i++) {
//				// 等下异步接口数据传输
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				try {
//					posCardEntity = pdaRepaymentService.queryPosCard(batchNo, currentInfo.getCurrentDeptCode(), codFee, toPayFee);
//					break;
//				} catch (BusinessException e) {
//					if (i == 9) {// 等了20S，没救了。
//						throw e;
//					}
//				}
//			}
			posCardEntity = pdaRepaymentService.queryPosCard(batchNo, currentInfo.getCurrentDeptCode(), codFee, toPayFee);
			if(posCardEntity == null){
				throw new SettlementException("没有刷卡数据，无法结清！");
			}
			pdaRepaymentService.applyPosCardAndDetail(waybillNo, codFee, toPayFee, currentInfo, posCardEntity, "W2");
		}
		// 判断是否合伙人运单
		if (FossConstants.YES.equals(dto.getIsPtp())) {
			// 更新代收货款应收单扣款状态为已扣款 243921
			BillReceivableEntity entity = billReceivableService.selectByWayBillNoAndBillType(dto.getWaybillNo(), SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
			if (null != entity) {

				entity.setWithholdStatus(SettlementDictionaryConstants.STL__WITHHOLD_STATUS__WITHHOLD_SUCCESS);
				billReceivableService.updateBillReceivableWithholdStatus(entity);
			}

			paymentSettlementService.confirmPTPPaymentAndWriteOff(paymentSettlementDto, currentInfo);
		} else {
			// 临欠/月结 或开单到付转 临欠 /月结
			if (FossConstants.YES.equals(dto.getIsMonthStatement())) {
				paymentSettlementService.confirmToBillReceivable(paymentSettlementDto, currentInfo);
			} else {
				paymentSettlementService.confirmToPayment(paymentSettlementDto, currentInfo);
			}
		}
	}

	//签收确认
	@Override
	@Transactional
	public void confirmTaking(EcsLineSignRequestDto dto) {

		lineSignService.confirmTaking(this.getLineSignDto(dto),
				ECSCurrentInfoUtil.getCurrentInfo(dto.getEmpCode(),dto.getEmpName(),
						dto.getCurrentDeptCode(),dto.getCurrentDeptName()));
	}

	//反签收确认
	@Override
	@Transactional
	public void reverseConfirmTaking(EcsLineSignRequestDto dto) {

		lineSignService.reverseConfirmTaking(this.getLineSignDto(dto),
				ECSCurrentInfoUtil.getCurrentInfo(dto.getEmpCode(),dto.getEmpName(),
						dto.getCurrentDeptCode(),dto.getCurrentDeptName()));
	}

	//反结清
	@Override
	@Transactional
	public void reversConfirmToPayment(EcsPaymentSettlementRequestDto dto) {
        PaymentSettlementDto paymentSettlementDto = this.getPaymentSettlementDto(dto);
        CurrentInfo currentInfo = ECSCurrentInfoUtil.getCurrentInfo(dto.getEmpCode(), dto.getEmpName(), dto.getCurrentDeptCode(), dto.getCurrentDeptName());
        // 临欠/月结 或开单到付转 临欠 /月结
        if (FossConstants.YES.equals(dto.getIsMonthStatement())) {
            paymentSettlementService.reversConfirmToBillReceiveable(paymentSettlementDto, currentInfo);
        } else {
            //释放T+0金额
            paymentSettlementService.reversPosCard(paymentSettlementDto, currentInfo);
            paymentSettlementService.reversConfirmPayment(paymentSettlementDto, currentInfo);
        }
    }

	//结清货款 根据请求的参数，转换成对应接口中的dto
	private PaymentSettlementDto getPaymentSettlementDto(EcsPaymentSettlementRequestDto request){
		PaymentSettlementDto dto = new PaymentSettlementDto();
		//运单号
		dto.setWaybillNo(request.getWaybillNo());
		//结清方式    
		dto.setSettleApproach(request.getSettleApproach());
		//付款类型
		dto.setPaymentType(request.getPaymentType());
		//部门code
		dto.setDestOrgCode(request.getCurrentDeptCode());
		//部门名称
		dto.setDestOrgName(request.getCurrentDeptName());
		//客户code
		dto.setCustomerCode(request.getCustomerCode());
		//客户名称
		dto.setCustomerName(request.getCustomerName());
		//时间
		dto.setBusinessDate(request.getBusinessDate());
		//付款编号
		dto.setSourceBillNo(request.getSourceBillNo());
		//款项认领编号
		dto.setPaymentNo(request.getPaymentNo());
		//实收代收货款费用
		dto.setCodFee(request.getCodFee());
		//实收到付运费
		dto.setToPayFee(request.getToPayFee());
		//币种
		dto.setCurrencyCode(request.getCurrencyCode());
		//串号
		dto.setPosSerialNum(request.getPosSerialNum());
		//银行交易流水号
		dto.setBatchNo(request.getBatchNo());
		//是否快递
		dto.setIsExpress(FossConstants.YES);
		//快递员工号
		dto.setDeliverExpressCode(request.getDeliverExpressCode());
		//快递员姓名
		dto.setDeliverExpressName(request.getDeliverExpressName());
		//是否合伙人运单 
		dto.setIsPtp(request.getIsPtp()); 
		return dto;
	}
	
	//签收 确认收入 根据请求的参数，转换成对应接口中的dto
	private LineSignDto getLineSignDto(EcsLineSignRequestDto request){
		LineSignDto dto = new LineSignDto();
		// 操作人编码
		dto.setOperatorName(request.getEmpName());
		// 操作人名称
		dto.setOperatorCode(request.getEmpCode());
		// 签收部门名称
		dto.setSignOrgName(request.getCurrentDeptName());
		// 签收部门编码
		dto.setSignOrgCode(request.getCurrentDeptCode());
		// 运单号
		dto.setWaybillNo(request.getWaybillNo());
		// 签收时间
		dto.setSignDate(request.getSignDate());
		dto.setSignType(request.getSignType());
		// 是否整车运单
		dto.setIsWholeVehicle(request.getIsWholeVehicle());
		// 是否PDA签收
		dto.setIsPdaSign(request.getIsPdaSign());
		// 产品类型
		dto.setProductType(request.getProductType());
		//外发单号
		dto.setExternalWaybillNo(request.getExternalWaybillNo());
		//外发流水号
		dto.setSerialNo(request.getSerialNo());
		//签收情况
		dto.setSignSituation(request.getSignSituation());
		//是否快递
		dto.setIsExpress(FossConstants.YES);
		return dto;
	}
	
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}
	
	public void setPaymentSettlementService(
			IPaymentSettlementService paymentSettlementService) {
		this.paymentSettlementService = paymentSettlementService;
	}

	public void setLineSignService(ILineSignService lineSignService) {
		this.lineSignService = lineSignService;
	}

	public void setCodAuditService(ICodAuditService codAuditService) {
		this.codAuditService = codAuditService;
	}

	public void setEcsSignForSettlement(IEcsSignForSettlement ecsSignForSettlement) {
		this.ecsSignForSettlement = WebApplicationContextHolder.getWebApplicationContext().
				getBean("ecsSignForSettlement",IEcsSignForSettlement.class);
	}

    public void setPdaRepaymentService(IPdaRepaymentService pdaRepaymentService) {
        this.pdaRepaymentService = pdaRepaymentService;
    }

    /*public void setLogEcsFossService(ILogEcsFossService logEcsFossService) {
        this.logEcsFossService = logEcsFossService;
    }*/
}
