package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

/**
 * 
 * @ClassName: PdaForwardDiverDto 
 * @Description: PDA可转发快递员实体 
 * @author 245955
 * @date 2016-5-3 上午11:30:42 
 *
 */
public class PdaForwardDiverDto implements Serializable {
	private static final long serialVersionUID = 4843013229100678687L;
	
	// 快递员工号
	private String driverCode;
	// 快递员姓名
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