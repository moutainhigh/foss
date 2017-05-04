package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SortdestStationEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SortdestStationDto;

public class SortdestStationVo {
	
	
	private SortdestStationEntity sortdestStationEntity;
	
	
	private List<SortdestStationEntity> sortdestStationEntitys;

	
	private List<SortdestStationDto> sortdestStationDtos;
	
	
	private String codeStr;
	
	private String active;

	private SortdestStationDto sortdestStationDto;
	
	public SortdestStationEntity getSortdestStationEntity() {
		return sortdestStationEntity;
	}


	public void setSortdestStationEntity(SortdestStationEntity sortdestStationEntity) {
		this.sortdestStationEntity = sortdestStationEntity;
	}


	public List<SortdestStationEntity> getSortdestStationEntitys() {
		return sortdestStationEntitys;
	}


	public void setSortdestStationEntitys(
			List<SortdestStationEntity> sortdestStationEntitys) {
		this.sortdestStationEntitys = sortdestStationEntitys;
	}


	public List<SortdestStationDto> getSortdestStationDtos() {
		return sortdestStationDtos;
	}


	public void setSortdestStationDtos(List<SortdestStationDto> sortdestStationDtos) {
		this.sortdestStationDtos = sortdestStationDtos;
	}


	public String getCodeStr() {
		return codeStr;
	}


	public void setCodeStr(String codeStr) {
		this.codeStr = codeStr;
	}


	public String getActive() {
		return active;
	}


	public void setActive(String active) {
		this.active = active;
	}


	public SortdestStationDto getSortdestStationDto() {
		return sortdestStationDto;
	}


	public void setSortdestStationDto(SortdestStationDto sortdestStationDto) {
		this.sortdestStationDto = sortdestStationDto;
	}
	
	
	
	
}
