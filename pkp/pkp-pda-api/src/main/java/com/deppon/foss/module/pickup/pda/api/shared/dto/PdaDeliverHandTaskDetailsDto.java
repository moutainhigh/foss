package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/**
 * Pda 查询快递派件交接任务明细DTO
 * @author 243921-foss-zhangtingitng
 * @date 2015-04-15 上午10:36:01
 */
public class PdaDeliverHandTaskDetailsDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  派送单号
	 */
	private String deliverbillNo;
	/**
	 *  运单号
	 */
	private String waybillNo;
	/**
	 *  到达联编号
	 */
//	private String arriveSheetNo;
	/**
	 *  流水号列表
	 */
	private List<String> serialNum;
	/**
	 *  派送日期
	 */
//	private Date submitTime;
	/**
	 * 总件数（派送单总件数）
	 */
	private Integer goodsQtyTotal;
	/**
	 *  件数 （到达联件数）
	 */
	private Integer arriveSheetGoodsQty;
	/**
	 *  重量（单位：千克）（派送单明细）
	 */
	private BigDecimal weight;
	/**
	 *  体积（派送单明细）
	 */
	private BigDecimal volume;
	/** 
	 * 是否贵重物品 （运单）
	 */
//	private String preciousGoods;
	/**
	 * 产品编号
	 */
//	private String productCode;
	/**
	 * 运输性质名称
	 */
	private String productName;
	/**
	 * 已扫件数
	 */
	private Integer scanQty;
	/**
	 * 已装车件数
	 */
//	private Integer loadQty;
	/**
	 * 扫描状态
	 */
	private String scanState;
	
	/*public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}*/
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDeliverbillNo() {
		return deliverbillNo;
	}
	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/*public String getArriveSheetNo() {
		return arriveSheetNo;
	}
	public void setArriveSheetNo(String arriveSheetNo) {
		this.arriveSheetNo = arriveSheetNo;
	}*/
	public List<String> getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(List<String> serialNum) {
		this.serialNum = serialNum;
	}
	public Integer getArriveSheetGoodsQty() {
		return arriveSheetGoodsQty;
	}
	public void setArriveSheetGoodsQty(Integer arriveSheetGoodsQty) {
		this.arriveSheetGoodsQty = arriveSheetGoodsQty;
	}
	public Integer getScanQty() {
		return scanQty;
	}
	public void setScanQty(Integer scanQty) {
		this.scanQty = scanQty;
	}
	/*public Integer getLoadQty() {
		return loadQty;
	}
	public void setLoadQty(Integer loadQty) {
		this.loadQty = loadQty;
	}*/
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	/*public String getPreciousGoods() {
		return preciousGoods;
	}
	public void setPreciousGoods(String preciousGoods) {
		this.preciousGoods = preciousGoods;
	}*/
	public String getScanState() {
		return scanState;
	}
	public void setScanState(String scanState) {
		this.scanState = scanState;
	}
	
}
