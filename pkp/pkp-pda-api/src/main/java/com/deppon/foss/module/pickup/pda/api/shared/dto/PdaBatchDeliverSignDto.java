package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;


/**
 * PDA批量派送签收运单明细
 * @author 159231
 * 2015-8-30 
 *
 */
public class PdaBatchDeliverSignDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	private WaybillEntity waybill;
	private Boolean isSendInvoiceInfo; //电子发票
	private ActualFreightEntity actual;
	private PdaDeliverSignDto pdaDeliverSignDto;
	
	public WaybillEntity getWaybill() {
		return waybill;
	}
	public void setWaybill(WaybillEntity waybill) {
		this.waybill = waybill;
	}
	
	public ActualFreightEntity getActual() {
		return actual;
	}
	public void setActual(ActualFreightEntity actual) {
		this.actual = actual;
	}
	public PdaDeliverSignDto getPdaDeliverSignDto() {
		return pdaDeliverSignDto;
	}
	public void setPdaDeliverSignDto(PdaDeliverSignDto pdaDeliverSignDto) {
		this.pdaDeliverSignDto = pdaDeliverSignDto;
	}
	public Boolean getIsSendInvoiceInfo() {
		return isSendInvoiceInfo;
	}
	public void setIsSendInvoiceInfo(Boolean isSendInvoiceInfo) {
		this.isSendInvoiceInfo = isSendInvoiceInfo;
	}
	
}