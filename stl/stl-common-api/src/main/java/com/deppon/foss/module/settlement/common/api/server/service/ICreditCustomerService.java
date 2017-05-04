package com.deppon.foss.module.settlement.common.api.server.service;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.CreditCustomerEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditCustomerDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CustomerInUseDto;

/**
 * 客户信用额度服务
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-16 下午6:43:42
 */
public interface ICreditCustomerService{

	/**
	 * 信息客户用额度信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-16 下午6:47:07
	 */
	void addCreditCustomer(CreditCustomerEntity item);

	/**
	 * 根据Id查询客户基础信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-16 下午6:49:16
	 */
	CreditCustomerEntity queryByCustomerCode(String customerCode);

	/**
	 * 更新客户的可用额度
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 上午8:07:57
	 */
	void updateCreditCustomer(CreditCustomerEntity item);

	/**
	 * 
	 * 获得组织行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-24 上午11:22:49
	 */
	int queryTotalRows();

	/**
	 * 通过分页的方式查询客户余额信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-24 下午3:02:16
	 */
	List<CreditCustomerDto> queryByPage(int offset, int limit);

	/**
	 * 按客户编码查询dto
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 下午3:24:23
	 */
	CreditCustomerDto queryDebitByCustomerCode(String customerCode);

	/**
	 * 更新客户已用额度
	 * 
	 * @param customerCode
	 *            客户编码
	 */
	void updateUsedAmount(String customerCode, BigDecimal debtAmount);

	/**
	 * 更新客户的超期欠款标记
	 * 
	 * @param customerCode
	 *            客户编码
	 */
	void updateOverdueStatus(String customerCode, String isOverdue, String notes);

	/**
	 * 查看客户是否在使用，以便CRM作废
	 * 
	 * @param customerCodes
	 *            编码编码
	 * @return
	 */
	List<CustomerInUseDto> isCustomerInUse(List<String> customerCodes);

    /**
     * 通过客户编码查询客户详细欠款信息
     * @param customerCode
     * @return
     */
    CreditCustomerDto queryDebitCustomerInfo(String customerCode);

    /**
     * 通过客户编码查询客户详细欠款信息
     * @param customerCode
     * @return
     */
    CreditCustomerDto queryCustomerDebit(String customerCode,String productId);

}
