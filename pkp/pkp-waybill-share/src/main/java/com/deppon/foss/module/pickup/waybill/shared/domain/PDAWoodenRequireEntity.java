package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
/**
 * 图片开单 包装明细
 * @author 076234 PanGuoYang
 */
public class PDAWoodenRequireEntity {
	private String id;
	// 运单编号
	private String waybillNo;
	//操作员工号
	private String operatorCode;
	//操作员所属外场
	private String outerCode;
	// 货物总体积
	private BigDecimal goodsVolumeTotal;
	// 货物总重量
	private BigDecimal goodsWeightTotal;
	// 是否大件上楼加收
	private String isBigUp;
	// 500KG到1000KG超重件数
	private Integer fhToOtOverQty;
	//1000KG到2000KG超重件数
	private Integer otToTtOverQty;
	//打木托件数
	private Integer woodenStockNum;
	// 件数
	private Integer goodsNum;
	//长
	private BigDecimal length ;
	
	//宽
	private BigDecimal  width;
	
	//高
	private BigDecimal  height ;
	
	//包装类型    stand=打木箱; box=打木架； noPack=没打木箱木架
	private String packType;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getOuterCode() {
		return outerCode;
	}
	public void setOuterCode(String outerCode) {
		this.outerCode = outerCode;
	}
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}
	public String getIsBigUp() {
		return isBigUp;
	}
	public void setIsBigUp(String isBigUp) {
		this.isBigUp = isBigUp;
	}
	public Integer getFhToOtOverQty() {
		return fhToOtOverQty;
	}
	public void setFhToOtOverQty(Integer fhToOtOverQty) {
		this.fhToOtOverQty = fhToOtOverQty;
	}
	public Integer getOtToTtOverQty() {
		return otToTtOverQty;
	}
	public void setOtToTtOverQty(Integer otToTtOverQty) {
		this.otToTtOverQty = otToTtOverQty;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	public BigDecimal getLength() {
		return length;
	}
	public void setLength(BigDecimal length) {
		this.length = length;
	}
	public BigDecimal getWidth() {
		return width;
	}
	public void setWidth(BigDecimal width) {
		this.width = width;
	}
	public BigDecimal getHeight() {
		return height;
	}
	public void setHeight(BigDecimal height) {
		this.height = height;
	}
	public String getPackType() {
		return packType;
	}
	public void setPackType(String packType) {
		this.packType = packType;
	}
	public Integer getWoodenStockNum() {
		return woodenStockNum;
	}
	public void setWoodenStockNum(Integer woodenStockNum) {
		this.woodenStockNum = woodenStockNum;
	} 
	
    	



}
