/**
*  2014-10-30
*  2014
   201638
*
*/

package com.deppon.pda.bdm.module.foss.packagemanager.shared.domain;

/**  
 *@author 201638
 *@date   2014-10-30
 * 直达包
 */
public class ThroughPackage {
	/**
	 *2014-10-30
	 *ThroughPackage.java
	 *
	 */
	/**包号*/
	private String packageNo;
	
	/**运单号*/
	private String waybillNo;
	
	/**流水号*/
	private String serialNo;
	
	/**当前部门*/
	private String currentOrgCode;
	
	/**包类型*/
	private String packageType;
	
	/**
	 * @return 包号
	 */
	public String getPackageNo() {
		return packageNo;
	}
	/**
	 * @param packageNo the packageNo to set
	 */
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	/**
	 * @return 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return 当前部门
	 */
	public String getCurrentOrgCode() {
		return currentOrgCode;
	}
	/**
	 * @param currentOrgCode the currentOrgCode to set
	 */
	public void setCurrentOrgCode(String currentOrgCode) {
		this.currentOrgCode = currentOrgCode;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	
}
