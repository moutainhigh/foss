package com.deppon.foss.module.transfer.edi.server.domain;

public class UpdateAirPickByOppResponse {

	//运单号
	private String waybillNo;
	//返回是否成功的标志  true ,false
	private boolean beSuccess ;
	//失败原因 
	private String failureReason;
	//返回类型，接口类型 
	private String returnType;
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public boolean isBeSuccess() {
		return beSuccess;
	}
	public void setBeSuccess(boolean beSuccess) {
		this.beSuccess = beSuccess;
	}
	public String getFailureReason() {
		return failureReason;
	}
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
}
