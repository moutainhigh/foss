package com.deppon.foss.module.pickup.sign.api.shared.dto;
/** 
 * 
 * @author foss结算-306579-guoxinru 
 * @date 2016-5-12 上午9:25:05    
 */
public class VtsQueryDto {

	/**
	 * 付款dto
	 */
	private RepaymentDto repaymentDto;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 成功失败标识
	 */
	private boolean isSuccess;
	
	/**
	 * 消息
	 */
	private String msg;

	public RepaymentDto getRepaymentDto() {
		return repaymentDto;
	}

	public void setRepaymentDto(RepaymentDto repaymentDto) {
		this.repaymentDto = repaymentDto;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
