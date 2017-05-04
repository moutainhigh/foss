package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;

/**
 * 付款单查询dto
 * @author 099995-foss-wujiangtao
 * @date 2012-10-25 上午10:31:30
 * @since
 * @version
 */
public class BillPaymentConditionDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5747723136117637864L;

	/**
	 * 付款单号
	 */
	private String paymentNo;

	/**
	 * 来源单据单号
	 */
	private String sourceBillNo;
	
	/**
	 * 单据类型
	 */
	private String[] billTypes;

	/**
	 * 来源单据类型
	 */
	private String sourceBillType;

	/**
	 * 付款部门编码
	 */
	private String paymentOrgCode;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 是否红单
	 */
	private String isRedBack;
	
	/**
	 * 外发代理编码
	 */
	private String partailLineAgentCode;
	
	/**
	 * 外发单号
	 */
	private String externalBillNo;
	
	
	
	public BillPaymentConditionDto(){}
	
	public BillPaymentConditionDto(String sourceBillNo){
		this.sourceBillNo=sourceBillNo;
	}

	
	/**
	 * @return paymentNo
	 */
	public String getPaymentNo() {
		return paymentNo;
	}

	
	/**
	 * @param  paymentNo  
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	
	/**
	 * @return sourceBillNo
	 */
	public String getSourceBillNo() {
		return sourceBillNo;
	}

	
	/**
	 * @param  sourceBillNo  
	 */
	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	
	/**
	 * @return billTypes
	 */
	public String[] getBillTypes() {
		return billTypes;
	}

	
	/**
	 * @param  billTypes  
	 */
	public void setBillTypes(String[] billTypes) {
		this.billTypes = billTypes;
	}

	
	/**
	 * @return sourceBillType
	 */
	public String getSourceBillType() {
		return sourceBillType;
	}

	
	/**
	 * @param  sourceBillType  
	 */
	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}

	
	/**
	 * @return paymentOrgCode
	 */
	public String getPaymentOrgCode() {
		return paymentOrgCode;
	}

	
	/**
	 * @param  paymentOrgCode  
	 */
	public void setPaymentOrgCode(String paymentOrgCode) {
		this.paymentOrgCode = paymentOrgCode;
	}

	
	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	
	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}

	
	/**
	 * @return isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	
	/**
	 * @param  isRedBack  
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	
	/**
	 * @return partailLineAgentCode
	 */
	public String getPartailLineAgentCode() {
		return partailLineAgentCode;
	}

	
	/**
	 * @param  partailLineAgentCode  
	 */
	public void setPartailLineAgentCode(String partailLineAgentCode) {
		this.partailLineAgentCode = partailLineAgentCode;
	}

	
	/**
	 * @return externalBillNo
	 */
	public String getExternalBillNo() {
		return externalBillNo;
	}

	
	/**
	 * @param  externalBillNo  
	 */
	public void setExternalBillNo(String externalBillNo) {
		this.externalBillNo = externalBillNo;
	}

	

}
