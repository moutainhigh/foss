package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 打款信息实体
 * 
 * @ClassName: PayInfoDO
 * @author & 周禹安 | zhouyuan008@deppon.com
 * @date 2016年10月29日 下午1:16:47
 */
public class PayInfoDO extends PayCenterBaseDO implements Serializable {

	private static final long serialVersionUID = 5941853434643298348L;

	/**
	 * 业务处理逻辑，根据不同的系统和和支付方式加上物流交易，确定业务逻辑处理
	 */
	private String bizHandle;

	/**
	 * 去重ID
	 */
	private String uniqueId;

	/**
	 * 关联类型
	 */
	private String relatedType;

	/**
	 * 关联对象
	 */
	private String relatedTarget;

	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 已使用金额
	 */
	private BigDecimal usedAmount;

	/**
	 * 未使用金额
	 */
	private BigDecimal unusedAmount;

	/**
	 * 进资产金额
	 */
	private BigDecimal assetAmount;

	/**
	 * 打款方式
	 */
	private String payType;

	/**
	 * 打款编码
	 */
	private String payCode;

	/**
	 * 打款部门名称
	 */
	private String payDeptName;

	/**
	 * 打款部门编码
	 */
	private String payDeptCode;

	/**
	 * 打款部门标杆编码
	 */
	private String payUnifiedCode;

	/**
	 * 打款时间
	 */
	private Date payDate;

	/**
	 * 对象名称
	 */
	private String customerName;

	/**
	 * 对象编码
	 */
	private String customerCode;

	/**
	 * 对象类型
	 */
	private String customerType;

	/**
	 * pos机串号
	 */
	private String posSerialNum;

	/**
	 * 锁定时间
	 */
	private Date lockDate;

	/**
	 * 账号，第三方汇款的时候设置账号
	 */
	private String accountNo;

	/**
	 * 账号类型
	 */
	private String accountType;

	/**
	 * 打款信息明细
	 */
	private List<PayInfoDetailDO> payInfoDetailDO;

	/************ 不需要传递 *************/

	/**
	 * 是否查询明细 cubc字段查询，不需要封装
	 */
	private String queryType;
	/**
	 * 外部单据子类型名称
	 */
	private String outBillName;
	
	/**
	 * 裹裹进来的时候没有部门，需要判断一下
	 */
	private String isGG;
	
	/**
	 * 封装到收款单数据，不需要传递
	 */
	private String billType;
	
	/**
	 * 是否要拆分,True:需要，False 不需要
	 */
	private Boolean isSplit;
	
	/**
	 * 封装返回数据,是否成功
	 */
	private String isSuccess;

	/**
	 * 错误消息
	 */
	private String errorMessage;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getQueryType() {
		return queryType;
	}
	
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getRelatedType() {
		return relatedType;
	}

	public void setRelatedType(String relatedType) {
		this.relatedType = relatedType;
	}

	public String getRelatedTarget() {
		return relatedTarget;
	}

	public void setRelatedTarget(String relatedTarget) {
		this.relatedTarget = relatedTarget;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(BigDecimal usedAmount) {
		this.usedAmount = usedAmount;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getPayDeptName() {
		return payDeptName;
	}

	public void setPayDeptName(String payDeptName) {
		this.payDeptName = payDeptName;
	}

	public String getPayDeptCode() {
		return payDeptCode;
	}

	public void setPayDeptCode(String payDeptCode) {
		this.payDeptCode = payDeptCode;
	}

	public String getPayUnifiedCode() {
		return payUnifiedCode;
	}

	public void setPayUnifiedCode(String payUnifiedCode) {
		this.payUnifiedCode = payUnifiedCode;
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

	public String getPosSerialNum() {
		return posSerialNum;
	}

	public void setPosSerialNum(String posSerialNum) {
		this.posSerialNum = posSerialNum;
	}

	public Date getLockDate() {
		return lockDate;
	}

	public void setLockDate(Date lockDate) {
		this.lockDate = lockDate;
	}

	public List<PayInfoDetailDO> getPayInfoDetailDO() {
		return payInfoDetailDO;
	}

	public void setPayInfoDetailDO(List<PayInfoDetailDO> payInfoDetailDO) {
		this.payInfoDetailDO = payInfoDetailDO;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getBizHandle() {
		return bizHandle;
	}

	public void setBizHandle(String bizHandle) {
		this.bizHandle = bizHandle;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public BigDecimal getUnusedAmount() {
		return unusedAmount;
	}

	public void setUnusedAmount(BigDecimal unusedAmount) {
		this.unusedAmount = unusedAmount;
	}

	public BigDecimal getAssetAmount() {
		return assetAmount;
	}

	public void setAssetAmount(BigDecimal assetAmount) {
		this.assetAmount = assetAmount;
	}

	public String getIsGG() {
		return isGG;
	}

	public void setIsGG(String isGG) {
		this.isGG = isGG;
	}

	public String getOutBillName() {
		return outBillName;
	}

	public void setOutBillName(String outBillName) {
		this.outBillName = outBillName;
	}

	public void setIsSplit(Boolean isSplit) {
		this.isSplit = isSplit;
	}

	public Boolean getIsSplit() {
		return isSplit;
	}
	
}
