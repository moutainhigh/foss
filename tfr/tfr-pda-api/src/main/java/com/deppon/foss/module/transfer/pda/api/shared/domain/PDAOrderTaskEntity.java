package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.io.Serializable;

/**
 * PDA接口返回值:刷新点单任务
 * @author foss-272681
 * @date 2016-01-28 下午12:25:29
 */
public class PDAOrderTaskEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//点单任务编号
	private String orderTaskNo;
	//点单人
	private String orderCode;
		
	private String orderName;
	//点单任务状态
	private String orderTaskState;
	public String getOrderTaskNo() {
		return orderTaskNo;
	}
	public void setOrderTaskNo(String orderTaskNo) {
		this.orderTaskNo = orderTaskNo;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getOrderTaskState() {
		return orderTaskState;
	}
	public void setOrderTaskState(String orderTaskState) {
		this.orderTaskState = orderTaskState;
	}
	
}
