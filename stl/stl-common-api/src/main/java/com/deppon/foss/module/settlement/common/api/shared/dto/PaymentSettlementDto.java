package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 接送货1、通知客户；2、结清货款调用 存放运单信息和到达部门信息Dto
 * 
 * 到付运费转临欠/月结
 * @author dp-wujiangtao
 * @date 2012-10-15 上午9:10:52
 * @since
 * @version
 */
public class PaymentSettlementDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * businessId
	 * VTS结清货款时候，传输给FOSS结算唯一标识
	 * @author 218392 zhangyongxue 2016-06-27  20:56:20
	 */
	private String businessId;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 付款方式
	 */
	private String paymentType;

	/**
	 * 到达部门编码
	 */
	private String destOrgCode;

	/**
	 * 到达部门名称
	 */
	private String destOrgName;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 发生业务日期
	 */
	private Date businessDate;

	/**
	 * 是否反操作
	 */
	private boolean isRevers;

    /**
     * 结清方式
     */
    private String settleApproach;

	/************************* 实收货款需要属性 ****************************************/

	/**
	 * 实收代收货款费用
	 */
	private BigDecimal codFee;

	/**
	 * 实收到付运费
	 */
	private BigDecimal toPayFee;

	/**
	 * 汇款编号
	 */
	private String paymentNo;

	/**
	 * 来源单号-存放（到达实收单号）
	 */
	private String sourceBillNo;

	/**
	 * 币种
	 */
	private String currencyCode;
	
	/**
	 * POS串号
	 */
	private String posSerialNum;
	
	/**
	 * 银行交易流水号
	 */
	private String batchNo;
	
	/************************* 快递员卡新增 ****************************************/
	/**
	 * 是否快递
	 */
	private String isExpress;
	
	/**
	 * 派送快递员工号
	 */
	private String deliverExpressCode;
	
	/**
	 * 派送快递员名称
	 */	
	private String deliverExpressName;
	
	/**
     * 结清类型
     */
    private String repaymentType;
    /** 
  	 * 是否合伙人运单 
    */ 
    private String isPtp;
	
	

    public String getIsPtp() {
		return isPtp;
	}

	public void setIsPtp(String isPtp) {
		this.isPtp = isPtp;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public String getDeliverExpressCode() {
		return deliverExpressCode;
	}

	public void setDeliverExpressCode(String deliverExpressCode) {
		this.deliverExpressCode = deliverExpressCode;
	}

	public String getDeliverExpressName() {
		return deliverExpressName;
	}

	public void setDeliverExpressName(String deliverExpressName) {
		this.deliverExpressName = deliverExpressName;
	}

	public String getSettleApproach() {
        return settleApproach;
    }

    public void setSettleApproach(String settleApproach) {
        this.settleApproach = settleApproach;
    }

    /**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @param destOrgCode
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * @param destOrgName
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * @return customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return isRevers
	 */
	public boolean isRevers() {
		return isRevers;
	}

	/**
	 * @param isRevers
	 */
	public void setRevers(boolean isRevers) {
		this.isRevers = isRevers;
	}

	/**
	 * @return codFee
	 */
	public BigDecimal getCodFee() {
		return codFee;
	}

	/**
	 * @param codFee
	 */
	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}

	/**
	 * @return toPayFee
	 */
	public BigDecimal getToPayFee() {
		return toPayFee;
	}

	/**
	 * @param toPayFee
	 */
	public void setToPayFee(BigDecimal toPayFee) {
		this.toPayFee = toPayFee;
	}

	/**
	 * @return paymentNo
	 */
	public String getPaymentNo() {
		return paymentNo;
	}

	/**
	 * @param paymentNo
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	/**
	 * @return sourceBillNo
	 */
	public String getSourceBillNo() {
		return sourceBillNo;
	}

	/**
	 * @param sourceBillNo
	 */
	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	/**
	 * @return currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

}
