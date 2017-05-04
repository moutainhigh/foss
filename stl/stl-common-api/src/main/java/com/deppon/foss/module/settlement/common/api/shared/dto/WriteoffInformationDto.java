package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 218392 张永雪
 * @date 2016-02-15
 */
public class WriteoffInformationDto implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 编号(GG+运单号)
	 */
	private String billNum;
	/**
	 * 汇款时间(对应DOP传给我的时间)
	 */
	private Date remitDate;
	/**
	 * 汇款部门(应收单中的催款部门对应的标杆编码DP开头的)
	 */
	private String remitDept;
	/**
	 * 款项所属部门(应收单中的催款部门对应的标杆编码DP开头的)
	 */
	private String fundDept;
	/**
	 * 汇款金额(对应DOP传给我的金额)
	 */
	private BigDecimal remitTance;
	/**
	 * 汇入账号(我FOSS这是为空,FINS那边有默认的)
	 */
	private String remitAccount;
	/**
	 * 已使用金额(FOSS核销金额)
	 */
	private BigDecimal alreadyamount;
	/**
	 * 未使用金额（FOSS这边传空过去）
	 */
	private BigDecimal unuseamount;
	/**
	 * 款项类别   1：运费，2：补贴  (我传过去的是运费)
	 */
	private String fundType; 
	
	public String getBillNum() {
		return billNum;
	}
	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	public Date getRemitDate() {
		return remitDate;
	}
	public void setRemitDate(Date remitDate) {
		this.remitDate = remitDate;
	}
	public String getRemitDept() {
		return remitDept;
	}
	public void setRemitDept(String remitDept) {
		this.remitDept = remitDept;
	}
	public String getFundDept() {
		return fundDept;
	}
	public void setFundDept(String fundDept) {
		this.fundDept = fundDept;
	}
	public BigDecimal getRemitTance() {
		return remitTance;
	}
	public void setRemitTance(BigDecimal remitTance) {
		this.remitTance = remitTance;
	}
	public String getRemitAccount() {
		return remitAccount;
	}
	public void setRemitAccount(String remitAccount) {
		this.remitAccount = remitAccount;
	}
	public BigDecimal getAlreadyamount() {
		return alreadyamount;
	}
	public void setAlreadyamount(BigDecimal alreadyamount) {
		this.alreadyamount = alreadyamount;
	}
	public BigDecimal getUnuseamount() {
		return unuseamount;
	}
	public void setUnuseamount(BigDecimal unuseamount) {
		this.unuseamount = unuseamount;
	}
	public String getFundType() {
		return fundType;
	}
	public void setFundType(String fundType) {
		this.fundType = fundType;
	} 	

}
