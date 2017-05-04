package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

/**
 * 反核销Dto，用户返回反核销后的结果
 * 
 * @author foss-wangxuemin
 * @date Nov 14, 2012 10:01:22 AM
 */
public class BillWritebackOperationDto implements Serializable {

	private static final long serialVersionUID = 7367057722812703855L;

	/**
	 * 应收单
	 */
	private BillReceivableEntity billReceivableEntity;

	/**
	 * 应付单
	 */
	private BillPayableEntity billPayableEntity;

	/**
	 * 预收单
	 */
	private BillDepositReceivedEntity billDepositReceivedEntity;

	/**
	 * 预付单
	 */
	private BillAdvancedPaymentEntity billAdvancedPaymentEntity;

	/**
	 * @return billReceivableEntity
	 */
	public BillReceivableEntity getBillReceivableEntity() {
		return billReceivableEntity;
	}

	/**
	 * @param billReceivableEntity
	 */
	public void setBillReceivableEntity(
			BillReceivableEntity billReceivableEntity) {
		this.billReceivableEntity = billReceivableEntity;
	}

	/**
	 * @return billPayableEntity
	 */
	public BillPayableEntity getBillPayableEntity() {
		return billPayableEntity;
	}

	/**
	 * @param billPayableEntity
	 */
	public void setBillPayableEntity(BillPayableEntity billPayableEntity) {
		this.billPayableEntity = billPayableEntity;
	}

	/**
	 * @return billDepositReceivedEntity
	 */
	public BillDepositReceivedEntity getBillDepositReceivedEntity() {
		return billDepositReceivedEntity;
	}

	/**
	 * @param billDepositReceivedEntity
	 */
	public void setBillDepositReceivedEntity(
			BillDepositReceivedEntity billDepositReceivedEntity) {
		this.billDepositReceivedEntity = billDepositReceivedEntity;
	}

	/**
	 * @return billAdvancedPaymentEntity
	 */
	public BillAdvancedPaymentEntity getBillAdvancedPaymentEntity() {
		return billAdvancedPaymentEntity;
	}

	/**
	 * @param billAdvancedPaymentEntity
	 */
	public void setBillAdvancedPaymentEntity(
			BillAdvancedPaymentEntity billAdvancedPaymentEntity) {
		this.billAdvancedPaymentEntity = billAdvancedPaymentEntity;
	}

}
