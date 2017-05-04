package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;

/**
 * 核销操作Dto，用于Service和Dao之间的参数传递
 * 
 * @author foss-wangxuemin
 * @date 2012-10-19 下午4:16:00
 */
public class BillWriteoffOperationDto implements Serializable {

	private static final long serialVersionUID = 6871999852994208721L;

	/**
	 * 应收单列表，应收冲应付、预付冲应收和还款冲应收时需要传入
	 */
	private List<BillReceivableEntity> billReceivableEntitys;

	/**
	 * 应付单列表，应收冲应付和预付冲应付时需要传入
	 */
	private List<BillPayableEntity> billPayableEntitys;

	/**
	 * 预收单列表，预收冲应收或者付款冲预收时传入
	 */
	private List<BillDepositReceivedEntity> billDepositReceivedEntitys;

	/**
	 * 预付单列表，预付冲应付时需要传入
	 */
	private List<BillAdvancedPaymentEntity> billAdvancedPaymentEntitys;

	/**
	 * 还款单，还款冲应收时需要传入
	 */
	private BillRepaymentEntity billRepaymentEntity;

	/**
	 * 付款单，付款冲应付时需要传入
	 */
	private BillPaymentEntity billPaymentEntity;

	/**
	 * 核销批次号，不能为空
	 */
	private String writeoffBatchNo;

	/**
	 * 对账单号，按照对账单还款时需要传入
	 */
	private String statementBillNo;

	/**
	 * 核销方式，不能为空
	 */
	private String createType;

	/**
	 * 坏账核销金额，坏账核销时不能为空
	 */
	private BigDecimal badWriteoffAmount;

	/**
	 * @return billReceivableEntitys
	 */
	public List<BillReceivableEntity> getBillReceivableEntitys() {
		return billReceivableEntitys;
	}

	/**
	 * @param billReceivableEntitys
	 */
	public void setBillReceivableEntitys(
			List<BillReceivableEntity> billReceivableEntitys) {
		this.billReceivableEntitys = billReceivableEntitys;
	}

	/**
	 * @return billPayableEntitys
	 */
	public List<BillPayableEntity> getBillPayableEntitys() {
		return billPayableEntitys;
	}

	/**
	 * @param billPayableEntitys
	 */
	public void setBillPayableEntitys(List<BillPayableEntity> billPayableEntitys) {
		this.billPayableEntitys = billPayableEntitys;
	}

	/**
	 * @return billDepositReceivedEntitys
	 */
	public List<BillDepositReceivedEntity> getBillDepositReceivedEntitys() {
		return billDepositReceivedEntitys;
	}

	/**
	 * @param billDepositReceivedEntitys
	 */
	public void setBillDepositReceivedEntitys(
			List<BillDepositReceivedEntity> billDepositReceivedEntitys) {
		this.billDepositReceivedEntitys = billDepositReceivedEntitys;
	}

	/**
	 * @return billAdvancedPaymentEntitys
	 */
	public List<BillAdvancedPaymentEntity> getBillAdvancedPaymentEntitys() {
		return billAdvancedPaymentEntitys;
	}

	/**
	 * @param billAdvancedPaymentEntitys
	 */
	public void setBillAdvancedPaymentEntitys(
			List<BillAdvancedPaymentEntity> billAdvancedPaymentEntitys) {
		this.billAdvancedPaymentEntitys = billAdvancedPaymentEntitys;
	}

	/**
	 * @return billRepaymentEntity
	 */
	public BillRepaymentEntity getBillRepaymentEntity() {
		return billRepaymentEntity;
	}

	/**
	 * @param billRepaymentEntity
	 */
	public void setBillRepaymentEntity(BillRepaymentEntity billRepaymentEntity) {
		this.billRepaymentEntity = billRepaymentEntity;
	}

	/**
	 * @return billPaymentEntity
	 */
	public BillPaymentEntity getBillPaymentEntity() {
		return billPaymentEntity;
	}

	/**
	 * @param billPaymentEntity
	 */
	public void setBillPaymentEntity(BillPaymentEntity billPaymentEntity) {
		this.billPaymentEntity = billPaymentEntity;
	}

	/**
	 * @return writeoffBatchNo
	 */
	public String getWriteoffBatchNo() {
		return writeoffBatchNo;
	}

	/**
	 * @param writeoffBatchNo
	 */
	public void setWriteoffBatchNo(String writeoffBatchNo) {
		this.writeoffBatchNo = writeoffBatchNo;
	}

	/**
	 * @return statementBillNo
	 */
	public String getStatementBillNo() {
		return statementBillNo;
	}

	/**
	 * @param statementBillNo
	 */
	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}

	/**
	 * @return createType
	 */
	public String getCreateType() {
		return createType;
	}

	/**
	 * @param createType
	 */
	public void setCreateType(String createType) {
		this.createType = createType;
	}

	/**
	 * @return badWriteoffAmount
	 */
	public BigDecimal getBadWriteoffAmount() {
		return badWriteoffAmount;
	}

	/**
	 * @param badWriteoffAmount
	 */
	public void setBadWriteoffAmount(BigDecimal badWriteoffAmount) {
		this.badWriteoffAmount = badWriteoffAmount;
	}

}
