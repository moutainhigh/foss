package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.OtHandOverBillDetailEntity;

public class OrderTaskAddnewDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//点单任务编号
	private String orderTaskNo;
	
	//车牌号
	private String vehicleNo;

	//点单人code
	private String orderCode;
	
	//点单人name
	private String orderName;
	
	//总票数
	private Long totWaybillQty;
	
	//总件数
	private Long totGoodsQty;
	
	//总重量
	private BigDecimal totWeight;
	
	//总体积
	private BigDecimal totVolume;

	//创建部门
	private String createOrgCode;
	
	//创建部门名称
	private String createOrgName;
		
	//添加的单据列表
	private List<OtHandOverBillDetailEntity> billList;
	
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public List<OtHandOverBillDetailEntity> getBillList() {
		return billList;
	}
	public void setBillList(List<OtHandOverBillDetailEntity> billList) {
		this.billList = billList;
	}
	public Long getTotWaybillQty() {
		return totWaybillQty;
	}
	public void setTotWaybillQty(Long totWaybillQty) {
		this.totWaybillQty = totWaybillQty;
	}
	public Long getTotGoodsQty() {
		return totGoodsQty;
	}
	public void setTotGoodsQty(Long totGoodsQty) {
		this.totGoodsQty = totGoodsQty;
	}
	public BigDecimal getTotWeight() {
		return totWeight;
	}
	public void setTotWeight(BigDecimal totWeight) {
		this.totWeight = totWeight;
	}
	public BigDecimal getTotVolume() {
		return totVolume;
	}
	public void setTotVolume(BigDecimal totVolume) {
		this.totVolume = totVolume;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getOrderTaskNo() {
		return orderTaskNo;
	}
	public void setOrderTaskNo(String orderTaskNo) {
		this.orderTaskNo = orderTaskNo;
	}
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	public String getCreateOrgName() {
		return createOrgName;
	}
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	
}
