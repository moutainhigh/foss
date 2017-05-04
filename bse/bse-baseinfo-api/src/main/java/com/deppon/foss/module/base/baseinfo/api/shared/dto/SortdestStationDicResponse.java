package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="SortdestStationDicResponse")
public class SortdestStationDicResponse {
	
	private List<SortdestStationDataDicDto> SortdestStationDataDicDtos;
	
	private boolean isSuccess;
	
	private String message;

	public List<SortdestStationDataDicDto> getSortdestStationDataDicDtos() {
		return SortdestStationDataDicDtos;
	}

	public void setSortdestStationDataDicDtos(
			List<SortdestStationDataDicDto> sortdestStationDataDicDtos) {
		SortdestStationDataDicDtos = sortdestStationDataDicDtos;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	 
	
}
