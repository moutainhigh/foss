package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;

/**
 * 付款单返回结果Dto
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-19 上午10:26:44
 */
public class BillPaymentResultDto implements Serializable {

	/**
	 * 付款单返回Dto
	 */
	private static final long serialVersionUID = -1126051381689441608L;

	/**
	 * 付款单结果
	 */
	private BillPaymentEntity billPaymentEntity = new BillPaymentEntity();

	/**
	 * 付款单列表结果
	 */
	private List<BillPaymentEntity> billPaymentEntityList = new ArrayList<BillPaymentEntity>();

	/**
	 * 付款单总条数
	 */
	private Long paymentTotalRows;

	/**
	 * 付款单总金额
	 */
	private BigDecimal paymentTotalAmount;
	
	/**
	 * 付款单明细总条数
	 */
	private Long paymentDetialTotal;

	/**
	 * 付款单明细中关联应收、预收列表
	 */
	private List<BillPaymentAddDto> billPaymentAddDtoList = new ArrayList<BillPaymentAddDto>();

	
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
	 * @return billPaymentEntityList
	 */
	public List<BillPaymentEntity> getBillPaymentEntityList() {
		return billPaymentEntityList;
	}

	
	/**
	 * @param billPaymentEntityList
	 */
	public void setBillPaymentEntityList(
			List<BillPaymentEntity> billPaymentEntityList) {
		this.billPaymentEntityList = billPaymentEntityList;
	}

	
	/**
	 * @return paymentTotalRows
	 */
	public Long getPaymentTotalRows() {
		return paymentTotalRows;
	}

	
	/**
	 * @param paymentTotalRows
	 */
	public void setPaymentTotalRows(Long paymentTotalRows) {
		this.paymentTotalRows = paymentTotalRows;
	}

	
	/**
	 * @return paymentTotalAmount
	 */
	public BigDecimal getPaymentTotalAmount() {
		return paymentTotalAmount;
	}

	
	/**
	 * @param paymentTotalAmount
	 */
	public void setPaymentTotalAmount(BigDecimal paymentTotalAmount) {
		this.paymentTotalAmount = paymentTotalAmount;
	}

	
	/**
	 * @return billPaymentAddDtoList
	 */
	public List<BillPaymentAddDto> getBillPaymentAddDtoList() {
		return billPaymentAddDtoList;
	}

	
	/**
	 * @param billPaymentAddDtoList
	 */
	public void setBillPaymentAddDtoList(
			List<BillPaymentAddDto> billPaymentAddDtoList) {
		this.billPaymentAddDtoList = billPaymentAddDtoList;
	}


	/**
	 * @return the paymentDetialTotal
	 */
	public Long getPaymentDetialTotal() {
		return paymentDetialTotal;
	}


	/**
	 * @param paymentDetialTotal the paymentDetialTotal to set
	 */
	public void setPaymentDetialTotal(Long paymentDetialTotal) {
		this.paymentDetialTotal = paymentDetialTotal;
	}

	
}
