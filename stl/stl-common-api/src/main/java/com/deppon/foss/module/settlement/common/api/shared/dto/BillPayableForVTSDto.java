package com.deppon.foss.module.settlement.common.api.shared.dto;

/**
 * 
 * @author 331556 fanjingwei
 * @date 2016-05-19
 */
public class BillPayableForVTSDto {
	
	/**
	 * 运单号
	 */
	private String wayBillNo;
	/**
	 * 支付状态
	 */
	private String payStatus;
	/**
	 * 是否成功
	 */
	private boolean isSuccess;
	

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * 消息
	 */
	private String msg;
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getWayBillNo() {
		return wayBillNo;
	}

	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	

}
