package com.deppon.foss.module.settlement.ptpitf.server.ws;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillDepositReceivedPayPtpService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPartnerDto;
import com.deppon.foss.module.settlement.ptpitf.server.inter.BillDepositReceivedPartnerService;


public class BillDepositReceivedPartnerServiceImpl implements
		BillDepositReceivedPartnerService {

	/**
	 * 日志属性
	 */
	private static final Logger logger = LogManager
			.getLogger(BillDepositReceivedPartnerServiceImpl.class);

	@Context
	HttpServletResponse response;
	@Context
	HttpServletRequest request;

	/**
	 * 注入Service
	 */
	private IBillDepositReceivedPayPtpService billDepositReceivedPayPtpService;
	/**
	 * 合伙人预存款生成预收单
	 */
	@SuppressWarnings({ "finally" })
	@Override
	public String addPartnerDepositReceved(String partnerDepositReceved) {
		logger.info("生成合伙人预收单开始...");
		BillDepositReceivedPartnerDto dto = new BillDepositReceivedPartnerDto();
		//参数返回dto
		BillDepositReceivedPartnerDto resultDto = new BillDepositReceivedPartnerDto();
		try {
			// 将JSON字符串转为实体对象
			JSONObject ja = JSONObject.parseObject(partnerDepositReceved);
			BillDepositReceivedPartnerDto ptpDto = JSONObject.toJavaObject(ja,BillDepositReceivedPartnerDto.class);
			
			if(StringUtils.isEmpty(ptpDto.getCustomerCode())){
				throw new SettlementException("汇款部门编码为空");
			}
			
			if(StringUtils.isEmpty(ptpDto.getCustomerName())){
				throw new SettlementException("汇款部门名称为空");
			}
			
			if(null==ptpDto.getAmount()){
				throw new SettlementException("汇款金额为空");
			}

			if (ptpDto.getAmount().compareTo(BigDecimal.ZERO) != 1) {
				throw new SettlementException("金额必须要大于0！");
			}
			
			validaExtracted(ptpDto);
			
			// 获取汇款部门编码
			String customerCode = ptpDto.getCustomerCode();
			
			// 获取汇款部门名称
			String customerName = ptpDto.getCustomerName();
			
			//获取汇款金额
			BigDecimal amount = ptpDto.getAmount();
					
			//获取预收部门标杆编码
			String unifiedCode=ptpDto.getGeneratingOrgCode();
			
			//获取汇款编号
			String remitNo = ptpDto.getRemitNo();
			
			//获取汇款人名称
			String remiterName = ptpDto.getRemiterName();
			
			//获取收款方式
			String paymentType = ptpDto.getPaymentType();
			
			//获取业务日期
			Date businessDate = ptpDto.getBusinessDate();
			
			//日志记录
			logger.info("生成合伙人预收单。"+ReflectionToStringBuilder.toString(ptpDto));
			
			//封装dto
			dto.setCustomerCode(customerCode);
			dto.setCustomerName(customerName);
			dto.setAmount(amount);
			dto.setUnifiedCode(unifiedCode); 			
			dto.setRemitNo(remitNo);
			dto.setRemiterName(remiterName);
			dto.setPaymentType(paymentType);
			dto.setBusinessDate(businessDate);
			
			dto =this.billDepositReceivedPayPtpService.addBillDepositReceivedPay(dto);
		
			//设置返回汇款编号
			resultDto.setRemitNo(dto.getRemitNo());
			//设置成功标识
			resultDto.setIsSuccess(dto.getIsSuccess());
			 
			if(dto.getIsSuccess()){
				dto.setMsg("生成合伙人预收单成功！");			
			}
		} catch (SettlementException e) {
			dto.setMsg(e.getErrorCode());
			logger.info("生成合伙人预收单异常,汇款编号："+resultDto.getRemitNo() + e.getErrorCode());
		} catch (Exception e1) {
			dto.setMsg(e1.getMessage());
			logger.info("生成合伙人预收单异常,汇款编号："+resultDto.getRemitNo() + e1.getMessage());
		} finally {
			response.addHeader("ESB-ResultCode", "1");
			logger.info("生成合伙人预收单结束");
			return JSONObject.toJSONString(resultDto);
		}
	}

	private void validaExtracted(BillDepositReceivedPartnerDto ptpDto) {
		if(StringUtils.isEmpty(ptpDto.getGeneratingOrgCode())){
			throw new SettlementException("标杆编码为空");
		}
		
		if(StringUtils.isEmpty(ptpDto.getRemitNo())){
			throw new SettlementException("汇款编号为空");
		}
		
		if(StringUtils.isEmpty(ptpDto.getRemiterName())){
			throw new SettlementException("汇款人名称为空");
		}
		
		if(StringUtils.isEmpty(ptpDto.getPaymentType())){
			throw new SettlementException("收款方式为空");
		}
		
		if(null==ptpDto.getBusinessDate()){
			throw new SettlementException("业务日期为空");
		}
	}

	public IBillDepositReceivedPayPtpService getBillDepositReceivedPayPtpService() {
		return billDepositReceivedPayPtpService;
	}

	public void setBillDepositReceivedPayPtpService(
			IBillDepositReceivedPayPtpService billDepositReceivedPayPtpService) {
		this.billDepositReceivedPayPtpService = billDepositReceivedPayPtpService;
	}
	

}
