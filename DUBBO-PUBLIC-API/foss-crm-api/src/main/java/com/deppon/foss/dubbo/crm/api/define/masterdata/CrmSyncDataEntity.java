package com.deppon.foss.dubbo.crm.api.define.masterdata;

import java.io.Serializable;
import java.util.List;

public class CrmSyncDataEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "CrmSyncDataEntity [pattern=" + pattern + ", tCustCustbasedata=" + tCustCustbasedata + ", tCustAccount="
				+ tCustAccount + ", tCustContract=" + tCustContract + ", tCustCustlinkman=" + tCustCustlinkman
				+ ", tCustShuttleaddress=" + tCustShuttleaddress + ", tCustPreferenceaddress=" + tCustPreferenceaddress
				+ ", tCustContractdept=" + tCustContractdept + ", tCustPreferential=" + tCustPreferential
				+ ", tCustContractTax=" + tCustContractTax + "]";
	}

	// 根据不同的协议模式保存不同的数据
	private int pattern;
	private CustCustbasedata tCustCustbasedata;
	private List<CustAccount> tCustAccount;
	private List<CustContract> tCustContract;
	private List<CustCustlinkman> tCustCustlinkman;
	private List<CustShuttleaddress> tCustShuttleaddress;
	private List<CustPreferenceaddress> tCustPreferenceaddress;
	private List<CustContractdept> tCustContractdept;
	private List<CustPreferential> tCustPreferential;
	private List<CustContractTax> tCustContractTax;

	public int getPattern() {
		return pattern;
	}

	public void setPattern(int pattern) {
		this.pattern = pattern;
	}

	public CustCustbasedata gettCustCustbasedata() {
		return tCustCustbasedata;
	}

	public void settCustCustbasedata(CustCustbasedata tCustCustbasedata) {
		this.tCustCustbasedata = tCustCustbasedata;
	}

	public List<CustAccount> gettCustAccount() {
		return tCustAccount;
	}

	public void settCustAccount(List<CustAccount> tCustAccount) {
		this.tCustAccount = tCustAccount;
	}

	public List<CustContract> gettCustContract() {
		return tCustContract;
	}

	public void settCustContract(List<CustContract> tCustContract) {
		this.tCustContract = tCustContract;
	}

	public List<CustCustlinkman> gettCustCustlinkman() {
		return tCustCustlinkman;
	}

	public void settCustCustlinkman(List<CustCustlinkman> tCustCustlinkman) {
		this.tCustCustlinkman = tCustCustlinkman;
	}

	public List<CustShuttleaddress> gettCustShuttleaddress() {
		return tCustShuttleaddress;
	}

	public void settCustShuttleaddress(List<CustShuttleaddress> tCustShuttleaddress) {
		this.tCustShuttleaddress = tCustShuttleaddress;
	}

	public List<CustPreferenceaddress> gettCustPreferenceaddress() {
		return tCustPreferenceaddress;
	}

	public void settCustPreferenceaddress(List<CustPreferenceaddress> tCustPreferenceaddress) {
		this.tCustPreferenceaddress = tCustPreferenceaddress;
	}

	public List<CustContractdept> gettCustContractdept() {
		return tCustContractdept;
	}

	public void settCustContractdept(List<CustContractdept> tCustContractdept) {
		this.tCustContractdept = tCustContractdept;
	}

	public List<CustPreferential> gettCustPreferential() {
		return tCustPreferential;
	}

	public void settCustPreferential(List<CustPreferential> tCustPreferential) {
		this.tCustPreferential = tCustPreferential;
	}

	public List<CustContractTax> gettCustContractTax() {
		return tCustContractTax;
	}

	public void settCustContractTax(List<CustContractTax> tCustContractTax) {
		this.tCustContractTax = tCustContractTax;
	}
}
