package com.deppon.pda.bdm.module.foss.crgreg.shared.domain;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

public class InOutStockPostionScanEntity extends ScanMsgEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 货区编码
	 */
	private String areaCode;
	/**
	 * 库位编码
	 */
	private String position;
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
}
