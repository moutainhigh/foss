package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 应付单 分区键记账日期 accountDate
 * 
 * @author zhouChunlai
 * @date 2012-10-11 下午5:24:23
 */
public class BillPayableEntity extends BaseEntity {

	private static final long serialVersionUID = -5335298382258349709L;

	/**
	 * 应付单号
	 */
	private String payableNo;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 运单ID
	 */
	private String waybillId;

	/**
	 * 付款单号（付款反写）
	 */
	private String paymentNo;

	/**
	 * 系统生成方式
	 */
	private String createType;

	/**
	 * 单据子类型
	 */
	private String billType;

	/**
	 * 来源单据单号
	 */
	private String sourceBillNo;

	/**
	 * 来源单据类型
	 */
	private String sourceBillType;

	/**
	 * 代收货款类型--应收单冲应付核销要求
	 */
	private String codType;

	/**
	 * 应付部门编码
	 */
	private String payableOrgCode;

	/**
	 * 应付部门名称
	 */
	private String payableOrgName;

	/**
	 * 应付子公司编码
	 */
	private String payableComCode;

	/**
	 * 应付子公司名称
	 */
	private String payableComName;

	/**
	 * 出发部门编码
	 */
	private String origOrgCode;

	/**
	 * 出发部门名称
	 */
	private String origOrgName;

	/**
	 * 到达部门编码
	 */
	private String destOrgCode;

	/**
	 * 到达部门名称
	 */
	private String destOrgName;

	/**
	 * 客户编码/代理编码
	 */
	private String customerCode;

	/**
	 * 客户名称/代理名称
	 */
	private String customerName;

	/**
	 * 应付金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal amount;

	/**
	 * 已核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal verifyAmount;

	/**
	 * 未核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal unverifyAmount;

	/**
	 * 币种
	 */
	private String currencyCode;

	/**
	 * 运输性质
	 */
	private String productCode;
	
	/**
	 * 运输名称
	 */
    private String productName;
	
	/**
	 * 产品ID
	 */
	private String productId;

	/**
	 * 记账日期
	 */
	private Date accountDate;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 签收日期
	 */
	private Date signDate;

	/**
	 * 生效日期
	 */
	private Date effectiveDate;

	/**
	 * 制单人名称
	 */
	private String createUserName;

	/**
	 * 制单人编码
	 */
	private String createUserCode;

	/**
	 * 制单部门编码
	 */
	private String createOrgCode;

	/**
	 * 制单部门名称
	 */
	private String createOrgName;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 是否红单
	 */
	private String isRedBack;

	/**
	 * 是否初始化
	 */
	private String isInit;

	/**
	 * 版本号
	 */
	private Short versionNo;

	/**
	 * 生效状态
	 */
	private String effectiveStatus;

	/**
	 * 生效人名称
	 */
	private String effectiveUserName;

	/**
	 * 生效人编码
	 */
	private String effectiveUserCode;

	/**
	 * 冻结状态(给代收货款资金部冻结用)
	 */
	private String frozenStatus;

	/**
	 * 冻结时间
	 */
	private Date frozenTime;

	/**
	 * 冻结人名称
	 */
	private String frozenUserName;

	/**
	 * 冻结人编码
	 */
	private String frozenUserCode;

	/**
	 * 支付状态（付款反写该状态）
	 */
	private String payStatus;


	/**
	 * 对账单号
	 */
	private String statementBillNo;

	/**
	 * 客户联系人编码
	 */
	private String customerContact;

	/**
	 * 客户联系人姓名
	 */
	private String customerContactName;

	/**
	 * 客户联系电话
	 */
	private String customerPhone;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 修改人编码
	 */
	private String modifyUserCode;


	/**
	 * 工作流号
	 */
	private String workflowNo;

	/**
	 * 外请车司机编码
	 */
	private String lgdriverCode;

	/**
	 * 外请车司机名称
	 */
	private String lgdriverName;

	/**
	 * 付款方（生成应付单时候已经确定，付款不需要反写）
	 */
	private String payerType;

	/**
	 * 应付类型
	 */
	private String payableType;

	/**
	 * 送货费
	 */
	private BigDecimal deliverFee;

