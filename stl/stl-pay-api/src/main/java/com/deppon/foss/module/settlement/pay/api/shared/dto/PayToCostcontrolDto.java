package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.esb.inteface.domain.payment.ExpenseDetail;
import com.deppon.esb.inteface.domain.payment.StowageEntity;
import com.deppon.fssc.inteface.domain.payment.StowageDetail;

/**
 * 给费控传递参数
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-11-30 上午9:35:10
 */
public class PayToCostcontrolDto implements Serializable {

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -3174330403431221365L;

	/**
	 * 付款编号
	 */
	private String paymentBillNo;

	/**
	 * 付款部门名称
	 */
	private String payBillDeptName;

	/**
	 * 付款部门标杆编码
	 */
	private String payBillDeptNo;

	/**
	 * 付款总金额
	 */
	private BigDecimal payBillAmount;

	/**
	 * 申请部门所属子公司名称
	 */
	private String payBillComNo;
	
	/**
	 * 申请部门所属子公司名称 --不传递给费控，仅反写付款单用
	 */
	private String payBillComName;

	/**
	 * 银行账号
	 */
	private String payBillBankNo;
	
	/**
	 * 账户类型
	 */
	private String accountType;

	/**
	 * 省份编码
	 */
	private String provinceCode;// accountBankProvince
	
	/**
	 * 省份名称--不传递给费控，仅反写付款单用
	 */
	private String provinceName;

	/**
	 * 城市编码
	 */
	private String cityCode;// accountBankCity
	
	/**
	 * 城市名称--不传递给费控，仅反写付款单用
	 */
	private String cityName;// accountBankCity

	/**
	 * 开户行编码
	 */
	private String bankHqCode;
	
	/**
	 * 开户行名称--不传递给费控，仅反写付款单用
	 */
	private String bankHqName;

	/**
	 * 支行编码（行号）
	 */
	private String bankBranchCode;
	
	/**
	 * 支行名称--不传递给费控，仅反写付款单用
	 */
	private String bankBranchName;// bankCode;

	/**
	 * 收款方姓名
	 */
	private String payBillPayeeName;
	
	/**
	 * 收款方姓名
	 */
	private String payBillPayeeCode;

	/**
	 * 联系方式
	 */
	private String payBillCelephone;

	/**
	 * 申请人工号
	 */
	private String payBillAppNo;

	/**
	 * 最迟汇款日期
	 */
	private Date payBillLastTime;

	/**
	 * 工作流类型
	 */
	private String payBillAppType;

	/**
	 * 支付类型
	 */
	private String payType;

	/**
	 * 调用ESB接口时，需要的批次号
	 */
	private String batchNo;

	/**
	 * 付款单号集合  ---给申请备用金，前台传递付款单号集合用
	 */
	private List<String> paymentNos;
	
	/**
	 * 事由说明
	 */
	private String payBillDiscription;
	
	/**
	 * 费用明细  --费控
	 */
	private List<ExpenseDetail> expenseDetail;
	
	/**
	 * 配载单信息 --费控
	 */
	private List<StowageEntity> StowageEntityList;
	
	/**
	 * 费用明细 --给财务共享使用，该明细比费控明细多一个字段
	 */
	private List<com.deppon.fssc.inteface.domain.payment.ExpenseDetail> expenseDetailToFSSC;
	
	/**********************下面是给财务共享新增字段***************************/
	
	/**
	 * 币种
	 */
	private String currency;
	
	/**
	 * 汇率
	 */
	private BigDecimal exchangeRate;
	
	/**
	 * 是否自动冲借支
	 */
	private String isAutoAbatementLoan;
	
	/**
	 * 配载单明细
	 */
	private List<StowageDetail> stowageDetail;
	
	/**********************保理新增字段传递***************************/
	

	/**
	 * 是否为保理 Y/N
	 */
	private String factoring; 
	/**
	 * 贷款客户编码
	 */
	private String cusCode;
	
	/**
	 * 客户名称
	 */
	private String customerName;
	
	/**
	 * 客户编码
	 */
	private String customerCode;
	
	/**
	 * @return paymentBillNo
	 */
	public String getPaymentBillNo() {
		return paymentBillNo;
	}

	/**
	 * @param paymentBillNo
	 */
	public void setPaymentBillNo(String paymentBillNo) {
		this.paymentBillNo = paymentBillNo;
	}

	/**
	 * @return payBillDeptName
	 */
	public String getPayBillDeptName() {
		return payBillDeptName;
	}

	/**
	 * @param payBillDeptName
	 */
	public void setPayBillDeptName(String payBillDeptName) {
		this.payBillDeptName = payBillDeptName;
	}

