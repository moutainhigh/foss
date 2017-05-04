package com.deppon.foss.module.settlement.closing.api.shared.inteface.domain;

public class BankPayInfo {
	// 支付方式：1-电汇，2-不限
	private String payWay;
	// 开户人姓名
	private String accountName;
	// 账户性质:(PUBLIC_ACCOUNT-对公账户、PRIVATE_ACCOUNT-对私账户、BACKUP_ACCOUNT-收银员账户)
	private String accountProperty;
	// 开户人账号
	private String accountNumber;
	// 开户行编码
	private String bankCode;
	// 开户行名称
	private String bankName;
	// 支行编码
	private String subbranchCode;
	// 支行名称
	private String subbranchName;
	// 开户行所在省份编码
	private String bankProvinceCode;
	// 开户行所在省份名称
	private String bankProvinceName;
	// 开户行所在城市编码
	private String bankCityCode;
	// 开户行所在城市名称
	private String bankCityName;

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountProperty() {
		return accountProperty;
	}

	public void setAccountProperty(String accountProperty) {
		this.accountProperty = accountProperty;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSubbranchCode() {
		return subbranchCode;
	}

	public void setSubbranchCode(String subbranchCode) {
		this.subbranchCode = subbranchCode;
	}

	public String getSubbranchName() {
		return subbranchName;
	}

	public void setSubbranchName(String subbranchName) {
		this.subbranchName = subbranchName;
	}

	public String getBankProvinceCode() {
		return bankProvinceCode;
	}

	public void setBankProvinceCode(String bankProvinceCode) {
		this.bankProvinceCode = bankProvinceCode;
	}

	public String getBankProvinceName() {
		return bankProvinceName;
	}

	public void setBankProvinceName(String bankProvinceName) {
		this.bankProvinceName = bankProvinceName;
	}

	public String getBankCityCode() {
		return bankCityCode;
	}

	public void setBankCityCode(String bankCityCode) {
		this.bankCityCode = bankCityCode;
	}

	public String getBankCityName() {
		return bankCityName;
	}

	public void setBankCityName(String bankCityName) {
		this.bankCityName = bankCityName;
	}

}
