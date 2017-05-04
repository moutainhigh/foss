package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

/**
 * 已绑定外请车的实体
 * @ClassName: BindingLeasedTruckDto 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author 310854-liuzhenhua
 * @date 2016-9-8 下午4:52:58 
 *
 */
public class BindingLeasedTruckDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 车型
	 */
//	private String vehicleLengthCode;

	/**
	 * 车型名称
	 */
	private String vehicleLengthName;

	/**
	 * 司机姓名
	 */
	private String driverName;
	/**
	 * 司机手机
	 */
	private String driverPhone;
	/**
	 * 是否敞篷
	 */
	private String openVehicle;

	/**
	 * 是否有尾板
	 */
	private String tailBoard;
	
	private String orgCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getOpenVehicle() {
		return openVehicle;
	}

	public void setOpenVehicle(String openVehicle) {
		this.openVehicle = openVehicle;
	}

	public String getTailBoard() {
		return tailBoard;
	}

	public void setTailBoard(String tailBoard) {
		this.tailBoard = tailBoard;
	}

/*	public String getVehicleLengthCode() {
		return vehicleLengthCode;
	}

	public void setVehicleLengthCode(String vehicleLengthCode) {
		this.vehicleLengthCode = vehicleLengthCode;
	}
*/
	public String getVehicleLengthName() {
		return vehicleLengthName;
	}

	public void setVehicleLengthName(String vehicleLengthName) {
		this.vehicleLengthName = vehicleLengthName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}