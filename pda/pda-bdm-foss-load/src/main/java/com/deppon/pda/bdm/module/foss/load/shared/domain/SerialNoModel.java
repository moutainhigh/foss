package com.deppon.pda.bdm.module.foss.load.shared.domain;

public class SerialNoModel {
	/**库区编码:装车时使用，卸车时为空*/
	private String stockAreaCode;
	/**是否未打包装*/
	private String isWrap;
	/**流水号*/
	private String serialNo;
	/**
	 * 是否贵重物品区
	 */
	private String isValArea;
	/**
	 * 是否未出包装货区
	 */
	private String isWrapArea;
	/**
	 * 是否更换标签
	 */
	private String isChgLabel;
	
	/**货物位置*/
	private String goodsPosition;
	
	public String getStockAreaCode() {
		return stockAreaCode;
	}
	public void setStockAreaCode(String stockAreaCode) {
		this.stockAreaCode = stockAreaCode;
	}
	public String getIsWrap() {
		return isWrap;
	}
	public void setIsWrap(String isWrap) {
		this.isWrap = isWrap;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getIsValArea() {
		return isValArea;
	}
	public void setIsValArea(String isValArea) {
		this.isValArea = isValArea;
	}
	public String getIsWrapArea() {
		return isWrapArea;
	}
	public void setIsWrapArea(String isWrapArea) {
		this.isWrapArea = isWrapArea;
	}
	public String getIsChgLabel() {
		return isChgLabel;
	}
	public void setIsChgLabel(String isChgLabel) {
		this.isChgLabel = isChgLabel;
	}
	public String getGoodsPosition() {
		return goodsPosition;
	}
	public void setGoodsPosition(String goodsPosition) {
		this.goodsPosition = goodsPosition;
	}
	
	
}
