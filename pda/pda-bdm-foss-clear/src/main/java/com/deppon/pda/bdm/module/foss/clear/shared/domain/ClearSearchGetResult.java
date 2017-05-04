package com.deppon.pda.bdm.module.foss.clear.shared.domain;

/**
 * 货区返回
 * @author 245955
 *
 */
public class ClearSearchGetResult {
	
    //货区编码
    private String zoneCode;
    //货区名称
    private String zoneName;
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
