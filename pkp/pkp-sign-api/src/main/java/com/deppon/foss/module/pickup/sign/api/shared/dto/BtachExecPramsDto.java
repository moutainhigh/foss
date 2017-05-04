package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
/**
 * 批量签收执行异步方法Dto
 * 用于存放批量执行异步方法所需的参数
 * @author 231438
 * 2015-09-10 下午19:21
 */
public class BtachExecPramsDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 运单签收结果Dto
	 */
	private WaybillSignResultDto waybillSignResultDto;
	/**
	 * 签收信息实体
	 */
	private WaybillSignResultEntity wayEntity;
	/**
	 * 实际承运实体
	 */
	private ActualFreightEntity actual;
	/**
	 * 运单实体
	 */
	private WaybillEntity waybill;
	/**
	 * 是否需要发送发票
	 */
	private boolean isSendInvoiceInfo;
	public WaybillSignResultDto getWaybillSignResultDto() {
		return waybillSignResultDto;
	}
	public void setWaybillSignResultDto(WaybillSignResultDto waybillSignResultDto) {
		this.waybillSignResultDto = waybillSignResultDto;
	}
	public WaybillSignResultEntity getWayEntity() {
		return wayEntity;
	}
	public void setWayEntity(WaybillSignResultEntity wayEntity) {
		this.wayEntity = wayEntity;
	}
	public ActualFreightEntity getActual() {
		return actual;
	}
	public void setActual(ActualFreightEntity actual) {
		this.actual = actual;
	}
	public WaybillEntity getWaybill() {
		return waybill;
	}
	public void setWaybill(WaybillEntity waybill) {
		this.waybill = waybill;
	}
	public boolean isSendInvoiceInfo() {
		return isSendInvoiceInfo;
	}
	public void setSendInvoiceInfo(boolean isSendInvoiceInfo) {
		this.isSendInvoiceInfo = isSendInvoiceInfo;
	}
}
