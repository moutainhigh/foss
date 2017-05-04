package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;

public class WaybillDestinationDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7894338636446874374L;
	
	//运单ID
	private String waybillNoid;

	//运单号
	private String waybillNo ;
	
	//最终配载部门
	private String lastLoadOrgCode ;
	
	private String targetOrgCode ;
	
	private String customerPickupOrgCode ;
	
	private String customerPickupOrgName ;
	
	//判断到达部门是否为合伙人
	private Boolean isArriveDepPartner ;

	//判断出发部门是否为合伙人
	private Boolean isReceiveDepPartner ;
		
	//
	private WaybillEntity waybillEntity ;
	//
	private ActualFreightEntity actualFreightEntity ;
	//快递信息
	WaybillExpressEntity waybillExpressEntity;
	//本次补码前的补码时间
	private Date lastAddCodeTime;
	//本次补码之前是否补码
	private String isLastAddCode;
	//本次补码之前是否补码到合伙人
	private Boolean isLastPickUpPartner;
	//未做任何修改的运单信息
	private WaybillEntity originalWaybill;
	
	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	@Override
	public String toString() {
		return "WaybillDestinationDto [waybillNo=" + waybillNo
				+ ", lastLoadOrgCode=" + lastLoadOrgCode + "]";
	}

	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}

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

	public Boolean getIsArriveDepPartner() {
		return isArriveDepPartner;
	}

	public void setIsArriveDepPartner(Boolean isArriveDepPartner) {
		this.isArriveDepPartner = isArriveDepPartner;
	}

	public Boolean getIsReceiveDepPartner() {
		return isReceiveDepPartner;
	}

	public void setIsReceiveDepPartner(Boolean isReceiveDepPartner) {
		this.isReceiveDepPartner = isReceiveDepPartner;
	}

	public String getWaybillNoid() {
		return waybillNoid;
	}

	public void setWaybillNoid(String waybillNoid) {
		this.waybillNoid = waybillNoid;
	}

	public WaybillExpressEntity getWaybillExpressEntity() {
		return waybillExpressEntity;
	}

	public void setWaybillExpressEntity(WaybillExpressEntity waybillExpressEntity) {
		this.waybillExpressEntity = waybillExpressEntity;
	}

	public Date getLastAddCodeTime() {
		return lastAddCodeTime;
	}

	public void setLastAddCodeTime(Date lastAddCodeTime) {
		this.lastAddCodeTime = lastAddCodeTime;
	}

	public String getIsLastAddCode() {
		return isLastAddCode;
	}

	public void setIsLastAddCode(String isLastAddCode) {
		this.isLastAddCode = isLastAddCode;
	}

	public Boolean getIsLastPickUpPartner() {
		return isLastPickUpPartner;
	}

	public void setIsLastPickUpPartner(Boolean isLastPickUpPartner) {
		this.isLastPickUpPartner = isLastPickUpPartner;
	}

	public WaybillEntity getOriginalWaybill() {
		return originalWaybill;
	}

	public void setOriginalWaybill(WaybillEntity originalWaybill) {
		this.originalWaybill = originalWaybill;
	}

}
