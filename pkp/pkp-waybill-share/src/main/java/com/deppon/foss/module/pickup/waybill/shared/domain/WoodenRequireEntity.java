package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
/**
 * 图片开单 包装明细
 * @author 076234 PanGuoYang
 */
public class WoodenRequireEntity {
	
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
	
	



}
