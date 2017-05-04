
package com.deppon.foss.module.pickup.waybill.shared.domain;


/**
 * 流水号所在货区
 * 
 * 
 */
public class flowcodeAreaForQms {
	private String loseFlowcode;
	private String GoodsAreaCode;
	private String GoodsAreaName;
	public String getLoseFlowcode() {
		return loseFlowcode;
	}
	public void setLoseFlowcode(String loseFlowcode) {
		this.loseFlowcode = loseFlowcode;
	}
	public String getGoodsAreaCode() {
		return GoodsAreaCode;
	}
	public void setGoodsAreaCode(String goodsAreaCode) {
		GoodsAreaCode = goodsAreaCode;
	}
	public String getGoodsAreaName() {
		return GoodsAreaName;
	}
	public void setGoodsAreaName(String goodsAreaName) {
		GoodsAreaName = goodsAreaName;
	}
}
