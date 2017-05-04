package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CenterAndDeliveryAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CenterAndDeliveryAreaDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CenterAndDeliveryBigRegionAreaDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CenterAndDeliverySamlleRegionAreaDto;

public interface ICenterAndDeliveryAreaDao {

 
	/**
	 * 对已经划过的车队的范围进行查询
	 * @param centerAndDeliveryAreaDto
	 * @return
	 */
	public List<CenterAndDeliveryAreaEntity> queryVehicleDept(CenterAndDeliveryAreaDto centerAndDeliveryAreaDto);
	
	/**
	 * 根据管理部门查询对应的大区
	 * @param centerAndDeliveryAreaDto
	 * @return
	 */
	public List<CenterAndDeliveryBigRegionAreaDto> queryBigRegion(CenterAndDeliveryAreaDto centerAndDeliveryAreaDto);
	
	/**
	 * 根据大区的虚拟编码查询大区对应的小区
	 * @param centerAndDeliveryAreaDto
	 * @return
	 */
	public List<CenterAndDeliverySamlleRegionAreaDto> querySmallRegion(String bigRegionCode);
	
	
	/**
	 * 根据管理部门、小区编码、车牌号查询
	 * @param centerAndDeliveryAreaDto
	 * @return
	 */
	public List<CenterAndDeliverySamlleRegionAreaDto> queryManageMentSmallCodevehicleNo (CenterAndDeliveryAreaDto centerAndDeliveryAreaDto);
	
	/**
	 * 根据用户的编码和所要操作的部门仅限权限的验证
	 * @param deptCode
	 * @param empCode
	 * @return
	 */
	public long queryDataPermissions(String deptCode,String empCode);
	
	/**
	 *  保存地图信息
	 * @param andDeliveryAreaEntity
	 * @return
	 * @param andDeliveryAreaEntity
	 * @return
	 */
	public long addVehicleInfo(CenterAndDeliveryAreaEntity andDeliveryAreaEntity);
	/**
	 * 根据GISID更新保存地图信息
	 * @param andDeliveryAreaEntity
	 * @return
	 * @param andDeliveryAreaEntity
	 * @return
	 */
	public long updateVehicleInfo(CenterAndDeliveryAreaEntity andDeliveryAreaEntity);
	/**
	 * 根据GISID删除系统中存在数据
	 * @param andDeliveryAreaEntity
	 * @return
	 * @param andDeliveryAreaEntity
	 * @return
	 */
	public long deleteVehicleInfo(CenterAndDeliveryAreaEntity andDeliveryAreaEntity);
	
	/**
	 *  根据用户的编码查询用户的角色
	 * @param empCode
	 * @return
	 * @param empCode
	 * @return
	 */
	public List<String> queryRolePermissions(String empCode);
	
	
}
