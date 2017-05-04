package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.dto.VehiclEmptyBillDto;

/*
 * AUTHOR  : FOSS中转系统开发组
 *
 */ 
public interface IvehiclEmptyBillService {
	
	/*空驶单查询*/ 
   List<VehiclEmptyBillDto> queryVehicleEmptyBill(VehiclEmptyBillDto vehiclEmDto,int limit,int start);
  
   
   /**
	 * 空驶单查询 获取查询的总记录数
	 * @author zhangpeng
	 * @date 2015-10-10 下午18:10:14
	 */
   Long queryVehiclEmptyBillCount(VehiclEmptyBillDto vehiclEmDto);
   
   
   /**
 	 * 空驶单保存
 	 * @author zhangpeng
 	 * @date 2015-10-10 下午18:10:14
 	 */
   String saveVehiclEmptyBill(VehiclEmptyBillDto vehiclEmDto);
   
   /**
	 * 根据车牌号查询车辆状态
	 * @author zhangpeng
	 * @date 2015-10-10 下午18:10:14
	 */
   Long searchStatusByVehicleNo(String vehicleNo);
   
   /**
	 * 根据空驶单号查询车辆状态是否在途，已经到达
	 * @author zhangpeng
	 * @date 2015-10-10 下午18:10:14
	 */
   Long searchStatusByVehiclEmptyBillNo(String vehicleEmpNo);
  
   /**
	 *通过空驶单删除车辆任务表，车辆明细表，车辆单据表
	 * @author 045923-foss-zhangpeng
	 * @date 2015-10-29 下午3:51:49
	 */
  int deleteTruckTaskByVehiclEmptyBill(String vehiclEmptyBillNo);

}
