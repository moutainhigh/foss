package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.PendingTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcActionHistoryEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;

/**
 * PDA待补录DTO
 * @author WangQianJin
 * @date 2013-7-25 下午4:27:00
 */
public class PDAWayBillRfcDto implements Serializable {

	/**
	 * 更改单信息
	 */
	private WaybillRfcEntity waybillRfcEntity;
	/**
	 * 起草审核受理信息
	 */
	private List<WaybillRfcActionHistoryEntity> rfcActionList;
	/**
	 * 待办信息
	 */
	private PendingTodoEntity pendingTodoEntity;
	
	/**
	 * 是否业务变更(业务变更包括：运输性质、目的站、提货网点、件数、包装信息)
	 */
	private boolean isBusinessChange;
	
	/**
	 * 变更项明细列表
	 */
	private List<WaybillRfcChangeDetailEntity> changeDetailList;
	
	/**
	 * 是否目的站变更
	 */
	private String isTargetChange;
	/**
	 * 是否财务类变更
	 */
	private String isFinanceChange;
	/**
	 * 是否修改运单号
	 */
	private String isChangeWaybillNo;

	public WaybillRfcEntity getWaybillRfcEntity() {
		return waybillRfcEntity;
	}
	
	public void setWaybillRfcEntity(WaybillRfcEntity waybillRfcEntity) {
		this.waybillRfcEntity = waybillRfcEntity;
	}

	public List<WaybillRfcActionHistoryEntity> getRfcActionList() {
		return rfcActionList;
	}

	public void setRfcActionList(List<WaybillRfcActionHistoryEntity> rfcActionList) {
		this.rfcActionList = rfcActionList;
	}

	public PendingTodoEntity getPendingTodoEntity() {
		return pendingTodoEntity;
	}
	
	public void setPendingTodoEntity(PendingTodoEntity pendingTodoEntity) {
		this.pendingTodoEntity = pendingTodoEntity;
	}
	
	public boolean isBusinessChange() {
		return isBusinessChange;
	}
	
	public void setBusinessChange(boolean isBusinessChange) {
		this.isBusinessChange = isBusinessChange;
	}

	public List<WaybillRfcChangeDetailEntity> getChangeDetailList() {
		return changeDetailList;
	}

	public void setChangeDetailList(
			List<WaybillRfcChangeDetailEntity> changeDetailList) {
		this.changeDetailList = changeDetailList;
	}

	public String getIsTargetChange() {
		return isTargetChange;
	}

	
	public void setIsTargetChange(String isTargetChange) {
		this.isTargetChange = isTargetChange;
	}

	public String getIsFinanceChange() {
		return isFinanceChange;
	}

	public void setIsFinanceChange(String isFinanceChange) {
		this.isFinanceChange = isFinanceChange;
	}

	public String getIsChangeWaybillNo() {
		return isChangeWaybillNo;
	}

	public void setIsChangeWaybillNo(String isChangeWaybillNo) {
		this.isChangeWaybillNo = isChangeWaybillNo;
	}	
	
}
