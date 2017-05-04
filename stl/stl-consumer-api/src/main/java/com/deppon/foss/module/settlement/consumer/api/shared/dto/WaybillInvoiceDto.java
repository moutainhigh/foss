package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class WaybillInvoiceDto implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 业务类型（即快递代理01还是零担02，（不是快递代理即是零担），必录，不能为空）
	 */
	private String product;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 小票单号
	 */
	private String otherRevenueNo;
	
	/**
	 * 小票客户编码
	 */
	private String otherRevenueCustomerCode;
	
	/**
	 * 发票标记(运输专票11%（01），非运输专票（02）)
	 */
	private String invoiceMark;
	
	/**
	 * 预付金额
	 */
	private BigDecimal prePayAmount;
	
	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;
	
	/**
	 * 发货方客户编码
	 */
	private String deliverCustomerCode;
	
	/**
	 * 收货方客户编码
	 */
	private String receiveCustomerCode;
	
	/**
	 * 客户名称
	 */
	private String customerName;
	
	/**
	 * 发货部门(Foss中收货部门编码)
	 */
	private String receiveOrgCode;
	
	/**
	 * 到达部门编码
	 */
	private String descOrgCode;
	
	/**
	 * 开单时间
	 */
	private Date billTime;
	
	/**
	 * 运输路线
	 */
	private String transferLine;
	
	/**
	 * 出发催款部门标杆编码
	 */
    protected String receiveDunningDeptCode;
    
    /**
     * 到达催款部门
     */
    protected String descDunningDeptCode;
    
    /**
     * 出发合同部门
     */
    protected String receiveContractDeptCode;
    
    /**
     * 到达合同部门
     */
    protected String descContractDeptCode;
    
    /**
     * 对应的对账单号
     */
  	String statementBillNo;
  	
  	/**
  	 * 发票抬头
  	 */
  	String invoiceHeadCode;
  	
  	/**
  	 * 税务登记号
  	 */
  	String taxId;

  	/**
  	 * 注册地
  	 */
  	String registeredAddress;
  			
  	/**
  	 * 注册电话
  	 */
  	String registeredTelephone;
  			
  	/**
  	 * 开户行
  	 */
  	String bank;
  			
  	/**
  	 * 开户银行帐号
  	 */
  	String accountBank;
  	
  	/**
  	 * 是否为一般纳税人
  	 */
  	String isGeneralTaxpayer;


	/**
	 * 运单集合
	 */
	private transient List<WaybillInvoiceDto> waybillDtoList;
	/**
	 * 合并运单集合
	 */
	private transient List<WaybillInvoiceDto> mergeWaybillDtoList;
	/**
	 * 已经合并过的运单号集合
	 */
	private  List<String> mergedWaybillNoList;

	/**
	 * 小票集合
	 */
	private transient List<WaybillInvoiceDto> otherRevenueDtoList;
	
	/**
	 * 合伙人对账单号集合
	 */
	private transient List<WaybillInvoiceDto> HhStatementDtoList;


	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the otherRevenueNo
	 */
	public String getOtherRevenueNo() {
		return otherRevenueNo;
	}

	/**
	 * @param otherRevenueNo the otherRevenueNo to set
	 */
	public void setOtherRevenueNo(String otherRevenueNo) {
		this.otherRevenueNo = otherRevenueNo;
	}

	/**
	 * @return the otherRevenueCustomerCode
	 */
	public String getOtherRevenueCustomerCode() {
		return otherRevenueCustomerCode;
	}

	/**
	 * @param otherRevenueCustomerCode the otherRevenueCustomerCode to set
	 */
	public void setOtherRevenueCustomerCode(String otherRevenueCustomerCode) {
		this.otherRevenueCustomerCode = otherRevenueCustomerCode;
	}

	/**
	 * @return the invoiceMark
	 */
	public String getInvoiceMark() {
		return invoiceMark;
	}

	/**
	 * @param invoiceMark the invoiceMark to set
	 */
	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	/**
	 * @return the prePayAmount
	 */
	public BigDecimal getPrePayAmount() {
		return prePayAmount;
	}

	/**
	 * @param prePayAmount the prePayAmount to set
	 */
	public void setPrePayAmount(BigDecimal prePayAmount) {
		this.prePayAmount = prePayAmount;
	}

	/**
	 * @return the toPayAmount
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * @param toPayAmount the toPayAmount to set
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	/**
	 * @return the deliverCustomerCode
	 */
	public String getDeliverCustomerCode() {
		return deliverCustomerCode;
	}

	/**
	 * @param deliverCustomerCode the deliverCustomerCode to set
	 */
	public void setDeliverCustomerCode(String deliverCustomerCode) {
		this.deliverCustomerCode = deliverCustomerCode;
	}

	/**
	 * @return the receiveCustomerCode
	 */
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	/**
	 * @param receiveCustomerCode the receiveCustomerCode to set
	 */
	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the receiveOrgCode
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * @param receiveOrgCode the receiveOrgCode to set
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * @return the descOrgCode
	 */
	public String getDescOrgCode() {
		return descOrgCode;
	}

	/**
	 * @param descOrgCode the descOrgCode to set
	 */
	public void setDescOrgCode(String descOrgCode) {
		this.descOrgCode = descOrgCode;
	}

	/**
	 * @return the billTime
	 */
	public Date getBillTime() {
		return billTime;
	}

	/**
	 * @param billTime the billTime to set
	 */
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	/**
	 * @return the transferLine
	 */
	public String getTransferLine() {
		return transferLine;
	}

	/**
	 * @param transferLine the transferLine to set
	 */
	public void setTransferLine(String transferLine) {
		this.transferLine = transferLine;
	}

	/**
	 * @return the waybillDtoList
	 */
	public List<WaybillInvoiceDto> getWaybillDtoList() {
		return waybillDtoList;
	}

	/**
	 * @param waybillDtoList the waybillDtoList to set
	 */
	public void setWaybillDtoList(List<WaybillInvoiceDto> waybillDtoList) {
		this.waybillDtoList = waybillDtoList;
	}

	/**
	 * @return the otherRevenueDtoList
	 */
	public List<WaybillInvoiceDto> getOtherRevenueDtoList() {
		return otherRevenueDtoList;
	}

	/**
	 * @param otherRevenueDtoList the otherRevenueDtoList to set
	 */
	public void setOtherRevenueDtoList(List<WaybillInvoiceDto> otherRevenueDtoList) {
		this.otherRevenueDtoList = otherRevenueDtoList;
	}

	public String getReceiveDunningDeptCode() {
		return receiveDunningDeptCode;
	}

	public void setReceiveDunningDeptCode(String receiveDunningDeptCode) {
		this.receiveDunningDeptCode = receiveDunningDeptCode;
	}

	public String getDescDunningDeptCode() {
		return descDunningDeptCode;
	}

	public void setDescDunningDeptCode(String descDunningDeptCode) {
		this.descDunningDeptCode = descDunningDeptCode;
	}

	public String getReceiveContractDeptCode() {
		return receiveContractDeptCode;
	}

	public void setReceiveContractDeptCode(String receiveContractDeptCode) {
		this.receiveContractDeptCode = receiveContractDeptCode;
	}

	public String getDescContractDeptCode() {
		return descContractDeptCode;
	}

	public void setDescContractDeptCode(String descContractDeptCode) {
		this.descContractDeptCode = descContractDeptCode;
	}

	public List<WaybillInvoiceDto> getHhStatementDtoList() {
		return HhStatementDtoList;
	}

	public void setHhStatementDtoList(List<WaybillInvoiceDto> hhStatementDtoList) {
		HhStatementDtoList = hhStatementDtoList;
	}

	public String getStatementBillNo() {
		return statementBillNo;
	}

	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
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

	public List<WaybillInvoiceDto> getMergeWaybillDtoList() {
		return mergeWaybillDtoList;
	}

	public void setMergeWaybillDtoList(List<WaybillInvoiceDto> mergeWaybillDtoList) {
		this.mergeWaybillDtoList = mergeWaybillDtoList;
	}

	public List<String> getMergedWaybillNoList() {
		return mergedWaybillNoList;
	}

	public void setMergedWaybillNoList(List<String> mergedWaybillNoList) {
		this.mergedWaybillNoList = mergedWaybillNoList;
	}
}
