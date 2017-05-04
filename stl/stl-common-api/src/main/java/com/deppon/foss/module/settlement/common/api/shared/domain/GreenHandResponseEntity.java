package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GreenHandResponseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 费用类型
	 */
	private String costType;
	
	/**
	 * dop传数据过来的时间
	 */
	private Date dopTime;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 *备注 
	 */
	private String note;
	/**
	 * 成功：”1”；失败：”0”
	 */
	private String result;
	/**
	 * 提示信息
	 */
	private String message;
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	public Date getDopTime() {
		return dopTime;
	}
	public void setDopTime(Date dopTime) {
		this.dopTime = dopTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
