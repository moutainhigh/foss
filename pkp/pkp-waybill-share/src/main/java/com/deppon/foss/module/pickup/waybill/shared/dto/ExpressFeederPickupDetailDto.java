package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 接驳点下拉数据明细
 * @author Foss-105888-Zhangxingwang
 * @date 2015-4-23 11:13:19
 */
public class ExpressFeederPickupDetailDto implements Serializable {
	private static final long serialVersionUID = 1L;
	//快递单号
	private String waybillNo;
	
	//开单件数 
	private Integer goodsQtyTotal;
	
	//重量 
	private BigDecimal goodsWeightTotal;
	
	//体积 
	private BigDecimal goodsVolumeTotal;
	
	//目的站  
	private String customerPickupOrgCode;
	
	//产品类型
	private String productCode;
	
	//入库时间
	private Date stockTime;
	
	//流水号
	private String serialNo;
	
	// 库存
	private String stockOrgCode;
	
	//已扫状态 
	private String scanStatus;
	
	//扫描件数
	private Integer scanQtyTotal;
	
	//装载件数
	private Integer loadQtyTotal;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Date getStockTime() {
		return stockTime;
	}

	public void setStockTime(Date stockTime) {
		this.stockTime = stockTime;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getStockOrgCode() {
		return stockOrgCode;
	}

	public void setStockOrgCode(String stockOrgCode) {
		this.stockOrgCode = stockOrgCode;
	}

	public String getScanStatus() {
		return scanStatus;
	}

	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
	}

	public Integer getScanQtyTotal() {
		return scanQtyTotal;
	}

	public void setScanQtyTotal(Integer scanQtyTotal) {
		this.scanQtyTotal = scanQtyTotal;
	}

	public Integer getLoadQtyTotal() {
		return loadQtyTotal;
	}

	public void setLoadQtyTotal(Integer loadQtyTotal) {
		this.loadQtyTotal = loadQtyTotal;
	}
}