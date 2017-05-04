package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 代收货款实例
 * @author 269044
 * @time 2017年04月07日
 */
public class CodDO implements Serializable {
	
	private static final long serialVersionUID = 5289574926023944551L;
	/**是否有效*/
	private String active;
	/**id*/
	private Long id;
	/**运单号*/
	private String waybillNo;
	/**运单id*/
	private String waybillNoId;
	/**应付交易单号*/
	private String payableBillNo;
	/**应付部门编码*/
	private String payableOrgCode;
	/**应付部门名称*/
	private String payableOrgName;
	/**应付部门标杆编码*/
	private String payableUnifiedCode;
	/**应付部门子公司编码*/
	private String payableComCode;
	/**应付部门子公司名称*/
	private String payableComName;
	/**金额*/
	private BigDecimal codAmount;
	/**代收货款类型*/
	private String codType;
	/**收款人账号*/
	private String accountNo;
	/**收款人手机*/
	private String payeePhone;
	/**客户编码*/
	private String customerCode;
	/**客户名称*/
	private String customerName;
	/**收款人名称*/
	private String payeeName;
	/**收款人与发货人关系*/
	private String payeeRelationship;
	/**对公对私标志 账户性质，对公对私支付宝*/
	private String publicPrivateFlag;
	/**开户人姓名*/
	private String accountName;
	/**批次号*/
	private String batchNumber;
	/**业务时间*/
	private Date businessDate;
	/**退款成功时间*/
	private Date refundSuccessDate;
	/**是否初始化*/
	private String isInit;
	/**制单人部门名称*/
	private String createOrgName;
	/**制单人部门编码*/
	private String createOrgCode;
	/**代收货款状态 未退款、退款中、已退款、资金部冻结*/
	private String status;
	/**空运代收货款状态*/
	private String airStatus;
	/**营业部冻结时间*/
	private Date orgFreezeTime;
	/**营业部冻结人名称*/
	private String orgFreezeUserName;
	/**营业部冻结人编码*/
	private String orgFreezeUserCode;
    /**账号修改时间*/
	private Date accountModifyTime;
	/**账号修改人编码*/
	private String accountModifyUserCode;
	/**账号修改人名称*/
	private String accountModifyUserName;
	/**营业部审核时间*/
	private Date orgAuditTime;
	/**营业部审核人编码*/
	private String orgAuditUserCode;
	/**营业部审核人名称*/
	private String orgAuditUserName;
	/**营业部经理审核时间*/
	private Date orgManagerAuditTime;
	/**营业部经理审核人编码*/
	private String orgManagerAuditCode;
	/**营业部经理审核人名称*/
	private String orgManagerAuditName;
	/** 营业部经理退回原因*/
	private String refundNotes;
	/** 资金部冻结时间*/
	private Date tusyorgFreezeDate;
	/** 资金部取消冻结时间*/
	private Date tusyorgClfreezeDate;
	/** 资金部取消冻结人编码*/
	private String tusyorgClfreezeUserCode;
	/** 资金部取消冻结人名称*/
	private String tusyorgClfreezeUserName;
	/** 资金部冻结人编码*/
	private String tusyorgFreezeUserCode;
	/** 资金部冻结人名称*/
	private String tusyorgFreezeUserName;
	/** 资金部审核时间*/
	private Date tusyorgAuditDate;
	/** 资金部审核人名称*/
	private String tusyorgAuditUserName;
	/** 资金部审核人编码*/
	private String tusyorgAuditUserCode;
	/** 资金部退款申请时间*/
	private Date tusyorgRfdApptime;
	/** 资金部退款申请人编码*/
	private String tusyorgRfdAppUserCode;
	/** 资金部退款申请人名称*/
	private String tusyorgRfdAppUserName;
	/** 汇款导出时间*/
	private Date codExportDate;
	/** 汇款导出人名称*/
	private String codExportName;
	/** 汇款导出人编码*/
	private String codExportCode;
	/** 汇款失败/反汇款成功原因*/
	private String remitTanceFailNotes;
	/** 空运审核时间*/
	private Date airOrgAuditDate;
	/** 空运审核人编码*/
	private String airOrgAuditUserCode;
	/** 空运审核人名称*/
	private String airOrgAuditUserName;
	/** 创建时间*/
	private Date createDate;
	/** 修改时间*/
	private Date modifyDate;
	/** 修改人编码*/
	private String modifyUserCode;
	/** 修改人名称*/
	private String modifyUserName;
	/** 版本号*/
	private Long versionNo;
	/** 退款路径 线上,线下*/
	private String refundPath;
	/** 客户类型*/
	private String customerType;
	/** 合并编号*/
	private String mergeCode;
	/** 公司付款账号:银行账户*/
	private String comAccount;
	/** 快递代理审核时间*/
	private Date expressOrgAuditTime;
	/** 快递代理审核人编码*/
	private String expressOrgAuditUserCode;
	/** 快递代理审核人名称*/
	private String expressOrgAuditUserName;
	/** 是否签收*/
	private String isSign;
	/** 快递代理审核状态*/
	private String expressAuditStatus;
	/** 营业部审核状态*/
	private String orgAuditStatus;
	/** 出发部门名称*/
	private String origOrgName;
	/**出发部门编码*/
	private String origOrgCode;
	/** 到达部门名称*/
	private String destOrgName;
	/** 到达部门编码*/
	private String destOrgCode;
	/** 运输性质*/
	private String productCode;
	/** 是否有货物轨迹 收银货签收时候获取*/
	private String isTrack;
	/** 发货人名称*/
	private String deliverCustomerName;
	/** 发货人编码*/
	private String deliverCustomerCode;
	/** 收货人编码*/
	private String receiveCustomerCode;
	/** 收货人名称*/
	private String receiveCustomerName;
	/** 收银确认时间*/
	private Date confirmTime;
	/** 签收时间*/
	private Date signDate;
	/** 支付审核状态*/
	private String lockStatus;
	/** 代收批次状态:已发送/银企审核通过/银企审核不通过*/
	private String batchStatus;
	/** 付款方式*/
	private String paymentType;
	/** 开单日期*/
	private Date billDate;
	/** 代收手续费*/
	private BigDecimal codFee;
	/** 签收开单时长*/
	private Long signBillDiffer;
	/** 更改金额*/
	private BigDecimal changeAmount;
	/** 对账单号*/
	private String statementBillNo;
	/** 开户行名称*/
	private String bankHqName;
	/** 开户行编码*/
	private String bankHqCode;
	/** 省份编码*/
	private String provinceCode;
	/** 省份名称*/
	private String provinceName;
	/** 市编码*/
	private String cityCode;
	/** 市名称*/
	private String cityName;
	/** 支行编码*/
	private String bankBranchCode;
	/** 支行名称*/
	private String bankBranchName;
	/** 账户类型*/
	private String accountType;
	/** 银企审核不通过原因*/
	private String fundAuditFailNotes;
	/** 到达部门电话*/
	private String destOrgTelephone;
	/** 扩展字段*/
	private String attribute;
	/** 按位标志*/
	private Long option;
	/** 备注*/
	private String remark;
	/** 收银确认状态*/
	private String comfirmStatus;
	/** 收银确认时间*/
	private Date comfirmTime;
	/**已冲销金额*/
	private BigDecimal verifyCodAmount;
	/** 到达部门收银员姓名-收银确认操作人*/
	private String destCashierName;
	/** 业务类型*/
	private String businessType;
	/** 签收人*/
	private String signer;
	/** 签收代理编码*/
	private String agencyCode;
	/** 签收代理名称*/
	private String agencyName;
	/** 目的站*/
	private String destination;
	/** 代理类型*/
	private String agencyType;
    /**标记去支付审核*/
	private String isPayAudit;
	// 以下应用字段--非数据库*/
	/**原来状态值*/
	private String oldStatus;
	/** 更改时间*/
	private Date changeTime;
	/**是否可以合并 true可以 false不可以*/
	private boolean canMerge;
	/**是否有效*/
	private boolean payActive;
	/**失败结果审核*/
	private String auditResult;
	
	
	/**
	 * 获取是否有效<p>
	 * @return  active  是否有效<br>
	 */
	public String getActive() {
		return active;
	}
	
