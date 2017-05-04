package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class QueryEffctiveBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3649755356377620660L;
	/**
	 * 始发区域ID
	 */
	private String deptRegionId;
	/**
	 * 产品类型Code
	 */
	private List<String> productList;
	/**
	 * 操作时间
	 */
	private Date currentDateTime;
	
    /**
     * 激活状态
     */
	private String active;

	public String getDeptRegionId() {
		return deptRegionId;
	}

	public void setDeptRegionId(String deptRegionId) {
		this.deptRegionId = deptRegionId;
	}

	public List<String> getProductList() {
		return productList;
	}

	public void setProductList(List<String> productList) {
		this.productList = productList;
	}

	public Date getCurrentDateTime() {
		return currentDateTime;
	}

	public void setCurrentDateTime(Date currentDateTime) {
		this.currentDateTime = currentDateTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
}