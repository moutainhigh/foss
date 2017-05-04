package com.deppon.foss.module.transfer.partialline.api.shared.domain;


public class SignInfoAuditResponse {
	//运单号
	private String waybillNo;
	
	//返回是否成功的标志  true ,false
	private boolean beSuccess ;
	//失败原因 
	private String failureReason;
	//返回类型，接口类型 
	private String returnType;
	
	/**
	 * @return the beSuccess
	 */
	public boolean isBeSuccess() {
		return beSuccess;
	}
	/**
	 * @param beSuccess the beSuccess to set
	 */
	public void setBeSuccess(boolean beSuccess) {
		this.beSuccess = beSuccess;
	}
	/**
	 * @return the failureReason
	 */
	public String getFailureReason() {
		return failureReason;
	}
	/**
	 * @param failureReason the failureReason to set
	 */
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
	/**
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}
	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
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
	
}
