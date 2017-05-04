package com.deppon.pda.bdm.module.foss.packaging.shared.domain;

/**
 * @author 092038
 *
 */
public class WrapedSupplierEntity {
	//包装供应商编码
  private String packageSupplierCode;
    //包装供应商名称
  private String packageSupplierName;
  
  public WrapedSupplierEntity(){}
  
  public WrapedSupplierEntity(String packageSupplierCode,String packageSupplierName){
	  this.packageSupplierCode=packageSupplierCode;
	  this.packageSupplierName=packageSupplierName;
  }
  
  
  
public String getPackageSupplierCode() {
	return packageSupplierCode;
}
public void setPackageSupplierCode(String packageSupplierCode) {
	this.packageSupplierCode = packageSupplierCode;
}
public String getPackageSupplierName() {
	return packageSupplierName;
}
public void setPackageSupplierName(String packageSupplierName) {
	this.packageSupplierName = packageSupplierName;
}
  
  
  
  
}
