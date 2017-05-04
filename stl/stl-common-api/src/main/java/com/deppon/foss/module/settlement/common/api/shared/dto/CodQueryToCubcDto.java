package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 对接cubc查询代收货款
 * @author 269044
 * @date 2017-04-07
 *
 */
public class CodQueryToCubcDto implements Serializable {
    
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 开单时间-开始
	 */
	private Date billStartDate;
	
	/**
	 * 开单时间-结束
	 */
	private Date billEndDate;
	
	/**
	 * 客户编码
	 */
	private String customerCode;
	
	/**
	 * 单页显示数量
	 */
	private int pageSize;
	
	/**
	 * 起始位置
	 */
	private int start;
	
	/**
	 * 运单号集合
	 */
	private List<String> wayBillNo;

	public Date getBillStartDate() {
		return billStartDate;
	}

	public void setBillStartDate(Date billStartDate) {
		this.billStartDate = billStartDate;
	}

	public Date getBillEndDate() {
		return billEndDate;
	}

	public void setBillEndDate(Date billEndDate) {
		this.billEndDate = billEndDate;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public List<String> getWayBillNo() {
		return wayBillNo;
	}

	public void setWayBillNo(List<String> wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
