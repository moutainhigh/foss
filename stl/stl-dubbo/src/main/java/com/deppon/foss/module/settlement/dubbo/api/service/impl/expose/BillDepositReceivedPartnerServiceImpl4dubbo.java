package com.deppon.foss.module.settlement.dubbo.api.service.impl.expose;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
//import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.dubbo.ptp.api.define.BillDepositReceivedPartnerEntity;
import com.deppon.foss.dubbo.ptp.api.define.exception.SettlementException;
import com.deppon.foss.dubbo.ptp.api.service.BillDepositReceivedPartnerService;
import com.deppon.foss.module.settlement.dubbo.api.service.IBillDepositReceivedPayPtpService4dubbo;

/**
 * 生成合伙人预收单
 * @author 335284
 * @since 2016.11.16
 */
public class BillDepositReceivedPartnerServiceImpl4dubbo implements BillDepositReceivedPartnerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BillDepositReceivedPartnerServiceImpl4dubbo.class);

	private static final String addResultMsg = "生成合伙人预收单成功！";
	private IBillDepositReceivedPayPtpService4dubbo billDepositReceivedPayPtpService;

	@Override
	@Transactional
	public BillDepositReceivedPartnerEntity addPartnerDepositReceved(BillDepositReceivedPartnerEntity ptpDto) {
		LOGGER.info("start of addPartnerDepositReceved()");
		BillDepositReceivedPartnerEntity dto = new BillDepositReceivedPartnerEntity();
		// 参数返回dto
		BillDepositReceivedPartnerEntity resultDto = new BillDepositReceivedPartnerEntity();
		if (StringUtils.isEmpty(ptpDto.getCustomerCode())) {
			throw new SettlementException("汇款部门编码为空");
		}

		if (StringUtils.isEmpty(ptpDto.getCustomerName())) {
			throw new SettlementException("汇款部门名称为空");
		}

		if (null == ptpDto.getAmount()) {
			throw new SettlementException("汇款金额为空");
		}

		if (ptpDto.getAmount().compareTo(BigDecimal.ZERO) != 1) {
			throw new SettlementException("金额必须要大于0！");
		}

		if (StringUtils.isEmpty(ptpDto.getGeneratingOrgCode())) {
			throw new SettlementException("标杆编码为空");
		}

		if (StringUtils.isEmpty(ptpDto.getRemitNo())) {
			throw new SettlementException("汇款编号为空");
		}

		if (StringUtils.isEmpty(ptpDto.getRemiterName())) {
			throw new SettlementException("汇款人名称为空");
		}

		if (StringUtils.isEmpty(ptpDto.getPaymentType())) {
			throw new SettlementException("收款方式为空");
		}

		if (null == ptpDto.getBusinessDate()) {
			throw new SettlementException("业务日期为空");
		}

		// 获取汇款部门编码
		String customerCode = ptpDto.getCustomerCode();

		// 获取汇款部门名称
		String customerName = ptpDto.getCustomerName();

		// 获取汇款金额
		BigDecimal amount = ptpDto.getAmount();

		// 获取预收部门标杆编码
		String unifiedCode = ptpDto.getGeneratingOrgCode();

		// 获取汇款编号
		String remitNo = ptpDto.getRemitNo();

		// 获取汇款人名称
		String remiterName = ptpDto.getRemiterName();

		// 获取收款方式
		String paymentType = ptpDto.getPaymentType();

		// 获取业务日期
		Date businessDate = ptpDto.getBusinessDate();

		// 封装dto
		dto.setCustomerCode(customerCode);
		dto.setCustomerName(customerName);
		dto.setAmount(amount);
		dto.setUnifiedCode(unifiedCode);
		dto.setRemitNo(remitNo);
		dto.setRemiterName(remiterName);
		dto.setPaymentType(paymentType);
		dto.setBusinessDate(businessDate);
		dto.setUuid(ptpDto.getUuid());
		LOGGER.info("开始生成逻辑addBillDepositReceivedPay = customerCode:" + customerCode 
				+" amount:"+amount +" paymentType:"+paymentType);
		dto = this.billDepositReceivedPayPtpService.addBillDepositReceivedPay(dto);

		// 设置返回汇款编号
		resultDto.setRemitNo(dto.getRemitNo());
		// 设置成功标识
		resultDto.setIsSuccess(dto.getIsSuccess());

		if (dto.getIsSuccess()) {
			resultDto.setMsg(addResultMsg);
		}
		return resultDto;
	}

	public IBillDepositReceivedPayPtpService4dubbo getBillDepositReceivedPayPtpService() {
		return billDepositReceivedPayPtpService;
	}

	@Autowired
	public void setBillDepositReceivedPayPtpService(IBillDepositReceivedPayPtpService4dubbo billDepositReceivedPayPtpService) {
		this.billDepositReceivedPayPtpService = billDepositReceivedPayPtpService;
	}

}
