package com.deppon.pda.bdm.module.foss.load.shared.domain;
/**
 * 装车封签
 * @author gaojia
 * @date Sep 6,2012 14:33:30 PM
 * @version 1.0
 * @since
 */
public class LoadSeal {
	private String id;
	private String scanId;
	private String sealsCode;
	private String truckCode;
	private String remark;
	private String sealType;
	public String getSealsCode() {
		return sealsCode;
	}
	public void setSealsCode(String sealsCode) {
		this.sealsCode = sealsCode;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getScanId() {
		return scanId;
	}
	public void setScanId(String scanId) {
		this.scanId = scanId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSealType() {
		return sealType;
	}
	public void setSealType(String sealType) {
		this.sealType = sealType;
	}
}
