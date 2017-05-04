package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
//import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;

/**
 * 制作对账单查询DTO类，主要负责接受ACTION给Service传参
 * 
 * @author zhangjiheng
 * @date 2012-10-11 下午4:48:00
 */
public class StatementOfAccountMakeQueryDto implements Serializable {

	/**
	 * DTO类序列号
	 */
	private static final long serialVersionUID = -1827529959996184637L;

	/**
	 * 对账单实体
	 */
	private StatementOfAccountEntity statementOfAccountEntity;

	/**
	 * 查询tab页
	 */
	private String queryPage;

	/**
	 * 客户编码
	 */
	private String customerCode;
	
	/**
	 * 客户编码
	 */
	private String customerName;

	/**
	 * 账期开始日期
	 */
	private Date periodBeginDate;

	/**
	 * 账期结束日期
	 */
	private Date periodEndDate;

	/**
	 * 对账单类型
	 */
	private String billType;

	/**
	 * 对账单确认状态
	 */
	private String confirmStatus;

	/**
	 * 结账状态
	 */
	private String settleStatus;

	/**
	 * 明细单据类型
	 */
	private String[] billDetailTypes;

	/**
	 * 明细单据编号或运单号
	 */
	private String[] billDetailNos;

	/**
	 * 登录用户所属部门编码
	 */
	private String orgCode;

	/**
	 * 传递context,service用来获取部门和登录用户
	 */
//	private FossUserContext context;

	/**
	 * 对账单期次类型
	 */
	private String periodType;

	/**
	 * 是否包含应收单
	 */
	private Boolean isReceivable = false;

	/**
	 * 是否包含应付单
	 */
	private Boolean isPayable = false;

	/**
	 * 是否包含预收单
	 */
	private Boolean isDepositReceived = false;

	/**
	 * 是否包含预付单
	 */
	private Boolean isAdvancedPayment = false;

	/**
	 * 应收单单号集合
	 */
	private List<String> receivableBillNosList;

	/**
	 * 应付单单号集合
	 */
	private List<String> payableBillNosList;

	/**
	 * 预收单单号集合
	 */
	private List<String> depositReceivedBillNosList;

	/**
	 * 预付单单号集合
	 */
	private List<String> advancedPaymentBillNosList;

	/**
	 * 来源单号集合
	 */
	private List<String> sourceBillNosList;

	/**
	 * 对账单ID
	 */
	private String statementBillId;

	/**
	 * *********************以下为设置常量值，作为常量查询 条件***************************
	 */
	/**
	 * 对账单号
	 */
	private String statementBillNo;
	/**
	 * 付款单号
	 */
	private String paymentBillNo;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 应收单据子类型-代收货款
	 */
	private String rBillTypeCODReceivable;

	/**
	 * 应收单据子类型-空运其他应收单
	 */
	private String rBillTypeAirOtherReceivable;

	/**
	 * 应付单据子类型集合（代收货款、装卸费、服务补救）
	 */
	private List<String> pBillTypeList;
	/**
	 * 付款单汇款状态（未付款、付款中）
	 */
    private List<String> remitStatusList;
	/**
	 * 产品类型
	 */
	private List<String> productTypeList;

	/**
	 * 应付单支付状态
	 */
	private String payStatus;
	/**
	 * 预付单审批状态
	 */
	private String auditStatus;

	/**
	 * 空运其他应收单和所有应付单的审批状态
	 */
	private String approveStatus;

	/**
	 * 应付单是否有效
	 */
	private String effectiveStatus;

	/**
	 * 应收单父类型
	 */
	private String ysBillParentType;

	/**
	 * 应付单父类型
	 */
	private String yfBillParentType;

	/**
	 * 预收单父类型
	 */
	private String usBillParentType;

	/**
	 * 预收单父类型
	 */
	private String ufBillParentType;

	/**
	 * 是否查询期初明细信息
	 */
	private String queryBeginPriodFlag;
	
	/**
	 * 起始正单号 
	 */
	private String startNumber;
	
