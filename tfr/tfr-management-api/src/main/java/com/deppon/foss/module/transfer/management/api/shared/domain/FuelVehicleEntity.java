/**
 * 
 */
package com.deppon.foss.module.transfer.management.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author Administrator
 *
 */
public class FuelVehicleEntity extends BaseEntity {

	private static final long serialVersionUID = 7201552324021207490L;
	
	//车牌号
    private String vehicleNo;  
    
    //车型
    private String vehicleType;  

    //车辆品牌
    private String vehicleBrand;  

    //事业部
    private String divisionOrgName;  

    //车队
    private String transDepartmentName;  

    //小组
    private String groupOrgName;  

    //所属大区
    private String regionOrgName;  

    //出车性质
    private String departureTypeCode;  

    //备注
    private String remark;

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
	 * @return the vehicleType
	 */
	public String getVehicleType() {
		return vehicleType;
	}

	/**
	 * @param vehicleType the vehicleType to set
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * @return the vehicleBrand
	 */
	public String getVehicleBrand() {
		return vehicleBrand;
	}

	/**
	 * @param vehicleBrand the vehicleBrand to set
	 */
	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	/**
	 * @return the divisionOrgName
	 */
	public String getDivisionOrgName() {
		return divisionOrgName;
	}

	/**
	 * @param divisionOrgName the divisionOrgName to set
	 */
	public void setDivisionOrgName(String divisionOrgName) {
		this.divisionOrgName = divisionOrgName;
	}

	/**
	 * @return the transDepartmentName
	 */
	public String getTransDepartmentName() {
		return transDepartmentName;
	}

	/**
	 * @param transDepartmentName the transDepartmentName to set
	 */
	public void setTransDepartmentName(String transDepartmentName) {
		this.transDepartmentName = transDepartmentName;
	}

	/**
	 * @return the groupOrgName
	 */
	public String getGroupOrgName() {
		return groupOrgName;
	}

	/**
	 * @param groupOrgName the groupOrgName to set
	 */
	public void setGroupOrgName(String groupOrgName) {
		this.groupOrgName = groupOrgName;
	}

	
	/**
	 * @return the regionOrgName
	 */
	public String getRegionOrgName() {
		return regionOrgName;
	}

	/**
	 * @param regionOrgName the regionOrgName to set
	 */
	public void setRegionOrgName(String regionOrgName) {
		this.regionOrgName = regionOrgName;
	}

	/**
	 * @return the departureTypeCode
	 */
	public String getDepartureTypeCode() {
		return departureTypeCode;
	}

	/**
	 * @param departureTypeCode the departureTypeCode to set
	 */
	public void setDepartureTypeCode(String departureTypeCode) {
		this.departureTypeCode = departureTypeCode;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
