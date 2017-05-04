package com.deppon.pda.bdm.module.foss.load.shared.domain.driverload;

import java.math.BigDecimal;


public class GetDetailResult {
	// 快递单号
	private String waybillNo;

	// 开单件数
	private Integer goodsQtyTotal;
	// 扫描件数
	private Integer scanQtyTotal;

	// 重量
	private BigDecimal goodsWeightTotal;

	// 体积
	private BigDecimal goodsVolumeTotal;

	// 目的站
	//private String customerPickupOrgCode;

	// 产品类型
	private String productCode;

	// 入库时间
	//private Date stockTime;

	// 流水号
	//private String serialNo;

	// 库存
	//private String stockOrgCode;

	// 已扫状态
	private String scanStatus;

	// 装载件数
	//private Integer loadQtyTotal;

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
    
	public Integer getScanQtyTotal() {
		return scanQtyTotal;
	}

	public void setScanQtyTotal(Integer scanQtyTotal) {
		this.scanQtyTotal = scanQtyTotal;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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

	public String getScanStatus() {
		return scanStatus;
	}

	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
	}
    
}
