package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;

/**
 * 还款单查询Dto
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-11-19 下午6:54:57
 * @since
 * @version
 */
public class BillRepaymentConditionDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6614993881633325513L;

	/**
	 * 还款单号
	 */
	private String repaymentNo;

	/**
	 * 存放(外发单号等)
	 */
	private String sourceBillNo;
	
	/**
	 * 运单号，只有在实收货款和签收地方使用查询使用，其他地方如果使用请联系 wujt
	 */
	private String waybillNo;

	/**
	 * 在线支付单号
	 */
	private String onlinePaymentNo;

	/**
	 * 单据类型集合
	 */
	private String[] billTypes;

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
	 * 还款部门编码
	 */
	private String orgCode;

	/**
	 * 来源单据类型集合
	 */
	private String[] sourceBillTypes;
	
	/**
	 * 还款单状态
	 */
	private String status;

	public BillRepaymentConditionDto() {
	}

	public BillRepaymentConditionDto(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	/**
	 * @return repaymentNo
	 */
	public String getRepaymentNo() {
		return repaymentNo;
	}

	/**
	 * @param repaymentNo
	 */
	public void setRepaymentNo(String repaymentNo) {
		this.repaymentNo = repaymentNo;
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
	 * @return onlinePaymentNo
	 */
	public String getOnlinePaymentNo() {
		return onlinePaymentNo;
	}

	/**
	 * @param onlinePaymentNo
	 */
	public void setOnlinePaymentNo(String onlinePaymentNo) {
		this.onlinePaymentNo = onlinePaymentNo;
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
	 * @return sourceBillTypes
	 */
	public String[] getSourceBillTypes() {
		return sourceBillTypes;
	}

	/**
	 * @param sourceBillTypes
	 */
	public void setSourceBillTypes(String[] sourceBillTypes) {
		this.sourceBillTypes = sourceBillTypes;
	}

	
	/**
	 * @return  the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	/**
	 * @return  the status
	 */
	public String getStatus() {
		return status;
	}

	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
