package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrRfdEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfdDto;

/**
 * 专线到达VO.
 * 
 * @author guxinhua
 * @date 2013-3-6 上午11:16:09
 */
public class MvrRfdVo implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = -4623302613676100797L;

	// 查询条件
	/** 期间. */
	private String period;

	/** 业务类型. */
	private String productCode;
	
	/** 业务类型集合. */
	private List<String> productCodeList;

	/** 客户编码. */
	private String customerCode;

	/** 客户名称. */
	private String customerName;

	/** 始发部门编码. */
	private String origOrgCode;

	/** 始发部门名称. */
	private String origOrgName;

	/** 到达部门编码. */
	private String destOrgCode;

	/** 到达部门名称. */
	private String destOrgName;

	/** 合计专线到达. */
	private MvrRfdDto mvrRfdDto;

	/** 专线到达集合信息. */
	private List<MvrRfdEntity> mvrRfdEntityList;

	/**
	 * Gets the period.
	 * 
	 * @return period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * Sets the period.
	 * 
	 * @param period
	 *            the new period
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * Gets the product code.
	 * 
	 * @return productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the product code.
	 * 
	 * @param productCode
	 *            the new product code
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Gets the customer code.
	 * 
	 * @return customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * Sets the customer code.
	 * 
	 * @param customerCode
	 *            the new customer code
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * Gets the customer name.
	 * 
	 * @return customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Sets the customer name.
	 * 
	 * @param customerName
	 *            the new customer name
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Gets the orig org code.
	 * 
	 * @return origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * Sets the orig org code.
	 * 
	 * @param origOrgCode
	 *            the new orig org code
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * Gets the orig org name.
	 * 
	 * @return origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * Sets the orig org name.
	 * 
	 * @param origOrgName
	 *            the new orig org name
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * Gets the dest org code.
	 * 
	 * @return destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * Sets the dest org code.
	 * 
	 * @param destOrgCode
	 *            the new dest org code
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * Gets the dest org name.
	 * 
	 * @return destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * Sets the dest org name.
	 * 
	 * @param destOrgName
	 *            the new dest org name
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * Gets the mvr rfd entity list.
	 *
	 * @return mvrRfdEntityList
	 */
	public List<MvrRfdEntity> getMvrRfdEntityList() {
		return mvrRfdEntityList;
	}

	/**
	 * Sets the mvr rfd entity list.
	 *
	 * @param mvrRfdEntityList the new mvr rfd entity list
	 */
	public void setMvrRfdEntityList(List<MvrRfdEntity> mvrRfdEntityList) {
		this.mvrRfdEntityList = mvrRfdEntityList;
	}

	/**
	 * Sets the mvr rfd dto.
	 * 
	 * @param mvrRfdDto
	 *            the new mvr rfd dto
	 */
	public void setMvrRfdDto(MvrRfdDto mvrRfdDto) {
		this.mvrRfdDto = mvrRfdDto;
	}

	/**
	 * Gets the mvr rfd dto.
	 *
	 * @return mvrRfdDto
	 */
	public MvrRfdDto getMvrRfdDto() {
		return mvrRfdDto;
	}

	/**
	 * @return productCodeList
	 */
	public List<String> getProductCodeList() {
		return productCodeList;
	}

	/**
	 * @param productCodeList
	 */
	public void setProductCodeList(List<String> productCodeList) {
		this.productCodeList = productCodeList;
	}

	
}
