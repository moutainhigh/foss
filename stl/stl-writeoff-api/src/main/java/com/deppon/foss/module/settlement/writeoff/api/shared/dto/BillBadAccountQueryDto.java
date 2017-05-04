package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 坏账查询Dto
 * 
 * @author foss-wangxuemin
 * @date Dec 10, 2012 1:35:12 PM
 */
public class BillBadAccountQueryDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1377518918947835264L;

	/**
	 * 查询类型
	 */
	private String queryType;

	/**
	 * 客户编号
	 */
	private String customerCode;

	/**
	 * 开始时间
	 */
	private Date beginTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 运单号
	 */
	private List<String> wayBillNum;

	/**
	 * 是否生效
	 */
	private String active;

	
	/**
	 * @return queryType
	 */
	public String getQueryType() {
		return queryType;
	}

	
	/**
	 * @param queryType
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
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
	 * @return beginTime
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	
	/**
	 * @param beginTime
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	
	/**
	 * @return endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	
	/**
	 * @param endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
	/**
	 * @return wayBillNum
	 */
	public List<String> getWayBillNum() {
		return wayBillNum;
	}

	
	/**
	 * @param wayBillNum
	 */
	public void setWayBillNum(List<String> wayBillNum) {
		this.wayBillNum = wayBillNum;
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

	

}
