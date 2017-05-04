package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 分拣扫描结果与库存数据 比对dto,用于grid显示
 * @author 200978  xiaobingcheng
 * 2014-8-31
 */
public class SortingScanCompareDto {

	/*运单号*/
	private String waybillNo;
	/*货物区域编号*/
	private String goodsAreaCode;
	/*货物区域名称*/
	private String goodsAreaName;
	/*库存件数*/
	private BigDecimal stockGoodsQty;
	/*开单件数*/
	private BigDecimal goodsQtyTotal;
	/*开单时间*/
	private Date billTime;
	/*入库时间*/
	private Date inStockTime;
	/*运输性质编码*/
	private String productCode;
	/*运输性质名称*/
	private String productName;
	/*提货方式*/
	private String receiveMethod;
	/*货物重量*/
	private BigDecimal goodsWeightTotal;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	public String getGoodsAreaName() {
		return goodsAreaName;
	}

	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}

	public BigDecimal getStockGoodsQty() {
		return stockGoodsQty;
	}

	public void setStockGoodsQty(BigDecimal stockGoodsQty) {
		this.stockGoodsQty = stockGoodsQty;
	}

	public BigDecimal getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(BigDecimal goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public Date getInStockTime() {
		return inStockTime;
	}

	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	@Override
	public String toString() {
		return "SortingScanCompareDto [waybillNo=" + waybillNo
				+ ", goodsAreaCode=" + goodsAreaCode + ", goodsAreaName="
				+ goodsAreaName + ", stockGoodsQty=" + stockGoodsQty
				+ ", goodsQtyTotal=" + goodsQtyTotal + ", billTime=" + billTime
				+ ", inStockTime=" + inStockTime + ", productCode="
				+ productCode + ", productName=" + productName
				+ ", receiveMethod=" + receiveMethod + ", goodsWeightTotal="
				+ goodsWeightTotal + "]";
	}
	
	
	
}
