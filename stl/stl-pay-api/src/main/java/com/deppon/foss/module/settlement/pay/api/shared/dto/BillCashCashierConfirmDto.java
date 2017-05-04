package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 确认收银Dto
 * @author foss-pengzhen
 * @date 2012-12-11 上午11:16:02
 * @since
 * @version
 */
public class BillCashCashierConfirmDto implements Serializable {

	/**
	 * Dto序列号
	 */
	private static final long serialVersionUID = -8288761080015694172L;

	/**
	 * id
	 */
	private String id;
	
	/**
	 * 查询页签
	 */
	private String queryByTab;
	
	/**
	 * 单号
	 */
	private String billNo;
	
	/**
	 * 单号集合
	 */
	private List<String> billNos;
	
	/**
	 * 来源单号
	 */
	private String sourceBillNo;
	
	/**
	 * 来源单号集合
	 */
	private List<String> sourceBillNos;
	
	/**
	 * 来源单号
	 */
	private String waybillNo;
	
	/**
	 * 来源单号集合
	 */
	private List<String> waybillNos;
	
	/**
	 * 核销单上的运单号
	 */
	private String endWaybillNo;
	
	private String beginNo;
	
	private String writeoffType;
	
	/**
	 * 单据类型
	 */
	private String billType;
	
	private String empCode;
	
	/**
	 * 金额
	 */
	private BigDecimal amount;
	
	/**
	 * 收款方式
	 */
	private String paymentType;
	
	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;
	
	/**
	 * 制单人工号
	 */
	private String createUserCode;

	/**
	 * 制单人名称
	 */
	private String createUserName;
	
	/**
	 * 版本号
	 */
	private Short versionNo;
	
	/**
	 * 单据状态
	 */
	private String status;
	
	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 收银确认人工号
	 */
	private String cashConfirmUserCode;

	/**
	 * 收银确认人名称
	 */
	private String cashConfirmUserName;
	
	/**
	 * 收银确认时间
	 */
	private Date cashConfirmTime;
	
	/**
	 * 收入部门编码
	 */
	private String generatingOrgCode;

	/**
	 * 收入部门名称
	 */
	private String generatingOrgName;

	/**
	 * 收款部门code
	 */
	private String collectionOrgCode;
	
	/**
	 * 收款部门名称
	 */
	private String collectionOrgName;
	
	/**
	 * 收银部门编码
	 */
	private String createOrgCode;

	/**
	 * 收银部门名称
	 */
	private String createOrgName;
	
	/**
	 * 业务开始时间
	 */
	private Date startBusinessDate;
	
	/**
	 * 记账日期
	 */
	private Date accountDate;
	
	/**
	 * 业务日期
	 */
	private Date businessDate;
	
	/**
	 * 业务结束时间
	 */
	private Date endBusinessDate;
	
	/**
	 * 现金总条数
	 */
	private int totalCashNum;
	
	/**
	 * 银行卡总条数
	 */
	private int totalCardNum;
	
	/**
	 * 汇款总条数
	 */
	private int totalTelegpaphTransferNum;
	
	/**
	 * 总条数
	 */
	private long totalNum;
	
	
	/**
	 * 现金总金额
	 */
	private BigDecimal totalCashAmount;
	
	/**
	 * 银行卡总金额
	 */
	private BigDecimal totalCardAmount;
	
	/**
	 * 汇款总金额
	 */
	private BigDecimal totalTelegpaphTransferAmount;
	
	/**
	 * 总金额
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 银联交易流水号
	 */
	private String batchNo;

	/**
	 * 银联交易流水号集合
	 */
	private List<String> batchNos;
	
	/**
	 * POS串号
	 */
	private String posSerialNum;

    /**
     * 结清方式
     */
    private String settleApproach;

    /**
     * 是否存在代收货款还款单未收银
     */

    public String getSettleApproach() {
        return settleApproach;
    }

