package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.io.Serializable;

public class StTaskZoneDto implements Serializable {

	private static final long serialVersionUID = 1L;
	/**外场code*/
	private String deptCode;
	/**分区code*/
	private String zoneCode;
	/**分区名称*/
	private String zoneName;
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
}
