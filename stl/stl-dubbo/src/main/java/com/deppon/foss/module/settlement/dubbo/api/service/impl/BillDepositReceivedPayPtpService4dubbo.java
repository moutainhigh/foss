package com.deppon.foss.module.settlement.dubbo.api.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.dubbo.ptp.api.define.BillDepositReceivedPartnerEntity;
import com.deppon.foss.dubbo.ptp.api.define.exception.SettlementException;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.settlement.dubbo.api.dao.IBillDepositReceivedEntityDao4dubbo;
import com.deppon.foss.module.settlement.dubbo.api.define.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.dubbo.api.service.IBillDepositReceivedPayPtpService4dubbo;
import com.deppon.foss.module.settlement.dubbo.api.service.IBillDepositReceivedService4dubbo;
import com.deppon.foss.module.settlement.dubbo.api.service.ISettlementCommonService4dubbo;
import com.deppon.foss.module.settlement.dubbo.api.util.FossConstants;
import com.deppon.foss.module.settlement.dubbo.api.util.SettlementConstants;
import com.deppon.foss.module.settlement.dubbo.api.util.SettlementDictionaryConstants;
import com.deppon.foss.util.UUIDUtils;


/**
 * 对接合伙人预收单service接口实现
 * 
 * @author foss-Jiang Xun
 * @date 2016-01-07 下午03:05:00
 * 
 */
public class BillDepositReceivedPayPtpService4dubbo implements IBillDepositReceivedPayPtpService4dubbo {
	private static final Logger logger = LoggerFactory.getLogger(BillDepositReceivedPayPtpService4dubbo.class);

	/**
	 * 注入自动获取单号的公共接口
	 */
	private ISettlementCommonService4dubbo settlementCommonService;

	/**
	 * 注入获取组织信息的公共接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	@Autowired
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	@Autowired
	public void setBillDepositReceivedEntityDao(IBillDepositReceivedEntityDao4dubbo billDepositReceivedEntityDao) {
		this.billDepositReceivedEntityDao = billDepositReceivedEntityDao;
	}

	/**
	 * 预收单service
	 */
	private IBillDepositReceivedService4dubbo billDepositReceivedService;

	/**
	 * 注入dao
	 */
	private IBillDepositReceivedEntityDao4dubbo billDepositReceivedEntityDao;

	@Transactional
	@Override
	public BillDepositReceivedPartnerEntity addBillDepositReceivedPay(
			BillDepositReceivedPartnerEntity billDepositReceivedPartnerDto) {
		try {
			// 根据汇款编号校验合伙人预收单是否存在
			BillDepositReceivedEntity depositReceivedEntity = getBillDepositReceivedService()
					.queryByRemitNo(billDepositReceivedPartnerDto.getRemitNo(), FossConstants.ACTIVE);
			if (depositReceivedEntity != null) {
				throw new SettlementException("合伙人预收单汇款编号" + billDepositReceivedPartnerDto.getRemitNo() + "已存在");
			}

			BillDepositReceivedEntity entity = new BillDepositReceivedEntity();

			// 付款方式为电汇且编码不能为空
			if (StringUtils.equals(billDepositReceivedPartnerDto.getPaymentType(),
					SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER)) {
				if (StringUtils.isBlank(billDepositReceivedPartnerDto.getRemitNo())) {
					throw new SettlementException("电汇支付编码为空");
				}
			} else if (StringUtils.equals(billDepositReceivedPartnerDto.getPaymentType(),
					SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT__US) ||
					StringUtils.equals(billDepositReceivedPartnerDto.getPaymentType(), 
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__COMMISSION_FEE) ||
							StringUtils.equals(billDepositReceivedPartnerDto.getPaymentType(), 
									SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__EXPRESS_ERROR_RETURN) ||
									StringUtils.equals(billDepositReceivedPartnerDto.getPaymentType(), 
											SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__REWARD_RETURN)) {
				// continue
			} else {
				throw new SettlementException("支付方式错误！");
			}

			String uuid = billDepositReceivedPartnerDto.getUuid();
			if (StringUtils.isEmpty(uuid)) {
				throw new SettlementException("传入ID为空！");
			}
			// 设置id
			entity.setId(uuid);

			// 设置合伙人预收单号,预收单号系统自动生成，生成规则“US+流水号”
			entity.setDepositReceivedNo(getSettlementCommonService().getSettlementNoRule(SettlementNoRuleEnum.US));

			// 设置付款单号--默认
			entity.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);

			// 根据合伙人组织标杆编码获取合伙人部门组织的的实体信息
			OrgAdministrativeInfoEntity customerOrgEntity = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByUnifiedCodeNoCache(billDepositReceivedPartnerDto.getCustomerCode());
			if (customerOrgEntity == null) {
				throw new SettlementException("当前的合伙人部门组织信息为空！");
			}

			// 设置合伙人编码
			entity.setCustomerCode(customerOrgEntity.getCode());

			// 设置合伙人名称
			entity.setCustomerName(customerOrgEntity.getName());

			// 设置录入部门编码
			entity.setCreateOrgCode(customerOrgEntity.getCode());

			// 设置录入部门名称
			entity.setCreateOrgName(customerOrgEntity.getName());

			// 根据组织标杆编码获取预收部门组织的的实体信息
			OrgAdministrativeInfoEntity generatOrgEntity = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByUnifiedCode(billDepositReceivedPartnerDto.getUnifiedCode());
			if (generatOrgEntity == null) {
				throw new SettlementException("当前选择的预收部门组织信息为空！");
			}

			// 设置预收部门编码
			entity.setGeneratingOrgCode(generatOrgEntity.getCode());

			// 设置预收部门名称
			entity.setGeneratingOrgName(generatOrgEntity.getName());

			// 设置预收部门所属子公司编码
			entity.setGeneratingCompanyCode(generatOrgEntity.getSubsidiaryCode());

			// 设置预收部门所属子公司名称
			entity.setGeneratingCompanyName(generatOrgEntity.getSubsidiaryName());

			// 设置收款部门编码
			entity.setCollectionOrgCode(generatOrgEntity.getCode());

			// 设置收款部门名称
			entity.setCollectionOrgName(generatOrgEntity.getName());

			// 设置收款部门所属子公司编码
			entity.setCollectionCompanyCode(generatOrgEntity.getSubsidiaryCode());

			// 设置收款部门所属子公司名称
			entity.setCollectionCompanyName(generatOrgEntity.getSubsidiaryName());

			// 设置退款状态默认为未退款
			entity.setRefundStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__REFUND_STATUS__NOT_REFUND);

