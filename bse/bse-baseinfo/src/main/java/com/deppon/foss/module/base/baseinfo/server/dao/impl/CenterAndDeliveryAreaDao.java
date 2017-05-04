package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICenterAndDeliveryAreaDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CenterAndDeliveryAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CenterAndDeliveryAreaDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CenterAndDeliveryBigRegionAreaDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CenterAndDeliverySamlleRegionAreaDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 1、车队和车牌号为条件
 * 2、车队和大区为条件
 * 3、一车队为条件
 * 4、车队和小区为条件
 */
public class CenterAndDeliveryAreaDao extends SqlSessionDaoSupport implements ICenterAndDeliveryAreaDao {

	private static final String NAMESPACE = "foss.bse.bse-baseinfo"
		    + ".centerAndDeliveryArea.";
	
	 
	/**
	 * 查询车队级联关系
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CenterAndDeliveryAreaEntity> queryVehicleDept(
			CenterAndDeliveryAreaDto centerAndDeliveryAreaDto) {
		 
		return this.getSqlSession().selectList(NAMESPACE+"queryVehicleInfo", centerAndDeliveryAreaDto);
	}

	/**
	 * /**
	 * 根据管理部门查询对应的大区
	 * @param centerAndDeliveryAreaDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CenterAndDeliveryBigRegionAreaDto> queryBigRegion(CenterAndDeliveryAreaDto centerAndDeliveryAreaDto) {
	 
		
		return this.getSqlSession().selectList(NAMESPACE+"big", centerAndDeliveryAreaDto);
	}

	/** 
	 *  根据大区的虚拟编码查询大区对应的小区
	 * @param centerAndDeliveryAreaDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CenterAndDeliverySamlleRegionAreaDto> querySmallRegion(String bigRegionCode) {
		
		return this.getSqlSession().selectList(NAMESPACE+"small", bigRegionCode);
	}
	
	
	/**
	 * 根据小区的编码或者小区的车牌号查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CenterAndDeliverySamlleRegionAreaDto> queryManageMentSmallCodevehicleNo (CenterAndDeliveryAreaDto centerAndDeliveryAreaDto){
		
		return  this.getSqlSession().selectList(NAMESPACE+"byManageMentAnd", centerAndDeliveryAreaDto);
	}

	/**
	 * 根据用户的编码和所要操作的部门仅限权限的验证
	 * @param deptCode
	 * @param empCode
	 * @return
	 */
	@Override
	public long queryDataPermissions(String deptCode,String empCode){
		Map<String,String> map=new HashMap<String,String>();
		map.put("deptCode", deptCode);
		map.put("empCode", empCode);
		return  (Long) this.getSqlSession().selectOne(NAMESPACE+"queryDataPermissions", map);
	}

	/**
	 *  保存地图信息
	 * @param andDeliveryAreaEntity
	 * @return
	 */
	@Override
	public long addVehicleInfo(CenterAndDeliveryAreaEntity andDeliveryAreaEntity) {
		andDeliveryAreaEntity.setId(UUIDUtils.getUUID());
		andDeliveryAreaEntity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().insert(NAMESPACE+"addVehicleInfo", andDeliveryAreaEntity);
	}

	/**
	 *  根据GISID更新保存地图信息
	 * @param andDeliveryAreaEntity
	 * @return
	 */
	@Override
	public long updateVehicleInfo(
			CenterAndDeliveryAreaEntity andDeliveryAreaEntity) {
		 
		return this.getSqlSession().update(NAMESPACE+"updateVehicleInfo", andDeliveryAreaEntity);
	}

	/**
	 *  根据GISID删除系统中存在数据
	 * @param andDeliveryAreaEntity
	 * @return
	 */
	@Override
	public long deleteVehicleInfo(
			CenterAndDeliveryAreaEntity andDeliveryAreaEntity) {
		 
		return this.getSqlSession().update(NAMESPACE+"deleteVehicleInfo", andDeliveryAreaEntity);
	}

	
	/**
	 * 根据用户的编码查询用户的角色
	 * @param empCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryRolePermissions(String empCode) {
		 Map<String,String> map=new HashMap<String, String>();
		 map.put("empCode", empCode);
		return this.getSqlSession().selectList(NAMESPACE+"queryRolePermissions", map);
	}
}
