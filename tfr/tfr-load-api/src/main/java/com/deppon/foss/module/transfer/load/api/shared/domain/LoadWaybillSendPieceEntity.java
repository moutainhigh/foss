package com.deppon.foss.module.transfer.load.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

public class LoadWaybillSendPieceEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**装车明细id**/
	private String loadWaybillDetailId;
	/**运单号**/
	private String waybillNo;
	/**派送单号**/
	private String deliverNo;
	/**任务号**/
	private String taskNo;
	
	public String getLoadWaybillDetailId() {
		return loadWaybillDetailId;
	}
	public void setLoadWaybillDetailId(String loadWaybillDetailId) {
		this.loadWaybillDetailId = loadWaybillDetailId;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getDeliverNo() {
		return deliverNo;
	}
	public void setDeliverNo(String deliverNo) {
		this.deliverNo = deliverNo;
	}
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

}
