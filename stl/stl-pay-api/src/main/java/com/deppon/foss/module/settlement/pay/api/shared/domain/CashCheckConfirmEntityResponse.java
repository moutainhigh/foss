package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author 218392 张永雪
 * @date 2015-12-08 15:16:45
 */
//@XmlRootElement(name = "responseMsg")
public class CashCheckConfirmEntityResponse implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 预收金额
	 */
	protected BigDecimal advanceMoney;
	/**
	 * 现金营业款收入
	 */
	protected BigDecimal businessMoney;
	/**
	 * 未收银确认的代收货款单号
	 */
	protected String cashUnconfirmedNo;
	
	/**
	 * 是否成功
	 */
	private boolean ifSuccess;
	
	/**
	 * 错误信息
	 */
	private String errorMsg;
	
	public BigDecimal getAdvanceMoney() {
		return advanceMoney;
	}
	public void setAdvanceMoney(BigDecimal advanceMoney) {
		this.advanceMoney = advanceMoney;
	}
	public BigDecimal getBusinessMoney() {
		return businessMoney;
	}
	public void setBusinessMoney(BigDecimal businessMoney) {
		this.businessMoney = businessMoney;
	}
	public String getCashUnconfirmedNo() {
		return cashUnconfirmedNo;
	}
	public void setCashUnconfirmedNo(String cashUnconfirmedNo) {
		this.cashUnconfirmedNo = cashUnconfirmedNo;
	}
	public boolean isIfSuccess() {
		return ifSuccess;
	}
	public void setIfSuccess(boolean ifSuccess) {
		this.ifSuccess = ifSuccess;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
