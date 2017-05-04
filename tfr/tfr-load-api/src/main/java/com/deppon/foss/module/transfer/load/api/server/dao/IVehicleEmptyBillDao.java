package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.dto.VehiclEmptyBillDto;


public interface IVehicleEmptyBillDao {

	/**
	 * 空驶单查询
	 *  @author zhangpeng
	 * @date 2015-10-10 上午10:51:04
	 */
	List<VehiclEmptyBillDto> queryVehicleEmptyBill(VehiclEmptyBillDto vehiclEmDto,int limit,int start);
	
	/**
	 * 空驶单查询 获取查询的总记录数
	 * @author zhangpeng
	 * @date 2015-10-10 上午10:51:04
	 */
	Long getVehiclEmptyBillCount(VehiclEmptyBillDto vehiclEmDto);
	/**
	 * 通过空驶单号查询车辆状态是否已经出发
	 * @author zhangpeng
	 * @date 2015-10-10 上午10:51:04
	 */
	Long searchStatusByVehiclEmptyBillNo(String vehiclEmptyBillNo); 
	/**
	 * 通过空驶单号查询车辆状态是否已经出发
	 * @author zhangpeng
	 * @date 2015-10-10 上午10:51:04
	 */
	Long  searchStatusByVehicleNoIsDepart(String vehicleNo); 
	/**
	 * 删除空驶单号
	 * @author zhangpeng
	 * @date 2015-10-30下午4:44:04
	 * @param dtoList，属性：vehicleNo空驶单号，)
	 */
	int updateVehiclEmptyBillState(String vehicleNo);
	
	/**
	 * 根据运单号查询车辆任务ID，车辆明细ID，车辆单据ID
	 * @author zhangpeng
	 * @date 2015-10-30下午4:44:04
	 * @param dtoList，属性：vehicleNo空驶单号，)
	 */
	
	VehiclEmptyBillDto queryTruckTaskIdByVehiclEmptyBillNo(String vehiclEmptyBillNo);
	
	/**
	 * 根据车辆明细ID删除明细表记录
	 * @author zhangpeng
	 * @date 2015-10-30下午4:44:04
	 * @param dtoList，属性：车辆明细Id，)
	 */
     int deleteTruckTaskDetail(String truckTaskDetailId);
    
     /**
 	 * 根据车辆任务ID删除车辆任务记录
 	 * @author zhangpeng
 	 * @date 2015-10-30下午4:44:04
 	 * @param dtoList，属性：车辆任务Id，)
 	  */
	
 	int deleteTruckTask(String truckTaskId);
    
     /**
  	 * 根据空驶单号删除单据表记录
  	 * @author zhangpeng
  	 * @date 2015-10-30下午4:44:04
  	 * @param dtoList，属性：车辆单据Id，)
  	  */
	 int deleteTruckTaskBill(String vehiclEmptyBillNo);

	
	
}
