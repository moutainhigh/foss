package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain 
 * @file AcctFinishEntity.java 
 * @description 上传完成接货信息
 * @author ChenLiang
 * @created 2012-12-31 下午2:58:48    
 * @version 1.0
 */
public class AcctFinishEntity implements Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 完成接货时间
	 */
	private Date acctFinishTime;
	/**
	 * PDA编号
	 */
	private String pdaCode;

	/**
	 * 车牌号
	 */
	private String truckCode;

	/**
	 * 金额
	 */
	// private double account;

	/**
	 * 员工编号
	 */
	private String userCode;

	public Date getAcctFinishTime() {
		return acctFinishTime;
	}

	public void setAcctFinishTime(Date acctFinishTime) {
		this.acctFinishTime = acctFinishTime;
	}

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

//	public double getAccount() {
//		return account;
//	}
//
//	public void setAccount(double account) {
//		this.account = account;
//	}

}