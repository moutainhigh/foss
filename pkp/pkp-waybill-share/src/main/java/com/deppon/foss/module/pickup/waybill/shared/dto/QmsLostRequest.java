package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/**
 * 
 * 查询QMS差错请求参数
 * 
 * @author huangwei
 * @date 2015-11-14 上午9:08:55
 */
public class QmsLostRequest implements Serializable {

	private static final long serialVersionUID = -38678678678768763L;

	/**
	 * 差错编码
	 */
	private String lostRepCode;

	/**
	 * 运单号
	 */
	private String wayBillNo;

	public String getLostRepCode() {
		return lostRepCode;
	}

	public void setLostRepCode(String lostRepCode) {
		this.lostRepCode = lostRepCode;
	}

	public String getWayBillNo() {
		return wayBillNo;
	}

	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	
}