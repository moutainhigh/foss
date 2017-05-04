package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

public class SendElectronicInvoiceSystemDto implements Serializable{
	private Boolean isSendInvoiceInfo; //是否将将发票信息传输至发票系统
	private WaybillEntity entity; //运单entity
	private ActualFreightEntity actual; //实际货物
	
	
	public Boolean getIsSendInvoiceInfo() {
		return isSendInvoiceInfo;
	}
	public void setIsSendInvoiceInfo(Boolean isSendInvoiceInfo) {
		this.isSendInvoiceInfo = isSendInvoiceInfo;
	}
	public WaybillEntity getEntity() {
		return entity;
	}
	public void setEntity(WaybillEntity entity) {
		this.entity = entity;
	}
	public ActualFreightEntity getActual() {
		return actual;
	}
	public void setActual(ActualFreightEntity actual) {
		this.actual = actual;
	}
	
}
