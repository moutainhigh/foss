package com.deppon.foss.module.pickup.waybill.server.utils.cubc.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @ClassName: TradeDO
 * @Description: 物流交易表DO
 * @author: 273238
 * @date: 2016年11月19日 上午10:53:19
 */
public class TradeDO extends TradeOptionDO {

	/**
     * @fieldName: serialVersionUID
     * @fieldType: long
     */
	private static final long serialVersionUID = -1478214807752408851L;
	
    // ID
    private Long id;
    // 物流交易号
    private String orderNo;
    // 物流交易单类型
    private String orderType;
    // 物流交易子类型
    private String orderSubType;
    // 产品ID
    private String productId;
    // 产品类型
    private String productType;
    // 业务类型
    private String businessType;
    // 来源单据编号ID
    private String sourceBillNoId;
    // 来源单据编号
    private String sourceBillNo;
    // 来源单据编号list
    private String[] sourceBillNos;
    // 原来源单据编号
    private String refSourceBillNo;
    // 来源单据类型
    private String sourceBillType;
    // 统一结算
    private String unifiedSettlement;
    // 付款方式
    private String paymentType;
    // 应收付金额
    private BigDecimal amount;
    // 是否国内-境内境外
    private String isDomestic;
    // 币种
    private String currencyType;
    // 汇率
    private BigDecimal exchangeRate;
    // 已核销金额
    private BigDecimal verifyAmount;
    // 未核销金额
    private BigDecimal unverifyAmount;
    // 折扣单号
    private String discountBillNo;
    // 事前折扣金额
    private BigDecimal discountAmount;
    // 事后折扣金额
    private BigDecimal postDiscountAmount;
    // 应收付单号
    private String actionOrderNo;
    // 对账单号
    private String statementBillNo;
    // 当期对账单号
    private String currentStatementNo;
    // 直营/加盟
    private String isSalesDepartment;
    // 应收/应付对象类型
    private String customerType;
    // 应收/应付对象名称
    private String customerName;
    // 应收/应付对象编码
    private String customerCode;
    // 出账客户类型
    private String accountCustomerType;
    // 出账客户名称
    private String accountCustomerName;
    // 出账客户编码
    private String accountCustomerCode;
    // 主账户客户类型
    private String masterCustomerType;
    // 主账户客户名称
    private String masterCustomerName;
    // 主账户客户编码
    private String masterCustomerCode;
    // 应收/付部门名称
    private String actionOrgName;
    // 应收/付部门编码
    private String actionOrgCode;
    // 应收/付部门标杆编码
    private String actionOrgUnifiedName;
    //应收部门对应的子公司编码
    private String receivableOrgComCode;
    //始发部门对应的子公司编码
    private String origOrgComCode;
    //到达部门对应的子公司编码
    private String destOrgComCode;
    // 收入/付款部门（费用承担部门）名称，本来需要谁来收款或者付款的部门
    private String burdenOrgName;
    // 收入/付款部门（费用承担部门）编码，本来需要谁来收款或者付款的部门
    private String burdenOrgCode;
    // 收入/付款部门（费用承担部门）标杆编码，本来需要谁来收款或者付款的部门
    private String burdenOrgUnifiedCode;
    // 合同部门名称
    private String contractOrgName;
    // 合同部门编码
    private String contractOrgCode;
    // 合同部门标杆编码
    private String contractOrgUnifiedCode;
    // 实际收/付款部门名称
    private String actualOrgName;
    // 实际收/付款部门编码
    private String actualOrgCode;
    // 实际收/付款部门标杆编码
    private String actualOrgUnifiedCode;
    // 大区
    private String bigArea;
    // 小区
    private String smallArea;
    // 发票标记
    private String invoiceMark;
    // 11%已开票金额
    private BigDecimal invoiceAmount;
    // 6%已开票金额
    private BigDecimal invoiceLiAmount;
    // 运输收入
    private BigDecimal transportIncome;
    // 物流辅助收入
    private BigDecimal logisticsIncome;
    // 不含税运输收入
    private BigDecimal transportIncomeNoTax;
    // 不含税物流辅助收入
    private BigDecimal logisticsIncomeNoTax;
    // 运输收入税额
    private BigDecimal transportIncomeTax;
    // 物流辅助收入税额
    private BigDecimal logisticsIncomeTax;
    // 创建人工号
    private String createUserCode;
    // 创建人名称
    private String createUserName;
    // 创建部门编码
    private String createOrgCode;
    // 创建部门名称
    private String createOrgName;
    // 修改人工号
    private String modifyUserCode;
    // 修改人姓名
    private String modifyUserName;
    // 记账状态
    private String statementStatus;
    // 记账日期
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date statementDate;
    // 业务状态
    private String businessStatus;
    // 业务发生时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date businessDate;
    // 业务完结时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date businessOverDate;
    // 核销超期时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date payExceedDate;
    // 支付/扣款状态
    private String payStatus;
    // 冻结状态
    private String frozenStatus;
    // 冻结日期
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date frozenDate;
    // 更改受理时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date modifyAcceptDate;
    // 版本号
    private Long versionNo;
    // 扩展字段
    private String attribute;
    // 属性对象
    private TradeAttributeDO tradeAttributeDO;
    // 创建时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    // 修改时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;
    // 备注
    private String remark;
    // 物流交易历史表ID
    private Long historyId;
    // 运单号
    private String waybillNo;
    // 来源系统
    private String sourceSystem;
    //付款单号
    private String paymentNo;
    

    /**
	 * 返回 付款单号
	 * 
	 * @return 付款单号
	*/
	public String getPaymentNo() {
		return paymentNo;
	}
	


	/**  
	 * 设置  付款单号
	 * @param paymentNo
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	


	/**
     * 返回 来源系统
     *
     * @return 来源系统
     */
    public String getSourceSystem() {
        return sourceSystem;
    }


