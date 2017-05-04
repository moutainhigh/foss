package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SortdestStationDto;

public class SortdestStationEntity  extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//id
	private String id;
	//
	private String origOrgCode;
	
	private String origOrgName;
	
	private String schemeCode;
	
	private String schemeName;
	
	private String virtualCode;
	
	private String active;
	
	private String status;
	//方案名称
	private String scheme;
	
	
	
	private List<SortdestStationDto> sortdestStationDtos;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getVirtualCode() {
		return virtualCode;
	}

	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public List<SortdestStationDto> getSortdestStationDtos() {
		return sortdestStationDtos;
	}

	public void setSortdestStationDtos(List<SortdestStationDto> sortdestStationDtos) {
		this.sortdestStationDtos = sortdestStationDtos;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	
}
