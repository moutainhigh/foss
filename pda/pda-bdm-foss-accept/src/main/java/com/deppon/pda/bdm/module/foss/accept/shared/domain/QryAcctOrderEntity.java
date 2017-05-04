package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain
 * @file QryAcctOrderEntity.java
 * @description 查询车辆接货订单
 * @author ChenLiang
 * @created 2012-12-31 下午3:02:30
 * @version 1.0
 */
public class QryAcctOrderEntity implements Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * PDA编号
	 */
	private String pdaCode;
	/**
	 * 车牌号
	 */
	private String truckCode;
	/**
	 * 员工编号
	 */
	private String userCode;

	public String getPdaCode() {
		return pdaCode;
	}

	public void setPdaCode(String pdaCode) {
		this.pdaCode = pdaCode;
	}

	public String getTruckCode() {
		return truckCode;
	}

	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}