package com.deppon.esb.inteface.domain.fins;

import java.io.Serializable;

/**
 * 同步合伙账户信息
 * 
 * @date 2016-01-06
 * @author 302302
 * 
 */
public class PartnerAccountInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 开户银行
	 */
	private String bankName;
	/**
	 * 所属子公司名称
	 */
	private String subbranchName;
	/**
	 * 开户名(合伙人)
	 */
	private String bankAccName;
	/**
	 * 开户所在省
	 */
	private String accprovice;
	/**
	 * 开户所在市
	 */
	private String acccity;
	/**
	 * 银行账户
	 */
	private String bankAcc;
	/**
	 * 主键
	 */
	private String fid;
	/**
	 * 账户状态（有正常和销户两种状态）
	 */
	private String accountStatus;
	/**
	 * 账户类型
	 */
	private String accType;
	/**
	 * 对接营业部编码
	 */
	private String dockDepartCD;
	/**
	 * 对接营业部名称
	 */
	private String dockDepartName;
	/**
	 * 录入人
	 */
	private String entryPerson;
	/**
	 * 录入人所在部门
	 */
	private String entryPersDept;
	/**
	 * 录入时间
	 */
	private String entryDate;
	/**
	 * 销户时间
	 */
	private String accancelDate;
	/**
	 * 财务经理(工号)
	 */
	private String finmanager;
	/**
	 * 财务部门编码
	 */
	private String finDept;
	/**
	 * 状态（1表示新增或修改 2表示销户）
	 */
	/**
     * 开户支行编码
     */
    private String fbranchBankCode;
    public String getFbranchBankCode() {
		return fbranchBankCode;
	}

	public void setFbranchBankCode(String fbranchBankCode) {
		this.fbranchBankCode = fbranchBankCode;
	}

	public String getFbranchBankName() {
		return fbranchBankName;
	}

	public void setFbranchBankName(String fbranchBankName) {
		this.fbranchBankName = fbranchBankName;
	}

	/**
     * 开户支行名称
     */
    private String fbranchBankName;
	private String state;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSubbranchName() {
		return subbranchName;
	}

	public void setSubbranchName(String subbranchName) {
		this.subbranchName = subbranchName;
	}

	public String getBankAccName() {
		return bankAccName;
	}

	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}

	public String getAccprovice() {
		return accprovice;
	}

	public void setAccprovice(String accprovice) {
		this.accprovice = accprovice;
	}

	public String getAcccity() {
		return acccity;
	}

	public void setAcccity(String acccity) {
		this.acccity = acccity;
	}

	public String getBankAcc() {
		return bankAcc;
	}

	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getDockDepartCD() {
		return dockDepartCD;
	}

	public void setDockDepartCD(String dockDepartCD) {
		this.dockDepartCD = dockDepartCD;
	}

	public String getDockDepartName() {
		return dockDepartName;
	}

	public void setDockDepartName(String dockDepartName) {
		this.dockDepartName = dockDepartName;
	}

	public String getEntryPerson() {
		return entryPerson;
	}

	public void setEntryPerson(String entryPerson) {
		this.entryPerson = entryPerson;
	}

	public String getEntryPersDept() {
		return entryPersDept;
	}

	public void setEntryPersDept(String entryPersDept) {
		this.entryPersDept = entryPersDept;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getAccancelDate() {
		return accancelDate;
	}

	public void setAccancelDate(String accancelDate) {
		this.accancelDate = accancelDate;
	}

	public String getFinmanager() {
		return finmanager;
	}

	public void setFinmanager(String finmanager) {
		this.finmanager = finmanager;
	}

	public String getFinDept() {
		return finDept;
	}

	public void setFinDept(String finDept) {
		this.finDept = finDept;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