	/**
	 * @return payBillDeptNo
	 */
	public String getPayBillDeptNo() {
		return payBillDeptNo;
	}

	/**
	 * @param payBillDeptNo
	 */
	public void setPayBillDeptNo(String payBillDeptNo) {
		this.payBillDeptNo = payBillDeptNo;
	}

	/**
	 * @return payBillAmount
	 */
	public BigDecimal getPayBillAmount() {
		return payBillAmount;
	}

	/**
	 * @param payBillAmount
	 */
	public void setPayBillAmount(BigDecimal payBillAmount) {
		this.payBillAmount = payBillAmount;
	}

	/**
	 * @return payBillComNo
	 */
	public String getPayBillComNo() {
		return payBillComNo;
	}

	/**
	 * @param payBillComNo
	 */
	public void setPayBillComNo(String payBillComNo) {
		this.payBillComNo = payBillComNo;
	}

	/**
	 * @return payBillBankNo
	 */
	public String getPayBillBankNo() {
		return payBillBankNo;
	}

	/**
	 * @param payBillBankNo
	 */
	public void setPayBillBankNo(String payBillBankNo) {
		this.payBillBankNo = payBillBankNo;
	}

	/**
	 * @return payBillPayeeName
	 */
	public String getPayBillPayeeName() {
		return payBillPayeeName;
	}

	/**
	 * @param payBillPayeeName
	 */
	public void setPayBillPayeeName(String payBillPayeeName) {
		this.payBillPayeeName = payBillPayeeName;
	}

	/**
	 * @return payBillCelephone
	 */
	public String getPayBillCelephone() {
		return payBillCelephone;
	}

	/**
	 * @param payBillCelephone
	 */
	public void setPayBillCelephone(String payBillCelephone) {
		this.payBillCelephone = payBillCelephone;
	}

	/**
	 * @return payBillAppNo
	 */
	public String getPayBillAppNo() {
		return payBillAppNo;
	}

	/**
	 * @param payBillAppNo
	 */
	public void setPayBillAppNo(String payBillAppNo) {
		this.payBillAppNo = payBillAppNo;
	}

	/**
	 * @return payBillLastTime
	 */
	public Date getPayBillLastTime() {
		return payBillLastTime;
	}

	/**
	 * @param payBillLastTime
	 */
	public void setPayBillLastTime(Date payBillLastTime) {
		this.payBillLastTime = payBillLastTime;
	}

	/**
	 * @return payBillAppType
	 */
	public String getPayBillAppType() {
		return payBillAppType;
	}

	/**
	 * @param payBillAppType
	 */
	public void setPayBillAppType(String payBillAppType) {
		this.payBillAppType = payBillAppType;
	}

	/**
	 * @return payType
	 */
	public String getPayType() {
		return payType;
	}

	/**
	 * @param payType
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}

	/**
	 * @return batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return expenseDetail
	 */
	public List<ExpenseDetail> getExpenseDetail() {
		return expenseDetail;
	}

	/**
	 * @param expenseDetail
	 */
	public void setExpenseDetail(List<ExpenseDetail> expenseDetail) {
		this.expenseDetail = expenseDetail;
	}

	/**
	 * @return paymentNos
	 */
	public List<String> getPaymentNos() {
		return paymentNos;
	}

	/**
	 * @param paymentNos
	 */
	public void setPaymentNos(List<String> paymentNos) {
		this.paymentNos = paymentNos;
	}

	
	/**
	 * @return accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	
	/**
	 * @param accountType
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	
	/**
	 * @return provinceCode
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	
	/**
	 * @param provinceCode
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	
	/**
	 * @return cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	
	/**
	 * @param cityCode
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	
	/**
	 * @return bankHqCode
	 */
	public String getBankHqCode() {
		return bankHqCode;
	}

	
	/**
	 * @param bankHqCode
	 */
	public void setBankHqCode(String bankHqCode) {
		this.bankHqCode = bankHqCode;
	}

	
	/**
	 * @return bankBranchCode
	 */
	public String getBankBranchCode() {
		return bankBranchCode;
	}

	
	/**
	 * @param bankBranchCode
	 */
	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	/**
	 * @return payBillPayeeCode
	 */
	public String getPayBillPayeeCode() {
		return payBillPayeeCode;
	}

	/**
	 * @param payBillPayeeCode
	 */
	public void setPayBillPayeeCode(String payBillPayeeCode) {
		this.payBillPayeeCode = payBillPayeeCode;
	}

	/**
	 * @return payBillComName
	 */
	public String getPayBillComName() {
		return payBillComName;
	}

