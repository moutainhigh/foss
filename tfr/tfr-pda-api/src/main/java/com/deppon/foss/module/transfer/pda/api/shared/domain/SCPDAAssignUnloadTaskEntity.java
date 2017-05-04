package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.io.Serializable;
import java.util.List;


/**
 * PDA接口返回值:已分配卸车任务
 * @author foss-hongwy
 * @date 2015-5-8 下午15:49:20
 */
public class SCPDAAssignUnloadTaskEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**车牌号*/
	private String vehicleNo;
	
	/**二程接驳卸车单据*/
	private List<SCPDAAssignUnloadTaskEntity> bills;
	

	/**卸车状态:未开始、进行中*/
	private String state;
	
	/**单据编号*/
	private String billNo;
	
	/**到达接驳点*/
	private String connectionPoint;
	
	/**总票数*/
	private String waybillQtyTotal;
	
	/**总件数*/
	private String goodsQtyTotal;
    
	/**任务编号*/
	private String taskNo;
	


	
	/**
	 * Gets the 状态.
	 *
	 * @return the 状态
	 */
	public String getState() {
		return state;
	}
	/**
	 * Sets the 状态.
	 *
	 * @param state the new 状态
	 */
	public void setState(String state) {
		this.state = state;
	}
    
	
	/**
	 * Gets the 单据编号.
	 *
	 * @return the 单据编号
	 */
	public String getBillNo() {
		return billNo;
	}
    
	/**
	 * Sets the 单据编号.
	 *
	 * @param state the new 单据编号
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

    
	/**
	 * Gets the 到达接驳点.
	 *
	 * @return the 到达接驳点
	 */
	public String getConnectionPoint() {
		return connectionPoint;
	}
    
	/**
	 * Sets the 到达接驳点.
	 *
	 * @param state the new 到达接驳点
	 */
	public void setConnectionPoint(String connectionPoint) {
		this.connectionPoint = connectionPoint;
	}
	
	/**
	 * Gets the 任务号.
	 *
	 * @return the 任务号
	 */
	public String getTaskNo() {
		return taskNo;
	}
	
	/**
	 * Sets the 任务号.
	 *
	 * @param state the new 任务号
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	/**
	 * Gets the 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * Sets the 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * Gets the 总票数.
	 *
	 * @return the 总票数.
	 */
	public String getWaybillQtyTotal() {
		return waybillQtyTotal;
	}
	
	/**
	 * Sets the 总票数.
	 *
	 * @param vehicleNo the new 总票数
	 */
	public void setWaybillQtyTotal(String waybillQtyTotal) {
		this.waybillQtyTotal = waybillQtyTotal;
	}
	
	/**
	 * Gets the 总件数.
	 *
	 * @return the 总件数.
	 */
	public String getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	
	/**
	 * Sets the 总件数.
	 *
	 * @param vehicleNo the new 总件数
	 */
	public void setGoodsQtyTotal(String goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	
	public List<SCPDAAssignUnloadTaskEntity> getBills() {
		return bills;
	}
	
	public void setBills(List<SCPDAAssignUnloadTaskEntity> bills) {
		this.bills = bills;
	}
	
	
}
