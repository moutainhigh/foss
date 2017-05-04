package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.io.Serializable;
import java.util.List;
/**
 * 查询快递派件交接任务查询条件DTO
 * @author 243921-foss-zhangtingting
 * @date 2015-04-15 上午10:04:09
 */
public class PdaDeliverHandTaskConditionDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 派送单号
	 */
	private String deliverbillNo;
	/**
	 * 派送单状态
	 */
	private List<String> deliverbillStatus;
	/**
	 *  司机编号
	 */
	private String driverCode;
	/**
	 *  车牌号
	 */
	private String vehicleNo;
	/**
	 * 任务号
	 */
	private String taskNo;
	/**
	 *  到达联是否有效
	 */
	private String arriveSheetActive;
	/**
	 *  到达联状态
	 */
	private String arriveSheetStatus;
	/**
	 * 到达联是否作废
	 */	
	private String arriveSheetDestroyed;
	/**
	 * 货物状态
	 */
	private List<String> goodsStates;
	
	public String getDeliverbillNo() {
		return deliverbillNo;
	}
	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
	}
	public List<String> getDeliverbillStatus() {
		return deliverbillStatus;
	}
	public void setDeliverbillStatus(List<String> deliverbillStatus) {
		this.deliverbillStatus = deliverbillStatus;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getArriveSheetActive() {
		return arriveSheetActive;
	}
	public void setArriveSheetActive(String arriveSheetActive) {
		this.arriveSheetActive = arriveSheetActive;
	}
	public String getArriveSheetStatus() {
		return arriveSheetStatus;
	}
	public void setArriveSheetStatus(String arriveSheetStatus) {
		this.arriveSheetStatus = arriveSheetStatus;
	}
	public String getArriveSheetDestroyed() {
		return arriveSheetDestroyed;
	}
	public void setArriveSheetDestroyed(String arriveSheetDestroyed) {
		this.arriveSheetDestroyed = arriveSheetDestroyed;
	}
	public List<String> getGoodsStates() {
		return goodsStates;
	}
	public void setGoodsStates(List<String> goodsStates) {
		this.goodsStates = goodsStates;
	}
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	
}
