package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OtHandOverBillDetailEntity;


public class OrderTaskModifyDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//车牌号
	private String vehicleNo;
	
	//点单任务编号
	private String orderTaskNo;
	
	
	//修改的流水号明细list
	private List<OrderSerialNoDetailEntity> addedBillList;
	
	//修改的流水号明细list
	private List<OtHandOverBillDetailEntity> addedBillDetailList;
	
	//添加的单据
	private List<OtHandOverBillDetailEntity> addedMoreBillList;

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getOrderTaskNo() {
		return orderTaskNo;
	}

	public void setOrderTaskNo(String orderTaskNo) {
		this.orderTaskNo = orderTaskNo;
	}

	public List<OrderSerialNoDetailEntity> getAddedBillList() {
		return addedBillList;
	}

	public void setAddedBillList(List<OrderSerialNoDetailEntity> addedBillList) {
		this.addedBillList = addedBillList;
	}

	public List<OtHandOverBillDetailEntity> getAddedMoreBillList() {
		return addedMoreBillList;
	}

	public void setAddedMoreBillList(List<OtHandOverBillDetailEntity> addedMoreBillList) {
		this.addedMoreBillList = addedMoreBillList;
	}

	public List<OtHandOverBillDetailEntity> getAddedBillDetailList() {
		return addedBillDetailList;
	}

	public void setAddedBillDetailList(List<OtHandOverBillDetailEntity> addedBillDetailList) {
		this.addedBillDetailList = addedBillDetailList;
	}


	
}