	/**
	 * 设置是否有效<p>
	 * @param  active  是否有效<br>
	 */
	public void setActive(String active) {
		this.active = active;
	}
	
	/**
	 * 获取id<p>
	 * @return  id  id<br>
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * 设置id<p>
	 * @param  id  id<br>
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 获取运单号<p>
	 * @return  waybillNo  运单号<br>
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	 * 设置运单号<p>
	 * @param  waybillNo  运单号<br>
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	 * 获取运单id<p>
	 * @return  waybillNoId  运单id<br>
	 */
	public String getWaybillNoId() {
		return waybillNoId;
	}
	
	/**
	 * 设置运单id<p>
	 * @param  waybillNoId  运单id<br>
	 */
	public void setWaybillNoId(String waybillNoId) {
		this.waybillNoId = waybillNoId;
	}
	
	/**
	 * 获取应付交易单号<p>
	 * @return  payableBillNo  应付交易单号<br>
	 */
	public String getPayableBillNo() {
		return payableBillNo;
	}
	
	/**
	 * 设置应付交易单号<p>
	 * @param  payableBillNo  应付交易单号<br>
	 */
	public void setPayableBillNo(String payableBillNo) {
		this.payableBillNo = payableBillNo;
	}
	
	/**
	 * 获取应付部门编码<p>
	 * @return  payableOrgCode  应付部门编码<br>
	 */
	public String getPayableOrgCode() {
		return payableOrgCode;
	}
	
