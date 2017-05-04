package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.util.Date;

/**
 *@项目：合伙人项目
 *@功能：定义“发票对账单新增”查询返回值的list集合Dto
 *@author:218371-foss-zhaoyanjun
 *@date:2016-02-26下午15:04
 */
public class InvoiceManagementAddListDto {
	private static final long serialVersionUID = -1827529959996184639L;
	//应收单id
	String id;
	
	//应收单号
	String receivableNumber;
	
	//业务日期
	Date businessDate;
	
	//运单号
	String waybillNumber;
	
	//总金额
	double totleFee;
	
	//运输性质
	String transportProperties;
	
	//单据子类型
	String subTypesOfDocuments;
	
	//客户名称
	String customerName;
	
	//客户编码
	String customerCode;
	
	//部门名称
	String departmentName;
	
	//部门编码
	String departmentCode;
	
	//记账日期
	Date dateOfEntry;
	
	//签收日期
	Date dateOfReceipt;
	
	//目的站
	String destinationStation;
	
	//创建时间
	Date createTime;
	
	//发票申请状态
	String invoiceStatus;
	
	//所属公司
	String companyName;
	
	//部门标杆编码
	String sectorBenchmarkingEncoding;
	
	//发票抬头
	String invoiceHeadCode;
	
	//税务登记号
	String taxId;
	
	//发票标记
	String invoiceMark;
	
	//注册地
	String registeredAddress;
		
	//注册电话
	String registeredTelephone;
		
	//开户行
	String bank;
		
	//开户银行帐号
	String accountBank;
		
	//是否为一般纳税人
	String isGeneralTaxpayer;
	
	//到达部门编码
	String destOrgCode;

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	public String getWaybillNumber() {
		return waybillNumber;
	}

	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}

	public double getTotleFee() {
		return totleFee;
	}

	public void setTotleFee(double totleFee) {
		this.totleFee = totleFee;
	}

	public String getTransportProperties() {
		return transportProperties;
	}

	public void setTransportProperties(String transportProperties) {
		this.transportProperties = transportProperties;
	}

	public String getSubTypesOfDocuments() {
		return subTypesOfDocuments;
	}

	public void setSubTypesOfDocuments(String subTypesOfDocuments) {
		this.subTypesOfDocuments = subTypesOfDocuments;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public Date getDateOfEntry() {
		return dateOfEntry;
	}

	public void setDateOfEntry(Date dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}

	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}

	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}

	public String getDestinationStation() {
		return destinationStation;
	}

	public void setDestinationStation(String destinationStation) {
		this.destinationStation = destinationStation;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getReceivableNumber() {
		return receivableNumber;
	}

	public void setReceivableNumber(String receivableNumber) {
		this.receivableNumber = receivableNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSectorBenchmarkingEncoding() {
		return sectorBenchmarkingEncoding;
	}

	public void setSectorBenchmarkingEncoding(String sectorBenchmarkingEncoding) {
		this.sectorBenchmarkingEncoding = sectorBenchmarkingEncoding;
	}

	public String getInvoiceHeadCode() {
		return invoiceHeadCode;
	}

	public void setInvoiceHeadCode(String invoiceHeadCode) {
		this.invoiceHeadCode = invoiceHeadCode;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getInvoiceMark() {
		return invoiceMark;
	}

	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getRegisteredTelephone() {
		return registeredTelephone;
	}

	public void setRegisteredTelephone(String registeredTelephone) {
		this.registeredTelephone = registeredTelephone;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getIsGeneralTaxpayer() {
		return isGeneralTaxpayer;
	}

	public void setIsGeneralTaxpayer(String isGeneralTaxpayer) {
		this.isGeneralTaxpayer = isGeneralTaxpayer;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
