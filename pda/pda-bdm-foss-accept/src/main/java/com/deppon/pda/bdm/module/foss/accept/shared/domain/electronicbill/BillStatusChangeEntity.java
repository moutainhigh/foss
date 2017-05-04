package com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill;

import java.util.Date;
import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

public class BillStatusChangeEntity   extends ScanMsgEntity{
	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 车牌号
	 */
	private String truckCode;

	/**
	 * 订单编号/约车编号
	 */
	private List<String> orderCodes;
	/**
	 * 备注
	 */
	private String retreatRemark;

	/**
	 * 退回原因
	 */
	private String retreatReason;
	
	/**
	 * 退回时间
	 */
	private Date backTime;

	public String getTruckCode() {
		return truckCode;
	}

	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

	
	public List<String> getOrderCodes() {
		return orderCodes;
	}

	public void setOrderCodes(List<String> orderCodes) {
		this.orderCodes = orderCodes;
	}


	public String getRetreatRemark() {
		return retreatRemark;
	}

	public void setRetreatRemark(String retreatRemark) {
		this.retreatRemark = retreatRemark;
	}

	public String getRetreatReason() {
		return retreatReason;
	}

	public void setRetreatReason(String retreatReason) {
		this.retreatReason = retreatReason;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}

	
}
