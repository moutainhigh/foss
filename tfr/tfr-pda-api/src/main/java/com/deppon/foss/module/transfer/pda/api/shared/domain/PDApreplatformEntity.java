/**
 * 
 */
package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.io.Serializable;


/**
 * @desc 将预分配月台情况返回给pda
 * @author 105795
 *
 */
public class PDApreplatformEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6079494598237333238L;
	//车牌号
	private String vehicleNo;
	//月台号
	private String platformNo;
	//出发部门code
	private String departOrgCode;
	//出发部门name
	private String departOrgName;
	//到达时间格式：2015-05-29 16:37:20
	private String arriveTime;
	
	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	/**
	 * @return the platformNo
	 */
	public String getPlatformNo() {
		return platformNo;
	}
	/**
	 * @param platformNo the platformNo to set
	 */
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	/**
	 * @return the departOrgCode
	 */
	public String getDepartOrgCode() {
		return departOrgCode;
	}
	/**
	 * @param departOrgCode the departOrgCode to set
	 */
	public void setDepartOrgCode(String departOrgCode) {
		this.departOrgCode = departOrgCode;
	}
	/**
	 * @return the departOrgName
	 */
	public String getDepartOrgName() {
		return departOrgName;
	}
	/**
	 * @param departOrgName the departOrgName to set
	 */
	public void setDepartOrgName(String departOrgName) {
		this.departOrgName = departOrgName;
	}
	/**
	 * @return the arriveTime
	 */
	public String getArriveTime() {
		return arriveTime;
	}
	/**
	 * @param arriveTime the arriveTime to set
	 */
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}


}
