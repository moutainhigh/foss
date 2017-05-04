package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.util.Date;

/**
 * 应收账款查询条件Dto
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-24 下午4:30:41
 */
public class BillReceivableQueryDto {

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 超期日期
	 */
	private Date overdueDate;

	/**
	 * 运单单号
	 */
	private String waybillNO;

	/**
	 * 付款方式
	 */
	private String paymentType;

	/**
	 * 单据值类型
	 */
	private String billType;

	/**
	 * 是否红单
	 */
	private String isRedBack;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 起始日期
	 */
	private Date inceptDate;

	/**
	 * 结束日期
	 */
	private Date endDate;

	/**
	 * 是否初始化
	 */
	private String isInit;

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
	 * @return overdueDate
	 */
	public Date getOverdueDate() {
		return overdueDate;
	}

	/**
	 * @param overdueDate
	 */
	public void setOverdueDate(Date overdueDate) {
		this.overdueDate = overdueDate;
	}

	/**
	 * @return waybillNO
	 */
	public String getWaybillNO() {
		return waybillNO;
	}

	/**
	 * @param waybillNO
	 */
	public void setWaybillNO(String waybillNO) {
		this.waybillNO = waybillNO;
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
	 * @return inceptDate
	 */
	public Date getInceptDate() {
		return inceptDate;
	}

	/**
	 * @param inceptDate
	 */
	public void setInceptDate(Date inceptDate) {
		this.inceptDate = inceptDate;
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

	public String getIsInit() {
		return isInit;
	}

	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}

}
