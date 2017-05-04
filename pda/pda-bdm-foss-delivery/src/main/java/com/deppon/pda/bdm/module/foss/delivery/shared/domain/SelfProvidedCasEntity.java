package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.util.List;

public class SelfProvidedCasEntity implements Serializable{
	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 运单号
	 * */
	private String wayBillCode;
    
	private List<String> wblCodes;
    
	public String getWayBillCode() {
		return wayBillCode;
	}

	public void setWayBillCode(String wayBillCode) {
		this.wayBillCode = wayBillCode;
	}

	public List<String> getWblCodes() {
		return wblCodes;
	}

	public void setWblCodes(List<String> wblCodes) {
		this.wblCodes = wblCodes;
	}
	
	
}