	/**
	 * 外发运费
	 */
	private BigDecimal outgoingFee;
	
	/**
	 * 代收货款手续费
	 */
	private BigDecimal codAgencyFee;
	
	/**
	 * 外发保价费
	 */
	private BigDecimal externalInsuranceFee;

	/**
	 * 审核人编码
	 */
	private String auditUserCode;

	/**
	 * 审核人名称
	 */
	private String auditUserName;

	/**
	 * 审核日期
	 */
	private Date auditDate;

	/**
	 * 审批状态
	 */
	private String approveStatus;

	/**
	 * 备注
	 */
	private String notes;
	
	/**
	 * 理赔类型
	 */
	private String claimWay;

	/*************************************** 11.28 银行信息 ***************************************/
	/**
	 * 开户人姓名
	 */
	private String payeeName;// PAYEE_NAME

	/**
	 * 开户行编码
	 */
	private String bankHqCode;// BANK_HQ_CODE

	/**
	 * 开户行名称
	 */
	private String bankHqName;// BANK_HQ_NAME

	/**
	 * 银行帐号
	 */
	private String accountNo;// ACCOUNT_NO

	/**
	 * 省份编码
	 */
	private String provinceCode;// PROVINCE_CODE

	/**
	 * 身份名称
	 */
	private String provinceName;// PROVINCE_NAME

	/**
	 * 城市编码
	 */
	private String cityCode;// CITY_CODE

	/**
	 * 城市名称
	 */
	private String cityName;// CITY_NAME

	/**
	 * 支行编码（行号）
	 */
	private String bankBranchCode;// BANK_BRANCH_CODE

	/**
	 * 支行名称
	 */
	private String bankBranchName;// BANK_BRANCH_NAME

	/**
	 * 付款备注
	 */
	private String paymentNotes;// PAYMENT_NOTES

	/**
	 * 付款金额
	 */
	private BigDecimal paymentAmount;// PAYMENT_AMOUNT
	
	/**
	 * 最后付款时间
	 */
	private Date lastPaymentTime;
	
	/**
	 * 支付类别
	 */
	private String paymentCategories;
	
	/**
	 * 是否核销
	 */
	private String isWriteoff;
	
	/**
	 * 账户类型
	 */
	private String accountType;
	
	/**
	 * 出发部门映射快递点部编码
	 */
	private String expressOrigOrgCode;
	
	/**
	 * 出发部门映射快递点部名称
	 */
	private String expressOrigOrgName;
	
	/**
	 * 到达部门映射快递点部编码
	 */
	private String expressDestOrgCode;
	
	/**
	 * 到达部门映射快递点部名称
	 */
	private String expressDestOrgName;
	
	/**
	 * 发票标记
	 */
	private String invoiceMark;
	
	/*============下面数据给临时租车使用 ，不涉及应付单表字段新增=======*/
	/**
	 * 费用承担部门名称
	 */
	private String costDeptCode;
	/**
	 * 费用承担部门编码
	 */
	private String costDeptName;
	
	/**
	 * 费用承担部门名称
	 */
	private String costType;
	
	/**
	 * 发票号
	 */
	private String vatInvoice;
	/**
	 * 费用发生日期
	 */
	private Date costDate;
	
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 租车用途
	 */
	private String rentCarUseType;
	/**
	 * 司机姓名
	 */
	private String driverName;
	/**
	 * 司机联系方式
	 */
	private String driverPhone;
	
	/**
	 * 税金
	 */
	private BigDecimal taxAmount;
	
	/**
	 * 税后金额
	 */
	private BigDecimal tax;
	
	/**
	 * 临时租车业务发生日期
	 */
	private Date businessOfDate;
	
	/**
	 * 预提状态
	 */
	private String accruedState;
	
	/**
	 * 预提状态
	 */
	private String withholdingStatus;
	
    /**
     * 是否统一结算
     */
	private String unifiedSettlement;
    /**
     * 合同部门编码
     */
    private String contractOrgCode;
    /**
     * 合同部门名称
     */
    private String contractOrgName;
    
	/**
	 * 纳税人识别号
	 */
	private String taxpayerNumber;
	