    /**
     * 设置 来源系统
     *
     * @param sourceSystem 来源系统
     */
    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }


    /**
     * 返回 运单号
     *
     * @return 运单号
     */
    public String getWaybillNo() {
        return waybillNo;
    }


    /**
     * 设置  运单号
     *
     * @param waybillNo
     */
    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }


    /**
     * 返回 ID
     *
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置 ID
     *
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 返回 物流交易号
     *
     * @return 物流交易号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置 物流交易号
     *
     * @param orderNo 物流交易号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 返回 物流交易单类型
     *
     * @return 物流交易单类型
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * 设置 物流交易单类型
     *
     * @param orderType 物流交易单类型
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * 返回 物流交易子类型
     *
     * @return 物流交易子类型
     */
    public String getOrderSubType() {
        return orderSubType;
    }

    /**
     * 设置 物流交易子类型
     *
     * @param orderSubType 物流交易子类型
     */
    public void setOrderSubType(String orderSubType) {
        this.orderSubType = orderSubType;
    }

    /**
     * 返回 产品ID
     *
     * @return 产品ID
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 设置 产品ID
     *
     * @param productId 产品ID
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 返回 产品类型
     *
     * @return 产品类型
     */
    public String getProductType() {
        return productType;
    }

    /**
     * 设置 产品类型
     *
     * @param productType 产品类型
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * 返回 业务类型
     *
     * @return 业务类型
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * 设置 业务类型
     *
     * @param businessType 业务类型
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    /**
     * 返回 来源单据编号ID
     *
     * @return 来源单据编号ID
     */
    public String getSourceBillNoId() {
        return sourceBillNoId;
    }

    /**
     * 设置 来源单据编号ID
     *
     * @param sourceBillNoId 来源单据编号ID
     */
    public void setSourceBillNoId(String sourceBillNoId) {
        this.sourceBillNoId = sourceBillNoId;
    }

    /**
     * 返回 来源单据编号
     *
     * @return 来源单据编号
     */
    public String getSourceBillNo() {
        return sourceBillNo;
    }

    /**
     * 设置 来源单据编号
     *
     * @param sourceBillNo 来源单据编号
     */
    public void setSourceBillNo(String sourceBillNo) {
        this.sourceBillNo = sourceBillNo;
    }

    /**
     * 返回 来源单据编号
     *
     * @return 来源单据编号
     */
    public String[] getSourceBillNos() {
        return sourceBillNos;
    }

    /**
     * 设置 来源单据编号
     *
     * @param sourceBillNos 来源单据编号
     */
    public void setSourceBillNos(String[] sourceBillNos) {
        this.sourceBillNos = sourceBillNos;
    }

    /**
     * 返回 原来源单据编号
     *
     * @return 原来源单据编号
     */
    public String getRefSourceBillNo() {
        return refSourceBillNo;
    }

    /**
     * 设置 原来源单据编号
     *
     * @param refSourceBillNo 原来源单据编号
     */
    public void setRefSourceBillNo(String refSourceBillNo) {
        this.refSourceBillNo = refSourceBillNo;
    }

    /**
     * 返回 来源单据类型
     *
     * @return 来源单据类型
     */
    public String getSourceBillType() {
        return sourceBillType;
    }

    /**
     * 设置 来源单据类型
     *
     * @param sourceBillType 来源单据类型
     */
    public void setSourceBillType(String sourceBillType) {
        this.sourceBillType = sourceBillType;
    }

    /**
     * 返回 统一结算
     *
     * @return 统一结算
     */
    public String getUnifiedSettlement() {
        return unifiedSettlement;
    }

    /**
     * 设置 统一结算
     *
     * @param unifiedSettlement 统一结算
     */
    public void setUnifiedSettlement(String unifiedSettlement) {
        this.unifiedSettlement = unifiedSettlement;
    }

    /**
     * 返回 付款方式
     *
     * @return 付款方式
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * 设置 付款方式
     *
     * @param paymentType 付款方式
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * 返回 应收付金额
     *
     * @return 应收付金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置 应收付金额
     *
     * @param amount 应收付金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 返回 是否国内-境内境外
     *
     * @return 是否国内-境内境外
     */
    public String getIsDomestic() {
        return isDomestic;
    }

    /**
     * 设置 是否国内-境内境外
     *
     * @param isDomestic 是否国内-境内境外
     */
    public void setIsDomestic(String isDomestic) {
        this.isDomestic = isDomestic;
    }

    /**
     * 返回 币种
     *
     * @return 币种
     */
    public String getCurrencyType() {
        return currencyType;
    }

    /**
     * 设置 币种
     *
     * @param currencyType 币种
     */
    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    /**
     * 返回 汇率
     *
     * @return 汇率
     */
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    /**
     * 设置 汇率
     *
     * @param exchangeRate 汇率
     */
    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * 返回 已核销金额
     *
     * @return 已核销金额
     */
    public BigDecimal getVerifyAmount() {
        return verifyAmount;
    }

    /**
     * 设置 已核销金额
     *
     * @param verifyAmount 已核销金额
     */
    public void setVerifyAmount(BigDecimal verifyAmount) {
        this.verifyAmount = verifyAmount;
    }

    /**
     * 返回 未核销金额
     *
     * @return 未核销金额
     */
    public BigDecimal getUnverifyAmount() {
        return unverifyAmount;
    }

    /**
     * 设置 未核销金额
     *
     * @param unverifyAmount 未核销金额
     */
    public void setUnverifyAmount(BigDecimal unverifyAmount) {
        this.unverifyAmount = unverifyAmount;
    }

    /**
     * 返回 折扣单号
     *
     * @return 折扣单号
     */
    public String getDiscountBillNo() {
        return discountBillNo;
    }

    /**
     * 设置 折扣单号
     *
     * @param discountBillNo 折扣单号
     */
    public void setDiscountBillNo(String discountBillNo) {
        this.discountBillNo = discountBillNo;
    }

    /**
     * 返回 事前折扣金额
     *
     * @return 事前折扣金额
     */
    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    /**
     * 设置 事前折扣金额
     *
     * @param discountAmount 事前折扣金额
     */
    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * 返回 事后折扣金额
     *
     * @return 事后折扣金额
     */
    public BigDecimal getPostDiscountAmount() {
        return postDiscountAmount;
    }

    /**
     * 设置 事后折扣金额
     *
     * @param postDiscountAmount 事后折扣金额
     */
    public void setPostDiscountAmount(BigDecimal postDiscountAmount) {
        this.postDiscountAmount = postDiscountAmount;
    }

    /**
     * 返回 应收付单号
     *
     * @return 应收付单号
     */
    public String getActionOrderNo() {
        return actionOrderNo;
    }

    /**
     * 设置 应收付单号
     *
     * @param actionOrderNo 应收付单号
     */
    public void setActionOrderNo(String actionOrderNo) {
        this.actionOrderNo = actionOrderNo;
    }

    /**
     * 返回 对账单号
     *
     * @return 对账单号
     */
    public String getStatementBillNo() {
        return statementBillNo;
    }

    /**
     * 设置 对账单号
     *
     * @param statementBillNo 对账单号
     */
    public void setStatementBillNo(String statementBillNo) {
        this.statementBillNo = statementBillNo;
    }

    /**
     * 返回 当期对账单号
     *
     * @return 当期对账单号
     */
    public String getCurrentStatementNo() {
        return currentStatementNo;
    }

    /**
     * 设置 当期对账单号
     *
     * @param currentStatementNo 当期对账单号
     */
    public void setCurrentStatementNo(String currentStatementNo) {
        this.currentStatementNo = currentStatementNo;
    }

    /**
     * 返回 直营/加盟
     *
     * @return 直营/加盟
     */
    public String getIsSalesDepartment() {
        return isSalesDepartment;
    }

    /**
     * 设置 直营/加盟
     *
     * @param isSalesDepartment 直营/加盟
     */
    public void setIsSalesDepartment(String isSalesDepartment) {
        this.isSalesDepartment = isSalesDepartment;
    }

    /**
     * 返回 应收/应付对象类型
     *
     * @return 应收/应付对象类型
     */
    public String getCustomerType() {
        return customerType;
    }

    /**
     * 设置 应收/应付对象类型
     *
     * @param customerType 应收/应付对象类型
     */
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    /**
     * 返回 应收/应付对象名称
     *
     * @return 应收/应付对象名称
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 设置 应收/应付对象名称
     *
     * @param customerName 应收/应付对象名称
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 返回 应收/应付对象编码
     *
     * @return 应收/应付对象编码
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * 设置 应收/应付对象编码
     *
     * @param customerCode 应收/应付对象编码
     */
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    /**
     * 返回 出账客户类型
     *
     * @return 出账客户类型
     */
    public String getAccountCustomerType() {
        return accountCustomerType;
    }

    /**
     * 设置 出账客户类型
     *
     * @param accountCustomerType 出账客户类型
     */
    public void setAccountCustomerType(String accountCustomerType) {
        this.accountCustomerType = accountCustomerType;
    }

    /**
     * 返回 出账客户名称
     *
     * @return 出账客户名称
     */
    public String getAccountCustomerName() {
        return accountCustomerName;
    }

    /**
     * 设置 出账客户名称
     *
     * @param accountCustomerName 出账客户名称
     */
    public void setAccountCustomerName(String accountCustomerName) {
        this.accountCustomerName = accountCustomerName;
    }

    /**
     * 返回 出账客户编码
     *
     * @return 出账客户编码
     */
    public String getAccountCustomerCode() {
        return accountCustomerCode;
    }

    /**
     * 设置 出账客户编码
     *
     * @param accountCustomerCode 出账客户编码
     */
    public void setAccountCustomerCode(String accountCustomerCode) {
        this.accountCustomerCode = accountCustomerCode;
    }

    /**
     * 返回 主账户客户类型
     *
     * @return 主账户客户类型
     */
    public String getMasterCustomerType() {
        return masterCustomerType;
    }

    /**
     * 设置 主账户客户类型
     *
     * @param masterCustomerType 主账户客户类型
     */
    public void setMasterCustomerType(String masterCustomerType) {
        this.masterCustomerType = masterCustomerType;
    }

    /**
     * 返回 主账户客户名称
     *
     * @return 主账户客户名称
     */
    public String getMasterCustomerName() {
        return masterCustomerName;
    }

    /**
     * 设置 主账户客户名称
     *
     * @param masterCustomerName 主账户客户名称
     */
    public void setMasterCustomerName(String masterCustomerName) {
        this.masterCustomerName = masterCustomerName;
    }

    /**
     * 返回 主账户客户编码
     *
     * @return 主账户客户编码
     */
    public String getMasterCustomerCode() {
        return masterCustomerCode;
    }

    /**
     * 设置 主账户客户编码
     *
     * @param masterCustomerCode 主账户客户编码
     */
    public void setMasterCustomerCode(String masterCustomerCode) {
        this.masterCustomerCode = masterCustomerCode;
    }

    /**
     * 返回 应收/付部门名称
     *
     * @return 应收/付部门名称
     */
    public String getActionOrgName() {
        return actionOrgName;
    }

    /**
     * 设置 应收/付部门名称
     *
     * @param actionOrgName 应收/付部门名称
     */
    public void setActionOrgName(String actionOrgName) {
        this.actionOrgName = actionOrgName;
    }

    /**
     * 返回 应收/付部门编码
     *
     * @return 应收/付部门编码
     */
    public String getActionOrgCode() {
        return actionOrgCode;
    }

    /**
     * 设置 应收/付部门编码
     *
     * @param actionOrgCode 应收/付部门编码
     */
    public void setActionOrgCode(String actionOrgCode) {
        this.actionOrgCode = actionOrgCode;
    }

    /**
     * 返回 应收/付部门标杆编码
     *
     * @return 应收/付部门标杆编码
     */
    public String getActionOrgUnifiedName() {
        return actionOrgUnifiedName;
    }

    /**
     * 设置 应收/付部门标杆编码
     *
     * @param actionOrgUnifiedName 应收/付部门标杆编码
     */
    public void setActionOrgUnifiedName(String actionOrgUnifiedName) {
        this.actionOrgUnifiedName = actionOrgUnifiedName;
    }

    /**
     * 返回 应收部门对应的子公司编码
     *
     * @return 应收部门对应的子公司编码
     */
    public String getReceivableOrgComCode() {
        return receivableOrgComCode;
    }

    /**
     * 设置 应收部门对应的子公司编码
     *
     * @param receivableOrgComCode 应收部门对应的子公司编码
     */
    public void setReceivableOrgComCode(String receivableOrgComCode) {
        this.receivableOrgComCode = receivableOrgComCode;
    }

    /**
     * 返回 始发部门编码对应的子公司编码
     *
     * @return 始发部门编码对应的子公司编码
     */
    public String getOrigOrgComCode() {
        return origOrgComCode;
    }

    /**
     * 设置 始发部门编码对应的子公司编码
     *
     * @param origOrgComCode 始发部门编码对应的子公司编码
     */
    public void setOrigOrgComCode(String origOrgComCode) {
        this.origOrgComCode = origOrgComCode;
    }

    /**
     * 到达部门对应的子公编码
     *
     * @return 到达部门对应的子公编码
     */
    public String getDestOrgComCode() {
        return destOrgComCode;
    }

    /**
     * 到达部门对应的子公编码
     *
     * @param destOrgComCode 到达部门对应的子公编码tr
     */
    public void setDestOrgComCode(String destOrgComCode) {
        this.destOrgComCode = destOrgComCode;
    }

    /**
     * 返回 费用承担部门名称
     *
     * @return 费用承担部门名称
     */
    public String getBurdenOrgName() {
        return burdenOrgName;
    }

    /**
     * 设置 费用承担部门名称
     *
     * @param burdenOrgName 费用承担部门名称
     */
    public void setBurdenOrgName(String burdenOrgName) {
        this.burdenOrgName = burdenOrgName;
    }

    /**
     * 返回 费用承担部门编码
     *
     * @return 费用承担部门编码
     */
    public String getBurdenOrgCode() {
        return burdenOrgCode;
    }

    /**
     * 设置 费用承担部门编码
     *
     * @param burdenOrgCode 费用承担部门编码
     */
    public void setBurdenOrgCode(String burdenOrgCode) {
        this.burdenOrgCode = burdenOrgCode;
    }

    /**
     * 返回 费用承担部门标杆编码
     *
     * @return 费用承担部门标杆编码
     */
    public String getBurdenOrgUnifiedCode() {
        return burdenOrgUnifiedCode;
    }

    /**
     * 设置 费用承担部门标杆编码
     *
     * @param burdenOrgUnifiedCode 费用承担部门标杆编码
     */
    public void setBurdenOrgUnifiedCode(String burdenOrgUnifiedCode) {
        this.burdenOrgUnifiedCode = burdenOrgUnifiedCode;
    }

    /**
     * 返回 合同部门名称
     *
     * @return 合同部门名称
     */
    public String getContractOrgName() {
        return contractOrgName;
    }

    /**
     * 设置 合同部门名称
     *
     * @param contractOrgName 合同部门名称
     */
    public void setContractOrgName(String contractOrgName) {
        this.contractOrgName = contractOrgName;
    }

    /**
     * 返回 合同部门编码
     *
     * @return 合同部门编码
     */
    public String getContractOrgCode() {
        return contractOrgCode;
    }

    /**
     * 设置 合同部门编码
     *
     * @param contractOrgCode 合同部门编码
     */
    public void setContractOrgCode(String contractOrgCode) {
        this.contractOrgCode = contractOrgCode;
    }

    /**
     * 返回 合同部门标杆编码
     *
     * @return 合同部门标杆编码
     */
    public String getContractOrgUnifiedCode() {
        return contractOrgUnifiedCode;
    }

    /**
     * 设置 合同部门标杆编码
     *
     * @param contractOrgUnifiedCode 合同部门标杆编码
     */
    public void setContractOrgUnifiedCode(String contractOrgUnifiedCode) {
        this.contractOrgUnifiedCode = contractOrgUnifiedCode;
    }

    /**
     * 返回 实际收/付款部门名称
     *
     * @return 实际收/付款部门名称
     */
    public String getActualOrgName() {
        return actualOrgName;
    }

    /**
     * 设置 实际收/付款部门名称
     *
     * @param actualOrgName 实际收/付款部门名称
     */
    public void setActualOrgName(String actualOrgName) {
        this.actualOrgName = actualOrgName;
    }

    /**
     * 返回 实际收/付款部门编码
     *
     * @return 实际收/付款部门编码
     */
    public String getActualOrgCode() {
        return actualOrgCode;
    }

    /**
     * 设置 实际收/付款部门编码
     *
     * @param actualOrgCode 实际收/付款部门编码
     */
    public void setActualOrgCode(String actualOrgCode) {
        this.actualOrgCode = actualOrgCode;
    }

    /**
     * 返回 实际收/付款部门标杆编码
     *
     * @return 实际收/付款部门标杆编码
     */
    public String getActualOrgUnifiedCode() {
        return actualOrgUnifiedCode;
    }

    /**
     * 设置 实际收/付款部门标杆编码
     *
     * @param actualOrgUnifiedCode 实际收/付款部门标杆编码
     */
    public void setActualOrgUnifiedCode(String actualOrgUnifiedCode) {
        this.actualOrgUnifiedCode = actualOrgUnifiedCode;
    }

    /**
     * 返回 大区
     *
     * @return 大区
     */
    public String getBigArea() {
        return bigArea;
    }

    /**
     * 设置 大区
     *
     * @param bigArea 大区
     */
    public void setBigArea(String bigArea) {
        this.bigArea = bigArea;
    }

    /**
     * 返回 小区
     *
     * @return 小区
     */
    public String getSmallArea() {
        return smallArea;
    }

    /**
     * 设置 小区
     *
     * @param smallArea 小区
     */
    public void setSmallArea(String smallArea) {
        this.smallArea = smallArea;
    }

    /**
     * 返回 发票标记
     *
     * @return 发票标记
     */
    public String getInvoiceMark() {
        return invoiceMark;
    }

    /**
     * 设置 发票标记
     *
     * @param invoiceMark 发票标记
     */
    public void setInvoiceMark(String invoiceMark) {
        this.invoiceMark = invoiceMark;
    }

    /**
     * 返回 11%已开票金额
     *
     * @return 11%已开票金额
     */
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * 设置 11%已开票金额
     *
     * @param invoiceAmount 11%已开票金额
     */
    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     * 返回 6%已开票金额
     *
     * @return 6%已开票金额
     */
    public BigDecimal getInvoiceLiAmount() {
        return invoiceLiAmount;
    }

    /**
     * 设置 6%已开票金额
     *
     * @param invoiceLiAmount 6%已开票金额
     */
    public void setInvoiceLiAmount(BigDecimal invoiceLiAmount) {
        this.invoiceLiAmount = invoiceLiAmount;
    }

    /**
     * 返回 运输收入
     *
     * @return 运输收入
     */
    public BigDecimal getTransportIncome() {
        return transportIncome;
    }

    /**
     * 设置 运输收入
     *
     * @param transportIncome 运输收入
     */
    public void setTransportIncome(BigDecimal transportIncome) {
        this.transportIncome = transportIncome;
    }

    /**
     * 返回 物流辅助收入
     *
     * @return 物流辅助收入
     */
    public BigDecimal getLogisticsIncome() {
        return logisticsIncome;
    }

    /**
     * 设置 物流辅助收入
     *
     * @param logisticsIncome 物流辅助收入
     */
    public void setLogisticsIncome(BigDecimal logisticsIncome) {
        this.logisticsIncome = logisticsIncome;
    }

    /**
     * 返回 不含税运输收入
     *
     * @return 不含税运输收入
     */
    public BigDecimal getTransportIncomeNoTax() {
        return transportIncomeNoTax;
    }

    /**
     * 设置 不含税运输收入
     *
     * @param transportIncomeNoTax 不含税运输收入
     */
    public void setTransportIncomeNoTax(BigDecimal transportIncomeNoTax) {
        this.transportIncomeNoTax = transportIncomeNoTax;
    }

    /**
     * 返回 不含税物流辅助收入
     *
     * @return 不含税物流辅助收入
     */
    public BigDecimal getLogisticsIncomeNoTax() {
        return logisticsIncomeNoTax;
    }

    /**
     * 设置 不含税物流辅助收入
     *
     * @param logisticsIncomeNoTax 不含税物流辅助收入
     */
    public void setLogisticsIncomeNoTax(BigDecimal logisticsIncomeNoTax) {
        this.logisticsIncomeNoTax = logisticsIncomeNoTax;
    }

    /**
     * 返回 运输收入税额
     *
     * @return 运输收入税额
     */
    public BigDecimal getTransportIncomeTax() {
        return transportIncomeTax;
    }

    /**
     * 设置 运输收入税额
     *
     * @param transportIncomeTax 运输收入税额
     */
    public void setTransportIncomeTax(BigDecimal transportIncomeTax) {
        this.transportIncomeTax = transportIncomeTax;
    }

    /**
     * 返回 物流辅助收入税额
     *
     * @return 物流辅助收入税额
     */
    public BigDecimal getLogisticsIncomeTax() {
        return logisticsIncomeTax;
    }

    /**
     * 设置 物流辅助收入税额
     *
     * @param logisticsIncomeTax 物流辅助收入税额
     */
    public void setLogisticsIncomeTax(BigDecimal logisticsIncomeTax) {
        this.logisticsIncomeTax = logisticsIncomeTax;
    }

    /**
     * 返回 创建人工号
     *
     * @return 创建人工号
     */
    public String getCreateUserCode() {
        return createUserCode;
    }

    /**
     * 设置 创建人工号
     *
     * @param createUserCode 创建人工号
     */
    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    /**
     * 返回 创建人名称
     *
     * @return 创建人名称
     */
    public String getCreateUserName() {
        return createUserName;
    }

    /**
     * 设置 创建人名称
     *
     * @param createUserName 创建人名称
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    /**
     * 返回 创建部门编码
     *
     * @return 创建部门编码
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }

    /**
     * 设置 创建部门编码
     *
     * @param createOrgCode 创建部门编码
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    /**
     * 返回 创建部门名称
     *
     * @return 创建部门名称
     */
    public String getCreateOrgName() {
        return createOrgName;
    }

    /**
     * 设置 创建部门名称
     *
     * @param createOrgName 创建部门名称
     */
    public void setCreateOrgName(String createOrgName) {
        this.createOrgName = createOrgName;
    }

    /**
     * 返回 修改人工号
     *
     * @return 修改人工号
     */
    public String getModifyUserCode() {
        return modifyUserCode;
    }

    /**
     * 设置 修改人工号
     *
     * @param modifyUserCode 修改人工号
     */
    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode;
    }

    /**
     * 返回 修改人姓名
     *
     * @return 修改人姓名
     */
    public String getModifyUserName() {
        return modifyUserName;
    }

    /**
     * 设置 修改人姓名
     *
     * @param modifyUserName 修改人姓名
     */
    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }

    /**
     * 返回 记账状态
     *
     * @return 记账状态
     */
    public String getStatementStatus() {
        return statementStatus;
    }

    /**
     * 设置 记账状态
     *
     * @param statementStatus 记账状态
     */
    public void setStatementStatus(String statementStatus) {
        this.statementStatus = statementStatus;
    }

    /**
     * 返回 记账日期
     *
     * @return 记账日期
     */
    public Date getStatementDate() {
        return statementDate;
    }

    /**
     * 设置 记账日期
     *
     * @param statementDate 记账日期
     */
    public void setStatementDate(Date statementDate) {
        this.statementDate = statementDate;
    }

    /**
     * 返回 业务状态
     *
     * @return 业务状态
     */
    public String getBusinessStatus() {
        return businessStatus;
    }

    /**
     * 设置 业务状态
     *
     * @param businessStatus 业务状态
     */
    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    /**
     * 返回 业务发生时间
     *
     * @return 业务发生时间
     */
    public Date getBusinessDate() {
        return businessDate;
    }

    /**
     * 设置 业务发生时间
     *
     * @param businessDate 业务发生时间
     */
    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    /**
     * 返回 业务完结时间
     *
     * @return 业务完结时间
     */
    public Date getBusinessOverDate() {
        return businessOverDate;
    }

    /**
     * 设置 业务完结时间
     *
     * @param businessOverDate 业务完结时间
     */
    public void setBusinessOverDate(Date businessOverDate) {
        this.businessOverDate = businessOverDate;
    }

    /**
     * 返回 核销超期时间
     *
     * @return 核销超期时间
     */
    public Date getPayExceedDate() {
        return payExceedDate;
    }

    /**
     * 设置 核销超期时间
     *
     * @param payExceedDate 核销超期时间
     */
    public void setPayExceedDate(Date payExceedDate) {
        this.payExceedDate = payExceedDate;
    }

    /**
     * 返回 支付/扣款状态
     *
     * @return 支付/扣款状态
     */
    public String getPayStatus() {
        return payStatus;
    }

    /**
     * 设置 支付/扣款状态
     *
     * @param payStatus 支付/扣款状态
     */
    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 返回 冻结状态
     *
     * @return 冻结状态
     */
    public String getFrozenStatus() {
        return frozenStatus;
    }

    /**
     * 设置 冻结状态
     *
     * @param frozenStatus 冻结状态
     */
    public void setFrozenStatus(String frozenStatus) {
        this.frozenStatus = frozenStatus;
    }

    /**
     * 返回 冻结日期
     *
     * @return 冻结日期
     */
    public Date getFrozenDate() {
        return frozenDate;
    }

    /**
     * 设置 冻结日期
     *
     * @param frozenDate 冻结日期
     */
    public void setFrozenDate(Date frozenDate) {
        this.frozenDate = frozenDate;
    }

    /**
     * 返回 更改受理时间
     *
     * @return 更改受理时间
     */
    public Date getModifyAcceptDate() {
        return modifyAcceptDate;
    }

    /**
     * 设置 更改受理时间
     *
     * @param modifyAcceptDate 更改受理时间
     */
    public void setModifyAcceptDate(Date modifyAcceptDate) {
        this.modifyAcceptDate = modifyAcceptDate;
    }

    /**
     * 返回 版本号
     *
     * @return 版本号
     */
    public Long getVersionNo() {
        return versionNo;
    }

    /**
     * 设置 版本号
     *
     * @param versionNo 版本号
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    /**
     * 返回 扩展字段
     *
     * @return 扩展字段
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * 设置 扩展字段
     *
     * @param attribute 扩展字段
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /**
     * 返回 属性对象
     *
     * @return 属性对象
     */
    public TradeAttributeDO getTradeAttributeDO() {
    	TradeAttributeDO attributeDO = null;
    	try {
    		attributeDO = GsonUtils.getInstance().fromJson(this.attribute, TradeAttributeDO.class);
        } catch (Exception e) {
			e.printStackTrace();
		}
        return null == attributeDO?new TradeAttributeDO():attributeDO;
    }

    /**
     * 设置 属性对象
     *
     * @param tradeAttributeDO 属性对象
     */
    public void setTradeAttributeDO(TradeAttributeDO tradeAttributeDO) {
        this.tradeAttributeDO = tradeAttributeDO;
        // 设置attribute
        this.attribute =  GsonUtils.getInstance().toJson(tradeAttributeDO);
    }

    /**
     * 返回 创建时间
     *
     * @return 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置 创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 返回 修改时间
     *
     * @return 修改时间
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * 设置 修改时间
     *
     * @param modifyDate 修改时间
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * 返回 备注
     *
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置 备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 返回 物流交易历史表ID
     *
     * @return 物流交易历史表ID
     */
    public Long getHistoryId() {
        return historyId;
    }
    
    
    
    
    

    /**
     * 设置 物流交易历史表ID
     *
     * @param historyId 物流交易历史表ID
     */
    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public BigDecimal getUnInvoiceAmount() {
        if (getTransportIncome() != null && getInvoiceAmount() != null) {
            return getTransportIncome().subtract(getInvoiceAmount());
        } else if (getTransportIncome() != null && getInvoiceAmount() == null) {
            return getTransportIncome();
        } else {
            return BigDecimal.valueOf(0);
        }
    }

    public BigDecimal getUnInvoiceLiAmount() {
        if (getLogisticsIncome() != null && getInvoiceLiAmount() != null) {
            return getLogisticsIncome().subtract(getInvoiceLiAmount());
        } else if (getLogisticsIncome() != null && getInvoiceLiAmount() == null) {
            return getLogisticsIncome();
        } else {
            return BigDecimal.valueOf(0);
        }
    }

    /* (non Javadoc) 
     * @Title: toString
     * @Description: TODO
     * @return 
     * @see java.lang.Object#toString() 
     */
    @Override
    public String toString() {
        return "TradeDO [id=" + id + ", orderNo=" + orderNo + ", orderType=" + orderType + ", orderSubType="
                + orderSubType + ", productId=" + productId + ", productType=" + productType + ", businessType="
                + businessType + ", sourceBillNoId=" + sourceBillNoId + ", sourceBillNo=" + sourceBillNo
                + ", refSourceBillNo=" + refSourceBillNo + ", sourceBillType=" + sourceBillType + ", unifiedSettlement="
                + unifiedSettlement + ", paymentType=" + paymentType + ", amount=" + amount + ", isDomestic="
                + isDomestic + ", currencyType=" + currencyType + ", exchangeRate=" + exchangeRate + ", verifyAmount="
                + verifyAmount + ", unverifyAmount=" + unverifyAmount + ", discountBillNo=" + discountBillNo
                + ", discountAmount=" + discountAmount + ", postDiscountAmount=" + postDiscountAmount
                + ", actionOrderNo=" + actionOrderNo + ", statementBillNo=" + statementBillNo + ", currentStatementNo="
                + currentStatementNo + ", isSalesDepartment=" + isSalesDepartment + ", customerType=" + customerType
                + ", customerName=" + customerName + ", customerCode=" + customerCode + ", accountCustomerType="
                + accountCustomerType + ", accountCustomerName=" + accountCustomerName + ", accountCustomerCode="
                + accountCustomerCode + ", masterCustomerType=" + masterCustomerType + ", masterCustomerName="
                + masterCustomerName + ", masterCustomerCode=" + masterCustomerCode + ", actionOrgName=" + actionOrgName
                + ", actionOrgCode=" + actionOrgCode + ", actionOrgUnifiedName=" + actionOrgUnifiedName
                + ", receivableOrgComCode='" + receivableOrgComCode + ", origOrgComCode='" + origOrgComCode
                + ", destOrgComCode='" + destOrgComCode
                + ", burdenOrgName=" + burdenOrgName + ", burdenOrgCode=" + burdenOrgCode + ", burdenOrgUnifiedCode="
                + burdenOrgUnifiedCode + ", contractOrgName=" + contractOrgName + ", contractOrgCode=" + contractOrgCode
                + ", contractOrgUnifiedCode=" + contractOrgUnifiedCode + ", actualOrgName=" + actualOrgName
                + ", actualOrgCode=" + actualOrgCode + ", actualOrgUnifiedCode=" + actualOrgUnifiedCode + ", bigArea="
                + bigArea + ", smallArea=" + smallArea + ", invoiceMark=" + invoiceMark + ", invoiceAmount="
                + invoiceAmount + ", invoiceLiAmount=" + invoiceLiAmount + ", transportIncome="
                + transportIncome + ", logisticsIncome=" + logisticsIncome + ", transportIncomeNoTax="
                + transportIncomeNoTax + ", logisticsIncomeNoTax=" + logisticsIncomeNoTax + ", transportIncomeTax="
                + transportIncomeTax + ", logisticsIncomeTax=" + logisticsIncomeTax + ", createUserCode="
                + createUserCode + ", createUserName=" + createUserName + ", createOrgCode=" + createOrgCode
                + ", createOrgName=" + createOrgName + ", modifyUserCode=" + modifyUserCode + ", modifyUserName="
                + modifyUserName + ", statementStatus=" + statementStatus + ", statementDate=" + statementDate
                + ", businessStatus=" + businessStatus + ", businessDate=" + businessDate + ", businessOverDate="
                + businessOverDate + ", payExceedDate=" + payExceedDate + ", payStatus=" + payStatus + ", frozenStatus="
                + frozenStatus + ", frozenDate=" + frozenDate + ", modifyAcceptDate=" + modifyAcceptDate
                + ", versionNo=" + versionNo + ", attribute=" + attribute + ", tradeAttributeDO=" + tradeAttributeDO
                + ", createDate=" + createDate + ", modifyDate=" + modifyDate + ", remark="
                + remark + ", historyId=" + historyId +"option="+option+ "]";
    }



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountCustomerCode == null) ? 0 : accountCustomerCode.hashCode());
		result = prime * result + ((accountCustomerName == null) ? 0 : accountCustomerName.hashCode());
		result = prime * result + ((accountCustomerType == null) ? 0 : accountCustomerType.hashCode());
		result = prime * result + ((actionOrderNo == null) ? 0 : actionOrderNo.hashCode());
		result = prime * result + ((actionOrgCode == null) ? 0 : actionOrgCode.hashCode());
		result = prime * result + ((actionOrgName == null) ? 0 : actionOrgName.hashCode());
		result = prime * result + ((actionOrgUnifiedName == null) ? 0 : actionOrgUnifiedName.hashCode());
		result = prime * result + ((actualOrgCode == null) ? 0 : actualOrgCode.hashCode());
		result = prime * result + ((actualOrgName == null) ? 0 : actualOrgName.hashCode());
		result = prime * result + ((actualOrgUnifiedCode == null) ? 0 : actualOrgUnifiedCode.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + ((bigArea == null) ? 0 : bigArea.hashCode());
		result = prime * result + ((burdenOrgCode == null) ? 0 : burdenOrgCode.hashCode());
		result = prime * result + ((burdenOrgName == null) ? 0 : burdenOrgName.hashCode());
		result = prime * result + ((burdenOrgUnifiedCode == null) ? 0 : burdenOrgUnifiedCode.hashCode());
		result = prime * result + ((businessDate == null) ? 0 : businessDate.hashCode());
		result = prime * result + ((businessOverDate == null) ? 0 : businessOverDate.hashCode());
		result = prime * result + ((businessStatus == null) ? 0 : businessStatus.hashCode());
		result = prime * result + ((businessType == null) ? 0 : businessType.hashCode());
		result = prime * result + ((contractOrgCode == null) ? 0 : contractOrgCode.hashCode());
		result = prime * result + ((contractOrgName == null) ? 0 : contractOrgName.hashCode());
		result = prime * result + ((contractOrgUnifiedCode == null) ? 0 : contractOrgUnifiedCode.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((createOrgCode == null) ? 0 : createOrgCode.hashCode());
		result = prime * result + ((createOrgName == null) ? 0 : createOrgName.hashCode());
		result = prime * result + ((createUserCode == null) ? 0 : createUserCode.hashCode());
		result = prime * result + ((createUserName == null) ? 0 : createUserName.hashCode());
		result = prime * result + ((currencyType == null) ? 0 : currencyType.hashCode());
		result = prime * result + ((currentStatementNo == null) ? 0 : currentStatementNo.hashCode());
		result = prime * result + ((customerCode == null) ? 0 : customerCode.hashCode());
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + ((customerType == null) ? 0 : customerType.hashCode());
		result = prime * result + ((destOrgComCode == null) ? 0 : destOrgComCode.hashCode());
		result = prime * result + ((discountAmount == null) ? 0 : discountAmount.hashCode());
		result = prime * result + ((discountBillNo == null) ? 0 : discountBillNo.hashCode());
		result = prime * result + ((exchangeRate == null) ? 0 : exchangeRate.hashCode());
		result = prime * result + ((frozenDate == null) ? 0 : frozenDate.hashCode());
		result = prime * result + ((frozenStatus == null) ? 0 : frozenStatus.hashCode());
		result = prime * result + ((historyId == null) ? 0 : historyId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((invoiceAmount == null) ? 0 : invoiceAmount.hashCode());
		result = prime * result + ((invoiceLiAmount == null) ? 0 : invoiceLiAmount.hashCode());
		result = prime * result + ((invoiceMark == null) ? 0 : invoiceMark.hashCode());
		result = prime * result + ((isDomestic == null) ? 0 : isDomestic.hashCode());
		result = prime * result + ((isSalesDepartment == null) ? 0 : isSalesDepartment.hashCode());
		result = prime * result + ((logisticsIncome == null) ? 0 : logisticsIncome.hashCode());
		result = prime * result + ((logisticsIncomeNoTax == null) ? 0 : logisticsIncomeNoTax.hashCode());
		result = prime * result + ((logisticsIncomeTax == null) ? 0 : logisticsIncomeTax.hashCode());
		result = prime * result + ((masterCustomerCode == null) ? 0 : masterCustomerCode.hashCode());
		result = prime * result + ((masterCustomerName == null) ? 0 : masterCustomerName.hashCode());
		result = prime * result + ((masterCustomerType == null) ? 0 : masterCustomerType.hashCode());
		result = prime * result + ((modifyAcceptDate == null) ? 0 : modifyAcceptDate.hashCode());
		result = prime * result + ((modifyDate == null) ? 0 : modifyDate.hashCode());
		result = prime * result + ((modifyUserCode == null) ? 0 : modifyUserCode.hashCode());
		result = prime * result + ((modifyUserName == null) ? 0 : modifyUserName.hashCode());
		result = prime * result + ((orderNo == null) ? 0 : orderNo.hashCode());
		result = prime * result + ((orderSubType == null) ? 0 : orderSubType.hashCode());
		result = prime * result + ((orderType == null) ? 0 : orderType.hashCode());
		result = prime * result + ((origOrgComCode == null) ? 0 : origOrgComCode.hashCode());
		result = prime * result + ((payExceedDate == null) ? 0 : payExceedDate.hashCode());
		result = prime * result + ((payStatus == null) ? 0 : payStatus.hashCode());
		result = prime * result + ((paymentNo == null) ? 0 : paymentNo.hashCode());
		result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
		result = prime * result + ((postDiscountAmount == null) ? 0 : postDiscountAmount.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((productType == null) ? 0 : productType.hashCode());
		result = prime * result + ((receivableOrgComCode == null) ? 0 : receivableOrgComCode.hashCode());
		result = prime * result + ((refSourceBillNo == null) ? 0 : refSourceBillNo.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((smallArea == null) ? 0 : smallArea.hashCode());
		result = prime * result + ((sourceBillNo == null) ? 0 : sourceBillNo.hashCode());
		result = prime * result + ((sourceBillNoId == null) ? 0 : sourceBillNoId.hashCode());
		result = prime * result + Arrays.hashCode(sourceBillNos);
		result = prime * result + ((sourceBillType == null) ? 0 : sourceBillType.hashCode());
		result = prime * result + ((sourceSystem == null) ? 0 : sourceSystem.hashCode());
		result = prime * result + ((statementBillNo == null) ? 0 : statementBillNo.hashCode());
		result = prime * result + ((statementDate == null) ? 0 : statementDate.hashCode());
		result = prime * result + ((statementStatus == null) ? 0 : statementStatus.hashCode());
		result = prime * result + ((tradeAttributeDO == null) ? 0 : tradeAttributeDO.hashCode());
		result = prime * result + ((transportIncome == null) ? 0 : transportIncome.hashCode());
		result = prime * result + ((transportIncomeNoTax == null) ? 0 : transportIncomeNoTax.hashCode());
		result = prime * result + ((transportIncomeTax == null) ? 0 : transportIncomeTax.hashCode());
		result = prime * result + ((unifiedSettlement == null) ? 0 : unifiedSettlement.hashCode());
		result = prime * result + ((unverifyAmount == null) ? 0 : unverifyAmount.hashCode());
		result = prime * result + ((verifyAmount == null) ? 0 : verifyAmount.hashCode());
		result = prime * result + ((versionNo == null) ? 0 : versionNo.hashCode());
		result = prime * result + ((waybillNo == null) ? 0 : waybillNo.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeDO other = (TradeDO) obj;
		if (accountCustomerCode == null) {
			if (other.accountCustomerCode != null)
				return false;
		} else if (!accountCustomerCode.equals(other.accountCustomerCode))
			return false;
		if (accountCustomerName == null) {
			if (other.accountCustomerName != null)
				return false;
		} else if (!accountCustomerName.equals(other.accountCustomerName))
			return false;
		if (accountCustomerType == null) {
			if (other.accountCustomerType != null)
				return false;
		} else if (!accountCustomerType.equals(other.accountCustomerType))
			return false;
		if (actionOrderNo == null) {
			if (other.actionOrderNo != null)
				return false;
		} else if (!actionOrderNo.equals(other.actionOrderNo))
			return false;
		if (actionOrgCode == null) {
			if (other.actionOrgCode != null)
				return false;
		} else if (!actionOrgCode.equals(other.actionOrgCode))
			return false;
		if (actionOrgName == null) {
			if (other.actionOrgName != null)
				return false;
		} else if (!actionOrgName.equals(other.actionOrgName))
			return false;
		if (actionOrgUnifiedName == null) {
			if (other.actionOrgUnifiedName != null)
				return false;
		} else if (!actionOrgUnifiedName.equals(other.actionOrgUnifiedName))
			return false;
		if (actualOrgCode == null) {
			if (other.actualOrgCode != null)
				return false;
		} else if (!actualOrgCode.equals(other.actualOrgCode))
			return false;
		if (actualOrgName == null) {
			if (other.actualOrgName != null)
				return false;
		} else if (!actualOrgName.equals(other.actualOrgName))
			return false;
		if (actualOrgUnifiedCode == null) {
			if (other.actualOrgUnifiedCode != null)
				return false;
		} else if (!actualOrgUnifiedCode.equals(other.actualOrgUnifiedCode))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		if (bigArea == null) {
			if (other.bigArea != null)
				return false;
		} else if (!bigArea.equals(other.bigArea))
			return false;
		if (burdenOrgCode == null) {
			if (other.burdenOrgCode != null)
				return false;
		} else if (!burdenOrgCode.equals(other.burdenOrgCode))
			return false;
		if (burdenOrgName == null) {
			if (other.burdenOrgName != null)
				return false;
		} else if (!burdenOrgName.equals(other.burdenOrgName))
			return false;
		if (burdenOrgUnifiedCode == null) {
			if (other.burdenOrgUnifiedCode != null)
				return false;
		} else if (!burdenOrgUnifiedCode.equals(other.burdenOrgUnifiedCode))
			return false;
		if (businessDate == null) {
			if (other.businessDate != null)
				return false;
		} else if (!businessDate.equals(other.businessDate))
			return false;
		if (businessOverDate == null) {
			if (other.businessOverDate != null)
				return false;
		} else if (!businessOverDate.equals(other.businessOverDate))
			return false;
		if (businessStatus == null) {
			if (other.businessStatus != null)
				return false;
		} else if (!businessStatus.equals(other.businessStatus))
			return false;
		if (businessType == null) {
			if (other.businessType != null)
				return false;
		} else if (!businessType.equals(other.businessType))
			return false;
		if (contractOrgCode == null) {
			if (other.contractOrgCode != null)
				return false;
		} else if (!contractOrgCode.equals(other.contractOrgCode))
			return false;
		if (contractOrgName == null) {
			if (other.contractOrgName != null)
				return false;
		} else if (!contractOrgName.equals(other.contractOrgName))
			return false;
		if (contractOrgUnifiedCode == null) {
			if (other.contractOrgUnifiedCode != null)
				return false;
		} else if (!contractOrgUnifiedCode.equals(other.contractOrgUnifiedCode))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (createOrgCode == null) {
			if (other.createOrgCode != null)
				return false;
		} else if (!createOrgCode.equals(other.createOrgCode))
			return false;
		if (createOrgName == null) {
			if (other.createOrgName != null)
				return false;
		} else if (!createOrgName.equals(other.createOrgName))
			return false;
		if (createUserCode == null) {
			if (other.createUserCode != null)
				return false;
		} else if (!createUserCode.equals(other.createUserCode))
			return false;
		if (createUserName == null) {
			if (other.createUserName != null)
				return false;
		} else if (!createUserName.equals(other.createUserName))
			return false;
		if (currencyType == null) {
			if (other.currencyType != null)
				return false;
		} else if (!currencyType.equals(other.currencyType))
			return false;
		if (currentStatementNo == null) {
			if (other.currentStatementNo != null)
				return false;
		} else if (!currentStatementNo.equals(other.currentStatementNo))
			return false;
		if (customerCode == null) {
			if (other.customerCode != null)
				return false;
		} else if (!customerCode.equals(other.customerCode))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (customerType == null) {
			if (other.customerType != null)
				return false;
		} else if (!customerType.equals(other.customerType))
			return false;
		if (destOrgComCode == null) {
			if (other.destOrgComCode != null)
				return false;
		} else if (!destOrgComCode.equals(other.destOrgComCode))
			return false;
		if (discountAmount == null) {
			if (other.discountAmount != null)
				return false;
		} else if (!discountAmount.equals(other.discountAmount))
			return false;
		if (discountBillNo == null) {
			if (other.discountBillNo != null)
				return false;
		} else if (!discountBillNo.equals(other.discountBillNo))
			return false;
		if (exchangeRate == null) {
			if (other.exchangeRate != null)
				return false;
		} else if (!exchangeRate.equals(other.exchangeRate))
			return false;
		if (frozenDate == null) {
			if (other.frozenDate != null)
				return false;
		} else if (!frozenDate.equals(other.frozenDate))
			return false;
		if (frozenStatus == null) {
			if (other.frozenStatus != null)
				return false;
		} else if (!frozenStatus.equals(other.frozenStatus))
			return false;
		if (historyId == null) {
			if (other.historyId != null)
				return false;
		} else if (!historyId.equals(other.historyId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (invoiceAmount == null) {
			if (other.invoiceAmount != null)
				return false;
		} else if (!invoiceAmount.equals(other.invoiceAmount))
			return false;
		if (invoiceLiAmount == null) {
			if (other.invoiceLiAmount != null)
				return false;
		} else if (!invoiceLiAmount.equals(other.invoiceLiAmount))
			return false;
		if (invoiceMark == null) {
			if (other.invoiceMark != null)
				return false;
		} else if (!invoiceMark.equals(other.invoiceMark))
			return false;
		if (isDomestic == null) {
			if (other.isDomestic != null)
				return false;
		} else if (!isDomestic.equals(other.isDomestic))
			return false;
		if (isSalesDepartment == null) {
			if (other.isSalesDepartment != null)
				return false;
		} else if (!isSalesDepartment.equals(other.isSalesDepartment))
			return false;
		if (logisticsIncome == null) {
			if (other.logisticsIncome != null)
				return false;
		} else if (!logisticsIncome.equals(other.logisticsIncome))
			return false;
		if (logisticsIncomeNoTax == null) {
			if (other.logisticsIncomeNoTax != null)
				return false;
		} else if (!logisticsIncomeNoTax.equals(other.logisticsIncomeNoTax))
			return false;
		if (logisticsIncomeTax == null) {
			if (other.logisticsIncomeTax != null)
				return false;
		} else if (!logisticsIncomeTax.equals(other.logisticsIncomeTax))
			return false;
		if (masterCustomerCode == null) {
			if (other.masterCustomerCode != null)
				return false;
		} else if (!masterCustomerCode.equals(other.masterCustomerCode))
			return false;
		if (masterCustomerName == null) {
			if (other.masterCustomerName != null)
				return false;
		} else if (!masterCustomerName.equals(other.masterCustomerName))
			return false;
		if (masterCustomerType == null) {
			if (other.masterCustomerType != null)
				return false;
		} else if (!masterCustomerType.equals(other.masterCustomerType))
			return false;
		if (modifyAcceptDate == null) {
			if (other.modifyAcceptDate != null)
				return false;
		} else if (!modifyAcceptDate.equals(other.modifyAcceptDate))
			return false;
		if (modifyDate == null) {
			if (other.modifyDate != null)
				return false;
		} else if (!modifyDate.equals(other.modifyDate))
			return false;
		if (modifyUserCode == null) {
			if (other.modifyUserCode != null)
				return false;
		} else if (!modifyUserCode.equals(other.modifyUserCode))
			return false;
		if (modifyUserName == null) {
			if (other.modifyUserName != null)
				return false;
		} else if (!modifyUserName.equals(other.modifyUserName))
			return false;
		if (orderNo == null) {
			if (other.orderNo != null)
				return false;
		} else if (!orderNo.equals(other.orderNo))
			return false;
		if (orderSubType == null) {
			if (other.orderSubType != null)
				return false;
		} else if (!orderSubType.equals(other.orderSubType))
			return false;
		if (orderType == null) {
			if (other.orderType != null)
				return false;
		} else if (!orderType.equals(other.orderType))
			return false;
		if (origOrgComCode == null) {
			if (other.origOrgComCode != null)
				return false;
		} else if (!origOrgComCode.equals(other.origOrgComCode))
			return false;
		if (payExceedDate == null) {
			if (other.payExceedDate != null)
				return false;
		} else if (!payExceedDate.equals(other.payExceedDate))
			return false;
		if (payStatus == null) {
			if (other.payStatus != null)
				return false;
		} else if (!payStatus.equals(other.payStatus))
			return false;
		if (paymentNo == null) {
			if (other.paymentNo != null)
				return false;
		} else if (!paymentNo.equals(other.paymentNo))
			return false;
		if (paymentType == null) {
			if (other.paymentType != null)
				return false;
		} else if (!paymentType.equals(other.paymentType))
			return false;
		if (postDiscountAmount == null) {
			if (other.postDiscountAmount != null)
				return false;
		} else if (!postDiscountAmount.equals(other.postDiscountAmount))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (productType == null) {
			if (other.productType != null)
				return false;
		} else if (!productType.equals(other.productType))
			return false;
		if (receivableOrgComCode == null) {
			if (other.receivableOrgComCode != null)
				return false;
		} else if (!receivableOrgComCode.equals(other.receivableOrgComCode))
			return false;
		if (refSourceBillNo == null) {
			if (other.refSourceBillNo != null)
				return false;
		} else if (!refSourceBillNo.equals(other.refSourceBillNo))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (smallArea == null) {
			if (other.smallArea != null)
				return false;
		} else if (!smallArea.equals(other.smallArea))
			return false;
		if (sourceBillNo == null) {
			if (other.sourceBillNo != null)
				return false;
		} else if (!sourceBillNo.equals(other.sourceBillNo))
			return false;
		if (sourceBillNoId == null) {
			if (other.sourceBillNoId != null)
				return false;
		} else if (!sourceBillNoId.equals(other.sourceBillNoId))
			return false;
		if (!Arrays.equals(sourceBillNos, other.sourceBillNos))
			return false;
		if (sourceBillType == null) {
			if (other.sourceBillType != null)
				return false;
		} else if (!sourceBillType.equals(other.sourceBillType))
			return false;
		if (sourceSystem == null) {
			if (other.sourceSystem != null)
				return false;
		} else if (!sourceSystem.equals(other.sourceSystem))
			return false;
		if (statementBillNo == null) {
			if (other.statementBillNo != null)
				return false;
		} else if (!statementBillNo.equals(other.statementBillNo))
			return false;
		if (statementDate == null) {
			if (other.statementDate != null)
				return false;
		} else if (!statementDate.equals(other.statementDate))
			return false;
		if (statementStatus == null) {
			if (other.statementStatus != null)
				return false;
		} else if (!statementStatus.equals(other.statementStatus))
			return false;
		if (tradeAttributeDO == null) {
			if (other.tradeAttributeDO != null)
				return false;
		} else if (!tradeAttributeDO.equals(other.tradeAttributeDO))
			return false;
		if (transportIncome == null) {
			if (other.transportIncome != null)
				return false;
		} else if (!transportIncome.equals(other.transportIncome))
			return false;
		if (transportIncomeNoTax == null) {
			if (other.transportIncomeNoTax != null)
				return false;
		} else if (!transportIncomeNoTax.equals(other.transportIncomeNoTax))
			return false;
		if (transportIncomeTax == null) {
			if (other.transportIncomeTax != null)
				return false;
		} else if (!transportIncomeTax.equals(other.transportIncomeTax))
			return false;
		if (unifiedSettlement == null) {
			if (other.unifiedSettlement != null)
				return false;
		} else if (!unifiedSettlement.equals(other.unifiedSettlement))
			return false;
		if (unverifyAmount == null) {
			if (other.unverifyAmount != null)
				return false;
		} else if (!unverifyAmount.equals(other.unverifyAmount))
			return false;
		if (verifyAmount == null) {
			if (other.verifyAmount != null)
				return false;
		} else if (!verifyAmount.equals(other.verifyAmount))
			return false;
		if (versionNo == null) {
			if (other.versionNo != null)
				return false;
		} else if (!versionNo.equals(other.versionNo))
			return false;
		if (waybillNo == null) {
			if (other.waybillNo != null)
				return false;
		} else if (!waybillNo.equals(other.waybillNo))
			return false;
		return true;
	}

}
