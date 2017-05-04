package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.BindingLeasedTruckDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleException;

public interface IBindingLeasedVehicleDao {

	/**
	 * 查询已绑定的外请车列表
	 * @Title: queryBindingLeasedVehicleList 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param leasedTruck
	 * @param @param offset
	 * @param @param limit
	 * @param @return
	 * @param @throws LeasedVehicleException    设定文件 
	 * @return List<BindingLeasedTruckDto>    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
 	List<BindingLeasedTruckDto> queryBindingLeasedVehicleList(BindingLeasedTruckDto leasedTruck, int offset, int limit) throws LeasedVehicleException;
 	
 	/**
 	 * 查询已绑定的外请车的总数
 	 * @Title: queryBindingLeasedVehicleListTotal 
 	 * @Description: TODO(这里用一句话描述这个方法的作用) 
 	 * @param @param leasedTruck
 	 * @param @return
 	 * @param @throws LeasedVehicleException    设定文件 
 	 * @return long    返回类型 
 	 * @throws 
 	 * @user 310854-liuzhenhua
 	 */
 	long queryBindingLeasedVehicleListTotal(BindingLeasedTruckDto leasedTruck) throws LeasedVehicleException;
 	
 	/**
	 * 通过车牌号获取绑定的顶级车队
	 * @Title: queryOrgNameByVehicleNo 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param vehicleNo
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public String queryOrgNameByVehicleNo(String vehicleNo);
}
