package com.deppon.foss.module.transfer.load.api.shared.dto;
/**
 *查询租车标记状态和付款状态 
 * 
 ***/
public class HoldingStateDto {

	private String accruedState;//预提状态
	private String tempRentalMarkNo;//租车编号
	public String getAccruedState() {
		return accruedState;
	}
	public void setAccruedState(String accruedState) {
		this.accruedState = accruedState;
	}
	public String getTempRentalMarkNo() {
		return tempRentalMarkNo;
	}
	public void setTempRentalMarkNo(String tempRentalMarkNo) {
		this.tempRentalMarkNo = tempRentalMarkNo;
	}
	
	
	
}
