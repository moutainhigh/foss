package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class PDAUnloadDiffReportDetailDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7053929875683598152L;

	//运单号
	private String waybillNo;
	//流水号
	private String serialNo;
	//目的站
	private String targetOrg;
	//卸车差异明细处理状态
	private String status;
	//包号
	private String packageNo;
	
	//差异类型
	private String differenceType;
	//车牌号
	private String vehicleNo;
	//操作人
	private String opreator ;
	//差异时间
	private Date createTime;
	//差错编号
	private String oaErrorNo;
	
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public String getTargetOrg() {
		return targetOrg;
	}
	public String getStatus() {
		return status;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public void setTargetOrg(String targetOrg) {
		this.targetOrg = targetOrg;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public final String getPackageNo() {
		return packageNo;
	}
	public final void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	
	public String getDifferenceType() {
		return differenceType;
	}
	public void setDifferenceType(String differenceType) {
		this.differenceType = differenceType;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getOpreator() {
		return opreator;
	}
	public void setOpreator(String opreator) {
		this.opreator = opreator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getOaErrorNo() {
		return oaErrorNo;
	}
	public void setOaErrorNo(String oaErrorNo) {
		this.oaErrorNo = oaErrorNo;
	}

	
}
