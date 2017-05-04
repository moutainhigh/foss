package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CodAuditDto implements Serializable{
	
	private static final long serialVersionUID = 5289574926023944551L;
	
	//是否有效
	private String active;

	// id
	private Long id;

	// 运单号
	private String waybillNo;

	// 运单id
	private String waybillNoId;

	// 应付交易单号
	private String payableBillNo;

	// 应付部门编码
	private String payableOrgCode;

	// 应付部门名称
	private String payableOrgName;

	// 应付部门标杆编码
	private String payableUnifiedCode;

	// 应付部门子公司编码
	private String payableComCode;

	// 应付部门子公司名称
	private String payableComName;

	// 金额
	private BigDecimal codAmount;

	// 代收货款类型
	private String codType;

	// 收款人账号
	private String accountNo;

	// 收款人手机
	private String payeePhone;

	// 客户编码
	private String customerCode;

	// 客户名称
	private String customerName;

	// 收款人名称
	private String payeeName;

	// 收款人与发货人关系
	private String payeeRelationship;

	// 对公对私标志 账户性质，对公对私支付宝
	private String publicPrivateFlag;

	// 开户人姓名
	private String accountName;

	// 批次号
	private String batchNumber;

	// 业务时间
	private Date businessDate;

	// 退款成功时间
	private Date refundSuccessDate;

	// 是否初始化
	private String isInit;

	// 制单人部门名称
	private String createOrgName;

	// 制单人部门编码
	private String createOrgCode;

	// 代收货款状态 未退款、退款中、已退款、资金部冻结
	private String status;

	// 空运代收货款状态
	private String airStatus;

	// 营业部冻结时间
	private Date orgFreezeTime;

	// 营业部冻结人名称
	private String orgFreezeUserName;

	// 营业部冻结人编码
	private String orgFreezeUserCode;

	// 账号修改时间
	private Date accountModifyTime;

	// 账号修改人编码
	private String accountModifyUserCode;

	// 账号修改人名称
	private String accountModifyUserName;

	// 营业部审核时间
	private Date orgAuditTime;

	// 营业部审核人编码
	private String orgAuditUserCode;

	// 营业部审核人名称
	private String orgAuditUserName;

	// 营业部经理审核时间
	private Date orgManagerAuditTime;

	// 营业部经理审核人编码
	private String orgManagerAuditCode;

	// 营业部经理审核人名称
	private String orgManagerAuditName;

	// 营业部经理退回原因
	private String refundNotes;

	// 资金部冻结时间
	private Date tusyorgFreezeDate;

	// 资金部取消冻结时间
	private Date tusyorgClfreezeDate;

	// 资金部取消冻结人编码
	private String tusyorgClfreezeUserCode;

	// 资金部取消冻结人名称
	private String tusyorgClfreezeUserName;

	// 资金部冻结人编码
	private String tusyorgFreezeUserCode;

	// 资金部冻结人名称
	private String tusyorgFreezeUserName;

	// 资金部审核时间
	private Date tusyorgAuditDate;

	// 资金部审核人名称
	private String tusyorgAuditUserName;

	// 资金部审核人编码
	private String tusyorgAuditUserCode;

	// 资金部退款申请时间
	private Date tusyorgRfdApptime;

	// 资金部退款申请人编码
	private String tusyorgRfdAppUserCode;

	// 资金部退款申请人名称
	private String tusyorgRfdAppUserName;

	// 汇款导出时间
	private Date codExportDate;

	// 汇款导出人名称
	private String codExportName;

	// 汇款导出人编码
	private String codExportCode;

	// 汇款失败/反汇款成功原因
	private String remitTanceFailNotes;

	// 空运审核时间
	private Date airOrgAuditDate;

	// 空运审核人编码
	private String airOrgAuditUserCode;

	// 空运审核人名称
	private String airOrgAuditUserName;

	// 创建时间
	private Date createDate;

	// 修改时间
	private Date modifyDate;

	// 修改人编码
	private String modifyUserCode;

	// 修改人名称
	private String modifyUserName;

	// 版本号
	private Long versionNo;

	// 退款路径 线上,线下
	private String refundPath;

	// 客户类型
	private String customerType;

	// 合并编号
	private String mergeCode;

	// 公司付款账号:银行账户
	private String comAccount;

	// 快递代理审核时间
	private Date expressOrgAuditTime;

	// 快递代理审核人编码
	private String expressOrgAuditUserCode;

	// 快递代理审核人名称
	private String expressOrgAuditUserName;

	// 是否签收
	private String isSign;

	// 快递代理审核状态
	private String expressAuditStatus;

	// 营业部审核状态
	private String orgAuditStatus;

	// 出发部门名称
	private String origOrgName;

	// 出发部门编码
	private String origOrgCode;

	// 到达部门名称
	private String destOrgName;

	// 到达部门编码
	private String destOrgCode;

	// 运输性质
	private String productCode;

	// 是否有货物轨迹 收银货签收时候获取
	private String isTrack;

	// 发货人名称
	private String deliverCustomerName;

	// 发货人编码
	private String deliverCustomerCode;

	// 收货人编码
	private String receiveCustomerCode;

	// 收货人名称
	private String receiveCustomerName;

	// 收银确认时间
	private Date confirmTime;

	// 签收时间
	private Date signDate;

	// 支付审核状态
	private String lockStatus;

	// 代收批次状态
	// 已发送/银企审核通过/银企审核不通过
	private String batchStatus;

	// 付款方式
	private String paymentType;

	// 开单日期
	private Date billDate;

	// 代收手续费
	private BigDecimal codFee;

	// 签收开单时长
	private Long signBillDiffer;

	// 更改金额
	private BigDecimal changeAmount;

	// 对账单号
	private String statementBillNo;

	// 开户行名称
	private String bankHqName;

	// 开户行编码
	private String bankHqCode;

	// 省份编码
	private String provinceCode;

	// 省份名称
	private String provinceName;

	// 市编码
	private String cityCode;

	// 市名称
	private String cityName;

	// 支行编码
	private String bankBranchCode;

	// 支行名称
	private String bankBranchName;

	// 账户类型
	private String accountType;

	// 银企审核不通过原因
	private String fundAuditFailNotes;

	// 到达部门电话
	private String destOrgTelephone;

	// 扩展字段
	private String attribute;

	// 按位标志
	private Long option;

	// 备注
	private String remark;

	// 收银确认状态
	private String comfirmStatus;

	// 收银确认时间
	private Date comfirmTime;

	// 已冲销金额
	private BigDecimal verifyCodAmount;

	// 到达部门收银员姓名-收银确认操作人
	private String destCashierName;

	// 业务类型
	private String businessType;

	// 签收人
	private String signer;

	// 签收代理编码
	private String agencyCode;

	// 签收代理名称
	private String agencyName;

	// 目的站
	private String destination;

	// 代理类型
	private String agencyType;
	
    //标记去支付审核
	private String isPayAudit;
	
	// 原来状态值
	private String oldStatus;

	// 更改时间
	private Date changeTime;

	// 是否可以合并 true可以 false不可以
	private boolean canMerge;

	// 是否有效
	private boolean payActive;

	// 失败结果审核
	private String auditResult;
	
	public String getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	public boolean isCanMerge() {
		return canMerge;
	}
	public void setCanMerge(boolean canMerge) {
		this.canMerge = canMerge;
	}
	public boolean isPayActive() {
		return payActive;
	}
	public void setPayActive(boolean payActive) {
		this.payActive = payActive;
	}
	public String getAuditResult() {
		return auditResult;
	}
	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getWaybillNoId() {
		return waybillNoId;
	}
	public void setWaybillNoId(String waybillNoId) {
		this.waybillNoId = waybillNoId;
	}
	public String getPayableBillNo() {
		return payableBillNo;
	}
	public void setPayableBillNo(String payableBillNo) {
		this.payableBillNo = payableBillNo;
	}
	public String getPayableOrgCode() {
		return payableOrgCode;
	}
	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}
	public String getPayableOrgName() {
		return payableOrgName;
	}
	public void setPayableOrgName(String payableOrgName) {
		this.payableOrgName = payableOrgName;
	}
	public String getPayableUnifiedCode() {
		return payableUnifiedCode;
	}
	public void setPayableUnifiedCode(String payableUnifiedCode) {
		this.payableUnifiedCode = payableUnifiedCode;
	}
	public String getPayableComCode() {
		return payableComCode;
	}
	public void setPayableComCode(String payableComCode) {
		this.payableComCode = payableComCode;
	}
	public String getPayableComName() {
		return payableComName;
	}
	public void setPayableComName(String payableComName) {
		this.payableComName = payableComName;
	}
	public BigDecimal getCodAmount() {
		return codAmount;
	}
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}
	public String getCodType() {
		return codType;
	}
	public void setCodType(String codType) {
		this.codType = codType;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getPayeePhone() {
		return payeePhone;
	}
	public void setPayeePhone(String payeePhone) {
		this.payeePhone = payeePhone;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getPayeeRelationship() {
		return payeeRelationship;
	}
	public void setPayeeRelationship(String payeeRelationship) {
		this.payeeRelationship = payeeRelationship;
	}
	public String getPublicPrivateFlag() {
		return publicPrivateFlag;
	}
	public void setPublicPrivateFlag(String publicPrivateFlag) {
		this.publicPrivateFlag = publicPrivateFlag;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public Date getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}
	public Date getRefundSuccessDate() {
		return refundSuccessDate;
	}
	public void setRefundSuccessDate(Date refundSuccessDate) {
		this.refundSuccessDate = refundSuccessDate;
	}
	public String getIsInit() {
		return isInit;
	}
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}
	public String getCreateOrgName() {
		return createOrgName;
	}
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAirStatus() {
		return airStatus;
	}
	public void setAirStatus(String airStatus) {
		this.airStatus = airStatus;
	}
	public Date getOrgFreezeTime() {
		return orgFreezeTime;
	}
	public void setOrgFreezeTime(Date orgFreezeTime) {
		this.orgFreezeTime = orgFreezeTime;
	}
	public String getOrgFreezeUserName() {
		return orgFreezeUserName;
	}
	public void setOrgFreezeUserName(String orgFreezeUserName) {
		this.orgFreezeUserName = orgFreezeUserName;
	}
	public String getOrgFreezeUserCode() {
		return orgFreezeUserCode;
	}
	public void setOrgFreezeUserCode(String orgFreezeUserCode) {
		this.orgFreezeUserCode = orgFreezeUserCode;
	}
	public Date getAccountModifyTime() {
		return accountModifyTime;
	}
	public void setAccountModifyTime(Date accountModifyTime) {
		this.accountModifyTime = accountModifyTime;
	}
	public String getAccountModifyUserCode() {
		return accountModifyUserCode;
	}
	public void setAccountModifyUserCode(String accountModifyUserCode) {
		this.accountModifyUserCode = accountModifyUserCode;
	}
	public String getAccountModifyUserName() {
		return accountModifyUserName;
	}
	public void setAccountModifyUserName(String accountModifyUserName) {
		this.accountModifyUserName = accountModifyUserName;
	}
	public Date getOrgAuditTime() {
		return orgAuditTime;
	}
	public void setOrgAuditTime(Date orgAuditTime) {
		this.orgAuditTime = orgAuditTime;
	}
	public String getOrgAuditUserCode() {
		return orgAuditUserCode;
	}
	public void setOrgAuditUserCode(String orgAuditUserCode) {
		this.orgAuditUserCode = orgAuditUserCode;
	}
	public String getOrgAuditUserName() {
		return orgAuditUserName;
	}
	public void setOrgAuditUserName(String orgAuditUserName) {
		this.orgAuditUserName = orgAuditUserName;
	}
	public Date getOrgManagerAuditTime() {
		return orgManagerAuditTime;
	}
	public void setOrgManagerAuditTime(Date orgManagerAuditTime) {
		this.orgManagerAuditTime = orgManagerAuditTime;
	}
	public String getOrgManagerAuditCode() {
		return orgManagerAuditCode;
	}
	public void setOrgManagerAuditCode(String orgManagerAuditCode) {
		this.orgManagerAuditCode = orgManagerAuditCode;
	}
	public String getOrgManagerAuditName() {
		return orgManagerAuditName;
	}
	public void setOrgManagerAuditName(String orgManagerAuditName) {
		this.orgManagerAuditName = orgManagerAuditName;
	}
	public String getRefundNotes() {
		return refundNotes;
	}
	public void setRefundNotes(String refundNotes) {
		this.refundNotes = refundNotes;
	}
	public Date getTusyorgFreezeDate() {
		return tusyorgFreezeDate;
	}
	public void setTusyorgFreezeDate(Date tusyorgFreezeDate) {
		this.tusyorgFreezeDate = tusyorgFreezeDate;
	}
	public Date getTusyorgClfreezeDate() {
		return tusyorgClfreezeDate;
	}
	public void setTusyorgClfreezeDate(Date tusyorgClfreezeDate) {
		this.tusyorgClfreezeDate = tusyorgClfreezeDate;
	}
	public String getTusyorgClfreezeUserCode() {
		return tusyorgClfreezeUserCode;
	}
	public void setTusyorgClfreezeUserCode(String tusyorgClfreezeUserCode) {
		this.tusyorgClfreezeUserCode = tusyorgClfreezeUserCode;
	}
	public String getTusyorgClfreezeUserName() {
		return tusyorgClfreezeUserName;
	}
	public void setTusyorgClfreezeUserName(String tusyorgClfreezeUserName) {
		this.tusyorgClfreezeUserName = tusyorgClfreezeUserName;
	}
	public String getTusyorgFreezeUserCode() {
		return tusyorgFreezeUserCode;
	}
	public void setTusyorgFreezeUserCode(String tusyorgFreezeUserCode) {
		this.tusyorgFreezeUserCode = tusyorgFreezeUserCode;
	}
	public String getTusyorgFreezeUserName() {
		return tusyorgFreezeUserName;
	}
	public void setTusyorgFreezeUserName(String tusyorgFreezeUserName) {
		this.tusyorgFreezeUserName = tusyorgFreezeUserName;
	}
	public Date getTusyorgAuditDate() {
		return tusyorgAuditDate;
	}
	public void setTusyorgAuditDate(Date tusyorgAuditDate) {
		this.tusyorgAuditDate = tusyorgAuditDate;
	}
	public String getTusyorgAuditUserName() {
		return tusyorgAuditUserName;
	}
	public void setTusyorgAuditUserName(String tusyorgAuditUserName) {
		this.tusyorgAuditUserName = tusyorgAuditUserName;
	}
	public String getTusyorgAuditUserCode() {
		return tusyorgAuditUserCode;
	}
	public void setTusyorgAuditUserCode(String tusyorgAuditUserCode) {
		this.tusyorgAuditUserCode = tusyorgAuditUserCode;
	}
	public Date getTusyorgRfdApptime() {
		return tusyorgRfdApptime;
	}
	public void setTusyorgRfdApptime(Date tusyorgRfdApptime) {
		this.tusyorgRfdApptime = tusyorgRfdApptime;
	}
	public String getTusyorgRfdAppUserCode() {
		return tusyorgRfdAppUserCode;
	}
	public void setTusyorgRfdAppUserCode(String tusyorgRfdAppUserCode) {
		this.tusyorgRfdAppUserCode = tusyorgRfdAppUserCode;
	}
	public String getTusyorgRfdAppUserName() {
		return tusyorgRfdAppUserName;
	}
	public void setTusyorgRfdAppUserName(String tusyorgRfdAppUserName) {
		this.tusyorgRfdAppUserName = tusyorgRfdAppUserName;
	}
	public Date getCodExportDate() {
		return codExportDate;
	}
	public void setCodExportDate(Date codExportDate) {
		this.codExportDate = codExportDate;
	}
	public String getCodExportName() {
		return codExportName;
	}
	public void setCodExportName(String codExportName) {
		this.codExportName = codExportName;
	}
	public String getCodExportCode() {
		return codExportCode;
	}
	public void setCodExportCode(String codExportCode) {
		this.codExportCode = codExportCode;
	}
	public String getRemitTanceFailNotes() {
		return remitTanceFailNotes;
	}
	public void setRemitTanceFailNotes(String remitTanceFailNotes) {
		this.remitTanceFailNotes = remitTanceFailNotes;
	}
	public Date getAirOrgAuditDate() {
		return airOrgAuditDate;
	}
	public void setAirOrgAuditDate(Date airOrgAuditDate) {
		this.airOrgAuditDate = airOrgAuditDate;
	}
	public String getAirOrgAuditUserCode() {
		return airOrgAuditUserCode;
	}
	public void setAirOrgAuditUserCode(String airOrgAuditUserCode) {
		this.airOrgAuditUserCode = airOrgAuditUserCode;
	}
	public String getAirOrgAuditUserName() {
		return airOrgAuditUserName;
	}
	public void setAirOrgAuditUserName(String airOrgAuditUserName) {
		this.airOrgAuditUserName = airOrgAuditUserName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	public Long getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
	public String getRefundPath() {
		return refundPath;
	}
	public void setRefundPath(String refundPath) {
		this.refundPath = refundPath;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getMergeCode() {
		return mergeCode;
	}
	public void setMergeCode(String mergeCode) {
		this.mergeCode = mergeCode;
	}
	public String getComAccount() {
		return comAccount;
	}
	public void setComAccount(String comAccount) {
		this.comAccount = comAccount;
	}
	public Date getExpressOrgAuditTime() {
		return expressOrgAuditTime;
	}
	public void setExpressOrgAuditTime(Date expressOrgAuditTime) {
		this.expressOrgAuditTime = expressOrgAuditTime;
	}
	public String getExpressOrgAuditUserCode() {
		return expressOrgAuditUserCode;
	}
	public void setExpressOrgAuditUserCode(String expressOrgAuditUserCode) {
		this.expressOrgAuditUserCode = expressOrgAuditUserCode;
	}
	public String getExpressOrgAuditUserName() {
		return expressOrgAuditUserName;
	}
	public void setExpressOrgAuditUserName(String expressOrgAuditUserName) {
		this.expressOrgAuditUserName = expressOrgAuditUserName;
	}
	public String getIsSign() {
		return isSign;
	}
	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}
	public String getExpressAuditStatus() {
		return expressAuditStatus;
	}
	public void setExpressAuditStatus(String expressAuditStatus) {
		this.expressAuditStatus = expressAuditStatus;
	}
	public String getOrgAuditStatus() {
		return orgAuditStatus;
	}
	public void setOrgAuditStatus(String orgAuditStatus) {
		this.orgAuditStatus = orgAuditStatus;
	}
	public String getOrigOrgName() {
		return origOrgName;
	}
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	public String getDestOrgName() {
		return destOrgName;
	}
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	public String getDestOrgCode() {
		return destOrgCode;
	}
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getIsTrack() {
		return isTrack;
	}
	public void setIsTrack(String isTrack) {
		this.isTrack = isTrack;
	}
	public String getDeliverCustomerName() {
		return deliverCustomerName;
	}
	public void setDeliverCustomerName(String deliverCustomerName) {
		this.deliverCustomerName = deliverCustomerName;
	}
	public String getDeliverCustomerCode() {
		return deliverCustomerCode;
	}
	public void setDeliverCustomerCode(String deliverCustomerCode) {
		this.deliverCustomerCode = deliverCustomerCode;
	}
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}
	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public String getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}
	public String getBatchStatus() {
		return batchStatus;
	}
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public BigDecimal getCodFee() {
		return codFee;
	}
	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}
	public Long getSignBillDiffer() {
		return signBillDiffer;
	}
	public void setSignBillDiffer(Long signBillDiffer) {
		this.signBillDiffer = signBillDiffer;
	}
	public BigDecimal getChangeAmount() {
		return changeAmount;
	}
	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}
	public String getStatementBillNo() {
		return statementBillNo;
	}
	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}
	public String getBankHqName() {
		return bankHqName;
	}
	public void setBankHqName(String bankHqName) {
		this.bankHqName = bankHqName;
	}
	public String getBankHqCode() {
		return bankHqCode;
	}
	public void setBankHqCode(String bankHqCode) {
		this.bankHqCode = bankHqCode;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getBankBranchCode() {
		return bankBranchCode;
	}
	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getFundAuditFailNotes() {
		return fundAuditFailNotes;
	}
	public void setFundAuditFailNotes(String fundAuditFailNotes) {
		this.fundAuditFailNotes = fundAuditFailNotes;
	}
	public String getDestOrgTelephone() {
		return destOrgTelephone;
	}
	public void setDestOrgTelephone(String destOrgTelephone) {
		this.destOrgTelephone = destOrgTelephone;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public Long getOption() {
		return option;
	}
	public void setOption(Long option) {
		this.option = option;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getComfirmStatus() {
		return comfirmStatus;
	}
	public void setComfirmStatus(String comfirmStatus) {
		this.comfirmStatus = comfirmStatus;
	}
	public Date getComfirmTime() {
		return comfirmTime;
	}
	public void setComfirmTime(Date comfirmTime) {
		this.comfirmTime = comfirmTime;
	}
	public BigDecimal getVerifyCodAmount() {
		return verifyCodAmount;
	}
	public void setVerifyCodAmount(BigDecimal verifyCodAmount) {
		this.verifyCodAmount = verifyCodAmount;
	}
	public String getDestCashierName() {
		return destCashierName;
	}
	public void setDestCashierName(String destCashierName) {
		this.destCashierName = destCashierName;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getSigner() {
		return signer;
	}
	public void setSigner(String signer) {
		this.signer = signer;
	}
	public String getAgencyCode() {
		return agencyCode;
	}
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getAgencyType() {
		return agencyType;
	}
	public void setAgencyType(String agencyType) {
		this.agencyType = agencyType;
	}
	public String getIsPayAudit() {
		return isPayAudit;
	}
	public void setIsPayAudit(String isPayAudit) {
		this.isPayAudit = isPayAudit;
	}
	@Override
	public String toString() {
		return "CodAuditDto [active=" + active + ", id=" + id + ", waybillNo="
				+ waybillNo + ", waybillNoId=" + waybillNoId
				+ ", payableBillNo=" + payableBillNo + ", payableOrgCode="
				+ payableOrgCode + ", payableOrgName=" + payableOrgName
				+ ", payableUnifiedCode=" + payableUnifiedCode
				+ ", payableComCode=" + payableComCode + ", payableComName="
				+ payableComName + ", codAmount=" + codAmount + ", codType="
				+ codType + ", accountNo=" + accountNo + ", payeePhone="
				+ payeePhone + ", customerCode=" + customerCode
				+ ", customerName=" + customerName + ", payeeName=" + payeeName
				+ ", payeeRelationship=" + payeeRelationship
				+ ", publicPrivateFlag=" + publicPrivateFlag + ", accountName="
				+ accountName + ", batchNumber=" + batchNumber
				+ ", businessDate=" + businessDate + ", refundSuccessDate="
				+ refundSuccessDate + ", isInit=" + isInit + ", createOrgName="
				+ createOrgName + ", createOrgCode=" + createOrgCode
				+ ", status=" + status + ", airStatus=" + airStatus
				+ ", orgFreezeTime=" + orgFreezeTime + ", orgFreezeUserName="
				+ orgFreezeUserName + ", orgFreezeUserCode="
				+ orgFreezeUserCode + ", accountModifyTime="
				+ accountModifyTime + ", accountModifyUserCode="
				+ accountModifyUserCode + ", accountModifyUserName="
				+ accountModifyUserName + ", orgAuditTime=" + orgAuditTime
				+ ", orgAuditUserCode=" + orgAuditUserCode
				+ ", orgAuditUserName=" + orgAuditUserName
				+ ", orgManagerAuditTime=" + orgManagerAuditTime
				+ ", orgManagerAuditCode=" + orgManagerAuditCode
				+ ", orgManagerAuditName=" + orgManagerAuditName
				+ ", refundNotes=" + refundNotes + ", tusyorgFreezeDate="
				+ tusyorgFreezeDate + ", tusyorgClfreezeDate="
				+ tusyorgClfreezeDate + ", tusyorgClfreezeUserCode="
				+ tusyorgClfreezeUserCode + ", tusyorgClfreezeUserName="
				+ tusyorgClfreezeUserName + ", tusyorgFreezeUserCode="
				+ tusyorgFreezeUserCode + ", tusyorgFreezeUserName="
				+ tusyorgFreezeUserName + ", tusyorgAuditDate="
				+ tusyorgAuditDate + ", tusyorgAuditUserName="
				+ tusyorgAuditUserName + ", tusyorgAuditUserCode="
				+ tusyorgAuditUserCode + ", tusyorgRfdApptime="
				+ tusyorgRfdApptime + ", tusyorgRfdAppUserCode="
				+ tusyorgRfdAppUserCode + ", tusyorgRfdAppUserName="
				+ tusyorgRfdAppUserName + ", codExportDate=" + codExportDate
				+ ", codExportName=" + codExportName + ", codExportCode="
				+ codExportCode + ", remitTanceFailNotes="
				+ remitTanceFailNotes + ", airOrgAuditDate=" + airOrgAuditDate
				+ ", airOrgAuditUserCode=" + airOrgAuditUserCode
				+ ", airOrgAuditUserName=" + airOrgAuditUserName
				+ ", createDate=" + createDate + ", modifyDate=" + modifyDate
				+ ", modifyUserCode=" + modifyUserCode + ", modifyUserName="
				+ modifyUserName + ", versionNo=" + versionNo + ", refundPath="
				+ refundPath + ", customerType=" + customerType
				+ ", mergeCode=" + mergeCode + ", comAccount=" + comAccount
				+ ", expressOrgAuditTime=" + expressOrgAuditTime
				+ ", expressOrgAuditUserCode=" + expressOrgAuditUserCode
				+ ", expressOrgAuditUserName=" + expressOrgAuditUserName
				+ ", isSign=" + isSign + ", expressAuditStatus="
				+ expressAuditStatus + ", orgAuditStatus=" + orgAuditStatus
				+ ", origOrgName=" + origOrgName + ", origOrgCode="
				+ origOrgCode + ", destOrgName=" + destOrgName
				+ ", destOrgCode=" + destOrgCode + ", productCode="
				+ productCode + ", isTrack=" + isTrack
				+ ", deliverCustomerName=" + deliverCustomerName
				+ ", deliverCustomerCode=" + deliverCustomerCode
				+ ", receiveCustomerCode=" + receiveCustomerCode
				+ ", receiveCustomerName=" + receiveCustomerName
				+ ", confirmTime=" + confirmTime + ", signDate=" + signDate
				+ ", lockStatus=" + lockStatus + ", batchStatus=" + batchStatus
				+ ", paymentType=" + paymentType + ", billDate=" + billDate
				+ ", codFee=" + codFee + ", signBillDiffer=" + signBillDiffer
				+ ", changeAmount=" + changeAmount + ", statementBillNo="
				+ statementBillNo + ", bankHqName=" + bankHqName
				+ ", bankHqCode=" + bankHqCode + ", provinceCode="
				+ provinceCode + ", provinceName=" + provinceName
				+ ", cityCode=" + cityCode + ", cityName=" + cityName
				+ ", bankBranchCode=" + bankBranchCode + ", bankBranchName="
				+ bankBranchName + ", accountType=" + accountType
				+ ", fundAuditFailNotes=" + fundAuditFailNotes
				+ ", destOrgTelephone=" + destOrgTelephone + ", attribute="
				+ attribute + ", option=" + option + ", remark=" + remark
				+ ", comfirmStatus=" + comfirmStatus + ", comfirmTime="
				+ comfirmTime + ", verifyCodAmount=" + verifyCodAmount
				+ ", destCashierName=" + destCashierName + ", businessType="
				+ businessType + ", signer=" + signer + ", agencyCode="
				+ agencyCode + ", agencyName=" + agencyName + ", destination="
				+ destination + ", agencyType=" + agencyType + ", isPayAudit="
				+ isPayAudit + ", oldStatus=" + oldStatus + ", changeTime="
				+ changeTime + ", canMerge=" + canMerge + ", payActive="
				+ payActive + ", auditResult=" + auditResult + "]";
	}
	
}