	/**
	 * @param payBillComName
	 */
	public void setPayBillComName(String payBillComName) {
		this.payBillComName = payBillComName;
	}

	/**
	 * @return provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * @param provinceName
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * @return cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return bankHqName
	 */
	public String getBankHqName() {
		return bankHqName;
	}

	/**
	 * @param bankHqName
	 */
	public void setBankHqName(String bankHqName) {
		this.bankHqName = bankHqName;
	}

	/**
	 * @return bankBranchName
	 */
	public String getBankBranchName() {
		return bankBranchName;
	}

	/**
	 * @param bankBranchName
	 */
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	/**
	 * @GET
	 * @return payBillDiscription
	 */
	public String getPayBillDiscription() {
		/*
		 *@get
		 *@ return payBillDiscription
		 */
		return payBillDiscription;
	}

	/**
	 * @SET
	 * @param payBillDiscription
	 */
	public void setPayBillDiscription(String payBillDiscription) {
		/*
		 *@set
		 *@this.payBillDiscription = payBillDiscription
		 */
		this.payBillDiscription = payBillDiscription;
	}

	/**
	 * @GET
	 * @return expenseDetailToFSSC
	 */
	public List<com.deppon.fssc.inteface.domain.payment.ExpenseDetail> getExpenseDetailToFSSC() {
		/*
		 *@get
		 *@ return expenseDetailToFSSC
		 */
		return expenseDetailToFSSC;
	}

	/**
	 * @SET
	 * @param expenseDetailToFSSC
	 */
	public void setExpenseDetailToFSSC(
			List<com.deppon.fssc.inteface.domain.payment.ExpenseDetail> expenseDetailToFSSC) {
		/*
		 *@set
		 *@this.expenseDetailToFSSC = expenseDetailToFSSC
		 */
		this.expenseDetailToFSSC = expenseDetailToFSSC;
	}

	/**
	 * @GET
	 * @return currency
	 */
	public String getCurrency() {
		/*
		 *@get
		 *@ return currency
		 */
		return currency;
	}

	/**
	 * @SET
	 * @param currency
	 */
	public void setCurrency(String currency) {
		/*
		 *@set
		 *@this.currency = currency
		 */
		this.currency = currency;
	}

	/**
	 * @GET
	 * @return exchangeRate
	 */
	public BigDecimal getExchangeRate() {
		/*
		 *@get
		 *@ return exchangeRate
		 */
		return exchangeRate;
	}

	/**
	 * @SET
	 * @param exchangeRate
	 */
	public void setExchangeRate(BigDecimal exchangeRate) {
		/*
		 *@set
		 *@this.exchangeRate = exchangeRate
		 */
		this.exchangeRate = exchangeRate;
	}

	/**
	 * @GET
	 * @return isAutoAbatementLoan
	 */
	public String getIsAutoAbatementLoan() {
		/*
		 *@get
		 *@ return isAutoAbatementLoan
		 */
		return isAutoAbatementLoan;
	}

	/**
	 * @SET
	 * @param isAutoAbatementLoan
	 */
	public void setIsAutoAbatementLoan(String isAutoAbatementLoan) {
		/*
		 *@set
		 *@this.isAutoAbatementLoan = isAutoAbatementLoan
		 */
		this.isAutoAbatementLoan = isAutoAbatementLoan;
	}

	/**
	 * @GET
	 * @return stowageDetail
	 */
	public List<StowageDetail> getStowageDetail() {
		/*
		 *@get
		 *@ return stowageDetail
		 */
		return stowageDetail;
	}

	/**
	 * @SET
	 * @param stowageDetail
	 */
	public void setStowageDetail(List<StowageDetail> stowageDetail) {
		/*
		 *@set
		 *@this.stowageDetail = stowageDetail
		 */
		this.stowageDetail = stowageDetail;
	}

	/**
	 * @GET
	 * @return stowageEntityList
	 */
	public List<StowageEntity> getStowageEntityList() {
		/*
		 *@get
		 *@ return StowageEntityList
		 */
		return StowageEntityList;
	}

	/**
	 * @SET
	 * @param stowageEntityList
	 */
	public void setStowageEntityList(List<StowageEntity> stowageEntityList) {
		/*
		 *@set
		 *@StowageEntityList = stowageEntityList
		 */
		StowageEntityList = stowageEntityList;
	}

	public String getFactoring() {
		return factoring;
	}

	public void setFactoring(String factoring) {
		this.factoring = factoring;
	}

	public String getCusCode() {
		return cusCode;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
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
	
	
}
