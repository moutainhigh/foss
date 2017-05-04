package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.io.Serializable;

/**
 * 
 * 客户实体类(为接送货准备)
 * @author 308861 
 * @date 2017-1-10 下午5:28:24
 * @since
 * @version
 */
public class CustomerNewEntity implements Serializable{
    /**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
    /**
     * 是否大客户标记
     */
    private String isLargeCustomers;
    /**
     * 客户分群
     */
    private String flabelleavemonth;
    /**
     * 是否电子运单大客户
     */
    private String isElecBillBigCust;
    
	public String getIsLargeCustomers() {
		return isLargeCustomers;
	}
	public void setIsLargeCustomers(String isLargeCustomers) {
		this.isLargeCustomers = isLargeCustomers;
	}
	public String getFlabelleavemonth() {
		return flabelleavemonth;
	}
	public void setFlabelleavemonth(String flabelleavemonth) {
		this.flabelleavemonth = flabelleavemonth;
	}
	public String getIsElecBillBigCust() {
		return isElecBillBigCust;
	}
	public void setIsElecBillBigCust(String isElecBillBigCust) {
		this.isElecBillBigCust = isElecBillBigCust;
	}
}