package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CenterAndDeliveryAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CenterAndDeliveryAreaDto;

public interface ICenterAndDeliveryAreaService {

	/**
	 * /**
	 * 1、车队和车牌号为条件
	 * 2、车队和大区为条件
	 * 3、一车队为条件
	 * 4、车队和小区为条件
	 * @param centerAndDeliveryAreaDto
	 * @return
	 */
	public CenterAndDeliveryAreaEntity  queryVehicleInfo(CenterAndDeliveryAreaDto centerAndDeliveryAreaDto);
	/**
	 * 保存地图信息
	 * @param andDeliveryAreaEntity
	 * @return
	 */
	public boolean addVehicleInfo(CenterAndDeliveryAreaEntity andDeliveryAreaEntity);
	/**
	 * 根据GISID更新保存地图信息
	 * @param andDeliveryAreaEntity
	 * @return
	 */
	public boolean updateVehicleInfo(CenterAndDeliveryAreaEntity andDeliveryAreaEntity);
	/**
	 * 根据GISID删除系统中存在数据
	 * @param andDeliveryAreaEntity
	 * @return
	 */
	public boolean deleteVehicleInfo(CenterAndDeliveryAreaEntity andDeliveryAreaEntity);
	
	/**
	 * 查询用户的角色权限
	 * @return
	 */
	public CenterAndDeliveryAreaEntity queryRolePermissions();
	/**
	 * 根据用户的部门名称和编码查询改用户的数据权限
	 * @param deptCode
	 * @param empCode
	 * @return
	 */
	public long queryDataPermissions(String deptCode,String empCode);
	/**
	 * 根据用户的编码查询用户的角色
	 * @param empCode
	 * @return
	 */
	public boolean queryRoles(String empCode);
	
}
