package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

public class PlatformQcDto extends PlatformVehicleInfoDto{

	private static final long serialVersionUID = -4092856874861119235L;
	
	/**
	 * 是否只显示空月台,Y/N
	 */
	private String onlyFree;
	
	public String getOnlyFree() {
		return onlyFree;
	}

	public void setOnlyFree(String onlyFree) {
		this.onlyFree = onlyFree;
	}
	
}
