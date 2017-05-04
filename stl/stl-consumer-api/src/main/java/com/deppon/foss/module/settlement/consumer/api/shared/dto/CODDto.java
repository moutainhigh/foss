package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;

/**
 * 出发代收货款信息
 * 
 * @author dp-zengbinwen
 * @date 2012-10-13 下午4:52:57
 */
public class CODDto extends CODEntity implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -4179345086759587844L;

	/**
	 * 应付单编号
	 */
	private String payableNo;

	/**
	 * 总金额
	 */
	private BigDecimal amount;

	/**
	 * 冲应收金额
	 */
	private BigDecimal verReceivableAmount;

	/**
	 * 应退金额
	 */
	private BigDecimal returnAmount;

	/**
	 * 账号
	 */
	private String payeeAccount;

	/**
	 * 开户行
	 */
	private String bank;

	/**
	 * 开户行编码
	 */
	private String bankCode;

	/**
	 * 发货客户与收款人关系
	 */
	private String payeeAndConsignor;

	/**始发部门.*/
	private String origOrgCode;
	/**始发部门.*/
	private String origOrgName;
	/**到达部门.*/
	private String destOrgCode;
	/**到达部门.*/
	private String destOrgName;
	
	/**
	 * 省份
	 */
	private String province;

	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 支行编码
	 */
	private String bankSubbranchCode;
	
	/**
	 * 支行
	 */
	private String bankSubbranch;

	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 签收日期
	 */
	private Date signDate;

	/**
	 * 汇款导出人编码
	 */
	private String codExportUserCode;

	/**
	 * 汇款导出人名称
	 */
	private String codExportUserName;

	/**
	 * 应付单客户编码
	 */
	private String payableCustomerCode;

	/**
	 * 应付单客户名称
	 */
	private String payableCustomerName;

	/**
	 * 应付子公司编码
	 */
	private String payableComCode;
	
	/**
	 * 应付子公司名称
	 */
	private String payableComName;
	
	/** 应收冲代收货款应付的金额. */
	private BigDecimal verifyAmount;
	
	/**
	 * 数据库符合条件的数据总条数
	 */
	private Long totalCount;

	/**
	 * 合计总金额
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 资金部冻结总条数
	 */
	private Long freezeTotalCount;
	
	/**
	 * 资金部冻结总金额
	 */
	private BigDecimal freezeTotalAmount;
	
	/**
	 * 代收货款应付单集合
	 */
	private List<CODPayableDto> codList;
	
	/**
	 * 代收货款集合
	 */
	private List<CODEntity> codEntityList;
	
	/**
	 * 代收货款类型集合
	 */
	private List<String> codTypes;
	
	/**
	 * 银行集合
	 */
	private List<String> bankList;
	
	/**
	 * @return payableNo
	 */
	public String getPayableNo() {
		return payableNo;
	}

	/**
	 * @param payableNo
	 */
	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
	}

	/**
	 * @return amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return verReceivableAmount
	 */
	public BigDecimal getVerReceivableAmount() {
		return verReceivableAmount;
	}

	/**
	 * @param verReceivableAmount
	 */
	public void setVerReceivableAmount(BigDecimal verReceivableAmount) {
		this.verReceivableAmount = verReceivableAmount;
	}

	/**
	 * @return codTypes
	 */
	public List<String> getCodTypes() {
		return codTypes;
	}

	/**
	 * @param codTypes
	 */
	public void setCodTypes(List<String> codTypes) {
		this.codTypes = codTypes;
	}

	/**
	 * @return bankList
	 */
	public List<String> getBankList() {
		return bankList;
	}

	/**
	 * @param bankList
	 */
	public void setBankList(List<String> bankList) {
		this.bankList = bankList;
	}

	/**
	 * @return returnAmount
	 */
	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	/**
	 * @param returnAmount
	 */
	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	/**
	 * @return payeeAccount
	 */
	public String getPayeeAccount() {
		return payeeAccount;
	}

	/**
	 * @param payeeAccount
	 */
	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}

	/**
	 * @return bank
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * @param bank
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * @return bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param bankCode
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @return payeeAndConsignor
	 */
	public String getPayeeAndConsignor() {
		return payeeAndConsignor;
	}

	/**
	 * @param payeeAndConsignor
	 */
	public void setPayeeAndConsignor(String payeeAndConsignor) {
		this.payeeAndConsignor = payeeAndConsignor;
	}

	/**
	 * @return province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return bankSubbranch
	 */
	public String getBankSubbranch() {
		return bankSubbranch;
	}

	/**
	 * @param bankSubbranch
	 */
	public void setBankSubbranch(String bankSubbranch) {
		this.bankSubbranch = bankSubbranch;
	}

	/**
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return signDate
	 */
	public Date getSignDate() {
		return signDate;
	}

	/**
	 * @param signDate
	 */
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	/**
	 * @return codExportUserCode
	 */
	public String getCodExportUserCode() {
		return codExportUserCode;
	}

	/**
	 * @param codExportUserCode
	 */
	public void setCodExportUserCode(String codExportUserCode) {
		this.codExportUserCode = codExportUserCode;
	}

	/**
	 * @return codExportUserName
	 */
	public String getCodExportUserName() {
		return codExportUserName;
	}

	/**
	 * @param codExportUserName
	 */
	public void setCodExportUserName(String codExportUserName) {
		this.codExportUserName = codExportUserName;
	}

	/**
	 * @return payableCustomerCode
	 */
	public String getPayableCustomerCode() {
		return payableCustomerCode;
	}

	/**
	 * @param payableCustomerCode
	 */
	public void setPayableCustomerCode(String payableCustomerCode) {
		this.payableCustomerCode = payableCustomerCode;
	}

	/**
	 * @return payableCustomerName
	 */
	public String getPayableCustomerName() {
		return payableCustomerName;
	}

	/**
	 * @param payableCustomerName
	 */
	public void setPayableCustomerName(String payableCustomerName) {
		this.payableCustomerName = payableCustomerName;
	}

	/**
	 * @return payableComCode
	 */
	public String getPayableComCode() {
		return payableComCode;
	}

	/**
	 * @param payableComCode
	 */
	public void setPayableComCode(String payableComCode) {
		this.payableComCode = payableComCode;
	}

	/**
	 * @return payableComName
	 */
	public String getPayableComName() {
		return payableComName;
	}

	/**
	 * @param payableComName
	 */
	public void setPayableComName(String payableComName) {
		this.payableComName = payableComName;
	}

	/**
	 * @return verifyAmount
	 */
	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}

	/**
	 * @param verifyAmount
	 */
	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}

	/**
	 * @return totalCount
	 */
	public Long getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 */
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return freezeTotalCount
	 */
	public Long getFreezeTotalCount() {
		return freezeTotalCount;
	}

	/**
	 * @param freezeTotalCount
	 */
	public void setFreezeTotalCount(Long freezeTotalCount) {
		this.freezeTotalCount = freezeTotalCount;
	}

	/**
	 * @return freezeTotalAmount
	 */
	public BigDecimal getFreezeTotalAmount() {
		return freezeTotalAmount;
	}

	/**
	 * @param freezeTotalAmount
	 */
	public void setFreezeTotalAmount(BigDecimal freezeTotalAmount) {
		this.freezeTotalAmount = freezeTotalAmount;
	}

	/**
	 * @return codList
	 */
	public List<CODPayableDto> getCodList() {
		return codList;
	}

	/**
	 * @param codList
	 */
	public void setCodList(List<CODPayableDto> codList) {
		this.codList = codList;
	}

	/**
	 * @return bankSubbranchCode
	 */
	public String getBankSubbranchCode() {
		return bankSubbranchCode;
	}

	/**
	 * @param bankSubbranchCode
	 */
	public void setBankSubbranchCode(String bankSubbranchCode) {
		this.bankSubbranchCode = bankSubbranchCode;
	}

	/**
	 * @return the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * @param origOrgCode the origOrgCode to set
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * @return the origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * @param origOrgName the origOrgName to set
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * @return the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @param destOrgCode the destOrgCode to set
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return the destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * @param destOrgName the destOrgName to set
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * @return the codEntityList
	 */
	public List<CODEntity> getCodEntityList() {
		return codEntityList;
	}

	/**
	 * @param codEntityList the codEntityList to set
	 */
	public void setCodEntityList(List<CODEntity> codEntityList) {
		this.codEntityList = codEntityList;
	}

}
