package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentAddDto;

/**
 * 家装对账单DAO
 * 
 * @ClassName: IHomeStatementPaymentDao
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-12-10 下午2:49:16
 */
public interface IDopStatementPaymentDao {
	/**
	 * 增加付款单明细
	 * 
	 * @Title: addBillPaymentD
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public void addBillPaymentD(String paymentNum, String statementBillNo);

	/**
	 * 增加付款单明细
	 * @Title: queryOrgAndAmountbyNo
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public void addBillPaymentDetails(String paymentNum, List<String> list);

	/**
	 * 付款
	 * 
	 * @Title: payForBillPayable
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@SuppressWarnings("rawtypes")
	public void payForBillPayable(Map map);

	/**
	 * 根据应付单号去去查部门和该部门付款总金额
	 * 
	 * @Title: queryOrgAndAmountbyNo
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public List<BillPaymentEntity> queryOrgAndAmountbyNo(
			List<BillPaymentAddDto> addDtoList);

	/**
	 * 根据部门编号和对账单号获取应付单号集合
	 * 
	 * @Title: queryOrgAndAmountbyNo
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-03-05  下午2:49:16
	 */
	public List<BillPaymentEntity> queryPayableByOrgCode(String orgCode,String statementBillNo);
}