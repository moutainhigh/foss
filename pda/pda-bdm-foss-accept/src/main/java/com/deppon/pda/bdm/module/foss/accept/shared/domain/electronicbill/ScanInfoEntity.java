package com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill;

import java.io.Serializable;

public class ScanInfoEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	//清关条码
	private String customsCode;
	public String getCustomsCode() {
		return customsCode;
	}
	public void setCustomsCode(String customsCode) {
		this.customsCode = customsCode;
	}
	
}

