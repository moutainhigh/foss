package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;

/**
 * 查询应付单返回dto
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-11-16 下午4:24:03
 */
public class BillPayableManageResultDto implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 6250425463486513629L;

	/**
	 * 总条数
	 */
	private long totalCount;

	/**
	 * 总金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 总未核销金额
	 */
	private BigDecimal totalUnVerifyAmount;

	/**
	 * 总已核销金额
	 */
	private BigDecimal totalVerifyAmount;

	/**
	 * 返回应付单实体列表
	 */
	private List<BillPayableEntity> list = new ArrayList<BillPayableEntity>();

	/**
	 * 付款单明细列表
	 */
	private List<BillPaymentAddDto> addDtoList = new ArrayList<BillPaymentAddDto>();

	/**
	 * 付款单实体
	 */
	private BillPaymentEntity paymentEntity = new BillPaymentEntity();
	
	/**
	 * 获取费用承担部门性质  --临时租车使用
	 */
	private String costDeptType;

	/**
	 * @return totalCount
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
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
	 * @return totalUnVerifyAmount
	 */
	public BigDecimal getTotalUnVerifyAmount() {
		return totalUnVerifyAmount;
	}

	/**
	 * @param totalUnVerifyAmount
	 */
	public void setTotalUnVerifyAmount(BigDecimal totalUnVerifyAmount) {
		this.totalUnVerifyAmount = totalUnVerifyAmount;
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
	 * @return list
	 */
	public List<BillPayableEntity> getList() {
		return list;
	}

	/**
	 * @param list
	 */
	public void setList(List<BillPayableEntity> list) {
		this.list = list;
	}

	/**
	 * @return addDtoList
	 */
	public List<BillPaymentAddDto> getAddDtoList() {
		return addDtoList;
	}

	/**
	 * @param addDtoList
	 */
	public void setAddDtoList(List<BillPaymentAddDto> addDtoList) {
		this.addDtoList = addDtoList;
	}

	/**
	 * @return paymentEntity
	 */
	public BillPaymentEntity getPaymentEntity() {
		return paymentEntity;
	}

	/**
	 * @param paymentEntity
	 */
	public void setPaymentEntity(BillPaymentEntity paymentEntity) {
		this.paymentEntity = paymentEntity;
	}

	public String getCostDeptType() {
		return costDeptType;
	}

	public void setCostDeptType(String costDeptType) {
		this.costDeptType = costDeptType;
	}

}
