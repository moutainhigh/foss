package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 代收货款
 * 
 * @author 00123-foss-huangxiaobo
 * @date 2012-10-10 下午4:36:55
 * @since
 * @version $Id$
 */
public class VTSCODEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 运单ID
	 */
	private String waybillId;

	/**
	 * 应付部门编码
	 */
	private String payableOrgCode;

	/**
	 * 应付部门名称
	 */
	private String payableOrgName;

	/**
	 * 代收货款金额
	 */
	private BigDecimal codAmount;

	/**
	 * 代收货款类型
	 */
	private String codType;

	/**
	 * 收款人与发货人关系
	 */
	private String payeeRelationship;

	/**
	 * 开户行名称
	 */
	private String bankHQName;

	/**
	 * 支行编码（行号）
	 */
	private String bankBranchCode;

	/**
	 * 支行名称
	 */
	private String bankBranchName;

	/**
	 * 对公对私标志
	 */
	private String publicPrivateFlag;

	/**
	 * 收款人名称
	 */
	private String payeeName;

	/**
	 * 银行帐号
	 */
	private String accountNo;

	/**
	 * 收款人手机
	 */
	private String payeePhone;

	/**
	 * 省份编码
	 */
	private String provinceCode;

	/**
	 * 省份名称
	 */
	private String provinceName;

	/**
	 * 城市编码
	 */
	private String cityCode;

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 批次号
	 */
	private String batchNumber;

	/**
	 * 退款成功时间
	 */
	private Date refundSuccessTime;

	/**
	 * 制单人名称
	 */
	private String createUserName;

	/**
	 * 制单人编码
	 */
	private String createUserCode;

	/**
	 * 制单人部门编码
	 */
	private String createOrgCode;

	/**
	 * 制单部门名称
	 */
	private String createOrgName;

	/**
	 * 是否初始化
	 */
	private String isInit;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 代收货款状态
	 */
	private String status;

	/**
	 * 空运代收货款状态
	 */
	private String airStatus;

	/**
	 * 空运代收货款审核时间
	 */
	private Date airOrgAuditTime;

	/**
	 * 空运代收货款审核人编码
	 */
	private String airOrgAuditUserCode;

	/**
	 * 空运代收货款审核名称
	 */
	private String airOrgAuditUserName;
	
	/**
	 * 快递代理代收货款状态ISSUE-3389 小件业务
	 */
	private String landStatus;

	/**
	 * 快递代理代收货款审核时间ISSUE-3389 小件业务
	 */
	private Date landOrgAuditTime;

	/**
	 * 快递代理代收货款审核人编码ISSUE-3389 小件业务
	 */
	private String landOrgAuditUserCode;

	/**
	 * 快递代理代收货款审核名称ISSUE-3389 小件业务
	 */
	private String landOrgAuditUserName;

	/**
	 * 营业部冻结时间
	 */
	private Date orgFreezeTime;

	/**
	 * 营业部冻结人编码
	 */
	private String orgFreezeUserCode;

	/**
	 * 营业部冻结人名称
	 */
	private String orgFreezeUserName;

	/**
	 * 账号修改时间
	 */
	private Date accountModifyTime;

	/**
	 * 账号修改人编码
	 */
	private String accountModifyUserCode;

	/**
	 * 账号修改人名称
	 */
	private String accountModifyUserName;

	/**
	 * 营业部审核时间
	 */
	private Date orgAuditTime;

	/**
	 * 营业部审核人编码
	 */
	private String orgAuditUserCode;

	/**
	 * 营业部审核人名称
	 */
	private String orgAuditUserName;

	/**
	 * 营业部经理审核时间
	 */
	private Date orgManagerAuditTime;

	/**
	 * 营业部经理审核人编码
	 */
	private String orgManagerAuditCode;

	/**
	 * 营业部经理审核人名称
	 */
	private String orgManagerAuditName;

	/**
	 * 营业部经理退回原因
	 */
	private String refundNotes;

	/**
	 * 资金部冻结时间
	 */
	private Date tusyorgFreezeTime;

	/**
	 * 资金部冻结人编码
	 */
	private String tusyorgFreezeUserCode;

	/**
	 * 资金部冻结人名称
	 */
	private String tusyorgFreezeUserName;

	/**
	 * 资金部取消冻结时间
	 */
	private Date tusyorgClfreezeTime;

	/**
	 * 资金部取消冻结人编码
	 */
	private String tusyorgClfreezeUserCode;

	/**
	 * 资金部取消冻结人名称
	 */
	private String tusyorgClfreezeUserName;

	/**
	 * 资金部审核时间
	 */
	private Date tusyorgAuditTime;

	/**
	 * 资金部审核人编码
	 */
	private String tusyorgAuditUserCode;

	/**
	 * 资金部审核人名称
	 */
	private String tusyorgAuditUserName;

	/**
	 * 资金部退款申请时间
	 */
	private Date tusyorgRfdApptime;

	/**
	 * 资金部退款申请人编码
	 */
	private String tusyorgRfdAppUserCode;

	/**
	 * 资金部退款申请人名称
	 */
	private String tusyorgRfdAppUserName;

	/**
	 * 汇款导出时间
	 */
	private Date codExportTime;

	/**
	 * 汇款导出人编码
	 */
	private String codExportCode;

	/**
	 * 汇款导出人名称
	 */
	private String codExportName;

	/**
	 * 汇款失败/反汇款成功原因
	 */
	private String remittanceFailNotes;

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
	private String modifyUserCode;

	/**
	 * 修改人编码
	 */
	private String modifyUserName;

	/**
	 * 退款路径
	 */
	private String refundPath;

	/**
	 * 版本号
	 */
	private Short versionNo;

	/**
	 * 开户行编码
	 */
	private String bankHQCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 公司付款账号-银行账户
	 */
	private String comAccount;
	
	/**
	 * 合并编码
	 */
	private String mergeCode;
	
	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param  waybillNo  
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return payableOrgCode
	 */
	public String getPayableOrgCode() {
		return payableOrgCode;
	}

	/**
	 * @param  payableOrgCode  
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
	 * @param  payableOrgName  
	 */
	public void setPayableOrgName(String payableOrgName) {
		this.payableOrgName = payableOrgName;
	}

	/**
	 * @return codAmount
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * @param  codAmount  
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * @return codType
	 */
	public String getCodType() {
		return codType;
	}

	/**
	 * @param  codType  
	 */
	public void setCodType(String codType) {
		this.codType = codType;
	}

	/**
	 * @return payeeRelationship
	 */
	public String getPayeeRelationship() {
		return payeeRelationship;
	}

	/**
	 * @param  payeeRelationship  
	 */
	public void setPayeeRelationship(String payeeRelationship) {
		this.payeeRelationship = payeeRelationship;
	}

	/**
	 * @return bankHQName
	 */
	public String getBankHQName() {
		return bankHQName;
	}

	/**
	 * @param  bankHQName  
	 */
	public void setBankHQName(String bankHQName) {
		this.bankHQName = bankHQName;
	}

	/**
	 * @return bankBranchCode
	 */
	public String getBankBranchCode() {
		return bankBranchCode;
	}

	/**
	 * @param  bankBranchCode  
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
	 * @param  bankBranchName  
	 */
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	/**
	 * @return publicPrivateFlag
	 */
	public String getPublicPrivateFlag() {
		return publicPrivateFlag;
	}

	/**
	 * @param  publicPrivateFlag  
	 */
	public void setPublicPrivateFlag(String publicPrivateFlag) {
		this.publicPrivateFlag = publicPrivateFlag;
	}

	/**
	 * @return payeeName
	 */
	public String getPayeeName() {
		return payeeName;
	}

	/**
	 * @param  payeeName  
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	/**
	 * @return accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param  accountNo  
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return payeePhone
	 */
	public String getPayeePhone() {
		return payeePhone;
	}

	/**
	 * @param  payeePhone  
	 */
	public void setPayeePhone(String payeePhone) {
		this.payeePhone = payeePhone;
	}

	/**
	 * @return provinceCode
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 * @param  provinceCode  
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
	 * @param  provinceName  
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
	 * @param  cityCode  
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
	 * @param  cityName  
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param  businessDate  
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return batchNumber
	 */
	public String getBatchNumber() {
		return batchNumber;
	}

	/**
	 * @param  batchNumber  
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * @return refundSuccessTime
	 */
	public Date getRefundSuccessTime() {
		return refundSuccessTime;
	}

	/**
	 * @param  refundSuccessTime  
	 */
	public void setRefundSuccessTime(Date refundSuccessTime) {
		this.refundSuccessTime = refundSuccessTime;
	}

	/**
	 * @return createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param  createUserName  
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
	 * @param  createUserCode  
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
	 * @param  createOrgCode  
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
	 * @param  createOrgName  
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	/**
	 * @return isInit
	 */
	public String getIsInit() {
		return isInit;
	}

	/**
	 * @param  isInit  
	 */
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param  status  
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return airStatus
	 */
	public String getAirStatus() {
		return airStatus;
	}

	/**
	 * @param  airStatus  
	 */
	public void setAirStatus(String airStatus) {
		this.airStatus = airStatus;
	}

	/**
	 * @return airOrgAuditTime
	 */
	public Date getAirOrgAuditTime() {
		return airOrgAuditTime;
	}

	/**
	 * @param  airOrgAuditTime  
	 */
	public void setAirOrgAuditTime(Date airOrgAuditTime) {
		this.airOrgAuditTime = airOrgAuditTime;
	}

	/**
	 * @return airOrgAuditUserCode
	 */
	public String getAirOrgAuditUserCode() {
		return airOrgAuditUserCode;
	}

	/**
	 * @param  airOrgAuditUserCode  
	 */
	public void setAirOrgAuditUserCode(String airOrgAuditUserCode) {
		this.airOrgAuditUserCode = airOrgAuditUserCode;
	}

	/**
	 * @return airOrgAuditUserName
	 */
	public String getAirOrgAuditUserName() {
		return airOrgAuditUserName;
	}

	/**
	 * @param  airOrgAuditUserName  
	 */
	public void setAirOrgAuditUserName(String airOrgAuditUserName) {
		this.airOrgAuditUserName = airOrgAuditUserName;
	}

	/**
	 * @return orgFreezeTime
	 */
	public Date getOrgFreezeTime() {
		return orgFreezeTime;
	}

	/**
	 * @param  orgFreezeTime  
	 */
	public void setOrgFreezeTime(Date orgFreezeTime) {
		this.orgFreezeTime = orgFreezeTime;
	}

	/**
	 * @return orgFreezeUserCode
	 */
	public String getOrgFreezeUserCode() {
		return orgFreezeUserCode;
	}

	/**
	 * @param  orgFreezeUserCode  
	 */
	public void setOrgFreezeUserCode(String orgFreezeUserCode) {
		this.orgFreezeUserCode = orgFreezeUserCode;
	}

	/**
	 * @return orgFreezeUserName
	 */
	public String getOrgFreezeUserName() {
		return orgFreezeUserName;
	}

	/**
	 * @param  orgFreezeUserName  
	 */
	public void setOrgFreezeUserName(String orgFreezeUserName) {
		this.orgFreezeUserName = orgFreezeUserName;
	}

	/**
	 * @return accountModifyTime
	 */
	public Date getAccountModifyTime() {
		return accountModifyTime;
	}

	/**
	 * @param  accountModifyTime  
	 */
	public void setAccountModifyTime(Date accountModifyTime) {
		this.accountModifyTime = accountModifyTime;
	}

	/**
	 * @return accountModifyUserCode
	 */
	public String getAccountModifyUserCode() {
		return accountModifyUserCode;
	}

	/**
	 * @param  accountModifyUserCode  
	 */
	public void setAccountModifyUserCode(String accountModifyUserCode) {
		this.accountModifyUserCode = accountModifyUserCode;
	}

	/**
	 * @return accountModifyUserName
	 */
	public String getAccountModifyUserName() {
		return accountModifyUserName;
	}

	/**
	 * @param  accountModifyUserName  
	 */
	public void setAccountModifyUserName(String accountModifyUserName) {
		this.accountModifyUserName = accountModifyUserName;
	}

	/**
	 * @return orgAuditTime
	 */
	public Date getOrgAuditTime() {
		return orgAuditTime;
	}

	/**
	 * @param  orgAuditTime  
	 */
	public void setOrgAuditTime(Date orgAuditTime) {
		this.orgAuditTime = orgAuditTime;
	}

	/**
	 * @return orgAuditUserCode
	 */
	public String getOrgAuditUserCode() {
		return orgAuditUserCode;
	}

	/**
	 * @param  orgAuditUserCode  
	 */
	public void setOrgAuditUserCode(String orgAuditUserCode) {
		this.orgAuditUserCode = orgAuditUserCode;
	}

	/**
	 * @return orgAuditUserName
	 */
	public String getOrgAuditUserName() {
		return orgAuditUserName;
	}

	/**
	 * @param  orgAuditUserName  
	 */
	public void setOrgAuditUserName(String orgAuditUserName) {
		this.orgAuditUserName = orgAuditUserName;
	}

	/**
	 * @return orgManagerAuditTime
	 */
	public Date getOrgManagerAuditTime() {
		return orgManagerAuditTime;
	}

	/**
	 * @param  orgManagerAuditTime  
	 */
	public void setOrgManagerAuditTime(Date orgManagerAuditTime) {
		this.orgManagerAuditTime = orgManagerAuditTime;
	}

	/**
	 * @return orgManagerAuditCode
	 */
	public String getOrgManagerAuditCode() {
		return orgManagerAuditCode;
	}

	/**
	 * @param  orgManagerAuditCode  
	 */
	public void setOrgManagerAuditCode(String orgManagerAuditCode) {
		this.orgManagerAuditCode = orgManagerAuditCode;
	}

	/**
	 * @return orgManagerAuditName
	 */
	public String getOrgManagerAuditName() {
		return orgManagerAuditName;
	}

	/**
	 * @param  orgManagerAuditName  
	 */
	public void setOrgManagerAuditName(String orgManagerAuditName) {
		this.orgManagerAuditName = orgManagerAuditName;
	}

	/**
	 * @return refundNotes
	 */
	public String getRefundNotes() {
		return refundNotes;
	}

	/**
	 * @param  refundNotes  
	 */
	public void setRefundNotes(String refundNotes) {
		this.refundNotes = refundNotes;
	}

	/**
	 * @return tusyorgFreezeTime
	 */
	public Date getTusyorgFreezeTime() {
		return tusyorgFreezeTime;
	}

	/**
	 * @param  tusyorgFreezeTime  
	 */
	public void setTusyorgFreezeTime(Date tusyorgFreezeTime) {
		this.tusyorgFreezeTime = tusyorgFreezeTime;
	}

	/**
	 * @return tusyorgFreezeUserCode
	 */
	public String getTusyorgFreezeUserCode() {
		return tusyorgFreezeUserCode;
	}

	/**
	 * @param  tusyorgFreezeUserCode  
	 */
	public void setTusyorgFreezeUserCode(String tusyorgFreezeUserCode) {
		this.tusyorgFreezeUserCode = tusyorgFreezeUserCode;
	}

	/**
	 * @return tusyorgFreezeUserName
	 */
	public String getTusyorgFreezeUserName() {
		return tusyorgFreezeUserName;
	}

	/**
	 * @param  tusyorgFreezeUserName  
	 */
	public void setTusyorgFreezeUserName(String tusyorgFreezeUserName) {
		this.tusyorgFreezeUserName = tusyorgFreezeUserName;
	}

	/**
	 * @return tusyorgClfreezeTime
	 */
	public Date getTusyorgClfreezeTime() {
		return tusyorgClfreezeTime;
	}

	/**
	 * @param  tusyorgClfreezeTime  
	 */
	public void setTusyorgClfreezeTime(Date tusyorgClfreezeTime) {
		this.tusyorgClfreezeTime = tusyorgClfreezeTime;
	}

	/**
	 * @return tusyorgClfreezeUserCode
	 */
	public String getTusyorgClfreezeUserCode() {
		return tusyorgClfreezeUserCode;
	}

	/**
	 * @param  tusyorgClfreezeUserCode  
	 */
	public void setTusyorgClfreezeUserCode(String tusyorgClfreezeUserCode) {
		this.tusyorgClfreezeUserCode = tusyorgClfreezeUserCode;
	}

	/**
	 * @return tusyorgClfreezeUserName
	 */
	public String getTusyorgClfreezeUserName() {
		return tusyorgClfreezeUserName;
	}

	/**
	 * @param  tusyorgClfreezeUserName  
	 */
	public void setTusyorgClfreezeUserName(String tusyorgClfreezeUserName) {
		this.tusyorgClfreezeUserName = tusyorgClfreezeUserName;
	}

	/**
	 * @return tusyorgAuditTime
	 */
	public Date getTusyorgAuditTime() {
		return tusyorgAuditTime;
	}

	/**
	 * @param  tusyorgAuditTime  
	 */
	public void setTusyorgAuditTime(Date tusyorgAuditTime) {
		this.tusyorgAuditTime = tusyorgAuditTime;
	}

	/**
	 * @return tusyorgAuditUserCode
	 */
	public String getTusyorgAuditUserCode() {
		return tusyorgAuditUserCode;
	}

	/**
	 * @param  tusyorgAuditUserCode  
	 */
	public void setTusyorgAuditUserCode(String tusyorgAuditUserCode) {
		this.tusyorgAuditUserCode = tusyorgAuditUserCode;
	}

	/**
	 * @return tusyorgAuditUserName
	 */
	public String getTusyorgAuditUserName() {
		return tusyorgAuditUserName;
	}

	/**
	 * @param  tusyorgAuditUserName  
	 */
	public void setTusyorgAuditUserName(String tusyorgAuditUserName) {
		this.tusyorgAuditUserName = tusyorgAuditUserName;
	}

	/**
	 * @return tusyorgRfdApptime
	 */
	public Date getTusyorgRfdApptime() {
		return tusyorgRfdApptime;
	}

	/**
	 * @param  tusyorgRfdApptime  
	 */
	public void setTusyorgRfdApptime(Date tusyorgRfdApptime) {
		this.tusyorgRfdApptime = tusyorgRfdApptime;
	}

	/**
	 * @return tusyorgRfdAppUserCode
	 */
	public String getTusyorgRfdAppUserCode() {
		return tusyorgRfdAppUserCode;
	}

	/**
	 * @param  tusyorgRfdAppUserCode  
	 */
	public void setTusyorgRfdAppUserCode(String tusyorgRfdAppUserCode) {
		this.tusyorgRfdAppUserCode = tusyorgRfdAppUserCode;
	}

	/**
	 * @return tusyorgRfdAppUserName
	 */
	public String getTusyorgRfdAppUserName() {
		return tusyorgRfdAppUserName;
	}

	/**
	 * @param  tusyorgRfdAppUserName  
	 */
	public void setTusyorgRfdAppUserName(String tusyorgRfdAppUserName) {
		this.tusyorgRfdAppUserName = tusyorgRfdAppUserName;
	}

	/**
	 * @return codExportTime
	 */
	public Date getCodExportTime() {
		return codExportTime;
	}

	/**
	 * @param  codExportTime  
	 */
	public void setCodExportTime(Date codExportTime) {
		this.codExportTime = codExportTime;
	}

	/**
	 * @return codExportCode
	 */
	public String getCodExportCode() {
		return codExportCode;
	}

	/**
	 * @param  codExportCode  
	 */
	public void setCodExportCode(String codExportCode) {
		this.codExportCode = codExportCode;
	}

	/**
	 * @return codExportName
	 */
	public String getCodExportName() {
		return codExportName;
	}

	/**
	 * @param  codExportName  
	 */
	public void setCodExportName(String codExportName) {
		this.codExportName = codExportName;
	}

	/**
	 * @return remittanceFailNotes
	 */
	public String getRemittanceFailNotes() {
		return remittanceFailNotes;
	}

	/**
	 * @param  remittanceFailNotes  
	 */
	public void setRemittanceFailNotes(String remittanceFailNotes) {
		this.remittanceFailNotes = remittanceFailNotes;
	}

	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param  createTime  
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
	 * @param  modifyTime  
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param  modifyUserCode  
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * @return modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * @param  modifyUserName  
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * @return refundPath
	 */
	public String getRefundPath() {
		return refundPath;
	}

	/**
	 * @param  refundPath  
	 */
	public void setRefundPath(String refundPath) {
		this.refundPath = refundPath;
	}

	/**
	 * @return versionNo
	 */
	public Short getVersionNo() {
		return versionNo;
	}

	/**
	 * @param  versionNo  
	 */
	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @return bankHQCode
	 */
	public String getBankHQCode() {
		return bankHQCode;
	}

	/**
	 * @param  bankHQCode  
	 */
	public void setBankHQCode(String bankHQCode) {
		this.bankHQCode = bankHQCode;
	}

	/**
	 * @return customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param  customerName  
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param  customerCode  
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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
	 * @return landStatus
	 */
	public String getLandStatus() {
		return landStatus;
	}

	/**
	 * @param landStatus
	 */
	public void setLandStatus(String landStatus) {
		this.landStatus = landStatus;
	}

	/**
	 * @return landOrgAuditTime
	 */
	public Date getLandOrgAuditTime() {
		return landOrgAuditTime;
	}

	/**
	 * @param landOrgAuditTime
	 */
	public void setLandOrgAuditTime(Date landOrgAuditTime) {
		this.landOrgAuditTime = landOrgAuditTime;
	}

	/**
	 * @return landOrgAuditUserCode
	 */
	public String getLandOrgAuditUserCode() {
		return landOrgAuditUserCode;
	}

	/**
	 * @param landOrgAuditUserCode
	 */
	public void setLandOrgAuditUserCode(String landOrgAuditUserCode) {
		this.landOrgAuditUserCode = landOrgAuditUserCode;
	}

	/**
	 * @return landOrgAuditUserName
	 */
	public String getLandOrgAuditUserName() {
		return landOrgAuditUserName;
	}

	/**
	 * @param landOrgAuditUserName
	 */
	public void setLandOrgAuditUserName(String landOrgAuditUserName) {
		this.landOrgAuditUserName = landOrgAuditUserName;
	}

	/**
	 * @return the comAccount
	 */
	public String getComAccount() {
		return comAccount;
	}

	/**
	 * @param comAccount the comAccount to set
	 */
	public void setComAccount(String comAccount) {
		this.comAccount = comAccount;
	}

	/**
	 * @return the mergeCode
	 */
	public String getMergeCode() {
		return mergeCode;
	}

	/**
	 * @param mergeCode the mergeCode to set
	 */
	public void setMergeCode(String mergeCode) {
		this.mergeCode = mergeCode;
	}

	
}

