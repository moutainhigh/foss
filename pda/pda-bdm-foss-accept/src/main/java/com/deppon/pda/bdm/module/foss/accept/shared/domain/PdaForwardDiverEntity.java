package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

public class PdaForwardDiverEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1171338045303939924L;
	
	private String driverCode;
	
    private String driverName;

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
    
    

}
