package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 始发外请车报表DTO
 * @author lianghaisheng
 *
 */
public class MvrOrccQueryDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 期间. */
	private String period;
	
	/** 业务类型. */
	private String productCode;
	
	/** 业务类型集合. */
	private List<String> productCodeList;
	
	/** 始发部门编码. */
	private String origOrgCode;
	
	/** 始发部门名称. */
	private String origOrgName;
	
	/** 到达部门编码. */
	private String destOrgCode;
	
	/** 到达部门名称. */
	private String destOrgName;

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public List<String> getProductCodeList() {
		return productCodeList;
	}

	public void setProductCodeList(List<String> productCodeList) {
		this.productCodeList = productCodeList;
	}

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public String getOrigOrgName() {
		return origOrgName;
	}

	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	public String getDestOrgName() {
		return destOrgName;
	}

	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	
}
