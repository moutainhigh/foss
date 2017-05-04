package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * 
 * 网上支付查询对账单DTO
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-29 下午2:55:26
 */
public class StatementOnlineQueryDto implements Serializable{

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 1016107108939482681L;
	/**
	 * ********************查询对账单*************************
	 */
	/**
	 * 业务开始日期
	 */
	private Date beginDate;
	/**
	 * 业务结束日期
	 */
	private Date endDate;
	/**
	 * 对账单号
	 */
	private String statementBillNo;
	/**
	 * 客户编码
	 */
	private String customerCode;
	/**
	 * 确认状态
	 */
	private String confirmStatus;
	/**
	 * 明细类型
	 */
	private String parentBillType;
	/**
	 * 应收单子类型（运单）
	 */
	private List<String> billTypeList;
	/**
	 * 删除标记
	 */
	private String deleteFlag;
	/**
	 * 分页的页号
	 */
	private int pageNo;
	/**
	 * 分页每页大小
	 */
	private int pageSize;
	/**
	 * ********************锁定对账单*************************
	 */
	/**
	 * 网上营业厅锁定时间
	 */
	private int lockTime;
	/**
	 * 版本号
	 */
	private short versionNo;
	/**
	 * ********************按对账单号*************************
	 */
	/**
	 * 还款金额
	 */
	private BigDecimal amount;
	/**
	 * 在线支付编号
	 */
	private String onlineNo;
	
	/**
	 * @return beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}
	
	/**
	 * @param beginDate
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	/**
	 * @return endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	 * @return confirmStatus
	 */
	public String getConfirmStatus() {
		return confirmStatus;
	}
	
	/**
	 * @param confirmStatus
	 */
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	
	/**
	 * @return parentBillType
	 */
	public String getParentBillType() {
		return parentBillType;
	}
	
	/**
	 * @param parentBillType
	 */
	public void setParentBillType(String parentBillType) {
		this.parentBillType = parentBillType;
	}
	
	/**
	 * @return billTypeList
	 */
	public List<String> getBillTypeList() {
		return billTypeList;
	}
	
	/**
	 * @param billTypeList
	 */
	public void setBillTypeList(List<String> billTypeList) {
		this.billTypeList = billTypeList;
	}
	
	/**
	 * @return deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}
	
	/**
	 * @param deleteFlag
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	/**
	 * @return pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}
	
	/**
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	/**
	 * @return pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * @return lockTime
	 */
	public int getLockTime() {
		return lockTime;
	}
	
	/**
	 * @param lockTime
	 */
	public void setLockTime(int lockTime) {
		this.lockTime = lockTime;
	}
	
	/**
	 * @return versionNo
	 */
	public short getVersionNo() {
		return versionNo;
	}
	
	/**
	 * @param versionNo
	 */
	public void setVersionNo(short versionNo) {
		this.versionNo = versionNo;
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
	 * @return onlineNo
	 */
	public String getOnlineNo() {
		return onlineNo;
	}
	
	/**
	 * @param onlineNo
	 */
	public void setOnlineNo(String onlineNo) {
		this.onlineNo = onlineNo;
	}
	
}
