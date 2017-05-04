package com.deppon.foss.module.settlement.pay.api.server.service;

import java.math.BigDecimal;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.RemitTransferQueryResultDto;

/**
 * 汇款服务接口类
 * 
 * @author foss-wangxuemin
 * @date Dec 7, 2012 4:43:15 PM
 */
public interface IFossToFinanceRemittanceService extends IService {

	/**
	 * 占用汇款
	 * 
	 * @author foss-wangxuemin
	 * @param claimAmount
	 *            占用金额
	 * @param claimBillNum
	 *            汇款编号
	 * @param claimDeptCode
	 *            占用部门
	 * @date Dec 7, 2012 4:33:35 PM
	 */
	public void obtainClaim(BigDecimal claimAmount, String claimBillNum,
			String claimDeptCode, String debtNumber);

	/**
	 * 释放汇款
	 * 
	 * @param claimAmount
	 *            占用金额
	 * @param claimBillNum
	 *            汇款编号
	 * @param claimDeptCode
	 *            占用部门
	 * @author foss-wangxuemin
	 * @date Dec 7, 2012 4:33:32 PM
	 */
	public void releaseClaim(BigDecimal claimAmount, String claimBillNum,
			String claimDeptCode, String debtNumber);

	/**
	 * 查询汇款信息
	 * 
	 * @param remitTransNum，payableType
	 *            汇款编号，支付方式
	 * @author foss-wangxuemin
	 * @date Dec 12, 2012 3:42:14 PM
	 */
	public RemitTransferQueryResultDto queryTransfer(String remitTransNum, String payableType);

}
