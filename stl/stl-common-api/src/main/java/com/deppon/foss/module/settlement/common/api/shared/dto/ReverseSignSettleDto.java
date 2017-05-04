package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;

/** 
 * 反签收签收反结清DTO
 * 
 * @author218392 张永雪
 * @date 2016-06-12 11:40：00
 * @since
 * @version
 */
public class ReverseSignSettleDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 是否反签收  Y-是；N-否
	 * @return
	 */
	private String isReverseSign;
	
	
	/**
	 * 是否反结清  Y-是；N-否
	 * @return
	 */
	private String isReverseSettle;
	
	/**
	 * 单号
	 */
	private String waybillNo;
	

	
	
	
	

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getIsReverseSign() {
		return isReverseSign;
	}

	public void setIsReverseSign(String isReverseSign) {
		this.isReverseSign = isReverseSign;
	}

	public String getIsReverseSettle() {
		return isReverseSettle;
	}

	public void setIsReverseSettle(String isReverseSettle) {
		this.isReverseSettle = isReverseSettle;
	}
	
}
