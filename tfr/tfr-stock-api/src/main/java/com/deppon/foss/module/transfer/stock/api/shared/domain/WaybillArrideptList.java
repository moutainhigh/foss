package com.deppon.foss.module.transfer.stock.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

public class WaybillArrideptList extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private String isSuccess;
	
	private String message;
	
	private List<WaybillArridept> waybillArrideptList;

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<WaybillArridept> getWaybillArrideptList() {
		return waybillArrideptList;
	}

	public void setWaybillArrideptList(List<WaybillArridept> waybillArrideptList) {
		this.waybillArrideptList = waybillArrideptList;
	}
	
}
