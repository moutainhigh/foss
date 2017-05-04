package com.deppon.foss.module.pickup.order.api.shared.vo;

import java.io.Serializable;
import java.util.List;
import com.deppon.foss.module.pickup.order.api.shared.dto.DriverQueryDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderVehViewSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleViewSmallZoneDto;

public class OrderVehVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 查询参数
	 */
	private DriverQueryDto queryDto;
	
	/**
	 * 选择人员工号 集合
	 */
	private List<String> changeStatusEmpCodes;
	
	/**
	 * 返回的签到车辆信息集合
	 */
	private List<OwnTruckSignDto> ownTruckSignList;
	
	/**
	 * 返回的签到司机对应的订单信息
	 */
	private List<OrderVehViewSignDto> orderVehViewSignList;
	
	/**
	 * 选择车辆 集合
	 */
	private List<String> vehicleNos;
	
	
	/**
	 * 返回GIS查询需要显示的车辆信息集合
	 * 
	 */
	private List<VehicleViewSmallZoneDto> vehSmallZones;
	
	public DriverQueryDto getQueryDto() {
		return queryDto;
	}

	public void setQueryDto(DriverQueryDto queryDto) {
		this.queryDto = queryDto;
	}

	public List<String> getChangeStatusEmpCodes() {
		return changeStatusEmpCodes;
	}

	public void setChangeStatusEmpCodes(List<String> changeStatusEmpCodes) {
		this.changeStatusEmpCodes = changeStatusEmpCodes;
	}

	public List<OwnTruckSignDto> getOwnTruckSignList() {
		return ownTruckSignList;
	}

	public void setOwnTruckSignList(List<OwnTruckSignDto> ownTruckSignList) {
		this.ownTruckSignList = ownTruckSignList;
	}

	public List<OrderVehViewSignDto> getOrderVehViewSignList() {
		return orderVehViewSignList;
	}

	public void setOrderVehViewSignList(
			List<OrderVehViewSignDto> orderVehViewSignList) {
		this.orderVehViewSignList = orderVehViewSignList;
	}

	public List<String> getVehicleNos() {
		return vehicleNos;
	}

	public void setVehicleNos(List<String> vehicleNos) {
		this.vehicleNos = vehicleNos;
	}

	public List<VehicleViewSmallZoneDto> getVehSmallZones() {
		return vehSmallZones;
	}

	public void setVehSmallZones(List<VehicleViewSmallZoneDto> vehSmallZones) {
		this.vehSmallZones = vehSmallZones;
	}
	
}