	/**
	 * 结束正单号
	 */
	private String endNumber;
	
	/**
	 * 正单号列表
	 */
	private List<String> airWaybillNos;
	
	/**
	 * 日期类型 --快递代理用
	 */
	private String dateType;
	
	/**
	 * *************************存放当前登录用户的所属信息***********************************
	 * 
	 */
	private UserEntity user;

	/**
	 * 发货联系人
	 */
	private String deliveryCustomerContact;
	
	/**
	 * 对账单部门
	 */
	private String statementOrgCode;
	
	/**
	 * 大区
	 */
	private String largeRegion;
	
	/**
	 * 小区
	 */
	private String smallRegion;
	
	/**
	 * 是否申请发票
	 */
	private String applyInvoice;
	
	/**
	 * 发票申请状态
	 */
	private String invoiceStatus;
	
	/**
	 * 可查询部门列表
	 */
	private List<String> orgCodeList;
	
	/**
	 * 当前登录人编码
	 */
	private String empCode;
	
	/**
	 * 是否统一结算
	 */
	private String receivableUnified;
	
	public List<String> getOrgCodeList() {
		return orgCodeList;
	}

	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getStatementOrgCode() {
		return statementOrgCode;
	}

	public void setStatementOrgCode(String statementOrgCode) {
		this.statementOrgCode = statementOrgCode;
	}

	public String getLargeRegion() {
		return largeRegion;
	}

	public void setLargeRegion(String largeRegion) {
		this.largeRegion = largeRegion;
	}

	public String getSmallRegion() {
		return smallRegion;
	}

