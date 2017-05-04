package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehicleEntity;

/**
 * 查询快递车辆信息
 * 
 * @author WangPeng
 * @date   2013-07-30 10:47 AM
 *
 */
public class ExpressVehicleDto extends ExpressVehicleEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4530729967681701707L;

	/**
	 * 快递员名称
	 */
	private String empName;
	
	/**
	 * 开单营业部名称
	 */
	private String orgName;
	
	/**
	 * 所属营业部编码
	 */
	private String ownDeptCode;
	
	/**
	 * 所属营业部名称
	 */
	private String ownDeptName;
	
	/**
	 * 车型名称
	 */
	private String vehicleLengthName;
	
	/**
	 * 行政区域列表
	 */
	private String[] districtCodes;
	
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * @return the ownDeptCode
	 */
	public String getOwnDeptCode() {
		return ownDeptCode;
	}
	/**
	 * @param ownDeptCode the ownDeptCode to set
	 */
	public void setOwnDeptCode(String ownDeptCode) {
		this.ownDeptCode = ownDeptCode;
	}
	/**
	 * @return the ownDeptName
	 */
	public String getOwnDeptName() {
		return ownDeptName;
	}
	/**
	 * @param ownDeptName the ownDeptName to set
	 */
	public void setOwnDeptName(String ownDeptName) {
		this.ownDeptName = ownDeptName;
	}
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return the districtCodes
	 */
	public String[] getDistrictCodes() {
		return districtCodes;
	}
	/**
	 * @param districtCodes the districtCodes to set
	 */
	public void setDistrictCodes(String[] districtCodes) {
		this.districtCodes = districtCodes;
	}
	/**
	 * @return the vehicleLengthName
	 */
	public String getVehicleLengthName() {
		return vehicleLengthName;
	}
	/**
	 * @param vehicleLengthName the vehicleLengthName to set
	 */
	public void setVehicleLengthName(String vehicleLengthName) {
		this.vehicleLengthName = vehicleLengthName;
	}

}
