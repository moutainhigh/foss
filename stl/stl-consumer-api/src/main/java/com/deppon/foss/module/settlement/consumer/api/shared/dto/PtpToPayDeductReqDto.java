package com.deppon.foss.module.settlement.consumer.api.shared.dto;

/**
 * 合伙人到付金额扣款请求参数
 * @author gpz
 * @date 2016年9月21日
 */
public class PtpToPayDeductReqDto {

	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 合伙人部门（标杆编码）
	 */
	private String partnerOrgCode;
	
	/**
	 * 操作人编码
	 */
	private String operatorCode;
	
	/**
	 * 操作人姓名
	 */
	private String operatorName;

	/**
	 * @return the waybillNo
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
	 * @return the partnerOrgCode
	 */
	public String getPartnerOrgCode() {
		return partnerOrgCode;
	}

	/**
	 * @param partnerOrgCode the partnerOrgCode to set
	 */
	public void setPartnerOrgCode(String partnerOrgCode) {
		this.partnerOrgCode = partnerOrgCode;
	}

	/**
	 * @return the operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * @param operatorCode the operatorCode to set
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * @param operatorName the operatorName to set
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
}
