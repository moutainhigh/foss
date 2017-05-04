package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillStatementToPaymentQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillStatementToPaymentResultDto;

/**
 * 
 * 查询 审核、作废、还款单用于 Action传入到界面
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午8:44:23
 */
public class BillRepaymentManageVo implements Serializable {

	/**
	 * VO类序列号
	 */
	private static final long serialVersionUID = -4914358752755960492L;

	// 还款单Action传入service
	private BillRepaymentManageDto billRepaymentManageDto;

	/**
	 * 按对账单还款输入参数Dto
	 */
	private BillStatementToPaymentQueryDto billStatementToPaymentQueryDto;

	/**
	 * 按对账单还款返回结果Dto
	 */
	private BillStatementToPaymentResultDto billStatementToPaymentResultDto;

	/**
	 * @return billRepaymentManageDto
	 */
	public BillRepaymentManageDto getBillRepaymentManageDto() {
		return billRepaymentManageDto;
	}

	/**
	 * @param billRepaymentManageDto
	 */
	public void setBillRepaymentManageDto(
			BillRepaymentManageDto billRepaymentManageDto) {
		this.billRepaymentManageDto = billRepaymentManageDto;
	}

	/**
	 * @return billStatementToPaymentQueryDto
	 */
	public BillStatementToPaymentQueryDto getBillStatementToPaymentQueryDto() {
		return billStatementToPaymentQueryDto;
	}

	/**
	 * @param billStatementToPaymentQueryDto
	 */
	public void setBillStatementToPaymentQueryDto(
			BillStatementToPaymentQueryDto billStatementToPaymentQueryDto) {
		this.billStatementToPaymentQueryDto = billStatementToPaymentQueryDto;
	}

	/**
	 * @return billStatementToPaymentResultDto
	 */
	public BillStatementToPaymentResultDto getBillStatementToPaymentResultDto() {
		return billStatementToPaymentResultDto;
	}

	/**
	 * @param billStatementToPaymentResultDto
	 */
	public void setBillStatementToPaymentResultDto(
			BillStatementToPaymentResultDto billStatementToPaymentResultDto) {
		this.billStatementToPaymentResultDto = billStatementToPaymentResultDto;
	}

}
