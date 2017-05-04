package com.deppon.pda.bdm.module.foss.packagemanager.shared.domain;

/**
 * 
  * @ClassName QryPackageInfo 
  * @Description TODO  
  * @author mt 
  * @date 2013-7-30 上午9:50:48
 */
public class QryPackageInfo {
	//包号
	String packageCode;
	//快递建包类型
		//"THROUGH_ARRIVE";//直达包
		//"NORMAL_ARRIVE";//普通包
	private String expressPackageType;

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	/**
	 * @return the expressPackageType
	 */
	public String getExpressPackageType() {
		return expressPackageType;
	}

	/**
	 * @param expressPackageType the expressPackageType to set
	 */
	public void setExpressPackageType(String expressPackageType) {
		this.expressPackageType = expressPackageType;
	}
	
}
