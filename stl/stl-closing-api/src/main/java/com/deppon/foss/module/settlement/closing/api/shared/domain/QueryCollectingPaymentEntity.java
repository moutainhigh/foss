package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 218392 zhangyongxue
 * @date 2015-09-07 09:14:50
 * 从CRM查询过来的参数实体
 */

public class QueryCollectingPaymentEntity implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 开始时间
	 */
	private Date beginTime;
	
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	/**
	 * 客户编码
	 */
	private String customerCode;
	
	/**
	 * 单页显示数量
	 */
	private int singlePages;
	
	/**
	 * 起始页
	 */
	private int currentPage;

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public int getSinglePages() {
		return singlePages;
	}

	public void setSinglePages(int singlePages) {
		this.singlePages = singlePages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
