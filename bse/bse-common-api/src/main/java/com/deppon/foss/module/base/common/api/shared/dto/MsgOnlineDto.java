package com.deppon.foss.module.base.common.api.shared.dto;

import com.deppon.foss.module.base.common.api.shared.domain.MsgOnlineEntity;

public class MsgOnlineDto extends MsgOnlineEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8788247979639140659L;
	/**
	 * 运单号
	 */
	private String billNo;
	/**
	 * 运单出发部门
	 */
	private String billSendOrgCode;
	/**
	 * 运单出发部门名称
	 */
	private String billSendOrgName;
	
	/**
	 * 正单号
	 */
	private String airBillNo;
	/**
	 * 交接单号
	 * @return
	 */
	private String handOverBillNo;
	
	private String queryHandOverType;
	private String airBillType;
	
	public String getQueryHandOverType() {
		return queryHandOverType;
	}
	public void setQueryHandOverType(String queryHandOverType) {
		this.queryHandOverType = queryHandOverType;
	}
	public String getAirBillType() {
		return airBillType;
	}
	public void setAirBillType(String airBillType) {
		this.airBillType = airBillType;
	}
	public String getBillSendOrgCode() {
		return billSendOrgCode;
	}
	public void setBillSendOrgCode(String billSendOrgCode) {
		this.billSendOrgCode = billSendOrgCode;
	}
	public String getBillSendOrgName() {
		return billSendOrgName;
	}
	public void setBillSendOrgName(String billSendOrgName) {
		this.billSendOrgName = billSendOrgName;
	}
	public String getAirBillNo() {
		return airBillNo;
	}
	public void setAirBillNo(String airBillNo) {
		this.airBillNo = airBillNo;
	}
	public String getHandOverBillNo() {
		return handOverBillNo;
	}
	public void setHandOverBillNo(String handOverBillNo) {
		this.handOverBillNo = handOverBillNo;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	
	
}
