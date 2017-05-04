package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentAddDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.PayToCostcontrolDto;

/**
 * 付款单Vo
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-19 上午10:39:19
 */
public class BillPaymentVo implements Serializable {

	/**
	 * 付款单Vo序列号
	 */
	private static final long serialVersionUID = -4060051014259410549L;

	/**
	 * 付款单参数Dto
	 */
	private BillPaymentQueryDto billPaymentQueryDto;
	
	/**
	 * 付款单返回结果Dto
	 */
	private BillPaymentResultDto billPaymentResultDto;
	
	/**
	 * 付款单明细列表
	 */
	private List<BillPaymentAddDto> addDtoList = new ArrayList<BillPaymentAddDto>();

	/**
	 * 付款单实体
	 */
	private BillPaymentEntity paymentEntity = new BillPaymentEntity();
	
	/**
	 *应付单或预收单号集合 
	 */
	private String[] billNos;
	
	/**
	 * 申请备用金工作流dto
	 */
	private PayToCostcontrolDto costcontrolDto = new PayToCostcontrolDto();

	private String payToSystem;
	
	private String statementBillNo;
	/**
	 * 是否保理
	 */
	private String factoring;
	/**
	 * 是否保理需求
	 */
	private String isFactoring;
	
	public String getIsFactoring() {
		return isFactoring;
	}

	public void setIsFactoring(String isFactoring) {
		this.isFactoring = isFactoring;
	}

	public String getFactoring() {
		return factoring;
	}

	public void setFactoring(String factoring) {
		this.factoring = factoring;
	}
	
	public String getStatementBillNo() {
		return statementBillNo;
	}

	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}

	public String getPayToSystem() {
		return payToSystem;
	}

	public void setPayToSystem(String payToSystem) {
		this.payToSystem = payToSystem;
	}

	/**
	 * @return billPaymentQueryDto
	 */
	public BillPaymentQueryDto getBillPaymentQueryDto() {
		return billPaymentQueryDto;
	}

	
	/**
	 * @param billPaymentQueryDto
	 */
	public void setBillPaymentQueryDto(BillPaymentQueryDto billPaymentQueryDto) {
		this.billPaymentQueryDto = billPaymentQueryDto;
	}

	
	/**
	 * @return billPaymentResultDto
	 */
	public BillPaymentResultDto getBillPaymentResultDto() {
		return billPaymentResultDto;
	}

	
	/**
	 * @param billPaymentResultDto
	 */
	public void setBillPaymentResultDto(BillPaymentResultDto billPaymentResultDto) {
		this.billPaymentResultDto = billPaymentResultDto;
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

	
	/**
	 * @return billNos
	 */
	public String[] getBillNos() {
		return billNos;
	}

	
	/**
	 * @param billNos
	 */
	public void setBillNos(String[] billNos) {
		this.billNos = billNos;
	}

	
	/**
	 * @return costcontrolDto
	 */
	public PayToCostcontrolDto getCostcontrolDto() {
		return costcontrolDto;
	}

	
	/**
	 * @param costcontrolDto
	 */
	public void setCostcontrolDto(PayToCostcontrolDto costcontrolDto) {
		this.costcontrolDto = costcontrolDto;
	}
	
	
}
