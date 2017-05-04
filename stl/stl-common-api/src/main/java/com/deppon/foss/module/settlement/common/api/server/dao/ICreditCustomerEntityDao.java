package com.deppon.foss.module.settlement.common.api.server.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.CreditCustomerEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditCustomerDto;

/**
 * 客户收支平衡表
 * 
 * @author dp-huangxb
 * @date 2012-10-19 下午3:31:43
 */
public interface ICreditCustomerEntityDao {

	/**
	 * 新加客户收支平衡表
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-19 下午3:32:00
	 * @param entity
	 *            客户收支平衡
	 * @return
	 */
	int addCreditCustomer(CreditCustomerEntity entity);

	/**
	 * 更新客户可用额度
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-19 下午3:32:00
	 * @param entity
	 *            客户收支平衡
	 * @return
	 */
	int updateCreditCustomer(CreditCustomerEntity entity);

	/**
	 * 按照客户编码更新客户已用额度信息
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-20 下午4:53:49
	 * @param customerCode
	 *            客户编码
	 * @param amount
	 *            金额
	 * @return
	 */
	int updateUsedAmount(String customerCode, BigDecimal amount);

	/**
	 * 按照客户编码更新客户超期欠款标记
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-20 下午4:53:49
	 * @param customerCode
	 *            客户编码
	 * @param status
	 *            状态
	 * @param notes
	 *            备注
	 * @return
	 */
	int updateOverdueStatus(String customerCode, String status, String notes);

	/**
	 * 查询客户收支平衡表
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-19 下午3:32:29
	 * @param customerNumber
	 *            客户编码
	 * @return
	 */
	CreditCustomerEntity queryByCustomerCode(String customerNumber);

	/**
	 * 
	 * 安客户编码查询客户的可用额度信息，是否存在超期欠款
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-19 下午4:36:42
	 * @param customerNumber
	 *            客户编码
	 * @return
	 */
	CreditCustomerDto queryDebitByCustomerCode(String customerNumber);

	/**
	 * 获得客户信息的总行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 下午4:35:41
	 * @return
	 */
	int queryTotalRows();

	/**
	 * 通过分页获得相应的客户编码
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 下午4:44:07
	 * @param offset
	 *            偏移量
	 * @param limit
	 *            限制最大行
	 * @return
	 */
	List<CreditCustomerDto> queryCreditCustomerByPage(int offset, int limit);
	
	
	/**
	 * 查看客户是否在使用，以便CRM作废
	 * 
	 * @param customerCodes
	 *            编码编码
	 * @return
	 */
	List<String> isCustomerInUseRCV(String active, Date acctDate,
			List<String> customerCodes);

	/**
	 * 查看客户是否在使用，以便CRM作废
	 * 
	 * @param customerCodes
	 *            编码编码
	 * @return
	 */
	List<String> isCustomerInUsePAY(String active, Date acctDate,
			List<String> customerCodes);
	
	/**
	 * 查看客户是否在使用，以便CRM作废
	 * 
	 * @param customerCodes
	 *            编码编码
	 * @return
	 */
	List<String> isCustomerInUseDR(String active, Date acctDate,
			List<String> customerCodes);

    /**
     * 根据客户编码查询客户对象
     * @param customerCode
     * @return
     */
    List<CreditCustomerDto> queryCustomerDebitInfo(String customerCode);
    /**
     * 根据客户编码查询客户对象
     * @param customerCode
     * @return
     */
    List<CreditCustomerDto> queryCustomerDebit(String customerCode,String productId);
}
