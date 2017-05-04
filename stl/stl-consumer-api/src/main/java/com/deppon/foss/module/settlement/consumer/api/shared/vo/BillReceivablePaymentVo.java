package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;

/**
 * 应收应付单据查询VO
 * 
 * @author foss-zhangxiaohui
 * @date Oct 24, 2012 12:20:13 PM
 */
public class BillReceivablePaymentVo implements Serializable {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = 8356537051751852601L;

	/**
	 * 运单单号数组
	 */
	private String[] wayBillNosArray;

	/**
	 * 应收单List
	 */
	private List<BillReceivableEntity> billReceivableList;

	/**
	 * 应付单List
	 */
	private List<BillPayableEntity> billPayableList;

	/**
	 * 还款单List
	 */
	private List<BillRepaymentEntity> billRepaymentList;

	/**
	 * 现金收款单List
	 */
	private List<BillCashCollectionEntity> billCashCollectionList;

	/**
	 * 付款单List
	 */
	private List<BillPaymentEntity> billPaymentList;

	/**
	 * 核销单List
	 */
	private List<BillWriteoffEntity> billWriteoffList;

	/**
	 * 数据库总记录条数
	 */
	private BigDecimal totalRecordsInDB;

	/**
	 * 合计总金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 核销总金额/实收总金额
	 */
	private BigDecimal totalVerifyAmount;

	/**
	 * 未核销总金额/未收金额
	 */
	private BigDecimal totalUnverifyAmount;

	/**
	 * @return wayBillNosArray
	 */
	public String[] getWayBillNosArray() {
		return wayBillNosArray;
	}

	/**
	 * @param wayBillNosArray
	 */
	public void setWayBillNosArray(String[] wayBillNosArray) {
		this.wayBillNosArray = wayBillNosArray;
	}

	/**
	 * @return billReceivableList
	 */
	public List<BillReceivableEntity> getBillReceivableList() {
		return billReceivableList;
	}

	/**
	 * @param billReceivableList
	 */
	public void setBillReceivableList(
			List<BillReceivableEntity> billReceivableList) {
		this.billReceivableList = billReceivableList;
	}

	/**
	 * @return billPayableList
	 */
	public List<BillPayableEntity> getBillPayableList() {
		return billPayableList;
	}

	/**
	 * @param billPayableList
	 */
	public void setBillPayableList(List<BillPayableEntity> billPayableList) {
		this.billPayableList = billPayableList;
	}

	/**
	 * @return billRepaymentList
	 */
	public List<BillRepaymentEntity> getBillRepaymentList() {
		return billRepaymentList;
	}

	/**
	 * @param billRepaymentList
	 */
	public void setBillRepaymentList(List<BillRepaymentEntity> billRepaymentList) {
		this.billRepaymentList = billRepaymentList;
	}

	/**
	 * @return billCashCollectionList
	 */
	public List<BillCashCollectionEntity> getBillCashCollectionList() {
		return billCashCollectionList;
	}

	/**
	 * @param billCashCollectionList
	 */
	public void setBillCashCollectionList(
			List<BillCashCollectionEntity> billCashCollectionList) {
		this.billCashCollectionList = billCashCollectionList;
	}

	/**
	 * @return billPaymentList
	 */
	public List<BillPaymentEntity> getBillPaymentList() {
		return billPaymentList;
	}

	/**
	 * @param billPaymentList
	 */
	public void setBillPaymentList(List<BillPaymentEntity> billPaymentList) {
		this.billPaymentList = billPaymentList;
	}

	/**
	 * @return billWriteoffList
	 */
	public List<BillWriteoffEntity> getBillWriteoffList() {
		return billWriteoffList;
	}

	/**
	 * @param billWriteoffList
	 */
	public void setBillWriteoffList(List<BillWriteoffEntity> billWriteoffList) {
		this.billWriteoffList = billWriteoffList;
	}

	/**
	 * @return totalRecordsInDB
	 */
	public BigDecimal getTotalRecordsInDB() {
		return totalRecordsInDB;
	}

	/**
	 * @param totalRecordsInDB
	 */
	public void setTotalRecordsInDB(BigDecimal totalRecordsInDB) {
		this.totalRecordsInDB = totalRecordsInDB;
	}

	/**
	 * @return totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return totalVerifyAmount
	 */
	public BigDecimal getTotalVerifyAmount() {
		return totalVerifyAmount;
	}

	/**
	 * @param totalVerifyAmount
	 */
	public void setTotalVerifyAmount(BigDecimal totalVerifyAmount) {
		this.totalVerifyAmount = totalVerifyAmount;
	}

	/**
	 * @return totalUnverifyAmount
	 */
	public BigDecimal getTotalUnverifyAmount() {
		return totalUnverifyAmount;
	}

	/**
	 * @param totalUnverifyAmount
	 */
	public void setTotalUnverifyAmount(BigDecimal totalUnverifyAmount) {
		this.totalUnverifyAmount = totalUnverifyAmount;
	}

}
