package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;

/**
 * 同步运单接口传参po
 * @author ECS-326181
 * 
 */
public class WayBillRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 运单信息
	 */
	private WaybillEntity waybillEntity;
	
	/**
	 * 实际承运信息
	 */
	private ActualFreightEntity actualFreightEntity;
	
	/**
	 * 其他快递信息
	 */
	private WaybillExpressEntity waybillExpressEntity;
	
	/**
	 * 运单更改信息
	 */
	private WaybillRfcEntity waybillRfcEntity;
	
	/**
	 * 操作类型
	 * DISCARD 子母件新增子件运单数据，其它的则不做处理
	 * null 为空新增
	 * UPDATE 修改
	 */
	private String operationType;

	public WaybillEntity getWaybillEntity() {
		return waybillEntity;
	}

	public void setWaybillEntity(WaybillEntity waybillEntity) {
		this.waybillEntity = waybillEntity;
	}

	public ActualFreightEntity getActualFreightEntity() {
		return actualFreightEntity;
	}

	public void setActualFreightEntity(ActualFreightEntity actualFreightEntity) {
		this.actualFreightEntity = actualFreightEntity;
	}

	public WaybillExpressEntity getWaybillExpressEntity() {
		return waybillExpressEntity;
	}

	public void setWaybillExpressEntity(WaybillExpressEntity waybillExpressEntity) {
		this.waybillExpressEntity = waybillExpressEntity;
	}

	public WaybillRfcEntity getWaybillRfcEntity() {
		return waybillRfcEntity;
	}

	public void setWaybillRfcEntity(WaybillRfcEntity waybillRfcEntity) {
		this.waybillRfcEntity = waybillRfcEntity;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

}
