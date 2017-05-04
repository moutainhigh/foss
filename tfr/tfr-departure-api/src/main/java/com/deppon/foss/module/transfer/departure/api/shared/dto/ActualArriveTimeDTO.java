package com.deppon.foss.module.transfer.departure.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class ActualArriveTimeDTO implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 到达时间
	 */
	private Date arriveTime;
	
	/**
	 * 库存件数
	 */
	private int qytNumber;

	/**
	 * 获取 ********到达时间***********.
	 *
	 * @return the ********到达时间***********
	 */
	public Date getArriveTime() {
		return arriveTime;
	}

	/**
	 * 设置 ********到达时间***********.
	 *
	 * @param arriveTime the new ********到达时间***********
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	
	/**
	 * 获取 ********库存件数***********.
	 *
	 * @return the ********库存件数***********
	 */
	public int getQytNumber() {
		return qytNumber;
	}

	/**
	 * 设置 ********库存件数***********.
	 *
	 * @param qytNumber the new ********库存件数***********
	 */
	public void setQytNumber(int qytNumber) {
		this.qytNumber = qytNumber;
	}

}
