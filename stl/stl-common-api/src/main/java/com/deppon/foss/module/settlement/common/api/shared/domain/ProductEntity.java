package com.deppon.foss.module.settlement.common.api.shared.domain;

/**
 * 产品类型
 * 
 * @author huanglewei
 * @date 2015-08-22 下午5:19:00
 */
public class ProductEntity {

	/**
	 * 产品代码
	 */
	private String productCode;
	
	/**
	 * 产品名称
	 */
	private String productName;
	
	/**
	 * 是否属于快递，进凭证报表时判断是否属于标准快递
	 */
	private String isBelongPackage;
	
	/**
	 * 进凭证月结表和折扣表时对应的产品代码
	 */
	private String monDisCode;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getIsBelongPackage() {
		return isBelongPackage;
	}

	public void setIsBelongPackage(String isBelongPackage) {
		this.isBelongPackage = isBelongPackage;
	}

	public String getMonDisCode() {
		return monDisCode;
	}

	public void setMonDisCode(String monDisCode) {
		this.monDisCode = monDisCode;
	}
	
}
