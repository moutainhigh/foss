/**
 * 
 */
package com.deppon.foss.module.transfer.management.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author Administrator
 *
 */
public class FuelStandardEntity extends BaseEntity {

	private static final long serialVersionUID = 6959823242593340116L;
	//油耗时间
	private Date fuelTime;
	//车牌号
	private String vehicleNo;
	//油耗标准
	private BigDecimal fuelStandard;
	//是否启用
	private String active;
	/**
	 * @return the fuelTime
	 */
	public Date getFuelTime() {
		return fuelTime;
	}
	/**
	 * @param fuelTime the fuelTime to set
	 */
	public void setFuelTime(Date fuelTime) {
		this.fuelTime = fuelTime;
	}
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
	 * @return the fuelStandard
	 */
	public BigDecimal getFuelStandard() {
		return fuelStandard;
	}
	/**
	 * @param fuelStandard the fuelStandard to set
	 */
	public void setFuelStandard(BigDecimal fuelStandard) {
		this.fuelStandard = fuelStandard;
	}
	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}
	

}