			// 设置预收单的币种
			entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);

			// 设置金额
			entity.setAmount(billDepositReceivedPartnerDto.getAmount());

			// 设置未核销金额
			entity.setUnverifyAmount(billDepositReceivedPartnerDto.getAmount());

			// 设置已核销金额
			entity.setVerifyAmount(BigDecimal.ZERO);

			// 设置汇款编号
			entity.setRemitNo(billDepositReceivedPartnerDto.getRemitNo());

			// 设置汇款人名称
			entity.setRemiterName(billDepositReceivedPartnerDto.getRemiterName());

			// 设置汇款所属部门编码
			entity.setRemitOrgCode(billDepositReceivedPartnerDto.getGeneratingOrgCode());

			// 汇款所属部门名称
			entity.setRemitOrgName(billDepositReceivedPartnerDto.getGeneratingOrgName());

			// 设置单据类型
			entity.setBillType(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE__DEPOSIT_PARTNER);

			// 设置单据状态默认为已确认
			entity.setStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS__CONFIRM);

			// 设置是否有效
			entity.setActive(FossConstants.ACTIVE);

			// 设置是否红单
			entity.setIsRedBack(FossConstants.NO);

			// 设置收款方式
			entity.setPaymentType(billDepositReceivedPartnerDto.getPaymentType());

			// 设置业务日期
			entity.setBusinessDate(billDepositReceivedPartnerDto.getBusinessDate());

			// 设置记账、创建时间
			Date nowDate = new Date();
			entity.setAccountDate(nowDate);
			entity.setCreateTime(nowDate);

			// 设置是否初始化
			entity.setIsInit(FossConstants.NO);

			// 设置版本号
			entity.setVersionNo(FossConstants.INIT_VERSION_NO);

			entity.setTransportType(SettlementDictionaryConstants.CUSTOMER_TYPE__PARTNER);

			// 设置客户类型
			entity.setCustomerType(SettlementDictionaryConstants.CUSTOMER_TYPE__PARTNER);

			// 设置发票标记
			entity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);

			// 设置制单人编号：默认：000000
			entity.setCreateUserCode(FossConstants.DEFAULT_USER_CODE);

			// 设置制单人名称：默认：系统管理员
			entity.setCreateUserName("系统管理员");

			// 设置默认对账单号--N/A
			entity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
			// entity.setId(UUIDUtils.getUUID());

			logger.info("插入的实体属性是： " + entity.toString());
			// 新增预收单
			billDepositReceivedEntityDao.addPartner(entity);

			// 设置合伙人预收单新增成功标识
			billDepositReceivedPartnerDto.setIsSuccess(true);

			return billDepositReceivedPartnerDto;
		} catch (BusinessException e) {
			// 设置合伙人预收单新增失败标识
			billDepositReceivedPartnerDto.setIsSuccess(false);
			// 记录日志
			throw new SettlementException(e.getErrorCode());
		}
	}

	public ISettlementCommonService4dubbo getSettlementCommonService() {
		return settlementCommonService;
	}

	@Autowired
	public void setSettlementCommonService(ISettlementCommonService4dubbo settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public IBillDepositReceivedService4dubbo getBillDepositReceivedService() {
		return billDepositReceivedService;
	}

	@Autowired
	public void setBillDepositReceivedService(IBillDepositReceivedService4dubbo billDepositReceivedService) {
		this.billDepositReceivedService = billDepositReceivedService;
	}
}