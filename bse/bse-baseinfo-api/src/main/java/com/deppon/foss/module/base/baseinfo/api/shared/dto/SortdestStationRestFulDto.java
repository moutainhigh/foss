package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.List;

public class SortdestStationRestFulDto {
	
	
	private String origOrgCode;
	
	private String origOrgName;
	
	private String schemeCode;
	
	private String schemeName;

	private List<SortdestStationRestFulInfoDto> sortdestStationRestFulInfoDtos;
	

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public String getOrigOrgName() {
		return origOrgName;
	}

	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	public String getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public List<SortdestStationRestFulInfoDto> getSortdestStationRestFulInfoDtos() {
		return sortdestStationRestFulInfoDtos;
	}

	public void setSortdestStationRestFulInfoDtos(
			List<SortdestStationRestFulInfoDto> sortdestStationRestFulInfoDtos) {
		this.sortdestStationRestFulInfoDtos = sortdestStationRestFulInfoDtos;
	}


}
