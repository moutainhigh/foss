package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 发货清单，请求参数VO
 * @author 272311
 *
 */
public class DeliverGoodsListQueryVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5511859882059628826L;

	/**
	 * 开始查询时间
	 */
	private Date startTime ;
	
	/**
	 * 结束查询时间
	 */
	private Date endTime ;
	
	/**
	 * 按月查询 开始时间
	 */
	private Date beginMonthTime ;
	
	/**
	 * 按月查询 结束时间
	 */
	private Date endMonthTime ;
	
	/**
	 * 运单号码
	 */
	private List<String> waybillNoList ;
	
	/**
	 * 货物状态
	 */
	private String goodsStatus ;
	
	/**
	 * 客户编码
	 */
	private String deliveryCustomerCode ;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	public List<String> getWaybillNoList() {
		return waybillNoList;
	}

	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public Date getBeginMonthTime() {
		return beginMonthTime;
	}

	public void setBeginMonthTime(Date beginMonthTime) {
		this.beginMonthTime = beginMonthTime;
	}

	public Date getEndMonthTime() {
		return endMonthTime;
	}

	public void setEndMonthTime(Date endMonthTime) {
		this.endMonthTime = endMonthTime;
	}

	@Override
	public String toString() {
		return "DeliverGoodsListQueryVo [startTime=" + startTime + ", endTime="
				+ endTime + ", beginMonthTime=" + beginMonthTime
				+ ", endMonthTime=" + endMonthTime + ", goodsStatus=" + goodsStatus
				+ ", deliveryCustomerCode=" + deliveryCustomerCode + "]";
	}
	
	
}