	/**
	 * 付款工作流
	 */
	private String paymentWorkflowNo;
	
	/**
	 * 转报销工作流
	 */
	private String paymentTransfer;
	
	/**
	 * 应付单费用承担部门
	 */
	private String expenseBearCode;
	
/*=====保理业务 @379106-2016-10-15============*/
	
	/**
	 * 是否为保理  默认为'N'
	 */
	private String factoring; 
	
	/**
	 * 保理开始日期   FACTOR_BEGIN_TIME
	 */
	private Date factorBeginTime;
	
	/**
	 * 保理结束日期   FACTOR_END_TIME
	 */
	private Date factorEndTime;
	
	/**
	 * 保理回款帐号  FACTOR_ACCOUNT
	 */
	private String factorAccount;
	
	/**
	 * 贷款客户编码  CUS_CODE
	 */
	private String cusCode;
	
    public String getPaymentWorkflowNo() {
		return paymentWorkflowNo;
	}

	public void setPaymentWorkflowNo(String paymentWorkflowNo) {
		this.paymentWorkflowNo = paymentWorkflowNo;
	}

	public String getPaymentTransfer() {
		return paymentTransfer;
	}

	public void setPaymentTransfer(String paymentTransfer) {
		this.paymentTransfer = paymentTransfer;
	}

	public String getUnifiedSettlement() {
        return unifiedSettlement;
    }

    public void setUnifiedSettlement(String unifiedSettlement) {
        this.unifiedSettlement = unifiedSettlement;
    }

    public String getContractOrgCode() {
        return contractOrgCode;
    }

    public void setContractOrgCode(String contractOrgCode) {
        this.contractOrgCode = contractOrgCode;
    }

    public String getContractOrgName() {
        return contractOrgName;
    }

    public void setContractOrgName(String contractOrgName) {
        this.contractOrgName = contractOrgName;
    }

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
	 * @return waybillId
	 */
	public String getWaybillId() {
		return waybillId;
	}

	/**
	 * @param waybillId
	 */
	public void setWaybillId(String waybillId) {
		this.waybillId = waybillId;
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
	 * @return createType
	 */
	public String getCreateType() {
		return createType;
	}

	/**
	 * @param createType
	 */
	public void setCreateType(String createType) {
		this.createType = createType;
	}

	/**
	 * @return billType
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType
	 */
	public void setBillType(String billType) {
		this.billType = billType;
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
	 * @return sourceBillType
	 */
	public String getSourceBillType() {
		return sourceBillType;
	}

	/**
	 * @param sourceBillType
	 */
	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}

	/**
	 * @return codType
	 */
	public String getCodType() {
		return codType;
	}

	/**
	 * @param codType
	 */
	public void setCodType(String codType) {
		this.codType = codType;
	}

	/**
	 * @return payableOrgCode
	 */
	public String getPayableOrgCode() {
		return payableOrgCode;
	}

	/**
	 * @param payableOrgCode
	 */
	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}

	/**
	 * @return payableOrgName
	 */
	public String getPayableOrgName() {
		return payableOrgName;
	}

	/**
	 * @param payableOrgName
	 */
	public void setPayableOrgName(String payableOrgName) {
		this.payableOrgName = payableOrgName;
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
	 * @return origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * @param origOrgCode
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * @return origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * @param origOrgName
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
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
	 * @return unverifyAmount
	 */
	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}

	/**
	 * @param unverifyAmount
	 */
	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
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
	 * @return productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return accountDate
	 */
	public Date getAccountDate() {
		return accountDate;
	}

	/**
	 * @param accountDate
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
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
	 * @return effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param createOrgCode
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return createOrgName
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}

	/**
	 * @param createOrgName
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	/**
	 * @param isRedBack
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	/**
	 * @return isInit
	 */
	public String getIsInit() {
		return isInit;
	}