	/**
	 * 设置应付部门编码<p>
	 * @param  payableOrgCode  应付部门编码<br>
	 */
	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}
	
	/**
	 * 获取应付部门名称<p>
	 * @return  payableOrgName  应付部门名称<br>
	 */
	public String getPayableOrgName() {
		return payableOrgName;
	}
	
	/**
	 * 设置应付部门名称<p>
	 * @param  payableOrgName  应付部门名称<br>
	 */
	public void setPayableOrgName(String payableOrgName) {
		this.payableOrgName = payableOrgName;
	}
	
	/**
	 * 获取应付部门标杆编码<p>
	 * @return  payableUnifiedCode  应付部门标杆编码<br>
	 */
	public String getPayableUnifiedCode() {
		return payableUnifiedCode;
	}
	
	/**
	 * 设置应付部门标杆编码<p>
	 * @param  payableUnifiedCode  应付部门标杆编码<br>
	 */
	public void setPayableUnifiedCode(String payableUnifiedCode) {
		this.payableUnifiedCode = payableUnifiedCode;
	}
	
	/**
	 * 获取应付部门子公司编码<p>
	 * @return  payableComCode  应付部门子公司编码<br>
	 */
	public String getPayableComCode() {
		return payableComCode;
	}
	
	/**
	 * 设置应付部门子公司编码<p>
	 * @param  payableComCode  应付部门子公司编码<br>
	 */
	public void setPayableComCode(String payableComCode) {
		this.payableComCode = payableComCode;
	}
	
	/**
	 * 获取应付部门子公司名称<p>
	 * @return  payableComName  应付部门子公司名称<br>
	 */
	public String getPayableComName() {
		return payableComName;
	}
	
	/**
	 * 设置应付部门子公司名称<p>
	 * @param  payableComName  应付部门子公司名称<br>
	 */
	public void setPayableComName(String payableComName) {
		this.payableComName = payableComName;
	}
	
	/**
	 * 获取金额<p>
	 * @return  codAmount  金额<br>
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}
	
	/**
	 * 设置金额<p>
	 * @param  codAmount  金额<br>
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}
	
	/**
	 * 获取代收货款类型<p>
	 * @return  codType  代收货款类型<br>
	 */
	public String getCodType() {
		return codType;
	}
	
	/**
	 * 设置代收货款类型<p>
	 * @param  codType  代收货款类型<br>
	 */
	public void setCodType(String codType) {
		this.codType = codType;
	}
	
	/**
	 * 获取收款人账号<p>
	 * @return  accountNo  收款人账号<br>
	 */
	public String getAccountNo() {
		return accountNo;
	}
	
	/**
	 * 设置收款人账号<p>
	 * @param  accountNo  收款人账号<br>
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	/**
	 * 获取收款人手机<p>
	 * @return  payeePhone  收款人手机<br>
	 */
	public String getPayeePhone() {
		return payeePhone;
	}
	
	/**
	 * 设置收款人手机<p>
	 * @param  payeePhone  收款人手机<br>
	 */
	public void setPayeePhone(String payeePhone) {
		this.payeePhone = payeePhone;
	}
	
	/**
	 * 获取客户编码<p>
	 * @return  customerCode  客户编码<br>
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	
	/**
	 * 设置客户编码<p>
	 * @param  customerCode  客户编码<br>
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	/**
	 * 获取客户名称<p>
	 * @return  customerName  客户名称<br>
	 */
	public String getCustomerName() {
		return customerName;
	}
	
	/**
	 * 设置客户名称<p>
	 * @param  customerName  客户名称<br>
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	/**
	 * 获取收款人名称<p>
	 * @return  payeeName  收款人名称<br>
	 */
	public String getPayeeName() {
		return payeeName;
	}
	
	/**
	 * 设置收款人名称<p>
	 * @param  payeeName  收款人名称<br>
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	
	/**
	 * 获取收款人与发货人关系<p>
	 * @return  payeeRelationship  收款人与发货人关系<br>
	 */
	public String getPayeeRelationship() {
		return payeeRelationship;
	}
	
	/**
	 * 设置收款人与发货人关系<p>
	 * @param  payeeRelationship  收款人与发货人关系<br>
	 */
	public void setPayeeRelationship(String payeeRelationship) {
		this.payeeRelationship = payeeRelationship;
	}
	
	/**
	 * 获取对公对私标志账户性质，对公对私支付宝<p>
	 * @return  publicPrivateFlag  对公对私标志账户性质，对公对私支付宝<br>
	 */
	public String getPublicPrivateFlag() {
		return publicPrivateFlag;
	}
	
	/**
	 * 设置对公对私标志账户性质，对公对私支付宝<p>
	 * @param  publicPrivateFlag  对公对私标志账户性质，对公对私支付宝<br>
	 */
	public void setPublicPrivateFlag(String publicPrivateFlag) {
		this.publicPrivateFlag = publicPrivateFlag;
	}
	
	/**
	 * 获取开户人姓名<p>
	 * @return  accountName  开户人姓名<br>
	 */
	public String getAccountName() {
		return accountName;
	}
	
	/**
	 * 设置开户人姓名<p>
	 * @param  accountName  开户人姓名<br>
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	/**
	 * 获取批次号<p>
	 * @return  batchNumber  批次号<br>
	 */
	public String getBatchNumber() {
		return batchNumber;
	}
	
	/**
	 * 设置批次号<p>
	 * @param  batchNumber  批次号<br>
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	
	/**
	 * 获取业务时间<p>
	 * @return  businessDate  业务时间<br>
	 */
	public Date getBusinessDate() {
		return businessDate;
	}
	
	/**
	 * 设置业务时间<p>
	 * @param  businessDate  业务时间<br>
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}
	
	/**
	 * 获取退款成功时间<p>
	 * @return  refundSuccessDate  退款成功时间<br>
	 */
	public Date getRefundSuccessDate() {
		return refundSuccessDate;
	}
	
	/**
	 * 设置退款成功时间<p>
	 * @param  refundSuccessDate  退款成功时间<br>
	 */
	public void setRefundSuccessDate(Date refundSuccessDate) {
		this.refundSuccessDate = refundSuccessDate;
	}
	
	/**
	 * 获取是否初始化<p>
	 * @return  isInit  是否初始化<br>
	 */
	public String getIsInit() {
		return isInit;
	}
	
	/**
	 * 设置是否初始化<p>
	 * @param  isInit  是否初始化<br>
	 */
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}
	
	/**
	 * 获取制单人部门名称<p>
	 * @return  createOrgName  制单人部门名称<br>
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}
	
	/**
	 * 设置制单人部门名称<p>
	 * @param  createOrgName  制单人部门名称<br>
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	
	/**
	 * 获取制单人部门编码<p>
	 * @return  createOrgCode  制单人部门编码<br>
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	
	/**
	 * 设置制单人部门编码<p>
	 * @param  createOrgCode  制单人部门编码<br>
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	
	/**
	 * 获取代收货款状态未退款、退款中、已退款、资金部冻结<p>
	 * @return  status  代收货款状态未退款、退款中、已退款、资金部冻结<br>
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * 设置代收货款状态未退款、退款中、已退款、资金部冻结<p>
	 * @param  status  代收货款状态未退款、退款中、已退款、资金部冻结<br>
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 获取空运代收货款状态<p>
	 * @return  airStatus  空运代收货款状态<br>
	 */
	public String getAirStatus() {
		return airStatus;
	}
	
	/**
	 * 设置空运代收货款状态<p>
	 * @param  airStatus  空运代收货款状态<br>
	 */
	public void setAirStatus(String airStatus) {
		this.airStatus = airStatus;
	}
	
	/**
	 * 获取营业部冻结时间<p>
	 * @return  orgFreezeTime  营业部冻结时间<br>
	 */
	public Date getOrgFreezeTime() {
		return orgFreezeTime;
	}
	
	/**
	 * 设置营业部冻结时间<p>
	 * @param  orgFreezeTime  营业部冻结时间<br>
	 */
	public void setOrgFreezeTime(Date orgFreezeTime) {
		this.orgFreezeTime = orgFreezeTime;
	}
	
	/**
	 * 获取营业部冻结人名称<p>
	 * @return  orgFreezeUserName  营业部冻结人名称<br>
	 */
	public String getOrgFreezeUserName() {
		return orgFreezeUserName;
	}
	
	/**
	 * 设置营业部冻结人名称<p>
	 * @param  orgFreezeUserName  营业部冻结人名称<br>
	 */
	public void setOrgFreezeUserName(String orgFreezeUserName) {
		this.orgFreezeUserName = orgFreezeUserName;
	}
	
	/**
	 * 获取营业部冻结人编码<p>
	 * @return  orgFreezeUserCode  营业部冻结人编码<br>
	 */
	public String getOrgFreezeUserCode() {
		return orgFreezeUserCode;
	}
	
	/**
	 * 设置营业部冻结人编码<p>
	 * @param  orgFreezeUserCode  营业部冻结人编码<br>
	 */
	public void setOrgFreezeUserCode(String orgFreezeUserCode) {
		this.orgFreezeUserCode = orgFreezeUserCode;
	}
	
	/**
	 * 获取账号修改时间<p>
	 * @return  accountModifyTime  账号修改时间<br>
	 */
	public Date getAccountModifyTime() {
		return accountModifyTime;
	}
	
	/**
	 * 设置账号修改时间<p>
	 * @param  accountModifyTime  账号修改时间<br>
	 */
	public void setAccountModifyTime(Date accountModifyTime) {
		this.accountModifyTime = accountModifyTime;
	}
	
	/**
	 * 获取账号修改人编码<p>
	 * @return  accountModifyUserCode  账号修改人编码<br>
	 */
	public String getAccountModifyUserCode() {
		return accountModifyUserCode;
	}
	
	/**
	 * 设置账号修改人编码<p>
	 * @param  accountModifyUserCode  账号修改人编码<br>
	 */
	public void setAccountModifyUserCode(String accountModifyUserCode) {
		this.accountModifyUserCode = accountModifyUserCode;
	}
	
	/**
	 * 获取账号修改人名称<p>
	 * @return  accountModifyUserName  账号修改人名称<br>
	 */
	public String getAccountModifyUserName() {
		return accountModifyUserName;
	}
	
	/**
	 * 设置账号修改人名称<p>
	 * @param  accountModifyUserName  账号修改人名称<br>
	 */
	public void setAccountModifyUserName(String accountModifyUserName) {
		this.accountModifyUserName = accountModifyUserName;
	}
	
	/**
	 * 获取营业部审核时间<p>
	 * @return  orgAuditTime  营业部审核时间<br>
	 */
	public Date getOrgAuditTime() {
		return orgAuditTime;
	}
	
	/**
	 * 设置营业部审核时间<p>
	 * @param  orgAuditTime  营业部审核时间<br>
	 */
	public void setOrgAuditTime(Date orgAuditTime) {
		this.orgAuditTime = orgAuditTime;
	}
	
	/**
	 * 获取营业部审核人编码<p>
	 * @return  orgAuditUserCode  营业部审核人编码<br>
	 */
	public String getOrgAuditUserCode() {
		return orgAuditUserCode;
	}
	
	/**
	 * 设置营业部审核人编码<p>
	 * @param  orgAuditUserCode  营业部审核人编码<br>
	 */
	public void setOrgAuditUserCode(String orgAuditUserCode) {
		this.orgAuditUserCode = orgAuditUserCode;
	}
	
	/**
	 * 获取营业部审核人名称<p>
	 * @return  orgAuditUserName  营业部审核人名称<br>
	 */
	public String getOrgAuditUserName() {
		return orgAuditUserName;
	}
	
	/**
	 * 设置营业部审核人名称<p>
	 * @param  orgAuditUserName  营业部审核人名称<br>
	 */
	public void setOrgAuditUserName(String orgAuditUserName) {
		this.orgAuditUserName = orgAuditUserName;
	}
	
	/**
	 * 获取营业部经理审核时间<p>
	 * @return  orgManagerAuditTime  营业部经理审核时间<br>
	 */
	public Date getOrgManagerAuditTime() {
		return orgManagerAuditTime;
	}
	
	/**
	 * 设置营业部经理审核时间<p>
	 * @param  orgManagerAuditTime  营业部经理审核时间<br>
	 */
	public void setOrgManagerAuditTime(Date orgManagerAuditTime) {
		this.orgManagerAuditTime = orgManagerAuditTime;
	}
	
	/**
	 * 获取营业部经理审核人编码<p>
	 * @return  orgManagerAuditCode  营业部经理审核人编码<br>
	 */
	public String getOrgManagerAuditCode() {
		return orgManagerAuditCode;
	}
	
	/**
	 * 设置营业部经理审核人编码<p>
	 * @param  orgManagerAuditCode  营业部经理审核人编码<br>
	 */
	public void setOrgManagerAuditCode(String orgManagerAuditCode) {
		this.orgManagerAuditCode = orgManagerAuditCode;
	}
	
	/**
	 * 获取营业部经理审核人名称<p>
	 * @return  orgManagerAuditName  营业部经理审核人名称<br>
	 */
	public String getOrgManagerAuditName() {
		return orgManagerAuditName;
	}
	
	/**
	 * 设置营业部经理审核人名称<p>
	 * @param  orgManagerAuditName  营业部经理审核人名称<br>
	 */
	public void setOrgManagerAuditName(String orgManagerAuditName) {
		this.orgManagerAuditName = orgManagerAuditName;
	}
	
	/**
	 * 获取营业部经理退回原因<p>
	 * @return  refundNotes  营业部经理退回原因<br>
	 */
	public String getRefundNotes() {
		return refundNotes;
	}
	
	/**
	 * 设置营业部经理退回原因<p>
	 * @param  refundNotes  营业部经理退回原因<br>
	 */
	public void setRefundNotes(String refundNotes) {
		this.refundNotes = refundNotes;
	}
	
	/**
	 * 获取资金部冻结时间<p>
	 * @return  tusyorgFreezeDate  资金部冻结时间<br>
	 */
	public Date getTusyorgFreezeDate() {
		return tusyorgFreezeDate;
	}
	
	/**
	 * 设置资金部冻结时间<p>
	 * @param  tusyorgFreezeDate  资金部冻结时间<br>
	 */
	public void setTusyorgFreezeDate(Date tusyorgFreezeDate) {
		this.tusyorgFreezeDate = tusyorgFreezeDate;
	}
	
	/**
	 * 获取资金部取消冻结时间<p>
	 * @return  tusyorgClfreezeDate  资金部取消冻结时间<br>
	 */
	public Date getTusyorgClfreezeDate() {
		return tusyorgClfreezeDate;
	}
	
	/**
	 * 设置资金部取消冻结时间<p>
	 * @param  tusyorgClfreezeDate  资金部取消冻结时间<br>
	 */
	public void setTusyorgClfreezeDate(Date tusyorgClfreezeDate) {
		this.tusyorgClfreezeDate = tusyorgClfreezeDate;
	}
	
	/**
	 * 获取资金部取消冻结人编码<p>
	 * @return  tusyorgClfreezeUserCode  资金部取消冻结人编码<br>
	 */
	public String getTusyorgClfreezeUserCode() {
		return tusyorgClfreezeUserCode;
	}
	
	/**
	 * 设置资金部取消冻结人编码<p>
	 * @param  tusyorgClfreezeUserCode  资金部取消冻结人编码<br>
	 */
	public void setTusyorgClfreezeUserCode(String tusyorgClfreezeUserCode) {
		this.tusyorgClfreezeUserCode = tusyorgClfreezeUserCode;
	}
	
	/**
	 * 获取资金部取消冻结人名称<p>
	 * @return  tusyorgClfreezeUserName  资金部取消冻结人名称<br>
	 */
	public String getTusyorgClfreezeUserName() {
		return tusyorgClfreezeUserName;
	}
	
	/**
	 * 设置资金部取消冻结人名称<p>
	 * @param  tusyorgClfreezeUserName  资金部取消冻结人名称<br>
	 */
	public void setTusyorgClfreezeUserName(String tusyorgClfreezeUserName) {
		this.tusyorgClfreezeUserName = tusyorgClfreezeUserName;
	}
	
	/**
	 * 获取资金部冻结人编码<p>
	 * @return  tusyorgFreezeUserCode  资金部冻结人编码<br>
	 */
	public String getTusyorgFreezeUserCode() {
		return tusyorgFreezeUserCode;
	}
	
	/**
	 * 设置资金部冻结人编码<p>
	 * @param  tusyorgFreezeUserCode  资金部冻结人编码<br>
	 */
	public void setTusyorgFreezeUserCode(String tusyorgFreezeUserCode) {
		this.tusyorgFreezeUserCode = tusyorgFreezeUserCode;
	}
	
	/**
	 * 获取资金部冻结人名称<p>
	 * @return  tusyorgFreezeUserName  资金部冻结人名称<br>
	 */
	public String getTusyorgFreezeUserName() {
		return tusyorgFreezeUserName;
	}
	
	/**
	 * 设置资金部冻结人名称<p>
	 * @param  tusyorgFreezeUserName  资金部冻结人名称<br>
	 */
	public void setTusyorgFreezeUserName(String tusyorgFreezeUserName) {
		this.tusyorgFreezeUserName = tusyorgFreezeUserName;
	}
	
	/**
	 * 获取资金部审核时间<p>
	 * @return  tusyorgAuditDate  资金部审核时间<br>
	 */
	public Date getTusyorgAuditDate() {
		return tusyorgAuditDate;
	}
	
	/**
	 * 设置资金部审核时间<p>
	 * @param  tusyorgAuditDate  资金部审核时间<br>
	 */
	public void setTusyorgAuditDate(Date tusyorgAuditDate) {
		this.tusyorgAuditDate = tusyorgAuditDate;
	}
	
	/**
	 * 获取资金部审核人名称<p>
	 * @return  tusyorgAuditUserName  资金部审核人名称<br>
	 */
	public String getTusyorgAuditUserName() {
		return tusyorgAuditUserName;
	}
	
	/**
	 * 设置资金部审核人名称<p>
	 * @param  tusyorgAuditUserName  资金部审核人名称<br>
	 */
	public void setTusyorgAuditUserName(String tusyorgAuditUserName) {
		this.tusyorgAuditUserName = tusyorgAuditUserName;
	}
	
	/**
	 * 获取资金部审核人编码<p>
	 * @return  tusyorgAuditUserCode  资金部审核人编码<br>
	 */
	public String getTusyorgAuditUserCode() {
		return tusyorgAuditUserCode;
	}
	
	/**
	 * 设置资金部审核人编码<p>
	 * @param  tusyorgAuditUserCode  资金部审核人编码<br>
	 */
	public void setTusyorgAuditUserCode(String tusyorgAuditUserCode) {
		this.tusyorgAuditUserCode = tusyorgAuditUserCode;
	}
	
	/**
	 * 获取资金部退款申请时间<p>
	 * @return  tusyorgRfdApptime  资金部退款申请时间<br>
	 */
	public Date getTusyorgRfdApptime() {
		return tusyorgRfdApptime;
	}
	
	/**
	 * 设置资金部退款申请时间<p>
	 * @param  tusyorgRfdApptime  资金部退款申请时间<br>
	 */
	public void setTusyorgRfdApptime(Date tusyorgRfdApptime) {
		this.tusyorgRfdApptime = tusyorgRfdApptime;
	}
	
	/**
	 * 获取资金部退款申请人编码<p>
	 * @return  tusyorgRfdAppUserCode  资金部退款申请人编码<br>
	 */
	public String getTusyorgRfdAppUserCode() {
		return tusyorgRfdAppUserCode;
	}
	
	/**
	 * 设置资金部退款申请人编码<p>
	 * @param  tusyorgRfdAppUserCode  资金部退款申请人编码<br>
	 */
	public void setTusyorgRfdAppUserCode(String tusyorgRfdAppUserCode) {
		this.tusyorgRfdAppUserCode = tusyorgRfdAppUserCode;
	}
	
	/**
	 * 获取资金部退款申请人名称<p>
	 * @return  tusyorgRfdAppUserName  资金部退款申请人名称<br>
	 */
	public String getTusyorgRfdAppUserName() {
		return tusyorgRfdAppUserName;
	}
	
	/**
	 * 设置资金部退款申请人名称<p>
	 * @param  tusyorgRfdAppUserName  资金部退款申请人名称<br>
	 */
	public void setTusyorgRfdAppUserName(String tusyorgRfdAppUserName) {
		this.tusyorgRfdAppUserName = tusyorgRfdAppUserName;
	}
	
	/**
	 * 获取汇款导出时间<p>
	 * @return  codExportDate  汇款导出时间<br>
	 */
	public Date getCodExportDate() {
		return codExportDate;
	}
	
	/**
	 * 设置汇款导出时间<p>
	 * @param  codExportDate  汇款导出时间<br>
	 */
	public void setCodExportDate(Date codExportDate) {
		this.codExportDate = codExportDate;
	}
	
	/**
	 * 获取汇款导出人名称<p>
	 * @return  codExportName  汇款导出人名称<br>
	 */
	public String getCodExportName() {
		return codExportName;
	}
	
	/**
	 * 设置汇款导出人名称<p>
	 * @param  codExportName  汇款导出人名称<br>
	 */
	public void setCodExportName(String codExportName) {
		this.codExportName = codExportName;
	}
	
	/**
	 * 获取汇款导出人编码<p>
	 * @return  codExportCode  汇款导出人编码<br>
	 */
	public String getCodExportCode() {
		return codExportCode;
	}
	
	/**
	 * 设置汇款导出人编码<p>
	 * @param  codExportCode  汇款导出人编码<br>
	 */
	public void setCodExportCode(String codExportCode) {
		this.codExportCode = codExportCode;
	}
	
	/**
	 * 获取汇款失败反汇款成功原因<p>
	 * @return  remitTanceFailNotes  汇款失败反汇款成功原因<br>
	 */
	public String getRemitTanceFailNotes() {
		return remitTanceFailNotes;
	}
	
	/**
	 * 设置汇款失败反汇款成功原因<p>
	 * @param  remitTanceFailNotes  汇款失败反汇款成功原因<br>
	 */
	public void setRemitTanceFailNotes(String remitTanceFailNotes) {
		this.remitTanceFailNotes = remitTanceFailNotes;
	}
	
	/**
	 * 获取空运审核时间<p>
	 * @return  airOrgAuditDate  空运审核时间<br>
	 */
	public Date getAirOrgAuditDate() {
		return airOrgAuditDate;
	}
	
	/**
	 * 设置空运审核时间<p>
	 * @param  airOrgAuditDate  空运审核时间<br>
	 */
	public void setAirOrgAuditDate(Date airOrgAuditDate) {
		this.airOrgAuditDate = airOrgAuditDate;
	}
	
	/**
	 * 获取空运审核人编码<p>
	 * @return  airOrgAuditUserCode  空运审核人编码<br>
	 */
	public String getAirOrgAuditUserCode() {
		return airOrgAuditUserCode;
	}
	
	/**
	 * 设置空运审核人编码<p>
	 * @param  airOrgAuditUserCode  空运审核人编码<br>
	 */
	public void setAirOrgAuditUserCode(String airOrgAuditUserCode) {
		this.airOrgAuditUserCode = airOrgAuditUserCode;
	}
	
	/**
	 * 获取空运审核人名称<p>
	 * @return  airOrgAuditUserName  空运审核人名称<br>
	 */
	public String getAirOrgAuditUserName() {
		return airOrgAuditUserName;
	}
	
	/**
	 * 设置空运审核人名称<p>
	 * @param  airOrgAuditUserName  空运审核人名称<br>
	 */
	public void setAirOrgAuditUserName(String airOrgAuditUserName) {
		this.airOrgAuditUserName = airOrgAuditUserName;
	}
	
	/**
	 * 获取创建时间<p>
	 * @return  createDate  创建时间<br>
	 */
	public Date getCreateDate() {
		return createDate;
	}
	
	/**
	 * 设置创建时间<p>
	 * @param  createDate  创建时间<br>
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * 获取修改时间<p>
	 * @return  modifyDate  修改时间<br>
	 */
	public Date getModifyDate() {
		return modifyDate;
	}
	
	/**
	 * 设置修改时间<p>
	 * @param  modifyDate  修改时间<br>
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	/**
	 * 获取修改人编码<p>
	 * @return  modifyUserCode  修改人编码<br>
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	
	/**
	 * 设置修改人编码<p>
	 * @param  modifyUserCode  修改人编码<br>
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	
	/**
	 * 获取修改人名称<p>
	 * @return  modifyUserName  修改人名称<br>
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}
	
	/**
	 * 设置修改人名称<p>
	 * @param  modifyUserName  修改人名称<br>
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	
	/**
	 * 获取版本号<p>
	 * @return  versionNo  版本号<br>
	 */
	public Long getVersionNo() {
		return versionNo;
	}
	
	/**
	 * 设置版本号<p>
	 * @param  versionNo  版本号<br>
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
	
	/**
	 * 获取退款路径线上线下<p>
	 * @return  refundPath  退款路径线上线下<br>
	 */
	public String getRefundPath() {
		return refundPath;
	}
	
	/**
	 * 设置退款路径线上线下<p>
	 * @param  refundPath  退款路径线上线下<br>
	 */
	public void setRefundPath(String refundPath) {
		this.refundPath = refundPath;
	}
	
	/**
	 * 获取客户类型<p>
	 * @return  customerType  客户类型<br>
	 */
	public String getCustomerType() {
		return customerType;
	}
	
	/**
	 * 设置客户类型<p>
	 * @param  customerType  客户类型<br>
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	/**
	 * 获取合并编号<p>
	 * @return  mergeCode  合并编号<br>
	 */
	public String getMergeCode() {
		return mergeCode;
	}
	
	/**
	 * 设置合并编号<p>
	 * @param  mergeCode  合并编号<br>
	 */
	public void setMergeCode(String mergeCode) {
		this.mergeCode = mergeCode;
	}
	
	/**
	 * 获取公司付款账号:银行账户<p>
	 * @return  comAccount  公司付款账号:银行账户<br>
	 */
	public String getComAccount() {
		return comAccount;
	}
	
	/**
	 * 设置公司付款账号:银行账户<p>
	 * @param  comAccount  公司付款账号:银行账户<br>
	 */
	public void setComAccount(String comAccount) {
		this.comAccount = comAccount;
	}
	
	/**
	 * 获取快递代理审核时间<p>
	 * @return  expressOrgAuditTime  快递代理审核时间<br>
	 */
	public Date getExpressOrgAuditTime() {
		return expressOrgAuditTime;
	}
	
	/**
	 * 设置快递代理审核时间<p>
	 * @param  expressOrgAuditTime  快递代理审核时间<br>
	 */
	public void setExpressOrgAuditTime(Date expressOrgAuditTime) {
		this.expressOrgAuditTime = expressOrgAuditTime;
	}
	
	/**
	 * 获取快递代理审核人编码<p>
	 * @return  expressOrgAuditUserCode  快递代理审核人编码<br>
	 */
	public String getExpressOrgAuditUserCode() {
		return expressOrgAuditUserCode;
	}
	
	/**
	 * 设置快递代理审核人编码<p>
	 * @param  expressOrgAuditUserCode  快递代理审核人编码<br>
	 */
	public void setExpressOrgAuditUserCode(String expressOrgAuditUserCode) {
		this.expressOrgAuditUserCode = expressOrgAuditUserCode;
	}
	
	/**
	 * 获取快递代理审核人名称<p>
	 * @return  expressOrgAuditUserName  快递代理审核人名称<br>
	 */
	public String getExpressOrgAuditUserName() {
		return expressOrgAuditUserName;
	}
	
	/**
	 * 设置快递代理审核人名称<p>
	 * @param  expressOrgAuditUserName  快递代理审核人名称<br>
	 */
	public void setExpressOrgAuditUserName(String expressOrgAuditUserName) {
		this.expressOrgAuditUserName = expressOrgAuditUserName;
	}
	
	/**
	 * 获取是否签收<p>
	 * @return  isSign  是否签收<br>
	 */
	public String getIsSign() {
		return isSign;
	}
	
	/**
	 * 设置是否签收<p>
	 * @param  isSign  是否签收<br>
	 */
	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}
	
	/**
	 * 获取快递代理审核状态<p>
	 * @return  expressAuditStatus  快递代理审核状态<br>
	 */
	public String getExpressAuditStatus() {
		return expressAuditStatus;
	}
	
	/**
	 * 设置快递代理审核状态<p>
	 * @param  expressAuditStatus  快递代理审核状态<br>
	 */
	public void setExpressAuditStatus(String expressAuditStatus) {
		this.expressAuditStatus = expressAuditStatus;
	}
	
	/**
	 * 获取营业部审核状态<p>
	 * @return  orgAuditStatus  营业部审核状态<br>
	 */
	public String getOrgAuditStatus() {
		return orgAuditStatus;
	}
	
	/**
	 * 设置营业部审核状态<p>
	 * @param  orgAuditStatus  营业部审核状态<br>
	 */
	public void setOrgAuditStatus(String orgAuditStatus) {
		this.orgAuditStatus = orgAuditStatus;
	}
	
	/**
	 * 获取出发部门名称<p>
	 * @return  origOrgName  出发部门名称<br>
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}
	
	/**
	 * 设置出发部门名称<p>
	 * @param  origOrgName  出发部门名称<br>
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}
	
	/**
	 * 获取出发部门编码<p>
	 * @return  origOrgCode  出发部门编码<br>
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	
	/**
	 * 设置出发部门编码<p>
	 * @param  origOrgCode  出发部门编码<br>
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	
	/**
	 * 获取到达部门名称<p>
	 * @return  destOrgName  到达部门名称<br>
	 */
	public String getDestOrgName() {
		return destOrgName;
	}
	
	/**
	 * 设置到达部门名称<p>
	 * @param  destOrgName  到达部门名称<br>
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	
	/**
	 * 获取到达部门编码<p>
	 * @return  destOrgCode  到达部门编码<br>
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}
	
	/**
	 * 设置到达部门编码<p>
	 * @param  destOrgCode  到达部门编码<br>
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	
	/**
	 * 获取运输性质<p>
	 * @return  productCode  运输性质<br>
	 */
	public String getProductCode() {
		return productCode;
	}
	
	/**
	 * 设置运输性质<p>
	 * @param  productCode  运输性质<br>
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	/**
	 * 获取是否有货物轨迹收银货签收时候获取<p>
	 * @return  isTrack  是否有货物轨迹收银货签收时候获取<br>
	 */
	public String getIsTrack() {
		return isTrack;
	}
	
	/**
	 * 设置是否有货物轨迹收银货签收时候获取<p>
	 * @param  isTrack  是否有货物轨迹收银货签收时候获取<br>
	 */
	public void setIsTrack(String isTrack) {
		this.isTrack = isTrack;
	}
	
	/**
	 * 获取发货人名称<p>
	 * @return  deliverCustomerName  发货人名称<br>
	 */
	public String getDeliverCustomerName() {
		return deliverCustomerName;
	}
	
	/**
	 * 设置发货人名称<p>
	 * @param  deliverCustomerName  发货人名称<br>
	 */
	public void setDeliverCustomerName(String deliverCustomerName) {
		this.deliverCustomerName = deliverCustomerName;
	}
	
	/**
	 * 获取发货人编码<p>
	 * @return  deliverCustomerCode  发货人编码<br>
	 */
	public String getDeliverCustomerCode() {
		return deliverCustomerCode;
	}
	
	/**
	 * 设置发货人编码<p>
	 * @param  deliverCustomerCode  发货人编码<br>
	 */
	public void setDeliverCustomerCode(String deliverCustomerCode) {
		this.deliverCustomerCode = deliverCustomerCode;
	}
	
	/**
	 * 获取收货人编码<p>
	 * @return  receiveCustomerCode  收货人编码<br>
	 */
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}
	
	/**
	 * 设置收货人编码<p>
	 * @param  receiveCustomerCode  收货人编码<br>
	 */
	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}
	
	/**
	 * 获取收货人名称<p>
	 * @return  receiveCustomerName  收货人名称<br>
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}
	
	/**
	 * 设置收货人名称<p>
	 * @param  receiveCustomerName  收货人名称<br>
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}
	
	/**
	 * 获取收银确认时间<p>
	 * @return  confirmTime  收银确认时间<br>
	 */
	public Date getConfirmTime() {
		return confirmTime;
	}
	
	/**
	 * 设置收银确认时间<p>
	 * @param  confirmTime  收银确认时间<br>
	 */
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	
	/**
	 * 获取签收时间<p>
	 * @return  signDate  签收时间<br>
	 */
	public Date getSignDate() {
		return signDate;
	}
	
	/**
	 * 设置签收时间<p>
	 * @param  signDate  签收时间<br>
	 */
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	
	/**
	 * 获取支付审核状态<p>
	 * @return  lockStatus  支付审核状态<br>
	 */
	public String getLockStatus() {
		return lockStatus;
	}
	
	/**
	 * 设置支付审核状态<p>
	 * @param  lockStatus  支付审核状态<br>
	 */
	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}
	
	/**
	 * 获取代收批次状态:已发送银企审核通过银企审核不通过<p>
	 * @return  batchStatus  代收批次状态:已发送银企审核通过银企审核不通过<br>
	 */
	public String getBatchStatus() {
		return batchStatus;
	}
	
	/**
	 * 设置代收批次状态:已发送银企审核通过银企审核不通过<p>
	 * @param  batchStatus  代收批次状态:已发送银企审核通过银企审核不通过<br>
	 */
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	
	/**
	 * 获取付款方式<p>
	 * @return  paymentType  付款方式<br>
	 */
	public String getPaymentType() {
		return paymentType;
	}
	
	/**
	 * 设置付款方式<p>
	 * @param  paymentType  付款方式<br>
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	/**
	 * 获取开单日期<p>
	 * @return  billDate  开单日期<br>
	 */
	public Date getBillDate() {
		return billDate;
	}
	
	/**
	 * 设置开单日期<p>
	 * @param  billDate  开单日期<br>
	 */
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	
	/**
	 * 获取代收手续费<p>
	 * @return  codFee  代收手续费<br>
	 */
	public BigDecimal getCodFee() {
		return codFee;
	}
	
	/**
	 * 设置代收手续费<p>
	 * @param  codFee  代收手续费<br>
	 */
	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}
	
	/**
	 * 获取签收开单时长<p>
	 * @return  signBillDiffer  签收开单时长<br>
	 */
	public Long getSignBillDiffer() {
		return signBillDiffer;
	}
	
	/**
	 * 设置签收开单时长<p>
	 * @param  signBillDiffer  签收开单时长<br>
	 */
	public void setSignBillDiffer(Long signBillDiffer) {
		this.signBillDiffer = signBillDiffer;
	}
	
	/**
	 * 获取更改金额<p>
	 * @return  changeAmount  更改金额<br>
	 */
	public BigDecimal getChangeAmount() {
		return changeAmount;
	}
	
	/**
	 * 设置更改金额<p>
	 * @param  changeAmount  更改金额<br>
	 */
	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}
	
	/**
	 * 获取对账单号<p>
	 * @return  statementBillNo  对账单号<br>
	 */
	public String getStatementBillNo() {
		return statementBillNo;
	}
	
	/**
	 * 设置对账单号<p>
	 * @param  statementBillNo  对账单号<br>
	 */
	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}
	
	/**
	 * 获取开户行名称<p>
	 * @return  bankHqName  开户行名称<br>
	 */
	public String getBankHqName() {
		return bankHqName;
	}
	
	/**
	 * 设置开户行名称<p>
	 * @param  bankHqName  开户行名称<br>
	 */
	public void setBankHqName(String bankHqName) {
		this.bankHqName = bankHqName;
	}
	
	/**
	 * 获取开户行编码<p>
	 * @return  bankHqCode  开户行编码<br>
	 */
	public String getBankHqCode() {
		return bankHqCode;
	}
	
	/**
	 * 设置开户行编码<p>
	 * @param  bankHqCode  开户行编码<br>
	 */
	public void setBankHqCode(String bankHqCode) {
		this.bankHqCode = bankHqCode;
	}
	
	/**
	 * 获取省份编码<p>
	 * @return  provinceCode  省份编码<br>
	 */
	public String getProvinceCode() {
		return provinceCode;
	}
	
	/**
	 * 设置省份编码<p>
	 * @param  provinceCode  省份编码<br>
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	/**
	 * 获取省份名称<p>
	 * @return  provinceName  省份名称<br>
	 */
	public String getProvinceName() {
		return provinceName;
	}
	
	/**
	 * 设置省份名称<p>
	 * @param  provinceName  省份名称<br>
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	/**
	 * 获取市编码<p>
	 * @return  cityCode  市编码<br>
	 */
	public String getCityCode() {
		return cityCode;
	}
	
	/**
	 * 设置市编码<p>
	 * @param  cityCode  市编码<br>
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	/**
	 * 获取市名称<p>
	 * @return  cityName  市名称<br>
	 */
	public String getCityName() {
		return cityName;
	}
	
	/**
	 * 设置市名称<p>
	 * @param  cityName  市名称<br>
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	/**
	 * 获取支行编码<p>
	 * @return  bankBranchCode  支行编码<br>
	 */
	public String getBankBranchCode() {
		return bankBranchCode;
	}
	
	/**
	 * 设置支行编码<p>
	 * @param  bankBranchCode  支行编码<br>
	 */
	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}
	
	/**
	 * 获取支行名称<p>
	 * @return  bankBranchName  支行名称<br>
	 */
	public String getBankBranchName() {
		return bankBranchName;
	}
	
	/**
	 * 设置支行名称<p>
	 * @param  bankBranchName  支行名称<br>
	 */
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	
	/**
	 * 获取账户类型<p>
	 * @return  accountType  账户类型<br>
	 */
	public String getAccountType() {
		return accountType;
	}
	
	/**
	 * 设置账户类型<p>
	 * @param  accountType  账户类型<br>
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	/**
	 * 获取银企审核不通过原因<p>
	 * @return  fundAuditFailNotes  银企审核不通过原因<br>
	 */
	public String getFundAuditFailNotes() {
		return fundAuditFailNotes;
	}
	
	/**
	 * 设置银企审核不通过原因<p>
	 * @param  fundAuditFailNotes  银企审核不通过原因<br>
	 */
	public void setFundAuditFailNotes(String fundAuditFailNotes) {
		this.fundAuditFailNotes = fundAuditFailNotes;
	}
	
	/**
	 * 获取到达部门电话<p>
	 * @return  destOrgTelephone  到达部门电话<br>
	 */
	public String getDestOrgTelephone() {
		return destOrgTelephone;
	}
	
	/**
	 * 设置到达部门电话<p>
	 * @param  destOrgTelephone  到达部门电话<br>
	 */
	public void setDestOrgTelephone(String destOrgTelephone) {
		this.destOrgTelephone = destOrgTelephone;
	}
	
	/**
	 * 获取扩展字段<p>
	 * @return  attribute  扩展字段<br>
	 */
	public String getAttribute() {
		return attribute;
	}
	
	/**
	 * 设置扩展字段<p>
	 * @param  attribute  扩展字段<br>
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	/**
	 * 获取按位标志<p>
	 * @return  option  按位标志<br>
	 */
	public Long getOption() {
		return option;
	}
	
	/**
	 * 设置按位标志<p>
	 * @param  option  按位标志<br>
	 */
	public void setOption(Long option) {
		this.option = option;
	}
	
	/**
	 * 获取备注<p>
	 * @return  remark  备注<br>
	 */
	public String getRemark() {
		return remark;
	}
	
	/**
	 * 设置备注<p>
	 * @param  remark  备注<br>
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 获取收银确认状态<p>
	 * @return  comfirmStatus  收银确认状态<br>
	 */
	public String getComfirmStatus() {
		return comfirmStatus;
	}
	
	/**
	 * 设置收银确认状态<p>
	 * @param  comfirmStatus  收银确认状态<br>
	 */
	public void setComfirmStatus(String comfirmStatus) {
		this.comfirmStatus = comfirmStatus;
	}
	
	/**
	 * 获取收银确认时间<p>
	 * @return  comfirmTime  收银确认时间<br>
	 */
	public Date getComfirmTime() {
		return comfirmTime;
	}
	
	/**
	 * 设置收银确认时间<p>
	 * @param  comfirmTime  收银确认时间<br>
	 */
	public void setComfirmTime(Date comfirmTime) {
		this.comfirmTime = comfirmTime;
	}
	
	/**
	 * 获取已冲销金额<p>
	 * @return  verifyCodAmount  已冲销金额<br>
	 */
	public BigDecimal getVerifyCodAmount() {
		return verifyCodAmount;
	}
	
	/**
	 * 设置已冲销金额<p>
	 * @param  verifyCodAmount  已冲销金额<br>
	 */
	public void setVerifyCodAmount(BigDecimal verifyCodAmount) {
		this.verifyCodAmount = verifyCodAmount;
	}
	
	/**
	 * 获取到达部门收银员姓名-收银确认操作人<p>
	 * @return  destCashierName  到达部门收银员姓名-收银确认操作人<br>
	 */
	public String getDestCashierName() {
		return destCashierName;
	}
	
	/**
	 * 设置到达部门收银员姓名-收银确认操作人<p>
	 * @param  destCashierName  到达部门收银员姓名-收银确认操作人<br>
	 */
	public void setDestCashierName(String destCashierName) {
		this.destCashierName = destCashierName;
	}
	
	/**
	 * 获取业务类型<p>
	 * @return  businessType  业务类型<br>
	 */
	public String getBusinessType() {
		return businessType;
	}
	
	/**
	 * 设置业务类型<p>
	 * @param  businessType  业务类型<br>
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	/**
	 * 获取签收人<p>
	 * @return  signer  签收人<br>
	 */
	public String getSigner() {
		return signer;
	}
	
	/**
	 * 设置签收人<p>
	 * @param  signer  签收人<br>
	 */
	public void setSigner(String signer) {
		this.signer = signer;
	}
	
	/**
	 * 获取签收代理编码<p>
	 * @return  agencyCode  签收代理编码<br>
	 */
	public String getAgencyCode() {
		return agencyCode;
	}
	
	/**
	 * 设置签收代理编码<p>
	 * @param  agencyCode  签收代理编码<br>
	 */
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	
	/**
	 * 获取签收代理名称<p>
	 * @return  agencyName  签收代理名称<br>
	 */
	public String getAgencyName() {
		return agencyName;
	}
	
	/**
	 * 设置签收代理名称<p>
	 * @param  agencyName  签收代理名称<br>
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	
	/**
	 * 获取目的站<p>
	 * @return  destination  目的站<br>
	 */
	public String getDestination() {
		return destination;
	}
	
	/**
	 * 设置目的站<p>
	 * @param  destination  目的站<br>
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	/**
	 * 获取代理类型<p>
	 * @return  agencyType  代理类型<br>
	 */
	public String getAgencyType() {
		return agencyType;
	}
	
	/**
	 * 设置代理类型<p>
	 * @param  agencyType  代理类型<br>
	 */
	public void setAgencyType(String agencyType) {
		this.agencyType = agencyType;
	}
	
	/**
	 * 获取标记去支付审核<p>
	 * @return  isPayAudit  标记去支付审核<br>
	 */
	public String getIsPayAudit() {
		return isPayAudit;
	}
	
	/**
	 * 设置标记去支付审核<p>
	 * @param  isPayAudit  标记去支付审核<br>
	 */
	public void setIsPayAudit(String isPayAudit) {
		this.isPayAudit = isPayAudit;
	}
	
	/**
	 * 获取以下应用字段-<p>
	 * @return  oldStatus  以下应用字段-<br>
	 */
	public String getOldStatus() {
		return oldStatus;
	}
	
	/**
	 * 设置以下应用字段-<p>
	 * @param  oldStatus  以下应用字段-<br>
	 */
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}
	
	/**
	 * 获取更改时间<p>
	 * @return  changeTime  更改时间<br>
	 */
	public Date getChangeTime() {
		return changeTime;
	}
	
	/**
	 * 设置更改时间<p>
	 * @param  changeTime  更改时间<br>
	 */
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	
	/**
	 * 获取是否可以合并true可以false不可以<p>
	 * @return  canMerge  是否可以合并true可以false不可以<br>
	 */
	public boolean isCanMerge() {
		return canMerge;
	}
	
	/**
	 * 设置是否可以合并true可以false不可以<p>
	 * @param  canMerge  是否可以合并true可以false不可以<br>
	 */
	public void setCanMerge(boolean canMerge) {
		this.canMerge = canMerge;
	}
	
	/**
	 * 获取是否有效<p>
	 * @return  payActive  是否有效<br>
	 */
	public boolean isPayActive() {
		return payActive;
	}
	
	/**
	 * 设置是否有效<p>
	 * @param  payActive  是否有效<br>
	 */
	public void setPayActive(boolean payActive) {
		this.payActive = payActive;
	}
	
	/**
	 * 获取失败结果审核<p>
	 * @return  auditResult  失败结果审核<br>
	 */
	public String getAuditResult() {
		return auditResult;
	}
	
	/**
	 * 设置失败结果审核<p>
	 * @param  auditResult  失败结果审核<br>
	 */
	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}
	

}