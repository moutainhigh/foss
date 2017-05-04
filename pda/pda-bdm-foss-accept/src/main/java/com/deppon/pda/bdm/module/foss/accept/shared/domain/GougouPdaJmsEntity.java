package com.deppon.pda.bdm.module.foss.accept.shared.domain;

/**
 * 裹裹系统对接PDA
 * @author 245955
 *
 */
public class GougouPdaJmsEntity {
	
	/**
	 * 验证码
	 */
	private String verifyCode;
	/**
	 * 渠道单号
	 */
	private String logisticNo;
	/**
	 * 备注
	 */
	private String remark;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getLogisticNo() {
		return logisticNo;
	}
	public void setLogisticNo(String logisticNo) {
		this.logisticNo = logisticNo;
	}
}
