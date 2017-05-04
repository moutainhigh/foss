package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleDrivingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleDrivingQueryDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

public interface IVehicleDrivingDao {

	/**
	 * @author 323136
	 * 新增长途车队实体信息
	 */
	public int addVehicleDriving(VehicleDrivingEntity entity,CurrentInfo info);
	
	/**
	 * @author 323136
	 * 修改长途车队实体信息
	 */
	public int updateVehicleDriving(VehicleDrivingQueryDto queryDto,CurrentInfo info);
	
	
	/**
	 * @author 323136
	 * 查询长途车队总条数
	 */
	public long queryTotalByCondition(VehicleDrivingQueryDto queryDto);

	/**
	 * @author 323136
	 * 分页查询长途车队
	 */
	public List<VehicleDrivingEntity> queryVehicleDrivingByCondition(VehicleDrivingQueryDto queryDto
			,int start,int limit);
	
	/**
	 * @author 323136
	 * 根据车队和下辖部门查询
	 */
	public List<VehicleDrivingEntity> queryVehicleDrivingByFleetDep(String longHaulFleetCode,String departmentCode );
	
	
	/**
	 * @author 323136
	 * 判断是否有重复的行车编码
	 */
	 public List<VehicleDrivingEntity> queryVehicleDrivingByFleetTra(String longHaulFleetCode,String trafficCode);
	 
	
	
	
}
