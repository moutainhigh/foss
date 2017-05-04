package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.util.Date;
import java.util.List;
/**
 *@项目：合伙人项目
 *@功能：定义“发票对账单新增”查询返回值的Dto
 *@author:218371-foss-zhaoyanjun
 *@date:2016-02-26下午15:04
 */
public class InvoiceManagementAddDto {
	private static final long serialVersionUID = -1827529959996184638L;
	//uuid
	String id;
	
	//查询tab页
	String queryPage;
	
	//制单时间
	Date createTime;
	
	//客户编码
	String customerCode;
	
	//账期开始
	Date periodBeginDate;
	
	//所属公司
	String companyName;
	
	//客户名称
	String customerName;
	
	//账期结束
	Date periodEndDate;
	
	//所属部门
	String createOrgName;
	
	//对账单号
	String statementBillNo;
	
	//部门标杆编码
	String sectorBenchmarkingEncoding;
	
	//发票抬头
	String invoiceHeadCode;
	
	//税务登记号
	String taxId;
	
	//本期总金额
	double totleFee;
	
	//运输性质
	String transportProperties;
	
	//部门编码
	String departmentCode;
	
	//大区编码
	String bigRegion;
	
	//小区编码
	String smallRegion;
	
	//明细单据编号或运单号
	private String[] billDetailNos;
	
	//按对账单号查询
	private String[] statementNos;
	
	//列表信息
	List<InvoiceManagementAddListDto> list;
	
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
	
	//发票标记
	String invoiceMark;
	
	//除去HH的对账单单号
	int statementNumberWithoutHh;
	
	//发票状态
	String invoiceStatus;
	
	//是否处于删除状态
	String isDelete;
	
	//当前部门编码
	String currentDeptNo;
	
	//筛选条件（单子类型）
	List<String> manycheck;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQueryPage() {
		return queryPage;
	}

	public void setQueryPage(String queryPage) {
		this.queryPage = queryPage;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Date getPeriodBeginDate() {
		return periodBeginDate;
	}

	public void setPeriodBeginDate(Date periodBeginDate) {
		this.periodBeginDate = periodBeginDate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getPeriodEndDate() {
		return periodEndDate;
	}

	public void setPeriodEndDate(Date periodEndDate) {
		this.periodEndDate = periodEndDate;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getStatementBillNo() {
		return statementBillNo;
	}

	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
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

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getBigRegion() {
		return bigRegion;
	}

	public void setBigRegion(String bigRegion) {
		this.bigRegion = bigRegion;
	}

	public String getSmallRegion() {
		return smallRegion;
	}

	public void setSmallRegion(String smallRegion) {
		this.smallRegion = smallRegion;
	}

	public String[] getBillDetailNos() {
		return billDetailNos;
	}

	public void setBillDetailNos(String[] billDetailNos) {
		this.billDetailNos = billDetailNos;
	}

	public String[] getStatementNos() {
		return statementNos;
	}

	public void setStatementNos(String[] statementNos) {
		this.statementNos = statementNos;
	}

	public List<InvoiceManagementAddListDto> getList() {
		return list;
	}

	public void setList(List<InvoiceManagementAddListDto> list) {
		this.list = list;
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

	public String getInvoiceMark() {
		return invoiceMark;
	}

	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	public int getStatementNumberWithoutHh() {
		return statementNumberWithoutHh;
	}

	public void setStatementNumberWithoutHh(int statementNumberWithoutHh) {
		this.statementNumberWithoutHh = statementNumberWithoutHh;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getCurrentDeptNo() {
		return currentDeptNo;
	}

	public void setCurrentDeptNo(String currentDeptNo) {
		this.currentDeptNo = currentDeptNo;
	}

	public List<String> getManycheck() {
		return manycheck;
	}

	public void setManycheck(List<String> manycheck) {
		this.manycheck = manycheck;
	}
}
