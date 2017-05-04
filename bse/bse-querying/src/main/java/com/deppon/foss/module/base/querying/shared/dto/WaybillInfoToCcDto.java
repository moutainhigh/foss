package com.deppon.foss.module.base.querying.shared.dto;
/**
 * 运单信息 推送给CC
 * @author 130566
 *
 */
public class WaybillInfoToCcDto {
	/**
	 * 运单编号
	 */
	private String waybillNo;
	/**
	 * 运单结果信息
	 */
	private int waybillResult;
	/**
	 * 电话记录单号
	 */
	private String callRecordNo;
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public int getWaybillResult() {
		return waybillResult;
	}
	public void setWaybillResult(int waybillResult) {
		this.waybillResult = waybillResult;
	}
	public String getCallRecordNo() {
		return callRecordNo;
	}
	public void setCallRecordNo(String callRecordNo) {
		this.callRecordNo = callRecordNo;
	}
	
	
}
