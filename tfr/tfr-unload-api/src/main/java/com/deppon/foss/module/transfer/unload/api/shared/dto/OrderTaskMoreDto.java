package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OtHandOverBillDetailEntity;

public class OrderTaskMoreDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//点单任务编号
	private String orderTaskNo;
	
	//总票数
	private Long totWaybillQty;
	
	private List<OrderSerialNoDetailEntity>  moreSerialNoList;
	
	private OtHandOverBillDetailEntity moreBillInfo;

	public String getOrderTaskNo() {
		return orderTaskNo;
	}

	public void setOrderTaskNo(String orderTaskNo) {
		this.orderTaskNo = orderTaskNo;
	}

	public List<OrderSerialNoDetailEntity> getMoreSerialNoList() {
		return moreSerialNoList;
	}

	public void setMoreSerialNoList(List<OrderSerialNoDetailEntity> moreSerialNoList) {
		this.moreSerialNoList = moreSerialNoList;
	}

	public OtHandOverBillDetailEntity getMoreBillInfo() {
		return moreBillInfo;
	}

	public void setMoreBillInfo(OtHandOverBillDetailEntity moreBillInfo) {
		this.moreBillInfo = moreBillInfo;
	}
	
	public Long getTotWaybillQty() {
		return totWaybillQty;
	}

	public void setTotWaybillQty(Long totWaybillQty) {
		this.totWaybillQty = totWaybillQty;
	}

}
