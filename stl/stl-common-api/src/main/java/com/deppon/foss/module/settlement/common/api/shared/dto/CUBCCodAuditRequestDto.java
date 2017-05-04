package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.List;

public class CUBCCodAuditRequestDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<String> waybillNo;

	public List<String> getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(List<String> waybillNo) {
		this.waybillNo = waybillNo;
	}
	

}
