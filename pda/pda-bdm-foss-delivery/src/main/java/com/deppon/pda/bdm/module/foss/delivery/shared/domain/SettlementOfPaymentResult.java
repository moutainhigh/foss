package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 结清货款模块根据单号查询数据返回实体
 * 
 * @author 245955
 * 
 */
public class SettlementOfPaymentResult implements Serializable {
	private static final long serialVersionUID = 1L;
	// 总金额
	private BigDecimal toPayAmount;
	// 运单号
	private String waybillNo;
	// 收货人
	private String receiveCustomerName;
	// 业务时间
	private Date createTime;
	// 未核销金额
	private BigDecimal unverifyAmount;
	//未付款总金额
	private BigDecimal unPayAmount;
	//归属系统
	private String affiliation;
	
	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public BigDecimal getUnPayAmount() {
		return unPayAmount;
	}

	public void setUnPayAmount(BigDecimal unPayAmount) {
		this.unPayAmount = unPayAmount;
	}

	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}

	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}

}
