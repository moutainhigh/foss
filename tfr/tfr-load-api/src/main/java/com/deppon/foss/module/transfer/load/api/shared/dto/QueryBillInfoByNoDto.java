package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.util.List;

public class QueryBillInfoByNoDto {
	//单据编号list
	private List<String> nosList;

	public List<String> getNosList() {
		return nosList;
	}

	public void setNosList(List<String> nosList) {
		this.nosList = nosList;
	}

}