    public void setSettleApproach(String settleApproach) {
        this.settleApproach = settleApproach;
    }
	/**
	 * @return  the id
	 */
	public String getId() {
		return id;
	}

	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	
	/**
	 * @return  the billNo
	 */
	public String getBillNo() {
		return billNo;
	}

	
	/**
	 * @param billNo the billNo to set
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	
	
	/**
	 * @return  the sourceBillNo
	 */
	public String getSourceBillNo() {
		return sourceBillNo;
	}


	
	/**
	 * @param sourceBillNo the sourceBillNo to set
	 */
	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}


	/**
	 * @return  the billNos
	 */
	public List<String> getBillNos() {
		return billNos;
	}

	
	/**
	 * @param billNos the billNos to set
	 */
	public void setBillNos(List<String> billNos) {
		this.billNos = billNos;
	}

	
	
	
	/**
	 * @return  the sourceBillNos
	 */
	public List<String> getSourceBillNos() {
		return sourceBillNos;
	}


	
	/**
	 * @param sourceBillNos the sourceBillNos to set
	 */
	public void setSourceBillNos(List<String> sourceBillNos) {
		this.sourceBillNos = sourceBillNos;
	}


	/**
	 * @return  the queryByTab
	 */
	public String getQueryByTab() {
		return queryByTab;
	}


	
	/**
	 * @param queryByTab the queryByTab to set
	 */
	public void setQueryByTab(String queryByTab) {
		this.queryByTab = queryByTab;
	}


	/**
	 * @return  the billType
	 */
	public String getBillType() {
		return billType;
	}

	
	/**
	 * @param billType the billType to set
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	
	/**
	 * @return  the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
	
	/**
	 * @return  the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}


	
	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}


	
	/**
	 * @return  the waybillNo
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
	 * @return  the waybillNos
	 */
	public List<String> getWaybillNos() {
		return waybillNos;
	}


	
	/**
	 * @param waybillNos the waybillNos to set
	 */
	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}


	/**
	 * @return  the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	
	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	
	/**
	 * @return  the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	
	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	
	/**
	 * @return  the customerName
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
	 * @return  the createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	
	/**
	 * @param createUserCode the createUserCode to set
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	
	/**
	 * @return  the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	
	/**
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	
	/**
	 * @return  the versionNo
	 */
	public Short getVersionNo() {
		return versionNo;
	}

	
	/**
	 * @param versionNo the versionNo to set
	 */
	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
	}

	
	/**
	 * @return  the status
	 */
	public String getStatus() {
		return status;
	}

	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	
	/**
	 * @return  the active
	 */
	public String getActive() {
		return active;
	}

	
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	
	/**
	 * @return  the cashConfirmUserCode
	 */
	public String getCashConfirmUserCode() {
		return cashConfirmUserCode;
	}

	
	/**
	 * @param cashConfirmUserCode the cashConfirmUserCode to set
	 */
	public void setCashConfirmUserCode(String cashConfirmUserCode) {
		this.cashConfirmUserCode = cashConfirmUserCode;
	}

	
	/**
	 * @return  the cashConfirmUserName
	 */
	public String getCashConfirmUserName() {
		return cashConfirmUserName;
	}

	
	/**
	 * @param cashConfirmUserName the cashConfirmUserName to set
	 */
	public void setCashConfirmUserName(String cashConfirmUserName) {
		this.cashConfirmUserName = cashConfirmUserName;
	}

	
	/**
	 * @return  the cashConfirmTime
	 */
	public Date getCashConfirmTime() {
		return cashConfirmTime;
	}

	
	/**
	 * @param cashConfirmTime the cashConfirmTime to set
	 */
	public void setCashConfirmTime(Date cashConfirmTime) {
		this.cashConfirmTime = cashConfirmTime;
	}

	
	/**
	 * @return  the generatingOrgCode
	 */
	public String getGeneratingOrgCode() {
		return generatingOrgCode;
	}

	
	/**
	 * @param generatingOrgCode the generatingOrgCode to set
	 */
	public void setGeneratingOrgCode(String generatingOrgCode) {
		this.generatingOrgCode = generatingOrgCode;
	}

	
	/**
	 * @return  the generatingOrgName
	 */
	public String getGeneratingOrgName() {
		return generatingOrgName;
	}

	
	/**
	 * @param generatingOrgName the generatingOrgName to set
	 */
	public void setGeneratingOrgName(String generatingOrgName) {
		this.generatingOrgName = generatingOrgName;
	}

	
	/**
	 * @return  the collectionOrgCode
	 */
	public String getCollectionOrgCode() {
		return collectionOrgCode;
	}

	
	/**
	 * @param collectionOrgCode the collectionOrgCode to set
	 */
	public void setCollectionOrgCode(String collectionOrgCode) {
		this.collectionOrgCode = collectionOrgCode;
	}

	
	/**
	 * @return  the collectionOrgName
	 */
	public String getCollectionOrgName() {
		return collectionOrgName;
	}

	
	/**
	 * @param collectionOrgName the collectionOrgName to set
	 */
	public void setCollectionOrgName(String collectionOrgName) {
		this.collectionOrgName = collectionOrgName;
	}

	
	/**
	 * @return  the createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	
	/**
	 * @param createOrgCode the createOrgCode to set
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	
	/**
	 * @return  the createOrgName
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}

	
	/**
	 * @param createOrgName the createOrgName to set
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	
	/**
	 * @return  the startBusinessDate
	 */
	public Date getStartBusinessDate() {
		return startBusinessDate;
	}

	
	/**
	 * @param startBusinessDate the startBusinessDate to set
	 */
	public void setStartBusinessDate(Date startBusinessDate) {
		this.startBusinessDate = startBusinessDate;
	}

	
	/**
	 * @return  the accountDate
	 */
	public Date getAccountDate() {
		return accountDate;
	}

	
	/**
	 * @param accountDate the accountDate to set
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	
	/**
	 * @return  the businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	
	/**
	 * @param businessDate the businessDate to set
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	
	/**
	 * @return  the endBusinessDate
	 */
	public Date getEndBusinessDate() {
		return endBusinessDate;
	}

	
	/**
	 * @param endBusinessDate the endBusinessDate to set
	 */
	public void setEndBusinessDate(Date endBusinessDate) {
		this.endBusinessDate = endBusinessDate;
	}

	
	/**
	 * @return  the totalCashNum
	 */
	public int getTotalCashNum() {
		return totalCashNum;
	}

	
	/**
	 * @param totalCashNum the totalCashNum to set
	 */
	public void setTotalCashNum(int totalCashNum) {
		this.totalCashNum = totalCashNum;
	}

	
	/**
	 * @return  the totalCardNum
	 */
	public int getTotalCardNum() {
		return totalCardNum;
	}

	
	/**
	 * @param totalCardNum the totalCardNum to set
	 */
	public void setTotalCardNum(int totalCardNum) {
		this.totalCardNum = totalCardNum;
	}

	
	/**
	 * @return  the totalTelegpaphTransferNum
	 */
	public int getTotalTelegpaphTransferNum() {
		return totalTelegpaphTransferNum;
	}

	
	/**
	 * @param totalTelegpaphTransferNum the totalTelegpaphTransferNum to set
	 */
	public void setTotalTelegpaphTransferNum(int totalTelegpaphTransferNum) {
		this.totalTelegpaphTransferNum = totalTelegpaphTransferNum;
	}

	
	/**
	 * @return  the totalNum
	 */
	public long getTotalNum() {
		return totalNum;
	}

	
	/**
	 * @param totalNum the totalNum to set
	 */
	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
	}

	
	/**
	 * @return  the totalCashAmount
	 */
	public BigDecimal getTotalCashAmount() {
		return totalCashAmount;
	}

	
	/**
	 * @param totalCashAmount the totalCashAmount to set
	 */
	public void setTotalCashAmount(BigDecimal totalCashAmount) {
		this.totalCashAmount = totalCashAmount;
	}

	
	/**
	 * @return  the totalCardAmount
	 */
	public BigDecimal getTotalCardAmount() {
		return totalCardAmount;
	}

	
	/**
	 * @param totalCardAmount the totalCardAmount to set
	 */
	public void setTotalCardAmount(BigDecimal totalCardAmount) {
		this.totalCardAmount = totalCardAmount;
	}

	
	/**
	 * @return  the totalTelegpaphTransferAmount
	 */
	public BigDecimal getTotalTelegpaphTransferAmount() {
		return totalTelegpaphTransferAmount;
	}

	/**
	 * @param totalTelegpaphTransferAmount the totalTelegpaphTransferAmount to set
	 */
	public void setTotalTelegpaphTransferAmount(
			BigDecimal totalTelegpaphTransferAmount) {
		this.totalTelegpaphTransferAmount = totalTelegpaphTransferAmount;
	}

	/**
	 * @return  the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}


	
	
	/**
	 * @return  the writeoffType
	 */
	public String getWriteoffType() {
		return writeoffType;
	}


	
	/**
	 * @param writeoffType the writeoffType to set
	 */
	public void setWriteoffType(String writeoffType) {
		this.writeoffType = writeoffType;
	}


	
	/**
	 * @return  the beginNo
	 */
	public String getBeginNo() {
		return beginNo;
	}


	
	/**
	 * @param beginNo the beginNo to set
	 */
	public void setBeginNo(String beginNo) {
		this.beginNo = beginNo;
	}


	/**
	 * @return  the endWaybillNo
	 */
	public String getEndWaybillNo() {
		return endWaybillNo;
	}


	
	/**
	 * @param endWaybillNo the endWaybillNo to set
	 */
	public void setEndWaybillNo(String endWaybillNo) {
		this.endWaybillNo = endWaybillNo;
	}


	/**
	 * @GET
	 * @return batchNo
	 */
	public String getBatchNo() {
		/*
		 *@get
		 *@ return batchNo
		 */
		return batchNo;
	}


	/**
	 * @SET
	 * @param batchNo
	 */
	public void setBatchNo(String batchNo) {
		/*
		 *@set
		 *@this.batchNo = batchNo
		 */
		this.batchNo = batchNo;
	}


	/**
	 * @GET
	 * @return batchNos
	 */
	public List<String> getBatchNos() {
		/*
		 *@get
		 *@ return batchNos
		 */
		return batchNos;
	}


	/**
	 * @SET
	 * @param batchNos
	 */
	public void setBatchNos(List<String> batchNos) {
		/*
		 *@set
		 *@this.batchNos = batchNos
		 */
		this.batchNos = batchNos;
	}


	/**
	 * @GET
	 * @return posSerialNum
	 */
	public String getPosSerialNum() {
		/*
		 *@get
		 *@ return posSerialNum
		 */
		return posSerialNum;
	}


	/**
	 * @SET
	 * @param posSerialNum
	 */
	public void setPosSerialNum(String posSerialNum) {
		/*
		 *@set
		 *@this.posSerialNum = posSerialNum
		 */
		this.posSerialNum = posSerialNum;
	}

}