	/**
	 * @param isInit
	 */
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}

	/**
	 * @return versionNo
	 */
	public Short getVersionNo() {
		return versionNo;
	}

	/**
	 * @param versionNo
	 */
	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @return effectiveStatus
	 */
	public String getEffectiveStatus() {
		return effectiveStatus;
	}

	/**
	 * @param effectiveStatus
	 */
	public void setEffectiveStatus(String effectiveStatus) {
		this.effectiveStatus = effectiveStatus;
	}

	/**
	 * @return effectiveUserName
	 */
	public String getEffectiveUserName() {
		return effectiveUserName;
	}

	/**
	 * @param effectiveUserName
	 */
	public void setEffectiveUserName(String effectiveUserName) {
		this.effectiveUserName = effectiveUserName;
	}

	/**
	 * @return effectiveUserCode
	 */
	public String getEffectiveUserCode() {
		return effectiveUserCode;
	}

	/**
	 * @param effectiveUserCode
	 */
	public void setEffectiveUserCode(String effectiveUserCode) {
		this.effectiveUserCode = effectiveUserCode;
	}

	/**
	 * @return frozenStatus
	 */
	public String getFrozenStatus() {
		return frozenStatus;
	}

	/**
	 * @param frozenStatus
	 */
	public void setFrozenStatus(String frozenStatus) {
		this.frozenStatus = frozenStatus;
	}

	/**
	 * @return frozenTime
	 */
	public Date getFrozenTime() {
		return frozenTime;
	}

	/**
	 * @param frozenTime
	 */
	public void setFrozenTime(Date frozenTime) {
		this.frozenTime = frozenTime;
	}

	/**
	 * @return frozenUserName
	 */
	public String getFrozenUserName() {
		return frozenUserName;
	}

	/**
	 * @param frozenUserName
	 */
	public void setFrozenUserName(String frozenUserName) {
		this.frozenUserName = frozenUserName;
	}

	/**
	 * @return frozenUserCode
	 */
	public String getFrozenUserCode() {
		return frozenUserCode;
	}

	/**
	 * @param frozenUserCode
	 */
	public void setFrozenUserCode(String frozenUserCode) {
		this.frozenUserCode = frozenUserCode;
	}

	/**
	 * @return payStatus
	 */
	public String getPayStatus() {
		return payStatus;
	}

	/**
	 * @param payStatus
	 */
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	/**
	 * @return statementBillNo
	 */
	public String getStatementBillNo() {
		return statementBillNo;
	}

	/**
	 * @param statementBillNo
	 */
	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}

	/**
	 * @return customerContact
	 */
	public String getCustomerContact() {
		return customerContact;
	}

	/**
	 * @param customerContact
	 */
	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}

	/**
	 * @return customerContactName
	 */
	public String getCustomerContactName() {
		return customerContactName;
	}

	/**
	 * @param customerContactName
	 */
	public void setCustomerContactName(String customerContactName) {
		this.customerContactName = customerContactName;
	}

	/**
	 * @return customerPhone
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}

	/**
	 * @param customerPhone
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * @param modifyUserName
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * @return modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param modifyUserCode
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * @return workflowNo
	 */
	public String getWorkflowNo() {
		return workflowNo;
	}

	/**
	 * @param workflowNo
	 */
	public void setWorkflowNo(String workflowNo) {
		this.workflowNo = workflowNo;
	}

	/**
	 * @return lgdriverCode
	 */
	public String getLgdriverCode() {
		return lgdriverCode;
	}

	/**
	 * @param lgdriverCode
	 */
	public void setLgdriverCode(String lgdriverCode) {
		this.lgdriverCode = lgdriverCode;
	}

	/**
	 * @return lgdriverName
	 */
	public String getLgdriverName() {
		return lgdriverName;
	}

	/**
	 * @param lgdriverName
	 */
	public void setLgdriverName(String lgdriverName) {
		this.lgdriverName = lgdriverName;
	}

	/**
	 * @return payerType
	 */
	public String getPayerType() {
		return payerType;
	}

	/**
	 * @param payerType
	 */
	public void setPayerType(String payerType) {
		this.payerType = payerType;
	}

	/**
	 * @return payableType
	 */
	public String getPayableType() {
		return payableType;
	}

	/**
	 * @param payableType
	 */
	public void setPayableType(String payableType) {
		this.payableType = payableType;
	}

	/**
	 * @return deliverFee
	 */
	public BigDecimal getDeliverFee() {
		return deliverFee;
	}

	/**
	 * @param deliverFee
	 */
	public void setDeliverFee(BigDecimal deliverFee) {
		this.deliverFee = deliverFee;
	}

	/**
	 * @return outgoingFee
	 */
	public BigDecimal getOutgoingFee() {
		return outgoingFee;
	}

	/**
	 * @param outgoingFee
	 */
	public void setOutgoingFee(BigDecimal outgoingFee) {
		this.outgoingFee = outgoingFee;
	}

	/**
	 * @return auditUserCode
	 */
	public String getAuditUserCode() {
		return auditUserCode;
	}

	/**
	 * @param auditUserCode
	 */
	public void setAuditUserCode(String auditUserCode) {
		this.auditUserCode = auditUserCode;
	}

	/**
	 * @return auditUserName
	 */
	public String getAuditUserName() {
		return auditUserName;
	}

	/**
	 * @param auditUserName
	 */
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	/**
	 * @return auditDate
	 */
	public Date getAuditDate() {
		return auditDate;
	}

	/**
	 * @param auditDate
	 */
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	/**
	 * @return approveStatus
	 */
	public String getApproveStatus() {
		return approveStatus;
	}

	/**
	 * @param approveStatus
	 */
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
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
	 * @return payeeName
	 */
	public String getPayeeName() {
		return payeeName;
	}

	/**
	 * @param payeeName
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
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
	 * @return accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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
	 * @return paymentNotes
	 */
	public String getPaymentNotes() {
		return paymentNotes;
	}

	/**
	 * @param paymentNotes
	 */
	public void setPaymentNotes(String paymentNotes) {
		this.paymentNotes = paymentNotes;
	}

	/**
	 * @return paymentAmount
	 */
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * @param paymentAmount
	 */
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	/**
	 * @return  the lastPaymentTime
	 */
	public Date getLastPaymentTime() {
		return lastPaymentTime;
	}
	
	/**
	 * @param lastPaymentTime the lastPaymentTime to set
	 */
	public void setLastPaymentTime(Date lastPaymentTime) {
		this.lastPaymentTime = lastPaymentTime;
	}
	
	/**
	 * @return  the paymentCategories
	 */
	public String getPaymentCategories() {
		return paymentCategories;
	}

	/**
	 * @param paymentCategories the paymentCategories to set
	 */
	public void setPaymentCategories(String paymentCategories) {
		this.paymentCategories = paymentCategories;
	}
	
	/**
	 * @return  the isWriteoff
	 */
	public String getIsWriteoff() {
		return isWriteoff;
	}
	
	/**
	 * @param isWriteoff the isWriteoff to set
	 */
	public void setIsWriteoff(String isWriteoff) {
		this.isWriteoff = isWriteoff;
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
	 * @return codAgencyFee
	 */
	public BigDecimal getCodAgencyFee() {
		return codAgencyFee;
	}

	/**
	 * @param codAgencyFee
	 */
	public void setCodAgencyFee(BigDecimal codAgencyFee) {
		this.codAgencyFee = codAgencyFee;
	}

	/**
	 * @return externalInsuranceFee
	 */
	public BigDecimal getExternalInsuranceFee() {
		return externalInsuranceFee;
	}

	/**
	 * @param externalInsuranceFee
	 */
	public void setExternalInsuranceFee(BigDecimal externalInsuranceFee) {
		this.externalInsuranceFee = externalInsuranceFee;
	}

	/**
	 * @GET
	 * @return expressOrigOrgCode
	 */
	public String getExpressOrigOrgCode() {
		/*
		 *@get
		 *@ return expressOrigOrgCode
		 */
		return expressOrigOrgCode;
	}

	/**
	 * @SET
	 * @param expressOrigOrgCode
	 */
	public void setExpressOrigOrgCode(String expressOrigOrgCode) {
		/*
		 *@set
		 *@this.expressOrigOrgCode = expressOrigOrgCode
		 */
		this.expressOrigOrgCode = expressOrigOrgCode;
	}

	/**
	 * @GET
	 * @return expressOrigOrgName
	 */
	public String getExpressOrigOrgName() {
		/*
		 *@get
		 *@ return expressOrigOrgName
		 */
		return expressOrigOrgName;
	}

	/**
	 * @SET
	 * @param expressOrigOrgName
	 */
	public void setExpressOrigOrgName(String expressOrigOrgName) {
		/*
		 *@set
		 *@this.expressOrigOrgName = expressOrigOrgName
		 */
		this.expressOrigOrgName = expressOrigOrgName;
	}

	/**
	 * @GET
	 * @return expressDestOrgCode
	 */
	public String getExpressDestOrgCode() {
		/*
		 *@get
		 *@ return expressDestOrgCode
		 */
		return expressDestOrgCode;
	}

	/**
	 * @SET
	 * @param expressDestOrgCode
	 */
	public void setExpressDestOrgCode(String expressDestOrgCode) {
		/*
		 *@set
		 *@this.expressDestOrgCode = expressDestOrgCode
		 */
		this.expressDestOrgCode = expressDestOrgCode;
	}

	/**
	 * @GET
	 * @return expressDestOrgName
	 */
	public String getExpressDestOrgName() {
		/*
		 *@get
		 *@ return expressDestOrgName
		 */
		return expressDestOrgName;
	}

	/**
	 * @SET
	 * @param expressDestOrgName
	 */
	public void setExpressDestOrgName(String expressDestOrgName) {
		/*
		 *@set
		 *@this.expressDestOrgName = expressDestOrgName
		 */
		this.expressDestOrgName = expressDestOrgName;
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

	public String getCostDeptCode() {
		return costDeptCode;
	}

	public void setCostDeptCode(String costDeptCode) {
		this.costDeptCode = costDeptCode;
	}

	public String getCostDeptName() {
		return costDeptName;
	}

	public void setCostDeptName(String costDeptName) {
		this.costDeptName = costDeptName;
	}

	public Date getCostDate() {
		return costDate;
	}

	public void setCostDate(Date costDate) {
		this.costDate = costDate;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getRentCarUseType() {
		return rentCarUseType;
	}

	public void setRentCarUseType(String rentCarUseType) {
		this.rentCarUseType = rentCarUseType;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public Date getBusinessOfDate() {
		return businessOfDate;
	}

	public void setBusinessOfDate(Date businessOfDate) {
		this.businessOfDate = businessOfDate;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public String getVatInvoice() {
		return vatInvoice;
	}

	public void setVatInvoice(String vatInvoice) {
		this.vatInvoice = vatInvoice;
	}

	public String getAccruedState() {
		return accruedState;
	}

	public void setAccruedState(String accruedState) {
		this.accruedState = accruedState;
	}

	public String getWithholdingStatus() {
		return withholdingStatus;
	}

	public void setWithholdingStatus(String withholdingStatus) {
		this.withholdingStatus = withholdingStatus;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTaxpayerNumber() {
		return taxpayerNumber;
	}

	public void setTaxpayerNumber(String taxpayerNumber) {
		this.taxpayerNumber = taxpayerNumber;
	}

	public String getClaimWay() {
		return claimWay;
	}

	public void setClaimWay(String claimWay) {
		this.claimWay = claimWay;
	}

	public String getExpenseBearCode() {
		return expenseBearCode;
	}

	public void setExpenseBearCode(String expenseBearCode) {
		this.expenseBearCode = expenseBearCode;
	}

	public String getFactoring() {
		return factoring;
	}

	public void setFactoring(String factoring) {
		this.factoring = factoring;
	}

	public Date getFactorBeginTime() {
		return factorBeginTime;
	}

	public void setFactorBeginTime(Date factorBeginTime) {
		this.factorBeginTime = factorBeginTime;
	}

	public Date getFactorEndTime() {
		return factorEndTime;
	}

	public void setFactorEndTime(Date factorEndTime) {
		this.factorEndTime = factorEndTime;
	}

	public String getFactorAccount() {
		return factorAccount;
	}

	public void setFactorAccount(String factorAccount) {
		this.factorAccount = factorAccount;
	}

	public String getCusCode() {
		return cusCode;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}

}

