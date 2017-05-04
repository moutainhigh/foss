package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.io.Serializable;

public class RentalTempMarkEntity implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 费用承担部门ＣＯＤＥ
	 */
	private String feesDeptCode;
	/**
	 * 租车金额
	 */
	private String rentalAmount;
	/**
	 * 租车编码
	 */
	private String temprentalMarkNo;
	/**
	 * 约车编号
	 */
	private String inviteVehicleno;
	/**
	 * 租车标记用车时间
	 */
	private String useCarDate;
	
	/**
	 * set\get
	 * @return
	 */
	public String getFeesDeptCode() {
		return feesDeptCode;
	}
	public void setFeesDeptCode(String feesDeptCode) {
		this.feesDeptCode = feesDeptCode;
	}
	public String getRentalAmount() {
		return rentalAmount;
	}
	public void setRentalAmount(String rentalAmount) {
		this.rentalAmount = rentalAmount;
	}
	public String getTemprentalMarkNo() {
		return temprentalMarkNo;
	}
	public void setTemprentalMarkNo(String temprentalMarkNo) {
		this.temprentalMarkNo = temprentalMarkNo;
	}
	public String getInviteVehicleno() {
		return inviteVehicleno;
	}
	public void setInviteVehicleno(String inviteVehicleno) {
		this.inviteVehicleno = inviteVehicleno;
	}
	public String getUseCarDate() {
		return useCarDate;
	}
	public void setUseCarDate(String useCarDate) {
		this.useCarDate = useCarDate;
	}
	/**
	 * toString()
	 */
	@Override
	public String toString() {
		return "RentalTempMarkEntity [feesDeptCode=" + feesDeptCode
				+ ", rentalAmount=" + rentalAmount + ", temprentalMarkNo="
				+ temprentalMarkNo + ", inviteVehicleno=" + inviteVehicleno
				+ ", useCarDate=" + useCarDate + "]";
	}
}
