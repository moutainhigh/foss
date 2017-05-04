package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sortdestStationResponse")
public class SortdestStationResponse {
	
	
	private List<SortdestStationRestFulDto> sortdestStationRestFulDtos;
	
	private boolean isSuccess;
	
	private String message;

	public List<SortdestStationRestFulDto> getSortdestStationRestFulDtos() {
		return sortdestStationRestFulDtos;
	}

	public void setSortdestStationRestFulDtos(
			List<SortdestStationRestFulDto> sortdestStationRestFulDtos) {
		this.sortdestStationRestFulDtos = sortdestStationRestFulDtos;
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
