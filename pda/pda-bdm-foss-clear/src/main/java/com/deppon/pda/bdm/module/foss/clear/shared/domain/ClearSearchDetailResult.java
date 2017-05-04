package com.deppon.pda.bdm.module.foss.clear.shared.domain;

import java.math.BigDecimal;
/**
 * 找货明细实体  --返回
 * @author 245955
 *
 */
public class ClearSearchDetailResult {
	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 流水号
	 */
	private String serialNo;

	/**
	 * 丢货件数
	 */
	private String pieces;

	/**
	 * 任务号
	 */
	private String taskCode;
	
	/**
	 * 总件数
	 */
	private String totalQty;

	/**
	 * 重量
	 */
	private BigDecimal weight;

	/**
	 * 体积
	 */
	private BigDecimal volume;

	/**
	 * 包装
	 */
	private String packing;

	/**
	 * 提货网点
	 */
	//private String destOrgCode;
	/**
	 * 目的站
	 */
	private String zoneName;
	
	/**
	 * 当前货区
	 **/
	private  String goodsName;	
	
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getPacking() {
		return packing;
	}
	public void setPacking(String packing) {
		this.packing = packing;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public String getPieces() {
		return pieces;
	}
	public void setPieces(String pieces) {
		this.pieces = pieces;
	}
	public String getTotalQty() {
		return totalQty;
	}
	public void setTotalQty(String totalQty) {
		this.totalQty = totalQty;
	}

}
