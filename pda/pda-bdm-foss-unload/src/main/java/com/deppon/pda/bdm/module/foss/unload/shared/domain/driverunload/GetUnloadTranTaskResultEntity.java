package com.deppon.pda.bdm.module.foss.unload.shared.domain.driverunload;

import java.util.List;

/**
 * 获取接驳卸车指令任务,返回实体
 * @ClassName GetUnloadTranTaskEntity.java 
 * @Description 
 * @author 245955
 * @date 2015-4-14
 */
public class GetUnloadTranTaskResultEntity {

	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 车牌号
	 */
	private String truckCode;
	
	/**
	 * 单据
	 */
	private String billNo;
	//private List<TranUnloadBillModel> billNos;
	
	/**
	 * 到达接驳点
	 */
	private String connectionPoint;
	
	/**
	 * 总票数
	 * */
	private Integer waybillQtyTotal;

	/**
	 * 总件数
	 **/
	private Integer goodsQtyTotal;
	/**
	 * 理货员
	 */
	//private List<String> userCodes;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getConnectionPoint() {
		return connectionPoint;
	}
	public void setConnectionPoint(String connectionPoint) {
		this.connectionPoint = connectionPoint;
	}
	
	public Integer getWaybillQtyTotal() {
		return waybillQtyTotal;
	}
	public void setWaybillQtyTotal(Integer waybillQtyTotal) {
		this.waybillQtyTotal = waybillQtyTotal;
	}
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}	
	
}
