package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;

/**
 * 批量修改重量体积DTO（零担）
 * 
 */
public class LTLEWaybillChangeWeightDto implements Serializable {

	private static final long serialVersionUID = 120794498654297649L;

	//id
	private String id;
	
	//更改状态
	private String changeStatus;
	
	//订单号
	private String orderNo;
	
	//运单号
	private String waybillNo;
	
	//重量
	private BigDecimal weightChanged;
	
	//体积
	private BigDecimal volumeChanged;
	
	//总费用
	private BigDecimal totalFee;
	
	//运输性质
	private String productCode;
	
	//失败原因
	private String failReason;
	
	//导入时间
	private Date importTime;
	
	//导入开始时间
	private Date importStartTime;
	
	//导入结束时间
	private Date importEndTime;
	
	//发货客户编码
	private String deliverCustomerCode;

    //是否有效
    private String active;
    
    //导入部门
    private String importDeptCode;
    
    //开单部门
    private String createOrgName;

	//件数
    private String goodsQtyTotal;
    
    //子母件信息
    private List<WaybillRelateDetailEntity> waybillRelateDetailEntityList;
	
    
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(String changeStatus) {
		this.changeStatus = changeStatus;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public BigDecimal getWeightChanged() {
		return weightChanged;
	}

	public void setWeightChanged(BigDecimal weightChanged) {
		this.weightChanged = weightChanged;
	}

	public BigDecimal getVolumeChanged() {
		return volumeChanged;
	}

	public void setVolumeChanged(BigDecimal volumeChanged) {
		this.volumeChanged = volumeChanged;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public Date getImportStartTime() {
		return importStartTime;
	}

	public void setImportStartTime(Date importStartTime) {
		this.importStartTime = importStartTime;
	}

	public Date getImportEndTime() {
		return importEndTime;
	}

	public void setImportEndTime(Date importEndTime) {
		this.importEndTime = importEndTime;
	}

	public String getDeliverCustomerCode() {
		return deliverCustomerCode;
	}

	public void setDeliverCustomerCode(String deliverCustomerCode) {
		this.deliverCustomerCode = deliverCustomerCode;
	}

	public List<WaybillRelateDetailEntity> getWaybillRelateDetailEntityList() {
		return waybillRelateDetailEntityList;
	}

	public void setWaybillRelateDetailEntityList(
			List<WaybillRelateDetailEntity> waybillRelateDetailEntityList) {
		this.waybillRelateDetailEntityList = waybillRelateDetailEntityList;
	}
	
    public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getImportDeptCode() {
		return importDeptCode;
	}

	public void setImportDeptCode(String importDeptCode) {
		this.importDeptCode = importDeptCode;
	}

	public String getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(String goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	
	
}