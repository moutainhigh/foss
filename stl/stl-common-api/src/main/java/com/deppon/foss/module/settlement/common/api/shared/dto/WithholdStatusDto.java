package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;

/**
 * 更新PTP扣款状态DTO
 * 
 * @author 269044-zhurongrong
 * @date 2016-12-23
 */
public class WithholdStatusDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 单据子类型
	 */
	private String billType;
	/**
	 * 到达部门编码
	 */
	private String destOrgCode;
	/**
	 * 到达部门名称
	 */
	private String destOrgName;
	/**
	 * 场景 online-网上支付，disable-作废还款单
	 */
	private String scene;
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getDestOrgCode() {
		return destOrgCode;
	}
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	public String getDestOrgName() {
		return destOrgName;
	}
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	public String getScene() {
		return scene;
	}
	public void setScene(String scene) {
		this.scene = scene;
	}
	
	
}