	public void setSmallRegion(String smallRegion) {
		this.smallRegion = smallRegion;
	}

	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}
	
	/**
	 * @return 
		statementOfAccountEntity
	 */
	public StatementOfAccountEntity getStatementOfAccountEntity() {
		return statementOfAccountEntity;
	}

	
	/**
	 * @param 
		statementOfAccountEntity
	 */
	public void setStatementOfAccountEntity(StatementOfAccountEntity statementOfAccountEntity) {
		this.statementOfAccountEntity = statementOfAccountEntity;
	}

	
	/**
	 * @return 
		queryPage
	 */
	public String getQueryPage() {
		return queryPage;
	}

	
	/**
	 * @param 
		queryPage
	 */
	public void setQueryPage(String queryPage) {
		this.queryPage = queryPage;
	}

	
	/**
	 * @return 
		customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	
	/**
	 * @param 
		customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	
	/**
	 * @return 
		customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	
	/**
	 * @param 
		customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	
	/**
	 * @return 
		periodBeginDate
	 */
	public Date getPeriodBeginDate() {
		return periodBeginDate;
	}

	
	/**
	 * @param 
		periodBeginDate
	 */
	public void setPeriodBeginDate(Date periodBeginDate) {
		this.periodBeginDate = periodBeginDate;
	}

	
	/**
	 * @return 
		periodEndDate
	 */
	public Date getPeriodEndDate() {
		return periodEndDate;
	}

	
	/**
	 * @param 
		periodEndDate
	 */
	public void setPeriodEndDate(Date periodEndDate) {
		this.periodEndDate = periodEndDate;
	}

	
	/**
	 * @return 
		billType
	 */
	public String getBillType() {
		return billType;
	}

	
	/**
	 * @param 
		billType
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	
	/**
	 * @return 
		confirmStatus
	 */
	public String getConfirmStatus() {
		return confirmStatus;
	}

	
	/**
	 * @param 
		confirmStatus
	 */
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	
	/**
	 * @return 
		settleStatus
	 */
	public String getSettleStatus() {
		return settleStatus;
	}

	
	/**
	 * @param 
		settleStatus
	 */
	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}

	
	/**
	 * @return 
		billDetailNos
	 */
	public String[] getBillDetailNos() {
		return billDetailNos;
	}

	
	/**
	 * @param 
		billDetailNos
	 */
	public void setBillDetailNos(String[] billDetailNos) {
		this.billDetailNos = billDetailNos;
	}

	
	/**
	 * @return 
		orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	
	/**
	 * @param 
		orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
	/**
	 * @return 
		periodType
	 */
	public String getPeriodType() {
		return periodType;
	}

	
	/**
	 * @param 
		periodType
	 */
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	
	/**
	 * @return 
		isReceivable
	 */
	public Boolean getIsReceivable() {
		return isReceivable;
	}

	
	/**
	 * @param 
		isReceivable
	 */
	public void setIsReceivable(Boolean isReceivable) {
		this.isReceivable = isReceivable;
	}

	
	/**
	 * @return 
		isPayable
	 */
	public Boolean getIsPayable() {
		return isPayable;
	}

	
	/**
	 * @param 
		isPayable
	 */
	public void setIsPayable(Boolean isPayable) {
		this.isPayable = isPayable;
	}

	
	/**
	 * @return 
		isDepositReceived
	 */
	public Boolean getIsDepositReceived() {
		return isDepositReceived;
	}

	
	/**
	 * @param 
		isDepositReceived
	 */
	public void setIsDepositReceived(Boolean isDepositReceived) {
		this.isDepositReceived = isDepositReceived;
	}

	
	/**
	 * @return 
		isAdvancedPayment
	 */
	public Boolean getIsAdvancedPayment() {
		return isAdvancedPayment;
	}

	
	/**
	 * @param 
		isAdvancedPayment
	 */
	public void setIsAdvancedPayment(Boolean isAdvancedPayment) {
		this.isAdvancedPayment = isAdvancedPayment;
	}

	
	/**
	 * @return 
		receivableBillNosList
	 */
	public List<String> getReceivableBillNosList() {
		return receivableBillNosList;
	}

	
	/**
	 * @param 
		receivableBillNosList
	 */
	public void setReceivableBillNosList(List<String> receivableBillNosList) {
		this.receivableBillNosList = receivableBillNosList;
	}

	
	/**
	 * @return 
		payableBillNosList
	 */
	public List<String> getPayableBillNosList() {
		return payableBillNosList;
	}

	
	/**
	 * @param 
		payableBillNosList
	 */
	public void setPayableBillNosList(List<String> payableBillNosList) {
		this.payableBillNosList = payableBillNosList;
	}

	
	/**
	 * @return 
		depositReceivedBillNosList
	 */
	public List<String> getDepositReceivedBillNosList() {
		return depositReceivedBillNosList;
	}

	
	/**
	 * @param 
		depositReceivedBillNosList
	 */
	public void setDepositReceivedBillNosList(List<String> depositReceivedBillNosList) {
		this.depositReceivedBillNosList = depositReceivedBillNosList;
	}

	
	/**
	 * @return 
		advancedPaymentBillNosList
	 */
	public List<String> getAdvancedPaymentBillNosList() {
		return advancedPaymentBillNosList;
	}

	
	/**
	 * @param 
		advancedPaymentBillNosList
	 */
	public void setAdvancedPaymentBillNosList(List<String> advancedPaymentBillNosList) {
		this.advancedPaymentBillNosList = advancedPaymentBillNosList;
	}

	
	/**
	 * @return 
		sourceBillNosList
	 */
	public List<String> getSourceBillNosList() {
		return sourceBillNosList;
	}

	
	/**
	 * @param 
		sourceBillNosList
	 */
	public void setSourceBillNosList(List<String> sourceBillNosList) {
		this.sourceBillNosList = sourceBillNosList;
	}

	
	/**
	 * @return 
		statementBillId
	 */
	public String getStatementBillId() {
		return statementBillId;
	}

	
	/**
	 * @param 
		statementBillId
	 */
	public void setStatementBillId(String statementBillId) {
		this.statementBillId = statementBillId;
	}

	
	/**
	 * @return 
		statementBillNo
	 */
	public String getStatementBillNo() {
		return statementBillNo;
	}

	
	/**
	 * @param 
		statementBillNo
	 */
	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}

	
	/**
	 * @return 
		paymentBillNo
	 */
	public String getPaymentBillNo() {
		return paymentBillNo;
	}

	
	/**
	 * @param 
		paymentBillNo
	 */
	public void setPaymentBillNo(String paymentBillNo) {
		this.paymentBillNo = paymentBillNo;
	}

	
	/**
	 * @return 
		active
	 */
	public String getActive() {
		return active;
	}

	
	/**
	 * @param 
		active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	
	/**
	 * @return 
		rBillTypeCODReceivable
	 */
	public String getrBillTypeCODReceivable() {
		return rBillTypeCODReceivable;
	}

	
	/**
	 * @param 
		rBillTypeCODReceivable
	 */
	public void setrBillTypeCODReceivable(String rBillTypeCODReceivable) {
		this.rBillTypeCODReceivable = rBillTypeCODReceivable;
	}

	
	/**
	 * @return 
		rBillTypeAirOtherReceivable
	 */
	public String getrBillTypeAirOtherReceivable() {
		return rBillTypeAirOtherReceivable;
	}

	
	/**
	 * @param 
		rBillTypeAirOtherReceivable
	 */
	public void setrBillTypeAirOtherReceivable(String rBillTypeAirOtherReceivable) {
		this.rBillTypeAirOtherReceivable = rBillTypeAirOtherReceivable;
	}

	
	/**
	 * @return 
		pBillTypeList
	 */
	public List<String> getpBillTypeList() {
		return pBillTypeList;
	}

	
	/**
	 * @param 
		pBillTypeList
	 */
	public void setpBillTypeList(List<String> pBillTypeList) {
		this.pBillTypeList = pBillTypeList;
	}

	
	/**
	 * @return 
		remitStatusList
	 */
	public List<String> getRemitStatusList() {
		return remitStatusList;
	}

	
	/**
	 * @param 
		remitStatusList
	 */
	public void setRemitStatusList(List<String> remitStatusList) {
		this.remitStatusList = remitStatusList;
	}

	
	/**
	 * @return 
		productTypeList
	 */
	public List<String> getProductTypeList() {
		return productTypeList;
	}

	
	/**
	 * @param 
		productTypeList
	 */
	public void setProductTypeList(List<String> productTypeList) {
		this.productTypeList = productTypeList;
	}

	
	/**
	 * @return 
		payStatus
	 */
	public String getPayStatus() {
		return payStatus;
	}

	
	/**
	 * @param 
		payStatus
	 */
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	
	/**
	 * @return 
		auditStatus
	 */
	public String getAuditStatus() {
		return auditStatus;
	}

	
	/**
	 * @param 
		auditStatus
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	
	/**
	 * @return 
		approveStatus
	 */
	public String getApproveStatus() {
		return approveStatus;
	}

	
	/**
	 * @param 
		approveStatus
	 */
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	
	/**
	 * @return 
		effectiveStatus
	 */
	public String getEffectiveStatus() {
		return effectiveStatus;
	}

	
	/**
	 * @param 
		effectiveStatus
	 */
	public void setEffectiveStatus(String effectiveStatus) {
		this.effectiveStatus = effectiveStatus;
	}

	
	/**
	 * @return 
		ysBillParentType
	 */
	public String getYsBillParentType() {
		return ysBillParentType;
	}

	
	/**
	 * @param 
		ysBillParentType
	 */
	public void setYsBillParentType(String ysBillParentType) {
		this.ysBillParentType = ysBillParentType;
	}

	
	/**
	 * @return 
		yfBillParentType
	 */
	public String getYfBillParentType() {
		return yfBillParentType;
	}

	
	/**
	 * @param 
		yfBillParentType
	 */
	public void setYfBillParentType(String yfBillParentType) {
		this.yfBillParentType = yfBillParentType;
	}

	
	/**
	 * @return 
		usBillParentType
	 */
	public String getUsBillParentType() {
		return usBillParentType;
	}

	
	/**
	 * @param 
		usBillParentType
	 */
	public void setUsBillParentType(String usBillParentType) {
		this.usBillParentType = usBillParentType;
	}

	
	/**
	 * @return 
		ufBillParentType
	 */
	public String getUfBillParentType() {
		return ufBillParentType;
	}

	
	/**
	 * @param 
		ufBillParentType
	 */
	public void setUfBillParentType(String ufBillParentType) {
		this.ufBillParentType = ufBillParentType;
	}

	
	/**
	 * @return 
		queryBeginPriodFlag
	 */
	public String getQueryBeginPriodFlag() {
		return queryBeginPriodFlag;
	}

	
	/**
	 * @param 
		queryBeginPriodFlag
	 */
	public void setQueryBeginPriodFlag(String queryBeginPriodFlag) {
		this.queryBeginPriodFlag = queryBeginPriodFlag;
	}

	
	/**
	 * @return 
		user
	 */
	public UserEntity getUser() {
		return user;
	}

	
	/**
	 * @param 
		user
	 */
	public void setUser(UserEntity user) {
		this.user = user;
	}

	/**
	 * @return billDetailTypes
	 */
	public String[] getBillDetailTypes() {
		return billDetailTypes;
	}

	/**
	 * @param billDetailTypes
	 */
	public void setBillDetailTypes(String[] billDetailTypes) {
		this.billDetailTypes = billDetailTypes;
		/**
		 * 将数据转换成4个boolean类型的值
		 */

		//如果单据类型不为空
		if (billDetailTypes != null && billDetailTypes.length > 0) {
			//循环处理
			for (int i = 0; i < billDetailTypes.length; i++) {
				//如果为应收单类型
				if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE
						.equals(billDetailTypes[i])) {
					//设置包含应收单状态
					isReceivable = true;
				//如果为应付单类型
				} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE
						.equals(billDetailTypes[i])) {
					//设置包含应付单状态
					isPayable = true;
				//如果为预收单类型	
				} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED
						.equals(billDetailTypes[i])) {
					//设置包含预收单状态
					isDepositReceived = true;
				//如果为预付单类型
				} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT
						.equals(billDetailTypes[i])) {
					//设置包含预付单状态
					isAdvancedPayment = true;
				}
			}
		}
	}


	/**
	 * @GET
	 * @return startNumber
	 */
	public String getStartNumber() {
		/*
		 *@get
		 *@ return startNumber
		 */
		return startNumber;
	}


	/**
	 * @SET
	 * @param startNumber
	 */
	public void setStartNumber(String startNumber) {
		/*
		 *@set
		 *@this.startNumber = startNumber
		 */
		this.startNumber = startNumber;
	}


	/**
	 * @GET
	 * @return endNumber
	 */
	public String getEndNumber() {
		/*
		 *@get
		 *@ return endNumber
		 */
		return endNumber;
	}


	/**
	 * @SET
	 * @param endNumber
	 */
	public void setEndNumber(String endNumber) {
		/*
		 *@set
		 *@this.endNumber = endNumber
		 */
		this.endNumber = endNumber;
	}


	public List<String> getAirWaybillNos() {
		return airWaybillNos;
	}


	public void setAirWaybillNos(List<String> airWaybillNos) {
		this.airWaybillNos = airWaybillNos;
	}


	/**
	 * @GET
	 * @return dateType
	 */
	public String getDateType() {
		/*
		 *@get
		 *@ return dateType
		 */
		return dateType;
	}


	/**
	 * @SET
	 * @param dateType
	 */
	public void setDateType(String dateType) {
		/*
		 *@set
		 *@this.dateType = dateType
		 */
		this.dateType = dateType;
	}

	public String getApplyInvoice() {
		return applyInvoice;
	}

	public void setApplyInvoice(String applyInvoice) {
		this.applyInvoice = applyInvoice;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getReceivableUnified() {
		return receivableUnified;
	}

	public void setReceivableUnified(String receivableUnified) {
		this.receivableUnified = receivableUnified;
	}

}
