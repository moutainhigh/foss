package com.deppon.foss.module.pickup.changingexp.client.vo;

import java.math.BigDecimal;

/**
 * 转寄退回 用于存放临时改变的值
 * @author 272311-sangwenhao
 * @date 2016-3-1
 */
public class WaybillInfoTempVo {
	
	/**
	 * 中转费
	 */
	private BigDecimal transferFee ;
	
	/**
	 * 标示是否同一个行政区域
	 */
	private boolean isSameProv = false ;
	
	/**
	 * 签收单回单费用
	 */
	private BigDecimal returnBillFee ;
	
	/**
	 * 标准快递首重价格（临时）
	 */
	private BigDecimal standExpFirstFee ;

	public boolean isSameProv() {
		return isSameProv;
	}

	public void setSameProv(boolean isSameProv) {
		this.isSameProv = isSameProv;
	}

	public BigDecimal getTransferFee() {
		return transferFee;
	}

	public void setTransferFee(BigDecimal transferFee) {
		this.transferFee = transferFee;
	}

	public BigDecimal getReturnBillFee() {
		return returnBillFee;
	}

	public void setReturnBillFee(BigDecimal returnBillFee) {
		this.returnBillFee = returnBillFee;
	}

	public BigDecimal getStandExpFirstFee() {
		return standExpFirstFee;
	}

	public void setStandExpFirstFee(BigDecimal standExpFirstFee) {
		this.standExpFirstFee = standExpFirstFee;
	}

}
