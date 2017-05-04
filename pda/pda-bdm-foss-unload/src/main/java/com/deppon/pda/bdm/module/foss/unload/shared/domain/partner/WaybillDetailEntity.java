package com.deppon.pda.bdm.module.foss.unload.shared.domain.partner;

import java.io.Serializable;
/**
 * 
 * @ClassName: WaybillDetailEntity 
 * @Description: TODO(运单明细接收参数实体) 
 * @author &268974  wangzhili
 * @date 2016-1-27 上午10:18:20 
 *
 */
public class WaybillDetailEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	//运单号
	private String waybillNo;
	//任务编号
	private String orderTaskNo;
	//运单明细id
	private String waybillDetailID;
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getOrderTaskNo() {
		return orderTaskNo;
	}
	public void setOrderTaskNo(String orderTaskNo) {
		this.orderTaskNo = orderTaskNo;
	}
	public String getWaybillDetailID() {
		return waybillDetailID;
	}
	public void setWaybillDetailID(String waybillDetailID) {
		this.waybillDetailID = waybillDetailID;
	}

	
}
