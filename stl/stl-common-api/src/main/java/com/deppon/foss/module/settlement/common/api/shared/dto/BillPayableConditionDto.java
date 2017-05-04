package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;

/**
 * 存放应付单查询过滤条件 dto
 * 
 * @author ibm-zhuwei
 * @date 2012-10-23 下午1:18:54
 */
public class BillPayableConditionDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2167823981170808947L;

	/**
	 * 应收单号
	 */
	private String payableNo;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 来源单据单号
	 */
	private String sourceBillNo;

	/**
	 * 来源单据类型
	 */
	private String sourceBillType;

	/**
	 * 单据类型集合
	 */
	private String[] billTypes;

	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 是否核销
	 */
	private String verify;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 是否红单
	 */
	private String isRedBack;

	/**
	 * 外发单号
	 */
	private String externalBillNo;

	/**
	 * 外发代理编码
	 */
	private String partailLineAgentCode;

	/**
	 * 付款单号
	 */
	private String paymentNo;

	/**
	 * 构造函数
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-10-24 下午6:34:56
	 */
	public BillPayableConditionDto() {

	}

	/**
	 * 构造函数
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-10-24 下午6:34:58
	 */
	public BillPayableConditionDto(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return payableNo
	 */
	public String getPayableNo() {
		return payableNo;
	}

	/**
	 * @param payableNo
	 */
	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
	}

	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return sourceBillNo
	 */
	public String getSourceBillNo() {
		return sourceBillNo;
	}

	/**
	 * @param sourceBillNo
	 */
	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	/**
	 * @return sourceBillType
	 */
	public String getSourceBillType() {
		return sourceBillType;
	}

	/**
	 * @param sourceBillType
	 */
	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}

	/**
	 * @return billTypes
	 */
	public String[] getBillTypes() {
		return billTypes;
	}

	/**
	 * @param billTypes
	 */
	public void setBillTypes(String[] billTypes) {
		this.billTypes = billTypes;
	}

	/**
	 * @return orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return verify
	 */
	public String getVerify() {
		return verify;
	}

	/**
	 * @param verify
	 */
	public void setVerify(String verify) {
		this.verify = verify;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
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
	 * @param isRedBack
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	/**
	 * @return externalBillNo
	 */
	public String getExternalBillNo() {
		return externalBillNo;
	}

	/**
	 * @param externalBillNo
	 */
	public void setExternalBillNo(String externalBillNo) {
		this.externalBillNo = externalBillNo;
	}

	/**
	 * @return partailLineAgentCode
	 */
	public String getPartailLineAgentCode() {
		return partailLineAgentCode;
	}

	/**
	 * @param partailLineAgentCode
	 */
	public void setPartailLineAgentCode(String partailLineAgentCode) {
		this.partailLineAgentCode = partailLineAgentCode;
	}

	/**
	 * @return paymentNo
	 */
	public String getPaymentNo() {
		return paymentNo;
	}

	/**
	 * @param paymentNo
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}


}
