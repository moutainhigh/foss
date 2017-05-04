package com.deppon.pda.bdm.module.foss.clear.shared.domain;

/**
 * 
 * 行政区域实体类
 * @author 200689
 * @date 2014-10-29 上午9:13:10
 * @since
 * @version
 */

public class AdministrativeRegionEntity {
	
	/**分区code*/
	private String zoneCode;
	/**分区名称*/
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
