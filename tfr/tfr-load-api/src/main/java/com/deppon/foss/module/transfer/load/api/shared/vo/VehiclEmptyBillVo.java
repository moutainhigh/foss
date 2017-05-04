package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.VehiclEmptyBillDto;

public class VehiclEmptyBillVo extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String vehiclEmptyBillNo;
	//查询空驶单返回list
	private List<VehiclEmptyBillDto> queryvehiclEmptyBill;
	//空驶单
	private VehiclEmptyBillDto vehiclEmptyBillDto;
	
	public List<VehiclEmptyBillDto> getQueryvehiclEmptyBill() {
		return queryvehiclEmptyBill;
	}
	public void setQueryvehiclEmptyBill(
			List<VehiclEmptyBillDto> queryvehiclEmptyBill) {
		this.queryvehiclEmptyBill = queryvehiclEmptyBill;
	}
	public VehiclEmptyBillDto getVehiclEmptyBillDto() {
		return vehiclEmptyBillDto;
	}
	public void setVehiclEmptyBillDto(VehiclEmptyBillDto vehiclEmptyBillDto) {
		this.vehiclEmptyBillDto = vehiclEmptyBillDto;
	}
	public String getVehiclEmptyBillNo() {
		return vehiclEmptyBillNo;
	}
	public void setVehiclEmptyBillNo(String vehiclEmptyBillNo) {
		this.vehiclEmptyBillNo = vehiclEmptyBillNo;
	}

}
