/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.shared.request;

import java.io.Serializable;
import java.util.Date;

/**
 * CubcQueryTotalAmountRequest
 * @author 198771-zhangwei
 * 2017-1-6 下午6:07:32
 */
public class CubcQueryTotalAmountRequest implements Serializable{
	private Date beginTime;
	
	private Date endTime;
	
	private String  customerCode;

	/**
	 * @return the beginTime
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime the beginTime to set
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
}
