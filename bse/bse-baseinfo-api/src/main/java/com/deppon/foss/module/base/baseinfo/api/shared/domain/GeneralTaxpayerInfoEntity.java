package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 一般纳税人信息
 * @author 308861 
 * @date 2016-2-28 下午2:50:45
 * @since
 * @version
 */
public class GeneralTaxpayerInfoEntity extends BaseEntity{
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	private String crmId;
	/**
	 * 税务登记号
	 */
	private String taxId;
	
	/**
	 * 发票抬头
	 */
	private String billTitle;
	
	/**
	 * 开户行
	 */
	private String bankName;
	
	/**
	 * 银行账号
	 */
	private String bankNumber;
	
	/**
	 * 是否一般纳税人
	 */
	private String isTaxpayer;
	
	/**
	 * 注册电话
	 */
	private String regTel;
	
	/**
	 * 注册地址
	 */
	private String regAddress;
	
	/**
	 * 创建部门
	 */
	private String createDepartment;
	/**
	 * 是否启用
	 */
	private String active;
	/**
	 * 操作类型
	 */
	private String operation;
	
	/**
	 * 版本号
	 */
	private String versionNo;
	
	public String getCrmId() {
		return crmId;
	}

	public void setCrmId(String crmId) {
		this.crmId = crmId;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getBillTitle() {
		return billTitle;
	}

	public void setBillTitle(String billTitle) {
		this.billTitle = billTitle;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public String getIsTaxpayer() {
		return isTaxpayer;
	}

	public void setIsTaxpayer(String isTaxpayer) {
		this.isTaxpayer = isTaxpayer;
	}

	public String getRegTel() {
		return regTel;
	}

	public void setRegTel(String regTel) {
		this.regTel = regTel;
	}

	public String getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}
	
	public String getCreateDepartment() {
		return createDepartment;
	}

	public void setCreateDepartment(String createDepartment) {
		this.createDepartment = createDepartment;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
}
