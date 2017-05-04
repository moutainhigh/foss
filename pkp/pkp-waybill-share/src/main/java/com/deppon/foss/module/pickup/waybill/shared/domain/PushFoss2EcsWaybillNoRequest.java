package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * FOSS将运单号传给ECS请求信息
 * @author 351326
 *
 */
public class PushFoss2EcsWaybillNoRequest implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//运单id
	//private String waybillID;
	
	//运单号
	private String waybillNo;
	
	//运单创建时间
	private Date billCreateTime;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Date getBillCreateTime() {
		return billCreateTime;
	}

	public void setBillCreateTime(Date billCreateTime) {
		this.billCreateTime = billCreateTime;
	}
	
	
}
