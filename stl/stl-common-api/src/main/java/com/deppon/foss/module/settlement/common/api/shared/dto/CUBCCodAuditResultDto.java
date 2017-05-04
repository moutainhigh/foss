package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.List;

public class CUBCCodAuditResultDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//代收货款记录List
	private List<CodAuditDto> codAuditList;
	
	//错误信息
	private String meg;
	
	public String getMeg() {
		return meg;
	}

	public void setMeg(String meg) {
		this.meg = meg;
	}

	public List<CodAuditDto> getCodAuditList() {
		return codAuditList;
	}

	public void setCodAuditList(List<CodAuditDto> codAuditList) {
		this.codAuditList = codAuditList;
	}

}
