package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

public class StockPositionList  extends ScanMsgEntity{

	private static final long serialVersionUID = 1L;

	/*
	 * 部门
	 * */
	private String orgCode;
	/*
	 * 库区编号
	 * */
	private String stockPositionNumber;
	
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getStockPositionNumber() {
		return stockPositionNumber;
	}
	public void setStockPositionNumber(String stockPositionNumber) {
		this.stockPositionNumber = stockPositionNumber;
	}
	
}
