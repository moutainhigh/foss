package com.deppon.foss.module.settlement.common.api.server.service;

import java.math.BigDecimal;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineOfcreditResponseDto;

/**
 * 客户信用额度管理服务
 * 
 * @author Administrator
 * 
 */
public interface ICustomerBargainService extends IService {

	/**
	 * 判断能否欠款
	 * 
	 * 使用说明，判断能否欠款均使用同一方法，如果付款方式为月结时请提供客户编码，欠款方式为临欠是请提供组织编码
	 * 
	 * @param customerCode
	 *            客户编码
	 * @param orgCode
	 *            组织编码 （欠款组织编码）
	 * @param debtType
	 *            欠款类别 区分临欠与月结
	 *            请使用常量：SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT
	 *            表示 月结 请使用常量：
	 *            SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
	 *            表示 临时欠款
	 * @param debtAmount
	 *            欠款金额
	 * @return 能否欠款的判断结果
	 */
	DebitDto isBeBebt(String customerCode, String orgCode, String debtType,
			BigDecimal debtAmount);

	/**
	 * 更新客户已用额度
	 * 
	 * @param customerCode
	 *            客户编码
	 * @param orgCode
	 *            组织编码 （欠款组织编码）
	 * @param debtType
	 *            欠款类别 区分临欠与月结
	 *            请使用常量：SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT
	 *            表示 月结 请使用常量：
	 *            SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
	 *            表示 临时欠款
	 * @param  currentInfo 当前客户信息
	 */
	boolean updateUsedAmount(String customerCode, String orgCode,
			String debtType, BigDecimal debtAmount, CurrentInfo currentInfo);

	public LineOfcreditResponseDto getIsBeBebt(String customerCode, String orgCode,
			String debtType, BigDecimal debtAmount);

}
